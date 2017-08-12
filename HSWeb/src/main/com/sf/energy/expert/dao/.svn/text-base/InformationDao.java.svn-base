package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.expert.model.InformationModel;
import com.sf.energy.util.ConnDB;

public class InformationDao {

	/**
	 * 信息表里查询指定年份全校的土地面积，建筑面积，绿化面积
	 * @param year	年份
	 * @return		结果集
	 * @throws SQLException
	 */
	public  InformationModel  getAreaData(int year) throws SQLException
	{
		InformationModel  imm = null;
		
		String sql = "select TDAREA,JZAREA,LHAREA,ManCount from INFORMATION WHERE THETIME = ?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				imm = new InformationModel();
				imm.setJzarea(rs.getFloat("JZAREA"));
				imm.setLharea(rs.getFloat("LHAREA"));
				imm.setTdarea(rs.getFloat("TDAREA"));
				imm.setManCount(rs.getInt("ManCount"));
			}
			else
			{
				imm = new InformationModel();
				imm.setJzarea(1);
				imm.setLharea(1);
				imm.setTdarea(1);
				imm.setManCount(1);
			}	
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setInt(1, year);
//		ResultSet rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			imm = new InformationModel();
//			imm.setJzarea(rs.getFloat("JZAREA"));
//			imm.setLharea(rs.getFloat("LHAREA"));
//			imm.setTdarea(rs.getFloat("TDAREA"));
//			imm.setManCount(rs.getInt("ManCount"));
//		}
//		else
//		{
//			imm = new InformationModel();
//			imm.setJzarea(1);
//			imm.setLharea(1);
//			imm.setTdarea(1);
//			imm.setManCount(1);
//		}	
		return imm;
	}
	
	
	
}
