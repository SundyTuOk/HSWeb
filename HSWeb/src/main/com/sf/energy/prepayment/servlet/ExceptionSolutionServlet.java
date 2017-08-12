package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.fop.fonts.base14.TimesBold;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.dao.ExceptionDao;
import com.sf.energy.prepayment.dao.YuebuExceptionSao;
import com.sf.energy.prepayment.model.ExceptionModel;
import com.sf.energy.prepayment.model.ExceptionYueBuModel;
import com.sf.energy.prepayment.model.XMLCoder;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class ExceptionSolutionServlet extends HttpServlet
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
		// 查询分合闸信息
		if ("getCardException".equals(method))
			getCardException(req, resp);

		if ("delCardException".equals(method))
			delCardException(req, resp);

		if ("soluteCardExecption".equals(method))
			soluteCardExecption(req, resp);

		if ("getYuebuException".equals(method))
			getYuebuException(req, resp);

		if ("delYueBuException".equals(method))
			delYueBuException(req, resp);

		if ("soluteYueBuExecption".equals(method))
			soluteYueBuExecption(req, resp);
		if("soluteYueBuDelExecption".equals(method))
			soluteYueBuDelExecption(req, resp);

	}

	private void soluteYueBuDelExecption(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String id = req.getParameter("id");
		
		YuebuExceptionSao dao = new YuebuExceptionSao();
		int result = dao.deleteInfo(id);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("result", result);
		json.add(jo);
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.write(json.toString());
		out.flush();
		out.close();
		
	}

	/**
	 * 處理YueBuExecption
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */

	private void soluteYueBuExecption(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		ReentrantLock lock = new ReentrantLock();
		int ammeter_id = Integer.parseInt(req.getParameter("ammeter_id"));
		String beilv = req.getParameter("beilv");
		String CValue = req.getParameter("CValue");

		YuebuExceptionSao dao = new YuebuExceptionSao();
		float priceValue = dao.getPriceValue(ammeter_id);

		boolean error = true;
		String xmlReply = "";// 回复的XML
		String protocol = "1";// 默认97规约
		int flag = 0;
		float QSYValue = 0;// 退电前剩余金额
		float theGross = Math.abs(Float.parseFloat(CValue));// 退购电量
		float theMoney = (float) (Math.round(theGross * 100 / priceValue) / 100.0);// 退购金额
																					// 李嵘20150410
		float gross = 0;
		float money = 0;// 李嵘20150410
		float HSYValue = 0;// 退电后剩余金额
		float HTZValue=0;
		String times = "";

		CMMsendCommand csc = new CMMsendCommand();
		SendingAPCode sendXML = new SendingAPCode();
		XMLCoder xmlCoder = new XMLCoder();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		// 读退电前剩余电量(兼容97 07)
		xmlCoder = csc.readMeter(ammeter_id, userID);

		try
		{
			lock.lock();
			if (xmlCoder != null)
			{
				xmlReply = sendXML.sendXMLToServer(xmlCoder);
				NodeList list = null;
				String err = "";
				String errMsg = "读退电前剩余金额(电量)失败！";
				NodeList gwList=null;
				String task;
				gwList = getNodeList(xmlReply, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						task = tasknum.getAttribute("tasknum");
						if(task.equals(xmlCoder.getTaskNum())){
							list = getNodeList(xmlReply, "commandback");

							Element commandBack = (Element) list.item(0);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								if (err.equals("1"))
								{// 失败
									flag = 1;
									jo = new JSONObject();
									jo.put("isError", "1");
									jo.put("message", errMsg);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.write(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0"))
								{// 成功
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int i = 0; i < result.getLength(); i++)
									{
										Element resultElement = (Element) result.item(i);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xmlCoder.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											String SYValue = "0.00";

											if (resultElement.hasAttribute("SYValue"))
											{// 97表
												SYValue = resultElement.getAttribute("SYValue");
												times = resultElement.getAttribute("BuyTimes");
											} else
											{// 07规约
												protocol = "30";
												SYValue = resultElement.getAttribute("SYMoney");
											}

											SYValue = df.format(df.parse(SYValue));
											if ("-1".equals(SYValue))
											{// 失败
												flag = 1;
												jo = new JSONObject();
												jo.put("isError", "1");
												jo.put("message", "网关抄读失败！");
												json.add(jo);
											} else
											{
												if ("1".equals(protocol))
												{// 97规约
													QSYValue = Float.parseFloat(SYValue) * csc.getBeiLv();
													gross = QSYValue - theGross;
												} else
												{// 07 金额
													QSYValue = Float.parseFloat(SYValue);
													money = QSYValue - theMoney;
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

		// 退电
		// 上次购电后总购电次数
		if ("30".equals(protocol))
		{
			xmlCoder = csc.readMeter07(ammeter_id, userID, "07010202");

			try
			{
				lock.lock();
				xmlReply = sendXML.sendXMLToServer(xmlCoder);
				NodeList list = null;
				String err = "";
				String errMsg = "读退电前剩余金额(电量)失败！";
				
				NodeList gwList=null;
				String task;
				gwList = getNodeList(xmlReply, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						task = tasknum.getAttribute("tasknum");
						if(task.equals(xmlCoder.getTaskNum())){
							list = getNodeList(xmlReply, "commandback");
							Element commandBack = (Element) list.item(0);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");

								if ("1".equals(err))
								{
									flag = 1;
									jo = new JSONObject();
									jo.put("isError", "1");
									jo.put("message", errMsg);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0"))
								{
									DecimalFormat df = new DecimalFormat("0.##");
									NodeList result = commandBack.getElementsByTagName("result");
									for (int i = 0; i < result.getLength(); i++)
									{
										Element resultElement = (Element) result.item(i);
										
										String dataid = "";
										String meterAddress = "";
										boolean isExecFlag = true;
										if(resultElement.hasAttribute("DataID")){
											dataid = resultElement.getAttribute("DataID");
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xmlCoder.getMeterAddress())){
												isExecFlag = false;
											}
										}
										if(isExecFlag){
											if((Integer.parseInt(times)+1)!=Integer.parseInt(resultElement.getAttribute("BuyTimes")))
											{
												jo=new JSONObject();
												jo.put("isError", "1");
												jo.put("message", "该电表只允许退购上一次电量");
												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
												return;
											}
											else
											{
												times = resultElement.getAttribute("BuyTimes");
											}
										}else{
											jo = new JSONObject();
											jo.put("isError", "6");
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
		}
		// 退电
		xmlCoder = csc.xiaoHuTuiDian(ammeter_id, times, userID);

		try
		{
			lock.lock();
			xmlReply = sendXML.sendXMLToServer(xmlCoder);
			NodeList list = null;
			String err = "";
			String errMsg = "退电失败";
			
			NodeList gwList=null;
			String task;
			gwList = getNodeList(xmlReply, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					task = tasknum.getAttribute("tasknum");
					if(task.equals(xmlCoder.getTaskNum())){
						list = getNodeList(xmlReply, "commandback");
						Element commandBack = (Element) list.item(0);

						if (commandBack.hasAttribute("error"))
						{
							err = commandBack.getAttribute("error");

							if (err.equals("1"))
							{
								flag = 1;
								jo = new JSONObject();
								jo.put("isError", "1");
								jo.put("message", errMsg);

								json.add(jo);
								resp.setContentType("application/x-json");

								PrintWriter out = resp.getWriter();
								out.write(json.toString());
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
									if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
										isExecFlag = false;
									}
								}else {
									if(resultElement.hasAttribute("Meter_Address")){
										meterAddress = resultElement.getAttribute("Meter_Address");
									}
									if(!meterAddress.equals(xmlCoder.getMeterAddress())){
										isExecFlag = false;
									}
								}
								if(!isExecFlag){
									jo = new JSONObject();
									jo.put("isError", "7");
									jo.put("message", "返回表地址或命令码错误!");

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
						jo.put("message", "任务编号错误!");

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
			jo.put("isError", "1");
			jo.put("message", "命令超时重发!");

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

		// 启动预付费
		if("1".equals(protocol)){

			xmlCoder = csc.kaiHuCommand(ammeter_id, userID);
			try
			{
				lock.lock();
				String str = sendXML.sendXMLToServer(xmlCoder);
				System.out.println("收到的回复是" + str);
				
				NodeList gwList = getNodeList(str, "GW");
				for (int m = 0; m < gwList.getLength(); m++)
				{
					Element tasknum = (Element) gwList.item(m);
					if (tasknum.hasAttribute("tasknum"))
					{
						String task = tasknum.getAttribute("tasknum");
						if(task.equals(xmlCoder.getTaskNum())){
							NodeList list = null;
							String err = "";
							String errMsg = "启动预付费失败！";
							list = getNodeList(str, "commandback");

							Element commandBack = (Element) list.item(0);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								errMsg += commandBack.getAttribute("errormessage");
								// 失败
								if (err.equals("1"))
								{
									flag = 1;
									jo = new JSONObject();
									jo.put("isError", "1");
									jo.put("message", errMsg);
									json.add(jo);
									resp.setContentType("application/x-json");

									PrintWriter out = resp.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
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
											if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
												isExecFlag = false;
											}
										}else {
											if(resultElement.hasAttribute("Meter_Address")){
												meterAddress = resultElement.getAttribute("Meter_Address");
											}
											if(!meterAddress.equals(xmlCoder.getMeterAddress())){
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

			if (gross < 0)
			{
				gross = 0;
			}
			/*if (money < 0)
			{
				money = 0;
			}*/
			/*if (((gross >= 0) && ("1".equals(protocol))) || ((money >= 0) && ("30".equals(protocol))))
			{*/
			if (gross >= 0)
			{
				// 下发剩余电量
				xmlCoder = csc.sendTGross(ammeter_id, String.valueOf(gross), "0", userID);
				/*if ("1".equals(protocol))
				{
					xc = csc.sendTGross(AmMeterID, String.valueOf(gross), "0", userID);
				} else
				{
					xc = csc.sendTGross(AmMeterID, String.valueOf(money), "0", userID);
				}*/

				try
				{
					lock.lock();
					String str = sendXML.sendXMLToServer(xmlCoder);
					System.out.println("收到的回复是" + str);
					
					NodeList gwList = getNodeList(str, "GW");
					for (int m = 0; m < gwList.getLength(); m++)
					{
						Element tasknum = (Element) gwList.item(m);
						if (tasknum.hasAttribute("tasknum"))
						{
							String task = tasknum.getAttribute("tasknum");
							if(task.equals(xmlCoder.getTaskNum())){
								NodeList list = null;
								String err = "";
								String errMsg = "启动预付费失败！";
								list = getNodeList(str, "commandback");

								Element commandBack = (Element) list.item(0);
								if (commandBack.hasAttribute("error"))
								{
									err = commandBack.getAttribute("error");
									errMsg += commandBack.getAttribute("errormessage");
									// 失败
									if (err.equals("1"))
									{
										flag = 1;
										jo = new JSONObject();
										jo.put("isError", "1");
										jo.put("message", errMsg);
										json.add(jo);
										resp.setContentType("application/x-json");

										PrintWriter out = resp.getWriter();
										out.println(json.toString());
										out.flush();
										out.close();
									} else if (err.equals("0")) // 成功
									{
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
												if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
													isExecFlag = false;
												}
											}else {
												if(resultElement.hasAttribute("Meter_Address")){
													meterAddress = resultElement.getAttribute("Meter_Address");
												}
												if(!meterAddress.equals(xmlCoder.getMeterAddress())){
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
			}
		
		}
		
		xmlCoder = csc.readMeter(ammeter_id, userID);
		try
		{
			lock.lock();
			String str = sendXML.sendXMLToServer(xmlCoder);
			System.out.println("收到的报文的回复是：" + str);
			
			NodeList gwList = getNodeList(str, "GW");
			for (int m = 0; m < gwList.getLength(); m++)
			{
				Element tasknum = (Element) gwList.item(m);
				if (tasknum.hasAttribute("tasknum"))
				{
					String task = tasknum.getAttribute("tasknum");
					if(task.equals(xmlCoder.getTaskNum())){
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
								jo.put("message", "读退电后金额(电量)失败！");
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
									if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
										isExecFlag = false;
									}
								}else {
									if(resultElement.hasAttribute("Meter_Address")){
										meterAddress = resultElement.getAttribute("Meter_Address");
									}
									if(!meterAddress.equals(xmlCoder.getMeterAddress())){
										isExecFlag = false;
									}
								}
								if(isExecFlag){
									String readTime = resultElement.getAttribute("ValueTime");
									String SYValue = "0.00", TotoleUsed = "0.00", TZValue = "0.00";
									float ZValueZY = -1;
									HTZValue = -1;
									if ("1".equals(protocol))// 97
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

										xmlCoder = csc.readMeter07(ammeter_id, userID, "00900201");// 07当前透支金额
										if (xmlCoder != null)
										{
											try
											{
												str = sendXML.sendXMLToServer(xmlCoder);
												System.out.println("收到的报文的回复是：" + str);
												
												gwList = getNodeList(str, "GW");
												for (m = 0; m < gwList.getLength(); m++)
												{
													tasknum = (Element) gwList.item(m);
													if (tasknum.hasAttribute("tasknum"))
													{
														task = tasknum.getAttribute("tasknum");
														if(task.equals(xmlCoder.getTaskNum())){
															list = getNodeList(str, "commandback");
															commandBack = (Element) list.item(0);
															result = commandBack.getElementsByTagName("result");
															
															err = getAttrValue(str, "commandback", "error");
															if ("0".equals(err))
															{
																resultElement = (Element) result.item(0);
																
																dataid = "";
																meterAddress = "";
																isExecFlag = true;
																if(resultElement.hasAttribute("DataID")){
																	dataid = resultElement.getAttribute("DataID");
																	if(resultElement.hasAttribute("Meter_Address")){
																		meterAddress = resultElement.getAttribute("Meter_Address");
																	}
																	if((!dataid.equals(xmlCoder.getDataid())) || (!meterAddress.equals(xmlCoder.getMeterAddress()))){
																		isExecFlag = false;
																	}
																}else {
																	if(resultElement.hasAttribute("Meter_Address")){
																		meterAddress = resultElement.getAttribute("Meter_Address");
																	}
																	if(!meterAddress.equals(xmlCoder.getMeterAddress())){
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
												jo = new JSONObject();
												jo.put("isError", "2");
												jo.put("message", "命令超时重发！");

												json.add(jo);
												resp.setContentType("application/x-json");

												PrintWriter out = resp.getWriter();
												out.println(json.toString());
												out.flush();
												out.close();
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
										cmd.SerSaveData(ammeter_id, ZValueZY, HSYValue, HTZValue, readTime);
										jo = new JSONObject();
										jo.put("isError", "0");
										jo.put("QSYValue", QSYValue);
										jo.put("HSYValue", HSYValue);
										jo.put("theGross", theGross);
										jo.put("theMoney", theMoney);
										jo.put("protocol", protocol);
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

	/**
	 * 处理cardException
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void soluteCardExecption(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		// /APCardErrorInfo_ID
		int id = Integer.parseInt(req.getParameter("id"));
		ExceptionDao dao = new ExceptionDao();
		boolean a = dao.soluteCardException(id);
		JSONObject jo = new JSONObject();
		if (a)
			jo.put("success", "处理成功");
		else
			jo.put("success", "处理失败，请核实信息！");
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}

	private void getYuebuException(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		YuebuExceptionSao dao = new YuebuExceptionSao();
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));

		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String treeSelect = "";
		ArrayList<ExceptionYueBuModel> list = new ArrayList<ExceptionYueBuModel>();
		list = dao.queryInfo(thePageCount, thePageIndex, order, treeSelect, startDate, endDate);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (ExceptionYueBuModel model : list)
		{
			json.add(model);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void delCardException(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "异常处理", "删除一卡通异常信息");
		
		String ids = req.getParameter("ids");

		String[] idArray = ids.split(",");

		ExceptionDao dao = new ExceptionDao();

		int count = dao.deleteInfo(ids);
		JSONObject jo = new JSONObject();
		if (idArray.length == count)
			jo.put("err", "false");
		else
			jo.put("err", "true");

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();

	}

	private void delYueBuException(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "异常处理", "删除月补异常信息");
		
		YuebuExceptionSao dao = new YuebuExceptionSao();

		String ids = req.getParameter("ids");
		String[] idArray = ids.split(",");
		int count = dao.deleteInfo(ids);
		JSONObject jo = new JSONObject();
		if (idArray.length == count)
			jo.put("err", "false");
		else
			jo.put("err", "true");
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();

	}

	private void getCardException(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		ExceptionDao dao = new ExceptionDao();

		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));

		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");
		String ErrorType = req.getParameter("ErrorType");
		String strExecState = req.getParameter("strExecState");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		ArrayList<ExceptionModel> list = new ArrayList<ExceptionModel>();

		list = dao.queryInfo(thePageCount, thePageIndex, order, ErrorType, strExecState, startDate, endDate);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (ExceptionModel model : list)
		{
			json.add(model);
		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
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
