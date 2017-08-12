package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.system.model.DataSite;
import com.sf.energy.util.ConnDB;

/**
 * 对数据中转站进行增删改查操作类
 * 
 * @author 李涵淼
 * @version 1.0
 * @since version 1.0
 * 
 */
public class DataSiteDao
{

	/**
	 * 定义list为ArrayList，对象为数据中转站对象
	 * 
	 */
	public DataSiteDao()
	{

	}

	/**
	 * 传入sql语句，对数据库进行操作，查出所有数据中转站对象，存入List中，并返回
	 * 
	 * @param sql
	 *            查询数据中转站的sql语句
	 * @return list 对象为数据中转站的的List
	 * @throws SQLException
	 */
	public List<DataSite> display(String sql) throws SQLException
	{
		DataSite site = null;
		List<DataSite> list = new ArrayList<DataSite>();
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
				site = new DataSite();
				site.setDatasiteID(rs.getInt("DataSite_ID"));
				site.setDatasiteNum(rs.getString("DataSite_Num"));
				site.setDatasiteName(rs.getString("DataSite_Name"));
				site.setDatasiteIP(rs.getString("DataSite_IP"));
				site.setDatasitePort(rs.getInt("DataSite_Prot"));
				site.setDatasiteHeart(rs.getInt("DataSite_Heart"));
				site.setDatasiteLastConTime(rs.getString("DataSite_LastConTime"));
				site.setDatasiteRemark(rs.getString("DataSite_Remark"));
				site.setDatasiteState(rs.getInt("DataSite_State"));

				list.add(site);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public Map<String, DataSite> getMapByName() throws SQLException
	{
		Map<String, DataSite> map = null;
		DataSite site = null;
		String sql = "select * from datasite";
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
				if (map == null)
				{
					map = new HashMap<String, DataSite>();
				}

				site = new DataSite();
				site.setDatasiteID(rs.getInt("DataSite_ID"));
				site.setDatasiteNum(rs.getString("DataSite_Num"));
				site.setDatasiteName(rs.getString("DataSite_Name"));
				site.setDatasiteIP(rs.getString("DataSite_IP"));
				site.setDatasitePort(rs.getInt("DataSite_Prot"));
				site.setDatasiteHeart(rs.getInt("DataSite_Heart"));
				site.setDatasiteLastConTime(rs.getString("DataSite_LastConTime"));
				site.setDatasiteRemark(rs.getString("DataSite_Remark"));
				site.setDatasiteState(rs.getInt("DataSite_State"));

				map.put(site.getDatasiteName(), site);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return map;
	}

	/**
	 * 传入sql语句，对数据库进行操作，查出所有数据中转站对象，存入List中，并返回
	 * 
	 * @param id
	 *            网关id
	 * @return site 对象为数据中转站的实例
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public DataSite queryByID(int id) throws NumberFormatException,
			SQLException
	{
		DataSite site = null;
		String sql = "Select * from DataSite where DataSite_ID=" + id;

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
				site = new DataSite();
				site.setDatasiteID(rs.getInt("DataSite_ID"));
				site.setDatasiteNum(rs.getString("DataSite_Num"));
				site.setDatasiteName(rs.getString("DataSite_Name"));
				site.setDatasiteIP(rs.getString("DataSite_IP"));
				site.setDatasitePort(rs.getInt("DataSite_Prot"));
				site.setDatasiteHeart(rs.getInt("DataSite_Heart"));
				site.setDatasiteLastConTime(rs.getString("DataSite_LastConTime"));
				site.setDatasiteRemark(rs.getString("DataSite_Remark"));
				site.setDatasiteState(rs.getInt("DataSite_State"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return site;

	}

	/**
	 * 查出所有数据中转站对象
	 * 
	 * @return list 数据中转站的对象
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public List<DataSite> displayAll() throws NumberFormatException,
			SQLException
	{
		DataSite site = null;
		List<DataSite> list = new ArrayList<DataSite>();
		String sql = "Select * from DataSite ORDER BY DATASITE_ID";

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
				site = new DataSite();
				site.setDatasiteID(rs.getInt("DataSite_ID"));
				site.setDatasiteNum(rs.getString("DataSite_Num"));
				site.setDatasiteName(rs.getString("DataSite_Name"));
				site.setDatasiteIP(rs.getString("DataSite_IP"));
				site.setDatasitePort(rs.getInt("DataSite_Prot"));
				site.setDatasiteHeart(rs.getInt("DataSite_Heart"));
				site.setDatasiteLastConTime(rs.getString("DataSite_LastConTime"));
				site.setDatasiteRemark(rs.getString("DataSite_Remark"));
				site.setDatasiteState(rs.getInt("DataSite_State"));

				list.add(site);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 通过传入一个数据中转站对象，对数据库进行操作，将此数据中转站信息插入数据库中
	 * 
	 * @param site
	 *            数据中转站对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean add(DataSite site) throws SQLException
	{

		String sql = "insert into datasite(DataSite_Num,DataSite_Name,DataSite_IP,DataSite_Prot,DataSite_Heart,DataSite_LastConTime,DataSite_Remark,DataSite_State)values('"

				+ site.getDatasiteNum()
				+ "','"
				+ site.getDatasiteName()
				+ "','"
				+ site.getDatasiteIP()
				+ "',"
				+ site.getDatasitePort()
				+ ","
				+ site.getDatasiteHeart()
				+ ",'"
				+ site.getDatasiteLastConTime()
				+ "','"
				+ site.getDatasiteRemark()

				+ "'," + site.getDatasiteState() + ")";

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
	 * 通过传入一个数据中转站对象，对数据库进行操作，更新数据库中数据
	 * 
	 * @param site
	 *            数据中转站对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean update(DataSite site) throws SQLException
	{

		String sql = "update datasite set DataSite_Num='"
				+ site.getDatasiteNum() + "' , DataSite_Name='"
				+ site.getDatasiteName() + "',DataSite_IP='"
				+ site.getDatasiteIP() + "',DataSite_Prot="
				+ site.getDatasitePort() + ",DataSite_Heart="
				+ site.getDatasiteHeart() + ",DataSite_LastConTime='"
				+ site.getDatasiteLastConTime() + "',DataSite_Remark='"
				+ site.getDatasiteRemark() + "',DataSite_State="
				+ site.getDatasiteState() + " where DataSite_ID="
				+ site.getDatasiteID() + "  ";
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
	 * 通过传入数据中转站的id,对数据进行操作，删除此id对应数据中转站
	 * 
	 * @param id
	 *            数据中转站的ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "delete from datasite where DataSite_ID=" + id;

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
	 * 通过中转站id查询中转站名称
	 * 
	 * @param id
	 *            中转站ID
	 * 
	 * @return 中转站名称
	 * @throws SQLException
	 */
	public String queryNameById(int id) throws SQLException
	{
		String dataSiteName = null;
		String sql = "SELECT DataSite_Name FROM DataSite WHERE DataSite_ID="
				+ id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				dataSiteName = rs.getString("DataSite_Name");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return dataSiteName;
	}

	public DataSite getFirstDataSite() throws SQLException
	{
		DataSite ds = null;

		String sql = "select * from DataSite where RowNum = 1";

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
				ds = new DataSite();
				ds.setDatasiteID(rs.getInt("DataSite_ID"));
				ds.setDatasiteNum(rs.getString("DataSite_Num"));
				ds.setDatasiteName(rs.getString("DataSite_Name"));
				ds.setDatasiteIP(rs.getString("DataSite_IP"));
				ds.setDatasitePort(rs.getInt("DataSite_Prot"));
				ds.setDatasiteHeart(rs.getInt("DataSite_Heart"));
				ds.setDatasiteLastConTime(rs.getString("DataSite_LastConTime"));
				ds.setDatasiteRemark(rs.getString("DataSite_Remark"));
				ds.setDatasiteState(rs.getInt("DataSite_State"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return ds;
	}
}
