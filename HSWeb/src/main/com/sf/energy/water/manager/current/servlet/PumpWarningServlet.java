package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sf.energy.water.manager.current.dao.PumpWarningDao;
import com.sf.energy.water.manager.current.dao.UseingWaterWarningDao;
import com.sf.energy.water.manager.current.model.PumpWarningModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class PumpWarningServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

	    try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		String method = req.getParameter("method");
		
		if ("paginate".equals(method))
			paginate(req, resp);
		if ("checkInfo".equals(method))
			checkInfo(req, resp);
		
	}

	private void checkInfo(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, IOException
	{
//		UseingWaterWarningDao info=new UseingWaterWarningDao();
//		
//		Integer thePageCount = Integer.parseInt(req
//				.getParameter("useingWaterWarningPageCount"));
//		Integer thePageIndex = Integer.parseInt(req
//				.getParameter("useingWaterWarningPageIndex"));
//		
//		int flag = Integer.parseInt(req.getParameter("flag"));
//		int floorID = 0;
//		if(flag==3)
//		{
//			floorID = Integer.parseInt(req.getParameter("FloorID"));
//		}
//		int Id = Integer.parseInt(req.getParameter("Id"));
//
//		String style=req.getParameter("style").trim();
//		
//		String startTime=null;
//		startTime=req.getParameter("startTime").trim();
//		String endTime=null;
//		endTime=req.getParameter("endTime").trim();
//		
//		String tableName=null;
//		tableName=req.getParameter("tableName").trim();
//		String order=null;
//		order=req.getParameter("order").trim();
//		
//		SimpleDateFormat df1=new SimpleDateFormat("yyyy-mm-dd");
//		if(startTime!=null && !"".equals(startTime)&&endTime!=null && !"".equals(endTime))
//		{
//			Date temp=null;
//			Date startTimed=df1.parse(startTime);
//			Date endTimed=df1.parse(endTime);
//			if(startTimed.after(endTimed))
//			{
//				temp=startTimed;
//				startTimed=endTimed;
//				endTimed=temp;
//			}
//			startTime=df1.format(startTimed);
//			endTime=df1.format(endTimed);
//		}
//		
//		List<UseingWaterWarningDao> list=info.checkByCondition(style,startTime,endTime,thePageCount,thePageIndex,flag,Id,floorID,tableName,order);
//		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", 1);
		json.add(jo);
		jo.put("PumpWarning_ID", 1);
		jo.put("PumpWarning_NAME", "1#管网");
		jo.put("PumpWarning_STYLE", "过压报警");
		jo.put("PumpWarning_TIME", "2015-04-10 10:20:20");
		jo.put("PumpWarning_REMARK","1#管网水压过高，测量压力值为：1.2mPa，限值为：0.9mPa");
		json.add(jo);
//		jo.put("totalCount", info.getTotal());
//		json.add(jo);
//
//		for (UseingWaterWarningDao n : list)
//		{
//			jo = new JSONObject();
//			jo.put("WATERBAOJING_ID", n.getWATERBAOJING_ID());
//			jo.put("WATerMETER_NAME", n.getWATerMETER_NAME());
//			jo.put("WATERBAOJING_STYLE", n.getWATERBAOJING_STYLE());
//			jo.put("WATERBAOJING_TIME", n.getWATERBAOJING_TIME());
//			jo.put("WATERBAOJING_REMARK", n.getWATERBAOJING_REMARK());
//
//			json.add(jo);
//		}
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void paginate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
//		UseingWaterWarningDao info=new UseingWaterWarningDao();
		PumpWarningDao pw=new PumpWarningDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("PumpWarningPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("PumpWarningPageIndex"));
		
		int flag = Integer.parseInt(req.getParameter("flag"));
		
		int Id = Integer.parseInt(req.getParameter("Id")); 
		String style=req.getParameter("style").trim();
		
		String startTime=null;
		startTime=req.getParameter("startTime").trim();
		String endTime=null;
		endTime=req.getParameter("endTime").trim();
		
		String tableName=null;
		tableName=req.getParameter("tableName").trim();
		String order=null;
		order=req.getParameter("order").trim();
		
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-mm-dd");
		if(startTime!=null && !"".equals(startTime)&&endTime!=null && !"".equals(endTime))
		{
			Date temp=null;
			Date startTimed=df1.parse(startTime);
			Date endTimed=df1.parse(endTime);
			if(startTimed.after(endTimed))
			{
				temp=startTimed;
				startTimed=endTimed;
				endTimed=temp;
			}
			startTime=df1.format(startTimed);
			endTime=df1.format(endTimed);
		}
		List<PumpWarningModel> list=pw.checkByCondition(style, startTime, endTime, thePageCount, thePageIndex, flag, Id, tableName, order);
//		List<UseingWaterWarningDao> list=info.paginate(thePageCount, thePageIndex,flag,Id,floorID,tableName,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		/*jo.put("totalCount", 1);
		json.add(jo);
		jo.put("PumpWarning_ID", 1);
		jo.put("PumpWarning_NAME", "1#管网");
		jo.put("PumpWarning_STYLE", "过压报警");
		jo.put("PumpWarning_TIME", "2015-04-10 10:20:20");
		jo.put("PumpWarning_REMARK","1#管网水压过高，测量压力值为：1.2mPa，限值为：0.9mPa");
		json.add(jo);*/
		jo.put("totalCount", pw.getTotal());
		json.add(jo);

		for (PumpWarningModel n : list)
		{
			jo = new JSONObject();
			jo.put("PumpWarning_ID", n.getPumpWarning_ID());
			jo.put("PumpWarning_NAME", n.getPumpWarning_NAME());
			jo.put("PumpWarning_STYLE", n.getPumpWarning_STYLE());
			jo.put("PumpWarning_TIME", n.getPumpWarning_TIME());
			jo.put("PumpWarning_REMARK", n.getPumpWarning_REMARK());

			json.add(jo);
		}
		
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
}
