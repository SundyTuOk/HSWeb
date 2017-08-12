package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.system.model.BldQuotaDetlByDevModel;
import com.sf.energy.util.ConnDB;

public class BldQuotaDetlByDevDao
{
	/**
	 * 增
	 * @param bdm
	 * @return
	 * @throws SQLException
	 */
	public String insert(BldQuotaDetlByDevModel bdm) throws SQLException
	{
		String returnMsg = "";
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		if(bdm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
           String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bdm.getBldOrAreaID();
           Connection conn=null;
           PreparedStatement ps_queryAreaID=null;
			ResultSet rs_queryAreaID=null;
			try
			{
				conn = ConnDB.getConnection();
				ps_queryAreaID = conn.prepareStatement(queryAreaID);
				rs_queryAreaID = ps_queryAreaID.executeQuery();
				if(rs_queryAreaID.next())
				{
					areaID = rs_queryAreaID.getInt("Area_ID");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps_queryAreaID, rs_queryAreaID);
			}
//            ResultSet rs_queryAreaID = sm_queryAreaID.executeQuery(queryAreaID);
//            if(rs_queryAreaID.next())
//            {
//            	areaID = rs_queryAreaID.getInt("Area_ID");
//            }
//            if(rs_queryAreaID != null)
//            	rs_queryAreaID.close();
//            if(sm_queryAreaID != null)
//            	sm_queryAreaID.close();

            if(areaID != -1)
            {
                // 区域的总分配定额
                parentTotalQuota = afd.getQuotaInfoByYearBldID(String.valueOf(areaID), "0", bdm.getYear());

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                Connection conn1=null;
                PreparedStatement ps_queryChildArch =null;
				ResultSet rs_queryChildArch =null;
				try
				{
					conn1=ConnDB.getConnection();
					ps_queryChildArch = conn1.prepareStatement(queryChildArch);
					rs_queryChildArch = ps_queryChildArch.executeQuery();
					while(rs_queryChildArch.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
	                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
//                ResultSet rs_queryChildArch = sm_queryChildArch.executeQuery(queryChildArch);
//                while(rs_queryChildArch.next())
//                {
//                	// 求出兄弟建筑的对象年定额情报（包括自己）
//                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdm.getYear());                	
//                }
//                if(rs_queryChildArch != null)
//                	rs_queryChildArch.close();
//                if(sm_queryChildArch != null)
//                	sm_queryChildArch.close();
            }

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += bdm.getM1();
            iResult += bdm.getM2();
            iResult += bdm.getM3();
            iResult += bdm.getM4();
            iResult += bdm.getM5();
            iResult += bdm.getM6();
            iResult += bdm.getM7();
            iResult += bdm.getM8();
            iResult += bdm.getM9();
            iResult += bdm.getM10();
            iResult += bdm.getM11();
            iResult += bdm.getM12();
            thisTotalStandardQuota = bdm.getTotalDevice() * bdm.getPower() * bdm.getRunHoursPerMth() * iResult * bdm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		String sql = "INSERT INTO BLDQUTADETLBYDEV(BLDORAREA_ID,AORBFLG,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALDEVICE,POWER,RUNHOURSPERMTH,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bdm.getBldOrAreaID());
			ps.setInt(2, bdm.getaOrbFlg());
			ps.setString(3, bdm.getYear());
			ps.setString(4, bdm.getName());
			ps.setDouble(5, bdm.getPrice());
			ps.setInt(6, bdm.getM1());
			ps.setInt(7, bdm.getM2());
			ps.setInt(8, bdm.getM3());
			ps.setInt(9, bdm.getM4());
			ps.setInt(10, bdm.getM5());
			ps.setInt(11, bdm.getM6());
			ps.setInt(12, bdm.getM7());
			ps.setInt(13, bdm.getM8());
			ps.setInt(14, bdm.getM9());
			ps.setInt(15, bdm.getM10());
			ps.setInt(16, bdm.getM11());
			ps.setInt(17, bdm.getM12());
			ps.setInt(18, bdm.getTotalDevice());
			ps.setDouble(19, bdm.getPower());
			ps.setDouble(20, bdm.getRunHoursPerMth());
			ps.setString(21, bdm.getRemark());
			
			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return returnMsg;
	}
	
	/**
	 * 删
	 * @param valueID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int valueID) throws SQLException
	{
		String sql = "delete FROM BldQutaDetlByDev where ValueId =  " + valueID;
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
	 * 改
	 * @param bdm
	 * @return
	 * @throws SQLException
	 */
	public String modify(BldQuotaDetlByDevModel bdm) throws SQLException
	{
		String returnMsg = "";
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		if(bdm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bdm.getBldOrAreaID();
            Connection conn=null;
            PreparedStatement ps_queryAreaID=null;
			ResultSet rs_queryAreaID=null;
			try
			{
				conn = ConnDB.getConnection();
				ps_queryAreaID = conn.prepareStatement(queryAreaID);
				rs_queryAreaID = ps_queryAreaID.executeQuery();
				if(rs_queryAreaID.next())
				{
					areaID = rs_queryAreaID.getInt("Area_ID");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps_queryAreaID, rs_queryAreaID);
			}
//            Statement sm_queryAreaID = ;
//            ResultSet rs_queryAreaID = sm_queryAreaID.executeQuery(queryAreaID);
//            if(rs_queryAreaID.next())
//            {
//            	areaID = rs_queryAreaID.getInt("Area_ID");
//            }
//            if(rs_queryAreaID != null)
//            	rs_queryAreaID.close();
//            if(sm_queryAreaID != null)
//            	sm_queryAreaID.close();

            if(areaID != -1)
            {
                // 区域的总分配定额
                parentTotalQuota = afd.getQuotaInfoByYearBldID(String.valueOf(areaID), "0", bdm.getYear());

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                Connection conn1=null;
                PreparedStatement ps_queryChildArch =null;
				ResultSet rs_queryChildArch =null;
				try
				{
					conn1=ConnDB.getConnection();
					ps_queryChildArch = conn1.prepareStatement(queryChildArch);
					rs_queryChildArch = ps_queryChildArch.executeQuery();
					while(rs_queryChildArch.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
//                while(rs_queryChildArch.next())
//                {
//                	// 求出兄弟建筑的对象年定额情报（包括自己）
//                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdm.getYear());                	
//                }
//                if(rs_queryChildArch != null)
//                	rs_queryChildArch.close();
//                if(sm_queryChildArch != null)
//                	sm_queryChildArch.close();
            }

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += bdm.getM1();
            iResult += bdm.getM2();
            iResult += bdm.getM3();
            iResult += bdm.getM4();
            iResult += bdm.getM5();
            iResult += bdm.getM6();
            iResult += bdm.getM7();
            iResult += bdm.getM8();
            iResult += bdm.getM9();
            iResult += bdm.getM10();
            iResult += bdm.getM11();
            iResult += bdm.getM12();
            thisTotalStandardQuota = bdm.getTotalDevice() * bdm.getPower() * bdm.getRunHoursPerMth() * iResult * bdm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		String sql = "UPDATE BLDQUTADETLBYDEV SET BLDORAREA_ID=?,AORBFLG=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALDEVICE=?,POWER=?,RUNHOURSPERMTH=?,REMARK=? where VALUEID=" + bdm.getValueID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bdm.getBldOrAreaID());
			ps.setInt(2, bdm.getaOrbFlg());
			ps.setString(3, bdm.getYear());
			ps.setString(4, bdm.getName());
			ps.setDouble(5, bdm.getPrice());
			ps.setInt(6, bdm.getM1());
			ps.setInt(7, bdm.getM2());
			ps.setInt(8, bdm.getM3());
			ps.setInt(9, bdm.getM4());
			ps.setInt(10, bdm.getM5());
			ps.setInt(11, bdm.getM6());
			ps.setInt(12, bdm.getM7());
			ps.setInt(13, bdm.getM8());
			ps.setInt(14, bdm.getM9());
			ps.setInt(15, bdm.getM10());
			ps.setInt(16, bdm.getM11());
			ps.setInt(17, bdm.getM12());
			ps.setInt(18, bdm.getTotalDevice());
			ps.setDouble(19, bdm.getPower());
			ps.setDouble(20, bdm.getRunHoursPerMth());
			ps.setString(21, bdm.getRemark());
			
			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return returnMsg;
	}
	
	/**
	 * 根据ID查询
	 * @param valueID
	 * @return
	 * @throws SQLException
	 */
	public BldQuotaDetlByDevModel queryByID(int valueID) throws SQLException
	{
		BldQuotaDetlByDevModel bdm = new BldQuotaDetlByDevModel();
		String sql = "Select * from BLDQUTADETLBYDEV where VALUEID=" + valueID;

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
				bdm.setValueID(valueID);
				bdm.setBldOrAreaID(rs.getInt("BLDORAREA_ID"));
				bdm.setaOrbFlg(rs.getInt("AORBFLG"));
				bdm.setYear(rs.getString("YEAR"));
				bdm.setName(rs.getString("NAME"));
				bdm.setPrice(rs.getDouble("PRICE"));
				bdm.setM1(rs.getInt("M1"));
				bdm.setM2(rs.getInt("M2"));
				bdm.setM3(rs.getInt("M3"));
				bdm.setM4(rs.getInt("M4"));
				bdm.setM5(rs.getInt("M5"));
				bdm.setM6(rs.getInt("M6"));
				bdm.setM7(rs.getInt("M7"));
				bdm.setM8(rs.getInt("M8"));
				bdm.setM9(rs.getInt("M9"));
				bdm.setM10(rs.getInt("M10"));
				bdm.setM11(rs.getInt("M11"));
				bdm.setM12(rs.getInt("M12"));
				bdm.setTotalDevice(rs.getInt("TOTALDEVICE"));
				bdm.setPower(rs.getDouble("POWER"));
				bdm.setRunHoursPerMth(rs.getDouble("RUNHOURSPERMTH"));
				bdm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return bdm;
	}
	
}
