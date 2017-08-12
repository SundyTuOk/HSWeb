package com.sf.energy.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.report.model.EnReportModel;
import com.sf.energy.util.ConnDB;

public class EnReportDao
{

	// 得到总记录
	private int totalCount = 0;

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * 根据id查询能耗信息
	 * 
	 * @param id
	 *            要查询能耗信息的编号
	 * @return 能耗的Model
	 * @throws SQLException
	 */
	public List<EnReportModel> queryEnReport(String enReportsortName, String enReportorder, int pageCount, int pageIndex) throws SQLException
	{
		EnReportModel reportModel = null;
		List<EnReportModel> list = new ArrayList<EnReportModel>();
		String sql = null;

		sql = "select EnergyNum,EnergyName,EnergyUnit,Calorific,Coefficient,isManual,isReport from  EnReport order by " + enReportsortName + " "
				+ enReportorder;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			// 得到总记录数
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

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
				reportModel = new EnReportModel();
				reportModel.setEnergynum(rs.getString(1));
				reportModel.setEnergyname(rs.getString(2));
				reportModel.setEnergyunit(rs.getString(3));
				reportModel.setCalorific(rs.getDouble(4));
				reportModel.setCoefficient(rs.getDouble(5));
				reportModel.setIsmanual(rs.getInt(6));
				reportModel.setIsreport(rs.getInt(7));
				list.add(reportModel);
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

	/**
	 * 对EnReport表进行修改
	 * 
	 * @param id
	 * @param enReportModel
	 * @return
	 * @throws SQLException
	 */
	public boolean updateEnReport(int id, EnReportModel enReportModel) throws SQLException
	{
		String sql = "update EnReport set EnergyNum='" + enReportModel.getEnergynum() + "'," + "EnergyName='" + enReportModel.getEnergyname()
				+ "',EnergyUnit='" + enReportModel.getEnergyunit() + "'," + "Calorific='" + enReportModel.getCalorific() + "',Coefficient='"
				+ enReportModel.getCoefficient() + "'," + "isManual='" + enReportModel.getIsmanual() + "',isReport='" + enReportModel.getIsreport()
				+ "'where EnergyNum='" + id + "'";


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
	 * 对enReport表的字段进行删除
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEnReport(int id) throws SQLException
	{
		String sql = "delete from EnReport where EnergyNum=" + id;

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
	 * 往数据库里面增加数据
	 * 
	 * @param enReportModel
	 * @return
	 * @throws SQLException
	 */
	public boolean addEnReport(EnReportModel enReportModel) throws SQLException
	{
		String sql = "insert into EnReport(EnergyNum,EnergyName,EnergyUnit,Calorific,Coefficient,isManual,isReport)  values('"
				+ enReportModel.getEnergynum() + "','" + enReportModel.getEnergyname() + "','" + enReportModel.getEnergyunit() + "','"
				+ enReportModel.getCalorific() + "','" + enReportModel.getCoefficient() + "'," + enReportModel.getIsmanual() + ","
				+ enReportModel.getIsreport() + ")";

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
	 * 获取插入表的ID
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getEnReportId() throws SQLException
	{
		int id = 0;
		String sql = "select max(EnergyNum) Id from EnReport";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				id = rs.getInt("Id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return id + 1;
	}

	/**
	 * 通过能耗名称查询折标系数
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public float queryCoefficientByName(String name) throws SQLException
	{
		float value = 0;
		String sql = "Select Coefficient From EnReport where EnergyName like '" + name + "'";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				value = rs.getFloat("Coefficient");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		return value;
	}

	/**
	 * 列出Enreport表中所有的行
	 * 
	 * @return 能耗的Model组成的集合
	 * @throws SQLException
	 */
	public ArrayList<EnReportModel> listEnReport() throws SQLException
	{
		EnReportModel reportModel = null;
		ArrayList<EnReportModel> energyList = new ArrayList<EnReportModel>();
		String sql = "select EnergyNum,EnergyName,EnergyUnit,Calorific,Coefficient,isManual,isReport from  EnReport";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				reportModel = new EnReportModel();
				reportModel.setEnergynum(rs.getString(1));
				reportModel.setEnergyname(rs.getString(2));
				reportModel.setEnergyunit(rs.getString(3));
				reportModel.setCalorific(rs.getDouble(4));
				reportModel.setCoefficient(rs.getDouble(5));
				reportModel.setIsmanual(rs.getInt(6));
				reportModel.setIsreport(rs.getInt(7));
				energyList.add(reportModel);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		
		return energyList;
	}

	/**
	 * 列出启用的能源
	 * 
	 * @return 能耗的Model组成的集合
	 * @throws SQLException
	 */
	public ArrayList<EnReportModel> listManualEnReport() throws SQLException
	{
		EnReportModel reportModel = null;
		ArrayList<EnReportModel> energyList = new ArrayList<EnReportModel>();
		String sql = "select EnergyNum,EnergyName,EnergyUnit,Calorific,Coefficient,isManual,isReport from  EnReport where IsManual=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				reportModel = new EnReportModel();
				reportModel.setEnergynum(rs.getString(1));
				reportModel.setEnergyname(rs.getString(2));
				reportModel.setEnergyunit(rs.getString(3));
				reportModel.setCalorific(rs.getDouble(4));
				reportModel.setCoefficient(rs.getDouble(5));
				reportModel.setIsmanual(rs.getInt(6));
				reportModel.setIsreport(rs.getInt(7));
				energyList.add(reportModel);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		
		return energyList;
	}

}
