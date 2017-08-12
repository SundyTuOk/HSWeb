package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.model.PumpCurrentManagerModel;
import com.sf.energy.project.system.model.PumpModel;
import com.sf.energy.util.ConnDB;

public class PumpDao
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
	 * 		删除一条Pump数据
	 * @param PumpID
	 * @return
	 * @throws SQLException
	 */
	public boolean  deletePump(int PumpID) throws SQLException
	{
		String sql = "DELETE FROM PUMP WHERE PUMP_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, PumpID);
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		PreparedStatement ps = null;
//		
//		ps.setInt(1, PumpID);
//		
//		boolean b =(1 == ps.executeUpdate());
//		
//
//
//		if (ps != null)
//			ps.close();
		return b;
	}
	/**
	 * 		往pump表插入一条记录
	 * @param pump
	 * @return
	 * @throws SQLException
	 */
	public boolean  insertPump(PumpModel pump) throws SQLException
	{
		boolean  b = false;
		
		String sql = "INSERT INTO PUMP(AREA_ID,ADDRESS,PUMP_NAME,XMLNAME,PUMP_XISHU,PUMP_JILIANGID,PUMP_NUM,PUMP_NOTE) VALUES(?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pump.getArea_id());
			ps.setString(2, pump.getAddress());
			ps.setString(3, pump.getPump_name());
			ps.setString(4, pump.getXMLName());
			ps.setFloat(5, pump.getPump_xishu());
			ps.setString(6, pump.getPump_jiliangID());
			ps.setString(7, pump.getPump_Num());
			ps.setString(8, pump.getPump_Note());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		PreparedStatement ps = null;
//
//		ps.setInt(1, pump.getArea_id());
//		ps.setString(2, pump.getAddress());
//		ps.setString(3, pump.getPump_name());
//		ps.setString(4, pump.getXMLName());
//		ps.setFloat(5, pump.getPump_xishu());
//		ps.setString(6, pump.getPump_jiliangID());
//		ps.setString(7, pump.getPump_Num());
//		ps.setString(8, pump.getPump_Note());
//		
//		int i = ps.executeUpdate();
//		
//		b = (i == 1);
//	
//
//		if (ps != null)
//			ps.close();
		return b;
	}
	
	/**
	 *    通过水泵房ID查询泵房名称
	 * @param pumpID
	 * @return
	 * @throws SQLException
	 */
	public String getPumpNameByID(int pumpID) throws SQLException
	{
		String Name = null;
		
		String sql = "SELECT pump_name from PUMP where PUMP_ID =?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pumpID);
			rs = null;
			
			rs = ps.executeQuery();
			if(rs.next())
				Name = rs.getString("pump_name");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, pumpID);
//		ResultSet rs = null;
//		
//		rs = ps.executeQuery();
//		if(rs.next())
//			Name = rs.getString("pump_name");
//		
//		
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		
		return Name;
	}
	
	/**
	 * 		往pump表里更新一条数据
	 * @param pump
	 * @return
	 * @throws SQLException
	 */
	public boolean updatePump(PumpModel pump) throws SQLException
	{
		boolean b = false;
		
		String sql = "UPDATE PUMP SET AREA_ID=?,ADDRESS=?,PUMP_NAME=?,XMLNAME=?,PUMP_XISHU=?,PUMP_JILIANGID=?,PUMP_NUM=?,PUMP_NOTE=? WHERE PUMP_ID=? ";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pump.getArea_id());
			ps.setString(2, pump.getAddress());
			ps.setString(3, pump.getPump_name());
			ps.setString(4, pump.getXMLName());
			ps.setFloat(5, pump.getPump_xishu());
			ps.setString(6, pump.getPump_jiliangID());
			ps.setString(7, pump.getPump_Num());
			ps.setString(8, pump.getPump_Note());
			ps.setInt(9, pump.getPump_id());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		PreparedStatement ps=null;
