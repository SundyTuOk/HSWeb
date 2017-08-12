package com.sf.energy.project.right.dao;

import org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.UsersPartmentDao;
import com.sf.energy.project.right.model.UsersPartmentModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[OprPartment_List]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestUsersPartment
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		UsersPartmentDao upd = new UsersPartmentDao();
		UsersPartmentModel upm = new UsersPartmentModel();

		upm.setUsersID(111);
		upm.setPartmentID(1111);
		upd.insert(upm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM OprPartment_List WHERE Users_ID=111";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersPartmentModel rm1 = upd.query(lastID);
		
		Assert.assertEquals(upm.getPartmentID(), rm1.getPartmentID());
	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		UsersPartmentDao upd = new UsersPartmentDao();
		UsersPartmentModel upm = new UsersPartmentModel();

		upm.setUsersID(123);
		upm.setPartmentID(123);
		upd.insert(upm);
		
		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM OprPartment_List WHERE Partment_ID=123";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersPartmentModel rm1 = upd.query(lastID);
		// 修改
		
		rm1.setUsersID(321);
		rm1.setPartmentID(321);
		upd.modify(rm1);
		
		Assert.assertEquals(321, rm1.getUsersID());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		UsersPartmentDao upd = new UsersPartmentDao();
		int lastID = 0;

		String sql = "SELECT * FROM OprPartment_List";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = upd.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
