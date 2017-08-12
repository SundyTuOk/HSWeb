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


public class WaterPartmentAnalysisFenlei extends HttpServlet {
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
		
		int PartmentTimeFenlei_start_year1=2012,PartmentTimeFenlei_start_month1=2,
		PartmentTimeFenlei_end_year1=2014,PartmentTimeFenlei_end_month1=4,
		PartmentTimeFenlei_start_year2=2013,PartmentTimeFenlei_start_month2=2,
		PartmentTimeFenlei_end_year2=2014,PartmentTimeFenlei_end_month2=4;
		if(request.getParameter("start_year1")!=null){
			//System.out.println(PartmentTimeFenlei_start_year1);
			PartmentTimeFenlei_start_year1=Integer.parseInt(request.getParameter("start_year1"));
		}
		if(request.getParameter("start_month1")!=null){
			PartmentTimeFenlei_start_month1=Integer.parseInt(request.getParameter("start_month1"));
		}
		if(request.getParameter("end_year1")!=null){
			PartmentTimeFenlei_end_year1=Integer.parseInt(request.getParameter("end_year1"));
		}
		if(request.getParameter("end_month1")!=null){
			PartmentTimeFenlei_end_month1=Integer.parseInt(request.getParameter("end_month1"));
		}
		if(request.getParameter("start_year2")!=null){
			PartmentTimeFenlei_start_year2=Integer.parseInt(request.getParameter("start_year2"));
		}
		if(request.getParameter("start_month2")!=null){
			PartmentTimeFenlei_start_month2=Integer.parseInt(request.getParameter("start_month2"));
		}
		if(request.getParameter("end_year2")!=null){
			PartmentTimeFenlei_end_year2=Integer.parseInt(request.getParameter("end_year2"));
		}
		if(request.getParameter("end_month2")!=null){
			PartmentTimeFenlei_end_month2=Integer.parseInt(request.getParameter("end_month2"));
		}
		int selectPartmentID = -1;
		String pid = request.getParameter("pid");
		if(pid !=null ){
			selectPartmentID=Integer.parseInt(pid);
		}
		
		DepartmentDao dpDao = new DepartmentDao();
		List<Department> list_dp = null;
		
		Date PartmentTimeFenlei_fenxiangstart=new Date(),PartmentTimeFenlei_fenxiangend=new Date();
		PartmentTimeFenlei_fenxiangstart.setYear(PartmentTimeFenlei_start_year1-1900);
		PartmentTimeFenlei_fenxiangstart.setMonth(PartmentTimeFenlei_start_month1);
		PartmentTimeFenlei_fenxiangstart.setDate(1);
		PartmentTimeFenlei_fenxiangend.setYear(PartmentTimeFenlei_end_year1-1900);
		PartmentTimeFenlei_fenxiangend.setMonth(PartmentTimeFenlei_end_month1);
		PartmentTimeFenlei_fenxiangend.setDate(30);
		Date PartmentTimeFenlei_fenxiangstart1=new Date(),PartmentTimeFenlei_fenxiangend1=new Date();
		PartmentTimeFenlei_fenxiangstart1.setYear(PartmentTimeFenlei_start_year2-1900);
		PartmentTimeFenlei_fenxiangstart1.setMonth(PartmentTimeFenlei_start_month2);
		PartmentTimeFenlei_fenxiangstart1.setDate(1);
		PartmentTimeFenlei_fenxiangend1.setYear(PartmentTimeFenlei_end_year2-1900);
		PartmentTimeFenlei_fenxiangend1.setMonth(PartmentTimeFenlei_end_month2);
		PartmentTimeFenlei_fenxiangend1.setDate(30);
		Map<String, Float> PartmentTimeFenlei_fenxiangmap = null;
		Map<String, Float> PartmentTimeFenlei_fenxiangmap1 = null;
		Map<String, Float>  PartmentTimeFenlei_stylemap = null;
		Map<String, Float>  PartmentTimeFenlei_stylemap1 = null;
		float Value_fenxiang1 = 0;
		float Value_fenxiang2 = 0;
		float Value_fenxiang3 = 0;
		float Value_fenxiang4 = 0;
		float Value_fenxiang11 = 0;
		float Value_fenxiang22 = 0;
		float Value_fenxiang33 = 0;
		float Value_fenxiang44 = 0;
		float Value_style1 = 0;
		float Value_style2 = 0;
		float Value_style3 = 0;
		float Value_style4 = 0;
		float Value_style11 = 0;
		float Value_style22 = 0;
		float Value_style33 = 0;
		float Value_style44 = 0;
		WaterReportHelperImpl  rhi_PartmentTimeFenlei_fenxiang = new WaterReportHelperImpl();
		WaterReportHelperImpl  rhi_PartmentTimeFenlei_style = new WaterReportHelperImpl();
		
