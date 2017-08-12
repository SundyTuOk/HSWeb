package com.sf.energy.project.system.service.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class PlantaskExecDao
{
	/**
	 * 定时执行计划任务
	 * 
	 * @param taskID
	 *            任务ID
	 * @param BeginTime
	 *            起始时间
	 * @param EndTime
	 *            结束时间
	 * @return 
	 * @throws SQLException
	 *             执行存储过程抛出SQL异常
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws ParseException
	 */
	public boolean planTask_exec(String style, String startTime, String endTime,
			String userId) throws  UnknownHostException,
			IOException, ParseException
	{
		boolean result=true;
		String EXEC_LOG = null;
		try
		{
		switch (style)
		{
		case "01":// 整理日数据
			
				zlDayAm(startTime, endTime, userId);
			
			EXEC_LOG = "用户手动执行：整理电能日数据";
			break;
		case "02":// 整理用电月数据
			zLMonthAm(startTime, endTime, userId);
			EXEC_LOG = "用户手动执行：整理电能月数据";
			break;
		case "03":// 整理待机功耗
			execDJGHData(startTime, endTime, userId);
			EXEC_LOG = "用户手动执行：整理待机功耗模型";
			break;
		case "04": // 整理用电匹配模型
			execPPMXData(startTime, endTime, userId);
			EXEC_LOG = "用户手动执行：整理用电匹配模型";
			break;
		case "11":// 整理用水日数据
			zlDayWater(startTime, endTime, userId);
			break;
		case "12":// 整理用水月数据
			zLMonthWater(startTime, endTime, userId);
			break;
		case "13":// 整理用水匹配模型
			ExecPPMXDataWater(startTime, endTime, userId);
			break;
		}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}
		return result;

	}

	/**
	 * 按月整理水数据
	 * 
	 * @param beginTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 异常信息
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public void zLMonthWater(String beginTime, String endTime, String userId)
			throws SQLException, ParseException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			beginTime = df.format(df1.parse(beginTime));
			endTime = df.format(df1.parse(endTime));
			//System.out.println("按月整理的时间：" + beginTime + " " + endTime);
			conn = ConnDB.getConnection();
			;
			CallableStatement c = conn
					.prepareCall("{ call VES_ZLMonthWater(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn.commit();

			String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('12',sysdate,'定时任务执行：整理用水月数据',to_date('"
					+ beginTime
					+ "','yyyy-mm'),to_date('"
					+ endTime
					+ "','yyyy-mm')," + userId + ")";

			ps = conn.prepareStatement(insertSql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	/**
	 * 按月整理电数据
	 * 
	 * @param beginTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 异常信息
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void zLMonthAm(String beginTime, String endTime, String userId)
			throws SQLException, ParseException
	{
		Connection conn = null;
		PreparedStatement ps1=null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			beginTime = df.format(df1.parse(beginTime));
			endTime = df.format(df1.parse(endTime));
			//System.out.println("按月整理的时间：" + beginTime + " " + endTime);
			conn = ConnDB.getConnection();
			;
			CallableStatement c = conn.prepareCall("{ call VES_ZLMonthAm(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn.commit();

			String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('02',sysdate,'定时任务执行：整理用电月数据',to_date('"
					+ beginTime
					+ "','yyyy-mm'),to_date('"
					+ endTime
					+ "','yyyy-mm')," + userId + ")";
			ps1 = conn.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps1);
		}
	

	}

	/**
	 * 按日整理水数据
	 * 
	 * @param beginTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 异常信息
	 * @throws SQLException
	 */
	public void zlDayWater(String beginTime, String endTime, String userId)
			throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps1=null;
		try
		{
			conn = ConnDB.getConnection();
			// /创建存储过程对象

			CallableStatement proc = conn
					.prepareCall("{ call VES_ZLDayWater(?, ?) }");
			proc.setString(1, beginTime);
			proc.setString(2, endTime);
			proc.execute();
			conn.commit();
			String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('11',sysdate,'定时任务执行：整理用水日数据',to_date('"
					+ beginTime
					+ "','yyyy-mm-dd'),to_date('"
					+ endTime
					+ "','yyyy-mm-dd'),0)";
			ps1 = conn.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps1);
		}
	}

	/**
	 * 按日整理电数据
	 * 
	 * @param beginTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 异常信息
	 * @throws SQLException
	 */
	public void zlDayAm(String beginTime, String endTime, String userId)
			throws SQLException
	{

		Connection conn = null;
		PreparedStatement ps1=null;
		try
		{
			conn = ConnDB.getConnection();

			// /创建存储过程对象
			CallableStatement c = conn.prepareCall("{ call VES_ZLDayAm(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn.commit();

			String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('01',sysdate,'定时任务执行：整理用电日数据',to_date('"
					+ beginTime
					+ "','yyyy-mm-dd'),to_date('"
					+ endTime
					+ "','yyyy-mm-dd')," + userId + ")";
			ps1 = conn.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps1);
		}
	}

	/**
	 * 整理用电匹配模型
	 * 
	 * @param startTime1
	 *            起始时间
	 * @param endTime1
	 *            结束时间
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public String execPPMXData(String startTime, String endTime, String userId)
			throws SQLException, ParseException, UnknownHostException,
			IOException
	{
		PreparedStatement modelDataps = null;
		Connection  conn = null;
		ResultSet modelRs = null;
		
		PreparedStatement warningPs = null;
		Connection  conn1 = null;
		
		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		SimpleDateFormat noHourDF = new SimpleDateFormat("yyyy-MM-dd");

		Date start = noHourDF.parse(startTime);
		Date end = noHourDF.parse(endTime);

		// 查询要执行用电匹配模型的所有点表
		// 1 2 3 4 5 6
		try
		{
			String sql = "select b.AmMeter_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23,LastCheckTime from (AmMeter)a,(AmMatchModel)b where a.AmMeter_ID=b.AmMeter_ID and CostCheck=1";
			conn = ConnDB.getConnection();
			modelDataps = conn.prepareStatement(sql);
			modelRs = modelDataps.executeQuery();

			// 循环检测这些电表的值是否超出模型

			int hour = 0;
			int lastCheckhour = 0;
			Date lastCheckDate;
			while (modelRs.next())
			{

				String ammeter_ID = modelRs.getString("AmMeter_ID");
				ArrayList<String> table = new ArrayList<String>();
				int AmMeterID = Integer.parseInt(ammeter_ID);

				// 获得时间段内每小时的用电量。
				ElecReportHelperImpl rhi_AmStandByModel = new ElecReportHelperImpl();
				List<AmMeterMataData> dataList = rhi_AmStandByModel
						.getAmMeterBetween(AmMeterID, start, end);

				/*
				 * 将每小时的用电量和模型进行匹配，匹配上的就将报警信心放在table里面， 匹配完成后发送报警信息，更新时间
				 */
				for (AmMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型
					if (hour >= lastCheckhour)
					{

						String num = modelRs.getString(hour + 2);
						if ("".equals(num) || num == null)
						{
							num = "0";
						} else
						{
							num = modelRs.getString(hour + 2);
						}
						// 模型值
						float modelNum = Float.parseFloat(num);
						// 实际值
						float actualNum = n.getValue();

						if (actualNum > modelNum)
						{
							String theDate = noMinDF.format(dataTime);

							// 检测是否有记录
							boolean has = false;
							for (String date : table)
							{
								if (date.equals(theDate))
								{
									has = true;
									break;
								}
							}

							if (!has)
							{
								table.add(theDate);
							}

						}
					}
				}

				// 如果有错误信息
				if (table.size() > 0)
				{
					String warning = "";
					// String SMSWill_PhoneList = "";
					String AmMeter_Name = "";
					String strsql = "select AmMeter_Name,ConsumerPhone,( select Architecture_Phone	from (Architecture)b 	where a.Architecture_ID=b.Architecture_ID ) as Architecture_Phone,(	select Area_Phone from (Area)b where a.Area_ID=b.Area_ID ) as Area_Phone,(select Partment_Phone from (Partment)b where a.Partment_id=b.Partment_ID)as Partment_Phone from (AmMeter)a where AmMeter_ID="
							+ ammeter_ID;
					PreparedStatement prep = ConnDB.getConnection()
							.prepareStatement(strsql);
					ResultSet drPhon = prep.executeQuery();
					if (drPhon.next())
					{
						AmMeter_Name = drPhon.getString("AmMeter_Name");
					}

					if (drPhon != null)
						drPhon.close();
					if (prep != null)
						prep.close();

					// 插入报警表
					for (String objDay : table)
					{
						String tablestring = objDay + " ";
						// 报警表里添加报警数据
						String sqlsql = "insert into AmBaojing(AmBaojing_Style,AmBaojing_Time,AmMeter_ID,AmBaojing_Remark,AmBaojing_SendSMS,inserttime) values("
								+ "1,sysdate,"
								+ ammeter_ID
								+ ",'"
								+ AmMeter_Name
								+ ": " + tablestring + "用电匹配模型报警',"+userId+",sysdate)";
						conn1 = ConnDB.getConnection();
						warningPs = conn1.prepareStatement(sqlsql);
						warningPs.executeUpdate();

					}

					// 组件这个网关的报警信息

					for (String date : table)
					{
						warning = "电表：" + AmMeter_Name + " 在" + date
								+ "违反了24小时用电匹配模型，请及时检查。\r\n";
						sendWarningToServer(warning);
					}

				}

			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, modelDataps, modelRs);
			ConnDB.release(conn1, warningPs);
		}

		// 往历史数据表中添加信息
		PreparedStatement ps1=null;
		Connection conn0 =null; 
		try
		{
			String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('04',sysdate,'定时任务执行：整理用电匹配模型数据',to_date('"
					+ startTime
					+ "','yyyy-mm-dd'),to_date('"
					+ endTime
					+ "','yyyy-mm-dd')," + userId + ")";
			conn0 = ConnDB.getConnection();
			ps1 = conn0.prepareStatement(
					insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps1);
		}


		return null;
	}
/***********************************************************************************************************************/
	/**
	 * 整理待机功耗匹配模型
	 * 
	 * @param startTime1
	 *            起始时间
	 * @param endTime1
	 *            结束时间
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public String execDJGHData(String startTime, String endTime, String userId)
			throws SQLException, ParseException, UnknownHostException,
			IOException
	{

		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		SimpleDateFormat noHourDF = new SimpleDateFormat("yyyy-MM-dd");
		Date start = noHourDF.parse(startTime);
		Date end = noHourDF.parse(endTime);

		Connection conn = null;
		PreparedStatement modelPs = null;
		ResultSet modelRs = null;
		try
		{
			String sql = "select b.AmMeter_ID,StartTime,EndTime,LowLimit,UpperLimit,LastCheckTime from (AmMeter)a,(AmStandbyModel)b where a.AmMeter_ID=b.AmMeter_ID and StandByCheck=1";
			conn = ConnDB.getConnection();
			modelPs = conn.prepareStatement(sql);
			modelRs = modelPs.executeQuery();

			int hour = 0;

			while (modelRs.next())
			{
				String AmMeter_ID = modelRs.getString("AmMeter_ID");
				int ammeter_ID = Integer.parseInt(AmMeter_ID);

				String startHour = modelRs.getString("StartTime");
				String endHour = modelRs.getString("EndTime");
				int lowHour = getHour2(startHour);
				int upHour = getHour2(endHour);

				ArrayList<String> table = new ArrayList<String>();
				// 获取限值
				float lowLimit = modelRs.getFloat("LowLimit");
				float upperLimit = modelRs.getFloat("UpperLimit");

				// 获得时间段内每小时的用电量。
				ElecReportHelperImpl rhi_AmStandByModel = new ElecReportHelperImpl();
				List<AmMeterMataData> dataList = rhi_AmStandByModel
						.getAmMeterBetween(ammeter_ID, start, end);

				for (AmMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型
					if (hour >= lowHour && hour <= upHour)
					{
						// 实际值
						float actualNum = n.getValue();

						if (actualNum > upperLimit || actualNum < lowLimit)
						{
							String theDate = noMinDF.format(dataTime);
							theDate += ",待机功耗:" + actualNum + ",";
							// 检测是否有记录
							boolean has = false;
							for (String date : table)
							{
								if (date.equals(theDate))
								{
									has = true;
									break;
								}
							}

							if (!has)
							{
								table.add(theDate);
							}

						}
					}
				}

				if (table.size() > 0)
				{
					String AmMeter_Name = null;
					String strsql = "select Meter_Name,ConsumerPhone,PartmentPhone,ArchitecturePhone,AreaPhone from V_AmMeter where AmMeter_ID="
							+ AmMeter_ID;
					Connection conn1 = null;
					PreparedStatement prep=null;
					ResultSet drPhon=null;
					try
					{
						conn1 = ConnDB.getConnection();
						prep = conn1.prepareStatement(strsql);
						drPhon = prep.executeQuery();
						if (drPhon.next())
							AmMeter_Name = drPhon.getString("Meter_Name");
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn1, prep, drPhon);
					}

					// 向报警信息表中插入报警信息
					Connection conn2 = null;
					PreparedStatement warningPs = null;
					for (String objDay : table)
					{
						String tablestring = objDay + " ";
						// 报警表里添加报警数据 待机功耗的报警类型是2
						String sqlsql = "insert into AmBaojing(AmBaojing_Style,AmBaojing_Time,AmMeter_ID,AmBaojing_Remark,AmBaojing_SendSMS,inserttime) values("
								+ "2,sysdate,"
								+ ammeter_ID
								+ ",'"
								+ AmMeter_Name
								+ ": " + tablestring + "待机功耗模型报警',0,sysdate)";
						try
						{
							conn2 = ConnDB.getConnection();
							warningPs = conn2.prepareStatement(sqlsql);
							warningPs.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(conn2, warningPs);
						}
					}

					// 组建报警信息

					String warning = null;
					for (String theDate : table)
					{
						warning = "电表:" + AmMeter_Name + " 在" + theDate
								+ "违反了待机功耗模型,请核查。\r\n";
						sendWarningToServer(warning);
					}

				}
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, modelPs, modelRs);
			}

		// 往历史数据表中添加信息
		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('03',sysdate,'定时任务执行：整理待机功耗模型数据',to_date('"
				+ startTime
				+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd hh24:mi:ss')," + userId + ")";
		Connection conn3 = null;
		PreparedStatement ps1=null;
		try
		{
			conn3=ConnDB.getConnection();
			ps1 = conn3.prepareStatement(
					insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn3, ps1);
		}

		return null;
	}

	/**
	 * 执行水的匹配模型
	 * 
	 * @param starTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @param UserID
	 *            用户ID
	 * @return 异常错误
	 * @throws SQLException
	 *             执行存储过程爬出异常
	 * @throws ParseException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	private String ExecPPMXDataWater(String startTime, String endTime,
			String UserID) throws SQLException, ParseException,
			UnknownHostException, IOException
	{
		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

		SimpleDateFormat noHourDF = new SimpleDateFormat("yyyy-MM-dd");
		Date start = noHourDF.parse(startTime);
		Date end = noHourDF.parse(endTime);

		String strSql = "select b.WaterMeter_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23,LastCheckTime from (WaterMeter)a,(WaterMatchModel)b where a.WaterMeter_ID=b.WaterMeter_ID and CostCheck=1";
		Connection conn = null;
		PreparedStatement modelPs = null;
		ResultSet modelRs=null;
		try
		{
			conn = ConnDB.getConnection();
			modelPs = conn.prepareStatement(strSql);
			modelRs = modelPs.executeQuery();

			// 循环检测这些电表的值是否超出模型

			int hour = 0;
			while (modelRs.next())
			{
				String WaterMeter_ID = modelRs.getString("WaterMeter_ID");

				ArrayList<String> table = new ArrayList<String>();

				int WaterMeterID = Integer.parseInt(WaterMeter_ID);

				WaterReportHelperImpl aa = new WaterReportHelperImpl();
				List<WaterMeterMataData> dataList = aa.getWaterMeterBetween(
						WaterMeterID, start, end);
				/*
				 * 将每小时的用水量和模型进行匹配，匹配上的就将报警信心放在table里面， 匹配完成后发送报警信息，更新时间
				 */

				for (WaterMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型

					String num = modelRs.getString(hour + 2);
					if ("".equals(num) || num == null)
					{
						num = "0";
					}
					// 模型值
					float modelNum = Float.parseFloat(num);
					// 实际值
					float actualNum = n.getValue();

					if (actualNum > modelNum)
					{
						String theDate = noMinDF.format(dataTime);

						// 检测是否有记录
						boolean has = false;
						for (String date : table)
						{
							if (date.equals(theDate))
							{
								has = true;
								break;
							}
						}

						if (!has)
						{
							table.add(theDate);
						}

					}

				}
				Connection conn4 = null;
				PreparedStatement prep = null;
				ResultSet drPhon=null;
				if (table.size() > 0)
				{
					String warning = null;
					String SMSWill_PhoneList = "";
					String WaterMeter_Name = "";
					String strsql = "select WaterMeter_Name,ConsumerPhone,(select Architecture_Phone from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Phone,(select Area_Phone from (Area)c where a.Area_ID=c.Area_ID) as Area_Phone,(select Partment_Phone from (Partment)d where a.Partment=d.Partment_ID) as Partment_Phone from (WaterMeter)a where WaterMeter_ID="
							+ WaterMeter_ID;
					try
					{
						conn4 = ConnDB.getConnection();
						prep = conn4.prepareStatement(strsql);
						drPhon = prep.executeQuery();
						WaterMeter_Name = drPhon.getString("WaterMeter_Name");
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}finally{
						ConnDB.release(conn4, prep, drPhon);
					}
					// 插入报警表
					Connection conn1 = null;
					PreparedStatement warningPs = null;
					for (String objDay : table)
					{
						String tablestring = objDay + " ";
						// 水报警表里添加报警数据
						String sqlsql = "insert into WaterBaojing(WaterBaojing_Style,WaterBaojing_Time,WaterMeter_ID,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime) values"
								+ "(1,sysdate,"
								+ WaterMeter_ID
								+ ",'"
								+ WaterMeter_Name
								+ ": "
								+ tablestring
								+ "用水匹配模型报警',0,sysdate)";

						try
						{
							conn1 = ConnDB.getConnection();
							warningPs = conn1.prepareStatement(sqlsql);
							warningPs.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(conn1, warningPs);
						}
					}
					

					// 组建推荐的报警信息,推送到页面上
					for (String date : table)
					{
						warning = "水表：" + WaterMeter_Name + " 在" + date
								+ "违反了24小时用水匹配模型，请及时检查。\r\n";
						sendWarningToServer(warning);
					}

				}
				
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, modelPs, modelRs);
		}
		
		// 往历史数据表中添加信息
				String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('13',sysdate,'定时任务执行：整理用水匹配模型数据',to_date('"
						+ startTime
						+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
						+ endTime
						+ "','yyyy-mm-dd hh24:mi:ss'),"+UserID+")";
				Connection conn2 = null;
				PreparedStatement ps1 = null;
				try
				{
					conn2 = ConnDB.getConnection();
					ps1 = conn2.prepareStatement(
							insertSql);
					ps1.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn2, ps1);
				}
		return null;
	}

	private String addDate(String date, int days) throws ParseException
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = df1.parse(date);
		Calendar cld = Calendar.getInstance();
		cld.setTime(date1);
		cld.add(Calendar.DATE, days);
		Date time = cld.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String temp = df.format(time);
		return temp;
	}

	private int getHour(Date time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int theHour = cal.get(Calendar.HOUR_OF_DAY);
		return theHour;
	}

	private int getHour(String time)
	{
		String thehour;
		int hour;
		String firstString = time.substring(0, 1);
		if ("0".equals(firstString))
			thehour = time.substring(1, 2);
		else
			thehour = time.substring(0, 2);
		hour = Integer.parseInt(thehour);
		return hour;
	}

	private int getHour2(String time)
	{
		String theHour = "0";
		int hour = 0;
		String sign = time.substring(0, 1);
		if ("0".equals(sign))
			theHour = time.substring(1, 2);
		else
			theHour = time.substring(0, 2);
		hour = Integer.parseInt(theHour);
		return hour;
	}

	private void sendWarningToServer(String warning)
			throws UnknownHostException, IOException
	{
		XMLConfiguration config=null;
		
		try
		{
			config=com.sf.energy.util.Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		String IP=config.getString("server.IP");
		int port=Integer.parseInt(config.getString("server.port"));
		PlanTaskSendDao p = new PlanTaskSendDao(IP, port);
		p.start(warning);
	}

}
