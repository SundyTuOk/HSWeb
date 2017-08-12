package com.sf.energy.prepayment.dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import oracle.net.aso.f;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//import com.sf.energy.map.model.AmMeterModel;
import com.sf.energy.prepayment.model.BatchOprModel;
import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.util.ConnDB;

public class BatchOprDao
{

	// 查询预付费电表的所有Id
	public List<String> queryAmMeterId() throws SQLException
	{
		List<String> amMeterIdArrayList = new ArrayList<String>();
		String sql = "select T1.*,(select  SYValue from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime)SYValue from  (select AmMeter_ID, AmMeter_Name, Floor,(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Price_Value from (Price)b where a.Price_ID=b.Price_ID)Price,Beilv,nvl(APState,'未开户')APState,AmMeter_Stat,IsSupply from (AmMeter)a where  Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1))T1 left join (select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID order by Architecture_Name asc";
		;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				amMeterIdArrayList.add(rs.getString("AmMeter_ID"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return amMeterIdArrayList;
	}

	// 解析预付费
	public BatchOprModel parseXml(XMLCoder xmlCoder, String info, String flag, String Meter_ID, String TheSaleInfoNum, double QSYValue, String Times, String htmlShow)
			throws ParserConfigurationException, SAXException, IOException, SQLException, NumberFormatException, ParseException
	{
		// double QSYValue=0;//购电前剩余金额
		double HSYValue = 0;// 购电后剩余金额
		// String Times="";//购电次数
		// String TheSaleInfoNum="";//购电单号
		String SYValue = "";// 剩余电量
		double TZValue = 0;// 透支电量
		String ValueTime = "";// 当前时间
		double theBeilv = 1;// 倍率

		double TotleUsed = 0;
		BatchOprModel batchOprModel = new BatchOprModel();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(info)));
		Element root = doc.getDocumentElement();
		Element gy = (Element) root.getFirstChild();
		NodeList gwList = doc.getElementsByTagName("GW");
		Element tasknum = (Element) gwList.item(0);
		String task = tasknum.getAttribute("tasknum");
		NodeList rootList = doc.getElementsByTagName("commandback");
		Element node = (Element) rootList.item(0);
		String code = node.getAttribute("code");
		String error = node.getAttribute("error");

		NodeList resultList = null;
		Element resultNode = null;
		
		if("0".equals(error)){
			resultList = doc.getElementsByTagName("result");
			resultNode = (Element) resultList.item(0);
			//比较tasknum
			if(!task.equals(xmlCoder.getTaskNum())){
				error = "1";
			}else{
				String dataid = "";
				String meterAddress = "";
				if(resultNode.hasAttribute("DataID")){
					dataid = resultNode.getAttribute("DataID");
					if(resultNode.hasAttribute("Meter_Address")){
						meterAddress = resultNode.getAttribute("Meter_Address");
					}
					if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
						error = "1";
					}
				}else {
					if(resultNode.hasAttribute("Meter_Address")){
						meterAddress = resultNode.getAttribute("Meter_Address");
					}
					if(!meterAddress.equals(xmlCoder.getMeterAddress())){
						error = "1";
					}
				}
				
			}
		}
		
		
		// System.out.println("code:"+code+"  error:"+error);
		if ("1".equals(error))
		{
			batchOprModel.setIsError("1");
		} else
		{
			batchOprModel.setIsError("0");
			//NodeList resultList = doc.getElementsByTagName("result");
			//Element resultNode = (Element) resultList.item(0);
			// 读购电前剩余电量
			if ("1".equals(flag))
			{
				SYValue = resultNode.getAttribute("SYValue");
				TZValue = Double.parseDouble(resultNode.getAttribute("TZValue"));
				if ("-1".equals(SYValue))
				{

				}
				ValueTime = resultNode.getAttribute("ValueTime");
				TZValue = TZValue * theBeilv;
				QSYValue = Double.parseDouble((SYValue)) * theBeilv;
				batchOprModel.setQSYValue(QSYValue);
				// 购电次数
				Times = resultNode.getAttribute("BuyTimes");
				batchOprModel.setBuyTimes(Integer.parseInt(Times));
				htmlShow = "剩余电量:" + QSYValue + "度，透支电量：" + TZValue + "度，购电次数：" + Times + "/";
				batchOprModel.setHtmlShow(htmlShow);

			}
			// 下发购电量
			else if ("2".equals(flag))
			{
				// Times=resultNode.getAttribute("BuyTimes");
				SerSaveSendState(Meter_ID, TheSaleInfoNum, "1", QSYValue + "", HSYValue + "", "", Times, "-1");
				htmlShow = htmlShow + "下发成功" + "/";
				batchOprModel.setHtmlShow(htmlShow);

			} else if ("3".equals(flag))
			{

				Times = resultNode.getAttribute("BuyTimes");

				SYValue = resultNode.getAttribute("SYValue");
				String tZValue = resultNode.getAttribute("TZValue");
				if (SYValue == "-1")
				{
					// return;
				}

				ValueTime = resultNode.getAttribute("ValueTime");

				HSYValue = Double.parseDouble(SYValue) * theBeilv;
				double HTZValue = Double.parseDouble(SYValue) * theBeilv;
				// TotleUsed=TotleUsed*theBeilv;
				// System.out.println(Meter_ID+" "+QSYValue+" "+" "+HSYValue+" "+Times+" "+TotleUsed);
				SerSaveSendState(Meter_ID, TheSaleInfoNum, "2", QSYValue + "", HSYValue + "", HTZValue + "", Times, "-1");
				htmlShow = htmlShow + HSYValue + "度";
				batchOprModel.setHtmlShow(htmlShow);
				// System.out.println(htmlShow);
			}
			/*
			 * //购买次数 String BuyTimes=resultNode.getAttribute("BuyTimes");
			 * batchOprModel
			 * .setBuyTimes(Integer.toString(Integer.parseInt(BuyTimes)+1));
			 * //剩余电量 SYValue=resultNode.getAttribute("SYValue");
			 * batchOprModel.setSYValue(Integer.parseInt(SYValue));
			 */

		}

