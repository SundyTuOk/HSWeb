package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.light.model.SlHistoryModel;
import com.sf.energy.util.ConnDB;

public class SlHistoryDao {

	SlHistoryModel slHistoryModel=null;
	//得到总记录
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
	 * 查询智能路灯控制子系统的历史记录的数据
	 * @return
	 * @throws SQLException 
	 */
	public List<SlHistoryModel> querySlHistory(String SlHistorysortName,String SlHistoryorder,String startTime,String endTime,String state,int areaId,int lineId,int pageCount,int pageIndex) throws SQLException
	{
		String strWhere="";	
		//起始时间
		if(!("-1".equals(startTime)))
		{			
			strWhere+=" and a.slHistory_Time>=to_date('" + startTime + "','yyyy-mm-dd')";
		}
		//结束时间
		if(!("-1".equals(endTime)))
		{
			endTime=endTime+" 23:59:59";			
			strWhere+=" and a.slHistory_Time<=to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss')";
		}
		//回路状态
		if(!("-1".equals(state)))
		{
			strWhere+=" and a.slHistory_state='" + state + "'";
		}
		//区域编号
		if(-1!=areaId)
		{
			strWhere+="and  a.SLLine_ID in (select SLLine_ID from SLLine where AREA_ID='" + areaId + "')";
		}
		//回路编号
		if(-1!=lineId)
		{
			strWhere+=" and a.SLLine_ID='" + lineId + "'";
		}
		
		List<SlHistoryModel> list=new ArrayList<SlHistoryModel>();
		
		String sql="select slHistory_id,b.SLLine_Name, to_char(slHistory_Time,'yyyy-mm-dd hh24:mi:ss') slHistory_Time,slHistory_state,nvl(users_name,' ') users_name,slHistory_style,slHistory_opr from SlHistory a,SLLine b where a.SLLine_ID=b.SLLine_ID"+strWhere+"order by "+SlHistorysortName+" "+SlHistoryorder;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 rs = ps.executeQuery();
			
			//得到总记录数
	        rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);
	        
	        while(rs.next() && (count > 0))		
			{
	        	slHistoryModel=new SlHistoryModel();
				slHistoryModel.setSlHistory_Id(rs.getInt("slHistory_id"));
				slHistoryModel.setSlLine_Name(rs.getString("SLLine_Name"));
				slHistoryModel.setSlHistory_Time(rs.getString("slHistory_Time"));
				slHistoryModel.setSlHistory_State(rs.getString("slHistory_state"));
				slHistoryModel.setUsers_Name(rs.getString("users_name"));
				slHistoryModel.setSlHistory_Style(rs.getString("slHistory_style"));
				slHistoryModel.setSlHistory_Opr(rs.getString("slHistory_opr"));
				list.add(slHistoryModel);
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
	
	public List<SlHistoryModel> querySlHistoryall() throws SQLException
	{
		List<SlHistoryModel> list=new ArrayList<SlHistoryModel>();
		
		String sql="select slHistory_id,(Select SLLine_Name from SLLine b where a.SLLine_ID=b.SLLine_ID) SLLine_Name, to_char(slHistory_Time,'yyyy-mm-dd hh24:mi:ss') slHistory_Time,slHistory_state,users_name,slHistory_style,slHistory_opr from SlHistory a";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())		
			{
			  slHistoryModel=new SlHistoryModel();
				slHistoryModel.setSlHistory_Id(rs.getInt("slHistory_id"));
				slHistoryModel.setSlLine_Name(rs.getString("SLLine_Name"));
				slHistoryModel.setSlHistory_Time(rs.getString("slHistory_Time"));
				slHistoryModel.setSlHistory_State(rs.getString("slHistory_state"));
				slHistoryModel.setUsers_Name(rs.getString("users_name"));
				slHistoryModel.setSlHistory_Style(rs.getString("slHistory_style"));
				slHistoryModel.setSlHistory_Opr(rs.getString("slHistory_opr"));
				list.add(slHistoryModel);		
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
			return list;
	}
	
	public boolean deleteSlHistoryall(int id) throws SQLException
	{
		String sql="delete from SlHistory where slHistory_id="+id;
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
