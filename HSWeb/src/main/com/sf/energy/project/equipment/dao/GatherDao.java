package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.util.ConnDB;

/**
 * 对数据网关信息的操作
 * 
 * @author 李涵淼
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 * 
 */

public class GatherDao
{
	// DBProcess db = null;
	AreaDao areaDao = new AreaDao();
	ArchitectureDao archDao = new ArchitectureDao();
	DataSiteDao dsd = new DataSiteDao();
	private int totalCount = 0;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 定义list为ArrayList，对象为数据网关对象
	 * 
	 */
	public GatherDao()
	{
	}

	/**
	 * 传入sql语句，对数据库进行操作，查出所有数据网关对象，存入List中，并返回
	 * 
	 * @param sql
	 *            查询数据网关信息的sql语句
	 * @return list 对象为数据网关的List
	 * @throws SQLException
	 */
	public List<Gather> display(String sql) throws SQLException
	{
		List<Gather> list = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			list = new ArrayList<Gather>();
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Gather gather = null;
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
	 * 对数据库进行操作，查出所有数据网关对象，存入List中，并返回
	 * 
	 * @return list 对象为数据网关的List
	 * @throws SQLException
	 */
	public List<Gather> display() throws SQLException
	{
		List<Gather> list = new LinkedList<Gather>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "Select * from V_Gather";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Gather gather = null;
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
	 * 对数据库进行操作，查出所有数据网关对象，存入List中，并返回
	 * 
	 * @return list 对象为数据网关的List
	 * @throws SQLException
	 */
	public List<Gather> display(int pageCount,int pageIndex) throws SQLException
	{
		List<Gather> list = new LinkedList<Gather>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "Select * from V_Gather";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			Gather gather = null;
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0) {
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0)) {
				gather = buildInstance(rs);

				list.add(gather);
				count--;
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
	 * 对数据库进行操作，查出数据网关对象
	 * 
	 * @param id
	 *            网关id
	 * @return gather 对象为数据网
	 * @throws SQLException
	 */
	public Gather queryByID(int id) throws SQLException
	{
		Gather gather = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Gather where gather_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				gather = buildInstance(rs);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return gather;
	}

	/**
	 * 通过传入一个数据网关对象，对数据库进行操作，将此数据网关信息插入数据库中
	 * 
	 * @param gather
	 *            数据网关对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */

	public boolean add(Gather gather) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastTime=df.format(new Date());
		boolean b =false;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			String sql = "insert into gather(Area_ID,Architecture_ID,DataSite_ID,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_PassWord,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,Sendway,LastTime,Ip,LastSetTime，LastSetMsg,Gather_Style)values("

					+ gather.getAreaID()
					+ ","
					+ gather.getArchitectureID()
					+ ","
					+ gather.getDatasiteID()
					+ ","
					+ gather.getGatherState()
					+ ",'"
					+ gather.getGatherNum()
					+ "','"
					+ gather.getGatherName()
					+ "','"
					+ gather.getGatherAddress()
					+ "','"
					+ gather.getGatherPw()
					+ "','"
					+ gather.getInstallAddress()
					+ "','"
					+ gather.getFactory()
					+ "','"
					+ gather.getVersion()
					+ "','"
					+ gather.getSize()
					+ "','"
					+ gather.getProtocol()
					+ "',"
					+ gather.getSendway()
					+ ",'"
					+ lastTime
					+ "','"
					+ gather.getIp()
					+ "','"
					+ gather.getLastSetTime()
					+ "','" + gather.getLastSetMsg() + "',"

					+ gather.getGatherStyle() + ")";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public boolean add(List<Gather> list) throws SQLException
	{
		Connection conn=null;
		PreparedStatement st =null;
		boolean flag =false;
		try
		{
			conn = ConnDB.getConnection();
			// 设置事务属性
			conn.setAutoCommit(false);

			String sql = "insert into gather "
					+ "(Area_ID,Architecture_ID,DataSite_ID, "
					+ "Gather_State,Gather_Num,Gather_Name,Gather_Address, "
					+ "Gather_PassWord,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben, "
					+ "Gather_Size,Protocol,Sendway,LastTime, "
					+ "Ip,LastSetTime,LastSetMsg,Gather_Style)values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			for (Gather gather : list)
			{
				st.setInt(1, gather.getAreaID());
				st.setInt(2, gather.getArchitectureID());
				st.setInt(3, gather.getGatherID());
				st.setInt(4, gather.getGatherState());
				st.setString(5, gather.getGatherNum());
				st.setString(6, gather.getGatherName());
				st.setString(7, gather.getGatherAddress());
				st.setString(8, gather.getGatherPw());
				st.setString(9, gather.getInstallAddress());
				st.setString(10, gather.getFactory());
				st.setString(11, gather.getVersion());
				st.setString(12, gather.getSize());
				st.setString(13, gather.getProtocol());
				st.setInt(14, gather.getSendway());
				st.setString(15, gather.getLastTime());
				st.setString(16, gather.getIp());
				st.setString(17, gather.getLastSetTime());
				st.setString(18, gather.getLastSetMsg());
				st.setInt(19, gather.getGatherStyle());
				st.addBatch();
			}
			flag = true;
			int[] result = st.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			for (int n : result)
			{
				if (n <= 0)
				{
					flag = false;
					break;
				}
			}
		} catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, st);
		}
		return flag;

	}

