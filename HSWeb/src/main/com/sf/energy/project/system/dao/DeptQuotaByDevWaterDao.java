package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sf.energy.project.system.model.DeptQuotaByDevWaterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.WaterPartmentFixedDao;

public class DeptQuotaByDevWaterDao
{
	/**
	 * 增
	 * @param ddwm
	 * @return
	 * @throws SQLException
	 */
	public String insert(DeptQuotaByDevWaterModel ddwm) throws SQLException
	{
		String returnMsg = "";
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+ddwm.getPartmentID();
        Connection conn=null;
        PreparedStatement ps_queryParentID=null;
		ResultSet rs_queryParentID=null;
		try
		{
			conn = ConnDB.getConnection();
			ps_queryParentID = conn.prepareStatement(queryParentID);
			rs_queryParentID = ps_queryParentID.executeQuery();
			if(rs_queryParentID.next())
			{
	        	parentID = rs_queryParentID.getInt("Partment_Parent");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps_queryParentID, rs_queryParentID);
		}
//        Statement sm_queryParentID = ConnDB.getConnection().createStatement();
//        ResultSet rs_queryParentID = sm_queryParentID.executeQuery(queryParentID);
//        if(rs_queryParentID.next())
//        {
//        	parentID = rs_queryParentID.getInt("Partment_Parent");
//        }
//        if(rs_queryParentID != null)
//        	rs_queryParentID.close();
//        if(sm_queryParentID != null)
//        	sm_queryParentID.close();
        
        // 父部门的ID存在且不为全校（顶层）的时候，做定额超过检查
        if (parentID != -1 && parentID != 1)
        {
            
            // 父部门的总分配定额
            double parentTotalQuota = wpfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), ddwm.getYear());

            // 取得父部门ID其下面的所有直接子部门，不包涵孙子部门(兄弟部门)
            String queryChildArch = "SELECT Partment_ID, Partment_Name FROM Partment WHERE Partment_Parent ='" + parentID + "' ORDER BY Partment_Num";
            Connection conn1=null;
            PreparedStatement ps_queryChildDept =null;
			ResultSet rs_queryChildDept =null;
			try
			{
				conn1=ConnDB.getConnection();
				ps_queryChildDept = conn1.prepareStatement(queryChildArch);
				rs_queryChildDept = ps_queryChildDept.executeQuery();
				while(rs_queryChildDept.next())
				{
					// 求出兄弟建筑的对象年定额情报（包括自己）
	            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddwm.getYear());                	
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps_queryChildDept, rs_queryChildDept);
			}
//            Statement sm_queryChildDept = ConnDB.getConnection().createStatement();
//            ResultSet rs_queryChildDept = sm_queryChildDept.executeQuery(queryChildArch);
//            while(rs_queryChildDept.next())
//            {
//            	// 求出兄弟建筑的对象年定额情报（包括自己）
//            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddwm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += ddwm.getM1();
            iResult += ddwm.getM2();
            iResult += ddwm.getM3();
            iResult += ddwm.getM4();
            iResult += ddwm.getM5();
            iResult += ddwm.getM6();
            iResult += ddwm.getM7();
            iResult += ddwm.getM8();
            iResult += ddwm.getM9();
            iResult += ddwm.getM10();
            iResult += ddwm.getM11();
            iResult += ddwm.getM12();
            thisTotalStandardQuota = ddwm.getTotalDevice() * ddwm.getPower() * ddwm.getRunHoursPerMth() * iResult * ddwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "INSERT INTO DEPTQUTADETLBYDEVWATER(PARTMENT_ID,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALDEVICE,POWER,RUNHOURSPERMTH,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{

			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, ddwm.getPartmentID());
			ps.setString(2, ddwm.getYear());
			ps.setString(3, ddwm.getName());
			ps.setDouble(4, ddwm.getPrice());
			ps.setInt(5, ddwm.getM1());
			ps.setInt(6, ddwm.getM2());
			ps.setInt(7, ddwm.getM3());
			ps.setInt(8, ddwm.getM4());
			ps.setInt(9, ddwm.getM5());
			ps.setInt(10, ddwm.getM6());
			ps.setInt(11, ddwm.getM7());
			ps.setInt(12, ddwm.getM8());
			ps.setInt(13, ddwm.getM9());
			ps.setInt(14, ddwm.getM10());
			ps.setInt(15, ddwm.getM11());
			ps.setInt(16, ddwm.getM12());
			ps.setInt(17, ddwm.getTotalDevice());
			ps.setDouble(18, ddwm.getPower());
			ps.setDouble(19, ddwm.getRunHoursPerMth());
			ps.setString(20, ddwm.getRemark());
			
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
		String sql = "delete FROM DEPTQUTADETLBYDEVWATER where ValueId =  " + valueID;
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
	 * @param ddwm
	 * @return
	 * @throws SQLException
	 */
	public String modify(DeptQuotaByDevWaterModel ddwm) throws SQLException
	{
		String returnMsg = "";
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+ddwm.getPartmentID();
        Connection conn=null;
        PreparedStatement ps_queryParentID=null;
		ResultSet rs_queryParentID=null;
		try
		{
			conn = ConnDB.getConnection();
			ps_queryParentID = conn.prepareStatement(queryParentID);
			rs_queryParentID = ps_queryParentID.executeQuery();
			if(rs_queryParentID.next())
			{
	        	parentID = rs_queryParentID.getInt("Partment_Parent");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps_queryParentID, rs_queryParentID);
		}
//        Statement sm_queryParentID = ConnDB.getConnection().createStatement();
//        ResultSet rs_queryParentID = sm_queryParentID.executeQuery(queryParentID);
//        if(rs_queryParentID.next())
//        {
//        	parentID = rs_queryParentID.getInt("Partment_Parent");
//        }
//        if(rs_queryParentID != null)
//        	rs_queryParentID.close();
//        if(sm_queryParentID != null)
//        	sm_queryParentID.close();
        
        // 父部门的ID存在且不为全校（顶层）的时候，做定额超过检查
        if (parentID != -1 && parentID != 1)
        {
            
            // 父部门的总分配定额
            double parentTotalQuota = wpfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), ddwm.getYear());

            // 取得父部门ID其下面的所有直接子部门，不包涵孙子部门(兄弟部门)
            String queryChildArch = "SELECT Partment_ID, Partment_Name FROM Partment WHERE Partment_Parent ='" + parentID + "' ORDER BY Partment_Num";
            Connection conn1=null;
            PreparedStatement ps_queryChildDept =null;
			ResultSet rs_queryChildDept =null;
			try
			{
				conn1=ConnDB.getConnection();
				ps_queryChildDept = conn1.prepareStatement(queryChildArch);
				rs_queryChildDept = ps_queryChildDept.executeQuery();
				while(rs_queryChildDept.next())
				{
					// 求出兄弟建筑的对象年定额情报（包括自己）
	            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddwm.getYear());                	
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps_queryChildDept, rs_queryChildDept);
			}
