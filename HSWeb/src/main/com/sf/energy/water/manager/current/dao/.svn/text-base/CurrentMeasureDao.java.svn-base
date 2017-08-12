package com.sf.energy.water.manager.current.dao;

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
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.configuration.Configuration;
import com.sf.energy.water.manager.current.model.CurrentManagerModel;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.util.ConnDB;

/**
 * 集中监测所有到的统计方法
 * 
 * @author yanhao
 *
 */
public class CurrentMeasureDao
{
	DecimalFormat df = new DecimalFormat("###0.00");
	NumberFormat nf = NumberFormat.getPercentInstance();// 小数百分号
	int total;

	/**
	 * 获取某建筑下面纳入计量的在线水表的个数
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
		String sql = "Select Watermeter_STAT from Watermeter where Architecture_ID=" + archID + " and ISCOMPUTATION=1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
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
	 * 获取某区域下面纳入计量的在线水表的个数
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
		String sql = "Select Watermeter_STAT from Watermeter where Area_ID=" + areaID + " and ISCOMPUTATION=1";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
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
	 * 获取某建筑的当日用水和水费
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
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_ArcDayWater where Architecture_ID=" + archID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth + " and selectDay=" + selectDay;
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
	 * 获取某建筑的当月用水和水费
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
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_MonthWater where Architecture_ID=" + archID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth;
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
	 * 获取某区域的当日用水和水费
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
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_ArcDayWater where Area_ID=" + areaID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth + " and selectDay=" + selectDay;
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
		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery(); if (rs.next()) { map.put("ZGross",
		 * rs.getString(1)); map.put("ZMoney", rs.getString(2)); } else {
		 * map.put("ZGross", "0"); map.put("ZMoney", "0"); } if (rs != null)
		 * rs.close();
		 * 
		 * if (ps != null) ps.close();
		 */
		return map;
	}

	/**
	 * 获取某区域的当月用水和水费
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
		String sql = "Select nvl(sum(ZGross),0) as ZGross,nvl(sum(ZMoney),0) as ZMoney from T_MonthWater where Area_ID=" + archID
				+ " and SelectYear=" + selectYear + " and selectMonth=" + selectMonth;
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

		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery(); if (rs.next()) { map.put("ZGross",
		 * rs.getString(1)); map.put("ZMoney", rs.getString(2)); } else {
		 * map.put("ZGross", "0"); map.put("ZMoney", "0"); } if (rs != null)
		 * rs.close();
		 * 
		 * if (ps != null) ps.close();
		 */
		return map;
	}

	/**
	 * 查询建筑所有楼层的所有水表
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
		String sql = "select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from Watermeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select Watermeter_ID,ZGross from T_DayWater where SelectYear="
				+ selectYear
				+ " and SelectMonth="
				+ selectMonth
				+ " and SelectDay="
				+ selectDay
				+ ") d on d.Watermeter_ID=a.Watermeter_ID  where a.Architecture_ID=" + archID + " order by FLOOR";
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

			// 当日用水
			while (rs.next() && (count > 0))
			{
				String sqlmeterCount = "select ZValueZY from ZWATERDATAS" + rs.getInt("Watermeter_ID")
						+ " where rownum = 1  order by valuetime desc ";

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				float currentValue = 0;
				try
				{
					ps2 = conn.prepareStatement(sqlmeterCount);
					rs2 = ps2.executeQuery();
					
					while (rs2.next())
					{
						currentValue = rs2.getFloat("ZValueZY");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps2, rs2);
				}
				cm = new CurrentManagerModel();
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setAmmID(rs.getInt("Watermeter_ID"));
				cm.setAmmName(rs.getString("Watermeter_Name"));
				cm.setAmmState(rs.getInt("Watermeter_Stat"));
				cm.setDayGross(rs.getFloat("DayGross"));
				cm.setCurrentValue(currentValue);
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
	 * 查询建筑所有楼层的所有水表
	 * 
	 * @param archID
	 * @param selectYear
	 * @param selectMonth
	 * @param selectDay
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CurrentManagerModel> getWhereSelectedBigSchool(int watermeterid,int selectYear, int selectMonth, int selectDay, int pageCount,
			int pageIndex,String order) throws SQLException
	{
		ArrayList<CurrentManagerModel> list = new ArrayList<CurrentManagerModel>();

		CurrentManagerModel cm = null;
		String sql ="";
		if(watermeterid==0){
			sql="select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from V_Watermeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select Watermeter_ID,ZGross from T_DayWater where SelectYear="
					+ selectYear
					+ " and SelectMonth="
					+ selectMonth
					+ " and SelectDay="
					+ selectDay
					+ ") d on d.Watermeter_ID=a.Watermeter_ID  where a.IsBig=1 "+order;
		}else{
			sql="select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from V_Watermeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select Watermeter_ID,ZGross from T_DayWater where SelectYear="
					+ selectYear
					+ " and SelectMonth="
					+ selectMonth
					+ " and SelectDay="
					+ selectDay
					+ ") d on d.Watermeter_ID=a.Watermeter_ID  where a.IsBig=1 and (a.watermeter_id="+watermeterid+" or a.parent_ID="+watermeterid+") "+order;
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
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			// 当日用水
			while (rs.next() && (count > 0))
			{
				String sqlmeterCount = "select * from (select nvl(ZValueZY,0) ZValueZY,nvl(CurrentFlow,0) CurrentFlow, nvl(to_char(valuetime,'yyyy-mm-dd hh24:mi:ss'),'2001-01-01 00:00:00') valuetime from ZWATERDATAS" + rs.getInt("Watermeter_ID")
						+ " order by valuetime desc )  where rownum = 1  ";

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				float currentValue = 0;
				float currentFlow=0;
				String lastDataTime="2001-01-01 00:00:00";
				try
				{
					ps2 = conn.prepareStatement(sqlmeterCount);
					rs2 = ps2.executeQuery();
					
					while (rs2.next())
					{
						currentValue = rs2.getFloat("ZValueZY");
						currentFlow=rs2.getFloat("CurrentFlow");
						lastDataTime=rs2.getString("valuetime");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps2, rs2);
				}
				cm = new CurrentManagerModel();
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setAmmID(rs.getInt("Watermeter_ID"));
				cm.setAmmName(rs.getString("Watermeter_Name"));
				cm.setAmmState(rs.getInt("Watermeter_Stat"));
				cm.setDayGross(rs.getFloat("DayGross"));
				cm.setCurrentValue(currentValue);
				cm.setCurPower(currentFlow);
				cm.setLastDataTime(lastDataTime);
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
	 * 点击楼层，查询该楼层所有水表
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
		String sql = "select a.*,b.Architecture_Name, nvl(d.ZGross,0) DayGross from Watermeter a left join Architecture b  on a.Architecture_ID= b.Architecture_ID  left join (select Watermeter_ID,ZGross from T_DayWater where SelectYear="
				+ selectYear
				+ " and SelectMonth="
				+ selectMonth
				+ " and SelectDay="
				+ selectDay
				+ ") d on d.Watermeter_ID=a.Watermeter_ID  where a.Architecture_ID=" + archID + " and Floor=" + floor + " order by FLOOR";
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

			// 当日用水
			while (rs.next() && (count > 0))
			{
				String sqlmeterCount = "select ZValueZY from ZWATERDATAS" + rs.getInt("Watermeter_ID")
						+ " where rownum = 1  order by valuetime desc ";

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				float currentValue = 0;
				try
				{
					ps2 = conn.prepareStatement(sqlmeterCount);
					rs2 = ps2.executeQuery();
					
					while (rs2.next())
					{
						currentValue = rs2.getFloat("ZValueZY");
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps2, rs2);
				}
				cm = new CurrentManagerModel();
				cm.setArchName(rs.getString("Architecture_Name"));
				cm.setAmmID(rs.getInt("Watermeter_ID"));
				cm.setAmmName(rs.getString("Watermeter_Name"));
				cm.setAmmState(rs.getInt("Watermeter_Stat"));

				cm.setDayGross(rs.getFloat("DayGross"));
				cm.setCurrentValue(currentValue);
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
	 * 获取某水表的当日用水和水费
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
		String sql = "Select ZGross,Price_Value from T_DayWater where Watermeter_ID=" + ammID + " and SelectYear=" + selectYear + " and selectMonth="
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
	 * 获取某水表的当月用水和水费
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
		String sql = "Select ZGross,ZMoney from T_MonthWater where Watermeter_ID=" + ammID + " and SelectYear=" + selectYear + " and selectMonth="
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

		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery(); if (rs.next()) { map.put("ZGross",
		 * rs.getString("ZGross")); map.put("ZMoney", rs.getString("ZMoney")); }
		 * else { map.put("ZGross", "0.0"); map.put("ZMoney", "0.0"); } if (rs
		 * != null) rs.close();
		 * 
		 * if (ps != null) ps.close();
		 */
		return map;
	}

	/**
	 * 查询水表的当前度数和功率
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
		String sql = "Select max(Watermeter.Watermeter_ID) as Watermeter_ID, " + "max(ZWATERDATAS" + String.valueOf(ammID)
				+ ".ValueTime) as LastTime, " + "max(ZWATERDATAS" + String.valueOf(ammID) + ".ZValueZY) as Watermeter_Value   from Watermeter  "
				+ "inner join ZWATERDATAS" + String.valueOf(ammID)
				+ "  on 1=1 "
				// +
				// "on Watermeter.Watermeter_ID=ZWATERDATAS"+String.valueOf(ammID)+".Watermeter_ID "
				+ "where " + " to_char(ZWATERDATAS" + String.valueOf(ammID) + ".valuetime,'yyyy-mm-dd')='" + selectYear + "-" + monthString + "-"
				+ dayString + "'";
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
				if (rs.getString("Watermeter_ID") != null)
				{
					map.put("ammID", rs.getString("Watermeter_ID"));
					map.put("ammLastTime", rs.getString("LastTime"));
					map.put("ammValue", rs.getString("Watermeter_Value"));
				} else
				{
					map.put("ammID", ammID + "");
					map.put("ammlastTime", new Date().toLocaleString());
					map.put("ammValue", "0.0");
				}

			} else
			{
				map.put("ammID", ammID + "");
				map.put("ammlastTime", " ");
				map.put("ammValue", "0.0");

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery(); if (rs.next()) { if
		 * (rs.getString("Watermeter_ID") != null) { map.put("ammID",
		 * rs.getString("Watermeter_ID")); map.put("ammLastTime",
		 * rs.getString("LastTime")); map.put("ammValue",
		 * rs.getString("Watermeter_Value")); } else { map.put("ammID", ammID +
		 * ""); map.put("ammlastTime", new Date().toLocaleString());
		 * map.put("ammValue", "0.0"); }
		 * 
		 * } else { map.put("ammID", ammID + ""); map.put("ammlastTime", " ");
		 * map.put("ammValue", "0.0");
		 * 
		 * } if (rs != null) rs.close();
		 * 
		 * if (ps != null) ps.close();
		 */
		return map;
	}

	/**
	 * 查询出所有区域的当天用水，当月用水，在线水表 （集中监测，点击学校事件）
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
				+ "								count(t.WaterMeter_ID) MeterCount, "
				+ "								nvl((select count(x.WaterMeter_ID) from WaterMeter x where x.Area_ID=t.Area_ID and x.WaterMeter_Stat>0),0) as UsingMeterCount, "
				+ "			(nvl((select count(x.WaterMeter_ID) from WaterMeter x where x.Area_ID=t.Area_ID and x.WaterMeter_Stat>0),0)/count(t.WaterMeter_ID) )as onlineRatio "

				+ "						from " + "						(" + "								select a.WaterMeter_ID, " + "											 a.Area_ID, " + "											 a.isComputation, "
				+ "											 p.Price_Value, " + "											 nvl(d.ZGross,0) DayGross, " + "											 nvl(m.ZGross,0) MonthGross "
				+ "								from " + "									WaterMeter a " + "													left join Price p on p.Price_ID=a.Price_ID "
				+ "													left join (select WaterMeter_ID,ZGross from T_DayWater where SelectYear=" + year + "  and SelectMonth=" + month
				+ " and SelectDay=" + day + ") d on d.WaterMeter_ID=a.WaterMeter_ID "
				+ "													left join (select WaterMeter_ID,ZGross from T_MonthWater where SelectYear=" + year + " and SelectMonth=" + month
				+ " ) m on m.WaterMeter_ID=a.WaterMeter_ID " + "						) t " + "													left join Area r on r.Area_ID=t.Area_ID "
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
		// System.out.println(sql);
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
	 * 查询出所有建筑的当天用水，当月用水，在线水表 （集中监测，点击区域事件）
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
				+ "								    sum(case isComputation when 1 then DayGross else 0 end) DayAmGross, "
				+ "									sum(case isComputation when 1 then DayGross*Price_Value else 0 end) DayAmMoney, "
				+ "									sum(case isComputation when 1 then MonthGross else 0 end) MonthAmGross, "
				+ "									sum(case isComputation when 1 then MonthGross*Price_Value else 0 end) MonthAmMoney, "
				+ "									count(t.WaterMeter_ID) MeterCount, "
				+ "									nvl((select count(x.WaterMeter_ID) from WaterMeter  x where x.Architecture_ID=t.Architecture_ID and x.WaterMeter_Stat>0),0) as UsingMeterCount,  "
				+ "      	(nvl((select count(x.WaterMeter_ID) from WaterMeter  x where x.Architecture_ID=t.Architecture_ID and x.WaterMeter_Stat>0),0)/count(t.WaterMeter_ID) )as onlineRatio "
				+ "					from ( " + "											select  a.WaterMeter_ID, " + "													a.Architecture_ID, " + "													a.isComputation, "
				+ "													p.Price_Value, " + "													nvl(d.ZGross,0) DayGross, " + "													nvl(m.ZGross,0) MonthGross  "
				+ "        from  " + "											WaterMeter a left join Price p on p.Price_ID=a.Price_ID  "
				+ "																left join (select WaterMeter_ID,ZGross from T_DayWater where SelectYear=" + year + " and SelectMonth=" + month
				+ " and SelectDay=" + day + ") d on d.WaterMeter_ID=a.WaterMeter_ID  "
				+ "																left join (select WaterMeter_ID,ZGross from T_MonthWater where SelectYear=" + year + " and SelectMonth=" + month
				+ ") m on m.WaterMeter_ID=a.WaterMeter_ID  " + "        where a.Area_ID= " + areaID + "							 ) t  "
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
	 * 集抄水表以后修改AmmeterDatas表
	 * 
	 * @param value
	 * @param sysdate
	 * @param ammID
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public boolean insertAfterCommDan(double value, String time, int ammID,float beilv,double currentFlow) throws SQLException, ParseException
	{


		//查询数据库系统时间
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
		String sqldata1 = "select ZValueZY,to_char(ValueTime,'yyyy-MM-dd hh24:mi:ss')  from ( select * from ( select * from ZWATERDATAS" + String.valueOf(ammID)+" "
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
					+ ",102)";
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
		//zValueZY = -1;
		//2、判断主键是否重复
		//对cnt进行判定
		int cnt=0;
		String sqldata3 = "select count(ValueTime)  from ZWATERDATAS"+String.valueOf(ammID)+" "
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
					+ ",103)";
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
		String ifadddec=	 Configuration.getString("server.IfAddDec");
		if (ifadddec.equals("1"))//添加三位随机小数 李嵘20160906
		{
			if (value*1000%1000==0)//如果小数位是0则模拟三位小数
			{
				value=AddDec(value,zValueZY);
			}
		}
		
		// 新增AmmeterDatas中的数据
		String sql = "insert into ZWATERDATAS" + String.valueOf(ammID) + "(ValueTime,ZValueZY,currentFlow) values(to_date('" + sysdate
				+ "','yyyy-mm-dd hh24:mi:ss') , round(" + value + ", 2) ,"+currentFlow+")";
	
		
		String sql1 = "update WaterMeter set LastTime= to_date('" + sysdate + "','yyyy-mm-dd hh24:mi:ss') where WaterMeter_ID= " + ammID;
		Connection conn = ConnDB.getConnection();
		Statement st = conn.createStatement();
		try
		{
			conn.setAutoCommit(false);
			st.executeUpdate(sql);
			st.executeUpdate(sql1);
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			conn.rollback();
			return false;
		} finally
		{
			ConnDB.release(conn, st);
		}

	}
	/**
	 * 重载方法
	 * 传入value未乘倍率和加修正
	 * 且无瞬时流量数据
	 * @param value
	 * @param time
	 * @param ammID
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean insertAfterCommDan(double value, String time, int ammID) throws SQLException, ParseException
	{
		//查询表计倍率 修正
		String beilv = null;
		String xiuZheng = null;
		String sqlam = "select nvl(beilv,1) as beilv,nvl(Xiuzheng,0) as Xiuzheng from V_AmMeter where ammeter_ID = "+ammID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlam, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				beilv = rs.getString(1);
				xiuZheng = rs.getString(2);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		value = value*Double.valueOf(beilv)+Double.valueOf(xiuZheng);
		//查询数据库系统时间
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
		String sqldata1 = "select ZValueZY,to_char(ValueTime,'yyyy-MM-dd hh24:mi:ss')  from ( select * from ( select * from ZWATERDATAS" + String.valueOf(ammID)+" "
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
		if ((value < zValueZY)||((zValueZY!=-1)&&((value-zValueZY)/Double.valueOf(beilv)/days>1000)))
		{
			String sqldata2 = "insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values("
					+ ammID
					+ ",to_date('"
					+ sysdate
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ value
					+ ",102)";
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
		String sqldata3 = "select count(ValueTime)  from ZWATERDATAS"+String.valueOf(ammID)+" "
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
					+ ",103)";
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
		String ifadddec=	 Configuration.getString("server.IfAddDec");
		if (ifadddec.equals("1"))//添加三位随机小数 李嵘20160906
		{
			if (value*1000%1000==0)//如果小数位是0则模拟三位小数
			{
				value=AddDec(value,zValueZY);
			}
		}
		// 新增AmmeterDatas中的数据
		String sql = "insert into ZWATERDATAS" + String.valueOf(ammID) + "(ValueTime,ZValueZY) values(to_date('" + sysdate
				+ "','yyyy-mm-dd hh24:mi:ss') , round(" + value + ", 2) )";
		String sql1 = "update WaterMeter set LastTime= to_date('" + sysdate + "','yyyy-mm-dd hh24:mi:ss') where WaterMeter_ID= " + ammID;
		Connection conn11 = ConnDB.getConnection();
		Statement st = conn11.createStatement();
		try
		{
			conn11.setAutoCommit(false);
			st.executeUpdate(sql);
			st.executeUpdate(sql1);
			conn11.commit();
			conn11.setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			conn11.rollback();
			return false;
		} finally
		{
			ConnDB.release(conn11, st);
		}

	}
		/*
	 * 生成模拟两位小数
	 * invalue 原始数据
	 * zvaluezynext 下次最小值
	 */
	private static double AddDec(double inValue,double zValueZYnext)
	{
		if(zValueZYnext==-1)
		{
			return inValue;
		}
		if(inValue>zValueZYnext)
		{
			return inValue;
		}
		double rtdec=0;
		double maxlen=zValueZYnext-inValue;//10.2-10
		
		if (maxlen>1)
		{
			return zValueZYnext;
		}
		else if (maxlen<0){
			return zValueZYnext;
		}
		else
		{//在小数点之间
			Random ra =new Random();
			rtdec=ra.nextDouble()*maxlen;
			DecimalFormat numDf = new DecimalFormat(".000");
			rtdec = Double.parseDouble(numDf.format(rtdec));
			return zValueZYnext+rtdec;
		}
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
	/**
	 * 查询水表的瞬时流量
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String queryWaterFlowValue(int id) throws SQLException
	{
		String value = "";
		String sql = "select ( max(a.ZValueZY) - min(a.ZValueZY) ) misValue, " + "		( max(a.ValueTime)-min(a.valueTime) ) misTime, "
				+ "		(( max(a.ZValueZY) - min(a.ZValueZY) )/( max(a.ValueTime)-min(a.valueTime) ) ) MisFlowValue " + "	from " + "	( "
				+ "			select ValueTime,ZValueZY from ZWATERDATAS" + String.valueOf(id) + " order by  ValueTime desc " + "	) a " + "    where  "
				+ "		rownum <=2 ";
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
				value = df.format(rs.getFloat("MisFlowValue"));
			} else
			{
				value = "--";
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		/*
		 * Statement st = ConnDB.getConnection().createStatement(); ResultSet rs
		 * = st.executeQuery(sql);
		 * 
		 * if (rs.next()) { value = df.format(rs.getFloat("MisFlowValue")); }
		 * else { value = "--"; }
		 * 
		 * if (rs != null) { rs.close(); }
		 * 
		 * if (st != null) { st.close(); }
		 */
		return value;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public JSONArray getBrotherMeter(String wmmID) throws SQLException
	{
		String sql = "SELECT (ammeter_id)id,(AMMETER_NAME)name,(select 1 from dual)style FROM ammeter WHERE  CONSUMERNUM=(SELECT CONSUMERNUM FROM WATERMETER WHERE WATERMETER_ID="
				+ wmmID
				+ ") "
				+ "UNION "
				+ "SELECT WATERMETER_ID id,WATERMETER_NAME name,(select 2 from dual)style FROM WATERMETER WHERE    CONSUMERNUM=(SELECT CONSUMERNUM FROM WATERMETER WHERE WATERMETER_ID="
				+ wmmID + ") and WATERMETER_ID <>" + wmmID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray js = new JSONArray();
		JSONObject jo = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("id", rs.getString("id"));
				jo.put("name", rs.getString("name"));
				jo.put("style", rs.getString("style"));
				js.add(jo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		/*
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		 * ResultSet rs = ps.executeQuery();
		 * 
		 * JSONArray js = new JSONArray(); JSONObject jo = null; while
		 * (rs.next()) { jo = new JSONObject(); jo.put("id",
		 * rs.getString("id")); jo.put("name", rs.getString("name"));
		 * jo.put("style", rs.getString("style")); js.add(jo); }
		 * 
		 * if(rs!=null) rs.close();
		 * 
		 * if (ps!=null) { ps.close(); }
		 */

		return js;
	}

}
