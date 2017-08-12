package com.sf.energy.project.right.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;


import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.serviceImpl.ListUsersModuleImpl;

/**
* 用户和模块对应信息操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestGetUsersModule
{

	@Test
	public void testGetUsersModule() throws SQLException
	{
		ListUsersModuleImpl up = new ListUsersModuleImpl();
		UsersModel um = new UsersModel();

		List<ModuleModel> list = new ArrayList<ModuleModel>();

		um.setUsersID(2);
		list = up.getUsersModule(um);

		Assert.assertNotNull(list);
	}

}
