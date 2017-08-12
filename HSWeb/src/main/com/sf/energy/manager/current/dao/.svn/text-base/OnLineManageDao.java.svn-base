package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class OnLineManageDao
{

		public JSONArray SelectDATASITEInfo() throws SQLException
		{
			JSONArray LinesInfo=new JSONArray();
				String sql = "select a.DATASITE_ID,DATASITE_NAME,DATASITE_STATE,DATASITE_LASTCONTIME,nvl(CG,0)CG,nvl(CGOut,0)CGOut,nvl(CM,0)CM,nvl(CMOut,0)CMOut from DATASITE a "+
						 " left join (select  count(Gather_ID)CG,DATASITE_ID from  GATHER group by DATASITE_ID)b on a.DATASITE_ID=b.DATASITE_ID "+
						 " left join (select  count(Gather_ID)CGOut,DATASITE_ID from  GATHER where GATHER_STATE=0 group by DATASITE_ID)c on a.DATASITE_ID=c.DATASITE_ID "+
						 " left join (select  DATASITE_ID,count(Ammeter_ID)CM from (select a.Gather_ID,DATASITE_ID,Ammeter_ID from  GATHER a,AmMeter b where a.Gather_ID=b.Gather_ID) group by DATASITE_ID)d on a.DATASITE_ID=d.DATASITE_ID "+
						 " left join (select  DATASITE_ID,count(Ammeter_ID)CMOut from (select a.Gather_ID,DATASITE_ID,Ammeter_ID from  GATHER a,AmMeter b where a.Gather_ID=b.Gather_ID and AMMETER_STAT=0) group by DATASITE_ID)e on a.DATASITE_ID=e.DATASITE_ID";

				Connection conn=null;
				PreparedStatement ps =null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();			
					while (rs.next())
					{
						JSONObject jo = new JSONObject();
						//String DATASITE_ID= rs.getString("DATASITE_ID");
						jo.put("DATASITE_ID", rs.getString("DATASITE_ID"));
						jo.put("DATASITE_NAME", rs.getString("DATASITE_NAME"));
						jo.put("DATASITE_STATE", rs.getString("DATASITE_STATE"));
						jo.put("DATASITE_LASTCONTIME", rs.getString("DATASITE_LASTCONTIME"));
						jo.put("CG", rs.getString("CG"));
						jo.put("CGOut", rs.getString("CGOut"));
						jo.put("CM", rs.getString("CM"));
						jo.put("CMOut", rs.getString("CMOut"));
					
						LinesInfo.add(jo);
						
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps, rs);
				}
			return LinesInfo;
			}
		public JSONArray SelectGatherInfo(String DATASITE_ID) throws SQLException
		{
			JSONArray LinesInfo=new JSONArray();
				String sql = "select  a.GATHER_ID,(select DATASITE_name from DATASITE c where a. DATASITE_ID=c.DATASITE_ID)DATASITE_Name,GATHER_Name,GATHER_ADDRESS,IP,GATHER_STATE,LASTTIME,nvl(CM,0)CM,nvl(CMOut,0)CMOut from  GATHER a  "+
				 " left join (select  Gather_ID,count(Ammeter_ID)CM from (select a.Gather_ID,Ammeter_ID from  GATHER a,AmMeter b where a.Gather_ID=b.Gather_ID and DATASITE_ID="+DATASITE_ID+") group by Gather_ID)b on a.Gather_ID=b.Gather_ID "+
				 " left join (select  Gather_ID,count(Ammeter_ID)CMOut from (select a.Gather_ID,Ammeter_ID from  GATHER a,AmMeter b where a.Gather_ID=b.Gather_ID and DATASITE_ID="+DATASITE_ID+" and AMMETER_STAT=0) group by Gather_ID)c on a.Gather_ID=c.Gather_ID "+
				" where DATASITE_ID="+DATASITE_ID;

				Connection conn=null;
				PreparedStatement ps =null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();			
					while (rs.next())
					{
						JSONObject jo = new JSONObject();
						jo.put("GATHER_ID", rs.getString("GATHER_ID"));
						jo.put("DATASITE_Name", rs.getString("DATASITE_Name"));
						jo.put("GATHER_Name", rs.getString("GATHER_Name"));
						jo.put("GATHER_ADDRESS", rs.getString("GATHER_ADDRESS"));
						jo.put("IP", rs.getString("IP"));
						jo.put("GATHER_STATE", rs.getString("GATHER_STATE"));
						jo.put("LASTTIME", rs.getString("LASTTIME"));
						jo.put("CM", rs.getString("CM"));
						jo.put("CMOut", rs.getString("CMOut"));
					
						LinesInfo.add(jo);
						
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps, rs);
				}
			return LinesInfo;
			}
		public JSONArray SelectMeterInfo(String GATHER_ID) throws SQLException
		{
			JSONArray LinesInfo=new JSONArray();
				String sql = "select AMMETER_ID,AMMETER_NAME,AMMETER_485ADDRESS,(select METESTYLE_Name from METESTYLE b where a.METESTYLE_ID=b.METESTYLE_ID)METESTYLE_Name, AMMETER_STAT,a.LASTTIME,b.GATHER_NAME from AMMETER a, GATHER b where a.Gather_ID=b.Gather_ID and a.Gather_ID="+GATHER_ID+" order by METESTYLE_ID";

				Connection conn=null;
				PreparedStatement ps =null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();			
					while (rs.next())
					{
						JSONObject jo = new JSONObject();
						jo.put("AMMETER_ID", rs.getString("AMMETER_ID"));
						jo.put("GATHER_NAME", rs.getString("GATHER_NAME"));
						jo.put("AMMETER_NAME", rs.getString("AMMETER_NAME"));
						jo.put("AMMETER_485ADDRESS", rs.getString("AMMETER_485ADDRESS"));
						jo.put("METESTYLE_Name", rs.getString("METESTYLE_Name"));
						jo.put("AMMETER_STAT", rs.getString("AMMETER_STAT"));
						jo.put("LASTTIME", rs.getString("LASTTIME"));
				
						LinesInfo.add(jo);
						
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn, ps, rs);
				}
			return LinesInfo;
			}
		
}
