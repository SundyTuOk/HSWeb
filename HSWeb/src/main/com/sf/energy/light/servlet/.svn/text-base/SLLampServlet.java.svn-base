package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.dao.SlLampDao;
import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.light.model.SlLampModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class SLLampServlet extends HttpServlet 
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
		
		if ("getLampByLineID".equals(method))
			getLampByLineID(request, response);
		if ("addSLLampInfo".equals(method))
			addSLLampInfo(request, response);
		if ("deleteSLLampInfo".equals(method))
			deleteSLLampInfo(request, response);
		if ("updateSLLampInfo".equals(method))
			updateSLLampInfo(request, response);
		if ("querySLLampOldInfo".equals(method))
			querySLLampOldInfo(request, response);
	}



	private void querySLLampOldInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SlLampDao slmd = new SlLampDao();

		int id = 0;
		if(request.getParameter("id") != null)
		{
			id = Integer.parseInt(request.getParameter("id"));
		}
		
		SlLampModel slmm = slmd.queryByID(id);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("slManagerAreaID", slmm.getArea_Id());
		jo.put("slManagerGatherID", slmm.getGather_Id());
		jo.put("slManagerControllerID", slmm.getSlKongzhi_Id());
		jo.put("slManagerLineID", slmm.getSlLine_Id());
		jo.put("slManagerLampNum", slmm.getSlLamp_Num());
		jo.put("slManagerLampSize", slmm.getSlLamp_Size());
		
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}



	private void updateSLLampInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SlLampDao slmd = new SlLampDao();	
		OperationLogInterface log = new OperationLogImplement();
		SlLampModel slmm = new SlLampModel();
		
		if(request.getParameter("slLampID") != null)
		{
			slmm.setSlLamp_Id(Integer.parseInt(request.getParameter("slLampID")));
		}
		if(request.getParameter("slManagerAreaID") != null)
		{
			slmm.setArea_Id(Integer.parseInt(request.getParameter("slManagerAreaID")));
		}
		if(request.getParameter("slManagerGatherID") != null)
		{
			slmm.setGather_Id(Integer.parseInt(request.getParameter("slManagerGatherID")));
		}
		if(request.getParameter("slManagerControllerID") != null)
		{
			slmm.setSlKongzhi_Id(Integer.parseInt(request.getParameter("slManagerControllerID")));
		}
		if(request.getParameter("slManagerLineID") != null)
		{
			slmm.setSlLine_Id(Integer.parseInt(request.getParameter("slManagerLineID")));
		}
		if(request.getParameter("slManagerLampNum") != null)
		{
			slmm.setSlLamp_Num(request.getParameter("slManagerLampNum"));
		}
		if(request.getParameter("slManagerLampSize") != null)
		{
			slmm.setSlLamp_Size(request.getParameter("slManagerLampSize"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slmd.modify(slmm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "编辑路灯信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "编辑路灯信息","编辑失败！！！");
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



	private void deleteSLLampInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SlLampDao slmd = new SlLampDao();
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
			if (slmd.delete(id))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "删除路灯信息");
			}	
			else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "删除路灯信息","删除失败！！！");
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



	private void addSLLampInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		SlLampDao slmd = new SlLampDao();	
		OperationLogInterface log = new OperationLogImplement();
		SlLampModel slmm = new SlLampModel();
		
		if(request.getParameter("slManagerAreaID") != null)
		{
			slmm.setArea_Id(Integer.parseInt(request.getParameter("slManagerAreaID")));
		}
		if(request.getParameter("slManagerGatherID") != null)
		{
			slmm.setGather_Id(Integer.parseInt(request.getParameter("slManagerGatherID")));
		}
		if(request.getParameter("slManagerControllerID") != null)
		{
			slmm.setSlKongzhi_Id(Integer.parseInt(request.getParameter("slManagerControllerID")));
		}
		if(request.getParameter("slManagerLineID") != null)
		{
			slmm.setSlLine_Id(Integer.parseInt(request.getParameter("slManagerLineID")));
		}
		if(request.getParameter("slManagerLampNum") != null)
		{
			slmm.setSlLamp_Num(request.getParameter("slManagerLampNum"));
		}
		if(request.getParameter("slManagerLampSize") != null)
		{
			slmm.setSlLamp_Size(request.getParameter("slManagerLampSize"));
		}
		
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if (slmd.insert(slmm))
			{
				resultInfo = "success";
				// 写入日志
				log.writeLog(userLoginName, "路灯设备管理", "添加路灯信息");
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "路灯设备管理", "添加路灯信息","添加失败！！！");
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



	private void getLampByLineID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		SlLampDao info=new SlLampDao();
		Integer area_ID = Integer.parseInt(request
				.getParameter("area_ID"));
		Integer SLLine_ID = Integer.parseInt(request
				.getParameter("SLLine_ID"));
		
		List<SlLampModel> list=info.queryByAreaID(area_ID,SLLine_ID);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		for (SlLampModel n : list)
		{
			jo = new JSONObject();
			jo.put("SLLamp_ID", n.getSlLamp_Id());
			jo.put("SLLamp_Num", n.getSlLamp_Num());
			json.add(jo);
		}
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}


}
