package com.sf.energy.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import java.util.List;

import com.sf.energy.sms.model.SMSHistoryModel;
import com.sf.energy.sms.model.SMSWillModel;
import com.sf.energy.util.ConnDB;

public class SMSWillDao
{
	int total = 0;
	
	
	public ArrayList<SMSWillModel> listAllWaitSendSMS(String sortName,String order,int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<SMSWillModel> list=new ArrayList<SMSWillModel>();
		SMSWillModel sms=null;
		String sql="Select a.SMSWILL_ID, a.SMSWILL_phoneList,a.SMSWILL_content,b.users_name,b.Users_ID,a.SMSWILL_state,a.SMSWILL_repeattime,a.MODULE_num,a.inserttime from SMSWILL a inner join users b on a.Users_id=b.Users_id  ";
		
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
				sms=new SMSWillModel();
				sms.setWillID(rs.getInt("SMSWILL_ID"));
				sms.setPhoneList(rs.getString("SMSWILL_phoneList"));			
				sms.setContent(rs.getString("SMSWILL_content")); 
				sms.setUserID(rs.getInt("USERS_ID"));
				sms.setUserName(rs.getString("Users_Name"));
				sms.setRepeatTime(rs.getInt("SMSWILL_repeattime"));
				sms.setState(rs.getInt("SMSWILL_state"));
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

		/*
	//	PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
			sms=new SMSWillModel();
			sms.setWillID(rs.getInt("SMSWILL_ID"));
			sms.setPhoneList(rs.getString("SMSWILL_phoneList"));			
			sms.setContent(rs.getString("SMSWILL_content")); 
			sms.setUserID(rs.getInt("USERS_ID"));
			sms.setUserName(rs.getString("Users_Name"));
			sms.setRepeatTime(rs.getInt("SMSWILL_repeattime"));
			sms.setState(rs.getInt("SMSWILL_state"));
			sms.setInsertTime(rs.getString("INSERTTIME"));
			list.add(sms);
			count--;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		
		return list;
	}

	//查询所有已废弃信息记录
	public ArrayList<SMSWillModel> listAllAbandonedSMS(String sortName,String order,int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<SMSWillModel> list=new ArrayList<SMSWillModel>();
		SMSWillModel sms=null;
		String sql="Select a.SMSWILL_ID, a.SMSWILL_phoneList,a.SMSWILL_content,b.users_name,b.Users_ID,a.SMSWILL_state,a.SMSWILL_repeattime,a.MODULE_num,a.inserttime from SMSWILL a inner join users b on a.Users_id=b.Users_id WHERE a.SMSWILL_STATE = 0 ";
		
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
				sms=new SMSWillModel();
				sms.setWillID(rs.getInt("SMSWILL_ID"));
				sms.setPhoneList(rs.getString("SMSWILL_phoneList"));			
				sms.setContent(rs.getString("SMSWILL_content")); 
				sms.setUserID(rs.getInt("USERS_ID"));
				sms.setUserName(rs.getString("Users_Name"));
				sms.setRepeatTime(rs.getInt("SMSWILL_repeattime"));
				sms.setState(rs.getInt("SMSWILL_state"));
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

		/*
	//	PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
			sms=new SMSWillModel();
			sms.setWillID(rs.getInt("SMSWILL_ID"));
			sms.setPhoneList(rs.getString("SMSWILL_phoneList"));			
			sms.setContent(rs.getString("SMSWILL_content")); 
			sms.setUserID(rs.getInt("USERS_ID"));
			sms.setUserName(rs.getString("Users_Name"));
			sms.setRepeatTime(rs.getInt("SMSWILL_repeattime"));
			sms.setState(rs.getInt("SMSWILL_state"));
			sms.setInsertTime(rs.getString("INSERTTIME"));
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
	 *   删除重发短信的记录
	 * @param id	记录ID
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteSendedMessage(int id) throws SQLException
	{
//		boolean  b = false;
		
		String sql="DELETE FROM SMSWILL WHERE SMSWILL_ID = "+id ;


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
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		
		b = (ps.executeUpdate() == 1);
		*/
		return b;
	}
	
	
	/**
	 * 	 删除已废弃的信息
	 * @param id	
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteAbandonedMessage(int id) throws SQLException
	{
//		boolean  b = false;
		
		String sql="DELETE FROM SMSWILL WHERE SMSWILL_ID = "+id ;
		
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		
		b = (ps.executeUpdate() == 1);*/


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
	 *   通过SMSWILLID获取记录的内容 
	 * @param smsWillID
	 * @return
	 * @throws SQLException
	 */
	public SMSWillModel getContentByID(int smsWillID) throws SQLException
	{
		//List<SMSWillModel>  arrList = new ArrayList<SMSWillModel>();
		SMSWillModel smsModel = null;
	/*	PreparedStatement ps = null;
		ResultSet  rs = null;*/
		
		String sql = "SELECT SMSWILL_content,SMSWILL_phonelist FROM SMSWILL WHERE SMSWILL_ID = ?";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				smsModel = new SMSWillModel();
				smsModel.setWillID(smsWillID);
				smsModel.setContent(rs.getString("SMSWILL_content"));
				smsModel.setPhoneList(rs.getString("SMSWILL_phonelist"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*ps = ConnDB.getConnection().prepareStatement(sql);
		ps.setInt(1, smsWillID);
		
		rs = ps.executeQuery();
		
		if(rs.next())
		{
			smsModel = new SMSWillModel();
			smsModel.setWillID(smsWillID);
			smsModel.setContent(rs.getString("SMSWILL_content"));
			smsModel.setPhoneList(rs.getString("SMSWILL_phonelist"));
		}
		*/
		return smsModel;
	}
	
	
	/**
	 *   通过历史信息ID获取历史信息表内容
	 * @param smsHistoryID
	 * @return
	 * @throws SQLException
	 */
	public SMSHistoryModel getHistoryContentByID(int smsHistoryID) throws SQLException
	{
		SMSHistoryModel  sshModel = null;
		/*PreparedStatement ps = null;
		ResultSet  rs = null;*/
		String sql = "SELECT SMSHISTORY_content,SMSHISTORY_phonelist FROM SMSHISTORY WHERE SMSHISTORY_ID = ?";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if(rs.next())
			{
				sshModel = new SMSHistoryModel();
				sshModel.setSmsHistoryID(smsHistoryID);
				sshModel.setContent(rs.getString("SMSHISTORY_content"));
				sshModel.setPhoneList(rs.getString("SMSHISTORY_phonelist"));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*ps = ConnDB.getConnection().prepareStatement(sql);
		ps.setInt(1, smsHistoryID);
		
		rs=ps.executeQuery();
		
		if(rs.next())
		{
			sshModel = new SMSHistoryModel();
			sshModel.setSmsHistoryID(smsHistoryID);
			sshModel.setContent(rs.getString("SMSHISTORY_content"));
			sshModel.setPhoneList(rs.getString("SMSHISTORY_phonelist"));
		}
		*/
		return sshModel;
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
