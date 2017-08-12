package com.sf.energy.map.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.map.model.AmMeterModel;
import com.sf.energy.util.ConnDB;

public class AmMeterDao
{

	/**
	 * 查询某个电表的相关信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public AmMeterModel queryAmMeter(int id) throws SQLException
	{
		AmMeterModel amMeterModel = null;
		String sql = "select AmMeter_Name,AmMeter_485Address,ConsumerPhone from AmMeter where AmMeter_ID=" + id;

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
				amMeterModel = new AmMeterModel();
				amMeterModel.setAmMeter_Name(rs.getString("AmMeter_Name"));
				amMeterModel.setAmMeter_485Address(rs.getString("AmMeter_485Address"));
				amMeterModel.setConsumerPhone(rs.getString("ConsumerPhone"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return amMeterModel;
	}

	/**
	 * 查询某个建筑下某个楼层下的电表信息
	 * 
	 * @param arcId
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<AmMeterModel> queryArcFoolAmMeter(int arcId, int floor) throws SQLException
	{
		List<AmMeterModel> amMeterList = new ArrayList<AmMeterModel>();
		String sql = "select AmMeter_ID,AmMeter_Name from AmMeter where Architecture_ID=" + arcId + " and  Floor=" + floor;

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
				AmMeterModel amMeterModel = new AmMeterModel();
				amMeterModel.setAmMeter_ID(rs.getString("AmMeter_ID"));
				amMeterModel.setAmMeter_Name(rs.getString("AmMeter_Name"));
				amMeterList.add(amMeterModel);

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return amMeterList;
	}

	/**
	 * 查询该建筑有多少层
	 * 
	 * @param arcId
	 * @return
	 * @throws SQLException
	 */
	public int queryArcFloor(int arcId) throws SQLException
	{
		int floor = 0;
		String sql = "select ARCHITECTURE_STOREY from Architecture where Architecture_ID=" + arcId;

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
				floor = rs.getInt("ARCHITECTURE_STOREY");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return floor;
	}

	// 获取电表当前示数
	public int getAmMeterRead(int id) throws SQLException
	{
		int amMeterValue = 0;
		String sql = "select max(ZVALUEZY) amMetervalue from ZAMDATAS" + String.valueOf(id);
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
				amMeterValue = rs.getInt("amMetervalue");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return amMeterValue;
	}

	// 得到建筑月分项用电
	public String getMapArcFenxiang(String Name, String Architecture_ID, String theYear, String theMonth) throws SQLException
	{
		String chatString = "<graph caption='"
				+ Name
				+ "  "
				+ theYear
				+ "年"
				+ theMonth
				+ "月 用电结构分析' baseFont='宋体' baseFontSize='11'   yAxisName='KW.H' showValues='1' decimalPrecision='2' formatNumberScale='0' showPercentageInLabel='1' showValues='0'>";
		int counGross = 0;
		// 查询建筑分项用电sql
		String fenxiangsql = "select DictionaryValue_Value  Style,nvl(ZGross,0) ZGross from (select  DictionaryValue_Num,DictionaryValue_Value from DictionaryValue where Dictionary_ID=7)a left join (select Fenlei,sum(ZGross)ZGross from T_ArcFenleiDayAm where Architecture_ID="
				+ Architecture_ID
				+ " and SelectYear="
				+ theYear
				+ " and SelectMonth="
				+ theMonth
				+ " group by Fenlei)  b on DictionaryValue_Num=Fenlei order by ZGross desc";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(fenxiangsql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				counGross = counGross + rs.getInt("ZGross");
				chatString += "<set name='" + rs.getString("Style") + "' value='" + rs.getString("ZGross") + "' hoverText='" + rs.getString("Style")
						+ "'/>";

			}
			chatString += "</graph>|" + theYear + "年" + theMonth + "月 用电" + counGross + " 千瓦时";

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return chatString;
	}

	// 得到建筑48小时用电状况图
	public String getMapArcAm48Hour(String startTime, String endTime) throws SQLException
	{
		int time = 0;
		String StyleName = "用电";
		String Unit = "度";
		String yAxisName = "KW.H";
		String strWorkingStartTime = "08:00";
		String strWorkingEndTime = "18:00";
		String valueText = "";
		int total = 0;
		String chatString = "<graph caption='24小时"
				+ StyleName
				+ "状况图  ' subCaption='"
				+ startTime
				+ " 至 "
				+ endTime
				+ "' chartLeftMargin='0'  chartRightMargin='0' chartTopMargin='0' chartBottomMargin='0' baseFont='宋体' baseFontSize='11' numVDivLines='0' numDivLines='4' WorkingStartTime='"
				+ 500 + "' WorkingEndTime='" + 1000 + "'  yAxisName='" + yAxisName + "'  showValues='0' decimalPrecision='2' formatNumberScale='0'>";
		String sql = "select theTime,theData from MapamMeterHourData  order by theTime asc";
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
				total = total + rs.getInt("theData");
				valueText = "value='" + rs.getInt("theData") + "'";
				if (time % 4 == 0)
				{
					chatString += "<set name='" + rs.getString("theTime").substring(11, 16) + "' " + valueText + " hoverText='"
							+ rs.getString("theTime") + "' color='0906FF'/>\r\n";

				} else
				{
					chatString += "<set  " + valueText + " hoverText='" + rs.getString("theTime") + "' color='0906FF'/>\r\n";
				}
				++time;

			}
			chatString += "</graph>|" + endTime + "日" + StyleName + "：" + total + " " + Unit;

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return chatString;
	}

	// 执行存储过程
	public void carryPro(String proName, int meterId)
	{
		String amMmeterId = meterId + "";
		ResultSetMetaData meta = null;
		String proString = "";
		proString = "{ call " + proName + "(";
		Connection conn = null;
		CallableStatement cstmt = null;
		try
		{
			conn = ConnDB.getConnection();
			proString = proString + "?) }";
			cstmt = conn.prepareCall(proString);
			cstmt.setString(1, amMmeterId);
			cstmt.execute();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				try
				{
					// 将Connection连接对象还给数据库连接池
					conn.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (cstmt != null){
				try
				{
					cstmt.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

}
