package com.sf.energy.powernet.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.powernet.model.CurrentPartDatasModel;
import com.sf.energy.powernet.model.PDPartRoom;
import com.sf.energy.powernet.model.TempWETDevDatasModel;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;

/**
 * 多功能表DAO层
 * 
 * @author 雷小龙
 * 
 */
public class AmMeterPDDataDao
{
	AmMeterPDDatasModel amMeterPDDatasModel = null;
	List<AmMeterPDDatasModel> list = new ArrayList<AmMeterPDDatasModel>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public List<AmMeterPDDatasModel> queryAmMeterPDDatasModelE(int id, String starttime, String str, String glstyle) throws SQLException
	{
		String sql = "select JLID from PD_PartInfo03 where part_id=" + id + "";
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
				String ammeterid = rs.getString("JLID");
				if ((!"".equals(ammeterid)) && (ammeterid != null))
				{
					sql = "select to_char(ValueTime,'hh24:mi:ss') ValueTime," + str + " from ZPDDATAS" + ammeterid + " "
							+ "where ValueTime>to_date('" + starttime + "','yyyy-mm-dd') and ValueTime<(to_date('" + starttime
							+ "','yyyy-mm-dd')+1) " + "order by ValueTime";
					Connection conn1 = null;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;

					try
					{
						conn1 = ConnDB.getConnection();
						ps1 = conn1.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs1 = ps1.executeQuery();
						if ("PowerY".equals(glstyle))
						{
							while (rs1.next())
							{
								amMeterPDDatasModel = new AmMeterPDDatasModel();
								amMeterPDDatasModel.setPowerZY(rs1.getDouble("PowerZY"));
								amMeterPDDatasModel.setPowerAY(rs1.getDouble("PowerAY"));
								amMeterPDDatasModel.setPowerBY(rs1.getDouble("PowerBY"));
								amMeterPDDatasModel.setPowerCY(rs1.getDouble("PowerCY"));
								amMeterPDDatasModel.setValueTime(rs1.getString("ValueTime"));
								list.add(amMeterPDDatasModel);
							}

						}
						// 无功功率
						else if ("PowerW".equals(glstyle))
						{
							while (rs1.next())
							{
								amMeterPDDatasModel = new AmMeterPDDatasModel();
								amMeterPDDatasModel.setPowerZW(rs1.getDouble("PowerZW"));
								amMeterPDDatasModel.setPowerAW(rs1.getDouble("PowerAW"));
								amMeterPDDatasModel.setPowerBW(rs1.getDouble("PowerBW"));
								amMeterPDDatasModel.setPowerCW(rs1.getDouble("PowerCW"));
								amMeterPDDatasModel.setValueTime(rs1.getString("ValueTime"));
								list.add(amMeterPDDatasModel);
							}

						}
						// 功率因数
						else if ("PowerFactor".equals(glstyle))
						{
							while (rs1.next())
							{
								amMeterPDDatasModel = new AmMeterPDDatasModel();
								amMeterPDDatasModel.setPowerFactorA(rs1.getDouble("PowerFactorA"));
								amMeterPDDatasModel.setPowerFactorB(rs1.getDouble("PowerFactorB"));
								amMeterPDDatasModel.setPowerFactorC(rs1.getDouble("PowerFactorC"));
								amMeterPDDatasModel.setPowerFactorZ(rs1.getDouble("PowerFactorZ"));
								amMeterPDDatasModel.setValueTime(rs1.getString("ValueTime"));
								list.add(amMeterPDDatasModel);
							}

						}
						// 电压
						else if ("Voltage".equals(glstyle))
						{
							while (rs1.next())
							{
								amMeterPDDatasModel = new AmMeterPDDatasModel();
								amMeterPDDatasModel.setVoltageA(rs1.getDouble("VoltageA"));
								amMeterPDDatasModel.setVoltageB(rs1.getDouble("VoltageB"));
								amMeterPDDatasModel.setVoltageC(rs1.getDouble("VoltageC"));
								amMeterPDDatasModel.setValueTime(rs1.getString("ValueTime"));
								list.add(amMeterPDDatasModel);
							}
						}
						// 电流
						else if ("Current".equals(glstyle))
						{
							while (rs1.next())
							{
								amMeterPDDatasModel = new AmMeterPDDatasModel();
								amMeterPDDatasModel.setCurrent0(rs1.getDouble("Current0"));
								amMeterPDDatasModel.setCurrentA(rs1.getDouble("CurrentA"));
								amMeterPDDatasModel.setCurrentB(rs1.getDouble("CurrentB"));
								amMeterPDDatasModel.setCurrentC(rs1.getDouble("CurrentC"));
								amMeterPDDatasModel.setValueTime(rs1.getString("ValueTime"));
								list.add(amMeterPDDatasModel);
							}

						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(conn1, ps1, rs1);
					}
					// ps = ConnDB.getConnection().prepareStatement(sql);
					// rs = ps.executeQuery();
					//
					// if ("PowerY".equals(glstyle))
					// {
					// while (rs.next())
					// {
					// amMeterPDDatasModel = new AmMeterPDDatasModel();
					// amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
					// amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
					// amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
					// amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));
					// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
					// list.add(amMeterPDDatasModel);
					// }
					//
					// }
					// // 无功功率
					// else if ("PowerW".equals(glstyle))
					// {
					// while (rs.next())
					// {
					// amMeterPDDatasModel = new AmMeterPDDatasModel();
					// amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
					// amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
					// amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
					// amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));
					// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
					// list.add(amMeterPDDatasModel);
					// }
					//
					// }
					// // 功率因数
					// else if ("PowerFactor".equals(glstyle))
					// {
					// while (rs.next())
					// {
					// amMeterPDDatasModel = new AmMeterPDDatasModel();
					// amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
					// amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
					// amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
					// amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));
					// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
					// list.add(amMeterPDDatasModel);
					// }
					//
					// }
					// // 电压
					// else if ("Voltage".equals(glstyle))
					// {
					// while (rs.next())
					// {
					// amMeterPDDatasModel = new AmMeterPDDatasModel();
					// amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
					// amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
					// amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));
					// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
					// list.add(amMeterPDDatasModel);
					// }
					// }
					// // 电流
					// else if ("Current".equals(glstyle))
					// {
					// while (rs.next())
					// {
					// amMeterPDDatasModel = new AmMeterPDDatasModel();
					// amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
					// amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
					// amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
					// amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));
					// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
					// list.add(amMeterPDDatasModel);
					// }
					//
					// }

				}

			}
		} catch (Exception e)
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
		// String ammeterid = rs.getString("JLID");
		// if ((!"".equals(ammeterid)) && (ammeterid != null))
		// {
		// sql = "select to_char(ValueTime,'hh24:mi:ss') ValueTime," + str +
		// " from ZPDDATAS" + ammeterid + " "
		// + "where ValueTime>to_date('"
		// + starttime + "','yyyy-mm-dd') and ValueTime<(to_date('" + starttime
		// + "','yyyy-mm-dd')+1) "
		// + "order by ValueTime";
		// ps = ConnDB.getConnection().prepareStatement(sql);
		// rs = ps.executeQuery();
		//
		// if ("PowerY".equals(glstyle))
		// {
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		// amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
		// amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
		// amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
		// amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));
		// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
		// list.add(amMeterPDDatasModel);
		// }
		//
		// }
		// // 无功功率
		// else if ("PowerW".equals(glstyle))
		// {
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		// amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
		// amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
		// amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
		// amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));
		// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
		// list.add(amMeterPDDatasModel);
		// }
		//
		// }
		// // 功率因数
		// else if ("PowerFactor".equals(glstyle))
		// {
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		// amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
		// amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
		// amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
		// amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));
		// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
		// list.add(amMeterPDDatasModel);
		// }
		//
		// }
		// // 电压
		// else if ("Voltage".equals(glstyle))
		// {
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		// amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
		// amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
		// amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));
		// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
		// list.add(amMeterPDDatasModel);
		// }
		// }
		// // 电流
		// else if ("Current".equals(glstyle))
		// {
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		// amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
		// amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
		// amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
		// amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));
		// amMeterPDDatasModel.setValueTime(rs.getString("ValueTime"));
		// list.add(amMeterPDDatasModel);
		// }
		//
		// }
		//
		// }
		//
		// }

