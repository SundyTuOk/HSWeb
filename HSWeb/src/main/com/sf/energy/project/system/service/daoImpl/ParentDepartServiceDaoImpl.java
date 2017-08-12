package com.sf.energy.project.system.service.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.system.service.dao.ParentDepartServiceDao;
import com.sf.energy.util.ConnDB;

/**
 * 通过对接口的实现，进行父部门的查找的查找操作
 * 
 * @author 李涵淼
 * @version 1.0
 * @see Department.java,FindParentDepart.java,ConnDB.java 
 * @since version 1.0 
 * 
 */

public class ParentDepartServiceDaoImpl implements ParentDepartServiceDao
{

	String parentName;

	/**
	 * 通过传入子部门的名称查找父部门的名称，并返回
	 * 
	 * @param name
	 *            传入子部门的名称
	 * @return parentName 父部门的名称
	 * @throws SQLException 
	 */
	public String findParentDepartName(String name) throws SQLException
	{
		String sql = "select Partment_Parent from partment where Partment_Name='"
					+ name + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					sql = "select Partment_Name from partment where Partment_ID="
							+ rs.getString("Partment_Parent") + "";
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			Connection conn1=null;
			PreparedStatement ps1 =null;
			ResultSet rs1 = null;

			try
			{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					parentName = rs1.getString("Partment_Name");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn1, ps1, rs1);
			}
		return parentName;

	}

}
