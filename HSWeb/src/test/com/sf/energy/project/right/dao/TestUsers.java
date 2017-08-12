package com.sf.energy.project.right.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.util.ConnDB;

/**
 * 数据表[Users]操作的测试用例
 *
 * @author 鄢浩
 * @version 1.0
 * @see  
 * @since version 1.0
 */
public class TestUsers
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		UsersDao ud = new UsersDao();
		UsersModel um = new UsersModel();

		um.setAreaID(5);
		um.setUsersNum("123");
		um.setUsersName("yanhao");
		um.setIsAlarm(0);
		um.setUsersBirth("2008-02-02 00:00:00");
		um.setUsersDepartment("xxxxx");
		um.setUsersGender("男");
		um.setUsersHomeAddress("xxxxx");
		um.setUsersLoginName("yanhao");
		um.setUsersPassword("yanhao");
		um.setUsersPhone("123");
		um.setUsersPhone1("123");
		um.setUsersPhone2("123");
		um.setUsersPhoto("C://xxx/xx.jpg");
		ud.insert(um);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Users ORDER BY Users_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersModel um1 = ud.query(lastID);

		Assert.assertEquals(um.getUsersNum(), um1.getUsersNum());

	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify()
	{
		UsersDao ud = new UsersDao();
		UsersModel um = new UsersModel();

		um.setUsersID(11);
		um.setAreaID(5);
		um.setUsersNum("123");
		um.setUsersName("yanhao");
		um.setIsAlarm(0);
		um.setUsersBirth("2008-02-02 00:00:00");
		um.setUsersDepartment("xxxxx");
		um.setUsersGender("男");
		um.setUsersHomeAddress("xxxxx");
		um.setUsersLoginName("yanhao");
		um.setUsersPassword("yanhao");
		um.setUsersPhone("123");
		um.setUsersPhone1("123");
		um.setUsersPhone2("123");
		um.setUsersPhoto("C://xxx/xx.jpg");

		try
		{
			ud.modify(um);
		} catch (SQLException e)
		{		
			e.printStackTrace();
		}

		UsersModel um1 = null;
		try
		{
			um1 = ud.query(11);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		Assert.assertEquals(um.getUsersNum(), um1.getUsersNum());
		Assert.assertEquals(um.getUsersLoginName(), um.getUsersLoginName());
		Assert.assertEquals(um.getUsersPassword(), um.getUsersPassword());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		UsersDao ud = new UsersDao();
		int lastID = 0;

		String sql = "SELECT * FROM Users ORDER BY Users_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = ud.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
