package com.sf.energy.project.run.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.run.model.LogInfoModel;
import com.sf.energy.util.ConnDB;

public class LogInfoDao
{
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}
	
	/**
	 * 列出所有日志信息
	 * @return LogInfoModel 日志信息实体类
	 * @throws SQLException
	 */
	public ArrayList<LogInfoModel> listLogInfo(int pageCount,int pageIndex,String sortName,String order) throws SQLException
	{
		ArrayList<LogInfoModel> list=new ArrayList<LogInfoModel>();
		LogInfoModel logModel=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="Select SystemLog_ID,Users_Name,Module_Num,Systemlog_Action,Systemlog_Result,to_char(to_date(Systemlog_Time,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')systemlog_time,SystemLog_message From SystemLog ";
			
			if(order.equals("asc"))//升序
			{
				sql+=" order by "+sortName+ " asc";
			}
			else if(order.equals("desc"))
			{
				sql+=" order by "+sortName+ " desc";
			}
			else {
				sql+=" order by "+sortName;
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
			{
				ConnDB.release(conn, ps, rs);
				return list;
			}

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.beforeFirst();
			} else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next() && (count > 0))
			{
				logModel = new LogInfoModel();
				logModel.setSystemLogID(rs.getInt("systemLog_ID"));
				logModel.setModuleName(rs.getString("module_Num"));
				logModel.setOperationKeyWord(rs.getString("systemLog_action"));
				
				if(rs.getInt("systemLog_result")==1)
					logModel.setOperationResultString("成功");
				else {
					logModel.setOperationResultString("失败");
				}
				
				if(rs.getString("systemLog_Message")==null || "".equals(rs.getString("systemLog_Message")))
					logModel.setLogMessage("无");
				else {
					logModel.setLogMessage(rs.getString("systemLog_Message"));
				}
				
				logModel.setOperationTime(rs.getString("systemLog_Time"));
				logModel.setUserName(rs.getString("Users_Name"));
				list.add(logModel);
				
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	
	/**
	 * 通过日志ID删除系统日志
	 * @param logID
	 * @return 是否删除成功
	 * @throws SQLException
	 */
	public boolean deleteSystemLog(int logID) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			String sql="delete from SystemLog where SystemLog_ID="+logID;
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
	 * 通过条件查询日志信息		
	 * @param searchKeyWords 查询条件
	 * @return LogInfoModel 日志信息实体类
	 * @throws SQLException
	 */
	public ArrayList<LogInfoModel> searchLogInfo(String searchKeyWords,int pageCount,int pageIndex) throws SQLException
	{
		ArrayList<LogInfoModel> list=new ArrayList<LogInfoModel>();
		LogInfoModel logModel=null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql="Select * From SystemLog Order by SystemLog_ID DESC";
			
			if(searchKeyWords!=null && !"".equals(searchKeyWords))
			{
				if("成功".equals(searchKeyWords))
				{
					 sql="select * from SystemLog where SystemLog_Result=1 Order by SystemLog_ID DESC" ;
				}
				else if("失败".equals(searchKeyWords))
				{
					 sql="select * from SystemLog where SystemLog_Result=0 Order by SystemLog_ID DESC" ;
				}
				else
				{
				  sql="select * from SystemLog where SystemLog_action like '"+searchKeyWords+"' or Module_Num like '"+searchKeyWords+"' or Users_Name like '"+searchKeyWords+"'";
				}
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			////System.out.println("查询日志的sql："+sql);
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
			{
				ConnDB.release(conn, ps, rs);
				return list;
			}

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.beforeFirst();
			} else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next() && (count > 0))
			{
				logModel = new LogInfoModel();
				logModel.setSystemLogID(rs.getInt("systemLog_ID"));
				logModel.setModuleName(rs.getString("module_Num"));
				logModel.setOperationKeyWord(rs.getString("systemLog_action"));
				
				if(rs.getInt("systemLog_result")==1)
					logModel.setOperationResultString("成功");
				else {
					logModel.setOperationResultString("失败");
				}
				
				if(rs.getString("systemLog_Message")==null || "".equals(rs.getString("systemLog_Message")))
					logModel.setLogMessage("无");
				else {
					logModel.setLogMessage(rs.getString("systemLog_Message"));
				}
				
				logModel.setOperationTime(rs.getString("systemLog_Time"));
				logModel.setUserName(rs.getString("Users_Name"));
				list.add(logModel);
				
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
}
