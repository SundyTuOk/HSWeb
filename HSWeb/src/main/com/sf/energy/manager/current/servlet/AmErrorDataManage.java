package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.sf.energy.manager.current.dao.AmmeterErrorDataDao;
import com.sf.energy.manager.current.model.ErrorDataModel;

public class AmErrorDataManage extends HttpServlet
{

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		String method = request.getParameter("method");

		if ("getAmErrorDatas".equals(method))
			getAmErrorDatas(request, response);
		if ("deleteOneErrorData".equals(method))
			deleteOneErrorData(request, response);
		if ("deleteErrorDatas".equals(method))
			deleteErrorDatas(request, response);
	}
	private void deleteErrorDatas(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException
	{
		AmmeterErrorDataDao admd = new AmmeterErrorDataDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}		
		String type="";
		if (request.getParameter("type") != null
				&& request.getParameter("type") != "")
			type=request.getParameter("type");
		
		Date start = new Date();
		if (request.getParameter("Start_Time") != null&&request.getParameter("Start_Time") != "")
			start = df.parse(request.getParameter("Start_Time"));
		Date end = new Date();
		if (request.getParameter("End_Time") != null&&request.getParameter("End_Time") != "")
			end = df.parse(request.getParameter("End_Time"));
		String info = "fail";
		if(type.equals("ammeter")){
			int ammeterID = 0;
			if (request.getParameter("Ammeter_ID") != null
					&& request.getParameter("Ammeter_ID") != "")
				ammeterID = Integer.parseInt(request.getParameter("Ammeter_ID"));
			else
			{
				return;
			}
			if(admd.deleteErrorDatasByAm(ammeterID, start, end)){
				info="success";
			}
			        
		}else if(type.equals("floor")){
			int floorID = 0;
			if (request.getParameter("floorID") != null
					&& request.getParameter("floorID") != "")
				floorID = Integer.parseInt(request.getParameter("floorID"));
			int archID = 0;
			if (request.getParameter("archID") != null
					&& request.getParameter("archID") != "")
				archID = Integer.parseInt(request.getParameter("archID"));
			if(admd.deleteErrorDatasByFloor(archID, floorID, start, end)){
				info="success";
			}
		}else if(type.equals("arch")){
			int archID = 0;
			if (request.getParameter("archID") != null
					&& request.getParameter("archID") != "")
				archID = Integer.parseInt(request.getParameter("archID"));
			else{
				return;
			}
			 if(admd.deleteErrorDatasByArch(archID, start, end)){
				 info="success";
			 }
		}else if(type.equals("area")){
			int areaID = 0;
			if (request.getParameter("areaID") != null
					&& request.getParameter("areaID") != "")
				areaID = Integer.parseInt(request.getParameter("areaID"));
			else{
				return;
			}
			if(admd.deleteErrorDatasByArea(areaID, userID, start, end)){
				info="success";
			}
		}
		PrintWriter out = response.getWriter();
		String data = new Gson().toJson(info);
		out.println(data);
		out.flush();
		out.close();
		
	}

	private void deleteOneErrorData(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		AmmeterErrorDataDao admd = new AmmeterErrorDataDao();
		int Ammeter_ID=0;
		if (request.getParameter("Ammeter_ID") != null
				&& request.getParameter("Ammeter_ID") != "")
			Ammeter_ID = Integer.parseInt(request.getParameter("Ammeter_ID"));
		String ValueTime="";
		if (request.getParameter("ValueTime") != null
				&& request.getParameter("ValueTime") != "")
			ValueTime = request.getParameter("ValueTime");
		float ZvalueZY=0;
		if (request.getParameter("ZvalueZY") != null
				&& request.getParameter("ZvalueZY") != "")
			ZvalueZY = Float.parseFloat(request.getParameter("ZvalueZY"));
		String resultInfo = null;

		if (admd.deleteOneErrorData(Ammeter_ID, ValueTime, ZvalueZY))
			resultInfo = "success";
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAmErrorDatas(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException,
			IOException
	{
//		Enumeration<String> enumeration = request.getParameterNames();
//		while(enumeration.hasMoreElements()){
//			String name= enumeration.nextElement();
//			System.out.println(name+" :"+request.getParameter(name));
//		}
		AmmeterErrorDataDao admd = new AmmeterErrorDataDao();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}		
		String type="";
		if (request.getParameter("type") != null
				&& request.getParameter("type") != "")
			type=request.getParameter("type");
		
		Date start = new Date();
		if (request.getParameter("Start_Time") != null&&request.getParameter("Start_Time") != "")
			start = df.parse(request.getParameter("Start_Time"));
		Date end = new Date();
		if (request.getParameter("End_Time") != null&&request.getParameter("End_Time") != "")
			end = df.parse(request.getParameter("End_Time"));
		int PageCount=0;
		if (request.getParameter("errorDataPageCount") != null
				&& request.getParameter("errorDataPageCount") != "")
			PageCount = Integer.parseInt(request.getParameter("errorDataPageCount"));

		int PageIndex=0;
		if (request.getParameter("errorDataPageIndex") != null
				&& request.getParameter("errorDataPageIndex") != "")
			PageIndex = Integer.parseInt(request.getParameter("errorDataPageIndex"));
		int count =0;
		List<ErrorDataModel> list=null;
		if(type.equals("ammeter")){
			int ammeterID = 0;
			if (request.getParameter("Ammeter_ID") != null
					&& request.getParameter("Ammeter_ID") != "")
				ammeterID = Integer.parseInt(request.getParameter("Ammeter_ID"));
			else
			{
				return;
			}

			count = admd.getAmErrorDataCountByAm(ammeterID, start, end);
			list = admd.getAmErrorDataByAm(ammeterID, start,end,PageCount,PageIndex);	        
		}else if(type.equals("floor")){
			int floorID = 0;
			if (request.getParameter("floorID") != null
					&& request.getParameter("floorID") != "")
				floorID = Integer.parseInt(request.getParameter("floorID"));
			else
			{
				return;
			}
			int archID = 0;
			if (request.getParameter("archID") != null
					&& request.getParameter("archID") != "")
				archID = Integer.parseInt(request.getParameter("archID"));
			count = admd.getAmErrorDataCountByFloor(archID,floorID,start, end);
			list = admd.getAmErrorDataByFloor(archID,floorID,start,end,PageCount,PageIndex);	        
		}else if(type.equals("arch")){
			int archID = 0;
			if (request.getParameter("archID") != null
					&& request.getParameter("archID") != "")
				archID = Integer.parseInt(request.getParameter("archID"));
			else{
				return;
			}
			count = admd.getAmErrorDataCountByArch(archID,start, end);
			list = admd.getAmErrorDataByArch(archID,start,end,PageCount,PageIndex);	        
		}else if(type.equals("area")){
			int areaID = 0;
			if (request.getParameter("areaID") != null
					&& request.getParameter("areaID") != "")
				areaID = Integer.parseInt(request.getParameter("areaID"));
			else{
				return;
			}
			count = admd.getAmErrorDataCountByArea(areaID,userID,start,end);
			list = admd.getAmErrorDataByArea(areaID,userID,start,end,PageCount,PageIndex);	        
		}
		PrintWriter out = response.getWriter();
		String data = new Gson().toJson(new Object[]{count,list});
		//System.out.println("电:"+type+":"+data);
		out.println(data);
		out.flush();
		out.close();
	}
}
