
package com.sf.energy.project.right.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import com.sf.energy.project.right.dao.RolesDao;
import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[Roles]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestRoles
{

	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		RolesDao rd = new RolesDao();
		RolesModel rm = new RolesModel();

		rm.setRolesName("超级管理员");
		rm.setRolesEnable(1);
		rm.setRolesRemark("全部的操作可用");
		rd.insert(rm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Roles ORDER BY Roles_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		RolesModel rm1 = rd.query(lastID);
		//
		Assert.assertEquals(rm.getRolesName(), rm1.getRolesName());
		Assert.assertEquals(rm.getRolesEnable(), rm1.getRolesEnable());
		Assert.assertEquals(rm.getRolesRemark(), rm1.getRolesRemark());

	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		RolesDao rd = new RolesDao();
		RolesModel rm = new RolesModel();

		rm.setRolesName("超级管理员");
		rm.setRolesEnable(1);
		rm.setRolesRemark("全部的操作可用");
		rd.insert(rm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Roles ORDER BY Roles_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		RolesModel rm1 = rd.query(lastID);
		// 修改
		rm1.setRolesName("系统管理员");
		rm1.setRolesEnable(0);
		rm1.setRolesRemark("系统一般管理员");
		rd.modify(rm1);

		// 查询出刚插入的数据
		int lastID1 = 0;
		String sql1 = "SELECT * FROM Roles ORDER BY Roles_ID";
		ResultSet rs1 = ConnDB.executeQuery(sql);
		while (rs1.next())
		{
			rs1.last();
			lastID = rs1.getInt(1);
		}
		RolesModel rm2 = rd.query(lastID);

		Assert.assertEquals("系统管理员", rm2.getRolesName());
		Assert.assertEquals(0, rm2.getRolesEnable());
		Assert.assertEquals("系统一般管理员", rm2.getRolesRemark());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		RolesDao rd = new RolesDao();
		int lastID = 0;

		String sql = "SELECT * FROM Roles ORDER BY Roles_ID";
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
