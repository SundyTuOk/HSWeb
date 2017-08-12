package com.sf.energy.prepayment.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.service.SendingXMLCode;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.dao.ExchangeMeterDao;
import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.ExchangeMeterModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.system.maintenance.OnlineGather;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class ExchangeMeterRecordServlet extends HttpServlet
{
	//private ReentrantLock lock = new ReentrantLock();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String method = req.getParameter("method");
		// System.out.println("method:" + method);
		// 换表记录
		if ("get_info_EMR".equals(method))
			get_info_EMR(req, resp);

		if ("new_info_EMR".equals(method))
			new_info_EMR(req, resp);

		if ("get_EditMeterInfo_EMR".equals(method))
			get_EditMeterInfo_EMR(req, resp);

		if ("get_oldMeterInfo_EMR".equals(method))
			get_oldMeterInfo_EMR(req, resp);

		if ("edit_info_EMR".equals(method))
			edit_info_EMR(req, resp);

		if ("del_info_EMR".equals(method))
			del_info_EMR(req, resp);

		if ("EMR_out_btn_click".equals(method))
			EMR_out_btn_click(req, resp);
		// 下发换表命令
		if ("exchangeMeter".equals(method))
			exchangeMeter(req, resp);

	}

	private void exchangeMeter(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		ReentrantLock lock = new ReentrantLock();
		HttpSession session = req.getSession();
		String userID = "0";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		// System.out.println("************************"+userID+"**********************");

		String amMeterID = req.getParameter("amMeterID");
		String isZQ = req.getParameter("isZQ");
		String gross = req.getParameter("gross");
		
		//System.out.println(new AmmeterDao().checkIsKaiHu(Integer.parseInt(amMeterID)));
		
		OnlineGather og = new OnlineGather();
		
		CMMsendCommand csc = new CMMsendCommand();
		csc.getPara(Integer.parseInt(amMeterID));
		SendingXMLCode sxc = new SendingAPCode();
		XMLCoder xc = new XMLCoder();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		String sql = "select Gather_ID,Gather_Address from V_AmMeter where AmMeter_ID=" + amMeterID;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		int flag = 0;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 清空网关
				int gatherID = rs.getInt("Gather_ID");
				String gatherAddress = rs.getString("Gather_Address");

				for (int i = 0; i < 2; i++)
				{
					xc = csc.cleanGather(gatherID, i + 2);
					try
					{
						lock.lock();
						sxc.sendXMLToServer(xc);
					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
				}
				if (flag == 1)
				{
					ConnDB.release(conn, ps, rs);
					return;
				}
				// 下表号
				String msg = "";
				String[] sendList = og.sendMeters(gatherID, userID);
				for (int i = 0; i < sendList.length; i++)
				{
					try
					{
						lock.lock();
						msg = og.sendCommand(sendList[i]);
					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
				}
				if (flag == 1)
				{
					ConnDB.release(conn, ps, rs);
					return;
				}

				// 总清
				if ("1".equals(isZQ))
				{
					xc = csc.zongQing(Integer.parseInt(amMeterID), userID);
					if (csc.getMeterProtocol().equals("1"))
					{
						try
						{
							lock.lock();
							String zqStr = sxc.sendXMLToServer(xc);
							// System.out.println(zqStr);

							NodeList gwList=null;
							String task;
							gwList = getNodeList(zqStr, "GW");
							for (int m = 0; m < gwList.getLength(); m++)
							{
								Element tasknum = (Element) gwList.item(m);
								if (tasknum.hasAttribute("tasknum"))
								{
									task = tasknum.getAttribute("tasknum");
									if(task.equals(xc.getTaskNum())){
										NodeList list = null;
										String err;
										String errMsg = "总清失败！";
										list = getNodeList(zqStr, "commandback");

										Element commandBack = (Element) list.item(0);
										if (commandBack.hasAttribute("error"))
										{
											err = commandBack.getAttribute("error");
											errMsg = commandBack.getAttribute("errormessage");
											// 失败
											if (err.equals("1"))
											{
												flag = 1;
												jo = new JSONObject();
												jo.put("isError", "3");
												jo.put("message", errMsg);
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}else if(err.equals("0")){
												NodeList result = commandBack.getElementsByTagName("result");
												for (int j = 0; j < result.getLength(); j++)
												{
													Element resultElement = (Element) result.item(j);

													String dataid = "";
													String meterAddress = "";
													boolean isExecFlag = true;
													if(resultElement.hasAttribute("DataID")){
														dataid = resultElement.getAttribute("DataID");
														if(resultElement.hasAttribute("Meter_Address")){
															meterAddress = resultElement.getAttribute("Meter_Address");
														}
														if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
															isExecFlag = false;
														}
													}else {
														if(resultElement.hasAttribute("Meter_Address")){
															meterAddress = resultElement.getAttribute("Meter_Address");
														}
														if(!meterAddress.equals(xc.getMeterAddress())){
															isExecFlag = false;
														}
													}
													if(!isExecFlag){
														jo = new JSONObject();
														jo.put("isError", "7");
														jo.put("message", "返回表地址或命令码错误！");

														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
											}
										}
									}else{
										jo = new JSONObject();
										jo.put("isError", "6");
										jo.put("message", "任务编号错误！");

										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									}
								}
							}


						} catch (SocketTimeoutException e)
						{
							flag = 1;
							jo = new JSONObject();
							jo.put("isError", "2");
							jo.put("message", "命令超时重发！");

							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							out.println(json.toString());
							out.flush();
							out.close();
						} finally
						{
							lock.unlock();
						}

					} else
					{//07
						// 设置电表场内模式
						xc = csc.setMode(Integer.parseInt(amMeterID), userID, 0);
						try
						{
							lock.lock();
							String zqStr = null;
							zqStr = sxc.sendXMLToServer(xc);
							System.out.println("收到回复：" + zqStr);
							String err = null;
							String errMsg = "总清失败！";

							if(zqStr==null)
								return;

							NodeList gwList=null;
							String task;
							gwList = getNodeList(zqStr, "GW");
							for (int m = 0; m < gwList.getLength(); m++)
							{
								Element tasknum = (Element) gwList.item(m);
								if (tasknum.hasAttribute("tasknum"))
								{
									task = tasknum.getAttribute("tasknum");
									if(task.equals(xc.getTaskNum())){
										NodeList list = null;

										err = getAttrValue(zqStr, "commandback", "error");
										errMsg = getAttrValue(zqStr, "commandback", "errormessage");

										if (!err.equals("0"))
										{
											flag = 1;
											jo = new JSONObject();
											jo.put("isError", "3");
											jo.put("message", errMsg);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										} else
										{
											// 总清
											zqStr = null;
											xc = csc.zongQing(Integer.parseInt(amMeterID), userID);
											zqStr = sxc.sendXMLToServer(xc);
											System.out.println("收到回复：" + zqStr);

											if(zqStr == null)
												return;

											gwList=null;
											gwList = getNodeList(zqStr, "GW");
											for (m = 0; m < gwList.getLength(); m++)
											{
												tasknum = (Element) gwList.item(m);
												if (tasknum.hasAttribute("tasknum"))
												{
													task = tasknum.getAttribute("tasknum");
													if(task.equals(xc.getTaskNum())){

														err = getAttrValue(zqStr, "commandback", "error");
														errMsg = getAttrValue(zqStr, "commandback", "errormessage");

														if (!err.equals("0"))
														{
															flag = 1;
															jo = new JSONObject();
															jo.put("isError", "3");
															jo.put("message", errMsg);
															json.add(jo);
															resp.setContentType("application/x-json");

															PrintWriter out = resp.getWriter();
															out.println(json.toString());
															out.flush();
															out.close();
														} else
														{
															list = getNodeList(zqStr, "commandback");
															Element commandBack = (Element) list.item(0);
															NodeList result = commandBack.getElementsByTagName("result");
															Element resultElement = (Element) result.item(0);

															String dataid = "";
															String meterAddress = "";
															boolean isExecFlag = true;
															if(resultElement.hasAttribute("DataID")){
																dataid = resultElement.getAttribute("DataID");
																if(resultElement.hasAttribute("Meter_Address")){
																	meterAddress = resultElement.getAttribute("Meter_Address");
																}
																if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																	isExecFlag = false;
																}
															}else {
																if(resultElement.hasAttribute("Meter_Address")){
																	meterAddress = resultElement.getAttribute("Meter_Address");
																}
																if(!meterAddress.equals(xc.getMeterAddress())){
																	isExecFlag = false;
																}
															}
															if(isExecFlag){
																// 设置出厂模式
																zqStr = null;
																xc = csc.setMode(Integer.parseInt(amMeterID), userID, 1);
																zqStr = sxc.sendXMLToServer(xc);
																System.out.println("收到回复：" + zqStr);

																if(zqStr == null)
																	return;

																gwList=null;
																gwList = getNodeList(zqStr, "GW");
																for (m = 0; m < gwList.getLength(); m++)
																{
																	tasknum = (Element) gwList.item(m);
																	if (tasknum.hasAttribute("tasknum"))
																	{
																		task = tasknum.getAttribute("tasknum");
																		if(task.equals(xc.getTaskNum())){

																			err = getAttrValue(zqStr, "commandback", "error");
																			errMsg = getAttrValue(zqStr, "commandback", "errormessage");

																			if (!err.equals("0"))
																			{
																				flag = 1;
																				jo = new JSONObject();
																				jo.put("isError", "3");
																				jo.put("message", errMsg);
																				json.add(jo);
																				resp.setContentType("application/x-json");

																				PrintWriter out = resp.getWriter();
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			}
																		}else{
																			jo = new JSONObject();
																			jo.put("isError", "6");
																			jo.put("message", "任务编号错误！");

																			json.add(jo);
																			resp.setContentType("application/x-json");

																			PrintWriter out = resp.getWriter();
																			out.println(json.toString());
																			out.flush();
																			out.close();
																		}
																	}
																}


															}else{
																jo = new JSONObject();
																jo.put("isError", "7");
																jo.put("message", "返回表地址或命令码错误！");

																json.add(jo);
																resp.setContentType("application/x-json");

																PrintWriter out = resp.getWriter();
																out.println(json.toString());
																out.flush();
																out.close();
															}


														}
													}else{
														jo = new JSONObject();
														jo.put("isError", "6");
														jo.put("message", "任务编号错误！");

														json.add(jo);
														resp.setContentType("application/x-json");

														PrintWriter out = resp.getWriter();
														out.println(json.toString());
														out.flush();
														out.close();
													}
												}
											}



										}
									}else{
										jo = new JSONObject();
										jo.put("isError", "6");
										jo.put("message", "任务编号错误！");

										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									}
								}
							}



						} catch (SocketTimeoutException e)
						{
							flag = 1;
							jo = new JSONObject();
							jo.put("isError", "2");
							jo.put("message", "命令超时重发！");

							json.add(jo);
							resp.setContentType("application/x-json");

							PrintWriter out = resp.getWriter();
							out.println(json.toString());
							out.flush();
							out.close();
						} finally
						{
							lock.unlock();
						}

					}
				}
				if (flag == 1)
				{
					ConnDB.release(conn, ps, rs);
					return;
				}
				if(new AmmeterDao().checkIsKaiHu(Integer.parseInt(amMeterID))){
					//若表之前开过户 则启动预付费
					// 启动预付费
					xc = csc.kaiHuCommand(Integer.parseInt(amMeterID), userID);
					try
					{
						lock.lock();
						String str = sxc.sendXMLToServer(xc);
						// System.out.println(str);

						NodeList gwList=null;
						String task;
						gwList = getNodeList(str, "GW");
						for (int m = 0; m < gwList.getLength(); m++)
						{
							Element tasknum = (Element) gwList.item(m);
							if (tasknum.hasAttribute("tasknum"))
							{
								task = tasknum.getAttribute("tasknum");
								if(task.equals(xc.getTaskNum())){
									NodeList list = null;
									String err;
									String errMsg = "启动预付费失败！";
									list = getNodeList(str, "commandback");

									Element commandBack = (Element) list.item(0);
									if (commandBack.hasAttribute("error"))
									{
										err = commandBack.getAttribute("error");
										errMsg = commandBack.getAttribute("errormessage");
										// 失败
										if (err.equals("1"))
										{
											flag = 1;
											jo = new JSONObject();
											jo.put("isError", "4");
											jo.put("message", errMsg);
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										}else if(err.equals("0")){
											NodeList result = commandBack.getElementsByTagName("result");
											Element resultElement = (Element) result.item(0);

											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(!isExecFlag){
												jo = new JSONObject();
												jo.put("isError", "7");
												jo.put("message", "返回表地址或命令码错误！");

												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}

										}
									}
								}else{
									jo = new JSONObject();
									jo.put("isError", "6");
									jo.put("message", "任务编号错误！");

									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}


					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
					if (flag == 1)
					{
						ConnDB.release(conn, ps, rs);
						return;
					}
				}
				if (!"".equals(gross) && Float.parseFloat(gross) > 0)
				{
					// 下发剩余电量
					xc = csc.sendTGross(Integer.parseInt(amMeterID), gross, "0", userID);
					try
					{
						lock.lock();
						sxc.sendXMLToServer(xc);
					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}

					xc = csc.readMeter(Integer.parseInt(amMeterID), userID);
					try
					{
						lock.lock();
						String str = sxc.sendXMLToServer(xc);
						System.out.println("收到的报文的回复是：" + str);

						NodeList gwList=null;
						String task;
						gwList = getNodeList(str, "GW");
						for (int m = 0; m < gwList.getLength(); m++)
						{
							Element tasknum = (Element) gwList.item(m);
							if (tasknum.hasAttribute("tasknum"))
							{
								task = tasknum.getAttribute("tasknum");
								if(task.equals(xc.getTaskNum())){
									NodeList list = null;
									String err;
									list = getNodeList(str, "commandback");

									Element commandBack = (Element) list.item(0);
									if (commandBack.hasAttribute("error"))
									{
										err = commandBack.getAttribute("error");
										// 失败
										if (err.equals("1"))
										{
											flag = 1;
											jo = new JSONObject();
											jo.put("isError", "1");
											json.add(jo);
											resp.setContentType("application/x-json");

											PrintWriter out = resp.getWriter();
											out.println(json.toString());
											out.flush();
											out.close();
										} else if (err.equals("0")) // 成功
										{

											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											DecimalFormat df = new DecimalFormat("0.##");
											NodeList result = commandBack.getElementsByTagName("result");

											Element resultElement = (Element) result.item(0);

											String dataid = "";
											String meterAddress = "";
											boolean isExecFlag = true;
											if(resultElement.hasAttribute("DataID")){
												dataid = resultElement.getAttribute("DataID");
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xc.getMeterAddress())){
													isExecFlag = false;
												}
											}
											if(isExecFlag){
												String readTime = resultElement.getAttribute("ValueTime");
												String SYValue = "0.00", TotoleUsed = "0.00", TZValue = "0.00";
												float ZValueZY = -1, HSYValue = -1, HTZValue = -1;
												if ("1".equals(csc.getMeterProtocol()))// 97
												{
													SYValue = resultElement.getAttribute("SYValue");
													TZValue = resultElement.getAttribute("TZValue");
													TotoleUsed = resultElement.getAttribute("TotoleUsed");
													ZValueZY = Float.parseFloat(TotoleUsed) * csc.getBeiLv();
													SYValue = df.format(df.parse(SYValue));
													HSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
													HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
												} else
													// 07
												{
													SYValue = resultElement.getAttribute("SYMoney");
													SYValue = df.format(df.parse(SYValue));
													HSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();

													xc = csc.readMeter07(Integer.parseInt(amMeterID), userID, "00010000");
													if (xc != null)
													{

														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);

														gwList=null;
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");
																	for (int i = 0; i < list.getLength(); i++)
																	{
																		commandBack = (Element) list.item(i);
																		if (commandBack.hasAttribute("error"))
																		{
																			err = commandBack.getAttribute("error");
																			// 失败
																			if (err.equals("1"))
																			{
																				String errormessage = commandBack.getAttribute("errormessage");
																				jo = new JSONObject();
																				jo.put("isError", "3");
																				jo.put("errormessage", errormessage);
																				json.add(jo);
																				resp.setContentType("application/x-json");

																				PrintWriter out = resp.getWriter();
																				// System.out.println(json.toString());
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			} else if (err.equals("0")) // 成功
																			{
																				result = commandBack.getElementsByTagName("result");
																				resultElement = (Element) result.item(0);

																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					TotoleUsed = getAttrValue(str, "result", "ZValueZY");
																					TotoleUsed = df.format(df.parse(TotoleUsed));
																					ZValueZY = Float.parseFloat(TZValue) * csc.getBeiLv();
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					jo.put("message", "返回表地址或命令码错误！");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}

																			}
																		}
																	}

																	/*err = getAttrValue(str, "commandback", "error");
																	if ("0".equals(err))
																	{
																		TZValue = getAttrValue(str, "result", "TZMoney");
																		TZValue = df.format(df.parse(TZValue));
																		HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
																	}*/
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	jo.put("message", "任务编号错误！");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}


													}

													xc = csc.readMeter07(Integer.parseInt(amMeterID), userID, "00900201");// 07当前透支金额
													if (xc != null)
													{
														str = sxc.sendXMLToServer(xc);
														System.out.println("收到的报文的回复是：" + str);

														gwList=null;
														gwList = getNodeList(str, "GW");
														for (m = 0; m < gwList.getLength(); m++)
														{
															tasknum = (Element) gwList.item(m);
															if (tasknum.hasAttribute("tasknum"))
															{
																task = tasknum.getAttribute("tasknum");
																if(task.equals(xc.getTaskNum())){
																	list = getNodeList(str, "commandback");
																	for (int i = 0; i < list.getLength(); i++)
																	{
																		commandBack = (Element) list.item(i);
																		if (commandBack.hasAttribute("error"))
																		{
																			err = commandBack.getAttribute("error");
																			// 失败
																			if (err.equals("1"))
																			{
																				String errormessage = commandBack.getAttribute("errormessage");
																				jo = new JSONObject();
																				jo.put("isError", "3");
																				jo.put("errormessage", errormessage);
																				json.add(jo);
																				resp.setContentType("application/x-json");

																				PrintWriter out = resp.getWriter();
																				// System.out.println(json.toString());
																				out.println(json.toString());
																				out.flush();
																				out.close();
																			} else if (err.equals("0")) // 成功
																			{
																				result = commandBack.getElementsByTagName("result");
																				resultElement = (Element) result.item(0);

																				dataid = "";
																				meterAddress = "";
																				isExecFlag = true;
																				if(resultElement.hasAttribute("DataID")){
																					dataid = resultElement.getAttribute("DataID");
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if((!dataid.equals(xc.getDataid())) || (!meterAddress.equals(xc.getMeterAddress()))){
																						isExecFlag = false;
																					}
																				}else {
																					if(resultElement.hasAttribute("Meter_Address")){
																						meterAddress = resultElement.getAttribute("Meter_Address");
																					}
																					if(!meterAddress.equals(xc.getMeterAddress())){
																						isExecFlag = false;
																					}
																				}
																				if(isExecFlag){
																					TZValue = getAttrValue(str, "result", "TZMoney");
																					TZValue = df.format(df.parse(TZValue));
																					HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
																				}else{
																					jo = new JSONObject();
																					jo.put("isError", "7");
																					jo.put("message", "返回表地址或命令码错误！");
																					json.add(jo);
																					resp.setContentType("application/x-json");

																					PrintWriter out = resp.getWriter();
																					out.println(json.toString());
																					out.flush();
																					out.close();
																				}

																			}
																		}
																	}

																	/*err = getAttrValue(str, "commandback", "error");
																	if ("0".equals(err))
																	{
																		TZValue = getAttrValue(str, "result", "TZMoney");
																		TZValue = df.format(df.parse(TZValue));
																		HTZValue = Float.parseFloat(TZValue) * csc.getBeiLv();
																	}*/
																}else{
																	jo = new JSONObject();
																	jo.put("isError", "6");
																	jo.put("message", "任务编号错误！");
																	json.add(jo);
																	resp.setContentType("application/x-json");

																	PrintWriter out = resp.getWriter();
																	out.println(json.toString());
																	out.flush();
																	out.close();
																}
															}
														}



													}
												}

												if ("-1".equals(SYValue))
												{
													jo = new JSONObject();
													jo.put("isError", "1");
													jo.put("message", "网关抄读失败！");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													out.println(json.toString());
													out.flush();
													out.close();
												} else
												{
													CMMeterDao cmd = new CMMeterDao();
													cmd.SerSaveData(Integer.parseInt(amMeterID), ZValueZY, HSYValue, HTZValue, readTime);
													jo = new JSONObject();
													jo.put("isError", "0");
													json.add(jo);
													resp.setContentType("application/x-json");

													PrintWriter out = resp.getWriter();
													out.println(json.toString());
													out.flush();
													out.close();
												}
											}else{
												jo = new JSONObject();
												jo.put("isError", "7");
												jo.put("message", "返回表地址或命令码错误！");

												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
											}


										}
									}
								}else{
									jo = new JSONObject();
									jo.put("isError", "6");
									jo.put("message", "任务编号错误！");

									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								}
							}
						}

					} catch (SocketTimeoutException e)
					{
						flag = 1;
						jo = new JSONObject();
						jo.put("isError", "2");
						jo.put("message", "命令超时重发！");

						json.add(jo);
						resp.setContentType("application/x-json");

						PrintWriter out = resp.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} finally
					{
						lock.unlock();
					}
				}

			} else
			{
				jo = new JSONObject();
				jo.put("isError", "1");
				json.add(jo);
				resp.setContentType("application/x-json");

				PrintWriter out = resp.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*	Statement sm = ConnDB.getConnection().createStatement();
	//	ResultSet rs = sm.executeQuery(sql);


		if (rs != null)
			rs.close();
		if (sm != null)
			sm.close();*/
	}

	/**
	 * 导出记录
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 *             20150722修改
	 */
	private void EMR_out_btn_click(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String selectInfo = "";
		String queryMsg = "";

		selectInfo += "all|";
		String order = " order by ID asc";

		ExchangeMeterDao emd = new ExchangeMeterDao();
		ArrayList<ExchangeMeterModel> list = new ArrayList<ExchangeMeterModel>();
		list = emd.queryInfo(0, 0, selectInfo, queryMsg, false, order);

		String filePath = "/img/replace.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);
		// System.out.println("XlsPath:"+XlsPath);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("区域");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("旧表地址");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("新表地址");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("旧表底数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue("新表底数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("旧表剩余电量（度）");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("旧表剩余电量（元）");
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setCellValue("新表剩余电量（度）");
		cell.setCellStyle(style);

		cell = row.createCell((short) 11);
		cell.setCellValue("新表剩余电量（元）");
		cell.setCellStyle(style);

		cell = row.createCell((short) 12);
		cell.setCellValue("总清");
		cell.setCellStyle(style);

		cell = row.createCell((short) 13);
		cell.setCellValue("操作员");
		cell.setCellStyle(style);

		cell = row.createCell((short) 14);
		cell.setCellValue("换表时间");
		cell.setCellStyle(style);
		int i = 0;
		for (ExchangeMeterModel emm : list)
		{
			i++;
			String meterPotocol = "1";// 默认97
			String sqlString = "select TexingValue from TEXINGVALUE where  METERTEXING_id=18 and METESTYLE_ID=(SELECT METESTYLE_ID from AMMETER WHERE AMMETER_ID="
					+ emm.getAmMeter_ID() + ")";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sqlString);
				rs = ps.executeQuery();
				if (rs.next())
				{
					meterPotocol = rs.getString("TexingValue");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			/*PreparedStatement pst = ConnDB.getConnection().prepareStatement(sqlString);
			ResultSet rSet = pst.executeQuery();
			if (rSet.next())
			{
				meterPotocol = rSet.getString("TexingValue");
			}
			if (rSet != null)
				rSet.close();
			if (pst != null)
				pst.close();*/

			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(emm.getArea_Name());
			row.createCell((short) 1).setCellValue(emm.getArchitecture_Name());
			row.createCell((short) 2).setCellValue(emm.getFloorName());
			row.createCell((short) 3).setCellValue(emm.getAmMeter_Name());
			row.createCell((short) 4).setCellValue(emm.getOldAddress());
			row.createCell((short) 5).setCellValue(emm.getNewAddress());
			row.createCell((short) 6).setCellValue(emm.getOldZValue());
			row.createCell((short) 7).setCellValue(emm.getNewZValue());

			if ("1".equals(meterPotocol))
			{// 97剩余电量
				// "旧表剩余量"
				row.createCell((short) 8).setCellValue(emm.getOldSYValue());
				// "旧表剩余金额"
				row.createCell((short) 9).setCellValue("-");
				// "新表剩余量"
				row.createCell((short) 10).setCellValue(emm.getNewSYValue());
				// "新表剩余金额"
				row.createCell((short) 11).setCellValue("-");
			} else
			{// 07 金额
				// "旧表剩余量"
				row.createCell((short) 8).setCellValue("-");
				// "旧表剩余金额"
				row.createCell((short) 9).setCellValue(emm.getOldSYValue());
				// "新表剩余量"
				row.createCell((short) 10).setCellValue("-");
				// "新表剩余金额"
				row.createCell((short) 11).setCellValue(emm.getNewSYValue());

			}

			row.createCell((short) 12).setCellValue(emm.getIsZQ());
			row.createCell((short) 13).setCellValue(emm.getUsers_Name());
			row.createCell((short) 14).setCellValue(emm.getInsertTime());

		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		// FileOutputStream fout = new FileOutputStream("replace.xml");
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}

	/**
	 * 删除记录
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void del_info_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		ExchangeMeterDao dao = new ExchangeMeterDao();
		String ID = req.getParameter("delete_EMR_info_ID");
		String[] IDs = ID.split("\\|");
		int k = 0;
		for (int i = 0; i < IDs.length; i++)
		{
			Boolean b = dao.delete(Integer.parseInt(IDs[i]));
			if (b)
			{
				k++;

				String info = dao.getAmMeterReplaceInfoByID(Integer.parseInt(IDs[i]));
				String userLoginName = (String) req.getSession().getAttribute(
						"userName");
				OperationLogInterface log = new OperationLogImplement();
				// 写入日志
				log.writeLog(userLoginName, "换表登记", "删除换表信息,"+info);
			}
		}
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("result", k);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 编辑换表记录的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void edit_info_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int EMID = Integer.parseInt(req.getParameter("ID"));
		String NewAddress = req.getParameter("NewAddress");
		String OldZValue = req.getParameter("OldZValue");
		String NewZValue = req.getParameter("NewZValue");
		String OldSYValue = req.getParameter("OldSYValue");
		String NewSYValue = req.getParameter("NewSYValue");
		String isZQ = req.getParameter("isZQ");
		String Reason = req.getParameter("Reason");
		String Remark = req.getParameter("Remark");


		ExchangeMeterDao emd = new ExchangeMeterDao();
		ExchangeMeterModel model = new ExchangeMeterModel();
		model.setID(EMID);
		model.setNewAddress(NewAddress);
		model.setOldZValue(Float.parseFloat(OldZValue));
		model.setNewZValue(Float.parseFloat(NewZValue));
		model.setOldSYValue(Float.parseFloat(OldSYValue));
		model.setNewSYValue(Float.parseFloat(NewSYValue));

		model.setIsZQ(isZQ);
		model.setREASON(Reason);
		model.setREMARK(Remark);

		int ammeter_id = emd.getAmmeterID(EMID);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if (emd.modify(model))
		{
			jo.put("result", "1");

			String info = emd.getAmMeterReplaceInfoByID(EMID);
			String userLoginName = (String) req.getSession().getAttribute(
					"userName");
			OperationLogInterface log = new OperationLogImplement();
			// 写入日志
			log.writeLog(userLoginName, "换表登记", "编辑换表信息,"+info);
		} else
		{
			jo.put("result", "0");
		}
		jo.put("ammeter_id", ammeter_id);
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 打开编辑页面时获得的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_EditMeterInfo_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int meterId = Integer.parseInt(req.getParameter("Id"));

		ExchangeMeterDao dao = new ExchangeMeterDao();
		JSONArray json = dao.get_EditMeterInfo_EMR(meterId);
		JSONObject jo = new JSONObject();

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());		
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 获得新增换表记录页面要显示的旧的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_oldMeterInfo_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int meterId = Integer.parseInt(req.getParameter("Id"));

		ExchangeMeterDao dao = new ExchangeMeterDao();
		JSONArray json = dao.get_oldMeterInfo_EMR(meterId);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 新建换表记录
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void new_info_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int AmMeter_ID = Integer.parseInt(req.getParameter("AmMeter_ID"));
		String OldAddress = req.getParameter("OldAddress");
		String NewAddress = req.getParameter("NewAddress");
		String OldZValue = req.getParameter("OldZValue");
		String NewZValue = req.getParameter("NewZValue");
		String OldSYValue = req.getParameter("OldSYValue");
		String NewSYValue = req.getParameter("NewSYValue");
		String isZQ = req.getParameter("isZQ");
		String Users_ID = req.getParameter("Users_ID");
		String Reason = req.getParameter("Reason");
		String Remark = req.getParameter("Remark");
		String insertTime = req.getParameter("insertTime");
		String isXZ = req.getParameter("isXZ");

		ExchangeMeterDao emd = new ExchangeMeterDao();
		ExchangeMeterModel model = new ExchangeMeterModel();
		model.setAmMeter_ID(AmMeter_ID);
		model.setOldAddress(OldAddress);
		model.setNewAddress(NewAddress);
		model.setOldZValue(Float.parseFloat(OldZValue));
		model.setNewZValue(Float.parseFloat(NewZValue));
		model.setOldSYValue(Float.parseFloat(OldSYValue));
		model.setNewSYValue(Float.parseFloat(NewSYValue));
		model.setUSERS_ID(Integer.parseInt(Users_ID));
		model.setIsZQ(isZQ);
		model.setREASON(Reason);
		model.setREMARK(Remark);
		model.setInsertTime(insertTime);
		model.setIsXZ(isXZ);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if (emd.insert(model))
		{
			jo.put("isInsert", "1");

			String userLoginName = (String) req.getSession().getAttribute(
					"userName");
			OperationLogInterface log = new OperationLogImplement();
			// 写入日志
			log.writeLog(userLoginName, "换表登记", "新建换表信息,新表地址："+NewAddress);
		} else
		{
			jo.put("isInsert", "0");
		}
		json.add(jo);

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 查询换表记录的信息
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void get_info_EMR(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if (flag == 3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));

		String selectInfo = "";
		String queryMsg = "";
		if (flag == 0)
			selectInfo += "all|";
		else if (flag == 1)
			selectInfo += "area|" + Id;
		else if (flag == 2)
			selectInfo += "arch|" + Id;
		else if (flag == 3)
			selectInfo += "floor|" + Id + "|" + floorID;
		else if (flag == 4)
			selectInfo += "meter|" + Id;

		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");

		ExchangeMeterDao emd = new ExchangeMeterDao();
		ArrayList<ExchangeMeterModel> list = new ArrayList<ExchangeMeterModel>();
		list = emd.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, true, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", emd.getTotalCount());
		json.add(jo);

		for (ExchangeMeterModel emm : list)
		{
			jo = new JSONObject();

			// zxm 20150721
			String meterPotocol = "1";// 默认97
			String sqlString = "select TexingValue from TEXINGVALUE where  METERTEXING_id=18 and METESTYLE_ID=(SELECT METESTYLE_ID from AMMETER WHERE AMMETER_ID="
					+ emm.getAmMeter_ID() + ")";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sqlString);
				rs = ps.executeQuery();
				if (rs.next())
				{
					meterPotocol = rs.getString("TexingValue");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			/*	PreparedStatement pst = ConnDB.getConnection().prepareStatement(sqlString);
			ResultSet rSet = pst.executeQuery();
			if (rSet.next())
			{
				meterPotocol = rSet.getString("TexingValue");
			}
			if (rSet != null)
				rSet.close();
			if (pst != null)
				pst.close();
			 */
			jo.put("meterPotocol", meterPotocol);
			jo.put("ID", emm.getID());
			jo.put("AmMeter_ID", emm.getAmMeter_ID());
			jo.put("Area_Name", emm.getArea_Name());
			jo.put("Architecture_Name", emm.getArchitecture_Name());
			jo.put("FloorName", emm.getFloorName());
			jo.put("AmMeter_Name", emm.getAmMeter_Name());
			jo.put("OldAddress", emm.getOldAddress());
			jo.put("NewAddress", emm.getNewAddress());
			jo.put("OldZValue", emm.getOldZValue());
			jo.put("NewZValue", emm.getNewZValue());
			jo.put("OldSYValue", emm.getOldSYValue());
			jo.put("NewSYValue", emm.getNewSYValue());
			jo.put("isZQ", emm.getIsZQ());
			jo.put("Users_Name", emm.getUsers_Name());
			jo.put("insertTime", emm.getInsertTime());
			json.add(jo);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		// System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

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
	public NodeList getNodeList(String xml, String tagName) throws Exception
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();

		read = new StringReader(xml);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList nodeList = document.getElementsByTagName(tagName);
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
}
