package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sf.energy.project.system.model.DeptFinalAccountsModel;
import com.sf.energy.util.ConnDB;

public class DeptFinalAccountsDao
{
	/**
	 * 增
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(DeptFinalAccountsModel dfam) throws SQLException
	{
		int AmMeterCnt = 0;   // 电表数量
		String PARTMENT_ID = "0";
		int LastSerialNo = 0;
         // 保存财务结算

        // 根据选择帐套部门ID
        String sql = "select PARTMENT_ID from DEPTBILLSET where BillId =" + dfam.getBILLID();
        Connection conn1=null;
		PreparedStatement ps1 =null;
		ResultSet rs1 = null;

		try
		{
			conn1= ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if(rs1.next())
			{
				PARTMENT_ID = rs1.getString("PARTMENT_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}

       
        // 求出结算电表数量
        sql = "select nvl(count(AmMeter_ID),0) AS  AmMeterCnt from AmMeter where isComputation = 1 And PARTMENT_ID = " + PARTMENT_ID;
        
        Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;

		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			rs2 = ps2.executeQuery();
			if(rs2.next())
			{
				AmMeterCnt = rs2.getInt("AmMeterCnt");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		
        sql = "INSERT INTO DEPTFINALACCOUNTS(BILLID,YEAR,STARTMM,ENDMM,STARTYMD,ENDYMD,LASTSERIALNO,REMARK,USERID,SYSTEMTIME,AMMETERCNT)"
				+ " Values(?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		
		
        Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, dfam.getBILLID());
			ps.setString(2, dfam.getYEAR());
			ps.setInt(3, dfam.getSTARTMM());
			ps.setInt(4, dfam.getENDMM());
			ps.setString(5, dfam.getSTARTYMD());
			ps.setString(6, dfam.getENDYMD());
			
//		LastSerialNo = dfam.getLASTSERIALNO();
//		if(LastSerialNo == 0)
//		{
//			LastSerialNo = (Integer) null;
//		}
			ps.setInt(7, dfam.getLASTSERIALNO());
			ps.setString(8, dfam.getREMARK());
			ps.setInt(9, dfam.getUSERID());
			ps.setString(10, dfam.getSYSTEMTIME());
			ps.setInt(11, AmMeterCnt);

			
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
	public ArrayList<DeptFinalAccountsModel> getZhangtao() throws SQLException
	{
		ArrayList<DeptFinalAccountsModel> list = new ArrayList<DeptFinalAccountsModel>();
		DeptFinalAccountsModel dfam = null;
		
		String sql = "Select * from DEPTFINALACCOUNTS ORDER BY BILLID ";

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
				dfam = new DeptFinalAccountsModel();
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
				dfam.setAMMETERCNT(rs.getInt("AMMETERCNT"));
				
				list.add(dfam);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
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
		String sql = "delete from DEPTFINALACCOUNTS where SERIALNO=" + id;
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
