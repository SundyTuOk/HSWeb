package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.DateOperation;

public class EnergyMonitorDao {
	
	
	/**
	 * 		查出总电表ID
	 * @param InfoStyle		建筑类型
	 * @param InfoID		类型ID
	 * @return
	 * @throws SQLException
	 */
	public	String	getAmmeterList(String InfoStyle,String InfoID) throws SQLException
	{
		String AmmeterList = "";
		
		String sql = "select AmmeterList from countmeterInfo where InfoStyle =? and InfoID =? ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, InfoStyle);
			ps.setString(2, InfoID);		
			rs = ps.executeQuery();			
			if(rs.next())
			{
				AmmeterList = rs.getString("AmmeterList");
			}
			else
			{
				AmmeterList = ""+0;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
				
//		ps.setString(1, InfoStyle);
//		ps.setString(2, InfoID);
//		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			AmmeterList = rs.getString("AmmeterList");
//		}
//		else
//		{
//			AmmeterList = ""+0;
//		}
		
		return AmmeterList;
	}
	
	
	/**
	 * 		查出总水表ID
	 * @param InfoStyle		建筑类型
	 * @param InfoID		类型ID
	 * @return
	 * @throws SQLException
	 */
	public	String	getWatermeterList(String InfoStyle,String InfoID) throws SQLException
	{
		String WatermeterList = "";	
		String sql = "select WatermeterList from countmeterInfo where InfoStyle =? and InfoID =? ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, InfoStyle);
			ps.setString(2, InfoID);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				WatermeterList = rs.getString("WatermeterList");
			}
			else
			{
				WatermeterList = ""+0;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
//		ps.setString(1, InfoStyle);
//		ps.setString(2, InfoID);
//		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			WatermeterList = rs.getString("WatermeterList");
//		}
//		else
//		{
//			WatermeterList = ""+0;
//		}
//		

		
		return WatermeterList;
	}

	/**
	 * 		查询指定类型建筑空调面积
	 * @param styleID
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAreaByStyle(String styleID) throws SQLException
	{
		float  data = 0;
		
		String sql = "SELECT sum(Architecture_Aircondition) as Architecture_Aircondition " +
				"FROM Architecture WHERE Architecture_STYLE = '"+styleID.charAt(0)+"'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("Architecture_Aircondition");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("Architecture_Aircondition");
//		}

		
		
		return data;
	}
	
	
	/**
	 * 			获取指定建筑空调面积
	 * @param archID	建筑ID
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAreaByArch(int archID) throws SQLException
	{
		float  data = 0;
	
		String sql = "SELECT sum(Architecture_Aircondition) as Architecture_Aircondition " +
					" FROM Architecture WHERE Architecture_ID = ?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, archID);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("Architecture_Aircondition");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
		
		
//		if(rs.next())
//		{
//			data = rs.getFloat("Architecture_Aircondition");
//		}
		
		return data;
	}
	
	/**
	 * 			查询指定建筑年空调用电
	 * @param archID
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAmByArchForYear(int archID,int year) throws SQLException
	{
		float  data = 0;
		
		String sql = "select sum(a.ZGross) as ZGross from T_MonthAm a  " +
				"where  a.SelectYear = ? and a.isComputation = 1 " +
				"and a.Architecture_ID = ? and substr(a.AmMeter_Num,14,1) = 'B'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, archID);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		//ps.setInt(1, archID);
		
//		ps.setInt(1, year);
//		ps.setInt(2, archID);
//		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}
		
		return data;
	}
	
	/**
	 * 		查询指定建筑月空调用电
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAmByArchForMonth(int archID,int year,int month) throws SQLException
	{
		float  data = 0;
		
		String sql = "select sum(a.ZGross) as ZGross from T_MonthAm a  " +
				"where  a.SelectYear = ? and a.SelectMonth = ? and a.isComputation = 1 " +
				"and a.Architecture_ID = ? and substr(a.AmMeter_Num,14,1) = 'B'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, archID);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
//		ps.setInt(1, year);
//		ps.setInt(2, month);
//		ps.setInt(3, archID);
//		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}
		
		return data;
	}
	
	/**
	 * 			查询指定建筑类型指定月份空调用电
	 * @param styleID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAmByArchStyleForMonth(String styleID,int year,int month) throws SQLException
	{
		float  data = 0;

		
		String sql = "select   sum(a.ZGross) as ZGross  from  T_MonthAm a, Architecture b " +
				" where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.isComputation = 1 " +
				"and a.Architecture_ID = b.Architecture_ID  and b.Architecture_Style = '"+styleID.charAt(0)+"' " +
				"and substr(a.AmMeter_Num,14,1) = 'B'   ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}

		
		return data;
	}
	
	/**
	 * 			查询指定建筑类型指定年份空调用电
	 * @param styleID
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public float getAirConditonAmByArchStyleForYear(String styleID,int year) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.ZGross) as ZGross  from  T_MonthAm a, Architecture b " +
				" where  a.SelectYear = "+year+"  and a.isComputation = 1 " +
				"and a.Architecture_ID = b.Architecture_ID  and b.Architecture_Style = '"+styleID.charAt(0)+"' " +
				"and substr(a.AmMeter_Num,14,1) = 'B'   ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//	
//		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}

		
		return data;
	}
	
	
	/**
	 * 		查询指定建筑类型指定日期用电情况（没有总表）
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTodayAmByarchStyleNotatal(String styleID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select  sum(a.ZGross) as ZGross  from  T_DayAm a, Architecture b " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = b.Architecture_ID " +
				"and b.Architecture_Style = '"+styleID.charAt(0)+"'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}

		
		return data;
	}
	
	
	/**
	 * 		查询指定建筑类型指定日期用水情况（没有总表）
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTodayWaterByarchStyleNotatal(String styleID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select  sum(a.ZGross) as ZGross  from  T_DayWater a, Architecture b " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = b.Architecture_ID " +
				"and b.Architecture_Style = '"+styleID.charAt(0)+"'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}
		
		return data;
	}
	
	
	/**
	 * 		查询指定建筑指定日期用电情况（没有总表）
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTodayAmByarchNotatal(int archID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.ZGross) as ZGross   from  T_DayAm a  " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = "+archID+"";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}
		
		return data;
	}
	
	
	/**
	 * 		查询指定建筑指定日期用水情况（没有总表）
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTodayWaterByarchNotatal(int archID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.ZGross) as ZGross   from  T_DayWater a  " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = "+archID+"";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("ZGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("ZGross");
//		}
		
		return data;
	}
	
	
	
	/**
	 * 		查询全校指定日期用电金额
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalAmMoneyBySchool(int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross  from  T_DayAm a " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
		
		return data;
	}
	
	/**
	 * 		查询指定建筑类型指定日期用电金额
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalAmMoneyByarchStyle(String styleID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross  from  T_DayAm a, Architecture b " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = b.Architecture_ID " +
				"and b.Architecture_Style = '"+styleID.charAt(0)+"'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
		
		return data;
	}
	
	/**
	 * 			查询指定建筑指定日期用电金额
	 * @param archID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalAmMoneyByarch(int archID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross   from  T_DayAm a  " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = "+archID+"";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
//		
		
		return data;
	}
	
	/**
	 * 		查询全校指定日期用电金额
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalWaterMoneyBySchool(int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross  from  T_DayWater a " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
		
		return data;
	}
	
	/**
	 * 		查询指定建筑类型指定日期用电金额
	 * @param styleID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalWaterMoneyByarchStyle(String styleID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross  from  T_DayWater a, Architecture b " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = b.Architecture_ID " +
				"and b.Architecture_Style = '"+styleID.charAt(0)+"'";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
		
		return data;
	}
	
	/**
	 * 			查询指定建筑指定日期用电金额
	 * @param archID
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws SQLException
	 */
	public float getTotalWaterMoneyByarch(int archID,int year,int month,int day) throws SQLException
	{
		float  data = 0;
		
		String sql = "select   sum(a.Price_Value * nvl(a.ZGross,0)) as MGross   from  T_DayWater a  " +
				"where  a.SelectYear = "+year+" and a.SelectMonth = "+month+" and a.SelectDay = "+day+" " +
				"and a.isComputation = 1 and a.Architecture_ID = "+archID+"";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				data = rs.getFloat("MGross");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			data = rs.getFloat("MGross");
//		}
		
		return data;
	}
	
	
	/**
	 * 		查询基本信息学校简介信息
	 * @return
	 * @throws SQLException
	 */
	public String getSchoolIntroduce() throws SQLException
	{
		String  Info = null;
		
		String sql = "select COMPLAYINTRODUCTION from	systeminfo ";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				Info = rs.getString("COMPLAYINTRODUCTION");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}	
		
//		rs = ps.executeQuery();
//		
//		if(rs.next())
//		{
//			Info = rs.getString("COMPLAYINTRODUCTION");
//		}
		
		return Info;
	}
	
