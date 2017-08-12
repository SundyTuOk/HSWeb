package com.sf.energy.classroomlight.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.classroomlight.model.LightRoomModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.util.ConnDB;

/**
 * 表LightRoom的数据库操作
 * @author yanhao
 *
 */
public class LightRoomDao
{
	int total;
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
	/**
	 * 查询出所有的教室照明检测
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<LightRoomModel> queryAll(int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
		LightRoomModel lrModel=null;
		String sql="Select * from LightRoom order by LightRoomNo desc";
		
		Connection conn=null;
		PreparedStatement ps =null;
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
			
			while (rs.next()&&(count > 0))
			{
				lrModel=new LightRoomModel();
				String archName="";
				String areaName="";
				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				lrModel.setAreaID(rs.getInt("Area_ID"));
				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
				lrModel.setFloor(rs.getInt("Floor"));
				lrModel.setArchName(archName);
				lrModel.setAreaName(areaName);
				lrModel.setLightState(rs.getInt("Stat"));
				list.add(lrModel);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		rs.last();
//		int count = rs.getRow();
//		setTotal(count);
//		if (count <= (pageCount * pageIndex))
//			return list;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//		
//		while (rs.next()&&(count > 0))
//		{
//			lrModel=new LightRoomModel();
//			String archName="";
//			String areaName="";
//			archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//			areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//			lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//			lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			lrModel.setAreaID(rs.getInt("Area_ID"));
//			lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//			lrModel.setFloor(rs.getInt("Floor"));
//			lrModel.setArchName(archName);
//			lrModel.setAreaName(areaName);
//			lrModel.setLightState(rs.getInt("Stat"));
//			list.add(lrModel);
//			count--;
//		}

		
		return list;
	}
	/**
	 * 查询出所有的教室照明检测
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<LightRoomModel> queryAll() throws SQLException
	{
		ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
		LightRoomModel lrModel=null;
		String sql="Select a.LightRoomNo,a.LightRoomName,a.Area_ID,a.Architecture_ID,a.floor,b.Stat,b.SwitchMode from LightRoom a inner join LightDeviceCTL b on a.LightRoomNo=b.LightRoomNo order by a.LightRoomNo desc";
		
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
				lrModel=new LightRoomModel();
				String archName="";
				String areaName="";
				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				lrModel.setAreaID(rs.getInt("Area_ID"));
				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
				lrModel.setFloor(rs.getInt("Floor"));
				lrModel.setSwitchMode(rs.getInt("SwitchMode"));
				lrModel.setArchName(archName);
				lrModel.setAreaName(areaName);
				lrModel.setLightState(rs.getInt("Stat"));
				list.add(lrModel);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while (rs.next())
//		{
//			lrModel=new LightRoomModel();
//			String archName="";
//			String areaName="";
//			archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//			areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//			lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//			lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			lrModel.setAreaID(rs.getInt("Area_ID"));
//			lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//			lrModel.setFloor(rs.getInt("Floor"));
//			lrModel.setSwitchMode(rs.getInt("SwitchMode"));
//			lrModel.setArchName(archName);
//			lrModel.setAreaName(areaName);
//			lrModel.setLightState(rs.getInt("Stat"));
//			list.add(lrModel);
//			
//		}

		
		return list;
	}
	
	/**
	 * 通过条件查询出教室照明检测
	 * @param areaID 区域ID
	 * @param archID	建筑ID
	 * @param floor 楼层
	 * @param roomID	教室ID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<LightRoomModel> searchAll(int areaID,int archID,int floor,int roomID,int pageCount, int pageIndex) throws SQLException
	{
		ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
		LightRoomModel lrModel=null;
		String a,b,c,d,e;
		if(areaID==-1)
		{
			a="'%'";
		}
		else {
			a=areaID+"";
		}
		
		if(archID==-1)
		{
			b="'%'";
		}
		else {
			b=archID+"";
		}
		
		if(floor==-1)
		{
			c="'%'";
		}
		else {
			c=floor+"";
		}
		
		if(roomID==-1)
		{
			d="'%'";
		}
		else {
			d=roomID+"";
		}
		
		String sql="";
		
		sql="Select  l.LightRoomNo,l.LightRoomName,l.Area_ID,l.Architecture_ID,l.floor,n.Stat,n.SwitchMode from LightRoom l inner join LightDeviceCTL n on l.LightRoomNo=n.LightRoomNo where l.area_ID like "+a+" and l.Architecture_ID like "+b+" and l.floor like "+c+" and l.LightRoomNo like "+d+" order by l.LightRoomNo";
		
		////System.out.println("xxxxxxxxxxxxxxxxxxxxxx::::"+sql);
		
		Connection conn=null;
		PreparedStatement ps =null;
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
			
			while (rs.next()&&(count > 0))
			{
				lrModel=new LightRoomModel();
				String archName="";
				String areaName="";
				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				lrModel.setAreaID(rs.getInt("Area_ID"));
				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
				lrModel.setFloor(rs.getInt("Floor"));
				lrModel.setSwitchMode(rs.getInt("SwitchMode"));
				lrModel.setArchName(archName);
				lrModel.setAreaName(areaName);
				lrModel.setLightState(rs.getInt("Stat"));
				list.add(lrModel);
				count--;
			}
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		rs.last();
//		int count = rs.getRow();
//		setTotal(count);
//		if (count <= (pageCount * pageIndex))
//			return list;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//		
//		while (rs.next()&&(count > 0))
//		{
//			lrModel=new LightRoomModel();
//			String archName="";
//			String areaName="";
//			archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//			areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//			lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//			lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			lrModel.setAreaID(rs.getInt("Area_ID"));
//			lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//			lrModel.setFloor(rs.getInt("Floor"));
//			lrModel.setSwitchMode(rs.getInt("SwitchMode"));
//			lrModel.setArchName(archName);
//			lrModel.setAreaName(areaName);
//			lrModel.setLightState(rs.getInt("Stat"));
//			list.add(lrModel);
//			count--;
//		}

		
		return list;
	}
	
	/**
	 * 通过ID查询有的教室照明检测
	 * @return
	 * @throws SQLException
	 */
	public LightRoomModel queryByID(int id) throws SQLException
	{
		
		LightRoomModel lrModel=new LightRoomModel();
		String sql="Select * from LightRoom where LightRoomNo="+id+" order by LightRoomNo desc";
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
				
				String archName="";
				String areaName="";
				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				lrModel.setAreaID(rs.getInt("Area_ID"));
				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
				lrModel.setFloor(rs.getInt("Floor"));
				lrModel.setArchName(archName);
				lrModel.setAreaName(areaName);
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			
//			String archName="";
//			String areaName="";
//			archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//			areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//			lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//			lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			lrModel.setAreaID(rs.getInt("Area_ID"));
//			lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//			lrModel.setFloor(rs.getInt("Floor"));
//			lrModel.setArchName(archName);
//			lrModel.setAreaName(areaName);
//			
//		}
		
		return lrModel;
	}
		/**
		 * 通过建筑和楼层查询所有的教室照明设备
		 * @return
		 * @throws SQLException
		 */
		public ArrayList<LightRoomModel> queryByFloorAndArch(int floor,int archID) throws SQLException
		{
			ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
			LightRoomModel lrModel=null;
			
			String sql="Select a.LightRoomNo,a.LightRoomName,a.Area_ID,a.Architecture_ID,a.floor,b.Stat,b.SwitchMode from LightRoom a inner join LightDeviceCTL b on a.LightRoomNo=b.LightRoomNo where Architecture_ID="+archID+" and Floor="+floor+" order by a.LightRoomNo";
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
					lrModel=new LightRoomModel();
					String archName="";
					String areaName="";
					archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
					areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
					lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
					lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
					lrModel.setAreaID(rs.getInt("Area_ID"));
					lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
					lrModel.setFloor(rs.getInt("Floor"));
					lrModel.setSwitchMode(rs.getInt("SwitchMode"));
					lrModel.setArchName(archName);
					lrModel.setAreaName(areaName);
					lrModel.setLightState(rs.getInt("Stat"));
					list.add(lrModel);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
//			while (rs.next())
//			{
//				lrModel=new LightRoomModel();
//				String archName="";
//				String areaName="";
//				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//				lrModel.setAreaID(rs.getInt("Area_ID"));
//				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//				lrModel.setFloor(rs.getInt("Floor"));
//				lrModel.setSwitchMode(rs.getInt("SwitchMode"));
//				lrModel.setArchName(archName);
//				lrModel.setAreaName(areaName);
//				lrModel.setLightState(rs.getInt("Stat"));
//				list.add(lrModel);
//			}

			
			return list;
	}
		
