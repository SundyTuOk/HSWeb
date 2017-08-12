package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.expert.model.AmmeterManual;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.util.ConnDB;

public class AmmeterManualDao
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	AmmeterDao amDao = new AmmeterDao();
	AreaDao areaDao = new AreaDao();
	ArchitectureDao archDao = new ArchitectureDao();

	public Map<String, Object> parginate(int userID, int areaID, int archID,
			int meterID, String sortLable, boolean isAsc, int pageCount,
			int pageIndex) throws SQLException
	{
		List<AmmeterManual> list = null;

		String sql = "select * from V_ManualAmmeter where Users_ID = " + userID;

		if (areaID != -1)
		{
			sql += " and Area_ID = " + areaID;

			if (archID != -1)
			{
				sql += " and Architecture_ID = " + archID;

				if (meterID != -1)
				{
					sql += " and Meter_ID = " + meterID;
				}
			}
		}

		sql += " Order by " + sortLable;

		if (isAsc)
		{
			sql += " Desc";
		}
		Map<String, Object> m=null;
		//System.out.println("AmmeterManualDao---SQL:" + sql);
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

			// rs.previous();
			while (rs.next() && (count > 0))
			{
				if (list == null)
					list = new LinkedList<AmmeterManual>();

				list.add(buildInstance(rs));
				count--;
			}

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			m = new HashMap<String, Object>();
			m.put("List", list);
			m.put("TotalCount", total);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		

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
//		// rs.previous();
//		while (rs.next() && (count > 0))
//		{
//			if (list == null)
//				list = new LinkedList<AmmeterManual>();
//
//			list.add(buildInstance(rs));
//			count--;
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
//
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("List", list);
//		m.put("TotalCount", total);

		return m;
	}

	public List<AmmeterManual> selectAllAmManual() throws SQLException
	{
		List<AmmeterManual> list = null;

		String sql = "select * from V_ManualAmmeter";
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
					list = new LinkedList<AmmeterManual>();

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
//				list = new LinkedList<AmmeterManual>();
//
//			list.add(buildInstance(rs));
//		}

		return list;
	}

	public List<AmmeterManual> selectAmManualByUser(int userID)
			throws SQLException
	{
		List<AmmeterManual> list = null;

		String sql = "select * from V_ManualAmmeter where Users_ID = ? order by DATAID desc";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, userID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<AmmeterManual>();

				AmmeterManual am = buildInstance(rs);

				if (am != null)
					list.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
//
//		ps.setInt(1, userID);
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			if (list == null)
//				list = new LinkedList<AmmeterManual>();
//
//			AmmeterManual am = buildInstance(rs);
//
//			if (am != null)
//				list.add(am);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}

	public AmmeterManual selectAmManualByDataID(int dataID) throws SQLException
	{
		AmmeterManual amManual = null;

		String sql = "select * from V_ManualAmmeter where DATAID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, dataID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				amManual = buildInstance(rs);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
//
//		ps.setInt(1, dataID);
//
//		ResultSet rs = ps.executeQuery();
//
//		if (rs.next())
//		{
//			amManual = buildInstance(rs);
//		}
//
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return amManual;
	}

	public boolean deleteSomeAmManualByDataID(List<Integer> dataIDList)
			throws SQLException
	{
		boolean flag = false;

		String sql = "delete from ManualAmmeter where DATAID in (";

		for (int i = 0; i < dataIDList.size(); i++)
		{
			sql += dataIDList.get(i);
			if (i < dataIDList.size() - 1)
				sql += ",";
			else
				sql += ")";
		}
		Connection conn=null;
		PreparedStatement ps =null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			if (ps.executeUpdate() == dataIDList.size())
				flag = true;
			else
				flag = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
//
//		if (ps.executeUpdate() == dataIDList.size())
//			flag = true;
//		else
//			flag = false;
//
//		if (ps != null)
//			ps.close();

		return flag;
	}

	public boolean deleteAmManualByDataID(int dataID) throws SQLException
	{
		boolean flag = false;

		String sql = "delete from ManualAmmeter where DATAID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataID);

			if (ps.executeUpdate() == 1)
				flag = true;
			else
				flag = false;

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
//
//		ps.setInt(1, dataID);
//
//		if (ps.executeUpdate() == 1)
//			flag = true;
//		else
//			flag = false;
//
//		if (ps != null)
//			ps.close();

		return flag;
	}

	public boolean insertAmManual(AmmeterManual amManual) throws SQLException
	{
		boolean flag = false;

		AmmeterModel am = amDao.queryByID(amManual.getMeterID());

		if (am == null)
			return false;

		String sql = "insert into ManualAmmeter(Meter_ID,ValueTime,AREA_ID,"
				+ "ARCHITECTURE_ID,ZVALUE,ZGROSS,POWERFACTORZ,ZMONEY,INSERTTIME,"
				+ "USERS_ID) VALUES (?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,"
				+ "?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, amManual.getMeterID());
			ps.setString(2, df.format(amManual.getValueTime()));
			ps.setInt(3, am.getAreaID());
			ps.setInt(4, am.getArchitectureID());
			ps.setFloat(5, amManual.getzValue());
			ps.setFloat(6, amManual.getzGross());
			ps.setFloat(7, amManual.getPowerFactorz());
			ps.setFloat(8, amManual.getzMoney());
			ps.setString(9, df.format(amManual.getInsertTime()));
			ps.setInt(10, amManual.getUserID());
			if (ps.executeUpdate() == 1)
				flag = true;
			else
				flag = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		ps.setInt(1, amManual.getMeterID());
//		ps.setString(2, df.format(amManual.getValueTime()));
//		ps.setInt(3, am.getAreaID());
//		ps.setInt(4, am.getArchitectureID());
//		ps.setFloat(5, amManual.getzValue());
//		ps.setFloat(6, amManual.getzGross());
//		ps.setFloat(7, amManual.getPowerFactorz());
//		ps.setFloat(8, amManual.getzMoney());
//		ps.setString(9, df.format(amManual.getInsertTime()));
//		ps.setInt(10, amManual.getUserID());


		return flag;
	}

	public boolean updateAmManual(AmmeterManual amManual) throws SQLException
	{
		boolean flag = false;

		if (selectAmManualByDataID(amManual.getDataID()) == null)
			return false;

		AmmeterModel am = amDao.queryByID(amManual.getMeterID());

		if (am == null)
			return false;

		String sql = "update ManualAmmeter set Meter_ID = ?,ValueTime = "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),AREA_ID = ?,ARCHITECTURE_ID = ?,"
				+ "ZVALUE = ?,ZGROSS = ?,POWERFACTORZ = ?,ZMONEY = ?,INSERTTIME = "
				+ "to_date(?,'yyyy-mm-dd hh24:mi:ss'),USERS_ID = ? where DATAID = ?";

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, amManual.getMeterID());
			ps.setString(2, df.format(amManual.getValueTime()));
			ps.setInt(3, am.getAreaID());
			ps.setInt(4, am.getArchitectureID());
			ps.setFloat(5, amManual.getzValue());
			ps.setFloat(6, amManual.getzGross());
			ps.setFloat(7, amManual.getPowerFactorz());
			ps.setFloat(8, amManual.getzMoney());
			ps.setString(9, df.format(amManual.getInsertTime()));
			ps.setInt(10, amManual.getUserID());
			ps.setInt(11, amManual.getDataID());

			if (ps.executeUpdate() == 1)
				flag = true;
			else
				flag = false;
			if (ps.executeUpdate() == 1)
				flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

