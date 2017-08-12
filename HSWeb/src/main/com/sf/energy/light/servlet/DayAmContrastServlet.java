package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.model.SLContrastModel;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.DateOperation;


public class DayAmContrastServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		try {
			dayAmContrastImpl(request,  response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			this.doGet(request, response);
	}
	
	private void dayAmContrastImpl(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int areaID = -1;
		int huiluID = -1;
		int year = 2014;
		int month = 1;
		int flag = 0;
		
		if(request.getParameter("areaID") != null && request.getParameter("areaID") != "")
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		if(request.getParameter("huiluID") != null && request.getParameter("huiluID") != "")
		{
			huiluID = Integer.parseInt(request.getParameter("huiluID"));
			//System.out.println("回路ID"+huiluID);
		}		
		if(request.getParameter("year") != null )
		{
			year = Integer.parseInt(request.getParameter("year"));
			//System.out.println("年"+year);
		}
		if(request.getParameter("month") != null )
		{
			month = Integer.parseInt(request.getParameter("month"));
			//System.out.println("月"+month);
		}
		
		if(areaID == -1)
		{
			flag = 3;
			//System.out.println("全校");
			int MaxDay = 0;
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			////System.out.println("天数"+MaxDay);
			
			AreaDao  areaDao = new AreaDao();
			SLLineDao  Daysld = new SLLineDao();
			SLContrastModel	slcm = null;
			ArrayList<List<SLContrastModel>> all_list = new ArrayList<List<SLContrastModel>>();
			
			List<SLContrastModel>  list = null;	//一个区域的信息
			List<Area>		list_area = null;	//区域的个数
			
			list_area = areaDao.display();
			for(int i = 0;i<list_area.size();i++)
			{
				list = new ArrayList<SLContrastModel>();
//				for(int j = 1;j <= MaxDay;j++)
//				{
//					slcm = new SLContrastModel();
//					slcm = Daysld.queryDayAmByArea(list_area.get(i).getId(), year, month, j);
//					list.add(slcm);
//				}
				
				list=Daysld.queryDayAmAllArea(list_area.get(i).getId(),year, month);
				
				all_list.add(list);
			}
			
			String dayContradata = gson.toJson(new Object[]{flag,all_list});
			//System.out.println("区域list"+dayContradata);
			out.println(dayContradata);
			
		}
		else if(areaID != -1&& huiluID == -1)
		{
			flag = 2;
			//System.out.println("区域");
			int MaxDay = 0;
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			//System.out.println("天数"+MaxDay);
			
			/**日用电对比**/
			SLLineDao  Daysld = new SLLineDao();
			//SLContrastModel	slcm = null;
			ArrayList<ArrayList<SLContrastModel>>  all_list = new ArrayList<ArrayList<SLContrastModel>>();
			
			ArrayList<SLContrastModel>	Daylist = null;		//查询回路的详细信息
			ArrayList<SLLineModel>	Arealist = null;
			
			Arealist = Daysld.queryByAreaID(areaID);		//获取区域里面的所有回路ID
			
			for(int i = 0;i<Arealist.size();i++)
			{
				//System.out.println("ID"+Arealist.get(i).getSLLINE_ID());
				Daylist = new ArrayList<SLContrastModel>();
//				for(int j = 1;j <= MaxDay ;j++)
//				{
//					slcm = new SLContrastModel();
//					slcm = Daysld.queryDayAmByhuilu(Arealist.get(i).getSLLINE_ID(), year, month, j);
//					Daylist.add(slcm);
//				}
				//System.out.println("ID"+Arealist.get(i).getSLLINE_ID());
				
				Daylist = Daysld.queryDayAmByArea(Arealist.get(i).getSLLINE_ID(), year, month);
				all_list.add(Daylist);
			}

			/**日用电对比**/
			
			String dayContradata = gson.toJson(new Object[]{flag,all_list});
			//System.out.println("区域list"+dayContradata);
			out.println(dayContradata);
		}
		else if(areaID != -1&& huiluID != -1)
		{
			flag = 1;
			//System.out.println("回路");
			
			SLLineModel sllm = null;	//获取回路名称
			String SLname = null; 
			
//			int MaxDay = 0;
//			
//			DateOperation dateop = new DateOperation();
//			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			//System.out.println("天数"+MaxDay);
			
			/**日用电对比**/
			SLLineDao  Daysld = new SLLineDao();
			
			sllm = Daysld.queryByID(huiluID);
			SLname = sllm.getSLLINE_NAME();
//			SLContrastModel	slcm = null;
//			ArrayList<SLContrastModel>	Daylist = null;
//			
//			Daylist = new ArrayList<SLContrastModel>();
			Map<String,String> map = null;
			map = new HashMap<String,String>();
			
//			for(int i = 1;i <= MaxDay;i++)
//			{
//				slcm = new SLContrastModel();
//				slcm = Daysld.queryDayAmByhuilu(huiluID, year, month, i);
//				Daylist.add(slcm);
//			}
			
			map = Daysld.queryDayAmByhuiluInMonth(huiluID, year, month);
			
			/**日用电对比**/
			
//			String dayContradata = gson.toJson(new Object[]{flag,Daylist});
			String dayContradata = gson.toJson(new Object[]{flag,map,SLname});
			//System.out.println(dayContradata);
			out.println(dayContradata);
		}
		out.flush();
		out.close();
	}


}
