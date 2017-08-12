package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.sf.energy.manager.monitor.dao.UseingEnergyWarningDao;

public class UseingEnergyWarningServlet extends HttpServlet
{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

	    try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);
		
		if ("paginate".equals(method))
			paginate(req, resp);
		if ("checkInfo".equals(method))
			checkInfo(req, resp);
		
	}

	private void checkInfo(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, IOException
	{
		UseingEnergyWarningDao info=new UseingEnergyWarningDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("useingEnergyWarningPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("useingEnergyWarningPageIndex"));
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if(flag==3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));
		

		String style=req.getParameter("style").trim();
		
		String startTime=null;
		startTime=req.getParameter("startTime").trim();
		String endTime=null;
		endTime=req.getParameter("endTime").trim();
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
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");
//		SimpleDateFormat df=new SimpleDateFormat("mm/dd/yyyy");
//		SimpleDateFormat df1=new SimpleDateFormat("yyyy-mm-dd");
//		if(startTime!=null && !"".equals(startTime))
//		    startTime=df1.format(df.parse(startTime));
//		if(endTime!=null && !"".equals(endTime))
//			endTime=df1.format(df.parse(endTime));
//		//System.out.println("asdfasdfsafdsafsa:"+style+" "+startTime+" "+endTime);
		
		List<UseingEnergyWarningDao> list=info.checkByCondition(style,startTime,endTime,thePageCount,thePageIndex,flag,Id,floorID,tableName,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (UseingEnergyWarningDao n : list)
		{
			jo = new JSONObject();
			jo.put("AMBAOJING_ID", n.getAMBAOJING_ID());
			jo.put("AMMETER_NAME", n.getAMMETER_NAME());
			jo.put("AMBAOJING_STYLE", n.getAMBAOJING_STYLE());
			jo.put("AMBAOJING_TIME", n.getAMBAOJING_TIME());
			jo.put("AMBAOJING_REMARK", n.getAMBAOJING_REMARK());

			json.add(jo);
		}
		//System.out.println("参数："+flag+" "+Id+" "+floorID+" "+style+" "+startTime+" "+endTime+" ");
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void paginate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		UseingEnergyWarningDao info=new UseingEnergyWarningDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("useingEnergyWarningPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("useingEnergyWarningPageIndex"));
		
		int flag = Integer.parseInt(req.getParameter("flag"));
		int floorID = 0;
		if(flag==3)
		{
			floorID = Integer.parseInt(req.getParameter("FloorID"));
		}
		int Id = Integer.parseInt(req.getParameter("Id"));
		
		
		List<UseingEnergyWarningDao> list=info.paginate(thePageCount, thePageIndex,flag,Id,floorID);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (UseingEnergyWarningDao n : list)
		{
			jo = new JSONObject();
			jo.put("AMBAOJING_ID", n.getAMBAOJING_ID());
			jo.put("AMMETER_NAME", n.getAMMETER_NAME());
			jo.put("AMBAOJING_STYLE", n.getAMBAOJING_STYLE());
			jo.put("AMBAOJING_TIME", n.getAMBAOJING_TIME());
			jo.put("AMBAOJING_REMARK", n.getAMBAOJING_REMARK());

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
