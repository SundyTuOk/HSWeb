package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.BldBillSetModel;
import com.sf.energy.util.ConnDB;

public class BldBillSetDao
{
	/**
	 * 保存建筑用电新建账套
	 * @param BldOrArea_ID
	 * @param AOrBFlg
	 * @param zhangtaoName
	 * @return
	 * @throws SQLException
	 */
	public boolean addArchZhangtaoSave(String BldOrArea_ID,String AOrBFlg, String zhangtaoName) throws SQLException
	{
		String sql = "insert into BldBillSet ( BldOrArea_ID,AOrBflg,BillName ) values ( " + BldOrArea_ID + "," + AOrBFlg + ", '" + zhangtaoName + "')";
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
	public ArrayList<BldBillSetModel> getZhangtao() throws SQLException
	{
		ArrayList<BldBillSetModel> list = new ArrayList<BldBillSetModel>();
		BldBillSetModel bbsm = null;
		
		String sql = "Select * from BldBillSet ORDER BY BILLID ";

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
				bbsm = new BldBillSetModel();
				bbsm.setBILLID(rs.getInt("BILLID"));
				bbsm.setBLDORAREA_ID(rs.getInt("BLDORAREA_ID"));
				bbsm.setAORBFLG(rs.getInt("AORBFLG"));
				bbsm.setBILLNAME(rs.getString("BILLNAME"));
				
				list.add(bbsm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 删
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "delete from BldFinalAccounts where SerialNo=" + id;
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

}
