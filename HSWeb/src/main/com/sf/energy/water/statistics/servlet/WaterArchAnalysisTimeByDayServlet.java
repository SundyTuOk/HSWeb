package com.sf.energy.water.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.DateOperation;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;

public class WaterArchAnalysisTimeByDayServlet extends HttpServlet {

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
				ArchAnalysisTimeByDay_ByStyle(request,response);
			}
			else if(style == 2)
			{
				//System.out.println("分项");
				ArchAnalysisTimeByDay_ByFenXiang(request,response);
			}
			else if(style == 3)
			{
				//System.out.println("时间");
				ArchAnalysisTimeByDay_ByTime(request,response);
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

	
	private  void ArchAnalysisTimeByDay_ByStyle(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 1;		//方式标记
		
		PrintWriter out = response.getWriter();
		
		int year=2014,month=2;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
		
		
		
		WaterReportHelperImpl  rhi_ArcTimeContrast_day = new WaterReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_daymap = new HashMap<String,List<Float>>();//每日返回值
		Map<String,List<Float>> ArcTimeContrast_daymap_1 = new HashMap<String,List<Float>>();
		Map<String,List<Float>> ArcTimeContrast_daymap_2 = new HashMap<String,List<Float>>();
		
		Map<String, Float>  ArcTimeContrast_day_a = null;
		Map<String, Float>  ArcTimeContrast_day_b = null;	//同比变量
		Map<String, Float>  ArcTimeContrast_day_c = null;	//环比变量
	
		
		List<Float> ArcTimeContrast_daylist = null;
		List<Float> ArcTimeContrast_daylist_1 = null;
		List<Float> ArcTimeContrast_daylist_2 = null;
	
		
		//List<ReportModel>  yearlist = new ArrayList<ReportModel>();
		//ReportModel  yearlist1 =null;
		if(AreaID == -1&&ArcID == -1)//选了所有区域的情形
		{	
			//获得指定建筑指定年月一个月内每一天的电量
			int MaxDay = 0;
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();	//选中的时间
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				Date d1 = new Date();	//同比的时间
				d1.setYear(year-1900-1);
				d1.setMonth(month-1);
				d1.setDate(i);
				Date d2 = new Date();	//环比的时间
				d2.setYear(year-1900);
				d2.setMonth(month-2);
				d2.setDate(i);
				
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAllAreaStyleCountBetween(d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAllAreaStyleCountBetween(d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getAllAreaStyleCountBetween(d2, d2);
						
					
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
					
					for(String key : ArcTimeContrast_day_b.keySet()){
						ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
						if(ArcTimeContrast_daylist_1 == null){
							ArcTimeContrast_daylist_1 = new ArrayList<Float>();
							ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
						}
						ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
					}
					
					for(String key : ArcTimeContrast_day_c.keySet()){
						ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
						if(ArcTimeContrast_daylist_2 == null){
							ArcTimeContrast_daylist_2 = new ArrayList<Float>();
							ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
						}
						ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
					}
				
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
			//System.out.println("数据"+data);
			out.println(data);	
			
		}else if(AreaID != -1&&ArcID == -1)//选了指定一个区域的情形
		{
			
			//获得指定建筑指定年月一个月内每一天的电量
			int MaxDay = 0;
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				Date d1 = new Date();	//同比的时间
				d1.setYear(year-1900-1);
				d1.setMonth(month-1);
				d1.setDate(i);
				Date d2 = new Date();	//环比的时间
				d2.setYear(year-1900);
				d2.setMonth(month-2);
				d2.setDate(i);
				
				
					
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAreaStyleCountBetween(AreaID,d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAreaStyleCountBetween(AreaID,d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getAreaStyleCountBetween(AreaID,d2, d2);
						
						
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
					
					for(String key : ArcTimeContrast_day_b.keySet()){
						ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
						if(ArcTimeContrast_daylist_1 == null){
							ArcTimeContrast_daylist_1 = new ArrayList<Float>();
							ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
						}
						ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
					}
					
					for(String key : ArcTimeContrast_day_c.keySet()){
						ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
						if(ArcTimeContrast_daylist_2 == null){
							ArcTimeContrast_daylist_2 = new ArrayList<Float>();
							ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
						}
						ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
					}			
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
			//System.out.println("数据"+data);
			out.println(data);
			
		}else if(AreaID != -1&&ArcID != -1)//选了指定建筑的情形
		{
			
		//获得指定建筑指定年月一个月内每一天的电量
		int MaxDay = 0;
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天	
			
		for(int i = 1;i<=MaxDay;i++){
			Date d = new Date();
			d.setYear(year-1900);
			d.setMonth(month-1);
			d.setDate(i);
			Date d1 = new Date();	//同比的时间
			d1.setYear(year-1900-1);
			d1.setMonth(month-1);
			d1.setDate(i);
			Date d2 = new Date();	//环比的时间
			d2.setYear(year-1900);
			d2.setMonth(month-2);
			d2.setDate(i);
			
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcStyleCountBetween(ArcID,d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getArcStyleCountBetween(ArcID,d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getArcStyleCountBetween(ArcID,d2, d2);
				
				for(String key : ArcTimeContrast_day_a.keySet()){
					ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
					if(ArcTimeContrast_daylist == null){
						ArcTimeContrast_daylist = new ArrayList<Float>();
						ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
					}
					ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
				}
				
				for(String key : ArcTimeContrast_day_b.keySet()){
					ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
					if(ArcTimeContrast_daylist_1 == null){
						ArcTimeContrast_daylist_1 = new ArrayList<Float>();
						ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
					}
					ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
				}
				
				for(String key : ArcTimeContrast_day_c.keySet()){
					ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
					if(ArcTimeContrast_daylist_2 == null){
						ArcTimeContrast_daylist_2 = new ArrayList<Float>();
						ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
					}
					ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
				}
			
		}
		
		Gson gson_ArcTimeContrast = new Gson();
		String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
		//System.out.println("数据"+data);
		out.println(data);
	}
		out.flush();
		out.close();
  }
	
	
	private  void ArchAnalysisTimeByDay_ByFenXiang(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 2;
		
		PrintWriter out = response.getWriter();
		
		int year=2014,month=2;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
			
		
		
		WaterReportHelperImpl  rhi_ArcTimeContrast_day = new WaterReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_daymap = new HashMap<String,List<Float>>();//每日返回值
		Map<String,List<Float>> ArcTimeContrast_daymap_1 = new HashMap<String,List<Float>>();
		Map<String,List<Float>> ArcTimeContrast_daymap_2 = new HashMap<String,List<Float>>();
		
		Map<String, Float>  ArcTimeContrast_day_a = null;
		Map<String, Float>  ArcTimeContrast_day_b = null;	//同比变量
		Map<String, Float>  ArcTimeContrast_day_c = null;	//环比变量
	
		
		List<Float> ArcTimeContrast_daylist = null;
		List<Float> ArcTimeContrast_daylist_1 = null;
		List<Float> ArcTimeContrast_daylist_2 = null;
			
		
		if(AreaID == -1&&ArcID == -1)//选了所有区域的情形
		{	
			//获得指定建筑指定年月一个月内每一天的电量
			int MaxDay = 0;
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();	//选中的时间
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				Date d1 = new Date();	//同比的时间
				d1.setYear(year-1900-1);
				d1.setMonth(month-1);
				d1.setDate(i);
				Date d2 = new Date();	//环比的时间
				d2.setYear(year-1900);
				d2.setMonth(month-2);
				d2.setDate(i);
						
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAllAreaFenLeiCountBetween(d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAllAreaFenLeiCountBetween(d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getAllAreaFenLeiCountBetween(d2, d2);
					
					
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
					
					for(String key : ArcTimeContrast_day_b.keySet()){
						ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
						if(ArcTimeContrast_daylist_1 == null){
							ArcTimeContrast_daylist_1 = new ArrayList<Float>();
							ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
						}
						ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
					}
					
					for(String key : ArcTimeContrast_day_c.keySet()){
						ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
						if(ArcTimeContrast_daylist_2 == null){
							ArcTimeContrast_daylist_2 = new ArrayList<Float>();
							ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
						}
						ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
					}
				
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
			//System.out.println("数据"+data);
			out.println(data);	
			
		}else if(AreaID != -1&&ArcID == -1)//选了指定一个区域的情形
		{
			
			//获得指定建筑指定年月一个月内每一天的电量
			int MaxDay = 0;
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			
			for(int i = 1;i<=MaxDay;i++){
				Date d = new Date();
				d.setYear(year-1900);
				d.setMonth(month-1);
				d.setDate(i);
				Date d1 = new Date();	//同比的时间
				d1.setYear(year-1900-1);
				d1.setMonth(month-1);
				d1.setDate(i);
				Date d2 = new Date();	//环比的时间
				d2.setYear(year-1900);
				d2.setMonth(month-2);
				d2.setDate(i);
				
						
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAreaFenLeiCountBetween(AreaID,d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAreaFenLeiCountBetween(AreaID,d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getAreaFenLeiCountBetween(AreaID,d2, d2);
						
				
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
					
					for(String key : ArcTimeContrast_day_b.keySet()){
						ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
						if(ArcTimeContrast_daylist_1 == null){
							ArcTimeContrast_daylist_1 = new ArrayList<Float>();
							ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
						}
						ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
					}
					
					for(String key : ArcTimeContrast_day_c.keySet()){
						ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
						if(ArcTimeContrast_daylist_2 == null){
							ArcTimeContrast_daylist_2 = new ArrayList<Float>();
							ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
						}
						ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
					}
				
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
			//System.out.println("数据"+data);
			out.println(data);
			
		}else if(AreaID != -1&&ArcID != -1)//选了指定建筑的情形
		{
			
		//获得指定建筑指定年月一个月内每一天的电量
		int MaxDay = 0;
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天	
			
		for(int i = 1;i<=MaxDay;i++){
			Date d = new Date();
			d.setYear(year-1900);
			d.setMonth(month-1);
			d.setDate(i);
			Date d1 = new Date();	//同比的时间
			d1.setYear(year-1900-1);
			d1.setMonth(month-1);
			d1.setDate(i);
			Date d2 = new Date();	//环比的时间
			d2.setYear(year-1900);
			d2.setMonth(month-2);
			d2.setDate(i);
			
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcFenLeiCountBetween(ArcID,d, d);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getArcFenLeiCountBetween(ArcID,d1, d1);
				ArcTimeContrast_day_c = rhi_ArcTimeContrast_day.getArcFenLeiCountBetween(ArcID,d2, d2);
				
				for(String key : ArcTimeContrast_day_a.keySet()){
					ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
					if(ArcTimeContrast_daylist == null){
						ArcTimeContrast_daylist = new ArrayList<Float>();
						ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
					}
					ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
				}
				
				for(String key : ArcTimeContrast_day_b.keySet()){
					ArcTimeContrast_daylist_1 = ArcTimeContrast_daymap_1.get(key);
					if(ArcTimeContrast_daylist_1 == null){
						ArcTimeContrast_daylist_1 = new ArrayList<Float>();
						ArcTimeContrast_daymap_1.put(key, ArcTimeContrast_daylist_1);
					}
					ArcTimeContrast_daylist_1.add(ArcTimeContrast_day_b.get(key));
				}
				
				for(String key : ArcTimeContrast_day_c.keySet()){
					ArcTimeContrast_daylist_2 = ArcTimeContrast_daymap_2.get(key);
					if(ArcTimeContrast_daylist_2 == null){
						ArcTimeContrast_daylist_2 = new ArrayList<Float>();
						ArcTimeContrast_daymap_2.put(key, ArcTimeContrast_daylist_2);
					}
					ArcTimeContrast_daylist_2.add(ArcTimeContrast_day_c.get(key));
				}
			
		}
		
		Gson gson_ArcTimeContrast = new Gson();
		String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1,ArcTimeContrast_daymap_2});
		//System.out.println("数据"+data);
		out.println(data);
	}
		out.flush();
		out.close();
	}
	
	
	private  void ArchAnalysisTimeByDay_ByTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 3;		//分类的标记
		
		PrintWriter out = response.getWriter();
		
		int year=2014,month=2;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		List<ReportModel>	list = new ArrayList<ReportModel>();
		List<ReportModel>	list_tong = new ArrayList<ReportModel>();	//环比数据
		List<ReportModel>	list_huan = new ArrayList<ReportModel>();	//同比数据
		ReportModel		rm = null;
		
		
		WaterReportHelperImpl  rhi_ArcTimeContrast_day = new WaterReportHelperImpl();
		Gson gson = new Gson();
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			month=Integer.parseInt(request.getParameter("month"));
		}
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
		
		if(AreaID == -1&&ArcID == -1)
		{
			//System.out.println("全校");
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<= MaxDay;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAllAreaWorkTimeCountByDay(year, month, i);
				list.add(rm);
			}
			
			for(int i = 1;i<= MaxDay_tong;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAllAreaWorkTimeCountByDay(year-1, month, i);
				list_tong.add(rm);
			}
			
			for(int i = 1;i<= MaxDay_huan;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAllAreaWorkTimeCountByDay(year, month-1, i);
				list_huan.add(rm);
			}
			
			String data = gson.toJson(new Object[]{Flag,list,list_tong,list_huan});
			//System.out.println("建筑数据"+data);
			out.println(data);
		}
		else if(AreaID != -1&&ArcID == -1)
		{
			//System.out.println("区域");
			int MaxDay = 0;		//这个月的天数
			int MaxDay_huan = 0;	//环比天数
			int MaxDay_tong = 0;	//同比天数
			
			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
			MaxDay_huan = dateop.queryMaxDayinMonth(year, month-1);
			MaxDay_tong = dateop.queryMaxDayinMonth(year-1, month);
			
			for(int i = 1;i<= MaxDay;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAreaWorkTimeCountByDay(AreaID, year, month, i);
				list.add(rm);
			}
			
			for(int i = 1;i<= MaxDay_tong;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAreaWorkTimeCountByDay(AreaID, year-1, month, i);
				list_tong.add(rm);
			}
			
			for(int i = 1;i<= MaxDay_huan;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAreaWorkTimeCountByDay(AreaID, year, month-1, i);
				list_huan.add(rm);
			}
			
			String data = gson.toJson(new Object[]{Flag,list,list_tong,list_huan});
			//System.out.println("建筑数据"+data);
			out.println(data);	
		}
		else if(AreaID != -1&&ArcID != -1)
		{
			//System.out.println("建筑");
			
			list = rhi_ArcTimeContrast_day.getArcWorkTimeCountEveryDay(ArcID, year, month);
			list_huan = rhi_ArcTimeContrast_day.getArcWorkTimeCountEveryDay(ArcID, year, month-1);
			list_tong = rhi_ArcTimeContrast_day.getArcWorkTimeCountEveryDay(ArcID, year-1, month);
			
			String data = gson.toJson(new Object[]{Flag,list,list_tong,list_huan});
			//System.out.println("建筑数据"+data);
			out.println(data);
		}
		out.flush();
		out.close();
	}


}
