package com.sf.energy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.sf.energy.project.right.dao.UsersDao;

public class OperationLogImplement implements OperationLogInterface
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
	public  boolean writeLogErrInfo(String userLoginName,String moduleName,String operationKeyWord,String errInfo) throws SQLException 
	{

		UsersDao ud=new UsersDao();
		String userName=null;
		if(userLoginName!=null);
		{
			userName=ud.queryByName(userLoginName).getUsersName();
		}
		String sql="insert into SystemLog(Module_Num,SystemLog_Action,SystemLog_Time,SystemLog_Result,SystemLog_Message,Users_Name) values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps =null;
		boolean b = false;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, moduleName);
			ps.setString(2, operationKeyWord);
			ps.setString(3, new Date().toLocaleString());
			ps.setInt(4, 0);
			ps.setString(5, errInfo);
			ps.setString(6, userName);
			b = (ps.executeUpdate() == 1);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		/*
		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		
		
		boolean b=(ps.executeUpdate() == 1);
		if(ps!=null)
			ps.close();*/
		return b;
	}
	
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
	public  boolean writeLog(String userLoginName,String moduleName,String operationKeyWord) throws SQLException
	{
		UsersDao ud=new UsersDao();
		String userName=null;
		if(userLoginName!=null);
		{
			userName=ud.queryByName(userLoginName).getUsersName();
		}
		String sql="insert into SystemLog(Module_Num,SystemLog_Action,SystemLog_Time,SystemLog_Result,SystemLog_Message,Users_Name) values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, moduleName);
			ps.setString(2, operationKeyWord);
			ps.setString(3, new Date().toLocaleString());
			ps.setInt(4, 1);
			ps.setString(5, null);
			ps.setString(6, userName);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ps.setString(1, moduleName);
		ps.setString(2, operationKeyWord);
		ps.setString(3, new Date().toLocaleString());
		ps.setInt(4, 1);
		ps.setString(5, null);
		ps.setString(6, userName);
		
		boolean b=(ps.executeUpdate() == 1);
		if(ps!=null)
			ps.close();*/
		return b;
	}
	
}