	/**
	 * 通过传入一个数据网关对象，对数据库进行操作，更新数据库中数据
	 * 
	 * @param gather
	 *            数据网关对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean update(Gather gather) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql = "update gather set Area_ID=" + gather.getAreaID()
					+ ", Architecture_ID=" + gather.getArchitectureID()
					+ ",DataSite_ID=" + gather.getDatasiteID() + ",Gather_State="
					+ gather.getGatherState() + ",Gather_Num='"
					+ gather.getGatherNum() + "',Gather_Name='"
					+ gather.getGatherName() + "',Gather_Address='"
					+ gather.getGatherAddress() + "',Gather_PassWord='"
					+ gather.getGatherPw() + "',Gather_AnzhuangAddress='"
					+ gather.getInstallAddress() + "',Gather_Changshang='"
					+ gather.getFactory() + "',Gather_Banben='"
					+ gather.getVersion() + "',Gather_Size='" + gather.getSize()
					+ "',Protocol='" + gather.getProtocol() + "',SendWay="
					+ gather.getSendway() 
					//+ ",LastTime='" + gather.getLastTime()//李嵘20150905 不要更新这几个参数，因为界面上没有，更新只会更新为空
					//+ "',Ip='" + gather.getIp() + "',LastSetTime='"
					//+ gather.getLastSetTime() + "',LastSetMsg='"
					//+ gather.getLastSetMsg() + "'"
					+",Gather_Style="
					+ gather.getGatherStyle() + " where Gather_ID="
					+ gather.getGatherID() + "  ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;

	}

	/**
	 * 通过传入数据网关的id,对数据进行操作，删除此id对应数据网关
	 * 
	 * @param id
	 *            数据网关对象ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql = "delete from gather where Gather_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;

	}

	public boolean checkAddRepeat(Gather gather) throws SQLException
	{
		boolean flag = false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Gather_ID from Gather where "
					+ "(Gather_Num = ? or Gather_Name = ? "
					+ "or GATHER_Address = ?) and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, gather.getGatherNum());
			ps.setString(2, gather.getGatherName());
			ps.setString(3, gather.getGatherAddress());
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

	public boolean checkUpdateRepeat(Gather gather) throws SQLException
	{
		boolean flag = false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Gather_ID from Gather where "
					+ "(Gather_Num = ? or Gather_Name = ? "
					+ "or GATHER_Address = ?) and Gather_ID != ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, gather.getGatherNum());
			ps.setString(2, gather.getGatherName());
			ps.setString(3, gather.getGatherAddress());
			ps.setInt(4, gather.getGatherID());
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

	/**
	 * 通过电表ID，查询网关地址码，用于抄表
	 * 
	 * @param AmmID
	 * @return 网关地址码
	 * @throws SQLException
	 */
	public String queryGatherNumByAmmID(int AmmID) throws SQLException
	{
		String gatherNum = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Gather_Address from V_Ammeter where Ammeter_ID="
					+ AmmID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				gatherNum = rs.getString("Gather_Address");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return gatherNum;
	}

	public Map<String, Gather> getMapByAdddr() throws SQLException
	{
		Map<String, Gather> map = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_Gather";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (map == null)
				{
					map = new HashMap<String, Gather>();
				}
				Gather gather = buildInstance(rs);
				map.put(gather.getGatherAddress(), gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();
		}
		return map;
	}

	public Map<String, Gather> getMapByName() throws SQLException
	{
		Map<String, Gather> map = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select * from V_Gather";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (map == null)
				{
					map = new HashMap<String, Gather>();
				}

				Gather gather = buildInstance(rs);

				map.put(gather.getGatherName(), gather);
			}// while 循环结束
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 通过水表ID，查询网关地址码，用于抄表
	 * 
	 * @param AmmID
	 * @return 网关地址码
	 * @throws SQLException
	 */
	public String queryGatherNumByWaterAmmID(int AmmID) throws SQLException
	{
		String gatherNum = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Gather_Address from V_Watermeter where Watermeter_ID="
					+ AmmID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				gatherNum = rs.getString("Gather_Address");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return gatherNum;
	}

