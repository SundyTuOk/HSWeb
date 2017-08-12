package com.sf.energy.powernet.servlet;

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
import java.util.HashMap;
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
import com.sf.energy.manager.current.dao.T_MonthAmDao;
import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.manager.current.service.XMLHelper;
import com.sf.energy.manager.current.serviceImpl.SendingImmediateCode;
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.powernet.service.SendingPowerNetSafeCode;
import com.sf.energy.prepayment.dao.CMMsendCommand;
import com.sf.energy.prepayment.serviceImpl.SendingAPCode;
import com.sf.energy.project.PDRoom.dao.PD_PartInfo03Dao;
import com.sf.energy.project.PDRoom.model.PD_PartInfo03Model;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.util.ConnDB;

public class PowerNetInfoServlet extends HttpServlet
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

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
String ssString=request.getParameter("method");
		if ("savePowerNetSafeEdit".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				savePowerNetSafeEdit(request, response);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("queryByID".equalsIgnoreCase(request.getParameter("method")))
		{
			queryByID(request, response);
		}

		if ("queryHLValue".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				try
				{
					queryHLValue(request, response);
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e)
			{

				e.printStackTrace();
			}
		}

		if ("queryAmmDanNow".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				queryAmmDanNow(request, response);
			} catch (Exception e)
			{

				e.printStackTrace();
			}
		}

		if ("queryAmmSanNow".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				queryAmmSanNow(request, response);
			} catch (Exception e)
			{

				e.printStackTrace();
			}
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

	public void savePowerNetSafeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int huiLuID = Integer.parseInt(request.getParameter("huiLuID"));

		float ZEDGL = Float.parseFloat(request.getParameter("ZEDGL"));
		float AEDGL = Float.parseFloat(request.getParameter("AEDGL"));
		float BEDGL = Float.parseFloat(request.getParameter("BEDGL"));
		float CEDGL = Float.parseFloat(request.getParameter("CEDGL"));

		float ZXEWG = Float.parseFloat(request.getParameter("ZXEWG"));
		float AXEWG = Float.parseFloat(request.getParameter("AXEWG"));
		float BXEWG = Float.parseFloat(request.getParameter("BXEWG"));
		float CXEWG = Float.parseFloat(request.getParameter("CXEWG"));

		float ADYSX = Float.parseFloat(request.getParameter("ADYSX"));
		float BDYSX = Float.parseFloat(request.getParameter("BDYSX"));
		float CDYSX = Float.parseFloat(request.getParameter("CDYSX"));

		float ADYXX = Float.parseFloat(request.getParameter("ADYXX"));
		float BDYXX = Float.parseFloat(request.getParameter("BDYXX"));
		float CDYXX = Float.parseFloat(request.getParameter("CDYXX"));

		float AXEDL = Float.parseFloat(request.getParameter("AXEDL"));
		float BXEDL = Float.parseFloat(request.getParameter("BXEDL"));
		float CXEDL = Float.parseFloat(request.getParameter("CXEDL"));
		
		float ISMULINE =0;
		if(request.getParameter("ISMULINE")!=null)
		{
			ISMULINE=Float.parseFloat(request.getParameter("ISMULINE"));
		}
		float DYSXL =15;
		if(request.getParameter("DYSXL")!=null)
		{
			DYSXL=Float.parseFloat(request.getParameter("DYSXL"));
		}
		float DYXXL =15;
		if(request.getParameter("DYXXL")!=null)
		{
			DYXXL=	Float.parseFloat(request.getParameter("DYXXL"));
		}
		float XEDLL =10;
		if(request.getParameter("XEDLL")!=null)
		{
			XEDLL=	Float.parseFloat(request.getParameter("XEDLL"));
		}

		PD_PartInfo03Dao hlDao = new PD_PartInfo03Dao();
		PD_PartInfo03Model hlModel = new PD_PartInfo03Model();

		try
		{
			// 把不要编辑的树形先赋给model
			hlModel = hlDao.queryPD_PartInfo03(huiLuID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		hlModel.setPart_ID(huiLuID);
		hlModel.setZEDGL(ZEDGL);
		hlModel.setAEDGL(AEDGL);
		hlModel.setBEDGL(BEDGL);
		hlModel.setCEDGL(CEDGL);

		hlModel.setZXEWG(ZXEWG);
		hlModel.setAXEWG(AXEWG);
		hlModel.setBXEWG(BXEWG);
		hlModel.setCXEWG(CXEWG);

		hlModel.setADYSX(ADYSX);
		hlModel.setBDYSX(BDYSX);
		hlModel.setCDYSX(CDYSX);

		hlModel.setADYXX(ADYXX);
		hlModel.setBDYXX(BDYXX);
		hlModel.setCDYXX(CDYXX);

		hlModel.setAXEDL(AXEDL);
		hlModel.setBXEDL(BXEDL);
		hlModel.setCXEDL(CXEDL);

		hlModel.setISMULINE(ISMULINE);
		hlModel.setISMULINE(DYSXL);
		hlModel.setISMULINE(DYXXL);
		hlModel.setISMULINE(XEDLL);
		
		boolean b = false;
		try
		{
			b = hlDao.editPD_PartInfo03(hlModel);

			// 发送给服务器
			XMLHelper xmlHelper = new XMLHelper();
			SendingPowerNetSafeCode spnsc = new SendingPowerNetSafeCode();
			XMLCoder xmlCoder = new XMLCoder();

			HttpSession session = request.getSession();
			int userID = 0;
			if (session.getAttribute("userId") != null)
			{
				userID = Integer.parseInt(session.getAttribute("userId").toString());
			}

			xmlCoder.setOperCode(userID + "");

			String result = "";
			try
			{
				result = spnsc.sendXMLToServer(xmlCoder, hlModel);
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
			// //System.out.println("服务器收到配网安全后返回的xml："+result);
			if ("".equals(result))// 服务器没有发送数据回来
			{
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("ServerOffline", 1);
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} else
			{
				NodeList list = xmlHelper.getNodeList(result, "commandback");
				for (int i = 0; i < list.getLength(); i++)
				{
					Element result1 = (Element) list.item(i);
					if (result1.getAttribute("error").equalsIgnoreCase("0"))// 中转站存入配网信息成功
					{
						b = true;
					} else
					{
						b = false;
					}
				}
				JSONArray json = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("success", b);// 服务器连接异常
				json.add(jo);
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void queryByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		AmmeterDao ad = new AmmeterDao();
		int id = 0;// 回路ID
		if (request.getParameter("huiLuID") != null)
		{
			id = Integer.parseInt(request.getParameter("huiLuID"));
		}
		String time = request.getParameter("time");
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(5, 7));

		PD_PartInfo03Model model = new PD_PartInfo03Model();
		PD_PartInfo03Dao dao = new PD_PartInfo03Dao();

		float monthValue = 0;
		String commTime = "";
		String dianliangValue = "";
		T_MonthAmDao monthMonthDao = new T_MonthAmDao();
		AmMeterPDDataDao pdDao = new AmMeterPDDataDao();
		AmMeterPDDatasModel apdModel = new AmMeterPDDatasModel();
		AmmeterModel ammModel = new AmmeterModel();
		try
		{
			int ammID = pdDao.getAmmeterIDByPdPartID(id);// 电表ID
			if(ammID!=-1){
				ammModel = ad.queryByID(ammID);// 为了的到计量电表的一些属性
				// Calendar now = Calendar.getInstance();
				// monthValue=monthMonthDao.queryMonthValue(ammID,
				// now.get(Calendar.YEAR), (now.get(Calendar.MONTH) + 1) );
				monthValue = monthMonthDao.queryMonthValue(ammID, year, month);// 当月用电
				commTime = ad.queryByID(ammID).getLastTime();// 最后通讯时间
				apdModel = pdDao.getLastConnData(ammID);// 首次加载读取数据库最新值
	
				dianliangValue = df.format(Double.parseDouble(pdDao.getLastDanData(ammID)));// 首次加载单相数据
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			model = dao.queryPD_PartInfo03(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		Gson gson = new Gson();
		if (model.getPart_ID() == 0)// 数据库没有数据
		{
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println("NoData");
			out.flush();
			out.close();
		} else
		{
			Object[] object =
			{ model, monthValue, commTime, apdModel, dianliangValue, ammModel };
			String result = gson.toJson(object);
			// String result =gson.toJson(new Object[]{model,monthValue});
			response.setContentType("application/x-json");
			// //System.out.println("回路信息："+result);
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		}

	}

	public void queryHLValue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException,
			SQLException
	{
		AmMeterPDDataDao pdDao = new AmMeterPDDataDao();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		int id = 0;// 回路ID
		int ammID = 0;
		String time = "";

		if (request.getParameter("huiLuID") != null)
		{
			id = Integer.parseInt(request.getParameter("huiLuID"));
		}
		// 通过回路查询出电表ID
		ammID = pdDao.getAmmeterIDByPdPartID(id);

		if (request.getParameter("time") != null)
		{
			time = request.getParameter("time");

		}
		map = pdDao.getSXHGL(time.toString(), ammID);

		Gson gson = new Gson();
		String result = gson.toJson(map);
		response.setContentType("application/x-json");
		// //System.out.println("三相数据合格率："+result);
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();

	}

	/**
	 * 单项
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmDanNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat df = new DecimalFormat("#####0.00");
		int huiLuID = Integer.parseInt(request.getParameter("huiLuID"));
		int ammID = 1;
		AmMeterPDDataDao pd = new AmMeterPDDataDao();

		// 通过回路ID得到电表ID
		ammID = pd.getAmmeterIDByPdPartID(huiLuID);

		GatherDao gd = new GatherDao();
		AmmeterDao ad = new AmmeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		AmmeterModel am = new AmmeterModel();

		am = ad.queryByID(ammID);
		String userID="";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		if (am != null)
		{
			Gather gm = new Gather();
			gm = gd.queryByID(am.getGatherID());
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
			// } else
			// // 在线
			// {
			//
			if (queryMeterProtocolByMeterStyleID(am.getMeteStyleID()) == 2)
			{
				CMMsendCommand aprm = new CMMsendCommand();

				SendingAPCode sxc = new SendingAPCode();
				com.sf.energy.prepayment.model.XMLCoder xc = aprm.readSanXiangJianCeYiDanXiang(ammID, "1");
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

									jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
									jo.put("ammTotalValue",
											df.format((Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv()) + am.getXiuzheng()));
									jo.put("commTime", result1.getAttribute("ValueTime"));
									// System.out.println("返回ajax的数据电表示数value："+
									// df.format(Double.parseDouble(result1.getAttribute("ZY0"))*am.getBeilv()));
									// //System.out.println("**************************************");
									// 抄表成功插入数据库ammeterData
									if(Float.parseFloat(result1.getAttribute("ZY0"))!=-1)
									 {
									 
										 CurrentMeasureDao cd = new CurrentMeasureDao();
										 cd.insertAfterCommDan(
												Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv() + am.getXiuzheng(),
												result1.getAttribute("ValueTime"), ammID,am.getBeilv());
										 AmmeterDao aDao = new AmmeterDao();
											/**
											 * 更新电表状态和最后通讯时间
											 */
											aDao.updateAmMeterWithLasttimeandState(ammID, result1.getAttribute("ValueTime"));
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

			} else
			{
				XMLCoder immXmlCoderD = new XMLCoder();// 单相
				int ammeNum = am.getAmmeterPoint();// 2
				String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0201
				immXmlCoderD.setCommandCodeAFN("0C");
				immXmlCoderD.setCommandCodeDA(ammPointNoDA);
				immXmlCoderD.setCommandCodeDT("0110");// 单相
				immXmlCoderD.setTerminalAddress(gd.queryGatherNumByAmmID(ammID));// 002700bb00
				immXmlCoderD.setDatasiteID(String.valueOf(gm.getDatasiteID()));
				immXmlCoderD.setOperCode(userID + "");
				String msg = "";
				try
				{
					msg = immSending.sendXMLToServer(immXmlCoderD);
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

									jo.put("value", df.format(Double.parseDouble(result1.getAttribute("ZY0"))));
									jo.put("ammTotalValue",
											df.format((Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv()) + am.getXiuzheng()));
									jo.put("commTime", result1.getAttribute("ValueTime"));
									// System.out.println("返回ajax的数据电表示数value："+
									// df.format(Double.parseDouble(result1.getAttribute("ZY0"))*am.getBeilv()));
									// //System.out.println("**************************************");
									// 抄表成功插入数据库ammeterData
									// if(Integer.parseInt(result1.getAttribute("ZY0"))!=-1)
									// {
									// CurrentMeasureDao cd=new
									// CurrentMeasureDao();
									// cd.insertAfterCommDan(Double.parseDouble(result1.getAttribute("ZY0")),
									// new Date().toLocaleString(), ammID);
									// }
									if(Float.parseFloat(result1.getAttribute("ZY0"))!=-1)
									 {
									 
										 CurrentMeasureDao cd = new CurrentMeasureDao();
										 cd.insertAfterCommDan(
												Double.parseDouble(result1.getAttribute("ZY0")) * am.getBeilv() + am.getXiuzheng(),
												result1.getAttribute("ValueTime"), ammID,am.getBeilv());
										 AmmeterDao aDao = new AmmeterDao();
											/**
											 * 更新电表状态和最后通讯时间
											 */
											aDao.updateAmMeterWithLasttimeandState(ammID, result1.getAttribute("ValueTime"));
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
			// }

		}

	}

	/**
	 * 三相
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void queryAmmSanNow(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int huiLuID = Integer.parseInt(request.getParameter("huiLuID"));
		int ammID = 1;
		AmMeterPDDataDao pd = new AmMeterPDDataDao();
		// 通过回路ID得到电表ID
		ammID = pd.getAmmeterIDByPdPartID(huiLuID);

		DecimalFormat df = new DecimalFormat("#####0.00");
		GatherDao gd = new GatherDao();
		AmmeterDao ad = new AmmeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();
		AmmeterModel am = new AmmeterModel();
		am = ad.queryByID(ammID);

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
			// } else
			// // 在线
			// {
			if (queryMeterProtocolByMeterStyleID(am.getMeteStyleID()) == 2)
			{
				CMMsendCommand aprm = new CMMsendCommand();

				SendingAPCode sxc = new SendingAPCode();
				com.sf.energy.prepayment.model.XMLCoder xc = aprm.readSanXiangJianCeYiSanXiang(ammID, "1");
				String msg = "";
				try
				{
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
				// //System.out.println(new Date().toLocaleString()+
				// ":服务器发回来的三相xml" + msg);

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
//									jo.put("PowerZY", df.format(Double.parseDouble(result1.getAttribute("PowerZY"))*am.getBeilv()));
//									jo.put("PowerZW", df.format(Double.parseDouble(result1.getAttribute("PowerZW"))*am.getBeilv()));
//									jo.put("VoltageA", df.format(Double.parseDouble(result1.getAttribute("VoltageA"))*am.getVolBeilv()));
//									jo.put("VoltageB", df.format(Double.parseDouble(result1.getAttribute("VoltageB"))*am.getVolBeilv()));
//									jo.put("VoltageC", df.format(Double.parseDouble(result1.getAttribute("VoltageC"))*am.getVolBeilv()));
//									jo.put("Current0", df.format(Double.parseDouble(result1.getAttribute("Current0"))*am.getColBeilv()));
//									jo.put("CurrentA", df.format(Double.parseDouble(result1.getAttribute("CurrentA"))*am.getColBeilv()));
//									jo.put("CurrentB", df.format(Double.parseDouble(result1.getAttribute("CurrentB"))*am.getColBeilv()));
//									jo.put("CurrentC", df.format(Double.parseDouble(result1.getAttribute("CurrentC"))*am.getColBeilv()));
//
//									jo.put("PowerAY", df.format(Double.parseDouble(result1.getAttribute("PowerAY"))*am.getBeilv()));
//									jo.put("PowerBY", df.format(Double.parseDouble(result1.getAttribute("PowerBY"))*am.getBeilv()));
//									jo.put("PowerCY", df.format(Double.parseDouble(result1.getAttribute("PowerCY"))*am.getBeilv()));
//									jo.put("PowerAW", df.format(Double.parseDouble(result1.getAttribute("PowerAW"))*am.getBeilv()));
//									jo.put("PowerBW", df.format(Double.parseDouble(result1.getAttribute("PowerBW"))*am.getBeilv()));
//									jo.put("PowerCW", df.format(Double.parseDouble(result1.getAttribute("PowerCW"))*am.getBeilv()));
//
//									jo.put("PowerFactorZ", df.format(Double.parseDouble(result1.getAttribute("PowerFactorZ"))));
//									jo.put("PowerFactorA", df.format(Double.parseDouble(result1.getAttribute("PowerFactorA"))));
//									jo.put("PowerFactorB", df.format(Double.parseDouble(result1.getAttribute("PowerFactorB"))));
//									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));
//									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));
//
//									jo.put("PowerSZZ", df.format(Double.parseDouble(result1.getAttribute("PowerSZZ"))*am.getBeilv()));
//									jo.put("PowerSZA", df.format(Double.parseDouble(result1.getAttribute("PowerSZA"))*am.getBeilv()));
//									jo.put("PowerSZB", df.format(Double.parseDouble(result1.getAttribute("PowerSZB"))*am.getBeilv()));
//									jo.put("PowerSZC", df.format(Double.parseDouble(result1.getAttribute("PowerSZC"))*am.getBeilv()));
									if(Double.parseDouble(result1.getAttribute("PowerZY"))!=-1)
										jo.put("PowerZY", df.format(Double.parseDouble(result1.getAttribute("PowerZY"))*am.getBeilv()));
									else
										jo.put("PowerZY", -1);
									if(Double.parseDouble(result1.getAttribute("PowerZW"))!=-1)
										jo.put("PowerZW", df.format(Double.parseDouble(result1.getAttribute("PowerZW"))*am.getBeilv()));
									else
										jo.put("PowerZW", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageA"))!=-1)
										jo.put("VoltageA", df.format(Double.parseDouble(result1.getAttribute("VoltageA"))*am.getVolBeilv()));
									else
										jo.put("VoltageA", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageB"))!=-1)
										jo.put("VoltageB", df.format(Double.parseDouble(result1.getAttribute("VoltageB"))*am.getVolBeilv()));
									else
										jo.put("VoltageB", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageC"))!=-1)
										jo.put("VoltageC", df.format(Double.parseDouble(result1.getAttribute("VoltageC"))*am.getVolBeilv()));
									else
										jo.put("VoltageC", -1);
									if(Double.parseDouble(result1.getAttribute("Current0"))!=-1)
										jo.put("Current0", df.format(Double.parseDouble(result1.getAttribute("Current0"))*am.getColBeilv()));
									else
										jo.put("Current0", -1);
									if(Double.parseDouble(result1.getAttribute("CurrentA"))!=-1)
										jo.put("CurrentA", df.format(Double.parseDouble(result1.getAttribute("CurrentA"))*am.getColBeilv()));
									else
										jo.put("CurrentA", -1);
									if(Double.parseDouble(result1.getAttribute("CurrentB"))!=-1)
										jo.put("CurrentB", df.format(Double.parseDouble(result1.getAttribute("CurrentB"))*am.getColBeilv()));
									else
										jo.put("CurrentB", -1);
									
									if(Double.parseDouble(result1.getAttribute("CurrentC"))!=-1)
										jo.put("CurrentC", df.format(Double.parseDouble(result1.getAttribute("CurrentC"))*am.getColBeilv()));
									else
										jo.put("CurrentC", -1);
									if(Double.parseDouble(result1.getAttribute("PowerAY"))!=-1)
										jo.put("PowerAY", df.format(Double.parseDouble(result1.getAttribute("PowerAY"))*am.getBeilv()));
									else
										jo.put("PowerAY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerBY"))!=-1)
										jo.put("PowerBY", df.format(Double.parseDouble(result1.getAttribute("PowerBY"))*am.getBeilv()));
									else
										jo.put("PowerBY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerCY"))!=-1)
										jo.put("PowerCY", df.format(Double.parseDouble(result1.getAttribute("PowerCY"))*am.getBeilv()));
									else
										jo.put("PowerCY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerAW"))!=-1)
										jo.put("PowerAW", df.format(Double.parseDouble(result1.getAttribute("PowerAW"))*am.getBeilv()));
									else
										jo.put("PowerAW", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerBW"))!=-1)
										jo.put("PowerBW", df.format(Double.parseDouble(result1.getAttribute("PowerBW"))*am.getBeilv()));
									else
										jo.put("PowerBW", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerCW"))!=-1)
										jo.put("PowerCW", df.format(Double.parseDouble(result1.getAttribute("PowerCW"))*am.getBeilv()));
									else
										jo.put("PowerCW", -1);
//									jo.put("PowerAW", df.format(Double.parseDouble(result1.getAttribute("PowerAW"))*am.getBeilv()));
//									jo.put("PowerBW", df.format(Double.parseDouble(result1.getAttribute("PowerBW"))*am.getBeilv()));
//									jo.put("PowerCW", df.format(Double.parseDouble(result1.getAttribute("PowerCW"))*am.getBeilv()));

									jo.put("PowerFactorZ", df.format(Double.parseDouble(result1.getAttribute("PowerFactorZ"))));
									jo.put("PowerFactorA", df.format(Double.parseDouble(result1.getAttribute("PowerFactorA"))));
									jo.put("PowerFactorB", df.format(Double.parseDouble(result1.getAttribute("PowerFactorB"))));
									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));
									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));

									
									
									if(Double.parseDouble(result1.getAttribute("PowerSZZ"))!=-1)
										jo.put("PowerSZZ", df.format(Double.parseDouble(result1.getAttribute("PowerSZZ"))*am.getBeilv()));
									else
										jo.put("PowerSZZ", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerSZA"))!=-1)
										jo.put("PowerSZA", df.format(Double.parseDouble(result1.getAttribute("PowerSZA"))*am.getBeilv()));
									else
										jo.put("PowerSZA", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerSZB"))!=-1)
										jo.put("PowerSZB", df.format(Double.parseDouble(result1.getAttribute("PowerSZB"))*am.getBeilv()));
									else
										jo.put("PowerSZB", -1);
									if(Double.parseDouble(result1.getAttribute("PowerSZC"))!=-1)
										jo.put("PowerSZC", df.format(Double.parseDouble(result1.getAttribute("PowerSZC"))*am.getBeilv()));
									else
										jo.put("PowerSZC", -1);
									jo.put("commTime", result1.getAttribute("ValueTime"));
									// //System.out.println("返回ajax的数据valueGonglv："+
									// df.format(Double.parseDouble(result1.getAttribute("PowerZY"))));
									// //System.out.println("**************************************");
									// 抄表成功插入数据库ammeter
									// CurrentMeasureDao cd=new
									// CurrentMeasureDao();
									// cd.insertAfterCommSan(Double.parseDouble(result1.getAttribute("PowerZY")),
									// new Date().toLocaleString(), ammID);
									 AmmeterDao aDao = new AmmeterDao();
										/**
										 * 更新电表状态和最后通讯时间
										 */
										aDao.updateAmMeterWithLasttimeandState(ammID, result1.getAttribute("ValueTime"));
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
			} else
			{
				int ammeNum = am.getAmmeterPoint();// 2
				String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0201

				// 三相
				XMLCoder immXmlCoderS = new XMLCoder();
				immXmlCoderS.setCommandCodeAFN("0C");
				immXmlCoderS.setCommandCodeDA(ammPointNoDA);
				immXmlCoderS.setCommandCodeDT("0103");// 三相
				immXmlCoderS.setTerminalAddress(gd.queryGatherNumByAmmID(ammID));// 002700bb00

				String msg = "";
				try
				{
					msg = immSending.sendXMLToServer(immXmlCoderS);
					System.out.println(msg);
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
				// //System.out.println(new Date().toLocaleString()+
				// ":服务器发回来的三相xml" + msg);

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
									if(Double.parseDouble(result1.getAttribute("PowerZY"))!=-1)
										jo.put("PowerZY", df.format(Double.parseDouble(result1.getAttribute("PowerZY"))*am.getBeilv()));
									else
										jo.put("PowerZY", -1);
									if(Double.parseDouble(result1.getAttribute("PowerZW"))!=-1)
										jo.put("PowerZW", df.format(Double.parseDouble(result1.getAttribute("PowerZW"))*am.getBeilv()));
									else
										jo.put("PowerZW", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageA"))!=-1)
										jo.put("VoltageA", df.format(Double.parseDouble(result1.getAttribute("VoltageA"))*am.getVolBeilv()));
									else
										jo.put("VoltageA", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageB"))!=-1)
										jo.put("VoltageB", df.format(Double.parseDouble(result1.getAttribute("VoltageB"))*am.getVolBeilv()));
									else
										jo.put("VoltageB", -1);
									if(Double.parseDouble(result1.getAttribute("VoltageC"))!=-1)
										jo.put("VoltageC", df.format(Double.parseDouble(result1.getAttribute("VoltageC"))*am.getVolBeilv()));
									else
										jo.put("VoltageC", -1);
									if(Double.parseDouble(result1.getAttribute("Current0"))!=-1)
										jo.put("Current0", df.format(Double.parseDouble(result1.getAttribute("Current0"))*am.getColBeilv()));
									else
										jo.put("Current0", -1);
									if(Double.parseDouble(result1.getAttribute("CurrentA"))!=-1)
										jo.put("CurrentA", df.format(Double.parseDouble(result1.getAttribute("CurrentA"))*am.getColBeilv()));
									else
										jo.put("CurrentA", -1);
									if(Double.parseDouble(result1.getAttribute("CurrentB"))!=-1)
										jo.put("CurrentB", df.format(Double.parseDouble(result1.getAttribute("CurrentB"))*am.getColBeilv()));
									else
										jo.put("CurrentB", -1);
									
									if(Double.parseDouble(result1.getAttribute("CurrentC"))!=-1)
										jo.put("CurrentC", df.format(Double.parseDouble(result1.getAttribute("CurrentC"))*am.getColBeilv()));
									else
										jo.put("CurrentC", -1);
									if(Double.parseDouble(result1.getAttribute("PowerAY"))!=-1)
										jo.put("PowerAY", df.format(Double.parseDouble(result1.getAttribute("PowerAY"))*am.getBeilv()));
									else
										jo.put("PowerAY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerBY"))!=-1)
										jo.put("PowerBY", df.format(Double.parseDouble(result1.getAttribute("PowerBY"))*am.getBeilv()));
									else
										jo.put("PowerBY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerCY"))!=-1)
										jo.put("PowerCY", df.format(Double.parseDouble(result1.getAttribute("PowerCY"))*am.getBeilv()));
									else
										jo.put("PowerCY", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerAW"))!=-1)
										jo.put("PowerAW", df.format(Double.parseDouble(result1.getAttribute("PowerAW"))*am.getBeilv()));
									else
										jo.put("PowerAW", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerBW"))!=-1)
										jo.put("PowerBW", df.format(Double.parseDouble(result1.getAttribute("PowerBW"))*am.getBeilv()));
									else
										jo.put("PowerBW", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerCW"))!=-1)
										jo.put("PowerCW", df.format(Double.parseDouble(result1.getAttribute("PowerCW"))*am.getBeilv()));
									else
										jo.put("PowerCW", -1);
//									jo.put("PowerAW", df.format(Double.parseDouble(result1.getAttribute("PowerAW"))*am.getBeilv()));
//									jo.put("PowerBW", df.format(Double.parseDouble(result1.getAttribute("PowerBW"))*am.getBeilv()));
//									jo.put("PowerCW", df.format(Double.parseDouble(result1.getAttribute("PowerCW"))*am.getBeilv()));

									jo.put("PowerFactorZ", df.format(Double.parseDouble(result1.getAttribute("PowerFactorZ"))));
									jo.put("PowerFactorA", df.format(Double.parseDouble(result1.getAttribute("PowerFactorA"))));
									jo.put("PowerFactorB", df.format(Double.parseDouble(result1.getAttribute("PowerFactorB"))));
									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));
									jo.put("PowerFactorC", df.format(Double.parseDouble(result1.getAttribute("PowerFactorC"))));

									
									
									if(Double.parseDouble(result1.getAttribute("PowerSZZ"))!=-1)
										jo.put("PowerSZZ", df.format(Double.parseDouble(result1.getAttribute("PowerSZZ"))*am.getBeilv()));
									else
										jo.put("PowerSZZ", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerSZA"))!=-1)
										jo.put("PowerSZA", df.format(Double.parseDouble(result1.getAttribute("PowerSZA"))*am.getBeilv()));
									else
										jo.put("PowerSZA", -1);
									
									if(Double.parseDouble(result1.getAttribute("PowerSZB"))!=-1)
										jo.put("PowerSZB", df.format(Double.parseDouble(result1.getAttribute("PowerSZB"))*am.getBeilv()));
									else
										jo.put("PowerSZB", -1);
									if(Double.parseDouble(result1.getAttribute("PowerSZC"))!=-1)
										jo.put("PowerSZC", df.format(Double.parseDouble(result1.getAttribute("PowerSZC"))*am.getBeilv()));
									else
										jo.put("PowerSZC", -1);
//									jo.put("PowerSZZ", df.format(Double.parseDouble(result1.getAttribute("PowerSZZ"))*am.getBeilv()));
//									jo.put("PowerSZA", df.format(Double.parseDouble(result1.getAttribute("PowerSZA"))*am.getBeilv()));
//									jo.put("PowerSZB", df.format(Double.parseDouble(result1.getAttribute("PowerSZB"))*am.getBeilv()));
//									jo.put("PowerSZC", df.format(Double.parseDouble(result1.getAttribute("PowerSZC"))*am.getBeilv()));
									jo.put("commTime", result1.getAttribute("ValueTime"));
									// //System.out.println("返回ajax的数据valueGonglv："+
									// df.format(Double.parseDouble(result1.getAttribute("PowerZY"))));
									// //System.out.println("**************************************");
									// 抄表成功插入数据库ammeter
									// CurrentMeasureDao cd=new
									// CurrentMeasureDao();
									// cd.insertAfterCommSan(Double.parseDouble(result1.getAttribute("PowerZY")),
									// new Date().toLocaleString(), ammID);
									 AmmeterDao aDao = new AmmeterDao();
										/**
										 * 更新电表状态和最后通讯时间
										 */
									 aDao.updateAmMeterWithLasttimeandState(ammID, result1.getAttribute("ValueTime"));
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
