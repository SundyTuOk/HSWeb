package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.xml.resolver.apps.resolver;

import com.google.gson.JsonObject;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.MeterStyleDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.MeterStyle;
import com.sf.energy.project.system.model.MeterTexing;
import com.sf.energy.util.ConnDB;

/**
 * 对电能表【Ammeter】操作, 实现电能表【Ammeter】增、删、改、查操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */

public class AmmeterDao
{
	int total;
	MeterStyleDao msd = new MeterStyleDao();

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public Integer getMaxPointByGather(int gatherID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer point = null;
		try
		{
			String sql = "select max(Ammeter_Point) theMaxPoint from Ammeter where Gather_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gatherID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				point = rs.getInt("theMaxPoint");
			}

			if (point == 0)
			{
				point = 1;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return point;
	}

	/**
	 * 通过电表ID查询电表
	 * 
	 * @param ammeterId
	 *            电表id
	 * @return AmmeterModel 电表实体类的实例
	 * @throws SQLException
	 */

	public AmmeterModel queryByID(int ammeterId) throws SQLException
	{
		AmmeterModel am = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_AmMeter WHERE Ammeter_ID=" + ammeterId;

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				am = buildInstance(rs);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return am;
	}
	/**
	 * 通过电表ID查询标记类型编号
	 * 
	 * @param ammeterId
	 *            电表id
	 * @return AmmeterModel 电表实体类的实例
	 * @throws SQLException
	 */
	
	public String queryMeterStyleNumByID(int ammeterId) throws SQLException
	{
		String meterStyle_num="";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select b.METESTYLE_NUM from AMMETER a, METESTYLE b where a.METESTYLE_ID=b.METESTYLE_ID and AMMETER_ID=" + ammeterId;
			
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				meterStyle_num =rs.getString("METESTYLE_NUM");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return meterStyle_num;
	}

	public String queryNameByID(int ammeterId) throws SQLException
	{

		String name = "------";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT Ammeter_name FROM V_AmMeter WHERE Ammeter_ID=" + ammeterId;

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				name = rs.getString("Ammeter_name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return name;
	}

	public List<AmmeterModel> queryAmmeterAllName()
	{

		AmmeterModel am = null;
		List<AmmeterModel> list = new ArrayList<AmmeterModel>();
		String sql = "Select Ammeter_id,Ammeter_name,Area_id from Ammeter where  isimportantuser=1 ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				am = new AmmeterModel();
				am.setAmmeterID(rs.getInt("ammeter_id"));
				am.setAmmeterName(rs.getString("ammeter_name"));
				am.setAreaID(rs.getInt("area_id"));
				list.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 列出所有电表【Ammeter】中的信息
	 * 
	 * @return AmmeterModel 电表实体类的实例
	 * @throws SQLException
	 */

	public ArrayList<AmmeterModel> display() throws SQLException
	{
		AmmeterModel am = null;
		ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_AmMeter ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				am = buildInstance(rs);

				lst.add(am);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;

	}

	public ArrayList<AmmeterModel> display(String sql) throws SQLException
	{
		AmmeterModel am = null;
		ArrayList<AmmeterModel> lst = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (lst == null)
					lst = new ArrayList<AmmeterModel>();

				am = buildInstance(rs);

				lst.add(am);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;

	}
	
	/**
	 * 列出所有所有电表信息【V_Ammeter】的信息
	 * 
	 * @return AmmeterModel 电表实体类的实例
	 * @throws SQLException
	 */

	public ArrayList<AmmeterModel> displayAll() throws SQLException
	{
		AmmeterModel am = null;
		ArrayList<AmmeterModel> lst = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (lst == null)
					lst = new ArrayList<AmmeterModel>();
				am = buildInstance(rs);
				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	public List<AmmeterModel> queryByAreaID(int areaID) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where Area_ID = ? ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);
				lst.add(am);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public List<AmmeterModel> queryByArchID(int archID) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where Architecture_ID = ? ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);
				lst.add(am);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}

	/**
	 * 查询某建筑某层楼的所有电表
	 * 
	 * @param archID
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<AmmeterModel> queryByArchAndFloor(int archID, int floor) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where Architecture_ID = ? and Floor=? ORDER BY Ammeter_Name";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, floor);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);

				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	/**
	 * 查询某建筑某层楼的所有重点电表
	 * 
	 * @param archID
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<AmmeterModel> queryImpAmmeterByArchAndFloor(int archID, int floor) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where Architecture_ID = ? and Floor=? and ISIMPORTANTUSER = 1 ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, floor);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);

				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	/**
	 * 查询某建筑某层楼的所有重点电表
	 * 
	 * @param archID
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<AmmeterModel> queryCountAmmeterByArchAndFloor(int archID, int floor) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where Architecture_ID = ? and Floor=? and ISCOUNTMETER = 1 ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, floor);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);

				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return lst;
	}
	/**
	 * 查询某建筑某层楼的所有水表
	 * 
	 * @param archID
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<WatermeterModel> queryWaterImpAmmeterByArchAndFloor(int archID, int floor) throws SQLException
	{
		List<WatermeterModel> lst = new LinkedList<WatermeterModel>();
		WatermeterModel wm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT Watermeter_ID,Watermeter_Name FROM Watermeter where Architecture_ID = ? and Floor=?  ORDER BY Watermeter_ID";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, archID);
			ps.setInt(2, floor);
			rs = ps.executeQuery();
			while (rs.next())
			{
				wm = new WatermeterModel();
				wm.setWATERMETER_ID(rs.getInt("Watermeter_ID"));
				wm.setWATERMETER_NAME(rs.getString("Watermeter_Name"));

				lst.add(wm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public boolean insert(List<AmmeterModel> list) throws SQLException
	{
		boolean b = false;
		String sql = "INSERT INTO AMMETER(AmMeter_Point,Area_ID,Architecture_ID,Gather_ID,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,Maker, "
				+ " MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,UseAmStyle,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,"
				+ "IsCountMeter,isComputation,AmMeter_Plose,Partment_ID,Floor,MeteStyle_ID,Price_ID,AmMeter_Stat,IsOffAlarm,CurPower,CostCheck,StandByCheck,Xiuzheng,LastTime,ParentID,beilv,LimitPart)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		try
		{
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			int count = 0, executeCount = 0;

			if (list != null && list.size() > 0)
			{
				for (AmmeterModel am : list)
				{
					if (am != null)
					{
						int newBeiLv = 1;
						MeterStyle ms = msd.queryByID(am.getMeteStyleID());
						List<MeterTexing> dataList = ms.getDetaiList();

						if (dataList != null && dataList.size() > 0)
						{
							for (MeterTexing mt : dataList)
							{
								if (mt.getMETERTEXING_ID() == 8)
								{
									newBeiLv = Integer.parseInt(mt.getValue());
									am.setBeilv(newBeiLv);
									break;
								}
							}
						}

						psmt.setInt(1, am.getAmmeterPoint());
						psmt.setInt(2, am.getAreaID());
						psmt.setInt(3, am.getArchitectureID());
						psmt.setInt(4, am.getGatherID());
						psmt.setString(5, am.getAmmeterNum());
						psmt.setString(6, am.getAmmeterName());
						psmt.setString(7, am.getAmmeterPassword());
						psmt.setString(8, am.getAmmeterAddress485());
						psmt.setString(9, am.getMaker());
						psmt.setString(10, am.getMakerCode());
						psmt.setString(11, am.getAssetNo());
						psmt.setInt(12, am.getIsSupply());
						psmt.setDouble(13, am.getzValue());
						psmt.setDouble(14, am.getjValue());
						psmt.setDouble(15, am.getfValue());
						psmt.setDouble(16, am.getpValue());
						psmt.setDouble(17, am.getgValue());
						psmt.setInt(18, am.getUseAmStyle());
						psmt.setString(19, am.getConsumerNum());
						psmt.setString(20, am.getConsumerName());
						psmt.setString(21, am.getConsumerPhone());
						psmt.setString(22, am.getConsumerAddress());
						psmt.setInt(23, am.getIsImportantUser());
						psmt.setInt(24, am.getIsCountMeter());
						psmt.setInt(25, am.getIsComputation());
						psmt.setInt(26, am.getAmmeterPlose());
						psmt.setInt(27, am.getPartment());
						psmt.setInt(28, am.getFloor());
						psmt.setInt(29, am.getMeteStyleID());
						psmt.setInt(30, am.getPriceID());
						psmt.setInt(31, am.getAmmeterStat());
						psmt.setInt(32, am.getIsOffAlarm());
						psmt.setDouble(33, am.getCurPower());
						psmt.setInt(34, am.getCostCheck());
						psmt.setInt(35, am.getStandByCheck());
						psmt.setDouble(36, am.getXiuzheng());
						psmt.setString(37, am.getLastTime());
						psmt.setInt(38, am.getParentID());
						psmt.setInt(39, am.getBeilv());
						psmt.setFloat(40, am.getLimitPart());

						if (count <= 1000)
						{
							psmt.addBatch();
							count++;
						} else
						{
							int[] array = psmt.executeBatch();
							for (int i : array)
							{
								if (i >= 0)
									executeCount += i;
							}

							count = 0;
						}
					}

				}

				if (count > 0)
				{
					int[] array = psmt.executeBatch();
					for (int i : array)
					{
						if (i >= 0)
							executeCount += i;
					}
				}
			} else
			{
				return false;
			}

			b = (executeCount == list.size());

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, psmt);
		}
		return b;
	}

	/**
	 * 插入电表
	 * 
	 * @param am
	 *            电表实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean insert(AmmeterModel am) throws SQLException
	{
		int newBeiLv = 1;
		MeterStyle ms = msd.queryByID(am.getMeteStyleID());
		List<MeterTexing> dataList = ms.getDetaiList();

		if (dataList != null && dataList.size() > 0)
		{
			for (MeterTexing mt : dataList)
			{
				if (mt.getMETERTEXING_ID() == 8)
				{
					newBeiLv = Integer.parseInt(mt.getValue());
					am.setBeilv(newBeiLv);
					break;
				}
			}
		}

		String sql = "INSERT INTO AMMETER(AmMeter_Point,Area_ID,Architecture_ID,Gather_ID,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,Maker, "
				+ " MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,UseAmStyle,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,"
				+ "IsCountMeter,isComputation,AmMeter_Plose,Partment_ID,Floor,MeteStyle_ID,Price_ID,AmMeter_Stat,IsOffAlarm,CurPower,CostCheck,StandByCheck,Xiuzheng,LastTime,ParentID,beilv,LimitPart,ApManCount,RoomManCount,IsVCCheck,IsRecycle)"
				+ " Values("
				+ am.getAmmeterPoint()
				+ ","
				+ am.getAreaID()
				+ ","
				+ am.getArchitectureID()
				+ ","
				+ am.getGatherID()
				+ ",'"
				+ am.getAmmeterNum()
				+ "','"
				+ am.getAmmeterName()
				+ "','"
				+ am.getAmmeterPassword()
				+ "','"
				+ am.getAmmeterAddress485()
				+ "','"
				+ am.getMaker()
				+ "','"
				+ am.getMakerCode()
				+ "','"
				+ am.getAssetNo()
				+ "',"
				+ am.getIsSupply()
				+ ","
				+ am.getzValue()
				+ ","
				+ am.getjValue()
				+ ","
				+ am.getfValue()
				+ ","
				+ am.getpValue()
				+ ","
				+ am.getgValue()
				+ ","
				+ am.getUseAmStyle()
				+ ",'"
				+ am.getConsumerNum()
				+ "','"
				+ am.getConsumerName()
				+ "','"
				+ am.getConsumerPhone()
				+ "','"
				+ am.getConsumerAddress()
				+ "',"
				+ am.getIsImportantUser()
				+ ","
				+ am.getIsCountMeter()
				+ ","
				+ am.getIsComputation()
				+ ","
				+ am.getAmmeterPlose()
				+ ","
				+ am.getPartment()
				+ ","
				+ am.getFloor()
				+ ","
				+ am.getMeteStyleID()
				+ ","
				+ am.getPriceID()
				+ ","
				+ am.getAmmeterStat()
				+ ","
				+ am.getIsOffAlarm()
				+ ","
				+ am.getCurPower()
				+ ","
				+ am.getCostCheck()
				+ ","
				+ am.getStandByCheck()
				+ ","
				+ am.getXiuzheng()
				+ ",'"
				+ am.getLastTime()
				+ "',"
				+ am.getParentID()
				+ "," + am.getBeilv() + "," + am.getLimitPart() + "," + am.getApManCount() + "," + am.getRoomManCount() + ","+am.getIsVCCheck() + "," +am.getIsRecycle()+")";

		Connection conn = null;
		PreparedStatement psmt = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			b = (psmt.executeUpdate() == 1);

			if (b)// 新建该表对应的AMMETERDATAS,AMMETERPDDATAS
			{
				String AmmeterID = "", isimportantuser = "0";
				sql = "select AMMETER_ID,ISIMPORTANTUSER from AMMETER where Gather_ID=" + am.getGatherID() + " and AmMeter_485Address='"
						+ am.getAmmeterAddress485() + "'";

				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{

					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next())
					{
						AmmeterID = rs.getString("AMMETER_ID");
						isimportantuser = rs.getString("ISIMPORTANTUSER");
						if (!"".equals(AmmeterID) && AmmeterID != null)
						{
							sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZAMDATAS" + String.valueOf(AmmeterID) + "'";

							// Connection conn3 = null;
							PreparedStatement ps2 = null;
							ResultSet rs2 = null;
							try
							{
								// conn3 = ConnDB.getConnection();
								ps2 = conn.prepareStatement(sql);
								rs2 = ps2.executeQuery();
								if (rs2.next())
								{
									String num = rs2.getString("NUM");
									if ("1".equals(num))
									{
										sql = "drop table ZAMDATAS" + String.valueOf(AmmeterID);
										// Connection conn4 = null;
										PreparedStatement ps3 = null;
										try
										{
											// conn4 = ConnDB.getConnection();
											ps3 = conn.prepareStatement(sql);
											ps3.executeUpdate();
										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											if (ps3 != null)
											{
												ps3.close();
											}
										}
									}
								}
							} catch (SQLException e)
							{
								e.printStackTrace();
							} finally
							{
								ConnDB.release(ps2, rs2);
							}

							// Connection conn5 = null;
							PreparedStatement ps4 = null;
							sql = "create table ZAmDatas" + String.valueOf(AmmeterID);
							sql += "(VALUETIME DATE not null,ZVALUEZY NUMBER not null ,primary key (VALUETIME)) ";
							try
							{
								// conn5 = ConnDB.getConnection();
								ps4 = conn.prepareStatement(sql);
								ps4.executeUpdate();
								if ("1".equals(isimportantuser))// 重点用户
								{
									sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZPDDATAS" + String.valueOf(AmmeterID) + "'";
									// Connection conn6 = null;
									PreparedStatement ps5 = null;
									ResultSet rs5 = null;
									try
									{
										// conn6 = ConnDB.getConnection();
										ps5 = conn.prepareStatement(sql);
										rs5 = ps5.executeQuery();
										if (rs5.next())
										{
											String num = rs5.getString("NUM");
											if ("1".equals(num))
											{
												sql = "drop table ZPDDATAS" + String.valueOf(AmmeterID);

												// Connection conn7 = null;
												PreparedStatement ps6 = null;
												try
												{
													// conn7 =
													// ConnDB.getConnection();
													ps6 = conn.prepareStatement(sql);
													ps6.executeUpdate();
												} catch (SQLException e)
												{
													e.printStackTrace();
												} finally
												{
													ConnDB.release(ps6);
												}

											}
										}
										sql = "create table ZPDDatas" + String.valueOf(AmmeterID);
										sql += "(VALUETIME DATE not null,POWERZY NUMBER,POWERAY NUMBER,POWERBY NUMBER,POWERCY NUMBER,";
										sql += "POWERZW NUMBER,POWERAW NUMBER,POWERBW NUMBER,POWERCW NUMBER,";
										sql += "POWERFACTORZ NUMBER,POWERFACTORA NUMBER,POWERFACTORB NUMBER,POWERFACTORC NUMBER,";
										sql += "VOLTAGEA NUMBER,VOLTAGEB NUMBER,VOLTAGEC NUMBER,";
										sql += "CURRENTA NUMBER,CURRENTB NUMBER,CURRENTC NUMBER,CURRENT0 NUMBER,";
										sql += "POWERSZZ NUMBER,POWERSZA NUMBER,POWERSZB NUMBER,POWERSZC NUMBER,primary key (VALUETIME)) ";

										// Connection conn8 = null;
										PreparedStatement ps7 = null;
										try
										{
											// conn8 = ConnDB.getConnection();
											ps7 = conn.prepareStatement(sql);
											ps7.executeUpdate();
										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release(ps7);
										}

									} catch (SQLException e)
									{
										e.printStackTrace();
									} finally
									{
										ConnDB.release(ps5, rs5);
									}

								}
							} catch (SQLException e)
							{
								e.printStackTrace();
							} finally
							{
								ConnDB.release(ps4);
							}

						}
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps, rs);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, psmt);
		}
		return b;
	}

	/**
	 * 删除电表
	 * 
	 * @param id
	 *            电表ID
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "DELETE FROM AMMETER WHERE AMMETER_ID =" + id;

		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);
			if (b)// 删除该表对应的AMMETERDATAS,AMMETERPDDATAS
			{
				sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZAMDATAS" + String.valueOf(id) + "'";

//				Connection conn1 = null;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				try
				{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sql);
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						String num = rs1.getString("NUM");
						if ("1".equals(num))
						{
							sql = "drop table ZAMDATAS" + String.valueOf(id);

//							Connection conn2 = null;
							PreparedStatement ps2 = null;
							try
							{
//								conn2 = ConnDB.getConnection();
								ps2 = conn.prepareStatement(sql);
								ps2.executeUpdate();
							} catch (SQLException e)
							{
								e.printStackTrace();
							} finally
							{
								ConnDB.release( ps2);
							}
							sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZPDDATAS" + String.valueOf(id) + "'";

//							Connection conn3 = null;
							PreparedStatement ps3 = null;
							ResultSet rs3 = null;
							try
							{
//								conn3 = ConnDB.getConnection();
								ps3 = conn.prepareStatement(sql);
								rs3 = ps3.executeQuery();
								if (rs3.next())
								{
									num = rs3.getString("NUM");
									if ("1".equals(num))
									{
										sql = "drop table ZPDDATAS" + String.valueOf(id);
//										Connection conn4 = null;
										PreparedStatement ps4 = null;
										try
										{
//											conn4 = ConnDB.getConnection();
											ps4 = conn.prepareStatement(sql);
											ps4.executeUpdate();
										} catch (SQLException e)
										{
											e.printStackTrace();
										} finally
										{
											ConnDB.release( ps4);
										}
									}
								}
							} catch (SQLException e)
							{
								e.printStackTrace();
							} finally
							{
								ConnDB.release(ps3, rs3);
							}
						}
					}

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(ps1, rs1);
				}

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		return b;

	}

	public boolean deleteSomeAmmeter(List<Integer> amIDList) throws SQLException
	{
		boolean flag = false;

		String sql = "delete from Ammeter where Ammeter_ID in (";
		Connection conn = null;
		PreparedStatement ps0 = null;
		for (int i = 0; i < amIDList.size(); i++)
		{
			sql += amIDList.get(i);
			if (i < amIDList.size() - 1)
				sql += ",";
			else
				sql += ")";
		}
		try
		{
			conn = ConnDB.getConnection();
			ps0 = conn.prepareStatement(sql);
			if (ps0.executeUpdate() == amIDList.size())
				flag = true;
			else
				flag = false;
			if (flag)// 删除该表对应的AMMETERDATAS,AMMETERPDDATAS
			{
				for (int i = 0; i < amIDList.size(); i++)
				{
//					Connection conn3 = null;
					PreparedStatement ps3 = null;
					ResultSet rs3 = null;
					sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZAMDATAS" + amIDList.get(i) + "'";
					try
					{
//						conn3 = ConnDB.getConnection();
						ps3 = conn.prepareStatement(sql);
						rs3 = ps3.executeQuery();
						if (rs3.next())
						{
							String num = rs3.getString("NUM");
							if ("1".equals(num))
							{
//								Connection conn = null;
								PreparedStatement ps = null;
								try
								{
									sql = "drop table ZAMDATAS" + amIDList.get(i);
//									conn = ConnDB.getConnection();
									ps = conn.prepareStatement(sql);
									ps.executeUpdate();
								} catch (Exception e)
								{
									e.printStackTrace();
								} finally
								{
									ConnDB.release( ps);
								}
							}
						}
					} catch (Exception e1)
					{
						e1.printStackTrace();
					} finally
					{
						ConnDB.release( ps3, rs3);
					}
//					Connection conn4 = null;
					PreparedStatement ps4 = null;
					ResultSet rs4 = null;
					sql = "select count(1) as NUM from all_tables where TABLE_NAME = 'ZPDDATAS" + amIDList.get(i) + "'";
					try
					{
//						conn4 = ConnDB.getConnection();
						ps4 = conn.prepareStatement(sql);
						rs4 = ps4.executeQuery();
						if (rs4.next())
						{
							String num = rs4.getString("NUM");
							if ("1".equals(num))
							{
//								Connection conn1 = null;
								PreparedStatement ps1 = null;
								try
								{
									sql = "drop table ZPDDATAS" + amIDList.get(i);
//									conn1 = ConnDB.getConnection();
									ps1 = conn.prepareStatement(sql);
									ps1.executeUpdate();
								} catch (Exception e)
								{
									e.printStackTrace();
								} finally
								{
									ConnDB.release( ps1);
								}
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release( ps4, rs4);

					}
				}
			}
		} catch (Exception e2)
		{
			e2.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps0);
		}

		
		return flag;
	}

	/**
	 * 修改电表信息
	 * 
	 * @param am
	 *            电表实体类的实例
	 * @return boolean 返回布尔变量表示操作成功与否
	 * @throws SQLException
	 */
	public boolean modify(AmmeterModel am) throws SQLException
	{
		Connection conn = null;
		PreparedStatement psmt = null;
		boolean b = false;
		int newBeiLv = 1;
		MeterStyle ms = msd.queryByID(am.getMeteStyleID());
		List<MeterTexing> dataList = ms.getDetaiList();
		if (dataList != null && dataList.size() > 0)
		{
			for (MeterTexing mt : dataList)
			{
				if (mt.getMETERTEXING_ID() == 8)
				{
					newBeiLv = Integer.parseInt(mt.getValue());
					am.setBeilv(newBeiLv);
					break;
				}
			}
		}
		try
		{
			String sql = "UPDATE AMMETER SET AmMeter_Point=?, Area_ID=?,  Architecture_ID=?,  Gather_ID=?,   "
					+ "AmMeter_Num=?,  AmMeter_Name=?,  AmMeter_Password=?,   AmMeter_485Address=?,   Maker=?, "
					+ " MakerCode=?,  AssetNo=?,  IsSupply=?,  ZValue=?,  JValue=?,  FValue=?,  PValue=?,  GValue=?,  "
					+ "UseAmStyle=?,  ConsumerNum=?,  ConsumerName=?,  ConsumerPhone=?,  ConsumerAddress=?,   IsImportantUser=?, "
					+ " IsCountMeter=?,  isComputation=?,  AmMeter_Plose=?,  Partment_ID=?,  Floor=?,   MeteStyle_ID=?,  Price_ID=?,  "
					+ "AmMeter_Stat=?,  IsOffAlarm=?,  CurPower=?,  CostCheck=?,  StandByCheck=?,  Xiuzheng=?,  LastTime=?,ParentID=?,beilv=?,LimitPart=?,IsVCCheck=?,IsRecycle="+am.getIsRecycle()+" where Ammeter_ID="
					+ am.getAmmeterID();

			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, am.getAmmeterPoint());
			psmt.setInt(2, am.getAreaID());
			psmt.setInt(3, am.getArchitectureID());
			psmt.setInt(4, am.getGatherID());
			psmt.setString(5, am.getAmmeterNum());
			psmt.setString(6, am.getAmmeterName());
			psmt.setString(7, am.getAmmeterPassword());
			psmt.setString(8, am.getAmmeterAddress485());
			psmt.setString(9, am.getMaker());
			psmt.setString(10, am.getMakerCode());
			psmt.setString(11, am.getAssetNo());
			psmt.setInt(12, am.getIsSupply());
			psmt.setDouble(13, am.getzValue());
			psmt.setDouble(14, am.getjValue());
			psmt.setDouble(15, am.getfValue());
			psmt.setDouble(16, am.getpValue());
			psmt.setDouble(17, am.getgValue());
			psmt.setInt(18, am.getUseAmStyle());
			psmt.setString(19, am.getConsumerNum());
			psmt.setString(20, am.getConsumerName());
			psmt.setString(21, am.getConsumerPhone());
			psmt.setString(22, am.getConsumerAddress());
			psmt.setInt(23, am.getIsImportantUser());
			psmt.setInt(24, am.getIsCountMeter());
			psmt.setInt(25, am.getIsComputation());
			psmt.setInt(26, am.getAmmeterPlose());
			psmt.setInt(27, am.getPartment());
			psmt.setInt(28, am.getFloor());
			psmt.setInt(29, am.getMeteStyleID());
			psmt.setInt(30, am.getPriceID());
			psmt.setInt(31, am.getAmmeterStat());
			psmt.setInt(32, am.getIsOffAlarm());
			psmt.setDouble(33, am.getCurPower());
			psmt.setInt(34, am.getCostCheck());
			psmt.setInt(35, am.getStandByCheck());
			psmt.setDouble(36, am.getXiuzheng());
			psmt.setString(37, am.getLastTime());
			psmt.setInt(38, am.getParentID());
			psmt.setInt(39, am.getBeilv());
			psmt.setInt(40, am.getIsVCCheck());
			psmt.setFloat(41, am.getLimitPart());

			b = (psmt.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, psmt);
		}
		return b;
	}

