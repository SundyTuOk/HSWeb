package com.sf.energy.prepayment.dao;

import java.io.IOException;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eltima.components.ui.m;
import com.sf.energy.prepayment.model.BatchOprModel;
import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.service.SendingXMLCode;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.util.ConnDB;

public class BatchOprOtherDao
{

	/**
	 * 解析预付费
	 * 
	 * @param info
	 *            返回的xml
	 * @param flag
	 *            判断标志
	 * @param Meter_ID
	 *            表计Id
	 * @param TheSaleInfoNum
	 *            售电单号
	 * @param QSYValue
	 *            购电前剩余金额
	 * @param Times
	 *            购买次数
	 * @param state
	 *            状态
	 * @param UserID
	 *            用户Id
	 * @param TotleUsed
	 * @param SYValue
	 *            剩余电量
	 * @param protocol 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public BatchOprModel parseXml(XMLCoder xmlCoder, String info, String flag, String Meter_ID, String TheSaleInfoNum, double QSYValue, String Times, String state,
			String UserID, String TotleUsed, String SYValue, String kind, String htmlShow) throws ParserConfigurationException, SAXException,
			IOException, SQLException, NumberFormatException, ParseException
	{
		BatchOprDao batchOprDao = new BatchOprDao();
		CMMeterDao cmMeterDao = new CMMeterDao();
		// String htmlShow="";
		// double QSYValue=0;//购电前剩余金额
		double HSYValue = 0;// 购电后剩余金额
		// String Times="";//购电次数
		// String TheSaleInfoNum="";//购电单号
		// String SYValue="";//剩余电量
		double TZValue = 0;// 透支电量
		String ValueTime = "";// 当前时间
		double theBeilv = 1;// 倍率
		String ZValueZY = "";
		// 电表名称
		String amMeter_name = "";
		double TotoleUsed = 0;
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
		String meterPotocol = "1";
		
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
			// 空调回路分闸 分闸报警表计
			if (("off".equals(flag)) || ("off1".equals(flag)))
			{
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);

				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			}
			// 下发购电量
			else if (("yufufei0".equals(flag)) || ("yufufei00".equals(flag)) || ("yufufei1".equals(flag)) || ("yufufei11".equals(flag)))
			{
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);

				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("bufa".equals(flag))
			{//97表的处理
				
				// System.out.println("bufa");
				/*NodeList resultList = doc.getElementsByTagName("result");
				Element resultNode = (Element) resultList.item(0);*/
				// 读电量
				if ("1".equals(kind))
				{
					SYValue = resultNode.getAttribute("SYValue");
					// System.out.println(resultNode.getAttribute("TZValue"));
					TZValue = Double.parseDouble(resultNode.getAttribute("TZValue"));
					// System.out.println(resultNode.getAttribute("TZValue"));
					if ("-1".equals(SYValue))
					{
					}
					ValueTime = resultNode.getAttribute("ValueTime");
					TZValue = TZValue * theBeilv;
					QSYValue = Double.parseDouble((SYValue)) * theBeilv;
					// System.out.println("123");
					batchOprModel.setQSYValue(QSYValue);
					// 购电次数
					Times = resultNode.getAttribute("BuyTimes");
					batchOprModel.setBuyTimes(Integer.parseInt(Times));
					// System.out.println(Meter_ID+"  "+TheSaleInfoNum+"  "+QSYValue+"  "+(Integer.parseInt(Times)+1));
					String back = SerCheckBu(Meter_ID, TheSaleInfoNum, QSYValue, Integer.parseInt(Times));
					batchOprModel.setBackValue(back);
					htmlShow = "<strong>读购电前剩余电量 </strong><span style='color:green'>  剩余电量：" + QSYValue + "度，透支电量：" + TZValue + "度，购电次数：" + Times
							+ "</span>";
					if("0".equals(back)){
						htmlShow += "<br/><span style='color:red'>该记录已经下发成功，系统将自动修改售电单记录！</span><br/>";
					}
					batchOprModel.setHtmlShow(htmlShow);
					// System.out.println("back:"+back);
				}
				// 下发购电量
				else if ("2".equals(kind))
				{
					batchOprDao.SerSaveSendState(Meter_ID, TheSaleInfoNum, "1", QSYValue + "", HSYValue + "", "",Times, "-1");
					batchOprModel.setHtmlShow(htmlShow);
				}
				// 再次读电量
				else if ("3".equals(kind))
				{
					// System.out.println("macarthur");
					Times = resultNode.getAttribute("BuyTimes");
					// System.out.println("Times:"+Times);
					TotoleUsed = Double.parseDouble(resultNode.getAttribute("TotoleUsed"));
					// //System.out.println("TotleUsed:"+TotleUsed);
					SYValue = resultNode.getAttribute("SYValue");
					String tZValue = resultNode.getAttribute("SYValue");
					// System.out.println("SYValue:"+SYValue);
					if ("-1".equals(SYValue))
					{
						// return;
					}
					ValueTime = resultNode.getAttribute("ValueTime");
					HSYValue = Double.parseDouble(SYValue) * theBeilv;
					double HTZValue = Double.parseDouble(tZValue) * theBeilv;
					TotoleUsed = TotoleUsed * theBeilv;
					// System.out.println(Meter_ID+" "+QSYValue+" "+" "+HSYValue+" "+Times+" "+TotleUsed);
					batchOprDao.SerSaveSendState(Meter_ID, TheSaleInfoNum, "2", QSYValue + "", HSYValue + "",HTZValue+"", Times, "-1");
					htmlShow = htmlShow + "<br/>" + "<strong>读购电后剩余电量:</strong><span style='color:green'>" + HSYValue + "度</span>";
					batchOprModel.setHtmlShow(htmlShow);
				}
			} else if ("off2".equals(flag))
			{
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);
				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("on".equals(flag))
			{
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);
				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("on2".equals(flag))
			{
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);
				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("ZQ".equals(flag))
			{
				/*NodeList resultList = doc.getElementsByTagName("result");
				Element resultNode = (Element) resultList.item(0);*/
				// 读总清前剩余电量
				if ("1".equals(kind))
				{
					SYValue = resultNode.getAttribute("SYValue");
					TotoleUsed = Double.parseDouble(resultNode.getAttribute("TotoleUsed"));
					batchOprModel.setSYValue(Integer.parseInt(SYValue));
					batchOprModel.setTotoleBuy(TotoleUsed);
//					System.out.println("TotoleUsed:" + TotoleUsed);
				}
				// 总清
				else if ("2".equals(kind))
				{
//					System.out.println("ZQ UserID：" + UserID);
					SerUpdateMeterState(Meter_ID, "ZQ", UserID, TotleUsed, SYValue);
					amMeter_name = getAmMeterName(Meter_ID);

					htmlShow = htmlShow + "执行成功";
					batchOprModel.setHtmlShow(htmlShow);
					// SYValue="0";
					// TotleUsed="0";
				}
			} else if ("readState".equals(flag))
			{
				/*NodeList resultList = doc.getElementsByTagName("result");
				Element resultNode = (Element) resultList.item(0);*/
				String flagState = resultNode.getAttribute("Flag");
//				System.out.println("flagState:" + flagState);
				SerUpdateMeterState(Meter_ID, flagState, UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);

				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("kaihu".equals(flag))
			{
				// System.out.println("pppp");
				SerUpdateMeterState(Meter_ID, flag, UserID, "", "");

				amMeter_name = getAmMeterName(Meter_ID);

				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			} else if ("read".equals(flag))
			{
				/*NodeList resultList = doc.getElementsByTagName("result");
				Element resultNode = (Element) resultList.item(0);*/
				// System.out.println("macarthur");
				ZValueZY = resultNode.getAttribute("TotoleUsed");
				SYValue = resultNode.getAttribute("SYValue");
				String tZValue = resultNode.getAttribute("TZValue");
				if ("-1".equals(SYValue))
				{
				} else
				{
					ValueTime = resultNode.getAttribute("ValueTime");
					// System.out.println("ZValueZY:"+ZValueZY);
					ZValueZY = Double.parseDouble(ZValueZY) * theBeilv + "";
					SYValue = Double.parseDouble(SYValue) * theBeilv + "";
					String TZValue1 = Double.parseDouble(tZValue) * theBeilv +"";
					// System.out.println(ValueTime+" fgfgfg "+ZValueZY+"  "+SYValue);
					cmMeterDao.SerSaveData(Integer.parseInt(Meter_ID), Float.parseFloat(ZValueZY), Float.parseFloat(SYValue), Float.parseFloat(TZValue1),ValueTime);
					htmlShow = "剩余电量:" + SYValue + "度";
					batchOprModel.setHtmlShow(htmlShow);
				}
			}
			// 销户
			else if ("xiaohu".equals(flag))
			{
				/*NodeList resultList = doc.getElementsByTagName("result");
				Element resultNode = (Element) resultList.item(0);*/
				// 读销户前剩余电量
				if ("1".equals(kind))
				{
					if (resultNode.hasAttribute("SYValue"))//97表
					{
						ZValueZY = resultNode.getAttribute("TotoleUsed");
						SYValue = resultNode.getAttribute("SYValue");
						Times = resultNode.getAttribute("BuyTimes");
						meterPotocol = "1";
						htmlShow = "读购电前剩余电量     剩余电量：" + SYValue + "度，购电次数：" + Times;
					}
					else
					{
						meterPotocol = "30";
						ZValueZY="0";
						SYValue = resultNode.getAttribute("SYMoney");
						Times=getBuytimes(Meter_ID.trim(),UserID);
						htmlShow = "读购电前剩余金额     剩余金额：" + SYValue + "元，购电次数：" + Times;
						
					}
					
					batchOprModel.setZValue(Double.parseDouble(ZValueZY)*theBeilv);
					batchOprModel.setSYValue(Double.parseDouble(SYValue)*theBeilv);
					batchOprModel.setBuyTimes(Integer.parseInt(Times));
					batchOprModel.setMeterPotocol(meterPotocol);
					batchOprModel.setHtmlShow(htmlShow);
					batchOprModel.setBeiLv(theBeilv);
				}
				// 销户退电
				else if ("2".equals(kind))
				{
					htmlShow = htmlShow + "执行成功";
					batchOprModel.setHtmlShow(htmlShow);
				}
				// 销户
				else if ("3".equals(kind))
				{
					SerUpdateMeterState(Meter_ID, "xiaohu", UserID, "", "");
					htmlShow = htmlShow + "执行成功";
					batchOprModel.setHtmlShow(htmlShow);
				}
				// 读销户后剩余电量
				else if ("4".equals(kind))
				{
					ZValueZY = resultNode.getAttribute("TotoleUsed");
					SYValue = resultNode.getAttribute("SYValue");
					String tZYValue = resultNode.getAttribute("TZYValue");
					if ("-1".equals(SYValue))
					{
					} else
					{
						if (resultNode.hasAttribute("SYValue"))//97表
						{
							ValueTime = resultNode.getAttribute("ValueTime");
							ZValueZY = Double.parseDouble(ZValueZY) * theBeilv + "";
							SYValue = Double.parseDouble(SYValue) * theBeilv + "";
							tZYValue= Double.parseDouble(tZYValue) * theBeilv + "";
							// System.out.println("SYValue:"+SYValue);
							cmMeterDao.SerSaveData(Integer.parseInt(Meter_ID), Float.parseFloat(ZValueZY), Float.parseFloat(SYValue),Float.parseFloat(tZYValue), ValueTime);
							htmlShow = htmlShow + "剩余电量:" + SYValue + "度";
							batchOprModel.setHtmlShow(htmlShow);
						}
						else
						{
							String tZValue="0.00";
							SYValue = resultNode.getAttribute("SYMoney");
							SYValue = Double.parseDouble(SYValue) * theBeilv + "";
							ValueTime = resultNode.getAttribute("ValueTime");
							
							ZValueZY=getzValueZY(Meter_ID.trim(),UserID);
							
							ZValueZY = Double.parseDouble(ZValueZY) * theBeilv + "";
							
							CMMsendCommand aprm = new CMMsendCommand();
							
							SendingXMLCode sxc = new SendingAPCode();
							XMLCoder xc = aprm.readMeter07(Integer.parseInt(Meter_ID), UserID, "00900201");// 07当前透支金额
							if (xc != null)
							{
								try
								{
									DecimalFormat df = new DecimalFormat("0.00");
									String str = sxc.sendXMLToServer(xc);
									System.out.println("收到的报文的回复是：" + str);
									String err = getAttrValue(str, "commandback", "error");
									if ("0".equals(err))
									{
										tZValue = getAttrValue(str, "result", "TZMoney");
										
										tZValue = df.format(df.parse(tZValue));
										tZValue = Double.parseDouble(tZValue)* theBeilv + "";
									}
								} catch (SocketTimeoutException e)
								{

								}
							}
							
							cmMeterDao.SerSaveData(Integer.parseInt(Meter_ID), Float.parseFloat(ZValueZY), Float.parseFloat(SYValue),Float.parseFloat(tZValue), ValueTime);
							
							htmlShow = "读购电前剩余金额     剩余金额：" + SYValue + "元，购电次数：" + Times;
							
						}
						
					}
				}
			} else if (("EXFZ".equals(flag)) || ("EXFZQX".equals(flag)) || ("EXFZFW".equals(flag)))
			{
				// SerUpdateMeterState(Meter_ID, "kaihu", UserID, "", "");
				amMeter_name = getAmMeterName(Meter_ID);
				htmlShow = htmlShow + "执行成功";
				batchOprModel.setHtmlShow(htmlShow);
			}
		}
		return batchOprModel;
	}
	public String getAttrValue(String xml, String tagName, String attrName)
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder;
		NodeList nodeList = null;
		String result = null;
		try
		{
			dombuilder = domfac.newDocumentBuilder();

			read = new StringReader(xml);

			source = new InputSource(read);

			Document document = dombuilder.parse(source);
			nodeList = document.getElementsByTagName(tagName);
			Element elm = (Element) nodeList.item(0);
			result = elm.getAttribute(attrName);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private String getzValueZY(String amMeterId, String userID) throws NumberFormatException, SQLException, UnknownHostException, IOException
	{
		String result="";
		CMMsendCommand cmMsendCommand = new CMMsendCommand();
		XMLCoder xmlCoder = new XMLCoder();
		SendingAPCode sendXml = new SendingAPCode();
		
		xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "00010000");
		if (xmlCoder != null)
		{
			String replyXml = sendXml.sendXMLToServer(xmlCoder);

			System.out.println("收到的报文的回复是：" + replyXml);

			if (replyXml != null)
			{
				NodeList nodeList = getNodeList(replyXml, "commandback");

				Element commandBack = (Element) nodeList.item(0);

				String err = commandBack.getAttribute("error");
				if (err.equals("0")) // 成功
				{
					NodeList resultNodelist = commandBack.getElementsByTagName("result");

					Element  resultElement = (Element) resultNodelist.item(0);
					
					String ZValueZY = resultElement.getAttribute("ZValueZY");
					
					result=ZValueZY;
				}

			} 
		} 
		return result;
		
	}

	/**
	 * 07 表获得购买次数
	 * @param trim
	 * @param userID
	 * @return
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private String getBuytimes(String amMeterId, String userID) throws NumberFormatException, SQLException, UnknownHostException, IOException
	{
		String result=null;
		CMMsendCommand cmMsendCommand = new CMMsendCommand();
		XMLCoder xmlCoder = new XMLCoder();
		SendingAPCode sendXml = new SendingAPCode();
		
		xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "07010202");
		if (xmlCoder != null)
		{
			String replyXml = sendXml.sendXMLToServer(xmlCoder);

			System.out.println("收到的报文的回复是：" + replyXml);

			if (replyXml != null)
			{
				NodeList nodeList = getNodeList(replyXml, "commandback");

				Element commandBack = (Element) nodeList.item(0);

				String err = commandBack.getAttribute("error");
				if (err.equals("0")) // 成功
				{
					NodeList resultNodelist = commandBack.getElementsByTagName("result");

					Element  resultElement = (Element) resultNodelist.item(0);
					
					String BuyTimes = resultElement.getAttribute("BuyTimes");
					
					result=BuyTimes;
				}

			} 
		} 
		return result;
	}

	/**
	 * 根据电表Id获取电表名称
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */

	public String getAmMeterName(String id) throws SQLException
	{
		String amMeterName = "";
		String sql = "select ammeter_name from ammeter where ammeter_id=" + id;
//		System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				amMeterName = rs.getString("ammeter_name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			amMeterName = rs.getString("ammeter_name");
//		}

		return amMeterName;
	}

	/**
	 * 下发选中 开户
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String kaiHuAmMeter(String id) throws SQLException
	{
		String amMeterId = null;
		String sql = "";
		String meterPotocol = (new CMMeterDao()).getMeterPotocol(Integer.parseInt(id));
		if("1".equals(meterPotocol)){
			sql = "select Ammeter_ID from AMMETERAPDATAS where TZVALUE<=0 and Ammeter_ID=" + id;
		}else {
			sql = "select Ammeter_ID from AMMETERAPDATAS where TZMoney<=0 and Ammeter_ID=" + id;
		}
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				amMeterId = id;
			} else
			{
				amMeterId = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			amMeterId = id;
//		} else
//		{
//			amMeterId = null;
//		}

		return amMeterId;
	}

	/**
	 * 开户根据条件筛选出电表id
	 * 
	 * @param areaId
	 *            区域Id
	 * @param arcId
	 *            建筑Id
	 * @param floor
	 *            楼层数
	 * @return
	 * @throws SQLException
	 */
	//哪里用到了？？？？？？？？？？？？？？？？？？？？
	public List<String> getKaihuamMeterId(String areaId, String arcId, String floor) throws SQLException
	{
		List<String> list = new ArrayList<String>();
		String sqlstrMeter = "";
		String strSelect = "  a.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )";
		if (!("-1".equals(areaId)))
		{
			strSelect = "  (a.Area_ID=" + areaId
					+ " and a.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) ";
		}
		if (!("-1".equals(arcId)))
		{
			strSelect = "  (a.Architecture_ID=" + arcId + ")";
		}
		if (!("-1".equals(floor)))
		{
			strSelect = "  (a.Architecture_ID=" + arcId + ") and (a.Floor=" + floor + ")";
		}
		String sql = "select Ammeter_ID from AmMeter  a,AMMETERAPDATAS b where a.AMMETER_ID=b.AMMETER_ID and b.TZVALUE<=0 and " + strSelect;
//		System.out.println("sql12:" + sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				list.add(rs.getString("Ammeter_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while (rs.next())
//		{
//			list.add(rs.getString("Ammeter_ID"));
//		}

		return list;
	}

	/**
	 * 根据条件筛选出电表id
	 * 
	 * @param areaId
	 *            区域Id
	 * @param arcId
	 *            建筑Id
	 * @param floor
	 *            楼层数
	 * @return
	 * @throws SQLException
	 */
	public List<String> getamMeterId(String areaId, String arcId, String floor) throws SQLException
	{
		List<String> list = new ArrayList<String>();
		String sqlstrMeter = "";
		String strSelect = "  a.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )";
		if (!("-1".equals(areaId)))
		{
			strSelect = "  (a.Area_ID=" + areaId
					+ " and a.Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) ";
		}
		if (!("-1".equals(arcId)))
		{
			strSelect = "  (a.Architecture_ID=" + arcId + ")";
		}
		if (!("-1".equals(floor)))
		{
			strSelect = "  (a.Architecture_ID=" + arcId + ") and (a.Floor=" + floor + ")";
		}
		String sql = "select Ammeter_ID from AmMeter  a  where  " + strSelect;
//		System.out.println("sql12:" + sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				list.add(rs.getString("Ammeter_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while (rs.next())
//		{
//			list.add(rs.getString("Ammeter_ID"));
//		}
	
		return list;
	}

	/**
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
	 * @param Cmd
	 * @param UserID
	 * @return
	 * @throws SQLException
	 */
	public String SerGetExecList(String SendType, String SelectTreeFlag, String SelectTreeID, String SelectFloorID, String[] amMeterList, String Cmd,
			String UserID, String price, String AmMeterStatus) throws SQLException
	{

		String write = null;
		String sql = "";

		String AmMeter_Name = "";
		String AmMeter_485Address = "";
		String Gross = "";
		String Price_Value = "";
		String sqlstrMeter = "";
		String amMeterId = "";
		if ("1".equals(SendType))// 所有表计月补
		{
			String strSelect = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )";
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

			/*sql = "select Ammeter_ID,nvl(APState,'未开户') APState,AmMeter_Name,AmMeter_485Address,nvl(APYBValue,0) APYBValue,"
					+ "(select BJValue from Price b where a.Price_ID=b.Price_ID) BJValue,"
					+ "(select Price_Num from Price  b where a.Price_ID=b.Price_ID) Price_Num,"
					+ "(SELECT TEXINGVALUE FROM TEXINGVALUE b WHERE A .METESTYLE_ID = b.METESTYLE_ID and b.METERTEXING_ID=18) PROTOCOL,AmMeter_Stat from AmMeter  a "
					+ strSelect + " and Gather_ID in (select Gather_ID from Gather where Gather_State<>0)";*/
			sql = "select Ammeter_ID,METESTYLE_ID,nvl(APState,'未开户') APState,AmMeter_Name,AmMeter_485Address,nvl(APYBValue,0) APYBValue,"
					+ "(select BJValue from Price b where a.Price_ID=b.Price_ID) BJValue,"
					+ "(select Price_Num from Price  b where a.Price_ID=b.Price_ID) Price_Num,"
					+ "(SELECT TEXINGVALUE FROM TEXINGVALUE b WHERE A .METESTYLE_ID = b.METESTYLE_ID and b.METERTEXING_ID=18) PROTOCOL,AmMeter_Stat from AmMeter  a "
					+ strSelect + " and Gather_ID in (select Gather_ID from Gather)";

		} else if ("2".equals(SendType))// 选中表计月补
		{
			// System.out.println("length:" + amMeterList.length);
			for (int i = 0; i < amMeterList.length - 1; i++)
			{
				amMeterId = amMeterId + amMeterList[i] + ",";
			}
			amMeterId = amMeterId + amMeterList[amMeterList.length - 1];
			/*sql = " select Ammeter_ID,nvl(APState,'未开户') APState,AmMeter_Name,AmMeter_485Address,nvl(APYBValue,0) APYBValue,"
					+ "(select BJValue from Price  b where a.Price_ID=b.Price_ID) BJValue,"
					+ "(select Price_Num from Price  b where a.Price_ID=b.Price_ID) Price_Num,"
					+ "(SELECT TEXINGVALUE FROM TEXINGVALUE b WHERE A .METESTYLE_ID = b.METESTYLE_ID and b.METERTEXING_ID=18) PROTOCOL,"
					+ "AmMeter_Stat from AmMeter  a  where AmMeter_ID in("
					+ amMeterId + ")" + " and Gather_ID in (select Gather_ID from Gather where Gather_State<>0)";*/
			sql = " select Ammeter_ID,METESTYLE_ID,nvl(APState,'未开户') APState,AmMeter_Name,AmMeter_485Address,nvl(APYBValue,0) APYBValue,"
					+ "(select BJValue from Price  b where a.Price_ID=b.Price_ID) BJValue,"
					+ "(select Price_Num from Price  b where a.Price_ID=b.Price_ID) Price_Num,"
					+ "(SELECT TEXINGVALUE FROM TEXINGVALUE b WHERE A .METESTYLE_ID = b.METESTYLE_ID and b.METERTEXING_ID=18) PROTOCOL,"
					+ "AmMeter_Stat from AmMeter  a  where AmMeter_ID in("
					+ amMeterId + ")" + " and Gather_ID in (select Gather_ID from Gather)";
			// System.out.println("sql:" + sql);
		}

		// 补发
		if ("bufa".equals(Cmd))
		{
			// System.out.println("macar");
			sqlstrMeter = "select SaleInfoNum from APSaleInfo where Status!=1 and Ammeter_ID in(select Ammeter_ID from (" + sql + ")T )";
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String SaleInfoNum = rs.getString("SaleInfoNum");
					if (write == null)
						write = SaleInfoNum;
					else
						write += "/" + SaleInfoNum;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			while (rs.next())
//			{
//				String SaleInfoNum = rs.getString("SaleInfoNum");
//				if (write == null)
//					write = SaleInfoNum;
//				else
//					write += "/" + SaleInfoNum;
//			}

		}
		// 分闸报警表计
		else if ("off1".equals(Cmd))
		{
			/*sqlstrMeter = "select Ammeter_ID from (select a.Ammeter_ID,BJValue,SYValue,Price_Num,APState from (select Ammeter_ID,(select max(ValueTime) from AmMeterAPDatas  c where T.AmMeter_ID=c.AmMeter_ID)MaxValueTime,BJValue,Price_Num,APState from ("
					+ sql
					+ " and AmMeter_Stat<>0 "
					+ ")T)a, AmMeterAPDatas  b where a.Ammeter_ID=b.Ammeter_ID and a.MaxValueTime=b.ValueTime)T where (SYValue<=BJValue and SYValue>=0) or (SYMONEY>=0 AND SYMONEY<=BJValue)";*/
			sqlstrMeter = "select Ammeter_ID from (select a.Ammeter_ID,BJValue,SYValue,SYMONEY,Price_Num,APState from (select Ammeter_ID,(select max(ValueTime) from AmMeterAPDatas  c where T.AmMeter_ID=c.AmMeter_ID)MaxValueTime,BJValue,Price_Num,APState from ("
					+ sql
					//+ " and "
					+ ")T)a, AmMeterAPDatas  b where a.Ammeter_ID=b.Ammeter_ID and a.MaxValueTime=b.ValueTime)T where (SYValue<=BJValue and SYValue>=0) or (SYMONEY>=0 AND SYMONEY<=BJValue)";
			// System.out.println("sqlstrMeter:" + sqlstrMeter);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			while (rs.next())
//			{
//				String Meter_ID = rs.getString("Ammeter_ID");
//				if (write == null)
//					write = Meter_ID;
//				else
//					write += "/" + Meter_ID;
//			}


		}
		// 取消预付费未开户表计 启动预付费未开户表计
		else if ("yufufei0".equals(Cmd) || "yufufei1".equals(Cmd))
		{
			/*sqlstrMeter = "select Ammeter_ID from (select a.Ammeter_ID,BJValue,SYValue,Price_Num,APState from (select Ammeter_ID,(select max(ValueTime) from AmMeterAPDatas  c where T.AmMeter_ID=c.AmMeter_ID)MaxValueTime,BJValue,Price_Num,APState from ("
					+ sql
					+ " and AmMeter_Stat<>0 "
					+ ")T)a, AmMeterAPDatas  b where a.Ammeter_ID=b.Ammeter_ID and a.MaxValueTime=b.ValueTime)T where APState!='开户' and Price_Num='E0'";*/
			sqlstrMeter = "select Ammeter_ID from (select a.Ammeter_ID,BJValue,SYValue,Price_Num,APState from (select Ammeter_ID,(select max(ValueTime) from AmMeterAPDatas  c where T.AmMeter_ID=c.AmMeter_ID)MaxValueTime,BJValue,Price_Num,APState from ("
					+ sql
					//+ " and "
					+ ")T)a, AmMeterAPDatas  b where a.Ammeter_ID=b.Ammeter_ID and a.MaxValueTime=b.ValueTime)T where APState!='开户' and Price_Num='E0'";
			// System.out.println("sqlstrMeter:" + sqlstrMeter);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			while (rs.next())
//			{
//				String Meter_ID = rs.getString("Ammeter_ID");
//				if (write == null)
//					write = Meter_ID;
//				else
//					write += "/" + Meter_ID;
//			}

		}
		// 开户
		else if ("kaihu".equals(Cmd))
		{
			// 剩余电量大于0开户
			/*sqlstrMeter = "select Ammeter_ID from (select T1.AmMeter_ID,(select  SYValue from AmMeterAPDatas  T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime)SYValue,(select  TZValue from AmMeterAPDatas  T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime)TZValue from  ("
					+ sql
					+ " and AmMeter_Stat<>0  "
					+ ")T1,(select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 where T1.AmMeter_ID=T2.AmMeter_ID and  APState!='开户')T where (SYValue>=0 and TZValue=0) or (SYMoney>=0 and TZMoney=0)";*/
			//sqlstrMeter = "select Ammeter_ID from (select T1.AmMeter_ID,(select  SYValue from AmMeterAPDatas  T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime)SYValue,(select  TZValue from AmMeterAPDatas  T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime)TZValue from  ("	
//			+ sql
//					//+ " and  "
//					+ ")T1,"
//					+ "(select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 "
//					+ "where T1.AmMeter_ID=T2.AmMeter_ID "
//					+ "and  APState!='开户'"
//					+ ")T where (SYValue>=0 and TZValue=0)"
//					+ " or (SYMoney>=0 and TZMoney=0)"
//					+ "";
			sqlstrMeter = "select T1.AmMeter_ID from  ("	//李嵘20150908
					+ sql
					+ ")T1 "
					+ " LEFT JOIN "
					+ " (SELECT a.AmMeter_ID,b.ValueTime,SYValue,TZValue,SYMoney,TZMoney FROM "
					+ "(select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID) a,AmMeterAPDatas b "
					+ " where a.AMMETER_ID=b.AMMETER_ID and a.MValueTime=b.ValueTime and TZValue<=0 and TZMoney<=0 "
					+ ")T2 "
					+ " ON T1.AmMeter_ID=T2.AmMeter_ID "
					+ " where T1.APState!='开户'";
			// System.out.println("sqlstrMeter:"+sqlstrMeter);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			/*
			 * rs.last(); int count = rs.getRow(); if (count > 5000) { write =
			 * "-1"; return write; }
			 */
//			while (rs.next())
//			{
//				String Meter_ID = rs.getString("Ammeter_ID");
//				if (write == null)
//					write = Meter_ID;
//				else
//					write += "/" + Meter_ID;
//			}
		}
		// 空调回路分闸；空调回路合闸
		else if (("off".equals(Cmd)) || ("on".equals(Cmd)))
		{
			//只有97规约双回路有效 李嵘20150720
			/*sqlstrMeter = "select AmMeter_ID from ("
					+ sql
					+ " and AmMeter_Stat<>0 )T1 WHERE PROTOCOL=1";*/
			sqlstrMeter = "select AmMeter_ID from ("
					+ sql
					+ " )";
			// System.out.println("sqlstrMeter:"+sqlstrMeter);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			/*
			 * rs.last(); int count = rs.getRow(); if (count > 5000) { write =
			 * "-1"; return write; }
			 */
//			while (rs.next())
//			{
//				String Meter_ID = rs.getString("Ammeter_ID");
//				if (write == null)
//					write = Meter_ID;
//				else
//					write += "/" + Meter_ID;
//			}

		}
		// 设置拉闸报警和设置价格都是对于07表的，查询语句一样
		else if ("lazhabaojing".equals(Cmd) || "setPrice".equals(Cmd))
		{
			sqlstrMeter = "select * from (" + sql + ")a  LEFT JOIN TEXINGVALUE b on a.METESTYLE_ID=b.METESTYLE_ID WHERE b.METERTEXING_ID=18 AND b.TEXINGVALUE=30" ;
			
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				//ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//李嵘20150804
				ps = conn.prepareStatement(sqlstrMeter, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
		}
		else
		{
			// System.out.println("kkkk");
			if (!("read".equals(Cmd)))
				/*sql = sql + " and AmMeter_Stat<>0";*/
				sql = sql + " ";
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					String Meter_ID = rs.getString("Ammeter_ID");
					if (write == null)
						write = Meter_ID;
					else
						write += "/" + Meter_ID;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			while (rs.next())
//			{
//				String Meter_ID = rs.getString("Ammeter_ID");
//				if (write == null)
//					write = Meter_ID;
//				else
//					write += "/" + Meter_ID;
//			}

		}
		/*
		 * if (write != "") { if (Cmd == "bufa") CommonCs.SystemLogAdd(UserID,
		 * "4100", "补发未下发购电记录", 1, "");//日志 else if (Cmd == "off" || Cmd ==
		 * "off2") CommonCs.SystemLogAdd(UserID, "4100", "批量分闸", 1, "");//日志
		 * else if (Cmd == "off1") CommonCs.SystemLogAdd(UserID, "4100",
		 * "批量分闸报警表计", 1, "");//日志 else if (Cmd == "yufufei0")
		 * CommonCs.SystemLogAdd(UserID, "4100", "取消预付费未开户表计", 1, "");//日志 else
		 * if (Cmd == "yufufei1") CommonCs.SystemLogAdd(UserID, "4100",
		 * "启动预付费未开户表计", 1, "");//日志 else if (Cmd == "on" || Cmd == "on2")
		 * CommonCs.SystemLogAdd(UserID, "4100", "批量合闸", 1, "");//日志 else if
		 * (Cmd == "readState") CommonCs.SystemLogAdd(UserID, "4100",
		 * "批量抄读表计状态字", 1, "");//日志 else if (Cmd == "read")
		 * CommonCs.SystemLogAdd(UserID, "4100", "批量抄表", 1, "");//日志 else if
		 * (Cmd == "kaihu") CommonCs.SystemLogAdd(UserID, "4100", "批量开户", 1,
		 * "");//日志 else if (Cmd == "xiaohu") CommonCs.SystemLogAdd(UserID,
		 * "4100", "批量销户", 1, "");//日志 else if (Cmd == "EXFZ")
		 * CommonCs.SystemLogAdd(UserID, "4100", "恶性负载设置", 1, "");//日志 else if
		 * (Cmd == "EXFZQX") CommonCs.SystemLogAdd(UserID, "4100", "取消恶性负载设置",
		 * 1, "");//日志 else if (Cmd == "EXFZFW") CommonCs.SystemLogAdd(UserID,
		 * "4100", "允许恶性负载范围设置", 1, "");//日志 else if (Cmd == "ZQ")
		 * CommonCs.SystemLogAdd(UserID, "4100", "批量总清", 1, "");//日志
		 * 
		 * }
		 */
		return write;
	}

	/**
	 * 
	 * @param TaskNum
	 * @param commandlist
	 *            判断标志
	 * @param UserID
	 *            用户id
	 * @param strPart
	 *            售电单号
	 * @return
	 * @throws SQLException
	 */
	public String SerGetCommandList(String commandlist, String UserID, String strPart) throws SQLException
	{
		String write = null;
		String sql = "";

		if ("bufa".equals(commandlist))
		{
			sql = "select AmMeter_ID,Ammeter_Name,Status,TheGross,theMoney from APSaleInfo where SaleInfoNum='" + strPart + "'";
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				if (rs.next())
				{

					String Meter_ID = rs.getString("AmMeter_ID");

					String Ammeter_Name = rs.getString("Ammeter_Name");

					String Status = rs.getString("Status");
					if (!"1".equals(Status))
					{
						String Gross = rs.getString("TheGross");
						String theMoney = rs.getString("theMoney");
						write = Meter_ID + "/" + Gross + "/" + Ammeter_Name + "/" + theMoney;
					} else
					{
						write = null;
					}

				} else
				{
					write = null;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			if (rs.next())
//			{
//
//				String Meter_ID = rs.getString("AmMeter_ID");
//
//				String Ammeter_Name = rs.getString("Ammeter_Name");
//
//				String Status = rs.getString("Status");
//				if (!"1".equals(Status))
//				{
//					String Gross = rs.getString("TheGross");
//					String theMoney = rs.getString("theMoney");
//					write = Meter_ID + "/" + Gross + "/" + Ammeter_Name + "/" + theMoney;
//				} else
//				{
//					write = null;
//				}
//
//			} else
//			{
//				write = null;
//			}

		}

		return write;
	}

	/**
	 * 得到XML后更新相关信息
	 * 
	 * @param Meter_ID
	 * @param state
	 * @param UserID
	 * @param TotleUsed
	 * @param SYValue
	 * @throws SQLException
	 */
	public void SerUpdateMeterState(String Meter_ID, String state, String UserID, String TotleUsed, String SYValue) throws SQLException// 注意必须是静态方法，获取地图标记列表信息
	{
		BatchOprDao batchOprDao = new BatchOprDao();
		String IsSupply = "";
		int supply = 0;
		String meterPotocol = (new CMMeterDao().getMeterPotocol(Integer.parseInt(Meter_ID)));
		if ("off".equals(state) || "off1".equals(state))
		{
			supply = getisSupply(Meter_ID);
			IsSupply = padLeft(supply);
			IsSupply = "1" + IsSupply.substring(1);
			updateAmMeter(IsSupply, Meter_ID);
		} else if ("off2".equals(state))
		{
			supply = getisSupply(Meter_ID);
			IsSupply = padLeft(supply);
			if("1".equals(meterPotocol)){
				IsSupply = IsSupply.substring(0, 1) + "1";
			}else{
				IsSupply =  "1" + IsSupply.substring(0, 1);
			}
			updateAmMeter(IsSupply, Meter_ID);
		} else if ("on".equals(state))
		{
			supply = getisSupply(Meter_ID);
			IsSupply = padLeft(supply);
			IsSupply = "0" + IsSupply.substring(1);
			updateAmMeter(IsSupply, Meter_ID);
		} else if ("on2".equals(state))
		{
			supply = getisSupply(Meter_ID);
			IsSupply = padLeft(supply);
			if("1".equals(meterPotocol)){
				IsSupply = IsSupply.substring(0, 1) + "0";
			}else{
				IsSupply = "0" + IsSupply.substring(0, 1);
			}
			updateAmMeter(IsSupply, Meter_ID);
		} else if ("ZQ".equals(state))
		{
			deleteZQ(Meter_ID);
			updateZQ(Meter_ID);
			// CommonCs.ExecSqlText("Delete from ZAmDatas" +
			// Meter_ID);//清空用电信息
			// .ExecSqlText("delete from AmMeterAPDatas where AmMeter_ID="
			// + Meter_ID);
			// CommonCs.ExecSqlText("update T_DayAm set EndReadingDate=getdate(),EndZValueZY=0 where AmMeter_ID="
			// + Meter_ID + " and SelectYear=" +
			// DateTime.Now.AddDays(-1).Year.ToString() +
			// " and SelectMonth=" +
			// DateTime.Now.AddDays(-1).Month.ToString() +
			// " and SelectDay=" +
			// DateTime.Now.AddDays(-1).Day.ToString() + "");
			String GValue = "";
			double Price = 0;
			String sql = "select nvl(GValue,0) GValue,(select Price_Value from Price  b where a.Price_ID=b.Price_ID) Price_Value from AmMeter  a where AmMeter_ID="
					+ Meter_ID;
//			System.out.println("sql:" + sql);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				if (rs.next())
				{
					GValue = rs.getString("GValue");
					String Price_Value = rs.getString("Price_Value");
					if (Price_Value != null)
					{
						Price = Double.parseDouble(Price_Value);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			if (rs.next())
//			{
//				GValue = rs.getString("GValue");
//				String Price_Value = rs.getString("Price_Value");
//				if (Price_Value != null)
//				{
//					Price = Double.parseDouble(Price_Value);
//				}
//			}
	

			if (GValue == null)
				GValue = "0";
			if (TotleUsed == null)
				TotleUsed = "0";
			if (SYValue == null)
				SYValue = "0";
			String TZValue = "0";
			String TZMoney = "0";
			if (Double.parseDouble(GValue) > 0)
			{
				double SY = Double.parseDouble(GValue) - Double.parseDouble(TotleUsed);
				if (SY > 0)
					SYValue = SY + "";
				else
				{
					TZValue = Math.abs(SY) + "";
					TZMoney = Math.abs(SY * Price) + "";
				}
			}
//			System.out.println("kuaiyue:" + UserID);
			updateLastZQ(Meter_ID);
			addZQ(Meter_ID, GValue, TotleUsed, SYValue, TZValue, TZMoney, UserID);
			// CommonCs.ExecSqlText("update AmMeter set APState='销户',GValue=0 where Ammeter_ID="
			// + Meter_ID);
			// CommonCs.ExecSqlText("insert into APZQInfo(Ammeter_ID,TheTime,OldSY,NewZValue,SYValue,TZValue,TheMoney,Users_ID) values("
			// + Meter_ID + ",getdate()," + GValue + "," +
			// TotleUsed + "," + SYValue + "," + TZValue + "," +
			// TZMoney + "," + UserID + ")");

		} else if ("kaihu".equals(state))
		{
			String AmMeter_485Address = "";
			String AmMeter_Name = "";
			String Price = "";
			String Gvalue = "";
			String sql = "select AmMeter_Name,GVALUE,AmMeter_485Address,(select Price_Value from Price  c where b.Price_ID=c.Price_ID)Price_Value,AmMeter_Stat,GVALUE from AmMeter  b where  AmMeter_ID="
					+ Meter_ID;
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				if (rs.next())
				{
					AmMeter_Name = rs.getString("AmMeter_Name");
					AmMeter_485Address = rs.getString("AmMeter_485Address");
					Price = rs.getString("Price_Value");
					Gvalue = rs.getString("GVALUE");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			if (rs.next())
//			{
//				AmMeter_Name = rs.getString("AmMeter_Name");
//				AmMeter_485Address = rs.getString("AmMeter_485Address");
//				Price = rs.getString("Price_Value");
//				Gvalue = rs.getString("GVALUE");
//			}
			addAPKaihuInfo(Meter_ID, Gvalue, UserID);
			sql = "select nvl(SYvalue,0) SYvalue,nvl(SYMoney,0) SYMoney from  AmMeterAPDatas  b,(select MAX(ValueTime )MValueTime from AmMeterAPDatas where AmMeter_ID="
					+ Meter_ID + ") a where b.AmMeter_ID=" + Meter_ID + " and a.MValueTime=b.ValueTime ";
			String SYvalue = "";
			String SYMoney = "";
			Connection conn1 = null;
			PreparedStatement ps1 = null;
			ResultSet rs1 =null;
			try
			{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					SYvalue = rs1.getString("SYvalue");
					SYMoney = rs1.getString("SYMoney");
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps1, rs1);
			}
			if ("".equals(SYvalue))
			{
				SYvalue = "0";
			}
			if("".equals(SYMoney)){
				SYMoney = "0";
			}
			//String meterPotocol = (new CMMeterDao().getMeterPotocol(Integer.parseInt(Meter_ID)));
			if("1".equals(meterPotocol)){
				
			}else {
				SYValue = SYMoney;
			}
			if (Double.parseDouble(SYvalue) > 0)
			{
				String SaleInfoNum = "";
				// System.out.println("macar");
				String maxNum = batchOprDao.getSaleInfoNum();
				// System.out.println("maxNum:"+maxNum);
				SaleInfoNum = batchOprDao.GetNextChareNum(maxNum);

				boolean isS = false;
				// 写入数据库
				// 添加售电单，SaleInfoNum必须是唯一的如果主键重复会异常，则重新生成单号添加直到添加成功
				while (!isS)
				{
					try
					{
						String Users_Name = "";
			            if ("0".equals(UserID))
			            {
			                Users_Name = "一卡通售电";
			            }
			            else
			            {
			                Users_Name = batchOprDao.getUserNameByID(UserID);
			                
			            }
						batchOprDao.addSaleInfoNum(SaleInfoNum, Meter_ID, AmMeter_Name, AmMeter_485Address, "3", UserID, Price, SYvalue, "0",Users_Name);
						isS = true;
						break;
					} catch (Exception e)
					{
						{
							maxNum = batchOprDao.getSaleInfoNum();
							SaleInfoNum = batchOprDao.GetNextChareNum(maxNum);
						}
					}
				}
				// CommonCs.SystemLogAdd(UserID, "4100",
				// "开户调剂", 1, "");//日志
			}
			updateAmMeter(Meter_ID);

		} else if ("xiaohu".equals(state))
		{
			xiaohuupdateAmMeter(Meter_ID);
		} else if ("00".equals(state))
		{
			updateAmMeter(state, Meter_ID);
			// CommonCs.ExecSqlText("update AmMeter set IsSupply=1 where Ammeter_ID="
			// + Meter_ID);
		} else if ("01".equals(state))
		{
			updateAmMeter(state, Meter_ID);
			// CommonCs.ExecSqlText("update AmMeter set IsSupply=1 where Ammeter_ID="
			// + Meter_ID);
		} else if ("10".equals(state))
		{
			updateAmMeter(state, Meter_ID);
			// CommonCs.ExecSqlText("update AmMeter set IsSupply=10 where Ammeter_ID="
			// + Meter_ID);
		} else if ("11".equals(state))
		{
			updateAmMeter(state, Meter_ID);
			// CommonCs.ExecSqlText("update AmMeter set IsSupply=11 where Ammeter_ID="
			// + Meter_ID);
		}
	}

	/**
	 * 总清删除
	 * 
	 * @param Meter_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteZQ(String Meter_ID) throws SQLException
	{
		String sql = "delete from AmMeterAPDatas where AmMeter_ID=" + Meter_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return b;
	}

	/**
	 * 总清增加
	 * 
	 * @param Meter_ID
	 * @param GValue
	 * @param TotleUsed
	 * @param SYValue
	 * @param TZValue
	 * @param TZMoney
	 * @param UserID
	 * @return
	 * @throws SQLException
	 */

	public boolean addZQ(String Meter_ID, String GValue, String TotleUsed, String SYValue, String TZValue, String TZMoney, String UserID)
			throws SQLException
	{
//		System.out.println("UserID:" + UserID);
		String sql = "insert into APZQInfo(Ammeter_ID,TheTime,OldSY,NewZValue,SYValue,TZValue,TheMoney,Users_ID) values(" + Meter_ID + ",sysdate,"
				+ GValue + "," + TotleUsed + "," + SYValue + "," + TZValue + "," + TZMoney + "," + UserID + ")";
//		System.out.println("sqladd:" + sql);
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 总清更新
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean updateLastZQ(String Meter_ID) throws SQLException
	{
		String sql = "update AmMeter set APState='销户',GValue=0 where Ammeter_ID=" + Meter_ID;
//		System.out.println("sql1：" + sql);
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 总清更新
	 * 
	 * @param Meter_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateZQ(String Meter_ID) throws SQLException
	{
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -1);
		String year = now.get(Calendar.YEAR) + "";
		String month = (now.get(Calendar.MONDAY) + 1) + "";
		String day = now.get(Calendar.DAY_OF_MONTH) + "";
		String sql = "update T_DayAm set EndReadingDate=sysdate,EndZValueZY=0 where AmMeter_ID=" + Meter_ID + " and SelectYear=" + year
				+ " and SelectMonth=" + month + " and SelectDay=" + day + "";

		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	// 获取指定电表的isSupply
	public int getisSupply(String Meter_ID) throws SQLException
	{
		int isSupply = 0;
		String sql = "select IsSupply from AmMeter where Ammeter_ID=" + Meter_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				isSupply = rs.getInt("IsSupply");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			isSupply = rs.getInt("IsSupply");
//		}

		return isSupply;
	}

	/**
	 * 销户更新电表相关信息
	 * 
	 * @param meter_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean xiaohuupdateAmMeter(String amMeterID) throws SQLException
	{
		/*
		 * String sql = "update AmMeter set APState='销户' where Ammeter_ID=" +
		 * meter_ID; PreparedStatement ps =
		 * ConnDB.getConnection().prepareStatement(sql); boolean b =
		 * (ps.executeUpdate() == 1); if (ps != null) { ps.close(); } return b;
		 */

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String oldSY = null;
		String sql = "select oldSY from (select oldSY from APKaihuInfo where Ammeter_ID=" + amMeterID + " and TheTime >to_date('"
				+ df.format(new Date()) + "','yyyy-mm-dd')-7 order by TheTime desc) where rownum=1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				oldSY = rs.getString("oldSY");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while (rs.next())
//		{
//			oldSY = rs.getString("oldSY");
//		}

		String set = " ";
		if (oldSY != null)
		{
			set = ",GValue=" + oldSY;
		}

		String sql3 = "update AmMeter set APState='销户'" + set + " where Ammeter_ID=" + amMeterID;
		Connection conn3=null;
		PreparedStatement ps3 =null;
		boolean b=false;
		try
		{
			conn3 = ConnDB.getConnection();
			ps3 = conn3.prepareStatement(sql3);

			b = (ps3.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn3, ps3);
		}
		
//		PreparedStatement ps3 = ConnDB.getConnection().prepareStatement(sql3);
//		boolean b = (ps3.executeUpdate() == 1);
//		if (ps3 != null)
//		{
//			ps3.close();
//		}
		return b;

	}

	/**
	 * 开户更新电表相关信息
	 * 
	 * @param meter_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean updateAmMeter(String meter_ID) throws SQLException
	{
		String sql = "update AmMeter set APState='开户',GValue=0 where Ammeter_ID=" + meter_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	// 更新表计相关信息
	public boolean updateAmMeter(String isSupply, String meter_ID) throws SQLException
	{
		String sql = "update AmMeter set IsSupply='" + isSupply + "' where Ammeter_ID=" + meter_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public String padLeft(int num)
	{
		String result = "";
		if (num < 10)
		{
			result = "1" + num;
		} else
		{
			result = "" + num;
		}
		return result;
	}

	/**
	 * 补发验证电量是否已发送
	 * 
	 * @param MeterID
	 *            表计Id
	 * @param SaleInfoNum
	 *            购电单号
	 * @param QSYValue
	 * @param Times
	 * @return
	 * @throws SQLException
	 */
	public String SerCheckBu(String MeterID, String SaleInfoNum, double QSYValue, int Times) throws SQLException
	{
		String write = "";
		String sql = "select Times,QSYValue,BuyTime from APSaleInfo where Status=0 and SaleInfoNum='" + SaleInfoNum + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				String strQSYValue = rs.getString("QSYValue");
//				System.out.println("strQSYValue:" + strQSYValue);
				if (strQSYValue == null)
				{
					write = "1";
				} else
				{
					if (Double.parseDouble(strQSYValue) >= QSYValue)
					{
						write = "1";
					} else
					{
						write = "0";
						updateAPSaleInfo(SaleInfoNum);
					}
				}
			} else
			{
				write = "0";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			String strQSYValue = rs.getString("QSYValue");
////			System.out.println("strQSYValue:" + strQSYValue);
//			if (strQSYValue == null)
//			{
//				write = "1";
//			} else
//			{
//				if (Double.parseDouble(strQSYValue) >= QSYValue)
//				{
//					write = "1";
//				} else
//				{
//					write = "0";
//					updateAPSaleInfo(SaleInfoNum);
//				}
//			}
//		} else
//		{
//			write = "0";
//		}

		return write;
	}

	// 更新APSaleInfo
	public boolean updateAPSaleInfo(String SaleInfoNum) throws SQLException
	{
		String sql = "update APSaleInfo set Status=1 where SaleInfoNum='" + SaleInfoNum + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 开户插入数据
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean addAPKaihuInfo(String Meter_ID, String Gvalue, String userID) throws SQLException
	{
		String sql = "insert into APKaihuInfo(Ammeter_ID,TheTime,OldSY,NewZValue,SYValue,TZValue,TheMoney,Users_ID) values(" + Meter_ID + ",sysdate,"
				+ Gvalue + ",0,0,0,0," + userID + ")";
//		System.out.println("sqladd:" + sql);
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	/**
	 * 获得某个节点的
	 * 
	 * @param xml
	 *            XML字符串
	 * @param tagName
	 *            节点的名称
	 * @return 对应节点的element对象
	 * @throws Exception
	 */
	public NodeList getNodeList(String xml, String tagName)
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder;
		NodeList nodeList = null;
		try
		{
			dombuilder = domfac.newDocumentBuilder();

			read = new StringReader(xml);

			source = new InputSource(read);

			Document document = dombuilder.parse(source);
			nodeList = document.getElementsByTagName(tagName);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeList;
	}

}