		/**
		 * 通过条件查询出教室照明检测
		 * @param areaID 区域ID
		 * @param archID	建筑ID
		 * @param floor 楼层
		 * @param roomID	教室ID
		 * @return
		 * @throws SQLException
		 */
		public ArrayList<LightRoomModel> searchRoom(int areaID,int archID,int floor,int roomID) throws SQLException
		{
			ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
			LightRoomModel lrModel=null;
			String a,b,c,d,e;
			if(areaID==-1)
			{
				a="'%'";
			}
			else {
				a=areaID+"";
			}
			
			if(archID==-1)
			{
				b="'%'";
			}
			else {
				b=archID+"";
			}
			
			if(floor==-1)
			{
				c="'%'";
			}
			else {
				c=floor+"";
			}
			
			if(roomID==-1)
			{
				d="'%'";
			}
			else {
				d=roomID+"";
			}
			
			String sql="";
			
			sql="Select  l.LightRoomNo,l.LightRoomName,l.Area_ID,l.Architecture_ID,l.floor,n.Stat,n.SwitchMode from LightRoom l inner join LightDeviceCTL n on l.LightRoomNo=n.LightRoomNo where l.area_ID like "+a+" and l.Architecture_ID like "+b+" and l.floor like "+c+" and l.LightRoomNo like "+d+" order by l.LightRoomNo";
			
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
					lrModel=new LightRoomModel();
					String archName="";
					String areaName="";
					archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
					areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
					lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
					lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
					lrModel.setAreaID(rs.getInt("Area_ID"));
					lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
					lrModel.setFloor(rs.getInt("Floor"));
					lrModel.setSwitchMode(rs.getInt("SwitchMode"));
					lrModel.setArchName(archName);
					lrModel.setAreaName(areaName);
					lrModel.setLightState(rs.getInt("Stat"));
					list.add(lrModel);
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
			
//			while (rs.next())
//			{
//				lrModel=new LightRoomModel();
//				String archName="";
//				String areaName="";
//				archName=new ArchitectureDao().queryByID(rs.getInt("ARCHITECTURE_ID")).getName();
//				areaName=new AreaDao().query(rs.getInt("Area_ID")).getName();
//				lrModel.setClassroomLightID(rs.getInt("LightRoomNo"));
//				lrModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//				lrModel.setAreaID(rs.getInt("Area_ID"));
//				lrModel.setRoomName(rs.getString("LIGHTROOMNAME"));
//				lrModel.setFloor(rs.getInt("Floor"));
//				lrModel.setSwitchMode(rs.getInt("SwitchMode"));
//				lrModel.setArchName(archName);
//				lrModel.setAreaName(areaName);
//				lrModel.setLightState(rs.getInt("Stat"));
//				list.add(lrModel);
//			}
			
			return list;
		}
		
		
}