	/**
	 * 获得电表【Ammeter】中的记录总数
	 * 
	 * @return AmmeterModel 电表数据表记录总数
	 * @throws SQLException
	 */

	public int getRecordCount() throws SQLException
	{
		int count = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT count(*) as recordCount FROM V_AmMeter";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next())
				count = rs.getInt("recordCount");
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return count;

	}

	/**
	 * 分页查询电表【Ammeter】中的信息
	 * 
	 * @param pageCount
	 *            每一页分页的记录数目
	 * @param pageIndex
	 *            分页的页码，也就是第几页（第一页页码是0）
	 * @return AmmeterModel 电表实体类的实例
	 * @throws SQLException
	 */

	public ArrayList<AmmeterModel> paginate(int pageCount, int pageIndex) throws SQLException
	{
		AmmeterModel am = null;
		ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		try
		{
			//String sql = "SELECT * FROM V_AmMeter ORDER BY Ammeter_ID desc";
			String sql = "select * from (select ROWNUM RN ,A.*from (SELECT * FROM V_AmMeter ORDER BY Ammeter_ID desc)A where ROWNUM<=?) where rn >=?";
			
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			//System.out.println(sql);
			//System.out.println(endCount+" "+startCount);
			ps.setInt(1, endCount);
			ps.setInt(2, startCount);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				am = new AmmeterModel();

				if (rs.getObject(1) != null)
					am.setAmmeterID(rs.getInt(1));

				if (rs.getObject(2) != null)
					am.setAmmeterPoint(rs.getInt(2));

				if (rs.getObject(3) != null)
					am.setAreaID(rs.getInt(3));

				if (rs.getObject(4) != null)
					am.setArchitectureID(rs.getInt(4));

				if (rs.getObject(5) != null)
					am.setGatherID(rs.getInt(5));

				if (rs.getObject(6) != null)
					am.setAmmeterNum(rs.getString(6));

				if (rs.getObject(7) != null)
					am.setAmmeterName(rs.getString(7));

				if (rs.getObject(8) != null)
					am.setAmmeterPassword(rs.getString(8));

				if (rs.getObject(9) != null)
					am.setAmmeterAddress485(rs.getString(9));

				if (rs.getObject(10) != null)
					am.setMaker(rs.getString(10));

				if (rs.getObject(11) != null)
					am.setMakerCode(rs.getString(11));

				if (rs.getObject(12) != null)
					am.setAssetNo(rs.getString(12));

				if (rs.getObject(13) != null)
					am.setIsSupply(rs.getInt(13));

				if (rs.getObject(14) != null)
					am.setzValue(rs.getDouble(14));

				if (rs.getObject(15) != null)
					am.setjValue(rs.getDouble(15));

				if (rs.getObject(16) != null)
					am.setfValue(rs.getDouble(16));

				if (rs.getObject(17) != null)
					am.setpValue(rs.getDouble(17));

				if (rs.getObject(18) != null)
					am.setgValue(rs.getDouble(18));

				if (rs.getObject(19) != null)
					am.setUseAmStyle(rs.getInt(19));

				if (rs.getObject(20) != null)
					am.setConsumerNum(rs.getString(20));

				if (rs.getObject(21) != null)
					am.setConsumerName(rs.getString(21));

				if (rs.getObject(22) != null)
					am.setConsumerPhone(rs.getString(22));

				if (rs.getObject(23) != null)
					am.setConsumerAddress(rs.getString(23));

				if (rs.getObject(24) != null)
					am.setIsImportantUser(rs.getInt(24));

				if (rs.getObject(25) != null)
					am.setIsCountMeter(rs.getInt(25));

				if (rs.getObject(26) != null)
					am.setIsComputation(rs.getInt(26));

				if (rs.getObject(27) != null)
					am.setAmmeterPlose(rs.getInt(27));

				if (rs.getObject(28) != null)
					am.setPartment(rs.getInt(28));

				if (rs.getObject(29) != null)
					am.setFloor(rs.getInt(29));

				if (rs.getObject(30) != null)
					am.setMeteStyleID(rs.getInt(30));

				if (rs.getObject(31) != null)
					am.setPriceID(rs.getInt(31));

				if (rs.getObject(32) != null)
					am.setAmmeterStat(rs.getInt(32));

				if (rs.getObject(33) != null)
					am.setIsOffAlarm(rs.getInt(33));

				if (rs.getObject(34) != null)
					am.setCurPower(rs.getDouble(34));

				if (rs.getObject(35) != null)
					am.setCostCheck(rs.getInt(35));

				if (rs.getObject(36) != null)
					am.setStandByCheck(rs.getInt(36));

				if (rs.getObject(37) != null)
					am.setXiuzheng(rs.getInt(37));

				if (rs.getObject(38) != null)
					am.setLastTime(rs.getString(38));

				if (rs.getObject(39) != null)
					am.setParentID(rs.getInt(39));

				if (rs.getObject("beilv") != null)
					am.setBeilv(rs.getInt("beilv"));

				if (rs.getObject("Architecture_Name") != null)
					am.setArchName(rs.getString("Architecture_Name"));

				if (rs.getObject("Gather_Name") != null)
					am.setGatherName(rs.getString("Gather_Name"));

				if (rs.getObject("Area_Name") != null)
					am.setAreaName(rs.getString("Area_Name"));

				if (rs.getObject("USEAMXZ") != null)
					am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质

				if (rs.getObject("USEAMFX") != null)
					am.setUseStyleName(rs.getString("USEAMFX"));// 分项

				if (rs.getObject("USEAMYJZX") != null)
					am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项

				if (rs.getObject("PartmentName") != null)
					am.setPartmentName(rs.getString("PartmentName"));

				if (rs.getObject("meteStyle_Name") != null)
					am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

				if (rs.getObject("Meter_Name") != null)
					am.setMeterName(rs.getString("Meter_Name"));
				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;

	}

	public ArrayList<AmmeterModel> paginate(String sortLabel, boolean isAsc, int pageCount, int pageIndex) throws SQLException
	{
		AmmeterModel am = null;
		ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		try
		{
			String sql = "select * from (select ROWNUM RN ,A.*from (SELECT * FROM V_AmMeter ";
			
			if (isAsc)
				sql += " order by " + sortLabel + " asc";
			else
				sql += " order by " + sortLabel + " desc";
			sql+=")A where ROWNUM<=?) where rn >=?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, endCount);
			ps.setInt(2, startCount);
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				am = new AmmeterModel();
								
				if (rs.getObject(1) != null)
					am.setAmmeterID(rs.getInt(1));

				if (rs.getObject(2) != null)
					am.setAmmeterPoint(rs.getInt(2));

				if (rs.getObject(3) != null)
					am.setAreaID(rs.getInt(3));

				if (rs.getObject(4) != null)
					am.setArchitectureID(rs.getInt(4));

				if (rs.getObject(5) != null)
					am.setGatherID(rs.getInt(5));

				if (rs.getObject(6) != null)
					am.setAmmeterNum(rs.getString(6));

				if (rs.getObject(7) != null)
					am.setAmmeterName(rs.getString(7));

				if (rs.getObject(8) != null)
					am.setAmmeterPassword(rs.getString(8));

				if (rs.getObject(9) != null)
					am.setAmmeterAddress485(rs.getString(9));

				if (rs.getObject(10) != null)
					am.setMaker(rs.getString(10));

				if (rs.getObject(11) != null)
					am.setMakerCode(rs.getString(11));

				if (rs.getObject(12) != null)
					am.setAssetNo(rs.getString(12));

				if (rs.getObject(13) != null)
					am.setIsSupply(rs.getInt(13));

				if (rs.getObject(14) != null)
					am.setzValue(rs.getDouble(14));

				if (rs.getObject(15) != null)
					am.setjValue(rs.getDouble(15));

				if (rs.getObject(16) != null)
					am.setfValue(rs.getDouble(16));

				if (rs.getObject(17) != null)
					am.setpValue(rs.getDouble(17));

				if (rs.getObject(18) != null)
					am.setgValue(rs.getDouble(18));

				if (rs.getObject(19) != null)
					am.setUseAmStyle(rs.getInt(19));

				if (rs.getObject(20) != null)
					am.setConsumerNum(rs.getString(20));

				if (rs.getObject(21) != null)
					am.setConsumerName(rs.getString(21));

				if (rs.getObject(22) != null)
					am.setConsumerPhone(rs.getString(22));

				if (rs.getObject(23) != null)
					am.setConsumerAddress(rs.getString(23));

				if (rs.getObject(24) != null)
					am.setIsImportantUser(rs.getInt(24));

				if (rs.getObject(25) != null)
					am.setIsCountMeter(rs.getInt(25));

				if (rs.getObject(26) != null)
					am.setIsComputation(rs.getInt(26));

				if (rs.getObject(27) != null)
					am.setAmmeterPlose(rs.getInt(27));

				if (rs.getObject(28) != null)
					am.setPartment(rs.getInt(28));

				if (rs.getObject(29) != null)
					am.setFloor(rs.getInt(29));

				if (rs.getObject(30) != null)
					am.setMeteStyleID(rs.getInt(30));

				if (rs.getObject(31) != null)
					am.setPriceID(rs.getInt(31));

				if (rs.getObject(32) != null)
					am.setAmmeterStat(rs.getInt(32));

				if (rs.getObject(33) != null)
					am.setIsOffAlarm(rs.getInt(33));

				if (rs.getObject(34) != null)
					am.setCurPower(rs.getDouble(34));

				if (rs.getObject(35) != null)
					am.setCostCheck(rs.getInt(35));

				if (rs.getObject(36) != null)
					am.setStandByCheck(rs.getInt(36));

				if (rs.getObject(37) != null)
					am.setXiuzheng(rs.getInt(37));

				if (rs.getObject(38) != null)
					am.setLastTime(rs.getString(38));

				if (rs.getObject("ParentID") != null){
					am.setParentID(rs.getInt("ParentID"));
					String parentName="";
					parentName=queryNameByID(rs.getInt("ParentID"));
					am.setParent_Name(parentName);
				}
				if (rs.getObject("beilv") != null)
					am.setBeilv(rs.getInt("beilv"));

				if (rs.getObject("Architecture_Name") != null)
					am.setArchName(rs.getString("Architecture_Name"));

				if (rs.getObject("Gather_Name") != null)
					am.setGatherName(rs.getString("Gather_Name"));

				if (rs.getObject("Area_Name") != null)
					am.setAreaName(rs.getString("Area_Name"));

				if (rs.getObject("USEAMXZ") != null)
					am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质

				if (rs.getObject("USEAMFX") != null)
					am.setUseStyleName(rs.getString("USEAMFX"));// 分项

				if (rs.getObject("USEAMYJZX") != null)
					am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项

				if (rs.getObject("PartmentName") != null)
					am.setPartmentName(rs.getString("PartmentName"));

				if (rs.getObject("meteStyle_Name") != null)
					am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

				if (rs.getObject("Meter_Name") != null)
					am.setMeterName(rs.getString("Meter_Name"));
				
				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;

	}

	/**
	 * 修改Ammeter_Num中的分项和一级子项信息
	 * 
	 * @param ammYJFX
	 *            AmmNum中的一级子项14和15位,一级子项包含了分项（14位）
	 * 
	 *            000025Fe05801 B1 0-->000025Fe05801 A2 0
	 * @return
	 * @throws SQLException
	 */
	public boolean editFXNum(int ammID, String ammYJZX) throws SQLException
	{
		// 这样不行，replace(x,y,z)返回值为将串X中的Y串用Z串替换后的结果字符串，如果Y有很多则都会修改
		// String newSql =
		// "Update Ammeter Set Ammeter_Num=replace(Ammeter_Num,substr(Ammeter_Num,14,1),'?') where Ammeter_ID="
		// + ammID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean b = false;
		String sql = "select Ammeter_Num from Ammeter where Ammeter_ID=" + ammID;
		String oldString = null;
		String newString = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				oldString = rs.getString("Ammeter_Num");
			}
			// 1到13位+ammYJFX（14和15位）+15位之后的
			newString = oldString.substring(0, 13) + ammYJZX + oldString.substring(15);
			String newSql = "Update Ammeter Set Ammeter_Num=? where Ammeter_ID=" + ammID;
//			Connection conn1 = null;
			PreparedStatement ps1 = null;
			try
			{
//				conn1 = ConnDB.getConnection();
				ps1 = conn.prepareStatement(newSql);
				ps1.setString(1, newString);
				b = (ps1.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release( ps1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return b;

	}

	/**
	 * 修改Ammeter_Num中的分项(不包括一级子项)
	 * 
	 * @param ammFX
	 *            AmmNum中的分项14 000025Fe05801 B 10-->000025Fe05801 A 10
	 * @return
	 * @throws SQLException
	 */
	public boolean editFX(int ammID, String ammFX) throws SQLException
	{
		// 这样不行，replace(x,y,z)返回值为将串X中的Y串用Z串替换后的结果字符串，如果Y有很多则都会修改
		// String newSql =
		// "Update Ammeter Set Ammeter_Num=replace(Ammeter_Num,substr(Ammeter_Num,14,1),'?') where Ammeter_ID="
		// + ammID;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean b = false;

		String sql = "select Ammeter_Num from Ammeter where Ammeter_ID=" + ammID;
		String oldString = null;
		String newString = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				oldString = rs.getString("Ammeter_Num");
			}
			// 1到13位+ammFX（14位）+14位之后的
			newString = oldString.substring(0, 13) + ammFX + oldString.substring(14);
//			Connection conn1 = null;
			PreparedStatement ps1 = null;
			String newSql = "Update Ammeter Set Ammeter_Num='" + newString + "' where Ammeter_ID=" + ammID;
			try
			{
//				conn1 = ConnDB.getConnection();
				ps1 = conn.prepareStatement(newSql);
				b = (ps1.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release( ps1);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return b;

	}

	public List<Gather> getAllAmmGather() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Gather> list = null;
		try
		{
			String sql = "select * from Gather where Gather_ID in (select Gather_ID from V_Ammeter group by Gather_ID)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Gather>();

				Gather gather = new Gather();
				gather.setGatherID(rs.getInt("Gather_ID"));
				gather.setAreaID(rs.getInt("Area_ID"));
				gather.setArchitectureID(rs.getInt("Architecture_ID"));
				gather.setDatasiteID(rs.getInt("DataSite_ID"));
				gather.setGatherState(rs.getInt("Gather_State"));
				gather.setGatherNum(rs.getString("Gather_Num"));
				gather.setGatherName(rs.getString("Gather_Name"));
				gather.setGatherAddress(rs.getString("Gather_Address"));
				gather.setGatherPw(rs.getString("Gather_PassWord"));
				gather.setInstallAddress(rs.getString("Gather_Anzhuangaddress"));
				gather.setFactory(rs.getString("Gather_Changshang"));
				gather.setVersion(rs.getString("Gather_Banben"));
				gather.setSize(rs.getString("Gather_Size"));
				gather.setProtocol(rs.getString("Protocol"));
				gather.setSendway(rs.getInt("Sendway"));
				gather.setLastTime(rs.getString("LastTime"));
				gather.setIp(rs.getString("Ip"));
				gather.setLastSetTime(rs.getString("LastSetTime"));
				gather.setLastSetMsg(rs.getString("LastSetMsg"));
				gather.setGatherStyle(rs.getInt("Gather_Style"));
				list.add(gather);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<Architecture> getAllAmmArch() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Architecture> list = null;

		try
		{
			String sql = "select * from Architecture where Architecture_ID in (select Architecture_ID from V_Ammeter group by Architecture_ID)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Architecture>();

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

				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Area> getAllAmmArea() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Area> list = null;

		try
		{
			String sql = "select * from Area where Area_ID in (select Area_ID from V_Ammeter group by Area_ID)";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Area>();

				Area area = new Area();
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
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<String> getAllAmmAddr() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = null;
		try
		{
			String sql = "select AmMeter_485Address from V_Ammeter group by AmMeter_485Address";

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				if (rs.getString("AmMeter_485Address") != null)
					list.add(rs.getString("AmMeter_485Address"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<String> getALlAmmName() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = null;
		try
		{
			String sql = "select Ammeter_Name from V_Ammeter group by Ammeter_Name";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				if (rs.getString("Ammeter_Name") != null)
					list.add(rs.getString("Ammeter_Name"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<String> getALlConsumeNum() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = null;
		try
		{
			String sql = "select ConsumerNum from V_Ammeter group by ConsumerNum";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<String>();

				if (rs.getString("ConsumerNum") != null)
					list.add(rs.getString("ConsumerNum"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<AmmeterModel> getAllCountMeter() throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AmmeterModel> list = new LinkedList<AmmeterModel>();
		try
		{
			String sql = "select * from V_Ammeter where IsCountMeter = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);

				list.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<AmmeterModel> EditAmStylesearch(int areaID, int archID, int floor, int partmentID, int priceID, int meterstyleID) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = buildEditAmStyleSql(areaID, archID, floor, partmentID, priceID, meterstyleID);
			// System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);
				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public Map<String, Object> queryPaginate(int areaID, int archID, int gatherID, String ammAddress, String ammeterName, String consumerNum,
			String sortLabel, boolean isAsc, int pageCount, int pageIndex) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
		AmmeterModel am = null;
		Map<String, Object> m = null;
		int total =0;
		int startCount = pageIndex*pageCount+1;
		int endCount = (pageIndex+1)*pageCount;
		String sql = "select * from (select ROWNUM RN ,A.*from ( ";
		sql+= buildSql(areaID, archID, gatherID, ammAddress, ammeterName, consumerNum, sortLabel, isAsc);
		sql+=" )A where ROWNUM<=?) where rn >=?";
		//sql = sql.replace("Ammeter", "V_Ammeter");			
		String countsql = buildSql(areaID, archID, gatherID, ammAddress, ammeterName, consumerNum, sortLabel, isAsc);
		countsql = countsql.replace("*", "count(*)");
		m = new HashMap<String, Object>();
//		System.out.println(sql);
//		System.out.println(startCount+" "+endCount);
//		System.out.println(countsql);
		try
		{
			
			conn = ConnDB.getConnection();
			/**
			 * 先获取记录条数
			 */
			ps = conn.prepareStatement(countsql);
			rs = ps.executeQuery();
			if(rs.next()){
				total = rs.getInt(1);
			}
			m.put("TotalCount", total);
			
			//m.put("TotalCount", getRecordCount());
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		try
		{
			/**
			 * 获取记录集
			 */
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, endCount);
			ps.setInt(2, startCount);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				am = buildInstance(rs);

				lst.add(am);
				
			}
			m.put("List", lst);
		}  catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		return m;
	}

	public String buildSql(int areaID, int archID, int gatherID, String ammAddress, String ammeterName, String consumerNum, String sortLabel,
			boolean isAsc)
	{
		
		String sql = "select * from V_Ammeter ";

		if (areaID != 0 || archID != 0 || gatherID != 0 || (ammAddress != null && !"".equals(ammAddress))
				|| (ammeterName != null && !"".equals(ammeterName)) || (consumerNum != null && !"".equals(consumerNum)))
		{
			sql += "where ";

			if (areaID != 0)
			{
				sql += "Area_ID = " + areaID;
			}

			if (archID != 0)
			{
				if (areaID != 0)
				{
					sql += " and";
				}

				sql += " Architecture_ID = " + archID;
			}

			if (gatherID != 0)
			{
				if (areaID != 0 || archID != 0)
				{
					sql += " and";
				}

				sql += " Gather_ID = " + gatherID;
			}

			if (ammAddress != null && !"".equals(ammAddress))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0)
				{
					sql += " and";
				}

				sql += " AmMeter_485Address like ('%" + ammAddress + "%') ";
			}

			if (ammeterName != null && !"".equals(ammeterName))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0 || (ammAddress != null && !"".equals(ammAddress)))
				{
					sql += " and";
				}

				sql += " AmMeter_Name like ('%" + ammeterName + "%') ";
			}

			if (consumerNum != null && !"".equals(consumerNum))
			{
				if (areaID != 0 || archID != 0 || gatherID != 0 || (ammAddress != null && !"".equals(ammAddress))
						|| (ammeterName != null && !"".equals(ammeterName)))
				{
					sql += " and";
				}

				sql += " ConsumerNum like ('%" + consumerNum + "%') ";
			}

		}

		if (sortLabel != null && !"".equals(sortLabel))
		{
			if (isAsc)
			{
				sql += " order by " + sortLabel + " asc";
			} else
			{
				sql += " order by " + sortLabel + " desc";
			}
		}
		return sql;
	}
	public String buildEditAmStyleSql(int areaID, int archID, int floor, int partmentID, int priceID, int meterstyleID)
	{
		String sql = "select * from v_Ammeter ";

		if (areaID != 0 || archID != 0 || floor != 0 || partmentID != 0 || priceID != 0 || meterstyleID != 0)
		{
			sql += "where ";

			if (areaID != 0)
			{
				sql += "Area_ID = " + areaID;
			}

			if (archID != 0)
			{
				if (areaID != 0)
				{
					sql += " and";
				}

				sql += " Architecture_ID = " + archID;
			}

			if (floor != 0)
			{
				if (areaID != 0 || archID != 0)
				{
					sql += " and";
				}

				sql += " floor = " + floor;
			}

			if (partmentID != 0)
			{
				if (areaID != 0 || archID != 0 || floor != 0)
				{
					sql += " and";
				}

				sql += " partment_ID= " + partmentID;
			}

			if (priceID != 0)
			{
				if (areaID != 0 || archID != 0 || floor != 0 || partmentID != 0)
				{
					sql += " and";
				}

				sql += " price_ID= " + priceID;
			}
			if (meterstyleID != 0)
			{
				if (areaID != 0 || archID != 0 || floor != 0 || partmentID != 0 || priceID != 0)
				{
					sql += " and";
				}
				sql += " Metestyle_ID= " + meterstyleID;
			}
		}
		sql += " ORDER BY Ammeter_ID";

		return sql;
	}

	private AmmeterModel buildInstance(ResultSet rs) throws SQLException
	{
		AmmeterModel am = new AmmeterModel();

		am.setAmmeterID(rs.getInt("AmMeter_ID"));
		am.setAmmeterPoint(rs.getInt("AmMeter_Point"));
		am.setAreaID(rs.getInt("Area_ID"));
		am.setArchitectureID(rs.getInt("Architecture_ID"));
		am.setGatherID(rs.getInt("Gather_ID"));
		am.setAmmeterNum(rs.getString("AmMeter_Num"));
		am.setAmmeterName(rs.getString("AmMeter_Name"));
		am.setAmmeterPassword(rs.getString("AmMeter_Password"));
		am.setAmmeterAddress485(rs.getString("AmMeter_485Address"));
		am.setMaker(rs.getString("Maker"));
		am.setMakerCode(rs.getString("MakerCode"));
		am.setAssetNo(rs.getString("AssetNo"));
		am.setIsSupply(rs.getInt("IsSupply"));
		am.setzValue(rs.getDouble("ZValue"));
		am.setjValue(rs.getDouble("JValue"));
		am.setfValue(rs.getDouble("FValue"));
		am.setpValue(rs.getDouble("PValue"));
		am.setgValue(rs.getDouble("GValue"));
		am.setUseAmStyle(rs.getInt("UseAmStyle"));
		am.setConsumerNum(rs.getString("ConsumerNum"));
		am.setConsumerName(rs.getString("ConsumerName"));
		am.setConsumerPhone(rs.getString("ConsumerPhone"));
		am.setConsumerAddress(rs.getString("ConsumerAddress"));
		am.setIsImportantUser(rs.getInt("IsImportantUser"));
		am.setIsCountMeter(rs.getInt("IsCountMeter"));
		am.setIsComputation(rs.getInt("isComputation"));
		am.setAmmeterPlose(rs.getInt("AmMeter_Plose"));
		am.setPartment(rs.getInt("Partment_ID"));
		am.setFloor(rs.getInt("Floor"));
		am.setMeteStyleID(rs.getInt("MeteStyle_ID"));
		am.setPriceID(rs.getInt("Price_ID"));
		am.setAmmeterStat(rs.getInt("AmMeter_Stat"));
		am.setIsOffAlarm(rs.getInt("IsOffAlarm"));
		am.setCurPower(rs.getDouble("CurPower"));
		am.setCostCheck(rs.getInt("CostCheck"));
		am.setStandByCheck(rs.getInt("StandByCheck"));
		am.setIsVCCheck(rs.getInt("IsVCCheck"));
		am.setXiuzheng(rs.getFloat("Xiuzheng"));
		am.setLastTime(rs.getString("LastTime"));
		am.setBeilv(rs.getInt("beilv"));
		am.setLimitPart(rs.getFloat("LIMITPART"));
		am.setParentID(rs.getInt("ParentID"));
		am.setDataFrom(rs.getInt("DataFrom"));
		
		am.setIsRecycle(rs.getInt("isRecycle"));

		// Ammeter新加的字段 但是没有加到V_Ammeter中
		// am.setApstate(rs.getString("APSTATE"));

		// am.setApybValue(rs.getFloat("APYBVALUE"));
		// am.setISzb(rs.getInt("ISZB"));
		 am.setApManCount(rs.getFloat("APMANCOUNT"));
		 am.setRoomManCount(rs.getFloat("ROOMMANCOUNT"));

		am.setArchName(rs.getString("Architecture_Name"));
		am.setGatherName(rs.getString("Gather_Name"));
		am.setGatherAddress(rs.getString("Gather_Address"));
		am.setAreaName(rs.getString("Area_Name"));
		am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质
		am.setUseStyleName(rs.getString("USEAMFX"));// 分项
		am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项
		am.setPartmentName(rs.getString("PartmentName"));
		am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型
		am.setVolBeilv(rs.getInt("VOlbeilv"));//电压倍率
		am.setColBeilv(rs.getInt("cOlbeilv"));//电流倍率
		return am;
	}

	/**
	 * 通过条件查询出所有电表【注意：此方法用于（批量设置）的列出电表】
	 * 
	 * @param areaID
	 *            区域ID
	 * @param archID
	 *            建筑ID
	 * @param floor
	 *            楼层
	 * @param ammID
	 *            电表ID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> searchAll(String sortName, boolean order, int areaID, int archID, int floor, int ammID, int pageCount,
			int pageIndex) throws SQLException
	{
		ArrayList<AmmeterModel> list = new ArrayList<AmmeterModel>();
		AmmeterModel am = null;
		String a, b, c, d;
		if (areaID == -1)
		{
			a = "'%'";
		} else
		{
			a = areaID + "";
		}

		if (archID == -1)
		{
			b = "'%'";
		} else
		{
			b = archID + "";
		}

		if (floor == -1)
		{
			c = "'%'";
		} else
		{
			c = floor + "";
		}

		if (ammID == -1)
		{
			d = "'%'";
		} else
		{
			d = ammID + "";
		}

		String sql = "";

		sql = "Select  * from V_Ammeter  where area_ID like " + a + " and Architecture_ID like " + b + " and floor like " + c
				+ " and Ammeter_ID like " + d;
		if (order)
			sql += " order by " + sortName + " asc";
		else
			sql += " order by " + sortName + " desc";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				am = new AmmeterModel();
				if (rs.getObject(1) != null)
					am.setAmmeterID(rs.getInt(1));

				if (rs.getObject(2) != null)
					am.setAmmeterPoint(rs.getInt(2));

				if (rs.getObject(3) != null)
					am.setAreaID(rs.getInt(3));

				if (rs.getObject(4) != null)
					am.setArchitectureID(rs.getInt(4));

				if (rs.getObject(5) != null)
					am.setGatherID(rs.getInt(5));

				if (rs.getObject(6) != null)
					am.setAmmeterNum(rs.getString(6));

				if (rs.getObject(7) != null)
					am.setAmmeterName(rs.getString(7));

				if (rs.getObject(8) != null)
					am.setAmmeterPassword(rs.getString(8));

				if (rs.getObject(9) != null)
					am.setAmmeterAddress485(rs.getString(9));

				if (rs.getObject(10) != null)
					am.setMaker(rs.getString(10));

				if (rs.getObject(11) != null)
					am.setMakerCode(rs.getString(11));

				if (rs.getObject(12) != null)
					am.setAssetNo(rs.getString(12));

				if (rs.getObject(13) != null)
					am.setIsSupply(rs.getInt(13));

				if (rs.getObject(14) != null)
					am.setzValue(rs.getDouble(14));

				if (rs.getObject(15) != null)
					am.setjValue(rs.getDouble(15));

				if (rs.getObject(16) != null)
					am.setfValue(rs.getDouble(16));

				if (rs.getObject(17) != null)
					am.setpValue(rs.getDouble(17));

				if (rs.getObject(18) != null)
					am.setgValue(rs.getDouble(18));

				if (rs.getObject(19) != null)
					am.setUseAmStyle(rs.getInt(19));

				if (rs.getObject(20) != null)
					am.setConsumerNum(rs.getString(20));

				if (rs.getObject(21) != null)
					am.setConsumerName(rs.getString(21));

				if (rs.getObject(22) != null)
					am.setConsumerPhone(rs.getString(22));

				if (rs.getObject(23) != null)
					am.setConsumerAddress(rs.getString(23));

				if (rs.getObject(24) != null)
					am.setIsImportantUser(rs.getInt(24));

				if (rs.getObject(25) != null)
					am.setIsCountMeter(rs.getInt(25));

				if (rs.getObject(26) != null)
					am.setIsComputation(rs.getInt(26));

				if (rs.getObject(27) != null)
					am.setAmmeterPlose(rs.getInt(27));

				if (rs.getObject(28) != null)
					am.setPartment(rs.getInt(28));

				if (rs.getObject(29) != null)
					am.setFloor(rs.getInt(29));

				if (rs.getObject(30) != null)
					am.setMeteStyleID(rs.getInt(30));

				if (rs.getObject(31) != null)
					am.setPriceID(rs.getInt(31));

				if (rs.getObject(32) != null)
					am.setAmmeterStat(rs.getInt(32));

				if (rs.getObject(33) != null)
					am.setIsOffAlarm(rs.getInt(33));

				if (rs.getObject(34) != null)
					am.setCurPower(rs.getDouble(34));

				if (rs.getObject(35) != null)
					am.setCostCheck(rs.getInt(35));

				if (rs.getObject(36) != null)
					am.setStandByCheck(rs.getInt(36));

				if (rs.getObject(37) != null)
					am.setXiuzheng(rs.getInt(37));

				if (rs.getObject(38) != null)
					am.setLastTime(rs.getString(38));

				if (rs.getObject("ParentID") != null){
					am.setParentID(rs.getInt("ParentID"));
					String partment_Name="";
					partment_Name=queryNameByID(rs.getInt("ParentID"));
					am.setParent_Name(partment_Name);
				}

				if (rs.getObject("beilv") != null)
					am.setBeilv(rs.getInt("beilv"));

				if (rs.getObject("Architecture_Name") != null)
					am.setArchName(rs.getString("Architecture_Name"));

				if (rs.getObject("Gather_Name") != null)
					am.setGatherName(rs.getString("Gather_Name"));

				if (rs.getObject("Area_Name") != null)
					am.setAreaName(rs.getString("Area_Name"));

				if (rs.getObject("USEAMXZ") != null)
					am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质

				if (rs.getObject("USEAMFX") != null)
					am.setUseStyleName(rs.getString("USEAMFX"));// 分项

				if (rs.getObject("USEAMYJZX") != null)
					am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项

				if (rs.getObject("PartmentName") != null)
					am.setPartmentName(rs.getString("PartmentName"));

				if (rs.getObject("meteStyle_Name") != null)
					am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

				if (rs.getObject("Meter_Name") != null)
					am.setMeterName(rs.getString("Meter_Name"));

				list.add(am);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public boolean checkAddRepeat(AmmeterModel am) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select Ammeter_ID from Ammeter where " + "(Ammeter_Point = ? or AMMETER_485ADDRESS = ?) "
					+ "and GATHER_ID = ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, am.getAmmeterPoint());
			ps.setString(2, am.getAmmeterAddress485());
			ps.setInt(3, am.getGatherID());
			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}

	public boolean checkUpdateRepeat(AmmeterModel am) throws SQLException
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select Ammeter_ID from Ammeter where " + "(Ammeter_Point = ? or AMMETER_485ADDRESS = ?) "
					+ "and GATHER_ID = ? and Ammeter_ID != ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, am.getAmmeterPoint());
			ps.setString(2, am.getAmmeterAddress485());
			ps.setInt(3, am.getGatherID());
			ps.setInt(4, am.getAmmeterID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return flag;
	}

	public boolean checkAmAddrExist(String addr) throws SQLException
	{
		boolean flag = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select Ammeter_ID from Ammeter where " + "AMMETER_485ADDRESS = ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, addr);
			rs = ps.executeQuery();

			if (rs.next())
			{
				flag = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return flag;
	}
	public String queryAddrById(int id) throws SQLException
	{
		String AMMETER_485ADDRESS = "";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select AMMETER_485ADDRESS from Ammeter where " + "Ammeter_ID = ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				AMMETER_485ADDRESS = rs.getString("AMMETER_485ADDRESS");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return AMMETER_485ADDRESS;
	}
	
	public int queryIdByAddr(String addr) throws SQLException
	{
		int ammeterId = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select Ammeter_ID from Ammeter where " + "AMMETER_485ADDRESS = ? and RowNum = 1";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, addr);
			rs = ps.executeQuery();

			if (rs.next())
			{
				ammeterId = rs.getInt("Ammeter_ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return ammeterId;
	}
	public String queryNameById(int id) throws SQLException
	{
		String ammeter_name = "";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select ammeter_name from Ammeter where " + "Ammeter_ID = ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				ammeter_name = rs.getString("ammeter_name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return ammeter_name;
	}
	public String queryMeterNameById(int id) throws SQLException
	{
		String ammeter_name = "";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select meter_name from v_Ammeter where " + "Ammeter_ID = ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				ammeter_name = rs.getString("meter_name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return ammeter_name;
	}
	
	public String queryPriceValueById(int id) throws SQLException
	{
		String PRICE_VALUE ="0";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT PRICE_VALUE from AMMETER a,PRICE p where AMMETER_ID=? and a.PRICE_ID = p.PRICE_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				PRICE_VALUE = rs.getString("PRICE_VALUE");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return PRICE_VALUE;
	}
	public Map<String, Integer> getAmMapByName() throws SQLException
	{

		Map<String, Integer> map = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT Ammeter_ID,Ammeter_Name FROM AmMeter";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				if (map == null)
				{
					map = new HashMap<String, Integer>();
				}

				map.put(rs.getString("Ammeter_Name"), rs.getInt("Ammeter_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return map;
	}

	/**
	 * 根据网关ID查询电表
	 * 
	 * @param gatherID
	 * @return
	 * @throws SQLException
	 */
	public List<AmmeterModel> queryByGatherID(int gatherID) throws SQLException
	{
		List<AmmeterModel> lst = new LinkedList<AmmeterModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM V_Ammeter where GATHER_ID = ? ORDER BY Ammeter_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gatherID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AmmeterModel am = buildInstance(rs);
				lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return lst;
	}

	public void updateBeiLv(int meterStyleID, int newBeiLv) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update Ammeter set BeiLv = ? where MeteStyle_ID = ?";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, newBeiLv);
			ps.setInt(2, meterStyleID);

			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

	}

	public int queryPriceIdByPriceName(String PriceName) throws SQLException
	{

		int priceid = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select price_id from PRICE where PRICE_NAME='" + PriceName + "'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
			rs = ps.executeQuery();

			while (rs.next())
			{
				priceid = rs.getInt("price_id");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return priceid;
	}
	public String queryPriceNameByID(int PriceID) throws SQLException
	{

		String price_Name = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select price_Name from PRICE where PRICE_ID='" + PriceID + "'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
			rs = ps.executeQuery();

			while (rs.next())
			{
				price_Name = rs.getString("price_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return price_Name;
	}
	public int queryUseAmStyleIdByStyleName(String StyleName) throws SQLException
	{

		int priceid = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select DICTIONARYVALUE_NUM from DictionaryValue where Dictionary_ID=6 and DICTIONARYVALUE_VALUE='" + StyleName + "'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.executeUpdate();
			rs = ps.executeQuery();

			while (rs.next())
			{
				priceid = rs.getInt("DICTIONARYVALUE_NUM");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return priceid;
	}

	public boolean editAmmeterstyle(List<Integer> amIDList, int meterstyleid)
	{
		boolean flag = false;

		String sql = "UPDATE AMMETER SET MeteStyle_ID=?" + "where Ammeter_ID in (";
		Connection conn0 = null;
		PreparedStatement ps0 = null;
		for (int i = 0; i < amIDList.size(); i++)
		{
			sql += amIDList.get(i);
			if (i < amIDList.size() - 1)
				sql += ",";
			else
				sql += ")";
		}
		try
		{
			conn0 = ConnDB.getConnection();
			ps0 = conn0.prepareStatement(sql);
			ps0.setInt(1, meterstyleid);
			if (ps0.executeUpdate() == amIDList.size())
				flag = true;
			else
				flag = false;
		} catch (Exception e2)
		{
			e2.printStackTrace();
		} finally
		{
			ConnDB.release(conn0, ps0);
		}
		return flag;
	}

	public List<Department> getAllDepartment()
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> list = null;

		try
		{
			String sql = "select * from partment where partment_id in (select partment_id from V_Ammeter group by partment_id)";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<Department>();

				Department dep = new Department();

				dep.setDepartmentID(rs.getInt("partment_ID"));
				dep.setDepartmetName(rs.getString("partment_Name"));
				list.add(dep);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public List<Architecture> queryArchByAreaID(int areaID) throws SQLException
	{
		Architecture arch = null;
		ArchitectureDao architectureDao = new ArchitectureDao();
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "select * from Architecture where Architecture_ID in (select Architecture_ID from V_Ammeter group by Architecture_ID) and Area_ID = ? ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = architectureDao.buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public JSONArray getFirstFXByFXNum(String fenXiangNum)
	{
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		
		String sql = "select DictionaryValue_Num as Num,DictionaryValue_Value as Text from DictionaryValue where Dictionary_ID=8 and DictionaryValue_Num like('"+fenXiangNum+"%')";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("Num", rs.getString("Num"));
				jo.put("Text", rs.getString("Text"));
				json.add(jo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return json;
	}

	public void updateAmMeterDatasWithChangeXiuzheng(int ammeterID,float oldXiuZheng,float newXiuZheng)
	{
		String sql = "update ZAMDATAS"+ammeterID+" set ZVALUEZY=(ZVALUEZY-"+oldXiuZheng+"+ "+newXiuZheng+")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
	}

	public void updateAmMeterDatasWithChangeBeiLV(int ammeterID, int oldBeiLv, int newBeilv, float xiuzheng)
	{
		String sql = "update ZAMDATAS"+ammeterID+" set ZVALUEZY=((ZVALUEZY-"+xiuzheng+")/"+oldBeiLv+"*"+newBeilv+"+"+xiuzheng+")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
	}
	public void updateAmMeterWithLasttimeandState(int ammeterID,String lasttime)
	{
		String sql = "update ammeter set lasttime=?,ammeter_stat=1 where ammeter_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, lasttime);
			ps.setInt(2, ammeterID);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
	}
	public boolean checkIsKaiHu(int ammeterId){
		boolean b =false;
		String sql = "select apstate from ammeter where ammeter_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ammeterId);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getString(1).equals("开户")){
					b = true;
				}
			}
		} catch (Exception e)
		{
			b = false;
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return b;
	}
	
	/**
	 * 查询 v_Ammeter 中指定字段
	 * @param id
	 * @param fields
	 * @return
	 * @throws SQLException
	 */
	public String[] queryFieldsById(int id,String ...fields) throws SQLException
	{
		String[] r = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String file ="";
			if(fields.length<=0){
				return null;
			}else{
				r = new String[fields.length];
				for(int i =0;i<fields.length;i++){
					if(i!=fields.length-1){
						file+= fields[i]+",";
					}else{
						file+=fields[i];
					}
				}
			}
			String sql = "select "+file+" from v_Ammeter where " + "Ammeter_ID = ? ";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				for(int i =0;i<fields.length;i++){
					r[i] = rs.getString(fields[i]);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return r;
	}

	public List<Architecture> queryArchByAreaIDWithoutAmmeter(int areaID) throws SQLException
	{
		Architecture arch = null;
		ArchitectureDao architectureDao = new ArchitectureDao();
		List<Architecture> list = new ArrayList<Architecture>();
		String sql = "select * from Architecture where Area_ID = ? ";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				arch = architectureDao.buildInstance(rs);
				list.add(arch);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
}
