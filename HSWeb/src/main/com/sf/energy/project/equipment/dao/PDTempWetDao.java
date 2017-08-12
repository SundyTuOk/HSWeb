package com.sf.energy.project.equipment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.sf.energy.project.equipment.model.PDTempWetModel;
import com.sf.energy.util.ConnDB;

/**
 * 
 * @author 李戬
 *对温湿度设备数据库操作
 */
public class PDTempWetDao
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

	public List<PDTempWetModel> queryAllPDRoom() throws SQLException
	{
		List<PDTempWetModel> list=new LinkedList<PDTempWetModel>();
		String sql = "SELECT PART_ID PDROOM_ID,PART_NAME PDROOM_Name FROM PD_PART where PartStyle_ID='01'";
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
				PDTempWetModel pdtwModel=new PDTempWetModel();
				
				pdtwModel.setPDRoom_ID(rs.getInt("PDRoom_ID"));
				pdtwModel.setPDRoom_Name(rs.getString("PDRoom_Name"));
				list.add(pdtwModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);		
		}

	//	System.out.println("queryAllPDRoom()调用:"+list);
		return list;
	}

	public boolean update(PDTempWetModel pdtwModel) throws SQLException
	{
		String sql = "update TEMPWETDEV set AREA_ID="+pdtwModel.getArea_ID()+", GATHER_ID="+pdtwModel.getGather_ID()+", PART_ID="+pdtwModel.getPDRoom_ID()+", TEMPWETDEV_485Address='"+pdtwModel.getPDTempWet_485Address()+"', TEMPWETDEV_NAME='"+pdtwModel.getPDTempWet_Name()+"', Num='"+pdtwModel.getPDTempWet_Num()+"', TEMPWETDEV_PORT="+pdtwModel.getPDTempWet_Port()+" where TEMPWETDEV_ID="+pdtwModel.getPDTempWet_ID();
//		System.out.println(sql);	
		Connection conn=null;
		PreparedStatement psmt =null;
		boolean b=false;
		try
		{
		
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			b = (psmt.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, psmt);
		}
	//	System.out.println("update(pdtwModel)调用");
		return b;
	}

	public boolean insert(PDTempWetModel pdtwModel) throws SQLException
	{
		
		String sql = "INSERT INTO TEMPWETDEV(AREA_ID, GATHER_ID, PART_ID, TEMPWETDEV_485Address, TEMPWETDEV_NAME, LASTTIME, Num, TEMPWETDEV_PORT) VALUES ("+
				pdtwModel.getArea_ID()+","+pdtwModel.getGather_ID()+","+pdtwModel.getPDRoom_ID()+",'"+pdtwModel.getPDTempWet_485Address()+"','"+pdtwModel.getPDTempWet_Name()+"',"+"to_date('"+pdtwModel.getLastTime()+"','yyyy-mm-dd')"+","+"'"+pdtwModel.getPDTempWet_Num()+"',"+pdtwModel.getPDTempWet_Port()+")";
	//	System.out.println(sql);
		Connection conn=null;
		PreparedStatement psmt =null;
		boolean b =false;
		try
		{
			conn = ConnDB.getConnection();
			psmt = conn.prepareStatement(sql);
			b = (psmt.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, psmt);
		}
			
	//	System.out.println("insert(pdtwModel)调用");
		return b;
	}

	public PDTempWetModel queryPDTempWetByID(int pDTempWet_ID) throws SQLException
	{
		PDTempWetModel pdtw=new PDTempWetModel();
		String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME PDRoom_Name FROM	TEMPWETDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .PART_ID = P .PART_ID and T.TEMPWETDEV_ID="+pDTempWet_ID;
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
				pdtw.setPDTempWet_ID(rs.getInt("TEMPWETDEV_ID"));
				pdtw.setPDTempWet_Num(rs.getString("Num"));
				pdtw.setArea_ID(rs.getInt("Area_ID"));
				pdtw.setArea_Name(rs.getString("Area_Name"));
				pdtw.setGather_ID(rs.getInt("Gather_ID"));
				pdtw.setGather_Name(rs.getString("Gather_Name"));
				pdtw.setPDTempWet_485Address(rs.getString("TEMPWETDEV_485Address"));
				pdtw.setPDTempWet_Name(rs.getString("TEMPWETDEV_Name"));
				pdtw.setPDRoom_ID(rs.getInt("Part_ID"));
				pdtw.setPDRoom_Name(rs.getString("PDRoom_Name"));
				pdtw.setLastTime(rs.getString("LastTime"));
				pdtw.setPDTempWet_Port(rs.getInt("TEMPWETDEV_Port"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
	//	System.out.println("queryPDTempWetByID(id) 调用");
		return pdtw;
	}

	public boolean deleteSomePDTempWet(List<Integer> IDList) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		boolean flag = false;
		String sql = "delete from TEMPWETDEV where tempwetdev_id in(";
		for (int i = 0; i < IDList.size(); i++)
		{
			sql += IDList.get(i);
			if (i < IDList.size() - 1)
				sql += ",";
			else
				sql += ")";
		}
		try
		{
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
	//	System.out.println("deleteSomePDTempWet(list)调用");
		return flag;
	}


	public List<PDTempWetModel> queryAllPDTempWet() throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<PDTempWetModel> list=new LinkedList<PDTempWetModel>();
		String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME PDRoom_Name FROM	TEMPWETDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .Part_ID = P .PART_ID";
		
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				PDTempWetModel pdtwModel=new PDTempWetModel();
				pdtwModel.setPDTempWet_ID(rs.getInt("TEMPWETDEV_ID"));
				pdtwModel.setPDTempWet_Num(rs.getString("Num"));
				pdtwModel.setArea_ID(rs.getInt("Area_ID"));
				pdtwModel.setArea_Name(rs.getString("Area_Name"));
				pdtwModel.setGather_ID(rs.getInt("Gather_ID"));
				pdtwModel.setGather_Name(rs.getString("Gather_Name"));
				pdtwModel.setPDTempWet_485Address(rs.getString("TEMPWETDEV_485Address"));
				pdtwModel.setPDTempWet_Name(rs.getString("TEMPWETDEV_Name"));
				pdtwModel.setPDRoom_ID(rs.getInt("Part_ID"));
				pdtwModel.setPDRoom_Name(rs.getString("PDRoom_Name"));
				pdtwModel.setLastTime(rs.getString("LastTime"));
				pdtwModel.setPDTempWet_Port(rs.getInt("TEMPWETDEV_Port"));
				list.add(pdtwModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	//	System.out.println("queryAllPDTempWet()调用"+list);
		return list;
	}
	public List<PDTempWetModel> queryAllPDTempWetOrder(String tablename,String order) throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		List<PDTempWetModel> list=new LinkedList<PDTempWetModel>();
		String sql = "SELECT T .*, A .AREA_NAME,G.GATHER_NAME,	P .PART_NAME PDRoom_Name FROM	TEMPWETDEV T,	Area A,	Gather G,PD_PART P WHERE T .AREA_ID = A .AREA_ID AND T .GATHER_ID = G .GATHER_ID AND T .Part_ID = P .PART_ID order by "+tablename +" "+order;
		
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				PDTempWetModel pdtwModel=new PDTempWetModel();
				pdtwModel.setPDTempWet_ID(rs.getInt("TEMPWETDEV_ID"));
				pdtwModel.setPDTempWet_Num(rs.getString("Num"));
				pdtwModel.setArea_ID(rs.getInt("Area_ID"));
				pdtwModel.setArea_Name(rs.getString("Area_Name"));
				pdtwModel.setGather_ID(rs.getInt("Gather_ID"));
				pdtwModel.setGather_Name(rs.getString("Gather_Name"));
				pdtwModel.setPDTempWet_485Address(rs.getString("TEMPWETDEV_485Address"));
				pdtwModel.setPDTempWet_Name(rs.getString("TEMPWETDEV_Name"));
				pdtwModel.setPDRoom_ID(rs.getInt("Part_ID"));
				pdtwModel.setPDRoom_Name(rs.getString("PDRoom_Name"));
				pdtwModel.setLastTime(rs.getString("LastTime"));
				pdtwModel.setPDTempWet_Port(rs.getInt("TEMPWETDEV_Port"));
				list.add(pdtwModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	//	System.out.println("queryAllPDTempWet()调用"+list);
		return list;
	}
	/**
	 * 查询返回配电房温湿度设备数据库记录条数
	 * @return
	 * @throws SQLException 
	 */
	public int queryPDTempWetCount() throws SQLException{
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		int count =0;
		String sql = "SELECT * FROM TEMPWETDEV";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
		return count;
	}
	
	public boolean checkPDhasTemWetDevice(int PD_id){
		boolean  b = false;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		int count = 0;
		String sql = "SELECT count(*) FROM TEMPWETDEV where Part_ID=?";
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, PD_id);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}		
		if(count>0){
			b=true;
		}
		return b;
	}
}
