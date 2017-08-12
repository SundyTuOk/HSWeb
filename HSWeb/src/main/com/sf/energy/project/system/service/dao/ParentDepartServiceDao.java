package com.sf.energy.project.system.service.dao;

import java.sql.SQLException;

/**
 * 通过子部门的名称查找父部门的名称
 * 
 * @author 李涵淼
 * @version 1.0
 * 
 * @since version 1.0 
 * 
 */

public interface ParentDepartServiceDao
{
	/**
	 * 通过传入子部门的名称查找父部门的名称，并返回
	 * 
	 * @param name
	 *            传入的子部门名称
	 * @return 返回父部门名称
	 * @throws SQLException 
	 */
	public String findParentDepartName(String name) throws SQLException;

}
