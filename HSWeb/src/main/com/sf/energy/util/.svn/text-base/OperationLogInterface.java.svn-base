package com.sf.energy.util;

import java.sql.SQLException;

public interface OperationLogInterface
{
	/**
	 * 记录系统操作到数据库
	 * userName可以从session中获得
	 * moduleNum：对应的模块名称
	 * operationKeyWord：编辑建筑信息、新增建筑信息、添加电表信息、修改操作员......
	 * @param userLoginName 操作员登录名
	 * @param moduleName 操作的模块
	 * @param operationKeyWord 关键字（添加，删除，修改）
	 * @param errInfo 失败则存入失败信息e.getMessage()，成功则存入null
	 * @return 是否操作成功
	 * @throws SQLException 
	 */
	public  boolean writeLogErrInfo(String userLoginName,String moduleName,String operationKeyWord,String errInfo) throws SQLException; 
	
	
	/**
	 * 记录系统操作到数据库
	 * userName可以从session中获得
	 * moduleNum：对应的模块名称
	 * operationKeyWord：编辑建筑信息、新增建筑信息、添加电表信息、修改操作员......
	 * @param userLoginName 操作员登录名
	 * @param moduleName 操作的模块
	 * @param operationKeyWord 关键字（添加，删除，修改）
	 * @return 是否操作成功
	 * @throws SQLException 
	 */
	public  boolean writeLog(String userLoginName,String moduleName,String operationKeyWord) throws SQLException;
}
