package com.sf.energy.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.configuration.Configuration;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class EnergyGeneralInfoServlert extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		try
		{
			EnergyGeneralInfoImpl(request, response);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void EnergyGeneralInfoImpl(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out = response.getWriter();
		int areaID = -1;
		int archID = -1;
		int flag = 0; // 标记是全校，区域，建筑

		if (request.getParameter("areaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
			// System.out.println("区域ID"+areaID);
		}
		if (request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
			// System.out.println("建筑ID"+archID);
		}

		if (areaID == -1)
		{
			flag = 1;
			// System.out.println("全校");
			Gson gson = new Gson();

			ElecReportHelperImpl rhi_ComplexInfo_Ammeter = new ElecReportHelperImpl();
			Map<String, Integer> ComplexInfo_Ammeter_m = rhi_ComplexInfo_Ammeter.getAmmeterCount();

			// 获得指定年份所有部门用电排名数据（按从大到小顺序）
			ElecReportHelperImpl rhi_ComplexInfo_PaiMing = new ElecReportHelperImpl();
			Calendar ComplexInfo_PaiMing_cal = Calendar.getInstance();
			int ComplexInfo_PaiMing_year = ComplexInfo_PaiMing_cal.get(Calendar.YEAR);
			List<ReportModel> list_paiMing = rhi_ComplexInfo_PaiMing.getAllGroupCountListDisc(ComplexInfo_PaiMing_year);

			// 获得指定区域当前年份用电分项电量
			ElecReportHelperImpl rhi_ComplexInfo_FenXiang = new ElecReportHelperImpl();
			Calendar ComplexInfo_FenXiang_cal = Calendar.getInstance();
			int ComplexInfo_FenXiang_year = ComplexInfo_FenXiang_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_FenXiang_map = rhi_ComplexInfo_FenXiang.getAllSchoolFenLeiCountByYearInList(ComplexInfo_FenXiang_year);

			// 获得指定区域当前年份用电性质电量
			ElecReportHelperImpl rhi_ComplexInfo_Style = new ElecReportHelperImpl();
			Calendar ComplexInfo_Style_cal = Calendar.getInstance();
			int ComplexInfo_Style_year = ComplexInfo_Style_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_Style_map = rhi_ComplexInfo_Style.getAllAreaStyleCountByYearInList(ComplexInfo_Style_year);

			// 获得指定区域当前年份里近两个月内每一天的电量
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List<List<ReportModel>> ComplexInfo_TwoMon_list1 = new ArrayList<List<ReportModel>>();
			for (int i = (ComplexInfo_TwoMon_month); i <= (ComplexInfo_TwoMon_month + 1); i++)
			{
				if(i==0){
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAllAreaCountEveryDay(ComplexInfo_TwoMon_year-1, 12);
				}else{
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAllAreaCountEveryDay(ComplexInfo_TwoMon_year, i);
				}
				
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}

			String data = gson.toJson(new Object[]
			{ flag, ComplexInfo_Ammeter_m, list_paiMing, ComplexInfo_FenXiang_map, ComplexInfo_Style_map, ComplexInfo_TwoMon_list1 });
			//System.out.println(data);
			out.println(data);
		} else if (areaID != -1 && archID == -1)
		{
			flag = 2;
			// System.out.println("区域");
			Gson gson = new Gson();

			ElecReportHelperImpl rhi_ComplexInfo_Ammeter = new ElecReportHelperImpl();
			Map<String, Integer> ComplexInfo_Ammeter_m = rhi_ComplexInfo_Ammeter.getAmmeterCountByArea(areaID);

			// 获得指定年份所有部门用电排名数据（按从大到小顺序）
			ElecReportHelperImpl rhi_ComplexInfo_PaiMing = new ElecReportHelperImpl();
			Calendar ComplexInfo_PaiMing_cal = Calendar.getInstance();
			int ComplexInfo_PaiMing_year = ComplexInfo_PaiMing_cal.get(Calendar.YEAR);
			List<ReportModel> list_paiMing = rhi_ComplexInfo_PaiMing.getAllGroupCountListDisc(ComplexInfo_PaiMing_year);

			// 获得指定区域当前年份用电分项电量
			ElecReportHelperImpl rhi_ComplexInfo_FenXiang = new ElecReportHelperImpl();
			Calendar ComplexInfo_FenXiang_cal = Calendar.getInstance();
			int ComplexInfo_FenXiang_year = ComplexInfo_FenXiang_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_FenXiang_map = rhi_ComplexInfo_FenXiang.getAreaFenLeiCountByYearInList(areaID, ComplexInfo_FenXiang_year);

			// 获得指定区域当前年份用电性质电量
			ElecReportHelperImpl rhi_ComplexInfo_Style = new ElecReportHelperImpl();
			Calendar ComplexInfo_Style_cal = Calendar.getInstance();
			int ComplexInfo_Style_year = ComplexInfo_Style_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_Style_map = rhi_ComplexInfo_Style.getAreaStyleCountByYearInList(areaID, ComplexInfo_Style_year);

			// 获得指定区域当前年份里近两个月内每一天的电量
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List<List<ReportModel>> ComplexInfo_TwoMon_list1 = new ArrayList<List<ReportModel>>();
			for (int i = (ComplexInfo_TwoMon_month); i <= (ComplexInfo_TwoMon_month + 1); i++)
			{
				if(i==0){
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAreaCountEveryDay(areaID, ComplexInfo_TwoMon_year-1, 12);
				}else{
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAreaCountEveryDay(areaID, ComplexInfo_TwoMon_year, i);
				}
				
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}

			String data = gson.toJson(new Object[]
			{ flag, ComplexInfo_Ammeter_m, list_paiMing, ComplexInfo_FenXiang_map, ComplexInfo_Style_map, ComplexInfo_TwoMon_list1 });
			//System.out.println(data);
			out.println(data);

		} else if (areaID != -1 && archID != -1)
		{
			flag = 3;
			// System.out.println("建筑");
			Gson gson = new Gson();

			ElecReportHelperImpl rhi_ComplexInfo_Ammeter = new ElecReportHelperImpl();
			Map<String, Integer> ComplexInfo_Ammeter_m = rhi_ComplexInfo_Ammeter.getAmmeterCountByArcOnly(archID);

			// 获得指定年份所有部门用电排名数据（按从大到小顺序）
			ElecReportHelperImpl rhi_ComplexInfo_PaiMing = new ElecReportHelperImpl();
			Calendar ComplexInfo_PaiMing_cal = Calendar.getInstance();
			int ComplexInfo_PaiMing_year = ComplexInfo_PaiMing_cal.get(Calendar.YEAR);
			List<ReportModel> list_paiMing = rhi_ComplexInfo_PaiMing.getAllGroupCountListDisc(ComplexInfo_PaiMing_year);

			// 获得指定建筑当前年份用电分项电量
			ElecReportHelperImpl rhi_ComplexInfo_FenXiang = new ElecReportHelperImpl();
			Calendar ComplexInfo_FenXiang_cal = Calendar.getInstance();
			int ComplexInfo_FenXiang_year = ComplexInfo_FenXiang_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_FenXiang_map = rhi_ComplexInfo_FenXiang.getArcFenLeiCountByYearInList(archID, ComplexInfo_FenXiang_year);

			// 获得指定建筑当前年份用电性质电量
			ElecReportHelperImpl rhi_ComplexInfo_Style = new ElecReportHelperImpl();
			Calendar ComplexInfo_Style_cal = Calendar.getInstance();
			int ComplexInfo_Style_year = ComplexInfo_Style_cal.get(Calendar.YEAR);
			List<ReportModel> ComplexInfo_Style_map = rhi_ComplexInfo_Style.getArcStyleCountByYearInList(archID, ComplexInfo_Style_year);

			// 获得指定建筑当前年份里近两个月内每一天的电量
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List<List<ReportModel>> ComplexInfo_TwoMon_list1 = new ArrayList<List<ReportModel>>();
			for (int i = ComplexInfo_TwoMon_month; i <= (ComplexInfo_TwoMon_month + 1); i++)
			{
				if(i==0){
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getArcCountEveryDay(archID, ComplexInfo_TwoMon_year-1, 12);
				}else{
					ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getArcCountEveryDay(archID, ComplexInfo_TwoMon_year, i);
				}
				
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}

			String data = gson.toJson(new Object[]
			{ flag, ComplexInfo_Ammeter_m, list_paiMing, ComplexInfo_FenXiang_map, ComplexInfo_Style_map, ComplexInfo_TwoMon_list1 });
			//System.out.println(data);
			out.println(data);

		}

		out.flush();
		out.close();
	}

}
