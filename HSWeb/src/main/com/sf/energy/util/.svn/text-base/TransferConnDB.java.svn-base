package com.sf.energy.util;

import java.sql.*;

/**
 * Oracle数据库的连接操作类
 * 
 * @author 崔正阳
 * @version 1.0
 * @see 
 * @since version 1.0
 */

public class TransferConnDB
{
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	// oracle connection driver values
	private static String dbClassName = "oracle.jdbc.driver.OracleDriver";
	private static String dbUrl = "jdbc:oracle:thin:@115.156.249.2:1521:orcl";
	private static String dbUser = "ls";
	private static String dbPassWord = "ky";

	/**
	 * 获取一个数据库连接
	 * @return Connection 数据库连接
	 */
	public static Connection getConnection()
	{
		if (conn == null)
		{
			try
			{
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPassWord);

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (Exception ee)
			{
				ee.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * 执行查找操作
	 * @param sql 要执行的sql语句
	 * @return ResultSet 执行的结果集
	 */
//	public static ResultSet executeQuery(String sql)
//	{
//		try
//		{
//			conn = getConnection();
//
//			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_READ_ONLY);
//			rs = stmt.executeQuery(sql);
//		} catch (SQLException ex)
//		{
//			System.err.println(ex.getMessage());
//		}
//		return rs;
//	}

	/**
	 * 执行数据库增、删、改操作
	 * @param sql 要执行的sql语句
	 * @return result 返回执行结果条数
	 */
//	public static int executeUpdate(String sql)
//	{
//		int result = 0;
//		try
//		{
//			conn = getConnection();
//			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_READ_ONLY);
//			result = stmt.executeUpdate(sql);
//		} catch (SQLException ex)
//		{
//			result = 0;
//		}
//		return result;
//	}

	/**
	 * 关闭连接
	 */
//	public static void close()
//	{
//		try
//		{
//			if (rs != null)
//			{
//				rs.close();
//				rs = null;
//			}
//
//			if (stmt != null)
//			{
//				stmt.close();
//				stmt = null;
//			}
//
//			if (conn != null)
//			{
//				conn.close();
//				conn = null;
//			}
//		} catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//	}
}
