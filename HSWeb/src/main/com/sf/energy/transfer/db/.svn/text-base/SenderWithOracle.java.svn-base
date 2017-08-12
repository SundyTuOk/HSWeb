package com.sf.energy.transfer.db;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.sf.energy.transfer.form.DataQueryTableModel;
import com.sf.energy.util.TransferConnDB;


/**
 * <与发送相关的数据库操作> <包括主动向服务器上传小时数据、取出数据网关下表计、查电表、网关、数据、命令等信息、查缺失数据、更新抄读时间、插限值信息。>
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.util.TransferConnDB、com.sf.energy.util.CreateLog、com.sf.energy.transfer.db.SendDatabase
 * @since 中转站
 */
public class SenderWithOracle implements SenderWithDatabase
{
	Logger logger=Logger.getLogger(SenderWithOracle.class);
	public  Boolean QueryDGAutoRead(String dgaddress) throws SQLException
    {
        String strsql = "select DG_AutoRead from DataGate where DG_Address='" + dgaddress + "'";
        PreparedStatement ps=null;
		ps=TransferConnDB.getConnection().prepareStatement(strsql);
		ResultSet rs=null;
		rs=ps.executeQuery();
        if (rs.next())
        {
            if (rs.getInt("DG_AutoRead") == 1)
            {
                return true;
            }
        }
        return false;
    }
	/**
	 * 组建预付费点表的XML
	 * @return
	 * @throws SQLException 
	 */
	public String AutoUpPrepare() throws SQLException
	{
		// XML标签数组
		String[] node =
		{ "<DG_Address>", "<Meter_Address>","<Read_Time>", "<SYMONEY>", "<TZMONEY>" , "<SYVALUE>" , "<TZVALUE>"  };
		// XML结束标签数组
		String[] rNode =
		{ "</DG_Address>", "</Meter_Address>", "</Read_Time>",  "</SYMONEY>", "</TZMONEY>" , "</SYVALUE>" , "</TZVALUE>"  };
		
		String sql="SELECT ID,DG_Address,Meter_Address,Read_Time,SYMoney,TZMoney,SYValue,TZValue,Up_Flag FROM (select * from AmMeterAPDatas where Up_Flag = 0) WHERE rownum<=100 order by ID asc";
		PreparedStatement ps=null;
		ps=TransferConnDB.getConnection().prepareStatement(sql);
		ResultSet rs=null;
		rs=ps.executeQuery();
		String xmlStr = null;
		if(rs.next())
		{
			xmlStr = "<NewDataSet>";
			while (rs.next())
			{
				String DG_Address = rs.getString("DG_Address");
				String Meter_Address = rs.getString("Meter_Address");
				String Read_Time = rs.getString("Read_Time");
				String SYMoney = rs.getString("SYMoney");
				String TZMoney = rs.getString("TZMoney");
				String SYValue = rs.getString("SYValue");
				String TZValue = rs.getString("TZValue");
				
				xmlStr+=node[0] +DG_Address+rNode[0];
				xmlStr+=node[1] +Meter_Address+rNode[0];
				xmlStr+=node[2] +Read_Time+rNode[0];
				xmlStr+=node[3] +SYMoney+rNode[0];
				xmlStr+=node[4] +TZMoney+rNode[0];
				xmlStr+=node[5] +SYValue+rNode[0];
				xmlStr+=node[6] +TZValue+rNode[0];
			}
			xmlStr += "</NewDataSet>";
		}
		
		return xmlStr;
	}

	@Override
	public String autoUpToServer(String meterType) throws SQLException
	{
		String tableName = "AmMeterData";
		if ("02".equals(meterType) || "2".equals(meterType)) // 水表
		{
			tableName = "WaMeterData";
		}

		String strSql = "SELECT ID,DG_Address,'"
				+ meterType
				+ "' as Meter_Type,Meter_Address,Command_Code,Read_Time,ZY0,Up_Flag FROM "
				+ "(select * from " + tableName + " WHERE Up_Flag = 0)"
				+ " where rownum<=100 order by rownum asc";

		String xmlStr = null;

		// XML标签数组
		String[] node =
		{ "<DG_Address>", "<meterType>", "<Meter_Address>", "<Command_Code>",
				"<Read_Time>", "<ZY0>" };
		// XML结束标签数组
		String[] rNode =
		{ "</DG_Address>", "</meterType>", "</Meter_Address>",
				"</Command_Code>", "</Read_Time>", "</ZY0>" };
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);

