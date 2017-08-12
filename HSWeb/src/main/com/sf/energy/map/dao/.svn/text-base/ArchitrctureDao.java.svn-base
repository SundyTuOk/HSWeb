package com.sf.energy.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.map.model.ArchitrctureMode;
import com.sf.energy.util.ConnDB;

public class ArchitrctureDao {
	
	
	public ArchitrctureMode queryArchitrcture(int id) throws SQLException
	{
		ArchitrctureMode architrctureMode=null;
		String sql="select a.ARCHITECTURE_ID,a.Architecture_Name,b.DICTIONARYVALUE_VALUE,a.Architecture_Area,a.Architecture_Storey,(select count(ammeter_id) from ammeter where ARCHITECTURE_ID="+id+") meter_num,(select count(WaterMeter_ID) from WaterMeter where ARCHITECTURE_ID="+id+") water_num,a.Architecture_imgUrl from   Architecture a  inner join DICTIONARYVALUE b on a.Architecture_Style=b.DICTIONARYVALUE_NUM where a.ARCHITECTURE_ID="+id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
			 architrctureMode=new ArchitrctureMode();
			 architrctureMode.setArchitecture_Name(rs.getString("Architecture_Name"));
			 architrctureMode.setArchitecture_Style(rs.getString("DICTIONARYVALUE_VALUE"));
			 architrctureMode.setArchitecture_Area(rs.getString("Architecture_Area"));
			 architrctureMode.setArchitecture_Storey(rs.getString("Architecture_Storey"));
			 architrctureMode.setArcimg(rs.getString("Architecture_imgUrl"));
			 architrctureMode.setElecNum(rs.getInt("meter_num"));
			 architrctureMode.setWaterNum(rs.getInt("water_num"));
			 
			
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		if (rs.next())
//		{
//		 architrctureMode=new ArchitrctureMode();
//		 architrctureMode.setArchitecture_Name(rs.getString("Architecture_Name"));
//		 architrctureMode.setArchitecture_Style(rs.getString("DICTIONARYVALUE_VALUE"));
//		 architrctureMode.setArchitecture_Area(rs.getString("Architecture_Area"));
//		 architrctureMode.setArchitecture_Storey(rs.getString("Architecture_Storey"));
//		 architrctureMode.setArcimg(rs.getString("Architecture_imgUrl"));
//		 architrctureMode.setElecNum(rs.getInt("meter_num"));
//		 architrctureMode.setWaterNum(rs.getInt("water_num"));
//		 
//		
//		}
//		if(rs!=null)
//			rs.close();
//		
//		if(ps!=null)
//			ps.close();
		return architrctureMode;
	}
	

	

}
