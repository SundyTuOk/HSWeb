package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sf.energy.project.equipment.model.GatherToDataSiteModel;
import com.sf.energy.util.ConnDB;

public class GatherToDataSiteDao
{
	
	public GatherToDataSiteModel queryByGatherAddress(String gatherAddress) throws SQLException
	{
		GatherToDataSiteModel gtdm = new GatherToDataSiteModel();
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from v_gathertodatasite where gather_address = '"
						+ gatherAddress + "'";
			
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = null;
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				gtdm.setDataSiteID(rs.getInt(1));
				gtdm.setDataSiteIP(rs.getString(2));
				gtdm.setGatherAddress(rs.getString(3));
				gtdm.setDataSiteState(rs.getInt(4));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return gtdm;
	}

}
