package com.sf.energy.project.right.dao;

import org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.UsersRolesDao;
import com.sf.energy.project.right.model.UsersRolesModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[Users_Roles]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestUsersRoles
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		UsersRolesDao rd = new UsersRolesDao();
		UsersRolesModel rm = new UsersRolesModel();

		rm.setUsersID(111);
		rm.setRolesID(1111);
		rd.insert(rm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Users_Roles WHERE Users_ID=111";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersRolesModel rm1 = rd.query(lastID);
		
		Assert.assertEquals(rm.getRolesID(), rm1.getRolesID());
	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		UsersRolesDao rd = new UsersRolesDao();
		UsersRolesModel rm = new UsersRolesModel();

		rm.setUsersID(123);
		rm.setRolesID(123);
		rd.insert(rm);
		
		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Users_Roles WHERE Roles_ID=123";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersRolesModel rm1 = rd.query(lastID);
		// 修改
		
		rm1.setUsersID(321);
		rm1.setRolesID(321);
		rd.modify(rm1);
		
		Assert.assertEquals(321, rm1.getUsersID());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		UsersRolesDao rd = new UsersRolesDao();
		int lastID = 0;

		String sql = "SELECT * FROM Users_Roles";
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
