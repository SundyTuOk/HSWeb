package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.DeptBillSetWaterModel;

public class DeptBillSetWaterDao
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
		String sql = "insert into DEPTBILLSETWATER ( Partment_ID,BillName ) values ( " + deptID + ", '" + zhangtaoName + "')";
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		
		boolean b = (ps.executeUpdate() == 1);

		if (ps != null)
			ps.close();*/

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		return b;
	}
	
	/**
	 * 查
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<DeptBillSetWaterModel> getZhangtao() throws SQLException
	{
		ArrayList<DeptBillSetWaterModel> list = new ArrayList<DeptBillSetWaterModel>();
		DeptBillSetWaterModel dbsm = null;
		
		String sql = "Select * from DEPTBILLSETWATER ORDER BY BILLID ";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				dbsm = new DeptBillSetWaterModel();
				dbsm.setBILLID(rs.getInt("BILLID"));
				dbsm.setPARTMENT_ID(rs.getInt("PARTMENT_ID"));
				dbsm.setBILLNAME(rs.getString("BILLNAME"));
				
				list.add(dbsm);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			dbsm = new DeptBillSetWaterModel();
			dbsm.setBILLID(rs.getInt("BILLID"));
			dbsm.setPARTMENT_ID(rs.getInt("PARTMENT_ID"));
			dbsm.setBILLNAME(rs.getString("BILLNAME"));
			
			list.add(dbsm);
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/
		return list;
	}
	

}
