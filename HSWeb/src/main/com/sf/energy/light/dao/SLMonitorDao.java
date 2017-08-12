package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.light.model.SLMonitorModel;
import com.sf.energy.util.ConnDB;

public class SLMonitorDao {
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
	 * 查询路灯监测
	 * @param state
	 * @param areaId
	 * @param lineId
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public List<SLMonitorModel> querySlMonitor(String SLMonitorsortName,String SLMonitororder,String state,int areaId,int lineId,int pageCount,int pageIndex) throws SQLException
	{
		String strWhere="";	
		if(-1!=areaId)
		{
			strWhere+="and  a.SLLine_ID in (select SLLine_ID from SLLine where AREA_ID='" + areaId + "')";
		}
		//回路编号
		if(-1!=lineId)
		{
			strWhere+=" and SLLine_ID='" + lineId + "'";
		}
		if(!("-1".equals(state)))
		{
			strWhere+=" and (case a.SLKongzhi_Index when 1 then b.Lamp_State1 when 2 then b.Lamp_State2 end )='" + state + "'";
		}
		List<SLMonitorModel> list=new ArrayList<SLMonitorModel>();
		SLMonitorModel slMonitorModel=null;
		String sql="select a.SLLine_ID,a.Area_ID,SLKongzhi_Index,(select Area_Name from Area  c where a.Area_ID=c.Area_ID) Area_Name,a.SLKongZhi_ID,a.SLLine_Num,a.SLLine_Name,a.SLLine_Star,a.SLLine_End,b.SLKongzhi_Name,b.SLKongzhi_State,(case a.SLKongzhi_Index when 1 then b.Lamp_State1 when 2 then b.Lamp_State2 end ) Lamp_State,to_char(b.LastTime,'yyyy-mm-dd hh24:mi:ss') lasttime,(case a.SLKongzhi_Index when 1 then b.Lamp_State11 when 2 then b.Lamp_State21 end ) LampKZ_State from SLLine a left join SLKongzhi  b on a.SLKongZhi_ID=b.SLKongZhi_ID where 1=1"+strWhere+"order by "+SLMonitorsortName+" "+SLMonitororder;
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
	        	slMonitorModel=new SLMonitorModel();
	        	slMonitorModel.setSLLINE_ID(rs.getInt("SLLine_ID"));
	        	slMonitorModel.setSLLine_Num(rs.getString("SLLine_Num"));
	        	slMonitorModel.setSLLine_Name(rs.getString("SLLine_Name"));
	        	slMonitorModel.setArea_Name(rs.getString("Area_Name"));
	        	slMonitorModel.setSLLine_Star(rs.getString("SLLine_Star"));
	        	slMonitorModel.setSLLine_End(rs.getString("SLLine_End"));
	        	slMonitorModel.setSLKongzhi_Name(rs.getString("SLKongzhi_Name"));
	        	slMonitorModel.setSLKongzhi_State(rs.getInt("SLKongzhi_State"));
	        	slMonitorModel.setLamp_State(rs.getString("Lamp_State"));
	        	slMonitorModel.setLamp_moshi(rs.getString("LampKZ_State"));
	        	slMonitorModel.setLastTime(rs.getString("lasttime"));
	        	list.add(slMonitorModel);
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
	 * 获取回路信息
	 * @param id 回路id
	 * @return
	 * @throws SQLException
	 */
	public SLMonitorModel queryLine(String id) throws SQLException
	{
		SLMonitorModel slMonitorModel=new SLMonitorModel();
		String sql="select a.SLLine_ID,a.Area_ID,SLKongzhi_Index,(select Area_Name from Area  c where a.Area_ID=c.Area_ID) Area_Name,a.SLKongZhi_ID,a.SLLine_Num,a.SLLine_Name,a.SLLine_Star,a.SLLine_End,b.SLKongzhi_Name,b.SLKongzhi_State,(case a.SLKongzhi_Index when 1 then b.Lamp_State1 when 2 then b.Lamp_State2 end ) Lamp_State,to_char(b.LastTime,'yyyy-mm-dd hh24:mi:ss') lasttime,(case a.SLKongzhi_Index when 1 then b.Lamp_State11 when 2 then b.Lamp_State21 end ) LampKZ_State from SLLine a left join SLKongzhi  b on a.SLKongZhi_ID=b.SLKongZhi_ID where SLLine_ID="+id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				slMonitorModel.setSLLINE_ID(rs.getInt("SLLine_ID"));
	        	slMonitorModel.setSLLine_Num(rs.getString("SLLine_Num"));
	        	slMonitorModel.setSLLine_Name(rs.getString("SLLine_Name"));
	        	slMonitorModel.setArea_Name(rs.getString("Area_Name"));
	        	slMonitorModel.setSLLine_Star(rs.getString("SLLine_Star"));
	        	slMonitorModel.setSLLine_End(rs.getString("SLLine_End"));
	        	slMonitorModel.setSLKongzhi_Name(rs.getString("SLKongzhi_Name"));
	        	slMonitorModel.setSLKongzhi_State(rs.getInt("SLKongzhi_State"));
	        	slMonitorModel.setLamp_State(rs.getString("Lamp_State"));
	        	slMonitorModel.setLamp_moshi(rs.getString("LampKZ_State"));
	        	slMonitorModel.setLastTime(rs.getString("lasttime"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
		return slMonitorModel;
	}
	
}
