package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.powernet.model.AmnoBalanceModel;
import com.sf.energy.powernet.model.PWnoBalanceModel;
import com.sf.energy.powernet.model.PYnoBalanceModel;
import com.sf.energy.powernet.model.VnoBalanceModel;
import com.sf.energy.util.ConnDB;

/**
 * 
 * @author 蒯越
 *
 */
public class AmMeterPDDatasDao
{

	/**
	 * 通过回路和日期时间查询该天内不同时刻的三相电流以及电流不平衡率
	 * 
	 * @param huiluID
	 *            回路ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @return 结果集
	 * @throws SQLException
	 */
	public AmMeterPDDatasModel getAnobalanceAnalysis(int huiluID, int year, int month, int day) throws SQLException
	{
		AmMeterPDDatasModel apdm = null;

		String sql = "select CurrentA,CurrentB,CurrentC,Current0,to_char(VALUETIME,'hh:mm:ss')TIME" + " from AmMeterPDDatas,PD_PartInfo03 "
				+ "where AmMeterPDDatas.AmMeter_ID = PD_PartInfo03.JLID and Part_ID = ? "
				+ "and extract(year from valuetime) = ? and extract(month from valuetime) = ? " + "and extract(day from valuetime) = ? ";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			rs = ps.executeQuery();			
			ps.setInt(1, huiluID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			ps.setInt(4, day);
			rs = ps.executeQuery();

			while (rs.next())
			{
				apdm = new AmMeterPDDatasModel();
				apdm.setCurrentA(rs.getFloat("CurrentA"));
				apdm.setCurrentB(rs.getFloat("CurrentB"));
				apdm.setCurrentC(rs.getFloat("CurrentC"));
				apdm.setCurrent0(rs.getFloat("Current0"));
				apdm.setTime(rs.getString("TIME"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
//		ps.setInt(1, huiluID);
//		ps.setInt(2, year);
//		ps.setInt(3, month);
//		ps.setInt(4, day);
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			apdm = new AmMeterPDDatasModel();
//			apdm.setCurrentA(rs.getFloat("CurrentA"));
//			apdm.setCurrentB(rs.getFloat("CurrentB"));
//			apdm.setCurrentC(rs.getFloat("CurrentC"));
//			apdm.setCurrent0(rs.getFloat("Current0"));
//			apdm.setTime(rs.getString("TIME"));
//		}
//		if (rs != null)
//			rs.close();
//		if (ps != null)
//			ps.close();

		return apdm;
	}

	/**
	 * 通过电表ID，查询指定年月日里面每个小时电流的不平衡率（包含A,B,C相电流值）
	 * 
	 * @param am_ID
	 *            电表ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<AmnoBalanceModel> getAmNobalancedata(int am_ID, int year, int month, int day, int hour) throws SQLException
	{
		List<AmnoBalanceModel> list = null;
		list = new ArrayList<AmnoBalanceModel>();
		AmnoBalanceModel anbm = null;
		float a = 0; // A相电流
		float b = 0; // B相电流
		float c = 0; // C相电流
		float t1 = 0; // 转换参数
		float t2 = 0; // 转换参数
		float rate = 0; // 不平衡率

		String sql = "select VALUETIME,CURRENTA,CURRENTB,CURRENTC from ZPDDATAS" + String.valueOf(am_ID) + " where "
				+ "extract(year from Valuetime)= ? " + "and extract(month from valuetime)= ? and extract(day from valuetime)= ? "
				+ "and TO_CHAR(valuetime,'HH24')= ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			rs = ps.executeQuery();			
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);

			rs = ps.executeQuery();
			while (rs.next())
			{
				anbm = new AmnoBalanceModel();

				a = rs.getFloat("CURRENTA");
				b = rs.getFloat("CURRENTB");
				c = rs.getFloat("CURRENTC");

				t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
				t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
				rate = t2 / (2 * t1) * 100;

				anbm.setCURRENTA(a);
				anbm.setCURRENTB(b);
				anbm.setCURRENTC(c);
				anbm.setNobalanceRate(rate);
				anbm.setValuetime(rs.getString("VALUETIME"));
				list.add(anbm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}

	/**
	 * 查询全校指定年月日里面每个小时电流的不平衡率（包含A,B,C相电流值）
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<AmnoBalanceModel> getAllAmNobalancedata(int year, int month, int day, int hour) throws SQLException
	{
		List<AmnoBalanceModel> list = null;
		list = new ArrayList<AmnoBalanceModel>();
		AmnoBalanceModel anbm = null;
		float a = 0; // A相电流
		float b = 0; // B相电流
		float c = 0; // C相电流
		float t1 = 0; // 转换参数
		float t2 = 0; // 转换参数
		float rate = 0; // 不平衡率

		String sql = "select JLID AMMETER_ID from PD_PARTINFO03";
		Connection conn1 =null;
		PreparedStatement ps1=null;
		ResultSet rs1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("AMMETER_ID");
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select VALUETIME,CURRENTA,CURRENTB,CURRENTC from ZPDDATAS" + ammeterid + " where extract(year from Valuetime)= ? "
							+ "and extract(month from valuetime)= ? and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";

					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, year);
						ps.setInt(2, month);
						ps.setInt(3, day);
						ps.setInt(4, hour);

						rs = ps.executeQuery();
						while (rs.next())
						{
							anbm = new AmnoBalanceModel();

							a = rs.getFloat("CURRENTA");
							b = rs.getFloat("CURRENTB");
							c = rs.getFloat("CURRENTC");

							t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
							t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
							rate = t2 / (2 * t1) * 100;

							anbm.setCURRENTA(a);
							anbm.setCURRENTB(b);
							anbm.setCURRENTC(c);
							anbm.setNobalanceRate(rate);
							anbm.setValuetime(rs.getString("VALUETIME"));
							list.add(anbm);
						}

					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}
					
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}

		return list;
	}

	/**
	 * 通过根ID查出下面所有子回路ID
	 * 
	 * @param parent_ID
	 *            根节点
	 * @return list集合
	 * @throws SQLException
	 */
	public List<Integer> getHuiluByParent(int parent_ID) throws SQLException
	{
		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT part_id FROM pd_part where PARTSTYLE_ID='03' START WITH part_parent=? CONNECT BY PRIOR part_id = part_parent ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, parent_ID);

			rs = ps.executeQuery();
			while (rs.next())
			{
				list.add(rs.getInt("part_id"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		return list;
	}

	/**
	 * 通过电表ID，查询指定年月日里面每个小时电压的不平衡率（包含A,B,C相电压值）
	 * 
	 * @param am_ID
	 *            电表ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<VnoBalanceModel> getVNobalancedata(int am_ID, int year, int month, int day, int hour) throws SQLException
	{
		List<VnoBalanceModel> list = null;
		list = new ArrayList<VnoBalanceModel>();
		VnoBalanceModel vnbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double vrate = 0; // 电压不平衡率

		String sql = "select VALUETIME,VoltageA,VoltageB,VoltageC from ZPDDATAS" + String.valueOf(am_ID) + " where "
				+ "extract(year from Valuetime)= ? " + "and extract(month from valuetime)= ? and extract(day from valuetime)= ? "
				+ "and TO_CHAR(valuetime,'HH24')= ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);

			rs = ps.executeQuery();
			while (rs.next())
			{
				vnbm = new VnoBalanceModel();

				a = rs.getFloat("VoltageA");
				b = rs.getFloat("VoltageB");
				c = rs.getFloat("VoltageC");

				t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
				t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
				vrate = t2 / (2 * t1) * 100;

				vnbm.setVoltageA(a);
				vnbm.setVoltageB(b);
				vnbm.setVoltageC(c);
				vnbm.setVrate(vrate);
				vnbm.setValuetime(rs.getString("VALUETIME"));
				list.add(vnbm);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	

		return list;
	}

	/**
	 * 查询全校指定年月日里面每个小时电压的不平衡率（包含A,B,C相电压值）
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<VnoBalanceModel> getAllVNobalancedata(int year, int month, int day, int hour) throws SQLException
	{
		List<VnoBalanceModel> list = null;
		list = new ArrayList<VnoBalanceModel>();
		VnoBalanceModel vnbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double vrate = 0; // 电压不平衡率

		String sql = "select JLID AMMETER_ID from PD_PARTINFO03";
		Connection conn1 = null;
		PreparedStatement ps1=null;
		ResultSet rs1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("AMMETER_ID");
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select VALUETIME,VoltageA,VoltageB,VoltageC from ZPDDATAS" + ammeterid + " where extract(year from Valuetime)= ? "
							+ "and extract(month from valuetime)= ? and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";

					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, year);
						ps.setInt(2, month);
						ps.setInt(3, day);
						ps.setInt(4, hour);

						rs = ps.executeQuery();
						while (rs.next())
						{
							vnbm = new VnoBalanceModel();

							a = rs.getFloat("VoltageA");
							b = rs.getFloat("VoltageB");
							c = rs.getFloat("VoltageC");

							t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
							t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
							vrate = t2 / (2 * t1) * 100;

							vnbm.setVoltageA(a);
							vnbm.setVoltageB(b);
							vnbm.setVoltageC(c);
							vnbm.setVrate(vrate);
							vnbm.setValuetime(rs.getString("VALUETIME"));
							list.add(vnbm);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}
					
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return list;
	}

	/**
	 * 通过电表ID，查询指定年月日里每个小时里面电流电压的数据点的条数
	 * 
	 * @param am_ID
	 *            电表ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @param hour
	 *            小时
	 * @return 记录条数
	 * @throws SQLException
	 */
	public int getcountNo(int am_ID, int year, int month, int day, int hour) throws SQLException
	{
		int count = 0;

		String sql = "select count(rownum) from ZPDDATAS" + String.valueOf(am_ID) + " where "
				+ "extract(year from Valuetime)=? and extract(month from valuetime)= ? "
				+ "and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);

			rs = ps.executeQuery();
			while (rs.next())
			{
				count = rs.getInt("count(rownum)");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		return count;
	}

	/**
	 * 查询全校指定年月日里每个小时里面电流电压的数据点的条数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public int getAllcountNo(int year, int month, int day, int hour) throws SQLException
	{
		int count = 0;

		String sql = "select JLID AMMETER_ID from PD_PARTINFO03";
		Connection conn1 = null;
		PreparedStatement ps1=null;
		ResultSet rs1 =null;
		try
		{
			conn1 =ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("AMMETER_ID");
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select count(rownum) from ZPDDATAS" + ammeterid + " where "
							+ " extract(year from Valuetime)=? and extract(month from valuetime)= ? "
							+ "and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";
					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, year);
						ps.setInt(2, month);
						ps.setInt(3, day);
						ps.setInt(4, hour);

						rs = ps.executeQuery();
						while (rs.next())
						{
							count = rs.getInt("count(rownum)");
						}

					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}
					
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return count;
	}
	/**
	 * 通过电表ID，查询指定年月日里面每个小时有功功率的不平衡率（包含A,B,C相有功功率值）
	 * 
	 * @param am_ID
	 *            电表ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<PYnoBalanceModel> getPYNobalancedata(int am_ID, int year, int month, int day, int hour) throws SQLException
	{
		List<PYnoBalanceModel> list = null;
		list = new ArrayList<PYnoBalanceModel>();
		PYnoBalanceModel pynbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double pyrate = 0; // 电压不平衡率

		String sql = "select VALUETIME,PowerAY,PowerBY,PowerCY from ZPDDATAS" + String.valueOf(am_ID) + " where "
				+ "extract(year from Valuetime)= ? " + "and extract(month from valuetime)= ? and extract(day from valuetime)= ? "
				+ "and TO_CHAR(valuetime,'HH24')= ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);

			rs = ps.executeQuery();
			while (rs.next())
			{
				pynbm = new PYnoBalanceModel();

				a = rs.getFloat("PowerAY");
				b = rs.getFloat("PowerBY");
				c = rs.getFloat("PowerCY");

				t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
				t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
				pyrate = t2 / (2 * t1) * 100;

				pynbm.setPowerAY(a);
				pynbm.setPowerBY(b);
				pynbm.setPowerCY(c);
				pynbm.setPYrate(pyrate);
				pynbm.setValuetime(rs.getString("VALUETIME"));
				list.add(pynbm);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		return list;
	}

	/**
	 * 查询全校指定年月日里面每个小时有功功率的不平衡率（包含A,B,C相有功功率值）
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<PYnoBalanceModel> getAllPYNobalancedata(int year, int month, int day, int hour) throws SQLException
	{
		List<PYnoBalanceModel> list = null;
		list = new ArrayList<PYnoBalanceModel>();
		PYnoBalanceModel pynbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double pyrate = 0; // 电压不平衡率

		String sql = "select JLID AMMETER_ID from PD_PARTINFO03";
		Connection conn1 = null;
		PreparedStatement ps1=null;
		ResultSet rs1 =null;
		try
		{
			conn1 =ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("AMMETER_ID");
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select VALUETIME,PowerAY,PowerBY,PowerCY from ZPDDATAS" + ammeterid + " where extract(year from Valuetime)= ? "
							+ "and extract(month from valuetime)= ? and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";

					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, year);
						ps.setInt(2, month);
						ps.setInt(3, day);
						ps.setInt(4, hour);
						
						rs = ps.executeQuery();
						while (rs.next())
						{
							pynbm = new PYnoBalanceModel();

							a = rs.getFloat("PowerAY");
							b = rs.getFloat("PowerBY");
							c = rs.getFloat("PowerCY");

							t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
							t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
							pyrate = t2 / (2 * t1) * 100;

							pynbm.setPowerAY(a);
							pynbm.setPowerBY(b);
							pynbm.setPowerCY(c);
							pynbm.setPYrate(pyrate);
							pynbm.setValuetime(rs.getString("VALUETIME"));
							list.add(pynbm);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}
					
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return list;
	}
	/**
	 * 通过电表ID，查询指定年月日里面每个小时无功功率的不平衡率（包含A,B,C相无功功率值）
	 * 
	 * @param am_ID
	 *            电表ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param day
	 *            日期
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<PWnoBalanceModel> getPWNobalancedata(int am_ID, int year, int month, int day, int hour) throws SQLException
	{
		List<PWnoBalanceModel> list = null;
		list = new ArrayList<PWnoBalanceModel>();
		PWnoBalanceModel pwnbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double pwrate = 0; // 电压不平衡率

		String sql = "select VALUETIME,PowerAW,PowerBW,PowerCW from ZPDDATAS" + String.valueOf(am_ID) + " where "
				+ "extract(year from Valuetime)= ? " + "and extract(month from valuetime)= ? and extract(day from valuetime)= ? "
				+ "and TO_CHAR(valuetime,'HH24')= ?";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);

			rs = ps.executeQuery();
			while (rs.next())
			{
				pwnbm = new PWnoBalanceModel();

				a = rs.getFloat("PowerAW");
				b = rs.getFloat("PowerBW");
				c = rs.getFloat("PowerCW");

				t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
				t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
				pwrate = t2 / (2 * t1) * 100;

				pwnbm.setPowerAW(a);
				pwnbm.setPowerBW(b);
				pwnbm.setPowerCW(c);
				pwnbm.setPWrate(pwrate);
				pwnbm.setValuetime(rs.getString("VALUETIME"));
				list.add(pwnbm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		return list;
	}

	/**
	 * 查询全校指定年月日里面每个小时无功功率的不平衡率（包含A,B,C相无功功率值）
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<PWnoBalanceModel> getAllPWNobalancedata(int year, int month, int day, int hour) throws SQLException
	{
		List<PWnoBalanceModel> list = null;
		list = new ArrayList<PWnoBalanceModel>();
		PWnoBalanceModel pwnbm = null;
		double a = 0; // A相电压
		double b = 0; // B相电压
		double c = 0; // C相电压
		double t1 = 0; // 转换参数
		double t2 = 0; // 转换参数
		double pwrate = 0; // 电压不平衡率

		String sql = "select JLID AMMETER_ID from PD_PARTINFO03";
		Connection conn1 = null;
		PreparedStatement ps1=null;
		ResultSet rs1=null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next())
			{
				String ammeterid = rs1.getString("AMMETER_ID");
				if ((ammeterid != null) && (!"".equals(ammeterid)))
				{
					sql = "select VALUETIME,PowerAW,PowerBW,PowerCW from ZPDDATAS" + ammeterid + " where extract(year from Valuetime)= ? "
							+ "and extract(month from valuetime)= ? and extract(day from valuetime)= ? and TO_CHAR(valuetime,'HH24')= ?";

					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setInt(1, year);
						ps.setInt(2, month);
						ps.setInt(3, day);
						ps.setInt(4, hour);

						rs = ps.executeQuery();
						while (rs.next())
						{
							pwnbm = new PWnoBalanceModel();

							a = rs.getFloat("PowerAW");
							b = rs.getFloat("PowerBW");
							c = rs.getFloat("PowerCW");

							t1 = (Math.abs(a) + Math.abs(b) + Math.abs(c)) / 3;
							t2 = Math.abs(a - t1) + Math.abs(b - t1) + Math.abs(c - t1);
							pwrate = t2 / (2 * t1) * 100;

							pwnbm.setPowerAW(a);
							pwnbm.setPowerBW(b);
							pwnbm.setPowerCW(c);
							pwnbm.setPWrate(pwrate);
							pwnbm.setValuetime(rs.getString("VALUETIME"));
							list.add(pwnbm);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}
					
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return list;
	}

	/**
	 * 通过回路查询电表ID
	 * 
	 * @param huilu
	 *            回路ID
	 * @return 电表ID
	 * @throws SQLException
	 */
	/*
	 * public int getAmmeterID(int huilu) throws SQLException { int amID = 0;
	 * 
	 * String sql = "select  JLID FROM PD_PARTINFO03 WHERE PART_ID = ?";
	 * 
	 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
	 * ps.setInt(1, huilu);
	 * 
	 * ResultSet rs = ps.executeQuery(); while(rs.next()) { amID =
	 * rs.getInt("JLID"); } return amID; }
	 */
}
