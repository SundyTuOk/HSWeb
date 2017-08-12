package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.codehaus.jettison.json.JSONArray;

import com.sf.energy.util.ConnDB;

public class PowerOffStatisticsDao
{
	public JSONArray queryEXFZInfoByPrice(int priceId){
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT A.AMMETER_ID,meter_name,times,lasttime,valuetime,state,zvaluezy,zvaluefy,bfvoltage,bfcurrent,bfpower,aftvoltage,aftcurrent,aftpower,powerfactor "
				+" FROM (SELECT c.AMMETER_ID,times,lasttime,valuetime,state,zvaluezy,zvaluefy,bfvoltage,bfcurrent,bfpower,aftvoltage,aftcurrent,aftpower,powerfactor "
				+"	FROM ( SELECT AMMETER_ID, COUNT (AMMETER_ID) TIMES, MAX (READTIME) lasttime FROM (SELECT ammeter_id,READTIME from AMMETEREXFZ WHERE To_number(STATE)>0) AXFZ "
				+"			WHERE EXISTS ( SELECT AMMETER_ID FROM AMMETER WHERE PRICE_ID = ? AND AXFZ.AMMETER_ID = AMMETER_ID ) GROUP BY AMMETER_ID "
				+"	) c "
				+"	LEFT JOIN (SELECT * FROM AMMETEREXFZ) D ON c.AMMETER_ID = D .AMMETER_ID "
				+"	AND c.lasttime = D .READTIME "
				+"	) A "
				+"	LEFT JOIN ( SELECT ammeter_id, METER_NAME FROM V_AMMETER "
				+"	) b ON A .ammeter_id = b.ammeter_id ORDER BY meter_name";
		//System.out.println(sql);
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, priceId);
			rs = ps.executeQuery();
			while (rs.next())
			{
				JSONObject object = new JSONObject();
				object.put("ammeter_Id", rs.getInt("AMMETER_ID"));
				object.put("ammeter_Name", rs.getString("METER_NAME"));
				object.put("times", rs.getInt("times"));
				object.put("lasttime", rs.getString("lasttime"));
				object.put("valuetime", rs.getString("valuetime"));
				object.put("state", rs.getString("state"));
				object.put("zvaluezy", rs.getString("zvaluezy"));
				object.put("zvaluefy", rs.getString("zvaluefy"));
				object.put("bfvoltage", rs.getString("bfvoltage"));
				object.put("bfcurrent", rs.getString("bfcurrent"));
				object.put("bfpower", rs.getString("bfpower"));
				object.put("aftvoltage", rs.getString("aftvoltage"));
				object.put("aftcurrent", rs.getString("aftcurrent"));
				object.put("aftpower", rs.getString("aftpower"));
				object.put("powerfactor", rs.getString("powerfactor"));
				array.put(object);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return array;
	}
	
	public JSONArray queryEXFZInfoByPrice(int priceId,String startTime,String endTime){
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT A.AMMETER_ID,meter_name,times,lasttime,valuetime,state,zvaluezy,zvaluefy,bfvoltage,bfcurrent,bfpower,aftvoltage,aftcurrent,aftpower,powerfactor "
				+" FROM (SELECT c.AMMETER_ID,times,lasttime,valuetime,state,zvaluezy,zvaluefy,bfvoltage,bfcurrent,bfpower,aftvoltage,aftcurrent,aftpower,powerfactor "
				+"	FROM ( SELECT AMMETER_ID, COUNT (AMMETER_ID) TIMES, MAX (READTIME) lasttime FROM (SELECT ammeter_id,READTIME from AMMETEREXFZ WHERE To_number(STATE)>0) AXFZ "
				+"			WHERE EXISTS ( SELECT AMMETER_ID FROM AMMETER WHERE PRICE_ID = ? AND AXFZ.AMMETER_ID = AMMETER_ID ) GROUP BY AMMETER_ID "
				+"	) c "
				+"	LEFT JOIN (SELECT * FROM AMMETEREXFZ) D ON c.AMMETER_ID = D .AMMETER_ID "
				+"	AND c.lasttime = D .READTIME "
				+"	) A "
				+"	LEFT JOIN ( SELECT ammeter_id, METER_NAME FROM V_AMMETER "
				+"	) b ON A .ammeter_id = b.ammeter_id "
				+ " WHERE valuetime BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')"
				+ " ORDER BY meter_name";
		//System.out.println(sql);
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, priceId);
			ps.setString(2, startTime);
			ps.setString(3, endTime);
			rs = ps.executeQuery();
			while (rs.next())
			{
				JSONObject object = new JSONObject();
				object.put("ammeter_Id", rs.getInt("AMMETER_ID"));
				object.put("ammeter_Name", rs.getString("METER_NAME"));
				object.put("times", rs.getInt("times"));
				object.put("lasttime", rs.getString("lasttime"));
				object.put("valuetime", rs.getString("valuetime"));
				object.put("state", rs.getString("state"));
				object.put("zvaluezy", rs.getString("zvaluezy"));
				object.put("zvaluefy", rs.getString("zvaluefy"));
				object.put("bfvoltage", rs.getString("bfvoltage"));
				object.put("bfcurrent", rs.getString("bfcurrent"));
				object.put("bfpower", rs.getString("bfpower"));
				object.put("aftvoltage", rs.getString("aftvoltage"));
				object.put("aftcurrent", rs.getString("aftcurrent"));
				object.put("aftpower", rs.getString("aftpower"));
				object.put("powerfactor", rs.getString("powerfactor"));
				array.put(object);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return array;
	}
}
