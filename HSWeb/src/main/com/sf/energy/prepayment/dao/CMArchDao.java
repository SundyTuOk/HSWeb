package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.sf.energy.prepayment.model.CMArchModel;
import com.sf.energy.util.ConnDB;

public class CMArchDao
{
	private int totalCount = 0;
	private float limitValue = 8;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public float getLimitValue()
	{
		return limitValue;
	}

	public void setLimitValue(float limitValue)
	{
		this.limitValue = limitValue;
	}

	public ArrayList<CMArchModel> queryInfoByArchID(int pageCount, int pageIndex, int archID) throws SQLException, ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<CMArchModel> list = new ArrayList<CMArchModel>();
		CMArchModel cmm = null;

		// Date date = new Date();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE); // 日
		int month = cal.get(Calendar.MONTH) + 1;// 月
		int year = cal.get(Calendar.YEAR); // 年

		String sql = "select " + "a.*,nvl(d.ZGross,0) DayGross,"
				+ "to_char((select max(ValueTime) from (AmMeterAPDatas)c where a.AmMeter_ID=c.AmMeter_ID),'yyyy-mm-dd hh24:mi:ss')MaxValueTime "
				+ "from " + "(AmMeter)a " + "left join " + "(" + "select " + "AmMeter_ID,ZGross " + "from " + "T_DayAm " + "where " + "SelectYear="
				+ year + " and SelectMonth=" + month + " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID " + "where "
				+ "a.Architecture_ID=" + archID;
		sql += " order by FLOOR,a.AmMeter_Name";
		// System.out.println(sql);
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			int amMeterID = 0;
			String maxValueTime = "";
			float syValue = 0;
			while (rs.next() && (count > 0))
			{
				cmm = new CMArchModel();

				amMeterID = rs.getInt("AMMETER_ID");
				getBJValue(amMeterID);
				String zValuezy = (new CMMeterDao()).getZValuezyById(amMeterID);
				maxValueTime = rs.getString("MaxValueTime");

				cmm.setAmMeterID(amMeterID);
				cmm.setzValuezy(zValuezy);
				cmm.setAmMeterName(rs.getString("AMMETER_NAME"));
				cmm.setDayGross(rs.getFloat("DayGross"));
				cmm.setAmMeterState(rs.getInt("AMMETER_STAT"));
				cmm.setFloorNum(rs.getInt("FLOOR"));

				String meterPotocol = (new CMMeterDao()).getMeterPotocol(amMeterID);
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterID);
				// 剩余金额-- 后付费要从结算表里面取
				if ("0".equals(AmMeter_CostType))
				{// 后付费--剩余金额
					syValue = Float.parseFloat(getRemainMoney(amMeterID));
					cmm.setRemainValue(syValue);
				} else
				{
					// 预付费
					if (maxValueTime != null && !"".equals(maxValueTime))
					{
						sql = "select nvl(SYValue,0)SYValue,nvl(SYMoney,0)SYMoney,nvl(TZValue,0)TZValue from AmMeterAPDatas where AmMeter_ID=" + amMeterID
								+ " and ValueTime=to_date('" + maxValueTime + "','yyyy-mm-dd hh24:mi:ss')";

						// System.out.println(sql);
						Connection conn1 = null;
						PreparedStatement ps1 =null;
						ResultSet rs1 =null;
						try{
							conn1 = ConnDB.getConnection();
							ps1 = conn1.prepareStatement(sql);
							rs1 = ps1.executeQuery();
							if (rs1.next())
							{
								if("1".equals(meterPotocol)){
									syValue = rs1.getFloat("SYValue");
								}else {
									syValue = rs1.getFloat("SYMoney");
								}
								
							}
							cmm.setRemainValue(syValue);
						}catch (SQLException e)
						{
							e.printStackTrace();
						} finally
						{
							ConnDB.release(conn1, ps1,rs1);
						}
					} else
					{
						cmm.setRemainValue(0);
					}
				}

