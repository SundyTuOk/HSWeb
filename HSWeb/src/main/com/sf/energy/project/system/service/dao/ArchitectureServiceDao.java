package com.sf.energy.project.system.service.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 通过区域信息对建筑物的查找操作
 * 此接口定义了查找建筑物的两种方法，一种是通过传入区域id查找下属的全部建筑物名称，第二种是通过传入区域名称查找下属的建筑物名称
 * 
 * @author 李涵淼
 * @version 1.0
 * 
 * @since version 1.0
 * 
 */
public interface ArchitectureServiceDao
{
	/**
	 * 通过区域id查找下属建筑物名称
	 * 
	 * @param id
	 *            区域ID
	 * @return 建筑物名称List
	 * @throws SQLException 
	 */

	public List<String> findArchById(int id) throws SQLException;

	/**
	 * 通过区域名查找下属建筑物名称
	 * 
	 * @param name
	 *            区域名称
	 * @return 建筑物名称List
	 * @throws SQLException 
	 */
	public List<String> findArchByName(String name) throws SQLException;

	/**
	 * 直接显示出建筑物名称
	 * 
	 * 
	 * @return 建筑物名称List
	 */
	public List<String> findArch();

}