//            Statement sm_queryChildDept = ConnDB.getConnection().createStatement();
//            ResultSet rs_queryChildDept = sm_queryChildDept.executeQuery(queryChildArch);
//            while(rs_queryChildDept.next())
//            {
//            	// 求出兄弟建筑的对象年定额情报（包括自己）
//            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddwm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += ddwm.getM1();
            iResult += ddwm.getM2();
            iResult += ddwm.getM3();
            iResult += ddwm.getM4();
            iResult += ddwm.getM5();
            iResult += ddwm.getM6();
            iResult += ddwm.getM7();
            iResult += ddwm.getM8();
            iResult += ddwm.getM9();
            iResult += ddwm.getM10();
            iResult += ddwm.getM11();
            iResult += ddwm.getM12();
            thisTotalStandardQuota = ddwm.getTotalDevice() * ddwm.getPower() * ddwm.getRunHoursPerMth() * iResult * ddwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "UPDATE DEPTQUTADETLBYDEVWATER SET PARTMENT_ID=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALDEVICE=?,POWER=?,RUNHOURSPERMTH=?,REMARK=? where VALUEID=" + ddwm.getValueID();
		
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{

			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, ddwm.getPartmentID());
			ps.setString(2, ddwm.getYear());
			ps.setString(3, ddwm.getName());
			ps.setDouble(4, ddwm.getPrice());
			ps.setInt(5, ddwm.getM1());
			ps.setInt(6, ddwm.getM2());
			ps.setInt(7, ddwm.getM3());
			ps.setInt(8, ddwm.getM4());
			ps.setInt(9, ddwm.getM5());
			ps.setInt(10, ddwm.getM6());
			ps.setInt(11, ddwm.getM7());
			ps.setInt(12, ddwm.getM8());
			ps.setInt(13, ddwm.getM9());
			ps.setInt(14, ddwm.getM10());
			ps.setInt(15, ddwm.getM11());
			ps.setInt(16, ddwm.getM12());
			ps.setInt(17, ddwm.getTotalDevice());
			ps.setDouble(18, ddwm.getPower());
			ps.setDouble(19, ddwm.getRunHoursPerMth());
			ps.setString(20, ddwm.getRemark());
			
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
	 * 根据ID查询
	 * @param valueID
	 * @return
	 * @throws SQLException
	 */
	public DeptQuotaByDevWaterModel queryByID(int valueID) throws SQLException
	{
		DeptQuotaByDevWaterModel ddwm = new DeptQuotaByDevWaterModel();
		String sql = "Select * from DEPTQUTADETLBYDEVWATER where VALUEID=" + valueID;

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
				ddwm.setValueID(valueID);
				ddwm.setPartmentID(rs.getInt("PARTMENT_ID"));
				ddwm.setYear(rs.getString("YEAR"));
				ddwm.setName(rs.getString("NAME"));
				ddwm.setPrice(rs.getFloat("PRICE"));
				ddwm.setM1(rs.getInt("M1"));
				ddwm.setM2(rs.getInt("M2"));
				ddwm.setM3(rs.getInt("M3"));
				ddwm.setM4(rs.getInt("M4"));
				ddwm.setM5(rs.getInt("M5"));
				ddwm.setM6(rs.getInt("M6"));
				ddwm.setM7(rs.getInt("M7"));
				ddwm.setM8(rs.getInt("M8"));
				ddwm.setM9(rs.getInt("M9"));
				ddwm.setM10(rs.getInt("M10"));
				ddwm.setM11(rs.getInt("M11"));
				ddwm.setM12(rs.getInt("M12"));
				ddwm.setTotalDevice(rs.getInt("TOTALDEVICE"));
				ddwm.setPower(rs.getFloat("POWER"));
				ddwm.setRunHoursPerMth(rs.getFloat("RUNHOURSPERMTH"));
				ddwm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return ddwm;
	}

}