//		
//		ps.setInt(1, pump.getArea_id());
//		ps.setString(2, pump.getAddress());
//		ps.setString(3, pump.getPump_name());
//		ps.setString(4, pump.getXMLName());
//		ps.setFloat(5, pump.getPump_xishu());
//		ps.setString(6, pump.getPump_jiliangID());
//		ps.setString(7, pump.getPump_Num());
//		ps.setString(8, pump.getPump_Note());
//		ps.setInt(9, pump.getPump_id());
//		
//		b = (1 == ps.executeUpdate());
//		
//
//		ps.close();
		return b;
	}
	/**
	 * 查询 返回数据库记录条数
	 * @param thePageCount
	 * @param thePageIndex
	 * @return
	 * @throws SQLException
	 */
	public int queryAllPumpNum() throws SQLException{
		int num=0;
		String sql = "Select * from Pump ORDER BY Pump_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			num = rs.getRow();		//记录条数
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ResultSet rs = ps.executeQuery();
//		rs.last();
//		num = rs.getRow();		//记录条数
//		
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return num;
	}
	/**
	 *    查询所有pump内容，带分页
	 * @param thePageCount
	 * @param thePageIndex
	 * @return
	 * @throws SQLException
	 */
	public List<PumpModel> queryAllPumpByPage(int thePageCount,int thePageIndex) throws SQLException
	{
		AmmeterDao amDao = new AmmeterDao();
		PumpModel pump = null;
		AreaDao areaDao = new AreaDao();
		String Name = "";		//区域名称
		String amName = "";  //电表名称
		List<PumpModel> list = new ArrayList<PumpModel>();
		String sql = "Select * from Pump ORDER BY Pump_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = ps.executeQuery();
			
			rs.last();
			int num = rs.getRow();		//记录条数
			setTotalCount(num);
		//	System.out.println("num:"+num+" thePageCount"+thePageCount+" thePageIndex"+thePageIndex);
			if(num <= (thePageCount * thePageIndex))
				return list;
			num = num - thePageCount * thePageIndex;
			if (num >= thePageCount)
				num = thePageCount;
			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);
			
			
			while (rs.next() && (num > 0))
			{
				pump=new PumpModel();
				Name = areaDao.queryNameById(rs.getInt("AREA_ID"));
				pump.setAreaName(Name);
				pump.setPump_id(rs.getInt("PUMP_ID"));
				pump.setArea_id(rs.getInt("AREA_ID"));
				pump.setAddress(rs.getString("ADDRESS"));
				pump.setPump_name(rs.getString("PUMP_NAME"));
				pump.setXMLName(rs.getString("XMLNAME"));
				pump.setPump_xishu(rs.getFloat("PUMP_XISHU"));
				pump.setPump_jiliangID(rs.getString("PUMP_JILIANGID"));
				pump.setPump_Num(rs.getString("PUMP_NUM"));
				pump.setPump_Note(rs.getString("PUMP_NOTE"));
				System.out.println("");
				amName = amDao.queryNameByID(Integer.parseInt(rs.getString("PUMP_JILIANGID")));
				pump.setAmmeterName(amName);
				/*pump = buildInstance(rs);*/
				list.add(pump);
				num--;
//				System.out.println("rs data:"+Name+
//						" | "+rs.getInt("AREA_ID")+
//						" |"+rs.getInt("PUMP_ID")+
//						" | "+rs.getString("ADDRESS")+
//						" | "+rs.getString("PUMP_NAME")+
//						" | "+rs.getFloat("PUMP_XISHU")+
//						" | "+rs.getString("PUMP_JILIANGID")+
//						" | "+rs.getString("PUMP_NUM")+
//						" | "+rs.getString("PUMP_NOTE")
//						);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		
//		ResultSet rs = ps.executeQuery();
//		
//		rs.last();
//		int num = rs.getRow();		//记录条数
//		setTotalCount(num);
//	//	System.out.println("num:"+num+" thePageCount"+thePageCount+" thePageIndex"+thePageIndex);
//		if(num <= (thePageCount * thePageIndex))
//			return list;
//		num = num - thePageCount * thePageIndex;
//		if (num >= thePageCount)
//			num = thePageCount;
//		if ((thePageCount * thePageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(thePageCount * thePageIndex);
//		
//		
//		while (rs.next() && (num > 0))
//		{
//			pump=new PumpModel();
//			Name = areaDao.queryNameById(rs.getInt("AREA_ID"));
//			pump.setAreaName(Name);
//			pump.setPump_id(rs.getInt("PUMP_ID"));
//			pump.setArea_id(rs.getInt("AREA_ID"));
//			pump.setAddress(rs.getString("ADDRESS"));
//			pump.setPump_name(rs.getString("PUMP_NAME"));
//			pump.setXMLName(rs.getString("XMLNAME"));
//			pump.setPump_xishu(rs.getFloat("PUMP_XISHU"));
//			pump.setPump_jiliangID(rs.getString("PUMP_JILIANGID"));
//			pump.setPump_Num(rs.getString("PUMP_NUM"));
//			pump.setPump_Note(rs.getString("PUMP_NOTE"));
//			/*pump = buildInstance(rs);*/
//			list.add(pump);
//			num--;
////			System.out.println("rs data:"+Name+
////					" | "+rs.getInt("AREA_ID")+
////					" |"+rs.getInt("PUMP_ID")+
////					" | "+rs.getString("ADDRESS")+
////					" | "+rs.getString("PUMP_NAME")+
////					" | "+rs.getFloat("PUMP_XISHU")+
////					" | "+rs.getString("PUMP_JILIANGID")+
////					" | "+rs.getString("PUMP_NUM")+
////					" | "+rs.getString("PUMP_NOTE")
////					);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	
	public List<PumpModel> queryAllPump() throws SQLException
	{
		PumpModel pump = null;
		AreaDao areaDao = new AreaDao();
		String Name = "";		//区域名称
		List<PumpModel> list = new ArrayList<PumpModel>();
		String sql = "Select * from Pump ORDER BY Pump_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				pump=new PumpModel();
				Name = areaDao.queryNameById(rs.getInt("Area_ID"));
				pump.setAreaName(Name);
				pump.setPump_id(rs.getInt("Pump_ID"));
				pump.setArea_id(rs.getInt("Area_ID"));
				pump.setAddress(rs.getString("address"));
				pump.setPump_name(rs.getString("pump_name"));
				pump.setXMLName(rs.getString("xmlname"));
				pump.setPump_xishu(rs.getFloat("Pump_XiShu"));
				pump.setPump_jiliangID(rs.getString("Pump_jiliangID"));
				pump.setPump_Num(rs.getString("Pump_Num"));
				pump.setPump_Note(rs.getString("Pump_Note"));
				/*pump = buildInstance(rs);*/
				list.add(pump);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			pump=new PumpModel();
//			Name = areaDao.queryNameById(rs.getInt("Area_ID"));
//			pump.setAreaName(Name);
//			pump.setPump_id(rs.getInt("Pump_ID"));
//			pump.setArea_id(rs.getInt("Area_ID"));
//			pump.setAddress(rs.getString("address"));
//			pump.setPump_name(rs.getString("pump_name"));
//			pump.setXMLName(rs.getString("xmlname"));
//			pump.setPump_xishu(rs.getFloat("Pump_XiShu"));
//			pump.setPump_jiliangID(rs.getString("Pump_jiliangID"));
//			pump.setPump_Num(rs.getString("Pump_Num"));
//			pump.setPump_Note(rs.getString("Pump_Note"));
//			/*pump = buildInstance(rs);*/
//			list.add(pump);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	
	public List<PumpModel> queryPumpByAreaID(int areaID) throws SQLException
	{
		PumpModel pump = null;
		List<PumpModel> list = new ArrayList<PumpModel>();
		String sql = "Select * from Pump where Area_ID = ? ORDER BY Pump_ID ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				pump=new PumpModel();
				pump.setPump_id(rs.getInt("PUMP_ID"));
				pump.setArea_id(rs.getInt("AREA_ID"));
				pump.setAddress(rs.getString("ADDRESS"));
				pump.setPump_name(rs.getString("PUMP_NAME"));
				pump.setXMLName(rs.getString("XMLNAME"));
				pump.setPump_xishu(rs.getFloat("PUMP_XISHU"));
				pump.setPump_jiliangID(rs.getString("Pump_JILIANGID"));
				/*pump = buildInstance(rs);*/
				list.add(pump);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ps.setInt(1, areaID);
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			pump=new PumpModel();
//			pump.setPump_id(rs.getInt("PUMP_ID"));
//			pump.setArea_id(rs.getInt("AREA_ID"));
//			pump.setAddress(rs.getString("ADDRESS"));
//			pump.setPump_name(rs.getString("PUMP_NAME"));
//			pump.setXMLName(rs.getString("XMLNAME"));
//			pump.setPump_xishu(rs.getFloat("PUMP_XISHU"));
//			pump.setPump_jiliangID(rs.getString("Pump_JILIANGID"));
//			/*pump = buildInstance(rs);*/
//			list.add(pump);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	
	public List<PumpCurrentManagerModel> listAllPumpData(String sortName, String order) throws SQLException
	{
		PumpCurrentManagerModel pump = null;
		List<PumpCurrentManagerModel> list = new ArrayList<PumpCurrentManagerModel>();
		String sql = "Select p.*,a.area_name from Pump p , Area a where p.area_id=a.area_id ORDER BY "+ sortName+ " "+ order;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				pump=new PumpCurrentManagerModel();
				pump.setPump_ID(rs.getInt("Pump_ID"));
				pump.setArea_ID(rs.getInt("Area_ID"));
				pump.setArea_Name(rs.getString("Area_Name"));
				pump.setAddress(rs.getString("Address"));
				pump.setPump_Name(rs.getString("pump_name"));
				pump.setPump_XiShu(rs.getFloat("Pump_XiShu"));
				pump.setPressureDev_Count(queryPressureDev_CountByPumpID(rs.getInt("Pump_ID")));
				/*pump = buildInstance(rs);*/
				list.add(pump);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			pump=new PumpCurrentManagerModel();
//			pump.setPump_ID(rs.getInt("Pump_ID"));
//			pump.setArea_ID(rs.getInt("Area_ID"));
//			pump.setArea_Name(rs.getString("Area_Name"));
//			pump.setAddress(rs.getString("Address"));
//			pump.setPump_Name(rs.getString("pump_name"));
//			pump.setPump_XiShu(rs.getFloat("Pump_XiShu"));
//			pump.setPressureDev_Count(queryPressureDev_CountByPumpID(rs.getInt("Pump_ID")));
//			/*pump = buildInstance(rs);*/
//			list.add(pump);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	public List<PumpCurrentManagerModel> listPumpDataByArea(String sortName, String order,String area_id) throws SQLException
	{
		PumpCurrentManagerModel pump = null;
		List<PumpCurrentManagerModel> list = new ArrayList<PumpCurrentManagerModel>();
		String sql = "Select p.*,a.area_name from Pump p , Area a where p.area_id=a.area_id and p.area_id="+area_id+" ORDER BY "+ sortName+ " "+ order;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				pump=new PumpCurrentManagerModel();
				pump.setPump_ID(rs.getInt("Pump_ID"));
				pump.setArea_ID(rs.getInt("Area_ID"));
				pump.setArea_Name(rs.getString("Area_Name"));
				pump.setAddress(rs.getString("Address"));
				pump.setPump_Name(rs.getString("pump_name"));
				pump.setPump_XiShu(rs.getFloat("Pump_XiShu"));
				pump.setPressureDev_Count(queryPressureDev_CountByPumpID(rs.getInt("Pump_ID")));
				/*pump = buildInstance(rs);*/
				list.add(pump);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			pump=new PumpCurrentManagerModel();
//			pump.setPump_ID(rs.getInt("Pump_ID"));
//			pump.setArea_ID(rs.getInt("Area_ID"));
//			pump.setArea_Name(rs.getString("Area_Name"));
//			pump.setAddress(rs.getString("Address"));
//			pump.setPump_Name(rs.getString("pump_name"));
//			pump.setPump_XiShu(rs.getFloat("Pump_XiShu"));
//			pump.setPressureDev_Count(queryPressureDev_CountByPumpID(rs.getInt("Pump_ID")));
//			/*pump = buildInstance(rs);*/
//			list.add(pump);
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	
	
	public int queryPressureDev_CountByPumpID(int Pump_ID) throws SQLException{
		int count=0;
		String sql = "Select count(*) count from PressureDev where pump_id= "+ Pump_ID;
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
				count=rs.getInt("count");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			count=rs.getInt("count");
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();
		return count;
	}
	/**获取Pump下所有压力计的ID
	 * @param pumpID
	 * @return
	 * @throws SQLException
	 */
	public List<String> getPressure_IDByPumpID(int pumpID) throws SQLException{
		List<String> list=new ArrayList<String>();
		String sql = "Select PressureDEV_ID from PressureDEV where Pump_ID="+pumpID;
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
				list.add(rs.getString("PressureDEV_ID"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			list.add(rs.getString("PressureDEV_ID"));
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	
	
	/**判断压力计是否是出口
	 * @param PressureDev_ID
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkIsOutPressureDev(int PressureDev_ID) throws SQLException{
		boolean isout=false;
		String sql = "Select * from PressureDEV where PressureDev_ID="+PressureDev_ID;
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
				if(rs.getInt("IsOutPressureDev")==0){//0代表入
					isout=false;
				}else if(rs.getInt("IsOutPressureDev")==1){//1代表出
					isout=true;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			if(rs.getInt("IsOutPressureDev")==0){//0代表入
//				isout=false;
//			}else if(rs.getInt("IsOutPressureDev")==1){//1代表出
//				isout=true;
//			}
//		}
		return isout;
	}
	public List<String> queryDataValueTime(String PressureList,String time) throws SQLException{
		List<String> valuetimeList = new ArrayList<String>();
		String start=time+" 00:00:00";
		String end=time+" 23:59:59";
		
		String sql = "select DISTINCT to_char (VALUETIME ,'YYYY-MM-DD HH24:MI:SS') VALUETIME from PressureDevDatas where VALUETIME between TO_DATE ('"+start+"','YYYY-MM-DD HH24:MI:SS' ) and TO_DATE ('"+end+"','YYYY-MM-DD HH24:MI:SS' ) and PressureDev_ID in("
				+ PressureList + ")  order by VALUETIME asc";
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
				valuetimeList.add(rs.getString("VALUETIME"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			valuetimeList.add(rs.getString("VALUETIME"));
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return valuetimeList;
	}
	/**获取水泵房所有压力计的信息，包括进口还是出口
	 * @param pumpID
	 * @return
	 * @throws SQLException
	 */
	public String getPressureDevByPumpID(int pumpID) throws SQLException{
		String list="";
		String sql = "Select * from PressureDEV where Pump_ID="+pumpID;
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
				list+=rs.getString("PressureDev_ID")+",";
			}
			list=list.substring(0,list.length()-1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			list+=rs.getString("PressureDev_ID")+",";
//		}
//		list=list.substring(0,list.length()-1);
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return list;
	}
	/**获取水泵房所有压力计的信息，包括进口还是出口
	 * @param pumpID
	 * @return
	 * @throws SQLException
	 */
	public float getPumpXishuByPumpID(int pumpID) throws SQLException{
		float pumpXiShu=0;
		String sql = "Select PUMP_XISHU from Pump where Pump_ID="+pumpID;
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
				pumpXiShu=rs.getFloat("PUMP_XISHU");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			pumpXiShu=rs.getFloat("PUMP_XISHU");
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return pumpXiShu;
	}
	public String getFlashXML(int pumpID) throws SQLException
	{
		String xmlname="";
		String sql = "Select xmlname from Pump where Pump_ID="+pumpID;
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
				xmlname=rs.getString("XMLName");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			xmlname=rs.getString("XMLName");
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return xmlname;
	}
	public String getFlashHWJDXML(int pumpID) throws SQLException
	{
		String xmlname="";
		String sql = "Select HWJDxmlname from Pump where Pump_ID="+pumpID;
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
				xmlname=rs.getString("HWJDxmlname");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			xmlname=rs.getString("XMLName");
//		}
//		if (rs != null)
//			rs.close();
//
//		if (ps != null)
//			ps.close();

		return xmlname;
	}
	
	public List<AmmeterModel> queryAmmeterByAreaID(int areaID)
	{	
		
		AmmeterModel am = null;
		List<AmmeterModel> list = new ArrayList<AmmeterModel>();
		String sql = "Select Ammeter_id,Ammeter_name,Area_id from Ammeter where Area_ID = ? and isimportantuser=1 ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				am=new AmmeterModel();
				am.setAmmeterID(rs.getInt("ammeter_id"));
				am.setAmmeterName(rs.getString("ammeter_name"));
				am.setAreaID(rs.getInt("area_id"));
				list.add(am);				
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
