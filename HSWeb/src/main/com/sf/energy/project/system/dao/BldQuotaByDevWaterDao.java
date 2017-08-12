package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.system.model.BldQuotaByDevWaterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.WaterArchitectureFixedDao;

public class BldQuotaByDevWaterDao
{
	/**
	 * 增
	 * @param bdwm
	 * @return
	 * @throws SQLException
	 */
	public String insert(BldQuotaByDevWaterModel bdwm) throws SQLException
	{
		String returnMsg = "";
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		if(bdwm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bdwm.getBldOrAreaID();
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

            if(areaID != -1)
            {
                // 区域的总分配定额
                parentTotalQuota = wafd.getWaterQuotaInfoByYearBldID(String.valueOf(areaID), "0", bdwm.getYear());

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
						parentTotalQuota -=  wafd.getWaterQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdwm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
            }

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += bdwm.getM1();
            iResult += bdwm.getM2();
            iResult += bdwm.getM3();
            iResult += bdwm.getM4();
            iResult += bdwm.getM5();
            iResult += bdwm.getM6();
            iResult += bdwm.getM7();
            iResult += bdwm.getM8();
            iResult += bdwm.getM9();
            iResult += bdwm.getM10();
            iResult += bdwm.getM11();
            iResult += bdwm.getM12();
            thisTotalStandardQuota = bdwm.getTotalDevice() * bdwm.getPower() * bdwm.getRunHoursPerMth() * iResult * bdwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		String sql = "INSERT INTO BLDQUTADETLBYDEVWATER(BLDORAREA_ID,AORBFLG,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALDEVICE,POWER,RUNHOURSPERMTH,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn0=null;
		PreparedStatement ps =null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);			
			ps.setInt(1, bdwm.getBldOrAreaID());
			ps.setInt(2, bdwm.getaOrbFlg());
			ps.setString(3, bdwm.getYear());
			ps.setString(4, bdwm.getName());
			ps.setDouble(5, bdwm.getPrice());
			ps.setInt(6, bdwm.getM1());
			ps.setInt(7, bdwm.getM2());
			ps.setInt(8, bdwm.getM3());
			ps.setInt(9, bdwm.getM4());
			ps.setInt(10, bdwm.getM5());
			ps.setInt(11, bdwm.getM6());
			ps.setInt(12, bdwm.getM7());
			ps.setInt(13, bdwm.getM8());
			ps.setInt(14, bdwm.getM9());
			ps.setInt(15, bdwm.getM10());
			ps.setInt(16, bdwm.getM11());
			ps.setInt(17, bdwm.getM12());
			ps.setInt(18, bdwm.getTotalDevice());
			ps.setDouble(19, bdwm.getPower());
			ps.setDouble(20, bdwm.getRunHoursPerMth());
			ps.setString(21, bdwm.getRemark());
			
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
			ConnDB.release(conn0, ps);
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
		String sql = "delete FROM BLDQUTADETLBYDEVWATER where ValueId =  " + valueID;
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
	 * @param bdwm
	 * @return
	 * @throws SQLException
	 */
	public String modify(BldQuotaByDevWaterModel bdwm) throws SQLException
	{
		String returnMsg = "";
		WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		if(bdwm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bdwm.getBldOrAreaID();
            Connection conn=null;
            PreparedStatement ps_queryAreaID = null;
			ResultSet rs_queryAreaID = null;
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
				ConnDB.release(conn, ps_queryAreaID,rs_queryAreaID);
			}

            if(areaID != -1)
            {
                // 区域的总分配定额
                parentTotalQuota = wafd.getWaterQuotaInfoByYearBldID(String.valueOf(areaID), "0", bdwm.getYear());

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                Connection conn1=null;
                PreparedStatement ps_queryChildArch =null;
				ResultSet rs_queryChildArch =null;
				try
				{
					conn1 = ConnDB.getConnection();
					ps_queryChildArch = conn1.prepareStatement(queryChildArch);
					rs_queryChildArch = ps_queryChildArch.executeQuery();
					while(rs_queryChildArch.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
						parentTotalQuota -=  wafd.getWaterQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bdwm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
            }

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += bdwm.getM1();
            iResult += bdwm.getM2();
            iResult += bdwm.getM3();
            iResult += bdwm.getM4();
            iResult += bdwm.getM5();
            iResult += bdwm.getM6();
            iResult += bdwm.getM7();
            iResult += bdwm.getM8();
            iResult += bdwm.getM9();
            iResult += bdwm.getM10();
            iResult += bdwm.getM11();
            iResult += bdwm.getM12();
            thisTotalStandardQuota = bdwm.getTotalDevice() * bdwm.getPower() * bdwm.getRunHoursPerMth() * iResult * bdwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		
		String sql = "UPDATE BLDQUTADETLBYDEVWATER SET BLDORAREA_ID=?,AORBFLG=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALDEVICE=?,POWER=?,RUNHOURSPERMTH=?,REMARK=? where VALUEID=" + bdwm.getValueID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bdwm.getBldOrAreaID());
			ps.setInt(2, bdwm.getaOrbFlg());
			ps.setString(3, bdwm.getYear());
			ps.setString(4, bdwm.getName());
			ps.setDouble(5, bdwm.getPrice());
			ps.setInt(6, bdwm.getM1());
			ps.setInt(7, bdwm.getM2());
			ps.setInt(8, bdwm.getM3());
			ps.setInt(9, bdwm.getM4());
			ps.setInt(10, bdwm.getM5());
			ps.setInt(11, bdwm.getM6());
			ps.setInt(12, bdwm.getM7());
			ps.setInt(13, bdwm.getM8());
			ps.setInt(14, bdwm.getM9());
			ps.setInt(15, bdwm.getM10());
			ps.setInt(16, bdwm.getM11());
			ps.setInt(17, bdwm.getM12());
			ps.setInt(18, bdwm.getTotalDevice());
			ps.setDouble(19, bdwm.getPower());
			ps.setDouble(20, bdwm.getRunHoursPerMth());
			ps.setString(21, bdwm.getRemark());	
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
			ConnDB.release(conn, ps);
		}
		
		return returnMsg;
	}
	
	/**
	 * 根据ID查询
	 * @param valueID
	 * @return
	 * @throws SQLException
	 */
	public BldQuotaByDevWaterModel queryByID(int valueID) throws SQLException
	{
		BldQuotaByDevWaterModel bdwm = new BldQuotaByDevWaterModel();
		String sql = "Select * from BLDQUTADETLBYDEVWATER where VALUEID=" + valueID;

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
				bdwm.setValueID(valueID);
				bdwm.setBldOrAreaID(rs.getInt("BLDORAREA_ID"));
				bdwm.setaOrbFlg(rs.getInt("AORBFLG"));
				bdwm.setYear(rs.getString("YEAR"));
				bdwm.setName(rs.getString("NAME"));
				bdwm.setPrice(rs.getDouble("PRICE"));
				bdwm.setM1(rs.getInt("M1"));
				bdwm.setM2(rs.getInt("M2"));
				bdwm.setM3(rs.getInt("M3"));
				bdwm.setM4(rs.getInt("M4"));
				bdwm.setM5(rs.getInt("M5"));
				bdwm.setM6(rs.getInt("M6"));
				bdwm.setM7(rs.getInt("M7"));
				bdwm.setM8(rs.getInt("M8"));
				bdwm.setM9(rs.getInt("M9"));
				bdwm.setM10(rs.getInt("M10"));
				bdwm.setM11(rs.getInt("M11"));
				bdwm.setM12(rs.getInt("M12"));
				bdwm.setTotalDevice(rs.getInt("TOTALDEVICE"));
				bdwm.setPower(rs.getDouble("POWER"));
				bdwm.setRunHoursPerMth(rs.getDouble("RUNHOURSPERMTH"));
				bdwm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return bdwm;
	}

}
