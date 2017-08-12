package com.sf.energy.transfer.db;

/**
 * 接收到报文后和数据库操作相关接口的实现
 * @author czy
 * @version 1.0
 * @see [ReceiveGW、Receiver]
 * @since [盛帆电子/数据中转站]
 */
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.Spring;

import com.sf.energy.util.TransferConnDB;


public class ReceiverWithOracle implements ReceiverWithDatabase
{
	final String DBNAME = "ls";
	final String DBPWD = "ky";
	final String NETNAME = "orcl";
	final String SAVEPATH = "D:" + File.separator + "OracleDataBackup";
	final String SAVENAME = "transferDatabase.dmp";
	final String LOGNAME = "transferDatabaseLog.log";
	
	@Override
	public void dataSave(String terminalAddress, String meterAddress,
			String commandCode, String readTime, String dataValue,
			String meterType) throws SQLException
	{
		// 默认电表
		String tableName1 = "AmMeterData";
		String tableName2 = "AmMeter";
		// 水表
		if ("02".equals(meterType) || "2".equals(meterType))
		{
			tableName1 = "WaMeterData";
			tableName2 = "WaMeter";
		}
		
		String strSql = "select count(*) from " + tableName1
				+ " where DG_Address='" + terminalAddress
				+ "' and Meter_Address='" + meterAddress
				+ "' and Command_Code='" + commandCode + "' and Read_Time='"
				+ readTime + "'";
		
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		
		if (rs.next())
		{
			int num = rs.getInt(1);
			if (num == 0)
			{
				strSql = "insert into "
						+ tableName1
						+ "(DG_Address,Meter_Address,Command_Code,Read_Time,ZY0,Up_Flag)";
				strSql += "Values('" + terminalAddress + "','" + meterAddress
						+ "','" + commandCode + "','" + readTime + "',"
						+ dataValue + ",0)";
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Statement sm2 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int insertInt = sm1.executeUpdate(strSql);
				
				strSql = "update " + tableName2 + " set Has_Data=1,LastTime='"
						+ readTime + "' where DG_Address='" + terminalAddress
						+ "' and Meter_Address='" + meterAddress + "'";
				
				int updateInt = sm2.executeUpdate(strSql);
				if(sm2 != null)
				{
					sm2.close();
				}
				if(sm1 != null)
				{
					sm1.close();
				}
			}
			
		}
		
		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		
	}
	