				if (rs.getInt("AMMETER_STAT") == 0)
				{
					cmm.setColorStyle(4);
				} else
				{
					if (syValue <= 0)
					{
						cmm.setColorStyle(3);
					} else if (syValue < getLimitValue())
					{
						cmm.setColorStyle(2);
					} else
					{
						cmm.setColorStyle(1);
					}
				}

				list.add(cmm);
				count--;
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
	
	public ArrayList<CMArchModel> queryInfoByArchID(int pageCount, int pageIndex, int archID,int year, int month, int day) throws SQLException, ParseException
	{
		ArrayList<CMArchModel> list = new ArrayList<CMArchModel>();
		CMArchModel cmm = null;
		String sql = "select " + "a.*,nvl(d.ZGross,0) DayGross,"
				+ "to_char((select max(ValueTime) from (AmMeterAPDatas)c where a.AmMeter_ID=c.AmMeter_ID),'yyyy-mm-dd hh24:mi:ss')MaxValueTime "
				+ "from " + "(AmMeter)a " + "left join " + "(" + "select " + "AmMeter_ID,ZGross " + "from " + "T_DayAm " + "where " + "SelectYear="
				+ year + " and SelectMonth=" + month + " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID " + "where "
				+ "a.Architecture_ID=" + archID;
		sql += " order by FLOOR,a.AmMeter_Name";
		// System.out.println(sql);
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			int amMeterID = 0;
			String maxValueTime = "";
			float syValue = 0;
			while (rs.next() && (count > 0))
			{
				cmm = new CMArchModel();

				amMeterID = rs.getInt("AMMETER_ID");
				getBJValue(amMeterID);
				String zValuezy = (new CMMeterDao()).getZValuezyById(amMeterID);
				maxValueTime = rs.getString("MaxValueTime");

				cmm.setAmMeterID(amMeterID);
				cmm.setzValuezy(zValuezy);
				cmm.setAmMeterName(rs.getString("AMMETER_NAME"));
				cmm.setDayGross(rs.getFloat("DayGross"));
				cmm.setAmMeterState(rs.getInt("AMMETER_STAT"));
				cmm.setFloorNum(rs.getInt("FLOOR"));

				String meterPotocol = (new CMMeterDao()).getMeterPotocol(amMeterID);
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterID);
				// 剩余金额-- 后付费要从结算表里面取
				if ("0".equals(AmMeter_CostType))
				{// 后付费--剩余金额
					syValue = Float.parseFloat(getRemainMoney(amMeterID));
					cmm.setRemainValue(syValue);
				} else
				{
					// 预付费
					if (maxValueTime != null && !"".equals(maxValueTime))
					{
						sql = "select nvl(SYValue,0)SYValue,nvl(SYMoney,0)SYMoney,nvl(TZValue,0)TZValue from AmMeterAPDatas where AmMeter_ID=" + amMeterID
								+ " and ValueTime=to_date('" + maxValueTime + "','yyyy-mm-dd hh24:mi:ss')";

						// System.out.println(sql);
						Connection conn1 = null;
						PreparedStatement ps1 =null;
						ResultSet rs1 =null;
						try{
							conn1 = ConnDB.getConnection();
							ps1 = conn1.prepareStatement(sql);
							rs1 = ps1.executeQuery();
							if (rs1.next())
							{
								if("1".equals(meterPotocol)){
									syValue = rs1.getFloat("SYValue");
								}else {
									syValue = rs1.getFloat("SYMoney");
								}
								
							}
							cmm.setRemainValue(syValue);
						}catch (SQLException e)
						{
							e.printStackTrace();
						} finally
						{
							ConnDB.release(conn1, ps1,rs1);
						}
					} else
					{
						cmm.setRemainValue(0);
					}
				}

				if (rs.getInt("AMMETER_STAT") == 0)
				{
					cmm.setColorStyle(4);
				} else
				{
					if (syValue <= 0)
					{
						cmm.setColorStyle(3);
					} else if (syValue < getLimitValue())
					{
						cmm.setColorStyle(2);
					} else
					{
						cmm.setColorStyle(1);
					}
				}

				list.add(cmm);
				count--;
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

	public ArrayList<CMArchModel> queryInfoByFloorNum(int pageCount, int pageIndex, int archID, int floorNum) throws SQLException
	{
		ArrayList<CMArchModel> list = new ArrayList<CMArchModel>();
		CMArchModel cmm = null;

		// Date date = new Date();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE); // 日
		int month = cal.get(Calendar.MONTH) + 1;// 月
		int year = cal.get(Calendar.YEAR); // 年

		String sql = "select " + "a.*,nvl(d.ZGross,0) DayGross,"
				+ "to_char((select max(ValueTime) from (AmMeterAPDatas)c where a.AmMeter_ID=c.AmMeter_ID),'yyyy-mm-dd hh24:mi:ss')MaxValueTime "
				+ "from " + "(AmMeter)a " + "left join " + "(" + "select " + "AmMeter_ID,ZGross " + "from " + "T_DayAm " + "where " + "SelectYear="
				+ year + " and SelectMonth=" + month + " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID " + "where "
				+ "a.Architecture_ID=" + archID + "and a.Floor=" + floorNum + " order by a.AmMeter_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			int amMeterID = 0;
			String maxValueTime = "";
			float syValue = 0;
			while (rs.next() && (count > 0))
			{
				cmm = new CMArchModel();

				amMeterID = rs.getInt("AMMETER_ID");
				maxValueTime = rs.getString("MaxValueTime");
				String zValuezy = (new CMMeterDao()).getZValuezyById(amMeterID);
				
				cmm.setAmMeterID(amMeterID);
				cmm.setzValuezy(zValuezy);
				getBJValue(amMeterID);
				cmm.setAmMeterName(rs.getString("AMMETER_NAME"));
				cmm.setDayGross(rs.getFloat("DayGross"));
				cmm.setAmMeterState(rs.getInt("AMMETER_STAT"));
				cmm.setFloorNum(floorNum);

				String meterPoyocol = (new CMMeterDao()).getMeterPotocol(amMeterID);
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterID);
				// 剩余金额-- 后付费要从结算表里面取
				if ("0".equals(AmMeter_CostType))
				{// 后付费--剩余金额
					syValue = Float.parseFloat(getRemainMoney(amMeterID));
					cmm.setRemainValue(syValue);
				} else
				{
					// 预付费
					if (maxValueTime != null && !"".equals(maxValueTime))
					{
						sql = "select nvl(SYValue,0)SYValue,nvl(SYMoney,0)SYMoney,nvl(TZValue,0)TZValue from AmMeterAPDatas where AmMeter_ID=" + amMeterID
								+ " and ValueTime=to_date('" + maxValueTime + "','yyyy-mm-dd hh24:mi:ss')";
						PreparedStatement ps1=null;
						ResultSet rs1=null;
//						Connection conn1 = null;
						try
						{
//							conn1 = ConnDB.getConnection();
							ps1 = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
							rs1 = null;
							rs1 = ps1.executeQuery();
							if (rs1.next())
							{
								if("1".equals(meterPoyocol)){
									syValue = rs1.getFloat("SYValue");
								}else {
									syValue = rs1.getFloat("SYMoney");
								}
								
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release( ps1, rs1);
						}
						cmm.setRemainValue(syValue);
					} else
					{
						cmm.setRemainValue(0);
					}
				}

				if (rs.getInt("AMMETER_STAT") == 0)
				{
					cmm.setColorStyle(4);
				} else
				{
					if (syValue <= 0)
					{
						cmm.setColorStyle(3);
					} else if (syValue < getLimitValue())
					{
						cmm.setColorStyle(2);
					} else
					{
						cmm.setColorStyle(1);
					}
				}

				list.add(cmm);
				count--;
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
	public ArrayList<CMArchModel> queryInfoByFloorNum(int pageCount, int pageIndex, int archID, int floorNum,int year, int month, int day) throws SQLException
	{
		ArrayList<CMArchModel> list = new ArrayList<CMArchModel>();
		CMArchModel cmm = null;

		String sql = "select " + "a.*,nvl(d.ZGross,0) DayGross,"
				+ "to_char((select max(ValueTime) from (AmMeterAPDatas)c where a.AmMeter_ID=c.AmMeter_ID),'yyyy-mm-dd hh24:mi:ss')MaxValueTime "
				+ "from " + "(AmMeter)a " + "left join " + "(" + "select " + "AmMeter_ID,ZGross " + "from " + "T_DayAm " + "where " + "SelectYear="
				+ year + " and SelectMonth=" + month + " and SelectDay=" + day + ") d on d.AmMeter_ID=a.AmMeter_ID " + "where "
				+ "a.Architecture_ID=" + archID + "and a.Floor=" + floorNum + " order by a.AmMeter_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			int amMeterID = 0;
			String maxValueTime = "";
			float syValue = 0;
			while (rs.next() && (count > 0))
			{
				cmm = new CMArchModel();

				amMeterID = rs.getInt("AMMETER_ID");
				maxValueTime = rs.getString("MaxValueTime");
				String zValuezy = (new CMMeterDao()).getZValuezyById(amMeterID);
				
				cmm.setAmMeterID(amMeterID);
				cmm.setzValuezy(zValuezy);
				getBJValue(amMeterID);
				cmm.setAmMeterName(rs.getString("AMMETER_NAME"));
				cmm.setDayGross(rs.getFloat("DayGross"));
				cmm.setAmMeterState(rs.getInt("AMMETER_STAT"));
				cmm.setFloorNum(floorNum);

				String meterPoyocol = (new CMMeterDao()).getMeterPotocol(amMeterID);
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(amMeterID);
				// 剩余金额-- 后付费要从结算表里面取
				if ("0".equals(AmMeter_CostType))
				{// 后付费--剩余金额
					syValue = Float.parseFloat(getRemainMoney(amMeterID));
					cmm.setRemainValue(syValue);
				} else
				{
					// 预付费
					if (maxValueTime != null && !"".equals(maxValueTime))
					{
						sql = "select nvl(SYValue,0)SYValue,nvl(SYMoney,0)SYMoney,nvl(TZValue,0)TZValue from AmMeterAPDatas where AmMeter_ID=" + amMeterID
								+ " and ValueTime=to_date('" + maxValueTime + "','yyyy-mm-dd hh24:mi:ss')";
						PreparedStatement ps1=null;
						ResultSet rs1=null;
//						Connection conn1 = null;
						try
						{
//							conn1 = ConnDB.getConnection();
							ps1 = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
							rs1 = null;
							rs1 = ps1.executeQuery();
							if (rs1.next())
							{
								if("1".equals(meterPoyocol)){
									syValue = rs1.getFloat("SYValue");
								}else {
									syValue = rs1.getFloat("SYMoney");
								}
								
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release( ps1, rs1);
						}
						cmm.setRemainValue(syValue);
					} else
					{
						cmm.setRemainValue(0);
					}
				}

				if (rs.getInt("AMMETER_STAT") == 0)
				{
					cmm.setColorStyle(4);
				} else
				{
					if (syValue <= 0)
					{
						cmm.setColorStyle(3);
					} else if (syValue < getLimitValue())
					{
						cmm.setColorStyle(2);
					} else
					{
						cmm.setColorStyle(1);
					}
				}

				list.add(cmm);
				count--;
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
	
	public void getBJValue(int amMeterID) throws SQLException
	{
		String sql = "select b.bjvalue from ammeter a,price b where a.ammeter_id = " + amMeterID + " and a.price_id = b.price_id";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				if (!"".equals(rs.getString("bjvalue")) && rs.getString("bjvalue") != null)
				{
					setLimitValue(rs.getFloat("bjvalue"));
				} else
				{
					setLimitValue(0);
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
	}

	private String getRemainMoney(int ammID) throws SQLException
	{
		String sYValue = "0";
		String sql = "select ThisRemainMoney from (select ThisRemainMoney from AmMeterJieSuan where AmMeter_ID=" + ammID
				+ " order by ThisTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sYValue = rs.getString("ThisRemainMoney");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return sYValue;
	}

}
