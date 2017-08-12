package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.project.equipment.model.TempDevModel;
import com.sf.energy.util.ConnDB;


public class TempDevDao
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
	public List<TempDevModel> queryAllTempDev() throws SQLException{
		List<TempDevModel> list=new LinkedList<TempDevModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME VOLTAGETRANS_Name FROM	TEMPDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .VOLTAGETRANS_ID = P .PART_ID";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				TempDevModel tModel=new TempDevModel();
				tModel.setTempDev_ID(rs.getInt("TempDev_ID"));
				tModel.setTempDev_Num(rs.getString("TempDev_Num"));
				tModel.setArea_ID(rs.getInt("Area_ID"));
				tModel.setArea_Name(rs.getString("Area_Name"));
				tModel.setGather_ID(rs.getInt("Gather_ID"));
				tModel.setGather_Name(rs.getString("Gather_Name"));
				tModel.setTempDev_485Address(rs.getString("TempDev_485Address"));
				tModel.setTempDev_Name(rs.getString("TempDev_Name"));
				tModel.setVoltageTrans_ID(rs.getInt("VoltageTrans_ID"));
				tModel.setVoltageTrans_Name(rs.getString("VoltageTrans_Name"));
				tModel.setLastTime(rs.getString("LastTime"));
				tModel.setTempDev_Port(rs.getInt("TempDev_Port"));
				list.add(tModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	public List<TempDevModel> queryAllTempDevOrder(String tableName,String order) throws SQLException{
		List<TempDevModel> list=new LinkedList<TempDevModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME VOLTAGETRANS_Name FROM	TEMPDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .VOLTAGETRANS_ID = P .PART_ID order by "+ tableName +" "+ order;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				TempDevModel tModel=new TempDevModel();
				tModel.setTempDev_ID(rs.getInt("TempDev_ID"));
				tModel.setTempDev_Num(rs.getString("TempDev_Num"));
				tModel.setArea_ID(rs.getInt("Area_ID"));
				tModel.setArea_Name(rs.getString("Area_Name"));
				tModel.setGather_ID(rs.getInt("Gather_ID"));
				tModel.setGather_Name(rs.getString("Gather_Name"));
				tModel.setTempDev_485Address(rs.getString("TempDev_485Address"));
				tModel.setTempDev_Name(rs.getString("TempDev_Name"));
				tModel.setVoltageTrans_ID(rs.getInt("VoltageTrans_ID"));
				tModel.setVoltageTrans_Name(rs.getString("VoltageTrans_Name"));
				tModel.setLastTime(rs.getString("LastTime"));
				tModel.setTempDev_Port(rs.getInt("TempDev_Port"));
				list.add(tModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	public boolean insert(TempDevModel tp) throws SQLException
	{
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b =false;
		try
		{
			String sql = "INSERT INTO TEMPDEV(AREA_ID, GATHER_ID, VOLTAGETRANS_ID, TEMPDEV_485Address, TEMPDEV_NAME, LASTTIME, TEMPDEV_NUM,TEMPDEV_PORT) VALUES ("+
					tp.getArea_ID()+","+tp.getGather_ID()+","+tp.getVoltageTrans_ID()+",'"+tp.getTempDev_485Address()+"','"+tp.getTempDev_Name()+"','','"+tp.getTempDev_Num()+"',"+tp.getTempDev_Port()+")";
				
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
	public HashMap<Integer,HashMap<String, String>> getTempDevDatas(String partStyle_ID,int part_ID,String date)throws SQLException{
		HashMap<Integer, HashMap<String, String>> lists=new HashMap<Integer,HashMap<String,String>>();
		Connection conn=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try
		{
			String starttime=date+" 00:00:00";
			String endtime=date+" 23:59:59";
			String sql="select a.*,c.PART_NAME VoltageTrans_Name from TEMPDEVDATAS a, TEMPDEV b, PD_PART c where c.PART_ID=b.VOLTAGETRANS_ID and b.TEMPDEV_ID=a.TEMPDEV_ID and a.VALUETIME between to_date(?,'yyyy-mm-dd hh24-mi-ss') and to_date(?,'yyyy-mm-dd hh24-mi-ss')  and c.PARTSTYLE_ID=? and c.PART_ID=? order by a.VALUETIME";
			conn = ConnDB.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,starttime);
			pstmt.setString(2,endtime);
			pstmt.setString(3,partStyle_ID);
			pstmt.setInt(4,part_ID);
			rs = pstmt.executeQuery();
			int count=0;
			while (rs.next())
			{
				HashMap<String, String> map=new HashMap<String,String>();
				map.put("TempDev_ID",rs.getString("TempDev_ID"));
				map.put("ValueTime",rs.getString("ValueTime"));
				map.put("TempValue",rs.getString("TempValue"));
				map.put("VoltageTrans_Name",rs.getString("VoltageTrans_Name"));
				lists.put(count,map);
				count++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pstmt, rs);
		}

		return lists;
	}
	
	public boolean update(TempDevModel tp) throws SQLException
	{
		//		System.out.println(sql);	
		Connection conn=null;
		PreparedStatement psmt =null;

		boolean b =
		false;
		try
		{
			String sql = "update TEMPDEV set AREA_ID="+tp.getArea_ID()+", GATHER_ID="+tp.getGather_ID()+", VOLTAGETRANS_ID="+tp.getVoltageTrans_ID()+", TEMPDEV_485Address='"+tp.getTempDev_485Address()+"', TEMPDEV_NAME='"+tp.getTempDev_Name()+"', TEMPDEV_NUM='"+tp.getTempDev_Num()+"', TEMPDEV_PORT="+tp.getTempDev_Port()+" where TEMPDEV_ID="+tp.getTempDev_ID();
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			b = (psmt.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, psmt);
		}

		return b;
	}
	
	public TempDevModel queryTempDevByID(int TempDev_ID) throws SQLException{
		TempDevModel tp=new TempDevModel();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME VOLTAGETRANS_Name FROM	TEMPDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .VOLTAGETRANS_ID = P .PART_ID and T.TempDev_ID="+TempDev_ID;
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				tp.setTempDev_ID(rs.getInt("TempDev_ID"));
				tp.setTempDev_Num(rs.getString("TempDev_Num"));
				tp.setArea_ID(rs.getInt("Area_ID"));
				tp.setArea_Name(rs.getString("Area_Name"));
				tp.setGather_ID(rs.getInt("Gather_ID"));
				tp.setGather_Name(rs.getString("Gather_Name"));
				tp.setTempDev_485Address(rs.getString("TempDev_485Address"));
				tp.setTempDev_Name(rs.getString("TempDev_Name"));
				tp.setVoltageTrans_ID(rs.getInt("VoltageTrans_ID"));
				tp.setVoltageTrans_Name(rs.getString("VoltageTrans_Name"));
				tp.setLastTime(rs.getString("LastTime"));
				tp.setTempDev_Port(rs.getInt("TempDev_Port"));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return tp;
	}
	public List<TempDevModel> queryAllVoltageTrans() throws SQLException{
		List<TempDevModel> list=new LinkedList<TempDevModel>();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT PART_ID VOLTAGETRANS_ID,PART_NAME VOLTAGETRANS_Name FROM PD_PART where PartStyle_ID='02'";
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				TempDevModel tModel=new TempDevModel();	
				tModel.setVoltageTrans_ID(rs.getInt("VoltageTrans_ID"));
				tModel.setVoltageTrans_Name(rs.getString("VoltageTrans_Name"));
				list.add(tModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	public boolean deleteSomeTempDev(List<Integer> IDList) throws SQLException{
		boolean flag = false;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			String sql = "delete from TEMPDEV where tempdev_id in(";
			for (int i = 0; i < IDList.size(); i++)
			{
				sql += IDList.get(i);
				if (i < IDList.size() - 1)
					sql += ",";
				else
					sql += ")";
			}
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			if (ps.executeUpdate() == IDList.size())
				flag = true;
			else
				flag = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return flag;
	}
	
}
