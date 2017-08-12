package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.right.model.UsersPartmentModel;
import com.sf.energy.util.ConnDB;

/**
 * 对用户部门表【OprPartment_List】操作， 实现用户部门表【OprPartment_List】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class UsersPartmentDao
{
	/**
	 * 查询用户部门权限
	 * 
	 * @param id 用户ID
	 * @return 用户部门实体类的实例
	 * @throws SQLException
	 */
	public UsersPartmentModel query(int id) throws SQLException
	{
		UsersPartmentModel upm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql = "SELECT * FROM OprPartment_List WHERE Users_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				upm = new UsersPartmentModel();
				upm.setUsersID(rs.getInt(1));
				upm.setPartmentID(rs.getInt(2));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return upm;
	}

	/**
	 * 插入用户部门权限
	 * 
	 * @param upm 用户部门实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(UsersPartmentModel upm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO OprPartment_List(Users_ID,Partment_ID) VALUES("
					+ upm.getUsersID() + "," + upm.getPartmentID() + ")";
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
	 * 删除用户部门权限
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
			String sql = "DELETE  FROM OprPartment_List WHERE Users_ID=" + id;
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
	 * 修改用户部门权限
	 * 
	 * @param upm 用户部门实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(UsersPartmentModel upm) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE OprPartment_List SET Partment_ID=" + upm.getPartmentID()
					+ "WHERE Users_ID=" + upm.getUsersID();
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
	 * 查询用户部门权限
	 * 
	 * @param id 用户ID
	 * @return  用户权限组成的字符串
	 * @throws SQLException
	 */
	public String getUserPartmentRight(int userID) throws SQLException
	{
		String rightStr="";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT Partment_ID FROM OprPartment_List WHERE Users_ID=" + userID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				rightStr=rightStr+rs.getInt("Partment_ID")+" ";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return rightStr;
	}
	
	/**
	 * 新增部门之后默认赋给超级管理员权限
	 * @param name  刚新增进入的partName
	 * @return
	 * @throws SQLException
	 */
	public boolean rightAfterInsert(String name,int userID) throws SQLException
	{
		boolean b=false;
		UsersPartmentModel uam=new UsersPartmentModel();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select Partment_ID from Partment where Partment_Name='"+name+"'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int partID=rs.getInt("Partment_ID");
				
				uam.setPartmentID(partID);
				uam.setUsersID(userID);
				
				b=this.insert(uam);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return b;
	}
	
	/**
	 * 删除部门时，删除对应的权限
	 * @param archID
	 * @return
	 * @throws SQLException 
	 */
	public boolean rightAfterDelete(int partID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql="DELETE FROM OprPartment_List WHERE Partment_ID=" + partID ;
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