		if(selectPartmentID == -1||selectPartmentID ==0)
		{
			try {
				list_dp = dpDao.display(userID);//获取所有部门的ID信息
				for(int i = 0;i<list_dp.size();i++)
				{
					PartmentTimeFenlei_fenxiangmap = rhi_PartmentTimeFenlei_fenxiang.getGroupFenLeiCountBetween(list_dp.get(i).getDepartmentID(),PartmentTimeFenlei_fenxiangstart, PartmentTimeFenlei_fenxiangend);
					PartmentTimeFenlei_fenxiangmap1 = rhi_PartmentTimeFenlei_fenxiang.getGroupFenLeiCountBetween(list_dp.get(i).getDepartmentID(),PartmentTimeFenlei_fenxiangstart1, PartmentTimeFenlei_fenxiangend1);
					Value_fenxiang1+=PartmentTimeFenlei_fenxiangmap.get("食堂餐厅");
					Value_fenxiang2+=PartmentTimeFenlei_fenxiangmap.get("公共洗手间");
					Value_fenxiang3+=PartmentTimeFenlei_fenxiangmap.get("澡堂淋浴");
					Value_fenxiang4+=PartmentTimeFenlei_fenxiangmap.get("消防浇灌");
					Value_fenxiang11+=PartmentTimeFenlei_fenxiangmap1.get("食堂餐厅");
					Value_fenxiang22+=PartmentTimeFenlei_fenxiangmap1.get("公共洗手间");
					Value_fenxiang33+=PartmentTimeFenlei_fenxiangmap1.get("澡堂淋浴");
					Value_fenxiang44+=PartmentTimeFenlei_fenxiangmap1.get("消防浇灌");
					PartmentTimeFenlei_stylemap = rhi_PartmentTimeFenlei_style.getGroupStyleCountBetween(list_dp.get(i).getDepartmentID(),PartmentTimeFenlei_fenxiangstart, PartmentTimeFenlei_fenxiangend);
					PartmentTimeFenlei_stylemap1 = rhi_PartmentTimeFenlei_style.getGroupStyleCountBetween(list_dp.get(i).getDepartmentID(),PartmentTimeFenlei_fenxiangstart1, PartmentTimeFenlei_fenxiangend1);
					Value_style1+=PartmentTimeFenlei_stylemap.get("生活");
					Value_style2+=PartmentTimeFenlei_stylemap.get("教学");
					Value_style3+=PartmentTimeFenlei_stylemap.get("公共");
					Value_style4+=PartmentTimeFenlei_stylemap.get("商业");
					Value_style11+=PartmentTimeFenlei_stylemap1.get("生活");
					Value_style22+=PartmentTimeFenlei_stylemap1.get("教学");
					Value_style33+=PartmentTimeFenlei_stylemap1.get("公共");
					Value_style44+=PartmentTimeFenlei_stylemap1.get("商业");
				}
				PartmentTimeFenlei_fenxiangmap = new HashMap<String, Float>();
				PartmentTimeFenlei_fenxiangmap.put("食堂餐厅", Value_fenxiang1);
				PartmentTimeFenlei_fenxiangmap.put("公共洗手间", Value_fenxiang2);
				PartmentTimeFenlei_fenxiangmap.put("澡堂淋浴", Value_fenxiang3);
				PartmentTimeFenlei_fenxiangmap.put("消防浇灌", Value_fenxiang4);
				PartmentTimeFenlei_fenxiangmap1 = new HashMap<String, Float>();
				PartmentTimeFenlei_fenxiangmap1.put("食堂餐厅", Value_fenxiang11);
				PartmentTimeFenlei_fenxiangmap1.put("公共洗手间", Value_fenxiang22);
				PartmentTimeFenlei_fenxiangmap1.put("澡堂淋浴", Value_fenxiang33);
				PartmentTimeFenlei_fenxiangmap1.put("消防浇灌", Value_fenxiang44);
				PartmentTimeFenlei_stylemap = new HashMap<String, Float>();
				PartmentTimeFenlei_stylemap.put("生活", Value_style1);
				PartmentTimeFenlei_stylemap.put("教学", Value_style2);
				PartmentTimeFenlei_stylemap.put("公共", Value_style3);
				PartmentTimeFenlei_stylemap.put("商业", Value_style4);
				PartmentTimeFenlei_stylemap1 = new HashMap<String, Float>();
				PartmentTimeFenlei_stylemap1.put("生活", Value_style11);
				PartmentTimeFenlei_stylemap1.put("教学", Value_style22);
				PartmentTimeFenlei_stylemap1.put("公共", Value_style33);
				PartmentTimeFenlei_stylemap1.put("商业", Value_style44);
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		else if(selectPartmentID != -1&&selectPartmentID != 0)
		{
			try {
				PartmentTimeFenlei_fenxiangmap = rhi_PartmentTimeFenlei_fenxiang.getGroupFenLeiCountBetween(selectPartmentID,PartmentTimeFenlei_fenxiangstart, PartmentTimeFenlei_fenxiangend);
				PartmentTimeFenlei_fenxiangmap1 = rhi_PartmentTimeFenlei_fenxiang.getGroupFenLeiCountBetween(selectPartmentID,PartmentTimeFenlei_fenxiangstart1, PartmentTimeFenlei_fenxiangend1);
				PartmentTimeFenlei_stylemap = rhi_PartmentTimeFenlei_style.getGroupStyleCountBetween(selectPartmentID,PartmentTimeFenlei_fenxiangstart, PartmentTimeFenlei_fenxiangend);
				PartmentTimeFenlei_stylemap1 = rhi_PartmentTimeFenlei_style.getGroupStyleCountBetween(selectPartmentID,PartmentTimeFenlei_fenxiangstart1, PartmentTimeFenlei_fenxiangend1);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Gson gson_PartmentTimeFenlei_style = new Gson();
		String PartmentTimeFenlei_fenxiangdata = gson_PartmentTimeFenlei_style.toJson(new Object[]
					{PartmentTimeFenlei_fenxiangmap,PartmentTimeFenlei_fenxiangmap1,
						PartmentTimeFenlei_stylemap,PartmentTimeFenlei_stylemap1});
		//System.out.println(PartmentTimeFenlei_fenxiangdata);
		PrintWriter out = response.getWriter();
		out.println(PartmentTimeFenlei_fenxiangdata);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
}
