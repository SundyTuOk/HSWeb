package com.sf.energy.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.right.model.RolesModel;
import com.sf.energy.report.model.SystemInfoModel;
import com.sf.energy.util.ConnDB;

public class SystemInfoDao {
	SystemInfoModel systemInfoModel=null;
	
	public SystemInfoModel querySystemInfo(int id) throws SQLException
	{
		String sql="select SystemInfo_ComplayName,SystemInfo_ComplayNum,SystemInfo_ComplayShot,SystemInfo_XZNum,EnReportServer,EnReportPort from SystemInfo where systemInfo_id="+id;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				systemInfoModel = new 	SystemInfoModel();
				systemInfoModel.setSystemInfo_complayname(rs.getString(1));	
				systemInfoModel.setSystemInfo_complaynum(rs.getString(2));
				systemInfoModel.setSystemInfo_complayshot(rs.getString(3));
				systemInfoModel.setSystemInfo_xznum(rs.getString(4));
				systemInfoModel.setEnreportserver(rs.getString(5));
				systemInfoModel.setEnreportport(rs.getInt(6));
				////System.out.println(systemInfoModel.getEnreportport());
				
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			systemInfoModel = new 	SystemInfoModel();
			systemInfoModel.setSystemInfo_complayname(rs.getString(1));	
			systemInfoModel.setSystemInfo_complaynum(rs.getString(2));
			systemInfoModel.setSystemInfo_complayshot(rs.getString(3));
			systemInfoModel.setSystemInfo_xznum(rs.getString(4));
			systemInfoModel.setEnreportserver(rs.getString(5));
			systemInfoModel.setEnreportport(rs.getInt(6));
			////System.out.println(systemInfoModel.getEnreportport());
			
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return systemInfoModel;
	}
	public SystemInfoModel querySystemInfo2(int id) throws SQLException
	{
		String sql="select SystemInfo_ComplayName,SystemInfo_ComplayNum,SystemInfo_ComplayShot,SystemInfo_XZNum,SYSTEMINFO_LO,COMPLAYINTRODUCTION from SystemInfo where systemInfo_id="+id;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				systemInfoModel = new 	SystemInfoModel();
				systemInfoModel.setSystemInfo_complayname(rs.getString(1));	
				systemInfoModel.setSystemInfo_complaynum(rs.getString(2));
				systemInfoModel.setSystemInfo_complayshot(rs.getString(3));
				systemInfoModel.setSystemInfo_xznum(rs.getString(4));
				systemInfoModel.setSystemInfo_lo(rs.getInt(5));
				systemInfoModel.setComplayintroduction(rs.getString(6));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*
		//PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			systemInfoModel = new 	SystemInfoModel();
			systemInfoModel.setSystemInfo_complayname(rs.getString(1));	
			systemInfoModel.setSystemInfo_complaynum(rs.getString(2));
			systemInfoModel.setSystemInfo_complayshot(rs.getString(3));
			systemInfoModel.setSystemInfo_xznum(rs.getString(4));
			systemInfoModel.setSystemInfo_lo(rs.getInt(5));
			systemInfoModel.setComplayintroduction(rs.getString(6));
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return systemInfoModel;
	}
	
	public boolean updateSystemInfo(int id,SystemInfoModel systemInfoModel) throws SQLException{
		String sql="update SystemInfo set SystemInfo_ComplayName='" + systemInfoModel.getSystemInfo_complayname()
				+ "',SystemInfo_ComplayNum='" +systemInfoModel.getSystemInfo_complaynum() + "',SystemInfo_ComplayShot='" 
				+systemInfoModel.getSystemInfo_complayshot() + "', SystemInfo_XZNum='" +systemInfoModel.getSystemInfo_xznum() 
				+ "',EnReportServer='" + systemInfoModel.getEnreportserver() + "',EnReportPort=" +systemInfoModel.getEnreportport()
				+"where systemInfo_id="+id;
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		boolean b=(ps.executeUpdate()== 1);
		if(ps!=null){
			ps.close();
		}*/
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	
	
	}
	
	public boolean updateSystemInfo2(int id, SystemInfoModel systemInfoModel) throws SQLException
	{
		String sql="update SystemInfo set SystemInfo_ComplayName='" + systemInfoModel.getSystemInfo_complayname()
				+ "',SystemInfo_ComplayNum='" +systemInfoModel.getSystemInfo_complaynum() + "',SystemInfo_ComplayShot='" 
				+systemInfoModel.getSystemInfo_complayshot() + "', SystemInfo_XZNum='" +systemInfoModel.getSystemInfo_xznum() 
				+ "',SYSTEMINFO_LO=" + systemInfoModel.getSystemInfo_lo() + ",COMPLAYINTRODUCTION='" +systemInfoModel.getComplayintroduction()
				+"' where systemInfo_id="+id;
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		boolean b=(ps.executeUpdate()== 1);
		if(ps!=null){
			ps.close();
		}*/
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}
	/**
	 * 查询出启用了手工录入的能源
	 * @return 能源编码的id集合
	 * @throws SQLException 
	 */

	public  ArrayList<Integer> queryStartManualEnergy() throws SQLException
	{
		String EnergyString="";
		String[] EnergyArray=null;
		ArrayList<Integer> energyList =new ArrayList<Integer>(); 
		String sql="Select MANUALENERGYNUM From SystemInfo ";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				EnergyString=rs.getString("MANUALENERGYNUM");
				EnergyArray=EnergyString.split(",");
			}
			for(int i=0;i<EnergyArray.length;i++)
			{
				energyList.add(Integer.parseInt(EnergyArray[i]));
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			EnergyString=rs.getString("MANUALENERGYNUM");
			EnergyArray=EnergyString.split(",");
		}
		for(int i=0;i<EnergyArray.length;i++)
		{
			energyList.add(Integer.parseInt(EnergyArray[i]));
		}
		if(rs != null)
			rs.close();
		if(ps != null)
			ps.close();*/
		return energyList;
	}

	
}
