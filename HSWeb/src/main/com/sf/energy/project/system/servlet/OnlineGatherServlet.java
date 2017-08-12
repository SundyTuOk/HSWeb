package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.manager.current.service.XMLHelper;
import com.sf.energy.manager.current.serviceImpl.CleanGather;
import com.sf.energy.manager.current.serviceImpl.SendingCheckTimeCode;
import com.sf.energy.manager.current.serviceImpl.SendingReadEditionCode;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.maintenance.OnlineGather;

public class OnlineGatherServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		// System.out.println("method:" + method);

		if ("onlineGatherPaginate".equals(method))
			onlineGatherPaginate(request, response);
		// 参数读取
		if ("getParameters".equals(method))
			getParameters(request, response);
		// 表计下达
		if ("sendMeters".equals(method))
			sendMeters(request, response);
		// 补抄数据
		if ("gatherFillDatas".equals(method))
			gatherFillDatas(request, response);
		// 网关校时
		if ("gatherCheckTime".equals(method))
			gatherCheckTime(request, response);
		// 除通信外全部初始化
		if ("gatherInitExceptComm".equals(method))
			gatherInitExceptComm(request, response);
		// 除通信外全部初始化
		if ("gatherSetReportTime".equals(method))
			gatherSetReportTime(request, response);
		// 修改主站IP和端口
		if ("gatherChangeIP".equals(method))
			gatherChangeIP(request, response);
		// 版本读取
		if ("gatherReadEdition".equals(method))
			gatherReadEdition(request, response);
		// 时钟读取
		if ("gatherReadTime".equals(method))
			gatherReadTime(request, response);
		// 参数读取发送命令
		if ("sendParametersCode".equals(method))
			sendParametersCode(request, response);
		// 表计下发发送命令
		if ("sendMetersCode".equals(method))
			sendMetersCode(request, response);

	}

	private void gatherReadTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		SendingXMLCode sxc = new SendingReadEditionCode();
		XMLCoder xc = og.readTime(terminalAddress, userID);
		XMLHelper xmlHelper = new XMLHelper();
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String msg = "";

		try
		{
			msg = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时！");

			json.add(jo);

			og.updateGather("", "时钟读取，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		if (!"".equals(msg))
		{
			NodeList lis = null;
			String err;
			lis = xmlHelper.getNodeList(msg, "commandback");

			for (int j = 0; j < lis.getLength(); j++)
			{
				Element commandBack = (Element) lis.item(j);
				NodeList list = getNodeList(msg, "commandback");
				if (commandBack.hasAttribute("error"))
				{
					err = commandBack.getAttribute("error");
					// 失败
					if (err.equals("1"))
					{
						String errormessage = commandBack.getAttribute("errormessage");
						jo = new JSONObject();
						jo.put("errormessage", errormessage);
						jo.put("isError", err);
						json.add(jo);
						og.updateGather("", "时钟读取，失败", gatherID);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						jo = new JSONObject();
						jo.put("isError", err);
						json.add(jo);
						list = getNodeList(msg, "result");
						jo = new JSONObject();
						Element result = (Element) list.item(0);
						jo.put("value", result.getAttribute("value"));
						json.add(jo);
						og.updateGather(sdf.format(time), "时钟读取，成功", gatherID);
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

	private void sendMetersCode(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String command = request.getParameter("command");
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int gatherID = Integer.parseInt(request.getParameter("gatherID"));
		XMLHelper xmlHelper = new XMLHelper();
		String msg = "";
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		try
		{
			msg = og.sendCommand(command);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", 2);
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

		if (!"".equals(msg))
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
						og.updateGather("", "表计下达，失败", gatherID);
						jo.put("errormessage", errormessage);
						jo.put("isError", err);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						jo = new JSONObject();
						jo.put("isError", err);
						og.updateGather(sdf.format(time), "表计下达，成功", gatherID);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					}
				}
			}
			// NodeList list = getNodeList(msg, "commandback");
			// Element error = (Element) list.item(0);
			// String isError = error.getAttribute("error");
			//
			// PrintWriter out = response.getWriter();
			// out.println(isError);
			// out.flush();
			// out.close();
		}

	}

	private void sendParametersCode(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XMLHelper xmlHelper = new XMLHelper();
		String command = request.getParameter("command");
		int gatherID = Integer.parseInt(request.getParameter("gatherID"));
		String msg = "";

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		try
		{
			msg = og.sendCommand(command);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时重发！");

			json.add(jo);
			og.updateGather("", "参数读取，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		if (!"".equals(msg))
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
						jo.put("isError", err);
						og.updateGather("", "参数读取，失败", gatherID);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						NodeList list = getNodeList(msg, "commandback");
						jo = new JSONObject();
						jo.put("isError", err);
						json.add(jo);
						list = getNodeList(msg, "result");
						for (int i = 0; i < list.getLength(); i++)
						{
							jo = new JSONObject();
							Element result = (Element) list.item(i);
							jo.put("value", result.getAttribute("value"));
							json.add(jo);
						}
						og.updateGather(sdf.format(time), "参数读取，成功", gatherID);
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

	private void gatherReadEdition(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		SendingXMLCode sxc = new SendingReadEditionCode();
		XMLCoder xc = og.readEition(terminalAddress, userID);
		XMLHelper xmlHelper = new XMLHelper();
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String msg = "";

		try
		{
			msg = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时！");

			json.add(jo);

			og.updateGather("", "版本读取，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		if (!"".equals(msg))
		{
			NodeList lis = null;
			String err;
			lis = xmlHelper.getNodeList(msg, "commandback");

			for (int j = 0; j < lis.getLength(); j++)
			{
				Element commandBack = (Element) lis.item(j);
				NodeList list = getNodeList(msg, "commandback");
				if (commandBack.hasAttribute("error"))
				{
					err = commandBack.getAttribute("error");
					// 失败
					if (err.equals("1"))
					{
						String errormessage = commandBack.getAttribute("errormessage");
						jo = new JSONObject();
						jo.put("errormessage", errormessage);
						jo.put("isError", err);
						json.add(jo);
						og.updateGather("", "版本读取，失败", gatherID);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						jo = new JSONObject();
						jo.put("isError", err);
						json.add(jo);
						list = getNodeList(msg, "result");
						jo = new JSONObject();
						Element result = (Element) list.item(0);
						jo.put("value", result.getAttribute("value"));
						json.add(jo);
						og.updateGather(sdf.format(time), "版本读取，成功", gatherID);
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

	private void gatherCheckTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		String datasiteID = request.getParameter("datasiteID");
		SendingXMLCode sxc = new SendingCheckTimeCode();
		XMLCoder xc = og.gatherCheckTime(terminalAddress, userID,datasiteID);
		XMLHelper xmlHelper = new XMLHelper();
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		String str = "";
		try
		{
			str = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			og.updateGather("", "网关校时，失败", gatherID);

			jo.put("isError", "2");
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

		if (!"".equals(str))
		{
			NodeList lis = null;
			String err;
			lis = xmlHelper.getNodeList(str, "commandback");

			for (int j = 0; j < lis.getLength(); j++)
			{
				Element commandBack = (Element) lis.item(j);
				if (commandBack.hasAttribute("error"))
				{
					err = commandBack.getAttribute("error");
					// 失败
					if (err.equals("1"))
					{
						og.updateGather("", "网关校时，失败", gatherID);
						String errormessage = commandBack.getAttribute("errormessage");
						jo = new JSONObject();
						jo.put("isError", err);
						jo.put("errormessage", errormessage);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						og.updateGather(sdf.format(time), "网关校时，成功", gatherID);
						jo = new JSONObject();
						jo.put("isError", err);
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

	private void gatherFillDatas(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		String filltime = "";
		if (request.getParameter("time") != null)
		{
			filltime = request.getParameter("time");
		}
		SendingXMLCode sxc = new SendingCheckTimeCode();
		XMLCoder xc = og.gatherFillDatas(terminalAddress, filltime, userID);

		String str = "";
		try
		{
			str = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			String isError = "2";
			og.updateGather("", "发送，失败", gatherID);

			PrintWriter out = response.getWriter();
			out.println(isError);
			out.flush();
			out.close();
		}
		PrintWriter out = response.getWriter();
		out.println("发送成功");
		out.flush();
		out.close();
		/*
		 * if(!"".equals(str)) { NodeList list = getNodeList(str,
		 * "commandback"); Element result = (Element) list.item(0); String
		 * isError = result.getAttribute("error");
		 * 
		 * if("1".equals(isError)) { og.updateGather("", "发送，失败", gatherID);
		 * }else { og.updateGather(sdf.format(time), "发送，成功", gatherID); }
		 * 
		 * PrintWriter out = response.getWriter(); out.println(isError);
		 * out.flush(); out.close(); }
		 */

	}

	private void gatherInitExceptComm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		SendingXMLCode sxc = new SendingReadEditionCode();
		XMLCoder xc = og.gatherInitExceptComm(terminalAddress, userID);
		XMLHelper xmlHelper = new XMLHelper();
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String str = "";

		try
		{
			str = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时！");

			json.add(jo);

			og.updateGather("", "网关初始化，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		if (!"".equals(str))
		{
			NodeList lis = null;
			String err;
			lis = xmlHelper.getNodeList(str, "commandback");

			for (int j = 0; j < lis.getLength(); j++)
			{
				Element commandBack = (Element) lis.item(j);
				if (commandBack.hasAttribute("error"))
				{
					err = commandBack.getAttribute("error");
					// 失败
					if (err.equals("1"))
					{
						og.updateGather("", "网关初始化，失败", gatherID);
						String errormessage = commandBack.getAttribute("errormessage");
						jo = new JSONObject();
						jo.put("isError", err);
						jo.put("errormessage", errormessage);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();
					} else if (err.equals("0")) // 成功
					{
						og.updateGather(sdf.format(time), "网关初始化，成功", gatherID);
						jo = new JSONObject();
						jo.put("isError", err);
						json.add(jo);
						response.setContentType("application/x-json");
						PrintWriter out = response.getWriter();
						out.println(json.toString());
						out.flush();
						out.close();

					}
				}
			}
			// if (!"".equals(str))
			// {

			// NodeList list = getNodeList(str, "commandback");
			// Element result = (Element) list.item(0);
			// String isError = result.getAttribute("error");
			//
			// if ("1".equals(isError))
			// {
			//
			// og.updateGather("", "网关初始化，失败", gatherID);
			// } else
			// {
			// og.updateGather(sdf.format(time), "网关初始化，成功", gatherID);
			// }
			//
			// PrintWriter out = response.getWriter();
			// out.println(isError);
			// out.flush();
			// out.close();
		}

	}

	private void gatherSetReportTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}
		int reportTime = 0;
		if (request.getParameter("reportTime") != null)
		{
			reportTime = Integer.parseInt(request.getParameter("reportTime"));
		}
		SendingXMLCode sxc = new SendingReadEditionCode();
		XMLCoder xc = og.gatherReport(terminalAddress, userID, reportTime);

		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String str = "";

		try
		{
			str = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时！");

			json.add(jo);

			og.updateGather("", "设置定时上传，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}

		if (!"".equals(str))
		{
			NodeList list = getNodeList(str, "commandback");
			Element result = (Element) list.item(0);
			String isError = result.getAttribute("error");
			xc = og.gatherReportStart(terminalAddress, userID);
			str = sxc.sendXMLToServer(xc);
			if (!"".equals(str))
			{
				PrintWriter out = response.getWriter();
				out.println(isError);
				out.flush();
				out.close();
			}

		}

	}

	private void gatherChangeIP(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String terminalAddress = "";
		if (request.getParameter("onlineGatherAddress") != null)
		{
			terminalAddress = request.getParameter("onlineGatherAddress");
		}
		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}

		String IP = request.getParameter("ip");
		String port = request.getParameter("port");
		String datasiteID = request.getParameter("datasiteID");
		SendingXMLCode sxc = new SendingReadEditionCode();
		XMLCoder xc = og.gatherChangeIP(terminalAddress, userID, IP, port,datasiteID);
		XMLHelper xmlHelper = new XMLHelper();
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		String str = "";

		try
		{
			str = sxc.sendXMLToServer(xc);
		} catch (SocketTimeoutException e)
		{
			jo = new JSONObject();
			jo.put("isError", "2");
			jo.put("message", "命令超时！");

			json.add(jo);

			og.updateGather("", "修改主站IP，失败", gatherID);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		if (!"".equals(str))
		{
			NodeList gwList = null;
			String task;
			gwList = xmlHelper.getNodeList(str, "GW");
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
						lis = xmlHelper.getNodeList(str, "commandback");

						for (int j = 0; j < lis.getLength(); j++)
						{
							Element commandBack = (Element) lis.item(j);
							if (commandBack.hasAttribute("error"))
							{
								err = commandBack.getAttribute("error");
								// 失败
								if (err.equals("1"))
								{
									og.updateGather("", "修改主站IP，失败", gatherID);
									String errormessage = commandBack.getAttribute("errormessage");
									jo = new JSONObject();
									jo.put("isError", err);
									jo.put("errormessage", errormessage);
									json.add(jo);
									response.setContentType("application/x-json");
									PrintWriter out = response.getWriter();
									out.println(json.toString());
									out.flush();
									out.close();
								} else if (err.equals("0")) // 成功
								{
									og.updateGather(sdf.format(time), "修改主站IP，成功", gatherID);
									jo = new JSONObject();
									jo.put("isError", err);
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
		} else
		{
			// JSONArray json = new JSONArray();
			// JSONObject jo = new JSONObject();
			//			jo.put("ServerOffline", 4);// 返回任务编号错误
			jo.put("isError", "4");
			jo.put("message", "返回任务编号错误！");
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		// }
		// if (!"".equals(str))
		// {
		// NodeList list = getNodeList(str, "commandback");
		// Element result = (Element) list.item(0);
		// String isError = result.getAttribute("error");
		//
		// if ("1".equals(isError))
		// {
		// og.updateGather("", "修改主站IP，失败", gatherID);
		// } else
		// {
		// og.updateGather(sdf.format(time), "修改主站IP，成功", gatherID);
		// }
		//
		// PrintWriter out = response.getWriter();
		// out.println(isError);
		// out.flush();
		// out.close();
		// }

	}

	private void sendMeters(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		int gatherID = 0;
		if (request.getParameter("onlineGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		}

		String msg = "";
		for (int i = 0; i < 2; i++)
		{
			SendingXMLCode sxc1 = new CleanGather();
			XMLCoder xc1 = og.cleanGather(gatherID, i + 2, userID);
			try
			{
				msg = sxc1.sendXMLToServer(xc1);
				System.out.println(msg);
			} catch (SocketTimeoutException e)
			{
				jo = new JSONObject();
				jo.put("isError", "2");
				jo.put("message", "命令超时！");

				json.add(jo);

				og.updateGather("", "表计下达,失败", gatherID);

				response.setContentType("application/x-json");

				PrintWriter out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			}
		}

		// if(!"".equals(msg))//李嵘20150827 清空失败也下发表档案，因为有的网关清空命令返回否认
		// {
		jo = new JSONObject();
		jo.put("isError", "0");
		json.add(jo);

		String[] sendList = og.sendMeters(gatherID, userID);

		for (int i = 0; i < sendList.length; i++)
		{
			// System.out.println(sendList[i]);
			jo = new JSONObject();
			jo.put("sendCode", sendList[i]);
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		// }

		// String isError = "0";
		// String returnMsg = "";
		// int meterNum = 0;
		//
		// SendingXMLCode sxc = new SendingMeterCode();
		// ArrayList<XMLCoder> xmlList = og.sendMeters(gatherID);
		// for(int i= 0; i<xmlList.size(); i++)
		// {
		// XMLCoder xc = xmlList.get(i);
		// meterNum = xc.getCount();
		// String str = sxc.sendXMLToServer(xc);
		// //System.out.println(str);
		//
		// NodeList list = getNodeList(str, "commandback");
		// Element result = (Element) list.item(0);
		// isError = result.getAttribute("error");
		//
		// if("1".equals(isError))
		// {
		// returnMsg += "，第"+i+"帧下发失败！";
		// }
		// }
		//
		// if("".equals(returnMsg))
		// {
		// returnMsg = "成功下发" + meterNum + "块表计";
		// }else
		// {
		// returnMsg = "总计下发" + meterNum + "块表计" + returnMsg;
		// }
		//
		//
		// if("1".equals(isError))
		// {
		// // for(int i = 0; i<2; i++)
		// // {
		// // SendingXMLCode sxc2 = new CleanGather();
		// // XMLCoder xc2 = og.cleanGather(gatherID, i+2);
		// //
		// // rt[i] = sxc2.sendXMLToServer(xc2);
		// // }
		// og.updateGather(sdf.format(time), "表计下达，失败", gatherID);
		// }else
		// {
		// og.updateGather(sdf.format(time), "表计下达，共下挂"+meterNum+"块设备成功",
		// gatherID);
		// }

	}

	private void getParameters(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OnlineGather og = new OnlineGather();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}

		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int gatherID = Integer.parseInt(request.getParameter("onlineGatherID"));
		String gatherAddress = request.getParameter("onlineGatherAddress");
		String datasiteid=request.getParameter("datasiteID");
		String[] sendList = og.getParameters(gatherAddress, gatherID, userID,datasiteid);

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		jo = new JSONObject();
		jo.put("gatherID", gatherID);
		json.add(jo);

		if(sendList.length>0){
			for (int i = 0; i < sendList.length; i++)
			{
				jo = new JSONObject();
				jo.put("sendCode", sendList[i]);
				json.add(jo);
			}
		}

		// SendingXMLCode sxc = new GetParameters();
		// ArrayList<XMLCoder> xmlList = og.getParameters(gatherAddress,
		// gatherID);
		//
		// JSONArray json = new JSONArray();
		// JSONObject jo = null;
		//
		// for(int i= 0; i<xmlList.size(); i++)
		// {
		// XMLCoder xc = xmlList.get(i);
		// String msg = sxc.sendXMLToServer(xc);
		//
		// NodeList list = getNodeList(msg, "commandback");
		// Element error = (Element) list.item(0);
		// String isError = error.getAttribute("error");
		//
		// if("0".equals(isError))
		// {
		// jo = new JSONObject();
		// list = getNodeList(msg, "result");
		// for(int j =0;j<list.getLength();j++)
		// {
		// jo = new JSONObject();
		// Element result = (Element) list.item(j);
		// jo.put("value", result.getAttribute("value"));
		// json.add(jo);
		// }
		// }
		// }

		// int totalMeter = xc.getCount();
		//
		// String msg = sxc.sendXMLToServer(xc);
		//
		// JSONArray json = new JSONArray();
		// JSONObject jo = null;
		//
		// NodeList list = getNodeList(msg, "commandback");
		// Element error = (Element) list.item(0);
		// String isError = error.getAttribute("error");
		// if("0".equals(isError))
		// {
		// jo = new JSONObject();
		// jo.put("isError", isError);
		// jo.put("totalMeter",totalMeter);
		// json.add(jo);
		// list = getNodeList(msg, "result");
		// for(int i =0;i<list.getLength();i++)
		// {
		// jo = new JSONObject();
		// Element result = (Element) list.item(i);
		// jo.put("value", result.getAttribute("value"));
		// json.add(jo);
		// }
		// og.updateGather(sdf.format(time), "参数读取，共读取"+totalMeter+"块设备成功",
		// gatherID);
		// }else
		// {
		// jo = new JSONObject();
		// jo.put("isError", isError);
		// json.add(jo);
		// og.updateGather(sdf.format(time), "参数读取，失败", gatherID);
		// }

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void onlineGatherPaginate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		OnlineGather og = new OnlineGather();
		Integer thePageCount = Integer.parseInt(request.getParameter("onlineGatherPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("onlineGatherPageIndex"));
		Integer archid = Integer.parseInt(request.getParameter("archID"));
		Integer areaid = Integer.parseInt(request.getParameter("areaID"));

		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");

		ArrayList<Gather> list = og.display(archid, areaid, thePageCount, thePageIndex, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", og.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		DataSiteDao dSiteDao=new DataSiteDao();
		for (Gather gather : list)
		{
			jo = new JSONObject();
			jo.put("datasiteName", dSiteDao.queryNameById(gather.getDatasiteID()));
			jo.put("datasiteID", gather.getDatasiteID());
			jo.put("gatherID", gather.getGatherID());
			jo.put("gatherName", gather.getGatherName());
			jo.put("gatherAddress", gather.getGatherAddress());
			jo.put("ip", gather.getIp());
			jo.put("lastTime", gather.getLastTime());
			jo.put("gatherState", gather.getGatherStateName());
			jo.put("lastSetTime", gather.getLastSetTime());
			jo.put("lastSetMsg", gather.getLastSetMsg());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
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

}
