package com.sf.energy.project.equipment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.serviceImpl.AmmeterSearchImpl;
import com.sf.energy.util.ConnDB;

/**
 * 数据表[Ammeter]操作的测试用例
 * 
 * @author 鄢浩
 * @version 1.0
 * @see
 * @since version 1.0
 */
public class TestAmmeter
{
	AmmeterDao ad = new AmmeterDao();
	AmmeterSearchImpl as = new AmmeterSearchImpl();

	/**
	 * 测试插入
	 * 
	 * @throws SQLException
	 */

	@Test
	public void testInsert()
	{
		AmmeterDao pd = new AmmeterDao();
		AmmeterModel pm = new AmmeterModel();

		pm.setAmmeterName("123");
		pm.setAmmeterNum("1323");
		pm.setArchitectureID(123);
		try
		{
			pd.insert(pm);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Ammeter ORDER BY Ammeter_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		try
		{
			while (rs.next())
			{
				rs.last();
				lastID = rs.getInt(1);
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		AmmeterModel pm1 = null;
		try
		{
			pm1 = pd.queryByID(lastID);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		//
		Assert.assertEquals("1323", pm1.getAmmeterNum());
	}

	/**
	 * 测试修改
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testModify()
	{
		AmmeterDao pd = new AmmeterDao();
		AmmeterModel pm = new AmmeterModel();

		pm.setAmmeterName("123");
		pm.setAmmeterNum("1323");
		pm.setArchitectureID(123);
		try
		{
			pd.insert(pm);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Ammeter ORDER BY Ammeter_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		try
		{
			while (rs.next())
			{
				rs.last();
				lastID = rs.getInt(1);
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		AmmeterModel pm1 = null;
		try
		{
			pm1 = pd.queryByID(lastID);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		// 修改
		pm1.setAmmeterName("222");
		pm1.setAmmeterNum("222");
		pm1.setArchitectureID(111);
		try
		{
			pd.modify(pm1);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		// 查询出刚插入的数据
		int lastID1 = 0;
		String sql1 = "SELECT * FROM Ammeter ORDER BY Ammeter_ID";
		ResultSet rs1 = ConnDB.executeQuery(sql);
		try
		{
			while (rs1.next())
			{
				rs.last();
				lastID = rs1.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		AmmeterModel pm2 = null;
		try
		{
			pm2 = pd.queryByID(lastID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		Assert.assertEquals(pm1.getAmmeterID(), pm2.getAmmeterID());

	}

	/**
	 * 测试删除
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testDelete()
	{

		int lastID = 0;

		String sql = "SELECT * FROM Ammeter";
		ResultSet rs = ConnDB.executeQuery(sql);
		try
		{
			rs.last();
			lastID = rs.getInt(1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		boolean b = false;
		try
		{
			b = ad.delete(lastID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		Assert.assertEquals(1, b);

	}

	/**
	 * 测试分页查询
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testParginate() throws SQLException
	{
		AmmeterSearchImpl as = new AmmeterSearchImpl();
		long searchStart = System.currentTimeMillis();
		List<AmmeterModel> list = as.queryPaginate(0, 0, 0, "", "", "",
				"Ammeter_ID", false, 10, 0);
		long searchEnd = System.currentTimeMillis();
		System.out.println("查询用时：" + (searchEnd - searchStart) + "ms size:"
				+ list.size());

	}

	@Test
	public void queryPaginate() throws SQLException
	{
		ArrayList<AmmeterModel> list = as.queryPaginate(0, 0, 0, "", "", "",
				"Ammeter_ID", false, 10, 0);

		System.out.println("size:" + list.size());
	}
}