	@Override
	public String dataSaveThreePhase(String terminalAddress,
			String meterAddress, String readTime, ArrayList<String> dataArray)
			throws NumberFormatException, SQLException
	{
		// 用于存储警告的信息。
		String alarm = "";
		
		// 查询要插入的记录是否存在
		String strSql = "select count(*) from AmMeterDataThreePhase where DG_Address='"
				+ terminalAddress
				+ "' and Meter_Address='"
				+ meterAddress
				+ "' and Command_Code='0C0103' and Read_Time='"
				+ readTime
				+ "'";
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		//
		if (rs.next())
		{
			int num = rs.getInt(1);
			// 如果记录数是0 则插入记录
			if (num == 0)
			{
				String strInsert = "insert into AmMeterDataThreePhase(DG_Address,"
						+ "Meter_Address,Command_Code,Read_Time";
				String strValue = "Values('" + terminalAddress + "','"
						+ meterAddress + "','0C0103','" + readTime + "'";
				// 拼接SQL语句
				for (String thisdata : dataArray)
				{
					String[] str = thisdata.split(",");
					String datacode = str[0];
					String datavalue = str[1];
					strInsert += "," + datacode;
					strValue += "," + datavalue;
				}
				
				strSql = "select DG_Address,Meter_Address,"
						+ "PowerZY_UL,PowerAY_UL,PowerBY_UL,PowerCY_UL,"
						+ "PowerZW_UL,PowerAW_UL,PowerBW_UL,PowerCW_UL,"
						+ "VoltageA_UL,VoltageA_LL,VoltageB_UL,VoltageB_LL,VoltageC_UL,VoltageC_LL,"
						+ "CurrentA_UL,CurrentB_UL,CurrentC_UL,Current0_UL "
						+ "from AmMeter where Meter_Style='02' and DG_Address='"
						+ terminalAddress + "' and Meter_Address='"
						+ meterAddress + "'";
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rsSelect = null;
				rsSelect = sm1.executeQuery(strSql);
				while (rsSelect.next())
				{
					if ((rsSelect.getString("DG_Address")
							.equals(terminalAddress))
							&& (rsSelect.getString("Meter_Address")
									.equals(meterAddress)))
					{
						String[] strArray = dataArray.get(0).split(",");
						double dtValue = Double.parseDouble(strArray[1]);
						String limitValue = rsSelect.getString("PowerZY_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",总有功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(1).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerAY_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",A相有功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(2).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerBY_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",B相有功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(3).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerCY_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",C相有功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(4).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerZW_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",总无功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(5).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerAW_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",A相无功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(6).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerBW_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",B相无功功率越上限"
									+ ";";
						}
						strArray = dataArray.get(7).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("PowerCW_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",C相无功功率越上限"
									+ ";";
						}
						
						strArray = dataArray.get(12).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("VoltageA_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",A相电压越上限"
									+ ";";
						}
						
						limitValue = rsSelect.getString("VoltageA_LL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue < Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",A相电压越下限"
									+ ";";
						}
						
						strArray = dataArray.get(13).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("VoltageB_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",B相电压越上限"
									+ ";";
						}
						
						limitValue = rsSelect.getString("VoltageB_LL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue < Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",B相电压越下限"
									+ ";";
						}
						
						strArray = dataArray.get(14).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("VoltageC_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",C相电压越上限"
									+ ";";
						}
						
						limitValue = rsSelect.getString("VoltageC_LL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue < Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",C相电压越下限"
									+ ";";
						}
						
						strArray = dataArray.get(15).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("CurrentA_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",当前A相电流越上限"
									+ ";";
						}
						
						strArray = dataArray.get(16).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("CurrentB_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",当前B相电流越上限"
									+ ";";
						}
						
						strArray = dataArray.get(17).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("CurrentC_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",当前C相电流越上限"
									+ ";";
						}
						strArray = dataArray.get(7).split(",");
						dtValue = Double.parseDouble(strArray[1]);
						limitValue = rsSelect.getString("Current0_UL");
						if ((!limitValue.isEmpty()) && (limitValue != "0")
								&& (dtValue > Double.parseDouble(limitValue)))
						{
							alarm += dtValue + "," + limitValue + ",当前零序电流越上限"
									+ ";";
						}
						break;
					}
				}
				strInsert += ",Up_Flag,AlARM)";
				strValue += ",0,'" + alarm + "')";
				strSql = strInsert + strValue;
				
				Statement sm2 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Statement sm3 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int retVal = sm2.executeUpdate(strSql);
				strSql = "update AmMeter set Has_DataThreePhase=1,LastTime='"
						+ readTime + "' where DG_Address='" + terminalAddress
						+ "' and Meter_Address='" + meterAddress + "'";
				retVal = sm3.executeUpdate(strSql);
				
				if(sm1 != null)
				{
					sm1.close();
				}
				if(sm2 != null)
				{
					sm2.close();
				}
				if(sm3 != null)
				{
					sm3.close();
				}
			}
		}
		
		if(rs != null)
		{
			rs.close();
		}
		if(sm != null)
		{
			sm.close();
		}
		return alarm;
	}
	
	@Override
	public void insertTerminal(String terminalAddress, String protocol,
			String ifAutoRead) throws SQLException
	{
		String strSql = "select count(*) from DataGate where DG_Address='"
				+ terminalAddress + "'";
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		if (rs.next())
		{
			int num = rs.getInt(1);
			if (num == 0)
			{
				Date now = new Date();
				// 可以方便地修改日期格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// 获取系统当前时间字符串
				String dateTime = dateFormat.format(now);
				
				strSql = "insert into DATAGATE(DG_Address,DG_Protocol,DG_LastCompleteDate,DG_AutoRead) "
						+ "Values('"
						+ terminalAddress
						+ "','"
						+ protocol
						+ "','" + dateTime + "'," + ifAutoRead + ")";
				
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int retVal = sm1.executeUpdate(strSql);
				
				if(sm1!=null)
				{
					sm1.close();
				}
			}
		}
		
		if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();
		
	}
	
