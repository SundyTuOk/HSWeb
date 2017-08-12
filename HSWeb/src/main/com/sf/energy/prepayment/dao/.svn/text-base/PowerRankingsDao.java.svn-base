package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.PowerRankingsModel;
import com.sf.energy.util.ConnDB;

public class PowerRankingsDao
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
	

	public ArrayList<PowerRankingsModel> queryAllInfo(int pageCount, int pageIndex, int theYear, int month, String tableName, String order) throws SQLException
	{
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		PowerRankingsModel prm = null;
		
		String sql = "";
		
		String strSelect = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		String strInWhere = " and Ammeter_ID in (select Ammeter_ID from Ammeter" + strSelect + ")";
		
		sql = "select "+
				"a.AmMeter_ID,TheOn,ZGross,ZMoney,AmMeter_Name,"+
				"(select Architecture_Name from (Architecture)c where a.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,"+
				"(select Price_Name from (Price)c where a.Price_ID=c.Price_ID)Price_Name "+
			"from "+
				"(select * from AmMeter"+strSelect+")a "+
			"inner join "+
			"("+
				"select "+
					"ROW_NUMBER() over ( order by ZGross Desc ) as TheOn,"+
					"AmMeter_ID,ZGross,ZMoney "+
				"from "+
					"T_MonthAm "+
				"where "+
					"SelectYear="+theYear+
					"and SelectMonth="+month+
					strInWhere+
			")b "+
			"on a.AmMeter_ID=b.AmMeter_ID";
		sql += " order by "+tableName+" "+order;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				prm = new PowerRankingsModel();
				prm.setRank(rs.getString("TheOn"));
				prm.setArchitecture_Name(rs.getString("Architecture_Name"));
				prm.setFloorName(rs.getString("Floor"));
				prm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				prm.setPrice_Name(rs.getString("Price_Name"));
				prm.setZGross(rs.getString("ZGross"));
				prm.setZMoney(rs.getString("ZMoney"));
				
				list.add(prm);
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
	
	public ArrayList<PowerRankingsModel> queryInfoByAreaID(int pageCount, int pageIndex, int areaID, int theYear, int month, String tableName, String order) throws SQLException
	{
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		PowerRankingsModel prm = null;
		
		String sql = "";
		
		String strSelect = " where (Area_ID="+areaID+" and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";
		String strInWhere = " and Ammeter_ID in (select Ammeter_ID from Ammeter" + strSelect + ")";
		
		sql = "select "+
				"a.AmMeter_ID,TheOn,ZGross,ZMoney,AmMeter_Name,"+
				"(select Architecture_Name from (Architecture)c where a.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,"+
				"(select Price_Name from (Price)c where a.Price_ID=c.Price_ID)Price_Name "+
			"from "+
				"(select * from AmMeter"+strSelect+")a "+
			"inner join "+
			"("+
				"select "+
					"ROW_NUMBER() over ( order by ZGross Desc ) as TheOn,"+
					"AmMeter_ID,ZGross,ZMoney "+
				"from "+
					"T_MonthAm "+
				"where "+
					"SelectYear="+theYear+
					"and SelectMonth="+month+
					strInWhere+
			")b "+
			"on a.AmMeter_ID=b.AmMeter_ID";
		sql += " order by "+tableName+" "+order;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				prm = new PowerRankingsModel();
				prm.setRank(rs.getString("TheOn"));
				prm.setArchitecture_Name(rs.getString("Architecture_Name"));
				prm.setFloorName(rs.getString("Floor"));
				prm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				prm.setPrice_Name(rs.getString("Price_Name"));
				prm.setZGross(rs.getString("ZGross"));
				prm.setZMoney(rs.getString("ZMoney"));
				
				list.add(prm);
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
	
	public ArrayList<PowerRankingsModel> queryInfoByArchID(int pageCount, int pageIndex, int archID, int theYear, int month, String tableName, String order) throws SQLException
	{
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		PowerRankingsModel prm = null;
		
		String sql = "";
		
		String strSelect = " where (Architecture_ID="+archID+")";
		String strInWhere = " and Ammeter_ID in (select Ammeter_ID from Ammeter" + strSelect + ")";
		
		sql = "select "+
				"a.AmMeter_ID,TheOn,ZGross,ZMoney,AmMeter_Name,"+
				"(select Architecture_Name from (Architecture)c where a.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,"+
				"(select Price_Name from (Price)c where a.Price_ID=c.Price_ID)Price_Name "+
			"from "+
				"(select * from AmMeter"+strSelect+")a "+
			"inner join "+
			"("+
				"select "+
					"ROW_NUMBER() over ( order by ZGross Desc ) as TheOn,"+
					"AmMeter_ID,ZGross,ZMoney "+
				"from "+
					"T_MonthAm "+
				"where "+
					"SelectYear="+theYear+
					"and SelectMonth="+month+
					strInWhere+
			")b "+
			"on a.AmMeter_ID=b.AmMeter_ID";
		sql += " order by "+tableName+" "+order;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				prm = new PowerRankingsModel();
				prm.setRank(rs.getString("TheOn"));
				prm.setArchitecture_Name(rs.getString("Architecture_Name"));
				prm.setFloorName(rs.getString("Floor"));
				prm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				prm.setPrice_Name(rs.getString("Price_Name"));
				prm.setZGross(rs.getString("ZGross"));
				prm.setZMoney(rs.getString("ZMoney"));
				
				list.add(prm);
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
	
	public ArrayList<PowerRankingsModel> queryInfoByFloorNum(int pageCount, int pageIndex,int archID, int floorNum, int theYear, int month, String tableName, String order) throws SQLException
	{
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		PowerRankingsModel prm = null;
		
		String sql = "";
		
		String strSelect = " where (Architecture_ID="+archID+") and (Floor="+floorNum+")";
		String strInWhere = " and Ammeter_ID in (select Ammeter_ID from Ammeter" + strSelect + ")";
		
		sql = "select "+
				"a.AmMeter_ID,TheOn,ZGross,ZMoney,AmMeter_Name,"+
				"(select Architecture_Name from (Architecture)c where a.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,"+
				"(select Price_Name from (Price)c where a.Price_ID=c.Price_ID)Price_Name "+
			"from "+
				"(select * from AmMeter"+strSelect+")a "+
			"inner join "+
			"("+
				"select "+
					"ROW_NUMBER() over ( order by ZGross Desc ) as TheOn,"+
					"AmMeter_ID,ZGross,ZMoney "+
				"from "+
					"T_MonthAm "+
				"where "+
					"SelectYear="+theYear+
					"and SelectMonth="+month+
					strInWhere+
			")b "+
			"on a.AmMeter_ID=b.AmMeter_ID";
		sql += " order by "+tableName+" "+order;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				prm = new PowerRankingsModel();
				prm.setRank(rs.getString("TheOn"));
				prm.setArchitecture_Name(rs.getString("Architecture_Name"));
				prm.setFloorName(rs.getString("Floor"));
				prm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				prm.setPrice_Name(rs.getString("Price_Name"));
				prm.setZGross(rs.getString("ZGross"));
				prm.setZMoney(rs.getString("ZMoney"));
				
				list.add(prm);
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

	public ArrayList<PowerRankingsModel> queryInfoByAmmhID(int pageCount, int pageIndex, int archID, int theYear, int month, String tableName, String order) throws SQLException
	{
		ArrayList<PowerRankingsModel> list = new ArrayList<PowerRankingsModel>();
		PowerRankingsModel prm = null;
		
		String sql = "";
		
		String strSelect = " where (ammeter_id="+archID+")";
		String strInWhere = " and Ammeter_ID in (select Ammeter_ID from Ammeter" + strSelect + ")";
		
		sql = "select "+
				"a.AmMeter_ID,TheOn,ZGross,ZMoney,AmMeter_Name,"+
				"(select Architecture_Name from (Architecture)c where a.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,"+
				"(select Price_Name from (Price)c where a.Price_ID=c.Price_ID)Price_Name "+
			"from "+
				"(select * from AmMeter"+strSelect+")a "+
			"inner join "+
			"("+
				"select "+
					"ROW_NUMBER() over ( order by ZGross Desc ) as TheOn,"+
					"AmMeter_ID,ZGross,ZMoney "+
				"from "+
					"T_MonthAm "+
				"where "+
					"SelectYear="+theYear+
					"and SelectMonth="+month+
					strInWhere+
			")b "+
			"on a.AmMeter_ID=b.AmMeter_ID";
		sql += " order by "+tableName+" "+order;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				prm = new PowerRankingsModel();
				prm.setRank(rs.getString("TheOn"));
				prm.setArchitecture_Name(rs.getString("Architecture_Name"));
				prm.setFloorName(rs.getString("Floor"));
				prm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				prm.setPrice_Name(rs.getString("Price_Name"));
				prm.setZGross(rs.getString("ZGross"));
				prm.setZMoney(rs.getString("ZMoney"));
				
				list.add(prm);
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

}
