package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.project.system.model.ArcMulSelectModel;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.util.ConnDB;

/**
 * 对建筑物进行增删改查操作类
 * 
 * @author 鄢浩
 * @version 1.0
 * 
 * @since version 1.0
 * 
 */
public class ArchitectureDao
{

	// ConnDB dbPro = null;
	AreaDao areaDao = new AreaDao();
	int total;

	/**
	 * 定义list为ArrayList，对象为建筑物对象
	 * 
	 */
	public ArchitectureDao()
	{

	}

	public Architecture getFirstArch() throws SQLException
	{
		Architecture arch = null;

		String sql = "select * from Architecture where RowNum = 1";
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
				arch = buildInstance(rs);

				if (ps != null)
					ps.close();

				return arch;
			} else
			{
				arch = new Architecture();
				arch.setName("自动添加的建筑");
				arch.setAreaID(areaDao.getFirstArea().getId());

				add(arch);

				return getFirstArch();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
	}

	/**
	 * 对数据库进行操作，查出所有建筑物对象，存入List中，并返回
	 * 
	 * @return list 一个对象为建筑物的List
	 * @throws SQLException
	 */
	public List<Architecture> displayAll() throws SQLException
	{
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "SELECT * FROM Architecture ORDER BY Area_ID ,Architecture_ID ";
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
				Architecture arch = buildInstance(rs);

				list.add(arch);
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
	 * 对数据库进行操作，查出所有建筑物对象，存入List中，并返回
	 * 
	 * @return list 一个对象为建筑物的List
	 * @throws SQLException
	 */
	public Architecture getArchByID(int archid) throws SQLException
	{
		Architecture list = new Architecture();
		String sql = "SELECT * FROM Architecture where architecture_id= "+archid+" ORDER BY Area_ID ,Architecture_ID ";
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
				list = buildInstance(rs);

//				list.add(arch);
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
	 * 对数据库进行操作，查出所有建筑物对象，存入List中，并返回
	 * 
	 * @return list 一个对象为建筑物的List
	 * @throws SQLException
	 */
	public List<Architecture> displayAll(int userID) throws SQLException
	{
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "SELECT * FROM Architecture where Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") ORDER BY Area_ID ,Architecture_ID ";
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
				Architecture arch = buildInstance(rs);
				list.add(arch);
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
	 *   查询所有预付费建筑
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public List<Architecture> displayAllForPrePay(int userID) throws SQLException
	{
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "SELECT * FROM Architecture where Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") and ARCHITECTURE_ADVANCEPAYMENT = 1 ORDER BY Area_ID ,Architecture_ID ";
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
				Architecture arch = buildInstance(rs);
				list.add(arch);
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
	 * 传入sql语句，对数据库进行操作，查出所有建筑物对象，存入List中，并返回
	 * 
	 * @param sql
	 *            查询建筑的语句
	 * @return list 一个对象为建筑物的List
	 * @throws SQLException
	 */
	public List<Architecture> displayAll(String sql) throws SQLException
	{
		List<Architecture> list = new ArrayList<Architecture>();
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
				Architecture arch = buildInstance(rs);
				list.add(arch);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public Map<String, Architecture> getMapDataByArchName() throws SQLException
	{
		Map<String, Architecture> map = null;
		String sql = "SELECT * FROM Architecture";
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
				if (map == null)
				{
					map = new HashMap<String, Architecture>();
				}

				Architecture arch = buildInstance(rs);

				map.put(arch.getName(), arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 通过传入建筑物的名称，对数据库进行操作，返回相应建筑物对象
	 * 
	 * @param name
	 *            建筑物的名称
	 * @return arch 相应建筑物对象
	 * @throws SQLException
	 * @throws NumberFormatException
	 */

	public Architecture queryByID(int id) throws NumberFormatException,
			SQLException
	{
		String sql = "select * from architecture where Architecture_ID=" + id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		Architecture arch =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			arch = null;
			if (rs.next())
			{
				arch = buildInstance(rs);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return arch;
	}

	/**
	 * 通过重点用户建筑查询有重点电表的楼层
	 * 
	 * @param id
	 *            建筑ID
	 * @return 结果集
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public List<Architecture> queryImportantFloorByarcID(int id,int userID)
			throws NumberFormatException, SQLException
	{
		String sql = "select  distinct Architecture.Architecture_ID,Architecture.Architecture_NAME,floor"
				+ " from Ammeter,Architecture where isimportantuser = 1 "
				+ "and Architecture.Architecture_ID = AMMETER.Architecture_ID "
				+ "and Architecture.Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID= "
				+ userID + ")"
				+ "and AMMETER.Architecture_ID = " + id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Architecture> list = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			Architecture arch = null;
			list = null;
			while (rs.next())
			{
				list = new ArrayList<Architecture>();
				arch = new Architecture();
				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				arch.setStorey(rs.getInt("floor"));
				list.add(arch);
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
	 * 通过重点用户建筑查询有重点电表的楼层
	 * 
	 * @param id
	 *            建筑ID
	 * @return 结果集
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public List<Architecture> queryCountMeterFloorByarcID(int id,int userID)
			throws NumberFormatException, SQLException
	{
		String sql = "select  distinct Architecture.Architecture_ID,Architecture.Architecture_NAME,floor"
				+ " from Ammeter,Architecture where iscountMeter = 1 "
				+ "and Architecture.Architecture_ID = AMMETER.Architecture_ID "
				+ "and Architecture.Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID= "
				+ userID + ")"
				+ "and AMMETER.Architecture_ID = " + id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Architecture> list = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			Architecture arch = null;
			list = null;
			while (rs.next())
			{
				list = new ArrayList<Architecture>();
				arch = new Architecture();
				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				arch.setStorey(rs.getInt("floor"));
				list.add(arch);
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
	 * 通过有水表建筑查询有水表的楼层
	 * 
	 * @param id
	 *            建筑ID
	 * @return 结果集
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public List<Architecture> queryWaterImportantFloorByarcID(int id)
			throws NumberFormatException, SQLException
	{
		String sql = "select  distinct Architecture.Architecture_ID,Architecture.Architecture_NAME,floor"
				+ " from Watermeter,Architecture where  "
				+ " Architecture.Architecture_ID = Watermeter.Architecture_ID "
				+ "and Watermeter.Architecture_ID = " + id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<Architecture> list=null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Architecture arch = null;
			list = new ArrayList<Architecture>();
			while (rs.next())
			{
				arch = new Architecture();
				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				arch.setStorey(rs.getInt("floor"));
				list.add(arch);
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
	 * 通过传入建筑物的名称，对数据库进行操作，返回相应建筑物对象
	 * 
	 * @param name
	 *            建筑物的名称
	 * @return arch 相应建筑物对象
	 * @throws SQLException
	 * @throws NumberFormatException
	 */

	public Architecture display(String name) throws NumberFormatException,
			SQLException
	{
		Architecture arch = null;
		String sql = "select * from architecture where Architecture_Name='"
				+ name + ",";
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
				arch = buildInstance(rs);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return arch;
	}

	/**
	 * 通过传入一个建筑物对象，对数据库进行操作，将此建筑物信息插入数据库中
	 * 
	 * @param arch
	 *            建筑物对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean add(Architecture arch) throws SQLException
	{

		String sql = "insert into architecture(Area_ID,Architecture_Num,Architecture_Name,Architecture_Style,Architecture_Time,Architecture_Storey,Architecture_Area,Architecture_Aircondition,Architecture_Function,Architecture_FL,Architecture_FloorStart,Architecture_imgUrl,Architecture_AdvancePayment,Architecture_Man,Architecture_Phone,Count_Man,ISUnit)values("

				+ arch.getAreaID()
				+ ",'"
				+ arch.getNum()
				+ "','"
				+ arch.getName()
				+ "','"
				+ arch.getStyle()
				+ "','"
				+ arch.getTime()
				+ "',"
				+ arch.getStorey()
				+ ","
				+ arch.getArea()
				+ ","
				+ arch.getAircondition()
				+ ",'"
				+ arch.getFunction()
				+ "','"
				+ arch.getFL()
				+ "',"
				+ arch.getFloorstart()
				+ ",'"
				+ arch.getImgUrl()
				+ "','"
				+ arch.getPayment() + "','" + arch.getMan()

				+ "','" + arch.getPhone() + "','" + arch.getCountMan()+ "','" + arch.getIsUnit() + "')";

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
		return b;

	}

	/**
	 * 通过传入一个建筑物对象，对数据库进行操作，更新数据库中数据
	 * 
	 * @param arch
	 *            建筑物对象
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean update(Architecture arch) throws SQLException
	{
		if (queryByID(arch.getId()).getAreaID() != arch.getAreaID())
		{
			updateInfoInAmmeter(arch.getId(), arch.getAreaID());
			updateInfoInWatermeter(arch.getId(), arch.getAreaID());
			updateInfoInGather(arch.getId(), arch.getAreaID());
		}

		String sql = "update architecture set Area_ID=" + arch.getAreaID()
				+ " ," + "Architecture_Num='" + arch.getNum()
				+ "',Architecture_Name='" + arch.getName() + "',"
				+ "Architecture_Style='" + arch.getStyle()
				+ "',Architecture_Time='" + arch.getTime()
				+ "',Architecture_Storey='" + arch.getStorey()
				+ "',Architecture_Area='" + arch.getArea()
				+ "',Architecture_Aircondition='" + arch.getAircondition()
				+ "',Architecture_Function='" + arch.getFunction()
				+ "',Architecture_FL='" + arch.getFL()
				+ "',Architecture_FloorStart='" + arch.getFloorstart()
				+ "',Architecture_imgUrl='" + arch.getImgUrl()
				+ "',Architecture_AdvancePayment='" + arch.getPayment()
				+ "',Architecture_Man='" + arch.getMan()+ "',ISUNIT='" + arch.getIsUnit()
				+ "',Architecture_Phone='" + arch.getPhone() + "', Count_Man="
				+ arch.getCountMan() + " where Architecture_ID=" + arch.getId();

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

		return b;

	}

	/**
	 * 通过传入建筑物的id,对数据进行操作，删除此id对应建筑物
	 * 
	 * @param id
	 *            建筑物对象的ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{

		String sql = "delete from architecture where Architecture_ID=" + id;
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
		return b;
	}

	/**
	 * 通过建筑类别查询所有的建筑
	 * 
	 * @param style
	 *            建筑类别
	 * @return 符合该类别的所有建筑
	 * @throws SQLException
	 */
	public ArrayList<Architecture> queryArchByStyle(char style)
			throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		ArrayList<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Architecture_Style ='"
				+ style + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);		
			rs = ps.executeQuery();
			archStyleName = queryArchStyleName(style);
			while (rs.next())
			{
				arch = buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<Architecture> queryArchByAreaID(int areaID, int userID)
			throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Area_ID = ? and Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") ORDER BY Area_ID ,Architecture_Name ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	
	
	//通过区域查询预付费建筑
	public List<Architecture> queryAdvancePreArchByAreaID(int areaID, int userID)
			throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Area_ID = ? and Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") and ARCHITECTURE_ADVANCEPAYMENT = 1 ORDER BY Area_ID ,Architecture_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	
	
	public List<Architecture> queryFloorByArchID(int archID, int userID)
			throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Architecture_ID = ? and Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") ORDER BY Area_ID ,Architecture_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}
	

	public Architecture getArchFloorByArchID(int archid)
	{
		Architecture arch = new Architecture();
		String sql = "Select Architecture_Storey, Architecture_FloorStart from Architecture where Architecture_id =?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, archid);
			rs=ps.executeQuery();
			while(rs.next()){
				arch.setStorey(rs.getInt("Architecture_Storey"));
				arch.setFloorstart(rs.getInt("Architecture_FloorStart"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return arch;
	}
	public List<Architecture> queryArchByAreaID(int areaID) throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Area_ID = ? ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = buildInstance(rs);
				list.add(arch);
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
	 * 通过区域ID查询该区域下所有重点用户所在的建筑
	 * 
	 * @param areaID
	 *            区域ID
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<Architecture> queryImportantArchByAreaID(int areaID,int userID)
			throws SQLException
	{
		Architecture arch = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "select  distinct Architecture_name,Ammeter.Architecture_ID "
				+ "from Ammeter,Architecture "
				+ "where isimportantuser = 1 and Ammeter.Architecture_ID = Architecture.Architecture_ID "
				+ "and Ammeter.Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID= "
				+ userID + ") "
				+ "and Ammeter.Area_ID = ? ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = new Architecture();

				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				list.add(arch);

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
	 * 通过区域ID查询该区域下所有重点用户所在的建筑
	 * 
	 * @param areaID
	 *            区域ID
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<Architecture> queryCountMeterArchByAreaID(int areaID,int userID)
			throws SQLException
	{
		Architecture arch = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "select  distinct Architecture_name,Ammeter.Architecture_ID "
				+ "from Ammeter,Architecture "
				+ "where ISCOUNTMETER = 1 and Ammeter.Architecture_ID = Architecture.Architecture_ID "
				+ "and Ammeter.Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID= "
				+ userID + ") "
				+ "and Ammeter.Area_ID = ? ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = new Architecture();

				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				list.add(arch);

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
	 * 通过区域ID查询该区域下所有有水表的建筑
	 * 
	 * @param areaID
	 *            区域ID
	 * @return 结果集
	 * @throws SQLException
	 */
	public List<Architecture> queryWaterImportantArchByAreaID(int areaID)
			throws SQLException
	{
		Architecture arch = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "select  distinct Architecture_name,Watermeter.Architecture_ID "
				+ "from Watermeter,Architecture "
				+ "where  Watermeter.Architecture_ID = Architecture.Architecture_ID "
				+ "and Watermeter.Area_ID = ? ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = new Architecture();

				arch.setId(rs.getInt("Architecture_ID"));
				arch.setName(rs.getString("Architecture_Name"));
				list.add(arch);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Architecture> queryArchByAreaID(int areaID, int pageCount,
			int pageIndex, int userID) throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Area_ID = ? and Architecture_ID in (select Architecture_ID from OprArc_List where Users_ID="
				+ userID + ") ORDER BY Area_ID ,Architecture_ID  ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				arch = buildInstance(rs);
				list.add(arch);
				count--;

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<Architecture> queryArchByAreaID(int areaID, int pageCount,
			int pageIndex) throws SQLException
	{
		Architecture arch = null;
		String archStyleName = null;
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "Select * from Architecture where Area_ID = ?  ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				arch = buildInstance(rs);
				list.add(arch);
				count--;

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
	 * 通过建筑表architecture_style查询数据字典里的建筑分类名称
	 * 
	 * @param style
	 *            建筑表里architecture_style的值
	 * @return 数据字典表里面建筑分类名称
	 * @throws SQLException
	 */
	public String queryArchStyleName(char style) throws SQLException
	{
		String archStyleName = null;
		String sqlStyle = "Select DictionaryValue_Value from DictionaryValue where Dictionary_id=3 and DictionaryValue_Num='"
				+ style + "'";
		PreparedStatement psStyle = null;
		ResultSet rsStyle = null;
		Connection conn = null;
		try
		{
			conn = ConnDB.getConnection();
			psStyle = conn.prepareStatement(
					sqlStyle);
			rsStyle = psStyle.executeQuery();
			if (rsStyle.next())
			{
				archStyleName = rsStyle.getString("DictionaryValue_Value");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ConnDB.release(conn, psStyle, rsStyle);
		}

		return archStyleName.trim();
	}

	/**
	 * 建筑多选的方法，返回以区域名称为key的map
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String, List<ArcMulSelectModel>> getArcMulSelect(int userID)
			throws SQLException // 多选的方法
	{
		List<Architecture> list = null;
		List<Area> list_area = null;
		ArcMulSelectModel amsm = new ArcMulSelectModel();
		AmHelperImpl amhi = new AmHelperImpl();
		List<ArcMulSelectModel> model_list = new ArrayList<ArcMulSelectModel>();
		Map<String, List<ArcMulSelectModel>> select_map = new HashMap<String, List<ArcMulSelectModel>>();
		AreaDao area = new AreaDao();

		list_area = area.display();
		for (int j = 0; j < list_area.size(); j++)// 遍历所有区域
		{
			model_list = new ArrayList<ArcMulSelectModel>();
			list = queryArchByAreaID(list_area.get(j).getId(), userID);
			for (int i = 0; i < list.size(); i++)
			{
				amsm = new ArcMulSelectModel();
				amsm.setAreaID(list_area.get(j).getId());
				amsm.setArcID(list.get(i).getId());
				amsm.setArcName(amhi.getArchitectureName(list.get(i).getId()));
				model_list.add(amsm);
			}
			select_map.put(list_area.get(j).getName(), model_list);
		}

		return select_map;
	}

	public boolean hasAmmeter(int theArchID) throws SQLException
	{
		boolean flag = false;

		String sql = "select Ammeter_ID,Architecture_ID from Ammeter where Architecture_ID = ? and RowNum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theArchID);

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

	public boolean hasWaterMeter(int theArchID) throws SQLException
	{
		boolean flag = false;

		String sql = "select WaterMeter_ID,Architecture_ID from WaterMeter where Architecture_ID = ? and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theArchID);

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

	public boolean hasGather(int theArchID) throws SQLException
	{
		boolean flag = false;

		String sql = "select Gather_ID,Architecture_ID from Gather where Architecture_ID = ? and RowNum = 1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, theArchID);

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

	public boolean hasAddRepeat(Architecture arch) throws SQLException
	{
		boolean flag = false;
		String sql = "select Architecture_ID from Architecture where "
				+ "(Architecture_Num = ? or Architecture_Name = ?) and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, arch.getNum());
			ps.setString(2, arch.getName());

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

	public boolean hasUpdateRepeat(Architecture arch) throws SQLException
	{
		boolean flag = false;
		String sql = "select Architecture_ID from Architecture where "
				+ "(Architecture_Num = ? or Architecture_Name = ?) and Architecture_ID !=? and RowNum = 1";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, arch.getNum());
			ps.setString(2, arch.getName());
			ps.setInt(3, arch.getId());

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

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public void updateInfoInAmmeter(int archID, int newAreaID)
			throws SQLException
	{
		String sql = "update Ammeter Set Area_ID = ? where Architecture_ID = ?";

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newAreaID);
			ps.setInt(2, archID);

			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	public void updateInfoInWatermeter(int archID, int newAreaID)
			throws SQLException
	{
		String sql = "update Watermeter Set Area_ID = ? where Architecture_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newAreaID);
			ps.setInt(2, archID);

			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	public void updateInfoInGather(int archID, int newAreaID)
			throws SQLException
	{
		String sql = "update Gather Set Area_ID = ? where Architecture_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newAreaID);
			ps.setInt(2, archID);

			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	public Architecture buildInstance(ResultSet rs) throws SQLException
	{
		Architecture arch = new Architecture();

		arch.setId(rs.getInt("Architecture_ID"));
		arch.setAreaID(rs.getInt("Area_ID"));
		arch.setNum(rs.getString("Architecture_Num"));
		arch.setName(rs.getString("Architecture_Name"));
		arch.setStyle(rs.getString("Architecture_Style"));
		arch.setTime(rs.getString("Architecture_Time"));
		arch.setStorey(rs.getInt("Architecture_Storey"));
		arch.setArea(rs.getFloat("Architecture_Area"));
		arch.setAircondition(rs.getFloat("Architecture_Aircondition"));
		arch.setFunction(rs.getString("Architecture_Function"));
		arch.setFL(rs.getString("Architecture_FL"));
		arch.setFloorstart(rs.getInt("Architecture_FloorStart"));
		arch.setImgUrl(rs.getString("Architecture_imgUrl"));
		arch.setPayment(rs.getInt("Architecture_AdvancePayment"));
		arch.setMan(rs.getString("Architecture_Man"));
		arch.setPhone(rs.getString("Architecture_Phone"));
		arch.setCountMan(rs.getInt("Count_Man"));
		arch.setIsUnit(rs.getInt("ISUNIT"));
		return arch;
	}
	public double OnlineMeterRadioByArchid(int archid){
		double onlineRadio=0;
		
		int onlinemeter=0;
		int allMeter=0;
		String sql="select count(AmMeter_ID) onlineMeter from AmMeter where Architecture_ID="+archid+" and AmMeter_Stat>0 ";
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
				onlinemeter = rs.getInt("onlineMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String sql2="select count(AmMeter_ID) allMeter from AmMeter where Architecture_ID= "+archid;
		Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2);
			rs2 = ps2.executeQuery();

			if (rs2.next())
			{
				allMeter = rs2.getInt("allMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		
		if(allMeter==0){
			onlineRadio=0;
		}else {
			onlineRadio=(double)onlinemeter/allMeter;
		}
		
		return onlineRadio;
	}
	public double OnlineMeterRadioByArchAndFloor(int archid,int floorid){
		double onlineRadio=0;
		
		int onlinemeter=0;
		int allMeter=0;
		String sql="select count(AmMeter_ID) onlineMeter from AmMeter where Architecture_ID="+archid+" and floor="+floorid+" and AmMeter_Stat>0 ";
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
				onlinemeter = rs.getInt("onlineMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String sql2="select count(AmMeter_ID) allMeter from AmMeter where Architecture_ID= "+archid+" and floor="+floorid;
		Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2);
			rs2 = ps2.executeQuery();
			
			if (rs2.next())
			{
				allMeter = rs2.getInt("allMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		
		if(allMeter==0){
			onlineRadio=0;
		}else {
			onlineRadio=(double)onlinemeter/allMeter;
		}
		
		return onlineRadio;
	}
	public double OnlineWaMeterRadioByArchid(int archid){
		double onlineRadio=0;
		
		int onlinemeter=0;
		int allMeter=0;
		String sql="select count(WaterMeter_ID) onlineMeter from WaterMeter where Architecture_ID="+archid+" and WATERMETER_STAT>0 ";
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
				onlinemeter = rs.getInt("onlineMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String sql2="select count(WaterMeter_ID) allMeter from WaterMeter where Architecture_ID= "+archid;
		Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2);
			rs2 = ps2.executeQuery();

			if (rs2.next())
			{
				allMeter = rs2.getInt("allMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		
		if(allMeter==0){
			onlineRadio=0;
		}else {
			onlineRadio=(double)onlinemeter/allMeter;
		}
		
		return onlineRadio;
	}
	public double OnlineWaMeterRadioByArchAndFloor(int archid,int Floor){
		double onlineRadio=0;
		
		int onlinemeter=0;
		int allMeter=0;
		String sql="select count(WaterMeter_ID) onlineMeter from WaterMeter where Architecture_ID="+archid+" and floor="+Floor+" and WATERMETER_STAT>0 ";
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
				onlinemeter = rs.getInt("onlineMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String sql2="select count(WaterMeter_ID) allMeter from WaterMeter where Architecture_ID= "+archid+" and floor="+Floor;
		Connection conn2=null;
		PreparedStatement ps2 =null;
		ResultSet rs2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			ps2 = conn2.prepareStatement(sql2);
			rs2 = ps2.executeQuery();
			
			if (rs2.next())
			{
				allMeter = rs2.getInt("allMeter");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, ps2, rs2);
		}
		
		
		if(allMeter==0){
			onlineRadio=0;
		}else {
			onlineRadio=(double)onlinemeter/allMeter;
		}
		
		return onlineRadio;
	}
	public int queryAreaIDByArchID(int archID)
			throws SQLException
	{
		String sql = "select Area_ID from Architecture where architecture_ID="+archID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs=null;
		int areaID=-1;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()){
				areaID=rs.getInt("Area_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		return areaID;
	}

	public List<String> getAllArchStyle()
	{
		List<String> archStyleList = new ArrayList<String>();
		String sqlStyle = "Select DICTIONARYVALUE_NUM from DictionaryValue where Dictionary_id=3";
		PreparedStatement psStyle = null;
		ResultSet rsStyle = null;
		Connection conn = null;
		try
		{
			conn = ConnDB.getConnection();
			psStyle = conn.prepareStatement(
					sqlStyle);
			rsStyle = psStyle.executeQuery();
			while(rsStyle.next())
			{
				archStyleList.add( rsStyle.getString("DICTIONARYVALUE_NUM"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally{
			ConnDB.release(conn, psStyle, rsStyle);
		}

		return archStyleList;
	}
	/**
	 * 查询建筑指定日期内的用电总和
	 * @param archID
	 * @return
	 * @throws SQLException
	 */
	public float queryArchMonEnTotal(int archID,String queryS,String queryE)
			throws SQLException
	{
		String sql = "select SUM(ZGROSS) ZGROSS from T_DAYAM where Architecture_ID="+archID+" AND TO_DATE(SELECTYEAR || '-' || SELECTMONTH || '-' || SELECTDAY, 'yyyy-mm-dd') BETWEEN TO_DATE('"+queryS+"', 'yyyy-mm-dd') AND TO_DATE('"+queryE+"', 'yyyy-mm-dd')";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs=null;
		float zgross=0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);		
			rs=ps.executeQuery();
			if(rs.next()){
				zgross = rs.getFloat("ZGROSS");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		return zgross;
	}
}
