/**
 * 2014-3-24
 */
package com.sf.energy.water.statistics.dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.sf.energy.water.statistics.model.WaterMeterMataData;
import com.sf.energy.water.statistics.model.WaterReportModel;

/**
 * 电能计量子系统统计功能封装的测试类
 * 
 * @author 王钊
 * @version 1.0
 * @since version 1.0
 */
public class WaterReportTest
{
	WaterReportHelperImpl rh = new WaterReportHelperImpl();

	@Test
	public void getOriginalWatermeterDataBetween()
	{
		Date start = new Date();
		start.setYear(114);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(114);
		end.setMonth(11);
		end.setDate(29);
		try
		{
			List<WaterMeterMataData> list = rh
					.getOriginalWatermeterDataBetween(286, start, end);

			if (list != null && list.size() > 0)
				printAmList(list);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getGroupCountBetween()
	{
		Date start = new Date();
		start.setYear(114);
		start.setMonth(4);
		start.setDate(1);

		Date end = new Date();
		end.setYear(114);
		end.setMonth(4);
		end.setDate(30);
		try
		{
			List<WaterReportModel> list = rh.getGroupCountBetween(51, start,
					end);

			print(list);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getFenleiArcCountEveryDay()
	{
		try
		{
			List<WaterReportModel> list = rh.getFenleiArcCountEveryDay('b',
					2014, 4);

			print(list);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getGroupCountByMonth()
	{
		try
		{
			WaterReportModel rm = rh.getGroupCountByMonth(51, 2014, 4);
			print(rm);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getGroupCountByYear()
	{
		try
		{
			WaterReportModel rm = rh.getGroupCountByYear(51, 2014);
			print(rm);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getGroupCountDetailByYear() throws SQLException
	{
		WaterReportModel rm = rh.getGroupCountDetailByYear(1, 2014);
		print(rm);
	}

	@Test
	public void getGroupCountDetailByMonth() throws SQLException
	{
		WaterReportModel rm = rh.getGroupCountDetailByMonth(1, 2012, 5);
		print(rm);
	}

	@Test
	public void getGroupCountEveryDay() throws SQLException
	{
		List<WaterReportModel> list = rh.getGroupCountEveryDay(49, 2014, 3);
		print(list);
	}

	@Test
	public void getGroupCountEveryMonth() throws SQLException
	{
		List<WaterReportModel> list = rh.getGroupCountEveryMonth(1, 2012);
		print(list);
	}

	@Test
	public void getGroupFenLeiCountBetween() throws SQLException
	{
		Date start = new Date();
		start.setYear(113);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(5);
		end.setDate(18);

		Map<String, Float> m = rh.getGroupFenLeiCountBetween(1, start, end);

		print(m);

	}

	@Test
	public void getGroupFenLeiCountByMonth() throws SQLException
	{
		Date start = new Date();
		start.setYear(111);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(1);
		end.setDate(18);

		Map<String, Float> m = rh.getGroupFenLeiCountByMonth(1, 2012, 6);

		print(m);

	}

	@Test
	public void getGroupFenLeiCountByYear() throws SQLException
	{
		Date start = new Date();
		start.setYear(113);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(2);
		end.setDate(18);

		Map<String, Float> m = rh.getGroupFenLeiCountByYear(1, 2012);

		print(m);

	}

	@Test
	public void getGroupStyleCountBetween() throws SQLException
	{
		Date start = new Date();
		start.setYear(112);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(7);
		end.setDate(18);

		Map<String, Float> m = rh.getGroupStyleCountBetween(1, start, end);

		print(m);

	}

	@Test
	public void getGroupStyleCountByMonth() throws SQLException
	{
		Map<String, Float> m = rh.getGroupStyleCountByMonth(1, 2012, 6);

		print(m);

	}

	@Test
	public void getGroupStyleCountByYear() throws SQLException
	{
		Map<String, Float> m = rh.getGroupStyleCountByYear(1, 2012);

		print(m);

	}

	@Test
	public void getArcCountBetween()
	{
		Date start = new Date();
		start.setYear(111);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(1);
		end.setDate(18);
		try
		{
			List<WaterReportModel> list = rh.getArcCountBetween(21, start, end);

			print(list);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Test
	public void getArcCountByMonth() throws SQLException
	{
		WaterReportModel rm = rh.getArcCountByMonth(21, 2012, 7);
		print(rm);
	}

	@Test
	public void getArcCountByYear() throws SQLException
	{
		WaterReportModel rm = rh.getArcCountByYear(21, 2012);
		print(rm);
	}

	@Test
	public void getArcCountDetailByMonth() throws SQLException
	{
		WaterReportModel rm = rh.getArcCountDetailByMonth(21, 2012, 3);
		print(rm);
	}

	@Test
	public void getArcCountDetailByYear() throws SQLException
	{
		WaterReportModel rm = null;
		rm = rh.getArcCountDetailByYear(21, 2012);

		print(rm);
	}

	@Test
	public void getArcFenLeiCountBetween() throws SQLException
	{
		Date start = new Date();
		start.setYear(111);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(1);
		end.setDate(18);

		Map<String, Float> m = rh.getArcFenLeiCountBetween(21, start, end);

		print(m);

	}

	@Test
	public void getArcStyleCountBetween() throws SQLException
	{
		Date start = new Date();
		start.setYear(113);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(113);
		end.setMonth(8);
		end.setDate(18);

		Map<String, Float> m = rh.getArcStyleCountBetween(21, start, end);

		print(m);

	}

	@Test
	public void getArcCountEveryMonth() throws SQLException
	{
		List<WaterReportModel> list = rh.getArcCountEveryMonth(56, 2014);
		print(list);
	}

	@Test
	public void getArcCountEveryDay() throws SQLException
	{
		List<WaterReportModel> list = rh.getArcCountEveryDay(1021, 2012, 4);
		print(list);
	}

	@Test
	public void getArcFenLeiCountByYear() throws SQLException
	{
		Map<String, Float> m = rh.getArcFenLeiCountByYear(21, 2012);

		print(m);

	}

	@Test
	public void getArcFenLeiCountByMonth() throws SQLException
	{
		Map<String, Float> m = rh.getArcFenLeiCountByMonth(21, 2012, 6);

		print(m);

	}

	@Test
	public void getArcStyleCountByYear() throws SQLException
	{
		Map<String, Float> m = rh.getArcStyleCountByYear(21, 2012);

		print(m);

	}

	@Test
	public void getArcStyleCountByMonth() throws SQLException
	{
		Map<String, Float> m = rh.getArcStyleCountByMonth(21, 2012, 6);

		print(m);

	}

	@Test
	public void getAllGroupCountListDisc() throws SQLException
	{
		List<WaterReportModel> list = rh.getAllGroupCountListDisc(2012);
		print(list);
	}

	@Test
	public void getAllSonGroupCountByMonth() throws SQLException
	{
		List<WaterReportModel> list = rh.getAllSonGroupCountByMonth(1, 2012, 6);
		print(list);
	}

	@Test
	public void getAllSonGroupCountByYear() throws SQLException
	{

		List<WaterReportModel> list = rh.getAllSonGroupCountByYear(1, 2012);
		print(list);
	}

	@Test
	public void getWaterMeterBetween() throws SQLException
	{
		Date start = new Date();
		start.setYear(111);
		start.setMonth(1);
		start.setDate(1);

		Date end = new Date();
		end.setYear(117);
		end.setMonth(1);
		end.setDate(1);

		System.out.println("start:"
				+ DateFormat.getDateInstance(DateFormat.DEFAULT).format(start)
				+ " end:"
				+ DateFormat.getDateInstance(DateFormat.DEFAULT).format(end));

		List<WaterMeterMataData> list = rh
				.getWaterMeterBetween(802, start, end);

		printAmList(list);

	}

	@Test
	public void getWaterMeterByArc() throws SQLException
	{
		List<WaterMeterMataData> list = rh.getWaterMeterByArc(32);
		printAmList(list);
	}

	@Test
	public void getWaterMeterByID() throws SQLException
	{
		List<WaterMeterMataData> list = rh.getWaterMeterByID(802);
		printAmList(list);
	}

	@Test
	public void getAmmeterCount() throws SQLException
	{
		Map<String, Integer> m = rh.getWaterMeterCount();

		printInteger(m);
	}

	private void print(List<WaterReportModel> l)
	{
		for (WaterReportModel rm : l)
		{
			print(rm);
		}
	}

	private void printAmList(List<WaterMeterMataData> list)
	{
		for (WaterMeterMataData amd : list)
		{
			print(amd);
		}
	}

	private void print(Map<String, Float> map)
	{
		for (Entry<String, Float> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}
	}

	private void printInteger(Map<String, Integer> map)
	{
		for (Entry<String, Integer> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}
	}

	private void print(WaterMeterMataData amd)
	{
		if (amd.getWaterMeterID() != 0)
			System.out.println("电表ID:" + amd.getWaterMeterID());
		if (amd.getRecordTimeDate() != null)
			System.out.println("记录时间:"
					+ amd.getRecordTimeDate().toLocaleString());
		if (amd.getValue() != 0)
			System.out.println("记录值:" + amd.getValue());
		System.out.println("\n");
	}

	private void print(WaterReportModel rm)
	{
		if (rm.getGroupName() != null)
			System.out.println("部门名称:" + rm.getGroupName());

		if (rm.getArcName() != null)
			System.out.println("建筑名称:" + rm.getArcName());

		if (rm.getSelectYear() != 0)
			System.out.println("年:" + rm.getSelectYear());

		if (rm.getSelectMonth() != 0)
			System.out.println("月:" + rm.getSelectMonth());

		if (rm.getSelectDay() != 0)
			System.out.println("日:" + rm.getSelectDay());

		if (rm.getFenXiangXiaoJi() != null)
			System.out.println("分项小计:" + rm.getFenXiangXiaoJi());

		if (rm.getXingZhiXiaoJi() != null)
			System.out.println("性质小计:" + rm.getXingZhiXiaoJi());

		System.out.println("用水量:" + rm.getTotalWaterCount());

		System.out.println("水费:" + rm.getTotalMoneyCount());

		if (rm.getFenlei() != null)
			print(rm.getFenlei());
		System.out.println("\n");

		if (rm.getStyle() != null)
			print(rm.getStyle());
		System.out.println("\n\n");
	}

}
