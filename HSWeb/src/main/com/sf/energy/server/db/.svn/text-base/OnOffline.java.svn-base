package com.sf.energy.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sf.energy.server.serverReciveMessage.ServerData;
import com.sf.energy.util.ConnDB;

public class OnOffline
{
	public static Logger logger = Logger.getLogger(OnOffline.class);
	
	/**
	 * 数据中转站定时上传的在线终端更新数据库
	 * 
	 * @param terminaladdress 网关地址            
	 * @param ip
	 * @param time
	 * @throws SQLException 
	 */
	public static void dGOnlineUpdate(String terminaladdress, String ip,
			String time) throws SQLException
	{
		//System.out.println(terminaladdress + "  " + ip);
		String strsql = "update Gather set LastTime='" + time
				+ "',Gather_State=1,IP='" + ip + "' where Gather_Address='"
				+ terminaladdress + "'";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql);
			ps.executeUpdate();
		} finally {
			ConnDB.release(conn, ps);
		}
		
	}
	
	/**
	 * 根据网关地址查找网关ID
	 * 
	 * @param terminaladdress    网关地址
	 * @return
	 * @throws SQLException 
	 */
	public static int findTerminalId(String terminaladdress) throws SQLException
	{
		int terminalid = 0;
		logger.info("网关" + terminaladdress);
		String strsql = "select Gather_ID from Gather where Gather_Address='"
				+ terminaladdress + "'";
		PreparedStatement ps=null;
		ResultSet result=null;
		Connection conn = null;
		try
		{
			conn = ConnDB.getConnection();
			ps=conn.prepareStatement(strsql);
			result = ps.executeQuery();
			while(result.next())
			{	
			terminalid = Integer.parseInt(result.getString("Gather_ID"));
			}
		} finally
		{
			ConnDB.release(conn, ps, result);
		}
		
		return terminalid;
	}
	
	/**
	 * 数据中转站定时上传的在线表计更新数据库
	 * 
	 * @param terminalid
	 * @param meteraddress
	 * @param time
	 * @param metertype
	 * @throws SQLException 
	 */
	public void meterOnlineUpdate(int terminalid, String meteraddress,
			String time, String metertype) throws SQLException
	{
		// 默认电表
		PreparedStatement ps=null;
		Connection conn =null;
		String strsql = "update AmMeter set LastTime='" + time
				+ "',AmMeter_Stat=1 where Gather_ID='" + terminalid
				+ "' and AmMeter_485Address='" + meteraddress + "'";
		if (metertype == "2") // 水表
		{
			strsql = "update WaterMeter set LastTime='" + time
					+ "',WaterMeter_Stat=1 where Gather_ID='" + terminalid
					+ "' and WaterMeter_485Address='" + meteraddress + "'";
		}
		
		try
		{
			conn = ConnDB.getConnection();
			ps=conn.prepareStatement(strsql);
			ps.executeUpdate();
		} finally
		{
			ConnDB.release(conn, ps);
		}
	}
}
