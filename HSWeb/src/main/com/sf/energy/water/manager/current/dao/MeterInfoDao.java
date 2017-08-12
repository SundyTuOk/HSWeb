package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.MeterInfoModel;

public class MeterInfoDao
{
	private int total;
	/**
	 *  待机功耗的信息
	 * @param pageCount 分页个数
	 * @param pageIndex 第几页
	 * @return 信息
	 * @throws SQLException
	 */
	public List<MeterInfoModel> paginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order) 
	{
		List<MeterInfoModel> list=new ArrayList<MeterInfoModel>();
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
		String strSql="select a.WATERMETER_ID,a.WATERMETER_NAME,b.PARTMENT_NAME,a.STANDBYCHECK as isCheck from (WATERMETER)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID  and a.STANDBYCHECK<>-1"+andsql+" ORDER BY "+tableName+" "+order;
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
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

			while(rs.next() && (count > 0))
			{
				MeterInfoModel mi=new MeterInfoModel();
				mi.setWatermeter_ID(rs.getString("WATERMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setWatermeter_name(rs.getString("WATERMETER_NAME"));
				mi.setIsCheck(rs.getInt("isCheck"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		
		return list;
	}
	/**
	 * 电能匹配模型电表信息
	 * @param pageCount 分页个数
	 * @param pageIndex 第几页
	 * @return 电能匹配模型信息
	 * @throws SQLException
	 */
	public List<MeterInfoModel> energyMatchPaginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order)
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
		List<MeterInfoModel> list=new ArrayList<MeterInfoModel>();
		String strSql="select a.WATERMETER_ID,a.WATERMETER_NAME,b.PARTMENT_NAME,a.COSTCHECK as isCheck from (WATERMETER)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID and a.COSTCHECK<>-1"+andsql +" ORDER BY "+tableName+" " +order;

		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
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

			while(rs.next() && (count > 0))
			{
				MeterInfoModel mi=new MeterInfoModel();
				mi.setWatermeter_ID(rs.getString("WATERMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setWatermeter_name(rs.getString("WATERMETER_NAME"));
				mi.setIsCheck(rs.getInt("isCheck"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}
	
	
	/**
	 * 获得电表【WATERMETER】中的记录总数
	 * 
	 * @return WATERMETERModel 电表数据表记录总数
	 * @throws SQLException
	 */
	public int getRecordCount() 
	{
		int count = -1;
		String sql = "SELECT count(*) as recordCount from (WATERMETER)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			if (rs.next())
				count = rs.getInt("recordCount");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return count;

	}
	
	/**
	 * 根据区域ID 部门ID 建筑ID 查询的所有电表的信息
	 * @param area_Id 区域的标示
	 * @param parment_ID 部门标示
	 * @param architecture_ID 建筑标示
	 * @param standByModelPageIndex 
	 * @param standByModelPageCount 
	 * @param check 
	 * @param order 
	 * @param tableName 
	 * @return 返回所有的对象的数组
	 * @throws SQLException
	 */
	public List<MeterInfoModel> getMeterInfo(String area_Id,String parment_ID,String architecture_ID, int standByModelPageCount, int standByModelPageIndex, String check, String tableName, String order) 
	{
		List<MeterInfoModel> list=new ArrayList<MeterInfoModel>();
		String strSql="select a.WATERMETER_ID,a.WATERMETER_NAME,b.PARTMENT_NAME,a."+check+" as isCheck from (WATERMETER)a,(PARTMENT)b where a.PARTMENT = b.PARTMENT_ID and a."+check+"=1 ";
		if(area_Id!=null && !"".equals(area_Id) && !"-1".equals(area_Id))
		{
			strSql+=" and a.AREA_ID="+area_Id;
		}
		if(parment_ID!=null && !"".equals(parment_ID) && !"-1".equals(parment_ID))
		{
			strSql+=" and a.PARTMENT="+parment_ID;
		}
		if(architecture_ID!=null && !"".equals(architecture_ID) && !"-1".equals(architecture_ID))
		{
			strSql+=" and a.ARCHITECTURE_ID="+architecture_ID;
		}
		strSql+=" order by "+tableName+" "+order;
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(strSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			rs=ps.executeQuery();
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
				MeterInfoModel mi=new MeterInfoModel();
				mi.setWatermeter_ID(rs.getString("WATERMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setWatermeter_name(rs.getString("WATERMETER_NAME"));
				mi.setIsCheck(rs.getInt("isCheck"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
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
