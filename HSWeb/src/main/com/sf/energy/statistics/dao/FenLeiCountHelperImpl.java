package com.sf.energy.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.sf.energy.prepayment.model.ByDayDetailModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.model.EnergyRecycleModel;
import com.sf.energy.statistics.model.FenLeiCountModel;
import com.sf.energy.util.ConnDB;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

public class FenLeiCountHelperImpl implements FenLeiCountHelper
{
	AreaDao areaDao = new AreaDao();
	ArchitectureDao archDao = new ArchitectureDao();
	

	public List<FenLeiCountModel> getArcFenLeiDataEveryMonthInArea(int areaID, int queryYear) throws SQLException
	{
		String areaName = null;
		Area a = areaDao.query(areaID);
		if (a != null)
		{
			areaName = a.getName();
		} else
		{
			return null;
		}

		List<FenLeiCountModel> list = null;

		String sql = "select " + "b.SelectMonth amMonth," + "c.SelectMonth waterMonth," + "d.SelectMonth gasMonth," + "e.SelectMonth extraMonth,"
				+ "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F.TEMPERATURE " + "from "
				+ "(select sum(ZGross) Am_Gross,sum(ZMoney) Am_Money,selectMonth " + "from T_ArcDayAm where selectYear = ? and Area_ID = ? "
				+ "group by selectMonth order by selectMonth) b " + "full join "
				+ "(select sum(ZGross) Water_Gross,sum(ZMoney) Water_Money,selectMonth " + "from T_ArcDayWater where selectYear = ? and Area_ID = ? "
				+ "group by selectMonth order by selectMonth) c " + "on b.selectMonth = c.selectMonth " + "full join "
				+ "(select sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money,selectMonth " + "from T_ArcDayGas where selectYear = ? and Area_ID = ? "
				+ "group by selectMonth order by selectMonth) d " + "on b.selectMonth = d.selectMonth " + "full join "
				+ "(select sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross,selectMonth "
				+ "from ManualDay where selectYear = ? " + "and Architecture_ID in (select Architecture_ID from Architecture where Area_ID = ?) "
				+ "group by selectMonth order by selectMonth) e "
				+ "on b.selectMonth = e.selectMonth FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm'),'mm') SelectMonth from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy')=?) F ON b.SelectMonth = F.SelectMonth order by amMonth,waterMonth,gasMonth,extraMonth";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, areaID);
			ps.setInt(3, queryYear);
			ps.setInt(4, areaID);
			ps.setInt(5, queryYear);
			ps.setInt(6, areaID);
			ps.setInt(7, queryYear);
			ps.setInt(8, areaID);
			ps.setInt(9, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setSelectYear(queryYear);

				fcm.setName(areaName);

				if (rs.getObject("amMonth") != null)
					fcm.setSelectMonth(rs.getInt("amMonth"));
				else
				{
					if (rs.getObject("waterMonth") != null)
						fcm.setSelectMonth(rs.getInt("waterMonth"));
					else
					{
						if (rs.getObject("gasMonth") != null)
							fcm.setSelectMonth(rs.getInt("gasMonth"));
						else
						{
							if (rs.getObject("extraMonth") != null)
								fcm.setSelectMonth(rs.getInt("extraMonth"));
							else
							{
								return null;
							}
						}
					}
				}

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		if (list != null && list.size() > 0)
		{
			Collections.sort(list, new Comparator<FenLeiCountModel>()
			{
				public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
				{
					return arg0.getSelectMonth().compareTo(arg1.getSelectMonth());
				}
			});
		}

		return list;
	}

	public List<FenLeiCountModel> getArcFenLeiDataEveryMonthInArch(int archID, int queryYear) throws SQLException
	{
		String archName = null;
		Architecture a = archDao.queryByID(archID);
		if (a != null)
		{
			archName = a.getName();
		} else
		{
			return null;
		}

		List<FenLeiCountModel> list = null;

		String sql = "select " + "b.SelectMonth amMonth," + "c.SelectMonth waterMonth," + "d.SelectMonth gasMonth," + "e.SelectMonth extraMonth,"
				+ "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F.TEMPERATURE " + "from "
				+ "(select sum(ZGross) Am_Gross,sum(ZMoney) Am_Money,selectMonth " + "from T_ArcDayAm where selectYear = ? and Architecture_ID = ? "
				+ "group by selectMonth order by selectMonth) b " + "full join "
				+ "(select sum(ZGross) Water_Gross,sum(ZMoney) Water_Money,selectMonth "
				+ "from T_ArcDayWater where selectYear = ? and Architecture_ID = ? " + "group by selectMonth order by selectMonth) c "
				+ "on b.selectMonth = c.selectMonth " + "full join " + "(select sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money,selectMonth "
				+ "from T_ArcDayGas where selectYear = ? and Architecture_ID = ? " + "group by selectMonth order by selectMonth) d "
				+ "on b.selectMonth = d.selectMonth " + "full join "
				+ "(select sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross,selectMonth "
				+ "from ManualDay where selectYear = ? "
				+ "and Architecture_ID in (select Architecture_ID from Architecture where Architecture_ID = ?) "
				+ "group by selectMonth order by selectMonth) e "
				+ "on b.selectMonth = e.selectMonth FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm'),'mm') SelectMonth from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy')=?) F ON b.SelectMonth = F.SelectMonth order by amMonth,waterMonth,gasMonth,extraMonth";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, archID);
			ps.setInt(3, queryYear);
			ps.setInt(4, archID);
			ps.setInt(5, queryYear);
			ps.setInt(6, archID);
			ps.setInt(7, queryYear);
			ps.setInt(8, archID);
			ps.setInt(9, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setSelectYear(queryYear);

				fcm.setName(archName);

				if (rs.getObject("amMonth") != null)
					fcm.setSelectMonth(rs.getInt("amMonth"));
				else
				{
					if (rs.getObject("waterMonth") != null)
						fcm.setSelectMonth(rs.getInt("waterMonth"));
					else
					{
						if (rs.getObject("gasMonth") != null)
							fcm.setSelectMonth(rs.getInt("gasMonth"));
						else
						{
							if (rs.getObject("extraMonth") != null)
								fcm.setSelectMonth(rs.getInt("extraMonth"));
							else
							{
								return null;
							}
						}
					}
				}

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		if (list != null && list.size() > 0)
		{
			Collections.sort(list, new Comparator<FenLeiCountModel>()
			{
				public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
				{
					return arg0.getSelectMonth().compareTo(arg1.getSelectMonth());
				}
			});
		}

		return list;
	}

