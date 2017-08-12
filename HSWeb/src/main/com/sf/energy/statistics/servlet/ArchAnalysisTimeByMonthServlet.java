package com.sf.energy.statistics.servlet;

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
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.DateOperation;

public class ArchAnalysisTimeByMonthServlet extends HttpServlet {

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
				ArchAnalysisTimeByMonth_ByStyle(request,response);
			}
			else if(style == 2)
			{
				////System.out.println("分项");
				ArchAnalysisTimeByMonth_ByFenXiang(request,response);
			}
			else if(style == 3)
			{
				////System.out.println("时间");
				ArchAnalysisTimeByMonth_ByTime(request,response);
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

	
	private  void ArchAnalysisTimeByMonth_ByStyle(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 1;		//方式标记
		
		PrintWriter out = response.getWriter();
		
		int year=2014;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
	
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
		
		
		ElecReportHelperImpl  rhi_ArcTimeContrast_day = new ElecReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_daymap = new HashMap<String,List<Float>>();//每日返回值
		Map<String,List<Float>> ArcTimeContrast_daymap_1 = new HashMap<String,List<Float>>();
		
		Map<String, Float>  ArcTimeContrast_day_a = null;
		Map<String, Float>  ArcTimeContrast_day_b = null;	//对比变量
	
		
		List<Float> ArcTimeContrast_daylist = null;
		List<Float> ArcTimeContrast_daylist_1 = null;
	
		if(AreaID == -1&&ArcID == -1)//选了所有区域的情形
		{	
			
			for(int i = 1;i<=12;i++)
			{
					ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAllAreaStyleCountByMonth(year,i);
					ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAllAreaStyleCountByMonth(year-1,i);
				
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
					
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
			////System.out.println("数据"+data);
			out.println(data);	
			
		}else if(AreaID != -1&&ArcID == -1)//选了指定一个区域的情形
		{
			
			
			for(int i = 1;i<=12;i++)
			{
					ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAreaStyleCountByMonth(AreaID,year,i);
					ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAreaStyleCountByMonth(AreaID,year-1,i);
								
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
					
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
			////System.out.println("数据"+data);
			out.println(data);
			
		}else if(AreaID != -1&&ArcID != -1)//选了指定建筑的情形
		{
				
		for(int i = 1;i<=12;i++){
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcStyleCountByMonth(ArcID,year,i);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getArcStyleCountByMonth(ArcID,year-1,i);
					
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
				
				
		}
		
		Gson gson_ArcTimeContrast = new Gson();
		String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
		////System.out.println("数据"+data);
		out.println(data);
	}
		out.flush();
		out.close();
  }
	
	
	private  void ArchAnalysisTimeByMonth_ByFenXiang(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 2;
		
		PrintWriter out = response.getWriter();
		
		int year=2014;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
			
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
		
		
		
		ElecReportHelperImpl  rhi_ArcTimeContrast_day = new ElecReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_daymap = new HashMap<String,List<Float>>();//每日返回值
		Map<String,List<Float>> ArcTimeContrast_daymap_1 = new HashMap<String,List<Float>>();
		
		Map<String, Float>  ArcTimeContrast_day_a = null;
		Map<String, Float>  ArcTimeContrast_day_b = null;	//对比变量

	
		
		List<Float> ArcTimeContrast_daylist = null;
		List<Float> ArcTimeContrast_daylist_1 = null;

	
		if(AreaID == -1&&ArcID == -1)//选了所有区域的情形
		{
			for(int i = 1;i<=12;i++)
			{		
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAllschoolFenLeiCountByMonth(year,i);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAllschoolFenLeiCountByMonth(year-1,i);
						
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
					
				
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
			////System.out.println("数据"+data);
			out.println(data);	
			
		}else if(AreaID != -1&&ArcID == -1)//选了指定一个区域的情形
		{
			
			for(int i = 1;i<=12;i++)
			{
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getAreaFenLeiCountByMonth(AreaID,year,i);
				ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getAreaFenLeiCountByMonth(AreaID,year-1,i);
						
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
				
			}
			
			Gson gson_ArcTimeContrast = new Gson();
			String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
			////System.out.println("数据"+data);
			out.println(data);
			
		}else if(AreaID != -1&&ArcID != -1)//选了指定建筑的情形
		{
				
		for(int i = 1;i<=12;i++)
		{
			ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcFenLeiCountByMonth(ArcID,year,i);
			ArcTimeContrast_day_b = rhi_ArcTimeContrast_day.getArcFenLeiCountByMonth(ArcID,year-1,i);	
				
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
			
		}
		
		Gson gson_ArcTimeContrast = new Gson();
		String  data = gson_ArcTimeContrast.toJson(new Object[]{Flag,ArcTimeContrast_daymap,ArcTimeContrast_daymap_1});
		////System.out.println("数据"+data);
		out.println(data);
	}
		out.flush();
		out.close();
	}
	
	
	private  void ArchAnalysisTimeByMonth_ByTime(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int Flag = 3;		//分类的标记
		
		PrintWriter out = response.getWriter();
		
		int year=2014;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		List<ReportModel>	list = new ArrayList<ReportModel>();
		List<ReportModel>	list_contrast = new ArrayList<ReportModel>();	//对比数据
		
		ReportModel		rm = null;
		
		
		ElecReportHelperImpl  rhi_ArcTimeContrast_day = new ElecReportHelperImpl();
		Gson gson = new Gson();
		
		
		if(request.getParameter("year")!=null)
		{
			year=Integer.parseInt(request.getParameter("year"));
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
			////System.out.println("全校");
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAllAreaWorkTimeCountByMonth(year, i);
				list.add(rm);
			}
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAllAreaWorkTimeCountByMonth(year-1,i);
				list_contrast.add(rm);
			}
			
			String data = gson.toJson(new Object[]{Flag,list,list_contrast});
			////System.out.println("建筑数据"+data);
			out.println(data);
		}
		else if(AreaID != -1&&ArcID == -1)
		{
			////System.out.println("区域");
			
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAreaWorkTimeCountByMonth(AreaID, year,i);
				list.add(rm);
			}
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getAreaWorkTimeCountByMonth(AreaID, year-1,i);
				list_contrast.add(rm);
			}
			
			
			String data = gson.toJson(new Object[]{Flag,list,list_contrast});
			////System.out.println("建筑数据"+data);
			out.println(data);	
		}
		else if(AreaID != -1&&ArcID != -1)
		{
			////System.out.println("建筑");
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getArcWorkTimeCountByMonth(ArcID, year,i);
				list.add(rm);
			}
			
			for(int i = 1;i<= 12;i++)
			{
				rm = rhi_ArcTimeContrast_day.getArcWorkTimeCountByMonth(ArcID, year-1,i);
				list_contrast.add(rm);
			}
			
			
			String data = gson.toJson(new Object[]{Flag,list,list_contrast});
			////System.out.println("建筑数据"+data);
			out.println(data);	
		}
		out.flush();
		out.close();
	}

}
