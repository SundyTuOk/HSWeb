package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.light.model.SLAlarmModel;
import com.sf.energy.util.ConnDB;

public class SLAlarmDao {
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
	 * 查询		
	 * @param startTime
	 * @param endTime
	 * @param areaId
	 * @param lineId
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public List<SLAlarmModel>  querySlAlarm(String SLAlarmsortName,String SLAlarmorder,String startTime,String endTime,int areaId,int lineId,int pageCount,int pageIndex) throws SQLException
	{
		List<SLAlarmModel> list=new ArrayList<SLAlarmModel>();
		SLAlarmModel slAlarmModel=null;
		String strWhere="";	

		if(!("-1".equals(startTime)))
		{
			strWhere+=" and BaojingInfo_Time>to_date('" + startTime + "','yyyy-mm-dd')";
		}
		//结束时间
		if(!("-1".equals(endTime)))
		{
			endTime=endTime+" 23:59:59";
			strWhere+=" and BaojingInfo_Time<to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss')";
		}
		//区域编号
		if(-1!=areaId)
		{
			strWhere+="and  a.BAOJINGINFO_INDEX in (select SLLine_ID from SLLine where AREA_ID='" + areaId + "')";
		}
		//回路编号
		if(-1!=lineId)
		{
			strWhere+=" and a.BAOJINGINFO_INDEX='" + lineId + "'";
		}
		String sql="select BaojingInfo_id,(select SLLine_Name from SLLine  b where a.BaojingInfo_Index=b.SLLine_ID) SLLine_Name,to_char(BaojingInfo_Time,'yyyy-mm-dd hh24:mi:ss') BaojingInfo_Time,BaojingInfo_Style,BaojingInfo_FL,BaojingInfo_Index,BaojingInfo_Title from BaojingInfo  a where Module_Num='5000' "+strWhere+" order by "+SLAlarmsortName+" "+SLAlarmorder;
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
				slAlarmModel=new SLAlarmModel();
				slAlarmModel.setBAOJINGINFO_ID(rs.getInt("BaojingInfo_id"));
				slAlarmModel.setSLLINE_ID(rs.getInt("BaojingInfo_Index"));
				slAlarmModel.setSLLINE_NAME(rs.getString("SLLine_Name"));
				slAlarmModel.setBAOJINGINFO_TIME(rs.getString("BaojingInfo_Time"));
				slAlarmModel.setBaojingInfo_Style(rs.getString("BaojingInfo_Style"));
				slAlarmModel.setBAOJINGINFO_TITLE(rs.getString("BaojingInfo_Title"));
				list.add(slAlarmModel);
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
	 * 删除
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteSlAlarmall(int id) throws SQLException
	{
		String sql="delete from BaojingInfo where BaojingInfo_id="+id;
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
