package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.util.ConnDB;

/**
 * 整理月电T_MonthAm的数据库操作类
 * @author yanhao
 *
 */
public class T_MonthAmDao
{
	/**
	 * 查询某电表的当月用电
	 * @param ammID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException 
	 */
	public float queryMonthValue(int ammID,int year,int month) throws SQLException
	{
		float value=0;
		String sql="Select nvl(sum(ZGROSS),0) ZGROSS from T_MonthAm where AMMETER_ID="+ ammID +" and SELECTYEAR="+ year +" and SELECTMONTH="+month;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				value=rs.getFloat("ZGROSS");
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return value;
	}
}
