package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sf.configuration.Configuration;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class WaterDataMaintenanceDao
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	WaterReportHelper rh = new WaterReportHelperImpl();

	public boolean updateWatermeterData(WaterMeterMataData wmd)
	{
	
		String sql = "UPDATE ZWATERDATAS"+String.valueOf(wmd.getWaterMeterID())+" SET ZValueZY = ? "
				+ "WHERE ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";

		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setFloat(1, wmd.getValue());
			//ps.setInt(2, wmd.getWaterMeterID());
			ps.setString(2, df.format(wmd.getRecordTimeDate()));
			flag = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		/*
		try
		{
			PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);

			ps.setFloat(1, wmd.getValue());
			//ps.setInt(2, wmd.getWaterMeterID());
			ps.setString(2, df.format(wmd.getRecordTimeDate()));

			ps.executeUpdate();
			flag = true;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
*/
		return flag;
	}

	public List<WaterMeterMataData> getWatermeterDataBetween(int watermeterID,
			Date start, Date end) throws SQLException
	{
		List<WaterMeterMataData> list = null;

		list = rh.getOriginalWatermeterDataBetween(watermeterID, start, end);

		return list;
	}
}
