package com.sf.energy.waternet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.waterMatchModelInfoDao;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class WaterBalanceDao
{
	DecimalFormat dd = new DecimalFormat("0.00");

	/**
	 * 获取水表父子关系的信息
	 * 
	 * @return 水表父子关系的信息
	 * @throws SQLException
	 */
	public List<WatermeterModel> getTreeInfoData() throws SQLException
	{
		String sql = "select WATERMETER_ID,PARENT_ID,WATERMETER_NAME from watermeter where isCountMeter=1";
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				WatermeterModel model = new WatermeterModel();
				model.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				model.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				if(rs.getInt("WATERMETER_ID")==rs.getInt("PARENT_ID"))
				{
					model.setPARENT_ID(-1);
				}
				else
				{
					model.setPARENT_ID(rs.getInt("PARENT_ID"));
				}
				
				

				list.add(model);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return list;
	}
	/**
	 * 获取水表父子关系的信息
	 * 
	 * @return 水表父子关系的信息
	 * @throws SQLException
	 */
	public List<WatermeterModel> getBigWaterMeterTreeInfoData() throws SQLException
	{
		String sql = "select WATERMETER_ID,PARENT_ID,WATERMETER_NAME from v_WaterMeter where isBig=1 order by watermeter_Name";
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				WatermeterModel model = new WatermeterModel();
				model.setWATERMETER_ID(rs.getInt("WATERMETER_ID"));
				model.setWATERMETER_NAME(rs.getString("WATERMETER_NAME"));
				if(rs.getInt("WATERMETER_ID")==rs.getInt("PARENT_ID"))
				{
					model.setPARENT_ID(-1);
				}
				else
				{
					model.setPARENT_ID(rs.getInt("PARENT_ID"));
				}
				list.add(model);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return list;
	}
	/**
	 * 计算平衡率
	 * 
	 * @param waterMeterID
	 *            父节点ID
	 * @param Float
	 *            .parseFloat( enterWaterMap 进水量数据集合
	 * @param exportWaterMap
	 *            出水量数据集合
	 * @return数据集
	 */
	public HashMap<String, String> analysisBalanceForPingHengLv(
			String waterMeterID, HashMap<String, String> enterWaterMap,
			HashMap<String, String> exportWaterMap)
	{
		HashMap<String, String> infoMap = new HashMap<>();
		int meterID = Integer.parseInt(waterMeterID);
		if (meterID == 0)
		{
			for (int i = 0; i < 24; i++)
			{
				String key = String.valueOf(i);
				infoMap.put(key, "--");
			}
		} else
		{
			for (int i = 0; i < 24; i++)
			{
				String key = String.valueOf(i);
				String missWater;
				float temp = 0;
				if (Float.parseFloat(enterWaterMap.get(key)) != 0)
				{
					temp = (Float.parseFloat(enterWaterMap.get(key)) - Float
							.parseFloat(exportWaterMap.get(key)))
							/ Float.parseFloat(enterWaterMap.get(key)) * 100;
				}
				missWater = dd.format(temp);
				infoMap.put(key, missWater);
			}

		}
		return infoMap;
	}

	/**
	 * 计算水量差
	 * 
	 * @param waterMeterID
	 *            父节点ID
	 * @param enterWaterMap
	 *            进水量数据集合
	 * @param exportWaterMap
	 *            出水量数据集合
	 * @return 数据集
	 */
	public HashMap<String, String> analysisBalanceForMissWater(
			String waterMeterID, HashMap<String, String> enterWaterMap,
			HashMap<String, String> exportWaterMap)
	{
		HashMap<String, String> infoMap = new HashMap<>();
		int meterID = Integer.parseInt(waterMeterID);
		if (meterID == 0)
		{
			for (int i = 0; i < 24; i++)
			{
				String key = String.valueOf(i);
				infoMap.put(key, "--");
			}
			return infoMap;
		} else
		{
			for (int i = 0; i < 24; i++)
			{
				String key = String.valueOf(i);
				float missWater = Float.parseFloat(enterWaterMap.get(key))
						- Float.parseFloat(exportWaterMap.get(key));
				infoMap.put(key, dd.format(missWater));
			}
			return infoMap;
		}
	}

	/**
	 * 获得入水量
	 * 
	 * @param waterMeterID
	 *            总表的水表ID
	 * @param checkTime
	 *            查询日期
	 * @return 数据集
	 * @throws ParseException
	 * @throws SQLException
	 */
	public HashMap<String, String> get24EnterDataOfwaterBalance(
			String waterMeterID, String checkTime) throws ParseException,
			SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");
		int meterID = Integer.parseInt(waterMeterID);

		HashMap<String, String> infoMap = new HashMap<>();
		// 初始化初值
		for (int i = 0; i < 24; i++)
		{
			String key = String.valueOf(i);
			String value = "0";
			infoMap.put(key, value);
		}

		if (meterID != 0)
		{
			Date start = dayDf.parse(checkTime);
			Date end = dayDf.parse(checkTime);
			WaterReportHelperImpl dao = new WaterReportHelperImpl();
			List<WaterMeterMataData> list = dao.getWaterMeterBetween(meterID,
					start, end);

			for (WaterMeterMataData n : list)
			{
				int hour = getHour(n.getRecordTimeDate());
				String key = String.valueOf(hour);
				// 祖先某小时用水量
				float value = n.getValue();

				float newValue = Float.parseFloat(infoMap.get(key)) + value;
				infoMap.put(key, dd.format(newValue));
			}

		}
		return infoMap;

	}

	/**
	 * 获得一天的出水量的数据
	 * 
	 * @param waterMeterID
	 *            总表的水表ID
	 * @param checkTime
	 *            查询日期
	 * @return 数据集
	 * @throws SQLException
	 * @throws ParseException
	 */
	public HashMap<String, String> get24ExportDataOfwaterBalance(
			String waterMeterID, String checkTime) throws SQLException,
			ParseException
	{
		String sql = "select WaterMeter_ID,WaterMeter_Name from WaterMeter where Parent_ID="
				+ waterMeterID;
		HashMap<String, String> infoMap = new HashMap<>();
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			
			infoMap.put("count", "0");
			// 初始化初值
			for (int i = 0; i < 24; i++)
			{
				String key = String.valueOf(i);
				String value = "0";
				infoMap.put(key, value);
			}
			// 计算子孙的个数
			
			
			rs.last();
			int count = rs.getRow();
			infoMap.put("count", String.valueOf(count));
			rs.beforeFirst();

			// 计算子孙的数据合
			WaterReportHelperImpl dao = new WaterReportHelperImpl();
			int meterID;
			SimpleDateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = dayDf.parse(checkTime);
			Date end = dayDf.parse(checkTime);
			while (rs.next())
			{
				meterID = rs.getInt("WaterMeter_ID");
				List<WaterMeterMataData> list = dao.getWaterMeterBetween(meterID,
						start, end);
				for (WaterMeterMataData n : list)
				{
					int hour = getHour(n.getRecordTimeDate());
					String key = String.valueOf(hour);
					float value = n.getValue();
					float newValue = Float.parseFloat(infoMap.get(key)) + value;
					infoMap.put(key, dd.format(newValue));
				}

			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
	/*	PreparedStatement ps = null;
		ResultSet rs = null;
		ps = ConnDB.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = ps.executeQuery();

		close(ps, rs);*/

		return infoMap;
	}

	/**
	 * 获得某个时刻的小时的数值
	 * 
	 * @param time
	 *            时间点
	 * @return 小时的数值
	 */
	private int getHour(Date time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int theHour = cal.get(Calendar.HOUR_OF_DAY);
		return theHour;
	}

	/**
	 * 关闭查询链接，回收资源
	 * 
	 * @throws SQLException
	 *             void
	 */
//	private void close(PreparedStatement pstmt, ResultSet rs)
//			throws SQLException
//	{
//		if (rs != null)
//			rs.close();
//
//		if (pstmt != null)
//			pstmt.close();
//
//	}

	public String getTreeRootNodeInfoData() throws SQLException
	{
		String rootName=" ";
		String sql = "select SYSTEMINFO_COMPLAYNAME from SYSTEMINFO";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		ArrayList<WatermeterModel> list = new ArrayList<WatermeterModel>();
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				rootName=rs.getString(1);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return rootName;
		
	}

	

}
