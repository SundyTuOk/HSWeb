package com.sf.energy.classroomlight.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sf.energy.classroomlight.model.LightRoomModel;
import com.sf.energy.classroomlight.model.LightRoomPlanModel;
import com.sf.energy.util.ConnDB;

public class LightRoomPlanDao
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
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(LightRoomPlanModel lrpm) throws SQLException
	{
		String sql = "INSERT INTO LIGHTROOMSCHEDULE(TYPE,AREA_ID,ARCHITECTURE_ID,FLOOR,LIGHTROOMNO,YYYY,MM,DD,ONHH,ONMM,CLEARHH,CLEARMM,ADJUSTHH,ADJUSTMM,OFFHH,OFFMM)"
				+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, lrpm.getTYPE());
			ps.setInt(2, lrpm.getAREA_ID());
			ps.setInt(3, lrpm.getARCHITECTURE_ID());
			ps.setInt(4, lrpm.getFLOOR());
			ps.setInt(5, lrpm.getLIGHTROOMNO());
			ps.setInt(6, lrpm.getYYYY());
			ps.setInt(7, lrpm.getMM());
			ps.setInt(8, lrpm.getDD());
			ps.setInt(9, lrpm.getONHH());
			ps.setInt(10, lrpm.getONMM());
			ps.setInt(11, lrpm.getCLEARHH());
			ps.setInt(12, lrpm.getCLEARMM());
			ps.setInt(13, lrpm.getADJUSTHH());
			ps.setInt(14, lrpm.getADJUSTMM());
			ps.setInt(15, lrpm.getOFFHH());
			ps.setInt(16, lrpm.getOFFMM());	
			b = (ps.executeUpdate() == 1);
			
			Date time = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String LightRoomHistory_Time = sdf1.format(time);
			String TaskNum = sdf2.format(time);		
			
			ArrayList<LightRoomModel> list=new ArrayList<LightRoomModel>();
			LightRoomDao lrd = new LightRoomDao();
			if(lrpm.getTYPE() == 1)
			{
				list = lrd.searchRoom(-1, -1, -1, -1);
			}
			else if(lrpm.getTYPE() == 2)
			{
				list = lrd.searchRoom(lrpm.getAREA_ID(), -1, -1, -1);
			}
			else if(lrpm.getTYPE() == 3)
			{
				list = lrd.searchRoom(-1, lrpm.getARCHITECTURE_ID(), -1, -1);
			}
			else if(lrpm.getTYPE() == 4)
			{
				list = lrd.searchRoom(-1, lrpm.getARCHITECTURE_ID(), lrpm.getFLOOR(), -1);
			}
			else if(lrpm.getTYPE() == 5)
			{
				list = lrd.searchRoom(-1, -1, -1, lrpm.getLIGHTROOMNO());
			}
			//System.out.println("插入历史记录条数："+list.size());
			for(LightRoomModel lrm : list)
			{
				String sql1 = "insert into LightRoomHistory(LightRoomNo,LightRoomHistory_Time,LightRoomHistory_State,Users_ID,LightRoomHistory_Style,LightRoomHistory_Opr,TaskNum)" +
						" values(?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)";
				PreparedStatement ps1 = null;
//				Connection conn1 = null;
				try
				{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sql1);
					ps1.setInt(1, lrm.getClassroomLightID());
					ps1.setString(2, LightRoomHistory_Time);
					if(b)
					{
						ps1.setString(3, "成功");
					}
					else
					{
						ps1.setString(3, "失败");
					}
					ps1.setInt(4, 1);
					ps1.setString(5, "自动");
					ps1.setString(6, "设置作息制度");
					ps1.setString(7, TaskNum);
					
					ps1.executeUpdate();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release( ps1);
				}
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		ps.setInt(1, lrpm.getTYPE());
//		ps.setInt(2, lrpm.getAREA_ID());
//		ps.setInt(3, lrpm.getARCHITECTURE_ID());
//		ps.setInt(4, lrpm.getFLOOR());
//		ps.setInt(5, lrpm.getLIGHTROOMNO());
//		ps.setInt(6, lrpm.getYYYY());
//		ps.setInt(7, lrpm.getMM());
//		ps.setInt(8, lrpm.getDD());
//		ps.setInt(9, lrpm.getONHH());
//		ps.setInt(10, lrpm.getONMM());
//		ps.setInt(11, lrpm.getCLEARHH());
//		ps.setInt(12, lrpm.getCLEARMM());
//		ps.setInt(13, lrpm.getADJUSTHH());
//		ps.setInt(14, lrpm.getADJUSTMM());
//		ps.setInt(15, lrpm.getOFFHH());
//		ps.setInt(16, lrpm.getOFFMM());		
		
		

		
		return b;
	}
	
	/**
	 * 删
	 * @param SWITCHSCHEDULEID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int SWITCHSCHEDULEID) throws SQLException
	{
		String sql = "delete FROM LIGHTROOMSCHEDULE where SWITCHSCHEDULEID =  " + SWITCHSCHEDULEID;
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
	 * @param lrpm
	 * @return
	 * @throws SQLException
	 */
	public boolean modify(LightRoomPlanModel lrpm) throws SQLException
	{
		String sql = "UPDATE LIGHTROOMSCHEDULE SET " +
				"ONHH=?,ONMM=?,CLEARHH=?,CLEARMM=?,ADJUSTHH=?,ADJUSTMM=?,OFFHH=?,OFFMM=?" +
				" where SWITCHSCHEDULEID=" + lrpm.getSWITCHSCHEDULEID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, lrpm.getONHH());
			ps.setInt(2, lrpm.getONMM());
			ps.setInt(3, lrpm.getCLEARHH());
			ps.setInt(4, lrpm.getCLEARMM());
			ps.setInt(5, lrpm.getADJUSTHH());
			ps.setInt(6, lrpm.getADJUSTMM());
			ps.setInt(7, lrpm.getOFFHH());
			ps.setInt(8, lrpm.getOFFMM());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
