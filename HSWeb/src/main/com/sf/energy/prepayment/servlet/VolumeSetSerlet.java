package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sf.energy.prepayment.dao.AutoUpDataSaver;
import com.sf.energy.prepayment.dao.BatchOprDao;
import com.sf.energy.prepayment.dao.BatchOprOtherDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.dao.VolumeSetDao;
import com.sf.energy.prepayment.model.BatchOprModel;
import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class VolumeSetSerlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		findMethod(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp)
	{
		String method = req.getParameter("method");
		// System.out.println("method:" + method);

		// 电费价格信息
		if ("getPriceInfo".equals(method))
		{
			try
			{
				try
				{
					getPriceInfo(req, resp);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("getYueBuAmMeter".equals(method))
		{
			try
			{
				getYueBuAmMeter(req, resp);
			} catch (UnknownHostException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("getSendTotal".equals(method))
		{
			try
			{
				getSendTotal(req, resp);
			} catch (UnknownHostException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 选中开户
		if ("getLinShiAmMeter".equals(method))
		{
			try
			{
				getLinShiAmMeter(req, resp);
			} catch (UnknownHostException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 临时调剂
		if ("linShiTiaoJi".equals(method))
			try
			{
				try
				{
					linShiTiaoJi(req, resp);
				} catch (NumberFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnknownHostException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		// 统一月补
		if ("tongYiYueBu".equals(method))
			try
			{
				try
				{
					try
					{
						try
						{
							tongYiYueBu(req, resp);
						} catch (ParseException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ParserConfigurationException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (UnknownHostException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if ("qiTaCaoZuo".equals(method))
			try
			{
				try
				{
					try
					{
						try
						{
							qiTaCaoZuo(req, resp);
						} catch (NumberFormatException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ParserConfigurationException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (UnknownHostException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void getPriceInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		BatchOprDao dao = new BatchOprDao();
		JSONArray json = dao.getPriceInfo();

		resp.setContentType("application/x-json");
		PrintWriter pw = resp.getWriter();
		// System.out.println(json.toString());
		pw.println(json.toString());
		pw.flush();
		pw.close();
	}

	// 开户选中
	private void getKaiHuAmMeter2(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException
	{
		String amMeterId = null;
		String[] meterId = req.getParameterValues("meterId[]");
		List<String> list = new ArrayList<String>();
		BatchOprOtherDao batchOprOtherDao = new BatchOprOtherDao();
		for (int i = 0; i < meterId.length; i++)
		{
			amMeterId = batchOprOtherDao.kaiHuAmMeter(meterId[i]);
			if (amMeterId != null)
			{
				list.add(amMeterId);
			}
		}

		JSONObject json = new JSONObject();
		json.put("totalCount", list.size());
		JSONArray jo = JSONArray.fromObject(list);
		json.put("jo", jo);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 根据条件得到电表信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private void getSendTotal(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException
	{
		String userID = req.getSession().getAttribute("userId").toString();// 获取用户ID
		String type = req.getParameter("comand");
		String commandStyle = req.getParameter("commandStyle");
		String price = req.getParameter("price");
		String AmMeterStatus = req.getParameter("AmMeterStatus");
		BatchOprOtherDao batchOprOtherDao = new BatchOprOtherDao();
		String result = "";
		if ("1".equals(type))// 下发全部
		{
			String SelectTreeFlag = req.getParameter("SelectTreeFlag");
			String SelectTreeID = req.getParameter("SelectTreeID");

			String SelectFloorID = null;
			if ("3".equals(SelectTreeFlag))
			{
				SelectFloorID = req.getParameter("SelectFloorID");
			}

			result = batchOprOtherDao.SerGetExecList(type, SelectTreeFlag, SelectTreeID, SelectFloorID, null, commandStyle, userID, price,
					AmMeterStatus);
			// System.out.println("result:" + result);
		} else if ("2".equals(type))// 下发选中
		{
			String[] amMeterarray = req.getParameterValues("meterId[]");
			result = batchOprOtherDao.SerGetExecList(type, "", "", "", amMeterarray, commandStyle, userID, price, AmMeterStatus);

		}
		// System.out.println(result);
		JSONObject json = new JSONObject();
		json.put("result", result);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 根据条件得到电表信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private void getYueBuAmMeter(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException
	{
		String userID = req.getSession().getAttribute("userId").toString();// 获取用户ID
		// String userLoginName = (String)
		// request.getSession().getAttribute("userName");//获取用户名
		String comand = req.getParameter("comand");
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");

		String SelectFloorID = null;
		if ("3".equals(SelectTreeFlag))
		{
			SelectFloorID = req.getParameter("SelectFloorID");
		}

		String commandStyle = req.getParameter("commandStyle");// 哪种下发类型（正常下发，临时下发，校正下发）
		String price = req.getParameter("price");
		String AmMeterStatus = req.getParameter("AmMeterStatus");
		String saleInfoNum = "";
		BatchOprDao batchOprDao = new BatchOprDao();
		if ("1".equals(comand))
		{

			saleInfoNum = batchOprDao.SerAddYubu(comand, SelectTreeFlag, SelectTreeID, SelectFloorID, null, commandStyle, "2", userID, price,
					AmMeterStatus);
		} else
		{
			String[] amMeterarray = req.getParameterValues("meterId[]");
			saleInfoNum = batchOprDao.SerAddYubu(comand, "", "", "", amMeterarray, commandStyle, "2", userID, price, AmMeterStatus);
		}

		JSONObject json = new JSONObject();
		json.put("saleInfoNum", saleInfoNum);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 临时调剂
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private void getLinShiAmMeter(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException
	{
		String userID = req.getSession().getAttribute("userId").toString();// 获取用户ID
		// String userLoginName = (String)
		// request.getSession().getAttribute("userName");//获取用户名
		String comand = req.getParameter("comand");
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");

		String SelectFloorID = null;
		if ("3".equals(SelectTreeFlag))
		{
			SelectFloorID = req.getParameter("SelectFloorID");
		}
		String commandStyle = req.getParameter("commandStyle");// 调剂量
		String price = req.getParameter("price");
		String AmMeterStatus = req.getParameter("AmMeterStatus");
		String saleInfoNum = "";
		BatchOprDao batchOprDao = new BatchOprDao();
		if ("1".equals(comand))
		{

			saleInfoNum = batchOprDao.SerAddYubu(comand, SelectTreeFlag, SelectTreeID, SelectFloorID, null, commandStyle, "3", userID, price,
					AmMeterStatus);
		} else
		{
			String[] amMeterarray = req.getParameterValues("linShiMeterId[]");

			saleInfoNum = batchOprDao.SerAddYubu(comand, "", "", "", amMeterarray, commandStyle, "3", userID, price, AmMeterStatus);
		}
		// System.out.println("saleInfoNum:" + saleInfoNum);
		JSONObject json = new JSONObject();
		json.put("saleInfoNum", saleInfoNum);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 临时调剂
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	private void linShiTiaoJi(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException, NumberFormatException, ParseException
	{// 方法内容应该和月补一样

		// 是否是下发全部
		// String comand=req.getParameter("comand");
		// String tjLiang=req.getParameter("tjLiang");
		// System.out.println(comand+"   "+tjLiang);

		String amMeter_Name = "";
		String saleInfo[] = null;
		String saleInfoNum = "";
		String amMeterMes = "";
		// 电表Id
		String amMeterId = "";
		// 下发电量
		String gross = "";
		String money = "";
		// 回复的XML
		String xmlReply = "";
		boolean error = true;
		int QSYValue = 0;// 购电前剩余金额
		int HSYValue = 0;// 购电后剩余金额
		int Times = 0;// 购电次数
		String TheSaleInfoNum = "";// 购电单号
		SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 下一个要执行的条数
		// int iArray=Integer.parseInt(req.getParameter("i"))+1;
		// 错误个数
		// int error=Integer.parseInt(req.getParameter("error"));

		// System.out.println("iArray:"+iArray+"   error:"+error);

		CMMsendCommand cmMsendCommand = new CMMsendCommand();
		XMLCoder xmlCoder = new XMLCoder();
		SendingAPCode sendXml = new SendingAPCode();
		BatchOprDao batchOprDao = new BatchOprDao();
		BatchOprModel batchOprModel = new BatchOprModel();

		String userID = "1";
		HttpSession session = req.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		saleInfoNum = req.getParameter("yueBuSalNum");
		// saleInfoNum=batchOprDao.SerAddYubu(meterMesArr,tjLiang,"3",userID);

		if (saleInfoNum != null)
		{
			amMeterMes = batchOprDao.getAmMeterMes(saleInfoNum);
			if (amMeterMes != null)
			{
				amMeterId = amMeterMes.split("/")[0];
				gross = amMeterMes.split("/")[1];
				// 售电单号
				TheSaleInfoNum = amMeterMes.split("/")[2];
				// 电表名称
				amMeter_Name = amMeterMes.split("/")[3];
				money = amMeterMes.split("/")[4];
				// System.out.println(amMeterId + " mmmkkk " + gross + "  " +
				// TheSaleInfoNum);
				// 读电量
				xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
				// 电表协议
				String protocol = cmMsendCommand.getMeterProtocol();
				try
				{
					if (xmlCoder != null)
					{
						xmlReply = sendXml.sendXMLToServer(xmlCoder);
						if (xmlReply != null)
						{
							if (Integer.parseInt(protocol) == 1)
							{// 97
								batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "1", amMeterId, TheSaleInfoNum, 0, "", "");
								System.out.println("xmlReply:" + xmlReply);

								if ("0".equals(batchOprModel.getIsError()))
								{

									// 下发电量
									xmlCoder = cmMsendCommand
											.sendTGross(Integer.parseInt(amMeterId), gross, batchOprModel.getBuyTimes() + "", userID);
									if (xmlCoder != null)
									{
										xmlReply = sendXml.sendXMLToServer(xmlCoder);
										if (xmlReply != null)
										{
											batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "2", amMeterId, TheSaleInfoNum,
													batchOprModel.getQSYValue(), batchOprModel.getBuyTimes() + "", batchOprModel.getHtmlShow());
											System.out.println("xmlReply:" + xmlReply);
											if ("0".equals(batchOprModel.getIsError()))
											{
												// 再次读电量
												xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
												if (xmlCoder != null)
												{
													xmlReply = sendXml.sendXMLToServer(xmlCoder);
													if (xmlReply != null)
													{
														batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "3", amMeterId, TheSaleInfoNum,
																batchOprModel.getQSYValue(), "", batchOprModel.getHtmlShow());
														System.out.println("xmlReply:" + xmlReply);
														if ("1".equals(batchOprModel.getIsError()))
														{
															error = false;
														}
													} else
													{
														error = false;
													}
												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}
									} else
									{
										error = false;
									}
								} else
								{
									error = false;
								}

							} else
							{// 07
								// 上一条收到的回复是07协议的00900200
								NodeList nodeList = getNodeList(xmlReply, "commandback");
								Element commandBack = (Element) nodeList.item(0);
								String err = commandBack.getAttribute("error");
								if (!err.equals("1"))// 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									/**
									 * 获得07协议的剩余金额
									 */
									String SYMoney_07 = resultElement.getAttribute("SYMoney");
									batchOprModel.setHtmlShow("剩余金额：" + SYMoney_07 + "元");

									xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "07010202");// 读充值次数
									if (xmlCoder != null)
									{
										// 上一条收到的回复是07协议的07010202
										xmlReply = sendXml.sendXMLToServer(xmlCoder);
										System.out.println("xmlReply:" + xmlReply);

										nodeList = getNodeList(xmlReply, "commandback");
										commandBack = (Element) nodeList.item(0);
										err = commandBack.getAttribute("error");
										if (!err.equals("1"))// 成功
										{
											result = commandBack.getElementsByTagName("result");
											resultElement = (Element) result.item(0);
											/**
											 * 获得07协议的抄读次数
											 */
											String BuyTimes = resultElement.getAttribute("BuyTimes");
											batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "，购电次数:" + BuyTimes + "次");

											String strsql = "update APSaleInfo set Times=" + BuyTimes + ",QSYValue=" + SYMoney_07
													+ ",Status=2,SendTime=TO_DATE('" + dateDF.format(new Date()) + "', 'yyyy-mm-dd hh24:mi:ss')"
													+ " where SaleInfoNum='" + saleInfoNum + "'";
											UpdateSql(strsql);

											// 发送下电量的协议
											xmlCoder = cmMsendCommand.sendTGross(Integer.parseInt(amMeterId), money, BuyTimes, userID);
											if (xmlCoder != null)
											{
												// 下发电量的回复
												xmlReply = sendXml.sendXMLToServer(xmlCoder);

												System.out.println("xmlReply:" + xmlReply);

												nodeList = getNodeList(xmlReply, "commandback");
												commandBack = (Element) nodeList.item(0);
												err = commandBack.getAttribute("error");

												if (!err.equals("1"))// 成功
												{
													batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "/下发成功");

													// 下发抄读后剩余金额
													xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
													xmlReply = sendXml.sendXMLToServer(xmlCoder);
													System.out.println("xmlReply:" + xmlReply);

													nodeList = getNodeList(xmlReply, "commandback");
													commandBack = (Element) nodeList.item(0);
													err = commandBack.getAttribute("error");
													if (!err.equals("1"))// 成功
													{
														result = commandBack.getElementsByTagName("result");
														resultElement = (Element) result.item(0);
														String SYMoney_07_hou = resultElement.getAttribute("SYMoney");
														String TotoleUsed = "";
														String readTime = "";
														DecimalFormat df = new DecimalFormat("0.00");

														batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "/" + SYMoney_07_hou + "元");

														strsql = "update APSaleInfo set HSYValue=" + SYMoney_07_hou + ",Status=1 where SaleInfoNum='"
																+ saleInfoNum + "'";
														UpdateSql(strsql);

														// 读总示数
														xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "00010000");
														xmlReply = sendXml.sendXMLToServer(xmlCoder);
														System.out.println("xmlReply:" + xmlReply);

														nodeList = getNodeList(xmlReply, "commandback");
														commandBack = (Element) nodeList.item(0);

														if (!err.equals("1"))// 成功
														{
															result = commandBack.getElementsByTagName("result");
															resultElement = (Element) result.item(0);
															readTime = resultElement.getAttribute("ValueTime");

															if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
															{
																TotoleUsed = resultElement.getAttribute("ZValueZY");
																TotoleUsed = df.format(df.parse(TotoleUsed));
															}
															float zValueZY = Float.parseFloat(TotoleUsed) * cmMsendCommand.getBeiLv();

															CMMeterDao dao = new CMMeterDao();
															dao.SerSaveData(Integer.parseInt(amMeterId), zValueZY, Float.parseFloat(SYMoney_07_hou),
																	0, readTime);

														} else
														{
															error = false;
														}

													} else
													{
														error = false;
													}

												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}
									} else
									{
										error = false;
									}

								} else
								{
									error = false;
								}
							}

						} else
						{
							error = false;
						}
					} else
					{
						error = false;
					}
				} catch (SocketTimeoutException e)
				{
					error = false;
				}
			} else
			{
				error = false;
			}

		} else
		{
			error = false;
		}

		// 将本条电表的执行结果返回给页面
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("amMeter_Name", amMeter_Name);
		json.put("gross", gross);

		json.put("htmlShow", batchOprModel.getHtmlShow());
		json.put("error", error);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 统一月补
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws ParseException
	 */
	private void tongYiYueBu(HttpServletRequest req, HttpServletResponse resp) throws NumberFormatException, SQLException, UnknownHostException,
			IOException, ParserConfigurationException, SAXException, ParseException
	{
		// 是否是下发全部
		// String comand=req.getParameter("comand");
		String saleInfo[] = null;
		String saleInfoNum = "";
		String amMeterMes = "";
		// 电表Id
		String amMeterId = "";
		// 下发电量
		String gross = "";
		String amMeter_Name = "";
		String money = "";
		String Status = "";
		// 回复的XML
		String xmlReply = "";
		boolean error = true;
		int QSYValue = 0;// 购电前剩余金额
		int HSYValue = 0;// 购电后剩余金额
		int Times = 0;// 购电次数
		String TheSaleInfoNum = "";// 购电单号
		SimpleDateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 下一个要执行的条数
		// int iArray=Integer.parseInt(req.getParameter("i"))+1;
		// 错误个数
		// int error=Integer.parseInt(req.getParameter("error"));

		CMMsendCommand cmMsendCommand = new CMMsendCommand();
		XMLCoder xmlCoder = new XMLCoder();
		SendingAPCode sendXml = new SendingAPCode();
		BatchOprDao batchOprDao = new BatchOprDao();
		BatchOprModel batchOprModel = new BatchOprModel();

		String userID = "1";
		HttpSession session = req.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		saleInfoNum = req.getParameter("yueBuSalNum");

		// boolean isCon=sendXml.creatCon();

		if (saleInfoNum != null)
		{
			amMeterMes = batchOprDao.getAmMeterMes(saleInfoNum);
			if (amMeterMes != null)
			{
				amMeterId = amMeterMes.split("/")[0];
				gross = amMeterMes.split("/")[1];
				// 售电单号
				TheSaleInfoNum = amMeterMes.split("/")[2];
				// 电表名称
				amMeter_Name = amMeterMes.split("/")[3];
				money = amMeterMes.split("/")[4];

				// 读电量
				xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
				// 该电表的协议
				String protocol = cmMsendCommand.getMeterProtocol();
				try
				{
					if (xmlCoder != null)
					{
						xmlReply = sendXml.sendXMLToServer(xmlCoder);
						System.out.println("xmlReply:" + xmlReply);
						if (xmlReply != null)
						{
							if (Integer.parseInt(protocol) == 1)// 97表
							{
								batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "1", amMeterId, TheSaleInfoNum, 0, "", "");
								if ("0".equals(batchOprModel.getIsError()))
								{
									// 下发电量
									xmlCoder = cmMsendCommand
											.sendTGross(Integer.parseInt(amMeterId), gross, batchOprModel.getBuyTimes() + "", userID);
									if (xmlCoder != null)
									{
										xmlReply = sendXml.sendXMLToServer(xmlCoder);
										if (xmlReply != null)
										{
											batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "2", amMeterId, TheSaleInfoNum,
													batchOprModel.getQSYValue(), batchOprModel.getBuyTimes() + "", batchOprModel.getHtmlShow());
											System.out.println("xmlReply:" + xmlReply);
											if ("0".equals(batchOprModel.getIsError()))
											{
												// 再次读电量
												xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
												if (xmlCoder != null)
												{
													xmlReply = sendXml.sendXMLToServer(xmlCoder);

													if (xmlReply != null)
													{
														batchOprModel = batchOprDao.parseXml(xmlCoder, xmlReply, "3", amMeterId, TheSaleInfoNum,
																batchOprModel.getQSYValue(), "", batchOprModel.getHtmlShow());
														System.out.println("xmlReply:" + xmlReply);
														if ("1".equals(batchOprModel.getIsError()))
														{
															error = false;
														}
													} else
													{
														error = false;
													}
												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}
									} else
									{
										error = false;
									}
								} else
								{
									error = false;
								}

							} else
							{
								// 上一条收到的回复是07协议的00900200
								NodeList nodeList = getNodeList(xmlReply, "commandback");
								Element commandBack = (Element) nodeList.item(0);
								String err = commandBack.getAttribute("error");
								if (!err.equals("1"))// 成功
								{
									NodeList result = commandBack.getElementsByTagName("result");
									Element resultElement = (Element) result.item(0);
									/**
									 * 获得07协议的剩余金额
									 */
									String SYMoney_07 = resultElement.getAttribute("SYMoney");
									batchOprModel.setHtmlShow("剩余金额：" + SYMoney_07 + "元");

									xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "07010202");// 读充值次数
									if (xmlCoder != null)
									{
										// 上一条收到的回复是07协议的07010202
										xmlReply = sendXml.sendXMLToServer(xmlCoder);
										System.out.println("xmlReply:" + xmlReply);

										nodeList = getNodeList(xmlReply, "commandback");
										commandBack = (Element) nodeList.item(0);
										err = commandBack.getAttribute("error");
										if (!err.equals("1"))// 成功
										{
											result = commandBack.getElementsByTagName("result");
											resultElement = (Element) result.item(0);
											/**
											 * 获得07协议的抄读次数
											 */
											String BuyTimes = resultElement.getAttribute("BuyTimes");
											batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "，购电次数:" + BuyTimes + "次");

											String strsql = "update APSaleInfo set Times=" + BuyTimes + ",QSYValue=" + SYMoney_07
													+ ",Status=2,SendTime=TO_DATE('" + dateDF.format(new Date()) + "', 'yyyy-mm-dd hh24:mi:ss')"
													+ " where SaleInfoNum='" + saleInfoNum + "'";
											UpdateSql(strsql);

											// 发送下电量的协议
											xmlCoder = cmMsendCommand.sendTGross(Integer.parseInt(amMeterId), money, BuyTimes, userID);
											if (xmlCoder != null)
											{
												// 下发电量的回复
												xmlReply = sendXml.sendXMLToServer(xmlCoder);

												System.out.println("xmlReply:" + xmlReply);

												nodeList = getNodeList(xmlReply, "commandback");
												commandBack = (Element) nodeList.item(0);
												err = commandBack.getAttribute("error");

												if (!err.equals("1"))// 成功
												{
													batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "/下发成功");

													// 下发抄读后剩余金额
													xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(amMeterId), userID);
													xmlReply = sendXml.sendXMLToServer(xmlCoder);
													System.out.println("xmlReply:" + xmlReply);

													nodeList = getNodeList(xmlReply, "commandback");
													commandBack = (Element) nodeList.item(0);
													err = commandBack.getAttribute("error");
													if (!err.equals("1"))// 成功
													{
														result = commandBack.getElementsByTagName("result");
														resultElement = (Element) result.item(0);
														String SYMoney_07_hou = resultElement.getAttribute("SYMoney");
														String TotoleUsed = "";
														String readTime = "";
														DecimalFormat df = new DecimalFormat("0.00");

														batchOprModel.setHtmlShow(batchOprModel.getHtmlShow() + "/" + SYMoney_07_hou + "元");

														strsql = "update APSaleInfo set HSYValue=" + SYMoney_07_hou + ",Status=1 where SaleInfoNum='"
																+ saleInfoNum + "'";
														UpdateSql(strsql);

														// 读总示数
														xmlCoder = cmMsendCommand.readMeter07(Integer.parseInt(amMeterId), userID, "00010000");
														xmlReply = sendXml.sendXMLToServer(xmlCoder);
														System.out.println("xmlReply:" + xmlReply);

														nodeList = getNodeList(xmlReply, "commandback");
														commandBack = (Element) nodeList.item(0);

														if (!err.equals("1"))// 成功
														{
															result = commandBack.getElementsByTagName("result");
															resultElement = (Element) result.item(0);
															readTime = resultElement.getAttribute("ValueTime");

															if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
															{
																TotoleUsed = resultElement.getAttribute("ZValueZY");
																TotoleUsed = df.format(df.parse(TotoleUsed));
															}
															float zValueZY = Float.parseFloat(TotoleUsed) * cmMsendCommand.getBeiLv();

															CMMeterDao dao = new CMMeterDao();
															dao.SerSaveData(Integer.parseInt(amMeterId), zValueZY, Float.parseFloat(SYMoney_07_hou),
																	0, readTime);

														} else
														{
															error = false;
														}

													} else
													{
														error = false;
													}

												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}
									} else
									{
										error = false;
									}

								} else
								{
									error = false;
								}
							}

						} else
						{
							error = false;
						}
					} else
					{
						error = false;
					}
				} catch (SocketTimeoutException e)
				{
					error = false;
				}

			} else
			{
				error = false;
			}

		} else
		{
			error = false;
		}

		// 将本条电表的执行结果返回给页面
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();

		json.put("htmlShow", batchOprModel.getHtmlShow());
		json.put("gross", gross);
		json.put("error", error);

		json.put("amMeter_Name", amMeter_Name);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 对数据库执行更新操作
	 * 
	 * @param strsql
	 */
	public void UpdateSql(String strsql)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
	}

	/**
	 * 更新最后通讯时间
	 * 
	 * @param ammeterID
	 * @param dateStr
	 * @return
	 */
	private boolean updateLastTimeForAmMeter(int ammeterID, String dateStr)
	{
		boolean b = false;
		String sql = "UPDATE AMMETER set LASTTIME='" + dateStr + "' WHERE AMMETER_ID=" + ammeterID;

		Connection conn = null;
		PreparedStatement ps = null;
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

	/**
	 * 其他操作
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	private void qiTaCaoZuo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, UnknownHostException, IOException,
			ParserConfigurationException, SAXException, NumberFormatException, ParseException
	{
		String commandStyle = req.getParameter("commandStyle");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String paraMes = "";
		String paraMesArr[] = null;
		String userID = "1";
		int amMeterId = 0;
		String replyXml = "";
		String saleInfo = "";
		String bufaError = "0";
		String result = "";
		String amMeterName = "";
		// 电表Id
		String ammeterId = "";
		// 补发电量
		String gross = "";
		// 补发金额
		String money = "";
		// 是否失败
		boolean error = true;
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
//		String userrealname=(String) req.getSession().getAttribute("realName");
		BatchOprOtherDao batchOprOtherDao = new BatchOprOtherDao();
		CMMsendCommand cmMsendCommand = new CMMsendCommand();
		XMLCoder xmlCoder = new XMLCoder();
		SendingAPCode sendXml = new SendingAPCode();
		BatchOprModel batchOprModel = new BatchOprModel();

		saleInfo = req.getParameter("amMeter");
		String html = "";
		try
		{
			// 补发未下发的购电记录
			if ("bufa".equals(commandStyle))
			{
				//String userLoginName = (String) req.getSession().getAttribute("userName");
				OperationLogInterface log = new OperationLogImplement();
				// 写入日志
				log.writeLog(userLoginName, "批量设置", "补发未下发购电记录,售电单号：" + saleInfo);

				result = batchOprOtherDao.SerGetCommandList(commandStyle, userID, saleInfo);

				if (result != null)
				{
					ammeterId = result.split("/")[0];
					gross = result.split("/")[1];
					amMeterName = result.split("/")[2];
					money = result.split("/")[3];

					// 读电量
					xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(ammeterId), userLoginName);
					if (xmlCoder != null)
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						System.out.println("xmlReply:" + replyXml);
						if ("1".equals(cmMsendCommand.getMeterProtocol()))// 97表的协议处理
						{
							if (replyXml != null)
							{
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, ammeterId, saleInfo, 0, "", "", "", "",
										"", "1", "");
								if ("0".equals(batchOprModel.getIsError()))
								{
									if ("1".equals(batchOprModel.getBackValue()))
									{

										// 下发电量
										html = batchOprModel.getHtmlShow() + "<br/><strong>下发购电量:</strong><span style='color:green'>" + gross
												+ "度</span>";
										batchOprModel.setHtmlShow(html);

										xmlCoder = cmMsendCommand.sendTGross(Integer.parseInt(ammeterId), gross, batchOprModel.getBuyTimes() + "",
												userLoginName);
										if (xmlCoder != null)
										{
											replyXml = sendXml.sendXMLToServer(xmlCoder);
											if (replyXml != null)
											{
												System.out.println("xmlReply:" + replyXml);
												batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, ammeterId, saleInfo,
														batchOprModel.getQSYValue(), batchOprModel.getBuyTimes() + "", "", "", "", "", "2",
														batchOprModel.getHtmlShow());
												if ("0".equals(batchOprModel.getIsError()))
												{

													// 再次读电量
													xmlCoder = cmMsendCommand.readMeter(Integer.parseInt(ammeterId), userLoginName);
													if (xmlCoder != null)
													{
														replyXml = sendXml.sendXMLToServer(xmlCoder);
														if (replyXml != null)
														{
															System.out.println("xmlReply:" + replyXml);
															batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, ammeterId,
																	saleInfo, batchOprModel.getQSYValue(), "", "", "", "", "", "3",
																	batchOprModel.getHtmlShow());
															if ("1".equals(batchOprModel.getIsError()))
															{
																bufaError = "1";
															}
														} else
														{
															bufaError = "1";
														}
													} else
													{
														bufaError = "1";
													}
												} else
												{
													bufaError = "1";
												}
											} else
											{
												bufaError = "1";
											}
										} else
										{
											bufaError = "1";
										}

									} else
									{
										html = batchOprModel.getHtmlShow() + "<br/><span style='color:red'>该记录已经下发成功，系统将自动修改售电单记录！</span><br/>";
									}
								} else
								{
									bufaError = "1";
								}
							} else
							{
								bufaError = "1";
							}
						} else
						// 07表补发的协议
						{
							bufa07xieyi(req, resp, replyXml, commandStyle, amMeterName, Integer.parseInt(ammeterId), saleInfo, money);
							return;
						}

					} else
					{
						bufaError = "1";
					}
				} else
				{
					bufaError = "1";
				}
				if ("1".equals(bufaError))
				{
					error = false;
				}

				// 更改最后通讯时间
				if (error == true)
				{
					Date nowDate = new Date();
					String dateStr = sdf.format(nowDate);
					boolean b = updateLastTimeForAmMeter(Integer.parseInt(ammeterId), dateStr);
					if (!b)
					{
						error = false;
						batchOprModel.setHtmlShow("更新最后通讯时间失败");
					}
				}

				// 将本条电表的执行结果返回给页面
				resp.setContentType("text/html;charset=utf-8");
				JSONObject json = new JSONObject();
				json.put("commandStyle", commandStyle);
				json.put("amMeterName", amMeterName);
				json.put("error", error);
				json.put("htmlShow", batchOprModel.getHtmlShow());
				json.put("backValue", batchOprModel.getBackValue());
				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			} else
			{

				ammeterId = saleInfo;
				amMeterId = Integer.parseInt(saleInfo);

				amMeterName = batchOprOtherDao.getAmMeterName(amMeterId + "");

				// 取消预付费
				if ("yufufei00".equals(commandStyle))
				{
					// xmlCoder = cmMsendCommand.xiaoHuCommand(amMeterId,
					// userID,0);
					// if (xmlCoder == null)
					// {
					// batchOprModel.setHtmlShow("执行失败");
					// error = false;
					// } else
					// {
					// replyXml = sendXml.sendXMLToServer(xmlCoder);
					// if (replyXml != null)
					// {
					// System.out.println("replyXml:" + replyXml);
					// batchOprModel = batchOprOtherDao.parseXml(replyXml,
					// commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
					// "1", "");
					// if ("1".equals(batchOprModel.getIsError()))
					// {
					// batchOprModel.setHtmlShow("执行失败");
					// error = false;
					// }
					// } else
					// {
					// batchOprModel.setHtmlShow("执行失败");
					// error = false;
					// }
					// }
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "取消预付费," + info);

					xmlCoder = cmMsendCommand.xiaoHuCommand(amMeterId, userLoginName, 2);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				//设置过载断电和恶性负载断电恢复时间
				else if ("setGZHFSJ".equals(commandStyle)||"setEXFZHFSJ".equals(commandStyle))
				{
					int times = (req.getParameter("times")!=null&&!req.getParameter("times").equals(""))?Integer.parseInt(req.getParameter("times")):5;
					String ss = "设置过载断电恢复时间,";
					int f = 0;
					if("setEXFZHFSJ".equals(commandStyle)){
						ss = "设置恶性负载断电恢复时间,";
						f = 1;
					}					
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", ss + info);

					xmlCoder = cmMsendCommand.setHuiFuTimeCommand(amMeterId, times, userID, f);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 启动预付费
				else if ("yufufei11".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "启动预付费," + info);

					xmlCoder = cmMsendCommand.kaiHuCommand(amMeterId, userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 取消预付费未开户表计
				else if ("yufufei0".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "取消预付费未开户表计," + info);

					xmlCoder = cmMsendCommand.xiaoHuCommand(amMeterId, userLoginName, 2);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "", "1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 启动预付费未开户表计
				else if ("yufufei1".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "启动预付费未开户表计," + info);

					xmlCoder = cmMsendCommand.kaiHuCommand(amMeterId, userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "", "1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 分闸报警表计
				else if ("off1".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "分闸报警表计," + info);

					xmlCoder = cmMsendCommand.fenZhaCommand(amMeterId, "C028", userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							System.out.println("replyXml:" + replyXml);
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 照明回路分闸
				else if ("off2".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "照明回路分闸," + info);

					xmlCoder = cmMsendCommand.fenZhaCommand(amMeterId, "C027", userLoginName);

					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 空调回路分闸
				else if ("off".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "空调回路分闸," + info);

					xmlCoder = cmMsendCommand.fenZhaCommand(amMeterId, "C028", userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 照明回路合闸
				else if ("on2".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "照明回路合闸," + info);

					xmlCoder = cmMsendCommand.heZhaCommand(amMeterId, "C027", userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 空调回路合闸
				else if ("on".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "空调回路合闸," + info);

					xmlCoder = cmMsendCommand.heZhaCommand(amMeterId, "C028", userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 抄读表计状态字
				else if ("readState".equals(commandStyle))
				{
					/*
					 * String userLoginName = (String)
					 * req.getSession().getAttribute( "userName");
					 * OperationLogInterface log = new OperationLogImplement();
					 * // 写入日志 log.writeLog(userLoginName, "批量设置", "抄读表计状态字");
					 */

					xmlCoder = cmMsendCommand.readMeterState(amMeterId, userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", "", "", "", "",
									"1", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							} else
							// 执行成功刷新界面 李嵘20150720
							{
								DecimalFormat df = new DecimalFormat("0.00");
								NodeList nodeList = getNodeList(replyXml, "commandback");
								Element commandBack = (Element) nodeList.item(0);
								String err = commandBack.getAttribute("error");
								if (!err.equals("1"))// 成功
								{
									NodeList resultNodelist = commandBack.getElementsByTagName("result");

									Element resultElement = (Element) resultNodelist.item(0);
									String meterAddress = resultElement.getAttribute("Meter_Address");
									String readTime = resultElement.getAttribute("ValueTime");
									String Flag = resultElement.getAttribute("Flag");
									String lightflag = "0", airconflag = "0";// 默认合闸
									lightflag = Flag.substring(0, 1);// 照明回路
									airconflag = Flag.substring(1, 2);// 空调回路
									if (lightflag.equals("1"))
									{
										lightflag = "分闸";
									} else
									{
										lightflag = "合闸";
									}
									if (airconflag.equals("1"))
									{
										airconflag = "分闸";
									} else
									{
										airconflag = "合闸";
									}
									batchOprModel.setHtmlShow("照明回路:" + lightflag + "，空调回路:" + airconflag);
								} else
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								}
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 抄表
				else if ("read".equals(commandStyle))
				{
					/*
					 * String userLoginName = (String)
					 * req.getSession().getAttribute( "userName");
					 * OperationLogInterface log = new OperationLogImplement();
					 * // 写入日志 log.writeLog(userLoginName, "批量设置", "抄表");
					 */

					try
					{
						xmlCoder = cmMsendCommand.readMeter(amMeterId, userLoginName);
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							System.out.println("replyXml:" + replyXml);

							if (replyXml != null)
							{
								if ("1".equals(cmMsendCommand.getMeterProtocol()))
								{// 97表
									batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "",
											commandStyle, userID, "", "", "", "");
									if ("1".equals(batchOprModel.getIsError()))
									{
										batchOprModel.setHtmlShow("执行失败");
										error = false;
									}
								} else
								{
									DecimalFormat df = new DecimalFormat("0.00");
									/**
									 * 读取SYMoney
									 */
									NodeList nodeList = getNodeList(replyXml, "commandback");
									Element commandBack = (Element) nodeList.item(0);
									String err = commandBack.getAttribute("error");
									if (!err.equals("1"))// 成功
									{
										NodeList resultNodelist = commandBack.getElementsByTagName("result");

										Element resultElement = (Element) resultNodelist.item(0);
										String meterAddress = resultElement.getAttribute("Meter_Address");
										String readTime = resultElement.getAttribute("ValueTime");
										String protocol = "30";
										String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterId);
										if ("0".equals(AmMeter_CostType))
										{// 07非费控 抄总示数
											String TotoleUsed = resultElement.getAttribute("ZValueZY");
											String sql = "select nvl(to_char(beilv),1)beilv FROM AMMETER where AMMETER_ID=" + amMeterId;
											Connection conn = null;
											PreparedStatement ps = null;
											ResultSet rs = null;
											String beiLv = "";
											try
											{
												conn = ConnDB.getConnection();
												ps = conn.prepareStatement(sql);
												rs = ps.executeQuery();
												if (rs.next())
													beiLv = rs.getString("beilv");
											} catch (SQLException e)
											{
												e.printStackTrace();
											} finally
											{
												ConnDB.release(conn, ps, rs);
											}

											sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
													+ amMeterId;

											Connection conn1 = null;
											PreparedStatement ps1 = null;
											ResultSet rs1 = null;
											try
											{
												conn1 = ConnDB.getConnection();
												ps1 = conn1.prepareStatement(sql);
												rs1 = ps1.executeQuery();
												// System.out.print(sql);
												if (rs1.next())// 李嵘20150905
												{
													String Gather_Address = rs1.getString("Gather_Address");
													String AmMeter_485Address = rs1.getString("AmMeter_485Address");
													float ZValue = Float.parseFloat(TotoleUsed) * Float.parseFloat(beiLv);
													if (!(ZValue < 0))
													{
														// 存储总示数
														AutoUpDataSaver.dataSave(Gather_Address, "1", AmMeter_485Address, "0C0110", readTime,
																String.valueOf(ZValue));
													}
													batchOprModel.setHtmlShow("当前电量:" + df.format(ZValue) + "度");
												}

											} catch (Exception e)
											{
												e.printStackTrace();
											} finally
											{
												ConnDB.release(conn1, ps1, rs1);
											}

										} else
										{// 07费控
											String SYMoney = resultElement.getAttribute("SYMoney");
											String SYValue = df.format(df.parse(SYMoney));
											float HTZValue = 0;
											/**
											 * 读取ZValueZY
											 */
											xmlCoder = cmMsendCommand.readMeter07(amMeterId, userLoginName, "00010000");
											if (xmlCoder != null)
											{
												replyXml = sendXml.sendXMLToServer(xmlCoder);

												System.out.println("收到的报文的回复是：" + replyXml);

												if (replyXml != null)
												{
													nodeList = getNodeList(replyXml, "commandback");

													commandBack = (Element) nodeList.item(0);

													err = commandBack.getAttribute("error");
													// 失败
													if (err.equals("1"))
													{
														batchOprModel.setHtmlShow("执行失败");
														error = false;
													} else if (err.equals("0")) // 成功
													{
														resultNodelist = commandBack.getElementsByTagName("result");

														resultElement = (Element) resultNodelist.item(0);
														readTime = resultElement.getAttribute("ValueTime");

														String TotoleUsed = resultElement.getAttribute("ZValueZY");

														xmlCoder = cmMsendCommand.readMeter07(amMeterId, userLoginName, "00900201");// 07当前透支金额
														if (xmlCoder != null)
														{
															try
															{
																String str = sendXml.sendXMLToServer(xmlCoder);
																System.out.println("收到的报文的回复是：" + str);
																err = getAttrValue(str, "commandback", "error");
																if ("0".equals(err))
																{
																	String TZValue = getAttrValue(str, "result", "TZMoney");
																	TZValue = df.format(df.parse(TZValue));
																	HTZValue = Float.parseFloat(TZValue);
																} else
																{
																	batchOprModel.setHtmlShow("执行失败");
																	error = false;
																}
															} catch (SocketTimeoutException e)
															{
																batchOprModel.setHtmlShow("执行失败");
																error = false;
															}
														}

														/**
														 * 存储数据库
														 */
														String sql = "select nvl(to_char(beilv),1)beilv FROM AMMETER where AMMETER_ID=" + amMeterId;
														Connection conn = null;
														PreparedStatement ps = null;
														ResultSet rs = null;
														String beiLv = "";
														try
														{
															conn = ConnDB.getConnection();
															ps = conn.prepareStatement(sql);
															rs = ps.executeQuery();
															if (rs.next())
																beiLv = rs.getString("beilv");
														} catch (SQLException e)
														{
															e.printStackTrace();
														} finally
														{
															ConnDB.release(conn, ps, rs);
														}

														// PreparedStatement ps
														// =
														// ConnDB.getConnection().prepareStatement(sql);
														// ResultSet rs =
														// ps.executeQuery();
														//
														// if (rs.next())
														// beiLv =
														// rs.getString("beilv");
														//
														// if (rs != null)
														// {
														// rs.close();
														// }
														//
														// if (ps != null)
														// {
														// ps.close();
														// }

														CMMeterDao dao = new CMMeterDao();
														HTZValue = HTZValue * Float.parseFloat(beiLv);
														float zValueZY = Float.parseFloat(TotoleUsed) * Float.parseFloat(beiLv);
														float sYValue = Float.parseFloat(SYValue) * Float.parseFloat(beiLv);
														dao.SerSaveData(amMeterId, zValueZY, sYValue, HTZValue, readTime);

														batchOprModel.setHtmlShow("剩余金额:" + SYValue + "元");
														/**
														 * 读取BuyTimes
														 */
														/*
														 * xmlCoder =
														 * cmMsendCommand.
														 * readMeter07
														 * (amMeterId, userID,
														 * "07010202"); if
														 * (xmlCoder != null) {
														 * replyXml =
														 * sendXml.sendXMLToServer
														 * (xmlCoder);
														 * 
														 * System.out.println(
														 * "收到的报文的回复是：" +
														 * replyXml);
														 * 
														 * if (replyXml != null)
														 * { nodeList =
														 * getNodeList(replyXml,
														 * "commandback");
														 * 
														 * commandBack =
														 * (Element)
														 * nodeList.item(0);
														 * 
														 * err =
														 * commandBack.getAttribute
														 * ("error"); // 失败 if
														 * (err.equals("1")) {
														 * batchOprModel
														 * .setHtmlShow("执行失败");
														 * error = false; } else
														 * if (err.equals("0"))
														 * // 成功 {
														 * resultNodelist =
														 * commandBack
														 * .getElementsByTagName
														 * ("result");
														 * 
														 * resultElement =
														 * (Element)
														 * resultNodelist
														 * .item(0);
														 * 
														 * String BuyTimes =
														 * resultElement
														 * .getAttribute
														 * ("BuyTimes"); }
														 * 
														 * } else {
														 * batchOprModel
														 * .setHtmlShow
														 * ("执行失败"); error =
														 * false; } } else {
														 * batchOprModel
														 * .setHtmlShow
														 * ("执行失败"); error =
														 * false; }
														 */
													}

												} else
												{
													batchOprModel.setHtmlShow("执行失败");
													error = false;
												}
											} else
											{
												batchOprModel.setHtmlShow("执行失败");
												error = false;
											}
										}

									} else
									{
										batchOprModel.setHtmlShow("执行失败");
										error = false;
									}
								}

							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					} catch (Exception e)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					}

				}
				// 开户
				else if ("kaihu".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "开户," + info);

					xmlCoder = cmMsendCommand.kaiHuCommand(amMeterId, userLoginName);
					if (xmlCoder == null)
					{
						batchOprModel.setHtmlShow("执行失败");
						error = false;
					} else
					{
						replyXml = sendXml.sendXMLToServer(xmlCoder);
						if (replyXml != null)
						{
							System.out.println("replyXml:" + replyXml);
							batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", "", 0, "", commandStyle,
									userID, "", "", "", "");
							if ("1".equals(batchOprModel.getIsError()))
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						} else
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						}
					}

				}
				// 销户
				else if ("xiaohu".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "销户," + info);

					try
					{
						// 读销户前剩余电量
						xmlCoder = cmMsendCommand.readMeter(amMeterId, userLoginName);
						if (xmlCoder != null)
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("xmlReply:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "",
										userID, "", "", "1", "");
								if ("0".equals(batchOprModel.getIsError()))
								{
									if ("30".equals(batchOprModel.getMeterPotocol()))
									{// 07 不退电 直接销户
										// 读总示数
										String ZValueZY = "";
										xmlCoder = cmMsendCommand.readMeter07(amMeterId, userLoginName, "00010000");
										if (xmlCoder != null)
										{
											replyXml = sendXml.sendXMLToServer(xmlCoder);

											System.out.println("xmlReply:" + replyXml);

											if (replyXml != null)
											{
												NodeList nodeList = getNodeList(replyXml, "commandback");

												Element commandBack = (Element) nodeList.item(0);

												String err = commandBack.getAttribute("error");
												if (err.equals("0")) // 成功
												{
													NodeList resultNodelist = commandBack.getElementsByTagName("result");

													Element resultElement = (Element) resultNodelist.item(0);

													ZValueZY = resultElement.getAttribute("ZValueZY");
												}

											}
										}

										CMMeterDao dao = new CMMeterDao();
										String xiaohuID = dao.SerSaveXiaoHuTuiDian(amMeterId, Double.parseDouble(ZValueZY) * batchOprModel.getBeiLv()
												+ "", batchOprModel.getSYValue() + "", Integer.parseInt(userID));

										// 销户
										xmlCoder = cmMsendCommand.xiaoHuCommand(amMeterId, userLoginName, 0);
										if (xmlCoder != null)
										{
											replyXml = sendXml.sendXMLToServer(xmlCoder);
											if (replyXml != null)
											{
												System.out.println("replyXml:" + replyXml);
												batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo,
														0, "", "", "", "", "", "3", batchOprModel.getHtmlShow());
												if ("0".equals(batchOprModel.getIsError()))
												{
													dao.SerSaveXiaohu2(amMeterId, xiaohuID);

													// 存储一条0的剩余记录 因为 07表销户后没有抄表
													String sql = "select  AmMeter_485Address,(select Gather_Address from Gather b where a.Gather_ID=b.Gather_ID)Gather_Address From AmMeter a where AmMeter_ID="
															+ amMeterId;

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
															String Gather_Address = rs.getString("Gather_Address");
															String AmMeter_485Address = rs.getString("AmMeter_485Address");
															SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
															// 写剩余电量
															AutoUpDataSaver.dataSavePurchase(Gather_Address, AmMeter_485Address,
																	df.format(new Date()), "0", "0", "0", "0", 0);

														}
													} catch (SQLException e)
													{
														e.printStackTrace();
													} finally
													{
														ConnDB.release(conn, ps, rs);
													}

													// 读销户后剩余电量
													/*
													 * xmlCoder =
													 * cmMsendCommand.
													 * readMeter(amMeterId,
													 * userID); if (xmlCoder !=
													 * null) { replyXml =
													 * sendXml
													 * .sendXMLToServer(xmlCoder
													 * ); System.out.println(
													 * "xmlReply:" + replyXml);
													 * if (replyXml != null) {
													 * batchOprModel =
													 * batchOprOtherDao
													 * .parseXml(replyXml,
													 * commandStyle, amMeterId +
													 * "", saleInfo, 0, "", "",
													 * "", "", "", "4",
													 * batchOprModel
													 * .getHtmlShow()); if
													 * ("1".equals
													 * (batchOprModel.
													 * getIsError())) { error =
													 * false; } } else { error =
													 * false; } } else { error =
													 * false; }
													 */
												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}

									} else
									{
										CMMeterDao dao = new CMMeterDao();
										String xiaohuID = dao.SerSaveXiaoHuTuiDian(amMeterId, batchOprModel.getZValue() + "",
												batchOprModel.getSYValue() + "", Integer.parseInt(userID));
										// 销户退电
										xmlCoder = cmMsendCommand.xiaoHuTuiDian(amMeterId, batchOprModel.getBuyTimes() + "", userLoginName + "");
										if (xmlCoder != null)
										{
											replyXml = sendXml.sendXMLToServer(xmlCoder);
											if (replyXml != null)
											{
												System.out.println("replyXml:" + replyXml);
												batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo,
														0, "", "", "", "", "", "2", batchOprModel.getHtmlShow());
												if ("0".equals(batchOprModel.getIsError()))
												{
													// 销户
													xmlCoder = cmMsendCommand.xiaoHuCommand(amMeterId, userLoginName, 0);
													if (xmlCoder != null)
													{
														replyXml = sendXml.sendXMLToServer(xmlCoder);
														if (replyXml != null)
														{
															System.out.println("replyXml:" + replyXml);
															batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId
																	+ "", saleInfo, 0, "", "", "", "", "", "3", batchOprModel.getHtmlShow());
															if ("0".equals(batchOprModel.getIsError()))
															{
																dao.SerSaveXiaohu2(amMeterId, xiaohuID);
																// 读销户后剩余电量
																xmlCoder = cmMsendCommand.readMeter(amMeterId, userLoginName);
																if (xmlCoder != null)
																{
																	replyXml = sendXml.sendXMLToServer(xmlCoder);
																	System.out.println("xmlReply:" + replyXml);
																	if (replyXml != null)
																	{
																		batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle,
																				amMeterId + "", saleInfo, 0, "", "", "", "", "", "4",
																				batchOprModel.getHtmlShow());
																		if ("1".equals(batchOprModel.getIsError()))
																		{
																			error = false;
																		}
																	} else
																	{
																		error = false;
																	}
																} else
																{
																	error = false;
																}
															} else
															{
																error = false;
															}
														} else
														{
															error = false;
														}
													} else
													{
														error = false;
													}
												} else
												{
													error = false;
												}
											} else
											{
												error = false;
											}
										} else
										{
											error = false;
										}

									}
								} else
								{
									error = false;
								}
							} else
							{
								error = false;
							}
						} else
						{
							error = false;
						}
					} catch (Exception e)
					{
						e.printStackTrace();
						error = false;
					}
				}
				// 恶性负载设置
				else if ("EXFZ".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "恶性负载设置," + info);

					cmMsendCommand.getPara(amMeterId);
					if ("1".equals(cmMsendCommand.getMeterProtocol()))// 97规约
					{
						// System.out.println("mmmm:" + userID);
						xmlCoder = cmMsendCommand.EXinFuZaiSheZhi(amMeterId, userLoginName);
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					} else
					// 07规约
					{
						// System.out.println("mmmm:" + userID);
						xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "1");// 打开编程键
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								} else
								{
									xmlCoder = cmMsendCommand.EXinFuZaiSheZhi(amMeterId, userLoginName);
									if (xmlCoder == null)
									{
										batchOprModel.setHtmlShow("执行失败");
										error = false;
									} else
									{
										replyXml = sendXml.sendXMLToServer(xmlCoder);
										if (replyXml != null)
										{
											System.out.println("replyXml:" + replyXml);
											batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0,
													"", "", "", "", "", "0", "");
											if ("1".equals(batchOprModel.getIsError()))
											{
												batchOprModel.setHtmlShow("执行失败");
												error = false;
											} else
											{
												xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "0");// 关闭编程键
												if (xmlCoder == null)
												{
													batchOprModel.setHtmlShow("执行失败");
													error = false;
												} else
												{
													replyXml = sendXml.sendXMLToServer(xmlCoder);
													if (replyXml != null)
													{
														System.out.println("replyXml:" + replyXml);
														batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "",
																saleInfo, 0, "", "", "", "", "", "0", "");
														if ("1".equals(batchOprModel.getIsError()))
														{
															batchOprModel.setHtmlShow("执行失败");
															error = false;
														}
													} else
													{
														batchOprModel.setHtmlShow("执行失败");
														error = false;
													}
												}
											}
										} else
										{
											batchOprModel.setHtmlShow("执行失败");
											error = false;
										}
									}
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					}
				}
				// 取消恶性负载设置
				else if ("EXFZQX".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "取消恶性负载设置," + info);

					cmMsendCommand.getPara(amMeterId);
					if ("1".equals(cmMsendCommand.getMeterProtocol()))// 97规约
					{
						// System.out.println("mmmm:" + userID);
						xmlCoder = cmMsendCommand.QuXiaoEXinFuZaiSheZhi(amMeterId, userLoginName);
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					} else
					// 07规约
					{
						xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "1");// 打开编程键
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								} else
								{
									xmlCoder = cmMsendCommand.QuXiaoEXinFuZaiSheZhi(amMeterId, userLoginName);
									if (xmlCoder == null)
									{
										batchOprModel.setHtmlShow("执行失败");
										error = false;
									} else
									{
										replyXml = sendXml.sendXMLToServer(xmlCoder);
										if (replyXml != null)
										{
											System.out.println("replyXml:" + replyXml);
											batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0,
													"", "", "", "", "", "0", "");
											if ("1".equals(batchOprModel.getIsError()))
											{
												batchOprModel.setHtmlShow("执行失败");
												error = false;
											} else
											{
												xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "0");// 关闭编程键
												if (xmlCoder == null)
												{
													batchOprModel.setHtmlShow("执行失败");
													error = false;
												} else
												{
													replyXml = sendXml.sendXMLToServer(xmlCoder);
													if (replyXml != null)
													{
														System.out.println("replyXml:" + replyXml);
														batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "",
																saleInfo, 0, "", "", "", "", "", "0", "");
														if ("1".equals(batchOprModel.getIsError()))
														{
															batchOprModel.setHtmlShow("执行失败");
															error = false;
														}
													} else
													{
														batchOprModel.setHtmlShow("执行失败");
														error = false;
													}
												}
											}
										} else
										{
											batchOprModel.setHtmlShow("执行失败");
											error = false;
										}
									}
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					}
				}
				// 允许恶性负载范围设置
				else if ("EXFZFW".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "允许恶性负载范围设置," + info);

					cmMsendCommand.getPara(amMeterId);
					if ("1".equals(cmMsendCommand.getMeterProtocol()))// 97规约
					{
						// System.out.println("mmmm:" + userID);
						xmlCoder = cmMsendCommand.YunXuEXinFuZaiSheZhi(amMeterId, userLoginName);
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					} else
					// 07规约
					{
						xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "1");// 打开编程键
						if (xmlCoder == null)
						{
							batchOprModel.setHtmlShow("执行失败");
							error = false;
						} else
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							if (replyXml != null)
							{
								System.out.println("replyXml:" + replyXml);
								batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0, "", "", "",
										"", "", "0", "");
								if ("1".equals(batchOprModel.getIsError()))
								{
									batchOprModel.setHtmlShow("执行失败");
									error = false;
								} else
								{
									xmlCoder = cmMsendCommand.YunXuEXinFuZaiSheZhi(amMeterId, userLoginName);
									if (xmlCoder == null)
									{
										batchOprModel.setHtmlShow("执行失败");
										error = false;
									} else
									{
										replyXml = sendXml.sendXMLToServer(xmlCoder);
										if (replyXml != null)
										{
											System.out.println("replyXml:" + replyXml);
											batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "", saleInfo, 0,
													"", "", "", "", "", "0", "");
											if ("1".equals(batchOprModel.getIsError()))
											{
												batchOprModel.setHtmlShow("执行失败");
												error = false;
											} else
											{
												xmlCoder = cmMsendCommand.YunXuEXinFuZaiShangXian(amMeterId, userLoginName);
												if (xmlCoder == null)
												{
													batchOprModel.setHtmlShow("执行失败");
													error = false;
												} else
												{
													replyXml = sendXml.sendXMLToServer(xmlCoder);
													if (replyXml != null)
													{
														System.out.println("replyXml:" + replyXml);
														batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle, amMeterId + "",
																saleInfo, 0, "", "", "", "", "", "0", "");
														if ("1".equals(batchOprModel.getIsError()))
														{
															batchOprModel.setHtmlShow("执行失败");
															error = false;
														} else
														{
															xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "0");// 关闭编程键
															if (xmlCoder == null)
															{
																batchOprModel.setHtmlShow("执行失败");
																error = false;
															} else
															{
																replyXml = sendXml.sendXMLToServer(xmlCoder);
																if (replyXml != null)
																{
																	System.out.println("replyXml:" + replyXml);
																	batchOprModel = batchOprOtherDao.parseXml(xmlCoder, replyXml, commandStyle,
																			amMeterId + "", saleInfo, 0, "", "", "", "", "", "0", "");
																	if ("1".equals(batchOprModel.getIsError()))
																	{
																		batchOprModel.setHtmlShow("执行失败");
																		error = false;
																	}
																} else
																{
																	batchOprModel.setHtmlShow("执行失败");
																	error = false;
																}
															}
														}
													} else
													{
														batchOprModel.setHtmlShow("执行失败");
														error = false;
													}
												}
											}
										} else
										{
											batchOprModel.setHtmlShow("执行失败");
											error = false;
										}
									}
								}
							} else
							{
								batchOprModel.setHtmlShow("执行失败");
								error = false;
							}
						}
					}
				}
				// 总清
				else if ("ZQ".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "总清," + info);

					// 读总清前剩余电量和总示数
					String SYValue = "", TotoleUsed = "";
					String protocol = "1";// 默认97规约
					xmlCoder = cmMsendCommand.readMeter(amMeterId, userLoginName);
					if (xmlCoder != null)
					{
						try
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							System.out.println("replyXml:" + replyXml);
						} catch (SocketTimeoutException e)
						{
							error = false;
						}

						NodeList list = null;
						String err;
						if (replyXml != null)
						{
							list = getNodeList(replyXml, "commandback");

							for (int i = 0; i < list.getLength(); i++)
							{
								Element commandBack = (Element) list.item(i);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");

									if ("0".equals(err))
									{
										DecimalFormat df = new DecimalFormat("0.00");
										NodeList resultList = commandBack.getElementsByTagName("result");
										for (int j = 0; j < resultList.getLength(); j++)
										{
											Element resultElement = (Element) resultList.item(j);

											if (resultElement.hasAttribute("SYValue"))// 97表
											{
												SYValue = resultElement.getAttribute("SYValue");
												TotoleUsed = resultElement.getAttribute("TotoleUsed");

												SYValue = df.format(df.parse(SYValue));
												TotoleUsed = df.format(df.parse(TotoleUsed));
											} else
											{// 07表
												protocol = "30";
												SYValue = resultElement.getAttribute("SYMoney");
												SYValue = df.format(df.parse(SYValue));

												xmlCoder = cmMsendCommand.readMeter07(amMeterId, userLoginName, "00010000");// 总示数
												if (xmlCoder != null)
												{
													try
													{
														replyXml = sendXml.sendXMLToServer(xmlCoder);
														System.out.println("replyXml：" + replyXml);
													} catch (SocketTimeoutException e)
													{
														error = false;
													}

													if (replyXml != null)
													{
														list = getNodeList(replyXml, "commandback");
														for (i = 0; i < list.getLength(); i++)
														{
															commandBack = (Element) list.item(i);
															if (commandBack.hasAttribute("error"))
															{
																err = commandBack.getAttribute("error");
																// 失败
																if (err.equals("1"))
																{
																	error = false;
																} else if (err.equals("0")) // 成功
																{
																	resultList = commandBack.getElementsByTagName("result");
																	for (j = 0; j < resultList.getLength(); j++)
																	{
																		resultElement = (Element) resultList.item(j);
																		if (resultElement.hasAttribute("ZValueZY"))// 07表当前电量
																		{
																			TotoleUsed = resultElement.getAttribute("ZValueZY");
																			TotoleUsed = df.format(df.parse(TotoleUsed));
																		}
																	}
																}
															}
														}

													} else
													{
														error = false;
													}

												} else
												{
													error = false;
												}

											}
										}

									} else
									{
										error = false;
									}
								}
							}

						} else
						{
							error = false;
						}
					} else
					{
						error = false;
					}
					int flag = 0;
					if (!"".equals(SYValue) && !"".equals(TotoleUsed))
					{
						// 总清

						xmlCoder = cmMsendCommand.zongQing(amMeterId, userLoginName);
						NodeList list = null;
						String err = "";
						if ("1".equals(cmMsendCommand.getMeterProtocol()))
						{
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							System.out.println("replyXml:" + replyXml);

							list = getNodeList(replyXml, "commandback");
							Element commandBack = (Element) list.item(0);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									error = false;
								} else
								{
									flag = 1;
									batchOprModel.setHtmlShow("执行成功");
								}
							}
						} else
						{
							xmlCoder = cmMsendCommand.setMode(amMeterId, userLoginName, 0);
							replyXml = sendXml.sendXMLToServer(xmlCoder);
							System.out.println("replyXml:" + replyXml);

							err = null;
							if (replyXml != null)
							{
								err = getAttrValue(replyXml, "commandback", "error");
							} else
							{
								error = false;
							}

							if (!err.equals("0"))
							{
								error = false;
							} else
							{
								xmlCoder = cmMsendCommand.zongQing(amMeterId, userLoginName);
								replyXml = sendXml.sendXMLToServer(xmlCoder);
								System.out.println("replyXml:" + replyXml);

								if (replyXml != null)
								{
									err = getAttrValue(replyXml, "commandback", "error");
								}

								if (!err.equals("0"))
								{
									error = false;
								} else
								{
									flag = 1;
									batchOprModel.setHtmlShow("执行成功");
									// 设置出厂模式
									xmlCoder = cmMsendCommand.setMode(amMeterId, userLoginName, 1);
									replyXml = sendXml.sendXMLToServer(xmlCoder);
									System.out.println("replyXml:" + replyXml);

									if (replyXml != null)
									{
										err = getAttrValue(replyXml, "commandback", "error");
									}
									/*
									 * if (!err.equals("0")) { error = false; }
									 * else { flag = 1;
									 * batchOprModel.setHtmlShow("执行成功"); }
									 */

								}

							}
						}

						if (flag == 1)
						{
							VolumeSetDao volumeSetDao = new VolumeSetDao();
							volumeSetDao.serUpdateZQInfo(amMeterId, userID, SYValue, TotoleUsed);
						}

					} else
					{
						error = false;
					}

				} else if ("setPrice".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "设置电费," + info);

					// 开启编程键
					xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "1");// 打开编程键
					replyXml = sendXml.sendXMLToServer(xmlCoder);
					System.out.println("replyXml:" + replyXml);

					String isSuccess = "0";
					
					String err = "";
					err = null;
					if (replyXml != null)
					{
						err = getAttrValue(replyXml, "commandback", "error");
					} else
					{
						error = false;
					}

					if (!err.equals("0"))
					{
						error = false;
					} else
					{
						// 设置第二套电价
						float priceValue = (new CMMeterDao()).getPriceByAmmId(amMeterId);
						xmlCoder = cmMsendCommand.SetPrice02(amMeterId, userLoginName, priceValue);
						if (xmlCoder != null)
						{
							try
							{
								replyXml = sendXml.sendXMLToServer(xmlCoder);
								System.out.println("replyXml:" + replyXml);
							} catch (SocketTimeoutException e)
							{
								error = false;
							}

							err = "";
							err = null;
							if (replyXml != null)
							{
								err = getAttrValue(replyXml, "commandback", "error");
							} else
							{
								error = false;
							}

							if (!err.equals("0"))
							{
								error = false;
							} else
							{
								//设置两套电价切换时间
								xmlCoder = cmMsendCommand.SetPriceQieHuanTime(amMeterId, userLoginName);
								if (xmlCoder != null)
								{
									try
									{
										replyXml = sendXml.sendXMLToServer(xmlCoder);
										System.out.println("replyXml:" + replyXml);
									} catch (SocketTimeoutException e)
									{
										error = false;
									}

									NodeList list = null;
									if (replyXml != null)
									{
										list = getNodeList(replyXml, "commandback");
										Element commandBack = (Element) list.item(0);
										if (commandBack.hasAttribute("error"))
										{
											err = commandBack.getAttribute("error");
											// 失败
											if (!err.equals("0"))
											{
												error = false;
											} else
											{
												batchOprModel.setHtmlShow("执行成功");
												isSuccess="1";
												//关闭编程键
												xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "0");
												replyXml = sendXml.sendXMLToServer(xmlCoder);
												System.out.println("replyXml:" + replyXml);

												err = "";
												err = null;
												if (replyXml != null)
												{
													err = getAttrValue(replyXml, "commandback", "error");
												} else
												{
													error = false;
												}
											}
										}
									} else
									{
										error = false;
									}
								}
							}
						} else
						{
							error = false;
						}
					}
					if(isSuccess.equals("1")){
						error = true;
					}

				}
				// 设置拉闸报警量
				else if ("lazhabaojing".equals(commandStyle))
				{
					String info = (new CMMeterDao()).getAmMeterByID(amMeterId + "");
					//String userLoginName = (String) req.getSession().getAttribute("userName");
					OperationLogInterface log = new OperationLogImplement();
					// 写入日志
					log.writeLog(userLoginName, "批量设置", "设置拉闸报警量," + info);

					// xmlCoder = cmMsendCommand.setMode(amMeterId, userID, 0);
					xmlCoder = cmMsendCommand.ProgramKey(amMeterId, userLoginName, "1");// 打开编程键
					replyXml = sendXml.sendXMLToServer(xmlCoder);
					System.out.println("replyXml:" + replyXml);

					String err = "";
					err = null;
					if (replyXml != null)
					{
						err = getAttrValue(replyXml, "commandback", "error");
					} else
					{
						error = false;
					}

					if (!err.equals("0"))
					{
						error = false;
					} else
					{

						xmlCoder = cmMsendCommand.setLaZhaValue(amMeterId, userLoginName);

						if (xmlCoder != null)
						{
							try
							{
								replyXml = sendXml.sendXMLToServer(xmlCoder);
								System.out.println("replyXml:" + replyXml);
							} catch (SocketTimeoutException e)
							{
								error = false;
							}

							NodeList list = null;
							// String err;
							if (replyXml != null)
							{
								list = getNodeList(replyXml, "commandback");
								Element commandBack = (Element) list.item(0);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									// 失败
									if (err.equals("1"))
									{
										error = false;
									} else
									{
										batchOprModel.setHtmlShow("执行成功");
									}
								}
							} else
							{
								error = false;
							}

						} else
						{
							error = false;
						}

					}

				}

			}

		} catch (SocketTimeoutException e)
		{
			error = false;
			batchOprModel.setHtmlShow("电表超时未响应");
		}
		// 将本条电表的执行结果返回给页面
		// 更改最后通讯时间
		if (error == true)
		{
			Date nowDate = new Date();
			String dateStr = sdf.format(nowDate);
			boolean b = updateLastTimeForAmMeter(Integer.parseInt(ammeterId), dateStr);
			if (!b)
			{
				error = false;
				batchOprModel.setHtmlShow("更新最后通讯时间失败");
			}
		}

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("commandStyle", commandStyle);
		json.put("htmlShow", batchOprModel.getHtmlShow());
		json.put("amMeterName", amMeterName);
		json.put("error", error);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 处理07协议的补发
	 * 
	 * @param req
	 * @param resp
	 * @param replyXml
	 * @param amMeterName
	 * @param commandStyle
	 * @param amMeterId
	 * @param TheSaleInfoNum
	 * @param money
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void bufa07xieyi(HttpServletRequest req, HttpServletResponse resp, String replyXml, String commandStyle, String amMeterName,
			int amMeterId, String TheSaleInfoNum, String money) throws IOException, SQLException, ParseException
	{
		String html = "";

		BatchOprOtherDao batchOprOtherDao = new BatchOprOtherDao();
		DecimalFormat df = new DecimalFormat("0.##");
		String str = null;
		CMMsendCommand csc = new CMMsendCommand();
		XMLCoder xc = new XMLCoder();
		SendingAPCode sxc = new SendingAPCode();

		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		boolean error = true;
		String err = getAttrValue(replyXml, "commandback", "error");
		String back = "0";

		String QSYValue = "", QTZValue = "", BuyTimes = null, HSYValue = "";
		if ("0".equals(err))
		{

			QSYValue = getAttrValue(replyXml, "result", "SYMoney");

			/**
			 * 读取当前透支电量
			 */
			xc = csc.readMeter07(amMeterId, userID, "00900201");

			if (xc != null)
			{
				str = sxc.sendXMLToServer(xc);
				System.out.println("收到的报文的回复是：" + str);
				if (str != null)
				{
					err = getAttrValue(str, "commandback", "error");
					if ("0".equals(err))
					{
						QTZValue = getAttrValue(str, "result", "TZMoney");

					} else
					{
						error = false;
					}
				} else
				{
					error = false;
				}

				/**
				 * 读购电次数
				 */
				xc = csc.readMeter07(amMeterId, userID, "07010202");

				if (xc != null)
				{
					str = sxc.sendXMLToServer(xc);
					System.out.println("收到的报文的回复是：" + str);
					if (str != null)
					{
						err = getAttrValue(str, "commandback", "error");
						if ("0".equals(err))
						{
							BuyTimes = getAttrValue(str, "result", "BuyTimes");
						} else
						{
							error = false;
						}

					} else
					{
						error = false;
					}
				} else
				{
					error = false;
				}

				if (BuyTimes != null)
				{
					/**
					 * 下发电量
					 */
					QSYValue = Double.parseDouble(QSYValue) * csc.getBeiLv() + "";
					html = "<strong>读购电前剩余金额</strong><span style='color:green'>  剩余金额：" + QSYValue + "元，透支金额：" + QTZValue + "元，购电次数：" + BuyTimes
							+ "</span>";
					back = batchOprOtherDao.SerCheckBu(amMeterId + "", TheSaleInfoNum, Double.parseDouble(QSYValue), Integer.parseInt(BuyTimes));
					if ("1".equals(back))
					{
						html += "<br/><strong>下发购电金额:</strong><span style='color:green'>" + money + "元</span>";
						xc = csc.sendTGross(amMeterId, money, BuyTimes, userID);

						str = sxc.sendXMLToServer(xc);
						System.out.println("收到的报文的回复是：" + str);
						if (str != null)
						{
							err = getAttrValue(str, "commandback", "error");
							if ("0".equals(err))
							{
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String sql = "update APSaleInfo set QSYValue=" + df.parse(QSYValue) + ",Times=" + BuyTimes
										+ ",Status=1,SendTime=to_date('" + sdf.format(new Date()) + "','yyyy-mm-dd hh24:mi:ss') where SaleInfoNum='"
										+ TheSaleInfoNum + "'";
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

								/**
								 * 抄读补发后剩余电量
								 */
								xc = csc.readMeter(amMeterId, userID);
								str = sxc.sendXMLToServer(xc);
								System.out.println("收到的报文的回复是：" + str);
								if (str != null)
								{
									err = getAttrValue(str, "commandback", "error");// 李嵘20150804
									if ("0".equals(err))
									{
										HSYValue = getAttrValue(str, "result", "SYMoney");

										html += "<br/>" + "<strong>读购电后剩余金额:</strong><span style='color:green'>" + HSYValue + "元</span>";
										if (Float.parseFloat(HSYValue) > Float.parseFloat(QSYValue))
										{
											sql = "update APSaleInfo set QSYValue=" + df.parse(QSYValue) + ",HSYValue=" + df.parse(HSYValue)
													+ ",Times=" + BuyTimes + ",Status=1,SendTime=to_date('" + sdf.format(new Date())
													+ "','yyyy-mm-dd hh24:mi:ss') where SaleInfoNum='" + TheSaleInfoNum + "'";
											Connection conn1 = null;
											PreparedStatement ps1 = null;
											try
											{
												conn1 = ConnDB.getConnection();
												ps1 = conn1.prepareStatement(sql);
												ps1.executeUpdate();

											} catch (SQLException e)
											{
												e.printStackTrace();
											} finally
											{
												ConnDB.release(conn1, ps1);
											}

										}
									} else
									{
										error = false;
									}

								} else
								{
									error = false;
								}
							} else
							{
								error = false;
							}

						} else
						{
							error = false;
						}

					} else
					{
						html += "<br/><span style='color:red'>该记录已经下发成功，系统将自动修改售电单记录！</span><br/>";
					}

				} else
				{
					error = false;
				}
			} else
			{
				error = false;
			}

		} else
		{
			error = false;
		}

		// 将本条电表的执行结果返回给页面
		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("commandStyle", commandStyle);
		json.put("amMeterName", amMeterName);
		json.put("error", error);
		json.put("htmlShow", html);
		json.put("backValue", back);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();
	}

}