//		ps.setInt(1, amManual.getMeterID());
//		ps.setString(2, df.format(amManual.getValueTime()));
//		ps.setInt(3, am.getAreaID());
//		ps.setInt(4, am.getArchitectureID());
//		ps.setFloat(5, amManual.getzValue());
//		ps.setFloat(6, amManual.getzGross());
//		ps.setFloat(7, amManual.getPowerFactorz());
//		ps.setFloat(8, amManual.getzMoney());
//		ps.setString(9, df.format(amManual.getInsertTime()));
//		ps.setInt(10, amManual.getUserID());
//		ps.setInt(11, amManual.getDataID());
//
//		if (ps.executeUpdate() == 1)
//			flag = true;
//		else
//			flag = false;
//
//		if (ps != null)
//			ps.close();

		return flag;
	}

	private AmmeterManual buildInstance(ResultSet rs) throws SQLException
	{
		AmmeterManual amManual = new AmmeterManual();

		amManual.setDataID(rs.getInt("DATAID"));
		amManual.setMeterID(rs.getInt("METER_ID"));
		amManual.setMeterName(rs.getString("AmMeter_Name"));
		Timestamp valueTime = rs.getTimestamp("VALUETIME");
		amManual.setValueTime(new Date(valueTime.getTime()));
		amManual.setAreaID(rs.getInt("AREA_ID"));
		amManual.setAreaName(areaDao.queryNameById(amManual.getAreaID()));
		amManual.setArchID(rs.getInt("ARCHITECTURE_ID"));
		amManual.setArchName(archDao.queryByID(amManual.getArchID()).getName());
		amManual.setzValue(rs.getFloat("ZVALUE"));
		amManual.setzGross(rs.getFloat("ZGROSS"));
		amManual.setPowerFactorz(rs.getFloat("POWERFACTORZ"));
		amManual.setzMoney(rs.getFloat("ZMONEY"));
		Timestamp insertTime = rs.getTimestamp("INSERTTIME");
		amManual.setInsertTime(new Date(insertTime.getTime()));
		amManual.setUserID(rs.getInt("USERS_ID"));
		amManual.setUserName(rs.getString("Users_Name"));

		return amManual;
	}
}
