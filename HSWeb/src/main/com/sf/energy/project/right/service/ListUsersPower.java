package com.sf.energy.project.right.service;


import java.sql.SQLException;

import java.util.ArrayList;

import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.project.right.model.UsersModel;

/**
 * 用户或角色相关权限信息的操作
 * 接口中包含通过用户、角色信息查询用户权限、查询角色权限，判断某用户是否有某权限的操作
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public interface ListUsersPower
{
	/**
	 * 判断某一个用户是否有某权限
	 * @param usersID 用户ID
	 * @param powerID 权限ID
	 * @return 是否有权限
	 * @throws SQLException
	 */
	public boolean hasPowerByID(int usersID, int powerID) throws SQLException;
	
	
	/**
	 * 判断某一个用户是否有某权限
	 * @param um 用户实体类的实例
	 * @param pm 权限实体类的实例
	 * @return 是否有权限
	 * @throws SQLException
	 */
	public boolean hasPower(UsersModel um, PowerModel pm) throws SQLException;
	
	
	/**
	 * 获取某角色的所有权限ID
	 * @param rm 角色实体类的实例
	 * @return  权限的ID的集合
	 * @throws SQLException
	 */
	public ArrayList<Integer> getRolesPowerID(RolesModel rm) throws SQLException;
	

	/**
	 * 获取某一个角色的所有权限信息
	 * @param rm 角色实体类的实例
	 * @return  权限实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> getRolesPower(RolesModel rm) throws SQLException;
	

	/**
	 * 获取用户权限
	 * @param um 用户实体类的实例
	 * @return  权限ID的集合
	 * @throws SQLException
	 */
	public ArrayList<Integer> getUsersPowerID(UsersModel um) throws SQLException;
	

	
	/**
	 * 获取某一个用户的所有权限
	 * @param um 用户实体类的实例
	 * @return  权限ID的集合
	 * @throws SQLException
	 */
	public ArrayList<PowerModel> getUsersPower(UsersModel um) throws SQLException;
	
}
