package com.sf.energy.water.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.sf.energy.util.DateOperation;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;

public class WaterPartmentAnalysisTimeByDayServlet extends HttpServlet {

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
				//System.out.println("性质");
				PartmentAnalysisTimeByDay_ByStyle(request,response);
			}
			else if(style == 2)
			{
				//System.out.println("分项");
				PartmentAnalysisTimeByDay_ByFenXiang(request,response);
			}
			else if(style == 3)
			{
				//System.out.println("时间");
				PartmentAnalysisTimeByDay_ByTime(request,response);
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
	
	private void PartmentAnalysisTimeByDay_ByStyle(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 1;
		int year=2014,month=2;
		
		PrintWriter out = response.getWriter();
		Map<String,List<Float>> PartmentTimeContrast_daymap = null;
		Map<String,List<Float>> PartmentTimeContrast_daymap_tong = null;	
		Map<String,List<Float>> PartmentTimeContrast_daymap_huan = null;
		WaterReportHelperImpl  rhi_PartmentTimeContrast_day = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		int pid=-1;
		String id = request.getParameter("pid");
		if(id != null){
			pid = Integer.parseInt(id);
		}
		
//		DepartmentDao dpDao = new DepartmentDao();
//		List<Department> list_dp = null;
//		
//		float Value_style1 = 0;
//		float Value_style2 = 0;
//		float Value_style3 = 0;
//		float Value_style4 = 0;
//		
//		try {
//			list_dp = dpDao.display();//获取所有部门的信息
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
		
		if(pid == -1||pid == 0)//选了所有部门
		{
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_tong = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_huan = new HashMap<String,List<Float>>();
			
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			Map<String, Float>  PartmentTimeContrast_day_b = null;	//同比
			Map<String, Float>  PartmentTimeContrast_day_c = null;	//环比
			
			List<Float> PartmentTimeContrast_daylist = null;
			List<Float> PartmentTimeContrast_daylist_tong = null;	//同比
			List<Float> PartmentTimeContrast_daylist_huan = null;	//环比
			
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<=MaxDay;i++)
			{
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month);
				d.setDate(i);
									
				PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getAllGroupStyleCountBetween(d,d);
					
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null){
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
				
			}
			
			for(int i = 1;i<=MaxDay_tong;i++)
			{
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				
				PartmentTimeContrast_day_b = rhi_PartmentTimeContrast_day.getAllGroupStyleCountBetween( d,d);
						
					for(String key : PartmentTimeContrast_day_b.keySet()){
						PartmentTimeContrast_daylist_tong = PartmentTimeContrast_daymap_tong.get(key);
						if(PartmentTimeContrast_daylist_tong == null){
							PartmentTimeContrast_daylist_tong = new ArrayList<Float>();
							PartmentTimeContrast_daymap_tong.put(key, PartmentTimeContrast_daylist_tong);
						}
						PartmentTimeContrast_daylist_tong.add(PartmentTimeContrast_day_b.get(key));
					}
				
			}
			
			for(int i = 1;i<=MaxDay_huan;i++)
			{
				Date d = new Date();
				d.setYear(year-1-1900);
				d.setMonth(month);
				d.setDate(i);
				
					PartmentTimeContrast_day_c = rhi_PartmentTimeContrast_day.getAllGroupStyleCountBetween(d,d);
							
					for(String key : PartmentTimeContrast_day_c.keySet()){
						PartmentTimeContrast_daylist_huan = PartmentTimeContrast_daymap_huan.get(key);
						if(PartmentTimeContrast_daylist_huan == null){
							PartmentTimeContrast_daylist_huan = new ArrayList<Float>();
							PartmentTimeContrast_daymap_huan.put(key, PartmentTimeContrast_daylist_huan);
						}
						PartmentTimeContrast_daylist_huan.add(PartmentTimeContrast_day_c.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_daymap,PartmentTimeContrast_daymap_tong,PartmentTimeContrast_daymap_huan});
			//System.out.println("数据"+data);
			out.println(data);
		}else if(pid != -1&&pid != 0)//选了一个部门
		{
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_tong = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_huan = new HashMap<String,List<Float>>();
			
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			Map<String, Float>  PartmentTimeContrast_day_b = null;	//同比
			Map<String, Float>  PartmentTimeContrast_day_c = null;	//环比
			
			List<Float> PartmentTimeContrast_daylist = null;
			List<Float> PartmentTimeContrast_daylist_tong = null;	//同比
			List<Float> PartmentTimeContrast_daylist_huan = null;	//环比
			
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month);
				d.setDate(i);
					PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getGroupStyleCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null)
						{
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
			}
			
			for(int i = 1;i<=MaxDay_tong;i++){
				Date d = new Date();
				d.setYear(year-1-1900);
				d.setMonth(month);
				d.setDate(i);
					PartmentTimeContrast_day_b = rhi_PartmentTimeContrast_day.getGroupStyleCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_b.keySet()){
						PartmentTimeContrast_daylist_tong = PartmentTimeContrast_daymap_tong.get(key);
						if(PartmentTimeContrast_daylist_tong == null)
						{
							PartmentTimeContrast_daylist_tong = new ArrayList<Float>();
							PartmentTimeContrast_daymap_tong.put(key, PartmentTimeContrast_daylist_tong);
						}
						PartmentTimeContrast_daylist_tong.add(PartmentTimeContrast_day_b.get(key));
					}
			}
			
			
			for(int i = 1;i<=MaxDay_huan;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
					PartmentTimeContrast_day_c = rhi_PartmentTimeContrast_day.getGroupStyleCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_c.keySet()){
						PartmentTimeContrast_daylist_huan = PartmentTimeContrast_daymap_huan.get(key);
						if(PartmentTimeContrast_daylist_huan == null)
						{
							PartmentTimeContrast_daylist_huan = new ArrayList<Float>();
							PartmentTimeContrast_daymap_huan.put(key, PartmentTimeContrast_daylist_huan);
						}
						PartmentTimeContrast_daylist_huan.add(PartmentTimeContrast_day_c.get(key));
					}
			}
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_daymap,PartmentTimeContrast_daymap_tong,PartmentTimeContrast_daymap_huan});
			//System.out.println("部门数据"+data);
			out.println(data);
		}
		out.flush();
		out.close();
	}
	
	private void PartmentAnalysisTimeByDay_ByFenXiang(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 2;
		int year=2014,month=2;
		
		PrintWriter out = response.getWriter();
		Map<String,List<Float>> PartmentTimeContrast_daymap = null;
		Map<String,List<Float>> PartmentTimeContrast_daymap_tong = null;	
		Map<String,List<Float>> PartmentTimeContrast_daymap_huan = null;
		WaterReportHelperImpl  rhi_PartmentTimeContrast_day = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		int pid=-1;
		String id = request.getParameter("pid");
		if(id != null){
			pid = Integer.parseInt(id);
		}
		
		
		if(pid == -1||pid == 0)//选了所有部门
		{
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_tong = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_huan = new HashMap<String,List<Float>>();
			
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			Map<String, Float>  PartmentTimeContrast_day_b = null;	//同比
			Map<String, Float>  PartmentTimeContrast_day_c = null;	//环比
			
			List<Float> PartmentTimeContrast_daylist = null;
			List<Float> PartmentTimeContrast_daylist_tong = null;	//同比
			List<Float> PartmentTimeContrast_daylist_huan = null;	//环比
			
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<=MaxDay;i++)
			{
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month);
				d.setDate(i);
					
				PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getAllGroupFenLeiCountBetween(d,d);
						
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null){
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
				
			}
			
			for(int i = 1;i<=MaxDay_tong;i++)
			{
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				
				PartmentTimeContrast_day_b = rhi_PartmentTimeContrast_day.getAllGroupFenLeiCountBetween(d,d);
							
					for(String key : PartmentTimeContrast_day_b.keySet()){
						PartmentTimeContrast_daylist_tong = PartmentTimeContrast_daymap_tong.get(key);
						if(PartmentTimeContrast_daylist_tong == null){
							PartmentTimeContrast_daylist_tong = new ArrayList<Float>();
							PartmentTimeContrast_daymap_tong.put(key, PartmentTimeContrast_daylist_tong);
						}
						PartmentTimeContrast_daylist_tong.add(PartmentTimeContrast_day_b.get(key));
					}
				
			}
			
			for(int i = 1;i<=MaxDay_huan;i++)
			{
				Date d = new Date();
				d.setYear(year-1-1900);
				d.setMonth(month);
				d.setDate(i);
				
				PartmentTimeContrast_day_c = rhi_PartmentTimeContrast_day.getAllGroupFenLeiCountBetween(d,d);
							
					for(String key : PartmentTimeContrast_day_c.keySet()){
						PartmentTimeContrast_daylist_huan = PartmentTimeContrast_daymap_huan.get(key);
						if(PartmentTimeContrast_daylist_huan == null){
							PartmentTimeContrast_daylist_huan = new ArrayList<Float>();
							PartmentTimeContrast_daymap_huan.put(key, PartmentTimeContrast_daylist_huan);
						}
						PartmentTimeContrast_daylist_huan.add(PartmentTimeContrast_day_c.get(key));
					}
				
			}
			
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_daymap,PartmentTimeContrast_daymap_tong,PartmentTimeContrast_daymap_huan});
			//System.out.println("数据"+data);
			out.println(data);
		}else if(pid != -1&&pid != 0)//选了一个部门
		{
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_tong = new HashMap<String,List<Float>>();
			PartmentTimeContrast_daymap_huan = new HashMap<String,List<Float>>();
			
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			Map<String, Float>  PartmentTimeContrast_day_b = null;	//同比
			Map<String, Float>  PartmentTimeContrast_day_c = null;	//环比
			
			List<Float> PartmentTimeContrast_daylist = null;
			List<Float> PartmentTimeContrast_daylist_tong = null;	//同比
			List<Float> PartmentTimeContrast_daylist_huan = null;	//环比
			
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month);
				d.setDate(i);
					PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getGroupFenLeiCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null)
						{
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
			}
			
			for(int i = 1;i<=MaxDay_tong;i++){
				Date d = new Date();
				d.setYear(year-1-1900);
				d.setMonth(month);
				d.setDate(i);
					PartmentTimeContrast_day_b = rhi_PartmentTimeContrast_day.getGroupFenLeiCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_b.keySet()){
						PartmentTimeContrast_daylist_tong = PartmentTimeContrast_daymap_tong.get(key);
						if(PartmentTimeContrast_daylist_tong == null)
						{
							PartmentTimeContrast_daylist_tong = new ArrayList<Float>();
							PartmentTimeContrast_daymap_tong.put(key, PartmentTimeContrast_daylist_tong);
						}
						PartmentTimeContrast_daylist_tong.add(PartmentTimeContrast_day_b.get(key));
					}
			}
			
			
			for(int i = 1;i<=MaxDay_huan;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
					PartmentTimeContrast_day_c = rhi_PartmentTimeContrast_day.getGroupFenLeiCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_c.keySet()){
						PartmentTimeContrast_daylist_huan = PartmentTimeContrast_daymap_huan.get(key);
						if(PartmentTimeContrast_daylist_huan == null)
						{
							PartmentTimeContrast_daylist_huan = new ArrayList<Float>();
							PartmentTimeContrast_daymap_huan.put(key, PartmentTimeContrast_daylist_huan);
						}
						PartmentTimeContrast_daylist_huan.add(PartmentTimeContrast_day_c.get(key));
					}
			}
			String data = gson.toJson(new Object[]{Flag,PartmentTimeContrast_daymap,PartmentTimeContrast_daymap_tong,PartmentTimeContrast_daymap_huan});
			//System.out.println("部门数据"+data);
			out.println(data);
		}
		out.flush();
		out.close();
	}
	
	private void PartmentAnalysisTimeByDay_ByTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 3;		//标记
		int year=2014,month=2;
		int pid=-1;
		
		List<ReportModel>	list = new ArrayList<ReportModel>();
		List<ReportModel>	list_tong = new ArrayList<ReportModel>();	//环比数据
		List<ReportModel>	list_huan = new ArrayList<ReportModel>();	//同比数据
		ReportModel		rm = null;
		
		PrintWriter out = response.getWriter();
		
		WaterReportHelperImpl  rhi_PartmentTimeContrast_day = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		if(request.getParameter("pid") != null){
			pid = Integer.parseInt(request.getParameter("pid"));
		}
		
		if(pid == -1||pid == 0)//选了所有部门
		{
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 0;i<MaxDay;i++)
			{
				rm = rhi_PartmentTimeContrast_day.getAllPartmentWorkTimeCountByDay(year, month, i);
				list.add(rm);
			}
			
			for(int i = 0;i<MaxDay_tong;i++)
			{
				rm = rhi_PartmentTimeContrast_day.getAllPartmentWorkTimeCountByDay(year-1, month, i);
				list_tong.add(rm);
			}
			
			for(int i = 0;i<MaxDay_huan;i++)
			{
				rm = rhi_PartmentTimeContrast_day.getAllPartmentWorkTimeCountByDay(year, month-1, i);
				list_huan.add(rm);
			}
			
			
			String data = gson.toJson(new Object[]{Flag,list,list_tong,list_huan});
			//System.out.println("时间数据"+data);
			out.println(data);	
		}
		else		//选了某个部门的情形
		{
			list = rhi_PartmentTimeContrast_day.getPartmentWorkTimeCountEveryDay(pid, year, month);
			list_tong = rhi_PartmentTimeContrast_day.getPartmentWorkTimeCountEveryDay(pid, year-1, month);
			list_huan = rhi_PartmentTimeContrast_day.getPartmentWorkTimeCountEveryDay(pid, year, month-1);
			
			String data = gson.toJson(new Object[]{Flag,list,list_tong,list_huan});
			//System.out.println("时间数据1"+data);
			out.println(data);
		}
		
		out.flush();
		out.close();
	}

}
