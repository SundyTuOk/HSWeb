package com.sf.energy.co2sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sf.energy.co2sale.model.CO2JieSuanModel;
import com.sf.energy.co2sale.model.CO2SaleInfoModel;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.util.ConnDB;

public class CO2SaleInfoHelperImpl {
	//Connection conn = null;
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DepartmentDao dd=new DepartmentDao();
	public CO2SaleInfoHelperImpl(){
	//	conn = ConnDB.getConnection();
	}
	
	
	public List<CO2SaleInfoModel> getCO2SaleInfo(int year) throws SQLException{
		List<CO2SaleInfoModel> lists=new ArrayList<CO2SaleInfoModel>();
		CO2SaleInfoModel co=null;
		String startDate=year+"-01-01";
		String endDate=year+"-12-31";
		String sql="SELECT Buy_Partmentid,Sale_Partmentid,to_char(DealTime,'yyyy-MM-dd HH24:mi:ss') DealTime,DealValue,RemainValue,BuyOrSale FROM CO2SALEINFO where DealTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') ORDER BY DealTime desc ";
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
				co=new CO2SaleInfoModel();
				co.setBuytime(rs.getString("DealTime"));
				co.setBuyValue(rs.getString("DealValue"));
				co.setRemainingValue(rs.getString("RemainValue"));
				co.setSaleOrBuy(rs.getInt("BuyOrSale"));
				
				if(rs.getInt("BuyOrSale")==1){
					co.setPartID(rs.getInt("Buy_Partmentid"));
					co.setPartName(dd.queryNameById(rs.getInt("Buy_Partmentid")));
				}
				else if(rs.getInt("BuyOrSale")==0){
					co.setPartID(rs.getInt("Sale_Partmentid"));
					co.setPartName(dd.queryNameById(rs.getInt("Sale_Partmentid")));
				}
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
//			co=new CO2SaleInfoModel();
//			co.setBuytime(rs.getString("DealTime"));
//			co.setBuyValue(rs.getString("DealValue"));
//			co.setRemainingValue(rs.getString("RemainValue"));
//			co.setSaleOrBuy(rs.getInt("BuyOrSale"));
//			
//			if(rs.getInt("BuyOrSale")==1){
//				co.setPartID(rs.getInt("Buy_Partmentid"));
//				co.setPartName(dd.queryNameById(rs.getInt("Buy_Partmentid")));
//			}
//			else if(rs.getInt("BuyOrSale")==0){
//				co.setPartID(rs.getInt("Sale_Partmentid"));
//				co.setPartName(dd.queryNameById(rs.getInt("Sale_Partmentid")));
//			}
//			lists.add(co);
//		}

		return lists;
	}
}
