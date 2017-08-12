package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.right.model.UsersRolesModel;
import com.sf.energy.util.ConnDB;

/**
 * 对用户角色表【Users_Roles】操作， 实现用户角色表【Users_Roles】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class UsersRolesDao
{
	/**
	 * 查询用户角色信息
	 * 
	 * @param id 用户ID
	 * @return  用户角色实体类的实例
	 * @throws SQLException
	 */
	public UsersRolesModel query(int id) throws SQLException
	{
		UsersRolesModel urm =null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Users_Roles WHERE Users_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				urm = new UsersRolesModel();
				urm.setUsersID(rs.getInt(1));
				urm.setRolesID(rs.getInt(2));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return urm;
	}

	/**
	 * 插入用户角色信息
	 * 
	 * @param urm 用户角色实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(UsersRolesModel urm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO Users_Roles(Users_ID,Roles_ID) VALUES("
					+ urm.getUsersID() + "," + urm.getRolesID() + ")";
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
	 * 删除用户角色信息
	 * 
	 * @param id 用户ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "DELETE FROM Users_Roles WHERE Users_ID=" + id;
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
	 * 修改用户角色信息
	 * 
	 * @param urm 用户角色实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(UsersRolesModel urm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE Users_Roles SET Roles_ID=" + urm.getRolesID()
					+ "WHERE Users_ID=" + urm.getUsersID();
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
}
