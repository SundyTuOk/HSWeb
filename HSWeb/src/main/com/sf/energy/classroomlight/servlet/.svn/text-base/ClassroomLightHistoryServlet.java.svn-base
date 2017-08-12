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

import com.sf.energy.classroomlight.dao.LightRoomHistoryDao;
import com.sf.energy.classroomlight.model.LightRoomHistoryModel;

public class ClassroomLightHistoryServlet extends HttpServlet
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

		if ("getClassroomLightHistory".equals(method))
			getClassroomLightHistory(request, response);
		
		if ("deletCLHistory".equals(method))
			deletCLHistory(request, response);

	}

	private void deletCLHistory(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		LightRoomHistoryDao lrhd = new LightRoomHistoryDao();
		String arrayOfID = request.getParameter("LightRoomHistory_ID");

		String[] IDs = null;
		IDs = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < IDs.length; i++)
		{
			if(lrhd.delete(Integer.parseInt(IDs[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
	}

	private void getClassroomLightHistory(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		LightRoomHistoryDao lrhd = new LightRoomHistoryDao();
		LightRoomHistoryModel lrhm = new LightRoomHistoryModel();
		
		int thePageCount = Integer.parseInt(request.getParameter("thePageCount"));
		int thePageIndex = Integer.parseInt(request.getParameter("thePageIndex"));
		String theState = request.getParameter("theState");
		String beginDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int type = Integer.parseInt(request.getParameter("type"));
		int id = Integer.parseInt(request.getParameter("theID"));
		int floor=Integer.parseInt(request.getParameter("theFloor"));
		String selectInfo = "";
		String queryMsg = "";
		
		if(type==2)
			selectInfo += "Area|" + id;
		else if(type==3)
			selectInfo += "Arc|" + id;
		else if(type==4)
			selectInfo += "Floor|" + id + "|" + floor;
		else if(type==5)
			selectInfo += "Room|" + id;
		
		queryMsg =theState+"|"+beginDate+"|"+endDate;
		
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ArrayList<LightRoomHistoryModel> list = new ArrayList<LightRoomHistoryModel>();
		list = lrhd.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", lrhd.getTotalCount());
		json.add(jo);
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			lrhm=list.get(i);
			jo.put("LightRoomHistory_ID",lrhm.getLightRoomHistory_ID());
			jo.put("LightRoomName",lrhm.getLightRoomName());
			jo.put("LightRoomHistory_Time",lrhm.getLightRoomHistory_Time());
			jo.put("LightRoomHistory_State",lrhm.getLightRoomHistory_State());
			jo.put("Users_Name",lrhm.getUsers_Name());
			jo.put("LightRoomHistory_Style",lrhm.getLightRoomHistory_Style());
			jo.put("LightRoomHistory_Opr",lrhm.getLightRoomHistory_Opr());
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
