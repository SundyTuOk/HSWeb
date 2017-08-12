package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.system.model.InformationModel;
import com.sf.energy.util.ConnDB;

/**
 * 数据表Information的操作
 * 
 * @author yanhao
 * 
 */
public class InformationDao
{
	/**
	 * 通过时间查询Information的数据用于【能源预测】模块
	 * 
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	public InformationModel queryByTime(int timeYear) throws SQLException
	{
		InformationModel infoModel = new InformationModel();
		String sql = "Select JZArea ,ManCount from Information where TheTime='"
				+ timeYear + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				infoModel.setArchArea(rs.getFloat("JZArea"));
				infoModel.setCountMan(rs.getInt("ManCount"));
				infoModel.setTheTime(timeYear + "");
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next())
//		{
//			infoModel.setArchArea(rs.getFloat("JZArea"));
//			infoModel.setCountMan(rs.getInt("ManCount"));
//			infoModel.setTheTime(timeYear + "");
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return infoModel;
	}

	public List<InformationModel> getInformationInfo() throws SQLException
	{
		List<InformationModel> list = new ArrayList<InformationModel>();
		InformationModel infoModel = null;
		String sql = "select INFORMATION_ID,TDAREA,JZAREA,LHAREA,BKMANCOUNT,YJMANCOUNT,BSMANCOUNT,MANCOUNT,THETIME from INFORMATION order by INFORMATION_ID desc";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				infoModel = new InformationModel();
				infoModel.setInfomation_id(rs.getInt("INFORMATION_ID"));
				infoModel.setTdarea(rs.getFloat("TDAREA"));
				infoModel.setArchArea(rs.getFloat("JZArea"));
				infoModel.setLharea(rs.getFloat("LHAREA"));
				infoModel.setBkMancount(rs.getInt("BKMANCOUNT"));
				infoModel.setYjMancount(rs.getInt("YJMANCOUNT"));
				infoModel.setBsMancount(rs.getInt("BSMANCOUNT"));
				infoModel.setCountMan(rs.getInt("ManCount"));
				infoModel.setTheTime(rs.getString("THETIME"));
				list.add(infoModel);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			infoModel = new InformationModel();
//			infoModel.setInfomation_id(rs.getInt("INFORMATION_ID"));
//			infoModel.setTdarea(rs.getFloat("TDAREA"));
//			infoModel.setArchArea(rs.getFloat("JZArea"));
//			infoModel.setLharea(rs.getFloat("LHAREA"));
//			infoModel.setBkMancount(rs.getInt("BKMANCOUNT"));
//			infoModel.setYjMancount(rs.getInt("YJMANCOUNT"));
//			infoModel.setBsMancount(rs.getInt("BSMANCOUNT"));
//			infoModel.setCountMan(rs.getInt("ManCount"));
//			infoModel.setTheTime(rs.getString("THETIME"));
//			list.add(infoModel);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}

	public boolean delInformationInfo(String id) throws SQLException
	{
		String sql = "delete from INFORMATION where INFORMATION_ID=" + id;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		boolean b = (ps.executeUpdate() == 1);
//		if (ps != null)
//		{
//			ps.close();
//		}
		return b;
	}

	public boolean updateInformationInfo(InformationModel infoModel) throws SQLException
	{
		String sql = "update INFORMATION set TDAREA=" + infoModel.getTdarea()
				+ ",JZAREA=" + infoModel.getArchArea() + ",LHAREA="
				+ infoModel.getLharea() + ",BKMANCOUNT="
				+ infoModel.getBkMancount() + ",YJMANCOUNT="
				+ infoModel.getYjMancount() + ",BSMANCOUNT="
				+ infoModel.getBsMancount() + ",MANCOUNT="
				+ infoModel.getCountMan() + " where INFORMATION_ID="
				+ infoModel.getInfomation_id();
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		boolean b = (ps.executeUpdate() == 1);
//		if (ps != null)
//		{
//			ps.close();
//		}
		return b;

	}

	public boolean insertInformationInfo(InformationModel infoModel) throws SQLException
	{
		String sql="insert into INFORMATION (TDAREA,JZAREA,LHAREA,BKMANCOUNT,YJMANCOUNT,BSMANCOUNT,MANCOUNT,THETIME) " +
				"values("+infoModel.getTdarea()+","+infoModel.getArchArea()+","+infoModel.getLharea()+","+infoModel.getBkMancount()+"" +
						","+infoModel.getYjMancount() +","+infoModel.getBsMancount()+","+infoModel.getCountMan()+","+infoModel.getTheTime()+")";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		boolean b = (ps.executeUpdate() == 1);
//		if (ps != null)
//		{
//			ps.close();
//		}
		return b;
	}

}
