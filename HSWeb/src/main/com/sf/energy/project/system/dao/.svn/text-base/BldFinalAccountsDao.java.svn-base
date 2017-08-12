package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.BldFinalAccountsModel;
import com.sf.energy.util.ConnDB;

public class BldFinalAccountsDao
{
	/**
	 * 增
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(BldFinalAccountsModel bfam) throws SQLException
	{
		int AmMeterCnt = 0;   // 电表数量
		String BldOrArea_ID = "0";
		String AOrBflg = "0";
		int LastSerialNo = 0;
         // 保存财务结算

        // 根据选择帐套求出区域建筑ID
        String sql = "select BldOrArea_ID,AOrBflg from BldBillSet where BillId =" + bfam.getBILLID();
        Connection conn1=null;
		PreparedStatement ps1 =null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if(rs1.next())
			{
				BldOrArea_ID = rs1.getString("BldOrArea_ID");   // 区域建筑ID
			    AOrBflg = rs1.getString("AOrBflg");             // 区域建筑flg
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
       
        // 求出区域建筑ID的结算电表数量
        if ("0".equals(AOrBflg)) // 区域
        {
            sql = "select nvl(count(AmMeter_ID),0) AS  AmMeterCnt from AmMeter where isComputation = 1 And Area_ID = " + BldOrArea_ID;
        }
        else  // 建筑
        {
            sql = "select nvl(count(AmMeter_ID),0) AS  AmMeterCnt from AmMeter where isComputation = 1 And Architecture_ID = " + BldOrArea_ID;
        }
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
		
		
        sql = "INSERT INTO BLDFINALACCOUNTS(BILLID,YEAR,STARTMM,ENDMM,STARTYMD,ENDYMD,LASTSERIALNO,REMARK,USERID,SYSTEMTIME,AMMETERCNT)"
				+ " Values(?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		
		
        Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, bfam.getBILLID());
			ps.setString(2, bfam.getYEAR());
			ps.setInt(3, bfam.getSTARTMM());
			ps.setInt(4, bfam.getENDMM());
			ps.setString(5, bfam.getSTARTYMD());
			ps.setString(6, bfam.getENDYMD());
			
//		LastSerialNo = bfam.getLASTSERIALNO();
//		if(LastSerialNo == 0)
//		{
//			LastSerialNo = (Integer) null;
//		}
			ps.setInt(7, bfam.getLASTSERIALNO());
			ps.setString(8, bfam.getREMARK());
			ps.setInt(9, bfam.getUSERID());
			ps.setString(10, bfam.getSYSTEMTIME());
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
	public ArrayList<BldFinalAccountsModel> getZhangtao() throws SQLException
	{
		ArrayList<BldFinalAccountsModel> list = new ArrayList<BldFinalAccountsModel>();
		BldFinalAccountsModel bfam = null;
		
		String sql = "Select * from BLDFINALACCOUNTS ORDER BY BILLID ";
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
				bfam = new BldFinalAccountsModel();
				bfam.setBILLID(rs.getInt("BILLID"));
				bfam.setSERIALNO(rs.getInt("SERIALNO"));
				bfam.setYEAR(rs.getString("YEAR"));
				bfam.setSTARTMM(rs.getInt("STARTMM"));
				bfam.setENDMM(rs.getInt("ENDMM"));
				bfam.setSTARTYMD(rs.getString("STARTYMD"));
				bfam.setENDYMD(rs.getString("ENDYMD"));
				bfam.setLASTSERIALNO(rs.getInt("LASTSERIALNO"));
				bfam.setREMARK(rs.getString("REMARK"));
				bfam.setUSERID(rs.getInt("USERID"));
				bfam.setSYSTEMTIME(rs.getString("SYSTEMTIME"));
				bfam.setAMMETERCNT(rs.getInt("AMMETERCNT"));			
				list.add(bfam);
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
