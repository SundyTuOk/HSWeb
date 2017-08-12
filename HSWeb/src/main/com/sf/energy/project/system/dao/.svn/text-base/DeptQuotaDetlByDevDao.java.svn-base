package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.system.model.DeptQuotaDetlByDevModel;
import com.sf.energy.util.ConnDB;

public class DeptQuotaDetlByDevDao
{
	/**
	 * 增
	 * @param ddm 
	 * @return
	 * @throws SQLException
	 */
	public String insert(DeptQuotaDetlByDevModel ddm) throws SQLException
	{
		String returnMsg = "";
		PartmentFixedDao pfd = new PartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+ddm.getPartmentID();
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
            double parentTotalQuota = pfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), ddm.getYear());

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
	            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddm.getYear());                	
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
//            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += ddm.getM1();
            iResult += ddm.getM2();
            iResult += ddm.getM3();
            iResult += ddm.getM4();
            iResult += ddm.getM5();
            iResult += ddm.getM6();
            iResult += ddm.getM7();
            iResult += ddm.getM8();
            iResult += ddm.getM9();
            iResult += ddm.getM10();
            iResult += ddm.getM11();
            iResult += ddm.getM12();
            thisTotalStandardQuota = ddm.getTotalDevice() * ddm.getPower() * ddm.getRunHoursPerMth() * iResult * ddm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "INSERT INTO DEPTQUTADETLBYDEV(PARTMENT_ID,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALDEVICE,POWER,RUNHOURSPERMTH,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn0=null;
		PreparedStatement ps =null;


		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, ddm.getPartmentID());
			ps.setString(2, ddm.getYear());
			ps.setString(3, ddm.getName());
			ps.setDouble(4, ddm.getPrice());
			ps.setInt(5, ddm.getM1());
			ps.setInt(6, ddm.getM2());
			ps.setInt(7, ddm.getM3());
			ps.setInt(8, ddm.getM4());
			ps.setInt(9, ddm.getM5());
			ps.setInt(10, ddm.getM6());
			ps.setInt(11, ddm.getM7());
			ps.setInt(12, ddm.getM8());
			ps.setInt(13, ddm.getM9());
			ps.setInt(14, ddm.getM10());
			ps.setInt(15, ddm.getM11());
			ps.setInt(16, ddm.getM12());
			ps.setInt(17, ddm.getTotalDevice());
			ps.setDouble(18, ddm.getPower());
			ps.setDouble(19, ddm.getRunHoursPerMth());
			ps.setString(20, ddm.getRemark());
			
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
		String sql = "delete FROM DEPTQUTADETLBYDEV where ValueId =  " + valueID;
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
	 * @param ddm
	 * @return
	 * @throws SQLException
	 */
	public String modify(DeptQuotaDetlByDevModel ddm) throws SQLException
	{
		String returnMsg = "";
		PartmentFixedDao pfd = new PartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+ddm.getPartmentID();
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
            double parentTotalQuota = pfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), ddm.getYear());

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
					parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddm.getYear());                	
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
//            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  ddm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            // 设备总数×功率×运行时间×适用月数×价格
            iResult += ddm.getM1();
            iResult += ddm.getM2();
            iResult += ddm.getM3();
            iResult += ddm.getM4();
            iResult += ddm.getM5();
            iResult += ddm.getM6();
            iResult += ddm.getM7();
            iResult += ddm.getM8();
            iResult += ddm.getM9();
            iResult += ddm.getM10();
            iResult += ddm.getM11();
            iResult += ddm.getM12();
            thisTotalStandardQuota = ddm.getTotalDevice() * ddm.getPower() * ddm.getRunHoursPerMth() * iResult * ddm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
                // 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "UPDATE DEPTQUTADETLBYDEV SET PARTMENT_ID=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALDEVICE=?,POWER=?,RUNHOURSPERMTH=?,REMARK=? where VALUEID=" + ddm.getValueID();
		
		Connection conn0=null;
		PreparedStatement ps =null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, ddm.getPartmentID());
			ps.setString(2, ddm.getYear());
			ps.setString(3, ddm.getName());
			ps.setDouble(4, ddm.getPrice());
			ps.setInt(5, ddm.getM1());
			ps.setInt(6, ddm.getM2());
			ps.setInt(7, ddm.getM3());
			ps.setInt(8, ddm.getM4());
			ps.setInt(9, ddm.getM5());
			ps.setInt(10, ddm.getM6());
			ps.setInt(11, ddm.getM7());
			ps.setInt(12, ddm.getM8());
			ps.setInt(13, ddm.getM9());
			ps.setInt(14, ddm.getM10());
			ps.setInt(15, ddm.getM11());
			ps.setInt(16, ddm.getM12());
			ps.setInt(17, ddm.getTotalDevice());
			ps.setDouble(18, ddm.getPower());
			ps.setDouble(19, ddm.getRunHoursPerMth());
			ps.setString(20, ddm.getRemark());
			
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
	public DeptQuotaDetlByDevModel queryByID(int valueID) throws SQLException
	{
		DeptQuotaDetlByDevModel ddm = new DeptQuotaDetlByDevModel();
		String sql = "Select * from DEPTQUTADETLBYDEV where VALUEID=" + valueID;
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
				ddm.setValueID(valueID);
				ddm.setPartmentID(rs.getInt("PARTMENT_ID"));
				ddm.setYear(rs.getString("YEAR"));
				ddm.setName(rs.getString("NAME"));
				ddm.setPrice(rs.getDouble("PRICE"));
				ddm.setM1(rs.getInt("M1"));
				ddm.setM2(rs.getInt("M2"));
				ddm.setM3(rs.getInt("M3"));
				ddm.setM4(rs.getInt("M4"));
				ddm.setM5(rs.getInt("M5"));
				ddm.setM6(rs.getInt("M6"));
				ddm.setM7(rs.getInt("M7"));
				ddm.setM8(rs.getInt("M8"));
				ddm.setM9(rs.getInt("M9"));
				ddm.setM10(rs.getInt("M10"));
				ddm.setM11(rs.getInt("M11"));
				ddm.setM12(rs.getInt("M12"));
				ddm.setTotalDevice(rs.getInt("TOTALDEVICE"));
				ddm.setPower(rs.getDouble("POWER"));
				ddm.setRunHoursPerMth(rs.getDouble("RUNHOURSPERMTH"));
				ddm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return ddm;
	}
	

}
