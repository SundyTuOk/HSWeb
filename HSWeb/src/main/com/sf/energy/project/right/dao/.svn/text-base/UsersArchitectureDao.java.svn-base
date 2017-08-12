package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.project.right.model.UsersArchitectureModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.util.ConnDB;


/**
 * 对用户建筑表【OprArc_List】操作， 实现用户建筑表【OprArc_List】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class UsersArchitectureDao
{
	/**
	 * 查询用户建筑权限
	 * 
	 * @param id 用户ID
	 * @return  用户建筑实体类的实例
	 * @throws SQLException
	 */
	public UsersArchitectureModel query(int id) throws SQLException
	{
		UsersArchitectureModel uam = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM OprArc_List WHERE Users_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				uam = new UsersArchitectureModel();
				uam.setUsersID(rs.getInt(1));
				uam.setArchID(rs.getInt(2));
				uam.setAreaID(rs.getInt(3));
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
	 * 插入用户建筑权限
	 * 
	 * @param uam 用户建筑实体类的实例
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(UsersArchitectureModel uam) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO OprArc_List(Users_ID,Architecture_ID,Area_ID) VALUES("
					+ uam.getUsersID() + "," + uam.getArchID() + ","+uam.getAreaID()+")";
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
	 * 删除用户建筑权限
	 * 
	 * @param  id 用户ID
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "DELETE FROM OprArc_List WHERE Users_ID=" + id;
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
	 * 修改用户建筑权限
	 * 
	 * @param uam 用户建筑实体类的实例
	 * @return boolean 表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(UsersArchitectureModel uam) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE OprArc_List SET Architecture_ID=" + uam.getArchID()+",Area_ID="+uam.getAreaID()
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
	 * 查询用户建筑权限
	 * 
	 * @param id 用户ID
	 * @return  用户权限组成的字符串
	 * @throws SQLException
	 */
	public String getUserArchRight(int userID) throws SQLException
	{
		String rightStr=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			rightStr = "";
			String sql = "SELECT Architecture_ID FROM OprArc_List WHERE Users_ID=" + userID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				rightStr=rightStr+rs.getInt("Architecture_ID")+" ";
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
	 * 新增建筑之后默认赋给超级管理员权限
	 * @param name  刚新增进入的archName
	 * @return
	 * @throws SQLException
	 */
	public boolean rightAfterInsert(String name,int userID) throws SQLException
	{
		boolean b=false;
		UsersArchitectureModel uam=new UsersArchitectureModel();
		ArchitectureDao ad=new ArchitectureDao();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="select Architecture_ID from Architecture where Architecture_Name='"+name+"'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				int archID=rs.getInt("Architecture_ID");
				uam.setArchID(archID);
				uam.setAreaID(ad.queryByID(archID).getAreaID());
				uam.setUsersID(userID);
				
				b=this.insert(uam);
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return b;
	}
	
	/**
	 * 删除建筑时，删除对应的权限
	 * @param archID
	 * @return
	 * @throws SQLException 
	 */
	public boolean rightAfterDelete(int archID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql="DELETE FROM OprArc_List WHERE Architecture_ID=" + archID ;
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
	 * 编辑建筑之后修改相应权限
	 * @param uam
	 * @return
	 * @throws SQLException 
	 */
	public boolean rightAfterModify(int archID,int areaID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE OprArc_List SET Area_ID="+areaID
					+ "WHERE Architecture_ID=" + archID;
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
