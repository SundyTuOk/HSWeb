package com.sf.energy.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.sms.model.SMSReceiveModel;
import com.sf.energy.util.ConnDB;

public class SMSReceiveDao
{
	int total;
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
	/**
	 * 列出所有收到的短信
	 * @param pageIndex 
	 * @param pageCount 
	 * @return 已发送短信的集合
	 * @throws SQLException
	 */
	public ArrayList<SMSReceiveModel> listAllReceiveSMS(String sortName,String order,int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<SMSReceiveModel> list=new ArrayList<SMSReceiveModel>();
		SMSReceiveModel sms=null;
		String sql="Select Smsreceive_ID,sendPhone,to_date(sendTime,'yyyy-mm-dd hh24:mi:ss') sendTime,sendTime timeString,context,receiveMan,module_num from SMSReceive ";
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
				sms=new SMSReceiveModel();
				sms.setReceiveID(rs.getInt("SMSReceive_ID"));
				sms.setSendPhone(rs.getString("SENDPHONE"));
				sms.setContent(rs.getString("CONTEXT"));
				sms.setSendTime(rs.getString("timeString"));
				sms.setReceiveManName(rs.getString("RECEIVEMAN"));
				sms.setModuleNum(rs.getString("MODULE_NUM"));
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
			sms=new SMSReceiveModel();
			sms.setReceiveID(rs.getInt("SMSReceive_ID"));
			sms.setSendPhone(rs.getString("SENDPHONE"));
			sms.setContent(rs.getString("CONTEXT"));
			sms.setSendTime(rs.getString("timeString"));
			sms.setReceiveManName(rs.getString("RECEIVEMAN"));
			sms.setModuleNum(rs.getString("MODULE_NUM"));
			list.add(sms);
			count--;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		
		return list;
	}
	
	/**
	 * 删除接收的短信
	 * @param smsID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int smsID) throws SQLException
	{
//		boolean b=false;
		String sql="delete from SMSReceive where SMSReceive_ID="+smsID;
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
	 * 新增接收的短信
	 * @param sms
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(SMSReceiveModel sms) throws SQLException
	{
		String sql = "INSERT INTO SMSReceive(SENDPHONE,SENDTIME,CONTEXT,RECEIVEMAN,MODULE_NUM)VALUES(?,?,?,?,?)";
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ps.setString(1, sms.getSendPhone());
		ps.setString(2, sms.getSendTime());
		ps.setString(3, sms.getContent());
		ps.setString(4, sms.getReceiveManName());
		ps.setString(5, sms.getModuleNum());*/

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, sms.getSendPhone());
			ps.setString(2, sms.getSendTime());
			ps.setString(3, sms.getContent());
			ps.setString(4, sms.getReceiveManName());
			ps.setString(5, sms.getModuleNum());
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		/*
		boolean b=(ps.executeUpdate() == 1);
		
		if(ps!=null)
			ps.close();
		*/
		return b;
		
	}
	
	/**
	 * 是否已接受信息，若是下次就不插入表
	 * @param sms
	 * @return
	 * @throws SQLException 
	 */
	public boolean queryBYUnique(SMSReceiveModel sms) throws SQLException
	{
		boolean b=false;
		String sql="Select * from SMSReceive where SENDPHONE='"+sms.getSendPhone()+"' and SENDTIME='"+sms.getSendTime()+"' and CONTEXT='"+sms.getContent()+"'";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())//存在相同
			{
				b=true;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*
	//	PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next())//存在相同
		{
			b=true;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return b;
	}

	
	
	
}
