package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.system.model.DeptQuotaByPeoWaterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.WaterPartmentFixedDao;

public class DeptQuotaByPeoWaterDao
{
	/**
	 * 增
	 * @param dpwm 
	 * @return
	 * @throws SQLException
	 */
	public String insert(DeptQuotaByPeoWaterModel dpwm) throws SQLException
	{
		String returnMsg = "";
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+dpwm.getPartmentID();
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
            double parentTotalQuota = wpfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), dpwm.getYear());

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
	            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpwm.getYear());                	
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
//            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpwm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            //人员总数×用电标准×适用月数×价格
            iResult += dpwm.getM1();
            iResult += dpwm.getM2();
            iResult += dpwm.getM3();
            iResult += dpwm.getM4();
            iResult += dpwm.getM5();
            iResult += dpwm.getM6();
            iResult += dpwm.getM7();
            iResult += dpwm.getM8();
            iResult += dpwm.getM9();
            iResult += dpwm.getM10();
            iResult += dpwm.getM11();
            iResult += dpwm.getM12();
            thisTotalStandardQuota = dpwm.getTotalPeople() * dpwm.geteStandard() * iResult * dpwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
            	// 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "INSERT INTO DEPTQUTADETLBYPEOWATER(PARTMENT_ID,YEAR,NAME,PRICE," +
				"M1,M2,M3,M4,M5,M6,M7,M8,M9,M10,M11,M12,TOTALPEOPLE,ESTANDARD,REMARK)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn0=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, dpwm.getPartmentID());
			ps.setString(2, dpwm.getYear());
			ps.setString(3, dpwm.getName());
			ps.setDouble(4, dpwm.getPrice());
			ps.setInt(5, dpwm.getM1());
			ps.setInt(6, dpwm.getM2());
			ps.setInt(7, dpwm.getM3());
			ps.setInt(8, dpwm.getM4());
			ps.setInt(9, dpwm.getM5());
			ps.setInt(10, dpwm.getM6());
			ps.setInt(11, dpwm.getM7());
			ps.setInt(12, dpwm.getM8());
			ps.setInt(13, dpwm.getM9());
			ps.setInt(14, dpwm.getM10());
			ps.setInt(15, dpwm.getM11());
			ps.setInt(16, dpwm.getM12());
			ps.setInt(17, dpwm.getTotalPeople());
			ps.setDouble(18, dpwm.geteStandard());
			ps.setString(19, dpwm.getRemark());
			
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
			ConnDB.release(conn0, ps, rs);
		}
		
		return returnMsg;
	}
	
	/**
	 * 删
	 * @param valueID 值ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int valueID) throws SQLException
	{
		String sql = "delete FROM DEPTQUTADETLBYPEOWATER where ValueId =  " + valueID;
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
	 * @param dpwm
	 * @return
	 * @throws SQLException
	 */
	public String modify(DeptQuotaByPeoWaterModel dpwm) throws SQLException
	{
		String returnMsg = "";
		WaterPartmentFixedDao wpfd = new WaterPartmentFixedDao();
		int parentID = -1;
		
		// 父部门的ID
        String queryParentID = "SELECT Partment_Parent FROM Partment WHERE Partment_ID = "+dpwm.getPartmentID();
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
            double parentTotalQuota = wpfd.getQuotaInfoByYearDeptID(String.valueOf(parentID), dpwm.getYear());

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
	            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpwm.getYear());                	
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
//            	parentTotalQuota -= wpfd.getQuotaInfoByYearDeptID(rs_queryChildDept.getString("Partment_ID"),  dpwm.getYear());                	
//            }
//            if(rs_queryChildDept != null)
//            	rs_queryChildDept.close();
//            if(sm_queryChildDept != null)
//            	sm_queryChildDept.close();

            // 本次标准分配定额计算
            double thisTotalStandardQuota = 0;
            int iResult = 0;
            //人员总数×用电标准×适用月数×价格
            iResult += dpwm.getM1();
            iResult += dpwm.getM2();
            iResult += dpwm.getM3();
            iResult += dpwm.getM4();
            iResult += dpwm.getM5();
            iResult += dpwm.getM6();
            iResult += dpwm.getM7();
            iResult += dpwm.getM8();
            iResult += dpwm.getM9();
            iResult += dpwm.getM10();
            iResult += dpwm.getM11();
            iResult += dpwm.getM12();
            thisTotalStandardQuota = dpwm.getTotalPeople() * dpwm.geteStandard() * iResult * dpwm.getPrice();

            // 做定额超过检查
            if (parentTotalQuota < thisTotalStandardQuota)
            {
            	// 定额超过
            	returnMsg = "定额超过(剩余定额：" + parentTotalQuota + ")";
                return returnMsg;
            }
        }
		
		String sql = "UPDATE DEPTQUTADETLBYPEOWATER SET PARTMENT_ID=?,YEAR=?,NAME=?,PRICE=?," +
				"M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,M7=?,M8=?,M9=?,M10=?,M11=?,M12=?," +
				"TOTALPEOPLE=?,ESTANDARD=?,REMARK=? where VALUEID=" + dpwm.getValueID();
		Connection conn0 = null;
		PreparedStatement ps=null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			
			ps.setInt(1, dpwm.getPartmentID());
			ps.setString(2, dpwm.getYear());
			ps.setString(3, dpwm.getName());
			ps.setDouble(4, dpwm.getPrice());
			ps.setInt(5, dpwm.getM1());
			ps.setInt(6, dpwm.getM2());
			ps.setInt(7, dpwm.getM3());
			ps.setInt(8, dpwm.getM4());
			ps.setInt(9, dpwm.getM5());
			ps.setInt(10, dpwm.getM6());
			ps.setInt(11, dpwm.getM7());
			ps.setInt(12, dpwm.getM8());
			ps.setInt(13, dpwm.getM9());
			ps.setInt(14, dpwm.getM10());
			ps.setInt(15, dpwm.getM11());
			ps.setInt(16, dpwm.getM12());
			ps.setInt(17, dpwm.getTotalPeople());
			ps.setDouble(18, dpwm.geteStandard());
			ps.setString(19, dpwm.getRemark());
			
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
	public DeptQuotaByPeoWaterModel queryByID(int valueID) throws SQLException
	{
		DeptQuotaByPeoWaterModel dpwm = new DeptQuotaByPeoWaterModel();
		String sql = "Select * from DEPTQUTADETLBYPEOWATER where VALUEID=" + valueID;

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
				dpwm.setValueID(valueID);
				dpwm.setPartmentID(rs.getInt("PARTMENT_ID"));
				dpwm.setYear(rs.getString("YEAR"));
				dpwm.setName(rs.getString("NAME"));
				dpwm.setPrice(rs.getFloat("PRICE"));
				dpwm.setM1(rs.getInt("M1"));
				dpwm.setM2(rs.getInt("M2"));
				dpwm.setM3(rs.getInt("M3"));
				dpwm.setM4(rs.getInt("M4"));
				dpwm.setM5(rs.getInt("M5"));
				dpwm.setM6(rs.getInt("M6"));
				dpwm.setM7(rs.getInt("M7"));
				dpwm.setM8(rs.getInt("M8"));
				dpwm.setM9(rs.getInt("M9"));
				dpwm.setM10(rs.getInt("M10"));
				dpwm.setM11(rs.getInt("M11"));
				dpwm.setM12(rs.getInt("M12"));
				dpwm.setTotalPeople(rs.getInt("TOTALPEOPLE"));
				dpwm.seteStandard(rs.getFloat("ESTANDARD"));
				dpwm.setRemark(rs.getString("REMARK"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return dpwm;
	}

}