		if (rs.next())
		{
			xmlStr = "<NewDataSet>";
			
			// 光标移动到ResultSet对象开头
			rs.beforeFirst();
			while (rs.next())
			{
				xmlStr += "<Table>";
				for (int i = 0; i < node.length; i++)
				{
					xmlStr += node[i] + rs.getString(i + 2) + rNode[i];
				}
				strSql = "update " + tableName + " set Up_Flag=1 where ID='"
						+ rs.getInt(1) + "'";
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sm1.executeUpdate(strSql);

				xmlStr += "</Table>";
				
				if(sm1 != null)
				{
					sm1.close();
				}
			}
			xmlStr += "</NewDataSet>";
		}

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return xmlStr;
	}

	@Override
	public String autoUpThreePhase() throws SQLException
	{
		String xmlStr = null;
		String strSql = "SELECT ID,DG_Address,Meter_Address,Read_Time,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW";
		strSql += ",PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB,CurrentC,Current0";
		strSql += ",PowerSZZ,PowerSZA,PowerSZB,PowerSZC,Up_Flag FROM "
				+ "(select * from AmMeterDataThreePhase WHERE Up_Flag = 0) where rownum<=50 order by rownum asc";

		// XML标签数组
		String[] node =
		{ "<DG_Address>", "<Meter_Address>", "<Read_Time>", "<PowerZY>",
				"<PowerAY>", "<PowerBY>", "<PowerCY>", "<PowerZW>",
				"<PowerAW>", "<PowerBW>", "<PowerCW>", "<PowerFactorZ>",
				"<PowerFactorA>", "<PowerFactorB>", "<PowerFactorC>",
				"<VoltageA>", "<VoltageB>", "<VoltageC>", "<CurrentA>",
				"<CurrentB>", "<CurrentC>", "<Current0>", "<PowerSZZ>",
				"<PowerSZA>", "<PowerSZB>", "<PowerSZC>" };
		// XML结束标签数组
		String[] rNode =
		{ "</DG_Address>", "</Meter_Address>", "</Read_Time>", "</PowerZY>",
				"</PowerAY>", "</PowerBY>", "</PowerCY>", "</PowerZW>",
				"</PowerAW>", "</PowerBW>", "</PowerCW>", "</PowerFactorZ>",
				"</PowerFactorA>", "</PowerFactorB>", "</PowerFactorC>",
				"</VoltageA>", "</VoltageB>", "</VoltageC>", "</CurrentA>",
				"</CurrentB>", "</CurrentC>", "</Current0>", "</PowerSZZ>",
				"</PowerSZA>", "</PowerSZB>", "</PowerSZC>" };
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		
		
		if (rs.next())
		{
			xmlStr = "<NewDataSet>";
			
			// 光标移动到ResultSet对象开头
			rs.beforeFirst();
			while (rs.next())
			{
				xmlStr += "<Table>";
				for (int i = 0; i < node.length; i++)
				{
					xmlStr += node[i] + rs.getString(i + 2) + rNode[i];
				}
				strSql = "update AmMeterDataThreePhase set Up_Flag=1 where ID='"
						+ rs.getInt(1) + "'";
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sm1.executeUpdate(strSql);

				xmlStr += "</Table>";
				
				if(sm1 != null)
					sm1.close();
			}
			xmlStr += "</NewDataSet>";
		}
		

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return xmlStr;
	}

	@Override
	public String allMeter(String terminalAddress, String meterType)
			throws SQLException
	{
		String meters = "";
		String strSql = "";

		String tableName = "AmMeter"; // 默认电表
		if ("02".equals(meterType) || "2".equals(meterType)) // 水表
		{
			tableName = "WaMeter";
		}
		strSql = "select Meter_Address,Meter_ID from " + tableName
				+ " where DG_Address='" + terminalAddress + "'";
		
		
		
		
		if (!strSql.equals("") && strSql != null)
		{

			Statement sm = null;
			sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			rs = sm.executeQuery(strSql);
			while (rs.next())
			{
				String address = rs.getString("Meter_Address");
				String meterID = Integer.toString(rs.getInt("Meter_ID"));
				meters += address + "|" + meterID + ",";
			}

			if(rs != null)
			{
				rs.close();
			}
			if(sm != null)
			{
				sm.close();
			}
		}


		return meters;
	}

	@Override
	public String metersToRead(String terminalAddress, String meterType)
			throws SQLException
	{
		String meters = "";
		if (Integer.parseInt(meterType) < 3)
		{

			String tableName = "AmMeter"; // 默认电表
			if ("02".equals(meterType) || "2".equals(meterType)) // 水表
			{
				tableName = "WaMeter";
			}
			logger.info("从"+tableName+"获取轮抄所需要发送的命令的参数信息！~");
			String strSql = "select substr(Meter_Style,2,1)Meter_Style,Meter_Address,Meter_ID from " + tableName
					+ " where DG_Address='" + terminalAddress
					+ "'and Has_Data=0 and substr(Meter_Style,2,1) in('1','2') order by Meter_ID ";

			Statement sm = null;
			sm = TransferConnDB.getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			rs = sm.executeQuery(strSql);
			while (rs.next())
			{
				String address = rs.getString("Meter_Address");
				String meterID = Integer.toString(rs.getInt("Meter_ID"));
				meters += address + "|" + meterID + "|0C|0110,";
				//TO-DO 判断是否是重点用户如果是 meters += address + "|" + meterID + "|0C|0103,";
				if(Integer.parseInt(meterType)==1)
				{
					if(Integer.parseInt(rs.getString("Meter_Style"))==2)
					{
						 meters += address + "|" + meterID + "|0C|0103,";
					}
				}	
			}

			if (rs != null)
			{
				rs.close();
			}
			if (sm != null)
			{
				sm.close();
			}
		}
		else
		{
			String strsql = "select Meter_Address,Meter_ID,Port,Protocle,3 as Meter_Style from AmMeter where DG_Address='" + terminalAddress + "'and Has_Data=0 and substr(Meter_Style,2,1)='1' order by Meter_ID ";
			Statement sm = null;
			sm = TransferConnDB.getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			rs = sm.executeQuery(strsql);
			
			while (rs.next())
			{
				String address = rs.getString("Meter_Address");
				String meterID = Integer.toString(rs.getInt("Meter_ID"));
				String Protocle=rs.getString("Protocle");
				String Port	   =rs.getString("Port");

				
				
				String para10 = "100100";
				//TO-DO 判断是否是重点用户如果是 meters += address + "|" + meterID + "|0C|0103,";
				if(Integer.parseInt(Protocle)==1)
				{
					para10 += "|E50F,";
					meters += address + "|" + meterID + "|"+Port+"|"+para10;
					// mm[0]-是电表地址 mm[1]-是测量点号 mm[2]-端口  mm[3]-100100 mm[4]-E50F 
				}
				else
				{
					
				}
				
			}
			
			if (rs != null)
			{
				rs.close();
			}
			if (sm != null)
			{
				sm.close();
			}
			
		}
		return meters;
	}

	@Override
	public ArrayList<String> metersOnline(String terminalAddress,
			String meterType) throws SQLException
	{
		ArrayList<String> meters = new ArrayList<String>();
		String tableName = "AmMeter"; // 默认电表
		if ("02".equals(meterType) || "2".equals(meterType)) // 水表
		{
			tableName = "WaMeter";
		}

		String strSql = "select Meter_Address,LastTime from " + tableName
				+ " where DG_Address='" + terminalAddress + "'";

		PreparedStatement sm = null;
		sm = TransferConnDB.getConnection().prepareStatement(strSql);
		ResultSet rs = null;
		rs = sm.executeQuery();
		while (rs.next())
		{
			meters.add(rs.getString("Meter_Address") + ","
					+ rs.getString("LastTime"));
		}

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return meters;
	}

	@Override
	public void readFlgClear(String meterType) throws SQLException
	{
		String tableName = "AmMeter"; // 默认电表
		if ("02".equals(meterType) || "2".equals(meterType)) // 水表
		{
			tableName = "WaMeter";
		}
		String strSql = "update " + tableName + " set Has_Data=0";

		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		sm.executeUpdate(strSql);
		
		if(sm != null)
		{
			sm.close();
		}

	}
	@Override
	public JTable queryMeter(String meterType, String meterStyle,
			String terminalAddress, JLabel jLabel3) throws SQLException
	{
		String tableName = "AmMeter";
		String columnName = "Has_Data";
		String sqlWhere = "";
		switch (meterType)
		{
		case "电表":
			tableName = "AmMeter";
			if ("单相表".equals(meterStyle))
			{
				columnName = "Has_Data";
				sqlWhere = " where Meter_Style='01'";
			} else
			{
				columnName = "Has_DataThreePhase";
				sqlWhere = " where Meter_Style='02'";
			}
			break;
		case "水表":
			tableName = "WaMeter";
			columnName = "Has_Data";
			break;
		default:
			break;
		}

		String strSql = "select DG_Address as 数据网关地址,Meter_ID as 测量点号,Meter_Address as 表计地址,"
				+ columnName + " as 已抄标志,LastTime as 最后通讯时间 from " + tableName;
		if (!sqlWhere.equals("") && sqlWhere != null)
		{
			strSql += sqlWhere;
			sqlWhere = " and";
		} else
		{
			sqlWhere = " where";
		}
		if (!terminalAddress.equals("") && terminalAddress != null)
		{

			sqlWhere += " DG_Address='" + terminalAddress + "'";
			strSql += sqlWhere;
		}
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		//TODO 引用的地方关闭rs，sm
		
		rs.last();
		int count=rs.getRow();
		String countString="      共计  "+count+"  条";
		jLabel3.setText(countString);
		rs.beforeFirst();
		
		String[] title=getTitle(rs);
		JTable table=null;
		table=getJTable(title, rs);
		
		table.setPreferredScrollableViewportSize(new Dimension());
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
	
		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return table;
	}

	@Override
	public String[] queryDG() throws SQLException
	{
		String strSql = "select DG_Address from DataGate";
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		
		rs.last();
		int size = rs.getRow();
		rs.beforeFirst();
		
		String daAddress[] = new String[size];
		int i = 0;
		while(rs.next())
		{
			daAddress[i] = rs.getString("DG_ADDRESS");
			i++;
		}
		if(rs!=null)
			rs.close();
		if(sm!=null)
			sm.close();
		return daAddress;
	}

	@Override
	public DataQueryTableModel queryData(String meterType, String thisCommand,
			String terminalAddress, String meterAddress, String readDate, JLabel jLabel5) throws SQLException
	{
		String tableName = "AmMeterData";
		String columnSql = "";
		switch (meterType)
		{
		case "电表":
			if (!"0C0103".equals(thisCommand) && !"0c0103".equals(thisCommand))
			{
				tableName = "AmMeterData";
				columnSql = "ZY0 as 正向有功总电能";
			} else
			{
				thisCommand = "0C0103";
				tableName = "AmMeterDataThreePhase";
				columnSql = "PowerZY as 总有功功率,PowerAY as A相有功功率,PowerBY as B相有功功率,PowerCY as C相有功功率,"
						+ "PowerZW as 总无功功率,PowerAW as A相无功功率,PowerBW as B相无功功率,PowerCW as C相无功功率,"
						+ "PowerFactorZ as 总功率因数,PowerFactorA as A相功率因数,PowerFactorB as B相功率因数,PowerFactorC as C相功率因数,"
						+ "VoltageA as A相电压,VoltageB as B相电压,VoltageC as C相电压,"
						+ "CurrentA as A相电流,CurrentB as B相电流,CurrentC as C相电流,Current0 as 零序电流,"
						+ "PowerSZZ as 总视在功率,PowerSZA as A相视在功率,PowerSZB as B相视在功率,PowerSZC as C相视在功率,Alarm as 报警信息";
			}
			break;
		case "水表":
			tableName = "WaMeterData";
			columnSql = "ZY0 as 水表止码";
			break;
		default:
			break;
		}
		String strSql = "select DG_Address as 网关地址,Meter_Address as 表计地址,Read_Time as 抄读时间,"
				+ columnSql
				+ ",Up_Flag as 上传标志 from "
				+ tableName
				+ " where Command_Code='" + thisCommand + "'";
		String whereSql = "";
		if (!terminalAddress.equals("") && terminalAddress != null)
		{
			whereSql = " and DG_Address='" + terminalAddress + "'";
		}
		if (!meterAddress.equals("") && meterAddress != null)
		{
			whereSql += " and Meter_Address='" + meterAddress + "'";
		}
		if (!readDate.equals("") && readDate != null)
		{
			whereSql += " and substr(Read_Time,0,10)='" + readDate + "'";
		}
		if (!whereSql.equals("") && whereSql != null)
		{
			strSql += whereSql;
		}
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		
		rs.last();
		int count=rs.getRow();
		String countString="      共计  "+count+"  条";
		jLabel5.setText(countString);
		rs.beforeFirst();
		String title[] = getTitle(rs);
		JTable table = null;
		table = getJTable(title, rs);
		
		
		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
		HashMap<String, String> recode=null;
		rs.beforeFirst();
		while(rs.next())
		{	
			recode=new HashMap<String, String>();
			for(int i=0;i<title.length;i++)
			{
				recode.put(title[i],rs.getString(title[i]));
			}
			list.add(recode);
		}
		
		DataQueryTableModel queryTable=new DataQueryTableModel();
		queryTable.setTitle(title);
		queryTable.setTable(table);
		queryTable.setList(list);
		
		
		
		if(rs!=null)
			rs.close();
		if(sm!=null)
			sm.close();

		return queryTable;
	}

	@Override
	public ResultSet queryDataItem(String tableName, String thisCommand) throws SQLException
	{
		String strSql = "select Data_Name,1 as Clumn_Visible from " + tableName
				+ " where Command_Code='" + thisCommand + "'";
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		//TODO 引用的地方关闭rs，sm

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return rs;
	}

	@Override
	public ResultSet queryCommand(String tableName) throws SQLException
	{
		String strSql = "select Distinct(Command_Code) from " + tableName;
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		//TODO 引用的地方关闭rs，sm

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return rs;
	}

	@Override
	public String queryMissedData(String terminalAddress) throws SQLException,
			ParseException
	{
		String missData = "";
		Date tempLastDate;
		Date nowDate = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy/MM/dd");
		String strSql = "select DG_LastCompleteDate from DataGate where DG_Address='"
				+ terminalAddress + "'";
		//System.out.println("sql:"+strSql);
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);

		if (rs.next())
		{

			Date lastDate = dateFormat1.parse(rs
					.getString("DG_LastCompleteDate"));
			tempLastDate = addDate(lastDate, 1); // lastDate加一天
			while (tempLastDate.before(nowDate)) // 有缺失数据
			{
				tempLastDate = addDate(lastDate, 7);
				if (tempLastDate.before(nowDate))
				{
					lastDate = addDate(nowDate, -7);
				}

				Date readDate = addDate(lastDate, 1);
				strSql = "select MAX(Meter_Address) AS MaxMeterAddress,Read_Time from AmMeterData where to_char(to_date(READ_TIME,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD')='"
						+ dateFormat1.format(readDate)
						+ "' and DG_Address='"
						+ terminalAddress
						+ "' GROUP BY Read_Time ORDER BY Read_Time";
				//System.out.println("sql:"+strSql);
				Statement sm1 = null;
				sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1 = null;
				rs1 = sm.executeQuery(strSql);
				Date startTime = dateFormat2.parse(dateFormat4.format(readDate)
						+ " 00:00:00");

				if (rs1.next() == false)// 是否有数据，没有的话直接抄读这一天
				{
					missData = dateFormat3.format(readDate);
					break;
				}
				rs1.beforeFirst();// 光标移动到ResultSet对象开头
				while (rs1.next())// 查询lastdata+1这一天是否有大于60分钟没有数据的，如果有这抄读这一天
				{
					Date temp = dateFormat2.parse(rs.getString("Read_Time"));
					if (addHour(startTime, 1).before(temp))
					{
						missData = dateFormat3.format(readDate);
						break;
					}
					startTime = temp;
				}
				if(rs1 != null)
				{
					rs1.close();
				}
				if(sm1 != null)
				{
					sm1.close();
				}
			}

		}

		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return missData;
	}

	/**
	 * 日期增加n天。
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            增加的天数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addDate(Date date, int days) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.DATE, days);
		return cld.getTime();
	}

	/**
	 * 时间增加N小时
	 * 
	 * @param date
	 *            时间
	 * @param hours
	 *            增加的小时数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addHour(Date date, int hours) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.HOUR_OF_DAY, hours);
		return cld.getTime();
	}

	@Override
	public void updateLastDate(String terminalAddress, String readDate) throws SQLException
	{
		String newDate = "20" + readDate.substring(0, 2) + "-"
				+ readDate.substring(2, 4) + "-" + readDate.substring(4, 6);

		String strSql = "UPDATE DATAGATE SET DG_LastCompleteDate = '" + newDate
				+ "' WHERE (DG_Address = '" + terminalAddress + "')";
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		int updateInt = sm.executeUpdate(strSql);
		
		if(sm != null)
		{
			sm.close();
		}

	}

	@Override
	public boolean insertAlarm(String terminalAddress, String alarmValue) throws SQLException
	{
		boolean isSucess = false;
		String[] strArray = alarmValue.split(",");
		if (strArray.length >= 19)
		{

				String strSql = null;
				// 该表是普通表
				if (strArray[0].length() <= 12)
				{
					strSql = "update AmMeter set PowerZY_UL=" + strArray[1]
							+ ",PowerAY_UL=" + strArray[2] + ",PowerBY_UL="
							+ strArray[3] + ",PowerCY_UL=" + strArray[4]
							+ ",PowerZW_UL=" + strArray[5] + ",";
					strSql += "PowerAW_UL=" + strArray[6] + ",PowerBW_UL="
							+ strArray[7] + ",PowerCW_UL=" + strArray[8]
							+ ",VoltageA_UL=" + strArray[9] + ",VoltageA_LL="
							+ strArray[10] + ",VoltageB_UL=" + strArray[11]
							+ ",";
					strSql += "VoltageB_LL=" + strArray[12] + ",VoltageC_UL="
							+ strArray[13] + ",VoltageC_LL=" + strArray[14]
							+ ",CurrentA_UL=" + strArray[15] + ",CurrentB_UL="
							+ strArray[16] + ",CurrentC_UL=" + strArray[17]
							+ ",";
					strSql += "Current0_UL=" + strArray[18]
							+ " where DG_Address='" + terminalAddress
							+ "' and Meter_Address='" + strArray[0] + "'";

				}
				Statement sm = null;
				sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int updateInt = sm.executeUpdate(strSql);
				if (updateInt == 1)
				{
					isSucess = true;
				}
				
				if(sm != null)
				{
					sm.close();
				}
		}

		return isSucess;
	}
	
	/**
	 * 查询结果集的所有字段名称
	 * @param rs 查询到的结果
	 * @return 字段名称
	 * @throws SQLException
	 */
	String[] getTitle(ResultSet rs) throws SQLException
	{
	ResultSetMetaData rsmd=rs.getMetaData();
	int count=rsmd.getColumnCount();
	String[] title=new String[count];
	for(int i=1;i<=count;i++)
	{
	title[i-1]=rsmd.getColumnName(i);
	}
	return title;
	}

	
	/**
	 * 传入结果集，和表的字段名称生成一个JTable 的组件
	 * 
	 * @param title
	 *            字段名称数组
	 * @param rs
	 *            表查出来的结果集
	 * @return JTable 组件
	 * @throws SQLException
	 */
	JTable getJTable(String title[], ResultSet rs) throws SQLException
	{
		// 获取列数，
		int col = title.length;
		// 获取行数
		rs.last();
		int row = rs.getRow();
		// 获取表格信息
		Object[][] info = new Object[row][col];
		int count = 0;
		rs.beforeFirst();
		while (rs.next())
		{
			for (int i = 1; i <= col; i++)
			{
				info[count][i - 1] = rs.getString(title[i - 1]);
			}
			count++;
		}
		JTable table = new JTable(info, title);
		return table;
	}
	
	@Override
	public boolean insertLamp(String terminaladdress, String lampaddress,
			String lampport) throws SQLException
	{
		boolean ifsuc = false;

		String strsql = "select count(*) from Lamp where Lamp_Address='"
				+ lampaddress + "' and  DG_Address='" + terminaladdress
				+ "' and Port=" + lampport;
		PreparedStatement ps = TransferConnDB.getConnection().prepareStatement(strsql);
		ResultSet rs = ps.executeQuery();
		
		int affectline = 0;
		if(rs.next())
		{
			int num = rs.getInt(1);
			if(num == 0)
			{
				strsql = "insert into Lamp(DG_Address,Lamp_Address,Port)values('"
						+ terminaladdress
						+ "','"
						+ lampaddress
						+ "',"
						+ lampport + ")";
				PreparedStatement ps1 = TransferConnDB.getConnection().prepareStatement(strsql);
				ResultSet rs1 = ps1.executeQuery();
				
				affectline = ps.executeUpdate();
				if(affectline == 1)
				{
					ifsuc = true;
				}
				
				if(rs1 != null)
					rs1.close();
				if(ps1 != null)
					ps1.close();
			}
			else
			{
				ifsuc = false;
			}
		}
		
		if(rs != null)
			rs.close();
		if(ps != null)
			ps.close();

		return ifsuc;
	}
	
	


}
