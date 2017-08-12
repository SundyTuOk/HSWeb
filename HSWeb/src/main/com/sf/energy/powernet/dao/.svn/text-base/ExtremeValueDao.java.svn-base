package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.model.ExtremeModel;
import com.sf.energy.util.ConnDB;

public class ExtremeValueDao
{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ExtremeModel getPDPartByID(int pdPartID) throws SQLException
	{
		ExtremeModel em = null;
		String sql = "select PART_PARENT,PART_NAME from PD_Part where PART_ID =? and PARTSTYLE_ID = 03";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, pdPartID);

			rs = ps.executeQuery();

			if (rs.next())
			{
				em = new ExtremeModel();

				em.setPdPartID(pdPartID);
				em.setPdpartParentID(rs.getInt("PART_PARENT"));
				em.setName(rs.getString("PART_NAME"));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return em;
	}

	public List<ExtremeModel> getExtremeValueBetween(int pdPartID, Date start,
			Date end) throws SQLException
	{

		List<ExtremeModel> list = null;

		String sql="select b.JLID ammeterID,a.Part_Name partname from "
				+ "PD_PartInfo03 b, (select Part_ID,Part_Name from PD_Part where "
				+ "PartStyle_ID = 03 and Part_Parent = ? or Part_ID = ?) a where "
				+ "b.Part_ID = a.Part_ID";
		Connection conn1 =null;
		PreparedStatement ps1=null;
		ResultSet rs1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql);
			ps1.setInt(1, pdPartID);
			ps1.setInt(2, pdPartID);
			rs1 = ps1.executeQuery();

