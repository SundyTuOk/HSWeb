package com.sf.energy.project.system.service.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.service.dao.ArchitectureServiceDao;
import com.sf.energy.util.ConnDB;

/**
 * 通过对接口的实现，进行建筑物的查找操作
 * 
 * @author 李涵淼
 * @version 1.0
 * @see Architecture.java,FindArchitecture.java,ConnDB.java 
 * @since version 1.0 
 * 
 */
public class ArchitectureServiceDaoImpl implements ArchitectureServiceDao
{

	/**
	 * 通过区域id查找下属建筑物名称
	 * 
	 * @param id
	 *            传入的建筑物ID
	 * @return list1 存放建筑物名称的List
	 * @throws SQLException 
	 */

	public List<String> findArchById(int id) throws SQLException
	{

		List<String> list1 = new ArrayList<String>(); // list1定义一个存放建筑物名称的List

		List<Architecture> list2 = new ArrayList<Architecture>();// list2定义一个存放建筑物的List

		String sql = "select * from Architecture where Area_ID=" + id ;
		

			ArchitectureDao arch = new ArchitectureDao();
			list2 = arch.displayAll(sql);
			for (int i = 0; i < list2.size(); i++)
			{
				list1.add(list2.get(i).getName());

			}
		
		return list1;
	}

	/**
	 * 通过区域名查找下属建筑物名称
	 * 
	 * @param name
	 *            传入的区域名称
	 * @return list1 存放建筑物名称的List
	 * @throws SQLException 
	 */
	public List<String> findArchByName(String name) throws SQLException
	{

		List<String> list1 = new ArrayList<String>();// list1定义一个存放建筑物名称的List

		List<Architecture> list2 = new ArrayList<Architecture>();// list2定义一个存放建筑物的List

		String sql = "select * from area a inner join architecture b on a.Area_ID=b.Area_ID where a.Area_Name='"
				+ name + "'";
		

			ArchitectureDao arch = new ArchitectureDao();
			list2 = arch.displayAll(sql);
			for (int i = 0; i < list2.size(); i++)
			{
				list1.add(list2.get(i).getName());

			}
		
		return list1;
	}

	public List<String> findArch()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
