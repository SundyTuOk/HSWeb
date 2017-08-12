package com.sf.energy.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerHelper
{
	private static Connection conn = null;

	private static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dbUrl = "jdbc:sqlserver://115.156.249.38:1433;DatabaseName=VESSchoolJingJi";
	private static String dbUser = "sa";
	private static String dbPassWord = "kidden406";

	/**
	 * 获取一个数据库连接
	 * 
	 * @return Connection 数据库连接
	 */
	public static Connection getConnection()
	{
		if (conn == null)
		{
			try
			{
				Class.forName(dbClassName);
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
}
