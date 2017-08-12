package com.sf.energy.project.right.dao;

import org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import com.sf.energy.project.right.dao.PowerDao;
import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.util.ConnDB;

/**
* 数据表[Power]操作的测试用例
*
* @author 鄢浩
* @version 1.0
* @see  
* @since version 1.0
*/
public class TestPower
{
	/**
	 * 测试插入
	 * @throws SQLException
	 */

	@Test
	public void testInsert() throws SQLException
	{
		PowerDao pd = new PowerDao();
		PowerModel pm = new PowerModel();

		pm.setModuleID(123);
		pm.setPowerNum("1323");
		pm.setPowerName("操作");
		pm.setPowerRemark("xxxx");
		pm.setPowerLevel(1);
		pd.insert(pm);

		 //查询出刚插入的数据
		 int lastID = 0;
		 String sql = "SELECT * FROM Power ORDER BY Power_ID";
		 ResultSet rs = ConnDB.executeQuery(sql);
		 while (rs.next())
		 {
		 rs.last();
		 lastID = rs.getInt(1);
		 }
		 PowerModel pm1 = pd.query(lastID);
		 //
		 Assert.assertEquals(pm.getPowerNum(), pm1.getPowerNum());
	}

	/**
	 * 测试修改
	 * @throws SQLException
	 */
	@Test
	public void testModify() throws SQLException
	{
		PowerDao pd = new PowerDao();
		PowerModel pm = new PowerModel();

		pm.setModuleID(123);
		pm.setPowerNum("1323");
		pm.setPowerName("操作");
		pm.setPowerRemark("xxxx");
		pm.setPowerLevel(1);
		pd.insert(pm);

		// 查询出刚插入的数据
		int lastID = 0;
		String sql = "SELECT * FROM Power ORDER BY Power_ID";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);
		}
		PowerModel pm1 = pd.query(lastID);
		// 修改
		pm1.setModuleID(234);
		pm1.setPowerNum("52345");
		pm1.setPowerName("操作权限");
		pm1.setPowerRemark("yyyy");
		pm1.setPowerLevel(2);
		pd.modify(pm1);

		// 查询出刚插入的数据
		int lastID1 = 0;
		String sql1 = "SELECT * FROM Power ORDER BY Power_ID";
		ResultSet rs1 = ConnDB.executeQuery(sql);
		while (rs1.next())
		{
			rs.last();
			lastID = rs1.getInt(1);
		}
		PowerModel pm2 = pd.query(lastID);

		Assert.assertEquals(234, pm2.getModuleID());
		Assert.assertEquals("52345", pm2.getPowerNum());
		Assert.assertEquals("操作权限", pm2.getPowerName());
		Assert.assertEquals("yyyy", pm2.getPowerRemark());
		Assert.assertEquals(2, pm2.getPowerLevel());
	}

	/**
	 * 测试删除
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException
	{
		PowerDao pd = new PowerDao();
		int lastID = 0;

		String sql = "SELECT * FROM Power";
		ResultSet rs = ConnDB.executeQuery(sql);
		while (rs.next())
		{
			rs.last();
			lastID = rs.getInt(1);

		}
		int b = pd.delete(lastID);// 1代表删除成功
		Assert.assertEquals(1, b);

	}

}
