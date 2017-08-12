package com.sf.energy.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.sf.energy.manager.current.model.CurrentManagerModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.util.ConnDB;

/**
 * 集中监测所有到的统计方法
 * 
 * @author yanhao
 *
 */
public class CurrentMeasureDao
{
	DecimalFormat df = new DecimalFormat("####.00");
	NumberFormat nf = NumberFormat.getPercentInstance();// 小数百分号
	int total;

	/**
	 * 获取某建筑下面纳入计量的在线电表的个数
	 * 
	 * @param archID
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getArchStateAmm(int archID) throws SQLException
	{
		int onlineCount = 0;
		int offlineCount = 0;
		int workCount = 0;
		int totalCount = 0;
		nf.setMinimumFractionDigits(2);// 保留2分小数
		Map<String, String> map = new HashMap<String, String>();
		String sql = "Select AMMETER_STAT from Ammeter where Architecture_ID=" + archID + " and ISCOMPUTATION=1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (rs.getInt(1) == 1)
				{
					onlineCount++;
				}
				if (rs.getInt(1) == 0)
				{
					offlineCount++;
				}
				if (rs.getInt(1) == 2)
				{
					workCount++;
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		totalCount = onlineCount + offlineCount + workCount;
		// int转string的三种方法：1》String.valueOf(i)2》 Integer.toString(i)3》 i+""
		map.put("onlineCount", onlineCount + "");
		map.put("offlineCount", offlineCount + "");
		map.put("workCount", workCount + "");
		map.put("totalCount", totalCount + "");
		if (totalCount == 0)
		{
			map.put("ratio", "0.00%");
		} else
		{
			map.put("ratio", nf.format(onlineCount * 1.0 / totalCount));
		}

		return map;
	}

	/**
	 * 获取某区域下面纳入计量的在线电表的个数
	 * 
	 * @param areaID
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAreaStateAmm(int areaID) throws SQLException
	{
		int onlineCount = 0;
		int offlineCount = 0;
		int workCount = 0;
		int totalCount = 0;
		nf.setMinimumFractionDigits(2);// 保留2分小数
		Map<String, String> map = new HashMap<String, String>();
		String sql = "Select AMMETER_STAT from Ammeter where Area_ID=" + areaID + " and ISCOMPUTATION=1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (rs.getInt(1) == 1)
				{
					onlineCount++;
				}
				if (rs.getInt(1) == 0)
				{
					offlineCount++;
				}
				if (rs.getInt(1) == 2)
				{
					workCount++;
				}
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		totalCount = onlineCount + offlineCount + workCount;
		map.put("onlineCount", onlineCount + "");
		map.put("offlineCount", offlineCount + "");
		map.put("workCount", workCount + "");
		map.put("totalCount", totalCount + "");
		if (totalCount == 0)
		{
			map.put("ratio", "0.00%");
		} else
		{
			map.put("ratio", nf.format(onlineCount * 1.0 / totalCount));
		}
		return map;
	}

	/**
	 * 获取某建筑的当日用电和电费
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getArchDayAmmValue(int archID, int selectYear, int selectMonth, int selectDay) throws SQLException
	{
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_ArcDayAm where Architecture_ID=" + archID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth + " and selectDay=" + selectDay;
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("ZGross", rs.getString(1));
				map.put("ZMoney", rs.getString(2));
			} else
			{
				map.put("ZGross", "0");
				map.put("ZMoney", "0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return map;
	}

	/**
	 * 获取某建筑的当月用电和电费
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getArchMonthAmmValue(int archID, int selectYear, int selectMonth) throws SQLException
	{
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_MonthAm where Architecture_ID=" + archID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth;
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("ZGross", rs.getString(1));
				map.put("ZMoney", rs.getString(2));
			} else
			{
				map.put("ZGross", "0");
				map.put("ZMoney", "0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return map;
	}

	/**
	 * 获取某区域的当日用电和电费
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAreaDayAmmValue(int areaID, int selectYear, int selectMonth, int selectDay) throws SQLException
	{
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_ArcDayAm where Area_ID=" + areaID + " and SelectYear="
				+ selectYear + " and selectMonth=" + selectMonth + " and selectDay=" + selectDay;
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("ZGross", rs.getString(1));
				map.put("ZMoney", rs.getString(2));
			} else
			{
				map.put("ZGross", "0");
				map.put("ZMoney", "0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 获取某区域的当月用电和电费
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAreaMonthAmmValue(int archID, int selectYear, int selectMonth) throws SQLException
	{
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_MonthAm where Area_ID=" + archID + " and SelectYear="
				+ selectYear + " and selectMonth=" + selectMonth;
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("ZGross", rs.getString(1));
				map.put("ZMoney", rs.getString(2));
			} else
			{
				map.put("ZGross", "0");
				map.put("ZMoney", "0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// ResultSet rs = ps.executeQuery();
		// if (rs.next())
		// {
		// map.put("ZGross", rs.getString(1));
		// map.put("ZMoney", rs.getString(2));
		// } else
		// {
		// map.put("ZGross", "0");
		// map.put("ZMoney", "0");
		// }
		// if (rs != null)
		// rs.close();
		//
		// if (ps != null)
		// ps.close();
		return map;
	}

	/**
	 * 查询建筑所有楼层的所有电表
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CurrentManagerModel> getWhereSelectedArch(int archID, int selectYear, int selectMonth, int selectDay, int pageCount,
			int pageIndex) throws SQLException
	{
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		CurrentManagerModel cm = null;
		String sql = "select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from AmMeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select AmMeter_ID,ZGross from T_DayAm where SelectYear="
				+ selectYear
				+ " and SelectMonth="
				+ selectMonth
				+ " and SelectDay="
				+ selectDay
				+ ") d on d.AmMeter_ID=a.AmMeter_ID  where a.Architecture_ID=" + archID + " order by FLOOR,a.AmMeter_Name";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			// 当日用电
			while (rs.next() && (count > 0))
			{
				cm = new CurrentManagerModel();
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setAmmID(rs.getInt("Ammeter_ID"));
				cm.setAmmName(rs.getString("Ammeter_Name"));
				cm.setAmmState(rs.getInt("Ammeter_Stat"));
				cm.setCurPower(rs.getFloat("CurPower"));
				cm.setDayGross(rs.getFloat("DayGross"));
				cm.setAmmFloor(rs.getInt("Floor"));
				list.add(cm);

				count--;
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

	/**
	 * 点击楼层，查询该楼层所有电表
	 * 
	 * @param archID
	 * @param floor
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CurrentManagerModel> getWhereSelectedFloor(int archID, int floor, int selectYear, int selectMonth, int selectDay, int pageCount,
			int pageIndex) throws SQLException
	{
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		CurrentManagerModel cm = null;
		String sql = "select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from AmMeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select AmMeter_ID,ZGross from T_DayAm where SelectYear="
				+ selectYear
				+ " and SelectMonth="
				+ selectMonth
				+ " and SelectDay="
				+ selectDay
				+ ") d on d.AmMeter_ID=a.AmMeter_ID  where a.Architecture_ID=" + archID + " and Floor=" + floor + " order by a.AmMeter_Name";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			// 当日用电
			while (rs.next() && (count > 0))
			{
				cm = new CurrentManagerModel();
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setAmmID(rs.getInt("Ammeter_ID"));
				cm.setAmmName(rs.getString("Ammeter_Name"));
				cm.setAmmState(rs.getInt("Ammeter_Stat"));
				cm.setCurPower(rs.getFloat("CurPower"));
				cm.setDayGross(rs.getFloat("DayGross"));
				cm.setAmmFloor(rs.getInt("Floor"));
				list.add(cm);

				count--;
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

	/**
	 * 获取某电表的当日用电和电费
	 * 
	 * @param ammID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAmmDayValue(int ammID, int selectYear, int selectMonth, int selectDay) throws SQLException
	{
		String sql = "Select ZGross,Price_Value from T_DayAm where Ammeter_ID=" + ammID + " and SelectYear=" + selectYear + " and selectMonth="
				+ selectMonth + " and selectDay=" + selectDay;
		Map<String, String> map = new HashMap<String, String>();
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
				map.put("ZGross", rs.getString("ZGross"));
				map.put("ZMoney", (rs.getFloat("Price_Value") * rs.getFloat("ZGross")) + "");
			} else
			{
				map.put("ZGross", "0.0");
				map.put("ZMoney", "0.0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 获取某电表的当月用电和电费
	 * 
	 * @param ammID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAmmMonthValue(int ammID, int selectYear, int selectMonth) throws SQLException
	{
		String sql = "Select ZGross,ZMoney from T_MonthAm where Ammeter_ID=" + ammID + " and SelectYear=" + selectYear + " and selectMonth="
				+ selectMonth;
		Map<String, String> map = new HashMap<String, String>();
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
				map.put("ZGross", rs.getString("ZGross"));
				map.put("ZMoney", rs.getString("ZMoney"));
			} else
			{
				map.put("ZGross", "0.0");
				map.put("ZMoney", "0.0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 查询电表的当前度数和功率
	 * 
	 * @param ammID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAmmValue(int ammID, int selectYear, int selectMonth, int selectDay) throws SQLException
	{
		String monthString = "";
		String dayString = "";
		if (selectMonth < 10)
		{
			monthString = "0" + "" + selectMonth;
		} else
		{
			monthString = "" + selectMonth;
		}

		if (selectDay < 10)
		{
			dayString = "0" + "" + selectDay;
		} else
		{
			dayString = "" + selectDay;
		}

		String sql = "Select c.LASTTIME LastTime,b.ZValueZY as Ammeter_Value,a.CurPower as Ammeter_Power" + " from Ammeter a,ZAMDATAS"
				+ String.valueOf(ammID) + " b,(select max(ValueTime) as LastTime" + " from ZAMDATAS" + String.valueOf(ammID)
				+ " where to_char(valuetime,'yyyy-mm-dd')='" + selectYear + "-" + monthString + "-" + dayString + "') c" + " where a.Ammeter_ID="
				+ ammID + " and b.VALUETIME=c.LASTTIME";
		Map<String, String> map = new HashMap<String, String>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			if (rs.next())
			{
				if (rs.getString("LastTime") != null)
				{
					map.put("ammID", ammID + "");
					map.put("ammLastTime", rs.getString("LastTime"));
					map.put("ammValue", rs.getString("Ammeter_Value"));
					map.put("ammPower", rs.getString("Ammeter_Power"));
				} else
				{
					map.put("ammID", ammID + "");
					map.put("ammlastTime", new Date().toLocaleString());
					map.put("ammValue", "0.0");
					map.put("ammPower", "0.0");
				}

			} else
			{
				map.put("ammID", ammID + "");
				map.put("ammlastTime", new Date().toLocaleString());
				map.put("ammValue", "0.0");
				map.put("ammPower", "0.0");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 查询出所有区域的当天用电，当月用电，在线电表 （集中监测，点击学校事件）
	 * 
	 * @param sortName
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CurrentManagerModel> listAllAreaData(String sortName, String order, int year, int month, int day) throws SQLException
	{
		nf.setMinimumFractionDigits(2);// 保留2分小数
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();
		CurrentManagerModel cm = null;
		String sql = "select * from ("
				+ "select "
				+ "								t.Area_ID,r.Area_Name, "
				+ "								sum(case isComputation when 1 then DayGross else 0 end) DayAmGross, "
				+ "								sum(case isComputation when 1 then DayGross*Price_Value else 0 end) DayAmMoney, "
				+ "								sum(case isComputation when 1 then MonthGross else 0 end) MonthAmGross, "
				+ "								sum(case isComputation when 1 then MonthGross*Price_Value else 0 end) MonthAmMoney, "
				+ "								count(t.AmMeter_ID) MeterCount, "
				+ "								nvl((select count(x.AmMeter_ID) from AmMeter x where x.Area_ID=t.Area_ID and x.AmMeter_Stat>0),0) as UsingMeterCount, "
				+ "			(nvl((select count(x.AmMeter_ID) from AmMeter x where x.Area_ID=t.Area_ID and x.AmMeter_Stat>0),0)/count(t.AmMeter_ID) )as onlineRatio "

				+ "						from " + "						(" + "								select a.AmMeter_ID, " + "											 a.Area_ID, " + "											 a.isComputation, "
				+ "											 p.Price_Value, " + "											 nvl(d.ZGross,0) DayGross, " + "											 nvl(m.ZGross,0) MonthGross "
				+ "								from " + "									AmMeter a " + "													left join Price p on p.Price_ID=a.Price_ID "
				+ "													left join (select AmMeter_ID,ZGross from T_DayAm where SelectYear=" + year + "  and SelectMonth=" + month
				+ " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID "
				+ "													left join (select AmMeter_ID,ZGross from T_MonthAm where SelectYear=" + year + " and SelectMonth=" + month
				+ " ) m on m.AmMeter_ID=a.AmMeter_ID " + "						) t " + "													left join Area r on r.Area_ID=t.Area_ID "
				+ " group by t.Area_ID,r.Area_Name " + ") o ";

		if (order.equals("asc"))// 升序
		{
			sql += " order by " + sortName + " asc";
		} else if (order.equals("desc"))
		{
			sql += " order by " + sortName + " desc";
		} else
		{
			sql += " order by " + sortName;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())// 只要有区域，数据一直存在
			{
				cm = new CurrentManagerModel();
				cm.setAreaID(rs.getInt("Area_ID"));
				cm.setAreaName(rs.getString("Area_Name"));
				cm.setDayGross(rs.getFloat("DayAmGross"));
				cm.setDayMoney(rs.getFloat("DayAmMoney"));
				cm.setMonthGross(rs.getFloat("MonthAmGross"));
				cm.setMonthMoney(rs.getFloat("MonthAmMoney"));
				cm.setTotalAmm(rs.getInt("MeterCount"));
				cm.setOnlineAmm(rs.getInt("UsingMeterCount"));
				cm.setOnlineRatio(nf.format(rs.getFloat("onlineRatio")));
				list.add(cm);
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

	/**
	 * 查询出所有建筑的当天用电，当月用电，在线电表 （集中监测，点击区域事件）
	 * 
	 * @param sortName
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CurrentManagerModel> listAllArchData(String sortName, String order, int areaID, int year, int month, int day, int pageCount,
			int pageIndex) throws SQLException
	{
		nf.setMinimumFractionDigits(2);// 保留2分小数
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();
		CurrentManagerModel cm = null;
		String sql = "select * from "
				+ "  ( "
				+ "					select  "
				+ "								t.Architecture_ID, "
				+ "								  r.Architecture_Name, "
				+ "								  sum(case isComputation when 1 then DayGross else 0 end) DayAmGross, "
				+ "									sum(case isComputation when 1 then DayGross*Price_Value else 0 end) DayAmMoney, "
				+ "									sum(case isComputation when 1 then MonthGross else 0 end) MonthAmGross, "
				+ "									sum(case isComputation when 1 then MonthGross*Price_Value else 0 end) MonthAmMoney, "
				+ "									count(t.AmMeter_ID) MeterCount, "
				+ "									nvl((select count(x.AmMeter_ID) from AmMeter  x where x.Architecture_ID=t.Architecture_ID and x.AmMeter_Stat>0),0) as UsingMeterCount,  "
				+ "      	(nvl((select count(x.AmMeter_ID) from AmMeter  x where x.Architecture_ID=t.Architecture_ID and x.AmMeter_Stat>0),0)/count(t.AmMeter_ID) )as onlineRatio "
				+ "					from ( " + "											select  a.AmMeter_ID, " + "													a.Architecture_ID, " + "													a.isComputation, "
				+ "													p.Price_Value, " + "													nvl(d.ZGross,0) DayGross, " + "													nvl(m.ZGross,0) MonthGross  "
				+ "        from  " + "											AmMeter a left join Price p on p.Price_ID=a.Price_ID  "
				+ "																left join (select AmMeter_ID,ZGross from T_DayAm where SelectYear=" + year + " and SelectMonth=" + month
				+ " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID  "
				+ "																left join (select AmMeter_ID,ZGross from T_MonthAm where SelectYear=" + year + " and SelectMonth=" + month
				+ ") m on m.AmMeter_ID=a.AmMeter_ID  " + "        where a.Area_ID= " + areaID + "							 ) t  "
				+ "        left join Architecture r on r.Architecture_ID=t.Architecture_ID  "
				+ "        group by t.Architecture_ID,r.Architecture_Name  " + "	)  ";

		if (order.equals("asc"))// 升序
		{
			sql += " order by " + sortName + " asc";
		} else if (order.equals("desc"))
		{
			sql += " order by " + sortName + " desc";
		} else
		{
			sql += " order by " + sortName;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;
			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))// 只要有区域，数据一直存在
			{
				cm = new CurrentManagerModel();
				cm.setArchID(rs.getInt("Architecture_ID"));
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setDayGross(rs.getFloat("DayAmGross"));
				cm.setDayMoney(rs.getFloat("DayAmMoney"));
				cm.setMonthGross(rs.getFloat("MonthAmGross"));
				cm.setMonthMoney(rs.getFloat("MonthAmMoney"));
				cm.setTotalAmm(rs.getInt("MeterCount"));
				cm.setOnlineAmm(rs.getInt("UsingMeterCount"));
				cm.setOnlineRatio(nf.format(rs.getFloat("onlineRatio")));
				list.add(cm);

				count--;
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

	/**
	 * 集抄电表以后修改Ammeter表
	 * 
	 * @param value
	 *            抄表示数
	 * @param power
	 *            当前功率
	 * @param time
	 *            最后通讯时间
	 * @param ammID
	 *            电表ID
	 * @return
	 * @throws SQLException
	 */
	public boolean insertAfterCommSan(double power, String time, int ammID) throws SQLException
	{
		boolean b;
		AmmeterDao ad = new AmmeterDao();
		AmmeterModel am = new AmmeterModel();

		// 其它值不修改
		am = ad.queryByID(ammID);
		// 修改最后通讯时间和当前功率
		am.setCurPower(power);
		am.setLastTime(time);
		b = ad.modify(am);

		return b;

	}

