package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;











import com.sf.energy.project.right.model.UsersAreaModel;


import com.sf.energy.util.ConnDB;

/**
 * 对用户表【OprArea_List】操作， 实现用户区域表【OprArea_List】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class UsersAreaDao
{
	/**
	 * 查询用户区域权限
	 * 
	 * @param id 用户ID
	 * @return 用户区域实体类的实例
	 * @throws SQLException
	 */
	public UsersAreaModel query(int id) throws SQLException
	{
		UsersAreaModel uam = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM OprArea_List WHERE Users_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				uam = new UsersAreaModel();
				uam.setUsersID(rs.getInt(1));
				uam.setAreaID(rs.getInt(2));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return uam;
	}

	/**
	 * 插入用户区域权限
	 * 
	 * @param uam 用户区域实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(UsersAreaModel uam) throws SQLException
	{
		boolean b=false;
		if(this.hasSame(uam))
		{
			return true;
		}
		else 
		{
			Connection conn=null;
			PreparedStatement ps =null;
			try
			{
				String sql = "INSERT INTO OprArea_List(Users_ID,Area_ID) VALUES("
						+ uam.getUsersID() + "," + uam.getAreaID() + ")";
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);				
				b=(ps.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps);
			}
		}	
		return b;
	}

	/**
	 * 删除用户区域权限
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
			String sql = "DELETE FROM OprArea_List WHERE Users_ID=" + id;
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
	 * 修改用户区域权限
	 * 
	 * @param uam 用户区域实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(UsersAreaModel uam) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE OprArea_List SET Area_ID=" + uam.getAreaID()
					+ "WHERE Users_ID=" + uam.getUsersID();
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
	 * 判断是否有相同的，有的话就不插入
	 * @param uam
	 * @return
	 * @throws SQLException 
	 */
	public boolean hasSame(UsersAreaModel uam) throws SQLException
	{
		boolean b=false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from OprArea_List where Users_ID=" + uam.getUsersID()
					+ " and Area_ID=" + uam.getAreaID();
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				b=true;
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
	 * 查询用户区域权限
	 * 
	 * @param id 用户ID
	 * @return  用户权限组成的字符串
	 * @throws SQLException
	 */
	public String getUserAreaRight(int userID) throws SQLException
	{
		String rightStr="";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT Area_ID FROM OprArea_List WHERE Users_ID=" + userID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();
			while (rs.next())
			{
				rightStr=rightStr+rs.getInt("Area_ID")+" ";
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
	 * 新增区域之后默认赋给超级管理员权限
	 * @param name  刚新增进入的areaName
	 * @return
	 * @throws SQLException
	 */
	public boolean rightAfterInsert(String name,int userID) throws SQLException
	{
		boolean b=false;
		UsersAreaModel uam=new UsersAreaModel();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select Area_ID from Area where Area_Name='"+name+"'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int areaID=rs.getInt("Area_ID");
				
				uam.setAreaID(areaID);
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
	 * 删除区域时，删除对应的权限
	 * @param archID
	 * @return
	 * @throws SQLException 
	 */
	public boolean rightAfterDelete(int areaID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql="DELETE FROM OprArea_List WHERE Area_ID=" + areaID ;
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
