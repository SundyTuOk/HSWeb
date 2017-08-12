package com.sf.energy.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.statistics.model.AmLoadTrendData;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.util.ConnDB;

/**
 * 	通过电表ID和日期查询负载趋势详细信息
 * @author Administrator
 *
 */
public class  AmLoadTrendImpl implements AmLoadTrendHelper{
	public List<AmLoadTrendData> getAmLoadTrend(int Ammeter_ID, String date)  throws SQLException{
	
		String  d1 = date + "00:00:00",
				d2 = date + "23:59:59";
		List<AmLoadTrendData> AmLoadTrend_list = new LinkedList<AmLoadTrendData>();
		AmLoadTrendData	altd = null;
		String sql = "select PowerZY,Ammeter_name,VALUETIME,area_name,architecture_name " +
				" from ZPDDATAS"+String.valueOf(Ammeter_ID)+",Ammeter,area,architecture " +
				"where Ammeter.Ammeter_ID = ? " +
				"and ValueTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') " +
				"and to_date(?,'yyyy-mm-dd hh24:mi:ss') " +
				//"and Ammeter.Ammeter_ID = ZPDDATAS"+String.valueOf(Ammeter_ID)+".Ammeter_ID " +
				"and Ammeter.architecture_ID = architecture.architecture_ID " +
				"and Ammeter.area_id = area.area_id order by VALUETIME ";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(2,d1 );
			ps.setString(3,d2 );
			ps.setInt(1, Ammeter_ID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				altd = new AmLoadTrendData();
				altd.setArea_name(rs.getString("area_name"));
				altd.setArchitecture_name(rs.getString("architecture_name"));
				altd.setPowerValue(rs.getFloat("PowerZY")); 
				altd.setDate((rs.getString("ValueTime")).substring(10, 16));
				altd.setAmmeter_name(rs.getString("Ammeter_NAME"));
				AmLoadTrend_list.add(altd);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return AmLoadTrend_list;
	}
	

}
