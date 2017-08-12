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

public class WaterArchAnalysisByFenxiangServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			
			String styleID = "";		//标记选择的性质类型
			
			styleID = request.getParameter("styleID");
			
			try {
				if(styleID.equals("0"))
				{
					ArchAnalysisByFenxiangImpl(request,response);
				}
				else 
				{
					////System.out.println("子项");
					ArchAnalysisByFenxiangImpl_ZX(request,response);
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

	private void ArchAnalysisByFenxiangImpl(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		int ArcID = -1; 		//表示选择选择所有建筑
		int AreaID = -1;		//表示选择所有区域
		int year = 2014;
		int startmonth = 2;
		int endmonth = 8;
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid") !=null ){
			AreaID=Integer.parseInt(request.getParameter("areaid"));
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
		
		if( AreaID == -1) //选了所有区域的情形
		{
			//System.out.println("全校");
			map = ehi.getSchoolAmFenxiangByAll(year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		else if(AreaID != -1 && ArcID==-1)	//选了某个区域情形
		{
			////System.out.println("区域");
			map = ehi.getAreaAmFenxiangByAll(AreaID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			////System.out.println(data);
			out.println(data);
		}
		else if(AreaID != -1 && ArcID!=-1)		//选了某个建筑
		{
			////System.out.println("建筑");
			map = ehi.getArcAmFenxiangByAll(ArcID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			////System.out.println(data);
			out.println(data);
		}
		
		
		out.flush();
		out.close();
	}
	
	
	private  void  ArchAnalysisByFenxiangImpl_ZX(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		int ArcID = -1; 		//表示选择选择所有建筑
		int AreaID = -1;		//表示选择所有区域
		int year = 2014;
		int startmonth = 2;
		int endmonth = 8;
		String styleID = "";		//标记选择的性质类型
		
		styleID = request.getParameter("styleID");
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid") !=null ){
			AreaID=Integer.parseInt(request.getParameter("areaid"));
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
		
		if( AreaID == -1) //选了所有区域的情形
		{
			//System.out.println("全校");
			map = ehi.getSchoolAmFenxiangByZX(Integer.parseInt(styleID), year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		else if(AreaID != -1 && ArcID==-1)	//选了某个区域情形
		{
			//System.out.println("区域");
			map = ehi.getAreaAmFenxiangByZX(Integer.parseInt(styleID), AreaID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		else if(AreaID != -1 && ArcID!=-1)		//选了某个建筑
		{
			//System.out.println("建筑");
			map = ehi.getArcAmFenxiangByZX(Integer.parseInt(styleID), ArcID, year, startmonth, endmonth);
			
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		
		
		out.flush();
		out.close();
	}

}
