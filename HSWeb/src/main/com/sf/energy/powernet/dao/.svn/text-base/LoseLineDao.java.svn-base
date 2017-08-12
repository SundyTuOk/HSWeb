package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.velocity.anakia.Escape;

import net.sf.json.JSONObject;

import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.util.ConnDB;
import com.sun.tools.xjc.reader.RawTypeSet.Ref;

public class LoseLineDao
{
	private String child = "";

	public String getChildren(String parentID)
			throws SQLException
	{
		ArrayList<PD_PartModel> dataTable = new ArrayList<>();
		String sql = "select * from pd_part ";
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
				PD_PartModel model = new PD_PartModel();
				int PART_ID = rs.getInt("PART_ID");
				String PARTSTYLE_ID = rs.getString("PARTSTYLE_ID");
				int PART_PARENT = rs.getInt("PART_PARENT");

				model.setPart_ID(PART_ID);
				model.setPart_Parent(PART_PARENT);
				model.setPartStyle_ID(PARTSTYLE_ID);
				dataTable.add(model);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		child ="";
		// 获得所有的子回路的ID
		getOneLevelAllChild(Integer.parseInt(parentID), dataTable);
		return child;

	}
	public String getPDRoomName(String PDRoomID)
	{
		String PDRoomName="";
		String sql = "select PART_ID,PART_Name from PD_PART where PART_ID ="+PDRoomID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
			PDRoomName = rs.getString("PART_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	PDRoomName;
	}
	public String getBYQName(String BYQID)
	{
		String BYQName="";
		String sql = "select PART_ID,PART_Name from PD_PART where PART_ID ="+BYQID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				BYQName = rs.getString("PART_Name");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	BYQName;
	}
	
	public String getBYQInLineID(String BYQID)
	{
		String InLineString="";
		String sql = "select PART_ID,PARTSTYLE_ID from PD_PART where PART_ID in(select PART_PARENT from PD_PART where  PART_ID= "+BYQID+") ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
			String	PARTSTYLE_ID = rs.getString("PARTSTYLE_ID");
				if ("03".equals(PARTSTYLE_ID))//如果是回路就返回回路信息
				{
					InLineString= rs.getString("PART_ID");
				}
				else {
					InLineString=getPDRoomInLineID(rs.getString("PART_ID"));
				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	InLineString;
	}
	public String getBYQOutLineID(String PDRoomID,String OutLineString)
	{//获取配电房出线
		String sql = "select PART_ID,PARTSTYLE_ID from PD_PART where PART_PARENT in("+PDRoomID+") ";
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
					String	PARTSTYLE_ID = rs.getString("PARTSTYLE_ID");
				if ("03".equals(PARTSTYLE_ID))//如果是回路就返回回路信息
				{
					if(OutLineString.equals(""))
					{
						OutLineString=		 rs.getString("PART_ID");		
					}
					else {
						OutLineString+=	","+	 rs.getString("PART_ID");		
					}
				}
				else {
					OutLineString=getPDRoomOutLineID(rs.getString("PART_ID"),OutLineString);
				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	OutLineString;
	}

	public String getPDRoomInLineID(String PDRoomID)
	{
		String InLineString="";
		String sql = "select PART_ID,PARTSTYLE_ID from PD_PART where PART_ID in(select PART_PARENT from PD_PART where  PART_ID= "+PDRoomID+") ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
			String	PARTSTYLE_ID = rs.getString("PARTSTYLE_ID");
				if ("03".equals(PARTSTYLE_ID))//如果是回路就返回回路信息
				{
					InLineString= rs.getString("PART_ID");
				}
				else {
					InLineString=getPDRoomInLineID(rs.getString("PART_ID"));
				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	InLineString;
	}
	public String getPDRoomOutLineID(String PDRoomID,String OutLineString)
	{//获取配电房出线
		String sql = "select PART_ID,PARTSTYLE_ID from PD_PART where PART_PARENT in("+PDRoomID+") ";
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
					String	PARTSTYLE_ID = rs.getString("PARTSTYLE_ID");
				if ("03".equals(PARTSTYLE_ID))//如果是回路就返回回路信息
				{
					if(OutLineString.equals(""))
					{
						OutLineString=		 rs.getString("PART_ID");		
					}
					else {
						OutLineString+=	","+	 rs.getString("PART_ID");		
					}
				}
				else {
					OutLineString=getPDRoomOutLineID(rs.getString("PART_ID"),OutLineString);
				}
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return 	OutLineString;
	}

	public JSONObject getInfo(int parentID, String startGetTime,
			String endGetTime) throws SQLException
	{
		String sql = "select PART_ID,PART_NAME,JLID,d.AMMETER_485ADDRESS,d.BEILV from "
				+ "( "
				+ "select a.PART_ID,a.PART_NAME,nvl(b.JLID,0)JLID "
				+ "from (PD_PART)a left join(PD_PARTINFO03)b "
				+ "on a.PART_ID=b.PART_ID "
				+ "where a.PART_ID in("
				+ parentID
				+ ") " + ")c left join (AMMETER)d " + "on c.JLID=d.AMMETER_ID";
		JSONObject jo = new JSONObject();
		int ammeterID = 0;
		String partName = "";
		String macAddress = "";
		String beilv = "";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				partName = rs.getString("PART_NAME");
				macAddress = rs.getString("AMMETER_485ADDRESS");
				beilv = rs.getString("BEILV");

				if ("".equals(partName) || partName == null)
					partName = "--";
				if ("".equals(macAddress) || macAddress == null)
					macAddress = "--";
				if ("".equals(beilv) || beilv == null)
					beilv = "--";
				jo.put("partID", parentID);
				jo.put("partName", partName);
				jo.put("macAddress", macAddress);
				jo.put("beilv", beilv);
				ammeterID = rs.getInt("JLID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		String startTime = "";
		String startValue = "";
		String endTime = "";
		String endValue = "";
		String sql1 = "select to_char(VALUETIME,'yyyy-mm-dd hh24:mi:ss')Time,ZVALUEZY from ZAMDATAS"+String.valueOf(ammeterID)+" "
				+ "where VALUETIME between to_date('"
				+ startGetTime
				+ "','yyyy-mm-dd hh24:mi') and to_date('"
				+ endGetTime
				+ "','yyyy-mm-dd hh24:mi') order by VALUETIME";
		Connection conn1=null;
		PreparedStatement ps1 = null;
		ResultSet rs1 =null;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(sql1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs1 = ps1.executeQuery();
			if (rs1.next())
			{
				startTime = rs1.getString("Time");
				startValue = rs1.getString("ZVALUEZY");
				rs1.last();
				endTime = rs1.getString("Time");
				endValue = rs1.getString("ZVALUEZY");

				jo.put("startTime",startTime );
				jo.put("startValue", startValue);
				jo.put("endTime", endTime);
				jo.put("endValue", endValue);

				float totalValue = Float.parseFloat(endValue)
						- Float.parseFloat(startValue);
				jo.put("useValue", totalValue);

			} else
			{

				startTime = "--";
				startValue = "--";
				endTime = "--";
				endValue = "--";
				jo.put("startTime", startTime);
				jo.put("startValue", startValue);
				jo.put("endTime", endTime);
				jo.put("endValue", endValue);
				jo.put("useValue", "--");
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1, rs1);
		}
		return jo;
		

	}

	/**
	 * 查询某父亲节点下面的所有一级子回来
	 * 
	 * @param parent
	 * @param dataTable
	 * @param child
	 * @return
	 * @throws SQLException
	 */
	private void getOneLevelAllChild(int parent,
			ArrayList<PD_PartModel> dataTable) throws SQLException
	{

		for (PD_PartModel model : dataTable)
		{
			int PART_ID = model.getPart_ID();
			String PARTSTYLE_ID = model.getPartStyle_ID();
			int PART_PARENT = model.getPart_Parent();
			int style = Integer.parseInt(PARTSTYLE_ID);

			if (parent == PART_PARENT)
			{
				if (style == 3)
				{
					child += PART_ID + ",";
				} else
				{
					getOneLevelAllChild(PART_ID, dataTable);
				}
			}

		}

	}

	/**
	 * 关闭查询链接，回收资源
	 * 
	 * @throws SQLException
	 *             void
	 */


	public String getChild()
	{
		return child;
	}

	public void setChild(String child)
	{
		this.child = child;
	}

}
