package com.sf.energy.co2sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.co2sale.model.CO2JieSuanModel;
import com.sf.energy.co2sale.model.CO2WarningModel;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.EnergyQuotaManagerModel;
import com.sf.energy.util.ConnDB;

public  class WarningCO2HelperImpl {
	//Connection conn = null;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DepartmentDao dd=new DepartmentDao();
	public WarningCO2HelperImpl(){
		//conn = ConnDB.getConnection();
	}
	public List<CO2WarningModel>  getPartCO2WarningInfo(int year) throws NumberFormatException, SQLException{
		List<CO2WarningModel> list = new ArrayList<CO2WarningModel>();
		CO2JieSuan co=new CO2JieSuan();
		CO2QuotaManageDao coQ=new CO2QuotaManageDao();
		CO2WarningModel co2W=null;		
		List<Department> parts=co.qetQuotoPartment(year);
		for(int i=0;i<parts.size();i++){
			int partid=parts.get(i).getDepartmentID();
			EnergyQuotaManagerModel coModel=new EnergyQuotaManagerModel();
			coModel=coQ.getAllWarningInfo(partid, year);
			/*CO2JieSuanModel coModel=getCo2JieSuanModelByPartID(partid, year);*/
			float currentQuota=getCurrentMonthQuotaByPartID(partid, year);
			if((Float.parseFloat(coModel.getRemainQuota())+Float.parseFloat(coModel.getBuyQuota())-Float.parseFloat(coModel.getSaleQuota()))<Float.parseFloat(coModel.getTotalQuota())-currentQuota){
				co2W=new CO2WarningModel();
				co2W.setPartID(partid);
				co2W.setPartName(parts.get(i).getDepartmetName());
				co2W.setQuotaValue(Float.parseFloat(coModel.getTotalQuota()));
				co2W.setRemainingValue(Float.parseFloat(coModel.getRemainQuota())+Float.parseFloat(coModel.getBuyQuota())-Float.parseFloat(coModel.getSaleQuota()));
				list.add(co2W);
			}
		}
		
		return list;
	}
	
	public CO2JieSuanModel getCo2JieSuanModelByPartID(int partid,int year) throws SQLException{
		CO2JieSuanModel co=new CO2JieSuanModel();
		String startDate=year+"-01-01";
		String endDate=year+"-12-31";
		String sql="SELECT * FROM CO2JIESUAN where ThisTIME between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') and PARTMENT_ID="+partid+" and ROWNUM<=1 ORDER BY THISTIME desc ";
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
				co.setPartment_id(rs.getInt("Partment_id"));
				co.setThisBuyValue(rs.getFloat("ThisBuyValue"));
				co.setThisSaleValue(rs.getFloat("ThisSaleValue"));
				co.setThisTime(rs.getString("ThisTime"));
				co.setLastTime(rs.getString("LastTime"));
				co.setLastRemainValue(rs.getFloat("LastRemainValue"));
				co.setThisRemainValue(rs.getFloat("ThisRemainValue"));
				co.setThisUsedValue(rs.getFloat("ThisUsedValue"));
				co.setTotalQuota(rs.getFloat("TotalQuota"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			co.setPartment_id(rs.getInt("Partment_id"));
//			co.setThisBuyValue(rs.getFloat("ThisBuyValue"));
//			co.setThisSaleValue(rs.getFloat("ThisSaleValue"));
//			co.setThisTime(rs.getString("ThisTime"));
//			co.setLastTime(rs.getString("LastTime"));
//			co.setLastRemainValue(rs.getFloat("LastRemainValue"));
//			co.setThisRemainValue(rs.getFloat("ThisRemainValue"));
//			co.setThisUsedValue(rs.getFloat("ThisUsedValue"));
//			co.setTotalQuota(rs.getFloat("TotalQuota"));
//		}
		return co;
	}
	
	public float getCurrentMonthQuotaByPartID(int partid,int year) throws NumberFormatException, SQLException{
		float quota=0;
		String sql="select * from DEPTQUTADETLBYDEVCO2 where partment_id="+partid+" and year="+year;
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
				float devquota=0;
				int num=0;
				for(int i=1;i<=cal.get(Calendar.MONTH)+1;i++){
					String mi="M"+i;
					num+=rs.getInt(mi);
				}
				devquota=(float) (num*(rs.getFloat("TotalDevice")*rs.getFloat("Power")*rs.getFloat("RUNHOURSPERMTH"))*4.04/10);
				quota+=devquota;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			float devquota=0;
//			int num=0;
//			for(int i=1;i<=cal.get(Calendar.MONTH)+1;i++){
//				String mi="M"+i;
//				num+=rs.getInt(mi);
//			}
//			devquota=(float) (num*(rs.getFloat("TotalDevice")*rs.getFloat("Power")*rs.getFloat("RUNHOURSPERMTH"))*4.04/10);
//			quota+=devquota;
//		}

		String sql2="select * from DEPTQUTADETLBYPEOCO2 where partment_id="+partid+" and year="+year;
		PreparedStatement ps2=null;
		ResultSet rs2=null;
		Connection conn2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs2 = ps2.executeQuery();
			
			while(rs2.next())
			{
				float devquota=0;
				int num=0;
				for(int i=1;i<=cal.get(Calendar.MONTH)+1;i++){
					String mi="M"+i;
					num+=rs2.getInt(mi);
				}
				devquota=(float) num*(rs2.getFloat("TotalPeople")*rs2.getFloat("Estandard"));
				quota+=devquota;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		return quota;
	}
}
