package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sf.energy.util.ConnDB;

public class WaterStandByModelDao
{
	private String WatermeterID;
	private String startTime = "00:00";
	private String endTime = "24:00";
	private String lowLimit = "0";
	private String upperLimit = "0";
	private String lastCheckTime;
	private float zgross = 0;
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
		String strSql = "select count(*) from WaterStandByModel where Watermeter_ID="+WatermeterID;
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
			int count = 0;
			if (rs.next())
			{
				count = rs.getInt(1);
				if (count>0)
				{
					resultInfo = updateMode(WatermeterID);
					update = true;
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
	 * 插入WaterStandByModel表
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean insertModel()
	{
		String strSql = "insert into WaterStandByModel (Watermeter_ID,STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT) values (" + WatermeterID + ",'" + startTime
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
		String sql = "update Watermeter set STANDBYCHECK=" + isCheck + " where Watermeter_ID=" + WatermeterID;

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
	 * 根据电表表示更新WaterStandByModel表
	 * 
	 * @param meterID
	 *            电表标示
	 * @return
	 * @throws SQLException
	 */
	public boolean updateMode(String meterID) throws SQLException
	{
		String strSql = "update WaterStandByModel set startTime='" + startTime + "',endTime='" + endTime + "',lowLimit=" + lowLimit + ",upperLimit="
				+ upperLimit + " where Watermeter_ID=" + meterID;
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
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		String sql = "update Watermeter set STANDBYCHECK=" + isCheck + " where Watermeter_ID=" + meterID;
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
	 * 根据电表表示删除WaterStandByModel表的记录
	 * 
	 * @param meterID
	 *            电表标示
	 * @throws SQLException
	 */
	public void deleteModel(String meterID) throws SQLException
	{
		String strSql = "delete from WaterStandByModel where Watermeter_ID=" + meterID;
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
	public WaterStandByModelDao getModelInfoByID(String meterID)
	{
		String strSql = "select STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT,(select STANDBYCHECK from Watermeter where Watermeter_ID = " + meterID
				+ ") as STANDBYCHECK from WaterStandByModel where Watermeter_ID=" + meterID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		WaterStandByModelDao modelByID = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				modelByID = new WaterStandByModelDao();
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

	public String getWatermeterID()
	{
		return WatermeterID;
	}

	public void setWatermeterID(String watermeterID)
	{
		WatermeterID = watermeterID;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
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

	public boolean isUpdate()
	{
		return update;
	}

	public void setUpdate(boolean update)
	{
		this.update = update;
	}

	public float getZgross()
	{
		return zgross;
	}

	public void setZgross(float zgross)
	{
		this.zgross = zgross;
	}


}