	/**
	 * 		查询72小时各小时的温度
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public  List<AmMeterMataData> getTemperatureHourValue(Date start,Date end) throws SQLException, ParseException
	{
		List<AmMeterMataData> list = null;
		AmMeterMataData  ammd = null;
		
		list = new ArrayList<AmMeterMataData>();
		for(int i=0;i<72;i++)
		{
			ammd = new AmMeterMataData();
			ammd.setTemperature(0);
			ammd.setHour(i%24);
			list.add(ammd);
		}
		
		
		String sql = "select to_char(selecttime,'yyyy-mm-dd')cal,to_char(selecttime,'hh24')H,Temperature " +
				"from TemperatureHour where SelectTime between to_date('"+DateFormat.getDateInstance(DateFormat.DEFAULT).format(start)+" 00:00:00"+"','yyyy-mm-dd hh24:mi:ss') " +
				" and to_date('"+DateFormat.getDateInstance(DateFormat.DEFAULT).format(end)+ " 23:59:59" +"','yyyy-mm-dd hh24:mi:ss') order by cal,h";
		//System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while(rs.next())
			{ 
				String date = rs.getString("cal");
				String H = rs.getString("H");
				if(date!=null&&!"".equals(date)&&H!=null&&!"".equals(H))
				{
					int hour = Integer.parseInt(H);
					int a=getListIndex(start, end, date, hour);
					if(a != -1)
					{
						list.get(a).setTemperature(rs.getFloat("Temperature"));
					}
					else
					{
						System.out.println("非法数据");
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
//		ps.setString(1,DateFormat.getDateInstance(DateFormat.DEFAULT).format(start)+" 00:00:00" );
//		ps.setString(2,DateFormat.getDateInstance(DateFormat.DEFAULT).format(end)+ " 23:59:59" );
	
//		rs = ps.executeQuery();
//		while(rs.next())
//		{ 
//			String date = rs.getString("cal");
//			String H = rs.getString("H");
//			if(date!=null&&!"".equals(date)&&H!=null&&!"".equals(H))
//			{
//				int hour = Integer.parseInt(H);
//				int a=getListIndex(start, end, date, hour);
//				if(a != -1)
//				{
//					list.get(a).setTemperature(rs.getFloat("Temperature"));
//				}
//				else
//				{
//					System.out.println("非法数据");
//				}
//			}
//		}
		
		return list;
	}
	
	public int getListIndex(Date begindate,Date endDate,String date,int hour) throws ParseException
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date temp=df.parse(date);
		int result=-1;
		if ((temp.getTime()-begindate.getTime())==0) {
			result=hour;
		} else if((temp.getTime()-begindate.getTime())>0&&(endDate.getTime()-temp.getTime())>0){
			result=hour+24;
		}
		else
		{
			result=hour+48;
		}
		
		
		return result;
	}
	
	
	/**
	 * 			查询指定月份每一天的温度值
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public  List<AmMeterMataData> getTemperatureDayValue(int year ,int month) throws ParseException, SQLException
	{
		int MaxDay = 0;		//这个月的天数
		List<AmMeterMataData> list = null;
		AmMeterMataData  ammd = null;
		
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
		
		list = new ArrayList<AmMeterMataData>();
		
		for(int i=1;i<=MaxDay;i++)			//开始给一个月温度全设0
		{
			ammd = new AmMeterMataData();
			ammd.setTemperature(0);
			list.add(ammd);
		}
		
		String sql = "select substr(SelectTime,9,2)SelectDay,Temperature from TemperatureDay " +
				"where substr(SelectTime,1,4) = ? and substr(SelectTime,6,2) = ?";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, year+"");
			if(month<10)
			{
				ps.setString(2, "0"+month);
			}
			else
			{
				ps.setString(2, ""+month);
			}
			
			rs = ps.executeQuery();
			
			
			
			while(rs.next())			//判断结果集天数和原空数据天数相等则替换温度值
			{
				for(int i=1;i<=MaxDay;i++)
				{
					if(i<10)
					{
						if(("0"+i).equals(rs.getString("SelectDay")))
						{
							list.get(i-1).setTemperature(rs.getFloat("Temperature"));
						}
					}
					else
					{
						if((""+i).equals(rs.getString("SelectDay")))
						{
							list.get(i-1).setTemperature(rs.getFloat("Temperature"));
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setString(1, year+"");
//		if(month<10)
//		{
//			ps.setString(2, "0"+month);
//		}
//		else
//		{
//			ps.setString(2, ""+month);
//		}
//		
//		rs = ps.executeQuery();
//		
//		
//		
//		while(rs.next())			//判断结果集天数和原空数据天数相等则替换温度值
//		{
//			for(int i=1;i<=MaxDay;i++)
//			{
//				if(i<10)
//				{
//					if(("0"+i).equals(rs.getString("SelectDay")))
//					{
//						list.get(i-1).setTemperature(rs.getFloat("Temperature"));
//					}
//				}
//				else
//				{
//					if((""+i).equals(rs.getString("SelectDay")))
//					{
//						list.get(i-1).setTemperature(rs.getFloat("Temperature"));
//					}
//				}
//			}
//		}

		
		return list;
	}
	
 
	
	
}
