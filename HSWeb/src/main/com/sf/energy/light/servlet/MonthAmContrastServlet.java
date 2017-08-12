package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class MonthAmContrastServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		try {
			monthAmContrastImpl( request,  response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

	private void monthAmContrastImpl(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int areaID = -1;
		int huiluID = -1;
		int year = 2014;
		int flag = 0;	//标记是否学校，区域，回路
		
		if(request.getParameter("areaID") != null && request.getParameter("areaID") != "")
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		if(request.getParameter("huiluID") != null && request.getParameter("huiluID") != "")
		{
			huiluID = Integer.parseInt(request.getParameter("huiluID"));
		}
		if(request.getParameter("year") != null )
		{
			year = Integer.parseInt(request.getParameter("year"));
			//System.out.println("年"+year);
		}
		
		
		if(areaID == -1)
		{
			flag = 3;
			//System.out.println("全校");
			
			AreaDao  areaDao = new AreaDao();
			SLLineDao  slDao = new SLLineDao();
			ArrayList<List<SLContrastModel>> 	all_list = new ArrayList<List<SLContrastModel>>();
			
			SLContrastModel    slmodel = null;	//区域回路电量信息
			List<SLContrastModel>  list = null;	//一个区域的信息
			List<Area>		list_area = null;	//区域的个数
			
			list_area = areaDao.display();
			for(int i = 0;i<list_area.size();i++)
			{
				//System.out.println("区域ID"+list_area.get(i).getId());
				//System.out.println("年"+year);
				list = new  ArrayList<SLContrastModel>();
				for(int j = 1;j <= 12;j++)
				{
					slmodel = slDao.queryMonthAmByArea(list_area.get(i).getId(), year, j);
					list.add(slmodel);
				}
				all_list.add(list);
			}
			
			String dayContradata = gson.toJson(new Object[]{flag,all_list});
			//System.out.println("全校list"+dayContradata);
			out.println(dayContradata);
		}
		else if(areaID != -1&& huiluID == -1)
		{
			flag = 2;
			//System.out.println("区域");
			
			/**月用电对比**/
			SLLineDao  slld = new SLLineDao();
			ArrayList<ArrayList<SLContrastModel>>  all_list = new ArrayList<ArrayList<SLContrastModel>>();
			
			ArrayList<SLContrastModel>	Monlist = null;
			SLContrastModel    slmodel = null;	//区域回路电量信息
			ArrayList<SLLineModel>	Arealist = null;	//获取区域里面的所有回路ID
			Arealist = slld.queryByAreaID(areaID);
			for(int i = 0;i<Arealist.size();i++)
			{
				Monlist = new ArrayList<SLContrastModel>();
				for(int j = 1;j <= 12;j++)
				{
					slmodel = slld.queryMonthAmByhuilu(Arealist.get(i).getSLLINE_ID(), year, j);
					Monlist.add(slmodel);
				}
				all_list.add(Monlist);
			}
			/**月用电对比**/
			
			String dayContradata = gson.toJson(new Object[]{flag,all_list});
			//System.out.println("区域list"+dayContradata);
			out.println(dayContradata);
			
		}
		else if(areaID != -1&& huiluID != -1)
		{
			flag = 1;
			//System.out.println("回路");
			/**月用电对比**/
			SLLineDao  slld = new SLLineDao();
			ArrayList<SLContrastModel>	Monlist = null;
			SLContrastModel    slmodel = null;	//区域回路电量信息
			
			Monlist = new ArrayList<SLContrastModel>();
			
			for(int i = 1;i <= 12;i++)
			{
				slmodel = slld.queryMonthAmByhuilu(huiluID, year, i); 
				Monlist.add(slmodel);
			}
			
			/**月用电对比**/
			
			String dayContradata = gson.toJson(new Object[]{flag,Monlist});
			//System.out.println("回路数据"+dayContradata);
			out.println(dayContradata);
		}
		out.flush();
		out.close();
		
		
		
	}

}