	@Override
	public void insertTerminalMeters(String terminalAddress, int meterId,
			String meterAddress, String meterType, String meterStyle)
			throws SQLException
	{
		// 默认电表
		String tableName = "AmMeter";
		// 水表
		if ("02".equals(meterType) || "2".equals(meterType))
		{
			tableName = "WaMeter";
		}
		
		String strSql = "select count(*) from " + tableName
				+ " where DG_Address='" + terminalAddress
				+ "'and Meter_Address='" + meterAddress + "'";
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		
		if (rs.next())
		{
			int num = rs.getInt(1);
			if (num == 0)
			{
				strSql = "insert into "
						+ tableName
						+ "(DG_Address,Meter_ID,Meter_Address,Meter_Style,Has_Data,LastTime) "
						+ "Values('" + terminalAddress + "'," + meterId + ",'"
						+ meterAddress + "','" + meterStyle
						+ "',0,'2000-01-01 00:00:00')";
				
				Statement sm1 = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				int retVal = sm1.executeUpdate(strSql);
				if(sm1!=null)
				{
					sm1.close();
				}
			}
		}
		if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();
		
	}
	
	@Override
	public void clearOneTerminal(String terminalAddress) throws SQLException
	{
		String strSql = "delete from DataGate where DG_Address='"
				+ terminalAddress + "'";
		
		Connection cn = null;
		Statement sm1 = null;
		Statement sm2 = null;
		Statement sm3 = null;
		try
		{
			cn = TransferConnDB.getConnection();
			cn.setAutoCommit(false);
			
			sm1 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sm2 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sm3 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int retVal = sm1.executeUpdate(strSql);
			strSql = "delete from AmMeter where DG_Address='" + terminalAddress
					+ "'";
			retVal = sm2.executeUpdate(strSql);
			strSql = "delete from WaMeter where DG_Address='" + terminalAddress
					+ "'";
			retVal = sm3.executeUpdate(strSql);
			cn.commit();
		} catch (Exception e)
		{
			try
			{
				// 出现异常，回滚当前事务
				cn.rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		} finally
		{
			if(sm1 != null)
				sm1.close();
			if(sm2 != null)
				sm2.close();
			if(sm3 != null)
				sm3.close();
		}
	}
	
	@Override
	public ArrayList<String> pointToAddress(String terminalAddress, String point)
			throws SQLException
	{
		
		ArrayList<String> meters = new ArrayList();
		String strSql = "select Meter_Address,Meter_Style from(SELECT DG_Address, Meter_ID, Meter_Address, 1 AS Meter_Style FROM AmMeter UNION ALL SELECT DG_Address, Meter_ID, Meter_Address, 2 AS Meter_Style FROM WaMeter)T where DG_Address= '"
				+terminalAddress +"' and Meter_ID="+ point;
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		if (rs.next())
		{
			meters.add(rs.getString("Meter_Address"));
			meters.add(rs.getString("Meter_Style"));
		}
		
		if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();
		return meters;
	}
	
	@Override
	public boolean backUp() throws IOException
	{
		
		String databaseName = DBNAME;
		String databasePwd = DBPWD;
		String netName = NETNAME;
		String filePath = SAVEPATH;
		String fileName = SAVENAME;
		
		Runtime rt = Runtime.getRuntime();
		Process processExp = null;
		File file = new File(filePath);
		if (!file.exists())
		{
			file.mkdir();
		}
		String exp = "exp " + databaseName + "/" + databasePwd + "@" + netName
				+ " file=" + filePath + "/" + fileName + " log=";
		
		boolean success = false;
		try
		{
			processExp = rt.exec(exp);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (processExp != null)
			success = true;
		
		return success;
	}
	
	@Override
	public String checkTerminalInfo(String terminalAddress) throws SQLException
	{
		
		String ifcheckin = "（未登记）";
		
		String strSql = "select count(*) from DataGate where DG_Address='"
				+ terminalAddress + "'";
		
		Statement sm = null;
		sm = TransferConnDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		rs = sm.executeQuery(strSql);
		if (rs.next())
		{
			int num = rs.getInt(1);
			if (num != 0)
			{
				ifcheckin = "（有记录）";
			}
		}
		
		if(rs != null)
			rs.close();
		if(sm != null)
			sm.close();
		return ifcheckin;
	}
	
}
