package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.util.ConnDB;

public class QueryTemperatureImpl implements QueryTemperatureHelper{
	public  int  getTemperature(int year, int month) throws SQLException
	{
		int temp = 0;
		String str = null;
		str = year+"-"+month;
		
		String sql = "select temperature from temperaturemonth where selecttime =?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, str);
			rs = ps.executeQuery();
			if(rs.next())
			{
				temp = rs.getInt("temperature");
			}	
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setString(1, str);
//		rs = ps.executeQuery();
//		if(rs.next())
//		{
//			temp = rs.getInt("temperature");
//		}	
	return  temp;
	}
		
}
