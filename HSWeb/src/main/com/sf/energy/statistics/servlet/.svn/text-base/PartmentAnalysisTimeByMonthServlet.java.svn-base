package com.sf.energy.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class PartmentAnalysisTimeByMonthServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int style = 0;	//划分方式
		
		
		if(request.getParameter("style") != null)
		{
			style = Integer.parseInt(request.getParameter("style"));
		}
		
		try {
			if(style == 1)
			{
				////System.out.println("性质");
				PartmentAnalysisTimeByMonth_ByStyle(request,response);
			}
			else if(style == 2)
			{
				////System.out.println("分项");
				PartmentAnalysisTimeByMonth_ByFenXiang(request,response);
			}
			else if(style == 3)
			{
				////System.out.println("时间");
				PartmentAnalysisTimeByMonth_ByTime(request,response);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

	private void PartmentAnalysisTimeByMonth_ByStyle(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 1;	//划分方式返回值标记
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int year=2014;
		int pid = -1;
		Map<String, Float>  PartmentTimeContrast_month_a = null;
		Map<String, Float>  PartmentTimeContrast_month_b = null;
		
		List<Float> PartmentTimeContrast_monthlist = null;
		List<Float> PartmentTimeContrast_monthlist_tong = null;		//同比list
		
		Map<String,List<Float>> PartmentTimeContrast_monthmap = new HashMap<String,List<Float>>();
		Map<String,List<Float>> PartmentTimeContrast_monthmap_tong = new HashMap<String,List<Float>>();
		
		
		
		ElecReportHelperImpl  rhi_PartmentTimeContrast_month = new ElecReportHelperImpl();
		
		if(request.getParameter("year") != null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("pid") != null){
			pid = Integer.parseInt(request.getParameter("pid"));
		}
		
		
		if(pid == 0)//选了所有部门
		{
			
			for(int m = 1;m<=12;m++)
			{
					PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getAllGroupStyleCountByMonth(year, m);
					PartmentTimeContrast_month_b = rhi_PartmentTimeContrast_month.getAllGroupStyleCountByMonth(year-1, m);
					
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
					
					for(String key : PartmentTimeContrast_month_b.keySet()){
						PartmentTimeContrast_monthlist_tong = PartmentTimeContrast_monthmap_tong.get(key);
						if(PartmentTimeContrast_monthlist_tong == null){
							PartmentTimeContrast_monthlist_tong = new ArrayList<Float>();
							PartmentTimeContrast_monthmap_tong.put(key, PartmentTimeContrast_monthlist_tong);
						}
						PartmentTimeContrast_monthlist_tong.add(PartmentTimeContrast_month_b.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_monthmap,PartmentTimeContrast_monthmap_tong});
			////System.out.println("月全数据"+data);
			out.println(data);
		}
		else		//选了部门的情况
		{
			for(int m = 1;m<=12;m++)
			{
					PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getGroupStyleCountByMonth(pid,year, m);
					PartmentTimeContrast_month_b = rhi_PartmentTimeContrast_month.getGroupStyleCountByMonth(pid,year-1, m);
					
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
					
					for(String key : PartmentTimeContrast_month_b.keySet()){
						PartmentTimeContrast_monthlist_tong = PartmentTimeContrast_monthmap_tong.get(key);
						if(PartmentTimeContrast_monthlist_tong == null){
							PartmentTimeContrast_monthlist_tong = new ArrayList<Float>();
							PartmentTimeContrast_monthmap_tong.put(key, PartmentTimeContrast_monthlist_tong);
						}
						PartmentTimeContrast_monthlist_tong.add(PartmentTimeContrast_month_b.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_monthmap,PartmentTimeContrast_monthmap_tong});
			////System.out.println("月全数据"+data);
			out.println(data);
		}
		out.flush();
		out.close();
	
	}
	
	private void PartmentAnalysisTimeByMonth_ByFenXiang(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 2;	//划分方式返回值标记
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int year=2014;
		int pid = -1;
		Map<String, Float>  PartmentTimeContrast_month_a = null;
		Map<String, Float>  PartmentTimeContrast_month_b = null;
		
		List<Float> PartmentTimeContrast_monthlist = null;
		List<Float> PartmentTimeContrast_monthlist_tong = null;		//同比list
		
		Map<String,List<Float>> PartmentTimeContrast_monthmap = new HashMap<String,List<Float>>();
		Map<String,List<Float>> PartmentTimeContrast_monthmap_tong = new HashMap<String,List<Float>>();
		
		
		
		ElecReportHelperImpl  rhi_PartmentTimeContrast_month = new ElecReportHelperImpl();
		
		if(request.getParameter("year") != null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("pid") != null){
			pid = Integer.parseInt(request.getParameter("pid"));
		}
		
		
		if(pid == 0)//选了所有部门
		{
			
			for(int m = 1;m<=12;m++)
			{
					PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getAllGroupFenLeiCountByMonth(year, m);
					PartmentTimeContrast_month_b = rhi_PartmentTimeContrast_month.getAllGroupFenLeiCountByMonth(year-1, m);
					
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
					
					for(String key : PartmentTimeContrast_month_b.keySet()){
						PartmentTimeContrast_monthlist_tong = PartmentTimeContrast_monthmap_tong.get(key);
						if(PartmentTimeContrast_monthlist_tong == null){
							PartmentTimeContrast_monthlist_tong = new ArrayList<Float>();
							PartmentTimeContrast_monthmap_tong.put(key, PartmentTimeContrast_monthlist_tong);
						}
						PartmentTimeContrast_monthlist_tong.add(PartmentTimeContrast_month_b.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_monthmap,PartmentTimeContrast_monthmap_tong});
			////System.out.println("月全数据"+data);
			out.println(data);
		}
		else		//选了部门的情况
		{
			for(int m = 1;m<=12;m++)
			{
					PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getGroupFenLeiCountByMonth(pid,year, m);
					PartmentTimeContrast_month_b = rhi_PartmentTimeContrast_month.getGroupFenLeiCountByMonth(pid,year-1, m);
					
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
					
					for(String key : PartmentTimeContrast_month_b.keySet()){
						PartmentTimeContrast_monthlist_tong = PartmentTimeContrast_monthmap_tong.get(key);
						if(PartmentTimeContrast_monthlist_tong == null){
							PartmentTimeContrast_monthlist_tong = new ArrayList<Float>();
							PartmentTimeContrast_monthmap_tong.put(key, PartmentTimeContrast_monthlist_tong);
						}
						PartmentTimeContrast_monthlist_tong.add(PartmentTimeContrast_month_b.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_monthmap,PartmentTimeContrast_monthmap_tong});
			////System.out.println("月全数据"+data);
			out.println(data);
		}
		out.flush();
		out.close();
	}
	
	private void PartmentAnalysisTimeByMonth_ByTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 3;		
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int year=2014;
		int pid = -1; 			//选择的部门ID		
		List<ReportModel>	list = new ArrayList<ReportModel>();
		List<ReportModel>	list_contrast = new ArrayList<ReportModel>();	//对比数据		
		ReportModel		rm = null;
		
		ElecReportHelperImpl  rhi_PartmentTimeContrast_month = new ElecReportHelperImpl();
		
		if(request.getParameter("year") != null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("pid") != null)
		{
			pid = Integer.parseInt(request.getParameter("pid"));
		}
		
		if(pid == 0 )		//选了全校的时候
		{
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_PartmentTimeContrast_month.getAllPartmentWorkTimeCountByMonth(year, i);
				list.add(rm);
			}
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_PartmentTimeContrast_month.getAllPartmentWorkTimeCountByMonth(year-1,i);
				list_contrast.add(rm);
			}
			
			String data = gson.toJson(new Object[]{Flag,list,list_contrast});
			////System.out.println("所有部门数据"+data);
			out.println(data);
		}
		else		//选了某个部门
		{
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_PartmentTimeContrast_month.getPartmentWorkTimeCountByMonth(pid, year,i);
				list.add(rm);
			}
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_PartmentTimeContrast_month.getPartmentWorkTimeCountByMonth(pid, year-1,i);
				list_contrast.add(rm);
			}
			
			
			String data = gson.toJson(new Object[]{Flag,list,list_contrast});
			////System.out.println("部门数据"+data);
			out.println(data);	
		}
		out.flush();
		out.close();
	}
}