			while (rs1.next())
			{
				String ammeterid=rs1.getString("ammeterID");
				String partname=rs1.getString("partname");
				if ((ammeterid!=null)&&(!"".equals(ammeterid)))
				{
					sql = "select max(PowerZY) maxPowerZY,min(PowerZY) minPowerZY,"
							+ "max(PowerAY) maxPowerAY,min(PowerAY) minPowerAY,"
							+ "max(PowerBY) maxPowerBY,min(PowerBY) minPowerBY,"
							+ "max(PowerCY) maxPowerCY,min(PowerCY) minPowerCY,"
							+ "max(PowerZW) maxPowerZW,min(PowerZW) minPowerZW,"
							+ "max(PowerAW) maxPowerAW,min(PowerAW) minPowerAW,"
							+ "max(PowerBW) maxPowerBW,min(PowerBW) minPowerBW,"
							+ "max(PowerCW) maxPowerCW,min(PowerCW) minPowerCW,"
							+ "max(VoltageA) maxVoltageA,min(VoltageA) minVoltageA,"
							+ "max(VoltageB) maxVoltageB,min(VoltageB) minVoltageB,"
							+ "max(VoltageC) maxVoltageC,min(VoltageC) minVoltageC,"
							+ "max(CurrentA) maxCurrentA,min(CurrentA) minCurrentA,"
							+ "max(CurrentB) maxCurrentB,min(CurrentA) minCurrentB,"
							+ "max(CurrentC) maxCurrentC,min(CurrentC) minCurrentC "
							+ "from ZPDDATAS"+ammeterid+" where "
							+ "ValueTime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and "
							+ "to_date(?,'yyyy-mm-dd hh24:mi:ss')";

					Connection conn=null;
					PreparedStatement ps =null;
					ResultSet rs = null;

					try
					{
						conn = ConnDB.getConnection();
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ps.setString(1, sdf.format(start));
						ps.setString(2, sdf.format(end));

						rs = ps.executeQuery();

						while (rs.next())
						{
							if (list == null)
								list = new LinkedList<ExtremeModel>();

							ExtremeModel em = new ExtremeModel();

							em.setName(partname);
							em.setMaxPowerZY(rs.getFloat("maxPowerZY"));
							em.setMinPowerZY(rs.getFloat("minPowerZY"));
							em.setMaxPowerAY(rs.getFloat("maxPowerAY"));
							em.setMinPowerAY(rs.getFloat("minPowerAY"));
							em.setMaxPowerBY(rs.getFloat("maxPowerBY"));
							em.setMinPowerBY(rs.getFloat("minPowerBY"));
							em.setMaxPowerCY(rs.getFloat("maxPowerCY"));
							em.setMinPowerCY(rs.getFloat("minPowerCY"));
							em.setMaxPowerZW(rs.getFloat("maxPowerZW"));
							em.setMinPowerZW(rs.getFloat("minPowerZW"));
							em.setMaxPowerAW(rs.getFloat("maxPowerAW"));
							em.setMinPowerAW(rs.getFloat("minPowerAW"));
							em.setMaxPowerBW(rs.getFloat("maxPowerBW"));
							em.setMinPowerBW(rs.getFloat("minPowerBW"));
							em.setMaxPowerCW(rs.getFloat("maxPowerCW"));
							em.setMinPowerCW(rs.getFloat("minPowerCW"));
							em.setMaxVoltageA(rs.getFloat("maxVoltageA"));
							em.setMinVoltageA(rs.getFloat("minVoltageA"));
							em.setMaxVoltageB(rs.getFloat("maxVoltageB"));
							em.setMinVoltageB(rs.getFloat("minVoltageB"));
							em.setMaxVoltageC(rs.getFloat("maxVoltageC"));
							em.setMinVoltageC(rs.getFloat("minVoltageC"));
							em.setMaxCurrentA(rs.getFloat("maxCurrentA"));
							em.setMinCurrentA(rs.getFloat("minCurrentA"));
							em.setMaxCurrentB(rs.getFloat("maxCurrentB"));
							em.setMinCurrentB(rs.getFloat("minCurrentB"));
							em.setMaxCurrentC(rs.getFloat("maxCurrentC"));
							em.setMinCurrentC(rs.getFloat("minCurrentC"));

							list.add(em);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release(conn, ps, rs);
					}

				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		
		return list;
	}

	public List<ExtremeModel> getRootPdPart() throws SQLException
	{
		List<ExtremeModel> list = null;

		String sql = "select Part_ID,Part_Name from PD_Part,(select Part_Parent "
				+ "from PD_Part group by Part_Parent order by Part_Parent) parentList "
				+ "where PD_Part.Part_ID = parentList.Part_Parent or PD_Part.Part_Parent = 0";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<ExtremeModel>();

				ExtremeModel em = new ExtremeModel();

				em.setPdPartID(rs.getInt("Part_ID"));
				em.setName(rs.getString("Part_Name"));

				list.add(em);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		return list;
	}

	public List<ExtremeModel> getPdPartByParentID(int parentID)
			throws SQLException
	{
		List<ExtremeModel> list = null;

		String sql = "select Part_ID,Part_Name from PD_Part where PartStyle_ID = 03 and Part_Parent = ?";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, parentID);

			rs = ps.executeQuery();

			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<ExtremeModel>();

				ExtremeModel em = new ExtremeModel();

				em.setPdPartID(rs.getInt("Part_ID"));
				em.setName(rs.getString("Part_Name"));

				list.add(em);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<ExtremeModel> getAllHuiLuPdPart() throws SQLException
	{
		List<ExtremeModel> list = null;

		String sql = "select Part_ID,Part_Parent,Part_Name from PD_Part where PartStyle_ID = 03 order by Part_Parent";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				if (list == null)
					list = new LinkedList<ExtremeModel>();

				ExtremeModel em = new ExtremeModel();

				em.setPdPartID(rs.getInt("Part_ID"));
				em.setPdpartParentID(rs.getInt("Part_Parent"));
				em.setName(rs.getString("Part_Name"));

				list.add(em);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public String getHuiLuPdPartTreeString() throws SQLException
	{
		String string = null;

		List<ExtremeModel> list = getAllHuiLuPdPart();

		if (list != null && list.size() > 0)
		{
			JSONArray json = buildTree(list);

			////System.out.println(json.toString());

			JSONObject jo = new JSONObject();

			jo.put("Type", "Dad");
			jo.put("Part_ID", -1);
			jo.put("Part_Name", "全部回路");
			jo.put("SonList", json);

			JSONArray json1 = new JSONArray();
			json1.add(jo);

			string = buildNode(json1.getJSONObject(0));
		}

		return string;
	}

	private String buildNode(JSONObject node)
	{
		String nodeContent = "";
		String id = node.getString("Part_ID");
		String name = node.getString("Part_Name");
		String type = node.getString("Type");

		if ("Son".equals(type))
		{
			nodeContent += "<a href='javascript:void(0);' >";
			nodeContent += "<span class='lbl' pdID=" + id + "> " + name
					+ "</span>";
			nodeContent += "</a>";
		}

		if ("Dad".equals(type))
		{
			nodeContent += "<ul class='nav nav-list'>";
			nodeContent += "<li>";
			nodeContent += "<ul class='submenu' style='display: block;'>";
			nodeContent += "<li>";
			nodeContent += "<a href='javascript:void(0);' class='dropdown-toggle'>";
			nodeContent += "<i class='icon-minus'></i>";
			nodeContent += "<span class='lbl' pdID='" + id + "'>  " + name
					+ " </span>";
			nodeContent += "</a>";
			nodeContent += "<ul class='submenu' style='display: block;'>";

			for (int i = 0; i < node.getJSONArray("SonList").size(); i++)
			{
				nodeContent += "<li>";
				nodeContent += buildNode(node.getJSONArray("SonList")
						.getJSONObject(i));
				nodeContent += "</li>";
			}

			nodeContent += "</ul>";
			nodeContent += "</li>";
			nodeContent += "</ul>";
			nodeContent += "</li>";
			nodeContent += "</ul>";
		}

		return nodeContent;
	}

	private JSONArray buildTree(List<ExtremeModel> list) throws SQLException
	{
		JSONArray json = new JSONArray();

		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getPdpartParentID() == 0)
			{
				JSONObject jo = new JSONObject();

				if (getPdPartByParentID(list.get(i).getPdPartID()) == null)
				{
					jo.put("Type", "Son");
				} else
				{
					jo.put("Type", "Dad");
				}

				jo.put("Part_ID", list.get(i).getPdPartID());
				jo.put("Part_Name", list.get(i).getName());

				json.add(jo);

				list.remove(i);

				i--;
			} else
			{
				if (getPDPartByID(list.get(i).getPdpartParentID()) == null)
				{
					JSONObject jo = new JSONObject();

					jo.put("Type", "Son");
					jo.put("Part_ID", list.get(i).getPdPartID());
					jo.put("Part_Name", list.get(i).getName());

					json.add(jo);

					list.remove(i);

					i--;
				}
			}
		}

		if (list != null && list.size() > 0)
		{
			for (ExtremeModel em : list)
			{
				JSONObject jo = getJSONObjectByID(json, em.getPdpartParentID());

				if (jo != null)
				{
					jo.put("Type", "Dad");
					JSONArray ja = null;
					if (jo.get("SonList") == null)
						ja = new JSONArray();
					else
					{
						ja = jo.getJSONArray("SonList");
					}

					JSONObject jo1 = new JSONObject();

					jo1.put("Part_ID", em.getPdPartID());
					jo1.put("Part_Name", em.getName());

					jo1.put("Type", "Son");

					ja.add(jo1);
					jo.put("SonList", ja);
				}

			}
		}
		return json;
	}

	private JSONObject getJSONObjectByID(JSONArray json, Integer id)
	{
		if (json == null)
			return null;

		JSONObject jo = null;

		Integer tempID = 0;

		for (int i = 0; i < json.size(); i++)
		{
			tempID = Integer.parseInt(json.getJSONObject(i).get("Part_ID")
					.toString());
			if (tempID == id)
			{
				jo = json.getJSONObject(i);
				break;
			} else
			{
				if ("Dad".equals(json.getJSONObject(i).get("Type"))
						&& json.getJSONObject(i).getJSONArray("SonList") != null)
				{
					jo = getJSONObjectByID(
							json.getJSONObject(i).getJSONArray("SonList"), id);

					if (jo != null)
						break;
				}
			}
		}

		return jo;
	}
}