		return batchOprModel;
	}

	/**
	 * 插入并返回售电单号
	 * 
	 * @param SendType
	 *            1为全部 2为选中
	 * @param areaId
	 *            区域Id
	 * @param arcId
	 *            建筑Id
	 * @param floor
	 *            楼层
	 * @param SetList
	 *            选中的电表Id
	 * @param Value
	 *            下发的电量
	 * @param Kind
	 *            2为统一月补 3则为临时调剂
	 * @param UserID
	 *            用户Id
	 * @param price
	 * @return
	 * @throws SQLException
	 */
	public String SerAddYubu(String SendType, String SelectTreeFlag, String SelectTreeID, String SelectFloorID, String[] amMeterList, String Value,
			String Kind, String UserID, String price, String AmMeterStatus) throws SQLException
	{
		String write = null;
		String sql = "";
		String Meter_ID = "";
		String AmMeter_Name = "";
		String AmMeter_485Address = "";
		String Gross = "";
		String Price_Value = "";
		String ammeterId = "";

		if ("1".equals(SendType))// 所有表计月补
		{
			String strSelect = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )";
			//String strSelect = "";
			if ("1".equals(SelectTreeFlag))
			{
				strSelect = " where (Area_ID=" + SelectTreeID
						+ " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) ";
			}
			if ("2".equals(SelectTreeFlag))
			{

				strSelect = " where (Architecture_ID=" + SelectTreeID + ")";
			}
			if ("3".equals(SelectTreeFlag))
			{
				strSelect = " where (Architecture_ID=" + SelectTreeID + ") and (Floor=" + SelectFloorID + ")";
			}

			if ("4".equals(SelectTreeFlag))
			{
				strSelect = " where ammeter_id=" + SelectTreeID;
			}

			if (!"0".equals(price) && !"-1".equals(AmMeterStatus))
			{
				strSelect += " and Price_ID=" + price + " and AmMeter_Stat=" + AmMeterStatus;
			}
			else if(!"0".equals(price) && "-1".equals(AmMeterStatus)){
				strSelect += " and Price_ID=" + price;
			}
			else if("0".equals(price) && !"-1".equals(AmMeterStatus)){
				strSelect += " and AmMeter_Stat=" + AmMeterStatus;
			}
			// strSelect +=
			// " and Ammeter_ID not in (select Ammeter_ID from Ammeter where)"
			// 预付费 texingvalue 费控类型 1预付费 0后付费
			sql = " select Ammeter_ID,AmMeter_Name,AmMeter_485Address,nvl(APManCount,0)APManCount,nvl(APYBValue,0)APYBValue,(select Price_Value from Price  b where a.Price_ID=b.Price_ID)Price_Value,nvl((select YBValue from Price  b where a.Price_ID=b.Price_ID),0) YBValue,(select TexingValue from TEXINGVALUE where  METERTEXING_id=32 and METESTYLE_ID=(SELECT METESTYLE_ID from AMMETER WHERE AMMETER_ID=a.AMMETER_ID)) TEXINGVALUE from AmMeter  a "
					+ strSelect + " and APState='开户'";
			sql = "select * from (" + sql + ") where nvl(TEXINGVALUE,'1')<>'0' ";

			if ("2".equals(Kind))
			{
				String[] vlist = Value.split("|");
				if ("1".equals(vlist[0]))
				{
					// 更新数据库中的房间现住人数为临时人数
					updateManCount(vlist[1], strSelect);
					// CommonCs.ExecSqlText("update Ammeter set APManCount=" +
					// vlist[1] + " " + strSelect2);
				}
			}

		} else if ("2".equals(SendType))// 选中表计月补
		{
			for (int i = 0; i < amMeterList.length - 1; i++)
			{
				ammeterId += amMeterList[i] + ",";
			}
			ammeterId = ammeterId + amMeterList[amMeterList.length - 1];
			sql = " select Ammeter_ID,AmMeter_Name,AmMeter_485Address,nvl(APManCount,0) APManCount,nvl(APYBValue,0) APYBValue,(select Price_Value from Price  b where a.Price_ID=b.Price_ID) Price_Value,nvl((select YBValue from Price  b where a.Price_ID=b.Price_ID),0) YBValue from AmMeter  a  where AmMeter_ID in("
					+ ammeterId + ")" + " and APState='开户'";

			if ("2".equals(Kind))
			{
				String[] vlist = Value.split("\\|");
				if ("1".equals(vlist[0]))// 临时下发
				{
					String strSelect = " where AmMeter_ID in(" + ammeterId + ")";
					// 更新数据库中的房间现住人数为临时人数
					updateManCount(vlist[1], strSelect);
					// CommonCs.ExecSqlText("update Ammeter set APManCount=" +
					// vlist[1] + " " + strSelect2);
				}
			}

		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Meter_ID = rs.getString("Ammeter_ID");
				AmMeter_Name = rs.getString("AmMeter_Name");
				AmMeter_485Address = rs.getString("AmMeter_485Address");
				Gross = "0";
				Price_Value = rs.getString("Price_Value");

				if ("3".equals(Kind))
					Gross = Value;
				else if ("2".equals(Kind))
				{
					String[] vlist = Value.split("\\|");

					if ("0".equals(vlist[0]))
					{// 正常下发
						double dvalue = Double.parseDouble(rs.getString("YBValue")) * rs.getDouble("APManCount");
						if (dvalue == 0)
						{
							continue;
						} else
						{
							Gross = dvalue + "";// 将Gross转换为String类型
						}
					} else if ("1".equals(vlist[0]))
					{// 临时下发
						if (Double.parseDouble(rs.getString("YBValue")) == 0)// 没有月补量的不发
						{
							continue;
						} else
						{
							double dvalue = Double.parseDouble(vlist[2]) * Integer.parseInt(vlist[1]);
							if (dvalue == 0)
							{
								continue;
							}
							Gross = dvalue + "";
						}
					} else if ("2".equals(vlist[0]))
					{
						if (Double.parseDouble(rs.getString("YBValue")) == 0)// 没有月补量的不发
							continue;
						double value = Double.parseDouble(rs.getString("YBValue")) * rs.getInt("APManCount");
						double CValue = Double.parseDouble(vlist[2]) * (rs.getInt("APManCount") - Integer.parseInt(vlist[1]));
						double LSValue = Double.parseDouble(vlist[2]) * Integer.parseInt(vlist[1]);
						double dvalue = value + CValue;// dvalue<0 不够补
						if (dvalue == 0)
						{
							continue;
						} else if (dvalue < 0)
						{
							Gross = dvalue + "";
							// 插入月补异常表
							insertYBYichang(Meter_ID, AmMeter_Name, vlist[1], vlist[2], rs.getString("APManCount"), LSValue, value, dvalue, UserID);

							continue;
						} else
						{
							Gross = dvalue + "";
						}

					}

				}
				String Money = "";
				//***************此处设置下发时减去总示数************************
				/*if("2".equals(Kind)){//月补
					String ZValueZY = getZValueZY(Meter_ID);
					double theGross = Double.parseDouble(Gross)-Double.parseDouble(ZValueZY);
					Gross = theGross + "";
					if(theGross<=0){//不够的不补
						continue;
					}
					
				}*/
				
				double DMoney = Double.parseDouble(Gross) * Double.parseDouble(Price_Value);
				DecimalFormat df = new DecimalFormat("#.00");
				Money = df.format(DMoney) + "";
				// System.out.println(Meter_ID + " " + AmMeter_Name + " " +
				// AmMeter_485Address + " " + Gross + " " + Price_Value + " " +
				// Money);
				if (DMoney == 0)
				{
					continue;
				}
				String SaleInfoNum = "";

				String maxNum = getSaleInfoNum();

				SaleInfoNum = GetNextChareNum(maxNum);

				String Users_Name = "";
				if ("0".equals(UserID))
				{
					Users_Name = "一卡通售电";
				} else
				{
					Users_Name = getUserNameByID(UserID);

				}

				boolean isS = false;
				// 写入数据库
				// 添加售电单，SaleInfoNum必须是唯一的如果主键重复会异常，则重新生成单号添加直到添加成功
				while (!isS)
				{
					try
					{
						addSaleInfoNum(SaleInfoNum, Meter_ID, AmMeter_Name, AmMeter_485Address, Kind, UserID, Price_Value, Gross, Money, Users_Name);
						// CommonCs.ExecSqlText("insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,Users_ID,BuyTime,Status,Price,TheGross,TheMoney)values('"
						// + SaleInfoNum + "'," + Meter_ID + ",'" + AmMeter_Name
						// +
						// "','" + AmMeter_485Address + "'," + Kind + "," +
						// UserID +
						// ",getdate(),0," + Price_Value + "," + Gross + "," +
						// Money
						// + ")");
						isS = true;
					} catch (Exception e)
					{
						maxNum = getSaleInfoNum();
						SaleInfoNum = GetNextChareNum(maxNum);
					}
				}

				if (write == null){
					write = SaleInfoNum;
				}
				else
				{
					write = write + "/" + SaleInfoNum;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		if (write != null)
		{
			if (Kind == "2")
			{

			}

		}
		return write;
	}

	private String getZValueZY(String meter_ID)
	{
		String zValue = "0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ZValueZY from (select ZValueZY from ZAMDatas"+ meter_ID + " order by ValueTime desc) where rownum=1";
		
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				zValue = rs.getString("ZValueZY");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		if("".equals(zValue) || "null".equals(zValue) || zValue==null){
			zValue="0";
		}
		
		return zValue;
	}

	String getUserNameByID(String userID) throws SQLException
	{
		// CommonCs.GetStringBySqlText("select Users_Name from Users where Users_ID="
		// + UserID);
		String userName = "";
		String sql = "select Users_Name from Users where Users_ID=" + userID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				userName = rs.getString("Users_Name");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return userName;
	}

	private void insertYBYichang(String meter_ID, String amMeter_Name, String lsManCount, String lsManValue, String APManCount, double lSValue,
			double value, double dvalue, String userID) throws SQLException
	{
		// CommonCs.ExecSqlText("insert into APYBYichang(Ammeter_ID,Ammeter_Name,LSMancount,LSValue,TheTime,State,ManCount,LSZValue,ZValue,CValue,UserID) "
		// +
		// "values(" + Meter_ID + ",'" + AmMeter_Name + "'," + vlist[1] + "," +
		// vlist[2] + ",getdate(),0," + drMeter["APManCount"].ToString() + "," +
		// LSValue.ToString() + "," + value.ToString() + "," + dvalue + "," +
		// UserID + ")");
		String sql = "insert into APYBYichang(Ammeter_ID,Ammeter_Name,LSMancount,LSValue,TheTime,State,ManCount,LSZValue,ZValue,CValue,UserID) "
				+ "values(" + meter_ID + ",'" + amMeter_Name + "'," + lsManCount + "," + lsManValue + ",sysdate,0," + APManCount + "," + lSValue
				+ "," + value + "," + dvalue + "," + userID + ")";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		// PreparedStatement pst = ConnDB.getConnection().prepareStatement(sql);
		// pst.executeUpdate();
		//
		// if (pst != null)
		// pst.close();

	}

	private void updateManCount(String value, String strSelect) throws SQLException
	{
		// CommonCs.ExecSqlText("update Ammeter set APManCount=" + vlist[1] +
		// " " + strSelect2);
		String sql = "update Ammeter set APManCount=" + value + " " + strSelect;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		// PreparedStatement pst = ConnDB.getConnection().prepareStatement(sql);
		// pst.executeUpdate();
		//
		// if (pst != null)
		// pst.close();

	}

	// 添加售电单
	public boolean addSaleInfoNum(String SaleInfoNum, String Meter_ID, String AmMeter_Name, String AmMeter_485Address, String Kind, String UserID,
			String Price_Value, String Gross, String Money, String Users_Name) throws SQLException
	{
		String sql = "insert into APSaleInfo(SaleInfoNum,Ammeter_ID,AmMeter_Name,AmMeter_485Address,Kind,Users_ID,BuyTime,Status,Price,TheGross,TheMoney,Users_Name)values('"
				+ SaleInfoNum
				+ "','"
				+ Meter_ID
				+ "','"
				+ AmMeter_Name
				+ "','"
				+ AmMeter_485Address
				+ "','"
				+ Kind
				+ "','"
				+ UserID
				+ "',sysdate,0,"
				+ Price_Value + "," + Gross + "," + Money + ",'" + Users_Name + "')";
		// System.out.println("addSaleInfoNum:"+sql);
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// boolean b = (ps.executeUpdate() == 1);
		// if (ps != null)
		// {
		// ps.close();
		// }
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	// 得到当前最大售电单编号
	public String getSaleInfoNum() throws SQLException
	{
		String saleInfoNum = null;
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select max(SaleInfoNum) SaleInfoNum from APSaleInfo where BuyTime>=to_date('" + df.format(date) + "','yyyy-mm-dd')";
		// System.out.println("sql:"+sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				saleInfoNum = rs.getString("SaleInfoNum");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// ResultSet rs = ps.executeQuery();
		// if (rs.next())
		// {
		// saleInfoNum = rs.getString("SaleInfoNum");
		// }
		// if (rs != null)
		// rs.close();
		//
		// if (ps != null)
		// ps.close();
		return saleInfoNum;
	}

	// 生成售电单编号
	public String GetNextChareNum(String maxNum)
	{
		String nextNum = "";
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd0001");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		// System.out.println("mmm:"+maxNum);
		if (maxNum == null)
		{
			// System.out.println("fd");
			nextNum = df.format(date);
		} else
		{
			int nextnum = Integer.parseInt(maxNum.substring(8)) + 1;
			nextNum = df1.format(date) + String.format("%04d", nextnum);
		}
		return nextNum;

	}

	/**
	 * 发送命令
	 * 
	 * @param TaskNum
	 *            任务编号
	 * @param commandlist
	 * @param UserID
	 *            用户id
	 * @param strPart
	 *            购电单号
	 * @return
	 * @throws SQLException
	 */
	public String getAmMeterMes(String strPart) throws SQLException
	{
		String write = null;
		BatchOprModel batchOprModel = new BatchOprModel();
		String sql = "select (select AmMeter_Stat from AmMeter a where a.AmMeter_ID=b.AmMeter_ID) AmMeter_Stat, AmMeter_ID,Ammeter_Name,Status,TheGross,TheMoney from APSaleInfo b where SaleInfoNum='"
				+ strPart + "'";
		// System.out.println("sql:"+sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{

				String AmMeter_Stat = "";
				String AmMeter_ID = rs.getString("AmMeter_ID");
				String amMeter_Name = rs.getString("Ammeter_Name");
				AmMeter_Stat = rs.getString("AmMeter_Stat");
				// System.out.println("AmMeter_ID:"+AmMeter_ID+"  AmMeter_Stat:"+AmMeter_Stat);
				if ("0".equals(AmMeter_Stat))
				{
					write = null;
					return write;
				}

				String Status = rs.getString("Status");
				// System.out.println("Status:"+Status);

				if ("0".equals(Status))
				{
					// System.out.println("macarthur");
					String Gross = rs.getString("TheGross");
					String TheMoney = rs.getString("TheMoney");
					// System.out.println("Gross:"+Gross);
					write = AmMeter_ID + "/" + Gross + "/" + strPart + "/" + amMeter_Name + "/" + TheMoney;
					// System.out.println("write:"+write);

					// double TGross =Double.parseDouble(Gross)/ BeiLv;
					/*
					 * write = "当前操作房间：" + Ammeter_Name + "，下发月补量：" + Gross +
					 * "|" + Meter_ID + "|" + BeiLv.ToString(); PartList =
					 * "E50F," + Meter_Port + "," + Meter_Sulv +
					 * ",e,8,1,1,60,10,," + AmMeter_485Address + "," +
					 * AmMeter_Password + ","; write +=
					 * "‖读购电前剩余电量|<SFROOT gy=\"GW\"><GW opercode=\"" + UserID +
					 * "\" tasknum=\"" + TaskNum + "\" terminaladdress=\"" +
					 * terminaladdress + "\" terminalip=\"" + terminalip +
					 * "\" way=\"" + way +
					 * "\"><command code=\"1000000100\" pw=\"" + pw +
					 * "\" metertype=\"1\" para=\"" + PartList +
					 * "\"/></GW></SFROOT>";
					 * //下发购电量para为E501,端口,速率,e,8,1,1,60,10,购电次数,电表地址,电表密码,购电量"
					 * decimal TGross = decimal.Parse(Gross) / BeiLv; PartList =
					 * "E501," + Meter_Port + "," + Meter_Sulv +
					 * ",e,8,1,1,60,10,{Times}," + AmMeter_485Address + "," +
					 * AmMeter_Password + "," + TGross.ToString("0.00"); write
					 * += "‖下发购电量|<SFROOT gy=\"GW\"><GW opercode=\"" + UserID +
					 * "\" tasknum=\"" + TaskNum + "\" terminaladdress=\"" +
					 * terminaladdress + "\" terminalip=\"" + terminalip +
					 * "\" way=\"" + way +
					 * "\"><command code=\"1000000100\" pw=\"" + pw +
					 * "\" metertype=\"1\" para=\"" + PartList +
					 * "\"/></GW></SFROOT>";
					 * //读剩余电量para为E502,端口,速率,e,8,1,1,60,10,,电表地址,电表密码,"
					 * PartList = "E50F," + Meter_Port + "," + Meter_Sulv +
					 * ",e,8,1,1,60,10,," + AmMeter_485Address + "," +
					 * AmMeter_Password + ","; write +=
					 * "‖读购电后剩余电量|<SFROOT gy=\"GW\"><GW opercode=\"" + UserID +
					 * "\" tasknum=\"" + TaskNum + "\" terminaladdress=\"" +
					 * terminaladdress + "\" terminalip=\"" + terminalip +
					 * "\" way=\"" + way +
					 * "\"><command code=\"1000000100\" pw=\"" + pw +
					 * "\" metertype=\"1\" para=\"" + PartList +
					 * "\"/></GW></SFROOT>";
					 */
				} else
				{
					write = null;
					return write;
				}
			} else
			{
				write = null;
				return write;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return write;
	}

	// 根据电表Id得到下发命令的相关参数
	public BatchOprModel getCommandPara(String Meter_ID) throws SQLException
	{
		BatchOprModel batchOprModel = new BatchOprModel();
		double BeiLv = 0;
		String sql = "select a.Gather_ID,Gather_Address,Gather_Password,Gather_State,SendWay,Protocol,IP,AmMeter_Point,AmMeter_Name,isnull( BeiLv,1)BeiLv,( select  TexingValue   from  TexingValue as c  where  c.MeteStyle_ID=b.MeteStyle_ID and   c.MeterTexing_ID=19 )Meter_Port,( select  TexingValue   from  TexingValue as c  where  c.MeteStyle_ID=b.MeteStyle_ID and   c.MeterTexing_ID=5 )Meter_Sulv,AmMeter_485Address,AmMeter_Password,Price_ID,AmMeter_Stat from AmMeter as b left join Gather as a on a.Gather_ID=b.Gather_ID  where   AmMeter_ID="
				+ Meter_ID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				batchOprModel.setAmMeter_State(rs.getString("AmMeter_Stat"));
				BeiLv = rs.getDouble("BeiLv");
				if (BeiLv == 0)
				{
					BeiLv = 1;
				}
				batchOprModel.setBeiLv(BeiLv);

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return batchOprModel;
	}

	/**
	 * 保存统一月补相关信息
	 * 
	 * @param MeterID
	 *            电表Id
	 * @param SaleInfoNum
	 *            售电单号
	 * @param msg
	 *            判断信息标志 1代表下发购电量 2代表读购电后剩余电量
	 * @param QSYValue
	 *            购电前剩余金额
	 * @param HSYValue
	 *            购电后剩余金额
	 * @param Times
	 *            购电次数
	 * @param TotleUsed
	 *            用户总共
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String SerSaveSendState(String MeterID, String SaleInfoNum, String msg, String QSYValue, String HSYValue, String HTZValue, String Times,
			String TotleUsed) throws SQLException, NumberFormatException, ParseException
	{
		String write = "";
		String ValueTime = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		ValueTime = df.format(date);
		CMMeterDao cmMeterDao = new CMMeterDao();
		if ("1".equals(msg))
		{
			updateAPSaleInfo("1", QSYValue, Times, SaleInfoNum, "");
			updateAmMeter(MeterID);
			write = "1";
		} else if ("2".equals(msg))
		{
			// 比较售电后剩余电量和购电前剩余电量，如果售电时返回的失败，但是购电后剩余金额增加了表示购电成功,否则失败
			if (Double.parseDouble(HSYValue) > Double.parseDouble(QSYValue))
			{
				updateAPSaleInfo("2", QSYValue, Times, SaleInfoNum, HSYValue);
				updateAmMeter(MeterID);

				cmMeterDao.SerSaveData(Integer.parseInt(MeterID), Float.parseFloat(TotleUsed), Float.parseFloat(HSYValue),
						Float.parseFloat(HTZValue), ValueTime);
				write = "1";
			} else
			{
				updateAPSaleInfo("3", "", "", SaleInfoNum, HSYValue);

				write = "0";
			}
		}
		return write + "/" + msg;
	}

	// 更新预付费表APSaleInfo
	public boolean updateAPSaleInfo(String flag, String QSYValue, String Times, String SaleInfoNum, String HSYValue) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String sql = "";
		// 下发购电量
		if ("1".equals(flag))
		{
			sql = "update APSaleInfo set QSYValue=" + QSYValue + ",Times=" + Times + ",Status=1,SendTime=sysdate where SaleInfoNum='" + SaleInfoNum
					+ "'";

		}
		// 读购电后剩余电量
		else if ("2".equals(flag))
		{
			sql = "update APSaleInfo set QSYValue=" + QSYValue + ",HSYValue=" + HSYValue + ",Times=" + Times
					+ ",Status=1,SendTime=sysdate where SaleInfoNum='" + SaleInfoNum + "'";

		}
		// 读购电后剩余电量
		else if ("3".equals(flag))
		{
			sql = "update APSaleInfo set HSYValue=" + HSYValue + ",Status=0 where SaleInfoNum='" + SaleInfoNum + "'";
		}
		// System.out.println("sql:"+sql);
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// boolean b = (ps.executeUpdate() == 1);
		// if (ps != null)
		// {
		// ps.close();
		// }
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	// 更新电表信息
	public boolean updateAmMeter(String amMeterId) throws SQLException
	{
		String sql = "update AmMeter set IsSupply=1 where Ammeter_ID=" + amMeterId;
		// System.out.println("sql:"+sql);
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// boolean b = (ps.executeUpdate() == 1);
		// if (ps != null)
		// {
		// ps.close();
		// }
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public JSONArray getPriceInfo() throws SQLException
	{
		String sql1 = "select distinct 0 as Price_ID,'全部' as Price_Name from Price union all select Price_ID,Price_Name from Price where Price_Style='E'";
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("Price_ID", rs.getString("Price_ID"));
				jo.put("Price_Name", rs.getString("Price_Name"));
				json.add(jo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// Statement sm1 = ConnDB.getConnection().createStatement();
		// ResultSet rs = sm1.executeQuery(sql1);
		//
		// while (rs.next())
		// {
		// jo = new JSONObject();
		// jo.put("Price_ID", rs.getString("Price_ID"));
		// jo.put("Price_Name", rs.getString("Price_Name"));
		// json.add(jo);
		// }

		return json;
	}
}
