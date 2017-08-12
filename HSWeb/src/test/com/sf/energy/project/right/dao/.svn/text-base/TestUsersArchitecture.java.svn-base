package com.sf.energy.project.right.dao;

import org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.UsersArchitectureDao;
import com.sf.energy.project.right.model.UsersArchitectureModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[OprArc_List]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestUsersArchitecture
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		UsersArchitectureDao uad = new UsersArchitectureDao();
		UsersArchitectureModel uam = new UsersArchitectureModel();

		uam.setUsersID(111);
		uam.setArchID(1111);
		uad.insert(uam);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM OprArc_List WHERE Users_ID=111";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersArchitectureModel rm1 = uad.query(lastID);
		
		Assert.assertEquals(uam.getArchID(), rm1.getArchID());
	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		UsersArchitectureDao uad = new UsersArchitectureDao();
		UsersArchitectureModel uam = new UsersArchitectureModel();

		uam.setUsersID(123);
		uam.setArchID(123);
		uad.insert(uam);
		
		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM OprArc_List WHERE Architecture_ID=123";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		UsersArchitectureModel rm1 = uad.query(lastID);
		// 修改
		
		rm1.setUsersID(321);
		rm1.setArchID(321);
		uad.modify(rm1);
		
		Assert.assertEquals(321, rm1.getUsersID());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		UsersArchitectureDao uad = new UsersArchitectureDao();
		int lastID = 0;

		String sql = "SELECT * FROM OprArc_List";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = uad.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
