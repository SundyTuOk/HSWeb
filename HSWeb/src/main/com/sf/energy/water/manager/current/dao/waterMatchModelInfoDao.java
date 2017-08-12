package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.manager.monitor.dao.MeterInfoDao;
import com.sf.energy.util.ConnDB;

public class waterMatchModelInfoDao
{
	String waterMeterID="";
	String waterMeterName="";
	String waterMeterPartment="";
	String isCheck;
	int total;
	
	
	


	public ArrayList<waterMatchModelInfoDao> getMeterInfo(String areaID,
			String groupID, String archID, int standByModelPageCount,
			int standByModelPageIndex, String tableName, String order) throws SQLException
	{
		ArrayList<waterMatchModelInfoDao> list=new ArrayList<waterMatchModelInfoDao>();
		String strSql="select a.WATERMETER_ID,a.WATERMETER_NAME,b.PARTMENT_NAME,a.COSTCHECK as isCheck from (watermeter)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID and a.COSTCHECK=1 ";
		if(areaID!=null && !"".equals(areaID) && !"-1".equals(areaID))
		{
			strSql+=" and a.AREA_ID="+areaID;
		}
		if(groupID!=null && !"".equals(groupID) && !"-1".equals(groupID))
		{
			strSql+=" and a.PARTMENT="+groupID;
		}
		if(archID!=null && !"".equals(archID) && !"-1".equals(archID))
		{
			strSql+=" and a.ARCHITECTURE_ID="+archID;
		}
		strSql+=" order by "+tableName+" "+order;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (standByModelPageCount * standByModelPageIndex))
				return list;

			count = count - standByModelPageCount * standByModelPageIndex;

			if (count >= standByModelPageCount)
				count = standByModelPageCount;

			if ((standByModelPageCount * standByModelPageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(standByModelPageCount * standByModelPageIndex);
			while(rs.next() && (count > 0))
			{
				waterMatchModelInfoDao mi=new waterMatchModelInfoDao();
				mi.setWaterMeterID(rs.getString("WATERMETER_ID"));
				mi.setWaterMeterName(rs.getString("WATERMETER_NAME"));
				mi.setWaterMeterPartment(rs.getString("PARTMENT_NAME"));
				mi.setIsCheck(rs.getString("isCheck"));
				list.add(mi);
				count--;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=ps.executeQuery();
		rs.last();
		int count = rs.getRow();
		setTotal(count);
		if (count <= (standByModelPageCount * standByModelPageIndex))
			return list;

		count = count - standByModelPageCount * standByModelPageIndex;

		if (count >= standByModelPageCount)
			count = standByModelPageCount;

		if ((standByModelPageCount * standByModelPageIndex) == 0)
			rs.beforeFirst();
		else
			rs.absolute(standByModelPageCount * standByModelPageIndex);
		while(rs.next() && (count > 0))
		{
			waterMatchModelInfoDao mi=new waterMatchModelInfoDao();
			mi.setWaterMeterID(rs.getString("WATERMETER_ID"));
			mi.setWaterMeterName(rs.getString("WATERMETER_NAME"));
			mi.setWaterMeterPartment(rs.getString("PARTMENT_NAME"));
			mi.setIsCheck(rs.getString("isCheck"));
			list.add(mi);
			count--;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return list;
	}


	/**
	 *  水表信息
	 * @param pageCount 分页个数
	 * @param pageIndex 第几页
	 * @return 信息
	 * @throws SQLException
	 */
	public List<waterMatchModelInfoDao> paginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order) throws SQLException
	{
		String andsql="";
		if(arch_id==-1){
			andsql="";
		}
		if(area_id!=-1){
			andsql=" and area_id="+area_id;
		}
		if(arch_id!=-1){
			andsql=" and architecture_id="+arch_id;
		}
		ArrayList<waterMatchModelInfoDao> list=new ArrayList<waterMatchModelInfoDao>();
		String strSql="select a.WATERMETER_ID,a.WATERMETER_NAME,b.PARTMENT_NAME,a.COSTCHECK as isCheck from (watermeter)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID  and a.COSTCHECK<>-1"+andsql +" ORDER BY "+tableName +" "+order;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			
			if (count <= (pageCount * pageIndex))
			{
				if(rs!=null)
					rs.close();
				
				if(ps!=null)
					ps.close();
				return list;
			}
			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			//rs.beforeFirst();
			while(rs.next() && (count > 0))
			{
				waterMatchModelInfoDao mi=new waterMatchModelInfoDao();
				mi.setWaterMeterID(rs.getString("WATERMETER_ID"));
				mi.setWaterMeterName(rs.getString("WATERMETER_NAME"));
				mi.setWaterMeterPartment(rs.getString("PARTMENT_NAME"));
				mi.setIsCheck(rs.getString("isCheck"));
				list.add(mi);
				count--;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		
		return list;
	}
	/**
	 * 获得电表【watermeter】中的记录总数
	 * 
	 * @return AmmeterModel 电表数据表记录总数
	 * @throws SQLException
	 */
	
	public int getRecordCount() throws SQLException
	{
		int count = -1;
		String sql = "SELECT count(*) as recordCount from (watermeter)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				count = rs.getInt("recordCount");
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
	/*	PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (rs.next())
			count = rs.getInt("recordCount");

		if (rs != null)
			rs.close();

		if (ps != null)
			ps.close();*/

		return count;

	}

	public String getWaterMeterID()
	{
		return waterMeterID;
	}


	public void setWaterMeterID(String waterMeterID)
	{
		this.waterMeterID = waterMeterID;
	}


	public String getWaterMeterName()
	{
		return waterMeterName;
	}


	public void setWaterMeterName(String waterMeterName)
	{
		this.waterMeterName = waterMeterName;
	}


	public String getWaterMeterPartment()
	{
		return waterMeterPartment;
	}


	public void setWaterMeterPartment(String waterMeterPartment)
	{
		this.waterMeterPartment = waterMeterPartment;
	}


	public String getIsCheck()
	{
		return isCheck;
	}


	public void setIsCheck(String isCheck)
	{
		this.isCheck = isCheck;
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
