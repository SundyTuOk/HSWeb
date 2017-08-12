package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.DeptBillSetModel;
import com.sf.energy.util.ConnDB;

public class DeptBillSetDao
{
	/**
	 * 保存部门用电新建账套
	 * @param BldOrArea_ID
	 * @param AOrBFlg
	 * @param zhangtaoName
	 * @return
	 * @throws SQLException
	 */
	public  boolean addDeptZhangtaoSave(String deptID, String zhangtaoName) throws SQLException
	{
		String sql = "insert into DeptBillSet ( Partment_ID,BillName ) values ( " + deptID + ", '" + zhangtaoName + "')";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);		
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
		return b;
	}
	
	/**
	 * 查
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<DeptBillSetModel> getZhangtao() throws SQLException
	{
		ArrayList<DeptBillSetModel> list = new ArrayList<DeptBillSetModel>();
		DeptBillSetModel dbsm = null;
		
		String sql = "Select * from DeptBillSet ORDER BY BILLID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				dbsm = new DeptBillSetModel();
				dbsm.setBILLID(rs.getInt("BILLID"));
				dbsm.setPARTMENT_ID(rs.getInt("PARTMENT_ID"));
				dbsm.setBILLNAME(rs.getString("BILLNAME"));
				
				list.add(dbsm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	

}
