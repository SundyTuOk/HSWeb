package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.sf.energy.manager.current.dao.CurrentMeasureDao;
import com.sf.energy.manager.current.model.CurrentManagerModel;
import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.manager.current.service.XMLHelper;
import com.sf.energy.manager.current.serviceImpl.SendingImmediateCode;
import com.sf.energy.prepayment.dao.CMArchDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.model.CMArchModel;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.system.dao.AmmPriceDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.AmmPriceModel;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.statistics.model.TableModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.GetServerInfo;
import com.sf.energy.util.GetSystemInfo;

public class CurrentManagerServlet extends HttpServlet
{
	DecimalFormat df = new DecimalFormat("#####0.00");

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if ("queryAmmRightNow".equals(method))
		{
			try
			{
				queryAmmRightNow(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("queryAmmSanRightNow".equals(method))
		{
			try
			{
				queryAmmSanRightNow(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("queryDayValue".equals(method))
		{
			try
			{
				queryDayValue(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("queryMonthValue".equals(method))
		{
			try
			{
				queryMonthValue(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("queryValueFromTable".equals(method))
		{
			try
			{
				queryValueFromTable(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ("getAmMeterFusionChart".equals(method))
			try
		{
				getAmMeterFusionChart(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		if ("getDictionaryValueFX".equals(method))
			try
		{
				getDictionaryValueFX(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("getDictionaryValueXZ".equals(method))
			try
		{
				getDictionaryValueXZ(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("getDictionaryValueBJLX".equals(method))
			try
		{
				getDictionaryValueBJLX(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("getDictionaryValueYJFX".equals(method))
			try
		{
				getDictionaryValueYJFX(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("getPriceValue".equals(method))
			try
		{
				getPriceValue(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("EditRelativeProperty".equals(method))
			try
		{
				EditRelativeProperty(request, response);
		} catch (ParseException e)
		{

			e.printStackTrace();
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		if ("whenSelectedSchoolFunction".equals(method))
		{
			whenSelectedSchoolFunction(request, response);
		}
		if ("whenSelectedAreaFunction".equals(method))
		{
			whenSelectedAreaFunction(request, response);
		}

		if ("whenSelectedArchFunction".equals(method))
		{
			whenSelectedArchFunction(request, response);
		}

		if ("whenSelectedArchFunction1".equals(method))
		{
			whenSelectedArchFunction1(request, response);
		}
		
		if ("whenSelectedFloorFunction".equals(method))
		{
			whenSelectedFloorFunction(request, response);
		}

		if ("whenSelectedFloorFunction1".equals(method))
		{
			whenSelectedFloorFunction1(request, response);
		}
		
		if ("getTimer".equals(method))
		{
			getTimer(request, response);
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	/**
	 * 立即抄表（单项数据，电表示数）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmRightNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		GatherDao gd = new GatherDao();
		AmmeterDao ad = new AmmeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		int queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		AmmeterModel am = new AmmeterModel();
		am = ad.queryByID(queryAmmID);

		Gather gm = new Gather();
		gm = gd.getGatherXMLInfo(queryAmmID);

		if (am != null)
		{
			String MeterStyle_num = ad.queryMeterStyleNumByID(queryAmmID);
			if (!MeterStyle_num .equals("07") && !MeterStyle_num .equals("08")  && !MeterStyle_num .equals("09"))
			{
				int ammeNum = am.getAmmeterPoint();// 2
				String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0201

				XMLCoder immXmlCoderD = new XMLCoder();// 单相

				immXmlCoderD.setCommandCodeAFN("0C");
				immXmlCoderD.setCommandCodeDA(ammPointNoDA);
				immXmlCoderD.setCommandCodeDT("0110");// 单相
				immXmlCoderD.setTerminalAddress(gd.queryGatherNumByAmmID(queryAmmID));// 002700bb00
				immXmlCoderD.setPw(gm.getGatherPw());
				immXmlCoderD.setDatasiteID(String.valueOf(gm.getDatasiteID()));
				immXmlCoderD.setOperCode(userID + "");

				String msg = "";
				try
				{
					msg = immSending.sendXMLToServer(immXmlCoderD);
					// System.out.println("tasknum:"+immXmlCoderD.getTaskNum());
					System.out.println("返回的信息是：" + msg);
				} catch (UnknownHostException e)
				{
					JSONArray json = new JSONArray();
					JSONObject jo = new JSONObject();
					jo.put("ServerOffline", 0);// 服务器不在线
					json.add(jo);
					response.setContentType("application/x-json");
					PrintWriter out = response.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} catch (SocketTimeoutException e)
				{
					JSONArray json = new JSONArray();
					JSONObject jo = new JSONObject();
					jo.put("ServerOffline", 2);// 服务器超时
					json.add(jo);
					response.setContentType("application/x-json");
					PrintWriter out = response.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} catch (ConnectException e)
				{
					JSONArray json = new JSONArray();
					JSONObject jo = new JSONObject();
					jo.put("ServerOffline", 3);// 服务器连接异常
					json.add(jo);
					response.setContentType("application/x-json");
					PrintWriter out = response.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				}

				// //System.out.println(new Date().toLocaleString()+
				// ":服务器发回来的单相xml"
				// + msg);

				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();

				if ("".equals(msg))// 服务器没有发送数据回来
				{

					jo.put("ServerOffline", 1);// 服务器没有发送数据回来
					json.add(jo);
					response.setContentType("application/x-json");
					PrintWriter out = response.getWriter();
					out.println(json.toString());
					out.flush();
					out.close();
				} else
				{
					NodeList gwList = null;
					String task;
					gwList = xmlHelper.getNodeList(msg, "GW");
					for (int m = 0; m < gwList.getLength(); m++)
					{
						Element tasknum = (Element) gwList.item(m);
						if (tasknum.hasAttribute("tasknum"))
						{
							task = tasknum.getAttribute("tasknum");

							if (task.equals(immXmlCoderD.getTaskNum()))
							{
								NodeList lis = null;
								String err;
								lis = xmlHelper.getNodeList(msg, "commandback");

								for (int j = 0; j < lis.getLength(); j++)
								{
									Element commandBack = (Element) lis.item(j);
									if (commandBack.hasAttribute("error"))
									{
										err = commandBack.getAttribute("error");
										// 失败
										if (err.equals("1"))
										{
											String errormessage = commandBack.getAttribute("errormessage");
											jo = new JSONObject();
											jo.put("errormessage", errormessage);
											json.add(jo);
											response.setContentType("application/x-json");
											PrintWriter out = response.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										} else if (err.equals("0")) // 成功
										{
											NodeList list = xmlHelper.getNodeList(msg, "result");
											for (int i = 0; i < list.getLength(); i++)
											{

												Element result1 = (Element) list.item(i);
												if (Float.parseFloat(result1.getAttribute("ZY0")) != -1)
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
													jo.put("ammTotalValue",
															df.format(Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv()
																	+ am.getXiuzheng()));
													jo.put("commTime", result1.getAttribute("ValueTime"));
													/**
													 * 点抄成功插入数据库
													 */
													CurrentMeasureDao cd = new CurrentMeasureDao();
													cd.insertAfterCommDan(
															Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv() + am.getXiuzheng(),
															result1.getAttribute("ValueTime"), queryAmmID,am.getBeilv());

													AmmeterDao aDao = new AmmeterDao();
													/**
													 * 更新电表状态和最后通讯时间
													 */
													aDao.updateAmMeterWithLasttimeandState(queryAmmID, result1.getAttribute("ValueTime"));
												} else
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", "-1.00");
													jo.put("ammTotalValue", "-1.00");
													jo.put("commTime", result1.getAttribute("ValueTime"));
												}
												// //System.out.println("返回ajax的数据电表示数value："+
												// df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
												// //System.out.println("**************************************");
												// 抄表成功插入数据库ammeterData

												//												if (Float.parseFloat(result1.getAttribute("ZY0")) != -1)
												//												{
												//													CurrentMeasureDao cd = new CurrentMeasureDao();
												//													cd.insertAfterCommDan(
												//															Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv() + am.getXiuzheng(),
												//															result1.getAttribute("ValueTime"), queryAmmID,am.getBeilv());
												//												}

											}
											json.add(jo);
											response.setContentType("application/x-json");
											PrintWriter out = response.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										}

									}
								}
							} else
							{
								// JSONArray json = new JSONArray();
								// JSONObject jo = new JSONObject();
								jo.put("ServerOffline", 4);// 返回任务编号错误
								json.add(jo);
								response.setContentType("application/x-json");
								PrintWriter out = response.getWriter();
								out.println(json.toString());
								out.flush();
								out.close();
							}
						}
					}
				}
			} else
			{
				if (queryMeterProtocolByMeterStyleID(am.getMeteStyleID()) == 2)
				{
					CMMsendCommand aprm = new CMMsendCommand();

					SendingAPCode sxc = new SendingAPCode();
					com.sf.energy.prepayment.model.XMLCoder xc = aprm.readSanXiangJianCeYiDanXiang(queryAmmID, userID);
					String msg = "";
					try
					{
						msg = sxc.sendXMLToServer(xc);
					} catch (UnknownHostException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 0);// 服务器不在线
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} catch (SocketTimeoutException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 2);// 服务器超时
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} catch (ConnectException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 3);// 服务器连接异常
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}

					// //System.out.println(new Date().toLocaleString()+
					// ":服务器发回来的单相xml" + msg);

					JSONArray json = new JSONArray();
					JSONObject jo = new JSONObject();

					if ("".equals(msg))// 服务器没有发送数据回来
					{

						jo.put("ServerOffline", 1);// 服务器没有发送数据回来
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else
					{
						NodeList lis = null;
						String err;
						lis = xmlHelper.getNodeList(msg, "commandback");

						for (int j = 0; j < lis.getLength(); j++)
						{
							Element commandBack = (Element) lis.item(j);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("errormessage", errormessage);
									json.add(jo);
									response.setContentType("application/x-json");
									PrintWriter out = response.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList list = xmlHelper.getNodeList(msg, "result");
									for (int i = 0; i < list.getLength(); i++)
									{

										Element result1 = (Element) list.item(i);
										if (Float.parseFloat(result1.getAttribute("ZY0")) != -1)
										{
											jo.put("ServerOffline", -1);// 成功
											jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
											jo.put("ammTotalValue",
													df.format((Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv()) + am.getXiuzheng()));
											jo.put("commTime", result1.getAttribute("ValueTime"));

											//点抄成功数据存入数据库
											CurrentMeasureDao cd = new CurrentMeasureDao();
											cd.insertAfterCommDan(
													Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv() + am.getXiuzheng(),
													result1.getAttribute("ValueTime"), queryAmmID,am.getBeilv());
											AmmeterDao aDao = new AmmeterDao();
											/**
											 * 更新电表状态和最后通讯时间
											 */
											aDao.updateAmMeterWithLasttimeandState(queryAmmID,result1.getAttribute("ValueTime"));
										} else
										{
											jo.put("ServerOffline", -1);// 成功
											jo.put("value", "-1.00");
											jo.put("ammTotalValue", "-1.00");
											jo.put("commTime", result1.getAttribute("ValueTime"));

										}

									}
									json.add(jo);
									response.setContentType("application/x-json");
									PrintWriter out = response.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}
					}

				} else if (queryMeterProtocolByMeterStyleID(am.getMeteStyleID()) == 1 || queryMeterProtocolByMeterStyleID(am.getMeteStyleID()) == 30)
				{
					CMMsendCommand aprm = new CMMsendCommand();

					SendingAPCode sxc = new SendingAPCode();
					com.sf.energy.prepayment.model.XMLCoder xc = aprm.readDanXiangTouChuan(queryAmmID, userID);
					String msg = "";
					try
					{
						msg = sxc.sendXMLToServer(xc);
					} catch (UnknownHostException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 0);// 服务器不在线
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} catch (SocketTimeoutException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 2);// 服务器超时
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} catch (ConnectException e)
					{
						JSONArray json = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put("ServerOffline", 3);// 服务器连接异常
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}

					// //System.out.println(new Date().toLocaleString()+
					// ":服务器发回来的单相xml" + msg);

					JSONArray json = new JSONArray();
					JSONObject jo = new JSONObject();

					if ("".equals(msg))// 服务器没有发送数据回来
					{

						jo.put("ServerOffline", 1);// 服务器没有发送数据回来
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else
					{
						NodeList lis = null;
						String err;
						lis = xmlHelper.getNodeList(msg, "commandback");

						for (int j = 0; j < lis.getLength(); j++)
						{
							Element commandBack = (Element) lis.item(j);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("errormessage", errormessage);
									json.add(jo);
									response.setContentType("application/x-json");
									PrintWriter out = response.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									NodeList list = xmlHelper.getNodeList(msg, "result");
									for (int i = 0; i < list.getLength(); i++)
									{

										Element result1 = (Element) list.item(i);
										if (result1.getAttribute("Meter_Address").equals(am.getAmmeterAddress485()))
										{
											if (Float.parseFloat(result1.getAttribute("ZValueZY")) != -1)
											{
												jo.put("ServerOffline", -1);// 成功
												jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZValueZY"))));
												jo.put("ammTotalValue",
														df.format((Double.parseDouble(result1.getAttribute("ZValueZY")) * am.getBeilv())
																+ am.getXiuzheng()));
												jo.put("commTime", result1.getAttribute("ValueTime"));
												CurrentMeasureDao cd = new CurrentMeasureDao();
												cd.insertAfterCommDan(
														Double.parseDouble(result1.getAttribute("ZValueZY")) * am.getBeilv() + am.getXiuzheng(),
														result1.getAttribute("ValueTime"), queryAmmID,am.getBeilv());
												AmmeterDao aDao = new AmmeterDao();
												/**
												 * 更新电表状态和最后通讯时间
												 */
												aDao.updateAmMeterWithLasttimeandState(queryAmmID, result1.getAttribute("ValueTime"));
											} else
											{
												jo.put("ServerOffline", -1);// 成功
												jo.put("value", "-1.00");
												jo.put("ammTotalValue", "-1.00");
												jo.put("commTime", result1.getAttribute("ValueTime"));
											}
										} else
										{
											jo.put("ServerOffline", -1);// 成功
											jo.put("value", "-1.00");
											jo.put("ammTotalValue", "-1.00");
											jo.put("commTime", result1.getAttribute("ValueTime"));
										}
									}
									json.add(jo);
									response.setContentType("application/x-json");
									PrintWriter out = response.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}
					}
				}
			}

			// }
		}

	}

	/**
	 * 从表中查询当天用电
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryDayValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CurrentMeasureDao cmd = new CurrentMeasureDao();
		AmmeterModel am = new AmmeterModel();
		AmmeterDao ad = new AmmeterDao();
		Map<String, String> map = new HashMap<String, String>();
		int ammID = Integer.parseInt(request.getParameter("queryAmmID"));
		String time = request.getParameter("time");
		int selectYear = Integer.parseInt(time.substring(0, 4));
		int selectMonth = Integer.parseInt(time.substring(5, 7));
		int selectDay = Integer.parseInt(time.substring(8, 10));

		am = ad.queryByID(ammID);
		map = cmd.getAmmDayValue(ammID, selectYear, selectMonth, selectDay);

		if (am != null)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("dayValue", df.format(Float.parseFloat(map.get("ZGross"))));
			jo.put("dayMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}

	}

	/**
	 * 从表中查询当月用电
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMonthValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CurrentMeasureDao cmd = new CurrentMeasureDao();
		AmmeterModel am = new AmmeterModel();
		AmmeterDao ad = new AmmeterDao();
		Map<String, String> map = new HashMap<String, String>();
		int ammID = Integer.parseInt(request.getParameter("queryAmmID"));

		String time = request.getParameter("time");
		int selectYear = Integer.parseInt(time.substring(0, 4));
		int selectMonth = Integer.parseInt(time.substring(5, 7));
		int selectDay = Integer.parseInt(time.substring(8, 10));

		am = ad.queryByID(ammID);
		map = cmd.getAmmMonthValue(ammID, selectYear, selectMonth);

		if (am != null)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("monthValue", df.format(Float.parseFloat(map.get("ZGross"))));
			jo.put("monthMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

	}

	/**
	 * 立即抄表（三项数据，电表功率）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmSanRightNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		GatherDao gd = new GatherDao();
		AmmeterDao ad = new AmmeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		int queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		AmmeterModel am = new AmmeterModel();
		am = ad.queryByID(queryAmmID);

		Gather gm = new Gather();
		gm = gd.getGatherXMLInfo(queryAmmID);

		if (am != null)
		{
			// // 离线
			// if (am.getAmmeterStat() == 0)
			// {
			// JSONArray json = new JSONArray();
			// JSONObject jo = new JSONObject();
			// jo.put("ErrorInfo", 0);
			// json.add(jo);
			// response.setContentType("application/x-json");
			// PrintWriter out = response.getWriter();
			// out.println(json.toString());
			// out.flush();
			// out.close();
			// }
			// else
			// // 在线
			// {
			int ammeNum = am.getAmmeterPoint();// 2
			String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0201

			// 三相
			XMLCoder immXmlCoderS = new XMLCoder();
			immXmlCoderS.setCommandCodeAFN("0C");
			immXmlCoderS.setCommandCodeDA(ammPointNoDA);
			immXmlCoderS.setCommandCodeDT("0103");// 三相
			immXmlCoderS.setTerminalAddress(gd.queryGatherNumByAmmID(queryAmmID));// 002700bb00

			immXmlCoderS.setPw(gm.getGatherPw());
			immXmlCoderS.setOperCode(userID + "");

			String msg = "";
			try
			{
				msg = immSending.sendXMLToServer(immXmlCoderS);
			} catch (UnknownHostException e)
			{
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("ServerOffline", 0);
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} catch (SocketTimeoutException e)
			{
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("ServerOffline", 2);// 服务器超时
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} catch (ConnectException e)
			{
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("ServerOffline", 3);// 服务器连接异常
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
			// //System.out.println(new Date().toLocaleString()+":服务器发回来的三相xml"
			// + msg);

			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();

			if ("".equals(msg))// 服务器没有发送数据回来
			{

				jo.put("ServerOffline", 1);
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} else
			{
				NodeList list = xmlHelper.getNodeList(msg, "result");
				for (int i = 0; i < list.getLength(); i++)
				{

					Element result1 = (Element) list.item(i);

					jo.put("valueGonglv", df.format(Double.parseDouble(result1.getAttribute("PowerZY"))));
					// //System.out.println("返回ajax的数据valueGonglv：" +
					// df.format(Double.parseDouble(result1.getAttribute("PowerZY"))));
					// //System.out.println("**************************************");
					// 抄表成功插入数据库ammeter
					CurrentMeasureDao cd = new CurrentMeasureDao();
					cd.insertAfterCommSan(Double.parseDouble(result1.getAttribute("PowerZY")), new Date().toLocaleString(), queryAmmID);
				}
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}

			// }
		}

	}

	/**
	 * 从表中查询电表的相关属性
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryValueFromTable(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int queryAmmID = 0;
		CurrentMeasureDao cd = new CurrentMeasureDao();
		Map<String, String> ammMap = new HashMap<String, String>();
		if (request.getParameter("queryAmmID") != null)
		{
			queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		}
		AmmeterDao ad = new AmmeterDao();
		AmmPriceDao apd = new AmmPriceDao();
		AmmeterModel am = new AmmeterModel();
		am = ad.queryByID(queryAmmID);

		Calendar cal = Calendar.getInstance();// 使用日历类
		int selectYear = cal.get(Calendar.YEAR);// 得到年
		int selectMonth = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int selectDay = cal.get(Calendar.DAY_OF_MONTH);// 得到天

		ammMap = cd.getAmmValue(queryAmmID, selectYear, selectMonth, selectDay);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		// 电表示数
		if(Double.parseDouble(ammMap.get("ammValue"))!=0){
			jo.put("ammValue", df.format((Double.parseDouble(ammMap.get("ammValue")) - am.getXiuzheng()) / am.getBeilv()));

			jo.put("ammPower", ammMap.get("ammPower"));
		}else{
			jo.put("ammValue","--");

			jo.put("ammPower", "--");
		}
		jo.put("ammLastTime", am.getLastTime());

		// 关联属性
		jo.put("ammNum", am.getAmmeterAddress485());
		jo.put("partmentName", am.getPartmentName());
		jo.put("isComputation", am.getIsComputation());// 计量属性
		jo.put("useAmStyle", am.getUseAmStyleName());// 性质
		jo.put("styleName", am.getUseStyleName());// 分项
		jo.put("beilv", am.getBeilv());
		jo.put("ammPrice", apd.queryByID(am.getPriceID()).getPriceValue());
		jo.put("costCheck", am.getCostCheck());
		jo.put("standByCheck", am.getStandByCheck());
		jo.put("ammAddress", am.getConsumerAddress());
		jo.put("archName", am.getArchName());
		jo.put("ammArchID", am.getArchitectureID());
		jo.put("ammFloor", am.getFloor());
		jo.put("ammName", am.getAmmeterName());
		jo.put("ammArea", am.getAreaName());
		jo.put("ammAreaID", am.getAreaID());
		jo.put("ammStyle", am.getMeterStyleName());
		jo.put("ammState", am.getAmmeterStat());
		jo.put("ammTotalValue", df.format((Double.parseDouble(ammMap.get("ammValue")))));

		// 为了编辑输出的信息
		jo.put("ammID", am.getAmmeterID());
		jo.put("meteStyle", am.getMeterStyleName());// 表计类型
		jo.put("isOfflineWaring", am.getIsOffAlarm());// 报警
		jo.put("isTotalAmm", am.getIsCountMeter());
		jo.put("styleNameYJZX", am.getUseStyleNameYJZX());// 一级子项

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		// //System.out.println("集中监测电表json：" + json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 72小时用电
	 * 
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAmMeterFusionChart(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Date start = new Date();
		Date end = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		int TotalAm_ammid = 0;
		String TotalAmTime1 = request.getParameter("dateTime");
		if (TotalAmTime1 != null)
		{
			int year1 = Integer.parseInt(TotalAmTime1.substring(0, 4));
			int month1 = Integer.parseInt(TotalAmTime1.substring(5, 7));
			int day1 = Integer.parseInt(TotalAmTime1.substring(8, 10));

			int dayend = day1 - 2;
			// System.out.println("天"+day1);
			// System.out.println("天333"+dayend);
			start.setYear(year1 - 1900);
			start.setMonth(month1 - 1);
			start.setDate(dayend);
			start.setHours(0);
			start.setMinutes(0);
			start.setSeconds(0);

			end.setYear(year1 - 1900);
			end.setMonth(month1 - 1);
			end.setDate(day1);

			// System.out.println("天11"+start.getDate());
			// System.out.println("天22"+end.getDate());
		}

		// //System.out.println("start:"+start.toLocaleString());
		// //System.out.println("end:"+end.toLocaleString());
		// //System.out.println("start:"+df.format(start));
		// //System.out.println("end:"+df.format(end));
		String ammid = request.getParameter("AmMeterID");
		if (ammid != null)
		{
			TotalAm_ammid = Integer.parseInt(ammid);
		}
		ElecReportHelperImpl rhi_TotalAm = new ElecReportHelperImpl();
		List<AmMeterMataData> TotalAm_list = null;
		Map<String, Float> map = new HashMap<String, Float>();
		JSONObject jo = new JSONObject();
		JSONArray json = new JSONArray();
		String hour;

		try
		{
			TotalAm_list = rhi_TotalAm.getAmMeterBetween(TotalAm_ammid, start, end);
			for (AmMeterMataData n : TotalAm_list)
			{
				map.put(df.format(n.getRecordTimeDate()), n.getValue());
			}
			long t = start.getTime();
			String key;
			int startTime = GetSystemInfo.getWorkTimeForXML().get("startTime");
			int endTime = GetSystemInfo.getWorkTimeForXML().get("endTime");
			Date d = new Date();
			for (int i = 0; i < 72; i++)
			{
				t += 3600000;
				d.setTime(t);
				key = df.format(d);
				jo = new JSONObject();
				hour = getHourString(d);
				jo.put("recordTimeDate", hour);
				jo.put("startWorkTime", startTime);
				jo.put("endWorkTime", endTime);
				jo.put("startQueryTime", df.format(start).substring(0, 10));
				jo.put("endQueryTime", df.format(end).substring(0, 10));
				if (map.get(key) == null)
				{
					jo.put("value", 0);
				} else
				{
					jo.put("value", map.get(key));
				}
				json.add(jo);
			}

			//System.out.println("72小时用电："+json.toString());

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 获取小时
	 * 
	 * @param recordTimeDate
	 * @return
	 */
	private String getHourString(Date recordTimeDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:00");
		String hour = df.format(recordTimeDate);
		return hour;
	}

	/**
	 * 获取时间
	 * 
	 * @param recordTimeDate
	 * @return
	 */
	private String getTime(Date recordTimeDate)
	{
		Date d = new Date();
		d.setYear(recordTimeDate.getYear());
		d.setMonth(recordTimeDate.getMonth());
		d.setDate(recordTimeDate.getDate());
		d.setHours(recordTimeDate.getHours());
		d.setMinutes(0);
		d.setSeconds(0);
		return d.getTime() + "";
	}

	/**
	 * 获取用电分项中的条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueFX(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();
		ArrayList<DictionaryValueModel> listFX = dvd.listDictionaryValueByName("能耗编码");

		JSONArray json = new JSONArray();
		for (int i = 0; i < listFX.size(); i++)
		{
			JSONObject jo = new JSONObject();
			DictionaryValueModel dvm = listFX.get(i);
			jo.put("DictionaryID", dvm.getDictionaryID());
			jo.put("DictionaryValueID", dvm.getDictionaryValueID());
			jo.put("DictionaryValue", dvm.getDictionaryValue());// 汉字
			jo.put("DictionaryNum", dvm.getDictionaryValueNum());// ABCD
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获取用电性质的条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueXZ(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();

		ArrayList<DictionaryValueModel> list = dvd.listDictionaryValueByName("用电性质");

		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jo = new JSONObject();
			DictionaryValueModel dvm = list.get(i);
			jo.put("DictionaryID", dvm.getDictionaryID());
			jo.put("DictionaryValueID", dvm.getDictionaryValueID());
			jo.put("DictionaryValue", dvm.getDictionaryValue());// 汉字
			jo.put("DictionaryNum", dvm.getDictionaryValueNum());// ABCD
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 从metestyle表获取，而不是dictionary获取
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueBJLX(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// DictionaryValueDao dvd=new DictionaryValueDao();
		//
		// ArrayList<DictionaryValueModel>
		// list=dvd.listDictionaryValueByName("电表类型");

		MeterStyleDao msd = new MeterStyleDao();
		ArrayList<MeterStyle> list = msd.displayAllType("电表");

		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jo = new JSONObject();
			MeterStyle msm = list.get(i);
			jo.put("meteStyleID", msm.getMeterStyleID());
			jo.put("meteStyleName", msm.getMeterStyleName());

			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获取一级子项
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueYJFX(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();
		String fenxiangValue = request.getParameter("fenxiangValue");
		ArrayList<DictionaryValueModel> list = dvd.listDVByEnergy("能耗编码一级子项", fenxiangValue);

		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jo = new JSONObject();
			DictionaryValueModel dvm = list.get(i);
			jo.put("DictionaryID", dvm.getDictionaryID());
			jo.put("DictionaryValueID", dvm.getDictionaryValueID());
			jo.put("DictionaryValue", dvm.getDictionaryValue());// 汉字
			jo.put("DictionaryNum", dvm.getDictionaryValueNum());// ABCD，存入数据库的编码
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获取电表价格条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getPriceValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AmmPriceDao apd = new AmmPriceDao();
		ArrayList<AmmPriceModel> list = new ArrayList<AmmPriceModel>();
		list = apd.listAllPriceValue("E");
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jo = new JSONObject();
			AmmPriceModel apm = list.get(i);
			jo.put("PriceID", apm.getPriceID());
			jo.put("PriceValue", apm.getPriceValue());
			jo.put("PriceStyle", apm.getPriceStyle());
			jo.put("PriceNum", apm.getPriceNum());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 编辑电表的关联属性
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void EditRelativeProperty(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int ammeterID = Integer.parseInt(request.getParameter("ammeterID"));
		int areaID = Integer.parseInt(request.getParameter("areaID"));
		int partmentID = Integer.parseInt(request.getParameter("partmentID"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		int metestyleID = Integer.parseInt(request.getParameter("metestyleID"));
		int priceID = Integer.parseInt(request.getParameter("priceID"));
		int isOffine = Integer.parseInt(request.getParameter("isOffine"));
		int isTotal = Integer.parseInt(request.getParameter("isTotal"));
		int useAmmStyleID = Integer.parseInt(request.getParameter("useAmmStyleID"));// 性质
		int isComputation = Integer.parseInt(request.getParameter("isComputation"));
		String ammFX = request.getParameter("ammFX");// 分项
		String ammYJZX = request.getParameter("ammYJZX");// 一级子项

		AmmeterDao ad = new AmmeterDao();

		// 这2个要在查出来的am前面，
		if (!"-1".equals(ammFX))// 说明修改了分项
		{
			ad.editFX(ammeterID, ammFX);
		}
		if (!"-1".equals(ammYJZX))// 说明修改了一级子项
		{
			ad.editFXNum(ammeterID, ammYJZX);
		}

		AmmeterModel am = ad.queryByID(ammeterID);// 不编辑的则不改变属性，只改变set的属性

		am.setAmmeterID(ammeterID);
		am.setAreaID(areaID);
		am.setPartment(partmentID);
		am.setArchitectureID(archID);
		if (floor != -1)// 说明修改了楼层
		{
			am.setFloor(floor);
		}
		am.setMeteStyleID(metestyleID);
		am.setPriceID(priceID);
		am.setIsOffAlarm(isOffine);
		am.setIsCountMeter(isTotal);
		am.setUseAmStyle(useAmmStyleID);
		am.setIsComputation(isComputation);

		String resultInfo = null;
		if (ad.modify(am) == true)
		{
			resultInfo = "success";
		} else
		{
			resultInfo = "faild";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	/**
	 * 选择学校之后列出所有区域
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedSchoolFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		CurrentMeasureDao cd = new CurrentMeasureDao();

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天

		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();
		String sort = "Area_Name";
		String order = "asc";

		if (request.getParameter("sort") != null)
		{
			sort = request.getParameter("sort");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}
		// if(sort.equals("areaName")){
		// sort="Area_Name";
		// }else if(sort.equals("dayGross")){
		// sort="DayAmGross";
		// }else if(sort.equals("dayMoney")){
		// sort="DayAmMoney";
		// }else if(sort.equals("monthGross")){
		// sort="MonthAmGross";
		// }else if(sort.equals("monthMoney")){
		// sort="MonthAmMoney";
		// }else if(sort.equals("totalAmm")){
		// sort="MeterCount";
		// }else if(sort.equals("onlineAmm")){
		// sort="UsingMeterCount";
		// }else if(sort.equals("onlineRatio")){
		// sort="onlineRatio";
		// }
		try
		{
			list = cd.listAllAreaData(sort, order, year, month, day);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		// List<TableModel> menu=new ArrayList<TableModel>();
		// menu.add(new TableModel("areaID","id",false,true));
		// menu.add(new TableModel("areaName","校区",true));
		// menu.add(new TableModel("dayGross","当天用电（千瓦时）",true));
		// menu.add(new TableModel("dayMoney","当天电费（元）",true));
		// menu.add(new TableModel("monthGross","当月用电（千瓦时）",true));
		// menu.add(new TableModel("monthMoney","当月电费（元）",true));
		// menu.add(new TableModel("totalAmm","计量电表（个）",true));
		// menu.add(new TableModel("onlineAmm","在线电表（个）",true));
		// menu.add(new TableModel("onlineRatio","在线电表比例",true));
		Gson json = new Gson();

		String result = json.toJson(list);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// System.out.println(result);
		out.println(result);
		out.flush();
		out.close();

	}

	/**
	 * 选择区域之后列出所有建筑
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedAreaFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));

		int areaID = Integer.parseInt(request.getParameter("areaID"));
		String sortName = "Architecture_Name";
		String order = "asc";

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}

		CurrentMeasureDao cd = new CurrentMeasureDao();
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();
		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天

		try
		{
			list = cd.listAllArchData(sortName, order, areaID, year, month, day, thePageCount, thePageIndex);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		int totalCount = cd.getTotal();

		Gson json = new Gson();
		String result = json.toJson(new Object[]
				{ totalCount, list });

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println(result);
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * 选择建筑之后列出所有楼层和电表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedArchFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		CurrentMeasureDao cd = new CurrentMeasureDao();

		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			list = cd.getWhereSelectedArch(archID, year, month, day, thePageCount, thePageIndex);

			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("totalCount", cd.getTotal());
			json.add(jo);

			for (int i = 0; i < list.size(); i++)
			{
				jo = new JSONObject();
				jo.put("archName", list.get(i).getArchName());
				jo.put("ammID", list.get(i).getAmmID());
				jo.put("ammName", list.get(i).getAmmName());
				jo.put("ammFloor", list.get(i).getAmmFloor());
				jo.put("curPower", df.format(list.get(i).getCurPower()));
				jo.put("dayGross", df.format(list.get(i).getDayGross()));
				jo.put("ammState", list.get(i).getAmmState());

				json.add(jo);
			}

			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			// //System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void whenSelectedArchFunction1(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			DecimalFormat numDf = new DecimalFormat("0.00");
			CMArchDao dao = new CMArchDao();
			ArrayList<CMArchModel> list = dao.queryInfoByArchID(thePageCount, thePageIndex, archID, year,  month,  day);
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("totalCount", dao.getTotalCount());
			json.add(jo);

			for (CMArchModel model : list)
			{
				String name = model.getAmMeterName();
				float dayGross = model.getDayGross();
				float RemainValue = model.getRemainValue();
				int AmMeterID = model.getAmMeterID();
				int color = model.getColorStyle();
				int FloorNum = model.getFloorNum();
				int ammState = model.getAmMeterState();
				String zvaluezy = model.getzValuezy();

				jo = new JSONObject();
				jo.put("ammName", name);
				jo.put("ammID", AmMeterID);
				String meterPotocol = (new CMMeterDao()).getMeterPotocol(AmMeterID);
				String costType = (new CMMeterDao()).getMeterCostType(AmMeterID);
				jo.put("meterPotocol", meterPotocol);
				jo.put("costType", costType);
				jo.put("zvaluezy", zvaluezy);
				jo.put("dayGross", numDf.format(dayGross));
				jo.put("RemainValue", numDf.format(RemainValue));
				jo.put("ammState", ammState);
				jo.put("color", color);
				jo.put("ammFloor", FloorNum);
				json.add(jo);

			}

			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			//System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 选择建筑之后列出所有楼层和电表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedFloorFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));

		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		CurrentMeasureDao cd = new CurrentMeasureDao();

		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			list = cd.getWhereSelectedFloor(archID, floor, year, month, day, thePageCount, thePageIndex);

			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("totalCount", cd.getTotal());
			json.add(jo);
			for (int i = 0; i < list.size(); i++)
			{
				jo = new JSONObject();
				jo.put("archName", list.get(i).getArchName());
				jo.put("ammID", list.get(i).getAmmID());
				jo.put("ammName", list.get(i).getAmmName());
				jo.put("ammFloor", list.get(i).getAmmFloor());
				jo.put("curPower", df.format(list.get(i).getCurPower()));
				jo.put("dayGross", df.format(list.get(i).getDayGross()));
				jo.put("ammState", list.get(i).getAmmState());

				json.add(jo);
			}

			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			// //System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void whenSelectedFloorFunction1(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));

		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			CMArchDao dao = new CMArchDao();
			ArrayList<CMArchModel> list = dao.queryInfoByFloorNum(thePageCount, thePageIndex, archID, floor,year,month,day);
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("totalCount", dao.getTotalCount());
			json.add(jo);
			DecimalFormat numDf = new DecimalFormat("0.00");
			for (CMArchModel model : list)
			{
				String name = model.getAmMeterName();
				float dayGross = model.getDayGross();
				float RemainValue = model.getRemainValue();
				int AmMeterID = model.getAmMeterID();
				int color = model.getColorStyle();
				int FloorNum = model.getFloorNum();
				int ammState = model.getAmMeterState();
				String zvaluezy = model.getzValuezy();

				jo = new JSONObject();
				jo.put("ammName", name);
				jo.put("ammID", AmMeterID);
				String meterPotocol = (new CMMeterDao()).getMeterPotocol(AmMeterID);
				String costType = (new CMMeterDao()).getMeterCostType(AmMeterID);
				jo.put("meterPotocol", meterPotocol);
				jo.put("costType", costType);
				jo.put("zvaluezy", zvaluezy);
				jo.put("dayGross", numDf.format(dayGross));
				jo.put("RemainValue", numDf.format(RemainValue));
				jo.put("ammState", ammState);
				jo.put("color", color);
				jo.put("ammFloor", FloorNum);
				json.add(jo);
			}

			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			//System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 获取页面定时的周期
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getTimer(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int time = 0;
		try
		{
			time = GetServerInfo.getServerOverTime() * 2;
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(time);
			out.flush();
			out.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private int queryMeterProtocolByMeterStyleID(int Meter_StyleID)
	{
		String sql = "select TexingValue meterProtocol from TexingValue where MeteStyle_ID=" + Meter_StyleID + " and MeterTexing_ID = 18";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int Protocol = 0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Protocol = rs.getInt("meterProtocol");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return Protocol;
	}
}
