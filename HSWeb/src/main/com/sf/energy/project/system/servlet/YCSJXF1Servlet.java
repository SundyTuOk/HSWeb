package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.YCSJXF1Dao;
import com.sf.energy.project.system.maintenance.OnlineMeter;
import com.sf.energy.project.system.model.OnlineMeterModel;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class YCSJXF1Servlet extends HttpServlet
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("SerGetInfoZ5052".equals(method))
			SerGetInfoZ5052(request, response);
		if ("SerXZData".equals(method))
			SerXZData(request, response);
//		if ("getOnlineMeter".equals(method))
//			getOnlineMeter(request, response);
		
	}
	private void SerXZData(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{//, , , , , 
		String Meter_ID = "";
		if(request.getParameter("Meter_ID") != null)
		{
			Meter_ID = request.getParameter("Meter_ID");
		}
		String MeterStyle = "";
		if(request.getParameter("MeterStyle") != null)
		{
			MeterStyle = request.getParameter("MeterStyle");
		}
		String txtStartXZ = "";
		if(request.getParameter("txtStartXZ") != null)
		{
			txtStartXZ = request.getParameter("txtStartXZ");
		}
		String txtEndXZ = "";
		if(request.getParameter("txtEndXZ") != null)
		{
			txtEndXZ = request.getParameter("txtEndXZ");
		}
		String txtStarValue = "";
		if(request.getParameter("txtStarValue") != null)
		{
			txtStarValue = request.getParameter("txtStarValue");
		}
		String txtEndValue = "";
		if(request.getParameter("txtEndValue") != null)
		{
			txtEndValue = request.getParameter("txtEndValue");
		}
		//System.out.println("offset:"+offset+" limit:"+limit);
		JSONObject json = new JSONObject();
		YCSJXF1Dao ds=new YCSJXF1Dao();
		try
		{
			
			String result=ds.SerXZData(Meter_ID, MeterStyle, txtStartXZ, txtEndXZ, txtStarValue, txtEndValue);
			json.put("result", result);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void SerGetInfoZ5052(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		String areaID = "-1";
		if(request.getParameter("areaID") != null)
		{
			areaID = request.getParameter("areaID");
		}
		String archID = "-1";
		if(request.getParameter("archID") != null)
		{
			archID = request.getParameter("archID");
		}
		String startTime = "";
		if(request.getParameter("startTime") != null)
		{
			startTime = request.getParameter("startTime");
		}
		String endTime = "";
		if(request.getParameter("endTime") != null)
		{
			endTime = request.getParameter("endTime");
		}
		
		String days = "2";
		if(request.getParameter("days") != null)
		{
			days = request.getParameter("days");
		}
		String Gross= "0";
		if(request.getParameter("Gross") != null)
		{
			Gross = request.getParameter("Gross");
		}
		String meterStyle = "Am";
		if(request.getParameter("meterStyle") != null)
		{
			meterStyle = request.getParameter("meterStyle");
		}

		int offset = 0;
		if(request.getParameter("offset") != null)
		{
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		
		int limit = 0;
		if(request.getParameter("limit") != null)
		{
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		//System.out.println("offset:"+offset+" limit:"+limit);
		JSONObject json = new JSONObject();
		YCSJXF1Dao ds=new YCSJXF1Dao();
		json=ds.SerGetInfoZ5052(areaID, archID, meterStyle, startTime, endTime, days, Gross,offset,limit);

		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
}
