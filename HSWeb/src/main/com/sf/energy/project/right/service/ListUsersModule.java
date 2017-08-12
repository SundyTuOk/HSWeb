package com.sf.energy.project.right.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.UsersModel;

/**
 * 用户和功能模块之间的相关权限操作
 * 本接口中包含通过用户信息查询出该用户具有的模块信息
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public interface ListUsersModule
{

	/**
	 * 获取某一个用户拥有的模块权限
	 * @param um 用户实体类的实例
	 * @return lst 获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> getUsersModule(UsersModel um) throws SQLException;
	

	/**
	 * 获取某一个用户拥有的模块权限
	 * @param id 用户ID
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<ModuleModel> getUsersModuleByID(int id) throws SQLException;
	
	/**
	 * 获取某一个用户拥有的模块权限
	 * @param id 用户ID
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public ArrayList<String> getUsersRootModuleNameByID(int id) throws SQLException;
	
	/**
	 * 获取模块路径
	 * 
	 * @param moduleName 子系统模块名称，默认有module_parent=0
	 * @return 获取用户对应的模块
	 * @throws SQLException
	 */
	public String getUsersModuleURLByName(String moduleName) throws SQLException;
}
