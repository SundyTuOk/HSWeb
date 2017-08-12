package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.sf.energy.light.dao.SLControlDao;
import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.manager.current.service.SendingXMLCode;
import com.sf.energy.manager.current.serviceImpl.SendingLampController;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class SLControlServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("addSLControl".equals(method))
			addSLControl(request, response);
		if ("queryOldInfo".equals(method))
			queryOldInfo(request, response);
		if ("deleteInfo".equals(method))
			deleteInfo(request, response);
		if ("updateSLControl".equals(method))
			updateSLControl(request, response);
		if ("queryAllInfo".equals(method))
			queryAllInfo(request, response);
		
		if ("slControllerDown".equals(method))
			slControllerDown(request, response);
		
		if ("queryByGatherID".equals(method))
			queryByGatherID(request, response);
		
	}

	private void queryByGatherID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLControlDao slcd =new SLControlDao();

		int gatherID = 0;
		if(request.getParameter("gatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("gatherID"));
		}
		
		ArrayList<SLControlModel> list = slcd.queryControllerByGatherID(gatherID);
		
		JSONArray json = new JSONArray();
		JSONObject jo;
		for(SLControlModel slcm : list)
		{
			jo = new JSONObject();
			jo.put("slControlID", slcm.getSLKONGZHI_ID());
			jo.put("slControlAreaID", slcm.getAREA_ID());
			jo.put("slControlGather", slcm.getSLGATHER_ID());
			jo.put("slControlNum", slcm.getSLKONGZHI_NUM());
			jo.put("slControlName", slcm.getSLKONGZHI_NAME());
			jo.put("slControl485Address", slcm.getSLKONGZHI_485ADDRESS());
			jo.put("slControlPort", slcm.getSLKONGZHI_PORT());
			jo.put("slControlSize", slcm.getSLKONGZHI_SIZE());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void slControllerDown(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, UnknownHostException, IOException
	{
		SLControlDao slcd =new SLControlDao();	
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") != null)
		{
			userID = session.getAttribute("userName").toString();
		}
		
		int gatherID = 0;
		if(request.getParameter("slControlGatherID") != null)
		{
			gatherID = Integer.parseInt(request.getParameter("slControlGatherID"));
		}
		String address = "";
		if(request.getParameter("slControl485Address") != null)
		{
			address = request.getParameter("slControl485Address");
		}
		int port = 0;
		if(request.getParameter("slControlPort") != null)
		{
			port = Integer.parseInt(request.getParameter("slControlPort"));
		}
		SendingXMLCode sxc = new SendingLampController();
		XMLCoder xc = slcd.lampDown(gatherID, address, port,userID);
		
		String str = sxc.sendXMLToServer(xc);
		
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
		out.close();
	}

	private void queryAllInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLControlDao slcd =new SLControlDao();	
		ArrayList<SLControlModel> list = slcd.queryAll();
		
		JSONArray json = new JSONArray();
		JSONObject jo;
		for(SLControlModel slcm : list)
		{
			jo = new JSONObject();
			jo.put("slControlID", slcm.getSLKONGZHI_ID());
			jo.put("slControlAreaID", slcm.getAREA_ID());
			jo.put("slControlGather", slcm.getSLGATHER_ID());
			jo.put("slControlNum", slcm.getSLKONGZHI_NUM());
			jo.put("slControlName", slcm.getSLKONGZHI_NAME());
			jo.put("slControl485Address", slcm.getSLKONGZHI_485ADDRESS());
			jo.put("slControlPort", slcm.getSLKONGZHI_PORT());
			jo.put("slControlSize", slcm.getSLKONGZHI_SIZE());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void updateSLControl(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLControlDao slcd =new SLControlDao();
		OperationLogInterface log = new OperationLogImplement();
		SLControlModel slcm = new SLControlModel();
		
		if(request.getParameter("slControlID") != null)
		{
			slcm.setSLKONGZHI_ID(Integer.parseInt(request.getParameter("slControlID")));
		}
		if(request.getParameter("slControlAreaID") != null)
		{
			slcm.setAREA_ID(Integer.parseInt(request.getParameter("slControlAreaID")));
		}
		if(request.getParameter("slControlGather") != null)
		{
			slcm.setSLGATHER_ID(Integer.parseInt(request.getParameter("slControlGather")));
		}
		if(request.getParameter("slControlNum") != null)
		{
			slcm.setSLKONGZHI_NUM(request.getParameter("slControlNum"));
		}
		if(request.getParameter("slControlName") != null)
		{
			slcm.setSLKONGZHI_NAME(request.getParameter("slControlName"));
		}
		if(request.getParameter("slControl485Address") != null)
		{
			slcm.setSLKONGZHI_485ADDRESS(request.getParameter("slControl485Address"));
		}
		if(request.getParameter("slControlPort") != null)
		{
			slcm.setSLKONGZHI_PORT(Integer.parseInt(request.getParameter("slControlPort")));
		}
		if(request.getParameter("slControlSize") != null)
		{
			slcm.setSLKONGZHI_SIZE(request.getParameter("slControlSize"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slcd.modify(slcm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "编辑路灯控制器信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "编辑路灯控制器信息","编辑失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLControlDao slcd =new SLControlDao();	
		OperationLogInterface log = new OperationLogImplement();
		int id = 0;
		if(request.getParameter("id") != null)
		{
			id = Integer.parseInt(request.getParameter("id"));
		}
		
		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slcd.delete(id))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "删除路灯控制器信息");
			}	
			else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "删除路灯控制器信息","删除失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
	}

	private void queryOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLControlDao slcd =new SLControlDao();		
		int id = 0;
		if(request.getParameter("id") != null)
		{
			id = Integer.parseInt(request.getParameter("id"));
		}
		
		SLControlModel slcm = slcd.queryByID(id);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("slControlAreaID", slcm.getAREA_ID());
		jo.put("slControlGather", slcm.getSLGATHER_ID());
		jo.put("slControlNum", slcm.getSLKONGZHI_NUM());
		jo.put("slControlName", slcm.getSLKONGZHI_NAME());
		jo.put("slControl485Address", slcm.getSLKONGZHI_485ADDRESS());
		jo.put("slControlPort", slcm.getSLKONGZHI_PORT());
		jo.put("slControlSize", slcm.getSLKONGZHI_SIZE());
		
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void addSLControl(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLControlDao slcd =new SLControlDao();	
		OperationLogInterface log = new OperationLogImplement();
		SLControlModel slcm = new SLControlModel();
		
		if(request.getParameter("slControlAreaID") != null)
		{
			slcm.setAREA_ID(Integer.parseInt(request.getParameter("slControlAreaID")));
		}
		if(request.getParameter("slControlGather") != null)
		{
			slcm.setSLGATHER_ID(Integer.parseInt(request.getParameter("slControlGather")));
		}
		if(request.getParameter("slControlNum") != null)
		{
			slcm.setSLKONGZHI_NUM(request.getParameter("slControlNum"));
		}
		if(request.getParameter("slControlName") != null)
		{
			slcm.setSLKONGZHI_NAME(request.getParameter("slControlName"));
		}
		if(request.getParameter("slControl485Address") != null)
		{
			slcm.setSLKONGZHI_485ADDRESS(request.getParameter("slControl485Address"));
		}
		if(request.getParameter("slControlPort") != null)
		{
			slcm.setSLKONGZHI_PORT(Integer.parseInt(request.getParameter("slControlPort")));
		}
		if(request.getParameter("slControlSize") != null)
		{
			slcm.setSLKONGZHI_SIZE(request.getParameter("slControlSize"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slcd.insert(slcm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "添加路灯控制器信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "添加路灯控制器信息","添加失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
		
	}
}
