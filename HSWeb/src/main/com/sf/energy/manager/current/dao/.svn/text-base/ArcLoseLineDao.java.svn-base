package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;



public class ArcLoseLineDao
{
	public List<Integer> getChildren(int ammeter_ID){
		List<Integer> list=new ArrayList<Integer>();
		String sql="select a.Ammeter_ID from ammeter a where A.PARENTID="+ammeter_ID;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getInt("Ammeter_ID"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		return list;
	}
	public JSONObject getInfo(int parentID, String startGetTime,
			String endGetTime) throws SQLException
	{
		String sql = "select a.*,b.area_name,c.architecture_name from V_ammeter a,area b,architecture c where A.AREA_ID=b.area_ID and A.ARCHITECTURE_ID=C.ARCHITECTURE_ID and ammeter_Id="+parentID;
		JSONObject jo = new JSONObject();
		int ammeterID = 0;
		String ammeterName = "";
		String areaName="";
		String archName="";
		String macAddress = "";
		String beilv = "";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				ammeterName=rs.getString("AMMETER_NAME");
				areaName=rs.getString("AREA_NAME");
				archName=rs.getString("ARCHITECTURE_NAME");
				macAddress = rs.getString("AMMETER_485ADDRESS");
				beilv = rs.getString("BEILV");

				if ("".equals(ammeterName) || ammeterName == null)
					ammeterName = "--";
				if ("".equals(macAddress) || macAddress == null)
					macAddress = "--";
				if ("".equals(beilv) || beilv == null)
					beilv = "--";

				jo.put("ammeterName", ammeterName);
				jo.put("areaName", areaName);
				jo.put("archName", archName);
				jo.put("macAddress", macAddress);
				jo.put("beilv", beilv);
				ammeterID = parentID;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String startTime = "";
		String startValue = "";
		String endTime = "";
		String endValue = "";
		String sql1 = "select to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss')Time,ZVALUEZY from ZAMDATAS"+String.valueOf(ammeterID)+" "
				+ "where VALUETIME between to_date('"
				+ startGetTime
				+ "','yyyy-mm-dd hh24:mi') and to_date('"
				+ endGetTime
				+ "','yyyy-mm-dd hh24:mi') order by VALUETIME";
		Connection conn1=null;
		PreparedStatement ps1 = null;
		ResultSet rs1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				startTime = rs1.getString("Time");
				startValue = rs1.getString("ZVALUEZY");
				rs1.last();
				endTime = rs1.getString("Time");
				endValue = rs1.getString("ZVALUEZY");

				jo.put("startTime",startTime );
				jo.put("startValue", startValue);
				jo.put("endTime", endTime);
				jo.put("endValue", endValue);

				float totalValue = Float.parseFloat(endValue)
						- Float.parseFloat(startValue);
				jo.put("useValue", totalValue);

			} else
			{

				startTime = "--";
				startValue = "--";
				endTime = "--";
				endValue = "--";
				jo.put("startTime", startTime);
				jo.put("startValue", startValue);
				jo.put("endTime", endTime);
				jo.put("endValue", endValue);
				jo.put("useValue", "--");
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return jo;
		

	}
}
