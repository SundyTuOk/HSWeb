package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.system.model.BldQuotaDetlByPeoModel;
import com.sf.energy.util.ConnDB;

public class BldQuotaDetlByPeoDao
{
	/**
	 * 增
	 * @param bpm
	 * @return
	 * @throws SQLException
	 */
	public String  insert(BldQuotaDetlByPeoModel bpm) throws SQLException
	{
    	String returnMsg = "";
    	ArchitectureFixedDao afd = new ArchitectureFixedDao();
		if(bpm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bpm.getBldOrAreaID();
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
                parentTotalQuota = afd.getQuotaInfoByYearBldID(String.valueOf(areaID), "0", bpm.getYear());

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
	                	parentTotalQuota -= afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
//                Statement sm_queryChildArch = ConnDB.getConnection().createStatement();
//                ResultSet rs_queryChildArch = sm_queryChildArch.executeQuery(queryChildArch);
//                while(rs_queryChildArch.next())
//                {
//                	// 求出兄弟建筑的对象年定额情报（包括自己）
//                	parentTotalQuota -= afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpm.getYear());                	
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
            iResult += bpm.getM1();
            iResult += bpm.getM2();
            iResult += bpm.getM3();
            iResult += bpm.getM4();
            iResult += bpm.getM5();
            iResult += bpm.getM6();
            iResult += bpm.getM7();
            iResult += bpm.getM8();
            iResult += bpm.getM9();
            iResult += bpm.getM10();
            iResult += bpm.getM11();
            iResult += bpm.getM12();
            thisTotalStandardQuota = bpm.getTotalPeople() * bpm.geteStandard() * iResult * bpm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		String sql = "INSERT INTO BLDQUTADETLBYPEO(BLDORAREA_ID,AORBFLG,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALPEOPLE,ESTANDARD,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bpm.getBldOrAreaID());
			ps.setInt(2, bpm.getaOrbFlg());
			ps.setString(3, bpm.getYear());
			ps.setString(4, bpm.getName());
			ps.setDouble(5, bpm.getPrice());
			ps.setInt(6, bpm.getM1());
			ps.setInt(7, bpm.getM2());
			ps.setInt(8, bpm.getM3());
			ps.setInt(9, bpm.getM4());
			ps.setInt(10, bpm.getM5());
			ps.setInt(11, bpm.getM6());
			ps.setInt(12, bpm.getM7());
			ps.setInt(13, bpm.getM8());
			ps.setInt(14, bpm.getM9());
			ps.setInt(15, bpm.getM10());
			ps.setInt(16, bpm.getM11());
			ps.setInt(17, bpm.getM12());
			ps.setInt(18, bpm.getTotalPeople());
			ps.setDouble(19, bpm.geteStandard());
			ps.setString(20, bpm.getRemark());
			
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
		String sql = "delete FROM BldQutaDetlByPeo where ValueId =  " + valueID;
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
	 * @param bpm
	 * @return
	 * @throws SQLException
	 */
	public String modify(BldQuotaDetlByPeoModel bpm) throws SQLException
	{
		String returnMsg = "";
		ArchitectureFixedDao afd = new ArchitectureFixedDao();
		if(bpm.getaOrbFlg() == 1)
		{
			double parentTotalQuota = 0;     // 区域的总分配定额
            int areaID = -1;            // 所属的区域ID

            // 求出所属的区域ID
            String queryAreaID = "select Area_ID from Architecture where Architecture_ID="+bpm.getBldOrAreaID();
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
//            Statement sm_queryAreaID = ConnDB.getConnection().createStatement();
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
                parentTotalQuota = afd.getQuotaInfoByYearBldID(String.valueOf(areaID), "0", bpm.getYear());

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
	                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpm.getYear());                	
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps_queryChildArch, rs_queryChildArch);
				}
//                Statement sm_queryChildArch = ConnDB.getConnection().createStatement();
//                ResultSet rs_queryChildArch = sm_queryChildArch.executeQuery(queryChildArch);
//                while(rs_queryChildArch.next())
//                {
//                	// 求出兄弟建筑的对象年定额情报（包括自己）
//                	parentTotalQuota -=  afd.getQuotaInfoByYearBldID(rs_queryChildArch.getString("Architecture_ID"), "1", bpm.getYear());                	
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
            iResult += bpm.getM1();
            iResult += bpm.getM2();
            iResult += bpm.getM3();
            iResult += bpm.getM4();
            iResult += bpm.getM5();
            iResult += bpm.getM6();
            iResult += bpm.getM7();
            iResult += bpm.getM8();
            iResult += bpm.getM9();
            iResult += bpm.getM10();
            iResult += bpm.getM11();
            iResult += bpm.getM12();
            thisTotalStandardQuota = bpm.getTotalPeople() * bpm.geteStandard() * iResult * bpm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
		}
		String sql = "UPDATE BLDQUTADETLBYPEO SET BLDORAREA_ID=?,AORBFLG=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALPEOPLE=?,ESTANDARD=?,REMARK=? where VALUEID=" + bpm.getValueID();
		
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, bpm.getBldOrAreaID());
			ps.setInt(2, bpm.getaOrbFlg());
			ps.setString(3, bpm.getYear());
			ps.setString(4, bpm.getName());
			ps.setDouble(5, bpm.getPrice());
			ps.setInt(6, bpm.getM1());
			ps.setInt(7, bpm.getM2());
			ps.setInt(8, bpm.getM3());
			ps.setInt(9, bpm.getM4());
			ps.setInt(10, bpm.getM5());
			ps.setInt(11, bpm.getM6());
			ps.setInt(12, bpm.getM7());
			ps.setInt(13, bpm.getM8());
			ps.setInt(14, bpm.getM9());
			ps.setInt(15, bpm.getM10());
			ps.setInt(16, bpm.getM11());
			ps.setInt(17, bpm.getM12());
			ps.setInt(18, bpm.getTotalPeople());
			ps.setDouble(19, bpm.geteStandard());
			ps.setString(20, bpm.getRemark());
			
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
	public BldQuotaDetlByPeoModel queryByID(int valueID) throws SQLException
	{
		BldQuotaDetlByPeoModel bpm = new BldQuotaDetlByPeoModel();
		String sql = "Select * from BLDQUTADETLBYPEO where VALUEID=" + valueID;

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
				bpm.setValueID(valueID);
				bpm.setBldOrAreaID(rs.getInt("BLDORAREA_ID"));
				bpm.setaOrbFlg(rs.getInt("AORBFLG"));
				bpm.setYear(rs.getString("YEAR"));
				bpm.setName(rs.getString("NAME"));
				bpm.setPrice(rs.getFloat("PRICE"));
				bpm.setM1(rs.getInt("M1"));
				bpm.setM2(rs.getInt("M2"));
				bpm.setM3(rs.getInt("M3"));
				bpm.setM4(rs.getInt("M4"));
				bpm.setM5(rs.getInt("M5"));
				bpm.setM6(rs.getInt("M6"));
				bpm.setM7(rs.getInt("M7"));
				bpm.setM8(rs.getInt("M8"));
				bpm.setM9(rs.getInt("M9"));
				bpm.setM10(rs.getInt("M10"));
				bpm.setM11(rs.getInt("M11"));
				bpm.setM12(rs.getInt("M12"));
				bpm.setTotalPeople(rs.getInt("TOTALPEOPLE"));
				bpm.seteStandard(rs.getFloat("ESTANDARD"));
				bpm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return bpm;
	}
	
}
