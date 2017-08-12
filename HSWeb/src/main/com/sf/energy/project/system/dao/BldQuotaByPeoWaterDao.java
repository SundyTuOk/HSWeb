package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sf.energy.project.system.model.BldQuotaByPeoWaterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.WaterArchitectureFixedDao;

public class BldQuotaByPeoWaterDao
{
	/**
	 * 增
	 * @param bpwm
	 * @return
	 * @throws SQLException
	 */
	public String insert(BldQuotaByPeoWaterModel bpwm) throws SQLException
	{
		String returnMsg = "";
    	WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		if(bpwm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            Connection conn=null;
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bpwm.getBldOrAreaID();
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
                parentTotalQuota = wafd.getWaterQuotaInfoByYearBldID(String.valueOf(areaID), "0", bpwm.getYear());

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                Connection conn1=null;
                PreparedStatement ps_queryChildArch=null;
				ResultSet rs_queryChildArch=null;
				try
				{
					conn1= ConnDB.getConnection();
					ps_queryChildArch = conn1.prepareStatement(queryChildArch);
					rs_queryChildArch = ps_queryChildArch.executeQuery();
					while(rs_queryChildArch.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
						parentTotalQuota -= wafd.getWaterQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpwm.getYear());                	
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
            iResult += bpwm.getM1();
            iResult += bpwm.getM2();
            iResult += bpwm.getM3();
            iResult += bpwm.getM4();
            iResult += bpwm.getM5();
            iResult += bpwm.getM6();
            iResult += bpwm.getM7();
            iResult += bpwm.getM8();
            iResult += bpwm.getM9();
            iResult += bpwm.getM10();
            iResult += bpwm.getM11();
            iResult += bpwm.getM12();
            thisTotalStandardQuota = bpwm.getTotalPeople() * bpwm.geteStandard() * iResult * bpwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		
		String sql = "INSERT INTO BLDQUTADETLBYPEOWATER(BLDORAREA_ID,AORBFLG,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALPEOPLE,ESTANDARD,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, bpwm.getBldOrAreaID());
			ps.setInt(2, bpwm.getaOrbFlg());
			ps.setString(3, bpwm.getYear());
			ps.setString(4, bpwm.getName());
			ps.setDouble(5, bpwm.getPrice());
			ps.setInt(6, bpwm.getM1());
			ps.setInt(7, bpwm.getM2());
			ps.setInt(8, bpwm.getM3());
			ps.setInt(9, bpwm.getM4());
			ps.setInt(10, bpwm.getM5());
			ps.setInt(11, bpwm.getM6());
			ps.setInt(12, bpwm.getM7());
			ps.setInt(13, bpwm.getM8());
			ps.setInt(14, bpwm.getM9());
			ps.setInt(15, bpwm.getM10());
			ps.setInt(16, bpwm.getM11());
			ps.setInt(17, bpwm.getM12());
			ps.setInt(18, bpwm.getTotalPeople());
			ps.setDouble(19, bpwm.geteStandard());
			ps.setString(20, bpwm.getRemark());		
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
	 * 删
	 * @param valueID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int valueID) throws SQLException
	{
		String sql = "delete FROM BLDQUTADETLBYPEOWATER where ValueId =  " + valueID;
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
	 * @param bpwm
	 * @return
	 * @throws SQLException
	 */
	public String modify(BldQuotaByPeoWaterModel bpwm) throws SQLException
	{
		String returnMsg = "";
    	WaterArchitectureFixedDao wafd = new WaterArchitectureFixedDao();
		if(bpwm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bpwm.getBldOrAreaID();
            PreparedStatement ps_queryAreaID = null;
			ResultSet rs_queryAreaID=null;
			Connection conn=null;
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
                parentTotalQuota = wafd.getWaterQuotaInfoByYearBldID(String.valueOf(areaID), "0", bpwm.getYear());

                // 取得区域下面的所有建筑
                String queryChildArch = "SELECT Architecture_ID FROM Architecture WHERE Area_ID = " + areaID;
                Connection conn1=null;
        		PreparedStatement ps_queryChildArch = null;
				ResultSet rs_queryChildArch = null;
				try
				{
					conn1 = ConnDB.getConnection();
					ps_queryChildArch = conn1.prepareStatement(queryChildArch);
					rs_queryChildArch = ps_queryChildArch.executeQuery();
					while(rs_queryChildArch.next())
					{
						// 求出兄弟建筑的对象年定额情报（包括自己）
						parentTotalQuota -= wafd.getWaterQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpwm.getYear());                	
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
            iResult += bpwm.getM1();
            iResult += bpwm.getM2();
            iResult += bpwm.getM3();
            iResult += bpwm.getM4();
            iResult += bpwm.getM5();
            iResult += bpwm.getM6();
            iResult += bpwm.getM7();
            iResult += bpwm.getM8();
            iResult += bpwm.getM9();
            iResult += bpwm.getM10();
            iResult += bpwm.getM11();
            iResult += bpwm.getM12();
            thisTotalStandardQuota = bpwm.getTotalPeople() * bpwm.geteStandard() * iResult * bpwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		
		String sql = "UPDATE BLDQUTADETLBYPEOWATER SET BLDORAREA_ID=?,AORBFLG=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALPEOPLE=?,ESTANDARD=?,REMARK=? where VALUEID=" + bpwm.getValueID();
		
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, bpwm.getBldOrAreaID());
			ps.setInt(2, bpwm.getaOrbFlg());
			ps.setString(3, bpwm.getYear());
			ps.setString(4, bpwm.getName());
			ps.setDouble(5, bpwm.getPrice());
			ps.setInt(6, bpwm.getM1());
			ps.setInt(7, bpwm.getM2());
			ps.setInt(8, bpwm.getM3());
			ps.setInt(9, bpwm.getM4());
			ps.setInt(10, bpwm.getM5());
			ps.setInt(11, bpwm.getM6());
			ps.setInt(12, bpwm.getM7());
			ps.setInt(13, bpwm.getM8());
			ps.setInt(14, bpwm.getM9());
			ps.setInt(15, bpwm.getM10());
			ps.setInt(16, bpwm.getM11());
			ps.setInt(17, bpwm.getM12());
			ps.setInt(18, bpwm.getTotalPeople());
			ps.setDouble(19, bpwm.geteStandard());
			ps.setString(20, bpwm.getRemark());
			
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
	public BldQuotaByPeoWaterModel queryByID(int valueID) throws SQLException
	{
		BldQuotaByPeoWaterModel bpwm = new BldQuotaByPeoWaterModel();
		String sql = "Select * from BLDQUTADETLBYPEOWATER where VALUEID=" + valueID;

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
				bpwm.setValueID(valueID);
				bpwm.setBldOrAreaID(rs.getInt("BLDORAREA_ID"));
				bpwm.setaOrbFlg(rs.getInt("AORBFLG"));
				bpwm.setYear(rs.getString("YEAR"));
				bpwm.setName(rs.getString("NAME"));
				bpwm.setPrice(rs.getDouble("PRICE"));
				bpwm.setM1(rs.getInt("M1"));
				bpwm.setM2(rs.getInt("M2"));
				bpwm.setM3(rs.getInt("M3"));
				bpwm.setM4(rs.getInt("M4"));
				bpwm.setM5(rs.getInt("M5"));
				bpwm.setM6(rs.getInt("M6"));
				bpwm.setM7(rs.getInt("M7"));
				bpwm.setM8(rs.getInt("M8"));
				bpwm.setM9(rs.getInt("M9"));
				bpwm.setM10(rs.getInt("M10"));
				bpwm.setM11(rs.getInt("M11"));
				bpwm.setM12(rs.getInt("M12"));
				bpwm.setTotalPeople(rs.getInt("TOTALPEOPLE"));
				bpwm.seteStandard(rs.getDouble("ESTANDARD"));
				bpwm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return bpwm;
	}

}