		return list;
	}

	public int getAmPDDataCountBetweenByPDPartID(int pdPartID, Date startTime, Date endTime) throws SQLException
	{
		int count = -1;
		if (endTime.before(startTime))
			return -1;

		int amID = getAmmeterIDByPdPartID(pdPartID);
		if (amID == -1)
			return -1;

		String sql = "select count(*) recordCount from ZPDDATAS" + String.valueOf(amID) + " where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and " + "to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sdf.format(startTime));
			ps.setString(2, sdf.format(endTime));
			// ps.setInt(3, amID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				count = rs.getInt("recordCount");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
		// ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//
		// ps.setString(1, sdf.format(startTime));
		// ps.setString(2, sdf.format(endTime));
		// //ps.setInt(3, amID);
		//
		// ResultSet rs = ps.executeQuery();
		//
		// if (rs.next())
		// {
		// count = rs.getInt("recordCount");
		// }
		return count;
	}

	public Map<String, Object> getAmDataBetweenByPDPartID(int pdPartID, Date startTime, Date endTime, int pageIndex, int pageCount)
			throws SQLException
	{
		List<AmMeterMataData> list = null;
		if (endTime.before(startTime))
			return null;
		Map<String, Object> map = null;
		int amID = getAmmeterIDByPdPartID(pdPartID);
		if (amID == -1)
			return null;

		String sql = "select * from ZAMDATAS" + String.valueOf(amID) + " where ValueTime between " + "to_date(?,'yyyy-mm-dd hh24:mi:ss') and "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') " + "order by ValueTime";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sdf.format(startTime));
			ps.setString(2, sdf.format(endTime));
			// ps.setInt(3, amID);

			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			int total = count;
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

			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<AmMeterMataData>();

				AmMeterMataData amd = new AmMeterMataData();

				amd.setAmMeterID(amID);
				amd.setRecordTimeDate(rs.getTimestamp("ValueTime"));
				amd.setValue(rs.getFloat("ZValueZY"));

				list.add(amd);

				count--;
			}

			map = new HashMap<String, Object>();

			map.put("Total", total);
			map.put("List", list);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
		// ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//
		// ps.setString(1, sdf.format(startTime));
		// ps.setString(2, sdf.format(endTime));
		// //ps.setInt(3, amID);
		//
		// ResultSet rs = ps.executeQuery();
		//
		// rs.last();
		// int count = rs.getRow();
		// int total = count;
		// if (count <= (pageCount * pageIndex))
		// return null;
		//
		// count = count - pageCount * pageIndex;
		//
		// if (count >= pageCount)
		// count = pageCount;
		//
		// if ((pageCount * pageIndex) == 0)
		// {
		// rs.absolute(1);
		// rs.previous();
		// } else
		// rs.absolute(pageCount * pageIndex);
		//
		// while (rs.next() && (count > 0))
		// {
		// if (list == null)
		// list = new LinkedList<AmMeterMataData>();
		//
		// AmMeterMataData amd = new AmMeterMataData();
		//
		// amd.setAmMeterID(amID);
		// amd.setRecordTimeDate(rs.getTimestamp("ValueTime"));
		// amd.setValue(rs.getFloat("ZValueZY"));
		//
		// list.add(amd);
		//
		// count--;
		// }
		// if (rs != null)
		// rs.close();
		//
		// if (ps != null)
		// ps.close();
		//
		// Map<String, Object> map = new HashMap<String, Object>();
		//
		// map.put("Total", total);
		// map.put("List", list);

		return map;
	}

	public Map<String, Object> getAmPDDataBetweenByPDPartID(int pdPartID, Date startTime, Date endTime, int pageIndex, int pageCount)
			throws SQLException
	{
		List<AmMeterPDDatasModel> list = null;
		if (endTime.before(startTime))
			return null;

		int amID = getAmmeterIDByPdPartID(pdPartID);
		if (amID == -1)
			return null;
		Map<String, Object> map = null;
		String sql = "select * from ZPDDATAS" + String.valueOf(amID) + " where ValueTime between " + "to_date(?,'yyyy-mm-dd hh24:mi:ss') and "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') order by ValueTime";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, sdf.format(startTime));
			ps.setString(2, sdf.format(endTime));
			// ps.setInt(3, amID);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			int total = count;
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

			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<AmMeterPDDatasModel>();

				AmMeterPDDatasModel amMeterPDDatasModel = new AmMeterPDDatasModel();

				amMeterPDDatasModel.setAmMeter_ID(amID);
				amMeterPDDatasModel.setRecordTime(new Date(rs.getTimestamp("ValueTime").getTime()));

				amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
				amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
				amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
				amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));

				amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
				amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
				amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
				amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));

				amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
				amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
				amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
				amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));

				amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
				amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
				amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));

				amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
				amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
				amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
				amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));

				amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
				amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
				amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
				amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));

				list.add(amMeterPDDatasModel);
				count--;
			}

			map = new HashMap<String, Object>();

			map.put("Total", total);
			map.put("List", list);

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
		// ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//
		// ps.setString(1, sdf.format(startTime));
		// ps.setString(2, sdf.format(endTime));
		// //ps.setInt(3, amID);
		// rs = ps.executeQuery();
		//
		// rs.last();
		// int count = rs.getRow();
		// int total = count;
		// if (count <= (pageCount * pageIndex))
		// return null;
		//
		// count = count - pageCount * pageIndex;
		//
		// if (count >= pageCount)
		// count = pageCount;
		//
		// if ((pageCount * pageIndex) == 0)
		// {
		// rs.absolute(1);
		// rs.previous();
		// } else
		// rs.absolute(pageCount * pageIndex);
		//
		// while (rs.next() && (count > 0))
		// {
		// if (list == null)
		// list = new LinkedList<AmMeterPDDatasModel>();
		//
		// AmMeterPDDatasModel amMeterPDDatasModel = new AmMeterPDDatasModel();
		//
		// amMeterPDDatasModel.setAmMeter_ID(amID);
		// amMeterPDDatasModel.setRecordTime(new
		// Date(rs.getTimestamp("ValueTime").getTime()));
		//
		// amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
		// amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
		// amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
		// amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));
		//
		// amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
		// amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
		// amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
		// amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));
		//
		// amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
		// amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
		// amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
		// amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));
		//
		// amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
		// amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
		// amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));
		//
		// amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
		// amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
		// amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
		// amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));
		//
		// amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
		// amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
		// amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
		// amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));
		//
		// list.add(amMeterPDDatasModel);
		// // count--;
		// }
		// if (rs != null)
		// rs.close();
		//
		// if (ps != null)
		// ps.close();
		//
		// Map<String, Object> map = new HashMap<String, Object>();
		//
		// map.put("Total", total);
		// map.put("List", list);

		return map;
	}

	/**
	 * 通过设备ID查询出对应的电表ID
	 * 
	 * @param pdpartID
	 * @return
	 * @throws SQLException
	 */
	public int getAmmeterIDByPdPartID(int pdpartID) throws SQLException
	{
		int ammeterID = -1;

		String sql = "select JLID from PD_PartInfo03 where Part_ID = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, pdpartID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				ammeterID = rs.getInt("JLID");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// ps.setInt(1, pdpartID);
		// ResultSet rs = ps.executeQuery();
		//
		// if (rs.next())
		// {
		// ammeterID = rs.getInt("JLID");
		// }

		return ammeterID;
	}

	public boolean updateAmmeterPDDataByCloum(String cloumLabel, float value, int AmmeterID, Date valueTime) throws SQLException
	{
		boolean flag = false;

		String sql = "update ZPDDATAS" + String.valueOf(AmmeterID) + " set " + cloumLabel
				+ " = ? where ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";

		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, value);
			// ps.setInt(2, AmmeterID);
			ps.setString(2, sdf.format(valueTime));
			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean updateAmmeterPDData(AmMeterPDDatasModel amd) throws SQLException
	{
		boolean flag = false;

		String sql = "update ZPDDATAS" + String.valueOf(amd.getAmMeter_ID()) + " set" + " PowerZY =? , PowerAY=? , PowerBY=? , PowerCY=? ,"
				+ " PowerZW=? , PowerAW=? , PowerBW=? , PowerCW=? ," + " PowerFactorA=? , PowerFactorB=? , PowerFactorC=? , PowerFactorZ=? ,"
				+ " VoltageA=? , VoltageB=? , VoltageC=? ," + " Current0=? , CurrentA=? , CurrentB=? , CurrentC=? ,"
				+ " POWERSZZ=? , POWERSZA=? , POWERSZB=? , POWERSZC=? " + "where ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, amd.getPowerZY());
			ps.setDouble(2, amd.getPowerAY());
			ps.setDouble(3, amd.getPowerBY());
			ps.setDouble(4, amd.getPowerCY());
			ps.setDouble(5, amd.getPowerZW());
			ps.setDouble(6, amd.getPowerAW());
			ps.setDouble(7, amd.getPowerBW());
			ps.setDouble(8, amd.getPowerCW());
			ps.setDouble(9, amd.getPowerFactorA());
			ps.setDouble(10, amd.getPowerFactorB());
			ps.setDouble(11, amd.getPowerFactorC());
			ps.setDouble(12, amd.getPowerFactorZ());
			ps.setDouble(13, amd.getVoltageA());
			ps.setDouble(14, amd.getVoltageB());
			ps.setDouble(15, amd.getVoltageC());
			ps.setDouble(16, amd.getCurrent0());
			ps.setDouble(17, amd.getCurrentA());
			ps.setDouble(18, amd.getCurrentB());
			ps.setDouble(19, amd.getCurrentC());
			ps.setDouble(20, amd.getPowerSZZ());
			ps.setDouble(21, amd.getPowerSZA());
			ps.setDouble(22, amd.getPowerSZB());
			ps.setDouble(23, amd.getPowerSZC());
			// ps.setInt(24, amd.getAmMeter_ID());
			ps.setString(24, sdf.format(amd.getRecordTime()));
			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		//
		// ps.setDouble(1, amd.getPowerZY());
		// ps.setDouble(2, amd.getPowerAY());
		// ps.setDouble(3, amd.getPowerBY());
		// ps.setDouble(4, amd.getPowerCY());
		// ps.setDouble(5, amd.getPowerZW());
		// ps.setDouble(6, amd.getPowerAW());
		// ps.setDouble(7, amd.getPowerBW());
		// ps.setDouble(8, amd.getPowerCW());
		// ps.setDouble(9, amd.getPowerFactorA());
		// ps.setDouble(10, amd.getPowerFactorB());
		// ps.setDouble(11, amd.getPowerFactorC());
		// ps.setDouble(12, amd.getPowerFactorZ());
		// ps.setDouble(13, amd.getVoltageA());
		// ps.setDouble(14, amd.getVoltageB());
		// ps.setDouble(15, amd.getVoltageC());
		// ps.setDouble(16, amd.getCurrent0());
		// ps.setDouble(17, amd.getCurrentA());
		// ps.setDouble(18, amd.getCurrentB());
		// ps.setDouble(19, amd.getCurrentC());
		// ps.setDouble(20, amd.getPowerSZZ());
		// ps.setDouble(21, amd.getPowerSZA());
		// ps.setDouble(22, amd.getPowerSZB());
		// ps.setDouble(23, amd.getPowerSZC());
		// //ps.setInt(24, amd.getAmMeter_ID());
		// ps.setString(24, sdf.format(amd.getRecordTime()));
		//
		// if (ps.executeUpdate() == 1)
		// flag = true;
		//
		// if (ps != null)
		// ps.close();

		return flag;
	}

	public boolean deleteAmmeterPDData(int ammeterID, Date valueTime) throws SQLException
	{
		boolean flag = false;

		String sql = "delete from ZPDDATAS" + String.valueOf(ammeterID) + " where ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, sdf.format(valueTime));

			if (ps.executeUpdate() == 1)
				flag = true;

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		//
		// //ps.setInt(1, ammeterID);
		// ps.setString(1, sdf.format(valueTime));
		//
		// if (ps.executeUpdate() == 1)
		// flag = true;
		//
		// if (ps != null)
		// ps.close();

		return flag;
	}

	public AmMeterPDDatasModel getSingleAmMeterPDData(int ammeterID, Date valueTime) throws SQLException
	{
		AmMeterPDDatasModel amMeterPDDatasModel = null;
		String sql = "select * from ZPDDATAS" + String.valueOf(ammeterID) + " where ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sdf.format(valueTime));

			rs = ps.executeQuery();

			if (rs.next())
			{
				amMeterPDDatasModel = new AmMeterPDDatasModel();

				amMeterPDDatasModel.setAmMeter_ID(ammeterID);
				amMeterPDDatasModel.setRecordTime(valueTime);

				amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
				amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
				amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
				amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));

				amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
				amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
				amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
				amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));

				amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
				amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
				amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
				amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));

				amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
				amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
				amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));

				amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
				amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
				amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
				amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));

				amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
				amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
				amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
				amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		//
		// //ps.setInt(1, ammeterID);
		// ps.setString(1, sdf.format(valueTime));
		//
		// ResultSet rs = ps.executeQuery();
		//
		// if (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		//
		// amMeterPDDatasModel.setAmMeter_ID(ammeterID);
		// amMeterPDDatasModel.setRecordTime(valueTime);
		//
		// amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
		// amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
		// amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
		// amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));
		//
		// amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
		// amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
		// amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
		// amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));
		//
		// amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
		// amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
		// amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
		// amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));
		//
		// amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
		// amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
		// amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));
		//
		// amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
		// amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
		// amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
		// amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));
		//
		// amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
		// amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
		// amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
		// amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));
		//
		// }

		return amMeterPDDatasModel;
	}

	public ArrayList<AmMeterPDDatasModel> getAmMeterPDData_Day(int ammeterID, Date valueTime) throws SQLException
	{
		AmMeterPDDatasModel amMeterPDDatasModel = null;
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		// SimpleDateFormat sdfSubTime=new
		// SimpleDateFormat("HH:mm:ss");//想法很美好，但是不能截取时分秒，要用Calendar

		ArrayList<AmMeterPDDatasModel> list = new ArrayList<AmMeterPDDatasModel>();
		String sql = "select * from ZPDDATAS" + String.valueOf(ammeterID) + " where TO_CHAR(ValueTime,'yyyy-mm-dd') = '" + sdfDay.format(valueTime)
				+ "'";
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
				amMeterPDDatasModel = new AmMeterPDDatasModel();

				amMeterPDDatasModel.setAmMeter_ID(ammeterID);
				amMeterPDDatasModel.setRecordTime(valueTime);

				Date time = rs.getTimestamp("ValueTime");
				amMeterPDDatasModel.setSubTime((time.getHours() < 10 ? ("0" + time.getHours()) : time.getHours()) + ":"
						+ (time.getMinutes() < 10 ? ("0" + time.getMinutes()) : time.getMinutes()) + ":"
						+ (time.getSeconds() < 10 ? ("0" + time.getSeconds()) : time.getSeconds()));
				amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
				amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
				amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
				amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));

				amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
				amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
				amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
				amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));

				amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
				amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
				amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
				amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));

				amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
				amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
				amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));

				amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
				amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
				amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
				amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));

				amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
				amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
				amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
				amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));

				list.add(amMeterPDDatasModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// ResultSet rs = ps.executeQuery();
		//
		// while (rs.next())
		// {
		// amMeterPDDatasModel = new AmMeterPDDatasModel();
		//
		// amMeterPDDatasModel.setAmMeter_ID(ammeterID);
		// amMeterPDDatasModel.setRecordTime(valueTime);
		//
		// Date time = rs.getTimestamp("ValueTime");
		// amMeterPDDatasModel.setSubTime((time.getHours() < 10 ? ("0" +
		// time.getHours()) : time.getHours()) + ":"
		// + (time.getMinutes() < 10 ? ("0" + time.getMinutes()) :
		// time.getMinutes()) + ":"
		// + (time.getSeconds() < 10 ? ("0" + time.getSeconds()) :
		// time.getSeconds()));
		// amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
		// amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
		// amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
		// amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));
		//
		// amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
		// amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
		// amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
		// amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));
		//
		// amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
		// amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
		// amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
		// amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));
		//
		// amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
		// amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
		// amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));
		//
		// amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
		// amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
		// amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
		// amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));
		//
		// amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
		// amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
		// amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
		// amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));
		//
		// list.add(amMeterPDDatasModel);
		// }
		return list;
	}

	/**
	 * 获取回路的三项合格率，包括电流、电压、有功功率和无功功率
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getSXHGL(String time, int ammID) throws SQLException
	{
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(2);
		Map<String, String> map = new HashMap<String, String>();
		int current = 0;
		int voltage = 0;
		int powery = 0;
		int powerw = 0;
		int total = 0;

		String sql = "{ call PD_UNBALTotal(?,?,?,?,?,?,?) }";
		Connection conn = null;
		CallableStatement proc = null;
		try
		{
			conn = ConnDB.getConnection();
			proc = conn.prepareCall(sql);
			proc.setString(1, time);
			proc.setInt(2, ammID);
			proc.registerOutParameter(3, Types.INTEGER);
			proc.registerOutParameter(4, Types.INTEGER);
			proc.registerOutParameter(5, Types.INTEGER);
			proc.registerOutParameter(6, Types.INTEGER);
			proc.registerOutParameter(7, Types.INTEGER);
			proc.execute();

			current = proc.getInt(3);
			voltage = proc.getInt(4);
			powery = proc.getInt(5);
			powerw = proc.getInt(6);
			total = proc.getInt(7);

			if (total == 0)
			{
				map.put("current", 0 + "");
				map.put("voltage", 0 + "");
				map.put("powery", 0 + "");
				map.put("powerw", 0 + "");
			} else
			{
				map.put("current", current * 1.0 / total * 100 + "");
				map.put("voltage", voltage * 1.0 / total * 100 + "");
				map.put("powery", powery * 1.0 / total * 100 + "");
				map.put("powerw", powerw * 1.0 / total * 100 + "");
				/*
				 * map.put("current", nf.format((current*1.0/total)));
				 * map.put("voltage", nf.format((voltage*1.0/total)));
				 * map.put("powery", nf.format((powery*1.0/total)));
				 * map.put("powerw", nf.format((powerw*1.0/total)));
				 */
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, proc);
		}

		return map;
	}

	/**
	 * 获取最新的数据显示在三相仪器上
	 * 
	 * @throws SQLException
	 */
	public AmMeterPDDatasModel getLastConnData(int ammID) throws SQLException
	{
		AmMeterPDDatasModel amMeterPDDatasModel = null;
		String sql = "select * from (select * from ZPDDATAS" + String.valueOf(ammID) + " order by ValueTime desc ) where rownum<=1";
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
				amMeterPDDatasModel = new AmMeterPDDatasModel();

				amMeterPDDatasModel.setAmMeter_ID(ammID);

				amMeterPDDatasModel.setPowerZY(rs.getDouble("PowerZY"));
				amMeterPDDatasModel.setPowerAY(rs.getDouble("PowerAY"));
				amMeterPDDatasModel.setPowerBY(rs.getDouble("PowerBY"));
				amMeterPDDatasModel.setPowerCY(rs.getDouble("PowerCY"));

				amMeterPDDatasModel.setPowerZW(rs.getDouble("PowerZW"));
				amMeterPDDatasModel.setPowerAW(rs.getDouble("PowerAW"));
				amMeterPDDatasModel.setPowerBW(rs.getDouble("PowerBW"));
				amMeterPDDatasModel.setPowerCW(rs.getDouble("PowerCW"));

				amMeterPDDatasModel.setPowerFactorA(rs.getDouble("PowerFactorA"));
				amMeterPDDatasModel.setPowerFactorB(rs.getDouble("PowerFactorB"));
				amMeterPDDatasModel.setPowerFactorC(rs.getDouble("PowerFactorC"));
				amMeterPDDatasModel.setPowerFactorZ(rs.getDouble("PowerFactorZ"));

				amMeterPDDatasModel.setVoltageA(rs.getDouble("VoltageA"));
				amMeterPDDatasModel.setVoltageB(rs.getDouble("VoltageB"));
				amMeterPDDatasModel.setVoltageC(rs.getDouble("VoltageC"));

				amMeterPDDatasModel.setCurrent0(rs.getDouble("Current0"));
				amMeterPDDatasModel.setCurrentA(rs.getDouble("CurrentA"));
				amMeterPDDatasModel.setCurrentB(rs.getDouble("CurrentB"));
				amMeterPDDatasModel.setCurrentC(rs.getDouble("CurrentC"));

				amMeterPDDatasModel.setPowerSZZ(rs.getDouble("POWERSZZ"));
				amMeterPDDatasModel.setPowerSZA(rs.getDouble("POWERSZA"));
				amMeterPDDatasModel.setPowerSZB(rs.getDouble("POWERSZB"));
				amMeterPDDatasModel.setPowerSZC(rs.getDouble("POWERSZC"));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return amMeterPDDatasModel;
	}

	/**
	 * 获取电表示数，首次加载显示
	 * 
	 * @throws SQLException
	 */
	public String getLastDanData(int ammID) throws SQLException
	{
		String value = "0.00";
		String sql = "select * from (select * from ZAMDATAS" + String.valueOf(ammID) + " order by ValueTime desc ) where rownum<=1";
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
				value = rs.getString("ZValueZY");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return value;
	}

	/**
	 * 获取树中加载flash的xml
	 * 
	 * @param partID
	 * @return
	 * @throws SQLException
	 */
	public String getFlashXML(int partID) throws SQLException
	{
		String value = "";
		String sql = "select XMLName from PD_PartInfo01 where Part_ID=" + partID;
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
				value = rs.getString("XMLName");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return value;
	}

	/**
	 * 通过配电ID查询配电num
	 * 
	 * @param partID
	 * @return
	 * @throws SQLException
	 */
	public String getPDPartNum(int partID) throws SQLException
	{
		String value = "";
		String sql = "";

		sql = "select Part_Num from PD_Part where Part_ID=" + partID;
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
				value = rs.getString("Part_Num");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		// Statement st = ConnDB.getConnection().createStatement();
		// ResultSet rs = st.executeQuery(sql);
		// if (rs.next())
		// {
		// value = rs.getString("Part_Num");
		// }
		// if (rs != null)
		// {
		// rs.close();
		// }
		// if (st != null)
		// {
		// st.close();
		// }

		return value;
	}

	/**
	 * 获取树中加载flash的xml
	 * 
	 * @param partName
	 * @return
	 * @throws SQLException
	 */
	public String getFlashXMLByName(String partName) throws SQLException
	{
		String value = "";
		String sql = "select xmlname from pd_partinfo01 where part_id=(select part_id from pd_part where part_Name='" + partName + "')";
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
				value = rs.getString("XMLName");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return value;
	}

	/**
	 * 获取树中加载flash的xml
	 * 
	 * @param partName
	 * @return
	 * @throws SQLException
	 */
	public String getHLData(String partName) throws SQLException
	{
		String value = "";
		String sql = "select xmlname from pd_partinfo01 where part_id=(select part_id from pd_part where part_Name='" + partName + "')";
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
				value = rs.getString("XMLName");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return value;
	}

	public HashMap<Integer, HashMap<String, String>> getTempWETDevDatas(String partStyle_ID, int part_ID, String date) throws SQLException
	{
		HashMap<Integer, HashMap   <String, String>> lists = new HashMap<Integer, HashMap<String, String>>();
		String starttime = date + " 00:00:00";
		String endtime = date + " 23:59:59";
		String sql = "select a.*,c.PART_NAME Part_Name from TEMPWETDEVDATAS a, TEMPWETDEV b, PD_PART c where c.PART_ID=b.Part_ID and b.TEMPWETDEV_ID=a.TEMPWETDEV_ID and a.VALUETIME between to_date(?,'yyyy-mm-dd hh24-mi-ss') and to_date(?,'yyyy-mm-dd hh24-mi-ss')  and c.PARTSTYLE_ID=? and c.PART_ID=? order by a.VALUETIME";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, starttime);
			ps.setString(2, endtime);
			ps.setString(3, partStyle_ID);
			ps.setInt(4, part_ID);
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next())
			{
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("TempWetDev_ID", rs.getString("TempWetDev_ID"));
				map.put("ValueTime", rs.getString("ValueTime"));
				map.put("TempValue", rs.getString("TempValue"));
				map.put("WetValue", rs.getString("WetValue"));
				map.put("Part_Name", rs.getString("Part_Name"));
				lists.put(count, map);
				count++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lists;
	}

	public List<TempWETDevDatasModel> getTempWETDevDatasHistory(int part_ID, String starttime, String endtime) throws SQLException
	{
		List<TempWETDevDatasModel> lists = new ArrayList<TempWETDevDatasModel>();
		starttime += ":00";
		endtime += ":59";
		String sql = "select a.TEMPWETDEV_ID,to_char(a.VALUETIME,'yyyy-mm-dd hh24:mi:ss') VALUETIME,a.TEMPVALUE,a.WETVALUE,c.PART_NAME Part_Name from TEMPWETDEVDATAS a, TEMPWETDEV b, PD_PART c where c.PART_ID=b.Part_ID and b.TEMPWETDEV_ID=a.TEMPWETDEV_ID and a.VALUETIME between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')  and c.PART_ID=? order by a.VALUETIME";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, starttime);
			ps.setString(2, endtime);

			ps.setInt(3, part_ID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				HashMap<String, String> map = new HashMap<String, String>();
				TempWETDevDatasModel tp = new TempWETDevDatasModel();
				tp.setTempWetDevID(rs.getInt("TempWetDev_ID"));
				tp.setRecordTimeDate(df.parse(rs.getString("ValueTime")));
				tp.setTemperature(rs.getFloat("TempValue"));
				tp.setWetvalue(rs.getFloat("WetValue"));
				lists.add(tp);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lists;
	}

	public List<CurrentPartDatasModel> getPowerNetLineCurrentDatas(int partid)
	{
		List<CurrentPartDatasModel> lists = new ArrayList<CurrentPartDatasModel>();
		//String sql = "select a.*,b.jlid from PD_part a left join PD_partInfo03 b on a.part_ID=b.part_ID  START WITH part_parent = "+ partid+" CONNECT BY PRIOR a.part_ID = part_parent ORDER BY a.PART_ID asc" ;
		String sql = "select a.*,b.jlid from PD_part a left join PD_partInfo03 b on a.part_ID=b.part_ID  where (part_parent = "+ partid+" or  a.part_ID="+partid+") and a.partstyle_ID='03' ORDER BY a.PART_ID asc" ;
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
				CurrentPartDatasModel co = new CurrentPartDatasModel();

				String partName = rs.getString("PART_NAME");
				co.setPartName(partName);
				int partID = rs.getInt("Part_ID");
				co.setPartID(partID);
				String JLID = rs.getString("JLID");
				if (JLID != "" && JLID != null)
				{
					String sqlAM = "select nvl(to_char(valuetime,'yyyy-MM-dd hh24:mi:ss'),'-') valuetime,ZVALUEZY from ZAMDATAS" + JLID
							+ " where valuetime=(select max(valuetime) from ZAMDATAS" + JLID + ")";
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					try
					{
						ps1 = conn.prepareStatement(sqlAM);
						rs1 = ps1.executeQuery();
						while (rs1.next())
						{
							co.setZvalueZY(rs1.getFloat("ZVALUEZY"));
							co.setValueTime(rs1.getString("valuetime"));
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(ps1, rs1);
					}

					String sqlPD = "select *  from ZPDDATAS" + JLID + " where valuetime=(select max(valuetime) from ZPDDATAS" + JLID + ") ";

					PreparedStatement ps2 = null;
					ResultSet rs2 = null;
					try
					{
						ps2 = conn.prepareStatement(sqlPD);
						rs2 = ps2.executeQuery();
						while (rs2.next())
						{
							co.setIa(rs2.getFloat("CurrentA"));
							co.setIb(rs2.getFloat("CurrentB"));
							co.setIc(rs2.getFloat("CurrentC"));

							co.setUa(rs2.getFloat("VOLTAGEA"));
							co.setUb(rs2.getFloat("VOLTAGEB"));
							co.setUc(rs2.getFloat("VOLTAGEC"));

							co.setP(rs2.getFloat("POWERZY"));
							co.setQ(rs2.getFloat("POWERZW"));

							co.setPF(rs2.getFloat("POWERFACTORZ"));
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release(ps2, rs2);
					}
				}
				lists.add(co);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lists;
	}

	public Map<String, String> getCurrentTempWet(int partID)
	{
		Map<String, String> map = new HashMap<String, String>();
		String sql = "SELECT tempValue,WetValue,to_char(valueTime,'yyyy-MM-dd hh24:mi:ss') valueTime FROM TEMPWETDEVDATAS A, TEMPWETDEV B WHERE     A.TEMPWETDEV_ID = B.TEMPWETDEV_ID AND B.PART_ID = "
				+ partID
				+ " AND VALUETIME =(SELECT MAX (VALUETIME) FROM TEMPWETDEVDATAS A, TEMPWETDEV B WHERE A.TEMPWETDEV_ID = B.TEMPWETDEV_ID AND B.PART_ID = "
				+ partID + ")";
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

				map.put("tempValue", rs.getString("TempValue"));
				map.put("valueTime", rs.getString("ValueTime"));
				map.put("WetValue", rs.getString("WETVALUE"));
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
	public List<PDPartRoom> getAllPartRoom()
	{
		List<PDPartRoom> list=new ArrayList<PDPartRoom>();
		String sql = "select a.*,b.part_name from PD_PartInfo01 a,PD_Part b where a.part_id=b.part_id ";
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
				PDPartRoom pdRoom=new PDPartRoom();
				pdRoom.setPart_id(rs.getInt("part_id"));
				pdRoom.setPart_Name(rs.getString("part_name"));
				pdRoom.setXMLName(rs.getString("XMLNAME"));
				list.add(pdRoom);
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
	public PDPartRoom getPartRoomByID(int Part_Id)
	{
		PDPartRoom pdRoom=new PDPartRoom();
		String sql = "select part_id,part_name,PART_NUM,PARTSTYLE_ID from PD_Part where  part_id="+Part_Id;
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
				pdRoom.setPart_id(rs.getInt("part_id"));
				pdRoom.setPart_Name(rs.getString("part_name"));
//				pdRoom.setXMLName(rs.getString("XMLNAME"));
				pdRoom.setPart_Num(rs.getString("PART_NUM"));
				pdRoom.setPartStyle_ID(rs.getString("PARTSTYLE_ID"));
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return pdRoom;
	}
}
