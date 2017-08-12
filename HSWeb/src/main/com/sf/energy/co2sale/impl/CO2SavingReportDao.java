package com.sf.energy.co2sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sf.energy.co2sale.model.CO2CalculatorSavingReportModel;
import com.sf.energy.util.ConnDB;

public class CO2SavingReportDao
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	public String getSchoolName() throws SQLException{
		String schoolName="";
		String sql = "select SystemInfo_ComplayName from SystemInfo where rownum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if(rs.next())
			{
				schoolName = rs.getString("SystemInfo_ComplayName");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		Statement sm = null;
//		sm = ConnDB.getConnection().createStatement(
//				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = null;
//		rs = sm.executeQuery(sql);
//		if(rs.next())
//		{
//			schoolName = rs.getString("SystemInfo_ComplayName");
//		}
		return schoolName;
	}
	public String insert(CO2CalculatorSavingReportModel co) throws SQLException{
		
		String returnMsg="";
		String sql="INSERT INTO CO2SAVINGREPORT (PARTMENT_ID, PARTMENT_NAME, CURRENT_YEAR, CURRENT_MONTH, REPORT_TIME, TOTALQUOTA, CURRENTPAIFANG, CURRENTXISHOU, TOTALENERGYQUOTA, "
				+ " CURRENTUSEDENERGY, PLANSAVINGENERGY, TOTALOILQUOTA, CURRENTUSEDOIL, PLANSAVINGOIL, CURRENTSTYLE_1, CURRENTSTYLE_2, CURRENTSTYLE_3, CURRENTSTYLE_4, CURRENTSTYLE_5, "
				+ " PLANADDSTYLE_1, PLANADDSTYLE_2, PLANADDSTYLE_3, PLANADDSTYLE_4, PLANADDSTYLE_5, "
				+ " CURRENTADD_1, CURRENTADD_2, CURRENTADD_3, CURRENTADD_4, PLANADD_1, PLANADD_2, PLANADD_3, PLANADD_4, AFTERSAVINGREMAINQUOTA) "
				+ " VALUES (?, ?, ?, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			ps.setInt(1, co.getPartid());
			if(co.getPartid()==0){
				ps.setString(2, getSchoolName());
			}else{
				ps.setString(2, co.getPartName());
			}
			ps.setInt(3, co.getYear());
			ps.setInt(4, co.getMonth());
			ps.setString(5, df.format(new Date()));
			ps.setFloat(6, co.getTotalQuota());
			ps.setFloat(7, co.getCurrentPaiFang());
			ps.setFloat(8, co.getCurrentXiShou());
			ps.setFloat(9, co.getTotalEnergyQuota());
			ps.setFloat(10, co.getCurrentUsedEnergy());
			ps.setFloat(11, co.getPlanSavingEnergy());
			ps.setFloat(12, co.getTotalOilQuota());
			ps.setFloat(13, co.getCurrentUsedOil());
			ps.setFloat(14, co.getPlanSavingOil());
			ps.setFloat(15, co.getCurrentStyle_1());
			ps.setFloat(16, co.getCurrentStyle_2());
			ps.setFloat(17, co.getCurrentStyle_3());
			ps.setFloat(18, co.getCurrentStyle_4());
			ps.setFloat(19, co.getCurrentStyle_5());
			ps.setFloat(20, co.getPlanAddStyle_1());
			ps.setFloat(21, co.getPlanAddStyle_2());
			ps.setFloat(22, co.getPlanAddStyle_3());
			ps.setFloat(23, co.getPlanAddStyle_4());
			ps.setFloat(24, co.getPlanAddStyle_5());
			ps.setFloat(25, co.getCurrentAdd_1());
			ps.setFloat(26, co.getCurrentAdd_2());
			ps.setFloat(27, co.getCurrentAdd_3());
			ps.setFloat(28, co.getCurrentAdd_4());
			ps.setFloat(29, co.getPlanAddAdd_1());
			ps.setFloat(30, co.getPlanAddAdd_2());
			ps.setFloat(31, co.getPlanAddAdd_3());
			ps.setFloat(32, co.getPlanAddAdd_4());
			ps.setFloat(33, co.getAfterSavingRemainQuota());			
			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		
//		ps.setInt(1, co.getPartid());
//		if(co.getPartid()==0){
//			ps.setString(2, getSchoolName());
//		}else{
//			ps.setString(2, co.getPartName());
//		}
//		ps.setInt(3, co.getYear());
//		ps.setInt(4, co.getMonth());
//		ps.setString(5, df.format(new Date()));
//		ps.setFloat(6, co.getTotalQuota());
//		ps.setFloat(7, co.getCurrentPaiFang());
//		ps.setFloat(8, co.getCurrentXiShou());
//		ps.setFloat(9, co.getTotalEnergyQuota());
//		ps.setFloat(10, co.getCurrentUsedEnergy());
//		ps.setFloat(11, co.getPlanSavingEnergy());
//		ps.setFloat(12, co.getTotalOilQuota());
//		ps.setFloat(13, co.getCurrentUsedOil());
//		ps.setFloat(14, co.getPlanSavingOil());
//		ps.setFloat(15, co.getCurrentStyle_1());
//		ps.setFloat(16, co.getCurrentStyle_2());
//		ps.setFloat(17, co.getCurrentStyle_3());
//		ps.setFloat(18, co.getCurrentStyle_4());
//		ps.setFloat(19, co.getCurrentStyle_5());
//		ps.setFloat(20, co.getPlanAddStyle_1());
//		ps.setFloat(21, co.getPlanAddStyle_2());
//		ps.setFloat(22, co.getPlanAddStyle_3());
//		ps.setFloat(23, co.getPlanAddStyle_4());
//		ps.setFloat(24, co.getPlanAddStyle_5());
//		ps.setFloat(25, co.getCurrentAdd_1());
//		ps.setFloat(26, co.getCurrentAdd_2());
//		ps.setFloat(27, co.getCurrentAdd_3());
//		ps.setFloat(28, co.getCurrentAdd_4());
//		ps.setFloat(29, co.getPlanAddAdd_1());
//		ps.setFloat(30, co.getPlanAddAdd_2());
//		ps.setFloat(31, co.getPlanAddAdd_3());
//		ps.setFloat(32, co.getPlanAddAdd_4());
//		ps.setFloat(33, co.getAfterSavingRemainQuota());
		
		return returnMsg;
	}
	public String delete(int CO2Report_id) throws SQLException{
		
		String returnMsg="";
		String sql="delete from CO2SAVINGREPORT where CO2Report_id="+CO2Report_id;
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
		
			
			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		
		return returnMsg;
	}
	public List<CO2CalculatorSavingReportModel> getCO2SavingReportListByPartid(int partid,int year,int month) throws SQLException{
		List<CO2CalculatorSavingReportModel> lists=new ArrayList<CO2CalculatorSavingReportModel>();
		String sql="select * from CO2SAVINGREPORT where partment_id="+partid+" and CURRENT_YEAR="+year+" and CURRENT_month ="+month;
		CO2CalculatorSavingReportModel co=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				co=new CO2CalculatorSavingReportModel();
				co.setID(rs.getInt("CO2Report_ID"));
				co.setPartid(rs.getInt("Partment_id"));
				co.setPartName(rs.getString("Partment_name"));
				co.setYear(rs.getInt("Current_year"));
				co.setMonth(rs.getInt("current_month"));
				co.setReportTime(df.format(rs.getDate("report_time")));
				co.setTotalQuota(rs.getFloat("totalQuota"));
				co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
				co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
				co.setCurrentRemain(rs.getFloat("totalQuota")+rs.getFloat("CurrentXiShou")-rs.getFloat("CurrentXiShou"));
				co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
				co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
				co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
				co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
				co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
				co.setPlanSavingOil(rs.getFloat("planSavingOil"));
				co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
				co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
				co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
				co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
				co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
				co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
				co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
				co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
				co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
				co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
				co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
				co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
				co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
				co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
				co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
				co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
				co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
				co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
				co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
				lists.add(co);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while(rs.next())
//		{
//			co=new CO2CalculatorSavingReportModel();
//			co.setID(rs.getInt("CO2Report_ID"));
//			co.setPartid(rs.getInt("Partment_id"));
//			co.setPartName(rs.getString("Partment_name"));
//			co.setYear(rs.getInt("Current_year"));
//			co.setMonth(rs.getInt("current_month"));
//			co.setReportTime(df.format(rs.getDate("report_time")));
//			co.setTotalQuota(rs.getFloat("totalQuota"));
//			co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
//			co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
//			co.setCurrentRemain(rs.getFloat("totalQuota")+rs.getFloat("CurrentXiShou")-rs.getFloat("CurrentXiShou"));
//			co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
//			co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
//			co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
//			co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
//			co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
//			co.setPlanSavingOil(rs.getFloat("planSavingOil"));
//			co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
//			co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
//			co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
//			co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
//			co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
//			co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
//			co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
//			co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
//			co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
//			co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
//			co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
//			co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
//			co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
//			co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
//			co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
//			co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
//			co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
//			co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
//			co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
//			lists.add(co);
//		}

		
		return lists;
	}
	
	
	public List<CO2CalculatorSavingReportModel> getAllCO2SavingReportListByPartid(int year,int month) throws SQLException{
		List<CO2CalculatorSavingReportModel> lists=new ArrayList<CO2CalculatorSavingReportModel>();
		String sql="select * from CO2SAVINGREPORT where CURRENT_YEAR="+year+" and CURRENT_month ="+month;
		CO2CalculatorSavingReportModel co=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				co=new CO2CalculatorSavingReportModel();
				co.setID(rs.getInt("CO2Report_ID"));
				co.setPartid(rs.getInt("Partment_id"));
				co.setPartName(rs.getString("Partment_name"));
				co.setYear(rs.getInt("Current_year"));
				co.setMonth(rs.getInt("current_month"));
				co.setReportTime(df.format(rs.getDate("report_time")));
				co.setTotalQuota(rs.getFloat("totalQuota"));
				co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
				co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
				float totalquota=rs.getFloat("totalQuota");
				float currentXishou=rs.getFloat("CurrentXiShou");
				float currentPaifang=rs.getFloat("CurrentPaiFang");
				float currentRemain=totalquota+currentXishou-currentPaifang;
				co.setCurrentRemain(currentRemain);
				co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
				co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
				co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
				co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
				co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
				co.setPlanSavingOil(rs.getFloat("planSavingOil"));
				co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
				co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
				co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
				co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
				co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
				co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
				co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
				co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
				co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
				co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
				co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
				co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
				co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
				co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
				co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
				co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
				co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
				co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
				co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
				lists.add(co);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			co=new CO2CalculatorSavingReportModel();
//			co.setID(rs.getInt("CO2Report_ID"));
//			co.setPartid(rs.getInt("Partment_id"));
//			co.setPartName(rs.getString("Partment_name"));
//			co.setYear(rs.getInt("Current_year"));
//			co.setMonth(rs.getInt("current_month"));
//			co.setReportTime(df.format(rs.getDate("report_time")));
//			co.setTotalQuota(rs.getFloat("totalQuota"));
//			co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
//			co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
//			float totalquota=rs.getFloat("totalQuota");
//			float currentXishou=rs.getFloat("CurrentXiShou");
//			float currentPaifang=rs.getFloat("CurrentPaiFang");
//			float currentRemain=totalquota+currentXishou-currentPaifang;
//			co.setCurrentRemain(currentRemain);
//			co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
//			co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
//			co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
//			co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
//			co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
//			co.setPlanSavingOil(rs.getFloat("planSavingOil"));
//			co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
//			co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
//			co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
//			co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
//			co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
//			co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
//			co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
//			co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
//			co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
//			co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
//			co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
//			co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
//			co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
//			co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
//			co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
//			co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
//			co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
//			co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
//			co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
//			lists.add(co);
//		}

		
		return lists;
	}
	public CO2CalculatorSavingReportModel getCO2SavingReportListBySavingReportID(int CO2Report_id) throws SQLException{
		String sql="select * from CO2SAVINGREPORT where CO2Report_id="+CO2Report_id;
		CO2CalculatorSavingReportModel co=new CO2CalculatorSavingReportModel();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				co=new CO2CalculatorSavingReportModel();
				co.setID(rs.getInt("CO2Report_ID"));
				co.setPartid(rs.getInt("Partment_id"));
				co.setPartName(rs.getString("Partment_name"));
				co.setYear(rs.getInt("Current_year"));
				co.setMonth(rs.getInt("current_month"));
				co.setReportTime(df.format(rs.getDate("report_time")));
				co.setTotalQuota(rs.getFloat("totalQuota"));
				co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
				co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
				float totalquota=rs.getFloat("totalQuota");
				float currentXishou=rs.getFloat("CurrentXiShou");
				float currentPaifang=rs.getFloat("CurrentPaiFang");
				float currentRemain=totalquota+currentXishou-currentPaifang;
				co.setCurrentRemain(currentRemain);
				co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
				co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
				co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
				co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
				co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
				co.setPlanSavingOil(rs.getFloat("planSavingOil"));
				co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
				co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
				co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
				co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
				co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
				co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
				co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
				co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
				co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
				co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
				co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
				co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
				co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
				co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
				co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
				co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
				co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
				co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
				co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			co=new CO2CalculatorSavingReportModel();
//			co.setID(rs.getInt("CO2Report_ID"));
//			co.setPartid(rs.getInt("Partment_id"));
//			co.setPartName(rs.getString("Partment_name"));
//			co.setYear(rs.getInt("Current_year"));
//			co.setMonth(rs.getInt("current_month"));
//			co.setReportTime(df.format(rs.getDate("report_time")));
//			co.setTotalQuota(rs.getFloat("totalQuota"));
//			co.setCurrentPaiFang(rs.getFloat("CurrentPaiFang"));
//			co.setCurrentXiShou(rs.getFloat("CurrentXiShou"));
//			float totalquota=rs.getFloat("totalQuota");
//			float currentXishou=rs.getFloat("CurrentXiShou");
//			float currentPaifang=rs.getFloat("CurrentPaiFang");
//			float currentRemain=totalquota+currentXishou-currentPaifang;
//			co.setCurrentRemain(currentRemain);
//			co.setTotalEnergyQuota(rs.getFloat("totalEnergyQuota"));
//			co.setCurrentUsedEnergy(rs.getFloat("CurrentUsedEnergy"));
//			co.setPlanSavingEnergy(rs.getFloat("PlanSavingEnergy"));
//			co.setTotalOilQuota(rs.getFloat("TotalOilQuota"));
//			co.setCurrentUsedOil(rs.getFloat("currentUsedOil"));
//			co.setPlanSavingOil(rs.getFloat("planSavingOil"));
//			co.setCurrentStyle_1(rs.getFloat("CurrentStyle_1"));
//			co.setCurrentStyle_2(rs.getFloat("CurrentStyle_2"));
//			co.setCurrentStyle_3(rs.getFloat("CurrentStyle_3"));
//			co.setCurrentStyle_4(rs.getFloat("CurrentStyle_4"));
//			co.setCurrentStyle_5(rs.getFloat("CurrentStyle_5"));
//			co.setPlanAddStyle_1(rs.getFloat("PlanAddStyle_1"));
//			co.setPlanAddStyle_2(rs.getFloat("PlanAddStyle_2"));
//			co.setPlanAddStyle_3(rs.getFloat("PlanAddStyle_3"));
//			co.setPlanAddStyle_4(rs.getFloat("PlanAddStyle_4"));
//			co.setPlanAddStyle_5(rs.getFloat("PlanAddStyle_5"));
//			co.setCurrentAdd_1(rs.getFloat("CurrentAdd_1"));
//			co.setCurrentAdd_2(rs.getFloat("CurrentAdd_2"));
//			co.setCurrentAdd_3(rs.getFloat("CurrentAdd_3"));
//			co.setCurrentAdd_4(rs.getFloat("CurrentAdd_4"));
//			co.setPlanAddAdd_1(rs.getFloat("PlanAdd_1"));
//			co.setPlanAddAdd_2(rs.getFloat("PlanAdd_2"));
//			co.setPlanAddAdd_3(rs.getFloat("PlanAdd_3"));
//			co.setPlanAddAdd_4(rs.getFloat("PlanAdd_4"));
//			co.setAfterSavingRemainQuota(rs.getFloat("AfterSavingRemainQuota"));
//			
//		}
		
		return co;
	}
}