	public boolean hasAmmeter(int gatherID) throws SQLException
	{
		boolean flag = false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Ammeter_ID from Ammeter where Gather_ID = ? and RowNum = 1";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, gatherID);

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

	public boolean hasWaterMeter(int gatherID) throws SQLException
	{
		boolean flag = false;
		//	System.out.println(gatherID+"gatherID");
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select Watermeter_ID from WATERMETER where Gather_ID = ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, gatherID);

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

	private Gather buildInstance(ResultSet rs) throws SQLException
	{
		Gather gather = new Gather();

		gather.setGatherID(rs.getInt("Gather_ID"));

		gather.setAreaID(rs.getInt("Area_ID"));
		gather.setAreaName(rs.getString("Area_Name"));

		gather.setArchitectureID(rs.getInt("Architecture_ID"));
		gather.setArchitectureName(rs.getString("Architecture_Name"));

		gather.setDatasiteID(rs.getInt("DataSite_ID"));
		gather.setDatasiteName(dsd.queryNameById(rs.getInt("DataSite_ID")));

		gather.setGatherState(rs.getInt("Gather_State"));
		if (gather.getGatherState() == 0)
		{
			gather.setGatherStateName("未采集");
		} else
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

		gather.setProtocol(rs.getString("Protocol"));
		if ("GW".equalsIgnoreCase(gather.getProtocol()))
		{
			gather.setProtocolName("国网协议");
		}

		gather.setSendway(rs.getInt("Sendway"));
		if (gather.getSendway() == 1)
		{
			gather.setSendwayName("串口方式");
		}

		if (gather.getSendway() == 2)
		{
			gather.setSendwayName("网络方式");
		}

		gather.setLastTime(rs.getString("LastTime"));
		gather.setIp(rs.getString("Ip"));
		gather.setLastSetTime(rs.getString("LastSetTime"));
		gather.setLastSetMsg(rs.getString("LastSetMsg"));
		gather.setGatherStyle(rs.getInt("Gather_Style"));
		if (gather.getGatherStyle() == 0)
		{
			gather.setGatherStyleName("主动上报");
		}

		if (gather.getGatherStyle() == 1)
		{
			gather.setGatherStyleName("主站轮抄");
		}

		return gather;
	}

	/**
	 * 根据区域ID查询网关
	 * 
	 * @param areaID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Gather> queryGatherByAreaID(int areaID)
			throws SQLException
			{
		ArrayList<Gather> list = new ArrayList<Gather>();
		Gather gather = null;
		Connection conn=null;
		PreparedStatement sm =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Gather where AREA_ID=" + areaID;
			conn = ConnDB.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next())
			{
				gather = buildInstance(rs);
				list.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, sm, rs);
		}
		return list;
			}

	public ArrayList<Gather> queryGatherByArchID(int archID)
			throws SQLException
			{
		ArrayList<Gather> list = new ArrayList<Gather>();
		Gather gather = null;
		Connection conn=null;
		PreparedStatement sm =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Gather where ARCHITECTURE_ID=" + archID;
			conn = ConnDB.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next())
			{
				gather = buildInstance(rs);
				list.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, sm, rs);
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
	public List<Gather> search(int areaID, int state, String gatherAddress,
			String gatherName, String gatherNum, String sortLable, boolean isAsc)
					throws SQLException
					{

		ArrayList<Gather> lst = new ArrayList<Gather>();
		Gather gather = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String complexItems = "SELECT * FROM V_Gather ";// 最后必须一个空格

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
				} else
				{
					complexItems += " and Gather_State=" + state;
				}
			}

			// 地址码
			if (gatherAddress != null && gatherAddress.length() > 0)
			{
				if (firstItem == true)
				{
					complexItems += "where Gather_Address='" + gatherAddress + "'";
					firstItem = false;
				} else
				{
					complexItems += " and Gather_Address='" + gatherAddress + "'";
				}
			}
			// 电表名称
			if (gatherName != null && gatherName.length() > 0)
			{
				if (firstItem == true)
				{
					complexItems += " where Gather_Name='" + gatherName + "'";
					firstItem = false;
				} else
				{
					complexItems += " and Gather_Name='" + gatherName + "'";
				}
			}
			// 编号
			if (gatherNum != null && gatherNum.length() > 0)
			{
				if (firstItem == true)
				{
					complexItems += " where Gather_Num='" + gatherNum + "'";
					firstItem = false;
				} else
				{
					complexItems += " and Gather_Num='" + gatherNum + "'";
				}
			}
			complexItems += " order by " + sortLable;
			if (isAsc)
			{
				complexItems += " Asc";
			} else
			{
				complexItems += " Desc";
			}
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
	public List<Gather> search(int areaID,int archID, int state, String gatherAddress,
			String gatherName, String gatherNum, String sortLable, boolean isAsc,int pageCount,int pageIndex)
					throws SQLException
					{

		ArrayList<Gather> lst = new ArrayList<Gather>();
		Gather gather = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		String complexItems = "SELECT * FROM V_Gather ";// 最后必须一个空格

		// 判断那个条件是第一个，若是第一个则带上 where 不带and
		// true为第一个,则带上where
		// 区域
		boolean firstItem = true;
		if (areaID != 0 && firstItem == true)
		{
			complexItems += " where area_ID=" + areaID;  //这里areaID 实际为机构ID
			firstItem = false;
		}
		if (archID != 0)
		{
			if (firstItem == true)
			{
				complexItems += " where Architecture_ID=" + archID;
				firstItem = false;
			} else
			{
				complexItems += " and Architecture_ID=" + archID;
			}
		}
		// 状态
		if (state != -1)
		{
			if (firstItem == true)
			{
				complexItems += " where Gather_State=" + state;
				firstItem = false;
			} else
			{
				complexItems += " and Gather_State=" + state;
			}
		}

		// 地址码
		if (gatherAddress != null && gatherAddress.length() > 0)
		{
			if (firstItem == true)
			{
				complexItems += "where Gather_Address like('%" + gatherAddress + "%')";
				firstItem = false;
			} else
			{
				complexItems += " and Gather_Address like('%" + gatherAddress + "%')";
			}
		}
		// 电表名称
		if (gatherName != null && gatherName.length() > 0)
		{
			if (firstItem == true)
			{
				complexItems += " where Gather_Name like('%" + gatherName + "%')";
				firstItem = false;
			} else
			{
				complexItems += " and Gather_Name like('%" + gatherName + "%')";
			}
		}
		// 编号
		if (gatherNum != null && gatherNum.length() > 0)
		{
			if (firstItem == true)
			{
				complexItems += " where Gather_Num  like('%" + gatherNum + "%')";
				firstItem = false;
			} else
			{
				complexItems += " and Gather_Num like('%" + gatherNum + "%')";
			}
		}
		complexItems += " order by " + sortLable;
		if (isAsc)
		{
			complexItems += " Asc";
		} else
		{
			complexItems += " Desc";
		}
		String datasql = "select * from (select * from (select ROWNUM RN ,A.*from ( ";
		datasql+=complexItems;
		datasql+=" )A ) where rn<=?) where rn >=?";
		String countsql = complexItems;
		countsql= countsql.replace("*", "count(*)");
//		System.out.println(countsql);
//		System.out.println(datasql);
		try
		{
			int total = 0;
			conn = ConnDB.getConnection();
			/**
			 * 先获取记录条数
			 */
			ps = conn.prepareStatement(countsql);
			//long start = System.currentTimeMillis();
			rs = ps.executeQuery();
			
			if(rs.next()){
				total = rs.getInt(1);
			}
			//System.out.println("count time:"+(System.currentTimeMillis()-start));
			setTotalCount(total);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		try
		{
			int startCount = pageIndex*pageCount+1;
			int endCount = (pageIndex+1)*pageCount;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(datasql);
			ps.setInt(1, endCount);
			ps.setInt(2, startCount);
			//long start = System.currentTimeMillis();
			rs = ps.executeQuery();
			while (rs.next()) {
				gather = buildInstance(rs);
				lst.add(gather);
			}
			//System.out.println("query time:"+(System.currentTimeMillis()-start));
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
					}

	/**
	 * 通过电表ID查询网管信息用于抄表
	 * 
	 * @param id
	 *            网关id
	 * @return gather 对象为数据网
	 * @throws SQLException
	 */
	public Gather getGatherXMLInfo(int id) throws SQLException
	{
		Gather gather =null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "select a.Gather_ID,AmMeter_485Address,Gather_Address,Gather_Password,SendWay,Protocol,datasite_id from Gather  a,AmMeter  b where a.Gather_ID=b.Gather_ID and  AmMeter_ID=" + id;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
			{
				gather = new Gather();
				gather.setGatherID(rs.getInt("Gather_ID"));
				gather.setGatherAddress(rs.getString("Gather_Address"));
				gather.setGatherPw(rs.getString("Gather_PassWord"));
				gather.setProtocol(rs.getString("Protocol"));
				gather.setSendway(rs.getInt("Sendway"));
				gather.setAmm485Address(rs.getString("AmMeter_485Address"));
				gather.setDatasiteID(rs.getInt("datasite_id"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return gather;
	}
	
	/**
	 * 李戬
	 * 2015/07/08 通过gatherID查询对应gatherName 
	 * @param gatherID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Gather> queryGatherByGatherID(int gatherID) throws SQLException
	{

		ArrayList<Gather> list = new ArrayList<Gather>();
		Gather gather = null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT GATHER_NAME FROM GATHER where GATHER_ID=" + gatherID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(); 
			while (rs.next())
			{
				gather = new Gather();
				gather.setGatherID(gatherID);
				gather.setGatherName(rs.getString("GATHER_NAME"));
				list.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}


}
