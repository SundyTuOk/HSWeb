package com.sf.energy.project.equipment.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.service.GatherServiceDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.util.ConnDB;

/**
 * 通过对接口的实现，进行数据网关的查找操作
 * 
 * @author 李涵淼
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 * 
 */
public class GatherServiceDaoImpl implements GatherServiceDao
{
    AreaDao areaDao = new AreaDao();
    ArchitectureDao archDao = new ArchitectureDao();
    DataSiteDao dsd = new DataSiteDao();

    /**
     * 通过数据网管的地址查找数据网关
     * 
     * @param address
     *            传入的数据网关地址
     * @return list 数据网关对象List
     * @throws SQLException
     * @throws Throwable
     * @throws Exception
     */
    public List<Gather> findGatherByAddress(String address) throws SQLException
    {
        Gather gather = null;
        List<Gather> list = new ArrayList<Gather>();
        String sql = "select * from gather where Gather_Address='" + address
                + "'";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    gather = buildInstance(rs);
			    list.add(gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    /**
     * 通过区域名查找数据网关
     * 
     * @param name
     *            传入区域名
     * @return list 区域内的数据网关对象List
     * @throws SQLException
     */
    public List<Gather> findGatherByArea(String name) throws SQLException
    {
        Gather gather = null;
        List<Gather> list = new ArrayList<Gather>();
        String sql = "select * from gather where Area_Name='" + name + "'";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    gather = buildInstance(rs);
			    list.add(gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    /**
     * 通过数据网管的名称查找数据网关
     * 
     * @param name
     *            传入数据网关的名称
     * @return list 返回数据网关对象List
     * @throws SQLException
     */
    public List<Gather> findGatherByName(String name) throws SQLException
    {
        Gather gather = null;
        List<Gather> list = new ArrayList<Gather>();
        String sql = "select * from gather where Gather_Name='" + name + "'";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    gather = buildInstance(rs);
			    list.add(gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    /**
     * 通过数据网管采集状态查找数据网关
     * 
     * @param state
     *            传入的数据网关的采集状态
     * @return list 数据网关对象List
     * @throws Throwable
     */
    public List<Gather> findGatherByState(int state) throws Throwable
    {
        Gather gather = null;
        List<Gather> list = new ArrayList<Gather>();
        String sql = "select * from gather where Gather_State='" + state + "'";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    gather = buildInstance(rs);
			    list.add(gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    /**
     * 通过数据网管的编号查找数据网关
     * 
     * @param num
     *            传入数据网关编号
     * @return list 数据网关对象List
     * @throws SQLException
     */
    public List<Gather> findGatherNum(String num) throws SQLException
    {
        Gather gather = null;
        List<Gather> list = new ArrayList<Gather>();
        String sql = "select * from gather where Gather_Num='" + num + "'";
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    gather = buildInstance(rs);
			    list.add(gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return list;
    }

    /**
     * 通过区域、状态、地址码、名称和编号查询出网关
     * 
     * @param areaID
     *            区域
     * @param state
     *            状态
     * @param gatherAddress
     *            地址码
     * @param gatherName
     *            名称
     * @param gatherNum
     *            编号
     * @return 返回对象为Gahter的集合
     * @throws SQLException
     */
    public ArrayList<Gather> complexQueryGather(int areaID, int state,
            String gatherAddress, String gatherName, String gatherNum)
            throws SQLException
    {

        ArrayList<Gather> lst = new ArrayList<Gather>();
        Gather gather = null;
        String complexItems = "SELECT * FROM Gather ";// 最后必须一个空格

        // 判断那个条件是第一个，若是第一个则带上 where 不带and
        // true为第一个,则带上where
        // 区域
        boolean firstItem = true;
        if (areaID != 0 && firstItem == true)
        {
            complexItems += " where Area_ID=" + areaID;
            firstItem = false;
        }

        // 状态
        if (state != -1)
        {
            if (firstItem == true)
            {
                complexItems += " where Gather_State=" + state;
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_State=" + state;
            }
        }

        // 地址码
        if (gatherAddress != null)
        {
            if (firstItem == true)
            {
                complexItems += "where Gather_Address='" + gatherAddress + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_Address='" + gatherAddress + "'";
            }
        }

        // 电表名称
        if (gatherName != null)
        {
            if (firstItem == true)
            {
                complexItems += " where Gather_Name='" + gatherName + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_Name='" + gatherName + "'";
            }
        }

        // 编号
        if (gatherNum != null)
        {
            if (firstItem == true)
            {
                complexItems += " where Gather_Num='" + gatherNum + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_Num='" + gatherNum + "'";
            }
        }

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(
			        complexItems);
			rs = ps.executeQuery();
			while (rs.next())
			{
			    gather = buildInstance(rs);
			    lst.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;

    }

    private Gather buildInstance(ResultSet rs) throws SQLException
    {
        Gather gather = new Gather();

        gather.setGatherID(rs.getInt("Gather_ID"));

        gather.setAreaID(rs.getInt("Area_ID"));
        gather.setAreaName(areaDao.queryNameById(gather.getAreaID()));

        gather.setArchitectureID(rs.getInt("Architecture_ID"));

        Architecture arch = archDao.queryByID(gather.getArchitectureID());
        if (arch != null)
            gather.setArchitectureName(arch.getName());

        gather.setDatasiteID(rs.getInt("DataSite_ID"));
        gather.setDatasiteName(dsd.queryNameById(gather.getDatasiteID()));

        if (rs.getInt("Gather_State") == 0)
        {
            gather.setGatherStateName("未采集");
        }
        else
        {
            gather.setGatherStateName("采集");
        }

        gather.setGatherNum(rs.getString("Gather_Num"));
        gather.setGatherName(rs.getString("Gather_Name"));
        gather.setGatherAddress(rs.getString("Gather_Address"));
        gather.setGatherPw(rs.getString("Gather_PassWord"));
        gather.setInstallAddress(rs.getString("Gather_Anzhuangaddress"));
        gather.setFactory(rs.getString("Gather_Changshang"));
        gather.setVersion(rs.getString("Gather_Banben"));
        gather.setSize(rs.getString("Gather_Size"));

        if ("GW".equalsIgnoreCase(rs.getString("Protocol")))
        {
            gather.setProtocol("国网协议");
        }

        if (rs.getInt("Sendway") == 1)
        {
            gather.setSendwayName("串口方式");
        }

        else
            if (rs.getInt("Sendway") == 2)
            {
                gather.setSendwayName("网络方式");
            }

        gather.setLastTime(rs.getString("LastTime"));
        gather.setIp(rs.getString("Ip"));
        gather.setLastSetTime(rs.getString("LastSetTime"));
        gather.setLastSetMsg(rs.getString("LastSetMsg"));
        if (rs.getInt("Gather_Style") == 0)
        {
            gather.setGatherStyleName("主动上报");
        }
        else
            if (rs.getInt("Gather_Style") == 1)
            {
                gather.setGatherStyleName("主站轮抄");
            }

        return gather;
    }
}
