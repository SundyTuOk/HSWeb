package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.prepayment.model.ApKaiHuInfo;
import com.sf.energy.util.ConnDB;

public class ApKaiHuInfoDao
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public List<ApKaiHuInfo> display() throws SQLException
	{
		List<ApKaiHuInfo> list = null;
		String sql = "select * from V_ApKaiHuInfo";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ApKaiHuInfo>();
				}
				list.add(buildInstance(rs));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		while (rs.next())
//		{
//			if (list == null)
//			{
//				list = new LinkedList<ApKaiHuInfo>();
//			}
//			list.add(buildInstance(rs));
//		}


		return list;
	}

	public List<ApKaiHuInfo> search(Date start, Date end, String sortLable,
			boolean isAsc) throws SQLException
	{
		List<ApKaiHuInfo> list = null;
		String sql = "select * from V_ApKaiHuInfo where theTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') order by "
				+ sortLable;

		if (isAsc)
		{
			sql += " asc";
		} else
		{
			sql += " desc";
		}

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, df.format(start));
			ps.setString(2, df.format(end));

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
				{
					list = new LinkedList<ApKaiHuInfo>();
				}
				list.add(buildInstance(rs));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setString(1, df.format(start));
//		ps.setString(2, df.format(end));
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			if (list == null)
//			{
//				list = new LinkedList<ApKaiHuInfo>();
//			}
//			list.add(buildInstance(rs));
//		}

		return list;
	}

	public Map<String, Object> searchParginate(Date start, Date end,
			String sortLable, boolean isAsc, int pageCount, int pageIndex)
			throws SQLException
	{
		List<ApKaiHuInfo> list = null;
		String sql = "select * from V_ApKaiHuInfo where theTime between "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') order by "
				+ sortLable;

		if (isAsc)
		{
			sql += " asc";
		} else
		{
			sql += " desc";
		}

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, df.format(start));
			ps.setString(2, df.format(end));

			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			int total = count;
			if (count <= (pageCount * pageIndex))
				return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				if (list == null)
				{
					list = new LinkedList<ApKaiHuInfo>();
				}
				list.add(buildInstance(rs));
				count--;
			}

			map = new HashMap<String, Object>();

			if (list != null && list.size() > 0)
			{
				map.put("List", list);
				map.put("Count", total);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		ps.setString(1, df.format(start));
//		ps.setString(2, df.format(end));
//
//		ResultSet rs = ps.executeQuery();
//
//		rs.last();
//		int count = rs.getRow();
//		int total = count;
//		if (count <= (pageCount * pageIndex))
//			return null;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//		{
//			rs.absolute(1);
//			rs.previous();
//		} else
//			rs.absolute(pageCount * pageIndex);
//
//		while (rs.next() && (count > 0))
//		{
//			if (list == null)
//			{
//				list = new LinkedList<ApKaiHuInfo>();
//			}
//			list.add(buildInstance(rs));
//			count--;
//		}
//
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		if (list != null && list.size() > 0)
//		{
//			map.put("List", list);
//			map.put("Count", total);
//		}

		return map;
	}
	
	public String getKaiHuInfoByID(int id){
		String str = "";
		String sql = "SELECT AMMETER_NAME,AMMETER_485ADDRESS from AMMETER where AMMETER_ID=(SELECT AMMETER_ID from APKAIHUINFO where id="+id+")";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next())
			{
				str = rs.getString("AMMETER_NAME")+",表地址："+rs.getString("AMMETER_485ADDRESS");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return str;
	}

	public boolean deleteInfo(int[] idArray) throws SQLException
	{
		boolean flag = true;

		String sql = "delete from ApKaiHuInfo where ID = ?";

		Connection conn =null;
		PreparedStatement ps=null;
		try
		{
			conn = ConnDB.getConnection();

			ps = conn.prepareStatement(sql);

			for (int i : idArray)
			{
				ps.setInt(1, i);
				ps.addBatch();
			}

			int[] result = ps.executeBatch();

			for (int state : result)
			{
				if (state != 1 && state != -2)
				{
					flag = false;
					break;
				}
			}
			conn.commit();
			ps.clearBatch();
		} catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean addKaiHuInfo(List<ApKaiHuInfo> infoList) throws SQLException
	{
		if (infoList != null || infoList.size() == 0)
			return false;

		boolean flag = false;

		String sql = "insert into ApKaiHuInfo (Ammeter_ID,TheTime,OldSY,NewZValue,"
				+ "SYValue,TZValue,TheMoney,Users_ID) values (?,"
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?)";

		Connection conn =null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);

			for (ApKaiHuInfo k : infoList)
			{
				ps.setInt(1, k.getAmmeter_ID());
				ps.setString(2, df.format(new Date()));
				ps.setFloat(3, k.getOldsy());
				ps.setFloat(4, k.getNewzValue());
				ps.setFloat(5, k.getSyValue());
				ps.setFloat(6, k.getTzValue());
				ps.setFloat(7, k.getTheMoney());
				ps.setInt(8, k.getUserID());

				ps.addBatch();
			}

			int[] result = ps.executeBatch();

			ps.clearBatch();

			for (int i : result)
			{
				if (i != 1)
				{
					flag = false;
					conn.rollback();
					break;
				}
			}
			conn.setAutoCommit(true);
		} catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public boolean updateKaiHuInfo(List<ApKaiHuInfo> infoList)
			throws SQLException
	{
		if (infoList != null || infoList.size() == 0)
			return false;

		boolean flag = false;

		String sql = "update ApKaiHuInfo set TheTime = ?,OldSY = ?,NewZValue = ?,"
				+ "SYValue = ?,TZValue = ?,TheMoney = ?,Users_ID = ? where Ammeter_ID = ?";

		Connection conn =null;
		PreparedStatement ps=null;
		try
		{
			conn = ConnDB.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (ApKaiHuInfo k : infoList)
			{
				ps.setString(1, df.format(new Date()));
				ps.setFloat(2, k.getOldsy());
				ps.setFloat(3, k.getNewzValue());
				ps.setFloat(4, k.getSyValue());
				ps.setFloat(5, k.getTzValue());
				ps.setFloat(6, k.getTheMoney());
				ps.setInt(7, k.getUserID());
				ps.setInt(8, k.getAmmeter_ID());

				ps.addBatch();
			}

			int[] result = ps.executeBatch();

			ps.clearBatch();

			for (int i : result)
			{
				if (i != 1)
				{
					flag = false;
					conn.rollback();
					break;
				}
			}

			conn.setAutoCommit(true);
		} catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	private ApKaiHuInfo buildInstance(ResultSet rs) throws SQLException
	{
		ApKaiHuInfo apk = new ApKaiHuInfo();

		apk.setID(rs.getInt("ID"));
		apk.setAreaName(rs.getString("Area_Name"));
		apk.setArchName(rs.getString("Architecture_Name"));
		apk.setFloor(rs.getInt("Floor"));
		apk.setAmmeter_Name(rs.getString("Ammeter_Name"));
		apk.setAmmeter_ID(rs.getInt("Ammeter_ID"));
		Timestamp ts = rs.getTimestamp("TheTime");
		apk.setTheTime(new Date(ts.getTime()));
		apk.setOldsy(rs.getFloat("OldSy"));
		apk.setNewzValue(rs.getFloat("NewZValue"));
		apk.setSyValue(rs.getFloat("SyValue"));
		apk.setTzValue(rs.getFloat("TzValue"));
		apk.setTheMoney(rs.getFloat("TheMoney"));
		apk.setUserID(rs.getInt("Users_ID"));
		apk.setUserName(rs.getString("Users_Name"));

		return apk;
	}
}
