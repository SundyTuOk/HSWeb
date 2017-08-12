package com.sf.energy.mail.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class MailDao
{
	
	/**
	 * 查询历史发件箱
	 * @param sortName
	 * @param order
	 * @param thePageCount
	 * @param thePageIndex
	 * @return
	 * @throws SQLException 
	 */
	public static JSONArray listAllSendMail(String sortName, String order, Integer pageCount, Integer pageIndex) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		String sql="Select a.sendId,b.users_name mail_serder,b.Users_ID,to_char(a.sendTime,'yyyy-mm-dd hh24:mi:ss')sendTime ,a.mail_receiver,a.attachFileName,a.mail_subject,a.mail_content,a.state	 from EmailSendBox a inner join users b on a.Users_id=b.Users_id ";	
		if (order.equals("desc"))
		{
			sql += " order by " + sortName + " desc";
		}        
	    else
	    {
	    	sql += " order by " + sortName + " asc";
	    }
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			rs.last();
			int count = rs.getRow();
			
			
			
			jo.put("totalCount", count);
			json.add(jo);
			
			if (count <= (pageCount * pageIndex))
				return json;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next()&&(count > 0))
			{
				jo = new JSONObject(); 
				jo.put("sendId", rs.getString("sendId"));
				jo.put("mail_serder", rs.getString("mail_serder"));
				jo.put("mail_receiver", rs.getString("mail_receiver"));
				jo.put("mail_subject", rs.getString("mail_subject"));
				jo.put("sendTime", rs.getString("sendTime"));
				jo.put("attachFileName", rs.getString("attachFileName"));
				jo.put("mail_content", rs.getString("mail_content"));
				jo.put("state", rs.getString("state"));
				
				json.add(jo);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return json;
	}

	/**
	 * 保存发送的邮件
	 * @param userID
	 * @param receiver
	 * @param date
	 * @param fileName
	 * @param mailSubject
	 * @param content
	 * @param state
	 * @throws SQLException
	 */
	public void saveSendMail(String userID, String receiver, Date date, String fileName, String mailSubject, String content, String state) throws SQLException
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO EmailSendBox(Users_id,mail_receiver,mail_subject,sendTime,attachFileName,mail_content,state)"
					+ "VALUES(?,?,?,to_date('"+sf.format(date)+"','yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, userID);
			ps.setString(2, receiver);
			ps.setString(3, mailSubject);
			if(fileName!=null){
				ps.setString(4, fileName);
			}else {
				ps.setString(4, "");
			}
			ps.setString(5, content);
			ps.setString(6, state);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}				
	}

	/**
	 * 更新发送邮件的状态
	 * @param userID
	 * @param state
	 * @throws SQLException
	 */
	public void updateState(String userID, String state) throws SQLException
	{
		String sql = "update  EmailSendBox set state='"+state+"' where Users_id="+userID+"  and  state='未发送'";
	//	boolean b =false;	
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
		
	}

	/**
	 * 删除已发邮件
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteSomeHistorySend(String ids) throws SQLException
	{
		String sql = "delete from EmailSendBox where sendId in("+ids+")";		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return b;
	}
	
	/**
	 * 保存草稿
	 * @param userID
	 * @param receiver
	 * @param fileName
	 * @param mailSubject
	 * @param content
	 * @throws SQLException 
	 */
	public void saveAssSketch(String userID, String receiver, String fileName, String mailSubject, String content) throws SQLException
	{
	//	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO EmaiAssSketchBox(Users_id,mail_receiver,mail_subject,attachFileName,mail_content,saveTime)"
					+ "VALUES(?,?,?,?,?,to_date(to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'))";
		Connection conn=null;
		PreparedStatement ps =null;
	//	boolean b =false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			ps.setString(1, userID);
			ps.setString(2, receiver);
			ps.setString(3, mailSubject);
			ps.setString(4, fileName);
			ps.setString(5, content);				
		//	b=(ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
	}

	public static JSONArray listAllAssSketchMail(String userID, String sortName, String order, Integer pageCount, Integer pageIndex) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		String sql="Select id,Users_id,mail_receiver,mail_subject,attachFileName,mail_content,to_char(saveTime,'yyyy-mm-dd hh24:mi:ss')saveTime from EmaiAssSketchBox where users_id="+userID;	
		if (order.equals("desc"))
		{
			sql += " order by " + sortName + " desc";
		}        
	    else
	    {
	    	sql += " order by " + sortName + " asc";
	    }
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			rs.last();
			int count = rs.getRow();	
			jo.put("totalCount", count);
			json.add(jo);
			
			if (count <= (pageCount * pageIndex))
				return json;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next()&&(count > 0))
			{
				jo = new JSONObject(); 
				jo.put("id", rs.getString("id"));
				jo.put("mail_receiver", rs.getString("mail_receiver"));
				jo.put("mail_subject", rs.getString("mail_subject"));
				jo.put("attachFileName", rs.getString("attachFileName"));
				jo.put("mail_content", rs.getString("mail_content"));
				jo.put("saveTime", rs.getString("saveTime"));
				
				json.add(jo);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return json;
	}

	public boolean deleteAssSketch(String ids) throws SQLException
	{
		String sql = "delete from EmaiAssSketchBox where id in("+ids+")";
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);			
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

}
