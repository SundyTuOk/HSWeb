package com.sf.energy.project.right.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.serviceImpl.ListUsersPowerImpl;


/**
* 用户和权限对应信息操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestGetUsersPower
{

	/**
	 * 测试获取权限
	 * @throws SQLException
	 */
	@Test
	public void testGetUsersPowerID() throws SQLException
	{
		ListUsersPowerImpl up = new ListUsersPowerImpl();
		UsersModel um = new UsersModel();

		List<Integer> lst = new ArrayList<Integer>();

		um.setUsersID(1);
		lst = up.getUsersPowerID(um);

		Assert.assertNotNull(lst);
	}
	
	/**
	 * 测试获取权限
	 * @throws SQLException
	 */
	@Test
	public void testGetUsersPower() throws SQLException
	{
		ListUsersPowerImpl up = new ListUsersPowerImpl();
		UsersModel um = new UsersModel();

		List<PowerModel> lst = new ArrayList<PowerModel>();

		um.setUsersID(1);
		lst = up.getUsersPower(um);

		Assert.assertNotNull(lst);
	}

}
