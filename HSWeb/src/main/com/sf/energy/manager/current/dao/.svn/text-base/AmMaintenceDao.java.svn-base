package com.sf.energy.manager.current.dao;

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
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.WmMaintence;

public class AmMaintenceDao
{	int total;
	AmmeterDao amd = new AmmeterDao();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void setTotal(int total)
	{
		this.total = total;
	}
	public int getTotal()
	{
		return total;
	}
	public List<AmMaintence> selectAllAmMaintenceByLableOrder(String theLable,
			boolean isAsc, int pageCount,int pageIndex) 
	{
		List<AmMaintence> list = null;

		String sql = "select * from AmMaintenance order by " + theLable;

		if (isAsc)
			sql += " asc";
		else
			sql += " desc";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
					list = new LinkedList<AmMaintence>();
				list.add(buildInstance(rs));
				count--;
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	public List<AmMaintence> selectAmMaintenceByTree(String theLable,
			boolean isAsc, int areaID, int archID, int floor, int ammID, int pageCount,int pageIndex)
	{
		List<AmMaintence> list = null;
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

		if (ammID == -1)
		{
			d = "'%'";
		} else
		{
			d = ammID + "";
		}
		
		String sql = "select aa.* from AMMAINTENANCE aa LEFT JOIN AMMETER bb on aa.ammeter_ID=bb.ammeter_ID where bb.area_ID like " + a + " and bb.Architecture_ID like " + b + " and bb.floor like " + c
				+ " and bb.Ammeter_ID like " + d+" order by " + theLable;

		if (isAsc)
			sql += " asc";
		else
			sql += " desc";
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
					list = new LinkedList<AmMaintence>();
				list.add(buildInstance(rs));
				count--;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}
	public List<AmMaintence> selectAllAmMaintence() 
	{
		List<AmMaintence> list = null;

		String sql = "select * from AmMaintenance order by MAINT_ID desc";

		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<AmMaintence>();
				list.add(buildInstance(rs));
			}

			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public AmMaintence selectAllAmMaintenceByID(int mainID) throws SQLException
	{
		AmMaintence amm = null;

		String sql = "select * from AmMaintenance where MAINT_ID = ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, mainID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				amm = buildInstance(rs);
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return amm;
	}

	public boolean insertAmMaintence(AmMaintence amm)
	{
		boolean flag = false;

		String sql = "INSERT INTO AmMaintenance (AMMETER_ID,"
				+ "MAINTTIME,MAINTREMARK,MAINTMAN) VALUES (?,"
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?)";
		Connection conn=null;
		PreparedStatement ps=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, amm.getAmmeterID());
			ps.setString(2, df.format(amm.getMainTime()));
			ps.setString(3, amm.getMaintRemark());
			ps.setString(4, amm.getMaintMan());

			if (ps.executeUpdate() == 1)
			{
				flag = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean deleteAmMaintence(int ID)
	{
		boolean flag = false;

		String sql = "DELETE FROM AmMaintenance WHERE MAINT_ID = ?";
		Connection conn=null;
		PreparedStatement ps=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, ID);

			if (ps.executeUpdate() == 1)
			{
				flag = true;
			}

			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps);
		}
		return flag;
	}

	public boolean updateAmMaintence(AmMaintence amm) throws SQLException
	{
		boolean flag = false;

		String sql = "UPDATE AmMaintenance SET AMMETER_ID = ?,"
				+ "MAINTTIME = to_date(?,'yyyy-mm-dd hh24:mi:ss'),"
				+ "MAINTREMARK=?,MAINTMAN= ? WHERE MAINT_ID = ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(5, amm.getMaintID());
			ps.setInt(1, amm.getAmmeterID());
			ps.setString(2, df.format(amm.getMainTime()));
			ps.setString(3, amm.getMaintRemark());
			ps.setString(4, amm.getMaintMan());
			if (ps.executeUpdate() == 1)
			{
				flag = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return flag;
	}

	private AmMaintence buildInstance(ResultSet rs) throws SQLException
	{
		AmMaintence amm = new AmMaintence();

		amm.setMaintID(rs.getInt("MAINT_ID"));
		amm.setAmmeterID(rs.getInt("AMMETER_ID"));
		Timestamp ts = rs.getTimestamp("MAINTTIME");
		if (ts != null)
			amm.setMainTime(new Date(ts.getTime()));
		amm.setMaintRemark(rs.getString("MAINTREMARK"));
		amm.setMaintMan(rs.getString("MAINTMAN"));
		AmmeterModel ammeter = amd.queryByID(amm.getAmmeterID());
		if (ammeter != null)
			amm.setAmmeterName(ammeter.getAmmeterName());

		return amm;
	}
}
