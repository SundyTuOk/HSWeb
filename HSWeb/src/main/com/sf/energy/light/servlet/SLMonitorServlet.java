package com.sf.energy.light.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.xml.sax.SAXException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.light.dao.ParseLightXml;
import com.sf.energy.light.dao.SLControlLineDao;
import com.sf.energy.light.dao.SLMonitorDao;
import com.sf.energy.light.dao.SLMonitorSocket;
import com.sf.energy.light.dao.SLOnOffDao;
import com.sf.energy.light.dao.SlHistoryDao;
import com.sf.energy.light.model.SLControlLineModel;
import com.sf.energy.light.model.SLMonitorModel;
import com.sf.energy.light.model.SlHistoryModel;
import com.sf.energy.light.model.XMLCoder;
import com.sf.energy.light.service.SendingXMLCode;
import com.sf.energy.light.serviceImpl.SendingLampOnOffXml;
import com.sf.energy.project.system.dao.PlantaskExecuteDao;
import com.sf.energy.util.ConnDB;

public class SLMonitorServlet extends HttpServlet
{

	// private ReentrantLock lock = new ReentrantLock();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String flag = req.getParameter("flag");

		if ("loaddate".equals(flag))
		{
			try
			{
				loadSlMonitordate(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("carry".equals(flag))
		{
			try
			{
				carrySLMonitor(req, resp);
			} catch (SQLException e)
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
	}

	private void carrySLMonitor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParserConfigurationException,
			SAXException
	{
 		ReentrantLock lock = new ReentrantLock();
		String userID = "1";
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		ParseLightXml parseLightXml = new ParseLightXml();
		String SLMonitorID = req.getParameter("SLMonitorID");


		//String[] idarray = req.getParameterValues("idarray[]");
		int carryID = Integer.parseInt(req.getParameter("carryID"));
		
		SLMonitorDao slMonitorDao = new SLMonitorDao();
		SLMonitorModel slMonitorModel = new SLMonitorModel();
		SLControlLineDao slControlLineDao = new SLControlLineDao();
		SLControlLineModel slControlLineModel = new SLControlLineModel();
		SendingXMLCode sxc = new SendingLampOnOffXml();

		// 获取session
		HttpSession httpSession = req.getSession();
		String userName = null;
		if (httpSession.getAttribute("userName") != null)
		{
			userName = httpSession.getAttribute("userName").toString();
		}
		String sLLine_Name = "";
		if((SLMonitorID!=null) && (!"".equals(SLMonitorID))){
			slMonitorModel = slMonitorDao.queryLine(SLMonitorID);
			sLLine_Name = slMonitorModel.getSLLine_Name();
		}
		
		
		boolean isCon = sxc.creatCon();
		
		//******************yyedit*****************************************/

		//*******************************************/
		if (isCon == false)
		{
			String opr = "";
			if (carryID == 1)
			{
				opr = "设置路灯线路器状态为开";
			} else if (carryID == 2)
			{
				opr = "设置路灯线路器状态为关";
			} else if (carryID == 3)
			{
				opr = "抄读路灯控制器状态";
			} else if (carryID == 4)
			{
				opr = "设置路灯线路器状态为自动";
			} else if (carryID == 5)
			{
				opr = "路灯校时";
			} else if (carryID == 6)
			{
				opr = "手动下发路灯计划";
			}
			
			/*for (int i = 0; i < idarray.length; i++)
			{*/
				SlHistoryModel slHistoryModel = new SlHistoryModel();
				// ID
				slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
				String time = "";
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = formatter.format(date);
				// 时间
				slHistoryModel.setSlHistory_Time(time);
				// 插入路灯ID
				//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
				slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
				// 执行结果
				slHistoryModel.setSlHistory_State("失败");
				// 插入用户id
				slHistoryModel.setUsers_Name(userName);
				// 执行操作
				slHistoryModel.setSlHistory_Opr(opr);
				// 操作类型
				slHistoryModel.setSlHistory_Style("手动");
				slHistoryModel.setTASKNUM("201504");
				parseLightXml.addSLHistory(slHistoryModel);

				JSONObject json = new JSONObject();
				json.put("isconnect", "failure");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			//}
		} else if (isCon == true)
		{

			SLOnOffDao dao = new SLOnOffDao();
			XMLCoder xc = new XMLCoder();
			int port = 0;
			String slkongzhiAddress = "";
			String gatherAddres = "";
			String line1 = "";
			String line2 = "";
			int index = 0;
			String state = "";
            String datasiteid="";
			// 没有选择
			if (carryID == -1)
			{
				// 请选择执行操作
			}
			// 即时开灯
			else if (carryID == 1)
			{
				int errorCount = 0;
				/*for (int i = 0; i < idarray.length; i++)
				{*/
					lock.lock();
					try
					{
						String carryIsSucces = "成功";

						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						index = slControlLineModel.getSLKONGZHI_INDEX();
						state = slControlLineModel.getLineState();
                        datasiteid=slControlLineModel.getDatasiteID();
						if (index == 1)
						{
							boolean flag = false;
							// 更新数据库
							xc = dao.lampOnOff(port, slkongzhiAddress, "on", state, gatherAddres, userName,datasiteid);

							String str = sxc.sendXMLToServer(xc);

							// 解析xml,error是否为0
							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"on","on");
							}

							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}

						} else
						{
							boolean flag = false;
							xc = dao.lampOnOff(port, slkongzhiAddress, state, "on", gatherAddres, userName,datasiteid);
							String str = sxc.sendXMLToServer(xc);

							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"on","on");
							}

							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}
						}
						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("设置路灯线路器状态为开");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("2012");
						parseLightXml.addSLHistory(slHistoryModel);

					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				// 失败个数
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				// 是否超时
				json.put("isTimeOut", "isTimeOut");
				resp.setContentType("text/html;charset=utf-8");

				
			
					PrintWriter pw = resp.getWriter();
					pw.print(json.toString());
					pw.flush();
					pw.close();
			
			}
			// 即时关灯
			 if (carryID == 2)
			{
				int errorCount = 0;

				/*for (int i = 0; i < idarray.length; i++)
				{*/
					lock.lock();
					try
					{
						String carryIsSucces = "成功";

						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						index = slControlLineModel.getSLKONGZHI_INDEX();
						state = slControlLineModel.getLineState();
						datasiteid=slControlLineModel.getDatasiteID();
						int sLGATHER_ID = parseLightXml.getgather(gatherAddres);

						if (index == 1)
						{
							boolean flag = false;
							xc = dao.lampOnOff(port, slkongzhiAddress, "off", state, gatherAddres, userName,datasiteid);

							String str = sxc.sendXMLToServer(xc);

							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"off","off");
							}
							// 解析xml,error是否为0
							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}
						} else
						{
							boolean flag = false;
							xc = dao.lampOnOff(port, slkongzhiAddress, state, "off", gatherAddres, userName,datasiteid);

							String str = sxc.sendXMLToServer(xc);

							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"off","off");
							}

							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}
						}
						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("设置路灯线路器状态为关");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("201212");
						parseLightXml.addSLHistory(slHistoryModel);
					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");
			
				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
				
			}
			// 抄读状态
			 if (carryID == 3)
			{
				int errorCount = 0;

				/*for (int i = 0; i < idarray.length; i++)
				{*/
					lock.lock();
					try
					{
						String carryIsSucces = "成功";

						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						index = slControlLineModel.getSLKONGZHI_INDEX();
						state = slControlLineModel.getLineState();
						int sLGATHER_ID = parseLightXml.getgather(gatherAddres);

						boolean flag = false;

						//xc = dao.readLampState(port, slkongzhiAddress, Integer.parseInt(idarray[i]), userID);
						xc = dao.readLampState(port, slkongzhiAddress,gatherAddres, Integer.parseInt(SLMonitorID), userName);
						
						String str = sxc.sendXMLToServer(xc);

						if ("TimeOut".equals(str))
						{
							flag = false;
						} else
						{
							flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"","");
						}
						// 解析xml,error是否为0
						if (flag == false)
						{
							++errorCount;
							carryIsSucces = "失败";
						}

						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("抄读路灯线路器状态");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("201212");
						parseLightXml.addSLHistory(slHistoryModel);
					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			}
			// 自动模式
			 if (carryID == 4)
			{
				int errorCount = 0;
				/*for (int i = 0; i < idarray.length; i++)
				{*/
					String carryIsSucces = "成功";
					lock.lock();
					try
					{
						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						index = slControlLineModel.getSLKONGZHI_INDEX();
						state = slControlLineModel.getLineState();
						datasiteid=slControlLineModel.getDatasiteID();
						if (slkongzhiAddress.length()>=12)//电表当路灯控制器
						{
							int sLGATHER_ID = parseLightXml.getgather(gatherAddres);
							slControlLineModel.setSLGATHER_ID(sLGATHER_ID);
							String time = "";
							Date date = new Date();
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							time = formatter.format(date);
							slControlLineModel.setLastTime(time);
							slControlLineModel.setLamp_state11("auto");
							slControlLineModel.setLamp_state21("auto");
							parseLightXml.updateSLkongzhi(slControlLineModel);
							//ParseLightXml.	updateSLkongzhi(slControlLineModel);
						}
						else {
				
						if (index == 1)
						{
							xc = dao.lampOnOff(port, slkongzhiAddress, "auto", state, gatherAddres, userName,datasiteid);
							boolean flag = false;
							String str = sxc.sendXMLToServer(xc);

							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								// 解析xml,error是否为0
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"","");
							}

							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}
						} else
						{
							xc = dao.lampOnOff(port, slkongzhiAddress, state, "auto", gatherAddres, userName,datasiteid);
							boolean flag = false;
							String str = sxc.sendXMLToServer(xc);

							if ("TimeOut".equals(str))
							{
								flag = false;
							} else
							{
								flag = parseLightXml.updateLightMes(str,slkongzhiAddress,"","");
							}

							if (flag == false)
							{
								++errorCount;
								carryIsSucces = "失败";
							}
						}		
						}
						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("设置路灯线路器状态为自动");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("201212");
						parseLightXml.addSLHistory(slHistoryModel);
					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			}
			// 校时
			 if (carryID == 5)
			{
				int errorCount = 0;
				/*for (int i = 0; i < idarray.length; i++)
				{*/
					String carryIsSucces = "成功";
					lock.lock();
					try
					{

						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						if (slkongzhiAddress.length()>=12)//电表当路灯控制器
						{
							++errorCount;
							carryIsSucces = "失败，设备不支持该属性";
						}
						else 
						{
						xc = dao.lampCheckTime(port, slkongzhiAddress, Integer.parseInt(SLMonitorID), userName);
						boolean flag = false;
						String str = sxc.sendXMLToServer(xc);

						if ("TimeOut".equals(str))
						{
							flag = false;
						} else
						{
							// 解析xml,error是否为0
							flag = parseLightXml.checkTime(str);
						}

						if (flag == false)
						{
							++errorCount;
							carryIsSucces = "失败";
						}
						}
						
						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("路灯校时");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("201212");
						parseLightXml.addSLHistory(slHistoryModel);
					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			}
			// 下发计划
			 if (carryID == 6)
			{
				int errorCount = 0;
				/*for (int i = 0; i < idarray.length; i++)
				{*/
					String carryIsSucces = "成功";
					lock.lock();
					try
					{
						//slControlLineModel = slControlLineDao.queryLineMes(idarray[i]);
						slControlLineModel = slControlLineDao.queryLineMes(SLMonitorID);
						port = slControlLineModel.getSLKONGZHI_PORT();
						slkongzhiAddress = slControlLineModel.getSLKONGZHI_485ADDRESS();
						gatherAddres = slControlLineModel.getGather_Address();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						String strsql = "update PlanTask set PlanTask_LastTime='"
								+ df.format(new Date()) + "',PlanTask_LastState='1' where PlanTask_ID="
								+ 61;
						Connection conn2=null;
						PreparedStatement ps2 =null;
						try
						{
							conn2 = ConnDB.getConnection();
							ps2 = conn2.prepareStatement(strsql);

							ps2.executeUpdate();
						} catch (Exception e1)
						{
							e1.printStackTrace();
						}finally{
							ConnDB.release(conn2, ps2);
						}
						
						
						// 插入历史记录
						SlHistoryModel slHistoryModel = new SlHistoryModel();
						// ID
						slHistoryModel.setSlHistory_Id(parseLightXml.getSLHistoryId());
						String time = "";
						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time = formatter.format(date);
						// 时间
						slHistoryModel.setSlHistory_Time(time);
						// 插入路灯ID
						//slHistoryModel.setSLLINE_ID(Integer.parseInt(idarray[i]));
						slHistoryModel.setSLLINE_ID(Integer.parseInt(SLMonitorID));
						// 执行结果
						slHistoryModel.setSlHistory_State(carryIsSucces);
						// 插入用户id
						slHistoryModel.setUsers_Name(userName);
						// 执行操作
						slHistoryModel.setSlHistory_Opr("下发路灯计划");
						// 操作类型
						slHistoryModel.setSlHistory_Style("手动");
						slHistoryModel.setTASKNUM("201212");
						parseLightXml.addSLHistory(slHistoryModel);
						makeServerAddTask();
					} finally
					{
						lock.unlock();
					}
				//}

				JSONObject json = new JSONObject();
				json.put("errorCount", errorCount);
				json.put("isconnect", "success");
				json.put("sLLine_Name", sLLine_Name);
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();
				pw.print(json.toString());
				pw.flush();
				pw.close();
			}
		}
	}
	/**
	 * 发送命令到服务器
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void makeServerAddTask() throws UnknownHostException, IOException
	{
		// 发送服务器让它从新添加
		XMLConfiguration config=null;
		
		try
		{
			config=com.sf.energy.util.Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String IP=config.getString("server.IP");
		int port=Integer.parseInt(config.getString("server.port"));
		
		String command = "<addPlantask>\r\n";
		Socket s = new Socket(IP, port);
		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeBytes(command);
	}
	/**
	 * 导入数据
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void loadSlMonitordate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("SLMonitorPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("SLMonitorPageIndex"));
		int AreaID = Integer.parseInt(req.getParameter("SLMonitorAreaId"));
		int lineID = Integer.parseInt(req.getParameter("SLMonitorLineId"));
		String state = req.getParameter("SLMonitorState");
		String SLMonitorsortName = req.getParameter("SLMonitorsortName");
		String SLMonitororder = req.getParameter("SLMonitororder");

		SLMonitorDao slMonitorDao = new SLMonitorDao();
		List<SLMonitorModel> list = new ArrayList<SLMonitorModel>();
		list = slMonitorDao.querySlMonitor(SLMonitorsortName, SLMonitororder, state, AreaID, lineID, thePageCount, thePageIndex);

		JSONObject json = new JSONObject();
		json.put("totalCount", slMonitorDao.getTotalCount());

		JSONArray jo = JSONArray.fromObject(list);

		json.put("jo", jo);

		resp.setContentType("text/html;charset=utf-8");

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());
		pw.flush();
		pw.close();

	}
}
