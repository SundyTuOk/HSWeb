package com.sf.energy.server.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sf.energy.util.ConnDB;
import com.sun.mail.imap.Utility.Condition;

/**
 * 定时任务执行任务的主要类
 * 
 * @author czy
 * @version 1.0
 * @since [盛帆电子/服务器]
 */
public class TimerToTask
{

	static ArrayList<Task> taskList = new ArrayList<Task>();
	private Timer timer = null;
	private Timer sendCommandTimer=null;

	public TimerToTask()
	{
		MyTask aa=new MyTask();
		
		try
		{
			addPlanTask();
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		// 初始化定时器
		timer = new Timer();
		// 设置定时周期
		timer.schedule(new MyTask(), 180000, 60000);
		
//		//建立命令发送的定时器
//		sendCommandTimer=new Timer();
//		sendCommandTimer.schedule(new LampComandCheck(), 40000, 5000);
	}

	/**
	 * 从数据库读取任务添加到任务队列
	 * @throws SQLException 
	 */
	public static void addPlanTask() throws SQLException
	{
		if (taskList.size() != 0)
		{
			taskList.removeAll(taskList);
		}
		ResultSet rs = null;
		String strsql = "select *  from PlanTask where (PlanTask_Exec='周期运行' or PlanTask_Exec='定时运行') and PlanTask_Style in('01','02','03','04','11','12','13','14','51','61')";
		PreparedStatement ps = null;
		Connection conn = null;
		
		try
		{
			conn = ConnDB.getConnection();
			ps= conn.prepareStatement(strsql);
			rs=ps.executeQuery();
			while (rs.next())
			{
				ArrayList<String> value = new ArrayList<String>();
				value.add(rs.getString("PlanTask_ID"));
				value.add(rs.getString("PlanTask_Style"));
				value.add(rs.getString("PlanTask_LastTime"));
				value.add(rs.getString("PlanTask_LastState"));
				value.add(rs.getString("PlanTask_Exec"));
				value.add(rs.getString("PlanTask_01Time"));
				value.add(rs.getString("PlanTask_02Pinlv"));
				value.add(rs.getString("PlanTask_02PinlvD"));
				value.add(rs.getString("PlanTask_02PinlvMday"));
				value.add(rs.getString("PlanTask_02PinlvMmonth"));
				value.add(rs.getString("PlanTask_02PinlvStyle"));
				value.add(rs.getString("PlanTask_02PinlvTime"));
				value.add(rs.getString("PlanTask_02Zhouqi"));
				value.add(rs.getString("PlanTask_02ZhouqiStyle"));
				Task task;
				
				try
				{
					task = new Task(value);

					if (task.isExec())
					{
						
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String nextTime = df
								.format(task.getPlanTask_NextTime());
						strsql = "update PlanTask set PlanTask_NextTime='"
								+ nextTime + "' where PlanTask_ID="
								+ task.getPlanTask_ID();
						PreparedStatement ps1=null;
						Connection conn1 = null;						
						try
						{
							conn1 = ConnDB.getConnection();
							ps1= conn1.prepareStatement(strsql);
							ps1.executeUpdate();
						} catch (Exception e)
						{
							e.printStackTrace();
						}finally{
							ConnDB.release(conn1, ps1);
						}
						taskList.add(task);
					}
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

	}

	public ArrayList<Task> getTaskList()
	{
		return taskList;
	}
}
