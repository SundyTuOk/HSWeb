package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;








import com.sf.energy.project.right.model.RolesPowerModel;
import com.sf.energy.util.ConnDB;

/**
 * 对角色权限表【RolesPower】操作， 实现角色权限表【RolesPower】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class RolesPowerDao
{
	/**
	 * 查询角色权限信息
	 * 
	 * @param id 角色ID
	 * @return rpm 角色权限实体类的实例
	 * @throws SQLException
	 */
	public RolesPowerModel query(int id) throws SQLException
	{
		RolesPowerModel rpm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql = "SELECT * FROM Roles_Power WHERE Roles_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				rpm = new RolesPowerModel();
				rpm.setRolesID(rs.getInt(1));
				rpm.setPowerID(rs.getInt(2));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return rpm;
	}

	/**
	 * 插入角色权限信息
	 * 
	 * @param rpm 角色权限实体类的实例
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(RolesPowerModel rpm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO Roles_Power(Roles_ID,Power_ID) VALUES("
					+ rpm.getRolesID() + "," + rpm.getPowerID() + ")";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			b = (ps.executeUpdate() >= 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 删除角色权限信息
	 * 
	 * @param id 角色ID
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		//ps.executeUpdate() 返回的是行数
		boolean b =false;
		try
		{
			String sql = "DELETE FROM Roles_Power WHERE Roles_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() >= 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 修改角色权限信息
	 * 
	 * @param rpm 角色权限实体类的实例
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(RolesPowerModel rpm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE Roles_Power SET Power_ID=" + rpm.getPowerID()
					+ "WHERE Roles_ID=" + rpm.getRolesID();
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			b = (ps.executeUpdate() >= 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 列出角色所拥有的权限ID
	 * @param roleID
	 * @return 权限的列表
	 * @throws SQLException
	 */
	public ArrayList<Integer > listRolesPower(int roleID) throws SQLException
	{
		String sql="select Power_ID from Roles_Power where Roles_ID="+roleID;
		ArrayList<Integer> lst=new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();		
			while(rs.next())
			{
				lst.add(rs.getInt("Power_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return lst;
	}
	
	

}
