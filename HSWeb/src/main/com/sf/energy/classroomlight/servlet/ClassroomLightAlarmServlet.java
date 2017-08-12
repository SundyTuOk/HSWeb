package com.sf.energy.classroomlight.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.classroomlight.dao.LightRoomAlarmDao;
import com.sf.energy.classroomlight.model.LightRoomAlarmModel;

public class ClassroomLightAlarmServlet extends HttpServlet
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

		if ("getClassroomLightAlarm".equals(method))
			getClassroomLightAlarm(request, response);
		
		if ("deletCLAlarm".equals(method))
			deletCLAlarm(request, response);

	}

	private void deletCLAlarm(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		LightRoomAlarmDao lrad = new LightRoomAlarmDao();
		String arrayOfID = request.getParameter("LightRoomAlarm_ID");

		String[] IDs = null;
		IDs = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < IDs.length; i++)
		{
			if(lrad.delete(Integer.parseInt(IDs[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		
	}

	private void getClassroomLightAlarm(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		LightRoomAlarmDao lrad = new LightRoomAlarmDao();
		LightRoomAlarmModel lram = new LightRoomAlarmModel();
		
		int thePageCount = Integer.parseInt(request.getParameter("thePageCount"));
		int thePageIndex = Integer.parseInt(request.getParameter("thePageIndex"));
		String beginDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int type = Integer.parseInt(request.getParameter("type"));
		int id = Integer.parseInt(request.getParameter("theID"));
		int floor=Integer.parseInt(request.getParameter("theFloor"));
		String selectInfo = "";
		String queryMsg = "";
		
		if(type==1)
			selectInfo += "Area|" + id;
		else if(type==2)
			selectInfo += "Arc|" + id;
		else if(type==3)
			selectInfo += "Floor|" + id + "|" + floor;
		else if(type==4)
			selectInfo += "Room|" + id;
		
		queryMsg =beginDate+"|"+endDate;
		
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ArrayList<LightRoomAlarmModel> list = new ArrayList<LightRoomAlarmModel>();
		list = lrad.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", lrad.getTotalCount());
		json.add(jo);
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			lram=list.get(i);
			jo.put("BaojingInfo_id",lram.getBaojingInfo_id());
			jo.put("LightRoomName",lram.getLightRoomName());
			jo.put("BaojingInfo_Time",lram.getBaojingInfo_Time());
			jo.put("BaojingInfo_Style",lram.getBaojingInfo_Style());
			jo.put("BaojingInfo_Title",lram.getBaojingInfo_Title());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.size());
		out.println(json.toString());
		out.flush();
		out.close();
	}
}
