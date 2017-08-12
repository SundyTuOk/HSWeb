package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class SLLineServlet extends HttpServlet
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
		}
		

	}

	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		doGet(request, response);;
	}
	
	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);
		
		if ("getLineByAreaID".equals(method))
			getLineByAreaID(request, response);
		if ("addSLLineInfo".equals(method))
			addSLLineInfo(request, response);
		if ("deleteSLLineInfo".equals(method))
			deleteSLLineInfo(request, response);
		if ("updataSLLineInfo".equals(method))
			updataSLLineInfo(request, response);
		if ("querySLLineInfo".equals(method))
			querySLLineInfo(request, response);
		if ("queryAllSLLineInfo".equals(method))
			queryAllSLLineInfo(request, response);
		if ("queryByControllerID".equals(method))
			queryByControllerID(request, response);
		
	}



	private void queryByControllerID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLLineDao slld = new SLLineDao();
		OperationLogInterface log = new OperationLogImplement();
		int controllerID = 0;
		if(request.getParameter("controllerID") != null)
		{
			controllerID = Integer.parseInt(request.getParameter("controllerID"));
		}
		ArrayList<SLLineModel> list = slld.queryByControllerID(controllerID);
		
		JSONArray json = new JSONArray();
		JSONObject jo;
		for(SLLineModel sllm : list)
		{
			jo = new JSONObject();
			jo.put("slLineID", sllm.getSLLINE_ID());
			jo.put("slLineAreaID", sllm.getAREA_ID());
			jo.put("slLineGatherID", sllm.getGATHER_ID());
			jo.put("slLineController", sllm.getSLKONGZHI_ID());
			jo.put("slLineControllerNum", sllm.getSLKONGZHI_INDEX());
			jo.put("slLineMeterID", sllm.getAMMETER_ID());
			jo.put("slLineNum", sllm.getSLLINE_NUM());
			jo.put("slLineName", sllm.getSLLINE_NAME());
			jo.put("slLineStartPlace", sllm.getSLLINE_STAR());
			jo.put("slLineEndPlace", sllm.getSLLINE_END());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}



	private void queryAllSLLineInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLLineDao slld = new SLLineDao();
		ArrayList<SLLineModel> list = slld.queryAll();
		
		JSONArray json = new JSONArray();
		JSONObject jo;
		for(SLLineModel sllm : list)
		{
			jo = new JSONObject();
			jo.put("slLineID", sllm.getSLLINE_ID());
			jo.put("slLineAreaID", sllm.getAREA_ID());
			jo.put("slLineGatherID", sllm.getGATHER_ID());
			jo.put("slLineController", sllm.getSLKONGZHI_ID());
			jo.put("slLineControllerNum", sllm.getSLKONGZHI_INDEX());
			jo.put("slLineMeterID", sllm.getAMMETER_ID());
			jo.put("slLineNum", sllm.getSLLINE_NUM());
			jo.put("slLineName", sllm.getSLLINE_NAME());
			jo.put("slLineStartPlace", sllm.getSLLINE_STAR());
			jo.put("slLineEndPlace", sllm.getSLLINE_END());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}



	private void querySLLineInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		SLLineDao slld = new SLLineDao();
		int id = 0;
		if(request.getParameter("id") != null)
		{
			id = Integer.parseInt(request.getParameter("id"));
		}
		
		SLLineModel sllm = slld.queryByID(id);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("slLineAreaID", sllm.getAREA_ID());
		jo.put("slLineGatherID", sllm.getGATHER_ID());
		jo.put("slLineController", sllm.getSLKONGZHI_ID());
		jo.put("slLineControllerNum", sllm.getSLKONGZHI_INDEX());
		jo.put("slLineMeterID", sllm.getAMMETER_ID());
		jo.put("slLineNum", sllm.getSLLINE_NUM());
		jo.put("slLineName", sllm.getSLLINE_NAME());
		jo.put("slLineStartPlace", sllm.getSLLINE_STAR());
		jo.put("slLineEndPlace", sllm.getSLLINE_END());
		
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}



	private void updataSLLineInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLLineDao slld = new SLLineDao();
		OperationLogInterface log = new OperationLogImplement();
		SLLineModel sllm = new SLLineModel();
		
		if(request.getParameter("slLineID") != null)
		{
			sllm.setSLLINE_ID(Integer.parseInt(request.getParameter("slLineID")));
		}
		if(request.getParameter("slLineAreaID") != null)
		{
			sllm.setAREA_ID(Integer.parseInt(request.getParameter("slLineAreaID")));
		}
		if(request.getParameter("slLineGatherID") != null)
		{
			sllm.setGATHER_ID(Integer.parseInt(request.getParameter("slLineGatherID")));
		}
		if(request.getParameter("slLineController") != null)
		{
			sllm.setSLKONGZHI_ID(Integer.parseInt(request.getParameter("slLineController")));
		}
		if(request.getParameter("slLineControllerNum") != null)
		{
			sllm.setSLKONGZHI_INDEX(Integer.parseInt(request.getParameter("slLineControllerNum")));
		}
		if(request.getParameter("slLineMeterID") != null)
		{
			sllm.setAMMETER_ID(Integer.parseInt(request.getParameter("slLineMeterID")));
		}
		if(request.getParameter("slLineNum") != null)
		{
			sllm.setSLLINE_NUM(request.getParameter("slLineNum"));
		}
		if(request.getParameter("slLineName") != null)
		{
			sllm.setSLLINE_NAME(request.getParameter("slLineName"));
		}
		if(request.getParameter("slLineStartPlace") != null)
		{
			sllm.setSLLINE_STAR(request.getParameter("slLineStartPlace"));
		}
		if(request.getParameter("slLineEndPlace") != null)
		{
			sllm.setSLLINE_END(request.getParameter("slLineEndPlace"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slld.modify(sllm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "编辑路灯回路信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "编辑路灯回路信息","编辑失败！！！");
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



	private void deleteSLLineInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLLineDao slld = new SLLineDao();
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
			if (slld.delete(id))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "删除路灯回路信息");
			}	
			else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "删除路灯回路信息","删除失败！！！");
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



	private void addSLLineInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SLLineDao slld = new SLLineDao();
		OperationLogInterface log = new OperationLogImplement();
		SLLineModel sllm = new SLLineModel();
		
		if(request.getParameter("slLineAreaID") != null)
		{
			sllm.setAREA_ID(Integer.parseInt(request.getParameter("slLineAreaID")));
		}
		if(request.getParameter("slLineGatherID") != null)
		{
			sllm.setGATHER_ID(Integer.parseInt(request.getParameter("slLineGatherID")));
		}
		if(request.getParameter("slLineController") != null)
		{
			sllm.setSLKONGZHI_ID(Integer.parseInt(request.getParameter("slLineController")));
		}
		if(request.getParameter("slLineControllerNum") != null)
		{
			sllm.setSLKONGZHI_INDEX(Integer.parseInt(request.getParameter("slLineControllerNum")));
		}
		if(request.getParameter("slLineMeterID") != null)
		{
			sllm.setAMMETER_ID(Integer.parseInt(request.getParameter("slLineMeterID")));
		}
		if(request.getParameter("slLineNum") != null)
		{
			sllm.setSLLINE_NUM(request.getParameter("slLineNum"));
		}
		if(request.getParameter("slLineName") != null)
		{
			sllm.setSLLINE_NAME(request.getParameter("slLineName"));
		}
		if(request.getParameter("slLineStartPlace") != null)
		{
			sllm.setSLLINE_STAR(request.getParameter("slLineStartPlace"));
		}
		if(request.getParameter("slLineEndPlace") != null)
		{
			sllm.setSLLINE_END(request.getParameter("slLineEndPlace"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slld.insert(sllm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "添加路灯回路信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "添加路灯回路信息","添加失败！！！");
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



	private void getLineByAreaID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SLLineDao info=new SLLineDao();
		Integer area_ID = Integer.parseInt(request
				.getParameter("area_ID"));
		
		List<SLLineModel> list=info.queryByAreaID(area_ID);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		for (SLLineModel n : list)
		{
			jo = new JSONObject();
			jo.put("SLLine_ID", n.getSLLINE_ID());
			jo.put("SLLine_Name", n.getSLLINE_NAME());
			json.add(jo);
		}
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

}
