package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.PlanTaskModel;
import com.sf.energy.util.ConnDB;

public class PlanTaskDao
{
	String taskID = null;
	String taskName = null;
	String operateStyle = null;
	String taskCondition = null;
	String operateState = null;
	String lastTime = " ";
	String nextTime = " ";
	String PlanTask_Style = " ";

	public String getPlanTask_Style()
	{
		return PlanTask_Style;
	}

	public void setPlanTask_Style(String planTask_Style)
	{
		PlanTask_Style = planTask_Style;
	}

	public ArrayList<PlanTaskDao> getAllTaskInfo(String tableName, String order)
			throws SQLException
	{
		ArrayList<PlanTaskDao> list = new ArrayList<PlanTaskDao>();

		String strSql = "select PlanTask_ID as taskID,PlanTask_Style,(select DictionaryValue_Value from"
				+ " DictionaryValue where Dictionary_ID=24 and  DictionaryValue_Num=PlanTask_Style) as taskName,PlanTask_Exec as operateStyle,PlanTask_Tiaojian as taskCondition,"
				+ "PlanTask_LastState as operateState,PlanTask_LastTime as lastTime,PLANTASK_NEXTTIME as nextTime from PlanTask order by "
				+ tableName + " " + order;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// //System.out.println(strSql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				PlanTaskDao task = new PlanTaskDao();
				if (rs.getString("taskID") != null)
					task.setTaskID(rs.getString("taskID"));
				else
					task.setTaskID(" ");

				if (rs.getString("PlanTask_Style") != null)
					task.setPlanTask_Style(rs.getString("PlanTask_Style"));
				else
					task.setTaskID(" ");

				if (rs.getString("taskName") != null)
					task.setTaskName(rs.getString("taskName"));
				else
					task.setTaskName(" ");

				if (rs.getString("operateStyle") != null)
					task.setOperateStyle(rs.getString("operateStyle"));
				else
					task.setOperateStyle(" ");

				if (rs.getString("taskCondition") != null)
					task.setTaskCondition(rs.getString("taskCondition"));
				else
					task.setTaskCondition(" ");

				if (rs.getString("operateState") != null)
					task.setOperateState(rs.getString("operateState"));
				else
					task.setOperateState(" ");

				if (rs.getString("lastTime") != null)
					task.setLastTime(rs.getString("lastTime"));
				else
					task.setLastTime(" ");

				if (rs.getString("nextTime") != null)
					task.setNextTime(rs.getString("nextTime"));
				else
					task.setNextTime(" ");

				list.add(task);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		// //System.out.println(strSql);
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			PlanTaskDao task = new PlanTaskDao();
//			if (rs.getString("taskID") != null)
//				task.setTaskID(rs.getString("taskID"));
//			else
//				task.setTaskID(" ");
//
//			if (rs.getString("PlanTask_Style") != null)
//				task.setPlanTask_Style(rs.getString("PlanTask_Style"));
//			else
//				task.setTaskID(" ");
//
//			if (rs.getString("taskName") != null)
//				task.setTaskName(rs.getString("taskName"));
//			else
//				task.setTaskName(" ");
//
//			if (rs.getString("operateStyle") != null)
//				task.setOperateStyle(rs.getString("operateStyle"));
//			else
//				task.setOperateStyle(" ");
//
//			if (rs.getString("taskCondition") != null)
//				task.setTaskCondition(rs.getString("taskCondition"));
//			else
//				task.setTaskCondition(" ");
//
//			if (rs.getString("operateState") != null)
//				task.setOperateState(rs.getString("operateState"));
//			else
//				task.setOperateState(" ");
//
//			if (rs.getString("lastTime") != null)
//				task.setLastTime(rs.getString("lastTime"));
//			else
//				task.setLastTime(" ");
//
//			if (rs.getString("nextTime") != null)
//				task.setNextTime(rs.getString("nextTime"));
//			else
//				task.setNextTime(" ");
//
//			list.add(task);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}

	public boolean deleteTask(String taskID) throws SQLException
	{
		String strSql = "delete from PlanTask where PlanTask_ID=" + taskID;
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int count = ps.executeUpdate();
			if (count != 0)
			{
				return true;
			} else
			{
				return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}finally{
			ConnDB.release(conn, ps);
		}

	}

	public boolean insertTask(PlanTaskModel ptm) throws SQLException
	{
		String sql = "INSERT INTO plantask(planTask_Style,planTask_Remark,planTask_Exec,planTask_TiaoJian,planTask_LastState,planTask_LastTime,planTask_NextTime,planTask_01Time,planTask_02Pinlv,planTask_02PinlvD,planTask_02PinlvMday,planTask_02PinlvMmonth,planTask_02PinlvStyle,planTask_02PinlvTime,planTask_02Zhouqi,planTask_02ZhouqiStyle,PlanTask_SMSWord)  "
				+ "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ptm.getPlanTask_Style());
			ps.setString(2, ptm.getPlanTask_Remark());
			ps.setString(3, ptm.getPlanTask_Exec());
			ps.setString(4, ptm.getPlanTask_TiaoJian());
			ps.setString(5, ptm.getPlanTask_LastState());
			ps.setString(6, ptm.getPlanTask_LastTime());
			ps.setString(7, ptm.getPlanTask_NextTime());
			ps.setString(8, ptm.getPlanTask_01Time());
			ps.setString(9, ptm.getPlanTask_02Pinlv());
			ps.setInt(10, ptm.getPlanTask_02PinlvD());
			ps.setInt(11, ptm.getPlanTask_02PinlvMday());
			ps.setInt(12, ptm.getPlanTask_02PinlvMmonth());
			ps.setInt(13, ptm.getPlanTask_02PinlvStyle());
			ps.setString(14, ptm.getPlanTask_02PinlvTime());
			ps.setInt(15, ptm.getPlanTask_02Zhouqi());
			ps.setString(16, ptm.getPlanTask_02ZhouqiStyle());
			ps.setString(17, ptm.getPlanTask_SMSWord());

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		PreparedStatement psmt = null;
//
//		psmt.setString(1, ptm.getPlanTask_Style());
//		psmt.setString(2, ptm.getPlanTask_Remark());
//		psmt.setString(3, ptm.getPlanTask_Exec());
//		psmt.setString(4, ptm.getPlanTask_TiaoJian());
//		psmt.setString(5, ptm.getPlanTask_LastState());
//		psmt.setString(6, ptm.getPlanTask_LastTime());
//		psmt.setString(7, ptm.getPlanTask_NextTime());
//		psmt.setString(8, ptm.getPlanTask_01Time());
//		psmt.setString(9, ptm.getPlanTask_02Pinlv());
//		psmt.setInt(10, ptm.getPlanTask_02PinlvD());
//		psmt.setInt(11, ptm.getPlanTask_02PinlvMday());
//		psmt.setInt(12, ptm.getPlanTask_02PinlvMmonth());
//		psmt.setInt(13, ptm.getPlanTask_02PinlvStyle());
//		psmt.setString(14, ptm.getPlanTask_02PinlvTime());
//		psmt.setInt(15, ptm.getPlanTask_02Zhouqi());
//		psmt.setString(16, ptm.getPlanTask_02ZhouqiStyle());
//		psmt.setString(17, ptm.getPlanTask_SMSWord());
//
//		boolean b = (psmt.executeUpdate() == 1);
//
//		if (psmt != null)
//			psmt.close();

		return b;
	}

	public PlanTaskModel queryByID(int planTask_ID) throws SQLException
	{
		PlanTaskModel ptm = new PlanTaskModel();

		String sql = "SELECT * FROM PlanTask WHERE planTask_ID=" + planTask_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				ptm.setPlanTask_ID(rs.getInt(1));
				ptm.setPlanTask_Style(rs.getString(2));
				ptm.setPlanTask_Remark(rs.getString(3));
				ptm.setPlanTask_Exec(rs.getString(4));
				ptm.setPlanTask_TiaoJian(rs.getString(5));
				ptm.setPlanTask_LastState(rs.getString(6));
				ptm.setPlanTask_LastTime(rs.getString(7));
				ptm.setPlanTask_NextTime(rs.getString(8));
				ptm.setPlanTask_01Time(rs.getString(9));
				ptm.setPlanTask_02Pinlv(rs.getString(10));
				ptm.setPlanTask_02PinlvD(rs.getInt(11));
				ptm.setPlanTask_02PinlvMday(rs.getInt(12));
				ptm.setPlanTask_02PinlvMmonth(rs.getInt(13));
				ptm.setPlanTask_02PinlvStyle(rs.getInt(14));
				ptm.setPlanTask_02PinlvTime(rs.getString(15));
				ptm.setPlanTask_02Zhouqi(rs.getInt(16));
				ptm.setPlanTask_02ZhouqiStyle(rs.getString(17));
				ptm.setPlanTask_SMSWord(rs.getString(18));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			ptm.setPlanTask_ID(rs.getInt(1));
//			ptm.setPlanTask_Style(rs.getString(2));
//			ptm.setPlanTask_Remark(rs.getString(3));
//			ptm.setPlanTask_Exec(rs.getString(4));
//			ptm.setPlanTask_TiaoJian(rs.getString(5));
//			ptm.setPlanTask_LastState(rs.getString(6));
//			ptm.setPlanTask_LastTime(rs.getString(7));
//			ptm.setPlanTask_NextTime(rs.getString(8));
//			ptm.setPlanTask_01Time(rs.getString(9));
//			ptm.setPlanTask_02Pinlv(rs.getString(10));
//			ptm.setPlanTask_02PinlvD(rs.getInt(11));
//			ptm.setPlanTask_02PinlvMday(rs.getInt(12));
//			ptm.setPlanTask_02PinlvMmonth(rs.getInt(13));
//			ptm.setPlanTask_02PinlvStyle(rs.getInt(14));
//			ptm.setPlanTask_02PinlvTime(rs.getString(15));
//			ptm.setPlanTask_02Zhouqi(rs.getInt(16));
//			ptm.setPlanTask_02ZhouqiStyle(rs.getString(17));
//			ptm.setPlanTask_SMSWord(rs.getString(18));
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return ptm;
	}

	public boolean updateTask(PlanTaskModel ptm) throws SQLException
	{
		String sql = "UPDATE PLANTASK SET planTask_Style=?, planTask_Remark=?, planTask_Exec=?, planTask_TiaoJian=?, "
				+ "planTask_LastState=?, planTask_LastTime=?, planTask_NextTime=?, planTask_01Time=?, planTask_02Pinlv=?, "
				+ "planTask_02PinlvD=?, planTask_02PinlvMday=?, planTask_02PinlvMmonth=?, planTask_02PinlvStyle=?, "
				+ "planTask_02PinlvTime=?, planTask_02Zhouqi=?, planTask_02ZhouqiStyle=?, planTask_SMSWord=? "
				+ "where planTask_ID=" + ptm.getPlanTask_ID();

		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ptm.getPlanTask_Style());
			ps.setString(2, ptm.getPlanTask_Remark());
			ps.setString(3, ptm.getPlanTask_Exec());
			ps.setString(4, ptm.getPlanTask_TiaoJian());
			ps.setString(5, ptm.getPlanTask_LastState());
			ps.setString(6, ptm.getPlanTask_LastTime());
			ps.setString(7, ptm.getPlanTask_NextTime());
			ps.setString(8, ptm.getPlanTask_01Time());
			ps.setString(9, ptm.getPlanTask_02Pinlv());
			ps.setInt(10, ptm.getPlanTask_02PinlvD());
			ps.setInt(11, ptm.getPlanTask_02PinlvMday());
			ps.setInt(12, ptm.getPlanTask_02PinlvMmonth());
			ps.setInt(13, ptm.getPlanTask_02PinlvStyle());
			ps.setString(14, ptm.getPlanTask_02PinlvTime());
			ps.setInt(15, ptm.getPlanTask_02Zhouqi());
			ps.setString(16, ptm.getPlanTask_02ZhouqiStyle());
			ps.setString(17, ptm.getPlanTask_SMSWord());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		PreparedStatement psmt = null;
//
//		psmt.setString(1, ptm.getPlanTask_Style());
//		psmt.setString(2, ptm.getPlanTask_Remark());
//		psmt.setString(3, ptm.getPlanTask_Exec());
//		psmt.setString(4, ptm.getPlanTask_TiaoJian());
//		psmt.setString(5, ptm.getPlanTask_LastState());
//		psmt.setString(6, ptm.getPlanTask_LastTime());
//		psmt.setString(7, ptm.getPlanTask_NextTime());
//		psmt.setString(8, ptm.getPlanTask_01Time());
//		psmt.setString(9, ptm.getPlanTask_02Pinlv());
//		psmt.setInt(10, ptm.getPlanTask_02PinlvD());
//		psmt.setInt(11, ptm.getPlanTask_02PinlvMday());
//		psmt.setInt(12, ptm.getPlanTask_02PinlvMmonth());
//		psmt.setInt(13, ptm.getPlanTask_02PinlvStyle());
//		psmt.setString(14, ptm.getPlanTask_02PinlvTime());
//		psmt.setInt(15, ptm.getPlanTask_02Zhouqi());
//		psmt.setString(16, ptm.getPlanTask_02ZhouqiStyle());
//		psmt.setString(17, ptm.getPlanTask_SMSWord());
//
//		boolean b = (psmt.executeUpdate() == 1);
//
//		if (psmt != null)
//			psmt.close();

		return b;
	}

	public String getTaskID()
	{
		return taskID;
	}

	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public String getOperateStyle()
	{
		return operateStyle;
	}

	public void setOperateStyle(String operateStyle)
	{
		this.operateStyle = operateStyle;
	}

	public String getTaskCondition()
	{
		return taskCondition;
	}

	public void setTaskCondition(String taskCondition)
	{
		this.taskCondition = taskCondition;
	}

	public String getOperateState()
	{
		return operateState;
	}

	public void setOperateState(String operateState)
	{
		this.operateState = operateState;
	}

	public String getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}

	public String getNextTime()
	{
		return nextTime;
	}

	public void setNextTime(String nextTime)
	{
		this.nextTime = nextTime;
	}

}
