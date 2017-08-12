package com.sf.energy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * 获取系统信息类（暂时只用来获取学校名字）
 * @author yanhao
 *
 */
public class GetSystemInfo
{
		/**
		 * 获取学校的名称
		 * @return
		 * @throws SQLException
		 */
		public static String getSchoolName() throws SQLException
		{
			String name = " ";
			String sql = "select SYSTEMINFO_COMPLAYNAME theName from "
					+ "SystemInfo where rownum<=1";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					name = rs.getString("theName");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			return name;
		}
		
		
		/**
		 * 获取工作时间
		 * @return
		 * @throws SQLException
		 */
		public static Map<String, String> getWorkTime() throws SQLException
		{
			Map<String, String> timeMap = new HashMap<String, String>();
			String sql = "select  nvl(WorkingStartTime,'08:00') WorkingStartTime,nvl(WorkingEndTime,'18:00') WorkingEndTime from SystemInfo where rownum<=1";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					timeMap.put("startTime", rs.getString("WorkingStartTime"));
					timeMap.put("endTime", rs.getString("WorkingEndTime"));
				}

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			return timeMap;
		}
		
		/**
		 * 获取工作时间,计算成xml节点中所需要的值
		 * @return
		 * @throws SQLException
		 */
		public static Map<String, Integer> getWorkTimeForXML() throws SQLException
		{
			Map<String, Integer> timeMap = new HashMap<String, Integer>();
			String sql = "select  nvl(WorkingStartTime,'08:00') WorkingStartTime,nvl(WorkingEndTime,'18:00') WorkingEndTime from SystemInfo where rownum<=1";
			String start="";
			String end="";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					start=rs.getString("WorkingStartTime");
					end=rs.getString("WorkingEndTime");
					
					String[] startArray=start.split(":");
					String[] endArray=end.split(":");
					timeMap.put("startTime", Integer.parseInt(startArray[0])*60+Integer.parseInt(startArray[0]));
					timeMap.put("endTime", Integer.parseInt(endArray[0])*60+Integer.parseInt(endArray[0]));
				}

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			/*
			PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{
				start=rs.getString("WorkingStartTime");
				end=rs.getString("WorkingEndTime");
				
				String[] startArray=start.split(":");
				String[] endArray=end.split(":");
				timeMap.put("startTime", Integer.parseInt(startArray[0])*60+Integer.parseInt(startArray[0]));
				timeMap.put("endTime", Integer.parseInt(endArray[0])*60+Integer.parseInt(endArray[0]));
			}

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();*/

			return timeMap;
		}
		
		/**
		 * 获取系统起始年份
		 * @return
		 */
		public static int getSystemStartYear()
		{
			int startYear=2010;
			XMLConfiguration config=null;
			try
			{
				config = Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
				
			} catch (ConfigurationException e)
			{
				e.printStackTrace();
				return 2010;
			}
			startYear=Integer.parseInt(config.getString("systemInfo.startYear"));
			//System.out.println("startYear:"+startYear);
			return startYear;
		}
		
		public static void main(String args[])
		{
			Map<String, Integer> timeMap = new HashMap<String, Integer>();
			try
			{
				timeMap=GetSystemInfo.getWorkTimeForXML();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			//System.out.println(timeMap.get("startTime"));
			//System.out.println(timeMap.get("endTime"));
			
			//System.out.println(GetSystemInfo.getSystemStartYear());
		}
		
		/**
		 * 获取超级管理员的id
		 * @return
		 * @throws SQLException
		 */
		public static int getAdminID() throws SQLException
		{
			int id = 1;
			String sql = "select Users_ID from Users_roles where roles_ID= (select roles_ID from Roles where roles_Name='系统管理员')";
			Connection conn = null;
			PreparedStatement ps =null;
			ResultSet rs =null;
			try{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					id = rs.getInt("Users_ID");
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps,rs);
			}

			/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{
				id = rs.getInt("Users_ID");
			}

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();*/

			return id;
		}
}
