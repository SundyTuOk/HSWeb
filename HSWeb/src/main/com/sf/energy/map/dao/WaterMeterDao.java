package com.sf.energy.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.map.model.WaterMeterModel;
import com.sf.energy.util.ConnDB;

public class WaterMeterDao {
	
	//根据水表ID查水表信息
	public WaterMeterModel queryWaterMeterById(String id) throws SQLException
	{
	    WaterMeterModel waterMeterModel=new WaterMeterModel();
		String sql="select WATERMETER_NAME,WATERMETER_485ADDRESS from WATERMETER where WATERMETER_ID="+id;
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
				waterMeterModel.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				waterMeterModel.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		if (rs.next())
//		{
//			waterMeterModel.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
//			waterMeterModel.setWATERMETER_485ADDRESS(rs.getString("WATERMETER_485ADDRESS"));
//		}
//		if(rs!=null)
//			rs.close();
//		
//		if(ps!=null)
//			ps.close();
		return waterMeterModel;
	}
	
	//根据建筑ID和楼层号，查找相应水表
	public List<WaterMeterModel> queryWaterMeterByFloor(String arcId,String floor) throws SQLException
	{
		List<WaterMeterModel> waterList=new ArrayList<WaterMeterModel>();
		String sql="select WATERMETER_NAME,WATERMETER_ID from WATERMETER where ARCHITECTURE_ID="+arcId+" and FLOOR="+floor;
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
				WaterMeterModel waterMeterModel=new WaterMeterModel();
				waterMeterModel.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				waterMeterModel.setWATERMETER_ID(rs.getString("WATERMETER_ID"));
				waterList.add(waterMeterModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			WaterMeterModel waterMeterModel=new WaterMeterModel();
//			waterMeterModel.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
//			waterMeterModel.setWATERMETER_ID(rs.getString("WATERMETER_ID"));
//			waterList.add(waterMeterModel);
//		}
//		if(rs!=null)
//			rs.close();
//		
//		if(ps!=null)
//			ps.close();
		return waterList;
	}
	
	//获取水表当前示数
	public int getWaterMeterRead(String id) throws SQLException
	{
		int waterMeterValue=0;
		String sql="select max(ZVALUEZY) waterMetervalue from ZWATERDATAS"+String.valueOf(id);
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
				waterMeterValue=rs.getInt("waterMetervalue");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		if (rs.next())
//		{
//			waterMeterValue=rs.getInt("waterMetervalue");
//		}
//		if(rs!=null)
//			rs.close();
//		
//		if(ps!=null)
//			ps.close();
		return waterMeterValue;
	}
	//得到建筑48小时用水状况图
	public String getMapArcWater48Hour(String startTime,String endTime) throws SQLException
	{
		int time=0;
		String StyleName = "用水";
		String  Unit = "吨";
		String yAxisName = "T";
		String strWorkingStartTime = "08:00";
		String strWorkingEndTime = "18:00";
		String valueText="";
		int total=0;
		String chatString="<graph caption='24小时" + StyleName + "状况图  ' subCaption='" + startTime + " 至 " + endTime + "' chartLeftMargin='0'  chartRightMargin='0' chartTopMargin='0' chartBottomMargin='0' baseFont='宋体' baseFontSize='11' numVDivLines='0' numDivLines='4' WorkingStartTime='" + 500 + "' WorkingEndTime='" + 1000 + "'  yAxisName='" + yAxisName + "'  showValues='0' decimalPrecision='2' formatNumberScale='0'>";
		String sql="select theTime,theData from MAPWATERMETERHOURDATA  order by theTime asc";
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
				total=total+rs.getInt("theData");
				valueText = "value='" + rs.getInt("theData") + "'";
				if(time%4==0)
				{
					chatString+="<set name='" + rs.getString("theTime").substring(11,16) + "' " + valueText + " hoverText='" + rs.getString("theTime") + "' color='0906FF'/>\r\n";

				}
				else {
					chatString+="<set  " + valueText + " hoverText='" + rs.getString("theTime") + "' color='0906FF'/>\r\n";				
				}
				++time;
			}
			chatString+="</graph>|"+endTime + "日" + StyleName + "：" +total+" " + Unit;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			total=total+rs.getInt("theData");
//			valueText = "value='" + rs.getInt("theData") + "'";
//			if(time%4==0)
//			{
//				chatString+="<set name='" + rs.getString("theTime").substring(11,16) + "' " + valueText + " hoverText='" + rs.getString("theTime") + "' color='0906FF'/>\r\n";
//
//			}
//			else {
//				chatString+="<set  " + valueText + " hoverText='" + rs.getString("theTime") + "' color='0906FF'/>\r\n";				
//			}
//			++time;
//		}
//		chatString+="</graph>|"+endTime + "日" + StyleName + "：" +total+" " + Unit;
//
//		if(rs!=null)
//			rs.close();
//
//		if(ps!=null)
//			ps.close();
		return chatString;

	}
		
		//得到建筑水表分项性质用水chat xml
		public String getMapArcWaterFenStyle(String Name,String Architecture_ID,String theYear,String theMonth) throws SQLException
		{
			String chatString="<graph caption='" + Name + "  " + theYear + "年" + theMonth + "月 用水结构分析' baseFont='宋体' baseFontSize='11'   yAxisName='T' showValues='1' decimalPrecision='2' formatNumberScale='0' showPercentageInLabel='1' showValues='0'>";
			int counGross=0;
			String fenstylesql="select DictionaryValue_Value  Style,nvl(ZGross,0) ZGross from (select  DictionaryValue_Num,DictionaryValue_Value from DictionaryValue where Dictionary_ID=25)a left join (select Fenlei,sum(ZGross)ZGross from T_ARCFENLEIDAYWATER where Architecture_ID="+Architecture_ID+" and SelectYear="+theYear+" and SelectMonth="+theMonth+" group by Fenlei)  b on DictionaryValue_Num=Fenlei order by ZGross desc";
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(fenstylesql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				while (rs.next())
				{
					counGross=counGross+rs.getInt("ZGross");
					chatString+="<set name='" + rs.getString("Style") + "' value='" + rs.getString("ZGross") + "' hoverText='" + rs.getString("Style") + "'/>";	
				
				}
				chatString+= "</graph>|" + theYear + "年" + theMonth + "月 用水" + counGross + "吨";
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			PreparedStatement ps=ConnDB.getConnection().prepareStatement(fenstylesql);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next())
//			{
//				counGross=counGross+rs.getInt("ZGross");
//				chatString+="<set name='" + rs.getString("Style") + "' value='" + rs.getString("ZGross") + "' hoverText='" + rs.getString("Style") + "'/>";	
//			
//			}
//			chatString+= "</graph>|" + theYear + "年" + theMonth + "月 用水" + counGross + "吨";
//			
//			if(rs!=null)
//				rs.close();
//			
//			if(ps!=null)
//				ps.close();
			return chatString;
		}
}
