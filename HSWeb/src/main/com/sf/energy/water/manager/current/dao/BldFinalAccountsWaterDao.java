package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.BldFinalAccountsWaterModel;

public class BldFinalAccountsWaterDao
{
	/**
	 * 增
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(BldFinalAccountsWaterModel bfawm) throws SQLException
	{
		int WaterMeterCnt = 0;   // 水表数量
		String BldOrArea_ID = "0";
		String AOrBflg = "0";
		//int LastSerialNo = 0;
         // 保存财务结算

        // 根据选择帐套求出区域建筑ID
        String sql = "select BldOrArea_ID,AOrBflg from BLDBILLSETWATER where BillId =" + bfawm.getBillId();
        Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				BldOrArea_ID = rs.getString("BldOrArea_ID"); // 区域建筑ID
				AOrBflg = rs.getString("AOrBflg"); // 区域建筑flg
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
        
       
        // 求出区域建筑ID的结算水表数量
        if ("0".equals(AOrBflg)) // 区域
        {
            sql = "select nvl(count(WaterMeter_ID),0) AS  WaterMeterCnt from WaterMeter where isComputation = 1 And Area_ID = " + BldOrArea_ID;
        }
        else  // 建筑
        {
            sql = "select nvl(count(WaterMeter_ID),0) AS  WaterMeterCnt from WaterMeter where isComputation = 1 And Architecture_ID = " + BldOrArea_ID;
        }
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
       
		
		
        sql = "INSERT INTO BLDFINALACCOUNTSWATER(BILLID,YEAR,STARTMM,ENDMM,STARTYMD,ENDYMD,LASTSERIALNO,REMARK,USERID,SYSTEMTIME,METERCNT)"
				+ " Values(?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		
        Connection conn2 = null;
		PreparedStatement ps2 = null;
		boolean b = false;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			ps2.setInt(1, bfawm.getBillId());
			ps2.setString(2, bfawm.getYear());
			ps2.setInt(3, bfawm.getStartMM());
			ps2.setInt(4, bfawm.getEndMM());
			ps2.setString(5, bfawm.getStartYMD());
			ps2.setString(6, bfawm.getEndYMD());
			
//			LastSerialNo = bfam.getLASTSERIALNO();
//			if(LastSerialNo == 0)
//			{
//				LastSerialNo = (Integer) null;
//			}
			ps2.setInt(7, bfawm.getLastSerialNo());
			
			if(bfawm.getRemark()==null||"".equals(bfawm.getRemark()))
			{
				ps2.setString(8, " ");
			}
			else
			{
				ps2.setString(8, bfawm.getRemark());
			}
			
			ps2.setInt(9, bfawm.getUserID());
			ps2.setString(10, bfawm.getSystemtime());
			ps2.setInt(11, WaterMeterCnt);
			b = (ps2.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
			
		return b;
	}
	
	/**
	 * 查
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BldFinalAccountsWaterModel> getZhangtao() throws SQLException
	{
		ArrayList<BldFinalAccountsWaterModel> list = new ArrayList<BldFinalAccountsWaterModel>();
		BldFinalAccountsWaterModel bfawm = null;
		
		String sql = "Select * from BLDFINALACCOUNTSWATER ORDER BY BILLID ";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				bfawm = new BldFinalAccountsWaterModel();
				bfawm.setBillId(rs.getInt("BILLID"));
				bfawm.setSerialNo(rs.getInt("SERIALNO"));
				bfawm.setYear(rs.getString("YEAR"));
				bfawm.setStartMM(rs.getInt("STARTMM"));
				bfawm.setEndMM(rs.getInt("ENDMM"));
				bfawm.setStartYMD(rs.getString("STARTYMD"));
				bfawm.setEndYMD(rs.getString("ENDYMD"));
				bfawm.setLastSerialNo(rs.getInt("LASTSERIALNO"));
				bfawm.setRemark(rs.getString("REMARK"));
				bfawm.setUserID(rs.getInt("USERID"));
				bfawm.setSystemtime(rs.getString("SYSTEMTIME"));
				bfawm.setMeterCnt(rs.getInt("METERCNT"));
				
				list.add(bfawm);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return list;
	}

}
