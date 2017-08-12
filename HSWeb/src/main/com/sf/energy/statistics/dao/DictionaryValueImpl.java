package com.sf.energy.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.util.ConnDB;

public class DictionaryValueImpl implements DictionaryValueHelper
{

	public String queryValueByNum(String num, int id)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn=null;
		String value = "";
		String sql = "select DictionaryValue_Value from DictionaryValue "
				+ "where" + " DictionaryValue_Num = ? and Dictionary_ID = ?";
		try
		{
			conn=ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.setInt(2, id);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				value = rs.getString("DictionaryValue_Value");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		return value;
	}

}