	/**
	 * 集抄电表以后修改AmmeterDatas表
	 * 
	 * @param value
	 * @param time
	 * @param ammID
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public boolean insertAfterCommDan(double value, String time, int ammID,double beilv) throws SQLException, ParseException
	{
		String sysdate=time;
		String sqldate = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sqldate);
			rs1 = ps1.executeQuery();
			if(rs1.next()){
				sysdate = rs1.getString(1);
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}
		//检验数据
		double zValueZY=-1;
		String valueTime = time;
		//查询当前时间前最近一条数据信息
		String sqldata1 = "select ZValueZY,to_char(ValueTime,'yyyy-MM-dd hh24:mi:ss')  from ( select * from ( select * from ZAMDATAS" + String.valueOf(ammID)+" "
				+ "where ValueTime < to_date('"
				+ sysdate
				+ "','yyyy-mm-dd hh24:mi:ss')  order by ValueTime desc) where rownum =1 order by rownum asc) a";
		Connection conn8=null;
		PreparedStatement ps8 =null;
		ResultSet rs8 = null;
		try
		{
			conn8 = ConnDB.getConnection();
			ps8 = conn8.prepareStatement(sqldata1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs8 = ps8.executeQuery();			
			if (rs8.next())
			{
				zValueZY = rs8.getDouble(1);
				valueTime = rs8.getString(2);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn8, ps8, rs8);
		}
		int days = DateDiff(valueTime, sysdate);
		//1、判断数据是否突增
		if ((value < zValueZY)||((zValueZY!=-1)&&((value-zValueZY)/beilv/days>1000)))
		{
			String sqldata2 = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
					+ ammID
					+ ",to_date('"
					+ sysdate
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ value
					+ ",2)";
			PreparedStatement ps4 = null;
			Connection conn4 = null;
			try
			{
				conn4 = ConnDB.getConnection();
				ps4 = conn4.prepareStatement(sqldata2);
				ps4.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn4, ps4);
			}
			return false;
		}
		zValueZY = -1;
		//2、判断主键是否重复
		//对cnt进行判定
		int cnt=0;
		String sqldata3 = "select count(ValueTime)  from ZAMDATAS"+String.valueOf(ammID)+" "
				+ "where ValueTime = to_date('" + sysdate + "','yyyy-mm-dd hh24:mi:ss')";
		Connection conn6=null;
		PreparedStatement ps6 =null;
		ResultSet rs6 = null;

		try
		{
			conn6 = ConnDB.getConnection();
			ps6 = conn6.prepareStatement(sqldata3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs6 = ps6.executeQuery();			
			if (rs6.next())
			{
				cnt = rs6.getInt(1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn6, ps6, rs6);
		}
		
		if (cnt > 0)
		{
			String sqldata4 = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
					+ ammID
					+ ",to_date('"
					+ sysdate
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ value
					+ ",3)";
			PreparedStatement ps5 = null;
			Connection conn5 = null;
			try
			{
				conn5=ConnDB.getConnection();
				ps5 = conn5.prepareStatement(sqldata4);
				ps5.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn5, ps5);
			}
			return false;
		}
		// 新增AmmeterDatas中的数据
		String sql = "insert into ZAMDATAS" + String.valueOf(ammID) + "(ValueTime,ZValueZY) values(to_date('" + time
				+ "','yyyy-mm-dd hh24:mi:ss') , round(" + value + ", 2) )";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			b = (psmt.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, psmt, rs);
		}

		return b;

	}
	private int DateDiff(String fromDate, String toDate)
			throws ParseException {
			int days = 0;
			 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date from = df.parse(fromDate);
			Date to = df.parse(toDate);
			days = (int) Math.abs((to.getTime() - from.getTime())
			 / (24*60 * 60 * 1000)) + 1;
			 return days;
	}
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

}
