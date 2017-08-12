package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.BldBillSetWaterModel;

public class BldBillSetWaterDao
{
	/**
	 * 保存建筑用电新建账套
	 * @param BldOrArea_ID 建筑或区域ID
	 * @param AOrBFlg 建筑或区域标识
	 * @param zhangtaoName 账套名称
	 * @return
	 * @throws SQLException
	 */
	public boolean addArchZhangtaoSave(String BldOrArea_ID,String AOrBFlg, String zhangtaoName) throws SQLException
	{
		String sql = "insert into BLDBILLSETWATER ( BldOrArea_ID,AOrBflg,BillName ) values ( " + BldOrArea_ID + "," + AOrBFlg + ", '" + zhangtaoName + "')";
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
	public ArrayList<BldBillSetWaterModel> getZhangtao() throws SQLException
	{
		ArrayList<BldBillSetWaterModel> list = new ArrayList<BldBillSetWaterModel>();
		BldBillSetWaterModel bbswm = null;
		
		String sql = "Select * from BLDBILLSETWATER ORDER BY BILLID ";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				bbswm = new BldBillSetWaterModel();
				bbswm.setBillId(rs.getInt("BILLID"));
				bbswm.setBldOrArea_ID(rs.getInt("BLDORAREA_ID"));
				bbswm.setAOrBflg(rs.getInt("AORBFLG"));
				bbswm.setBillName(rs.getString("BILLNAME"));
				
				list.add(bbswm);
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
			bbswm = new BldBillSetWaterModel();
			bbswm.setBillId(rs.getInt("BILLID"));
			bbswm.setBldOrArea_ID(rs.getInt("BLDORAREA_ID"));
			bbswm.setAOrBflg(rs.getInt("AORBFLG"));
			bbswm.setBillName(rs.getString("BILLNAME"));
			
			list.add(bbswm);
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/
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
		String sql = "delete from BLDFINALACCOUNTSWATER where SerialNo=" + id;
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

}
