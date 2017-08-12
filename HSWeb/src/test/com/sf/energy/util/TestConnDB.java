package com.sf.energy.util;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * 测试Oracle数据库的连接
 * 
 * @author 鄢浩
 * @version 1.0
 * @see
 * @since version 1.0
 */
public class TestConnDB
{
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testaaaa()
	{
		String str1 = "1122.2.2";
		String str2 = "111";
		String str3 = "111.2";
		String str4 = "111s";
		String str5 = "111.s";
		String str6 = "1s11";
		String str7 = "";
		System.out.println(str1 + ":" + isNum(str1));
		System.out.println(str2 + ":" + isNum(str2));
		System.out.println(str3 + ":" + isNum(str3));
		System.out.println(str4 + ":" + isNum(str4));
		System.out.println(str5 + ":" + isNum(str5));
		System.out.println(str6 + ":" + isNum(str6));
		System.out.println(str7 + ":" + isNum(str7));
	}

	private boolean isNum(String str)
	{
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	@Test(timeout = 500)
	public void test()
	{
		Connection connection = (Connection) ConnDB.getConnection();
		assertNotNull(connection);
	}

	@Test
	public void transferAPSaleInfo() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from APSaleInfo";
		String insertSql = "insert into APSaleInfo "
				+ "(SALEINFONUM,AMMETER_ID,AMMETER_NAME,AMMETER_485ADDRESS,TIMES,"
				+ "KIND,QSYVALUE,HSYVALUE,USERS_ID,BUYTIME,SENDTIME,STATUS,PRICE,"
				+ "THEGROSS,THEMONEY,STUDENTID,ORDERNO,SIGN) values (?,?,?,?,?,?,"
				+ "?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),"
				+ "to_date(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?,?,?,?,?)";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{
			insertPs.setString(1, rs.getString("SaleInfoNum"));
			insertPs.setInt(2, rs.getInt("Ammeter_ID"));
			insertPs.setString(3, rs.getString("AmMeter_Name"));
			insertPs.setString(4, rs.getString("AmMeter_485Address"));
			insertPs.setInt(5, rs.getInt("Times"));
			insertPs.setInt(6, rs.getInt("Kind"));
			insertPs.setFloat(7, rs.getFloat("QSYValue"));
			insertPs.setFloat(8, rs.getFloat("HSYValue"));
			insertPs.setInt(9, rs.getInt("Users_ID"));
			insertPs.setString(10, format.format(new java.util.Date(rs
					.getTimestamp("BuyTime").getTime())));
			insertPs.setString(11, format.format(new java.util.Date(rs
					.getTimestamp("SendTime").getTime())));
			insertPs.setInt(12, rs.getInt("Status"));
			insertPs.setFloat(13, rs.getFloat("Price"));
			insertPs.setFloat(14, rs.getFloat("TheGross"));
			insertPs.setFloat(15, rs.getFloat("TheMoney"));
			insertPs.setString(16, rs.getString("studentID"));
			insertPs.setString(17, rs.getString("OrderNo"));
			insertPs.setString(18, rs.getString("sign"));

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.addBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}

	@Test
	public void transferTEMPERATUREHOUR() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from TEMPERATUREHOUR";
		String insertSql = "insert into TEMPERATUREHOUR "
				+ "(VALUE_ID,SELECTTIME,TEMPERATURE,INSERTTIME) "
				+ "values (?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?, "
				+ "to_date(?,'YYYY-MM-DD HH24:MI:SS'))";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{
			insertPs.setInt(1, rs.getInt("VALUE_ID"));
			insertPs.setString(2, format.format(new java.util.Date(rs
					.getTimestamp("SELECTTIME").getTime())));
			insertPs.setFloat(3, rs.getFloat("TEMPERATURE"));
			Timestamp ts=rs.getTimestamp("INSERTTIME");
			Date theDate=new Date();
			if(ts!=null)
				theDate=new Date(ts.getTime());
			insertPs.setString(4, format.format(theDate));	

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.addBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}
	
	
	@Test
	public void transferTEMPERATUREDAY() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from TEMPERATUREDAY";
		String insertSql = "insert into TEMPERATUREDAY "
				+ "(VALUE_ID,SELECTTIME,TEMPERATURE,INSERTTIME) "
				+ "values (?,to_date(?,'YYYY-MM-DD'),?, "
				+ "to_date(?,'YYYY-MM-DD HH24:MI:SS'))";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{
			insertPs.setInt(1, rs.getInt("VALUE_ID"));
			insertPs.setString(2, format.format(new java.util.Date(rs.getTimestamp("SELECTTIME").getTime())));
			insertPs.setFloat(3, rs.getFloat("TEMPERATURE"));
			Timestamp ts=rs.getTimestamp("INSERTTIME");
			Date theDate=new Date();
			if(ts!=null)
				theDate=new Date(ts.getTime());
			insertPs.setString(4, format.format(theDate));	

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.addBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}
	
	@Test
	public void transferAPKaihuInfo() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from APKaihuInfo";
		String insertSql = "insert into APKaihuInfo "
				+ "(ID,AMMETER_ID,THETIME,OLDSY,NEWZVALUE,SYVALUE,TZVALUE,THEMONEY,USERS_ID) values (?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),"
				+ "?,?,?,?,?,?)";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{
			insertPs.setInt(1, rs.getInt("ID"));
			insertPs.setInt(2, rs.getInt("Ammeter_ID"));
			insertPs.setString(3, format.format(new java.util.Date(rs
					.getTimestamp("THETIME").getTime())));
			insertPs.setFloat(4, rs.getFloat("OLDSY"));
			insertPs.setFloat(5, rs.getFloat("NEWZVALUE"));
			insertPs.setFloat(6, rs.getFloat("SYVALUE"));
			insertPs.setFloat(7, rs.getFloat("TZVALUE"));
			insertPs.setFloat(8, rs.getFloat("THEMONEY"));
			insertPs.setInt(9, rs.getInt("USERS_ID"));

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.addBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}

	@Test
	public void transferAmMeterAPDatas() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from AmMeterAPDatas";
		String insertSql = "insert into AmMeterAPDatas "
				+ "(AmMeter_ID,ValueTime,SYMoney,TZMoney,SYValue,"
				+ "TZValue,ThisCircleVol,LastCircleVol) values "
				+ "(?,to_date(?,'YYYY-MM-DD HH24:MI:SS')," + "?,?,?,?,?,?)";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{
			insertPs.setInt(1, rs.getInt("AmMeter_ID"));
			insertPs.setString(
					2,
					format.format(new java.util.Date(rs.getTimestamp(
							"ValueTime").getTime())));
			insertPs.setFloat(3, rs.getFloat("SYMoney"));
			insertPs.setFloat(4, rs.getFloat("TZMoney"));
			insertPs.setFloat(5, rs.getFloat("SYValue"));
			insertPs.setFloat(6, rs.getFloat("TZValue"));
			insertPs.setFloat(7, rs.getFloat("ThisCircleVol"));
			insertPs.setFloat(8, rs.getFloat("LastCircleVol"));

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.addBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}

	@Test
	public void transferApZQInfo() throws SQLException
	{
		int count = 0;

		Connection sqlserver = SqlServerHelper.getConnection();
		Connection oracle = ConnDB.getConnection();

		String readSql = "select * from ApZQInfo";
		String insertSql = "insert into ApZQInfo (Ammeter_ID,TheTime,OldSY,NewZValue,"
				+ "SYValue,TZValue,TheMoney,Users_ID) values (?,"
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?)";
		PreparedStatement readPs = sqlserver.prepareStatement(readSql);
		PreparedStatement insertPs = oracle.prepareStatement(insertSql);

		ResultSet rs = readPs.executeQuery();

		while (rs.next())
		{

			insertPs.setInt(1, rs.getInt("AmMeter_ID"));
			Date TheTime = new Date();
			if (rs.getTimestamp("TheTime") != null)
				TheTime.setTime(rs.getTimestamp("TheTime").getTime());
			insertPs.setString(2, format.format(TheTime));
			// Float OldSY = (float) 0;
			// if(rs.getFloat("OldSY")!=null)
			// OldSY=;
			insertPs.setFloat(3, rs.getFloat("OldSY"));
			// Float NewZValue = (float) 0;
			insertPs.setFloat(4, rs.getFloat("NewZValue"));
			// Float SYValue = (float) 0;
			insertPs.setFloat(5, rs.getFloat("SYValue"));
			// Float TZValue = (float) 0;
			insertPs.setFloat(6, rs.getFloat("TZValue"));
			// Float TheMoney = (float) 0;
			insertPs.setFloat(7, rs.getFloat("TheMoney"));
			insertPs.setInt(8, rs.getInt("Users_ID"));

			if (count <= 1000)
			{
				insertPs.addBatch();
				count++;
			} else
			{
				insertPs.executeBatch();
				count = 0;
			}

		}

		if (count > 0)
		{
			insertPs.executeBatch();
		}

		if (rs != null)
			rs.close();

		if (readPs != null)
			readPs.close();

		if (insertPs != null)
			insertPs.close();

		if (sqlserver != null)
			sqlserver.close();

		if (oracle != null)
			oracle.close();
	}
}
