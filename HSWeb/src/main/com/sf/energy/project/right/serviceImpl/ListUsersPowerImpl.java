package com.sf.energy.project.right.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.service.ListUsersPower;
import com.sf.energy.util.ConnDB;

/**
 * 用户或角色相关权限信息的操作
 * 类中包含通过用户、角色信息查询用户权限、查询角色权限，判断某用户是否有某权限的操作
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public class ListUsersPowerImpl implements ListUsersPower
{
	/**
	 * 判断某一个用户是否有某权限
	 * @param usersID 用户ID
	 * @param powerID 权限ID
	 * @return 是否有权限
	 * @throws SQLException
	 */
	public boolean hasPowerByID(int usersID, int powerID) throws SQLException
	{
		boolean flag = false;
		String sql = "select COUNT(*) from users_roles a inner join Roles_Power b on a.Roles_ID=b.Roles_ID where a.Users_ID="
				+ usersID + " and b.Power_ID=" + powerID;

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
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}

	
	
	/**
	 * 判断某一个用户是否有某权限
	 * @param um 用户实体类的实例
	 * @param pm 权限实体类的实例
	 * @return  是否有权限
	 * @throws SQLException
	 */
	public boolean hasPower(UsersModel um, PowerModel pm) throws SQLException
	{
		boolean flag = false;
		String sql = "select COUNT(*) from users_roles a inner join Roles_Power b on a.Roles_ID=b.Roles_ID where a.Users_ID="
				+ um.getUsersID() + " and b.Power_ID=" + pm.getPowerID();

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
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();
		return flag;
	}
	
	/**
	 * 获取某角色的所有权限ID
	 * @param rm 角色实体类的实例
	 * @return 权限的ID的集合
	 * @throws SQLException
	 */
	public ArrayList<Integer> getRolesPowerID(RolesModel rm) throws SQLException
	{
		ArrayList<Integer> lst = new ArrayList<Integer>();
		String sql = "SELECT Power_ID from Roles_Power where Roles_ID="
				+ rm.getRolesID();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lst.add(rs.getInt(2));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	/**
	 * 获取某一个角色的所有权限信息
	 * @param rm 角色实体类的实例
	 * @return 权限实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> getRolesPower(RolesModel rm) throws SQLException
	{
		ArrayList<PowerModel> lst = new ArrayList<PowerModel>();
		PowerModel pm = new PowerModel();
		String sql = "SELECT * from Roles_Power a inner join Power b on a.Power_ID=b.Power_ID where Roles_ID="
				+ rm.getRolesID();

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				pm.setPowerID(rs.getInt(1));
				pm.setModuleID(rs.getInt(2));
				pm.setPowerNum(rs.getString(3));
				pm.setPowerName(rs.getString(4));
				pm.setPowerRemark(rs.getString(5));
				pm.setPowerLevel(rs.getInt(6));

				lst.add(pm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	/**
	 * 获取用户权限
	 * @param um 用户实体类的实例
	 * @return 权限ID的集合
	 * @throws SQLException
	 */
	public ArrayList<Integer> getUsersPowerID(UsersModel um) throws SQLException
	{
		ArrayList<Integer> lst = new ArrayList<Integer>();

		String sql = "SELECT a.Power_ID  from Power a inner join Roles_Power b on a.Power_ID=b.Power_ID inner join Users_Roles c on c.Roles_ID=b.Roles_ID where c.Users_ID="
				+ um.getUsersID();

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				lst.add(new Integer(rs.getInt(1)));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return  lst;
	}

	
	/**
	 * 获取某一个用户的所有权限
	 * @param um 用户实体类的实例
	 * @return 权限ID的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> getUsersPower(UsersModel um) throws SQLException
	{
		ArrayList<PowerModel> lst = new ArrayList<PowerModel>();
		
		PowerModel pm=null;

		String sql = "SELECT *  from Power a inner join Roles_Power b on a.Power_ID=b.Power_ID inner join Users_Roles c on c.Roles_ID=b.Roles_ID where c.Users_ID="
				+ um.getUsersID();

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				pm=new PowerModel();
				
				pm.setPowerID(rs.getInt(1));
				pm.setModuleID(rs.getInt(2));
				pm.setPowerNum(rs.getString(3));
				pm.setPowerName(rs.getString(4));
				pm.setPowerRemark(rs.getString(5));
				pm.setPowerLevel(rs.getInt(6));

				lst.add(pm);
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
