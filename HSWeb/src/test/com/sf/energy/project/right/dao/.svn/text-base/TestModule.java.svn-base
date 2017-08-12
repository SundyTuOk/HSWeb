package com.sf.energy.project.right.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.right.dao.ModuleDao;
import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.util.ConnDB;

 /**
 * 数据表[Module]操作的测试用例
 *
 * @author 鄢浩
 * @version 1.0
 * @see  
 * @since version 1.0
 */
public class TestModule
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */
	@Test
	public void testInsert() throws SQLException
	{
		ModuleDao md = new ModuleDao();
		ModuleModel mm = new ModuleModel();

		mm.setModuleNum("111");
		mm.setModuleName("111");
		mm.setModuleAddress("111");
		mm.setModuleParent(1);
		mm.setModulePart1("111");
		mm.setModulePart2("111");
		mm.setModuleOrder(1);
		mm.setModuleMark("111");
		mm.setModuleRemark("111");
		mm.setModuleImage("C://xxx/xx.jpg");
		md.insert(mm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Module ORDER BY Module_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}

		ModuleModel mm1 = md.query(lastID);

		Assert.assertEquals(mm.getModuleNum(), mm1.getModuleNum());

	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		ModuleDao md = new ModuleDao();
		ModuleModel mm = new ModuleModel();
		
		mm.setModuleNum("111");
		mm.setModuleName("111");
		mm.setModuleAddress("111");
		mm.setModuleParent(1);
		mm.setModulePart1("111");
		mm.setModulePart2("111");
		mm.setModuleOrder(1);
		mm.setModuleMark("111");
		mm.setModuleRemark("111");
		mm.setModuleImage("C://xxx/xx.jpg");
		md.insert(mm);
		
		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Module ORDER BY Module_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
			System.out.print(lastID);
		}
		ModuleModel mm1 = md.query(lastID);
		// 修改
		mm1.setModuleNum("222");
		mm1.setModuleName("222");
		mm1.setModuleAddress("222");
		mm1.setModuleParent(1);
		mm1.setModulePart1("222");
		mm1.setModulePart2("222");
		mm1.setModuleOrder(1);
		mm1.setModuleMark("222");
		mm1.setModuleRemark("222");
		mm1.setModuleImage("C://xxx/xx.jpg");

		md.modify(mm1);
		
		// 查询出刚插入的数据
				int lastID1 = 0;
				String sql1 = "SELECT * FROM Module ORDER BY Module_ID";
				ResultSet rs1 = ConnDB.executeQuery(sql);
				while (rs1.next())
				{
					rs1.last();
					lastID1 = rs1.getInt(1);
				}
				ModuleModel mm2 = md.query(lastID1);
		Assert.assertEquals("222", mm2.getModuleNum());
		Assert.assertEquals("222", mm2.getModuleName());
		Assert.assertEquals(1, mm2.getModuleParent());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		ModuleDao md = new ModuleDao();
		int lastID = 0;

		String sql = "SELECT * FROM Module ORDER BY Module_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = md.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