	public List<FenLeiCountModel> getAllFenLeiDataEveryMonth(int queryYear) throws SQLException
	{
		String name = getSystmName();
		if (name == null)
		{
			return null;
		}

		List<FenLeiCountModel> list = null;

		String sql = "select " + "b.SelectMonth amMonth," + "c.SelectMonth waterMonth," + "d.SelectMonth gasMonth," + "e.SelectMonth extraMonth,"
				+ "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F.TEMPERATURE " + "from "
				+ "(select sum(ZGross) Am_Gross,sum(ZMoney) Am_Money,SelectMonth " + "from T_ArcDayAm where selectYear = ? "
				+ "group by SelectMonth order by SelectMonth) b " + "full join "
				+ "(select sum(ZGross) Water_Gross,sum(ZMoney) Water_Money,SelectMonth " + "from T_ArcDayWater where selectYear = ? "
				+ "group by SelectMonth order by SelectMonth) c " + "on b.SelectMonth = c.SelectMonth " + "full join "
				+ "(select sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money,SelectMonth " + "from T_ArcDayGas where selectYear = ? "
				+ "group by SelectMonth order by SelectMonth) d " + "on b.SelectMonth = d.SelectMonth " + "full join "
				+ "(select sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross,SelectMonth "
				+ "from ManualDay where selectYear = ? " + "group by SelectMonth order by SelectMonth) e "
				+ "on b.SelectMonth = e.SelectMonth  FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm'),'mm') SelectMonth from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy')=?) F ON b.SelectMonth = F.SelectMonth order by amMonth,waterMonth,gasMonth,extraMonth";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryYear);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryYear);
			ps.setInt(5, queryYear);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setSelectYear(queryYear);

				fcm.setName(name);

				if (rs.getObject("amMonth") != null)
					fcm.setSelectMonth(rs.getInt("amMonth"));
				else
				{
					if (rs.getObject("waterMonth") != null)
						fcm.setSelectMonth(rs.getInt("waterMonth"));
					else
					{
						if (rs.getObject("gasMonth") != null)
							fcm.setSelectMonth(rs.getInt("gasMonth"));
						else
						{
							if (rs.getObject("extraMonth") != null)
								fcm.setSelectMonth(rs.getInt("extraMonth"));
							else
							{
								return null;
							}
						}
					}
				}

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		if (list != null && list.size() > 0)
		{
			Collections.sort(list, new Comparator<FenLeiCountModel>()
			{
				public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
				{
					return arg0.getSelectDay().compareTo(arg1.getSelectDay());
				}
			});
		}

		return list;
	}

	public List<FenLeiCountModel> getArcFenLeiDataEveryDayInArea(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		String areaName = null;
		Area a = areaDao.query(areaID);
		if (a != null)
		{
			areaName = a.getName();
		} else
		{
			return null;
		}

		List<FenLeiCountModel> list = null;

		String sql = "select " + "b.SelectDay amDay," + "c.SelectDay waterDay," + "d.SelectDay gasDay," + "e.SelectDay extraDay,"
				+ "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F .TEMPERATURE " + "from "
				+ "(select sum(ZGross) Am_Gross,sum(ZMoney) Am_Money,SelectDay "
				+ "from T_ArcDayAm where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by SelectDay order by SelectDay) b "
				+ "full join " + "(select sum(ZGross) Water_Gross,sum(ZMoney) Water_Money,SelectDay "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by SelectDay order by SelectDay) c "
				+ "on b.SelectDay = c.SelectDay " + "full join " + "(select sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money,SelectDay "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by SelectDay order by SelectDay) d "
				+ "on b.SelectDay = d.SelectDay " + "full join "
				+ "(select sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross,SelectDay "
				+ "from ManualDay where selectYear = ? and selectMonth = ? "
				+ "and Architecture_ID in (select Architecture_ID from Architecture where Area_ID = ?) "
				+ "group by SelectDay order by SelectDay) e" + " on b.SelectDay = e.SelectDay  FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm-dd'),'dd') SelectDay from TEMPERATUREDAY where to_char(to_date(selectTime,'yyyy-mm-dd'),'yyyy-mm')=?||'-'||?) F ON b.SelectDay = F.SelectDay order by amDay,waterDay,gasDay,extraDay";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, areaID);
			ps.setInt(4, queryYear);
			ps.setInt(5, queryMonth);
			ps.setInt(6, areaID);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, areaID);
			ps.setInt(10, queryYear);
			ps.setInt(11, queryMonth);
			ps.setInt(12, areaID);
			ps.setInt(13, queryYear);
			ps.setInt(14, queryMonth);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setSelectMonth(queryMonth);

				fcm.setSelectYear(queryYear);

				fcm.setName(areaName);

				if (rs.getObject("amDay") != null)
					fcm.setSelectDay(rs.getInt("amDay"));
				else
				{
					if (rs.getObject("waterDay") != null)
						fcm.setSelectDay(rs.getInt("waterDay"));
					else
					{
						if (rs.getObject("gasDay") != null)
							fcm.setSelectDay(rs.getInt("gasDay"));
						else
						{
							if (rs.getObject("extraDay") != null)
								fcm.setSelectDay(rs.getInt("extraDay"));
							else
							{
								return null;
							}
						}
					}
				}

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
			}


		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		if (list != null && list.size() > 0)
		{
			Collections.sort(list, new Comparator<FenLeiCountModel>()
			{
				public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
				{
					return arg0.getSelectDay().compareTo(arg1.getSelectDay());
				}
			});
		}

		return list;
	}

	public List<FenLeiCountModel> getAllFenLeiDataEveryDay(int queryYear, int queryMonth) throws SQLException
	{
		String name = getSystmName();
		if (name == null)
		{
			return null;
		}

		List<FenLeiCountModel> list = null;

		String sql = "select " + "b.SelectDay amDay," + "c.SelectDay waterDay," + "d.SelectDay gasDay," + "e.SelectDay extraDay,"
				+ "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F.TEMPERATURE " + "from "
				+ "(select sum(ZGross) Am_Gross,sum(ZMoney) Am_Money,SelectDay " + "from T_ArcDayAm where selectYear = ? and selectMonth = ? "
				+ "group by SelectDay order by SelectDay) b " + "full join " + "(select sum(ZGross) Water_Gross,sum(ZMoney) Water_Money,SelectDay "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? " + "group by SelectDay order by SelectDay) c "
				+ "on b.SelectDay = c.SelectDay " + "full join " + "(select sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money,SelectDay "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? " + "group by SelectDay order by SelectDay) d "
				+ "on b.SelectDay = d.SelectDay " + "full join "
				+ "(select sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross,SelectDay "
				+ "from ManualDay where selectYear = ? and selectMonth = ? " + "group by SelectDay order by SelectDay) e "
				+ "on b.SelectDay = e.SelectDay  FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm-dd'),'dd') SelectDay from TEMPERATUREDAY where to_char(to_date(selectTime,'yyyy-mm-dd'),'yyyy-mm')=?||'-'||?) F ON b.SelectDay = F.SelectDay order by amDay,waterDay,gasDay,extraDay";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryMonth);
			ps.setInt(5, queryYear);
			ps.setInt(6, queryMonth);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, queryYear);
			ps.setInt(10, queryMonth);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setSelectMonth(queryMonth);

				fcm.setSelectYear(queryYear);

				fcm.setName(name);

				if (rs.getObject("amDay") != null)
					fcm.setSelectDay(rs.getInt("amDay"));
				else
				{
					if (rs.getObject("waterDay") != null)
						fcm.setSelectDay(rs.getInt("waterDay"));
					else
					{
						if (rs.getObject("gasDay") != null)
							fcm.setSelectDay(rs.getInt("gasDay"));
						else
						{
							if (rs.getObject("extraDay") != null)
								fcm.setSelectDay(rs.getInt("extraDay"));
							else
							{
								return null;
							}
						}
					}
				}

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		if (list != null && list.size() > 0)
		{
			Collections.sort(list, new Comparator<FenLeiCountModel>()
			{
				public int compare(FenLeiCountModel arg0, FenLeiCountModel arg1)
				{
					return arg0.getSelectDay().compareTo(arg1.getSelectDay());
				}
			});
		}

		return list;
	}

	public List<FenLeiCountModel> getArcFenLeiDataByMonthInArea(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		List<FenLeiCountModel> list = null;

		String theName = null;
		Area area = areaDao.query(areaID);

		if (area == null)
			return null;
		else
			theName = area.getName();
		
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,(select nvl(TEMPERATURE,'--')TEMPERATURE from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy-mm')=?||'-'||?) TEMPERATURE " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture where Area_ID = ? ) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money "
				+ "from T_ArcDayAm where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by Architecture_ID) b "
				+ "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by Architecture_ID) c "
				+ "on a.Architecture_ID = c.Architecture_ID " + "left join " + "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? and Area_ID = ? " + "group by Architecture_ID) d "
				+ "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? and selectMonth = ? "
				+ "and Architecture_ID in (select Architecture_ID from Architecture where Area_ID = ?) group by Architecture_ID) e "
				+ "on a.Architecture_ID = e.Architecture_ID " + "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, areaID);
			ps.setInt(4, queryYear);
			ps.setInt(5, queryMonth);
			ps.setInt(6, areaID);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, areaID);
			ps.setInt(10, queryYear);
			ps.setInt(11, queryMonth);
			ps.setInt(12, areaID);
			ps.setInt(13, queryYear);
			ps.setInt(14, queryMonth);
			ps.setInt(15, areaID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());
				fcm.setName(theName);
				
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");
				else
				{
					fcm.setDianNengCount("");
				}

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");
				else
				{
					fcm.setDianNengMoney("");
				}

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");
				else
				{
					fcm.setShuiNengCount("");
				}

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");
				else
				{
					fcm.setShuiNengMoney("");
				}

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");
				else
				{
					fcm.setQiCount("");
				}

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");
				else
				{
					fcm.setQiMoney("");
				}

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");
				else
				{
					fcm.setMeiCount("");
				}

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");
				else
				{
					fcm.setQiYouMoney("");
				}

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");
				else
				{
					fcm.setMeiYouMoney("");
				}

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				else
				{
					fcm.setChaiYouMoney("");
				}
				fcm.setQueryTime(queryYear + "  年  " + queryMonth + "  月");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setTemp(""+rs.getFloat("TEMPERATURE")+"℃");
				}else{
					fcm.setTemp("--℃");
				}
				list.add(fcm);
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

	public List<FenLeiCountModel> getAllFenLeiDataByMonth(int queryYear, int queryMonth) throws SQLException
	{
		List<FenLeiCountModel> list = null;
		String theName = getSystmName();

		if (theName == null)
			theName = "";
		
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,(select nvl(TEMPERATURE,'--')TEMPERATURE from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy-mm')=?||'-'||?) TEMPERATURE " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money " + "from T_ArcDayAm where selectYear = ? and selectMonth = ? "
				+ "group by Architecture_ID) b " + "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? " + "group by Architecture_ID) c "
				+ "on a.Architecture_ID = c.Architecture_ID " + "left join " + "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? " + "group by Architecture_ID) d "
				+ "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? and selectMonth = ? " + "group by Architecture_ID) e "
				+ "on a.Architecture_ID = e.Architecture_ID " + "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryMonth);
			ps.setInt(5, queryYear);
			ps.setInt(6, queryMonth);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, queryYear);
			ps.setInt(10, queryMonth);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());

				fcm.setName(theName);
				
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");
				else
				{
					fcm.setDianNengCount("");
				}

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");
				else
				{
					fcm.setDianNengMoney("");
				}

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");
				else
				{
					fcm.setShuiNengCount("");
				}

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");
				else
				{
					fcm.setShuiNengMoney("");
				}

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");
				else
				{
					fcm.setQiCount("");
				}

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");
				else
				{
					fcm.setQiMoney("");
				}

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");
				else
				{
					fcm.setMeiCount("");
				}

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");
				else
				{
					fcm.setQiYouMoney("");
				}

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");
				else
				{
					fcm.setMeiYouMoney("");
				}

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				else
				{
					fcm.setChaiYouMoney("");
				}
				fcm.setQueryTime(queryYear + "  年  " + queryMonth + "  月");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setTemp(""+rs.getFloat("TEMPERATURE")+"℃");
				}else{
					fcm.setTemp("--℃");
				}
				list.add(fcm);
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

	public List<FenLeiCountModel> getArcFenLeiDataByYearInArea(int areaID, int queryYear) throws SQLException
	{
		List<FenLeiCountModel> list = null;
		String theName = null;
		Area area = areaDao.query(areaID);

		if (area == null)
			return null;
		else
			theName = area.getName();
		
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture where Area_ID = ? ) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money " + "from T_ArcDayAm where selectYear = ? and Area_ID = ? "
				+ "group by Architecture_ID) b " + "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money "
				+ "from T_ArcDayWater where selectYear = ? and Area_ID = ? " + "group by Architecture_ID) c "
				+ "on a.Architecture_ID = c.Architecture_ID " + "left join " + "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money "
				+ "from T_ArcDayGas where selectYear = ? and Area_ID = ? " + "group by Architecture_ID) d "
				+ "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? "
				+ "and Architecture_ID in (select Architecture_ID from Architecture where Area_ID = ?) group by Architecture_ID) e "
				+ "on a.Architecture_ID = e.Architecture_ID " + "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			ps.setInt(2, queryYear);
			ps.setInt(3, areaID);
			ps.setInt(4, queryYear);
			ps.setInt(5, areaID);
			ps.setInt(6, queryYear);
			ps.setInt(7, areaID);
			ps.setInt(8, queryYear);
			ps.setInt(9, areaID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());

				fcm.setName(theName);
				fcm.setQueryTime(queryYear + "  年");
				
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");

				list.add(fcm);
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

	public List<FenLeiCountModel> getAllFenLeiDataByYear(int queryYear) throws SQLException
	{
		List<FenLeiCountModel> list = null;
		String theName = getSystmName();

		if (theName == null)
			theName = "";
		
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money " + "from T_ArcDayAm where selectYear = ? "
				+ "group by Architecture_ID) b " + "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money " + "from T_ArcDayWater where selectYear = ? "
				+ "group by Architecture_ID) c " + "on a.Architecture_ID = c.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money " + "from T_ArcDayGas where selectYear = ? "
				+ "group by Architecture_ID) d " + "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? " + "group by Architecture_ID) e " + "on a.Architecture_ID = e.Architecture_ID "
				+ "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryYear);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryYear);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<FenLeiCountModel>();
				}

				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());

				fcm.setName(theName);
				fcm.setQueryTime(queryYear + "  年");
				
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");

				list.add(fcm);
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

	public List<FenLeiCountModel> getArcFenLeiDataEveryDayInArch(int archID, int queryYear, int queryMonth) throws SQLException
	{
		String archName = null;
		Architecture arch = archDao.queryByID(archID);
		if (arch != null)
		{
			archName = arch.getName();
		} else
		{
			return null;
		}

		List<FenLeiCountModel> list = new LinkedList<FenLeiCountModel>();

		String sql = "select " + "b.SelectDay," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money," + "d.Gas_Gross,d.Gas_Money,"
				+ "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,F.TEMPERATURE " + "from " + "(select ZGross Am_Gross,ZMoney Am_Money,SelectDay "
				+ "from T_ArcDayAm where selectYear = ? and selectMonth = ? and Architecture_ID = ? " + "order by SelectDay) b " + "full join "
				+ "(select ZGross Water_Gross,ZMoney Water_Money,SelectDay "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? and Architecture_ID = ? " + "order by SelectDay) c "
				+ "on b.SelectDay = c.SelectDay " + "full join " + "(select ZGross Gas_Gross,ZMoney Gas_Money,SelectDay "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? and Architecture_ID = ? " + "order by SelectDay) d "
				+ "on b.SelectDay = d.SelectDay " + "full join "
				+ "(select En07 Mei_Gross,En10 QiYou_Gross,En11 MeiYou_Gross,En12 ChaiYou_Gross,SelectDay "
				+ "from ManualDay where selectYear = ? and selectMonth = ? " + "and Architecture_ID = ? order by SelectDay) e "
				+ "on b.SelectDay = e.SelectDay  FULL JOIN (SELECT TEMPERATURE,to_char(to_date(selectTime,'yyyy-mm-dd'),'dd') SelectDay from TEMPERATUREDAY where to_char(to_date(selectTime,'yyyy-mm-dd'),'yyyy-mm')=?||'-'||?) F ON b.SelectDay = F.SelectDay  order by SelectDay";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, archID);
			ps.setInt(4, queryYear);
			ps.setInt(5, queryMonth);
			ps.setInt(6, archID);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, archID);
			ps.setInt(10, queryYear);
			ps.setInt(11, queryMonth);
			ps.setInt(12, archID);
			ps.setInt(13, queryYear);
			ps.setInt(14, queryMonth);
			rs = ps.executeQuery();

			while (rs.next())
			{
				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setName(archName);

				if (rs.getObject("SelectDay") != null)
					fcm.setSelectDay(rs.getInt("SelectDay"));

				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setSelectDayTemp("("+rs.getFloat("TEMPERATURE")+"℃)");
				}else{
					fcm.setSelectDayTemp("(--℃)");
				}
				list.add(fcm);
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

	public List<FenLeiCountModel> getArcFenLeiDataByMonthInArch(int archID, int queryYear, int queryMonth) throws SQLException
	{
		List<FenLeiCountModel> list = new LinkedList<FenLeiCountModel>();

		String theName = null;
		Architecture arch = archDao.queryByID(archID);

		if (arch == null)
			return null;
		else
			theName = arch.getName();
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross,(select nvl(TEMPERATURE,'--')TEMPERATURE from TEMPERATUREMONTH where to_char(to_date(selectTime,'yyyy-mm'),'yyyy-mm')=?||'-'||?) TEMPERATURE " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture where Architecture_ID = ? ) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money "
				+ "from T_ArcDayAm where selectYear = ? and selectMonth = ? and Architecture_ID = ? group by Architecture_ID) b "
				+ "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money "
				+ "from T_ArcDayWater where selectYear = ? and selectMonth = ? and Architecture_ID = ? group by Architecture_ID) c "
				+ "on a.Architecture_ID = c.Architecture_ID " + "left join " + "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money "
				+ "from T_ArcDayGas where selectYear = ? and selectMonth = ? and Architecture_ID = ? group by Architecture_ID) d "
				+ "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? and selectMonth = ? " + "and Architecture_ID = ? group by Architecture_ID) e "
				+ "on a.Architecture_ID = e.Architecture_ID " + "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			ps.setInt(3, archID);
			ps.setInt(4, queryYear);
			ps.setInt(5, queryMonth);
			ps.setInt(6, archID);
			ps.setInt(7, queryYear);
			ps.setInt(8, queryMonth);
			ps.setInt(9, archID);
			ps.setInt(10, queryYear);
			ps.setInt(11, queryMonth);
			ps.setInt(12, archID);
			ps.setInt(13, queryYear);
			ps.setInt(14, queryMonth);
			ps.setInt(15, archID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				FenLeiCountModel fcm = new FenLeiCountModel();
				
				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());
				fcm.setName(theName);
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");
				if (rs.getObject("TEMPERATURE") != null){
					fcm.setTemp(""+rs.getFloat("TEMPERATURE")+"℃");
				}else{
					fcm.setTemp("--℃");
				}
				fcm.setQueryTime(queryYear + "  年  " + queryMonth + "  月");
				list.add(fcm);
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

	public List<FenLeiCountModel> getArcFenLeiDataByYearInArch(int archID, int queryYear) throws SQLException
	{
		List<FenLeiCountModel> list = new LinkedList<FenLeiCountModel>();
		String theName = null;
		Architecture arch = archDao.queryByID(archID);

		if (arch == null)
			return null;
		else
			theName = arch.getName();
		
		String sql = "select " + "a.Architecture_ID,a.Architecture_Name," + "b.Am_Gross,b.Am_Money," + "c.Water_Gross,c.Water_Money,"
				+ "d.Gas_Gross,d.Gas_Money," + "e.Mei_Gross,e.QiYou_Gross,e.MeiYou_Gross,e.ChaiYou_Gross " + "from "
				+ "(select Architecture_ID,Architecture_Name " + "from Architecture where Architecture_ID = ? ) a " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Am_Gross,sum(ZMoney) Am_Money "
				+ "from T_ArcDayAm where selectYear = ? and Architecture_ID = ? group by Architecture_ID) b "
				+ "on a.Architecture_ID = b.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(ZGross) Water_Gross,sum(ZMoney) Water_Money "
				+ "from T_ArcDayWater where selectYear = ? and Architecture_ID = ? group by Architecture_ID) c "
				+ "on a.Architecture_ID = c.Architecture_ID " + "left join " + "(select Architecture_ID,sum(ZGross) Gas_Gross,sum(ZMoney) Gas_Money "
				+ "from T_ArcDayGas where selectYear = ? and Architecture_ID = ? group by Architecture_ID) d "
				+ "on a.Architecture_ID = d.Architecture_ID " + "left join "
				+ "(select Architecture_ID,sum(En07) Mei_Gross,sum(En10) QiYou_Gross,sum(En11) MeiYou_Gross,sum(En12) ChaiYou_Gross "
				+ "from MANUALMONTH where selectYear = ? " + "and Architecture_ID = ? group by Architecture_ID) e "
				+ "on a.Architecture_ID = e.Architecture_ID " + "order by Architecture_ID";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, queryYear);
			ps.setInt(3, archID);
			ps.setInt(4, queryYear);
			ps.setInt(5, archID);
			ps.setInt(6, queryYear);
			ps.setInt(7, archID);
			ps.setInt(8, queryYear);
			ps.setInt(9, archID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				FenLeiCountModel fcm = new FenLeiCountModel();

				fcm.setArchitectureID(rs.getInt("Architecture_ID"));

				fcm.setArchitectureName(rs.getString("Architecture_Name").toString());

				fcm.setName(theName);
				fcm.setQueryTime(queryYear + "  年");
				
				if (rs.getObject("Am_Gross") != null)
					fcm.setDianNengCount(rs.getFloat("Am_Gross") + "");

				if (rs.getObject("Am_Money") != null)
					fcm.setDianNengMoney(rs.getFloat("Am_Money") + "");

				if (rs.getObject("Water_Gross") != null)
					fcm.setShuiNengCount(rs.getFloat("Water_Gross") + "");

				if (rs.getObject("Water_Money") != null)
					fcm.setShuiNengMoney(rs.getFloat("Water_Money") + "");

				if (rs.getObject("Gas_Gross") != null)
					fcm.setQiCount(rs.getFloat("Gas_Gross") + "");

				if (rs.getObject("Gas_Money") != null)
					fcm.setQiMoney(rs.getFloat("Gas_Money") + "");

				if (rs.getObject("Mei_Gross") != null)
					fcm.setMeiCount(rs.getFloat("Mei_Gross") + "");

				if (rs.getObject("QiYou_Gross") != null)
					fcm.setQiYouMoney(rs.getFloat("QiYou_Gross") + "");

				if (rs.getObject("MeiYou_Gross") != null)
					fcm.setMeiYouMoney(rs.getFloat("MeiYou_Gross") + "");

				if (rs.getObject("ChaiYou_Gross") != null)
					fcm.setChaiYouMoney(rs.getFloat("ChaiYou_Gross") + "");

				list.add(fcm);
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

	// 获得总区域名称
	private String getSystmName() throws SQLException
	{
		String name = null;

		String sql = "select SYSTEMINFO_COMPLAYNAME theName from " + "SystemInfo where SYSTEMINFO_ID = 1";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("theName");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return name;
	}

	@Override
	public List<EnergyRecycleModel> getEnergyRecycle(Date startDate, Date endDate) throws SQLException
	{
		List<EnergyRecycleModel> list = new LinkedList<EnergyRecycleModel>();
		HashMap<String, EnergyRecycleModel> map = new HashMap<String,EnergyRecycleModel>();
		EnergyRecycleModel model = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("0.00");
		
		String sql = "select DICTIONARYVALUE_VALUE,DICTIONARYVALUE_NUM from DICTIONARYVALUE where DICTIONARY_ID=3 order by DICTIONARYVALUE_VALUE";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				model = new EnergyRecycleModel();
				model.setStarDate(sdf1.format(startDate));
				model.setEndDate(sdf1.format(endDate));
				model.setArcStyleName(rs.getString("DICTIONARYVALUE_VALUE"));
				map.put(rs.getString("DICTIONARYVALUE_NUM"), model);
			}
			
			
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		double amRecycleGross = 0;
		double amRecycleNoGross = 0;
		double amRecycleMoney = 0;
		double amRecycleNoMoney = 0;
		double waRecycleGross = 0;
		double waRecycleNoGross = 0;
		double waRecycleMoney = 0;
		double waRecycleNoMoney = 0;
		if(map!=null){
			//不可回收电
			sql = "SELECT ARCHITECTURE_STYLE,nvl(sum(ZGROSS),0)ZGROSS,nvl(sum(ZGROSS*p.price_value),0)zmoney,DICTIONARYVALUE_VALUE FROM T_DAYAM a LEFT JOIN AMMETER b on(a.AMMETER_ID=b.AMMETER_ID)  LEFT JOIN PRICE p on(b.price_id=p.PRICE_id) LEFT JOIN ARCHITECTURE c"
					+" on (a.ARCHITECTURE_ID=c.ARCHITECTURE_ID) LEFT JOIN DICTIONARYVALUE d on(c.ARCHITECTURE_STYLE=d.DICTIONARYVALUE_NUM)"
					+" where b.ISCOMPUTATION=1 and nvl(b.isrecycle,0)<>1 and d.DICTIONARY_ID=3 and" 
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))>=to_number("+sdf.format(startDate)+") and" 
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))<=to_number("+sdf.format(endDate)+") GROUP BY ARCHITECTURE_STYLE,DICTIONARYVALUE_VALUE,DICTIONARYVALUE_NUM ";
			Connection connAmNo = null;
			PreparedStatement psAmNo =null;
			ResultSet rsAmNo =null;
			try{
				connAmNo = ConnDB.getConnection();
				psAmNo = connAmNo.prepareStatement(sql);
				rsAmNo = psAmNo.executeQuery();
				while (rsAmNo.next())
				{
					//根据ARCHITECTURE_STYLE值找到map中对应的EnergyRecycleModel，给其他属性赋值
					EnergyRecycleModel erm = map.get(rsAmNo.getString("ARCHITECTURE_STYLE"));

					erm.setAmRecycleNoGross(rsAmNo.getDouble("ZGROSS"));
					erm.setAmRecycleNoMoney(Double.parseDouble(df.format(rsAmNo.getDouble("zmoney"))));
					amRecycleNoGross += rsAmNo.getDouble("ZGROSS");
					amRecycleNoMoney +=Double.parseDouble(df.format(rsAmNo.getDouble("zmoney")));
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(connAmNo, psAmNo,rsAmNo);
			}
			//可回收电
			sql = "SELECT ARCHITECTURE_STYLE,nvl(sum(ZGROSS),0)ZGROSS,nvl(sum(ZGROSS*p.price_value),0)zmoney,DICTIONARYVALUE_VALUE FROM T_DAYAM a LEFT JOIN AMMETER b on(a.AMMETER_ID=b.AMMETER_ID)  LEFT JOIN PRICE p on(b.price_id=p.PRICE_id) LEFT JOIN ARCHITECTURE c"
					+" on (a.ARCHITECTURE_ID=c.ARCHITECTURE_ID) LEFT JOIN DICTIONARYVALUE d on(c.ARCHITECTURE_STYLE=d.DICTIONARYVALUE_NUM)"
					+" where b.ISCOMPUTATION=1 and nvl(b.isrecycle,0)=1 and d.DICTIONARY_ID=3 and" 
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))>=to_number("+sdf.format(startDate)+") and" 
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))<=to_number("+sdf.format(endDate)+") GROUP BY ARCHITECTURE_STYLE,DICTIONARYVALUE_VALUE,DICTIONARYVALUE_NUM ";
			Connection connAm = null;
			PreparedStatement psAm =null;
			ResultSet rsAm =null;
			try{
				connAm = ConnDB.getConnection();
				psAm = connAm.prepareStatement(sql);
				rsAm = psAm.executeQuery();
				while (rsAm.next())
				{
					//根据ARCHITECTURE_STYLE值找到map中对应的EnergyRecycleModel，给其他属性赋值
					EnergyRecycleModel erm = map.get(rsAm.getString("ARCHITECTURE_STYLE"));
					
					erm.setAmRecycleGross(rsAm.getDouble("ZGROSS"));
					erm.setAmRecycleMoney(Double.parseDouble(df.format(rsAm.getDouble("zmoney"))));
					amRecycleGross += rsAm.getDouble("ZGROSS");
					amRecycleMoney +=Double.parseDouble(df.format(rsAm.getDouble("zmoney")));
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(connAm, psAm,rsAm);
			}
			//不可回收水
			sql = "SELECT ARCHITECTURE_STYLE,nvl(sum(ZGROSS),0)ZGROSS,nvl(sum(ZGROSS*p.price_value),0)zmoney,DICTIONARYVALUE_VALUE FROM T_DAYWATER a LEFT JOIN WATERMETER b on(a.WATERMETER_ID=b.WATERMETER_ID)  LEFT JOIN PRICE p on(b.price_id=p.PRICE_id) LEFT JOIN ARCHITECTURE c"
					+" on (a.ARCHITECTURE_ID=c.ARCHITECTURE_ID) LEFT JOIN DICTIONARYVALUE d on(c.ARCHITECTURE_STYLE=d.DICTIONARYVALUE_NUM)"
					+" where b.ISCOMPUTATION=1 and nvl(b.isrecycle,0)<>1 and d.DICTIONARY_ID=3 and"
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))>=to_number("+sdf.format(startDate)+") and"
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))<=to_number("+sdf.format(endDate)+") GROUP BY ARCHITECTURE_STYLE,DICTIONARYVALUE_VALUE,DICTIONARYVALUE_NUM";
			Connection connWaNo = null;
			PreparedStatement psWaNo =null;
			ResultSet rsWaNo =null;
			try{
				connWaNo = ConnDB.getConnection();
				psWaNo = connWaNo.prepareStatement(sql);
				rsWaNo = psWaNo.executeQuery();
				while (rsWaNo.next())
				{
					//根据ARCHITECTURE_STYLE值找到map中对应的EnergyRecycleModel，给其他属性赋值
					EnergyRecycleModel erm = map.get(rsWaNo.getString("ARCHITECTURE_STYLE"));
					
					erm.setWaRecycleNoGross(rsWaNo.getDouble("ZGROSS"));
					erm.setWaRecycleNoMoney(Double.parseDouble(df.format(rsWaNo.getDouble("zmoney"))));
					waRecycleNoGross += rsWaNo.getDouble("ZGROSS");
					waRecycleNoMoney += Double.parseDouble(df.format(rsWaNo.getDouble("zmoney")));
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(connWaNo, psWaNo,rsWaNo);
			}
			//可回收水
			sql = "SELECT ARCHITECTURE_STYLE,nvl(sum(ZGROSS),0)ZGROSS,nvl(sum(ZGROSS*p.price_value),0)zmoney,DICTIONARYVALUE_VALUE FROM T_DAYWATER a LEFT JOIN WATERMETER b on(a.WATERMETER_ID=b.WATERMETER_ID)  LEFT JOIN PRICE p on(b.price_id=p.PRICE_id) LEFT JOIN ARCHITECTURE c"
					+" on (a.ARCHITECTURE_ID=c.ARCHITECTURE_ID) LEFT JOIN DICTIONARYVALUE d on(c.ARCHITECTURE_STYLE=d.DICTIONARYVALUE_NUM)"
					+" where b.ISCOMPUTATION=1 and nvl(b.isrecycle,0)=1 and d.DICTIONARY_ID=3 and"
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))>=to_number("+sdf.format(startDate)+") and"
					+" to_number(SELECTYEAR|| LPAD(SELECTMONTH,2,'0')||LPAD(SELECTDAY,2,'0'))<=to_number("+sdf.format(endDate)+") GROUP BY ARCHITECTURE_STYLE,DICTIONARYVALUE_VALUE,DICTIONARYVALUE_NUM";
			Connection connWa = null;
			PreparedStatement psWa =null;
			ResultSet rsWa =null;
			try{
				connWa = ConnDB.getConnection();
				psWa = connWa.prepareStatement(sql);
				rsWa = psWa.executeQuery();
				while (rsWa.next())
				{
					//根据ARCHITECTURE_STYLE值找到map中对应的EnergyRecycleModel，给其他属性赋值
					EnergyRecycleModel erm = map.get(rsWa.getString("ARCHITECTURE_STYLE"));
					
					erm.setWaRecycleGross(rsWa.getDouble("ZGROSS"));
					erm.setWaRecycleMoney(Double.parseDouble(df.format(rsWa.getDouble("zmoney"))));
					waRecycleGross += rsWa.getDouble("ZGROSS");
					waRecycleMoney += Double.parseDouble(df.format(rsWa.getDouble("zmoney")));
				}
				
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(connWa, psWa,rsWa);
			}
		}
		
		String energyAllRecycle = "用电:"+ df.format(amRecycleGross) +"KWh(电费:"+ df.format(amRecycleMoney)+");用水:"
				+df.format(waRecycleGross)+"吨(水费:"+df.format(waRecycleMoney)+")";
		String energyAllRecycleNo = "用电:"+df.format(amRecycleNoGross)+"KWh(电费:"+df.format(amRecycleNoMoney)+");用水:"
				+df.format(waRecycleNoGross)+"吨(水费:"+df.format(waRecycleNoMoney)+")";
		model = new EnergyRecycleModel();
		model.setStarDate(sdf1.format(startDate));
		model.setEndDate(sdf1.format(endDate));
		model.setArcStyleName("全校");
		model.setEnergyRecycle(energyAllRecycle);
		model.setEnergyRecycleNo(energyAllRecycleNo);
		list.add(model);
		
		Iterator iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,EnergyRecycleModel> entry = (Entry<String,EnergyRecycleModel>)iterator.next();
			EnergyRecycleModel erm = entry.getValue();
			String energyRecycle = "用电:"+erm.getAmRecycleGross()+"KWh(电费:"+erm.getAmRecycleMoney()+");用水:"
					+erm.getWaRecycleGross()+"吨(水费:"+erm.getWaRecycleMoney()+")";
			String energyRecycleNo = "用电:"+erm.getAmRecycleNoGross()+"KWh(电费:"+erm.getAmRecycleNoMoney()+");用水:"
					+erm.getWaRecycleNoGross()+"吨(水费:"+erm.getWaRecycleNoMoney()+")";
			erm.setEnergyRecycle(energyRecycle);
			erm.setEnergyRecycleNo(energyRecycleNo);
			list.add(erm);
		}
		
		return list;
	}
}
