package com.sf.energy.classroomlight.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.classroomlight.model.LightRoomAlarmModel;
import com.sf.energy.util.ConnDB;

public class LightRoomAlarmDao
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
	public ArrayList<LightRoomAlarmModel> queryInfo(int pageCount, int pageIndex, String selectInfo, String queryMsg,String order) throws SQLException
	{
		ArrayList<LightRoomAlarmModel> list = new ArrayList<LightRoomAlarmModel>();
		LightRoomAlarmModel lram = null;
		
		String sql = "";
		String where = "";
		if(queryMsg != null && !"".equals(queryMsg))
		{
			String[] msg = queryMsg.split("\\|");
			where = " and BaojingInfo_Time>=" + "to_date('" + msg[0] + "','yyyy-mm-dd')" + " and BaojingInfo_Time<=" + "(to_date('" + msg[1] + "','yyyy-mm-dd')+1) ";
		}
		
//		if(selectInfo != null && !"".equals(selectInfo))
//		{
//			String[] info = selectInfo.split("\\|");
//			if("Area".equals(info[0]))
//			{
//				where += " and BaojingInfo_Index in(select LightRoomNo from LightRoom where Area_ID=" + info[1] + ")";  
//			}
//			if("Arc".equals(info[0]))
//			{
//				where += " and BaojingInfo_Index in(select LightRoomNo from LightRoom where Architecture_ID=" + info[1] + ")";
//			}
//			if("Floor".equals(info[0]))
//			{
//				where += " and BaojingInfo_Index in(select LightRoomNo from LightRoom where Architecture_ID=" + info[1] + " and Floor=" + info[2] + ")";
//			}
//			if("Room".equals(info[0]))
//			{
//				where += " and BaojingInfo_Index =" + info[1];
//			}
//		}
		
		sql = "select "+
				"BaojingInfo_id,"+
				"(select LightRoomName from (LightRoom)b where a.BaojingInfo_Index=b.LightRoomNo)LightRoomName,"+
				"to_char(BaojingInfo_Time,'yyyy-mm-dd hh24:mi:ss')BaojingInfo_Time,"+
				"BaojingInfo_Style,BaojingInfo_FL,BaojingInfo_Index,BaojingInfo_Title "+
			"from "+
				"(BaojingInfo)a "+
			"where "+
				"Module_Num='C000'" + where + order;
		
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
				lram = new LightRoomAlarmModel();
				lram.setBaojingInfo_id(rs.getInt("BaojingInfo_id"));
				lram.setLightRoomName(rs.getString("LightRoomName"));
				lram.setBaojingInfo_Time(rs.getString("BaojingInfo_Time"));
				lram.setBaojingInfo_Style(rs.getString("BaojingInfo_Style"));
				lram.setBaojingInfo_Title(rs.getString("BaojingInfo_Title"));
				
				list.add(lram);
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
//			lram = new LightRoomAlarmModel();
//			lram.setBaojingInfo_id(rs.getInt("BaojingInfo_id"));
//			lram.setLightRoomName(rs.getString("LightRoomName"));
//			lram.setBaojingInfo_Time(rs.getString("BaojingInfo_Time"));
//			lram.setBaojingInfo_Style(rs.getString("BaojingInfo_Style"));
//			lram.setBaojingInfo_Title(rs.getString("BaojingInfo_Title"));
//			
//			list.add(lram);
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
		String sql = "delete FROM BaojingInfo where BaojingInfo_id =  " + ID;
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
