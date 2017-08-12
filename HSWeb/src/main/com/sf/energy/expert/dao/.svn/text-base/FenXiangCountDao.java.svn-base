package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;

public class FenXiangCountDao
{
	ElecReportHelper erh = new ElecReportHelperImpl();
	WaterReportHelper wrh = new WaterReportHelperImpl();

	public List<Map<String, Float>> getElecGroupFenLeiCountByYear(int groupID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(erh.getGroupFenLeiCountByMonth(groupID, queryYear, i));
		}

		return list;
	}

	public List<String> getElecFenLeiTitle() throws SQLException
	{
		List<String> list = new LinkedList<String>();

		String sql = "select DictionaryValue.DictionaryValue_value fenleiValue "
				+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, 7);

			rs = ps.executeQuery();

			while (rs.next())
			{
				list.add(rs.getString("fenleiValue"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, 7);
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			list.add(rs.getString("fenleiValue"));
//		}

		return list;
	}

	public List<Map<String, Float>> getElecArchFenLeiCountByYear(int groupID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(erh.getArcFenLeiCountByMonth(groupID, queryYear, i));
		}

		return list;
	}
	
	public List<Map<String, Float>> getElecAreaFenLeiCountByYear(int AreaID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(erh.getAreaFenLeiCountByMonth(AreaID, queryYear, i));
		}

		return list;
	}
	
	public List<Map<String, Float>> getElecSchoolFenLeiCountByYear(
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(erh.getAllschoolFenLeiCountByMonth(queryYear, i));
		}

		return list;
	}

	public List<Map<String, Float>> getWaterGroupFenLeiCountByYear(int groupID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(wrh.getGroupFenLeiCountByMonth(groupID, queryYear, i));
		}

		return list;
	}

	public List<String> getWaterFenLeiTitle() throws SQLException
	{
		List<String> list = new LinkedList<String>();

		String sql = "select DictionaryValue.DictionaryValue_value fenleiValue "
				+ "from DictionaryValue where DictionaryValue.Dictionary_ID=?";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, 25);

			rs = ps.executeQuery();

			while (rs.next())
			{
				list.add(rs.getString("fenleiValue"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setInt(1, 25);
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			list.add(rs.getString("fenleiValue"));
//		}
		return list;
	}

	public List<Map<String, Float>> getWaterArchFenLeiCountByYear(int groupID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(wrh.getArcFenLeiCountByMonth(groupID, queryYear, i));
		}

		return list;
	}
	
	public List<Map<String, Float>> getWaterAreaFenLeiCountByYear(int AreaID,
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(wrh.getAreaFenLeiCountByMonth(AreaID, queryYear, i));
		}

		return list;
	}
	
	public List<Map<String, Float>> getWaterSchoolFenLeiCountByYear(
			int queryYear) throws SQLException
	{
		List<Map<String, Float>> list = new LinkedList<Map<String, Float>>();

		for (int i = 1; i <= 12; i++)
		{
			list.add(wrh.getAllSchoolFenLeiCountByMonth(queryYear, i));
		}

		return list;
	}
}
