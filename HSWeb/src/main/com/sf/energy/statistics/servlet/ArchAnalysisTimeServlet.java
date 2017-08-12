package com.sf.energy.statistics.servlet;

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
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class ArchAnalysisTimeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		int ArcTimeContrast_year=2014,ArcTimeContrast_month=2;
		int ArcID = -1; 			//表示选择选择所有建筑
		int AreaID = -1;			//表示选择所有区域
		
		Map<Integer, Area> resultMap = null;    //查询所有的区域ID
		List<Integer>		allArcIDlist = null;	//存放所有区域下的所有建筑
		List<Architecture> arealist = null; //单个区域的建筑ID
		List<Architecture> list = null; //通过区域id获取下面的所有建筑id
		
		List<Integer>		ArcIDlist = null;	//存放每个区域下的所有建筑
		allArcIDlist = new ArrayList<Integer>();
		ArchitectureDao  archDao = new ArchitectureDao();
		AreaDao  areaDao = new AreaDao();
		ArcIDlist = new ArrayList<Integer>();
		
		if(request.getParameter("year")!=null)
		{
			ArcTimeContrast_year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			ArcTimeContrast_month=Integer.parseInt(request.getParameter("month"));
		}
		
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid")!=null)
		{
			AreaID = Integer.parseInt(request.getParameter("areaid"));		
		}
		
		//查出所有的区域ID
		try {
			resultMap = areaDao.getAllArea();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// 查出所有区域下的建筑ID
		for (int i : resultMap.keySet()) {
			try {
				arealist = archDao.queryArchByAreaID(i);
				for (int j = 0; j < arealist.size(); j++) {
					allArcIDlist.add(arealist.get(j).getId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 通过区域ID查出对应区域下面建筑ID
		try {
			list = archDao.queryArchByAreaID(AreaID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ArcIDlist.add(list.get(i).getId());
		}
		
		ElecReportHelperImpl  rhi_ArcTimeContrast_year = new ElecReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_yearmap = new HashMap<String,List<Float>>();//年份返回值
		ElecReportHelperImpl  rhi_ArcTimeContrast_month = new ElecReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_monthmap = new HashMap<String,List<Float>>();//月份返回值
		ElecReportHelperImpl  rhi_ArcTimeContrast_day = new ElecReportHelperImpl();
		Map<String,List<Float>> ArcTimeContrast_daymap = new HashMap<String,List<Float>>();//每日返回值
		
		Map<String, Float>  ArcTimeContrast_year_a = null;
		Map<String, Float>  ArcTimeContrast_day_a = null;
		Map<String, Float>  ArcTimeContrast_month_a = null;
		
		List<Float> ArcTimeContrast_yearlist = null;
		List<Float> ArcTimeContrast_daylist = null;
		List<Float> ArcTimeContrast_monthlist = null;
		
		float Value_style1 = 0;
		float Value_style2 = 0;
		float Value_style3 = 0;
		float Value_style4 = 0;
		
		//List<ReportModel>  ArcTimeContrast_yearlist = new ArrayList<ReportModel>();
		//ReportModel  ArcTimeContrast_yearlist1 =null;
		
		if(AreaID == -1&&ArcID == -1)//选了所有区域的情形
		{
			int ArcTimeContrast_yeararray[]={ArcTimeContrast_year-1,ArcTimeContrast_year,ArcTimeContrast_year+1};
			for(int i = 0;i <  ArcTimeContrast_yeararray.length; i++){
				try {
					for(int j=0;j<allArcIDlist.size();j++)
					{
						ArcTimeContrast_year_a = rhi_ArcTimeContrast_year.getArcStyleCountByYear(allArcIDlist.get(j).intValue(), ArcTimeContrast_yeararray[i]);
						Value_style1+=ArcTimeContrast_year_a.get("生活");
						Value_style2+=ArcTimeContrast_year_a.get("教学");
						Value_style3+=ArcTimeContrast_year_a.get("公共");
						Value_style4+=ArcTimeContrast_year_a.get("商业");
					}
					   ArcTimeContrast_year_a = new HashMap<String, Float>();//所有区域求和后的新对象
					   ArcTimeContrast_year_a.put("生活", Value_style1);
					   ArcTimeContrast_year_a.put("教学", Value_style2);
					   ArcTimeContrast_year_a.put("公共", Value_style3);
					   ArcTimeContrast_year_a.put("商业", Value_style4);
					   
					for(String key : ArcTimeContrast_year_a.keySet()){
						ArcTimeContrast_yearlist = ArcTimeContrast_yearmap.get(key);
						if(ArcTimeContrast_yearlist == null){
							ArcTimeContrast_yearlist = new ArrayList<Float>();
							ArcTimeContrast_yearmap.put(key, ArcTimeContrast_yearlist);
						}
						ArcTimeContrast_yearlist.add(ArcTimeContrast_year_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		

			//获得指定建筑指定年份内每个月的电量		
			for(int m = 1;m<=12;m++)
			{
				try {
					for(int j=0;j<allArcIDlist.size();j++)
					{
						ArcTimeContrast_month_a = rhi_ArcTimeContrast_month.getArcStyleCountByMonth(allArcIDlist.get(j).intValue(),ArcTimeContrast_year, m);
						Value_style1+=ArcTimeContrast_month_a.get("生活");
						Value_style2+=ArcTimeContrast_month_a.get("教学");
						Value_style3+=ArcTimeContrast_month_a.get("公共");
						Value_style4+=ArcTimeContrast_month_a.get("商业");
					}
					  ArcTimeContrast_month_a = new HashMap<String, Float>();//所有区域求和后的新对象
					   ArcTimeContrast_month_a.put("生活", Value_style1);
					   ArcTimeContrast_month_a.put("教学", Value_style2);
					   ArcTimeContrast_month_a.put("公共", Value_style3);
					   ArcTimeContrast_month_a.put("商业", Value_style4);
					
					for(String key : ArcTimeContrast_month_a.keySet()){
						ArcTimeContrast_monthlist = ArcTimeContrast_monthmap.get(key);
						if(ArcTimeContrast_monthlist == null){
							ArcTimeContrast_monthlist = new ArrayList<Float>();
							ArcTimeContrast_monthmap.put(key, ArcTimeContrast_monthlist);
						}
						ArcTimeContrast_monthlist.add(ArcTimeContrast_month_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			//获得指定建筑指定年月一个月内每一天的电量
			for(int i = 1;i<=31;i++){
				Date d = new Date();
				d.setYear(ArcTimeContrast_year-1900);
				d.setMonth(ArcTimeContrast_month);
				d.setDate(i);
				try {
					for(int j=0;j<allArcIDlist.size();j++)
					{
						ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcStyleCountBetween(allArcIDlist.get(j).intValue(),d, d);
						Value_style1+=ArcTimeContrast_day_a.get("生活");
						Value_style2+=ArcTimeContrast_day_a.get("教学");
						Value_style3+=ArcTimeContrast_day_a.get("公共");
						Value_style4+=ArcTimeContrast_day_a.get("商业");	
					}
					ArcTimeContrast_day_a = new HashMap<String, Float>();//所有区域求和后的新对象
					ArcTimeContrast_day_a.put("生活", Value_style1);
					ArcTimeContrast_day_a.put("教学", Value_style2);
					ArcTimeContrast_day_a.put("公共", Value_style3);
					ArcTimeContrast_day_a.put("商业", Value_style4);
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		}else if(AreaID != -1&&ArcID == -1)//选了指定一个区域的情形
		{
			int ArcTimeContrast_yeararray[]={ArcTimeContrast_year-1,ArcTimeContrast_year,ArcTimeContrast_year+1};
			for(int i = 0;i <  ArcTimeContrast_yeararray.length; i++){
				try {
					for(int j=0;j<ArcIDlist.size();j++)
					{
						ArcTimeContrast_year_a = rhi_ArcTimeContrast_year.getArcStyleCountByYear(ArcIDlist.get(j).intValue(), ArcTimeContrast_yeararray[i]);
						Value_style1+=ArcTimeContrast_year_a.get("生活");
						Value_style2+=ArcTimeContrast_year_a.get("教学");
						Value_style3+=ArcTimeContrast_year_a.get("公共");
						Value_style4+=ArcTimeContrast_year_a.get("商业");
					}
					   ArcTimeContrast_year_a = new HashMap<String, Float>();//一个区域所有建筑求和后的新对象
					   ArcTimeContrast_year_a.put("生活", Value_style1);
					   ArcTimeContrast_year_a.put("教学", Value_style2);
					   ArcTimeContrast_year_a.put("公共", Value_style3);
					   ArcTimeContrast_year_a.put("商业", Value_style4);
					   
					for(String key : ArcTimeContrast_year_a.keySet()){
						ArcTimeContrast_yearlist = ArcTimeContrast_yearmap.get(key);
						if(ArcTimeContrast_yearlist == null){
							ArcTimeContrast_yearlist = new ArrayList<Float>();
							ArcTimeContrast_yearmap.put(key, ArcTimeContrast_yearlist);
						}
						ArcTimeContrast_yearlist.add(ArcTimeContrast_year_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		

			//获得指定建筑指定年份内每个月的电量		
			for(int m = 1;m<=12;m++)
			{
				try {
					for(int j=0;j<ArcIDlist.size();j++)
					{
						ArcTimeContrast_month_a = rhi_ArcTimeContrast_month.getArcStyleCountByMonth(ArcIDlist.get(j).intValue(),ArcTimeContrast_year, m);
						Value_style1+=ArcTimeContrast_month_a.get("生活");
						Value_style2+=ArcTimeContrast_month_a.get("教学");
						Value_style3+=ArcTimeContrast_month_a.get("公共");
						Value_style4+=ArcTimeContrast_month_a.get("商业");
					}
					  ArcTimeContrast_month_a = new HashMap<String, Float>();//一个区域所有建筑求和后的新对象
					   ArcTimeContrast_month_a.put("生活", Value_style1);
					   ArcTimeContrast_month_a.put("教学", Value_style2);
					   ArcTimeContrast_month_a.put("公共", Value_style3);
					   ArcTimeContrast_month_a.put("商业", Value_style4);
					
					for(String key : ArcTimeContrast_month_a.keySet()){
						ArcTimeContrast_monthlist = ArcTimeContrast_monthmap.get(key);
						if(ArcTimeContrast_monthlist == null){
							ArcTimeContrast_monthlist = new ArrayList<Float>();
							ArcTimeContrast_monthmap.put(key, ArcTimeContrast_monthlist);
						}
						ArcTimeContrast_monthlist.add(ArcTimeContrast_month_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			//获得指定建筑指定年月一个月内每一天的电量
			for(int i = 1;i<=31;i++){
				Date d = new Date();
				d.setYear(ArcTimeContrast_year-1900);
				d.setMonth(ArcTimeContrast_month);
				d.setDate(i);
				try {
					for(int j=0;j<ArcIDlist.size();j++)
					{
						ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcStyleCountBetween(ArcIDlist.get(j).intValue(),d, d);
						Value_style1+=ArcTimeContrast_day_a.get("生活");
						Value_style2+=ArcTimeContrast_day_a.get("教学");
						Value_style3+=ArcTimeContrast_day_a.get("公共");
						Value_style4+=ArcTimeContrast_day_a.get("商业");	
					}
					ArcTimeContrast_day_a = new HashMap<String, Float>();//一个区域所有建筑求和后的新对象
					ArcTimeContrast_day_a.put("生活", Value_style1);
					ArcTimeContrast_day_a.put("教学", Value_style2);
					ArcTimeContrast_day_a.put("公共", Value_style3);
					ArcTimeContrast_day_a.put("商业", Value_style4);
					for(String key : ArcTimeContrast_day_a.keySet()){
						ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
						if(ArcTimeContrast_daylist == null){
							ArcTimeContrast_daylist = new ArrayList<Float>();
							ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
						}
						ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}else if(AreaID != -1&&ArcID != -1)//选了指定建筑的情形
		{
		int ArcTimeContrast_yeararray[]={ArcTimeContrast_year-1,ArcTimeContrast_year,ArcTimeContrast_year+1};
		for(int i = 0;i <  ArcTimeContrast_yeararray.length; i++){
			try {
				ArcTimeContrast_year_a = rhi_ArcTimeContrast_year.getArcStyleCountByYear(ArcID, ArcTimeContrast_yeararray[i]);
				for(String key : ArcTimeContrast_year_a.keySet()){
					ArcTimeContrast_yearlist = ArcTimeContrast_yearmap.get(key);
					if(ArcTimeContrast_yearlist == null){
						ArcTimeContrast_yearlist = new ArrayList<Float>();
						ArcTimeContrast_yearmap.put(key, ArcTimeContrast_yearlist);
					}
					ArcTimeContrast_yearlist.add(ArcTimeContrast_year_a.get(key));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	
		//获得指定建筑指定年月一个月内每一天的电量
		for(int i = 1;i<=31;i++){
			Date d = new Date();
			d.setYear(ArcTimeContrast_year-1900);
			d.setMonth(ArcTimeContrast_month);
			d.setDate(i);
			try {
				ArcTimeContrast_day_a = rhi_ArcTimeContrast_day.getArcStyleCountBetween(ArcID,d, d);
				for(String key : ArcTimeContrast_day_a.keySet()){
					ArcTimeContrast_daylist = ArcTimeContrast_daymap.get(key);
					if(ArcTimeContrast_daylist == null){
						ArcTimeContrast_daylist = new ArrayList<Float>();
						ArcTimeContrast_daymap.put(key, ArcTimeContrast_daylist);
					}
					ArcTimeContrast_daylist.add(ArcTimeContrast_day_a.get(key));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//获得指定建筑指定年份内每个月的电量		
		for(int m = 1;m<=12;m++)
		{
			try {
				ArcTimeContrast_month_a = rhi_ArcTimeContrast_month.getArcStyleCountByMonth(ArcID,ArcTimeContrast_year, m);
				for(String key : ArcTimeContrast_month_a.keySet()){
					ArcTimeContrast_monthlist = ArcTimeContrast_monthmap.get(key);
					if(ArcTimeContrast_monthlist == null){
						ArcTimeContrast_monthlist = new ArrayList<Float>();
						ArcTimeContrast_monthmap.put(key, ArcTimeContrast_monthlist);
					}
					ArcTimeContrast_monthlist.add(ArcTimeContrast_month_a.get(key));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		}
		
		Gson gson_ArcTimeContrast = new Gson();
		String  data = gson_ArcTimeContrast.toJson
						(
								new Object[]{
										ArcTimeContrast_yearmap,
										ArcTimeContrast_daymap,
										ArcTimeContrast_monthmap
								}
						);
		////System.out.println(data);
		
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
}
