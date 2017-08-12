package com.sf.energy.water.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.ReportUtil.servlet.EveryMonthCountModel;
import com.sf.energy.expert.dao.EnergyMonitorDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.DateOperation;
import com.sf.energy.util.GetSystemInfo;
import com.sf.energy.water.statistics.model.WaterMeterMataData;
import com.sf.energy.water.statistics.model.WaterReportModel;

/**
 * 水计量子系统统计功能封装
 * 
 * @author 王钊是个帅小伙儿
 * @version 1.0
 * @since version 1.0
 */
public class WaterReportHelperImpl implements WaterReportHelper
{
	// private ResultSet rs = null;
	// private PreparedStatement pstmt = null;
	private DepartmentDao groupDao = new DepartmentDao();
	// private Connection conn = null;
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/***
	 * 构造函数，初始化是就取得连接数据库链接
	 */
	public WaterReportHelperImpl()
	{
		// conn = ConnDB.getConnection();
	}

	/**
	 * 查询全校指定年份指定月段的用水性质情况
	 * 
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getSchoolAmStyleByAll(int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "							where SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  " + "							where SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, (year - 1));
			pstmt.setInt(5, startmonth);
			pstmt.setInt(6, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定年份指定月段的用水性质情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAreaAmStyleByAll(int areaID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "	where Area_ID=?	and SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  " + "							where Area_ID=?	and SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, areaID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定建筑指定年份指定月段的用水性质情况
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getArcAmStyleByAll(int arcID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "	where Architecture_ID=?	  and SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "							where Architecture_ID=?	  and SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, arcID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询全校指定年份指定月段的用水性质子项用电情况
	 * 
	 * @param style
	 *            子项编码
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getSchoolAmZXStyleByAll(String style, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  " + "	where  SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  " + "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  " + "							where SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setString(4, style);
			pstmt.setInt(5, (year - 1));
			pstmt.setInt(6, startmonth);
			pstmt.setInt(7, endmonth);
			pstmt.setString(8, style);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定年份指定月段的用水性质子项用电情况
	 * 
	 * @param style
	 *            子项编码
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAreaAmZXStyleByAll(String style, int areaID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "	where Area_ID=?	  and SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  " + "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "							where Area_ID=?	  and SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setString(5, style);
			pstmt.setInt(6, areaID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setString(10, style);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定建筑指定年份指定月段的用水性质子项用电情况
	 * 
	 * @param style
	 *            子项编码
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getArcAmZXStyleByAll(String style, int arcID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross " + "	from " + "	(	 "
				+ "		select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "		from  "
				+ "	(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  " + "	     (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "	where Architecture_ID=?	  and SelectYear=? and SelectMonth	 "
				+ "	                 between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  " + "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater  "
				+ "							where Architecture_ID=?	  and SelectYear=? and SelectMonth  "
				+ "	                  between ? and ? and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setString(5, style);
			pstmt.setInt(6, arcID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setString(10, style);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询全校指定年份指定月段的用水分项用水情况
	 * 
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getSchoolAmFenxiangByAll(int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, (year - 1));
			pstmt.setInt(5, startmonth);
			pstmt.setInt(6, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定年份指定月段的用水分项用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAreaAmFenxiangByAll(int areaID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Area_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Area_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, areaID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定建筑指定年份指定月段的用水分项用水情况
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getArcAmFenxiangByAll(int arcID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Architecture_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Architecture_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, arcID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询全校指定年份指定月段的用水分项子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getSchoolAmFenxiangByZX(int styleID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, styleID);
			pstmt.setInt(5, (year - 1));
			pstmt.setInt(6, startmonth);
			pstmt.setInt(7, endmonth);
			pstmt.setInt(8, styleID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定年份指定月段的用水分项子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年份
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAreaAmFenxiangByZX(int styleID, int areaID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Area_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Area_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, styleID);
			pstmt.setInt(6, areaID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setInt(10, styleID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定建筑指定年份指定月段的用水分项子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getArcAmFenxiangByZX(int styleID, int arcID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross "
				+ "	 from "
				+ "	(	"
				+ "		select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from "
				+ "	( "
				+ "		select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 "
				+ "	left join "
				+ "	   (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Architecture_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T2 on T1.FenX=T2.FenX)T3 "
				+ "	left join "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater where Architecture_ID=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? and UseAmStyle=? )T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, styleID);
			pstmt.setInt(6, arcID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setInt(10, styleID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询所有部门指定年份指定月段的用水性质用水情况
	 * 
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAllPartmentAmStyleByAll(int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  from (select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from  "
				+ "				(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  "
				+ "	      (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, (year - 1));
			pstmt.setInt(5, startmonth);
			pstmt.setInt(6, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定部门指定年份指定月段的用水性质用水情况
	 * 
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getPartmentAmStyleByAll(int parID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  from (select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from  "
				+ "				(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  "
				+ "	      (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where Partment=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where Partment=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, parID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询所有部门指定年份指定月段的用水性质子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAllPartmentAmStyleByZX(String styleID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  from (select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from  "
				+ "				(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  "
				+ "	      (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ?  and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where  SelectYear=? and SelectMonth "
				+ "	                  between ? and ?  and substr(WaterMeter_Num,14,1)=? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setString(4, styleID);
			pstmt.setInt(5, (year - 1));
			pstmt.setInt(6, startmonth);
			pstmt.setInt(7, endmonth);
			pstmt.setString(8, styleID);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定部门指定年份指定月段的用水性质子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getPartmentAmStyleByZX(String styleID, int parID, int year, int startmonth, int endmonth)
			throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.UseAmStyle,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  from (select T1.UseAmStyle,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross "
				+ "	from  "
				+ "				(select DictionaryValue_Num as UseAmStyle,DictionaryValue_value from DictionaryValue where Dictionary_ID =26)T1  "
				+ "	left join  "
				+ "	      (select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where Partment=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?  and substr(AmMeter_Num,14,1)=? group by UseAmStyle)T2 on T1.UseAmStyle=T2.UseAmStyle)T3  "
				+ "	left join  "
				+ "				(select sum(ZGross) as Gross,UseAmStyle from T_MonthWater where Partment=? and SelectYear=? and SelectMonth "
				+ "	                  between ? and ?  and substr(AmMeter_Num,14,1)=? group by UseAmStyle)T4 on T3.UseAmStyle=T4.UseAmStyle";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setString(5, styleID);
			pstmt.setInt(6, parID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setString(10, styleID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询所有部门指定年份指定月段的用水分项用水情况
	 * 
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAllPartmentAmFenxiangByAll(int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  " + "	from  "
				+ "				(select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "	from  "
				+ "				(select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 " + "	left join  "
				+ "	       (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "						where  SelectYear=? and SelectMonth " + "	             between ? and ?)T group by FenX)T2 on T1.FenX=T2.FenX)T3  "
				+ "	left join  " + "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "			where  SelectYear=? and SelectMonth " + "	          between ? and ?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, (year - 1));
			pstmt.setInt(5, startmonth);
			pstmt.setInt(6, endmonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定部门指定年份指定月段的用水分项用水情况
	 * 
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getPartmentAmFenxiangByAll(int parID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  " + "	from  "
				+ "				(select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "	from  "
				+ "				(select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 " + "	left join  "
				+ "	       (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "						where Partment=? and SelectYear=? and SelectMonth "
				+ "	             between ? and ?)T group by FenX)T2 on T1.FenX=T2.FenX)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "			where Partment=? and SelectYear=? and SelectMonth " + "	          between ? and ?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, parID);
			pstmt.setInt(6, (year - 1));
			pstmt.setInt(7, startmonth);
			pstmt.setInt(8, endmonth);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询所有部门指定年份指定月段的用水分项子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getAllPartmentAmFenxiangByZX(int styleID, int year, int startmonth, int endmonth) throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  " + "	from  "
				+ "				(select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "	from  "
				+ "				(select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 " + "	left join  "
				+ "	       (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "						where  SelectYear=? and SelectMonth "
				+ "	             between ? and ? and UseAmStyle=?)T group by FenX)T2 on T1.FenX=T2.FenX)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "			where  SelectYear=? and SelectMonth " + "	          between ? and ? and UseAmStyle=?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, startmonth);
			pstmt.setInt(3, endmonth);
			pstmt.setInt(4, styleID);
			pstmt.setInt(5, (year - 1));
			pstmt.setInt(6, startmonth);
			pstmt.setInt(7, endmonth);
			pstmt.setInt(8, styleID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定部门指定年份指定月段的用水分项子项用水情况
	 * 
	 * @param styleID
	 *            子项编码
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param startmonth
	 *            起始月份
	 * @param endmonth
	 *            截止月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, WaterReportModel> getPartmentAmFenxiangByZX(int styleID, int parID, int year, int startmonth, int endmonth)
			throws SQLException
	{
		WaterReportModel rm = null;
		Map<String, WaterReportModel> map = new HashMap<String, WaterReportModel>();

		String sql = "select T3.FenX,T3.DictionaryValue_value,ThisYGross,nvl(T4.Gross,0) as LastYGross  " + "	from  "
				+ "				(select T1.FenX,T1.DictionaryValue_value,nvl(Gross,0)ThisYGross  " + "	from  "
				+ "				(select DictionaryValue_Num as FenX,DictionaryValue_value from DictionaryValue where Dictionary_ID =25)T1 " + "	left join  "
				+ "	       (select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "						where Partment=? and SelectYear=? and SelectMonth "
				+ "	             between ? and ? and UseAmStyle=?)T group by FenX)T2 on T1.FenX=T2.FenX)T3  " + "	left join  "
				+ "				(select sum(ZGross) as Gross,FenX from(select ZGross,substr(WaterMeter_Num,14,1)FenX from T_MonthWater  "
				+ "			where Partment=? and SelectYear=? and SelectMonth "
				+ "	          between ? and ? and UseAmStyle=?)T group by FenX)T4 on T3.FenX=T4.FenX";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, startmonth);
			pstmt.setInt(4, endmonth);
			pstmt.setInt(5, styleID);
			pstmt.setInt(6, parID);
			pstmt.setInt(7, (year - 1));
			pstmt.setInt(8, startmonth);
			pstmt.setInt(9, endmonth);
			pstmt.setInt(10, styleID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				rm = new WaterReportModel();
				rm.setThisyGross(rs.getFloat("ThisYGross"));
				rm.setLastyGross(rs.getFloat("LastYGross"));
				map.put(rs.getString("DictionaryValue_value"), rm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/***
	 * 查询获得指定部门的年度用水详细情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param queryYear
	 *            查询的年份
	 * @return WaterReportModel 查询结果实体集
	 * @throws SQLException
	 */
	public WaterReportModel getGroupCountDetailByYear(int groupID, int queryYear) throws SQLException
	{
		WaterReportModel gym = new WaterReportModel();

		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		String groupName = null;
		float totalEneryCount = 0;
		float totalMoneyCount = 0;
		groupName = getGroupName(groupID);

		if (groupName != null)
			gym.setGroupName(groupName);
		else
		{
			return null;
		}

		sql = "select fenlei,DictionaryValue_value as type,gross,money from (select "
				+ "DictionaryValue.DictionaryValue_Num,DictionaryValue.DictionaryValue_value "
				+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) b left join "
				+ "(select fenlei,sum(zgross) gross,sum(zmoney) money from T_PartmentFenLeiDayWater "
				+ "where selectyear=? and partment_id = ? group by fenlei order by fenlei) a on " + "a.fenlei=b.DictionaryValue_Num";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, groupID);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("type") + " " + rs.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			gym.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		sql = "select fenlei,DictionaryValue_value as type,gross,money from (select "
				+ "DictionaryValue.DictionaryValue_Num,DictionaryValue.DictionaryValue_value "
				+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) b left join "
				+ "(select useamstyle fenlei,sum(zgross) gross,sum(zmoney) money from T_PartmentStyleDayWater "
				+ "where selectyear=? and partment_id = ? group by useamstyle order by useamstyle) a on " + "a.fenlei=b.DictionaryValue_Num";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);

			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, queryYear);
			pstmt1.setInt(3, groupID);
			rs1 = pstmt1.executeQuery();
			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("type") + " " + rs1.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			gym.setXingZhiXiaoJi(xingZhiXiaoJi);

			gym.setTotalWaterCount(totalEneryCount);
			gym.setTotalMoneyCount(totalMoneyCount);

			gym.setSelectYear(queryYear);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}
		return gym;
	}

	/**
	 * 查询得到在线，离线和用水的水表的数量
	 * 
	 * @return Map<String, Integer> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Integer> getWatermeterCount() throws SQLException
	{
		Map<String, Integer> count = new HashMap<String, Integer>();
		String sql = "select count(*) as onlineCount from watermeter where WaterMeter_Stat=1";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				count.put("在线水表", rs1.getInt("onlineCount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1, rs1);
		}
		sql = "select count(*) as offlineCount from watermeter where WaterMeter_Stat=0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count.put("离线水表", rs.getInt("offlineCount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		

		/*
		 * sql =
		 * "select count(*) as workingCount from watermeter where WaterMeter_Stat=2"
		 * ;
		 * 
		 * rs = ConnDB.executeQuery(sql);
		 * 
		 * if (rs.next()) { count.put("工作中水表", rs.getInt("workingCount")); }
		 * 
		 * close(pstmt, rs);
		 */

		return count;
	}

	/**
	 * 获取全校的某年用水信息
	 * 
	 * @param year
	 * @return
	 * @throws SQLException
	 */

	public WaterReportModel getAllSchoolWaterByYear(int year) throws SQLException
	{
		AreaDao ad = new AreaDao();
		Area am = new Area();
		List<Area> aList = new ArrayList<Area>();
		aList = ad.display();
		WaterReportModel rm = new WaterReportModel();
		WaterReportModel rmTotal = new WaterReportModel();
		for (int i = 0; i < aList.size(); i++)
		{
			rm = this.getAreaCountDetailByYear(aList.get(i).getId(), year);
			if(rm!=null){
				rmTotal.setTotalWaterCount(rmTotal.getTotalWaterCount()+rm.getTotalWaterCount());
			}
		}
		return rmTotal;
	}

	/**
	 * 获取全校的某年某月用水信息
	 * 
	 * @param year
	 * @return
	 * @throws SQLException
	 */

	public WaterReportModel getAllSchoolWaterByMonth(int year, int month) throws SQLException
	{
		AreaDao ad = new AreaDao();
		Area am = new Area();
		List<Area> aList = new ArrayList<Area>();
		aList = ad.display();
		WaterReportModel rm = new WaterReportModel();
		WaterReportModel rmTotal = new WaterReportModel();
		for (int i = 0; i < aList.size(); i++)
		{
			rm = this.getAreaCountByMonth(aList.get(i).getId(), year, month);
			if(rm!=null){
				rmTotal.setTotalWaterCount(rmTotal.getTotalWaterCount()+rm.getAreaTotalWaterCount());
			}
		}
		return rmTotal;
	}

	/***
	 * 查询指定部门的月度用水情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param groupID
	 *            所要查询的部门ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return WaterReportModel 查询结果实体集
	 * @throws SQLException
	 */
	public WaterReportModel getGroupCountDetailByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel gmm = new WaterReportModel();
		Map<String, Float> fenlei = null;
		Map<String, Float> style = null;

		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		String groupName = null;
		float totalEneryCount = 0;
		float totalMoneyCount = 0;
		groupName = getGroupName(groupID);

		if (groupName != null)
			gmm.setGroupName(groupName);
		else
		{
			return null;
		}

		gmm.setSelectYear(queryYear);
		gmm.setSelectMonth(queryMonth);

		sql = "select fenlei,DictionaryValue_value as type,gross,money from (select "
				+ "DictionaryValue.DictionaryValue_Num,DictionaryValue.DictionaryValue_value "
				+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) b left join "
				+ "(select fenlei,sum(zgross) gross,sum(zmoney) money from T_PartmentFenLeiDayWater "
				+ "where selectyear=? and selectmonth=? and partment_id = ? group by fenlei order by fenlei) a on "
				+ "a.fenlei=b.DictionaryValue_Num";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, groupID);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (fenlei == null)
					fenlei = new HashMap<String, Float>();

				fenlei.put(rs.getString("type"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("type") + " " + rs.getFloat("gross") + "\n";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			gmm.setFenlei(fenlei);
			gmm.setFenXiangXiaoJi(fenXiangXiaoJi);
			sql = "select fenlei,DictionaryValue_value as type,gross,money from (select "
					+ "DictionaryValue.DictionaryValue_Num,DictionaryValue.DictionaryValue_value "
					+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) b left join "
					+ "(select useamstyle fenlei,sum(zgross) gross,sum(zmoney) money from T_PartmentStyleDayWater "
					+ "where selectyear=? and selectmonth=? and partment_id = ? group by useamstyle order by useamstyle) a on "
					+ "a.fenlei=b.DictionaryValue_Num";
//			Connection conn1 = null;
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try
			{
//				conn1 = ConnDB.getConnection();
				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setInt(1, 26);
				pstmt1.setInt(2, queryYear);
				pstmt1.setInt(3, queryMonth);
				pstmt1.setInt(4, groupID);

				rs1 = pstmt1.executeQuery();
				while (rs1.next())
				{
					if (style == null)
						style = new HashMap<String, Float>();
					style.put(rs1.getString("type"), rs1.getFloat("gross"));
					xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("type") + " " + rs1.getFloat("gross") + "\n";
					totalEneryCount = totalEneryCount + rs1.getFloat("gross");
					totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
				}

				gmm.setStyle(style);
				gmm.setXingZhiXiaoJi(xingZhiXiaoJi);

				gmm.setTotalWaterCount(totalEneryCount);
				gmm.setTotalMoneyCount(totalMoneyCount);

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release( pstmt1, rs1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		
		return gmm;
	}

	/***
	 * 查询指定时间段内的指定部门的用水情况，包括用水量和水费
	 * 
	 * @param groupID
	 *            索要查询的部门ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return List<WaterReportModel> 查询结果集，链表的每一项都是单独的一天的记录值（用水量和水费）
	 * @throws SQLException
	 */
	public List<WaterReportModel> getGroupCountBetween(int groupID, Date start, Date end) throws SQLException
	{
		List<WaterReportModel> list = null;
		String groupName = getGroupName(groupID);
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (groupName == null)
			return null;

		if (start.after(end))
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_partmentdaywater where partment_id=? and selectDay>? and "
						+ "selectDay<? and selectMonth=? and selectYear=? order by " + "selectYear,selectMonth,selectDay";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupID);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_partmentdaywater where partment_id=? and ((selectMonth=? "
						+ "and selectDay>?) or (selectMonth>? and selectMonth<?) or " + "(selectMonth=? and selectDay<?)) and selectYear=? order by "
						+ "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupID);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear() + 1900);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from t_partmentdaywater " + "where partment_id=? and "
						+ "((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or " + "(selectYear>? and selectYear<?) or "
						+ "(selectYear=? and (selectMonth<? or (selectMonth=? and selectDay<?)))) " + "order by selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupID);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<WaterReportModel>();
				WaterReportModel rm = new WaterReportModel();
				rm.setSelectYear(rs.getInt("selectYear"));
				rm.setSelectMonth(rs.getInt("selectMonth"));
				rm.setSelectDay(rs.getInt("selectDay"));
				rm.setGroupName(groupName);
				rm.setTotalWaterCount(rs.getFloat("zgross"));
				rm.setTotalMoneyCount(rs.getFloat("zmoney"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 查询指定部门指定年份的用水情况，包括用水量和水费
	 * 
	 * @param groupID
	 *            所要查询的部门ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　WaterReportModel　查询结果实体类
	 * @throws SQLException
	 */
	public WaterReportModel getGroupCountByYear(int groupID, int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		sql = "select sum(zgross) gross,sum(zmoney) money from t_partmentdaywater " + "where selectYear=? and PARTMENT_ID=? group by selectYear";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, groupID);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm.setGroupName(groupName);
				rm.setSelectYear(queryYear);
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 查询得到指定部门指定年月的用水情况，包括用水量和水费
	 * 
	 * @param groupID
	 *            所要查询部门ＩＤ
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            　索要查询的月份
	 * @return　WaterReportModel　查询得到的结果
	 * @throws SQLException
	 */
	public WaterReportModel getGroupCountByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		sql = "select sum(zgross) gross,sum(zmoney) money from t_partmentdaywater "
				+ "where selectYear=? and selectMonth=? and PARTMENT_ID=? group by selectYear,selectMonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);
			pstmt.setInt(3, groupID);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm.setGroupName(groupName);
				rm.setSelectYear(queryYear);
				rm.setSelectMonth(queryMonth);
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/***
	 * 查询指定部门指定年份的每个月的用水情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param groupID
	 *            所要查询的部门ID
	 * @param queryYear
	 *            所要查询的年份
	 * @throws SQLException
	 * @return List<WaterReportModel> 查询得到12个月份的结果集
	 */
	public List<WaterReportModel> getGroupCountEveryMonth(int groupID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = null;

		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		sql = "select selectmonth,sum(zgross) gross,sum(zmoney) money from " + "t_partmentdaywater where selectyear=? and partment_id=? group by "
				+ "selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, groupID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<WaterReportModel>();
				}

				WaterReportModel rm = new WaterReportModel();

				rm.setGroupName(groupName);
				rm.setSelectYear(queryYear);
				rm.setSelectMonth(rs.getInt("selectmonth"));
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	public EveryMonthCountModel getEveryMonthBySingleGroup(int groupID, int queryYear) throws SQLException
	{
		EveryMonthCountModel rmcm = null;

		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		sql = "select selectmonth,sum(zgross) gross,sum(zmoney) money from " + "T_PARTMENTDAYWATER where selectyear=? and partment_id=? group by "
				+ "selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, groupID);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (rmcm == null)
				{
					rmcm = new EveryMonthCountModel();
					rmcm.setName(groupName);
					rmcm.setTotalName(groupName);
					rmcm.setSelectYear(queryYear);
				}

				if (rs.getInt("selectmonth") == 1)
				{
					rmcm.setMonth1(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth1());
				}

				if (rs.getInt("selectmonth") == 2)
				{
					rmcm.setMonth2(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth2());
				}

				if (rs.getInt("selectmonth") == 3)
				{
					rmcm.setMonth3(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth3());
				}

				if (rs.getInt("selectmonth") == 4)
				{
					rmcm.setMonth4(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth4());
				}

				if (rs.getInt("selectmonth") == 5)
				{
					rmcm.setMonth5(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth5());
				}

				if (rs.getInt("selectmonth") == 6)
				{
					rmcm.setMonth6(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth6());
				}

				if (rs.getInt("selectmonth") == 7)
				{
					rmcm.setMonth7(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth7());
				}

				if (rs.getInt("selectmonth") == 8)
				{
					rmcm.setMonth8(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth8());
				}

				if (rs.getInt("selectmonth") == 9)
				{
					rmcm.setMonth9(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth9());
				}

				if (rs.getInt("selectmonth") == 10)
				{
					rmcm.setMonth10(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth10());
				}

				if (rs.getInt("selectmonth") == 11)
				{
					rmcm.setMonth11(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth11());
				}

				if (rs.getInt("selectmonth") == 12)
				{
					rmcm.setMonth12(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth12());
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rmcm;
	}

	public List<EveryMonthCountModel> getEveryMonthByGroup(int groupID, int queryYear) throws SQLException
	{
		List<EveryMonthCountModel> result = null;
		List<Department> groupList = groupDao.getDepartmentByParentID(groupID);

		String groupName = getGroupName(groupID);

		if (groupName == null)
		{
			return null;
		}

		if (groupList != null && groupList.size() > 0)
		{
			result = new LinkedList<EveryMonthCountModel>();
			EveryMonthCountModel last = new EveryMonthCountModel();
			last.setTotalName(groupName);
			last.setName(groupName + " 合计");
			for (Department group : groupList)
			{
				EveryMonthCountModel rm = getEveryMonthBySingleGroup(group.getDepartmentID(), queryYear);
				if (rm != null)
				{
					rm.setTotalName(groupName);
					last.setMonth1(last.getMonth1() + rm.getMonth1());
					last.setMonth2(last.getMonth2() + rm.getMonth2());
					last.setMonth3(last.getMonth3() + rm.getMonth3());
					last.setMonth4(last.getMonth4() + rm.getMonth4());
					last.setMonth5(last.getMonth5() + rm.getMonth5());
					last.setMonth6(last.getMonth6() + rm.getMonth6());
					last.setMonth7(last.getMonth7() + rm.getMonth7());
					last.setMonth8(last.getMonth8() + rm.getMonth8());
					last.setMonth9(last.getMonth9() + rm.getMonth9());
					last.setMonth10(last.getMonth10() + rm.getMonth10());
					last.setMonth11(last.getMonth11() + rm.getMonth11());
					last.setMonth12(last.getMonth12() + rm.getMonth12());

					last.setTotalCount(last.getTotalCount() + rm.getTotalCount());
					result.add(rm);
				}

			}
			result.add(last);
		} else
		{
			EveryMonthCountModel self = getEveryMonthBySingleGroup(groupID, queryYear);
			if (self != null)
			{
				result = new LinkedList<EveryMonthCountModel>();
				result.add(self);
				EveryMonthCountModel rm = getEveryMonthBySingleGroup(groupID, queryYear);
				rm.setName(rm.getTotalName() + " 合计");
				result.add(rm);
			}
		}

		return result;
	}

	public EveryMonthCountModel getEveryMonthByArch(int arcID, int queryYear) throws SQLException
	{
		EveryMonthCountModel rmcm = null;
		String arcName = getArcName(arcID);
		String sql = null;
		if (arcName == null)
			return null;

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money " + "from T_ArcDayWater where selectyear=? and ARCHITECTURE_ID=?  group "
				+ "by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, arcID);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (rmcm == null)
				{
					rmcm = new EveryMonthCountModel();
					rmcm.setName(arcName);
					rmcm.setTotalName(arcName);
					rmcm.setSelectYear(queryYear);
				}

				if (rs.getInt("selectmonth") == 1)
				{
					rmcm.setMonth1(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth1());
				}

				if (rs.getInt("selectmonth") == 2)
				{
					rmcm.setMonth2(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth2());
				}

				if (rs.getInt("selectmonth") == 3)
				{
					rmcm.setMonth3(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth3());
				}

				if (rs.getInt("selectmonth") == 4)
				{
					rmcm.setMonth4(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth4());
				}

				if (rs.getInt("selectmonth") == 5)
				{
					rmcm.setMonth5(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth5());
				}

				if (rs.getInt("selectmonth") == 6)
				{
					rmcm.setMonth6(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth6());
				}

				if (rs.getInt("selectmonth") == 7)
				{
					rmcm.setMonth7(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth7());
				}

				if (rs.getInt("selectmonth") == 8)
				{
					rmcm.setMonth8(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth8());
				}

				if (rs.getInt("selectmonth") == 9)
				{
					rmcm.setMonth9(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth9());
				}

				if (rs.getInt("selectmonth") == 10)
				{
					rmcm.setMonth10(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth10());
				}

				if (rs.getInt("selectmonth") == 11)
				{
					rmcm.setMonth11(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth11());
				}

				if (rs.getInt("selectmonth") == 12)
				{
					rmcm.setMonth12(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth12());
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rmcm;
	}

	public List<EveryMonthCountModel> getEveryMonthByArea(int areaID, int queryYear) throws SQLException
	{
		List<EveryMonthCountModel> result = null;
		ArchitectureDao archDao = new ArchitectureDao();

		String areaName = getAreaName(areaID);
		if (areaName == null)
			return null;

		List<Architecture> archList = archDao.queryArchByAreaID(areaID);

		EveryMonthCountModel last = new EveryMonthCountModel();
		last.setName(areaName + " 合计");
		for (Architecture arch : archList)
		{
			if (result == null)
			{
				result = new LinkedList<EveryMonthCountModel>();
			}
			EveryMonthCountModel rm = getEveryMonthByArch(arch.getId(), queryYear);
			if (rm != null)
			{
				rm.setTotalName(areaName);
				result.add(rm);
				last.setMonth1(last.getMonth1() + rm.getMonth1());
				last.setMonth2(last.getMonth2() + rm.getMonth2());
				last.setMonth3(last.getMonth3() + rm.getMonth3());
				last.setMonth4(last.getMonth4() + rm.getMonth4());
				last.setMonth5(last.getMonth5() + rm.getMonth5());
				last.setMonth6(last.getMonth6() + rm.getMonth6());
				last.setMonth7(last.getMonth7() + rm.getMonth7());
				last.setMonth8(last.getMonth8() + rm.getMonth8());
				last.setMonth9(last.getMonth9() + rm.getMonth9());
				last.setMonth10(last.getMonth10() + rm.getMonth10());
				last.setMonth11(last.getMonth11() + rm.getMonth11());
				last.setMonth12(last.getMonth12() + rm.getMonth12());

				last.setTotalCount(last.getTotalCount() + rm.getTotalCount());

			}
		}
		if (result != null)
			result.add(last);

		archList = null;
		return result;
	}

	public EveryMonthCountModel getEveryMonthBySingleArea(int areaID, int queryYear) throws SQLException
	{
		EveryMonthCountModel rmcm = null;
		String areaName = getAreaName(areaID);
		String sql = null;
		if (areaName == null)
			return null;

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money " + "from T_ArcDayWater where selectyear=? and AREA_ID=?  group "
				+ "by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rmcm == null)
				{
					rmcm = new EveryMonthCountModel();
					rmcm.setName(areaName);
					rmcm.setTotalName(areaName);
					rmcm.setSelectYear(queryYear);
				}

				if (rs.getInt("selectmonth") == 1)
				{
					rmcm.setMonth1(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth1());
				}

				if (rs.getInt("selectmonth") == 2)
				{
					rmcm.setMonth2(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth2());
				}

				if (rs.getInt("selectmonth") == 3)
				{
					rmcm.setMonth3(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth3());
				}

				if (rs.getInt("selectmonth") == 4)
				{
					rmcm.setMonth4(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth4());
				}

				if (rs.getInt("selectmonth") == 5)
				{
					rmcm.setMonth5(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth5());
				}

				if (rs.getInt("selectmonth") == 6)
				{
					rmcm.setMonth6(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth6());
				}

				if (rs.getInt("selectmonth") == 7)
				{
					rmcm.setMonth7(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth7());
				}

				if (rs.getInt("selectmonth") == 8)
				{
					rmcm.setMonth8(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth8());
				}

				if (rs.getInt("selectmonth") == 9)
				{
					rmcm.setMonth9(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth9());
				}

				if (rs.getInt("selectmonth") == 10)
				{
					rmcm.setMonth10(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth10());
				}

				if (rs.getInt("selectmonth") == 11)
				{
					rmcm.setMonth11(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth11());
				}

				if (rs.getInt("selectmonth") == 12)
				{
					rmcm.setMonth12(rs.getFloat("gross"));
					rmcm.setTotalCount(rmcm.getTotalCount() + rmcm.getMonth12());
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rmcm;
	}

	public List<EveryMonthCountModel> getEveryMonthByAll(int queryYear) throws SQLException
	{
		List<EveryMonthCountModel> result = null;
		EveryMonthCountModel rmcm = null;
		AreaDao areaDao = new AreaDao();

		String systName = getSystmName();
		if (systName == null)
			return null;

		List<Area> areaList = areaDao.display();
		EveryMonthCountModel last = new EveryMonthCountModel();
		last.setName(systName + " 合计");
		for (Area area : areaList)
		{
			if (result == null)
			{
				result = new LinkedList<EveryMonthCountModel>();
			}
			EveryMonthCountModel rm = getEveryMonthBySingleArea(area.getId(), queryYear);
			if (rm != null)
			{
				rm.setTotalName(systName);
				result.add(rm);

				last.setMonth1(last.getMonth1() + rm.getMonth1());
				last.setMonth2(last.getMonth2() + rm.getMonth2());
				last.setMonth3(last.getMonth3() + rm.getMonth3());
				last.setMonth4(last.getMonth4() + rm.getMonth4());
				last.setMonth5(last.getMonth5() + rm.getMonth5());
				last.setMonth6(last.getMonth6() + rm.getMonth6());
				last.setMonth7(last.getMonth7() + rm.getMonth7());
				last.setMonth8(last.getMonth8() + rm.getMonth8());
				last.setMonth9(last.getMonth9() + rm.getMonth9());
				last.setMonth10(last.getMonth10() + rm.getMonth10());
				last.setMonth11(last.getMonth11() + rm.getMonth11());
				last.setMonth12(last.getMonth12() + rm.getMonth12());

				last.setTotalCount(last.getTotalCount() + rm.getTotalCount());
			}

		}

		if (result != null)
			result.add(last);

		areaList = null;
		return result;
	}

	/***
	 * 查询指定部门指定某年月的每一天的用水量
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return List<WaterReportModel> 查询得到的每一天的数据结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getGroupCountEveryDay(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = null;

		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		sql = "select selectday,zgross,zmoney from T_PartmentDayWater where selectyear=? " + "and selectmonth=? and partment_id=? order by selectday";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, groupID);
			rst = ps.executeQuery();
			while (rst.next())
			{
				if (list == null)
				{
					list = new LinkedList<WaterReportModel>();
				}

				WaterReportModel rm = new WaterReportModel();
				rm.setGroupName(groupName);
				rm.setSelectYear(queryYear);
				rm.setSelectMonth(queryMonth);
				rm.setSelectDay(rst.getInt("selectday"));
				rm.setTotalWaterCount(rst.getFloat("zgross"));
				rm.setTotalMoneyCount(rst.getFloat("zmoney"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rst);
		}
		return list;
	}

	/***
	 * 查询指定部门指定时间区间内的每一天的用水量
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param start
	 *            起始日期
	 * @param end
	 *            截止日期
	 * @return List<ReportModel> 查询得到的每一天的数据结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getGroupCountEveryDayBetween(int groupID, Date start, Date end) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "T_PartmentDayWater where partment_id=? and selectDay>? and "
						+ "selectDay<? and selectMonth=? and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, groupID);
				ps.setInt(2, start.getDate());
				ps.setInt(3, end.getDate());
				ps.setInt(4, end.getMonth() + 1);
				ps.setInt(5, end.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "T_PartmentDayWater where partment_id=? and ((selectMonth=? "
						+ "and selectDay>?) or (selectMonth>? and selectMonth<?) or " + "(selectMonth=? and selectDay<?)) and selectYear=? order by "
						+ "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, groupID);
				ps.setInt(2, start.getMonth() + 1);
				ps.setInt(3, start.getDate());
				ps.setInt(4, start.getMonth() + 1);
				ps.setInt(5, end.getMonth() + 1);
				ps.setInt(6, end.getMonth() + 1);
				ps.setInt(7, end.getDate());
				ps.setInt(8, end.getYear() + 1900);

			}

			if (start.getYear() < end.getYear())
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from T_PartmentDayWater " + "where partment_id=? and "
						+ "((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or " + "(selectYear>? and selectYear<?) or "
						+ "(selectYear=? and (selectMonth<? or (selectMonth=? and selectDay<?)))) " + "order by selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, groupID);
				ps.setInt(2, start.getYear() + 1900);
				ps.setInt(3, start.getMonth() + 1);
				ps.setInt(4, start.getMonth() + 1);
				ps.setInt(5, start.getDate());
				ps.setInt(6, start.getYear() + 1900);
				ps.setInt(7, end.getYear() + 1900);
				ps.setInt(8, end.getYear() + 1900);
				ps.setInt(9, end.getMonth() + 1);
				ps.setInt(10, end.getMonth() + 1);
				ps.setInt(11, end.getDate());
			}

			rst = ps.executeQuery();

			while (rst.next())
			{
				WaterReportModel rm = new WaterReportModel();
				rm.setGroupName(groupName);
				rm.setSelectYear(rst.getInt("selectYear"));
				rm.setSelectMonth(rst.getInt("selectMonth"));
				rm.setSelectDay(rst.getInt("selectday"));
				rm.setTotalWaterCount(rst.getFloat("zgross"));
				rm.setTotalMoneyCount(rst.getFloat("zmoney"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rst);
		}
		return list;
	}

	/***
	 * 查询指定建筑指定时间区间内的每一天的用水量
	 * 
	 * @param archID
	 *            查询的建筑ID
	 * @param start
	 *            起始日期
	 * @param end
	 *            截止日期
	 * @return List<ReportModel> 查询得到的每一天的数据结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getArchCountEveryDayBetween(int archID, Date start, Date end) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		String sql = null;
		String archName = null;
		archName = getArcName(archID);

		if (archName == null)
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "T_ArcDayWater where Architecture_id=? and selectDay>? and "
						+ "selectDay<? and selectMonth=? and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, archID);
				ps.setInt(2, start.getDate());
				ps.setInt(3, end.getDate());
				ps.setInt(4, end.getMonth() + 1);
				ps.setInt(5, end.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "T_ArcDayWater where Architecture_id=? and ((selectMonth=? "
						+ "and selectDay>?) or (selectMonth>? and selectMonth<?) or " + "(selectMonth=? and selectDay<?)) and selectYear=? order by "
						+ "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, archID);
				ps.setInt(2, start.getMonth() + 1);
				ps.setInt(3, start.getDate());
				ps.setInt(4, start.getMonth() + 1);
				ps.setInt(5, end.getMonth() + 1);
				ps.setInt(6, end.getMonth() + 1);
				ps.setInt(7, end.getDate());
				ps.setInt(8, end.getYear() + 1900);

			}

			if (start.getYear() < end.getYear())
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from T_ArcDayWater " + "where Architecture_id=? and "
						+ "((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or " + "(selectYear>? and selectYear<?) or "
						+ "(selectYear=? and (selectMonth<? or (selectMonth=? and selectDay<?)))) " + "order by selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, archID);
				ps.setInt(2, start.getYear() + 1900);
				ps.setInt(3, start.getMonth() + 1);
				ps.setInt(4, start.getMonth() + 1);
				ps.setInt(5, start.getDate());
				ps.setInt(6, start.getYear() + 1900);
				ps.setInt(7, end.getYear() + 1900);
				ps.setInt(8, end.getYear() + 1900);
				ps.setInt(9, end.getMonth() + 1);
				ps.setInt(10, end.getMonth() + 1);
				ps.setInt(11, end.getDate());
			}

			rst = ps.executeQuery();

			while (rst.next())
			{
				WaterReportModel rm = new WaterReportModel();
				rm.setGroupName(archName);
				rm.setSelectYear(rst.getInt("selectYear"));
				rm.setSelectMonth(rst.getInt("selectMonth"));
				rm.setSelectDay(rst.getInt("selectday"));
				rm.setTotalWaterCount(rst.getFloat("zgross"));
				rm.setTotalMoneyCount(rst.getFloat("zmoney"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rst);
		}

		return list;
	}

	/***
	 * 查询所有建筑年度用水量排名，并按降序排列
	 * 
	 * @param quryYear
	 *            查询的年份
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getAllArchCountListDisc(int quryYear) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();
		String sql = "select b.ARCHITECTURE_NAME archName,gross,money from(select ARCHITECTURE_ID,sum(zgross) "
				+ "gross,sum(zmoney) money from t_arcdaywater where selectYear=? "
				+ "group by ARCHITECTURE_ID) a left join (select ARCHITECTURE_ID,ARCHITECTURE_NAME "
				+ "from ARCHITECTURE) b on a.ARCHITECTURE_ID=b.ARCHITECTURE_ID order by gross desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, quryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();
				rm.setSelectYear(quryYear);
				rm.setArcName(rs.getString("archName"));
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/***
	 * 查询所有部门年度用水量排名，并按降序排列
	 * 
	 * @param quryYear
	 *            查询的年份
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getAllGroupCountListDisc(int quryYear) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();
		String sql = "select b.partment_name groupName,gross,money from(select partment_id,sum(zgross) "
				+ "gross,sum(zmoney) money from t_partmentdaywater where selectYear=? "
				+ "group by partment_id) a left join (select partment_id,partment_name "
				+ "from partment) b on a.partment_id=b.partment_id order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, quryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();
				rm.setSelectYear(quryYear);
				rm.setGroupName(rs.getString("groupName"));
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询得到全校指定年份的分类分项数据
	 * 
	 * 
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　Map<String, Float>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public Map<String, Float> getAllSchoolFenLeiCountByYear(int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=?  "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到全校指定年份的分类分项数据<以List形式>
	 * 
	 * 
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　List<WaterReportModel>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAllSchoolFenLeiCountByYearInList(int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;
		String sql = null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=?  "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setFenXiangXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// if (map == null)
				// map = new HashMap<String, Float>();
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 通过年份查询该年所有区域的性质用水情况
	 * 
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAllAreaStyleCountByYear(int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=?  "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 6);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 通过年份查询该年所有区域的性质用水情况<以List形式>
	 * 
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAllAreaStyleCountByYearInList(int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=?  "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 6);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setXingZhiXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 通过区域ID，查询该区域下在离线电表个数
	 * 
	 * @param areaID
	 *            区域ID
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Integer> getWatermeterCountByArea(int areaID) throws SQLException
	{
		Map<String, Integer> count = new HashMap<String, Integer>();
		String sql = "select count(*) as onlineCount from watermeter where WaterMeter_Stat=1 and Area_ID = " + areaID;

		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);

			rs1 = pstmt1.executeQuery();
			if (rs1.next())
			{
				count.put("在线水表", rs1.getInt("onlineCount"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}
		
		sql = "select count(*) as offlineCount from watermeter where WaterMeter_Stat=0 and Area_ID = " + areaID;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				count.put("离线水表", rs.getInt("offlineCount"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		

		/*
		 * sql =
		 * "select count(*) as workingCount from watermeter where WaterMeter_Stat=2 and Area_ID = "
		 * + areaID;
		 * 
		 * rs = ConnDB.executeQuery(sql);
		 * 
		 * if (rs.next()) { count.put("工作中水表", rs.getInt("workingCount")); }
		 * 
		 * close(pstmt, rs);
		 */

		return count;
	}

	/**
	 * 查询得到指定区域指定年份的分类分项数据
	 * 
	 * @param areaID
	 *            所要查询的区域ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　Map<String, Float>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaFenLeiCountByYear(int areaID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		String areaName = getAreaName(areaID);
		if (areaName == null)
			return null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=? and Area_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到指定区域指定年份的分类分项数据
	 * 
	 * @param areaID
	 *            所要查询的区域ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　Map<String, Float>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAreaFenLeiCountByYearInList(int areaID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;
		String sql = null;

		String areaName = getAreaName(areaID);
		if (areaName == null)
			return null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=? and Area_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setFenXiangXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// if (map == null)
				// map = new HashMap<String, Float>();
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定部门指定年月的的用水分项分布
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getGroupFenLeiCountByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_partmentfenleidaywater " + "where selectYear=? and selectMonth=? and partment_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, groupID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 通过区域ID和指定年份 查询该区域年性质用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param queryYear
	 *            查询年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaStyleCountByYear(int areaID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String areaName = null;
		areaName = getAreaName(areaID);

		if (areaName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=? and AREA_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 通过区域ID和指定年份 查询该区域年性质用水情况<以List形式>
	 * 
	 * @param areaID
	 *            区域ID
	 * @param queryYear
	 *            查询年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAreaStyleCountByYearInList(int areaID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;
		String areaName = null;
		areaName = getAreaName(areaID);

		if (areaName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=? and AREA_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setXingZhiXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 通过区域ID和建筑ID,查询该建筑下的水表在离线个数
	 * 
	 * @param areaID
	 *            区域ID
	 * @param arcID
	 *            建筑ID
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Integer> getWatermeterCountByArc(int areaID, int arcID) throws SQLException
	{
		Map<String, Integer> count = new HashMap<String, Integer>();
		String sql = "select count(*) as onlineCount from watermeter where WaterMeter_Stat=1 and Area_ID = " + areaID + " and Architecture_ID = " + arcID;
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);

			rs1 = pstmt1.executeQuery();
			if (rs1.next())
			{
				count.put("在线水表", rs1.getInt("onlineCount"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}
		sql = "select count(*) as offlineCount from watermeter where WaterMeter_Stat=0 and Area_ID = " + areaID + " and Architecture_ID = "
				+ arcID;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				count.put("离线水表", rs.getInt("offlineCount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		

		/*
		 * sql =
		 * "select count(*) as workingCount from watermeter where WaterMeter_Stat=2 and Area_ID = "
		 * + areaID + " and Architecture_ID = " + arcID;
		 * 
		 * rs = ConnDB.executeQuery(sql);
		 * 
		 * if (rs.next()) { count.put("工作中水表", rs.getInt("workingCount")); }
		 * 
		 * close(pstmt, rs);
		 */

		return count;
	}

	/**
	 * 查询得到指定部门指定年份的用水分类分项数据
	 * 
	 * @param groupID
	 *            所要查询的部门ID
	 * @param queryYear
	 *            所要查询的年份
	 * @return Map<String, Float> 查询得到的分类分项数据
	 * @throws SQLException
	 */
	public Map<String, Float> getGroupFenLeiCountByYear(int groupID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_partmentfenleidayWater " + "where selectYear=? and partment_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, groupID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/***
	 * 查询指定部门指定时间段内的用水分项分布
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getGroupFenLeiCountBetween(int groupID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String groupName = getGroupName(groupID);
		try
		{
			if (groupName == null)
				return null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_partmentfenleidayWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and partment_ID=? group by fenlei) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, groupID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select fenlei,sum(zgross) gross " + "from T_partmentfenleidayWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and partment_ID=? " + "group by fenlei) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
				pstmt.setInt(9, groupID);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
						+ "from T_partmentfenleidayWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?)))) and partment_ID=? group by fenlei) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
				pstmt.setInt(12, groupID);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/***
	 * 查询所有部门指定时间段内的用水分项分布
	 * 
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getAllGroupFenLeiCountBetween(Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_partmentfenleidayWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=?  group by fenlei) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select fenlei,sum(zgross) gross " + "from T_partmentfenleidayWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=?  " + "group by fenlei) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
						+ "from T_partmentfenleidayWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?))))  group by fenlei) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/***
	 * 查询指定部门指定时间段内的用水性质分布
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getGroupStyleCountBetween(int groupID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String groupName = null;
		groupName = getGroupName(groupID);
		try
		{
			if (groupName == null)
				return null;

			String sql = null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_partmentstyledayWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and partment_ID=? group by USEAMSTYLE) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, groupID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_partmentstyledayWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and partment_ID=? " + "group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
				pstmt.setInt(9, groupID);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select USEAMSTYLE fenlei,sum(zgross) gross "
						+ "from T_partmentstyledayWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?)))) and partment_ID=? group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
				pstmt.setInt(12, groupID);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/***
	 * 查询所有部门指定时间段内的用水性质分布
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getAllGroupStyleCountBetween(Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;
		try
		{
			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_partmentstyledayWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=?  group by USEAMSTYLE) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_partmentstyledayWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=?  " + "group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select USEAMSTYLE fenlei,sum(zgross) gross "
						+ "from T_partmentstyledayWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?))))  group by USEAMSTYLE) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询指定部门指定年月的性质分项数据
	 * 
	 * @param groupID
	 *            所要查询的部门ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @param queryMonth
	 *            　所要查询的月份
	 * @return　Map<String, Float>　查询得到的用水性质分项数据
	 * @throws SQLException
	 */
	public Map<String, Float> getGroupStyleCountByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String groupName = null;
		groupName = getGroupName(groupID);// 改了

		if (groupName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_partmentstyledayWater "
				+ "where selectYear=? and selectMonth=? and partment_ID=? " + "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, groupID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询所有部门指定年月的性质分项数据
	 * 
	 * @param queryYear
	 *            　所要查询的年份
	 * @param queryMonth
	 *            　所要查询的月份
	 * @return　Map<String, Float>　查询得到的用水性质分项数据
	 * @throws SQLException
	 */
	public Map<String, Float> getAllGroupStyleCountByMonth(int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_partmentstyledayWater " + "where selectYear=? and selectMonth=?  "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到指定部门指定年份的用水性质分项
	 * 
	 * @param groupID
	 *            所要查询的部门ID
	 * @param queryYear
	 *            所要查询的年份
	 * @return Map<String, Float> 查询得到的性质分项数据
	 * @throws SQLException
	 */
	public Map<String, Float> getGroupStyleCountByYear(int groupID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_partmentstyledayWater " + "where selectYear=? and partment_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, groupID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/***
	 * 查询指定建筑的年度用水详细情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * @param queryYear
	 *            查询的月份
	 * @return WaterReportModel 查询结果
	 * @throws SQLException
	 * 
	 */
	public WaterReportModel getArcCountDetailByYear(int arcID, int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String arcName = getArcName(arcID);
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (arcName == null)
			return null;

		rm.setSelectYear(queryYear);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where ARCHITECTURE_ID=? and SELECTYEAR=? group by fenlei)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, arcID);
			pstmt.setInt(3, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where ARCHITECTURE_ID=? and SELECTYEAR=? group by USEAMSTYLE)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, arcID);
			pstmt1.setInt(3, queryYear);

			rs1 = pstmt1.executeQuery();
			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setArcName(arcName);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}

		return rm;
	}

	public WaterReportModel getAreaCountDetailByYear(int areaID, int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String areaName = getAreaName(areaID);
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (areaName == null)
			return null;

		rm.setSelectYear(queryYear);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where Area_ID=? and SELECTYEAR=? group by fenlei)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, areaID);
			pstmt.setInt(3, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where Area_ID=? and SELECTYEAR=? group by USEAMSTYLE)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, areaID);
			pstmt1.setInt(3, queryYear);

			rs1 = pstmt1.executeQuery();

			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setAreaName(areaName);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}

		return rm;
	}

	public WaterReportModel getAllCountDetailByYear(int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String name = getSystmName();
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (name == null)
			return null;

		rm.setSelectYear(queryYear);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where SELECTYEAR=? group by fenlei)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where SELECTYEAR=? group by USEAMSTYLE)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, queryYear);

			rs1 = pstmt1.executeQuery();

			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getFloat("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setName(name);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}
		return rm;
	}

	/***
	 * 查询指定建筑的月度用水详细情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return WaterReportModel 查询结果实体集
	 * @throws SQLException
	 */
	public WaterReportModel getArcCountDetailByMonth(int arcID, int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String arcName = getArcName(arcID);
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (arcName == null)
			return null;

		rm.setSelectYear(queryYear);
		rm.setSelectMonth(queryMonth);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where ARCHITECTURE_ID=?  and SELECTYEAR=? and selectmonth=? "
				+ "group by fenlei) b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, arcID);
			pstmt.setInt(3, queryYear);
			pstmt.setInt(4, queryMonth);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where ARCHITECTURE_ID=? and SELECTYEAR=? and selectmonth=? group by USEAMSTYLE)"
				+ " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, arcID);
			pstmt1.setInt(3, queryYear);
			pstmt1.setInt(4, queryMonth);

			rs1 = pstmt1.executeQuery();

			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getInt("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setArcName(arcName);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}

		return rm;
	}

	public WaterReportModel getAreaCountDetailByMonth(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String areaName = getAreaName(areaID);
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (areaName == null)
			return null;

		rm.setSelectYear(queryYear);
		rm.setSelectMonth(queryMonth);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where Area_ID=?  and SELECTYEAR=? and selectmonth=? " + "group by fenlei) b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, areaID);
			pstmt.setInt(3, queryYear);
			pstmt.setInt(4, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where Area_ID=? and SELECTYEAR=? and selectmonth=? group by USEAMSTYLE)"
				+ " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, areaID);
			pstmt1.setInt(3, queryYear);
			pstmt1.setInt(4, queryMonth);

			rs1 = pstmt1.executeQuery();

			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getInt("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setAreaName(areaName);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}

		return rm;
	}

	public WaterReportModel getAllCountDetailByMonth(int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		Map<String, Float> fenlei = new HashMap<String, Float>();
		Map<String, Float> style = new HashMap<String, Float>();

		String name = getSystmName();
		String sql = null;
		String fenXiangXiaoJi = "";
		String xingZhiXiaoJi = "";
		float totalEneryCount = 0;
		float totalMoneyCount = 0;

		if (name == null)
			return null;

		rm.setSelectYear(queryYear);
		rm.setSelectMonth(queryMonth);

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_arcfenleidayWater where SELECTYEAR=? and selectmonth=? " + "group by fenlei) b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				fenlei.put(rs.getString("value"), rs.getFloat("gross"));

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + " " + rs.getFloat("gross") + "\n";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
				+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
				+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
				+ "T_ARCSTYLEDAYWater where SELECTYEAR=? and selectmonth=? group by USEAMSTYLE)" + " b on a.DictionaryValue_NUM=b.fenlei";
		Connection conn1 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt1 = conn1.prepareStatement(sql);
			pstmt1.setInt(1, 26);
			pstmt1.setInt(2, queryYear);
			pstmt1.setInt(3, queryMonth);

			rs1 = pstmt1.executeQuery();

			totalEneryCount = 0;
			totalMoneyCount = 0;
			while (rs1.next())
			{
				style.put(rs1.getString("value"), rs1.getFloat("gross"));

				xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + " " + rs1.getInt("gross") + "\n";
				totalEneryCount = totalEneryCount + rs1.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
			}

			rm.setStyle(style);
			rm.setXingZhiXiaoJi(xingZhiXiaoJi);
			rm.setTotalWaterCount(totalEneryCount);
			rm.setTotalMoneyCount(totalMoneyCount);
			rm.setName(name);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt1, rs1);
		}

		return rm;
	}

	/***
	 * 查询指定时间段内的指定建筑物的 每一天的用水情况，包括用水量和水费
	 * 
	 * @param groupID
	 *            所要查询的部门ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getArcCountBetween(int arcID, Date start, Date end) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String arcName = getArcName(arcID);
		String sql = null;
		try
		{
			if (arcName == null)
				return null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_arcdaywater where ARCHITECTURE_ID=? and selectDay>? and "
						+ "selectDay<? and selectMonth=? and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, arcID);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_arcdaywater where ARCHITECTURE_ID=? and ((selectMonth=? "
						+ "and seledtDate>?) or (selectMonth>? and selectMonth<?) or "
						+ "(selectMonth=? and selectDay<?)) and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, arcID);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear() + 1900);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from t_arcdaywater " + "where ARCHITECTURE_ID=? and "
						+ "((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or " + "(selectYear>? and selectYear<?) or "
						+ "(selectYear=? and (selectMonth<? or (selectMonth=? and selectDay<?)))) " + "order by selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, arcID);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();

				rm.setArcName(arcName);
				rm.setSelectYear(rs.getInt("selectYear"));
				rm.setSelectMonth(rs.getInt("selectMonth"));
				rm.setSelectDay(rs.getInt("selectDay"));
				rm.setTotalWaterCount(rs.getFloat("zgross"));
				rm.setTotalMoneyCount(rs.getFloat("zmoney"));

				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定时间段内的全校的 每一天的用水情况，包括用水量和水费
	 * 
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getSchoolCountBetween(Date start, Date end) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try
		{
			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_arcdaywater where  selectDay>? and "
						+ "selectDay<? and selectMonth=? and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start.getDate() - 1);
				pstmt.setInt(2, end.getDate() + 1);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getYear() + 1900);
//				System.out.println( (start.getDate() - 1)+"  "+(end.getDate() + 1)+"   "+ (start.getMonth() + 1)+"  "+(start.getYear() + 1900));
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from " + "t_arcdaywater where  ((selectMonth=? "
						+ "and seledtDate>?) or (selectMonth>? and selectMonth<?) or "
						+ "(selectMonth=? and selectDay<?)) and selectYear=? order by " + "selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start.getMonth() + 1);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, end.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getDate() + 1);
				pstmt.setInt(7, start.getYear() + 1900);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select selectYear,selectMonth,selectDay,zgross,zmoney from t_arcdaywater " + "where  "
						+ "((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or " + "(selectYear>? and selectYear<?) or "
						+ "(selectYear=? and (selectMonth<? or (selectMonth=? and selectDay<?)))) " + "order by selectYear,selectMonth,selectDay";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start.getYear() + 1900);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getDate() - 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, end.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getMonth() + 1);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();

				rm.setSelectYear(rs.getInt("selectYear"));
				rm.setSelectMonth(rs.getInt("selectMonth"));
				rm.setSelectDay(rs.getInt("selectDay"));
				rm.setTotalWaterCount(rs.getFloat("zgross"));
				rm.setTotalMoneyCount(rs.getFloat("zmoney"));

				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 通过区域ID，查询的年份和月份查出对应的电量
	 * 
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 结果
	 * @throws SQLException
	 */
	public WaterReportModel getAreaCountByMonth(int areaID, int year, int month) throws SQLException
	{
		WaterReportModel wrm = new WaterReportModel();
		ArchitectureDao archDao = new ArchitectureDao();
		AreaDao areaDao = new AreaDao();
		List<Architecture> arclist = null; // 区域下的所有建筑信息
		WaterReportModel arc_wrm = null;
		Area area = null;
		float energy_value = 0; // 存放建筑所有电量之和
		arclist = archDao.queryArchByAreaID(areaID);
		area = areaDao.query(areaID);

		for (int i = 0; i < arclist.size(); i++)
		{
			arc_wrm = getArcCountByMonth(arclist.get(i).getId(), year, month);
			energy_value += arc_wrm.getTotalWaterCount();
		}
		wrm.setAreaTotalWaterCount(energy_value);
		wrm.setArcID(areaID);
		wrm.setAreaName(area.getName());
		wrm.setSelectYear(year);
		wrm.setSelectMonth(month);

		return wrm;
	}

	/**
	 * 查询指定建筑指定年月份的用水情况，包括用水量和水费
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return WaterReportModel 查询结果集
	 * @throws SQLException
	 */
	public WaterReportModel getArcCountByMonth(int arcID, int queryYear, int queryMonth) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		if(arcID==0){

			String sql = null;

			sql = "select sum(zgross) gross,sum(zmoney) money from t_arcdaywater "
					+ "where selectYear=? and selectMonth=?  group by selectYear,selectMonth";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, queryYear);
				pstmt.setInt(2, queryMonth);
				 

				rs = pstmt.executeQuery();

				if (rs.next())
				{
					 
					rm.setSelectYear(queryYear);
					rm.setSelectMonth(queryMonth);
					rm.setTotalWaterCount(rs.getFloat("gross"));
					rm.setTotalMoneyCount(rs.getFloat("money"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}

		}
		else{
			String arcName = getArcName(arcID);

			if (arcName == null)
				return null;

			String sql = null;

			sql = "select sum(zgross) gross,sum(zmoney) money from t_arcdaywater "
					+ "where selectYear=? and selectMonth=? and ARCHITECTURE_ID=? group by selectYear,selectMonth";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, queryYear);
				pstmt.setInt(2, queryMonth);
				pstmt.setInt(3, arcID);

				rs = pstmt.executeQuery();

				if (rs.next())
				{
					rm.setArcName(arcName);
					rm.setSelectYear(queryYear);
					rm.setSelectMonth(queryMonth);
					rm.setTotalWaterCount(rs.getFloat("gross"));
					rm.setTotalMoneyCount(rs.getFloat("money"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}

		}
		
		return rm;
	}

	/**
	 * 查询指定建筑指定年份的用水情况，包括用水量和水费
	 * 
	 * @param arcID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @return WaterReportModel 查询返回结果集
	 * @throws SQLException
	 */
	public WaterReportModel getArcCountByYear(int arcID, int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();
		String arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		String sql = null;

		sql = "select sum(zgross) gross,sum(zmoney) money from t_arcdaywater " + "where selectYear=? and ARCHITECTURE_ID=? group by selectYear";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, arcID);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm.setArcName(arcName);
				rm.setSelectYear(queryYear);
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 通过建筑分类ID和年份，查询该年份类该类的用水信息
	 * 
	 * @param styleID
	 *            分类ID
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public WaterReportModel getFenleiArcCountByYear(char styleID, int queryYear) throws SQLException
	{
		WaterReportModel rm = new WaterReportModel();

		String sql = null;

		sql = "select sum(zGross),sum(zmoney) from T_ArcDayWater " + "where selectYear = ?  and Architecture_ID in "
				+ "(select Architecture_ID from Architecture where Architecture_Style = ?)" + " group by selectYear ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setString(2, styleID + "");

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm.setSelectYear(queryYear);
				rm.setTotalWaterCount(rs.getFloat("sum(zGross)"));
				rm.setTotalMoneyCount(rs.getFloat("sum(zmoney)"));
			} else
			{
				rm.setSelectYear(queryYear);
				rm.setTotalWaterCount(0);
				rm.setTotalMoneyCount(0);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/***
	 * 查询指定建筑指定时间段内的用水分项分布
	 * 
	 * @param groupID
	 *            查询的建筑物ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            查截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getArcFenLeiCountBetween(int arcID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		if (arcID == 0)
		{
			String sql = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{

				if (start.after(end))
					return null;

				if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
				{
					sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
							+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
							+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
							+ "gross from T_ARCFENLEIDAYWATER where selectDay>? and selectDay<? and "
							+ "selectMonth=? and selectYear=?   group by fenlei) b on "
							+ "a.fenleiNum=b.fenlei order by gross desc";
					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getDate() - 1);
					pstmt.setInt(3, end.getDate() + 1);
					pstmt.setInt(4, start.getMonth()+1);
					pstmt.setInt(5, start.getYear()+ 1900 );
					
				}

				if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
				{
					sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
							+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
							+ "left join(select fenlei,sum(zgross) gross " + "from T_ARCFENLEIDAYWATER "
							+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
							+ "or (selectMonth=? and selectDay<?)) and selectYear=?  " + "group by fenlei) b "
							+ "on a.fenleiNum=b.fenlei order by gross desc";

					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getMonth());
					pstmt.setInt(3, start.getDate() - 1);
					pstmt.setInt(4, start.getMonth());
					pstmt.setInt(5, end.getMonth()+1);
					pstmt.setInt(6, end.getMonth()+1);
					pstmt.setInt(7, end.getDate() + 1);
					pstmt.setInt(8, start.getYear()+ 1900);
					 
				}

				if (start.getYear() < end.getYear())
				{
					sql = "select a.fenleiValue,b.gross "
							+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
							+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
							+ "from T_ARCFENLEIDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
							+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
							+ "(selectMonth=? and selectDay<?))))  group by fenlei) b "
							+ "on a.fenleiNum=b.fenlei order by gross desc";

					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getYear()+ 1900);
					pstmt.setInt(3, start.getMonth()+1);
					pstmt.setInt(4, start.getMonth()+1 );
					pstmt.setInt(5, start.getDate() - 1);
					pstmt.setInt(6, start.getYear()+ 1900);
					pstmt.setInt(7, end.getYear()+ 1900);
					pstmt.setInt(8, end.getYear()+ 1900);
					pstmt.setInt(9, end.getMonth()+1);
					pstmt.setInt(10, end.getMonth()+1);
					pstmt.setInt(11, end.getDate() + 1);
					 
				}

				rs = pstmt.executeQuery();

				while (rs.next())
				{
					map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		} else
		{
			String sql = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String arcName = getArcName(arcID);
			try
			{
				if (arcName == null)
					return null;

				if (start.after(end))
					return null;

				if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
				{
					sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
							+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
							+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
							+ "gross from T_ARCFENLEIDAYWATER where selectDay>? and selectDay<? and "
							+ "selectMonth=? and selectYear=? and ARCHITECTURE_ID=? group by fenlei) b on "
							+ "a.fenleiNum=b.fenlei order by gross desc";
					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getDate() - 1);
					pstmt.setInt(3, end.getDate() + 1);
					pstmt.setInt(4, start.getMonth()+1);
					pstmt.setInt(5, start.getYear()+1900);
					pstmt.setInt(6, arcID);
//					System.out.println((start.getDate() - 1)+"   "+(end.getDate() + 1)+"   "+(start.getMonth()+1)+"   "+(start.getYear()+ 1900 ));
				}

				if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
				{
					sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
							+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
							+ "left join(select fenlei,sum(zgross) gross " + "from T_ARCFENLEIDAYWATER "
							+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
							+ "or (selectMonth=? and selectDay<?)) and selectYear=? and ARCHITECTURE_ID=? " + "group by fenlei) b "
							+ "on a.fenleiNum=b.fenlei order by gross desc";

					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getMonth()+1);
					pstmt.setInt(3, start.getDate() - 1);
					pstmt.setInt(4, start.getMonth()+1);
					pstmt.setInt(5, end.getMonth()+1);
					pstmt.setInt(6, end.getMonth()+1);
					pstmt.setInt(7, end.getDate() + 1);
					pstmt.setInt(8, start.getYear()+1900);
					pstmt.setInt(9, arcID);
				}

				if (start.getYear() < end.getYear())
				{
					sql = "select a.fenleiValue,b.gross "
							+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
							+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
							+ "from T_ARCFENLEIDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
							+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
							+ "(selectMonth=? and selectDay<?)))) and ARCHITECTURE_ID=? group by fenlei) b "
							+ "on a.fenleiNum=b.fenlei order by gross desc";

					conn = ConnDB.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, 25);
					pstmt.setInt(2, start.getYear()+1900);
					pstmt.setInt(3, start.getMonth()+1 );
					pstmt.setInt(4, start.getMonth()+1 );
					pstmt.setInt(5, start.getDate() - 1);
					pstmt.setInt(6, start.getYear() +1900);
					pstmt.setInt(7, end.getYear()+1900 );
					pstmt.setInt(8, end.getYear()+1900 );
					pstmt.setInt(9, end.getMonth()+1 );
					pstmt.setInt(10, end.getMonth() +1);
					pstmt.setInt(11, end.getDate() + 1);
					pstmt.setInt(12, arcID);
				}

				rs = pstmt.executeQuery();

				while (rs.next())
				{
					map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		}
		return map;
	}

	/**
	 * 查询指定建筑指定年月每一天的工作和非工作时间的用水量
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ReportModel> getArcWorkTimeCountEveryDay(int arcID, int year, int month) throws SQLException, Exception
	{
		List<ReportModel> list = new ArrayList<ReportModel>();
		ReportModel rm = null;
		String sql = null;
		int MaxDay = 0; // 这个月的天数

		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month); // 查询月份有多少天
		for (int i = 1; i <= MaxDay; i++)
		{
			rm = new ReportModel();
			rm.setSelectDay(i);
			rm.setWorkEnergyCount(0);
			rm.setUnworkEnergyCount(0);
			list.add(rm);
		}

		sql = "select selectday,workdata,unworkdata from T_ARCDAYWATER "
				+ " where architecture_ID = ?  AND selectyear = ? and selectmonth = ? order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				for (int i = 0; i < list.size(); i++)
				{
					if (rs.getInt("selectday") == list.get(i).getSelectDay())
					{
						list.get(i).setWorkEnergyCount(rs.getFloat("workdata"));
						list.get(i).setUnworkEnergyCount(rs.getFloat("unworkdata"));
					}
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 查询指定区域某天的工作和非工作用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年份
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return ReportModel
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAreaWorkTimeCountByDay(int areaID, int year, int month, int day) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_ARCDAYWATER "
				+ "where area_ID = ?  AND selectyear = ? and selectmonth = ? and selectday = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);
			pstmt.setInt(4, day);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 查询所有区域某天工作和非工作用水情况
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAllAreaWorkTimeCountByDay(int year, int month, int day) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_ARCDAYWATER "
				+ "where selectyear = ? and selectmonth = ? and selectday = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, day);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 查询所有区域指定月份工作和非工作用水情况
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAllAreaWorkTimeCountByMonth(int year, int month) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_ARCDAYWATER " + "where  selectyear = ? and selectmonth = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rm;
	}

	/**
	 * 查询所有区域指定年月按性质用水情况
	 * 
	 * @param queryYear
	 *            年
	 * @param queryMonth
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAllAreaStyleCountByMonth(int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=? and selectMonth=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定年月按性质用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param queryYear
	 *            年
	 * @param queryMonth
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaStyleCountByMonth(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String areaName = null;
		areaName = getAreaName(areaID);

		if (areaName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledaywater " + "where selectYear=? and selectMonth=? and AREA_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定时间段分项用电情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaFenLeiCountBetween(int areaID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String areaName = getAreaName(areaID);
		try
		{
			if (areaName == null)
				return null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_ARCFENLEIDAYWATER where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and AREA_ID=? group by fenlei) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, areaID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select fenlei,sum(zgross) gross " + "from T_ARCFENLEIDAYWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and AREA_ID=? " + "group by fenlei) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear()+ 1900);
				pstmt.setInt(9, areaID);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
						+ "from T_ARCFENLEIDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?)))) and AREA_ID=? group by fenlei) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
				pstmt.setInt(12, areaID);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询所有区域指定时间段分项用水情况
	 * 
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAllAreaFenLeiCountBetween(Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_ARCFENLEIDAYWATER where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=?  group by fenlei) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select fenlei,sum(zgross) gross " + "from T_ARCFENLEIDAYWATER "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=?  " + "group by fenlei) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select fenlei,sum(zgross) gross "
						+ "from T_ARCFENLEIDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?))))  group by fenlei) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询得到制指定建筑指定月份的分类分项数据
	 * 
	 * @param arcID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return Map<String, Float> 查询得到的分项分部
	 * @throws SQLException
	 */
	public Map<String, Float> getArcFenLeiCountByMonth(int arcID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		if (arcID == 0)
		{
			String sql = null;

			sql = "select a.fenleiValue,b.gross from(select "
					+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
					+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
					+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater " + "where selectYear=? and selectMonth=?   "
					+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, queryYear);
				pstmt.setInt(3, queryMonth);

				rs = pstmt.executeQuery();

				while (rs.next())
				{
					map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		} else
		{
			String sql = null;
			String arcName = getArcName(arcID);
			if (arcName == null)
				return null;

			sql = "select a.fenleiValue,b.gross from(select "
					+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
					+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
					+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater "
					+ "where selectYear=? and selectMonth=? and ARCHITECTURE_ID=? "
					+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, queryYear);
				pstmt.setInt(3, queryMonth);
				pstmt.setInt(4, arcID);

				rs = pstmt.executeQuery();

				while (rs.next())
				{
					map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		}
		return map;
	}

	/**
	 * 查询得到全校指定月份的分类分项数据
	 * 
	 * 
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return Map<String, Float> 查询得到的分项分部
	 * @throws SQLException
	 */
	public Map<String, Float> getAllSchoolFenLeiCountByMonth(int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater " + "where selectYear=? and selectMonth=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到全校指定月份的分类分项数据
	 * 
	 * @param areaID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return Map<String, Float> 查询得到的分项分部
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaFenLeiCountByMonth(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		String areaName = getAreaName(areaID);
		if (areaName == null)
			return null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater " + "where selectYear=? and selectMonth=? and Area_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定区域指定月份工作和非工作用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAreaWorkTimeCountByMonth(int areaID, int year, int month) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_ARCDAYWATER "
				+ "where area_ID = ?  AND selectyear = ? and selectmonth = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 查询指定建筑指定月份工作和非工作用水情况
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getArcWorkTimeCountByMonth(int arcID, int year, int month) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_ARCDAYWATER "
				+ "where architecture_ID = ?  AND selectyear = ? and selectmonth = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return rm;
	}

	/**
	 * 查询得到指定建筑指定年份的分类分项数据
	 * 
	 * @param arcID
	 *            所要查询的建筑ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　Map<String, Float>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public Map<String, Float> getArcFenLeiCountByYear(int arcID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		String arcName = getArcName(arcID);
		if (arcName == null)
			return null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater " + "where selectYear=? and ARCHITECTURE_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到指定建筑指定年份的分类分项数据<以List形式>
	 * 
	 * @param arcID
	 *            所要查询的建筑ＩＤ
	 * @param queryYear
	 *            　所要查询的年份
	 * @return　List<WaterReportModel>　查询得到的分项分布数据
	 * @throws SQLException
	 */
	public List<WaterReportModel> getArcFenLeiCountByYearInList(int arcID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;
		String sql = null;

		String arcName = getArcName(arcID);
		if (arcName == null)
			return null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidayWater " + "where selectYear=? and ARCHITECTURE_ID=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setFenXiangXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// if (map == null)
				// map = new HashMap<String, Float>();
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定建筑指定时间段内的用水性质分布
	 * 
	 * @param groupID
	 *            查询的建筑物ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            查截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Float> getArcStyleCountBetween(int arcID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String arcName = getArcName(arcID);
		try
		{
			if (arcName == null)
				return null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_ARCSTYLEDAYWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and ARCHITECTURE_ID=? group by USEAMSTYLE) b on "
						+ "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, arcID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_ARCSTYLEDAYWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and ARCHITECTURE_ID=? " + "group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
				pstmt.setInt(9, arcID);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select USEAMSTYLE fenlei,sum(zgross) gross "
						+ "from T_ARCSTYLEDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?)))) and ARCHITECTURE_ID=? group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
				pstmt.setInt(12, arcID);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询指定区域指定时间段性质用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAreaStyleCountBetween(int areaID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String areaName = getAreaName(areaID);
		try
		{
			if (areaName == null)
				return null;

			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_ARCSTYLEDAYWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and AREA_ID=? group by USEAMSTYLE) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, areaID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_ARCSTYLEDAYWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and AREA_ID=? " + "group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
				pstmt.setInt(9, areaID);
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select USEAMSTYLE fenlei,sum(zgross) gross "
						+ "from T_ARCSTYLEDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?)))) and AREA_ID=? group by USEAMSTYLE) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
				pstmt.setInt(12, areaID);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询所有区域指定时间段性质用水情况
	 * 
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return 结果集
	 * @throws SQLException
	 */
	public Map<String, Float> getAllAreaStyleCountBetween(Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (start.after(end))
				return null;

			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getDate() <= end.getDate()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_ARCSTYLEDAYWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=?  group by USEAMSTYLE) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross " + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_ARCSTYLEDAYWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=?  " + "group by USEAMSTYLE) b "
						+ "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getMonth() + 1);
				pstmt.setInt(3, start.getDate() - 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, end.getMonth() + 1);
				pstmt.setInt(6, end.getMonth() + 1);
				pstmt.setInt(7, end.getDate() + 1);
				pstmt.setInt(8, start.getYear());
			}

			if (start.getYear() < end.getYear())
			{
				sql = "select a.fenleiValue,b.gross "
						+ "from(select DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value fenleiValue "
						+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a " + "left join(select USEAMSTYLE fenlei,sum(zgross) gross "
						+ "from T_ARCSTYLEDAYWater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
						+ "(selectYear>? and selectYear<?) or (selectYear=? and (selectMonth<? or "
						+ "(selectMonth=? and selectDay<?))))  group by USEAMSTYLE) b " + "on a.fenleiNum=b.fenlei order by gross desc";

				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 26);
				pstmt.setInt(2, start.getYear() + 1900);
				pstmt.setInt(3, start.getMonth() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getDate() - 1);
				pstmt.setInt(6, start.getYear() + 1900);
				pstmt.setInt(7, end.getYear() + 1900);
				pstmt.setInt(8, end.getYear() + 1900);
				pstmt.setInt(9, end.getMonth() + 1);
				pstmt.setInt(10, end.getMonth() + 1);
				pstmt.setInt(11, end.getDate() + 1);
			}

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return map;
	}

	/**
	 * 查询得到指定建筑指定年月的用水性质分布
	 * 
	 * @param arcID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return Map<String, Float> 查询得到的用水性质分布数据
	 * @throws SQLException
	 */
	public Map<String, Float> getArcStyleCountByMonth(int arcID, int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String arcName = null;
		arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledayWater "
				+ "where selectYear=? and selectMonth=? and ARCHITECTURE_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);
			pstmt.setInt(4, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (map == null)
					map = new HashMap<String, Float>();
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到全校指定月份的分类分项用水数据
	 * 
	 * 
	 * @param queryYear
	 *            所要查询的年份
	 * @param queryMonth
	 *            所要查询的月份
	 * @return Map<String, Float> 查询得到的分项分部
	 * @throws SQLException
	 */
	public Map<String, Float> getAllschoolFenLeiCountByMonth(int queryYear, int queryMonth) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;

		sql = "select a.fenleiValue,b.gross from(select " + "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=? and selectMonth=? "
				+ "group by fenlei) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 25);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询指定部门指定月份工作和非工作时间用水情况
	 * 
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getPartmentWorkTimeCountByMonth(int parID, int year, int month) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_PARTMENTDAYWATER "
				+ "where partment_ID = ?  AND selectyear = ? and selectmonth = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rm;
	}

	/**
	 * 查询所有部门指定月份 工作时间和非工作时间用水情况
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAllPartmentWorkTimeCountByMonth(int year, int month) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_PARTMENTDAYWATER " + "where  selectyear = ? and selectmonth = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rm;
	}

	/**
	 * 查询指定月份每一天部门工作时间和非工作时间用水情况
	 * 
	 * @param parID
	 *            部门ID
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ReportModel> getPartmentWorkTimeCountEveryDay(int parID, int year, int month) throws SQLException, Exception
	{
		List<ReportModel> list = new ArrayList<ReportModel>();
		ReportModel rm = null;
		String sql = null;
		int MaxDay = 0; // 这个月的天数

		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month); // 查询月份有多少天
		for (int i = 1; i <= MaxDay; i++)
		{
			rm = new ReportModel();
			rm.setSelectDay(i);
			rm.setWorkEnergyCount(0);
			rm.setUnworkEnergyCount(0);
			list.add(rm);
		}

		sql = "select selectday,workdata,unworkdata from T_PARTMENTDAYWATER"
				+ " where PARTMENT_ID = ?  AND selectyear = ? and selectmonth = ? order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				for (int i = 0; i < list.size(); i++)
				{
					if (rs.getInt("selectday") == list.get(i).getSelectDay())
					{
						list.get(i).setWorkEnergyCount(rs.getFloat("workdata"));
						list.get(i).setUnworkEnergyCount(rs.getFloat("unworkdata"));
					}
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 查询所有部门指定日期工作时间和非工作时间的用水情况
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            天
	 * @return 结果集
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportModel getAllPartmentWorkTimeCountByDay(int year, int month, int day) throws SQLException, Exception
	{
		ReportModel rm = null;
		String sql = null;

		sql = "select sum(workdata) workdata,sum(unworkdata) unworkdata " + "from T_PARTMENTDAYWATER "
				+ "where  selectyear = ? and selectmonth = ? and selectday = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, day);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(rs.getFloat("workdata"));
				rm.setUnworkEnergyCount(rs.getFloat("unworkdata"));
			} else
			{
				rm = new ReportModel();
				rm.setWorkEnergyCount(0);
				rm.setUnworkEnergyCount(0);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return rm;
	}

	/**
	 * 查询得到指定建筑的指定年份的用水性质分项数据
	 * 
	 * @param arcID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @return Map<String, Float> 查询得到的用水性质分项数据
	 * @throws SQLException
	 */
	public Map<String, Float> getArcStyleCountByYear(int arcID, int queryYear) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String arcName = null;
		arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledayWater " + "where selectYear=? and ARCHITECTURE_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return map;
	}

	/**
	 * 查询得到指定建筑的指定年份的用水性质分项数据
	 * 
	 * @param arcID
	 *            所要查询的建筑ID
	 * @param queryYear
	 *            所要查询的年份
	 * @return List<WaterReportModel> 查询得到的用水性质分项数据
	 * @throws SQLException
	 */
	public List<WaterReportModel> getArcStyleCountByYearInList(int arcID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		WaterReportModel wrm = null;
		String arcName = null;
		arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		String sql = "select a.fenleiValue,b.gross from(select "
				+ "DictionaryValue.DictionaryValue_Num fenleiNum,DictionaryValue.DictionaryValue_value "
				+ "fenleiValue from DictionaryValue where DictionaryValue.Dictionary_ID=?) a left "
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_arcstyledayWater " + "where selectYear=? and ARCHITECTURE_ID=? "
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 26);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				wrm = new WaterReportModel();
				wrm.setXingZhiXiaoJi(rs.getString("fenleiValue"));
				wrm.setTotalWaterCount(rs.getFloat("gross"));
				list.add(wrm);
				// map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定建筑指定年份的每个月的用水情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * @param queryYear
	 *            查询的年份
	 * @throws SQLException
	 * @return List<WaterReportModel> 查询得到12个月份的结果集
	 */
	public List<WaterReportModel> getArcCountEveryMonth(int arcID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);
		WaterReportModel rm = null;
		String arcName = getArcName(arcID);
		String sql = null;
		if (arcName == null)
			return null;

		for (int i = 1; i <= 12; i++)
		{
			rm = new WaterReportModel();
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(i);
			rm.setTotalWaterCount(0);
			rm.setTotalMoneyCount(0);
			rm.setArcName(arcName);
			list.add(rm);
		}

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money " + "from T_ArcDayWater where selectyear=? and ARCHITECTURE_ID=?  group "
				+ "by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, arcID);

			rs = pstmt.executeQuery();
			Map<String, Float[]> map = new HashMap<String, Float[]>();
			while (rs.next())
			{
				map.put(rs.getInt("selectmonth") + "", new Float[]
				{ rs.getFloat("gross"), rs.getFloat("money") });
				/*
				 * if(rs.getInt("selectmonth") == list.get(i).getSelectMonth())
				 * { list.get(i).setTotalEnergyCount(rs.getFloat("gross"));
				 * list.get(i).setTotalMoneyCount(rs.getFloat("money")); }
				 */
			}
			WaterReportModel r = null;
			Float data[] = null;
			for (int i = 0; i < list.size(); i++)
			{
				r = list.get(i);
				data = map.get(r.getSelectMonth() + "");
				if (data == null)
					continue;
				r.setTotalWaterCount(data[0]);
				r.setTotalMoneyCount(data[1]);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询同类别建筑指定年份每个月的用电情况
	 * 
	 * @param archStyle
	 *            建筑类别
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getArcStyleCountEveryMonth(String archStyle, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);
		WaterReportModel rm = null;
		ArchitectureDao arcDao = new ArchitectureDao();
		String archStyleName = arcDao.queryArchStyleName(archStyle.charAt(0));
		String sql = null;

		for (int i = 1; i <= 12; i++)
		{
			rm = new WaterReportModel();
			rm.setSelectMonth(i);
			rm.setTotalWaterCount(0);
			rm.setTotalMoneyCount(0);
			rm.setSelectYear(queryYear);
			rm.setStyleName(archStyleName);
			list.add(rm);
		}

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money" + " from T_ArcDayWater where selectyear=? " + "and ARCHITECTURE_ID in"
				+ "(Select Architecture_ID from Architecture where Architecture_Style = '" + archStyle.charAt(0) + "') "
				+ "group by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);

			rs = pstmt.executeQuery();

			Map<String, Float[]> map = new HashMap<String, Float[]>();
			while (rs.next())
			{
				map.put(rs.getInt("selectmonth") + "", new Float[]
				{ rs.getFloat("gross"), rs.getFloat("money") });
				/*
				 * if(rs.getInt("selectmonth") == list.get(i).getSelectMonth())
				 * { list.get(i).setTotalEnergyCount(rs.getFloat("gross"));
				 * list.get(i).setTotalMoneyCount(rs.getFloat("money")); }
				 */
			}
			WaterReportModel r = null;
			Float data[] = null;
			for (int i = 0; i < list.size(); i++)
			{
				r = list.get(i);
				data = map.get(r.getSelectMonth() + "");
				if (data == null)
					continue;
				r.setTotalWaterCount(data[0]);
				r.setTotalMoneyCount(data[1]);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询指定区域指定年份的每个月的用水情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param areaID
	 *            查询的区域ID
	 * @param queryYear
	 *            查询的年份
	 * @return 查询得到12个月份的结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAreaCountEveryMonth(int areaID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);

		String areaName = getAreaName(areaID);
		String sql = null;
		if (areaName == null)
			return null;

		for (int i = 0; i < 12; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setAreaName(areaName);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(i + 1);
			list.add(rm);
		}

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money " + "from T_ArcDayWater where selectyear=? and AREA_ID=?  group "
				+ "by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectmonth") <= 12 && rs.getInt("selectmonth") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectmonth") - 1);
					rm.setTotalWaterCount(rs.getFloat("gross"));
					rm.setTotalMoneyCount(rs.getFloat("money"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	public List<WaterReportModel> getAllCountEveryMonth(int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);

		String name = getSystmName();
		String sql = null;
		if (name == null)
			return null;

		for (int i = 0; i < 12; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setAreaName(name);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(i + 1);
			list.add(rm);
		}

		sql = "select selectmonth,sum(zgross) gross,sum(zmoney) " + "money from T_ArcDayWater where selectyear=? "
				+ "group by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectmonth") <= 12 && rs.getInt("selectmonth") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectmonth") - 1);
					rm.setTotalWaterCount(rs.getFloat("gross"));
					rm.setTotalMoneyCount(rs.getFloat("money"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 通过查询的年份，查询出该年份里面所有区域的用水情况
	 * 
	 * @param queryYear
	 *            查询的年份
	 * @return 查询结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAllAreaCountEveryMonth(int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);

		String sql = null;

		for (int i = 0; i < 12; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(i + 1);
			rm.setTotalMoneyCount(0);
			rm.setTotalWaterCount(0);
			list.add(rm);
		}

		sql = "select selectmonth,sum(zgross) as gross,sum(zmoney) as money " + "from T_ArcDayWater where selectyear=?   group "
				+ "by selectmonth order by selectmonth";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);

			rs = pstmt.executeQuery();

			Map<String, Float[]> map = new HashMap<String, Float[]>();
			while (rs.next())
			{
				map.put(rs.getInt("selectmonth") + "", new Float[]
				{ rs.getFloat("gross"), rs.getFloat("money") });
			}
			WaterReportModel r = null;
			Float data[] = null;
			for (int i = 0; i < list.size(); i++)
			{
				r = list.get(i);
				data = map.get(r.getSelectMonth() + "");
				if (data == null)
					continue; // 为空则继续找下一个不为空的替换
				r.setTotalWaterCount(data[0]);
				r.setTotalMoneyCount(data[1]);
			}

			/*
			 * while (rs.next()) { if (rs.getInt("selectmonth") <= 12 &&
			 * rs.getInt("selectmonth") >= 1) { WaterReportModel rm =
			 * list.get(rs.getInt("selectmonth") - 1);
			 * rm.setTotalWaterCount(rs.getFloat("gross"));
			 * rm.setTotalMoneyCount(rs.getFloat("money")); } }
			 */
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定部门指定某年月的每一天的用水量和水费
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return List<WaterReportModel> 查询得到的每一天的数据结果集
	 * @throws SQLException
	 * @throws ParseException
	 * 
	 */
	public List<WaterReportModel> getArcCountEveryDay(int arcID, int queryYear, int queryMonth) throws SQLException, ParseException
	{
		// List<WaterReportModel> list = new ArrayList<WaterReportModel>();
		//
		// String arcName = getArcName(arcID);
		// String sql = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// if (arcName == null)
		// return null;
		// // int dayCount = getDayCount(queryYear, queryMonth);
		// int MaxDay = 0;
		// DateOperation dateop = new DateOperation();
		// MaxDay = dateop.queryMaxDayinMonth(queryYear, queryMonth); //
		// 查询月份有多少天
		//
		// for (int i = 1; i <= MaxDay; i++) {
		// WaterReportModel rm = new WaterReportModel();
		// rm.setSelectDay(i);
		// rm.setSelectMonth(queryMonth);
		// rm.setSelectYear(queryYear);
		// rm.setTotalWaterCount(0);
		// rm.setTotalMoneyCount(0);
		// list.add(rm);
		// }
		//
		// sql =
		// "select selectday,zgross,zmoney from T_ArcDayWater where selectyear=? "
		// + "and selectmonth=? and ARCHITECTURE_ID=? order by selectday";
		//
		// pstmt = ConnDB.getConnection().prepareStatement(sql);
		//
		// pstmt.setInt(1, queryYear);
		// pstmt.setInt(2, queryMonth);
		// pstmt.setInt(3, arcID);
		//
		// rs = pstmt.executeQuery();
		//
		// for (int i = 0; i < list.size(); i++) {
		// while (rs.next()) {
		// if (rs.getInt("selectday") == list.get(i).getSelectDay()) {
		// list.get(i).setTotalWaterCount(rs.getFloat("zgross"));
		// list.get(i).setTotalMoneyCount(rs.getFloat("zmoney"));
		// }
		// }
		//
		// }
		//
		// close(pstmt, rs);
		// return list;
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		int dayCount = getDayCount(queryYear, queryMonth);

		String arcName = getArcName(arcID);
		String sql = null;
		if (arcName == null)
			return null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setAreaName(arcName);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

		sql = "select selectday,zgross,zmoney from T_ArcDayWater where selectyear=? " + "and selectmonth=? and ARCHITECTURE_ID=? order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);
			pstmt.setInt(3, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectday") <= dayCount && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("zgross"));
					rm.setTotalMoneyCount(rs.getFloat("zmoney"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 通过分类建筑ID和指定的年月，查询指定年月里该类建筑一个月里每一天的用水情况
	 * 
	 * @param styleID
	 *            分类建筑ID
	 * @param queryYear
	 *            年份
	 * @param queryMonth
	 *            月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getFenleiArcCountEveryDay(char styleID, int queryYear, // 分类建筑一个月内每一天的用水情况
			int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(31);

		int dayCount = getDayCount(queryYear, queryMonth);

		ArchitectureDao archdao = new ArchitectureDao();
		String styleName = archdao.queryArchStyleName(styleID);
		String sql = null;
		if (styleName == null)
			return null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			rm.setStyleName(styleName);
			list.add(rm);
		}

		sql = "select selectDay,sum(zGross),sum(zmoney) from T_ArcDayWater" + " where selectYear = ? and selectMonth =? "
				+ "and Architecture_ID in (select Architecture_ID from "
				+ "Architecture where Architecture_Style = ?) group by selectDay order by selectDay";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);
			pstmt.setString(3, styleID + "");

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectday") <= 31 && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("sum(zGross)"));
					rm.setTotalMoneyCount(rs.getFloat("sum(zmoney)"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询所有建筑指定年月一个月内每一天的用电情况
	 * 
	 * @param queryYear
	 *            年份
	 * @param queryMonth
	 *            月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAllFenleiArcCountEveryDay(int queryYear, int queryMonth) throws SQLException// 所有建筑一个月内每一天的用水情况
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(31);

		int dayCount = getDayCount(queryYear, queryMonth);

		String sql = null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

		sql = "select selectDay,sum(zGross),sum(zmoney) from T_ArcDayWater"
				+ " where selectYear = ? and selectMonth =? group by selectDay order by selectDay";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (rs.getInt("selectday") <= 31 && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("sum(zGross)"));
					rm.setTotalMoneyCount(rs.getFloat("sum(zmoney)"));
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 通过区域ID和指定年月，查询该区域该月内每一天的用水情况
	 * 
	 * @param areaID
	 *            区域ID
	 * @param queryYear
	 *            年份
	 * @param queryMonth
	 *            月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAreaCountEveryDay(int areaID, int queryYear, // 加的
			int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		int dayCount = getDayCount(queryYear, queryMonth);

		String areaName = getAreaName(areaID);
		String sql = null;
		if (areaName == null)
			return null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setAreaName(areaName);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

		sql = "select selectday,sum(zgross)zgross,sum(zmoney)zmoney from T_ArcDayWater where selectyear=? " + "and selectmonth=? and AREA_ID=?   group by selectday order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);
			pstmt.setInt(3, areaID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectday") <= dayCount && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("zgross"));
					rm.setTotalMoneyCount(rs.getFloat("zmoney"));
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	public List<WaterReportModel> getAllCountEveryDay(int queryYear, int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		int dayCount = getDayCount(queryYear, queryMonth);

		String name = getSystmName();
		String sql = null;
		if (name == null)
			return null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setName(name);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

		sql = "select selectday,sum(zgross) gross,sum(zmoney) " + "money from T_ArcDayWater where selectyear=? "
				+ "and selectmonth=? group by selectday order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectday") <= dayCount && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("gross"));
					rm.setTotalMoneyCount(rs.getFloat("money"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询指定年月所有区域该月里每一天的用电情况
	 * 
	 * @param queryYear
	 *            年份
	 * @param queryMonth
	 *            月份
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<WaterReportModel> getAllAreaCountEveryDay(int queryYear, // 加的
			int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>();

		int dayCount = getDayCount(queryYear, queryMonth);
		String schoolName = GetSystemInfo.getSchoolName();
		String sql = null;

		for (int i = 0; i < dayCount; i++)
		{
			WaterReportModel rm = new WaterReportModel();
			rm.setName(schoolName);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

		sql = "select distinct selectday,sum(zgross)zgross,sum(zmoney)zmoney from T_ArcDayWater where selectyear=? "
				+ "and selectmonth=? group by selectday order by selectday";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				if (rs.getInt("selectday") <= dayCount && rs.getInt("selectday") >= 1)
				{
					WaterReportModel rm = list.get(rs.getInt("selectday") - 1);
					rm.setTotalWaterCount(rs.getFloat("zgross"));
					rm.setTotalMoneyCount(rs.getFloat("zmoney"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 根据电表ID和起始截止时间获取电量数据
	 * 
	 * @param ammeterID
	 *            电表ID
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<AmMeterMataData> getWaterMeterBetweenNew(int ammeterID, Date start, Date end) throws SQLException, ParseException
	{
		List<AmMeterMataData> list = new LinkedList<AmMeterMataData>();
		AmMeterMataData ammd = null;
		EnergyMonitorDao emDao = new EnergyMonitorDao();

		for (int i = 0; i < 72; i++)
		{
			ammd = new AmMeterMataData();
			ammd.setTemperature(0);
			ammd.setHour(i % 24);
			ammd.setValue(0);
			list.add(ammd);
		}

		String sql = "select " + "to_char(ValueTime,'yyyy-mm-dd hh24') ValueTime,to_char(ValueTime,'yyyy-mm-dd')cal,to_char(ValueTime,'hh24')H, "
				+ "(max(ZValueZY) -min(ZValueZY) ) Value " + "from " + "ZWATERDATAS" + String.valueOf(ammeterID) + " " + "where  "
				+ "	ValueTime between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') "
				// + "	and WaterMeter_ID=? "
				+ " group by to_char(ValueTime,'yyyy-mm-dd hh24'),to_char(ValueTime,'yyyy-mm-dd'),to_char(ValueTime,'hh24') " + " order by ValueTime";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, DateFormat.getDateInstance(DateFormat.DEFAULT).format(start));
			pstmt.setString(2, DateFormat.getDateInstance(DateFormat.DEFAULT).format(end));

			rs = pstmt.executeQuery();

			while (rs.next())
			{

				float valuedata = rs.getFloat("Value");
				String date = rs.getString("cal");
				String H = rs.getString("H");

				if (date != null && !"".equals(date) && H != null && !"".equals(H))
				{
					int hour = Integer.parseInt(H);
					int a = emDao.getListIndex(start, end, date, hour);
					if (a != -1)
					{

						list.get(a).setValue(valuedata);
					} else
					{
						// System.out.println("非法数据");
					}
				}

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 将得到的水表数组数据汇总
	 * 
	 * @param watermeterListArray
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<AmMeterMataData> getWaterMeterCountBetween(String[] WatermeterListArray, Date start, Date end) throws SQLException, ParseException
	{
		List<AmMeterMataData> list_back = null; // 返回的list
		List<AmMeterMataData> list_get = null; // 得到单个电表list
		AmMeterMataData ammd = null;
		float value = 0; // 电量临时变量

		list_back = new ArrayList<AmMeterMataData>();

		for (int j = 0; j < 72; j++)
		{
			for (int i = 0; i < WatermeterListArray.length; i++)
			{
				int a = Integer.parseInt(WatermeterListArray[i]);
				list_get = getWaterMeterBetweenNew(a, start, end);
				// for(int k = 0;k < list_get.size();k++)
				// {
				float b = list_get.get(j).getValue();
				value += b;
				// }

				// if(list_get.size() >= 72)
				// {
				// float b = list_get.get(j).getValue();
				// value += b;
				// }
				// else
				// {
				// value = 0;
				// }
				//

				/*
				 * for(int j=0;j<list_get.size();j++) {
				 * 
				 * value += list_get.get(j).getValue();
				 * 
				 * ammd.setValue(value);
				 * ammd.setRecordTimeDate(list_get.get(j).getRecordTimeDate());
				 * 
				 * list_back.add(ammd); }
				 */
			}

			ammd = new AmMeterMataData();
			ammd.setValue(value);
			// if(list_get.size() >= 72)
			// {
			ammd.setRecordTimeDate(list_get.get(j).getRecordTimeDate());
			// }

			list_back.add(ammd);
		}

		return list_back;
	}

	/**
	 * 查询指定月份指定类别建筑每天的用水情况
	 * 
	 * @param archStyle
	 *            建筑类别
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<WaterReportModel> getArcStyleCountEveryDay(String archStyle, int queryYear, int queryMonth) throws SQLException, ParseException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(12);
		WaterReportModel rm = null;
		ArchitectureDao arcDao = new ArchitectureDao();
		String archStyleName = arcDao.queryArchStyleName(archStyle.charAt(0));
		String sql = null;

		int MaxDay = 0; // 这个月的天数

		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(queryYear, queryMonth); // 查询月份有多少天

		for (int i = 1; i <= MaxDay; i++)
		{
			rm = new WaterReportModel();
			rm.setName(archStyleName);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i);
			rm.setTotalWaterCount(0);
			rm.setTotalMoneyCount(0);
			rm.setSelectYear(queryYear);
			rm.setStyleName(archStyleName);
			list.add(rm);
		}

		sql = "select selectday,sum(zgross) as gross,sum(zmoney) as money" + " from T_ArcDayWater where selectyear=? and selectmonth=? "
				+ "and ARCHITECTURE_ID in" + "(Select Architecture_ID from Architecture where Architecture_Style = '" + archStyle.charAt(0) + "') "
				+ "group by selectday order by selectday ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, queryYear);
			pstmt.setInt(2, queryMonth);

			rs = pstmt.executeQuery();

			Map<String, Float[]> map = new HashMap<String, Float[]>();
			while (rs.next())
			{
				map.put(rs.getInt("selectday") + "", new Float[]
				{ rs.getFloat("gross"), rs.getFloat("money") });
				/*
				 * if(rs.getInt("selectmonth") == list.get(i).getSelectMonth())
				 * { list.get(i).setTotalEnergyCount(rs.getFloat("gross"));
				 * list.get(i).setTotalMoneyCount(rs.getFloat("money")); }
				 */
			}
			WaterReportModel r = null;
			Float data[] = null;
			for (int i = 0; i < list.size(); i++)
			{
				r = list.get(i);
				data = map.get(r.getSelectDay() + "");
				if (data == null)
					continue;
				r.setTotalWaterCount(data[0]);
				r.setTotalMoneyCount(data[1]);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询指定月份指定建筑每天的用水分项情况
	 * 
	 * @param archID
	 *            建筑id
	 * @param queryYear
	 *            年份
	 * @return 结果集
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<WaterReportModel> getArcWaterByFenxiangCountEveryDay(String WaterStyle, int archID, int queryYear, int queryMonth)
			throws SQLException, ParseException
	{
		List<WaterReportModel> list = new ArrayList<WaterReportModel>(31);
		WaterReportModel rm = null;
		ArchitectureDao arcDao = new ArchitectureDao();
		if (archID == 0)
		{
			String schoolName = GetSystemInfo.getSchoolName();
			String sql = null;
			String waterFenXiang = getWMFenLeiName(WaterStyle);
			int MaxDay = 0; // 这个月的天数

			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(queryYear, queryMonth); // 查询月份有多少天

			for (int i = 1; i <= MaxDay; i++)
			{
				rm = new WaterReportModel();
				rm.setName(schoolName + " " + waterFenXiang);
				rm.setSelectMonth(queryMonth);
				rm.setSelectDay(i);
				rm.setTotalWaterCount(0);
				rm.setTotalMoneyCount(0);
				rm.setSelectYear(queryYear);
				list.add(rm);
			}

			sql = "select  SelectDay, sum(ZGross)as ZGross, sum(ZMoney)as ZMoney  from  T_ArcFenleiDayWater "
					+ " where  SelectYear= ? and SelectMonth= ? and Fenlei = ? " + "  group by  SelectDay order by SelectDay ";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, queryYear);
				pstmt.setInt(2, queryMonth);
				pstmt.setString(3, WaterStyle);

				rs = pstmt.executeQuery();

				Map<String, Float[]> map = new HashMap<String, Float[]>();
				while (rs.next())
				{
					map.put(rs.getInt("SelectDay") + "", new Float[]
					{ rs.getFloat("ZGross"), rs.getFloat("ZMoney") });
					/*
					 * if(rs.getInt("selectmonth") ==
					 * list.get(i).getSelectMonth()) {
					 * list.get(i).setTotalEnergyCount(rs.getFloat("gross"));
					 * list.get(i).setTotalMoneyCount(rs.getFloat("money")); }
					 */
				}
				WaterReportModel r = null;
				Float data[] = null;
				for (int i = 0; i < list.size(); i++)
				{
					r = list.get(i);
					data = map.get(r.getSelectDay() + "");
					if (data == null)
						continue;
					r.setTotalWaterCount(data[0]);
					r.setTotalMoneyCount(data[1]);
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		} else
		{
			String arcName = getArcName(archID);
			String sql = null;
			String waterFenXiang = getWMFenLeiName(WaterStyle);
			int MaxDay = 0; // 这个月的天数

			DateOperation dateop = new DateOperation();
			MaxDay = dateop.queryMaxDayinMonth(queryYear, queryMonth); // 查询月份有多少天

			for (int i = 1; i <= MaxDay; i++)
			{
				rm = new WaterReportModel();
				rm.setName(arcName + " " + waterFenXiang);
				rm.setSelectMonth(queryMonth);
				rm.setSelectDay(i);
				rm.setTotalWaterCount(0);
				rm.setTotalMoneyCount(0);
				rm.setSelectYear(queryYear);
				list.add(rm);
			}

			sql = "select  SelectDay, sum(ZGross)as ZGross, sum(ZMoney)as ZMoney  from  T_ArcFenleiDayWater "
					+ " where  SelectYear= ? and SelectMonth= ? and Fenlei = ? " + "	and Architecture_ID= ? group by  SelectDay order by SelectDay ";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, queryYear);
				pstmt.setInt(2, queryMonth);
				pstmt.setString(3, WaterStyle);
				pstmt.setInt(4, archID);

				rs = pstmt.executeQuery();

				Map<String, Float[]> map = new HashMap<String, Float[]>();
				while (rs.next())
				{
					map.put(rs.getInt("SelectDay") + "", new Float[]
					{ rs.getFloat("ZGross"), rs.getFloat("ZMoney") });
					/*
					 * if(rs.getInt("selectmonth") ==
					 * list.get(i).getSelectMonth()) {
					 * list.get(i).setTotalEnergyCount(rs.getFloat("gross"));
					 * list.get(i).setTotalMoneyCount(rs.getFloat("money")); }
					 */
				}
				WaterReportModel r = null;
				Float data[] = null;
				for (int i = 0; i < list.size(); i++)
				{
					r = list.get(i);
					data = map.get(r.getSelectDay() + "");
					if (data == null)
						continue;
					r.setTotalWaterCount(data[0]);
					r.setTotalMoneyCount(data[1]);
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, pstmt, rs);
			}
		}
		return list;
	}

	/***
	 * 查询获得指定部门下面的所有子部门指定年份的用水量和水费
	 * 
	 * @param groupID
	 *            查询的父部门ID
	 * @param queryYear
	 *            查询的年份
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getAllSonGroupCountByYear(int groupID, int queryYear) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();
		String groupName = getGroupName(groupID);
		String sql = null;
		if (groupName == null)
			return null;

		sql = "select a.partment_ID groupID,a.partment_name groupName,gross,money from (select partment_ID,"
				+ "partment_name from partment where partment_parent=?) a  left join "
				+ "(select partment_id,sum(zgross) gross,sum(zmoney) money from"
				+ " t_partmentdaywater where selectyear=? group by partment_ID) b on" + " a.partment_ID=b.partment_id order by gross";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupID);
			pstmt.setInt(2, queryYear);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();

				rm.setSelectYear(queryYear);
				rm.setGroupName(rs.getString("groupName"));
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/***
	 * 查询指定部门下面的所有子部门指定年月的用水量和水费
	 * 
	 * @param groupID
	 *            查询的父部门的ID
	 * @param queryYear
	 *            查询的年份
	 * @param queryMonth
	 *            查询的月份
	 * @return List<WaterReportModel> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterReportModel> getAllSonGroupCountByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		List<WaterReportModel> list = new LinkedList<WaterReportModel>();
		String groupName = getGroupName(groupID);
		String sql = null;
		if (groupName == null)
			return null;

		sql = "select a.partment_ID groupID,a.partment_name groupName,gross,money from (select partment_ID,"
				+ "partment_name from partment where partment_parent=?) a  left join "
				+ "(select partment_id,sum(zgross) gross,sum(zmoney) money from"
				+ " t_partmentdaywater where selectyear=? and selectMonth=? group by partment_ID) b on"
				+ " a.partment_ID=b.partment_id order by gross";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupID);
			pstmt.setInt(2, queryYear);
			pstmt.setInt(3, queryMonth);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterReportModel rm = new WaterReportModel();

				rm.setSelectYear(queryYear);
				rm.setSelectMonth(queryMonth);
				rm.setGroupName(rs.getString("groupName"));
				rm.setTotalWaterCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));

				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		return list;
	}

	/**
	 * 得到指定建筑下的所有水表数据
	 * 
	 * @param arcID
	 *            要查询的建筑ID
	 * @return List<WaterMeterMataData> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterMeterMataData> getWaterMeterByArc(int arcID) throws SQLException
	{
		List<WaterMeterMataData> list = new LinkedList<WaterMeterMataData>();
		String arcName = getArcName(arcID), sql = null;
		if (arcName == null)
			return null;

		sql = "select WaterMeter_ID from WaterMeter where ARCHITECTURE_ID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, arcID);

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				WaterMeterMataData amd = new WaterMeterMataData();
				amd.setWaterMeterID(rs.getInt("WaterMeter_ID"));
				list.add(amd);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 得到指定的ID水表的所有数据
	 * 
	 * @param amID
	 *            要查询的水表ID
	 * @return List<WaterMeterMataData> 查询结果集
	 * @throws SQLException
	 * 
	 */
	public List<WaterMeterMataData> getWaterMeterByID(int amID) throws SQLException
	{
		List<WaterMeterMataData> list = new LinkedList<WaterMeterMataData>();
		String sql = null;

		sql = "select VALUETIME,ZVALUEZY from ZWATERDATAS" + String.valueOf(amID);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				WaterMeterMataData amd = new WaterMeterMataData();
				amd.setWaterMeterID(amID);
				amd.setRecordTimeDate(rs.getTimestamp("VALUETIME"));
				amd.setValue(rs.getFloat("ZVALUEZY"));
				list.add(amd);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	public List<WaterReportModel> getWatermeterCountEveryDay(int watermeterID, Date start, Date end) throws SQLException
	{
		if (end.before(start))
			return null;

		List<WaterReportModel> list = null;

		String sql = "select (nvl(max(zValuezy),0)-nvl(min(zValuezy),0)) value," + "selectYear,selectMonth,selectDay from " + "(select zValuezy,"
				+ "to_char(ValueTime,'yyyy') as selectYear," + "to_char(ValueTime,'mm') as selectMonth," + "to_char(ValueTime,'dd') as selectDay "
				+ "from ZWATERDATAS" + String.valueOf(watermeterID) + " where ValueTime between " + "to_Date(?,'yyyy-mm-dd hh24:mi:ss') and "
				+ "to_Date(?,'yyyy-mm-dd hh24:mi:ss')) " + "group by selectYear,selectMonth,selectDay " + "order by selectYear,selectMonth,selectDay";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, df.format(start));
			ps.setString(2, df.format(end));
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<WaterReportModel>();

				WaterReportModel rm = new WaterReportModel();
				rm.setTotalWaterCount(rs.getFloat("value"));
				rm.setSelectYear(rs.getInt("selectYear"));
				rm.setSelectMonth(rs.getInt("selectMonth"));
				rm.setSelectDay(rs.getInt("selectDay"));

				list.add(rm);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<WaterMeterMataData> getWaterMeterBetween(int ammeterID, Date start, Date end) throws SQLException
	{
		float startValue = -1;
		float theValue = -1;
		// 获取要查询日期的前一个小时的最大值作为初始值
		String startValueSql = "select ValueTime,ZValueZY from ZWATERDATAS" + String.valueOf(ammeterID) + " " + "where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd')-1/24 and to_date(?,'yyyy-mm-dd') " + "order by ValueTime desc";

		Connection conn = null;
		PreparedStatement startValuepstmt = null;
		ResultSet startRs = null;
		try
		{
			conn = ConnDB.getConnection();
			startValuepstmt = conn.prepareStatement(startValueSql);
			startValuepstmt.setString(1, DateFormat.getDateInstance(DateFormat.DEFAULT).format(start));
			startValuepstmt.setString(2, DateFormat.getDateInstance(DateFormat.DEFAULT).format(start));
			startRs = startValuepstmt.executeQuery();
			if (startRs.next())
				startValue = startRs.getFloat("ZValueZY");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, startValuepstmt, startRs);
		}

		List<WaterMeterMataData> list = new LinkedList<WaterMeterMataData>();
		WaterMeterMataData amd = null;
		Timestamp ts = null;
		String sql = null;

		// 查询要查询的日起大田的所有数据，按升序排列
		sql = "select ValueTime,ZValueZY from ZWATERDATAS" + String.valueOf(ammeterID) + " where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')+1 " + "order by ValueTime";
		//System.out.println(sql);
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt = conn1.prepareStatement(sql);
			pstmt.setString(1, DateFormat.getDateInstance(DateFormat.DEFAULT).format(start));
			pstmt.setString(2, DateFormat.getDateInstance(DateFormat.DEFAULT).format(end));

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// 若初始值为-1 ，将当天的第一个值作为初始值。
				if (startValue == -1)
					startValue = rs.getFloat("ZValueZY");

				// 若list中为空说明
				if (list.isEmpty())
				{
					amd = new WaterMeterMataData();
					amd.setWaterMeterID(ammeterID);
					ts = rs.getTimestamp("ValueTime");
					theValue = rs.getFloat("ZValueZY");
					ts.setMinutes(0);
					ts.setSeconds(0);
					amd.setRecordTimeDate(ts);
					amd.setValue(theValue - startValue);
					list.add(amd);
				} else
				{
					ts = rs.getTimestamp("ValueTime");
					ts.setMinutes(0);
					ts.setSeconds(0);
					if (ts.getYear() == amd.getRecordTimeDate().getYear() && ts.getMonth() == amd.getRecordTimeDate().getMonth()
							&& ts.getDate() == amd.getRecordTimeDate().getDate() && ts.getHours() == amd.getRecordTimeDate().getHours()
							&& rs.getFloat("ZValueZY") > amd.getValue())
					{
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
					} else
					{
						amd = new WaterMeterMataData();
						amd.setWaterMeterID(ammeterID);
						amd.setRecordTimeDate(ts);
						startValue = theValue;
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
						list.add(amd);
					}
				}

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, pstmt, rs);
		}

		return list;
	}

	/**
	 * 查询得到指定电表指定时间段内（精确到秒）的原始数据（没有按小时聚合）
	 * 
	 * @return List<AmMeterMataData> 查询电表数据结果
	 * @throws SQLException
	 * 
	 */
	public List<WaterMeterMataData> getOriginalWatermeterDataBetween(int watermeterID, Date start, Date end) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (getWatermeterName(watermeterID) == null)
			return null;
		List<WaterMeterMataData> list = null;
		String sql = null;
		sql = "select ValueTime,ZValueZY from ZWATERDATAS" + String.valueOf(watermeterID) + " where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') " + "order by ValueTime";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			String startString = df.format(start);
			ps.setString(1, startString);
			String endString = df.format(end);
			ps.setString(2, endString);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<WaterMeterMataData>();

				WaterMeterMataData amd = new WaterMeterMataData();

				amd.setWaterMeterID(watermeterID);
				amd.setRecordTimeDate(rs.getTimestamp("ValueTime"));
				amd.setValue(rs.getFloat("ZValueZY"));

				list.add(amd);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	private String getWatermeterName(int watermeterID) throws SQLException
	{
		String name = null;
		String sql = "select watermeter_name from watermeter where watermeter_id = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, watermeterID);
			rs = ps.executeQuery();
			if (rs.next())
				name = rs.getString("watermeter_name");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return name;
	}

	/**
	 * 查询得到在线，离线和用水的水表的数量
	 * 
	 * @return Map<String, Integer> 查询结果
	 * @throws SQLException
	 * 
	 */
	public Map<String, Integer> getWaterMeterCount() throws SQLException
	{
		Map<String, Integer> count = new HashMap<String, Integer>();
		String sql = "select count(*) as offlineCount from WaterMeter where WaterMeter_Stat=0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count.put("离线水表", rs.getInt("offlineCount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		sql = "select count(*) as onlineCount from WaterMeter where WaterMeter_Stat=1";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				count.put("在线水表", rs1.getInt("onlineCount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1, rs1);
		}

		/*
		 * sql =
		 * "select count(*) as workingCount from WaterMeter where WaterMeter_Stat=2"
		 * ;
		 * 
		 * rs = ConnDB.executeQuery(sql);
		 * 
		 * if (rs.next()) { count.put("工作中水表", rs.getInt("workingCount")); }
		 * 
		 * close(pstmt, rs);
		 */

		return count;
	}

	// 获得总区域名称
	private String getSystmName() throws SQLException
	{
		String name = null;

		String sql = "select SYSTEMINFO_COMPLAYNAME theName from " + "SystemInfo where SYSTEMINFO_ID = 1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("theName");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return name;
	}

	/**
	 * 私有方法，用来检查部门ID是否有效
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * @return String 查询得到的部门名称
	 * @throws SQLException
	 * 
	 */
	private String getGroupName(int groupID) throws SQLException
	{
		String sql = "select Partment_NAME from Partment where Partment_ID=" + groupID;
		String groupName = null;
		// 改了

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next())
				groupName = rs.getString("Partment_NAME");

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		/*
		 * PreparedStatement pstmt = null; pstmt =
		 * ConnDB.getConnection().prepareStatement(sql); ResultSet rs = null; rs
		 * = pstmt.executeQuery(); if (rs.next()) groupName =
		 * rs.getString("Partment_NAME");
		 * 
		 * close(pstmt, rs);
		 */
		return groupName;
	}

	/**
	 * 私有方法，用来检查建筑ID是否有效
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * @return String 查询得到的建筑名称
	 * @throws SQLException
	 * 
	 */

	private String getAreaName(int areaID) throws SQLException
	{
		String sql = "select AREA_NAME from AREA where AREA_ID=" + areaID;
		String areaName = null;
		// 改了
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next())
				areaName = rs.getString("Area_NAME");

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// pstmt = ConnDB.getConnection().prepareStatement(sql);
		// rs = pstmt.executeQuery();
		// ;
		//
		// if (rs.next())
		// areaName = rs.getString("Area_NAME");
		//
		// close(pstmt, rs);
		return areaName;
	}

	private String getWMFenLeiName(String AmStyle)
	{
		String name = null;
		String sql = "Select * from DictionaryValue where Dictionary_ID= (select Dictionary_ID From Dictionary where Dictionary_Name='用水分项') and DICTIONARYVALUE_NUM=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, AmStyle);
			rs = ps.executeQuery();
			if (rs.next())
				name = rs.getString("DICTIONARYVALUE_VALUE");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return name;
	}

	private String getArcName(int arcID) throws SQLException
	{
		String sql = "select ARCHITECTURE_NAME from ARCHITECTURE where ARCHITECTURE_ID=" + arcID;
		String arcName = null;
		// 改了
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next())
				arcName = rs.getString("Architecture_NAME");

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt, rs);
		}
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		// pstmt = ConnDB.getConnection().prepareStatement(sql);
		// rs = pstmt.executeQuery();
		// ;
		//
		// if (rs.next())
		// arcName = rs.getString("Architecture_NAME");
		//
		// close(pstmt, rs);
		return arcName;
	}

	/**
	 * 关闭查询链接，回收资源
	 * 
	 * @throws SQLException
	 *             void
	 */
	private void close(PreparedStatement pstmt, ResultSet rs) throws SQLException
	{
		if (rs != null)
			rs.close();

		if (pstmt != null)
			pstmt.close();

	}

	/***
	 * 内部调用私有方法，用来判断查询数值结果是否为null，是的话就赋值为0
	 * 
	 * @param obj
	 *            判断的对象
	 * @return Integer 数值
	 */
	private Integer isNull(Object obj)
	{
		if (obj == null)
			return 0;
		else
		{
			return (Integer) obj;
		}
	}

	private int getDayCount(int year, int month)
	{
		Calendar a = Calendar.getInstance();  
		a.set(Calendar.YEAR, year);  
		a.set(Calendar.MONTH, month - 1);  
		a.set(Calendar.DATE, 1);  
		a.roll(Calendar.DATE, -1);  
				return  a.get(Calendar.DATE); 
				
	}
}
