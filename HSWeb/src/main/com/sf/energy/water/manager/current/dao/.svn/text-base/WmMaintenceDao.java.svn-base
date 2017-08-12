package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.manager.current.model.AmMaintence;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.WmMaintence;

public class WmMaintenceDao
{
	private int total;
	WatermeterDao amd = new WatermeterDao();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getTotal()
	{
		return total;
	}
	public List<WmMaintence> selectAllWmMaintenceByLableOrder(String theLable,
			boolean isAsc, int pageCount,int pageIndex) throws SQLException
	{
		List<WmMaintence> list = null;

		String sql = "select * from WaterMaintenance order by " + theLable;

		if (isAsc)
			sql += " asc";
		else
			sql += " desc";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
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

			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<WmMaintence>();
				list.add(buildInstance(rs));
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
	public List<WmMaintence> selectWmMaintenceByTree(String theLable,
			boolean isAsc, int areaID, int archID, int floor, int wamID, int pageCount,int pageIndex) throws SQLException
	{
		List<WmMaintence> list = null;
		String a, b, c, d;
		if (areaID == -1)
		{
			a = "'%'";
		} else
		{
			a = areaID + "";
		}

		if (archID == -1)
		{
			b = "'%'";
		} else
		{
			b = archID + "";
		}

		if (floor == -1)
		{
			c = "'%'";
		} else
		{
			c = floor + "";
		}

		if (wamID == -1)
		{
			d = "'%'";
		} else
		{
			d = wamID + "";
		}
		
		String sql = "select aa.* from WATERMAINTENANCE aa LEFT JOIN WATERMETER bb on aa.WATERMETER_ID=bb.WATERMETER_ID where bb.area_ID like " + a + " and bb.Architecture_ID like " + b + " and bb.floor like " + c
				+ " and bb.WATERMETER_ID like " + d+" order by " + theLable;

		if (isAsc)
			sql += " asc";
		else
			sql += " desc";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
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

			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<WmMaintence>();
				list.add(buildInstance(rs));
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
	public List<WmMaintence> selectAllWmMaintence() throws SQLException
	{
		List<WmMaintence> list = null;

		String sql = "select * from WaterMaintenance order by MAINT_ID desc";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<WmMaintence>();
				list.add(buildInstance(rs));
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

	public WmMaintence selectAllWmMaintenceByID(int mainID) throws SQLException
	{
		WmMaintence amm = null;

		String sql = "select * from WaterMaintenance where MAINT_ID = ?";

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mainID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				amm = buildInstance(rs);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

		return amm;
	}

	public boolean insertWmMaintence(WmMaintence amm) throws SQLException
	{
		boolean flag = false;

		String sql = "INSERT INTO WaterMaintenance (WaterMETER_ID,"
				+ "MAINTTIME,MAINTREMARK,MAINTMAN) VALUES (?,"
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?)";


		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, amm.getWatermeterID());
			ps.setString(2, df.format(amm.getMainTime()));
			ps.setString(3, amm.getMaintRemark());
			ps.setString(4, amm.getMaintMan());
			flag = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean deleteWmMaintence(int ID) throws SQLException
	{
		boolean flag = false;

		String sql = "DELETE FROM WaterMaintenance WHERE MAINT_ID = ?";


		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ID);
			flag = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		return flag;
	}

	public boolean updateWmMaintence(WmMaintence amm) throws SQLException
	{
		boolean flag = false;

		String sql = "UPDATE WaterMaintenance SET WaterMETER_ID = ?,"
				+ "MAINTTIME = to_date(?,'yyyy-mm-dd hh24:mi:ss'),"
				+ "MAINTREMARK=?,MAINTMAN= ? WHERE MAINT_ID = ?";


		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, amm.getWatermeterID());
			ps.setString(2, df.format(amm.getMainTime()));
			ps.setString(3, amm.getMaintRemark());
			ps.setString(4, amm.getMaintMan());
			ps.setInt(5, amm.getMaintID());
			flag = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		
		return flag;
	}

	private WmMaintence buildInstance(ResultSet rs) throws SQLException
	{
		WmMaintence amm = new WmMaintence();

		amm.setMaintID(rs.getInt("MAINT_ID"));
		amm.setWatermeterID(rs.getInt("WaterMETER_ID"));
		Timestamp ts = rs.getTimestamp("MAINTTIME");
		if (ts != null)
			amm.setMainTime(new Date(ts.getTime()));
		amm.setMaintRemark(rs.getString("MAINTREMARK"));
		amm.setMaintMan(rs.getString("MAINTMAN"));
		WatermeterModel ammeter = amd.getWatermeterByID(amm.getWatermeterID());
		if (ammeter != null)
			amm.setWatermeterName(ammeter.getWATERMETER_NAME());

		return amm;
	}
}
