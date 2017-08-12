package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
import com.sf.energy.util.GetServerInfo;
import com.sf.energy.util.GetSystemInfo;
import com.sf.energy.water.manager.current.dao.CurrentMeasureDao;
import com.sf.energy.water.manager.current.model.CurrentManagerModel;
import com.sf.energy.water.manager.current.model.XMLCoder;
import com.sf.energy.water.manager.current.service.SendingXMLCode;
import com.sf.energy.water.manager.current.service.XMLHelper;
import com.sf.energy.water.manager.current.serviceImpl.SendingImmediateCode;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.model.WatermeterModel;
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
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class WaterCurrentManagerServlet extends HttpServlet
{

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
		if ("showBigWaterMeterList".equals(method))
		{
			showBigWaterMeterList(request, response);
		}
		if ("showBigWaterMeterListCurrentDatas".equals(method))
		{
			showBigWaterMeterListCurrentDatas(request, response);
		}

		if ("whenSelectedFloorFunction".equals(method))
		{
			whenSelectedFloorFunction(request, response);
		}

		if ("getTimer".equals(method))
		{
			getTimer(request, response);
		}

		if ("queryWaterFlowValue".equals(method))
		{
			queryWaterFlowValue(request, response);
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
	 * 立即抄表（单项数据，水表示数）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmRightNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat df = new DecimalFormat("#####0.00");

		HttpSession session = request.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		GatherDao gd = new GatherDao();
		WatermeterDao ad = new WatermeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		int queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		WatermeterModel am = new WatermeterModel();
		am = ad.getWatermeterInfoByID(queryAmmID);
		String msg = "";
		Gather gm = new Gather();
		gm = gd.queryByID(am.getGATHER_ID());
		if (am.getISTouChuan() == 1)
		{
			CMMsendCommand aprm = new CMMsendCommand();
			com.sf.energy.prepayment.model.XMLCoder xc = aprm.readWaterMeter(queryAmmID, userID + "");
			try
			{
				com.sf.energy.prepayment.service.SendingXMLCode sxc = new SendingAPCode();
				msg = sxc.sendXMLToServer(xc);
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
			System.out.println(new Date().toLocaleString() + ":服务器发回来的单相xml" + msg);

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
				NodeList gwList = null;
				String task;
				gwList = xmlHelper.getNodeList(msg, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						task = tasknum.getAttribute("tasknum");

						if (task.equals(xc.getTaskNum()))
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
										//System.out.println(list.getLength());
										for (int i = 0; i < list.getLength(); i++)
										{
											Element result1 = (Element) list.item(0);
											//String aaa = result1.getAttribute("Meter_Address");
											//String bbString = "00"+am.getWATERMETER_485ADDRESS().substring(am.getWATERMETER_485ADDRESS().length()-10);
											if (result1.getAttribute("Meter_Address").equals("00"+am.getWATERMETER_485ADDRESS().substring(am.getWATERMETER_485ADDRESS().length()-10)))
											{
												if (Float.parseFloat(result1.getAttribute("ZValueZY")) != -1)
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZValueZY"))));
													jo.put("ammTotalValue",
															df.format(Double.parseDouble(result1.getAttribute("ZValueZY")) * am.getBEILV()
																	+ am.getXIUZHENG()));
													//System.out.println(result1.getAttribute("ValueTime"));
													jo.put("commTime", result1.getAttribute("ValueTime"));
													jo.put("CurrentFlow", df.format(Double.parseDouble(result1.getAttribute("CurrentFlow"))));
													CurrentMeasureDao cd = new CurrentMeasureDao();
													cd.insertAfterCommDan(
															Double.parseDouble(result1.getAttribute("ZValueZY")) * am.getBEILV() + am.getXIUZHENG(),
															result1.getAttribute("ValueTime"), queryAmmID,am.getBEILV(),Double.parseDouble(result1.getAttribute("CurrentFlow")));
													WatermeterDao aDao = new WatermeterDao();
													/**
													 * 更新表状态和最后通讯时间
													 */
													aDao.updateWaterMeterWithLasttimeandState(queryAmmID, result1.getAttribute("ValueTime"));
												} else
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", "-1.00");
													jo.put("ammTotalValue", "-1.00");
													jo.put("commTime", result1.getAttribute("ValueTime"));
													jo.put("CurrentFlow", "-1.00");
												}
											} else
											{
												jo.put("ServerOffline", 5);// 返回表地址错误
												json.add(jo);
												response.setContentType("application/x-json");
												PrintWriter out = response.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
											// jo.put("value",
											// df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
											// jo.put("ammTotalValue",
											// df.format(Double.parseDouble(result1.getAttribute("ZY0"))+am.getXIUZHENG()));
											// jo.put("commTime",
											// result1.getAttribute("ValueTime"));
											// //System.out.println("返回ajax的数据水表示数："
											// +
											// df.format(Double.parseDouble(result1
											// .getAttribute("ZY0"))));
											// //System.out.println("**************************************");

											// 抄表成功插入数据库ammeterData
//											if(Float.parseFloat(result1.getAttribute("ZValueZY"))
//													!= -1)
//											{
//												CurrentMeasureDao cd = new
//														CurrentMeasureDao();
//												cd.insertAfterCommDan(Double.parseDouble(result1.getAttribute("ZValueZY"))*am.getBEILV()
//														+ am.getXIUZHENG(), new
//														Date().toLocaleString(),
//														queryAmmID,am.getBEILV());
//											}
										}
										json.add(jo);
										System.out.println("透传抄表成功："+json.toString());
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
			XMLCoder immXmlCoderD = new XMLCoder();// 单相
			int ammeNum = am.getWATERMETER_POINT();// 10
			String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0202
			immXmlCoderD.setCommandCodeAFN("0C");
			immXmlCoderD.setCommandCodeDA(ammPointNoDA);
			immXmlCoderD.setCommandCodeDT("0110");// 单相
			immXmlCoderD.setTerminalAddress(gd.queryGatherNumByWaterAmmID(queryAmmID));// 002700bb00
			immXmlCoderD.setDatasiteID(String.valueOf(gm.getDatasiteID()));
			immXmlCoderD.setOperCode(userID + "");

			try
			{
				msg = immSending.sendXMLToServer(immXmlCoderD);
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
			System.out.println(new Date().toLocaleString() + ":服务器发回来的单相xml:" + msg);

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
											String watermeter_pointString=result1.getAttribute("AmMeter_Point");
											if (watermeter_pointString.equals(am.getWATERMETER_POINT()+""))
											{
												if (Float.parseFloat(result1.getAttribute("ZY0")) != -1)
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
													jo.put("ammTotalValue",
															df.format(Double.parseDouble(result1.getAttribute("ZY0")) * am.getBEILV()
																	+ am.getXIUZHENG()));
													//System.out.println(result1.getAttribute("ValueTime"));
													jo.put("commTime", result1.getAttribute("ValueTime"));
													jo.put("CurrentFlow", "-1.00");
													CurrentMeasureDao cd = new CurrentMeasureDao();
													cd.insertAfterCommDan(
															Double.parseDouble(result1.getAttribute("ZY0")) * am.getBEILV() + am.getXIUZHENG(),
															result1.getAttribute("ValueTime"), queryAmmID,am.getBEILV(),-1);//非拓传水表抄不到瞬时流量
													WatermeterDao aDao = new WatermeterDao();
													/**
													 * 更新表状态和最后通讯时间
													 */
													aDao.updateWaterMeterWithLasttimeandState(queryAmmID, result1.getAttribute("ValueTime"));
												} else
												{
													jo.put("ServerOffline", -1);// 成功
													jo.put("value", "-1.00");
													jo.put("ammTotalValue", "-1.00");
													jo.put("commTime", result1.getAttribute("ValueTime"));
													jo.put("CurrentFlow", "-1.00");
												}

												// jo.put("value",
												// df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
												// jo.put("ammTotalValue",
												// df.format(Double.parseDouble(result1.getAttribute("ZY0"))+am.getXIUZHENG()));
												// jo.put("commTime",
												// result1.getAttribute("ValueTime"));
												// //System.out.println("返回ajax的数据水表示数："
												// +
												// df.format(Double.parseDouble(result1
												// .getAttribute("ZY0"))));
												// //System.out.println("**************************************");

												// 抄表成功插入数据库ammeterData
//												if (Float.parseFloat(result1.getAttribute("ZY0")) != -1)
//												{
//													CurrentMeasureDao cd = new CurrentMeasureDao();
//													cd.insertAfterCommDan(
//															Double.parseDouble(result1.getAttribute("ZY0")) * am.getBEILV() + am.getXIUZHENG(),
//															new Date().toLocaleString(), queryAmmID,am.getBEILV());
//												}
											}else
											{
												jo.put("ServerOffline", 5);// 返回表地址错误
												json.add(jo);
												response.setContentType("application/x-json");
												PrintWriter out = response.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}
										}
										json.add(jo);
										response.setContentType("application/x-json");
										PrintWriter out = response.getWriter();
										System.out.println("抄表成功："+json.toString());
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
		}

		// }
	}

	/**
	 * 从表中查询当天用水
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryDayValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		CurrentMeasureDao cmd = new CurrentMeasureDao();
		WatermeterModel am = new WatermeterModel();
		WatermeterDao ad = new WatermeterDao();
		Map<String, String> map = new HashMap<String, String>();
		int ammID = Integer.parseInt(request.getParameter("queryAmmID"));
		String time = request.getParameter("time");
		int selectYear = Integer.parseInt(time.substring(0, 4));
		int selectMonth = Integer.parseInt(time.substring(5, 7));
		int selectDay = Integer.parseInt(time.substring(8, 10));

		am = ad.getWatermeterInfoByID(ammID);
		map = cmd.getAmmDayValue(ammID, selectYear, selectMonth, selectDay);

		if (am != null)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("dayValue", df.format(Float.parseFloat(map.get("ZGross"))));
			jo.put("dayMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
			json.add(jo);
			//System.out.println("当日用水："+json.toString());
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 从表中查询当月用水
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryMonthValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		CurrentMeasureDao cmd = new CurrentMeasureDao();
		WatermeterModel am = new WatermeterModel();
		WatermeterDao ad = new WatermeterDao();
		Map<String, String> map = new HashMap<String, String>();
		int ammID = Integer.parseInt(request.getParameter("queryAmmID"));
		String time = request.getParameter("time");
		int selectYear = Integer.parseInt(time.substring(0, 4));
		int selectMonth = Integer.parseInt(time.substring(5, 7));
		int selectDay = Integer.parseInt(time.substring(8, 10));

		am = ad.getWatermeterInfoByID(ammID);
		map = cmd.getAmmMonthValue(ammID, selectYear, selectMonth);

		if (am != null)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("monthValue", df.format(Float.parseFloat(map.get("ZGross"))));
			jo.put("monthMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
			json.add(jo);
			//System.out.println("当月用水:"+json.toString());
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
	}

	/**
	 * 立即抄表（三项数据，水表功率）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmSanRightNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GatherDao gd = new GatherDao();
		WatermeterDao ad = new WatermeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		int queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		WatermeterModel am = new WatermeterModel();
		am = ad.getWatermeterInfoByID(queryAmmID);
		if (am != null)
		{
			// 离线
			if (am.getWATERMETER_STAT() == 0)
			{
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("ErrorInfo", 0);
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} else
			// 在线
			{
				int ammeNum = am.getWATERMETER_POINT();// 2
				String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0201

				// 三相
				XMLCoder immXmlCoderS = new XMLCoder();
				immXmlCoderS.setCommandCodeAFN("0C");
				immXmlCoderS.setCommandCodeDA(ammPointNoDA);
				immXmlCoderS.setCommandCodeDT("0103");// 三相
				immXmlCoderS.setTerminalAddress(gd.queryGatherNumByAmmID(queryAmmID));// 002700bb00

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
				}

				// //System.out.println(new Date().toLocaleString() +
				// ":服务器发回来的三相xml"
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

						DecimalFormat df = new DecimalFormat("#.00");

						jo.put("valueGonglv", df.format(Double.parseDouble(result1.getAttribute("PowerZY"))));
						// //System.out.println("返回ajax的数据valueGonglv："
						// + df.format(Double.parseDouble(result1
						// .getAttribute("PowerZY"))));
						// //System.out.println("**************************************");
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

	/**
	 * 从表中查询水表的相关属性
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryValueFromTable(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat dfd = new DecimalFormat("#####0.00");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int queryAmmID = 0;
		CurrentMeasureDao cd = new CurrentMeasureDao();
		Map<String, String> ammMap = new HashMap<String, String>();
		if (request.getParameter("queryAmmID") != null)
		{
			queryAmmID = Integer.parseInt(request.getParameter("queryAmmID"));
		}
		String queryMsg = queryAmmID + "";
		String sidebarItem = request.getParameter("sidebarItem");// 4C01 预付费水表监测
		if (!"".equals(sidebarItem) && sidebarItem != null)
		{
			queryMsg = queryAmmID + "/" + sidebarItem;
		}

		WatermeterDao ad = new WatermeterDao();
		AmmPriceDao apd = new AmmPriceDao();
		WatermeterModel am = new WatermeterModel();
		am = ad.getWatermeterInfoByCondiction(queryMsg);

		Calendar cal = Calendar.getInstance();// 使用日历类
		int selectYear = cal.get(Calendar.YEAR);// 得到年
		int selectMonth = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int selectDay = cal.get(Calendar.DAY_OF_MONTH);// 得到天

		ammMap = cd.getAmmValue(queryAmmID, selectYear, selectMonth, selectDay);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		// 水表示数
		if(Double.parseDouble(ammMap.get("ammValue"))!=0){
			jo.put("ammValue", dfd.format((Double.parseDouble(ammMap.get("ammValue")) - am.getXIUZHENG()) / am.getBEILV()));
		
		}else{
			jo.put("ammValue", "--");
			
		}
		jo.put("ammLastTime", am.getLASTTIME());
		// 关联属性
		jo.put("ammNum", am.getWATERMETER_485ADDRESS());
		jo.put("partmentName", am.getPartmentName());
		jo.put("partmentID", am.getPARTMENT());
		jo.put("isComputation", am.getISCOMPUTATION());// 计量属性
		jo.put("FX", am.getUseAmStyleName());// 分项
		jo.put("XZ", am.getUseStyleName());// 性质
		jo.put("beilv", am.getBEILV());
		jo.put("ammPrice", apd.queryByID(am.getPRICE_ID()).getPriceValue());
		jo.put("ammPriceID", am.getPRICE_ID());
		jo.put("costCheck", am.getCOSTCHECK());
		jo.put("standByCheck", am.getSTANDBYCHECK());
		jo.put("ammAddress", am.getCONSUMERADDRESS());
		jo.put("archName", am.getArchName());
		jo.put("ammArchID", am.getARCHITECTURE_ID());
		jo.put("ammFloor", am.getFLOOR());
		jo.put("ammName", am.getWATERMETER_NAME());
		jo.put("ammArea", am.getAreaName());
		jo.put("ammAreaID", am.getAREA_ID());

		jo.put("ammState", am.getWATERMETER_STAT());
		jo.put("ammTotalValue", dfd.format(Double.parseDouble(ammMap.get("ammValue"))));

		// 为了编辑输出的信息
		jo.put("ammID", am.getWATERMETER_ID());
		jo.put("meteStyle", am.getMeterStyleName());// 表计类型
		jo.put("isOfflineWaring", am.getISOFFALARM());// 报警
		jo.put("isTotalAmm", am.getISCOUNTMETER());
		jo.put("styleNameYJZX", am.getUseAmStyleNameJYZX());// 一级子项
		jo.put("leakingValue", am.getWLEAKAGEVALUE());// 漏水参数
		jo.put("leakingCheck", am.getWLEAKAGECHECK());// 漏水排查
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println("集中监测水表json："+json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 72小时用水
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

		int TotalAm_ammid = 0;
		String TotalAmTime1 = request.getParameter("dateTime");
		if (TotalAmTime1 != null)
		{
			int year1 = Integer.parseInt(TotalAmTime1.substring(0, 4));
			int month1 = Integer.parseInt(TotalAmTime1.substring(5, 7));
			int day1 = Integer.parseInt(TotalAmTime1.substring(8, 10));

			start.setYear(year1 - 1900);
			start.setMonth(month1 - 1);
			//start.setDate(day1 - 1);
			start.setDate(day1 - 2);
			start.setHours(0);
			start.setMinutes(0);
			start.setSeconds(0);

			end.setYear(year1 - 1900);
			end.setMonth(month1 - 1);
			end.setDate(day1);
		}

		// //System.out.println(start);
		// //System.out.println(end);
		String ammid = request.getParameter("AmMeterID");
		if (ammid != null)
		{
			TotalAm_ammid = Integer.parseInt(ammid);
		}
		WaterReportHelperImpl rhi_TotalAm = new WaterReportHelperImpl();
		List<WaterMeterMataData> TotalAm_list = null;
		Map<String, Float> map = new HashMap<String, Float>();
		JSONObject jo = new JSONObject();
		JSONArray json = new JSONArray();
		String hour;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		try
		{
			TotalAm_list = rhi_TotalAm.getWaterMeterBetween(TotalAm_ammid, start, end);
			for (WaterMeterMataData n : TotalAm_list)
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

			// //System.out.println(json.toString());

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		out.println(json.toString());
		out.flush();
		out.close();
	}

	private String getHourString(Date recordTimeDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:00");
		String hour = df.format(recordTimeDate);
		return hour;
	}

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
	 * 获取用水分项中的条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueFX(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();
		ArrayList<DictionaryValueModel> listFX = dvd.listDictionaryValueByName("用水分项");

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
	 * 获取用水性质的条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueXZ(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();

		ArrayList<DictionaryValueModel> list = dvd.listDictionaryValueByName("用水性质");

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
		// list=dvd.listDictionaryValueByName("水表类型");

		MeterStyleDao msd = new MeterStyleDao();
		ArrayList<MeterStyle> list = msd.displayAllType("水表");

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
	 * 水的一级子项暂时不编辑
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDictionaryValueYJFX(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DictionaryValueDao dvd = new DictionaryValueDao();

		ArrayList<DictionaryValueModel> list = dvd.listDictionaryValueByName("能耗编码一级子项");

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
	 * 获取水表价格条目
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getPriceValue(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AmmPriceDao apd = new AmmPriceDao();
		ArrayList<AmmPriceModel> list = new ArrayList<AmmPriceModel>();
		list = apd.listAllPriceValue("W");
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
	 * 编辑水表的关联属性
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
		int useAmmStyleID = Integer.parseInt(request.getParameter("useAmmStyleID"));
		int isComputation = Integer.parseInt(request.getParameter("isComputation"));
		String ammFX = request.getParameter("ammFX");
		int isLeakCheck = Integer.parseInt(request.getParameter("isLeakCheck"));
		float leakingValue = Float.parseFloat(request.getParameter("leakingValue"));
		// String ammYJZX = request.getParameter("ammYJZX");

		WatermeterDao ad = new WatermeterDao();
		if (!"-1".equals(ammFX))// 说明修改了分项
		{
			ad.editFX(ammeterID, ammFX);
		}

		WatermeterModel am = ad.getWatermeterInfoByID(ammeterID);// 不编辑的则不改变属性，只改变set的属性
		am.setWATERMETER_ID(ammeterID);
		am.setAREA_ID(areaID);
		am.setPARTMENT(partmentID);
		am.setARCHITECTURE_ID(archID);

		if (floor != -1)// 说明修改了楼层
		{
			am.setFLOOR(floor);
		}
		am.setMETESTYLE_ID(metestyleID);
		am.setPRICE_ID(priceID);
		am.setISOFFALARM(isOffine);
		am.setISCOUNTMETER(isTotal);
		am.setUSEAMSTYLE(useAmmStyleID);
		am.setISCOMPUTATION(isComputation);
		am.setWLEAKAGECHECK(isLeakCheck);
		am.setWLEAKAGEVALUE(leakingValue);
		String resultInfo = null;
		if (ad.updateWatermeter(am) == true)
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
		String sortName = "Area_Name";
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
		try
		{
			list = cd.listAllAreaData(sortName, order, year, month, day);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		Gson json = new Gson();
		String result = json.toJson(list);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		// //System.out.println(result);
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
	 * 选择建筑之后列出所有楼层和水表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedArchFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
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
				jo.put("currentValue", df.format(list.get(i).getCurrentValue()));
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

	/**
	 * 选择建筑之后列出所有楼层和水表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void showBigWaterMeterList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		Integer thePageCount = Integer.parseInt(request.getParameter("EnergyMeasurePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("EnergyMeasurePageIndex"));
		Integer watermeterid = Integer.parseInt(request.getParameter("watermeterid"));

		String tableName = request.getParameter("tableName");
		String order = request.getParameter("order");

		String orderString = "order by " + tableName + " " + order;
		CurrentMeasureDao cd = new CurrentMeasureDao();

		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			list = cd.getWhereSelectedBigSchool(0, year, month, day, thePageCount, thePageIndex, orderString);

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
				jo.put("currentValue", df.format(list.get(i).getCurrentValue()));
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

	/**
	 * 选择建筑之后列出所有楼层和水表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void showBigWaterMeterListCurrentDatas(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		Integer thePageCount = Integer.parseInt(request.getParameter("PageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("PageIndex"));

		Integer watermeterid = Integer.parseInt(request.getParameter("watermeterid"));

		String tableName = request.getParameter("tableName");
		String order = request.getParameter("order");

		String orderString = "order by " + tableName + " " + order;
		CurrentMeasureDao cd = new CurrentMeasureDao();

		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		try
		{
			list = cd.getWhereSelectedBigSchool(watermeterid, year, month, day, thePageCount, thePageIndex, orderString);

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
				jo.put("currentValue", df.format(list.get(i).getCurrentValue()));
				jo.put("currentFlow", df.format(list.get(i).getCurPower()));
				jo.put("lastDataTime", list.get(i).getLastDataTime());
				if (list.get(i).getAmmState() == 1)
				{
					jo.put("ammState", "在线");
				} else
				{
					jo.put("ammState", "离线");
				}

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

	/**
	 * 选择建筑之后列出所有楼层和水表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedFloorFunction(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
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
				jo.put("currentValue", df.format(list.get(i).getCurrentValue()));
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
			time = GetServerInfo.getServerOverTime();
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

	/**
	 * 获水表取瞬时流量
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryWaterFlowValue(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String value = "";
		CurrentMeasureDao cd = new CurrentMeasureDao();
		try
		{
			value = cd.queryWaterFlowValue(id);
			// //System.out.println("瞬时流量："+value);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(value);
			out.flush();
			out.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
