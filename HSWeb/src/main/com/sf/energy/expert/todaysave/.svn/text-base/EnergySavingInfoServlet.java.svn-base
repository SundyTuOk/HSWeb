package com.sf.energy.expert.todaysave;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.TargetAmDao;
import com.sf.energy.expert.dao.TargetWaterDao;
import com.sf.energy.expert.model.TargetAmModel;
import com.sf.energy.expert.model.TargetWaterModel;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class EnergySavingInfoServlet extends HttpServlet
{

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		int AreaID = -1;
		int ArcID = -1;

		if (request.getParameter("AreaID") != null && request.getParameter("AreaID") != "")
		{
			AreaID = Integer.parseInt(request.getParameter("AreaID"));
			// //System.out.println("区域ID"+AreaID);
		}
		if (request.getParameter("ArcID") != null && request.getParameter("ArcID") != "")
		{
			ArcID = Integer.parseInt(request.getParameter("ArcID"));
			// //System.out.println("建筑ID"+ArcID);
		}

		if (ArcID != -1) // 选了指定区域下的某个建筑
		{
			/** 两月用电数据 **/
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List ComplexInfo_TwoMon_list1 = new ArrayList();
			for (int i = (ComplexInfo_TwoMon_month); i <= (ComplexInfo_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getArcCountEveryDay(ArcID, ComplexInfo_TwoMon_year-1, 12);
					}else{
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getArcCountEveryDay(ArcID, ComplexInfo_TwoMon_year, i);
					}
					
				} catch (SQLException | ParseException e)
				{
					e.printStackTrace();
				}
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}
			/** 两月用电数据 **/

			/** 两月用水数据 **/
			WaterReportHelperImpl rhi_Water_TwoMon = new WaterReportHelperImpl();
			Calendar Water_TwoMon_cal = Calendar.getInstance();
			int Water_TwoMon_year = Water_TwoMon_cal.get(Calendar.YEAR);
			int Water_TwoMon_month = Water_TwoMon_cal.get(Calendar.MONTH);
			List<WaterReportModel> Water_TwoMon_list = null;
			List Water_TwoMon_list1 = new ArrayList();
			for (int i = Water_TwoMon_month; i <= (Water_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						Water_TwoMon_list = rhi_Water_TwoMon.getArcCountEveryDay(ArcID, Water_TwoMon_year-1, 12);
					}else{
						Water_TwoMon_list = rhi_Water_TwoMon.getArcCountEveryDay(ArcID, Water_TwoMon_year, i);
					}
					
				} catch (SQLException | ParseException e)
				{
					e.printStackTrace();
				}
				Water_TwoMon_list1.add(Water_TwoMon_list);
			}
			/** 两月用水数据 **/

			/** 节电目标数据 **/
			Calendar savecal = Calendar.getInstance();
			int year = savecal.get(Calendar.YEAR);
			ElecReportHelperImpl SaveAm = new ElecReportHelperImpl();
			TargetAmDao TargetAm = new TargetAmDao();
			TargetAmModel tarAm = null;
			List<ReportModel> totalAm = null;
			List SaveAmlist = new ArrayList();
			try
			{
				totalAm = SaveAm.getArcCountEveryMonth(ArcID, year);
				tarAm = TargetAm.queryByIndex(ArcID + "", 3, year, "用电预测(总)");
				SaveAmlist.add(totalAm);
				SaveAmlist.add(tarAm);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节电目标数据 **/

			/** 节水目标数据 **/
			WaterReportHelperImpl SaveWater = new WaterReportHelperImpl();
			TargetAmDao TargetWater = new TargetAmDao();
			TargetAmModel tarWater = null;
			List<WaterReportModel> totalWater = null;
			List SaveWaterlist = new ArrayList();
			try
			{
				totalWater = SaveWater.getArcCountEveryMonth(ArcID, year);
				tarWater = TargetWater.queryByIndex(ArcID + "", 3, year, "用水预测(总)");
				SaveWaterlist.add(totalWater);
				SaveWaterlist.add(tarWater);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节水目标数据 **/

			Gson gson_ComplexInfo_TwoMon = new Gson();
			String ComplexInfo_TwoMondata = gson_ComplexInfo_TwoMon.toJson(new Object[]
			{ ComplexInfo_TwoMon_list1, Water_TwoMon_list1, SaveAmlist, SaveWaterlist });
			// //System.out.println(ComplexInfo_TwoMondata);
			out.println(ComplexInfo_TwoMondata);
		} else if (AreaID != -1 && ArcID == -1) // 选了某个区域下的所有建筑
		{
			// System.out.println("区域");
			/** 两月用电数据 **/
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List ComplexInfo_TwoMon_list1 = new ArrayList();
			for (int i = ComplexInfo_TwoMon_month; i <= (ComplexInfo_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAreaCountEveryDay(AreaID, ComplexInfo_TwoMon_year-1, 12);
					}else{
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAreaCountEveryDay(AreaID, ComplexInfo_TwoMon_year, i);
					}
					
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}
			/** 两月用电数据 **/

			/** 两月用水数据 **/
			WaterReportHelperImpl rhi_Water_TwoMon = new WaterReportHelperImpl();
			Calendar Water_TwoMon_cal = Calendar.getInstance();
			int Water_TwoMon_year = Water_TwoMon_cal.get(Calendar.YEAR);
			int Water_TwoMon_month = Water_TwoMon_cal.get(Calendar.MONTH);
			List<WaterReportModel> Water_TwoMon_list = null;
			List Water_TwoMon_list1 = new ArrayList();
			for (int i = Water_TwoMon_month; i <= (Water_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						Water_TwoMon_list = rhi_Water_TwoMon.getAreaCountEveryDay(AreaID, Water_TwoMon_year-1, 12);
					}else{
						Water_TwoMon_list = rhi_Water_TwoMon.getAreaCountEveryDay(AreaID, Water_TwoMon_year, i);
					}
					
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				Water_TwoMon_list1.add(Water_TwoMon_list);
			}
			/** 两月用水数据 **/

			/** 节电目标数据 **/
			Calendar savecal = Calendar.getInstance();
			int year = savecal.get(Calendar.YEAR);
			ElecReportHelperImpl SaveAm = new ElecReportHelperImpl();
			TargetAmDao TargetAm = new TargetAmDao();
			TargetAmModel tarAm = null;
			List<ReportModel> totalAm = null;
			List SaveAmlist = new ArrayList();
			try
			{
				totalAm = SaveAm.getAreaCountEveryMonth(AreaID, year);
				tarAm = TargetAm.queryByIndex(AreaID + "", 4, year, "用电预测(总)");
				SaveAmlist.add(totalAm);
				SaveAmlist.add(tarAm);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节电目标数据 **/

			/** 节水目标数据 **/
			WaterReportHelperImpl SaveWater = new WaterReportHelperImpl();
			TargetAmDao TargetWater = new TargetAmDao();
			TargetAmModel tarWater = null;
			List<WaterReportModel> totalWater = null;
			List SaveWaterlist = new ArrayList();
			try
			{
				totalWater = SaveWater.getAreaCountEveryMonth(AreaID, year);
				tarWater = TargetWater.queryByIndex(AreaID + "", 4, year, "用水预测(总)");
				SaveWaterlist.add(totalWater);
				SaveWaterlist.add(tarWater);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节水目标数据 **/

			Gson gson_ComplexInfo_TwoMon = new Gson();
			String ComplexInfo_TwoMondata = gson_ComplexInfo_TwoMon.toJson(new Object[]
			{ ComplexInfo_TwoMon_list1, Water_TwoMon_list1, SaveAmlist, SaveWaterlist });
			// //System.out.println(ComplexInfo_TwoMondata);
			out.println(ComplexInfo_TwoMondata);
		} else if (AreaID == -1 && ArcID == -1) // 选了全校所有区域的情形
		{
			// System.out.println("全校");
			/** 两月用电数据 **/
			ElecReportHelperImpl rhi_ComplexInfo_TwoMon = new ElecReportHelperImpl();
			Calendar ComplexInfo_TwoMon_cal = Calendar.getInstance();
			int ComplexInfo_TwoMon_year = ComplexInfo_TwoMon_cal.get(Calendar.YEAR);
			int ComplexInfo_TwoMon_month = ComplexInfo_TwoMon_cal.get(Calendar.MONTH);
			List<ReportModel> ComplexInfo_TwoMon_list = null;
			List ComplexInfo_TwoMon_list1 = new ArrayList();
			for (int i = ComplexInfo_TwoMon_month; i <= (ComplexInfo_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAllAreaCountEveryDay(ComplexInfo_TwoMon_year-1, 12);
					}else{
						ComplexInfo_TwoMon_list = rhi_ComplexInfo_TwoMon.getAllAreaCountEveryDay(ComplexInfo_TwoMon_year, i);
					}
					
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				ComplexInfo_TwoMon_list1.add(ComplexInfo_TwoMon_list);
			}
			/** 两月用电数据 **/

			/** 两月用水数据 **/
			WaterReportHelperImpl rhi_Water_TwoMon = new WaterReportHelperImpl();
			Calendar Water_TwoMon_cal = Calendar.getInstance();
			int Water_TwoMon_year = Water_TwoMon_cal.get(Calendar.YEAR);
			int Water_TwoMon_month = Water_TwoMon_cal.get(Calendar.MONTH);
			List<WaterReportModel> Water_TwoMon_list = null;
			List Water_TwoMon_list1 = new ArrayList();
			for (int i = Water_TwoMon_month; i <= (Water_TwoMon_month + 1); i++)
			{

				try
				{
					if(i==0){
						Water_TwoMon_list = rhi_Water_TwoMon.getAllAreaCountEveryDay(Water_TwoMon_year-1, 12);
					}else{
						Water_TwoMon_list = rhi_Water_TwoMon.getAllAreaCountEveryDay(Water_TwoMon_year, i);
					}
					
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				Water_TwoMon_list1.add(Water_TwoMon_list);
			}
			/** 两月用水数据 **/

			/** 节电目标数据 **/
			Calendar savecal = Calendar.getInstance();
			int year = savecal.get(Calendar.YEAR);
			ElecReportHelperImpl SaveAm = new ElecReportHelperImpl();
			TargetAmDao TargetAm = new TargetAmDao();
			TargetAmModel tarAm = null;
			List<ReportModel> totalAm = null;
			List SaveAmlist = new ArrayList();
			try
			{
				totalAm = SaveAm.getAllAreaCountEveryMonth(year);
				tarAm = TargetAm.queryByIndex(0 + "", 1, year, "用电预测(总)");
				SaveAmlist.add(totalAm);
				SaveAmlist.add(tarAm);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节电目标数据 **/

			/** 节水目标数据 **/
			WaterReportHelperImpl SaveWater = new WaterReportHelperImpl();
			TargetWaterDao TargetWater = new TargetWaterDao();
			TargetWaterModel tarWater = null;
			List<WaterReportModel> totalWater = null;
			List SaveWaterlist = new ArrayList();
			try
			{
				totalWater = SaveWater.getAllAreaCountEveryMonth(year);
				tarWater = TargetWater.queryByIndex(0 + "", 1, year, "用水预测(总)");
				SaveWaterlist.add(totalWater);
				SaveWaterlist.add(tarWater);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			/** 节水目标数据 **/

			Gson gson_ComplexInfo_TwoMon = new Gson();
			String ComplexInfo_TwoMondata = gson_ComplexInfo_TwoMon.toJson(new Object[]
			{ ComplexInfo_TwoMon_list1, Water_TwoMon_list1, SaveAmlist, SaveWaterlist });
			// System.out.println(ComplexInfo_TwoMondata);
			out.println(ComplexInfo_TwoMondata);
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
}
