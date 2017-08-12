package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.util.ConnDB;

public class AmStandByModelDao
{
	private String AmmeterID;
	private String startTime = "00:00";
	private String endTime = "24:00";
	private String lowLimit = "0";
	private String upperLimit = "0";
	private String lastCheckTime;
	private String isCheck = "-1";

	boolean update = false;

	/**
	 * 添加待机功耗模型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean addModel()
	{
		String strSql = "select * from AmStandByModel";
		ResultSet rs = null;
		String meterID = null;
		boolean resultInfo = false;
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery(strSql);

			while (rs.next())
			{
				meterID = rs.getString("AmMeter_ID");
				if (AmmeterID.equals(meterID))
				{
					resultInfo = updateMode(AmmeterID);
					update = true;
					break;
				}
			}

			if (!update)
				resultInfo = insertModel();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return resultInfo;
	}

	/**
	 * 插入AmStandByModel表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean insertModel()
	{
		String strSql = "insert into AmStandByModel (AMMETER_ID,STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT) values (" + AmmeterID + ",'" + startTime
				+ "','" + endTime + "'," + lowLimit + "," + upperLimit + ")";
		Connection conn = null;
		PreparedStatement ps = null;
		int resultInfo = 0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			resultInfo = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		String sql = "update AMMETER set STANDBYCHECK=" + isCheck + " where AMMETER_ID=" + AmmeterID;

		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			ps2.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}
		if (resultInfo != 0)
		{
			return true;
		} else
		{
			return false;
		}

	}

	/**
	 * 根据电表表示更新AmStandByModel表
	 * 
	 * @param meterID
	 *            电表标示
	 * @return
	 * @throws SQLException
	 */
	public boolean updateMode(String meterID) throws SQLException
	{
		String strSql = "update AmStandByModel set startTime='" + startTime + "',endTime='" + endTime + "',lowLimit=" + lowLimit + ",upperLimit="
				+ upperLimit + " where AMMETER_ID=" + meterID;
		Connection conn = null;
		PreparedStatement ps = null;
		int resultInfo = 0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			resultInfo = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		String sql = "update AMMETER set STANDBYCHECK=" + isCheck + " where AMMETER_ID=" + meterID;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql);
			ps2.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn2, ps2);
		}

		update = false;
		if (resultInfo != 0)
		{
			return true;
		} else
		{
			return false;
		}

	}

	/**
	 * 根据电表表示删除AmStandByModel表的记录
	 * 
	 * @param meterID
	 *            电表标示
	 * @throws SQLException
	 */
	public void deleteModel(String meterID) throws SQLException
	{
		String strSql = "delete from AmStandByModel where AMMETER_ID=" + meterID;
		ConnDB.executeUpdate(strSql);
		// ConnDB.close();
	}

	/**
	 * 获得某电表的model信息
	 * 
	 * @param meterID
	 * @return 电表的模型信息
	 * @throws SQLException
	 */
	public AmStandByModelDao getModelInfoByID(String meterID)
	{
		String strSql = "select STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT,(select STANDBYCHECK from ammeter where AMMETER_ID = " + meterID
				+ ") as STANDBYCHECK from AmStandByModel where AMMETER_ID=" + meterID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AmStandByModelDao modelByID = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				modelByID = new AmStandByModelDao();
				modelByID.setUpperLimit(rs.getString("UPPERLIMIT"));
				modelByID.setLowLimit(rs.getString("LOWLIMIT"));
				modelByID.setStartTime(rs.getString("STARTTIME"));
				modelByID.setEndTime(rs.getString("ENDTIME"));
				modelByID.setIsCheck(rs.getString("STANDBYCHECK"));
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}

		return modelByID;
	}

	public String getAmmeterID()
	{
		return AmmeterID;
	}

	public void setAmmeterID(String ammeterID)
	{
		AmmeterID = ammeterID;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime.trim();
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime.trim();
	}

	public String getLowLimit()
	{
		return lowLimit;
	}

	public void setLowLimit(String lowLimit)
	{
		this.lowLimit = lowLimit;
	}

	public String getUpperLimit()
	{
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit)
	{
		this.upperLimit = upperLimit;
	}

	public String getLastCheckTime()
	{
		return lastCheckTime;
	}

	public void setLastCheckTime(String lastCheckTime)
	{
		this.lastCheckTime = lastCheckTime;
	}

	public String getIsCheck()
	{
		return isCheck;
	}

	public void setIsCheck(String isCheck)
	{
		this.isCheck = isCheck;
	}

}
