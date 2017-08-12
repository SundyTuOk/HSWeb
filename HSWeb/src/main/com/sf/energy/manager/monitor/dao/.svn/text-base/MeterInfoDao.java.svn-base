package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;

public class MeterInfoDao
{
	String ammerter_ID=null;
	String ammerter_name=null;
	String partment=null;
	int isCheck;
	int total;
	

	/**
	 *  待机功耗的信息
	 * @param pageCount 分页个数
	 * @param pageIndex 第几页
	 * @return 信息
	 * @throws SQLException
	 */
	public List<MeterInfoDao> paginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order) 
	{
		ArrayList<MeterInfoDao> list=new ArrayList<MeterInfoDao>();
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
		String strSql="select a.AMMETER_ID,a.AMMETER_NAME,b.PARTMENT_NAME,a.STANDBYCHECK as isCheck from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID  and a.STANDBYCHECK<>-1"+andsql+" ORDER BY "+tableName+" "+order;
		
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
				MeterInfoDao mi=new MeterInfoDao();
				mi.setAmmerter_ID(rs.getString("AMMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setAmmerter_name(rs.getString("AMMETER_NAME"));
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
	
	public List<MeterInfoDao> archAmStandbypaginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order) 
	{
		ArrayList<MeterInfoDao> list=new ArrayList<MeterInfoDao>();
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
		String strSql="select a.AMMETER_ID,a.AMMETER_NAME,b.PARTMENT_NAME,"
				+ "(SELECT NVL(max(isvoice), 0) isvoice FROM ARCHAMSTANDBYMODEL WHERE AMMETER_ID = A.AMMETER_ID) as isCheck from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID  "+andsql+" ORDER BY "+tableName+" "+order;
		
		String strSql1="select * from (select 'INSERT INTO ARCHAMSTANDBYMODEL (AMMETER_ID, STARTTIME, ENDTIME, LOWLIMIT, UPPERLIMIT, LASTCHECKTIME, ISTOMORROW, ISVOICE) VALUES ('''||A .AMMETER_ID||''', ''23:00'', ''07:00'', ''0'', ''2'', null, ''1'', ''1'');' as insertsql,"
				+ "a.AMMETER_NAME,b.PARTMENT_NAME,a.ISCOUNTMETER,(SELECT PRICE_name from price where PRICE_ID = a.PRICE_ID) price_Name,"
				+ "(SELECT NVL(max(isvoice), 0) isvoice FROM ARCHAMSTANDBYMODEL WHERE AMMETER_ID = A.AMMETER_ID) as isCheck from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID  "+andsql+" ORDER BY "+tableName+" "+order+")"
						+ "WHERE PARTMENT_NAME<>'华中师范大学' AND (price_name='空调电费' or price_name='照明电费' or price_name='公共电费') AND ISCOUNTMETER=0";
		
		System.out.println(strSql1);
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
				MeterInfoDao mi=new MeterInfoDao();
				mi.setAmmerter_ID(rs.getString("AMMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setAmmerter_name(rs.getString("AMMETER_NAME"));
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
	public List<MeterInfoDao> energyMatchPaginate(int pageCount, int pageIndex,int area_id,int arch_id,String tableName,String order)
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
		ArrayList<MeterInfoDao> list=new ArrayList<MeterInfoDao>();
		String strSql="select a.AMMETER_ID,a.AMMETER_NAME,b.PARTMENT_NAME,a.COSTCHECK as isCheck from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID and a.COSTCHECK<>-1"+andsql +" ORDER BY "+tableName+" " +order;

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
				MeterInfoDao mi=new MeterInfoDao();
				mi.setAmmerter_ID(rs.getString("AMMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setAmmerter_name(rs.getString("AMMETER_NAME"));
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
	 * 获得电表【Ammeter】中的记录总数
	 * 
	 * @return AmmeterModel 电表数据表记录总数
	 * @throws SQLException
	 */
	public int getRecordCount() 
	{
		int count = -1;
		String sql = "SELECT count(*) as recordCount from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID";
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
	public ArrayList<MeterInfoDao> getMeterInfo(String area_Id,String parment_ID,String architecture_ID, int standByModelPageCount, int standByModelPageIndex, String check, String tableName, String order) 
	{
		ArrayList<MeterInfoDao> list=new ArrayList<MeterInfoDao>();
		String strSql="select a.AMMETER_ID,a.AMMETER_NAME,b.PARTMENT_NAME,a."+check+" as isCheck from (AMMETER)a,(PARTMENT)b where a.PARTMENT_ID = b.PARTMENT_ID and a."+check+"=1 ";
		if(area_Id!=null && !"".equals(area_Id) && !"-1".equals(area_Id))
		{
			strSql+=" and a.AREA_ID="+area_Id;
		}
		if(parment_ID!=null && !"".equals(parment_ID) && !"-1".equals(parment_ID))
		{
			strSql+=" and a.PARTMENT_ID="+parment_ID;
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
				MeterInfoDao mi=new MeterInfoDao();
				mi.setAmmerter_ID(rs.getString("AMMETER_ID"));
				mi.setPartment(rs.getString("PARTMENT_NAME"));
				mi.setAmmerter_name(rs.getString("AMMETER_NAME"));
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

	public String getAmmerter_ID()
	{
		return ammerter_ID;
	}

	public void setAmmerter_ID(String ammerter_ID)
	{
		this.ammerter_ID = ammerter_ID;
	}

	public String getAmmerter_name()
	{
		return ammerter_name;
	}

	public void setAmmerter_name(String ammerter_name)
	{
		this.ammerter_name = ammerter_name;
	}

	public String getPartment()
	{
		return partment;
	}

	public void setPartment(String partment)
	{
		this.partment = partment;
	}

	public int getIsCheck()
	{
		return isCheck;
	}

	public void setIsCheck(int isCheck)
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
