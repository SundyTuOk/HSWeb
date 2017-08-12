package com.sf.energy.water.statistics.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.statistics.model.WaterLoadTrendData;

public class WaterLoadTrendImpl implements WaterLoadTrendHelper {
	
	public List<WaterLoadTrendData> getWaterLoadTrend(int Watermeter_ID, String date)  throws SQLException{
		
	
		String  d1 = date + "00:00:00",
				d2 = date + "23:59:59";
		List<WaterLoadTrendData> WaterLoadTrend_list = new LinkedList<WaterLoadTrendData>();
		WaterLoadTrendData	wltd = null;
		String sql = "select ZVALUEZY,VALUETIME,Watermeter_name,area_name,area_address  from ZWATERDATAS"+String.valueOf(Watermeter_ID)+","
				+ "Watermeter,area" +
				" where Watermeter.Watermeter_ID = ? and ValueTime between " +
					       "to_date(?,'mm/dd/yyyy hh24:mi:ss') and to_date(?,'mm/dd/yyyy hh24:mi:ss')" 
					       //" and Watermeter.Watermeter_ID = ZWATERDATAS"+String.valueOf(Watermeter_ID)+".Watermeter_ID "
					       		+ "and Watermeter.area_id = area.area_id order by VALUETIME ";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Watermeter_ID);
			pstmt.setString(2,d1 );
			pstmt.setString(3,d2 );
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				wltd = new WaterLoadTrendData();
				wltd.setArea_name(rs.getString("area_name"));
				wltd.setArea_address(rs.getString("area_address"));
				wltd.setWaterValue(rs.getFloat("ZVALUEZY")); 
				wltd.setDate((rs.getString("ValueTime")).substring(10, 16));
				wltd.setWatermeter_name(rs.getString("Watermeter_NAME"));
				WaterLoadTrend_list.add(wltd);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return WaterLoadTrend_list;
	}
	
	
	/**
	 * 			获取指定时间段内的用水数据
	 * @param Watermeter_ID
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public List<WaterLoadTrendData> getDataInfo(int Watermeter_ID, String date)  throws SQLException{
		
	
		String  d1 = date + " 00:00:00",
				d2 = date + " 23:59:59";
		List<WaterLoadTrendData> WaterLoadTrend_list = new LinkedList<WaterLoadTrendData>();
		WaterLoadTrendData	wltd = null;
		String sql = "select ZValueZY,CurrentFlow, valueTime from ZWATERDATAS"+String.valueOf(Watermeter_ID)+" " +
				"where ValueTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') " +
				"	and to_date(?,'yyyy-mm-dd hh24:mi:ss')  order by  ValueTime  ";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,d1 );
			pstmt.setString(2,d2 );
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				wltd = new WaterLoadTrendData();
				wltd.setWaterValue(rs.getFloat("ZVALUEZY")); 
				wltd.setValuerate(rs.getFloat("CurrentFlow"));
				wltd.setDate((rs.getString("ValueTime")).substring(10, 16));
				wltd.setValueTime(rs.getString("ValueTime"));
				WaterLoadTrend_list.add(wltd);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return WaterLoadTrend_list;
	}
	
	/**
	 * 		计算水的流量
	 * @param list
	 * @return
	 * @throws ParseException
	 * @throws SQLException 
	 */
	public	List<WaterLoadTrendData>	getcountWaterData(int Watermeter_ID, String date) throws ParseException, SQLException
	{
		List<WaterLoadTrendData>  list = getDataInfo(Watermeter_ID, date);
		
//		List<WaterLoadTrendData> 	list_back = null;	//返回的list
//		float minusTime = 0;		//相差的时间数
//		float	value = -1;			//水量
//		float	valuerate = 0;		//流量
//		WaterLoadTrendData	wltd = null;
//		
//		list_back = new ArrayList<WaterLoadTrendData>();
//		
//		for(int i=0;i<list.size()-1;i++)
//		{
//			String d1 = list.get(i).getValueTime();
//			String d2 = list.get(i+1).getValueTime();
//			
//			wltd = new WaterLoadTrendData();
//			minusTime = getTimeDifference(d1,d2);
//			value = list.get(i+1).getWaterValue()-list.get(i).getWaterValue();
//			valuerate = value/minusTime;
//			
//			wltd.setValueTime(d2);
//			wltd.setMinusTime(minusTime);
//			wltd.setWaterValue(value);
//			wltd.setValuerate(valuerate);
//			list_back.add(wltd);
//		}
//		
		return list;
	}
	
	
	//计算时间差
	public float  getTimeDifference(String date1,String date2) throws ParseException
	{
		/*SimpleDateFormat  datefomat = new SimpleDateFormat("yyyy-mm-dd hh24:mi:ss");
		Date  d1 =  datefomat.parse(date1);
		Date  d2 =  datefomat.parse(date2);
		
		Calendar  c = Calendar.getInstance();
		c.setTime(d1);*/
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String da1=(df.parse(date1)).toString();
		String da2=(df.parse(date2)).toString();
		Date  d1 = new Date(da1);
		Date  d2 =  new Date(da2);;
		
		long temp = d2.getTime()-d1.getTime();		//相差毫秒数
		
		long second = temp/1000;					//相差秒数
		
		return second;
	}
	
	

}
