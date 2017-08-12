package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sf.energy.project.system.model.DeptQuotaDetlByPeoModel;
import com.sf.energy.util.ConnDB;

public class DeptQuotaDetlByPeoDao
{

	/**
	 * 增
	 * @param dpm
	 * @return
	 * @throws SQLException
	 */
	public String insert(DeptQuotaDetlByPeoModel dpm) throws SQLException
	{
		String returnMsg = "";
		PartmentFixedDao pfd = new PartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+dpm.getPartmentID();
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
            double parentTotalQuota = pfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), dpm.getYear());

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
	            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpm.getYear());                	
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
//            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            //人员总数×用电标准×适用月数×价格
            iResult += dpm.getM1();
            iResult += dpm.getM2();
            iResult += dpm.getM3();
            iResult += dpm.getM4();
            iResult += dpm.getM5();
            iResult += dpm.getM6();
            iResult += dpm.getM7();
            iResult += dpm.getM8();
            iResult += dpm.getM9();
            iResult += dpm.getM10();
            iResult += dpm.getM11();
            iResult += dpm.getM12();
            thisTotalStandardQuota = dpm.getTotalPeople() * dpm.geteStandard() * iResult * dpm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
            	// 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }

		String sql = "INSERT INTO DEPTQUTADETLBYPEO(PARTMENT_ID,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALPEOPLE,ESTANDARD,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn0=null;
		PreparedStatement ps =null;
		try
		{

			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, dpm.getPartmentID());
			ps.setString(2, dpm.getYear());
			ps.setString(3, dpm.getName());
			ps.setDouble(4, dpm.getPrice());
			ps.setInt(5, dpm.getM1());
			ps.setInt(6, dpm.getM2());
			ps.setInt(7, dpm.getM3());
			ps.setInt(8, dpm.getM4());
			ps.setInt(9, dpm.getM5());
			ps.setInt(10, dpm.getM6());
			ps.setInt(11, dpm.getM7());
			ps.setInt(12, dpm.getM8());
			ps.setInt(13, dpm.getM9());
			ps.setInt(14, dpm.getM10());
			ps.setInt(15, dpm.getM11());
			ps.setInt(16, dpm.getM12());
			ps.setInt(17, dpm.getTotalPeople());
			ps.setDouble(18, dpm.geteStandard());
			ps.setString(19, dpm.getRemark());
			
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
		String sql = "delete FROM DEPTQUTADETLBYPEO where ValueId =  " + valueID;
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
	 * @param dpm
	 * @return
	 * @throws SQLException
	 */
	public String modify(DeptQuotaDetlByPeoModel dpm) throws SQLException
	{
		String returnMsg = "";
		PartmentFixedDao pfd = new PartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+dpm.getPartmentID();
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
            double parentTotalQuota = pfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), dpm.getYear());

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
	            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpm.getYear());                	
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
//            	parentTotalQuota -= pfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            //人员总数×用电标准×适用月数×价格
            iResult += dpm.getM1();
            iResult += dpm.getM2();
            iResult += dpm.getM3();
            iResult += dpm.getM4();
            iResult += dpm.getM5();
            iResult += dpm.getM6();
            iResult += dpm.getM7();
            iResult += dpm.getM8();
            iResult += dpm.getM9();
            iResult += dpm.getM10();
            iResult += dpm.getM11();
            iResult += dpm.getM12();
            thisTotalStandardQuota = dpm.getTotalPeople() * dpm.geteStandard() * iResult * dpm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
            	// 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "UPDATE DEPTQUTADETLBYPEO SET PARTMENT_ID=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALPEOPLE=?,ESTANDARD=?,REMARK=? where VALUEID=" + dpm.getValueID();
		
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, dpm.getPartmentID());
			ps.setString(2, dpm.getYear());
			ps.setString(3, dpm.getName());
			ps.setDouble(4, dpm.getPrice());
			ps.setInt(5, dpm.getM1());
			ps.setInt(6, dpm.getM2());
			ps.setInt(7, dpm.getM3());
			ps.setInt(8, dpm.getM4());
			ps.setInt(9, dpm.getM5());
			ps.setInt(10, dpm.getM6());
			ps.setInt(11, dpm.getM7());
			ps.setInt(12, dpm.getM8());
			ps.setInt(13, dpm.getM9());
			ps.setInt(14, dpm.getM10());
			ps.setInt(15, dpm.getM11());
			ps.setInt(16, dpm.getM12());
			ps.setInt(17, dpm.getTotalPeople());
			ps.setDouble(18, dpm.geteStandard());
			ps.setString(19, dpm.getRemark());
			
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
	 * @param valueID 值ID
	 * @return
	 * @throws SQLException
	 */
	public DeptQuotaDetlByPeoModel queryByID(int valueID) throws SQLException
	{
		DeptQuotaDetlByPeoModel dpm = new DeptQuotaDetlByPeoModel();
		String sql = "Select * from DEPTQUTADETLBYPEO where VALUEID=" + valueID;

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
				dpm.setValueID(valueID);
				dpm.setPartmentID(rs.getInt("PARTMENT_ID"));
				dpm.setYear(rs.getString("YEAR"));
				dpm.setName(rs.getString("NAME"));
				dpm.setPrice(rs.getDouble("PRICE"));
				dpm.setM1(rs.getInt("M1"));
				dpm.setM2(rs.getInt("M2"));
				dpm.setM3(rs.getInt("M3"));
				dpm.setM4(rs.getInt("M4"));
				dpm.setM5(rs.getInt("M5"));
				dpm.setM6(rs.getInt("M6"));
				dpm.setM7(rs.getInt("M7"));
				dpm.setM8(rs.getInt("M8"));
				dpm.setM9(rs.getInt("M9"));
				dpm.setM10(rs.getInt("M10"));
				dpm.setM11(rs.getInt("M11"));
				dpm.setM12(rs.getInt("M12"));
				dpm.setTotalPeople(rs.getInt("TOTALPEOPLE"));
				dpm.seteStandard(rs.getDouble("ESTANDARD"));
				dpm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return dpm;
	}
}
