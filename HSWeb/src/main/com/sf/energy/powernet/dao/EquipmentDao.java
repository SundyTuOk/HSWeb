package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sf.energy.powernet.model.EquipmentModel;
import com.sf.energy.util.ConnDB;

public class EquipmentDao
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
	
	
	public ArrayList<EquipmentModel> queryEquipmentInfo(int pageCount, int pageIndex, int partID, String orderInfo) throws SQLException
	{
		ArrayList<EquipmentModel> list = new ArrayList<EquipmentModel>();
		EquipmentModel em = null;
		
		String sql = "";
		String strWhere = "";
		if(partID != 0)
		{
			strWhere = " where Part_ID in (SELECT part_id FROM pd_part START WITH part_id = " + partID + " CONNECT BY PRIOR part_id = part_parent)";
		}
		sql = "select "+
					"Part_ID,Part_Num,Part_Name,State,JLID,AmMeter_Stat,"+
					"substr(LastTime,0,19) as LastTime,"+
					"IsSupply,nvl(AmMeter_485Address,'暂未配置计量设备')AmMeter_485Address,AmMeter_ID "+
				"from "+
					"("+
						"select "+
							"a.Part_ID,Part_Num,Part_Name,State,JLID  "+
						"from "+
							"(PD_Part)a,"+
							"(PD_PartInfo03)b "+
						"where "+
							"a.Part_ID=b.Part_ID "+
							"and PartStyle_ID='03'"+
					")T "+
				"left join "+
					"AmMeter "+
				"on  "+
					"AmMeter_ID=JLID";
		sql += strWhere + orderInfo;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
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
			String amMeterState = "";
			String isSupply = "通电";
			String amMeter485Address="";
			while (rs.next()&&(count > 0))
			{
				em=new EquipmentModel();
				em.setPartID(rs.getInt("Part_ID"));
				em.setPartNum(rs.getString("Part_Num"));
				em.setPartName(rs.getString("Part_Name"));
				
				amMeter485Address = rs.getString("AmMeter_485Address");
//				if("".equals(amMeter485Address) || amMeter485Address == null)
//				{
//					amMeter485Address = "暂未配置计量设备";
//				}
				em.setAmMeter485Address(amMeter485Address);
				
				if("0".equals(rs.getString("AmMeter_Stat")))
				{
					amMeterState = "<font color='red'>离线</font>";
				}
				else if ("".equals(rs.getString("AmMeter_Stat")))
	            {
					amMeterState = "<font color='blue'>未知</font>";
	            }
	            else
	            {
	            	amMeterState = "<font color='green'>在线</font>";
	            }
				em.setAmMeterState(amMeterState);
				
				if("1".equals(rs.getString("IsSupply")))
				{
					isSupply = "<font color='green'>通电</font>";
				}
				else if ("0".equals(rs.getString("IsSupply")))
	            {
					isSupply = "<font color='red'>断电</font>";
	            }
	            else
	            {
	            	isSupply = "<font color='blue'>未知</font>";
	            }
				em.setIsSupply(isSupply);
				
				em.setState(rs.getString("State"));
				if("".equals(rs.getString("LastTime")) || rs.getString("LastTime") == null)
				{
					em.setLastTime("");
				}
				else
				{
					em.setLastTime(rs.getString("LastTime"));
				}
				
				
				list.add(em);
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
	
	
	public ArrayList<EquipmentModel> queryMaintainInfo(int pageCount, int pageIndex, int partID, String orderInfo) throws SQLException
	{
		ArrayList<EquipmentModel> list = new ArrayList<EquipmentModel>();
		EquipmentModel em = null;
		
		String sql = "";
		String strWhere = "";
		if(partID != 0)
		{
			strWhere = " and a.Part_ID in (SELECT part_id FROM pd_part START WITH part_id = " + partID + " CONNECT BY PRIOR part_id = part_parent)";
		}
		sql = "select "+ 
					"Maintain_ID,a.Part_ID,Part_Num,Part_Name,"+
					"("+
						"select "+
							"DictionaryValue_Value "+
						"from "+
							"DictionaryValue "+
						"where "+
							"PartStyle_ID=DictionaryValue_Num "+
							"and Dictionary_ID=28"+
					")PartStyle_Name,"+
					"Project,"+
					"(to_char(MainDate,'yyyy-mm-dd hh24:mi:ss'))MainDate,"+
					"MainMan,Remark "+
				"from "+
					"(PD_Maintain)a,"+
					"(PD_Part)b "+
				"where "+
					"a.Part_ID=b.Part_ID";
		sql += strWhere + orderInfo;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
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
			
			while (rs.next()&&(count > 0))
			{
				em=new EquipmentModel();
				em.setMaintainID(rs.getInt("Maintain_ID"));
				//em.setPartID(rs.getInt("Part_ID"));
				em.setPartNum(rs.getString("Part_Num"));
				em.setPartName(rs.getString("Part_Name"));
				em.setPartStyleName(rs.getString("PartStyle_Name"));
				em.setProject(rs.getString("Project"));
				em.setMainDate(rs.getString("MainDate"));
				em.setMainMan(rs.getString("MainMan"));
				em.setRemark(rs.getString("Remark"));
				
				list.add(em);
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
	 * 增
	 * @param em
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public boolean insert(EquipmentModel em) throws SQLException, ParseException
	{
		////System.out.println("dao");
		
		String sql = "INSERT INTO PD_MAINTAIN(PART_ID,PROJECT,MAINDATE,MAINMAN,REMARK)"
				+ " Values(?,?,to_date(?,'yyyy-mm-dd'),?,?)";
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mainDate = sdf.format(sdf.parse(em.getMainDate()));
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, em.getPartID());
			ps.setString(2, em.getProject());
			ps.setString(3, mainDate);
			ps.setString(4, em.getMainMan());
			ps.setString(5, em.getRemark());
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
	 * 删
	 * @param MAINTAIN_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int MAINTAIN_ID) throws SQLException
	{
		String sql = "delete FROM PD_MAINTAIN where MAINTAIN_ID =  " + MAINTAIN_ID;
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
	 * 改
	 * @param em
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public boolean modify(EquipmentModel em) throws SQLException, ParseException
	{
		String sql = "UPDATE PD_MAINTAIN SET " +
				"PART_ID=?,PROJECT=?,MAINDATE=to_date(?,'yyyy-mm-dd'),MAINMAN=?,REMARK=?" +
				" where MAINTAIN_ID=" + em.getMaintainID();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mainDate = sdf.format(sdf.parse(em.getMainDate()));
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, em.getPartID());
			ps.setString(2, em.getProject());
			ps.setString(3, mainDate);
			ps.setString(4, em.getMainMan());
			ps.setString(5, em.getRemark());
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
	 * 根据ID查询
	 * @param MAINTAIN_ID
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public EquipmentModel queryByID(int MAINTAIN_ID) throws SQLException, ParseException
	{
		EquipmentModel em = new EquipmentModel();
		////System.out.println(MAINTAIN_ID);
		String sql = "Select * from PD_MAINTAIN where MAINTAIN_ID=" + MAINTAIN_ID;

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;


		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String mainDate = "";
			
			while(rs.next())
			{
				mainDate = sdf.format(sdf.parse(rs.getString("MAINDATE")));
				////System.out.println(mainDate);
				em.setMaintainID(MAINTAIN_ID);
				em.setPartID(rs.getInt("PART_ID"));
				
				String sql1 = "Select Part_Name from PD_Part where Part_ID = " + rs.getInt("PART_ID");
				Connection conn1 = null;
				PreparedStatement ps1=null;
				ResultSet rs1=null;
				try
				{
					conn1 = ConnDB.getConnection();
					ps1 = conn1.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					if(rs1.next())
					{
						em.setPartName(rs1.getString("Part_Name"));
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, ps1, rs1);
				}
				
				em.setProject(rs.getString("PROJECT"));
				em.setMainDate(mainDate);
				em.setMainMan(rs.getString("MAINMAN"));
				em.setRemark(rs.getString("REMARK"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps,rs);
		}
		return em;
	}
	
	
	
	
}
