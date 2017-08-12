package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.system.model.Area;
import com.sf.energy.util.ConnDB;

/**
 * 对区域进行增删改查操作类
 * 
 * @author 李涵淼
 * @version 1.0
 * 
 * @since version 1.0
 * 
 */
public class AreaDao
{

    // ConnDB db = null;

    /**
     * 定义list为ArrayList，对象为区域对象
     * 
     */
    public AreaDao()
    {

    }

    public Area getFirstArea() throws SQLException
    {
        Area area = null;

        String sql = "select * from Area where RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
        try
		{
    		conn = ConnDB.getConnection();
    		ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			area = new Area();
			if (rs.next())
			{
			    area.setId(rs.getInt("Area_ID"));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));
			    return area;
			}
			else
			{
			    area.setName("自动添加的区域");
			    area.setRemark("这是区域表数据为空的时候，自动添加的一条记录");

			    add(area);

			    return getFirstArea();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			ConnDB.release(conn, ps, rs);
		}

    }

    public Map<Integer, Area> getAllArea() throws SQLException
    {
        Map<Integer, Area> resultMap = null;
        String sql = "SELECT * FROM AREA ORDER BY AREA_ID";
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
			    if (resultMap == null)
			        resultMap = new HashMap<Integer, Area>();

			    Area area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));

			    resultMap.put(area.getId(), area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return resultMap;
    }

    /**
     * 查询所有重点用户电表所在的区域
     * 
     * @return
     * @throws SQLException
     */
    public List<Area> getImportantArea() throws SQLException
    {
        Area area = null;
        List<Area> list = new ArrayList<Area>();

        String sql = "select  distinct Area_name,Ammeter.Area_ID from Ammeter,Area "
                + "where isimportantuser = 1 and Ammeter.Area_ID = Area.Area_ID";

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
			    area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setName(rs.getString("Area_name"));
			    list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }

    /**
     * 查询所有水表所在的区域
     * 
     * @return
     * @throws SQLException
     */
    public List<Area> getWaterImportantArea() throws SQLException
    {
        Area area = null;
        List<Area> list = new ArrayList<Area>();

        String sql = "select  distinct Area_name,Watermeter.Area_ID from Watermeter,Area "
                + "where  Watermeter.Area_ID = Area.Area_ID";

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
			    area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setName(rs.getString("Area_name"));
			    list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }

    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * 
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public List<Area> display() throws SQLException
    {
        Area area = null;
        List<Area> list = new ArrayList<Area>();
        String sql = "SELECT * FROM Area  order by Area_Name";
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
			    area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));
			    list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }
    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * 
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public  List<Integer>  CountMeterArea() throws SQLException
    {
        Area area = null;
        List<Integer> list = new ArrayList<Integer>();
        String sql = "select distinct(a.area_ID) area_ID from area a,architecture b,ammeter c where A.AREA_ID=B.AREA_ID and B.ARCHITECTURE_ID=c.architecture_ID and C.ISCOUNTMETER=1 ";
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
			    list.add(rs.getInt("Area_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }
    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * 
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public  List<Integer>  CountMeterArch(int area_ID) throws SQLException
    {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "select distinct(b.architecture_ID) architecture_ID from area a,architecture b,ammeter c where A.AREA_ID=B.AREA_ID and B.ARCHITECTURE_ID=c.architecture_ID and C.ISCOUNTMETER=1 and a.area_ID="+area_ID;
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
			    list.add(rs.getInt("architecture_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }
    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * 
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public  List<Integer>  CountMeter(int arch_ID) throws SQLException
    {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "select distinct(c.ammeter_ID) from area a,architecture b,ammeter c where A.AREA_ID=B.AREA_ID and B.ARCHITECTURE_ID=c.architecture_ID and C.ISCOUNTMETER=1 and b.architecture_ID="+arch_ID;
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
			    list.add(rs.getInt("ammeter_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }
    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * 
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public List<Area> display(int userID) throws SQLException
    {
        Area area = null;
        List<Area> list = new ArrayList<Area>();
        String sql = "SELECT * FROM Area where Area_ID in (select Area_ID from OprArea_List where Users_ID= "
                + userID + ") order by Area_ID";
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
			    area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));
			    list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }

    /**
     * 传入sql语句，对数据库进行操作，查出所有区域对象，存入List中，并返回
     * 
     * @param sql
     *            查询区域sql语句
     * @return list 对象为区域的List
     * @throws SQLException
     */
    public List<Area> display(String sql) throws SQLException
    {
        Area area = null;
        List<Area> list = new ArrayList<Area>();
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
			    area = new Area();
			    area.setId(rs.getInt("Area_ID"));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));
			    list.add(area);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return list;
    }

    /**
     * 对数据库进行操作，查出所有区域对象
     * 
     * @param id
     * 
     * @return Area
     * @throws SQLException
     * @throws NumberFormatException
     */
    public Area query(int id) throws NumberFormatException, SQLException
    {
        String sql = "SELECT * FROM AREA WHERE AREA_ID=" + id;
        Area area = null;

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
			    area = new Area();
			    area.setId(rs.getInt(1));
			    area.setNum(rs.getString("Area_Num"));
			    area.setName(rs.getString("Area_Name"));
			    area.setAddress(rs.getString("Area_Address"));
			    area.setPhone(rs.getString("Area_Phone"));
			    area.setRemark(rs.getString("Area_Remark"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
        return area;
    }

    /**
     * 通过建筑ID查询出区域ID
     * 
     * @param archID
     * 
     * @return int
     * @throws SQLException
     * @throws NumberFormatException
     */
    public int queryByArchID(int archID) throws NumberFormatException,
            SQLException
    {
        String sql = "SELECT Area_ID FROM Architecture WHERE Architecture_ID="
                + archID;
        int areaID = 0;
        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
			    areaID = rs.getInt("Area_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return areaID;
    }

    /**
     * 通过传入一个区域对象，对数据库进行操作，将此区域信息插入数据库中
     * 
     * @param area
     *            区域对象
     * @return boolean 返回布尔变量表示操作成功与否
     * @throws SQLException
     */
    public boolean add(Area area) throws SQLException
    {
        String sql = "insert into area(Area_Num,Area_Name,Area_Address,Area_Phone,Area_Remark)values('"
                + area.getNum()
                + "','"
                + area.getName()
                + "','"
                + area.getAddress()
                + "','"
                + area.getPhone()
                + "','"
                + area.getRemark() + "')";
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

        // if (ps != null)
        // ps.close();

        return b;
    }

    /**
     * 通过传入一个区域对象，对数据库进行操作，更新数据库中数据
     * 
     * @param area
     *            区域对象
     * @return boolean 返回布尔变量表示操作成功与否
     * @throws SQLException
     */
    public boolean update(Area area) throws SQLException
    {

        String sql = "update area set Area_Num='" + area.getNum()
                + "' ,Area_Name='" + area.getName() + "',Area_Address='"
                + area.getAddress() + "',Area_Phone='" + area.getPhone()
                + "',Area_Remark='" + area.getRemark() + "' where Area_ID="
                + area.getId();
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

        // if (ps != null)
        // ps.close();
        return b;
    }

    /**
     * 通过传入区域的id,对数据进行操作，删除此id对应区域
     * 
     * @param id
     *            区域对象的ID
     * 
     * @return boolean 返回布尔变量表示操作成功与否
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException
    {
        String sql = "delete from area where Area_ID=" + id;

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

        // if (ps != null)
        // ps.close();
        return b;
    }

    /**
     * 通过区域id查询区域名称
     * 
     * @param id
     *            区域名称
     * @return 区域名称
     * @throws SQLException
     */
    public String queryNameById(int id) throws SQLException
    {
        String areaName = null;
        String sql = "SELECT AREA_NAME FROM AREA WHERE AREA_ID=" + id;
//        List<OracleDataSet> list = ConnDB.execQuery(sql);
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
//        if (list.size() == 1)
			    areaName =rs.getString("Area_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return areaName;
    }

    /**
     * 通过区域名称查询区域id
     * 
     * @param id
     *            区域名称
     * @return 区域名称
     * @throws SQLException
     */
    public int queryIDByName(String name) throws SQLException
    {
        int areaID = 0;
        String sql = "SELECT AREA_ID FROM AREA WHERE AREA_NAME='" + name+"'";
//        List<OracleDataSet> list = ConnDB.execQuery(sql);
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
//        if (list.size() == 1)
			    areaID =rs.getInt("Area_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return areaID;
    }
    public boolean hasAmmeter(int theAreaID) throws SQLException
    {
        boolean flag = false;

        String sql = "select Ammeter_ID,Area_ID from Ammeter where Area_ID = ? and RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theAreaID);

			rs = ps.executeQuery();

			if (rs.next())
			{
			    flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return flag;
    }

    public boolean hasWaterMeter(int theAreaID) throws SQLException
    {
        boolean flag = false;

        String sql = "select WaterMeter_ID,Area_ID from WaterMeter where Area_ID = ? and RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theAreaID);

			rs = ps.executeQuery();

			if (rs.next())
			{
			    flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return flag;
    }

    public boolean hasGather(int theAreaID) throws SQLException
    {
        boolean flag = false;

        String sql = "select Gather_ID,Area_ID from Gather where Area_ID = ? and RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theAreaID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return flag;
    }

    public boolean hasAddRepeat(Area area) throws SQLException
    {
        boolean flag = false;
        String sql = "select Area_ID from Area where "
                + "(Area_Num = ? or Area_Name = ?) and RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, area.getNum());
			ps.setString(2, area.getName());
			rs = ps.executeQuery();

			if (rs.next())
			{
			    flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return flag;
    }

    public boolean hasUpdateRepeat(Area area) throws SQLException
    {
        boolean flag = false;
        String sql = "select Area_ID from Area where "
                + "(Area_Num = ? or Area_Name = ?) and Area_ID !=? and RowNum = 1";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, area.getNum());
			ps.setString(2, area.getName());
			ps.setInt(3, area.getId());
			rs = ps.executeQuery();
			if (rs.next())
			{
			    flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return flag;
    }
}
