package com.sf.energy.project.right.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.RolesPowerDao;
import com.sf.energy.project.right.model.RolesPowerModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[Roles_Power]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestRolesPower
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		RolesPowerDao rd = new RolesPowerDao();
		RolesPowerModel rm = new RolesPowerModel();

		rm.setRolesID(111);
		rm.setPowerID(1111);
		rd.insert(rm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Roles_Power WHERE Roles_ID=111";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		RolesPowerModel rm1 = rd.query(lastID);
		//
		
		Assert.assertEquals(rm.getPowerID(), rm1.getPowerID());
	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		RolesPowerDao rd = new RolesPowerDao();
		RolesPowerModel rm = new RolesPowerModel();

		rm.setPowerID(123);
		rm.setRolesID(123);
		rd.insert(rm);
		
		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Roles_Power WHERE Roles_ID=123";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		RolesPowerModel rm1 = rd.query(lastID);
		// 修改
		
		rm1.setPowerID(321);
		rm1.setRolesID(321);
		rd.modify(rm1);
		
		Assert.assertEquals(321, rm1.getPowerID());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		RolesPowerDao rd = new RolesPowerDao();
		int lastID = 0;

		String sql = "SELECT * FROM Roles_Power";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = rd.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
