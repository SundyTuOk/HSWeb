package com.sf.energy.transfer.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.sf.energy.transfer.db.ReceiverWithOracle;
import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.util.ConnDB;

/**
 * <与发送相关的数据库操作类测试类> 
 * <包括主动向服务器上传小时数据、取出数据网关下表计、查电表、网关、数据、命令等信息、查缺失数据、更新抄读时间、插限值信息等方法测试。>
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.transfer.SenderWithOracle
 * @since 中转站
 */
public class SenderWithOracleTest
{

	@Test
	public void testAutoUpToServer()
	{
		try
		{
			//将上传标识全部置为“1”
			String sql = "update ammeterdata set up_flag=1";
			ConnDB.executeUpdate(sql);
			
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");
			// 插入用量数据
			ro.dataSave("test000000", "123456789123", "0C0164",
					"2014-01-01 00:00:00", "20", "1");
			
			// 查询数据
			SenderWithOracle so = new SenderWithOracle();
			String xmlStr = so.autoUpToServer("1");

			// 断言
			assertEquals(
					"<NewDataSet><Table><DG_Address>test000000</DG_Address><meterType>1</meterType><Meter_Address>123456789123</Meter_Address><Command_Code>0C0164</Command_Code><Read_Time>2014-01-01 00:00:00</Read_Time><ZY0>20</ZY0></Table></NewDataSet>"
					,xmlStr);

			// 删除插入的临时网关及表计信息以及测试用量数据
			sql = "delete from AMMETERDATA where DG_Address = 'test000000'";
			ConnDB.executeUpdate(sql);
			ro.clearOneTerminal("test000000");
			//将上传标识全部置为“0”
			sql = "update ammeterdata set up_flag=0";
			ConnDB.executeUpdate(sql);
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testAutoUpThreePhase()
	{
		try
		{
			//将上传标识全部置为“1”
			String sql = "update AmMeterDataThreePhase set up_flag=1";
			ConnDB.executeUpdate(sql);
			
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");
			//插入限值
			SenderWithOracle so = new SenderWithOracle();
			String alarmValue = "123456789123,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10";
			boolean a = so.insertAlarm("test000000", alarmValue);
			// 插入三相用量数据
			ArrayList<String> dataArray = new ArrayList<>();
			dataArray.add("PowerZY,2");
			dataArray.add("PowerAY,2");
			dataArray.add("PowerBY,2");
			dataArray.add("PowerCY,2");
			dataArray.add("PowerZW,2");
			dataArray.add("PowerAW,2");
			dataArray.add("PowerBW,2");
			dataArray.add("PowerCW,2");
			dataArray.add("PowerFactorZ,2");
			dataArray.add("PowerFactorA,2");
			dataArray.add("PowerFactorB,2");
			dataArray.add("PowerFactorC,2");
			dataArray.add("VoltageA,2");
			dataArray.add("VoltageB,2");
			dataArray.add("VoltageC,2");
			dataArray.add("CurrentA,2");
			dataArray.add("CurrentB,2");
			dataArray.add("CurrentC,2");
			dataArray.add("Current0,2");
			dataArray.add("PowerSZZ,2");
			dataArray.add("PowerSZA,2");
			dataArray.add("PowerSZB,2");
			dataArray.add("PowerSZC,2");
			String alarm = ro.dataSaveThreePhase("test000000", "123456789123", "2014-01-01 00:00:00",
					dataArray);

			//查询数据
			String xmlStr = so.autoUpThreePhase();
			
			// 断言
			assertEquals("<NewDataSet><Table><DG_Address>test000000</DG_Address><Meter_Address>123456789123</Meter_Address><Read_Time>2014-01-01 00:00:00</Read_Time><PowerZY>2</PowerZY><PowerAY>2</PowerAY><PowerBY>2</PowerBY><PowerCY>2</PowerCY><PowerZW>2</PowerZW><PowerAW>2</PowerAW><PowerBW>2</PowerBW><PowerCW>2</PowerCW><PowerFactorZ>2</PowerFactorZ><PowerFactorA>2</PowerFactorA><PowerFactorB>2</PowerFactorB><PowerFactorC>2</PowerFactorC><VoltageA>2</VoltageA><VoltageB>2</VoltageB><VoltageC>2</VoltageC><CurrentA>2</CurrentA><CurrentB>2</CurrentB><CurrentC>2</CurrentC><Current0>2</Current0></Table></NewDataSet>",xmlStr);
			
			// 删除插入的临时网关及表计信息以及测试用量数据
			sql = "delete from AmMeterDataThreePhase where DG_Address = 'test000000'";
			ConnDB.executeUpdate(sql);
			ro.clearOneTerminal("test000000");
			//将上传标识全部置为“0”
			sql = "update AmMeterDataThreePhase set up_flag=0";
			ConnDB.executeUpdate(sql);
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testAllMeter()
	{
		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 取出数据网关test000000下所有表计
			SenderWithOracle so = new SenderWithOracle();
			String meters = so.allMeter("test000000", "1");

			// 断言
			assertEquals("123456789123|2,", meters);

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMetersToRead()
	{
		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 取出数据网关test000000下所有没有本次用量的表计。
			SenderWithOracle so = new SenderWithOracle();
			String meters = so.metersToRead("test000000", "1");

			// 断言
			assertEquals("123456789123|2,", meters);

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testMetersOnline()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 取出数据网关test000000下所有在线表计
			SenderWithOracle so = new SenderWithOracle();
			ArrayList<String> meters;
			meters = so.metersOnline("test000000", "1");

			// 断言
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("123456789123,2000-01-01 00:00:00");
			assertEquals(temp, meters);

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testReadFlgClear()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");
			// 保存用量数据（置Has_Data为1）
			ro.dataSave("test000000", "123456789123", "0C0164",
					"2014-01-01 00:00:00", "20", "1");

			// 把每块表计的的抄读标志置为0
			SenderWithOracle so = new SenderWithOracle();
			so.readFlgClear("1");

			// 断言
			String sql = "select Has_Data from AmMeter where DG_Address='test000000' and Meter_Address='123456789123'";
			ResultSet rs = null;
			rs = ConnDB.executeQuery(sql);
			if (rs.next())
			{
				assertEquals(0, rs.getInt(1));
			}

			// 删除插入的临时网关及表计信息以及测试用量数据
			sql = "delete from AMMETERDATA where DG_Address = 'test000000'";
			ConnDB.executeUpdate(sql);
			ro.clearOneTerminal("test000000");
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Test
	public void testQueryMeter()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 查询表计信息
			SenderWithOracle so = new SenderWithOracle();
			ResultSet rs = null;
			rs = so.queryMeter("电表", "三相表", "test000000");

			// 断言
			boolean a = false;
			while (rs.next())
			{
				if (rs.getString("数据网关地址").equals("test000000")
						&& (rs.getInt("测量点号") == 2)
						&& rs.getString("表计地址").equals("123456789123")
						&& (rs.getInt("已抄标志") == 0)
						&& rs.getString("最后通讯时间").equals("2000-01-01 00:00:00"))
				{
					a = true;
					break;
				}
			}
			assertEquals(true, a);

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDG()
	{

		try
		{
			// 插入数据网关测试数据
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");

			// 查网关
			SenderWithOracle so = new SenderWithOracle();
			ResultSet rs = null;
			rs = so.queryDG();

			// 断言
			boolean a = false;
			while (rs.next())
			{
				if (rs.getString(1).equals("test000000"))
				{
					a = true;
					break;
				}
			}
			assertEquals(true, a);

			// 删除插入的临时网关测试数据
			ro.clearOneTerminal("test000000");

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryData()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");
			// 插入用量数据
			ro.dataSave("test000000", "123456789123", "0C0164",
								"2014-01-01 00:00:00", "20", "1");
			
			//查询电量数据
			SenderWithOracle so = new SenderWithOracle();
			ResultSet rs = null;
			rs = so.queryData("电表", "0C0164", "test000000", "123456789123", "2014-01-01");
			
			//断言
			boolean a = false;
			if (rs.next())
			{
				if (rs.getString(1).equals("test000000")
						&& rs.getString(2).equals("123456789123")
						&& rs.getString(3).equals("2014-01-01 00:00:00")
						&& rs.getString(4).equals("20")
						&& (rs.getInt(5) == 0))
				{
					a = true;
				}
			}
			assertEquals(true, a);
			
			// 删除插入的临时网关及表计信息以及测试用量数据
			String sql = "delete from AMMETERDATA where DG_Address = 'test000000'";
			ConnDB.executeUpdate(sql);
			ro.clearOneTerminal("test000000");
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryDataItem()
	{

		try
		{
			// 插入测试数据
			String sql = "insert into AmDataItem(COMMAND_CODE,DATA_CODE,DATA_NAME,DATA_INDEX) Values('xx0000','test','测试数据', 0)";
			int retVal = ConnDB.executeUpdate(sql);

			// 查询数据项
			SenderWithOracle so = new SenderWithOracle();
			ResultSet rs = null;
			rs = so.queryDataItem("AmDataItem", "xx0000");

			// 断言
			boolean a = false;
			while (rs.next())
			{
				if (rs.getString(1).equals("测试数据"))
				{
					a = true;
					break;
				}
			}
			assertEquals(true, a);

			// 删除测试数据
			sql = "delete from AmDataItem where COMMAND_CODE = 'xx0000'";
			ConnDB.executeUpdate(sql);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryCommand()
	{

		try
		{
			// 插入测试数据
			String sql = "insert into AmDataItem(COMMAND_CODE,DATA_CODE,DATA_NAME,DATA_INDEX) Values('xx0000','test','测试数据', 0)";
			int retVal = ConnDB.executeUpdate(sql);

			// 查询命令信息
			SenderWithOracle so = new SenderWithOracle();
			ResultSet rs = null;
			rs = so.queryCommand("AmDataItem");

			// 断言
			boolean a = false;
			while (rs.next())
			{
				if (rs.getString(1).equals("xx0000"))
				{
					a = true;
					break;
				}
			}
			assertEquals(true, a);

			// 删除测试数据
			sql = "delete from AmDataItem where COMMAND_CODE = 'xx0000'";
			ConnDB.executeUpdate(sql);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryMissedData()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 更新抄读时间
			Date time = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String date = dateFormat.format(time);
			SenderWithOracle so = new SenderWithOracle();
			so.updateLastDate("test000000", date);
			// 查缺失数据
			String missData = so.queryMissedData("test000000");

			// 断言
			assertEquals("", missData);
			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testUpdateLastDate()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 更新抄读时间
			SenderWithOracle so = new SenderWithOracle();
			so.updateLastDate("test000000", "140311");

			// 断言
			String sql = "select DG_LastCompleteDate from DataGate WHERE DG_Address = 'test000000'";
			ResultSet rs = null;
			rs = ConnDB.executeQuery(sql);
			if (rs.next())
			{
				String time = rs.getString(1);
				assertEquals("2014-03-11", time);
			}

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertAlarm()
	{

		try
		{
			// 插入数据网关及表计信息
			ReceiverWithOracle ro = new ReceiverWithOracle();
			ro.insertTerminal("test000000", "GW", "1");
			ro.insertTerminalMeters("test000000", 2, "123456789123", "1", "02");

			// 插入限值信息
			SenderWithOracle so = new SenderWithOracle();
			String alarmValue = "123456789123,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1";
			boolean a = so.insertAlarm("test000000", alarmValue);

			// 断言
			assertEquals(true, a);

			// 删除插入的临时网关及表计信息
			ro.clearOneTerminal("test000000");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
