package com.sf.energy.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.ConnDB;

public class WaterReportHelperImpl
{

	/*
	 * // private Connection conn = null;
	 *//**
	 * 构造函数，初始化是就取得连接数据库链接
	 */
	/*
	 * public WaterReportHelperImpl() { try { conn = ConnDB.getConnection(); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */
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
		String sql = "select count(*) as offlineCount from watermeter where WaterMeter_Stat=0";

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

		sql = "select count(*) as onlineCount from watermeter where WaterMeter_Stat=1";

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
	 * 查询所有部门年度用水量排名，并按降序排列
	 * 
	 * @param quryYear
	 *            查询的年份
	 * 
	 * @return List<ReportModel> 查询结果集
	 * 
	 * @throws SQLException
	 */
	public List<ReportModel> getAllGroupCountListDisc(int quryYear) throws SQLException
	{
		List<ReportModel> list = new LinkedList<ReportModel>();
		String sql = "select b.partment_name groupName,gross,money from(select partment_id,sum(zgross) "
				+ "gross,sum(zmoney) money from T_PartmentDayWater where selectYear=? "
				+ "group by partment_id) a left join (select partment_id,partment_name "
				+ "from partment) b on a.partment_id=b.partment_id order by gross desc";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, quryYear);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ReportModel rm = new ReportModel();
				rm.setSelectYear(quryYear);
				rm.setGroupName(rs.getString("groupName"));
				rm.setTotalEnergyCount(rs.getFloat("gross"));
				rm.setTotalMoneyCount(rs.getFloat("money"));
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

