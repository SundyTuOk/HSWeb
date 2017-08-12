package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;

public class ArchAmStandByModelDao
{
	private String AmmeterID;
	private String startTime = "00:00";
	private String endTime = "24:00";
	private String lowLimit = "0";
	private String upperLimit = "0";
	private String lastCheckTime;
	private String isCheck = "-1";
	private int isTomorrow = 0;
	private int isVoice = 0;
	boolean update = false;

	
	
	public List<ArchAmStandByModelDao> getAllModel(int isVoice)
	{
		List<ArchAmStandByModelDao> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select b.AmMeter_ID,StartTime,EndTime,LowLimit,UpperLimit,isTomorrow,isVoice,LastCheckTime from (ArchAmStandbyModel)b where  isVoice="+isVoice;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if(list==null){
					list = new ArrayList<ArchAmStandByModelDao>();
				}
				ArchAmStandByModelDao modelByID = new ArchAmStandByModelDao();
				modelByID.setAmmeterID(rs.getString("AmMeter_ID"));
				modelByID.setUpperLimit(rs.getString("UPPERLIMIT"));
				modelByID.setLowLimit(rs.getString("LOWLIMIT"));
				modelByID.setStartTime(rs.getString("STARTTIME"));
				modelByID.setEndTime(rs.getString("ENDTIME"));
				modelByID.setIsTomorrow(rs.getInt("ISTOMORROW"));
				modelByID.setIsVoice(rs.getInt("ISVOICE"));
				list.add(modelByID);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	/**
	 * 添加待机功耗模型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean addModel()
	{
		String strSql = "select * from ArchAmStandbyModel";
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
		String strSql = "insert into ArchAmStandbyModel (AMMETER_ID,STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT,ISTOMORROW,ISVOICE) values (" + AmmeterID + ",'" + startTime
				+ "','" + endTime + "'," + lowLimit + "," + upperLimit + ","+isTomorrow+","+isVoice+")";
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
		String strSql = "update ArchAmStandbyModel set startTime='" + startTime + "',endTime='" + endTime + "',lowLimit=" + lowLimit + ",upperLimit="
				+ upperLimit + ",isTomorrow=" + isTomorrow + ",isVoice=" + isVoice + " where AMMETER_ID=" + meterID;
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
		String strSql = "delete from ArchAmStandbyModel where AMMETER_ID=" + meterID;
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
	public ArchAmStandByModelDao getModelInfoByID(String meterID)
	{
		String strSql = "select STARTTIME,ENDTIME,LOWLIMIT,UPPERLIMIT,ISTOMORROW,ISVOICE from ArchAmStandbyModel where AMMETER_ID=" + meterID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArchAmStandByModelDao modelByID = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				modelByID = new ArchAmStandByModelDao();
				modelByID.setUpperLimit(rs.getString("UPPERLIMIT"));
				modelByID.setLowLimit(rs.getString("LOWLIMIT"));
				modelByID.setStartTime(rs.getString("STARTTIME"));
				modelByID.setEndTime(rs.getString("ENDTIME"));
				modelByID.setIsTomorrow(rs.getInt("ISTOMORROW"));
				modelByID.setIsVoice(rs.getInt("ISVOICE"));
				modelByID.setIsCheck(rs.getString("ISVOICE"));
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

	public int getIsTomorrow()
	{
		return isTomorrow;
	}

	public void setIsTomorrow(int isTomorrow)
	{
		this.isTomorrow = isTomorrow;
	}

	public int getIsVoice()
	{
		return isVoice;
	}

	public void setIsVoice(int isVoice)
	{
		this.isVoice = isVoice;
	}
}
