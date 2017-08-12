package com.sf.energy.prepayment.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import com.sf.energy.prepayment.model.ByBetweenDetailModel;
import com.sf.energy.prepayment.model.ByDayDetailModel;
import com.sf.energy.prepayment.model.ByMonthDetailModel;
import com.sf.energy.prepayment.model.ByMonthInfoModel;
import com.sf.energy.prepayment.model.ByMonthSellModel;
import com.sf.energy.prepayment.model.ByYearDetailModel;
import com.sf.energy.prepayment.model.ByYearInfoModel;
import com.sf.energy.prepayment.model.ByYearSellModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.util.ConnDB;

public class PrepaymenReportHelper
{
	ArchitectureDao archDao = new ArchitectureDao();
	AreaDao areaDao = new AreaDao();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 按建筑 按月售电
	public List<ByMonthSellModel> getArchSellByMonth(int archID, int queryYear, int queryMonth) throws SQLException
	{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, queryYear);
		cal.set(Calendar.MONTH, queryMonth - 1);
		cal.set(Calendar.DATE, 1);
		String date = dFormat.format(cal.getTime());

		List<ByMonthSellModel> list = null;
		String theName = null;
		Architecture arch = archDao.queryByID(archID);

		if (arch == null)
			return null;
		else
			theName = arch.getName();
		String sql =" SELECT NVL (b.THEDAY, rn) THEDAY, NVL (b.MONEYCOUNT, '0') MONEYCOUNT FROM	(SELECT	LPAD (ROWNUM, 2, '0') rn	FROM	all_objects	WHERE ROWNUM <= (select trunc(add_months(to_date(?,'yyyy-mm-dd'),1),'mon') - trunc(to_date(?,'yyyy-mm-dd'),'mon') from dual))C LEFT JOIN (SELECT	SUM (money) moneyCount,	theDay FROM	(SELECT	theMoney AS money,TO_CHAR (BuyTime, 'dd') AS theDay	FROM	APSaleInfo WHERE TO_CHAR (BuyTime, 'yyyy') = ?	AND TO_CHAR (BuyTime, 'mm') = ?  and KIND NOT in (2,3) and Ammeter_ID in (select Ammeter_ID from Ammeter where Architecture_ID= ? )) A	GROUP BY	theDay) b ON rn = theDay ORDER BY	rn";			
		//		String sql = "select sum(money) moneyCount,theDay from " + "(select theMoney as money,to_char(BuyTime,'dd') "
		//				+ "as theDay from APSaleInfo where to_char(BuyTime,'yyyy') " + "= ? and to_char(BuyTime,'mm') = ? and Ammeter_ID in (select Ammeter_ID from Ammeter where Architecture_ID= ? )) a " + "group by theDay order by theDay";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, date);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryMonth);
			ps.setInt(5, archID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByMonthSellModel>();
				}

				ByMonthSellModel bsm = new ByMonthSellModel();

				bsm.setQueryYear(queryYear);
				bsm.setQueryMonth(queryMonth);
				bsm.setName(theName);
				bsm.setDay(rs.getInt("theDay"));
				bsm.setDayCount(rs.getFloat("moneyCount"));

				list.add(bsm);
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

	// 按区域 按月售电
	public List<ByMonthSellModel> getAreaSellByMonth(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, queryYear);
		cal.set(Calendar.MONTH, queryMonth - 1);
		cal.set(Calendar.DATE, 1);
		String date = dFormat.format(cal.getTime());

		List<ByMonthSellModel> list = null;
		String theName = null;
		Area area = areaDao.query(areaID);

