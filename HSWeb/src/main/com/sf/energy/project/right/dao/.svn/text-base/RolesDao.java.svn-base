package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.util.ConnDB;

/**
 * 对角色表【Roles】操作， 实现角色表【Roles】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class RolesDao
{
	int total;
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
	/**
	 * 列出所有角色 
	 * @return 角色实力类的列表
	 * @throws SQLException
	 */
	public ArrayList<RolesModel> listRoles() throws SQLException
	{
		ArrayList<RolesModel> lst=new ArrayList<RolesModel>();
		RolesModel rm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Roles ORDER BY Roles_ID ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				rm=new RolesModel();
				
				rm.setRolesID(rs.getInt(1));
				rm.setRolesName(rs.getString(2));
				rm.setRolesEnable(rs.getInt(3));
				rm.setRolesRemark(rs.getString(4));
				
				lst.add(rm);
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
	 * 列出所有角色 (分页)
	 * @return 角色实力类的列表
	 * @throws SQLException
	 */
	public ArrayList<RolesModel> listRoles(int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<RolesModel> lst=new ArrayList<RolesModel>();
		RolesModel rm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Roles ORDER BY Roles_ID ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return lst;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next()&&(count > 0))
			{
				rm=new RolesModel();
				
				rm.setRolesID(rs.getInt(1));
				rm.setRolesName(rs.getString(2));
				rm.setRolesEnable(rs.getInt(3));
				rm.setRolesRemark(rs.getString(4));
				
				lst.add(rm);
				
				count--;
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
	 * 查询角色
	 * 
	 * @param id 角色ID
	 * @return  角色实体类的实例
	 * @throws SQLException
	 */
	public RolesModel query(int id) throws SQLException
	{
		RolesModel rm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Roles WHERE Roles_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();
			if (rs.next())
			{
				rm = new RolesModel();
				rm.setRolesID(rs.getInt(1));
				rm.setRolesName(rs.getString(2));
				rm.setRolesEnable(rs.getInt(3));
				rm.setRolesRemark(rs.getString(4));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return rm;
	}

	
	/**
	 * 查询角色
	 * 
	 * @param String  roleName 角色名称 
	 * @return  角色实体类的实例
	 * @throws SQLException
	 */
	public RolesModel queryByName(String roleName) throws SQLException
	{
		RolesModel rm = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql = "SELECT * FROM Roles WHERE Roles_Name='" + roleName+"'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				rm = new RolesModel();
				rm.setRolesID(rs.getInt(1));
				rm.setRolesName(rs.getString(2));
				rm.setRolesEnable(rs.getInt(3));
				rm.setRolesRemark(rs.getString(4));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return rm;
	}

	
	
	
	
	
	
	
	/**
	 * 增加角色
	 * 
	 * @param r
	 *            角色实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(RolesModel r) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO Roles(Roles_Name,Roles_Enable,Roles_Remark)VALUES('"
					+ r.getRolesName()
					+ "',"
					+ r.getRolesEnable()
					+ ",'"
					+ r.getRolesRemark() + "')";
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
	 * 删除角色
	 * 
	 * 
	 * 
	 * @param id
	 *            角色ID
	 * @return boolean 返回布尔变量表示操作成功与否 
	 * @param id
	 *            角色ID
	 * @return 删除数据的数目 
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "DELETE FROM Roles WHERE Roles_ID=" + id;
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
	 * 修改角色
	 * 
	 * @param r
	 *            角色实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(RolesModel r) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "UPDATE Roles SET Roles_Name='" + r.getRolesName()
					+ "',Roles_Enable=" + r.getRolesEnable() + ",Roles_Remark='"
					+ r.getRolesRemark() + "' WHERE Roles_ID=" + r.getRolesID();
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
	 * 新增角色之前，判断是否有相同的角色
	 * @param roleName
	 * @return
	 * @throws SQLException
	 */
	public boolean hasSameRoleName(String roleName) throws SQLException
	{
		boolean b = false;
		String sql="select Roles_ID from Roles where Roles_Name='"+roleName+"'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				b=true;
			}
		}
		finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return b;
		
	}
	
}