package com.sf.energy.water.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;


public class WaterPartmentAnalysisByFenxiangServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String styleID = "";		//标记选择的性质类型
		
		styleID = request.getParameter("styleID");
		
		try {
			if(styleID.equals("0"))
			{
				PartmentAnalysisByFenxiangImpl(request,response);
			}
			else 
			{
				//System.out.println("子项");
				PartmentAnalysisByFenxiangImpl_ZX(request,response);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

	private void PartmentAnalysisByFenxiangImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		int ParID = -1; 		
		int year = 2014;
		int startmonth = 2;
		int endmonth = 8;
		
		if(request.getParameter("pid") !=null ){
			ParID=Integer.parseInt(request.getParameter("pid"));
		}
		if(request.getParameter("year") !=null ){
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("startmonth") !=null ){
			startmonth=Integer.parseInt(request.getParameter("startmonth"));
		}
		if(request.getParameter("endmonth") !=null ){
			endmonth=Integer.parseInt(request.getParameter("endmonth"));
		}
		
		
		
		Map<String,WaterReportModel>	map = null;
		
		WaterReportHelperImpl	ehi = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		if( ParID == 0) //选了所有部门的情形
		{
			//System.out.println("全校");
			map = ehi.getAllPartmentAmFenxiangByAll(year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		else 	//选了某个部门情形
		{
			//System.out.println("部门");
			map = ehi.getPartmentAmFenxiangByAll(ParID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		
		
		out.flush();
		out.close();
	}
	
	
	private  void  PartmentAnalysisByFenxiangImpl_ZX(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		int ParID = -1; 		
		int year = 2014;
		int startmonth = 2;
		int endmonth = 8;
		String styleID = "";		//标记选择的性质类型
		
		styleID = request.getParameter("styleID");
		
		if(request.getParameter("pid") !=null ){
			ParID=Integer.parseInt(request.getParameter("pid"));
		}
		if(request.getParameter("year") !=null ){
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("startmonth") !=null ){
			startmonth=Integer.parseInt(request.getParameter("startmonth"));
		}
		if(request.getParameter("endmonth") !=null ){
			endmonth=Integer.parseInt(request.getParameter("endmonth"));
		}
		
		
		
		Map<String,WaterReportModel>	map = null;
		
		WaterReportHelperImpl	ehi = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		if( ParID == 0) //选了所有部门的情形
		{
			//System.out.println("全校");
			map = ehi.getAllPartmentAmFenxiangByZX(Integer.parseInt(styleID), year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		else	//选了某个部门情形
		{
			//System.out.println("部门");
			map = ehi.getPartmentAmFenxiangByZX(Integer.parseInt(styleID), ParID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		
		
		out.flush();
		out.close();
	}



}
