package com.sf.energy.server.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import com.sf.energy.server.tftcp.Tool;
import com.sf.energy.util.ConnDB;

/**
 * 定时任务执行线程
 * 
 * @author czy
 * @version 1.0
 * @since [盛帆电子/服务器]
 */
public class MyTask extends TimerTask
{
	private static final String UserID = null;
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	// 格式时间的工具
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// 定时任务执行代码
	@Override
	public void run()
	{
		if (TimerToTask.taskList.size() == 0)
		{
			//System.out.println("不存在任务!定时任务队列为空！");
			return;
		}

		for (int i = 0; i < TimerToTask.taskList.size(); i++)
		{

			Task task = TimerToTask.taskList.get(i);

			// 获取下次执行时间
			Date nextTime = task.getPlanTask_NextTime();
			//System.out.println("任务" + i + ":" + task.getPlanTask_Style() + "的nextTime:" + df.format(nextTime));
			Date now = new Date();
			// 时间到了并未执行 就执行任务
			if (nextTime.before(now) && task.getExecState() == 0)
			{
				//System.out.println("开始执行任务" + task.getPlanTask_Style());
				task.setExecState(1);
				int taskID = task.getPlanTask_ID();
				Lamp lamp=null;
				try
				{

					if ("61".equals(task.getPlanTask_Style())) // 设置开关时间需特殊处理
					{
						lamp=Lamp.getInstance();
						lamp.SetLampSchedule();
						String strsql = "update PlanTask set PlanTask_LastTime=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),PlanTask_LastState=1 where PlanTask_ID="
								+ task.getPlanTask_ID();
						Connection conn=null;
						PreparedStatement ps =null;
						try
						{
							conn = ConnDB.getConnection();
							ps = conn.prepareStatement(strsql);
							ps.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(conn, ps);
						}
//						PreparedStatement ps = null;
//						ps = ConnDB.getConnection().prepareStatement(strsql);
//						ps.executeUpdate();
//
//						if (ps != null)
//							ps.close();
					} else
					{
						planTask_exec(taskID, "0");
					}
					// 任务执行完成，修改对象属性
					task.setExecState(0);

					task.reMoveNext();

					// 更新下一次的执行时间
					String nextTime1 = df.format(task.getPlanTask_NextTime());
					String sql = "update PlanTask set PlanTask_NextTime='"
							+ nextTime1 + "' where PlanTask_ID="
							+ task.getPlanTask_ID();
					Connection conn=null;
					PreparedStatement ps =null;
					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql);
						ps.executeUpdate();
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps);
					}

				} catch (SQLException e1)
				{
					e1.printStackTrace();
				} catch (ParseException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				// 不再执行就将其移除
				if (!task.isExec())
				{
					TimerToTask.taskList.remove(i);
				}
			}

		}

	}

	
	

	/**
	 * 定时执行计划任务
	 * 
	 * @param taskID
	 *            任务ID
	 * @param BeginTime
	 *            起始时间
	 * @param EndTime
	 *            结束时间
	 * @throws SQLException
	 *             执行存储过程抛出SQL异常
	 * @throws ParseException
	 */
	public void planTask_exec(int taskID, String userID) throws SQLException,
			ParseException
	{
		String style = null;
		String result = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 读取数据库planTask_style
		String sql = "select PlanTask_Style from PlanTask where PlanTask_ID="
				+ taskID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next())
				{
					style = rs.getString("PlanTask_Style");
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

			// 对style 进行匹配
			switch (style)
			{
			case "01":// 整理用电日数据
				zlDayAm();
				break;
			case "02":// 整理用电月数据
				zLMonthAm();
				break;
			case "11":// 整理用水日数据
				zlDayWater();
				break;
			case "12":// 整理用水月数据
				zLMonthWater();
				break;
			case "03":// 整理待机功耗
				Date Time = new Date();
				SimpleDateFormat df1 = new SimpleDateFormat(
						"yyyy-MM-dd 00:00:00");
				SimpleDateFormat df2 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:00");
				String startTime = df1.format(Time);
				String endTime = df2.format(Time);
				try
				{
					result = execDJGHData(startTime, endTime, "0");
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "04": // 整理用电匹配模型
				Date time = new Date();
				SimpleDateFormat df3 = new SimpleDateFormat(
						"yyyy-MM-dd 00:00:00");
				SimpleDateFormat df4 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:00");
				String startTime1 = df3.format(time);
				String endTime1 = df4.format(time);
				try
				{
					result = execPPMXData(startTime1, endTime1, "0");
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "13":// 整理用水匹配模型
				Date time2 = new Date();
				SimpleDateFormat df5 = new SimpleDateFormat(
						"yyyy-MM-dd 00:00:00");
				SimpleDateFormat df6 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:00");
				String startTime2 = df5.format(time2);
				String endTime2 = df6.format(time2);
				try
				{
					result = ExecPPMXDataWater(startTime2, endTime2, "0");
				} catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			// 更新上一次的执行时间
			Date lastTime = new Date();
			String lastTime1 = df.format(lastTime);
			String strsql = "update PlanTask set PlanTask_LastTime='"
					+ lastTime1 + "',PlanTask_LastState='1' where PlanTask_ID="
					+ taskID;
			Connection conn2=null;
			PreparedStatement ps2 =null;
			try
			{
				conn2 = ConnDB.getConnection();
				ps2 = conn2.prepareStatement(strsql);
				ps2.executeUpdate();
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn2, ps2);
			}
			
//			PreparedStatement ps2 = null;
//			ps2 = ConnDB.getConnection().prepareStatement(strsql);
//			int k = ps2.executeUpdate();
//			if (ps2 != null)
//				ps2.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
	private void zLMonthWater() throws SQLException, ParseException
	{
		SimpleDateFormat toMonth = new SimpleDateFormat("yyyy-mm");
		String beginTime = toMonth.format(new Date());
		String endTime = toMonth.format(new Date());
		String strSql = "select nvl(max(to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99'),'yyyy-mm')),sysdate) as BeginTime from T_ArcDayWater";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				beginTime = rs.getString("BeginTime");
				beginTime = toMonth.format(toMonth.parse(beginTime));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			beginTime = rs.getString("BeginTime");
//			beginTime = toMonth.format(toMonth.parse(beginTime));
//		}

		// /创建存储过程对象
		CallableStatement c=null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			c = conn1
					.prepareCall("{ call VES_ZLMonthWater(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn1.commit();
		} catch (Exception e)
		{
			conn1.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, c);
		}

		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('12',sysdate,'定时任务执行：整理用水月数据',to_date('"
				+ beginTime
				+ "','yyyy-mm'),to_date('"
				+ endTime
				+ "','yyyy-mm'),0)";

		Connection conn0=null;
		PreparedStatement ps0 =null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps0 = conn0.prepareStatement(insertSql);
			ps0.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps0);
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
	private void zLMonthAm() throws SQLException, ParseException
	{
		SimpleDateFormat toMonth = new SimpleDateFormat("yyyy-mm");
		String beginTime = toMonth.format(new Date());
		String endTime = toMonth.format(new Date());
		String strSql = "select nvl(max(to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99'),'yyyy-mm')),sysdate) as BeginTime from T_ArcDayAm";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				beginTime = rs.getString("BeginTime");
				beginTime = toMonth.format(toMonth.parse(beginTime));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			beginTime = rs.getString("BeginTime");
//			beginTime = toMonth.format(toMonth.parse(beginTime));
//		}
//
//		if (rs != null)
//			rs.close();
//		if (ps != null)
//			ps.close();
		// /创建存储过程对象
		CallableStatement c =null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			c = conn0.prepareCall("{ call VES_ZLMonthAm(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn0.commit();
		} catch (Exception e)
		{
			conn0.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, c);
		}

		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('02',sysdate,'定时任务执行：整理用电月数据',to_date('"
				+ beginTime
				+ "','yyyy-mm'),to_date('"
				+ endTime
				+ "','yyyy-mm'),0)";
		Connection conn1=null;
		PreparedStatement ps1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
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
	 * @throws ParseException
	 */
	private void zlDayWater() throws SQLException, ParseException
	{
		SimpleDateFormat toDay = new SimpleDateFormat("yyyy-mm-dd");
		String beginTime = toDay.format(new Date());
		String endTime = toDay.format(new Date());
		String strSql = "select nvl(max(to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd')),sysdate) as BeginTime from T_ArcDayWater";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				beginTime = rs.getString("BeginTime");
				beginTime = toDay.format(toDay.parse(beginTime));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			beginTime = rs.getString("BeginTime");
//			beginTime = toDay.format(toDay.parse(beginTime));
//		}

		// /创建存储过程对象
		CallableStatement c=null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			c = conn1.prepareCall("{ call VES_ZLDayWater(?, ?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn1.commit();
		} catch (Exception e)
		{
			conn1.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, c);
		}
	
		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('11',sysdate,'定时任务执行：整理用水日数据',to_date('"
				+ beginTime
				+ "','yyyy-mm-dd'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd'),0)";
		PreparedStatement ps1=null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			ps1 = conn0.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn0, ps1);
		}
	}

	/**
	 * 按日整理电数据
	 * 
	 * @return 异常信息
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void zlDayAm() throws SQLException, ParseException
	{
		SimpleDateFormat toDay = new SimpleDateFormat("yyyy-mm-dd");
		String beginTime = toDay.format(new Date());
		String endTime = toDay.format(new Date());
		String strSql = "select nvl(max(to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd')),sysdate) as BeginTime from T_ArcDayAm";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				beginTime = rs.getString("BeginTime");
				beginTime = toDay.format(toDay.parse(beginTime));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			beginTime = rs.getString("BeginTime");
//			beginTime = toDay.format(toDay.parse(beginTime));
//		}

		// /创建存储过程对象
		CallableStatement c=null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			c = conn0.prepareCall("{ call VES_ZLDayAm(?,?) }");
			c.setString(1, beginTime);
			c.setString(2, endTime);
			c.execute();
			conn0.commit();
		} catch (Exception e)
		{
			conn0.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, c);
		}

		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('01',sysdate,'定时任务执行：整理用电日数据',to_date('"
				+ beginTime
				+ "','yyyy-mm-dd'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd'),0)";
		PreparedStatement ps1=null;
		Connection conn1= null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
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
	 */
	private String execPPMXData(String startTime, String endTime, String userId)
			throws SQLException, ParseException
	{
		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

		Date start = standardDF.parse(startTime);
		// Date end=standardDF.parse(endTime);

		// 查询要执行用电匹配模型的所有点表
		// 1 2 3 4 5 6
		String sql = "select b.AmMeter_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23,LastCheckTime from (AmMeter)a,(AmMatchModel)b where a.AmMeter_ID=b.AmMeter_ID and CostCheck=1";
		PreparedStatement modelDataps =null;
		ResultSet modelRs =null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			modelDataps = conn0
					.prepareStatement(sql);
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
				String lastCheckTime = modelRs.getString("LastCheckTime");

				if (lastCheckTime == null || " ".equals(lastCheckTime))
				{
					lastCheckhour = 0;
				} else
				{
					// 若上次查询时间是在同一天，checkHour以前的数据将不在机型匹配
					String day = standardDF.format(start);
					day = day.substring(0, 10);
					String checkDay = lastCheckTime.substring(0, 10);
					if (day.equals(checkDay))
					{
						lastCheckDate = standardDF.parse(lastCheckTime);
						lastCheckhour = getHour(lastCheckDate);
					} else
					{
						lastCheckhour = 0;
					}
				}

				// 获得时间段内每小时的用电量。
				//System.out.println("获取各个小时的用电量");
				List<AmMeterMataData> dataList = getAmMeterBetween(AmMeterID,
						startTime, endTime);

				/*
				 * 将每小时的用电量和模型进行匹配，匹配上的就将报警信心放在table里面， 匹配完成后发送报警信息，更新时间
				 */
				int index = 2;
				for (AmMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型
					if (hour >= lastCheckhour)
					{
						index = hour + 2;
						String num = modelRs.getString(index);
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
				}

				// 如果有错误信息
				if (table.size() > 0)
				{
					String warning = "";
					// String SMSWill_PhoneList = "";
					String AmMeter_Name = "";
					String strsql = "select AmMeter_Name,ConsumerPhone,( select Architecture_Phone	from (Architecture)b 	where a.Architecture_ID=b.Architecture_ID ) as Architecture_Phone,(	select Area_Phone from (Area)b where a.Area_ID=b.Area_ID ) as Area_Phone,(select Partment_Phone from (Partment)b where a.Partment_id=b.Partment_ID)as Partment_Phone from (AmMeter)a where AmMeter_ID="
							+ ammeter_ID;
					PreparedStatement prep=null;
					ResultSet drPhon =null;
					Connection connx  =null;
					
					try
					{
						connx = ConnDB.getConnection();
						prep = connx
								.prepareStatement(strsql);
						drPhon = prep.executeQuery();
						if (drPhon.next())
						{
							AmMeter_Name = drPhon.getString("AmMeter_Name");
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(connx, prep, drPhon);
					}
					// 插入报警表
					
					for (String objDay : table)
					{
						String tablestring = objDay + " ";
						// 报警表里添加报警数据 匹配模型的报警类型是1
						String sqlsql = "insert into AmBaojing(AmBaojing_Style,AmBaojing_Time,AmMeter_ID,AmBaojing_Remark,AmBaojing_SendSMS,inserttime) values("
								+ "1,sysdate,"
								+ ammeter_ID
								+ ",'"
								+ AmMeter_Name
								+ ": " + tablestring + "用电匹配模型报警',0,sysdate)";
						PreparedStatement warningPs = null;
						Connection  connz = null;
						try
						{
							connz = ConnDB.getConnection();
							warningPs = connz.prepareStatement(sqlsql);
							warningPs.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(connz, warningPs);
						}
				
					}

					

					// 组件这个网关的报警信息
					Tool tool = new Tool();
					for (String date : table)
					{
						warning = "电表：" + AmMeter_Name + " 在" + date
								+ "违反了24小时用电匹配模型，请及时检查。\r\n";
						tool.broadcast(warning);
						//System.out.println("发送用电匹配模型报警:" + warning);
					}

				}
				// 更新上一次的执行时间 在AmMatchModel
				PreparedStatement ps2 = null;
				Connection conn2 = null;
				String strsql2 = "update AmMatchModel set LastCheckTime=to_date('"
						+ endTime + "','yyyy-mm-dd hh24:mi:ss') where AmMeter_ID="
						+ ammeter_ID;
				try
				{
					conn2 = ConnDB.getConnection();
					ps2 = conn2.prepareStatement(strsql2);
					ps2.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn2, ps2);
				}
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, modelDataps, modelRs);
		}
		// 往历史数据表中添加信息
		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('04',sysdate,'定时任务执行：整理用电匹配模型数据',to_date('"
				+ startTime
				+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd hh24:mi:ss'),0)";
		PreparedStatement ps1 = null;
		Connection conn1 = null;
		try
		{
			conn1= ConnDB.getConnection();
			ps1 = conn1.prepareStatement(
					insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
		}

		return null;
	}

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
	 */
	private String execDJGHData(String startTime, String endTime, String string)
			throws SQLException, ParseException
	{

		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

		Date start = standardDF.parse(startTime);

		String sql = "select b.AmMeter_ID,StartTime,EndTime,LowLimit,UpperLimit,LastCheckTime from (AmMeter)a,(AmStandbyModel)b where a.AmMeter_ID=b.AmMeter_ID and StandByCheck=1";
		PreparedStatement modelPs=null;
		ResultSet modelRs =null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			modelPs = conn0
					.prepareStatement(sql);
			modelRs = modelPs.executeQuery();

			int hour = 0;
			int lastCheckhour = 0;
			Date lastCheckDate;
			while (modelRs.next())
			{
				String ammeter_ID = modelRs.getString("AmMeter_ID");
				int AmMeterID = Integer.parseInt(ammeter_ID);
				String lastCheckTime = modelRs.getString("LastCheckTime");

				if (lastCheckTime == null || " ".equals(lastCheckTime))
				{
					lastCheckhour = 0;
				} else
				{
					// 若上次查询时间是在同一天，checkHour以前的数据将不在机型匹配
					String day = standardDF.format(start);
					day = day.substring(0, 10);
					String checkDay = lastCheckTime.substring(0, 10);
					if (day.equals(checkDay))
					{
						lastCheckDate = standardDF.parse(lastCheckTime);
						lastCheckhour = getHour(lastCheckDate);
					} else
					{
						lastCheckhour = 0;
					}
				}
				String startHour = modelRs.getString("StartTime");
				String endHour = modelRs.getString("EndTime");
				int lowHour = getHour2(startHour);
				int upHour = getHour2(endHour);

				ArrayList<String> table = new ArrayList<String>();
				// 获取限值
				float lowLimit = modelRs.getFloat("LowLimit");
				float upperLimit = modelRs.getFloat("UpperLimit");

				// 获得时间段内每小时的用电量。
				List<AmMeterMataData> dataList = getAmMeterBetween(AmMeterID,
						startTime, endTime);

				for (AmMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型
					if (hour >= lastCheckhour && hour >= lowHour && hour <= upHour)
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
							+ ammeter_ID;
					PreparedStatement prep = null;
					ResultSet drPhon = null;
					Connection connx = null;
					try
					{
						connx = ConnDB.getConnection();
						prep = connx
								.prepareStatement(strsql);
						drPhon = prep.executeQuery();
						if (drPhon.next())
							AmMeter_Name = drPhon.getString("Meter_Name");
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(connx, prep, drPhon);
					}

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
						PreparedStatement warningPs = null;
						Connection connz = null;
						try
						{
							connz = ConnDB.getConnection();
							warningPs = connz.prepareStatement(sqlsql);
							warningPs.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(connz, warningPs);
						}
					}

					

					// 组建报警信息
					Tool tool = new Tool();
					String warning = "";
					for (String theDate : table)
					{
						warning = "电表:" + AmMeter_Name + " 在" + theDate
								+ "违反了待机功耗模型,请核查.\r\n";
						tool.broadcast(warning);
						//System.out.println("发送待机功耗报警:" + warning);
					}

					// 更新上一次的检测结束时间
					PreparedStatement ps2 = null;
					Connection conn2 = null;
					String strsql2 = "update AmStandbyModel set LastCheckTime=to_date('"
							+ endTime
							+ "','yyyy-mm-dd hh24:mi:ss') where AmMeter_ID="
							+ ammeter_ID;
					try
					{
						conn2 = ConnDB.getConnection();
						ps2 = conn2.prepareStatement(strsql2);
						ps2.executeUpdate();
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn2, ps2);
					}
				}
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, modelPs, modelRs);
		}

		// 往历史数据表中添加信息
		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('03',sysdate,'定时任务执行：整理待机功耗模型数据',to_date('"
				+ startTime
				+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd hh24:mi:ss'),0)";
		PreparedStatement ps1=null;
		Connection conn1 = null;
		try
		{
			conn1=ConnDB.getConnection();
			ps1 = conn1.prepareStatement(
					insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
		}

		return null;
	}

	/**
	 * 查询得到指定电表在特定时间段内的数据
	 * 
	 * @param ammeterID
	 *            电表ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点
	 * @return List<AmMeterMataData> 查询结果集
	 * @throws SQLException
	 * 
	 */
	private List<AmMeterMataData> getAmMeterBetween(int ammeterID,
			String start, String end) throws SQLException
	{
		Connection conn0 = null;
		PreparedStatement startValuepstmt = null;
		ResultSet startRs = null;
		float startValue = -1;
		float theValue = -1;
		// 获取要查询日期的前一个小时的最大值作为初始值
		String startValueSql = "select ValueTime,ZValueZY from ZAMDATAS"+String.valueOf(ammeterID)+" where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss')-1/24 and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ "order by ValueTime desc";
		try
		{
			conn0 = ConnDB.getConnection();
			startValuepstmt = conn0
					.prepareStatement(startValueSql);
			startValuepstmt.setString(1, start);
			startValuepstmt.setString(2, start);
			//startValuepstmt.setInt(3, ammeterID);
			startRs = startValuepstmt.executeQuery();
			if (startRs.next())
				startValue = startRs.getFloat("ZValueZY");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, startValuepstmt, startRs);
		}

		List<AmMeterMataData> list = new LinkedList<AmMeterMataData>();
		AmMeterMataData amd = null;
		Timestamp ts = null;
		String sql = null;
		
		Connection conn1 = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		sql = "select ValueTime,ZValueZY from ZAMDATAS"+String.valueOf(ammeterID)+" where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ "and order by ValueTime";
		try
		{
			conn1 = ConnDB.getConnection();
			pstmt = conn1.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			//pstmt.setInt(3, ammeterID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// 若初始值为-1 ，将当天的第一个值作为初始值。
				if (startValue == -1)
					startValue = rs.getFloat("ZValueZY");

				if (list.isEmpty())
				{
					amd = new AmMeterMataData();
					amd.setAmMeterID(ammeterID);
					ts = rs.getTimestamp("ValueTime");
					theValue = rs.getFloat("ZValueZY");
					ts.setMinutes(0);
					ts.setSeconds(0);
					amd.setRecordTimeDate(ts);
					amd.setValue(theValue - startValue);
					list.add(amd);
				} else
				{
					ts = rs.getTimestamp("ValueTime");
					ts.setMinutes(0);
					ts.setSeconds(0);
					if (ts.getYear() == amd.getRecordTimeDate().getYear()
							&& ts.getMonth() == amd.getRecordTimeDate().getMonth()
							&& ts.getDate() == amd.getRecordTimeDate().getDate()
							&& ts.getHours() == amd.getRecordTimeDate().getHours()
							&& rs.getFloat("ZValueZY") > amd.getValue())
					{
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
					} else
					{
						amd = new AmMeterMataData();
						amd.setAmMeterID(ammeterID);
						amd.setRecordTimeDate(ts);
						startValue = theValue;
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
						list.add(amd);
					}
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, pstmt,rs);
		}

		return list;
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
	 */
	private String ExecPPMXDataWater(String startTime, String endTime,
			String UserID) throws SQLException, ParseException
	{
		SimpleDateFormat standardDF = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat noMinDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

		Date start = standardDF.parse(startTime);

		String strSql = "select b.WaterMeter_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23,LastCheckTime from (WaterMeter)a,(WaterMatchModel)b where a.WaterMeter_ID=b.WaterMeter_ID and CostCheck=1";
		Connection conns = null;
		PreparedStatement modelPs = null;
		ResultSet modelRs = null;
		// 循环检测这些水表的值是否超出模型

		int hour = 0;
		int lastCheckhour = 0;
		Date lastCheckDate;
		try
		{
			conns = ConnDB.getConnection();
			modelPs = conns.prepareStatement(strSql);
			modelRs = modelPs.executeQuery();
			while (modelRs.next())
			{
				String WaterMeter_ID = modelRs.getString("WaterMeter_ID");

				ArrayList<String> table = new ArrayList<String>();

				int WaterMeterID = Integer.parseInt(WaterMeter_ID);

				// 获得上一次执行的时间
				String lastCheckTime = modelRs.getString("LastCheckTime");

				if (lastCheckTime == null || " ".equals(lastCheckTime))
				{
					lastCheckhour = 0;
				} else
				{
					// 若上次查询时间是在同一天，checkHour以前的数据将不在机型匹配
					String day = standardDF.format(start);
					day = day.substring(0, 10);
					String checkDay = lastCheckTime.substring(0, 10);
					if (day.equals(checkDay))
					{
						lastCheckDate = standardDF.parse(lastCheckTime);
						lastCheckhour = getHour(lastCheckDate);
					} else
					{
						lastCheckhour = 0;
					}
				}
				// 获得时间段内每小时的用水量
				//System.out.println("获取各个小时的用水量");
				List<WaterMeterMataData> dataList = getWaterMeterBetween(
						WaterMeterID, startTime, endTime);

				/*
				 * 将每小时的用水量和模型进行匹配，匹配上的就将报警信心放在table里面， 匹配完成后发送报警信息，更新时间
				 */
				int index = 2;
				for (WaterMeterMataData n : dataList)
				{
					// 数据的发生时间
					Date dataTime = n.getRecordTimeDate();
					hour = getHour(dataTime);

					// 数据发生的时间如果大于上次查询时间就开始匹配模型
					if (hour >= lastCheckhour)
					{
						index = hour + 2;
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
				}

				if (table.size() > 0)
				{
					String warning = "";
					String SMSWill_PhoneList = "";
					String WaterMeter_Name = "";
					String strsql = "select WaterMeter_Name,ConsumerPhone,(select Architecture_Phone from (Architecture)b where a.Architecture_ID=b.Architecture_ID) as Architecture_Phone,(select Area_Phone from (Area)c where a.Area_ID=c.Area_ID) as Area_Phone,(select Partment_Phone from (Partment)d where a.Partment=d.Partment_ID) as Partment_Phone from (WaterMeter)a where WaterMeter_ID="
							+ WaterMeter_ID;
					Connection conn0 =null;
					PreparedStatement prep =null;
					ResultSet drPhon = null;
					try
					{
						conn0 = ConnDB.getConnection();
						prep = conn0
								.prepareStatement(strsql);
						drPhon = prep.executeQuery();
						WaterMeter_Name = drPhon.getString("WaterMeter_Name");
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn0, prep, drPhon);
					}
					// 插入报警表
					
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

						PreparedStatement warningPs = null;
						Connection connps = null;
						try
						{
							connps = ConnDB.getConnection();
							warningPs = connps.prepareStatement(sqlsql);
							warningPs.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(connps, warningPs);
						}

					}
					
					Tool tool = new Tool();
					// 组建推荐的报警信息,推送到页面上
					for (String date : table)
					{
						warning = "水表：" + WaterMeter_Name + " 在" + date
								+ "违反了24小时用水匹配模型，请及时检查。\r\n";
						tool.broadcast(warning);
						//System.out.println("发送用水匹配模型报警:" + warning);
					}

				}

				// 更新上一次的执行时间 在AmMatchModel
				PreparedStatement ps2 = null;
				Connection conn2 = null;
				String strsql2 = "update WaterMatchModel set LastCheckTime=to_date('"
						+ endTime
						+ "','yyyy-mm-dd hh24:mi:ss') where AmMeter_ID="
						+ WaterMeter_ID;
				try
				{
					conn2 = ConnDB.getConnection();
					ps2 = conn2.prepareStatement(strsql2);
					ps2.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn2, ps2);
				}
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conns, modelPs, modelRs);
		}

		// 往历史数据表中添加信息
		String insertSql = "insert into LogHistoryData(PlanTask_Style,Exec_Time,Exec_Log,Star_Time,End_Time,Exec_Opr) values ('13',sysdate,'定时任务执行：整理用水匹配模型数据',to_date('"
				+ startTime
				+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
				+ endTime
				+ "','yyyy-mm-dd hh24:mi:ss'),0)";
		PreparedStatement ps1 = null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(
					insertSql);
			ps1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
		}

		return null;
	}

	/**
	 * 查询得到指定水表在特定时间段内的数据
	 * 
	 * @param ammeterID
	 *            水表ID
	 * @param start
	 *            起始时间点
	 * @param end
	 *            截止时间点(是要查询的那一天加上一天)
	 * @return List<WaterMeterMataData> 查询结果集
	 * @throws SQLException
	 * 
	 */
	private List<WaterMeterMataData> getWaterMeterBetween(int ammeterID,
			String start, String end) throws SQLException
	{
		PreparedStatement startValuepstmt = null;
		ResultSet startRs = null;
		Connection conn0= null;
		float startValue = -1;
		float theValue = -1;
		// 获取要查询日期的前一个小时的最大值作为初始值
		String startValueSql = "select ValueTime,ZValueZY from ZWATERDATAS"+String.valueOf(ammeterID)+" where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss')-1/24 and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ "order by ValueTime desc";
		try
		{
			conn0 = ConnDB.getConnection();
			startValuepstmt = conn0
					.prepareStatement(startValueSql);
			startValuepstmt.setString(1, start);
			startValuepstmt.setString(2, start);
			//startValuepstmt.setInt(3, ammeterID);
			startRs = startValuepstmt.executeQuery();
			if (startRs.next())
				startValue = startRs.getFloat("ZValueZY");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, startValuepstmt, startRs);
		}

		List<WaterMeterMataData> list = new LinkedList<WaterMeterMataData>();
		WaterMeterMataData amd = null;
		Timestamp ts = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		// 查询要查询的日起大田的所有数据，按升序排列
		sql = "select ValueTime,ZValueZY from ZWATERDATAS"+String.valueOf(ammeterID)+" where ValueTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ "order by ValueTime";
		try
		{
			conn =ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			//pstmt.setInt(3, ammeterID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				// 若初始值为-1 ，将当天的第一个值作为初始值。
				if (startValue == -1)
					startValue = rs.getFloat("ZValueZY");

				// 若list中为空说明
				if (list.isEmpty())
				{
					amd = new WaterMeterMataData();
					amd.setWaterMeterID(ammeterID);
					ts = rs.getTimestamp("ValueTime");
					theValue = rs.getFloat("ZValueZY");
					ts.setMinutes(0);
					ts.setSeconds(0);
					amd.setRecordTimeDate(ts);
					amd.setValue(theValue - startValue);
					list.add(amd);
				} else
				{
					ts = rs.getTimestamp("ValueTime");
					ts.setMinutes(0);
					ts.setSeconds(0);
					if (ts.getYear() == amd.getRecordTimeDate().getYear()
							&& ts.getMonth() == amd.getRecordTimeDate().getMonth()
							&& ts.getDate() == amd.getRecordTimeDate().getDate()
							&& ts.getHours() == amd.getRecordTimeDate().getHours()
							&& rs.getFloat("ZValueZY") > amd.getValue())
					{
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
					} else
					{
						amd = new WaterMeterMataData();
						amd.setWaterMeterID(ammeterID);
						amd.setRecordTimeDate(ts);
						startValue = theValue;
						theValue = rs.getFloat("ZValueZY");
						amd.setValue(theValue - startValue);
						list.add(amd);
					}
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pstmt, rs);
		}

		return list;
	}

	/**
	 * 关闭查询链接，回收资源
	 * 
	 * @throws SQLException
	 *             void
	 */
	private void close(PreparedStatement pstmt, ResultSet rs)
			throws SQLException
	{
		if (rs != null)
			rs.close();

		if (pstmt != null)
			pstmt.close();

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

	private int getTodayYear(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = 0;
		year = Integer.parseInt(df.format(time));
		return year;
	}

	private int getTodayMonth(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM");
		int month = 0;
		month = Integer.parseInt(df.format(time));
		return month;
	}

	private int getTodayDay(Date time)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd");
		int day = 0;
		day = Integer.parseInt(df.format(time));
		return day;
	}
	private String padLeft0(String canshu)
	{
		if(canshu.length()<2)
		{
			canshu="0"+canshu;
		}
		return canshu;
	}
	private String jiaoZhengminute(String min)
	{
		int mi=Integer.parseInt(min)/5;
		String result=String.valueOf(mi);
		return result;
	}
}
