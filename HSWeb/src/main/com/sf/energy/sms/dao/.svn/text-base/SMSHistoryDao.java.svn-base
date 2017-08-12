package com.sf.energy.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.expert.model.TargetWaterAuditModel;
import com.sf.energy.sms.model.SMSHistoryModel;
import com.sf.energy.util.ConnDB;

public class SMSHistoryDao
{
	int total;
	/**
	 * 列出所有已发送短信
	 * @return 已发送短信的集合
	 * @throws SQLException
	 */
	public ArrayList<SMSHistoryModel> listAllSendSMS(String sortName,String order,int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<SMSHistoryModel> list=new ArrayList<SMSHistoryModel>();
		SMSHistoryModel sms=null;
		String sql="Select a.SmsHistory_ID, to_date(a.SmsHistory_time,'yyyy-mm-dd hh24:mi:ss')SmsHistory_time ,a.SmsHistory_phoneList,a.SmsHistory_count,a.smsHistory_content,b.users_name,b.Users_ID,a.smshistory_style,a.smsManage_id,a.SmsHistory_timing,a.inserttime from SMSHistory a inner join users b on a.Users_id=b.Users_id ";
		
		if (order.equals("desc"))
		{
			sql += " order by " + sortName + " desc";
		}        
	    else
	    {
	    	sql += " order by " + sortName + " asc";
	    }
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next()&&(count > 0))
			{
				sms=new SMSHistoryModel();
				sms.setSmsHistoryID(rs.getInt("SMSHISTORY_ID"));
				sms.setSmsHistoryTime(rs.getString("INSERTTIME"));
				sms.setPhoneList(rs.getString("SMSHISTORY_PHONELIST"));
				sms.setCount(rs.getInt("SMSHISTORY_COUNT"));
				sms.setContent(rs.getString("SMSHISTORY_CONTENT")); 
				sms.setUserID(rs.getInt("USERS_ID"));
				sms.setUserName(rs.getString("Users_Name"));
				sms.setStyleID(rs.getInt("SMSHISTORY_STYLE"));
				sms.setSmsModemID(rs.getInt("SMSMANAGE_ID"));
				sms.setTiming(rs.getInt("SMSHISTORY_TIMING"));
				sms.setInsertTime(rs.getString("INSERTTIME"));
				list.add(sms);
				count--;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();
		
		rs.last();
		int count = rs.getRow();
		setTotal(count);
		if (count <= (pageCount * pageIndex))
			return list;

		count = count - pageCount * pageIndex;

		if (count >= pageCount)
			count = pageCount;

		if ((pageCount * pageIndex) == 0)
			rs.beforeFirst();
		else
			rs.absolute(pageCount * pageIndex);
		
		while (rs.next()&&(count > 0))
		{
			sms=new SMSHistoryModel();
			sms.setSmsHistoryID(rs.getInt("SMSHISTORY_ID"));
			sms.setSmsHistoryTime(rs.getString("INSERTTIME"));
			sms.setPhoneList(rs.getString("SMSHISTORY_PHONELIST"));
			sms.setCount(rs.getInt("SMSHISTORY_COUNT"));
			sms.setContent(rs.getString("SMSHISTORY_CONTENT")); 
			sms.setUserID(rs.getInt("USERS_ID"));
			sms.setUserName(rs.getString("Users_Name"));
			sms.setStyleID(rs.getInt("SMSHISTORY_STYLE"));
			sms.setSmsModemID(rs.getInt("SMSMANAGE_ID"));
			sms.setTiming(rs.getInt("SMSHISTORY_TIMING"));
			sms.setInsertTime(rs.getString("INSERTTIME"));
			list.add(sms);
			count--;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();
		*/
		return list;
	}
	
	/**
	 * 删除已发送短信
	 * @param smsID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int smsID) throws SQLException
	{
//		boolean b=false;
		String sql="delete from SMSHistory where SMSHistory_ID="+smsID;
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		b = (ps.executeUpdate() == 1);
		 
		if(ps!=null)
			ps.close();*/
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	
	
	/**
	 * 新增已发送短信
	 * @param sms
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(SMSHistoryModel sms) throws SQLException
	{
		String sql = "INSERT INTO SMSHistory(SMSHISTORY_TIME,SMSHISTORY_PHONELIST,SMSHISTORY_COUNT,SMSHISTORY_CONTENT,USERS_ID,SMSHISTORY_STYLE,SMSMANAGE_ID,SMSHISTORY_TIMING,INSERTTIME)VALUES(?,?,?,?,?,?,?,?,?)";
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		
		
	/*	
		boolean b=(ps.executeUpdate() == 1);
		
		if(ps!=null)
			ps.close();*/
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, sms.getSmsHistoryTime());
			ps.setString(2, sms.getPhoneList());
			ps.setInt(3, sms.getCount());
			ps.setString(4, sms.getContent());
			ps.setInt(5, sms.getUserID());
			ps.setInt(6, sms.getStyleID());
			ps.setInt(7, sms.getSmsModemID());
			ps.setInt(8, sms.getTiming());
			ps.setString(9, sms.getInsertTime());
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
		
	}
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
	
	
}
