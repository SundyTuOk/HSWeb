package com.sf.energy.transfer.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.sf.energy.transfer.db.ReceiverWithOracle;
import com.sf.energy.transfer.db.SenderWithOracle;
import com.sf.energy.util.TransferConnDB;

public class ReceiverWithOracleTest
{

	@Test
	public void testDataSave()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();

		try
		{
			ro.insertTerminalMeters("444444", 120, "001301343775","1", "02");
			ro.dataSave("444444", "001301343775", "0C0164",
					"2014-03-17 14:42:00", "14.18", "1");
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		String sql = "select Command_Code,Read_Time,ZY0,Up_Flag from AmMeterData where DG_Address='444444' and Meter_Address='001301343775'";
		ResultSet rsI = TransferConnDB.executeQuery(sql);
		sql = "select Has_Data,LastTime from AmMeter where DG_Address='444444' and Meter_Address='001301343775'";
		ResultSet rsU = TransferConnDB.executeQuery(sql);
		try
		{
			while (rsI.next())
			{
				assertEquals(rsI.getString(1), "0C0164");
				assertEquals(rsI.getString(2), "2014-03-17 14:42:00");
				assertEquals(rsI.getString(3), "14.18");
				assertEquals(rsI.getString(4), "0");
			}
			while (rsU.next())
			{
				assertEquals("1", rsU.getString(1));
				assertEquals("2014-03-17 14:42:00", rsU.getString(2));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql="delete from AmMeterData where DG_Address='444444'";
		TransferConnDB.executeUpdate(sql);
		ro.clearOneTerminal("444444");
	}

	@Test
	public void testDataSaveThreePhase()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();
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

		try
		{
			SenderWithOracle so = new SenderWithOracle();
			ro.insertTerminal("czyDGaddress", "GW", "1");
			ro.insertTerminalMeters("czyDGaddress", 2, "czyMeterAdd", "1", "02");
			String alarmValue = "czyMeterAdd,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10";
			boolean a = so.insertAlarm("czyDGaddress", alarmValue);
			String alarm=ro.dataSaveThreePhase("czyDGaddress", "czyMeterAdd", "2014-03-17 14:42:00",
					dataArray);
			System.out.println("alarm: "+alarm);
			String sql = "select PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,"
					+ "PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC,"
					+ "VoltageA,VoltageB,VoltageC,CurrentA,CurrentB,CurrentC,Current0,"
					+ "PowerSZZ,PowerSZA,PowerSZB,PowerSZC,Up_Flag,AlARM from AmMeterDataThreePhase where DG_Address='111111' and Meter_Address='111111' and Command_Code='0C0103' and Read_Time='2014-03-17 14:42:00'";
			ResultSet rsI = TransferConnDB.executeQuery(sql);
			while (rsI.next())
			{
				for (int i = 1; i <= 23; i++)
				{
					assertEquals("2", rsI.getString(i));
				}
				assertEquals(0, rsI.getInt(24));
				assertNotNull(rsI.getString(25));
			}

			sql = "select Has_DataThreePhase,LastTime from AmMeter where DG_Address='111111' and Meter_Address='111111' ";
			ResultSet rsU = TransferConnDB.executeQuery(sql);
			while (rsU.next())
			{
				assertEquals("1", rsU.getString(1));
				assertEquals("2014-03-17 14:42:00", rsU.getString(2));
			}
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql="delete AmMeterDataThreePhase where dg_address='czyDGaddress'";
		TransferConnDB.executeUpdate(sql);
	}

	@Test
	public void testInsertTerminal()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();
		try
		{
			ro.insertTerminal("FFFFFFF", "gw", "1");
			String sql = "select DG_Address,DG_Protocol,DG_LastCompleteDate,DG_AutoRead from DataGate" +
					" where DG_Address='FFFFFFF'";
			ResultSet rs = TransferConnDB.executeQuery(sql);
			if (rs.next())
			{
				assertEquals("gw", rs.getString(2));
				assertEquals("1", rs.getString(4));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertTerminalMeters()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();
		try
		{
			ro.insertTerminalMeters("xxxxxxxxx", 2, "222222666666", "1", "02");

			String sql = "select Meter_ID,Meter_Address,Meter_Style,Has_Data,LastTime from AmMeter where DG_Address='xxxxxxxxx'";
			ResultSet rs = TransferConnDB.executeQuery(sql);
			if (rs.next())
			{
				assertEquals(2, rs.getInt(1));
				assertEquals("222222666666", rs.getString(2));
				assertEquals(02, rs.getInt(3));
				assertEquals(0, rs.getInt(4));
				assertEquals("2000-01-01 00:00:00", rs.getString(5));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testClearOneTerminal()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();
		try
		{
			ro.insertTerminal("DDDDD", "gw", "1");
			ro.insertTerminalMeters("DDDDD", 110, "eeeeee", "1", "02");
			ro.insertTerminalMeters("DDDDD", 110, "eeeeee", "2", "02");
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ro.clearOneTerminal("DDDDD");
		String sql = "select * from AmMeter where DG_Address='DDDDD'";
		String sql2 = "select * from WaMeter where DG_Address='DDDDD'";
		String sql3 = "select * from DataGate where DG_Address='DDDDD'";
		ResultSet rs1 = TransferConnDB.executeQuery(sql);
		ResultSet rs2 = TransferConnDB.executeQuery(sql2);
		ResultSet rs3 = TransferConnDB.executeQuery(sql3);

		try
		{
			assertFalse(rs1.next());
			assertFalse(rs2.next());
			assertFalse(rs3.next());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	@Test
	public void testPointToAddress()
	{
		ReceiverWithOracle ro = new ReceiverWithOracle();
		try
		{
//			ro.insertTerminalMeters("KKKK", 110, "LLLLLL", "1", "02");
			ro.clearOneTerminal("KKKK");
			ro.insertTerminalMeters("KKKK", 110, "LLLLLL", "2", "02");
			ArrayList<String> aa = ro.pointToAddress("KKKK", "110");
			for (String a : aa)
			{
				System.out.println(a);
			}
			assertEquals("LLLLLL", aa.get(0));
			assertEquals("02", aa.get(1));

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testBackUp()
	{
		ReceiverWithOracle ro = new ReceiverWithOracle();
		try
		{
			ro.backUp();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckTerminalInfo()
	{
		
		ReceiverWithOracle ro = new ReceiverWithOracle();
		String sql = "insert into DataGate(DG_Address,DG_Protocol,DG_LastCompleteDate,DG_AutoRead) values ('asdfasdf','GW','2014-03-17',1)";
		TransferConnDB.executeQuery(sql);
		try
		{
			String check = ro.checkTerminalInfo("asdfasdf");
			assertEquals("（有记录）", check);
			String check2 = ro.checkTerminalInfo("asdfasde");
			assertEquals("（未登记）", check2);
			ro.clearOneTerminal("asdfasdf");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