		if (area == null)
			return null;
		else
			theName = area.getName();
		String sql =" SELECT NVL (b.THEDAY, rn) THEDAY, NVL (b.MONEYCOUNT, '0') MONEYCOUNT FROM	(SELECT	LPAD (ROWNUM, 2, '0') rn	FROM	all_objects	WHERE ROWNUM <= (select trunc(add_months(to_date(?,'yyyy-mm-dd'),1),'mon') - trunc(to_date(?,'yyyy-mm-dd'),'mon') from dual))C LEFT JOIN (SELECT	SUM (money) moneyCount,	theDay FROM	(SELECT	theMoney AS money,TO_CHAR (BuyTime, 'dd') AS theDay	FROM	APSaleInfo WHERE TO_CHAR (BuyTime, 'yyyy') = ?	AND TO_CHAR (BuyTime, 'mm') = ?  and KIND NOT in (2,3) and Ammeter_ID in (select Ammeter_ID from Ammeter where Area_ID= ? )) A	GROUP BY	theDay) b ON rn = theDay ORDER BY	rn";
		//		String sql = "select sum(money) moneyCount,theDay from " + "(select theMoney as money,to_char(BuyTime,'dd') "
		//				+ "as theDay from APSaleInfo where to_char(BuyTime,'yyyy') " + "= ? and to_char(BuyTime,'mm') = ? and Ammeter_ID in (select Ammeter_ID from Ammeter where Area_ID = ? )) a " + "group by theDay order by theDay";
		//System.out.println(date+" "+queryYear+" "+queryMonth+" "+areaID);
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, date);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryMonth);
			ps.setInt(5, areaID);

			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByMonthSellModel>();
				}

				ByMonthSellModel bsm = new ByMonthSellModel();

				bsm.setQueryYear(queryYear);
				bsm.setQueryMonth(queryMonth);
				bsm.setName(theName);
				bsm.setDay(rs.getInt("theDay"));
				bsm.setDayCount(rs.getFloat("moneyCount"));

				list.add(bsm);
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

	// 按所有 按月售电
	public List<ByMonthSellModel> getAllSellByMonth(int queryYear, int queryMonth) throws SQLException
	{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, queryYear);
		cal.set(Calendar.MONTH, queryMonth - 1);
		cal.set(Calendar.DATE, 1);
		String date = dFormat.format(cal.getTime());

		List<ByMonthSellModel> list = null;
		String theName = getSystmName();

		if (theName == null)
			theName = "";

		//String sql = "SELECT NVL (b.THEDAY, rn) THEDAY, NVL (b.MONEYCOUNT, '0') MONEYCOUNT FROM	(SELECT	LPAD (ROWNUM, 2, '0') rn	FROM	all_objects	WHERE ROWNUM <= (select trunc(add_months(to_date(?,'yyyy-mm-dd'),1),'mon') - trunc(to_date(?,'yyyy-mm-dd'),'mon') from dual))C LEFT JOIN (SELECT	SUM (money) moneyCount,	theDay FROM	(SELECT	theMoney AS money,TO_CHAR (BuyTime, 'dd') AS theDay	FROM	APSaleInfo WHERE TO_CHAR (BuyTime, 'yyyy') = ?	AND TO_CHAR (BuyTime, 'mm') = ?) A	GROUP BY	theDay) b ON rn = theDay ORDER BY	rn";

		//售电报表去掉月补和调剂
		String sql = "SELECT NVL (b.THEDAY, rn) THEDAY, NVL (b.MONEYCOUNT, '0') MONEYCOUNT FROM	(SELECT	LPAD (ROWNUM, 2, '0') rn	FROM	all_objects	WHERE ROWNUM <= (select trunc(add_months(to_date(?,'yyyy-mm-dd'),1),'mon') - trunc(to_date(?,'yyyy-mm-dd'),'mon') from dual))C LEFT JOIN (SELECT	SUM (money) moneyCount,	theDay FROM	(SELECT	theMoney AS money,TO_CHAR (BuyTime, 'dd') AS theDay	FROM	APSaleInfo WHERE TO_CHAR (BuyTime, 'yyyy') = ?	AND TO_CHAR (BuyTime, 'mm') = ?  and KIND NOT in (2,3)) A	GROUP BY	theDay) b ON rn = theDay ORDER BY	rn";
		// String sql = "select sum(money) moneyCount,theDay from "
		// + "(select theMoney as money,to_char(BuyTime,'dd') "
		// + "as theDay from APSaleInfo where to_char(BuyTime,'yyyy') "
		// + "= ? and to_char(BuyTime,'mm') = ?) a "
		// + "group by theDay order by theDay";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, date);
			ps.setInt(3, queryYear);
			ps.setInt(4, queryMonth);

			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByMonthSellModel>();
				}

				ByMonthSellModel bsm = new ByMonthSellModel();

				bsm.setQueryYear(queryYear);
				bsm.setQueryMonth(queryMonth);
				bsm.setName(theName);
				bsm.setDay(rs.getInt("theDay"));
				bsm.setDayCount(rs.getFloat("moneyCount"));

				list.add(bsm);
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

	// 按所有 按年售电
	public ByYearSellModel getAllSellByYear(int queryYear) throws SQLException
	{
		ByYearSellModel bsm = null;
		String theName = getSystmName();

		if (theName == null)
			theName = "";

		String sql = "select sum(money) moneyCount,theMonth from " + "(select theMoney as money,to_char(BuyTime,'mm') "
				+ "as theMonth from APSaleInfo where to_char(BuyTime,'yyyy') " + "= ?) a group by theMonth order by theMonth";
		//System.out.println("年售电:"+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (bsm == null)
				{
					bsm = new ByYearSellModel();
					bsm.setQueryYear(queryYear);
					bsm.setName(theName);
				}

				if (rs.getInt("theMonth") == 1)
				{
					bsm.setMonth1(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 2)
				{
					bsm.setMonth2(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 3)
				{
					bsm.setMonth3(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 4)
				{
					bsm.setMonth4(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 5)
				{
					bsm.setMonth5(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 6)
				{
					bsm.setMonth6(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 7)
				{
					bsm.setMonth7(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 8)
				{
					bsm.setMonth8(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 9)
				{
					bsm.setMonth9(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 10)
				{
					bsm.setMonth10(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 11)
				{
					bsm.setMonth11(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 12)
				{
					bsm.setMonth12(rs.getFloat("moneyCount"));
				}

			}

			if (bsm != null)
			{
				bsm.setTotal(bsm.getMonth1() + bsm.getMonth2() + bsm.getMonth3() + bsm.getMonth4() + bsm.getMonth5() + bsm.getMonth6() + bsm.getMonth7()
						+ bsm.getMonth8() + bsm.getMonth9() + bsm.getMonth10() + bsm.getMonth11() + bsm.getMonth12());
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return bsm;
	}

	// 按区域 按年售电
	public ByYearSellModel getAreaSellByYear(String name, int areaID, int queryYear) throws SQLException
	{
		ByYearSellModel bsm = null;
		//		String theName = getSystmName();
		//
		//		if (theName == null)
		//			theName = "";

		String sql = "select sum(money) moneyCount,theMonth from " + "(select theMoney as money,to_char(BuyTime,'mm') "
				+ "as theMonth from APSaleInfo where to_char(BuyTime,'yyyy') " + "= ? and Ammeter_ID in (select Ammeter_ID from Ammeter "
				+ "where Area_ID = ? )) a group by theMonth order by theMonth";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (bsm == null)
				{
					bsm = new ByYearSellModel();
					bsm.setQueryYear(queryYear);
					bsm.setName(name);
				}

				if (rs.getInt("theMonth") == 1)
				{
					bsm.setMonth1(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 2)
				{
					bsm.setMonth2(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 3)
				{
					bsm.setMonth3(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 4)
				{
					bsm.setMonth4(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 5)
				{
					bsm.setMonth5(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 6)
				{
					bsm.setMonth6(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 7)
				{
					bsm.setMonth7(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 8)
				{
					bsm.setMonth8(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 9)
				{
					bsm.setMonth9(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 10)
				{
					bsm.setMonth10(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 11)
				{
					bsm.setMonth11(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 12)
				{
					bsm.setMonth12(rs.getFloat("moneyCount"));
				}

			}

			if (bsm != null)
			{
				bsm.setTotal(bsm.getMonth1() + bsm.getMonth2() + bsm.getMonth3() + bsm.getMonth4() + bsm.getMonth5() + bsm.getMonth6() + bsm.getMonth7()
						+ bsm.getMonth8() + bsm.getMonth9() + bsm.getMonth10() + bsm.getMonth11() + bsm.getMonth12());
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return bsm;
	}

	// 按建筑 按年售电
	public ByYearSellModel getArchSellByYear(String name,int archID, int queryYear) throws SQLException
	{
		ByYearSellModel bsm = null;
		//		String theName = getSystmName();

		//		if (theName == null)
		//			theName = "";

		String sql = "select sum(money) moneyCount,theMonth from " + "(select theMoney as money,to_char(BuyTime,'mm') "
				+ "as theMonth from APSaleInfo where to_char(BuyTime,'yyyy') " + "= ? and Ammeter_ID in (select Ammeter_ID from Ammeter "
				+ "where Architecture_ID = ? )) a group by theMonth order by theMonth";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, archID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (bsm == null)
				{
					bsm = new ByYearSellModel();
					bsm.setQueryYear(queryYear);
					bsm.setName(name);
				}

				if (rs.getInt("theMonth") == 1)
				{
					bsm.setMonth1(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 2)
				{
					bsm.setMonth2(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 3)
				{
					bsm.setMonth3(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 4)
				{
					bsm.setMonth4(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 5)
				{
					bsm.setMonth5(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 6)
				{
					bsm.setMonth6(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 7)
				{
					bsm.setMonth7(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 8)
				{
					bsm.setMonth8(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 9)
				{
					bsm.setMonth9(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 10)
				{
					bsm.setMonth10(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 11)
				{
					bsm.setMonth11(rs.getFloat("moneyCount"));
				}

				if (rs.getInt("theMonth") == 12)
				{
					bsm.setMonth12(rs.getFloat("moneyCount"));
				}

			}

			if (bsm != null)
			{
				bsm.setTotal(bsm.getMonth1() + bsm.getMonth2() + bsm.getMonth3() + bsm.getMonth4() + bsm.getMonth5() + bsm.getMonth6() + bsm.getMonth7()
						+ bsm.getMonth8() + bsm.getMonth9() + bsm.getMonth10() + bsm.getMonth11() + bsm.getMonth12());
			}

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return bsm;
	}

	// 按所有 按月综合
	public ByMonthInfoModel getAllInfoByMonth(int queryYear, int queryMonth) throws SQLException
	{
		String name = getSystmName();

		if (name == null)
			return null;

		ByMonthInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter) a " + "where APSaleInfo.Ammeter_ID in a.Ammeter_ID "
				+ "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? " + ") b where Price.Price_ID = b.Price_ID "
				+ "group by Price_Name";
		//System.out.println("月综合sql1:"+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByMonthInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setQueryMonth(queryMonth);
			bmim.setName(name);
		}

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			ps.setInt(2, queryMonth);
			rs = ps.executeQuery();
			while (rs.next())
			{
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter) a " + "where APSaleInfo.Ammeter_ID in a.Ammeter_ID "
				+ "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? group by kind";
		//System.out.println("月综合sql2:"+sql);
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle = null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, queryYear);
			ps1.setInt(2, queryMonth);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{
				if(selStyle==null)
					selStyle=" ";
				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"",queryMonth+"","All|");
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"",queryMonth+"","All|");
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}
			//	float aaa = rs1.getFloat("money");
				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null)
				bmim.setSellStyle(selStyle);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按建筑 按月综合
	public ByMonthInfoModel getArchInfoByMonth(int archID, int queryYear, int queryMonth) throws SQLException
	{
		Architecture arch = archDao.queryByID(archID);

		if (arch == null)
			return null;

		String name = arch.getName();

		ByMonthInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Architecture_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? "
				+ ") b where Price.Price_ID = b.Price_ID " + "group by Price_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByMonthInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setQueryMonth(queryMonth);
			bmim.setName(name);
		}

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, queryYear);
			ps.setInt(3, queryMonth);
			rs = ps.executeQuery();
			while (rs.next())
			{				
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}


		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Architecture_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? group by kind";
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle =null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, archID);
			ps1.setInt(2, queryYear);
			ps1.setInt(3, queryMonth);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{
				if(selStyle==null)
					selStyle=" ";
				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"",queryMonth+"", "Arc|"+archID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"",queryMonth+"","Arc|"+archID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}

				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null&&!"".equals(selStyle))
				bmim.setSellStyle(selStyle);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按区域 按月综合
	public ByMonthInfoModel getAreaInfoByMonth(int areaID, int queryYear, int queryMonth) throws SQLException
	{
		String name = areaDao.queryNameById(areaID);

		if (name == null)
			return null;

		ByMonthInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Area_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? "
				+ ") b where Price.Price_ID = b.Price_ID " + "group by Price_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByMonthInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setQueryMonth(queryMonth);
			bmim.setName(name);
		}

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			ps.setInt(2, queryYear);
			ps.setInt(3, queryMonth);
			rs = ps.executeQuery();
			while (rs.next())
			{
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Area_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " + "and to_char(BuyTime,'mm') = ? group by kind";
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle = null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, areaID);
			ps1.setInt(2, queryYear);
			ps1.setInt(3, queryMonth);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{
				if(selStyle==null)
					selStyle=" ";
				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"",queryMonth+"","Area|"+areaID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"",queryMonth+"","Area|"+areaID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}

				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null&&!"".equals(selStyle))
				bmim.setSellStyle(selStyle);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按所有 按年综合
	public ByYearInfoModel getAllInfoByYear(int queryYear) throws SQLException
	{
		String name = getSystmName();

		if (name == null)
			return null;

		ByYearInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByYearInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setName(name);
		}

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter) a " + "where APSaleInfo.Ammeter_ID in a.Ammeter_ID "
				+ "and to_char(BuyTime,'yyyy') = ? " + ") b where Price.Price_ID = b.Price_ID " + "group by Price_Name";
		//System.out.println("年综合sql1"+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter) a " + "where APSaleInfo.Ammeter_ID in a.Ammeter_ID "
				+ "and to_char(BuyTime,'yyyy') = ? " + "group by kind";
		//System.out.println("年综合sql2"+sql);
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle = null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, queryYear);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{
				if(selStyle==null)
					selStyle="";

				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"","","All|");
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"","","All|");
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}

				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null)
				bmim.setSellStyle(selStyle);

		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按建筑 按年综合
	public ByYearInfoModel getArchInfoByYear(int archID, int queryYear) throws SQLException
	{
		Architecture arch = archDao.queryByID(archID);

		if (arch == null)
			return null;

		String name = arch.getName();

		ByYearInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Architecture_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " 
				+ ") b where Price.Price_ID = b.Price_ID " + "group by Price_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByYearInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setName(name);
		}

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Architecture_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ?  group by kind";
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle = null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, archID);
			ps1.setInt(2, queryYear);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{
				if(selStyle==null)
					selStyle=" ";
				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"","","Arc|"+archID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"","","Arc|"+archID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}


				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null)
				bmim.setSellStyle(selStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按区域 按年综合
	public ByYearInfoModel getAreaInfoByYear(int areaID, int queryYear) throws SQLException
	{
		String name = areaDao.queryNameById(areaID);

		if (name == null)
			return null;

		ByYearInfoModel bmim = null;
		double totalGross = 0;
		double totalMoney = 0;

		String sql = "select Price_Name,sum(b.TheGross) gross,sum(b.TheMoney) " + "money from Price,(select BuyTime,TheGross,TheMoney,Price_ID "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Area_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ? " 
				+ ") b where Price.Price_ID = b.Price_ID " + "group by Price_Name";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		String feeStyle = null;

		if (bmim == null)
		{
			feeStyle = "";
			bmim = new ByYearInfoModel();
			bmim.setQueryYear(queryYear);
			bmim.setName(name);
		}

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			ps.setInt(2, queryYear);
			rs = ps.executeQuery();
			while (rs.next())
			{
				feeStyle += rs.getString("Price_Name") + ":" + rs.getDouble("money") + "   ";
				totalGross = (totalGross + rs.getDouble("gross"));
				totalMoney = (totalMoney + rs.getDouble("money"));
			}

			if (feeStyle != null)
				bmim.setFeeStyle(feeStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		sql = "select (case when Kind=0 then '系统售电' " + "when Kind=1 then '一卡通售电' when Kind=2 then '统一月补'  "
				+ "when Kind=3 then '临时调剂' when Kind=4 then 'ATM售电' when Kind=6 then '微信支付' else '其他' end) kindName,kind," + "sum(TheGross) gross,sum(TheMoney) money "
				+ "from APSaleInfo,(select Ammeter_ID,Price_ID " + "from Ammeter where Area_ID = ?) a "
				+ "where APSaleInfo.Ammeter_ID in a.Ammeter_ID " + "and to_char(BuyTime,'yyyy') = ?  group by kind";
		//System.out.println(sql);
		Connection conn1 = null;
		PreparedStatement ps1 =null;
		ResultSet rs1 =null;
		String selStyle = null;
		try{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, areaID);
			ps1.setInt(2, queryYear);
			rs1 = ps1.executeQuery();
			totalGross = 0;
			totalMoney = 0;
			while (rs1.next())
			{				
				if(selStyle==null)
					selStyle=" ";
				//一卡通售电和ATM售电安装机器的名称来展示
				if("1".equals(rs1.getString("kind"))){//一卡通售电
					ArrayList<String> list = getSaleInfoByKind("1",queryYear+"","","Area|"+areaID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}
				else if("4".equals(rs1.getString("kind"))){//ATM售电
					ArrayList<String> list = getSaleInfoByKind("4",queryYear+"","","Area|"+areaID);
					for(String saleInfo : list){
						String[] infos = saleInfo.split(",");
						selStyle += infos[0] + ":" + infos[2] + "   ";
					}
				}else{
					selStyle += rs1.getString("kindName") + ":" + rs1.getDouble("money") + "   ";
				}

				totalGross = (totalGross + rs1.getDouble("gross"));
				totalMoney = (totalMoney + rs1.getDouble("money"));
			}
			totalGross = (double)(Math.round(totalGross*100))/100;
			totalMoney = (double)(Math.round(totalMoney*100))/100;
			bmim.setzGross(totalGross);
			bmim.setzMoney(totalMoney);

			if (selStyle != null)
				bmim.setSellStyle(selStyle);
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1,rs1);
		}

		return bmim;
	}

	// 按所有 按时间段盘点
	public List<ByBetweenDetailModel> getAllDetailByBetween(Date start, Date end, String userIDs) throws SQLException
	{
		String name = getSystmName();

		if (name == null)
			return null;

		List<ByBetweenDetailModel> list = null;

		/*
		 * String sql = "select 0 as Users_ID,'一卡通售电' as Users_Name," +
		 * "count(SaleInfoNum)Times,nvl(SUM(TheMoney),0)CTheMoney " +
		 * "from APSaleInfo where BuyTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
		 * + "and Kind=1 union all " +
		 * "(select T1.Users_ID,Users_Name,Times0,(CTheMoney1-nvl" +
		 * "(CTheMoney2,0))CTheMoney0 from " +
		 * "(select A.Users_ID,Users_Name,nvl(Times0,0)Times0,nvl" +
		 * "(CTheMoney0,0)CTheMoney1 from " +
		 * "(select Users_ID,Users_Name from Users " +
		 * "where Users_ID in( select Users_ID from V_UserToPower " +
		 * "where  Power_Num='42030'))A " +
		 * "Left join (select Users_ID,COUNT(SaleInfoNum)Times0," +
		 * "SUM(TheMoney)CTheMoney0 from APSaleInfo " +
		 * "where BuyTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
		 * + "and Kind=0 group by Users_ID)T0 " +
		 * "on A.Users_ID=T0.Users_ID )T1 left join " +
		 * "(select USERs_ID,SUM(cast(TheMoney as int))CTheMoney2 " +
		 * "from APXiaohuInfo where TheTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
		 * + "group by Users_ID ) T2 on T1.Users_ID=T2.Users_ID)";
		 * 
		 * PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql)
		 * 
		 * String startStr = ldf.format(start); String endStr = ldf.format(end);
		 * ps.setString(1, startStr); ps.setString(2, endStr); ps.setString(3,
		 * startStr); ps.setString(4, endStr); ps.setString(5, startStr);
		 * ps.setString(6, endStr);
		 */
		// ResultSet rs = ps.executeQuery();
		// while (rs.next())
		// {
		// if (list == null)
		// {
		// list = new LinkedList<ByBetweenDetailModel>();
		// }
		//
		// ByBetweenDetailModel bdm = new ByBetweenDetailModel();
		//
		// bdm.setStarDate(df.format(start));
		// bdm.setEndDate(df.format(end));
		//
		// bdm.setName(rs.getString("Users_Name"));
		// bdm.setMoney(rs.getFloat("CTheMoney"));
		// bdm.setTimes(rs.getInt("Times"));
		//
		//
		// list.add(bdm);
		// }
		Connection conn=ConnDB.getConnection();
		CallableStatement stmt =conn.prepareCall("{call R_AP_getTimePandian(?,?,?,?)}");
		if(userIDs==null)
		{
			userIDs="-1";
		}
		else if(userIDs.length()>2)
		{
			userIDs=userIDs.substring(3);
		}
		//		System.out.println("userIDs:"+userIDs);
		String startStr = df.format(start);
		String endStr = df.format(end);
		stmt.setString(1, userIDs);
		stmt.setString(2, startStr.trim());
		stmt.setString(3, endStr.trim());
		stmt.registerOutParameter(4,OracleTypes.CURSOR);
		stmt.execute();
		ResultSet rs = (ResultSet) stmt.getObject(4);
		while(rs!=null && rs.next())
		{
			if (list == null)
			{
				list = new LinkedList<ByBetweenDetailModel>();
			}
			ByBetweenDetailModel bdm = new ByBetweenDetailModel();

			bdm.setStarDate(df.format(start));
			bdm.setEndDate(df.format(end));

			bdm.setDate(rs.getString("THEDAY"));
			bdm.setMoney(rs.getFloat("CTHEMONEY"));
			bdm.setTimes(rs.getInt("TIMES"));
			if(rs.getString("REMARK")!=null)
				bdm.setInduce(rs.getString("REMARK"));
			else
				bdm.setInduce(" ");
			list.add(bdm);
		}

		if(conn!=null){
			conn.close();
		}

		if (rs != null)
			rs.close();

		if (stmt != null)
			stmt.close();
		return list;
	}

	// 按所有 按日盘点
	/**
	 * 
	 * @param queryDate
	 * @param type 可取 ： area all arch
	 * @param iD 
	 * @return
	 * @throws SQLException
	 */
	public List<ByDayDetailModel> getAllDetailByDay(Date queryDate, String type, Integer iD) throws SQLException
	{
		String name = getSystmName();
		String TheDate = df.format(queryDate);
		String sourceTable="APSALEINFO";
		if (name == null)
			return null;
		if("area".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(area)a where  S.AMMETER_ID=m.AMMETER_ID and M.AREA_ID=a.AREA_ID and a.AREA_ID="+iD;
		}

		if("arch".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(ARCHITECTURE)a where  S.AMMETER_ID=m.AMMETER_ID and M.ARCHITECTURE_ID=a.ARCHITECTURE_ID and a.ARCHITECTURE_ID="+iD;
		}

		List<ByDayDetailModel> list = null;


		String sql=
				/*" SELECT"+
				" 0 AS Users_ID,"+
				" '一卡通售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"'"+
				" AND Kind = 1"+

				" UNION ALL" +*/

				/*" SELECT"+
				" -1 AS Users_ID,"+
				" 'ATM售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"'"+
				" AND Kind = 4"+*/
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=1 and TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"') group BY Users_ID,Users_Name" +

				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=4 and TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"') group BY Users_ID,Users_Name" +
				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=6 and TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"') group BY Users_ID,Users_Name"+
				" UNION ALL"+
				
				" select USERS_ID,USERS_NAME,TIMES,TheMoney from "+
				" ("+
				" 	SELECT allUsers.USERS_ID USERS_ID,ALLUSERS.USERS_NAME USERS_NAME,NVL(allRecordGroupByUser.TIMES,0 ) TIMES,nvl(allRecordGroupByUser.THEMONEY,0) TheMoney from"+
				" ("+
				" 	SELECT"+
				" 		Users_ID,"+
				" 		Users_Name"+
				" 	FROM"+
				" 		USERS"+
				" 	WHERE"+
				" 		Users_ID IN ("+
				" 			SELECT"+
				" 				Users_ID"+
				" 			FROM"+
				" 				V_UserToPower"+
				" 			WHERE"+
				" 				Power_Num = '42010' and USERS_ID NOT IN (select USERS_ID from USERS where USERS_BIRTH like '%4%')"+//此处屏蔽没有售电权限的人
				" 		)"+
				" ) allUsers"+
				" LEFT JOIN"+
				" ("+
				" SELECT Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ("+
				" SELECT"+
				" sourceTable.Users_ID,SaleInfoNum,TheMoney,BuyTime,USERS.USERS_NAME"+
				" FROM"+
				" ("+sourceTable+")sourceTable"+
				" LEFT JOIN USERS ON  sourceTable.USERS_ID=USERS.USERS_ID"+
				" WHERE TO_CHAR (BuyTime, 'yyyy-mm-dd') = '"+TheDate+"' and kind=0"+
				" )joinSrcAndUser"+
				" group BY Users_ID,Users_Name"+
				" )allRecordGroupByUser"+
				" on allUsers.USERS_ID=ALLRECORDGROUPBYUSER.USERS_ID"+
				" ) where TheMoney>0";
		//System.out.println("日："+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByDayDetailModel>();
				}

				ByDayDetailModel bdm = new ByDayDetailModel();

				bdm.setQueryDate(TheDate);
				bdm.setName(rs.getString("Users_Name"));
				bdm.setMoney(rs.getFloat("TheMoney"));
				bdm.setTimes(rs.getInt("TIMES"));

				list.add(bdm);
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

	// 按所有 按月盘点
	/**
	 * 
	 * @param queryYear
	 * @param queryMonth
	 * @param type 可取 ： area all arch
	 * @param iD
	 * @return
	 * @throws SQLException
	 */
	public List<ByMonthDetailModel> getAllDetailByMonth(int queryYear, int queryMonth, String type, Integer iD) throws SQLException
	{
		String name = getSystmName();

		if (name == null)
			return null;


		String sourceTable="APSALEINFO";
		if("area".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(area)a where  S.AMMETER_ID=m.AMMETER_ID and M.AREA_ID=a.AREA_ID and a.AREA_ID="+iD;
		}

		if("arch".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(ARCHITECTURE)a where  S.AMMETER_ID=m.AMMETER_ID and M.ARCHITECTURE_ID=a.ARCHITECTURE_ID and a.ARCHITECTURE_ID="+iD;
		}

		List<ByMonthDetailModel> list = null;


		String sql=
				/*" SELECT"+
				" 0 AS Users_ID,"+
				" '一卡通售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+
				" AND Kind = 1"+

				" UNION ALL"+*/
				/*" SELECT"+
				" -1 AS Users_ID,"+
				" 'ATM售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+
				" AND Kind = 4"+*/
				/**
				 * 将 APSALEINFO 改成  sourceTable
				 */
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=1 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+") group BY Users_ID,Users_Name" +

				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=4 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+") group BY Users_ID,Users_Name" +

				" UNION ALL"+
				
				" select USERS_ID,USERS_NAME,TIMES,TheMoney from "+
				" ("+
				" 	SELECT allUsers.USERS_ID USERS_ID,ALLUSERS.USERS_NAME USERS_NAME,NVL(allRecordGroupByUser.TIMES,0 ) TIMES,nvl(allRecordGroupByUser.THEMONEY,0) TheMoney from"+
				" ("+
				" 	SELECT"+
				" 		Users_ID,"+
				" 		Users_Name"+
				" 	FROM"+
				" 		USERS"+
				" 	WHERE"+
				" 		Users_ID IN ("+
				" 			SELECT"+
				" 				Users_ID"+
				" 			FROM"+
				" 				V_UserToPower"+
				" 			WHERE"+
				" 				Power_Num = '42010' and USERS_ID NOT IN (select USERS_ID from USERS where USERS_BIRTH like '%4%')"+//此处屏蔽没有售电权限的人
				" 		)"+
				" ) allUsers"+
				" LEFT JOIN"+
				" ("+
				" SELECT Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ("+
				" SELECT"+
				" sourceTable.Users_ID,SaleInfoNum,TheMoney,BuyTime,USERS.USERS_NAME"+
				" FROM"+
				" ("+sourceTable+")sourceTable"+
				" LEFT JOIN USERS ON  sourceTable.USERS_ID=USERS.USERS_ID"+
				" WHERE "+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+ " and kind=0"+
				" )joinSrcAndUser"+
				" group BY Users_ID,Users_Name"+
				" )allRecordGroupByUser"+
				" on allUsers.USERS_ID=ALLRECORDGROUPBYUSER.USERS_ID"+
				" ) where TheMoney>0"+
				
				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=6 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+") group BY Users_ID,Users_Name" ;
		//System.out.println("月："+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByMonthDetailModel>();
				}

				ByMonthDetailModel bdm = new ByMonthDetailModel();

				bdm.setQueryYear(queryYear);
				bdm.setQueryMonth(queryMonth);
				bdm.setName(rs.getString("Users_Name"));
				bdm.setMoney(rs.getFloat("TheMoney"));
				bdm.setTimes(rs.getInt("Times"));

				list.add(bdm);
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

	// 按所有 按年盘点
	public List<ByYearDetailModel> getAllDetailByYear(int queryYear, String type, Integer iD) throws SQLException
	{
		String name = getSystmName();

		if (name == null)
			return null;


		String sourceTable="APSALEINFO";
		if("area".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(area)a where  S.AMMETER_ID=m.AMMETER_ID and M.AREA_ID=a.AREA_ID and a.AREA_ID="+iD;
		}

		if("arch".equals(type)&&iD!=-1){
			sourceTable="SELECT s.* from (APSALEINFO)s ,(AMMETER)m ,(ARCHITECTURE)a where  S.AMMETER_ID=m.AMMETER_ID and M.ARCHITECTURE_ID=a.ARCHITECTURE_ID and a.ARCHITECTURE_ID="+iD;
		}

		List<ByYearDetailModel> list = null;


		String sql=
				/*" SELECT"+
				" 0 AS Users_ID,"+
				" '一卡通售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+
				" AND Kind = 1"+

				" UNION ALL"+*/
				/*" SELECT"+
				" -1 AS Users_ID,"+
				" 'ATM售电' AS Users_Name,"+
				" COUNT (SaleInfoNum) TIMES,"+
				" NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ( "+sourceTable+" )sourceTable "+
				" WHERE"+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" AND TO_CHAR (BuyTime, 'mm') = "+queryMonth+
				" AND Kind = 4"+*/
				/**
				 * 将 APSALEINFO 改成  sourceTable
				 */
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=1 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" ) group BY Users_ID,Users_Name" +

				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=4 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" ) group BY Users_ID,Users_Name" +

				" UNION ALL"+
				
				" select USERS_ID,USERS_NAME,TIMES,TheMoney from "+
				" ("+
				" 	SELECT allUsers.USERS_ID USERS_ID,ALLUSERS.USERS_NAME USERS_NAME,NVL(allRecordGroupByUser.TIMES,0 ) TIMES,nvl(allRecordGroupByUser.THEMONEY,0) TheMoney from"+
				" ("+
				" 	SELECT"+
				" 		Users_ID,"+
				" 		Users_Name"+
				" 	FROM"+
				" 		USERS"+
				" 	WHERE"+
				" 		Users_ID IN ("+
				" 			SELECT"+
				" 				Users_ID"+
				" 			FROM"+
				" 				V_UserToPower"+
				" 			WHERE"+
				" 				Power_Num = '42010' and USERS_ID NOT IN (select USERS_ID from USERS where USERS_BIRTH like '%4%')"+//此处屏蔽没有售电权限的人
				" 		)"+
				" ) allUsers"+
				" LEFT JOIN"+
				" ("+
				" SELECT Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney"+
				" FROM"+
				" ("+
				" SELECT"+
				" sourceTable.Users_ID,SaleInfoNum,TheMoney,BuyTime,USERS.USERS_NAME"+
				" FROM"+
				" ("+sourceTable+")sourceTable"+
				" LEFT JOIN USERS ON  sourceTable.USERS_ID=USERS.USERS_ID"+
				" WHERE "+
				" TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				"  and kind=0"+
				" )joinSrcAndUser"+
				" group BY Users_ID,Users_Name"+
				" )allRecordGroupByUser"+
				" on allUsers.USERS_ID=ALLRECORDGROUPBYUSER.USERS_ID"+
				" ) where TheMoney>0"+
				
				" UNION ALL"+
				" SELECT " +
				" Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney FROM" + 
				" (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime" +
				" from ("+sourceTable+") a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where kind=6 and TO_CHAR (BuyTime, 'yyyy') = "+queryYear+
				" ) group BY Users_ID,Users_Name" ;
		//System.out.println("月："+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ByYearDetailModel>();
				}

				ByYearDetailModel bdm = new ByYearDetailModel();

				bdm.setQueryYear(queryYear);
				bdm.setName(rs.getString("Users_Name"));
				bdm.setMoney(rs.getFloat("TheMoney"));
				bdm.setTimes(rs.getInt("Times"));

				list.add(bdm);
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
	public String getSystmName() throws SQLException
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

	private int getDayOfMonth(int queryYear, int queryMonth)
	{
		Date date = new Date();
		date.setYear(queryYear - 1900);
		date.setMonth(queryMonth - 1);
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		int dayOfMonth = gCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return dayOfMonth;
	}


	private ArrayList<String> getSaleInfoByKind(String kind, String queryYear, String queryMonth, String info)
	{
		ArrayList<String> list = new ArrayList<String>();
		String saleInfo = "";
		String part = " and to_char(BuyTime,'yyyy') = "+ queryYear;
		if(!"".equals(queryMonth)){
			part += " and to_char(BuyTime,'mm') = "+queryMonth;
		}
		String[] infos = info.split("\\|");
		if("All".equals(infos[0])){
			part += " and a.Ammeter_id in (select ammeter_id from ammeter)";
		}else if("Area".equals(infos[0])){
			part += " and a.Ammeter_id in (select ammeter_id from ammeter where Area_id="+infos[1]+")";
		}else if("Arc".equals(infos[0])){
			part += " and a.Ammeter_id in (select ammeter_id from ammeter where ARCHITECTURE_ID="+infos[1]+")";
		}
		String sql = "SELECT Users_ID,Users_Name,COUNT (SaleInfoNum) TIMES,NVL (SUM(TheMoney), 0) TheMoney,NVL (SUM(TheGross), 0) TheGross FROM"
				+ " (select a.Users_ID,b.Users_Name,SaleInfoNum,TheMoney,BuyTime,THEGROSS"
				+"		  from APSALEINFO a LEFT JOIN USERS b ON a.USERS_ID=b.USERS_ID where a.kind=" + kind + part + ") group BY Users_ID,Users_Name";
		//System.out.println(kind+" : "+sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;

		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				saleInfo = rs.getString("Users_Name") + "," + rs.getString("TheGross") + "," + rs.getString("TheMoney");
				list.add(saleInfo);
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

}
