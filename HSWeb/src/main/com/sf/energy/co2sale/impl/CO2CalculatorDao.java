package com.sf.energy.co2sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.co2sale.model.CO2CalculatorPaiFangModel;
import com.sf.energy.co2sale.model.CO2IndexModel;
import com.sf.energy.util.ConnDB;

public class CO2CalculatorDao
{
	public List<CO2CalculatorPaiFangModel> getCalculatorPaiFangByPartID(int partid,int year) throws SQLException{
		List<CO2CalculatorPaiFangModel> CO2PaiFang_list = new ArrayList<CO2CalculatorPaiFangModel> ();
		String sql="";
		
		float useEnergy=0;
		if(partid==0){
			sql="select SUM (zgross) zgross from T_PARTMENTDAYAM where selectYear="+year;
		}else{
			sql="select SUM (zgross) zgross from T_PARTMENTDAYAM where selectYear="+year+" and partment_id="+partid;
		}
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
				useEnergy=rs.getFloat("Zgross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		while(rs.next())
//		{
//			useEnergy=rs.getFloat("Zgross");
//		}
		CO2CalculatorPaiFangModel copf = new CO2CalculatorPaiFangModel();
		copf.setEnergyStyleName("电能");
		copf.setUnitName("万千瓦时");
		copf.setUseValue(useEnergy/10000+"");
		copf.setConversionCoefficient("4.04");
		copf.setPaiFangValue(useEnergy*4.04/10+"");
		CO2PaiFang_list.add(copf);
		
		CO2CalculatorPaiFangModel copf1 = new CO2CalculatorPaiFangModel();
		copf1.setEnergyStyleName("汽油");
		copf1.setUnitName("千升");
		copf1.setUseValue("0");
		copf1.setConversionCoefficient("1.47");
		copf1.setPaiFangValue("0");
		CO2PaiFang_list.add(copf1);
		
		return CO2PaiFang_list;

	}
	public CO2IndexModel getCalculatorXiShouByPartID(int partid,int year) throws SQLException{
		CO2IndexModel CO2Index = new CO2IndexModel();
		String sql="";
		
		if(partid==0){
			sql="select SUM (style_1) style_1,SUM (style_2) style_2,SUM (style_3) style_3,SUM (style_4) style_4,SUM (style_5) style_5,SUM (add_1) add_1,SUM (add_2) add_2,SUM (add_3) add_3,SUM (add_4) add_4 from CO2Index where Year="+year;
		}else{
			sql="select SUM (style_1) style_1,SUM (style_2) style_2,SUM (style_3) style_3,SUM (style_4) style_4,SUM (style_5) style_5,SUM (add_1) add_1,SUM (add_2) add_2,SUM (add_3) add_3,SUM (add_4) add_4 from CO2Index where Year="+year+" and partment_id="+partid;
		}
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
				CO2Index.setStyle_1(rs.getFloat("Style_1"));
				CO2Index.setStyle_2(rs.getFloat("Style_2"));
				CO2Index.setStyle_3(rs.getFloat("Style_3"));
				CO2Index.setStyle_4(rs.getFloat("Style_4"));
				CO2Index.setStyle_5(rs.getFloat("Style_5"));
				
				CO2Index.setAdd_1(rs.getFloat("Add_1"));
				CO2Index.setAdd_2(rs.getFloat("Add_2"));
				CO2Index.setAdd_3(rs.getFloat("Add_3"));
				CO2Index.setAdd_4(rs.getFloat("Add_4"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			CO2Index.setStyle_1(rs.getFloat("Style_1"));
//			CO2Index.setStyle_2(rs.getFloat("Style_2"));
//			CO2Index.setStyle_3(rs.getFloat("Style_3"));
//			CO2Index.setStyle_4(rs.getFloat("Style_4"));
//			CO2Index.setStyle_5(rs.getFloat("Style_5"));
//			
//			CO2Index.setAdd_1(rs.getFloat("Add_1"));
//			CO2Index.setAdd_2(rs.getFloat("Add_2"));
//			CO2Index.setAdd_3(rs.getFloat("Add_3"));
//			CO2Index.setAdd_4(rs.getFloat("Add_4"));
//			
//		}

		return CO2Index;

	}
	
	
}
