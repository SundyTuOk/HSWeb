package com.sf.energy.classroomlight.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.classroomlight.dao.LightRoomDao;
import com.sf.energy.classroomlight.model.LightRoomModel;
import com.sf.energy.project.equipment.dao.LightDeviceCtlDao;
import com.sf.energy.project.equipment.model.LightDeviceCtlModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class LightRoomServlet extends HttpServlet
{
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		if("getAllLightRoom".equalsIgnoreCase(request.getParameter("method")))
		{
			getAllLightRoom(request,response);
		}
		
		if("getLightRoomByFloor".equalsIgnoreCase(request.getParameter("method")))
		{
			getLightRoomByFloor(request,response);
		}
		
		if("whenSelectedRoom".equalsIgnoreCase(request.getParameter("method")))
		{
			whenSelectedRoom(request,response);
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	
	/**
	 * 选择的是某间教室
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void whenSelectedRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		LightDeviceCtlModel model=new LightDeviceCtlModel();
		LightDeviceCtlDao dao=new LightDeviceCtlDao();
		int roomID=0;
		if(request.getParameter("roomID")!=null)
		{
			roomID=Integer.parseInt(request.getParameter("roomID"));
		}
		try
		{
			model=dao.queryRoomInfo(roomID);
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		
		Gson gson=new Gson();
		String result=gson.toJson(model);
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		////System.out.println("某间教室照明："+result);
		out.println(result);
		out.flush();
		out.close();
	}
	//用于构建上面的树的下拉选择教室
	public void getLightRoomByFloor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int floor=0;
		int archID=0;
		floor=Integer.parseInt(request.getParameter("floor"));
		archID=Integer.parseInt(request.getParameter("archID"));
		LightRoomDao lrd=new LightRoomDao();
		ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
		try
		{
			list=lrd.queryByFloorAndArch(floor, archID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String result=gson.toJson(list);
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		////System.out.println("所有教室照明："+result);
		out.println(result);
		out.flush();
		out.close();
	}
	
	//业务
	public void getAllLightRoom(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		LightRoomDao ld=new LightRoomDao();
		LightRoomModel lm=null;
		int schoolID=-1;
		int areaID=-1;
		int archID=-1;
		int floor=-1;
		int roomID=-1;
		
		Integer thePageCount = Integer.parseInt(request.getParameter("ClassroomLightCurrentPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("ClassroomLightCurrentPageIndex"));
		ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
		
		if(Integer.parseInt(request.getParameter("schoolID"))!=-1)
		{
			schoolID=Integer.parseInt(request.getParameter("schoolID"));
		}
		if(Integer.parseInt(request.getParameter("areaID"))!=-1)
		{
			areaID=Integer.parseInt(request.getParameter("areaID"));
		}
		if(Integer.parseInt(request.getParameter("archID"))!=-1)
		{
			archID=Integer.parseInt(request.getParameter("archID"));
		}
		
		if(Integer.parseInt(request.getParameter("floor"))!=-1)
		{
			floor=Integer.parseInt(request.getParameter("floor"));
		}
		if(Integer.parseInt(request.getParameter("roomID"))!=-1)
		{
			roomID=Integer.parseInt(request.getParameter("roomID"));
		}
		////System.out.println("选择的信息：areaID:"+areaID+" archID:"+archID+" floor:"+floor+" roomID:"+roomID);
		
		//如果是全校业务
		if(areaID!=-1 && archID!=-1 && floor!=-1 && roomID!=-1)
		{
			schoolID=0;//将school置为非-1的任何数，则代表是全校的业务
		}
		
		if(schoolID!=-1)//查整个学校
		{
			try
			{
				list=ld.queryAll(thePageCount,thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		else //其他情况，区域，建筑，楼层
		{
			try
			{
				//综合查询
				list=ld.searchAll(areaID,archID,floor,roomID,thePageCount,thePageIndex);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", ld.getTotal());
		json.add(jo);
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			lm=list.get(i);
			jo.put("lightID",lm.getClassroomLightID());
			jo.put("lightName",lm.getRoomName());
			jo.put("floor",lm.getFloor() );
			jo.put("archID", lm.getArchID());
			jo.put("archName", lm.getArchName());
			jo.put("areaID", lm.getAreaID());
			jo.put("areaName", lm.getAreaName());
			jo.put("lightState", lm.getLightState());
			jo.put("switchMode", lm.getSwitchMode());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		////System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		this.doGet(request, response);
	}

}
