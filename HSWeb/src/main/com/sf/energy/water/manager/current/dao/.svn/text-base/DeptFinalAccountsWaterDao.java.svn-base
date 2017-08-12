package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.DeptFinalAccountsWaterModel;

public class DeptFinalAccountsWaterDao
{
	/**
	 * 增
	 * 
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(DeptFinalAccountsWaterModel dfam) throws SQLException
	{
		int WaterMeterCnt = 0; // 电表数量
		String PARTMENT_ID = "0";
		int LastSerialNo = 0;
		// 保存财务结算

		// 根据选择帐套部门ID
		String sql = "select PARTMENT_ID from DEPTBILLSETWATER where BillId =" + dfam.getBILLID();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				PARTMENT_ID = rs.getString("PARTMENT_ID");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
/*
		Statement sm1 = ConnDB.getConnection().createStatement();
		ResultSet rs1 = sm1.executeQuery(sql);
		if (rs1.next())
		{
			PARTMENT_ID = rs1.getString("PARTMENT_ID");
		}
		if (rs1 != null)
			rs1.close();
		if (sm1 != null)
			sm1.close();*/

		// 求出结算电表数量
		sql = "select nvl(count(WaterMeter_ID),0) AS  WaterMeterCnt from WaterMeter where isComputation = 1 And Partment = " + PARTMENT_ID;
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				WaterMeterCnt = rs1.getInt("WaterMeterCnt");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
		/*Statement sm2 = ConnDB.getConnection().createStatement();
		ResultSet rs2 = sm2.executeQuery(sql);
		if (rs2.next())
		{
			WaterMeterCnt = rs2.getInt("WaterMeterCnt");
		}
		if (rs2 != null)
			rs2.close();
		if (sm2 != null)
			sm2.close();*/

		sql = "INSERT INTO DEPTFINALACCOUNTSWATER(BILLID,YEAR,STARTMM,ENDMM,STARTYMD,ENDYMD,LASTSERIALNO,REMARK,USERID,SYSTEMTIME,MeterCnt)"
				+ " Values(?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";

		Connection conn2 = null;
		PreparedStatement ps2 = null;
		boolean b = false;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			ps2.setInt(1, dfam.getBILLID());
			ps2.setString(2, dfam.getYEAR());
			ps2.setInt(3, dfam.getSTARTMM());
			ps2.setInt(4, dfam.getENDMM());
			ps2.setString(5, dfam.getSTARTYMD());
			ps2.setString(6, dfam.getENDYMD());

			// LastSerialNo = dfam.getLASTSERIALNO();
			// if(LastSerialNo == 0)
			// {
			// LastSerialNo = (Integer) null;
			// }
			ps2.setInt(7, dfam.getLASTSERIALNO());
			if (dfam.getREMARK() == null || "".equals(dfam.getREMARK()))
			{
				ps2.setString(8, " ");
			} else
			{
				ps2.setString(8, dfam.getREMARK());
			}

			ps2.setInt(9, dfam.getUSERID());
			ps2.setString(10, dfam.getSYSTEMTIME());
			ps2.setInt(11, WaterMeterCnt);
			b = (ps2.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
		/*PreparedStatement ps;
		ps = ConnDB.getConnection().prepareStatement(sql);

		ps.setInt(1, dfam.getBILLID());
		ps.setString(2, dfam.getYEAR());
		ps.setInt(3, dfam.getSTARTMM());
		ps.setInt(4, dfam.getENDMM());
		ps.setString(5, dfam.getSTARTYMD());
		ps.setString(6, dfam.getENDYMD());

		// LastSerialNo = dfam.getLASTSERIALNO();
		// if(LastSerialNo == 0)
		// {
		// LastSerialNo = (Integer) null;
		// }
		ps.setInt(7, dfam.getLASTSERIALNO());
		if (dfam.getREMARK() == null || "".equals(dfam.getREMARK()))
		{
			ps.setString(8, " ");
		} else
		{
			ps.setString(8, dfam.getREMARK());
		}

		ps.setInt(9, dfam.getUSERID());
		ps.setString(10, dfam.getSYSTEMTIME());
		ps.setInt(11, WaterMeterCnt);

		boolean b = (ps.executeUpdate() == 1);

		if (ps != null)
			ps.close();*/

		return b;
	}

	/**
	 * 查
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<DeptFinalAccountsWaterModel> getZhangtao() throws SQLException
	{
		ArrayList<DeptFinalAccountsWaterModel> list = new ArrayList<DeptFinalAccountsWaterModel>();
		DeptFinalAccountsWaterModel dfam = null;

		String sql = "Select * from DEPTFINALACCOUNTSWATER ORDER BY BILLID ";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				dfam = new DeptFinalAccountsWaterModel();
				dfam.setBILLID(rs.getInt("BILLID"));
				dfam.setSERIALNO(rs.getInt("SERIALNO"));
				dfam.setYEAR(rs.getString("YEAR"));
				dfam.setSTARTMM(rs.getInt("STARTMM"));
				dfam.setENDMM(rs.getInt("ENDMM"));
				dfam.setSTARTYMD(rs.getString("STARTYMD"));
				dfam.setENDYMD(rs.getString("ENDYMD"));
				dfam.setLASTSERIALNO(rs.getInt("LASTSERIALNO"));
				dfam.setREMARK(rs.getString("REMARK"));
				dfam.setUSERID(rs.getInt("USERID"));
				dfam.setSYSTEMTIME(rs.getString("SYSTEMTIME"));
				dfam.setMETERCNT(rs.getInt("METERCNT"));

				list.add(dfam);
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

		while (rs.next())
		{
			dfam = new DeptFinalAccountsWaterModel();
			dfam.setBILLID(rs.getInt("BILLID"));
			dfam.setSERIALNO(rs.getInt("SERIALNO"));
			dfam.setYEAR(rs.getString("YEAR"));
			dfam.setSTARTMM(rs.getInt("STARTMM"));
			dfam.setENDMM(rs.getInt("ENDMM"));
			dfam.setSTARTYMD(rs.getString("STARTYMD"));
			dfam.setENDYMD(rs.getString("ENDYMD"));
			dfam.setLASTSERIALNO(rs.getInt("LASTSERIALNO"));
			dfam.setREMARK(rs.getString("REMARK"));
			dfam.setUSERID(rs.getInt("USERID"));
			dfam.setSYSTEMTIME(rs.getString("SYSTEMTIME"));
			dfam.setMETERCNT(rs.getInt("METERCNT"));

			list.add(dfam);
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/
		return list;
	}

	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "delete from DEPTFINALACCOUNTSWATER where SERIALNO=" + id;
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
