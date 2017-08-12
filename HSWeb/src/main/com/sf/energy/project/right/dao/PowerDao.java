package com.sf.energy.project.right.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.util.ConnDB;

/**
 * 对权限表【Power】操作， 实现权限表【Power】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class PowerDao
{
	/**
	 * 列出所有的权限
	 * @return 权限实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> listPower() throws SQLException
	{
		PowerModel pModel = null;
		ArrayList<PowerModel> lst=new ArrayList<PowerModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Power ORDER BY Power_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				pModel=new PowerModel();
				
				pModel.setPowerID(rs.getInt(1));
				pModel.setModuleID(rs.getInt(2));
				pModel.setPowerNum(rs.getString(3));
				pModel.setPowerName(rs.getString(4));
				pModel.setPowerRemark(rs.getString(5));
				pModel.setPowerLevel(rs.getInt(6));
				
				lst.add(pModel);
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
	 * 查询权限
	 * 
	 * @param id 权限ID
	 * @return  权限实体类的实例
	 * @throws SQLException
	 */
	public PowerModel query(int id) throws SQLException
	{
		PowerModel pModel = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM Power WHERE Power_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				pModel = new PowerModel();
				pModel.setPowerID(rs.getInt(1));
				pModel.setModuleID(rs.getInt(2));
				pModel.setPowerNum(rs.getString(3));
				pModel.setPowerName(rs.getString(4));
				pModel.setPowerRemark(rs.getString(5));
				pModel.setPowerLevel(rs.getInt(6));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return pModel;
	}

	/**
	 * 插入权限
	 * 
	 * @param 权限实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(PowerModel r) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql = "INSERT INTO Power(Module_ID,Power_Num,Power_Name,Power_Remark,Power_Level)VALUES("
					+ r.getModuleID()
					+ ",'"
					+ r.getPowerNum()
					+ "','"
					+ r.getPowerName()
					+ "','"
					+ r.getPowerRemark()
					+ "',"
					+ r.getPowerLevel() + ")";

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
	 * 删除权限
	 * 
	 * @param id
	 *            权限ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */

	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql = "DELETE FROM Power WHERE Power_ID=" + id;
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
	 * 修改权限
	 * 
	 * @param r
	 *            权限实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(PowerModel r) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql = "UPDATE Power SET Module_ID=" + r.getModuleID()
					+ ",Power_Num='" + r.getPowerNum() + "',Power_Name='"
					+ r.getPowerName() + "',Power_Remark='" + r.getPowerRemark()
					+ "',Power_Level=" + r.getPowerLevel() + " WHERE Power_ID="
					+ r.getPowerID();
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
	 * 按模块层次列出所有的权限
	 * @return 权限实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> listModulePower() throws SQLException
	{
		PowerModel pModel = null;
		ArrayList<PowerModel> lst=new ArrayList<PowerModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_ModuleToPower ORDER BY Power_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				pModel=new PowerModel();
				
				pModel.setPowerID(rs.getInt("Power_ID"));
				pModel.setModuleID(rs.getInt("Module_ID"));
				pModel.setModuleName(rs.getString("Module_Name"));
				pModel.setModuleParent(rs.getInt("Module_Parent"));
				pModel.setPowerNum(rs.getString("Power_Num"));
				pModel.setModuleNum(rs.getString("Module_Num"));
				pModel.setPowerName(rs.getString("Power_Name"));

				lst.add(pModel);
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
	 * 查出模块对应的所有等级权限
	 * @param moduleID
	 * @return 权限的集合
	 * @throws SQLException
	 */
	public ArrayList<Integer> queryPowerIDsByModuleID(int moduleID) throws SQLException
	{
		ArrayList<Integer> lst=new ArrayList<Integer>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="Select Power_ID from Power Where Module_ID="+moduleID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
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

	/**
	 * 查出模块对应的一级权限,数据库中权限等级为0
	 * @param moduleID
	 * @return 模块对应的一级权限
	 * @throws SQLException
	 */
	public int queryPowerIDByModuleID(int moduleID) throws SQLException
	{
	
		int powerID=0;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="Select Power_ID from Power Where  Power_Level=0 and Module_ID="+moduleID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				powerID=rs.getInt("Power_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return powerID;
	}
	
	public static void main(String[] args) throws SQLException
	{
		PowerDao pd=new PowerDao();
		////System.out.println(pd.queryPowerIDByModuleID(333));
	}
}
