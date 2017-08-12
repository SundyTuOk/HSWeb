package com.sf.energy.classroomlight.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.classroomlight.model.LightRoomHistoryModel;
import com.sf.energy.util.ConnDB;

public class LightRoomHistoryDao
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
	 * 查询历史记录
	 * @param pageCount 分页大小
	 * @param pageIndex 当前页码
	 * @param selectInfo 选择区域建筑等信息
	 * @param queryMsg 查询条件，状态时间
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<LightRoomHistoryModel> queryInfo(int pageCount, int pageIndex, String selectInfo, String queryMsg, String order) throws SQLException
	{
		ArrayList<LightRoomHistoryModel> list = new ArrayList<LightRoomHistoryModel>();
		LightRoomHistoryModel lrhm = null;
		
		String sql = "";
		String where = "";
		if(queryMsg != null && !"".equals(queryMsg))
		{
			String[] msg = queryMsg.split("\\|");
			where = " where LightRoomHistory_Time>=" + "to_date('" + msg[1] + "','yyyy-mm-dd')" + " and LightRoomHistory_Time<=" + "(to_date('" + msg[2] + "','yyyy-mm-dd')+1) ";
			 if (!"-1".equals(msg[0])) 
			 {
				 where += " and LightRoomHistory_State='" + msg[0]+"'";
			 } 
		}
		
		if(selectInfo != null && !"".equals(selectInfo))
		{
			String[] info = selectInfo.split("\\|");
			if("Area".equals(info[0]))
			{
				where += " and LightRoomNo in(select LightRoomNo from LightRoom where Area_ID=" + info[1] + ")";  
			}
			if("Arc".equals(info[0]))
			{
				where += " and LightRoomNo in(select LightRoomNo from LightRoom where Architecture_ID=" + info[1] + ")";
			}
			if("Floor".equals(info[0]))
			{
				where += " and LightRoomNo in(select LightRoomNo from LightRoom where Architecture_ID=" + info[1] + " and Floor=" + info[2] + ")";
			}
			if("Room".equals(info[0]))
			{
				where += " and LightRoomNo =" + info[1];
			}
		}
		
		sql = "select "+
				"LightRoomHistory_ID,"+
				"(select LightRoomName from (LightRoom)b where a.LightRoomNo=b.LightRoomNo)LightRoomName,"+
				"to_char(LightRoomHistory_Time,'yyyy-mm-dd hh24:mi:ss')LightRoomHistory_Time,"+
				"LightRoomHistory_State,"+
				"nvl((select Users_Name from (Users)b where a.Users_ID=b.Users_ID),'系统')Users_Name,"+
				"LightRoomHistory_Style,LightRoomHistory_Opr "+
			"from "+
				"(LightRoomHistory)a  " + where + order;
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while(rs.next()&&(count > 0))
			{
				lrhm = new LightRoomHistoryModel();
				lrhm.setLightRoomHistory_ID(rs.getInt("LightRoomHistory_ID"));
				lrhm.setLightRoomName(rs.getString("LightRoomName"));
				lrhm.setLightRoomHistory_Time(rs.getString("LightRoomHistory_Time"));
				lrhm.setLightRoomHistory_State(rs.getString("LightRoomHistory_State"));
				lrhm.setUsers_Name(rs.getString("Users_Name"));
				lrhm.setLightRoomHistory_Style(rs.getString("LightRoomHistory_Style"));	
				lrhm.setLightRoomHistory_Opr(rs.getString("LightRoomHistory_Opr"));
				
				list.add(lrhm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		rs = ps.executeQuery();
//
//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (pageCount * pageIndex))
//			return list;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//		
//		while(rs.next()&&(count > 0))
//		{
//			lrhm = new LightRoomHistoryModel();
//			lrhm.setLightRoomHistory_ID(rs.getInt("LightRoomHistory_ID"));
//			lrhm.setLightRoomName(rs.getString("LightRoomName"));
//			lrhm.setLightRoomHistory_Time(rs.getString("LightRoomHistory_Time"));
//			lrhm.setLightRoomHistory_State(rs.getString("LightRoomHistory_State"));
//			lrhm.setUsers_Name(rs.getString("Users_Name"));
//			lrhm.setLightRoomHistory_Style(rs.getString("LightRoomHistory_Style"));	
//			lrhm.setLightRoomHistory_Opr(rs.getString("LightRoomHistory_Opr"));
//			
//			list.add(lrhm);
//			count--;
//		}

		
		return list;	
	}
	
	/**
	 * 删
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int ID) throws SQLException
	{
		String sql = "delete FROM LightRoomHistory where LightRoomHistory_ID =  " + ID;
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
