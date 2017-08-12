package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.project.equipment.model.LightDeviceCtlModel;
import com.sf.energy.util.ConnDB;

public class LightDeviceCtlDao
{
    public LightDeviceCtlModel getLightDeviceCtlByRoomNo(int roomNo)
            throws SQLException
    {
        LightDeviceCtlModel ldc = null;
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from LightDeviceCtl where LightRoomNo = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roomNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    ldc = new LightDeviceCtlModel();

			    ldc.setLightRoomNo(rs.getInt("LightRoomNo"));
			    ldc.setDeviceNo(rs.getInt("DeviceNo"));
			    ldc.setGather_ID(rs.getInt("Gather_ID"));
			    ldc.setGather_PortNo(rs.getInt("Gather_PortNo"));
			    ldc.setBaudRate(rs.getInt("BaudRate"));
			    ldc.setParity(rs.getString("Parity").charAt(0));
			    ldc.setDataBit(rs.getInt("DataBit"));
			    ldc.setStopBit(rs.getInt("StopBit"));
			    ldc.setTimeOutUnit(rs.getInt("TimeOutUnit"));
			    ldc.setTimeOutTime(rs.getInt("TimeOutTime"));
			    ldc.setByteTimeOutTime(rs.getInt("ByteTimeOutTime"));
			    ldc.setSwitchMode(rs.getInt("SwitchMode"));
			    ldc.setLineSum(rs.getInt("LineSum"));
			    ldc.setCurrentLineOnSum(rs.getInt("CurrentLineOnSum"));
			    ldc.setMaxPeoplePerLine(rs.getInt("MaxPeoplePerLine"));
			    ldc.setCurrentPeopleSum(rs.getInt("CurrentPeopleSum"));
			    ldc.setSwitchOnLightValue(rs.getInt("SwitchOnLightValue"));
			    ldc.setCurrentLightValue(rs.getInt("CurrentLightValue"));
			    ldc.setStat(rs.getInt("Stat"));
			    ldc.setIsOffAlarm(rs.getInt("IsOffAlarm"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return ldc;
    }

    public LightDeviceCtlModel getLightDeviceCtlByDeviceNo(int deviceNo)
            throws SQLException
    {
        LightDeviceCtlModel ldc = null;
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from LightDeviceCtl where DeviceNo = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deviceNo);
			rs = ps.executeQuery();

			if (rs.next())
			{
			    ldc = new LightDeviceCtlModel();

			    ldc.setLightRoomNo(rs.getInt("LightRoomNo"));
			    ldc.setDeviceNo(rs.getInt("DeviceNo"));
			    ldc.setGather_ID(rs.getInt("Gather_ID"));
			    ldc.setGather_PortNo(rs.getInt("Gather_PortNo"));
			    ldc.setBaudRate(rs.getInt("BaudRate"));
			    ldc.setParity(rs.getString("Parity").charAt(0));
			    ldc.setDataBit(rs.getInt("DataBit"));
			    ldc.setStopBit(rs.getInt("StopBit"));
			    ldc.setTimeOutUnit(rs.getInt("TimeOutUnit"));
			    ldc.setTimeOutTime(rs.getInt("TimeOutTime"));
			    ldc.setByteTimeOutTime(rs.getInt("ByteTimeOutTime"));
			    ldc.setSwitchMode(rs.getInt("SwitchMode"));
			    ldc.setLineSum(rs.getInt("LineSum"));
			    ldc.setCurrentLineOnSum(rs.getInt("CurrentLineOnSum"));
			    ldc.setMaxPeoplePerLine(rs.getInt("MaxPeoplePerLine"));
			    ldc.setCurrentPeopleSum(rs.getInt("CurrentPeopleSum"));
			    ldc.setSwitchOnLightValue(rs.getInt("SwitchOnLightValue"));
			    ldc.setCurrentLightValue(rs.getInt("CurrentLightValue"));
			    ldc.setStat(rs.getInt("Stat"));
			    ldc.setIsOffAlarm(rs.getInt("IsOffAlarm"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return ldc;
    }

    public List<LightDeviceCtlModel> getAllLightDevice() throws SQLException
    {
        List<LightDeviceCtlModel> list = null;
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select LightDeviceCtl.*,LightRoom.LightRoomName from "
			        + "LightDeviceCtl,LightRoom where LightRoom.LightRoomNo = "
			        + "LightDeviceCtl.LightRoomNo order by LightDeviceCtl.DeviceNo desc";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    if (list == null)
			        list = new LinkedList<LightDeviceCtlModel>();

			    LightDeviceCtlModel ldc = new LightDeviceCtlModel();

			    ldc.setLightRoomName(rs.getString("LightRoomName"));
			    ldc.setLightRoomNo(rs.getInt("LightRoomNo"));
			    ldc.setDeviceNo(rs.getInt("DeviceNo"));
			    ldc.setGather_ID(rs.getInt("Gather_ID"));
			    ldc.setGather_PortNo(rs.getInt("Gather_PortNo"));
			    ldc.setBaudRate(rs.getInt("BaudRate"));
			    ldc.setParity(rs.getString("Parity").charAt(0));
			    ldc.setDataBit(rs.getInt("DataBit"));
			    ldc.setStopBit(rs.getInt("StopBit"));
			    ldc.setTimeOutUnit(rs.getInt("TimeOutUnit"));
			    ldc.setTimeOutTime(rs.getInt("TimeOutTime"));
			    ldc.setByteTimeOutTime(rs.getInt("ByteTimeOutTime"));
			    ldc.setSwitchMode(rs.getInt("SwitchMode"));
			    ldc.setLineSum(rs.getInt("LineSum"));
			    ldc.setCurrentLineOnSum(rs.getInt("CurrentLineOnSum"));
			    ldc.setMaxPeoplePerLine(rs.getInt("MaxPeoplePerLine"));
			    ldc.setCurrentPeopleSum(rs.getInt("CurrentPeopleSum"));
			    ldc.setSwitchOnLightValue(rs.getInt("SwitchOnLightValue"));
			    ldc.setCurrentLightValue(rs.getInt("CurrentLightValue"));
			    ldc.setStat(rs.getInt("Stat"));
			    ldc.setIsOffAlarm(rs.getInt("IsOffAlarm"));

			    list.add(ldc);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    public boolean addLightDeviceCtl(LightDeviceCtlModel ldc)
            throws SQLException
    {
        if (getLightDeviceCtlByDeviceNo(ldc.getDeviceNo()) != null)
            return false;

        boolean flag = false;
        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			String sql = "insert into LightDeviceCtl "
			        + "(LightRoomNo,Gather_ID,Gather_PortNo,"
			        + "BaudRate,Parity,DataBit,StopBit,TimeOutUnit,"
			        + "TimeOutTime,ByteTimeOutTime,SwitchMode,LineSum,"
			        + "CurrentLineOnSum,MaxPeoplePerLine,CurrentPeopleSum,"
			        + "SwitchOnLightValue,CurrentLightValue,Stat,IsOffAlarm) "
			        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, ldc.getLightRoomNo());
			ps.setInt(2, ldc.getGather_ID());
			ps.setInt(3, ldc.getGather_PortNo());
			ps.setInt(4, ldc.getBaudRate());
			ps.setString(5, ldc.getParity() + "");
			ps.setInt(6, ldc.getDataBit());
			ps.setInt(7, ldc.getStopBit());
			ps.setInt(8, ldc.getTimeOutUnit());
			ps.setInt(9, ldc.getTimeOutTime());
			ps.setInt(10, ldc.getByteTimeOutTime());
			ps.setInt(11, ldc.getSwitchMode());
			ps.setInt(12, ldc.getLineSum());
			ps.setInt(13, ldc.getCurrentLineOnSum());
			ps.setInt(14, ldc.getMaxPeoplePerLine());
			ps.setInt(15, ldc.getCurrentPeopleSum());
			ps.setInt(16, ldc.getSwitchOnLightValue());
			ps.setInt(17, ldc.getCurrentLightValue());
			ps.setInt(18, ldc.getStat());
			ps.setInt(19, ldc.getIsOffAlarm());
			
			if (ps.executeUpdate() == 1)
		            flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
        return flag;
    }

    public boolean updateLightDeviceCtl(LightDeviceCtlModel ldc)
            throws SQLException
    {
        if (getLightDeviceCtlByDeviceNo(ldc.getDeviceNo()) == null)
            return false;

        boolean flag = false;
        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			String sql = "update LightDeviceCtl set LightRoomNo = ?,"
			        + "Gather_ID = ?,Gather_PortNo = ?,BaudRate = ?,"
			        + "Parity = ?,DataBit = ?,StopBit = ?,TimeOutUnit = ?,"
			        + "TimeOutTime = ?,ByteTimeOutTime = ?,SwitchMode = ?,"
			        + "LineSum = ?,CurrentLineOnSum = ?,MaxPeoplePerLine = ?,"
			        + "CurrentPeopleSum = ?,SwitchOnLightValue = ?,"
			        + "CurrentLightValue = ?,Stat = ?,IsOffAlarm = ? "
			        + "WHERE DeviceNo = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, ldc.getLightRoomNo());
			ps.setInt(2, ldc.getGather_ID());
			ps.setInt(3, ldc.getGather_PortNo());
			ps.setInt(4, ldc.getBaudRate());
			ps.setString(5, ldc.getParity() + "");
			ps.setInt(6, ldc.getDataBit());
			ps.setInt(7, ldc.getStopBit());
			ps.setInt(8, ldc.getTimeOutUnit());
			ps.setInt(9, ldc.getTimeOutTime());
			ps.setInt(10, ldc.getByteTimeOutTime());
			ps.setInt(11, ldc.getSwitchMode());
			ps.setInt(12, ldc.getLineSum());
			ps.setInt(13, ldc.getCurrentLineOnSum());
			ps.setInt(14, ldc.getMaxPeoplePerLine());
			ps.setInt(15, ldc.getCurrentPeopleSum());
			ps.setInt(16, ldc.getSwitchOnLightValue());
			ps.setInt(17, ldc.getCurrentLightValue());
			ps.setInt(18, ldc.getStat());
			ps.setInt(19, ldc.getIsOffAlarm());
			ps.setInt(20, ldc.getDeviceNo());

			if (ps.executeUpdate() == 1)
			    flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

        return flag;
    }

    public boolean deleteLightDeviceCtl(int deviceNo) throws SQLException
    {
        boolean flag = false;
        Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			String sql = "delete from LightDeviceCtl where DeviceNo = ?";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, deviceNo);

			if (ps.executeUpdate() == 1)
			    flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

        return flag;
    }
    
    /**
	 * 通过ID查询有的教室照明当前信息
	 * @return
	 * @throws SQLException
	 */
	public LightDeviceCtlModel queryRoomInfo(int id) throws SQLException
	{
		
		LightDeviceCtlModel ldc=new LightDeviceCtlModel();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			String sql = " select ";
			sql += "    a.LightRoomNo, ";
			sql += "    a.LightRoomName, ";
			sql += "    b.Area_Name, ";
			sql += "    b.Area_ID, ";
			sql += "    c.Architecture_Name, ";
			sql += "    c.Architecture_ID, ";
			sql += "    d.SwitchMode, ";
			sql += "    d.LineSum, ";
			sql += "    d.CurrentLineOnSum, ";
			sql += "    d.MaxPeoplePerLine, ";
			sql += "    d.CurrentPeopleSum, ";
			sql += "    d.SwitchOnLightValue, ";
			sql += "    d.CurrentLightValue, ";
			sql += "    d.Stat ";
			sql += " from ";
			sql += "    lightroom a, ";
			sql += "    area b, ";
			sql += "    architecture c, ";
			sql += "    LightDeviceCtl d ";
			sql += " where ";
			sql += "        a.Area_ID = b.Area_ID ";
			sql += "    and a.Architecture_ID = c.Architecture_ID ";
			sql += "    and a.LightRoomNo = d.LightRoomNo and a.LightRoomNo="+id;

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();	
			if (rs.next())
			{ 
			    ldc.setCurrentLineOnSum(rs.getInt("CurrentLineOnSum"));//教室当前开灯路数
			    ldc.setMaxPeoplePerLine(rs.getInt("MaxPeoplePerLine"));//教室每路开灯人数
			    ldc.setCurrentPeopleSum(rs.getInt("CurrentPeopleSum"));//教室当前人数
			    ldc.setSwitchOnLightValue(rs.getInt("SwitchOnLightValue"));//教室开灯光照度
			    ldc.setCurrentLightValue(rs.getInt("CurrentLightValue"));//教室当前光照度
			    ldc.setStat(rs.getInt("Stat"));//设备状态
			    ldc.setSwitchMode(rs.getInt("SwitchMode"));//当前开关状态
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return ldc;
	}	
    
}