	/**
	 * 查询指定建筑指定时间段内的用水分项分布
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            结束时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 */
	public Map<String, Float> getArcFenLeiCountBetween(int arcID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		if (start.after(end))
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getYear() <= end.getYear()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_ARCFENLEIDAYWater where selectDay>? and selectDay<? and "
						+ "selectMonth=? and selectYear=? and ARCHITECTURE_ID=? group by fenlei) b on " + "a.fenleiNum=b.fenlei order by gross desc";
				conn = ConnDB.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 25);
				pstmt.setInt(2, start.getDate() - 1);
				pstmt.setInt(3, end.getDate() + 1);
				pstmt.setInt(4, start.getMonth() + 1);
				pstmt.setInt(5, start.getYear() + 1900);
				pstmt.setInt(6, arcID);
			}

			if ((start.getYear() == end.getYear()) && (start.getMonth() < end.getMonth()))
			{
				sql = "select a.fenleiValue,b.gross" + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select fenlei,sum(zgross) gross " + "from T_ARCFENLEIDAYWater "
						+ "where ((selectMonth=? and selectDay>?) or (selectMonth>? and selectMonth<?) "
						+ "or (selectMonth=? and selectDay<?)) and selectYear=? and ARCHITECTURE_ID=? " + "group by fenlei) b "
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
	 * 查询指定建筑指定时间段内的用水性质分布
	 * 
	 * @param arcID
	 *            建筑ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 */
	public Map<String, Float> getArcStyleCountBetween(int arcID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String arcName = getArcName(arcID);

		if (arcName == null)
			return null;

		if (start.after(end))
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getYear() <= end.getYear()))
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
				sql = "select a.fenleiValue,b.gross" + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
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
	 * 查询指定部门指定某年月的每一天的用水量
	 * 
	 * @param groupID
	 *            部门ID
	 * 
	 * @param queryYear
	 *            查询年份
	 * 
	 * @param queryMonth
	 *            查询月份
	 * 
	 * @return List<ReportModel> 查询得到的每一天的数据结果集
	 * @throws SQLException
	 */
	public List<ReportModel> getGroupCountEveryDay(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		List<ReportModel> list = new ArrayList<ReportModel>(31);

		String sql = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		for (int i = 0; i < 31; i++)
		{
			ReportModel rm = new ReportModel();
			rm.setGroupName(groupName);
			rm.setSelectYear(queryYear);
			rm.setSelectMonth(queryMonth);
			rm.setSelectDay(i + 1);
			list.add(rm);
		}

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
				if (rst.getInt("selectday") <= 31 && rst.getInt("selectday") >= 1)
				{
					ReportModel rm = list.get(rst.getInt("selectday") - 1);
					rm.setTotalEnergyCount(rst.getFloat("zgross"));
					rm.setTotalMoneyCount(rst.getFloat("zmoney"));
				}
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

	/**
	 * 查询得到指定建筑的指定年份的用水性质分项数据
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @return Map<String, Float> 查询得到的用水性质分项数据
	 * 
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 26);
			ps.setInt(2, queryYear);
			ps.setInt(3, arcID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				map.put(rs.getString("fenleiValue"), rs.getFloat("gross"));
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
	 * 查询得到指定建筑指定年份的分类分项数据
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @return Map<String, Float>　查询得到的分项分布数据
	 * 
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
				+ "join(select fenlei,sum(zgross) gross from T_arcfenleidaywater " + "where selectYear=? and ARCHITECTURE_ID=?"
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
	 * 查询得到指定建筑指定年月的用水性质分布
	 * 
	 * @param arcID
	 *            查询的建筑ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @param queryMonth
	 *            查询的月份
	 * 
	 * @return Map<String, Float> 查询得到的用水性质分布数据
	 * 
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
				+ "where selectYear=? and selectMonth=? and ARCHITECTURE_ID=?" + "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
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
	 * 查询指定建筑的月度用水详细情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param arcID
	 *            建筑ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @param queryMonth
	 *            查询的月份
	 * 
	 * @return ReportModel 查询结果实体集
	 * 
	 * @throws SQLException
	 */
	public ReportModel getArcCountDetailByMonth(int arcID, int queryYear, int queryMonth) throws SQLException
	{
		ReportModel rm = new ReportModel();
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

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("value") + rs.getFloat("gross") + "   ";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			rm.setFenlei(fenlei);
			rm.setFenXiangXiaoJi(fenXiangXiaoJi);
			
			sql = "select DictionaryValue_VALUE value,gross,money from (select DictionaryValue_NUM,"
					+ "DictionaryValue_VALUE from DictionaryValue where DictionaryValue.Dictionary_ID=?) "
					+ "a left join (select USEAMSTYLE fenlei,sum(zgross) as gross,sum(zmoney) as money from "
					+ "T_ARCSTYLEDAYWater where ARCHITECTURE_ID=? and SELECTYEAR=? and selectmonth=? group by USEAMSTYLE)"
					+ " b on a.DictionaryValue_NUM=b.fenlei";
//			Connection conn1 = null;
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try
			{
//				conn1 = ConnDB.getConnection();
				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setInt(1, 26);
				pstmt1.setInt(2, arcID);
				pstmt1.setInt(3, queryYear);
				pstmt1.setInt(4, queryMonth);
				rs1 = pstmt1.executeQuery();

				while (rs1.next())
				{
					style.put(rs1.getString("value"), rs1.getFloat("gross"));

					xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("value") + rs1.getInt("gross") + "   ";
					totalEneryCount = totalEneryCount + rs1.getFloat("gross");
					totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
				}

				rm.setStyle(style);
				rm.setXingZhiXiaoJi(xingZhiXiaoJi);
				rm.setTotalEnergyCount(totalEneryCount);
				rm.setTotalMoneyCount(totalMoneyCount);
				rm.setArcName(arcName);

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(pstmt1, rs1);
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
	 * 查询指定部门指定时间段内的用水分项分布
	 * 
	 * @param groupID
	 *            要查询的部门ID
	 * 
	 * @param start
	 *            查询的起始时间点
	 * 
	 * @param end
	 *            查询的截止时间点
	 * 
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 */
	public Map<String, Float> getGroupFenLeiCountBetween(int groupID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		if (start.after(end))
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getYear() <= end.getYear()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select fenlei,sum(zgross) "
						+ "gross from T_partmentfenleidaywater where selectDay>? and selectDay<? and "
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
						+ "left join(select fenlei,sum(zgross) gross " + "from T_partmentfenleidaywater "
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

	/**
	 * 查询指定部门指定时间段内的用水性质分布
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * 
	 * @param start
	 *            查询的起始时间点
	 * 
	 * @param end
	 *            查询的截止时间点
	 * 
	 * @return Map<String,Float> 查询结果
	 * @throws SQLException
	 */
	public Map<String, Float> getGroupStyleCountBetween(int groupID, Date start, Date end) throws SQLException
	{
		Map<String, Float> map = new HashMap<String, Float>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String groupName = null;
		groupName = getGroupName(groupID);

		if (groupName == null)
			return null;

		String sql = null;

		if (start.after(end))
			return null;
		try
		{
			if ((start.getYear() == end.getYear()) && (start.getMonth() == end.getMonth()) && (start.getYear() <= end.getYear()))
			{
				sql = "select a.fenleiValue,b.gross from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue from DictionaryValue "
						+ "where DictionaryValue.Dictionary_ID=?) a left join(select USEAMSTYLE fenlei,sum(zgross) "
						+ "gross from T_partmentstyledaywater where selectDay>? and selectDay<? and "
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
				sql = "select a.fenleiValue,b.gross" + "from(select DictionaryValue.DictionaryValue_Num fenleiNum,"
						+ "DictionaryValue.DictionaryValue_value fenleiValue " + "from DictionaryValue where DictionaryValue.Dictionary_ID=?) a "
						+ "left join(select USEAMSTYLE fenlei,sum(zgross) gross " + "from T_partmentstyledaywater "
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
						+ "from T_partmentstyledaywater " + "where ((selectYear=? and (selectMonth>? or (selectMonth=? and selectDay>?))) or "
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

	/**
	 * 查询指定部门的月度用水情况，分别获得用水分项分布，用水性质分布，总用水量，总水费
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @param queryMonth
	 *            查询的月份
	 * 
	 * @return ReportModel 查询结果实体集
	 * @throws SQLException
	 */
	public ReportModel getGroupCountDetailByMonth(int groupID, int queryYear, int queryMonth) throws SQLException
	{
		ReportModel gmm = new ReportModel();
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
				+ "where selectyear=? and selectmonth=? and partment_id=? group by fenlei order by fenlei) a on " + "a.fenlei=b.DictionaryValue_Num";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
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

				fenXiangXiaoJi = fenXiangXiaoJi + rs.getString("type") + rs.getFloat("gross") + "         ";

				totalEneryCount = totalEneryCount + rs.getFloat("gross");
				totalMoneyCount = totalMoneyCount + rs.getFloat("money");
			}

			gmm.setFenlei(fenlei);
			gmm.setFenXiangXiaoJi(fenXiangXiaoJi);

			sql = "select fenlei,DictionaryValue_value as type,gross,money from (select "
					+ "DictionaryValue.DictionaryValue_Num,DictionaryValue.DictionaryValue_value "
					+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?) b left join "
					+ "(select useamstyle fenlei,sum(zgross) gross,sum(zmoney) money from T_PartmentStyleDayWater "
					+ "where selectyear=? and selectmonth=? and partment_id=? group by useamstyle order by useamstyle) a on "
					+ "a.fenlei=b.DictionaryValue_Num";
//			Connection conn1 = null;
			PreparedStatement pstmt1 =null;
			ResultSet rs1 =null;
			try{
//				conn1 = ConnDB.getConnection();
				pstmt1 = conn.prepareStatement(sql);
				rs1 = pstmt1.executeQuery();
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
					xingZhiXiaoJi = xingZhiXiaoJi + rs1.getString("type") + rs1.getFloat("gross") + "                ";
					totalEneryCount = totalEneryCount + rs1.getFloat("gross");
					totalMoneyCount = totalMoneyCount + rs1.getFloat("money");
				}

				gmm.setStyle(style);
				gmm.setXingZhiXiaoJi(xingZhiXiaoJi);

				gmm.setTotalEnergyCount(totalEneryCount);
				gmm.setTotalMoneyCount(totalMoneyCount);

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release( pstmt1,rs1);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}

		
		return gmm;
	}

	/**
	 * 查询得到指定部门指定年份的用水性质分项
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
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
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_partmentstyledaywater " + "where selectYear=? and partment_ID=?"
				+ "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
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

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return map;
	}

	/**
	 * 查询指定部门指定年月的用水性质分项数据
	 * 
	 * @param groupID
	 *            查询的部门ID
	 * 
	 * @param queryYear
	 *            查询的年份
	 * 
	 * @param queryMonth
	 *            查询的月份
	 * 
	 * @return Map<String, Float>　查询得到的用水性质分项数据
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
				+ "join(select USEAMSTYLE fenlei,sum(zgross) gross from T_partmentstyledaywater "
				+ "where selectYear=? and selectMonth=? and partment_ID=?" + "group by USEAMSTYLE) b on a.fenleiNum=b.fenlei order by gross desc";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
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

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
		return map;
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
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				groupName = rs.getString("Partment_NAME");
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
		
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
	private String getArcName(int arcID) throws SQLException
	{
		String sql = "select ARCHITECTURE_NAME from ARCHITECTURE where ARCHITECTURE_ID=" + arcID;
		String arcName = null;
		// 改了
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				arcName = rs.getString("Architecture_NAME");

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pstmt,rs);
		}
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

		// ConnDB.close();
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

}