//		ps.setInt(1, lrpm.getONHH());
//		ps.setInt(2, lrpm.getONMM());
//		ps.setInt(3, lrpm.getCLEARHH());
//		ps.setInt(4, lrpm.getCLEARMM());
//		ps.setInt(5, lrpm.getADJUSTHH());
//		ps.setInt(6, lrpm.getADJUSTMM());
//		ps.setInt(7, lrpm.getOFFHH());
//		ps.setInt(8, lrpm.getOFFMM());
		
		return b;
	}
	
	/**
	 * 根据ID查询
	 * @param SWITCHSCHEDULEID
	 * @return
	 * @throws SQLException
	 */
	public LightRoomPlanModel queryByID(int SWITCHSCHEDULEID) throws SQLException
	{
		LightRoomPlanModel lrpm = new LightRoomPlanModel();
		String sql = "Select * from LIGHTROOMSCHEDULE where SWITCHSCHEDULEID=" + SWITCHSCHEDULEID;

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				lrpm.setSWITCHSCHEDULEID(SWITCHSCHEDULEID);
				lrpm.setTYPE(rs.getInt("TYPE"));
				lrpm.setAREA_ID(rs.getInt("AREA_ID"));
				lrpm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				lrpm.setFLOOR(rs.getInt("FLOOR"));
				lrpm.setLIGHTROOMNO(rs.getInt("LIGHTROOMNO"));
				lrpm.setYYYY(rs.getInt("YYYY"));
				lrpm.setMM(rs.getInt("MM"));
				lrpm.setDD(rs.getInt("DD"));
				lrpm.setONHH(rs.getInt("ONHH"));
				lrpm.setONMM(rs.getInt("ONMM"));
				lrpm.setCLEARHH(rs.getInt("CLEARHH"));
				lrpm.setCLEARMM(rs.getInt("CLEARMM"));
				lrpm.setADJUSTHH(rs.getInt("ADJUSTHH"));
				lrpm.setADJUSTMM(rs.getInt("ADJUSTMM"));
				lrpm.setOFFHH(rs.getInt("OFFHH"));
				lrpm.setOFFMM(rs.getInt("OFFMM"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		while(rs.next())
//		{
//			lrpm.setSWITCHSCHEDULEID(SWITCHSCHEDULEID);
//			lrpm.setTYPE(rs.getInt("TYPE"));
//			lrpm.setAREA_ID(rs.getInt("AREA_ID"));
//			lrpm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
//			lrpm.setFLOOR(rs.getInt("FLOOR"));
//			lrpm.setLIGHTROOMNO(rs.getInt("LIGHTROOMNO"));
//			lrpm.setYYYY(rs.getInt("YYYY"));
//			lrpm.setMM(rs.getInt("MM"));
//			lrpm.setDD(rs.getInt("DD"));
//			lrpm.setONHH(rs.getInt("ONHH"));
//			lrpm.setONMM(rs.getInt("ONMM"));
//			lrpm.setCLEARHH(rs.getInt("CLEARHH"));
//			lrpm.setCLEARMM(rs.getInt("CLEARMM"));
//			lrpm.setADJUSTHH(rs.getInt("ADJUSTHH"));
//			lrpm.setADJUSTMM(rs.getInt("ADJUSTMM"));
//			lrpm.setOFFHH(rs.getInt("OFFHH"));
//			lrpm.setOFFMM(rs.getInt("OFFMM"));
//		}

		return lrpm;
	}
	
	public ArrayList<LightRoomPlanModel> queryClassroomPlan(int pageCount, int pageIndex, int type, int id,int floor, int theYear, int theMonth, String order) throws SQLException
	{
		ArrayList<LightRoomPlanModel> list = new ArrayList<LightRoomPlanModel>();
		LightRoomPlanModel lrpm = null;
		
		String sql = "";
		if(type == 1)
		{
			sql = "Select * from LIGHTROOMSCHEDULE where Type=" + type;
		}
		else if (type == 2)
		{
			sql = "Select * from LIGHTROOMSCHEDULE where Type=" + type + " and Area_ID = " + id;
		}
		else if (type == 3)
		{
			sql = "Select * from LIGHTROOMSCHEDULE where Type=" + type + " and Architecture_ID = " + id;
		}
		else if (type == 4)
		{
			sql = "Select * from LIGHTROOMSCHEDULE where Type=" + type + " and Architecture_ID = " + id + " and Floor = " + floor;
		}
		else if (type == 5)
		{
			sql = "Select * from LIGHTROOMSCHEDULE where Type=" + type + " and LightRoomNo = " + id;
		}
		sql += " and YYYY = " + theYear + " and MM = " + theMonth + order;
		
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
				lrpm=new LightRoomPlanModel();
				lrpm.setSWITCHSCHEDULEID(rs.getInt("SWITCHSCHEDULEID"));
				lrpm.setTYPE(rs.getInt("TYPE"));
				lrpm.setAREA_ID(rs.getInt("AREA_ID"));
				lrpm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
				lrpm.setFLOOR(rs.getInt("FLOOR"));
				lrpm.setLIGHTROOMNO(rs.getInt("LIGHTROOMNO"));
				lrpm.setYYYY(rs.getInt("YYYY"));
				lrpm.setMM(rs.getInt("MM"));
				lrpm.setDD(rs.getInt("DD"));
				lrpm.setONHH(rs.getInt("ONHH"));
				lrpm.setONMM(rs.getInt("ONMM"));
				lrpm.setCLEARHH(rs.getInt("CLEARHH"));
				lrpm.setCLEARMM(rs.getInt("CLEARMM"));
				lrpm.setADJUSTHH(rs.getInt("ADJUSTHH"));
				lrpm.setADJUSTMM(rs.getInt("ADJUSTMM"));
				lrpm.setOFFHH(rs.getInt("OFFHH"));
				lrpm.setOFFMM(rs.getInt("OFFMM"));
				list.add(lrpm);
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
//		setTotalCount(count);
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
//			lrpm=new LightRoomPlanModel();
//			lrpm.setSWITCHSCHEDULEID(rs.getInt("SWITCHSCHEDULEID"));
//			lrpm.setTYPE(rs.getInt("TYPE"));
//			lrpm.setAREA_ID(rs.getInt("AREA_ID"));
//			lrpm.setARCHITECTURE_ID(rs.getInt("ARCHITECTURE_ID"));
//			lrpm.setFLOOR(rs.getInt("FLOOR"));
//			lrpm.setLIGHTROOMNO(rs.getInt("LIGHTROOMNO"));
//			lrpm.setYYYY(rs.getInt("YYYY"));
//			lrpm.setMM(rs.getInt("MM"));
//			lrpm.setDD(rs.getInt("DD"));
//			lrpm.setONHH(rs.getInt("ONHH"));
//			lrpm.setONMM(rs.getInt("ONMM"));
//			lrpm.setCLEARHH(rs.getInt("CLEARHH"));
//			lrpm.setCLEARMM(rs.getInt("CLEARMM"));
//			lrpm.setADJUSTHH(rs.getInt("ADJUSTHH"));
//			lrpm.setADJUSTMM(rs.getInt("ADJUSTMM"));
//			lrpm.setOFFHH(rs.getInt("OFFHH"));
//			lrpm.setOFFMM(rs.getInt("OFFMM"));
//			list.add(lrpm);
//			count--;
//		}

		
		return list;	
	}

}
