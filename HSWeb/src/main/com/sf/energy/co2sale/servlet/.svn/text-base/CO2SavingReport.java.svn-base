package com.sf.energy.co2sale.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.co2sale.impl.CO2SavingReportDao;
import com.sf.energy.co2sale.model.CO2CalculatorSavingReportModel;
import com.sf.energy.project.system.dao.DepartmentDao;

public class CO2SavingReport extends HttpServlet
{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);
		if ("saveReport".equals(method))
			saveReport(request, response);
		if ("showReportList".equals(method))
			showReportList(request, response);
		if ("showSavingReportByID".equals(method))
			showSavingReportByID(request, response);
		if ("deleteSavingReportByID".equals(method))
			deleteSavingReportByID(request, response);
		
	}
	private void deleteSavingReportByID(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SavingReportDao cos=new CO2SavingReportDao();
		int CO2Report_id = 0;
		if (request.getParameter("CO2Report_id") != null
				&& request.getParameter("CO2Report_id") != "") {
			CO2Report_id = Integer.parseInt(request.getParameter("CO2Report_id"));
		}
		String msg =cos.delete(CO2Report_id);
		String resultInfo="";
		if ("success".equals(msg))
		{
			resultInfo = "删除成功！";
			// 写入日志
		} else if ("fail".equals(msg))
		{
			resultInfo = "删除失败！";
		} else
		{
			resultInfo = msg;
		}
		PrintWriter out = response.getWriter();
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	
	private void showSavingReportByID(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2CalculatorSavingReportModel co2SR=new CO2CalculatorSavingReportModel();
		DepartmentDao dp=new DepartmentDao();
		CO2SavingReportDao cos=new CO2SavingReportDao();
		CO2CalculatorSavingReportModel co2=new CO2CalculatorSavingReportModel();
		Calendar year_cal = Calendar.getInstance();
		int CO2Report_id = 0;
		if (request.getParameter("CO2Report_id") != null
				&& request.getParameter("CO2Report_id") != "") {
			CO2Report_id = Integer.parseInt(request.getParameter("CO2Report_id"));
		}
		co2=cos.getCO2SavingReportListBySavingReportID(CO2Report_id);
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(co2);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	private void showReportList(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2CalculatorSavingReportModel co2SR=new CO2CalculatorSavingReportModel();
		DepartmentDao dp=new DepartmentDao();
		CO2SavingReportDao cos=new CO2SavingReportDao();
		List<CO2CalculatorSavingReportModel> lists=new ArrayList<CO2CalculatorSavingReportModel>();
		Calendar year_cal = Calendar.getInstance();
		int year = year_cal.get(Calendar.YEAR);
		int month=year_cal.get(Calendar.MONTH)+1;
		int partid = 0;
		if (request.getParameter("partid") != null
				&& request.getParameter("partid") != "") {
			partid = Integer.parseInt(request.getParameter("partid"));
		}
		if (request.getParameter("year") != null
				&& request.getParameter("year") != "") {
			year = Integer.parseInt(request.getParameter("year"));
		}
		if (request.getParameter("month") != null
				&& request.getParameter("month") != "") {
			month = Integer.parseInt(request.getParameter("month"));
		}
		if(partid==0){
			lists=cos.getAllCO2SavingReportListByPartid(year, month);
		}else{
			lists=cos.getCO2SavingReportListByPartid(partid, year, month);
		}
		
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(new Object[] {lists});
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	private void saveReport(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2CalculatorSavingReportModel co2SR=new CO2CalculatorSavingReportModel();
		DepartmentDao dp=new DepartmentDao();
		CO2SavingReportDao cos=new CO2SavingReportDao();
		Calendar year_cal = Calendar.getInstance();
		int year = year_cal.get(Calendar.YEAR);
		int month=year_cal.get(Calendar.MONTH)+1;
		int partid = 0;
		if (request.getParameter("partid") != null
				&& request.getParameter("partid") != "") {
			partid = Integer.parseInt(request.getParameter("partid"));
		}
		if (request.getParameter("year") != null
				&& request.getParameter("year") != "") {
			year = Integer.parseInt(request.getParameter("year"));
		}
		if (request.getParameter("month") != null
				&& request.getParameter("month") != "") {
			month = Integer.parseInt(request.getParameter("month"));
		}
		co2SR.setYear(year);
		co2SR.setMonth(month);
		co2SR.setPartid(partid);
		co2SR.setPartName(dp.queryNameById(partid));
		co2SR.setTotalQuota(Float.parseFloat(request.getParameter("totalQuota")));
		co2SR.setCurrentPaiFang(Float.parseFloat(request.getParameter("currentPaiFang")));
		co2SR.setCurrentXiShou(Float.parseFloat(request.getParameter("currentXiShou")));
		co2SR.setCurrentRemain(Float.parseFloat(request.getParameter("currentRemain")));
		co2SR.setTotalEnergyQuota(Float.parseFloat(request.getParameter("totalEnergyQuota")));
		co2SR.setCurrentUsedEnergy(Float.parseFloat(request.getParameter("currentUsedEnergy")));
		co2SR.setPlanSavingEnergy(Float.parseFloat(request.getParameter("planSavingEnergy")));
		co2SR.setTotalOilQuota(Float.parseFloat(request.getParameter("totalOilQuota")));
		co2SR.setCurrentUsedOil(Float.parseFloat(request.getParameter("currentUsedOil")));
		co2SR.setPlanSavingOil(Float.parseFloat(request.getParameter("planSavingOil")));
		co2SR.setCurrentStyle_1(Float.parseFloat(request.getParameter("currentStyle_1")));
		co2SR.setCurrentStyle_2(Float.parseFloat(request.getParameter("currentStyle_2")));
		co2SR.setCurrentStyle_3(Float.parseFloat(request.getParameter("currentStyle_3")));
		co2SR.setCurrentStyle_4(Float.parseFloat(request.getParameter("currentStyle_4")));
		co2SR.setCurrentStyle_5(Float.parseFloat(request.getParameter("currentStyle_5")));
		co2SR.setPlanAddStyle_1(Float.parseFloat(request.getParameter("planAddStyle_1")));
		co2SR.setPlanAddStyle_2(Float.parseFloat(request.getParameter("planAddStyle_2")));
		co2SR.setPlanAddStyle_3(Float.parseFloat(request.getParameter("planAddStyle_3")));
		co2SR.setPlanAddStyle_4(Float.parseFloat(request.getParameter("planAddStyle_4")));
		co2SR.setPlanAddStyle_5(Float.parseFloat(request.getParameter("planAddStyle_5")));
		co2SR.setCurrentAdd_1(Float.parseFloat(request.getParameter("currentAdd_1")));
		co2SR.setCurrentAdd_2(Float.parseFloat(request.getParameter("currentAdd_2")));
		co2SR.setCurrentAdd_3(Float.parseFloat(request.getParameter("currentAdd_3")));
		co2SR.setCurrentAdd_4(Float.parseFloat(request.getParameter("currentAdd_4")));
		co2SR.setPlanAddAdd_1(Float.parseFloat(request.getParameter("planAddAdd_1")));
		co2SR.setPlanAddAdd_2(Float.parseFloat(request.getParameter("planAddAdd_2")));
		co2SR.setPlanAddAdd_3(Float.parseFloat(request.getParameter("planAddAdd_3")));
		co2SR.setPlanAddAdd_4(Float.parseFloat(request.getParameter("planAddAdd_4")));
		co2SR.setAfterSavingRemainQuota(Float.parseFloat(request.getParameter("afterSavingRemainQuota")));
		String resultInfo="";
		
		try
		{
			String msg = cos.insert(co2SR);
			if ("success".equals(msg))
			{
				resultInfo = "添加成功！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
}
