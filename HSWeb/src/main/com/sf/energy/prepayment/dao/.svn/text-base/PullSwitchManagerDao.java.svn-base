package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.ehcache.search.Results;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.model.PDPartInfo03HLModel;
import com.sf.energy.prepayment.model.PullSwitchManagerModel;
import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.util.ConnDB;

public class PullSwitchManagerDao
{
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
	 * 增
	 * 
	 * @param psmm
	 * @param heZhaDate
	 * @param laZhaDate
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(PullSwitchManagerModel psmm, String laZhaDate, String heZhaDate) throws SQLException
	{
		String sql = "insert into AmMeterLaHeZha(planNum,planName,laZha,heZha,biaoJiType,caoZuoHuiLu,zhiXingType,choose,beiZhu)"
				+ " Values(?,?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, psmm.getPlanNum());
			ps.setString(2, psmm.getPlanName());
			ps.setString(3, psmm.getLaZha());
			ps.setString(4, psmm.getHeZha());
			ps.setString(5, psmm.getBiaoJiType());
			ps.setString(6, psmm.getCaoZuoHuiLu());
			ps.setString(7, psmm.getZhiXingType());
			ps.setString(8, psmm.getChoose());
			ps.setString(9, psmm.getBeiZhu());
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		// PreparedStatement ps;
		// ps = ConnDB.getConnection().prepareStatement(sql);
		//
		//
		//
		// boolean b = (ps.executeUpdate() == 1);
		//
		// if (ps != null)
		// ps.close();

		String dr = "select nvl(Max(ID),0)ID from AmMeterLaHeZha";
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String planID = null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(dr);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				planID = rs1.getString("ID");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn1, ps1, rs1);
		}

		boolean ChkIsLaZha = false;
		boolean ChkIsHeZha = false;
		if ("1".equals(psmm.getLaZha()))
			ChkIsLaZha = true;
		if ("1".equals(psmm.getHeZha()))
			ChkIsHeZha = true;

		oprateAmmeterJihua(psmm.getZhiXingType(), psmm.getChoose(), psmm.getBiaoJiType(), planID, psmm.getPlanNum(), ChkIsLaZha, ChkIsHeZha,
				laZhaDate, heZhaDate, psmm.getCaoZuoHuiLu());

		return b;
	}

	// ddlBiaoJiType 电费类型
	// 执行类型： ddlZhiXingType
	private void oprateAmmeterJihua(String ddlZhiXingType, String hidChooseID, String ddlBiaoJiType, String planID, String planNum,
			boolean ChkIsLaZha, boolean ChkIsHeZha, String laZhaDate, String heZhaDate, String CaoZuoHuiLu) throws SQLException
	{
		if (hidChooseID != null && !"".equalsIgnoreCase(hidChooseID))
		{
			hidChooseID = hidChooseID.substring(0, hidChooseID.length() - 1);
		}
		if ("All".equalsIgnoreCase(ddlZhiXingType))
		{
			if ("0".equalsIgnoreCase(ddlBiaoJiType))
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter";
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}
				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// // System.out.println(dr);
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			} else
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where Price_ID=" + ddlBiaoJiType;
				// System.out.println(dr);
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}

				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			}
		}
		if ("Area".equalsIgnoreCase(ddlZhiXingType))
		{

			if ("0".equalsIgnoreCase(ddlBiaoJiType))
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where Area_ID in (" + hidChooseID + ")";
				// System.out.println(dr);
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}

				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			} else
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where Area_ID in (" + hidChooseID + ") and Price_ID=" + ddlBiaoJiType;
				// System.out.println(dr);
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}
				//
				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			}

		}
		if ("Arc".equalsIgnoreCase(ddlZhiXingType))
		{

			if ("0".equalsIgnoreCase(ddlBiaoJiType))
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where Architecture_ID in (" + hidChooseID + ")";
				// System.out.println(dr);
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}
				//
				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			} else
			{
				String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where Architecture_ID in (" + hidChooseID + ") and Price_ID="
						+ ddlBiaoJiType;
				// System.out.println(dr);
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try
				{
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(dr);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String meter_ID = rs.getString("AmMeter_ID");
						String meter_485Address = rs.getString("AmMeter_485Address");
						insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn, ps, rs);
				}

				// PreparedStatement ps = null;
				// ResultSet rs = null;
				// ps = ConnDB.getConnection().prepareStatement(dr);
				// rs = ps.executeQuery();
				// while (rs.next())
				// {
				// String meter_ID = rs.getString("AmMeter_ID");
				// String meter_485Address = rs
				// .getString("AmMeter_485Address");
				// insertAmmeterJiHua(planID, planNum, meter_ID,
				// meter_485Address, ChkIsLaZha, ChkIsHeZha,
				// laZhaDate, heZhaDate, CaoZuoHuiLu);
				// }
				// Closeable(rs, ps);
			}

		}
		if ("Meter".equalsIgnoreCase(ddlZhiXingType))
		{
			String dr = "select AmMeter_ID,AmMeter_485Address from AmMeter where AmMeter_ID in (" + hidChooseID + ")";
			// System.out.println(dr);
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(dr);
				rs = ps.executeQuery();
				while (rs.next())
				{
					String meter_ID = rs.getString("AmMeter_ID");
					String meter_485Address = rs.getString("AmMeter_485Address");
					insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address, ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate, CaoZuoHuiLu);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

			// PreparedStatement ps = null;
			// ResultSet rs = null;
			// ps = ConnDB.getConnection().prepareStatement(dr);
			// rs = ps.executeQuery();
			// while (rs.next())
			// {
			// String meter_ID = rs.getString("AmMeter_ID");
			// String meter_485Address = rs.getString("AmMeter_485Address");
			// insertAmmeterJiHua(planID, planNum, meter_ID, meter_485Address,
			// ChkIsLaZha, ChkIsHeZha, laZhaDate, heZhaDate,
			// CaoZuoHuiLu);
			// }
			// Closeable(rs, ps);

		}
	}

	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException
	{
		String sql = "delete FROM AmMeterLaHeZha where ID =  " + id;
		// PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		// boolean b = (ps.executeUpdate() == 1);
		// if (ps != null)
		// ps.close();
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
		deleteAmmeterJiHua(String.valueOf(id));
		return b;
	}

	/**
	 * 改
	 * 
	 * @param psmm
	 * @param heZhaDate
	 * @param laZhaDate
	 * @return
	 * @throws SQLException
	 */
	public boolean update(PullSwitchManagerModel psmm, String laZhaDate, String heZhaDate) throws SQLException
	{
		String sql = "update AmMeterLaHeZha set " + "planNum=?,planName=?,laZha=?,heZha=?,biaoJiType=?,caoZuoHuiLu=?,zhiXingType=?,choose=?,beiZhu=?"
				+ " where ID=" + psmm.getId();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, psmm.getPlanNum());
			ps.setString(2, psmm.getPlanName());
			ps.setString(3, psmm.getLaZha());
			ps.setString(4, psmm.getHeZha());
			ps.setString(5, psmm.getBiaoJiType());
			ps.setString(6, psmm.getCaoZuoHuiLu());
			ps.setString(7, psmm.getZhiXingType());
			ps.setString(8, psmm.getChoose());
			ps.setString(9, psmm.getBeiZhu());
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
	
		String planID = String.valueOf(psmm.getId());
		deleteAmmeterJiHua(planID);

		boolean ChkIsLaZha = false;
		boolean ChkIsHeZha = false;
		if ("1".equals(psmm.getLaZha()))
			ChkIsLaZha = true;
		if ("1".equals(psmm.getHeZha()))
			ChkIsHeZha = true;
		oprateAmmeterJihua(psmm.getZhiXingType(), psmm.getChoose(), psmm.getBiaoJiType(), planID, psmm.getPlanNum(), ChkIsLaZha, ChkIsHeZha,
				laZhaDate, heZhaDate, psmm.getCaoZuoHuiLu());

		return b;
	}

	/**
	 * 查询全部信息
	 * 
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PullSwitchManagerModel> queryInfo(int pageCount, int pageIndex, String order) throws SQLException
	{
		ArrayList<PullSwitchManagerModel> list = new ArrayList<PullSwitchManagerModel>();
		PullSwitchManagerModel psmm = null;

		String sql = "select ID,planNum,planName,laZha,heZha,biaoJiType,CAOZUOHUILU,ZHIXINGTYPE from AmMeterLaHeZha" + order;
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
			setTotalCount(count);
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
				psmm = new PullSwitchManagerModel();

				psmm.setId(rs.getInt("ID"));
				psmm.setPlanNum(rs.getString("planNum"));
				psmm.setPlanName(rs.getString("planName"));

				String laZha = "";
				laZha = rs.getString("laZha");
				if ("1".equals(laZha))
				{
					laZha = "是";
				} else
				{
					laZha = "否";
				}
				psmm.setLaZha(laZha);

				String heZha = "";
				heZha = rs.getString("heZha");
				if ("1".equals(heZha))
				{
					heZha = "是";
				} else
				{
					heZha = "否";
				}
				psmm.setHeZha(heZha);

				String biaoJiTypeName = "";
				biaoJiTypeName = rs.getString("biaoJiType");
				if ("0".equals(biaoJiTypeName))
				{
					biaoJiTypeName = "全部";
				} else
				{
					String sql1 = "select Price_Name from Price where Price_ID=" + rs.getString("biaoJiType");
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
//					Connection connz = null;
					try
					{
//						connz = ConnDB.getConnection();
						ps1 = conn.prepareStatement(sql1);
						rs1 = ps1.executeQuery();
						if (rs1.next())
						{
							biaoJiTypeName = rs1.getString("Price_Name");
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						ConnDB.release( ps1, rs1);
					}
				}
				psmm.setBiaoJiType(biaoJiTypeName);

				String caoZuoHuiLu = "";
				caoZuoHuiLu = rs.getString("CAOZUOHUILU");
				if ("0".equals(caoZuoHuiLu))
				{
					caoZuoHuiLu = "所有回路";
				} else if ("1".equals(caoZuoHuiLu))
				{
					caoZuoHuiLu = "空调回路";
				} else if ("2".equals(caoZuoHuiLu))
				{
					caoZuoHuiLu = "照明回路";
				}
				psmm.setCaoZuoHuiLu(caoZuoHuiLu);

				String zhiXingType = "";
				zhiXingType = rs.getString("ZHIXINGTYPE");
				if ("All".equals(zhiXingType))
				{
					zhiXingType = "全部";
				} else if ("Area".equals(zhiXingType))
				{
					zhiXingType = "区域";
				} else if ("Arc".equals(zhiXingType))
				{
					zhiXingType = "建筑";
				} else if ("Meter".equals(zhiXingType))
				{
					zhiXingType = "房间";
				}
				psmm.setZhiXingType(zhiXingType);

				list.add(psmm);
				count--;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	/**
	 * 根据ID查询分合闸信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public PullSwitchManagerModel queryByID(int id) throws SQLException
	{
		PullSwitchManagerModel psmm = new PullSwitchManagerModel();
		String sql = "Select AmMeterLaHeZha.* ,(select lazhadate from ammeterjihua where planid=" + id
				+ " and rownum=1)lazhadate,(select hezhadate from ammeterjihua where planid=" + id
				+ " and rownum=1)hezhadate from AmMeterLaHeZha  where ID=" + id + "";
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
				psmm.setId(rs.getInt("ID"));
				psmm.setPlanNum(rs.getString("planNum"));
				psmm.setPlanName(rs.getString("planName"));
				psmm.setLaZha(rs.getString("laZha"));
				psmm.setHeZha(rs.getString("heZha"));
				psmm.setBiaoJiType(rs.getString("biaoJiType"));
				psmm.setCaoZuoHuiLu(rs.getString("CAOZUOHUILU"));
				psmm.setZhiXingType(rs.getString("ZHIXINGTYPE"));
				psmm.setChoose(rs.getString("CHOOSE"));
				psmm.setBeiZhu(rs.getString("BEIZHU"));
				psmm.setLazhadate(rs.getString("lazhadate"));
				psmm.setHezhadate(rs.getString("hezhadate"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return psmm;
	}

	/**
	 * 查询电费类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getDianFeiLeiXingName() throws SQLException
	{
		String sql1 = "select distinct 0 as Price_ID,'全部' as Price_Name from Price union all select Price_ID,Price_Name from Price where Price_Style='E'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			while (rs.next())
			{
				jo = new JSONObject();
				jo.put("Price_ID", rs.getString("Price_ID"));
				jo.put("Price_Name", rs.getString("Price_Name"));
				json.add(jo);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return json;
	}

	/**
	 * 查询树的方法
	 * 
	 * @param style
	 * @return
	 * @throws SQLException
	 */
	public JSONArray getXuanXiangOption(int style, int userID) throws SQLException
	{
		JSONObject jo = null;
		JSONArray json = new JSONArray();

		// 获得根
		PD_PartDao rootDao = new PD_PartDao();
		String root = rootDao.getRoot();
		jo = new JSONObject();
		jo.put("treeID", "0");
		jo.put("parentID", "0");
		jo.put("style", "0");
		jo.put("id", "0");
		jo.put("Name", root);
		json.add(jo);

		AreaDao area = new AreaDao();
		ArchitectureDao archDao = new ArchitectureDao();
		AmmeterDao ammDao = new AmmeterDao();

		ArrayList<Area> areaList = (ArrayList<Area>) area.display(userID);

		int treeID = 0;
		int parent;
		int parent2;
		// 获得区域
		for (Area area1 : areaList)
		{
			jo = new JSONObject();
			int areaID = area1.getId();
			String areaName = area1.getName();
			treeID++;
			jo.put("treeID", treeID);
			jo.put("parentID", "0");
			jo.put("style", "1");
			jo.put("id", areaID);
			jo.put("Name", areaName);
			json.add(jo);
			if (style > 1)
			{
				// 获得建筑
				parent = treeID;
				ArrayList<Architecture> archList = (ArrayList<Architecture>) archDao.queryArchByAreaID(areaID, userID);

				for (Architecture arch : archList)
				{
					jo = new JSONObject();
					int archID = arch.getId();
					String arcName = arch.getName();
					treeID++;
					jo.put("treeID", treeID);
					jo.put("parentID", parent);
					jo.put("style", "2");
					jo.put("id", archID);
					jo.put("Name", arcName);
					json.add(jo);
					if (style > 2)
					{
						// 获得电表
						parent2 = treeID;
						List<AmmeterModel> ammList = ammDao.queryByArchID(archID);

						for (AmmeterModel amm : ammList)
						{
							jo = new JSONObject();
							int ammID = amm.getAmmeterID();
							String ammName = amm.getAmmeterName();
							treeID++;
							jo.put("treeID", treeID);
							jo.put("parentID", parent2);
							jo.put("style", "3");
							jo.put("id", ammID);
							jo.put("Name", ammName);
							json.add(jo);

						}
					}
				}

			}
		}

		return json;
	}

	public JSONArray getOptionContent(int style, int id, int priceID) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if (style == 1)
		{
			// 区域
			String sql = "";
			if (priceID == 0)
			{
				sql = "select distinct area_name,area_num ,(select count(*) from v_ammeter where area_id=" + id
						+ ")num  from v_ammeter where area_id=" + id;
			} else
			{
				sql = "select distinct area_name,area_num ,(select count(*) from v_ammeter where area_id=" + id + " and price_id=" + priceID
						+ ")num  from v_ammeter where area_id=" + id;
			}

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
					jo.put("id", id);
					jo.put("name", rs.getString("area_name"));
					jo.put("num", rs.getString("area_num"));
					jo.put("number", rs.getString("num"));
					json.add(jo);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

			// PreparedStatement
			// ps=ConnDB.getConnection().prepareStatement(sql);
			// ResultSet rs=ps.executeQuery();
			// while(rs.next())
			// {
			// jo=new JSONObject();
			// jo.put("id",id);
			// jo.put("name",rs.getString("area_name"));
			// jo.put("num", rs.getString("area_num"));
			// jo.put("number", rs.getString("num"));
			// json.add(jo);
			// }
			// if (rs != null)
			// rs.close();
			// if (ps != null)
			// ps.close();
			//
		} else if (style == 2)
		{
			// 建筑
			String sql = "";
			if (priceID == 0)
			{
				sql = "select distinct architecture_name,architecture_num ,(select count(*) from v_ammeter where architecture_id=" + id
						+ ")num  from v_ammeter where architecture_id=" + id;
			} else
			{
				sql = "select distinct architecture_name,architecture_num ,(select count(*) from v_ammeter where architecture_id=" + id
						+ " and price_id=" + priceID + ")num  from v_ammeter where architecture_id=" + id;
			}

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
					jo.put("id", id);
					jo.put("name", rs.getString("architecture_name"));
					jo.put("num", rs.getString("architecture_num"));
					jo.put("number", rs.getString("num"));
					json.add(jo);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

			// PreparedStatement
			// ps=ConnDB.getConnection().prepareStatement(sql);
			// ResultSet rs=ps.executeQuery();
			// while(rs.next())
			// {
			// jo=new JSONObject();
			// jo.put("id",id);
			// jo.put("name",rs.getString("architecture_name"));
			// jo.put("num", rs.getString("architecture_num"));
			// jo.put("number", rs.getString("num"));
			// json.add(jo);
			// }
			// if (rs != null)
			// rs.close();
			// if (ps != null)
			// ps.close();
			//
		} else if (style == 3)
		{
			// 房间
			String sql = "";
			if (priceID == 0)
			{
				sql = "select distinct ammeter_name,ammeter_num ,(AMMETER_485ADDRESS)num  from v_ammeter where ammeter_id=" + id;
			} else
			{
				sql = "select distinct ammeter_name,ammeter_num ,(AMMETER_485ADDRESS)num  from v_ammeter where ammeter_id=" + id + " and price_id="
						+ priceID;
			}

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
					jo.put("id", id);
					jo.put("name", rs.getString("ammeter_name"));
					jo.put("num", rs.getString("ammeter_num"));
					jo.put("number", rs.getString("num"));
					json.add(jo);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn, ps, rs);
			}

			// PreparedStatement
			// ps=ConnDB.getConnection().prepareStatement(sql);
			// ResultSet rs=ps.executeQuery();
			// while(rs.next())
			// {
			// jo=new JSONObject();
			// jo.put("id",id);
			// jo.put("name",rs.getString("ammeter_name"));
			// jo.put("num", rs.getString("ammeter_num"));
			// jo.put("number", rs.getString("num"));
			// json.add(jo);
			// }
			// if (rs != null)
			// rs.close();
			// if (ps != null)
			// ps.close();
		}
		return json;
	}

	/**
	 * 插入ammeterjihua 表
	 * 
	 * @param planID
	 * @param planNum
	 * @param meter_ID
	 * @param meter_485Address
	 * @param ChkIsLaZha
	 * @param ChkIsHeZha
	 * @param laZhaDate
	 * @param heZhaDate
	 * @param CaoZuoHuiLu
	 * @return
	 * @throws SQLException
	 */
	int insertAmmeterJiHua(String planID, String planNum, String meter_ID, String meter_485Address, boolean ChkIsLaZha, boolean ChkIsHeZha,
			String laZhaDate, String heZhaDate, String CaoZuoHuiLu) throws SQLException
	{
		String sql1 = "insert into AmMeterJiHua(planID,planNum,meter_ID,meter_485Address,laZha,heZha,laZhaDate,heZhaDate,caoZuoHuiLu,state,times,lastTime) values("
				+ planID
				+ ",'"
				+ planNum
				+ "',"
				+ meter_ID
				+ ",'"
				+ meter_485Address
				+ "',"
				+ (ChkIsLaZha ? 1 : 2)
				+ ","
				+ (ChkIsHeZha ? 1 : 2)
				+ ",'" + laZhaDate + "','" + heZhaDate + "','" + CaoZuoHuiLu + "','00',0,'')";
		// System.out.println(sql1);
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			result = ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}

		// PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql1);
		// int result=ps.executeUpdate();
		// if (ps!=null)
		// {
		// ps.close();
		// }

		return result;

	}

	/**
	 * 删除ammeterjihua 表
	 * 
	 * @param planID
	 * @return
	 * @throws SQLException
	 */
	int deleteAmmeterJiHua(String planID) throws SQLException
	{
		String sql1 = "delete from AmMeterJiHua where planID=" + planID;
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql1);
			result = ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		// PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql1);
		// int result=ps.executeUpdate();
		// if (ps!=null)
		// {
		// ps.close();
		// }
		//
		return result;
	}

	// // 关闭游标
	// private void Closeable(ResultSet rs, PreparedStatement ps)
	// throws SQLException
	// {
	// if (rs != null)
	// {
	// rs.close();
	// }
	// if (ps != null)
	// {
	// ps.close();
	//
	// }
	//
	// }

}
