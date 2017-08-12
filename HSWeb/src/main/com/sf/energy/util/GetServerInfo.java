package com.sf.energy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 获取服务器相关信息类
 * @author yanhao
 *
 */
public class GetServerInfo
{
	/**
	 * 获取web与服务器通信的超时时间
	 * @return
	 * @throws SQLException
	 */
	public static int getServerOverTime() throws SQLException
	{
		int time=0;
		
		String sql = "select ServerInfo_OverTime  from "
				+ "ServerInfo where rownum<=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				time = rs.getInt("ServerInfo_OverTime");
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		
		return time;
	}
}
