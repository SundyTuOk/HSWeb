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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;

public class WaterPartmentAnalysisTimeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
    	int userID=0;
    	if(session.getAttribute("userId")!=null)
    	{
    		userID=Integer.parseInt(session.getAttribute("userId").toString());
    	}
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		int PartmentTimeContrast_year=2014,PartmentTimeContrast_month=2;
		Map<String,List<Float>> PartmentTimeContrast_yearmap = null;
		Map<String,List<Float>> PartmentTimeContrast_monthmap = null;	
		Map<String,List<Float>> PartmentTimeContrast_daymap = null;
		
		if(request.getParameter("year")!=null)
		{
			PartmentTimeContrast_year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!=null)
		{
			PartmentTimeContrast_month=Integer.parseInt(request.getParameter("month"));
		}
		int pid=-1;
		String id = request.getParameter("pid");
		if(id != null){
			pid = Integer.parseInt(id);
		}
		
		DepartmentDao dpDao = new DepartmentDao();
		List<Department> list_dp = null;
		
		float Value_style1 = 0;
		float Value_style2 = 0;
		float Value_style3 = 0;
		float Value_style4 = 0;
		
		try {
			list_dp = dpDao.display(userID);//获取所有部门的信息
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(pid == -1||pid == 0)//选了所有部门
		{
			WaterReportHelperImpl  rhi_PartmentTimeContrast_year = new WaterReportHelperImpl();
			PartmentTimeContrast_yearmap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_year_a = null;
			List<Float> PartmentTimeContrast_yearlist = null;
			//List<ReportModel>  PartmentTimeContrast_yearlist = new ArrayList<ReportModel>();
			//ReportModel  PartmentTimeContrast_yearlist1 =null;
			int PartmentTimeContrast_yeararray[]={PartmentTimeContrast_year-1,PartmentTimeContrast_year,PartmentTimeContrast_year+1};
			for(int i = 0;i <  PartmentTimeContrast_yeararray.length; i++){
				try {
					for(int j = 0;j<list_dp.size();j++)
					{
						PartmentTimeContrast_year_a = rhi_PartmentTimeContrast_year.getGroupStyleCountByYear(list_dp.get(j).getDepartmentID(), PartmentTimeContrast_yeararray[i]);
						Value_style1+=PartmentTimeContrast_year_a.get("生活");
						Value_style2+=PartmentTimeContrast_year_a.get("教学");
						Value_style3+=PartmentTimeContrast_year_a.get("公共");
						Value_style4+=PartmentTimeContrast_year_a.get("商业");
					}
						PartmentTimeContrast_year_a = new HashMap<String, Float>();//所有部门求和后的新对象
						PartmentTimeContrast_year_a.put("生活", Value_style1);
						PartmentTimeContrast_year_a.put("教学", Value_style2);
						PartmentTimeContrast_year_a.put("公共", Value_style3);
						PartmentTimeContrast_year_a.put("商业", Value_style4);
					for(String key : PartmentTimeContrast_year_a.keySet()){
						PartmentTimeContrast_yearlist = PartmentTimeContrast_yearmap.get(key);
						if(PartmentTimeContrast_yearlist == null){
							PartmentTimeContrast_yearlist = new ArrayList<Float>();
							PartmentTimeContrast_yearmap.put(key, PartmentTimeContrast_yearlist);
						}
						PartmentTimeContrast_yearlist.add(PartmentTimeContrast_year_a.get(key));
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			//获得指定部门指定年月一个月内每一天的电量
			WaterReportHelperImpl  rhi_PartmentTimeContrast_day = new WaterReportHelperImpl();
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			List<Float> PartmentTimeContrast_daylist = null;
			for(int i = 1;i<=31;i++){
				Date d = new Date();
				d.setYear(PartmentTimeContrast_year-1900);
				d.setMonth(PartmentTimeContrast_month);
				d.setDate(i);
				try {
					for(int j = 0;j<list_dp.size();j++)
					{
						PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getGroupStyleCountBetween(list_dp.get(j).getDepartmentID(), d,d);
						Value_style1+=PartmentTimeContrast_day_a.get("生活");
						Value_style2+=PartmentTimeContrast_day_a.get("教学");
						Value_style3+=PartmentTimeContrast_day_a.get("公共");
						Value_style4+=PartmentTimeContrast_day_a.get("商业");
					}
						PartmentTimeContrast_day_a = new HashMap<String, Float>();//所有部门求和后的新对象
						PartmentTimeContrast_day_a.put("生活", Value_style1);
						PartmentTimeContrast_day_a.put("教学", Value_style2);
						PartmentTimeContrast_day_a.put("公共", Value_style3);
						PartmentTimeContrast_day_a.put("商业", Value_style4);
					
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null){
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//获得指定部门指定年份内每个月的电量
			WaterReportHelperImpl  rhi_PartmentTimeContrast_month = new WaterReportHelperImpl();
			PartmentTimeContrast_monthmap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_month_a = null;
			List<Float> PartmentTimeContrast_monthlist = null;
			for(int m = 1;m<=12;m++)
			{
				try {
					for(int j = 0;j<list_dp.size();j++)
					{
						PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getGroupStyleCountByMonth(list_dp.get(j).getDepartmentID(),PartmentTimeContrast_year, m);
						Value_style1+=PartmentTimeContrast_month_a.get("生活");
						Value_style2+=PartmentTimeContrast_month_a.get("教学");
						Value_style3+=PartmentTimeContrast_month_a.get("公共");
						Value_style4+=PartmentTimeContrast_month_a.get("商业");
					}
						PartmentTimeContrast_month_a = new HashMap<String, Float>();//所有部门求和后的新对象
						PartmentTimeContrast_month_a.put("生活", Value_style1);
						PartmentTimeContrast_month_a.put("教学", Value_style2);
						PartmentTimeContrast_month_a.put("公共", Value_style3);
						PartmentTimeContrast_month_a.put("商业", Value_style4);
					
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}else if(pid != -1&&pid != 0)//选了一个部门
		{
			WaterReportHelperImpl  rhi_PartmentTimeContrast_year = new WaterReportHelperImpl();
			PartmentTimeContrast_yearmap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_year_a = null;
			List<Float> PartmentTimeContrast_yearlist = null;
			//List<ReportModel>  PartmentTimeContrast_yearlist = new ArrayList<ReportModel>();
			//ReportModel  PartmentTimeContrast_yearlist1 =null;
			int PartmentTimeContrast_yeararray[]={PartmentTimeContrast_year-1,PartmentTimeContrast_year,PartmentTimeContrast_year+1};
			for(int i = 0;i <  PartmentTimeContrast_yeararray.length; i++){
				try {
					PartmentTimeContrast_year_a = rhi_PartmentTimeContrast_year.getGroupStyleCountByYear(pid, PartmentTimeContrast_yeararray[i]);
					for(String key : PartmentTimeContrast_year_a.keySet()){
						PartmentTimeContrast_yearlist = PartmentTimeContrast_yearmap.get(key);
						if(PartmentTimeContrast_yearlist == null){
							PartmentTimeContrast_yearlist = new ArrayList<Float>();
							PartmentTimeContrast_yearmap.put(key, PartmentTimeContrast_yearlist);
						}
						PartmentTimeContrast_yearlist.add(PartmentTimeContrast_year_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			//获得指定部门指定年月一个月内每一天的电量
			WaterReportHelperImpl  rhi_PartmentTimeContrast_day = new WaterReportHelperImpl();
			PartmentTimeContrast_daymap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_day_a = null;
			List<Float> PartmentTimeContrast_daylist = null;
			for(int i = 1;i<=31;i++){
				Date d = new Date();
				d.setYear(PartmentTimeContrast_year-1900);
				d.setMonth(PartmentTimeContrast_month);
				d.setDate(i);
				try {
					PartmentTimeContrast_day_a = rhi_PartmentTimeContrast_day.getGroupStyleCountBetween(pid,d, d);
					for(String key : PartmentTimeContrast_day_a.keySet()){
						PartmentTimeContrast_daylist = PartmentTimeContrast_daymap.get(key);
						if(PartmentTimeContrast_daylist == null){
							PartmentTimeContrast_daylist = new ArrayList<Float>();
							PartmentTimeContrast_daymap.put(key, PartmentTimeContrast_daylist);
						}
						PartmentTimeContrast_daylist.add(PartmentTimeContrast_day_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//获得指定部门指定年份内每个月的电量
			WaterReportHelperImpl  rhi_PartmentTimeContrast_month = new WaterReportHelperImpl();
			PartmentTimeContrast_monthmap = new HashMap<String,List<Float>>();
			Map<String, Float>  PartmentTimeContrast_month_a = null;
			List<Float> PartmentTimeContrast_monthlist = null;
			for(int m = 1;m<=12;m++)
			{
				try {
					PartmentTimeContrast_month_a = rhi_PartmentTimeContrast_month.getGroupStyleCountByMonth(pid,PartmentTimeContrast_year, m);
					for(String key : PartmentTimeContrast_month_a.keySet()){
						PartmentTimeContrast_monthlist = PartmentTimeContrast_monthmap.get(key);
						if(PartmentTimeContrast_monthlist == null){
							PartmentTimeContrast_monthlist = new ArrayList<Float>();
							PartmentTimeContrast_monthmap.put(key, PartmentTimeContrast_monthlist);
						}
						PartmentTimeContrast_monthlist.add(PartmentTimeContrast_month_a.get(key));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		Gson gson_PartmentTimeContrast = new Gson();
		String  data = gson_PartmentTimeContrast.toJson
						(
								new Object[]{
										PartmentTimeContrast_yearmap,
										PartmentTimeContrast_daymap,
										PartmentTimeContrast_monthmap
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
