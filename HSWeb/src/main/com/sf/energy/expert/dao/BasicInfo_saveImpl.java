package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.expert.model.BasicInfo_saveData;
import com.sf.energy.util.ConnDB;

public class BasicInfo_saveImpl implements BasicInfo_saveHelper
{
	/**
	 * 查询指定年份节约型校园评价指标总分
	 */
	public List<BasicInfo_saveData> getBasicInfo_save(int year)
			throws SQLException
	{
		List<BasicInfo_saveData> BasicInfo_save_list = new LinkedList<BasicInfo_saveData>();
		BasicInfo_saveData bisd = null;
		String sql = "select Estimate_Count from Estimate where Estimate_Style = 0 and Estimate_Index ='0' and Estimate_Year = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
			ps.setInt(1, year);
			rs = ps.executeQuery();
			while (rs.next())
			{
				bisd = new BasicInfo_saveData();
				bisd.setCount(rs.getInt("Estimate_Count"));
				BasicInfo_save_list.add(bisd);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
		return BasicInfo_save_list;
	}
}
