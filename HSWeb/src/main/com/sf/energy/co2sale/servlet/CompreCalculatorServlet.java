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
import com.sf.energy.co2sale.impl.CO2CalculatorDao;
import com.sf.energy.co2sale.impl.CO2QuotaManageDao;
import com.sf.energy.co2sale.model.CO2CalculatorPaiFangModel;
import com.sf.energy.co2sale.model.CO2CalculatorSavingAdditionalModel;
import com.sf.energy.co2sale.model.CO2CalculatorSavingReportModel;
import com.sf.energy.co2sale.model.CO2CalculatorXiShouModel;
import com.sf.energy.co2sale.model.CO2IndexModel;
import com.sf.energy.co2sale.model.CO2WarningModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

/**
 * @author xiaoh
 * 
 */
public class CompreCalculatorServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CO2CalculatorDao ca=new CO2CalculatorDao();
		CO2QuotaManageDao caoDao=new CO2QuotaManageDao();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int partid = 0;
		double totalQuota=0;
		double totalEnergyQuota=0;
		double currentUsedEnergy=0;
		double currentUsedOil=0;
		Calendar year_cal = Calendar.getInstance();
		int year = year_cal.get(Calendar.YEAR);
		if (request.getParameter("partid") != null
				&& request.getParameter("partid") != "") {
			partid = Integer.parseInt(request.getParameter("partid"));
		}
		if (request.getParameter("year") != null
				&& request.getParameter("year") != "") {
			year = Integer.parseInt(request.getParameter("year"));
		}
		List<CO2CalculatorPaiFangModel> CO2PaiFang_list = new ArrayList<CO2CalculatorPaiFangModel>();
//		List<CO2CalculatorXiShouModel> CO2XiShou_list = new ArrayList<CO2CalculatorXiShouModel>();
//		List<CO2CalculatorSavingAdditionalModel> cO2SavingAdditional_list = new ArrayList<CO2CalculatorSavingAdditionalModel>();
		List<CO2CalculatorSavingReportModel> CO2SavingReport_list=new ArrayList<CO2CalculatorSavingReportModel>();
		CO2IndexModel CO2Index=new CO2IndexModel();
		/** 二氧化碳排放量 **/
		try
		{
			CO2PaiFang_list=ca.getCalculatorPaiFangByPartID(partid,year);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		/** 二氧化碳排放量 **/

		/** 二氧化碳吸收量 及能源附加项**/
		try
		{
			CO2Index=ca.getCalculatorXiShouByPartID(partid, year);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** 二氧化碳吸收量 及能源附加项**/
		/** 获取总定额 **/
		if(partid==0){
			try
			{
				totalQuota=caoDao.getAllQuotaInfoByYearDeptID(year+"");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try
			{
				totalQuota=caoDao.getQuotaInfoByYearDeptID(partid+"", year+"");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/** 获取总定额 **/
		/** 获取总用电定额 **/
		if(partid==0){
			try
			{
				totalEnergyQuota=caoDao.getAllEnergyQuotaInfoByYearDeptID(year+"");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try
			{
				totalEnergyQuota=caoDao.getEnergyQuotaInfoByYearDeptID(partid+"", year+"");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/** 获取总用电定额 **/
		/** 获取当前总用电 **/
		
		if(partid==0){
			try
			{
				currentUsedEnergy=caoDao.getAllCurrentUsedEnergyByYear(year);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try
			{
				currentUsedEnergy=caoDao.getCurrentUsedEnergyByYearDeptID(partid, year);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/** 获取当前总用电 **/
		/** 获取总用油定额 **/
		
		double totalOilQuota=0;
		
		/** 获取总用油定额 **/
		
		
		Gson gson_Supervision = new Gson();
		String SupervisionInfo = gson_Supervision.toJson(new Object[] {
				CO2PaiFang_list, CO2Index,totalQuota,totalEnergyQuota,currentUsedEnergy,totalOilQuota,currentUsedOil});
		// System.out.println(SupervisionInfo);
		out.println(SupervisionInfo);
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
