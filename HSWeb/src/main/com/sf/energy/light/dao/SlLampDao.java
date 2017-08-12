package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.light.model.SlLampModel;
import com.sf.energy.util.ConnDB;



/**
 * 对路灯信息进行查询和修改
 * @author Administrator
 *
 */
public class SlLampDao {

	SlLampModel slLampModel=null;
	
	public SlLampModel queryLamp(String lid) throws SQLException
	{
		if(lid==null)
		{
			slLampModel=null;
			return slLampModel;
		}
		int id=Integer.parseInt(lid);
		
		String sql="select c.AREA_ADDRESS,b.SLKongzhi_Name,a.slLamp_State,a.lastTime from sllamp a inner join SLKONGZHI b on   a.slKongzhi_Id=b.slKongzhi_Id inner join area c      on   a.area_Id=c.area_Id where a.slLamp_Id="+id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				slLampModel=new SlLampModel();
				slLampModel.setSlLamp_State(rs.getString("slLamp_State"));			
				slLampModel.setLastTime(rs.getDate("lastTime"));
				slLampModel.setAREA_ADDRESS(rs.getString("AREA_ADDRESS"));
				slLampModel.setSLKongzhi_Name(rs.getString("SLKongzhi_Name"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return slLampModel;
	}
	
	/**
	 * 增
	 * @param sllm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(SlLampModel sllm) throws SQLException
	{
		String sql = "INSERT INTO SLLAMP(AREA_ID,GATHER_ID,SLKONGZHI_ID,SLLINE_ID,SLLAMP_NUM,SLLAMP_SIZE)"
				+ " Values(?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sllm.getArea_Id());
			ps.setInt(2, sllm.getGather_Id());
			ps.setInt(3, sllm.getSlKongzhi_Id());
			ps.setInt(4, sllm.getSlLine_Id());
			ps.setString(5, sllm.getSlLamp_Num());
			ps.setString(6, sllm.getSlLamp_Size());
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
	 * @param SLLAMP_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int SLLAMP_ID) throws SQLException
	{
		String sql = "delete FROM SLLAMP where SLLAMP_ID =  " + SLLAMP_ID;
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
	 * @param sllm
	 * @return
	 * @throws SQLException
	 */
	public boolean modify(SlLampModel sllm) throws SQLException
	{
		String sql = "UPDATE SLLAMP SET AREA_ID=?,GATHER_ID=?,SLKONGZHI_ID=?,SLLINE_ID=?," +
				"SLLAMP_NUM=?,SLLAMP_SIZE=? where SLLAMP_ID=" + sllm.getSlLamp_Id();
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sllm.getArea_Id());
			ps.setInt(2, sllm.getGather_Id());
			ps.setInt(3, sllm.getSlKongzhi_Id());
			ps.setInt(4, sllm.getSlLine_Id());
			ps.setString(5, sllm.getSlLamp_Num());
			ps.setString(6, sllm.getSlLamp_Size());
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
	 * @param SLLAMP_ID
	 * @return
	 * @throws SQLException
	 */
	public SlLampModel queryByID(int SLLAMP_ID) throws SQLException
	{
		SlLampModel sllm = new SlLampModel();
		String sql = "Select * from SLLAMP where SLLAMP_ID=" + SLLAMP_ID;
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
				sllm.setSlLamp_Id(SLLAMP_ID);
				sllm.setArea_Id(rs.getInt("AREA_ID"));
				sllm.setGather_Id(rs.getInt("GATHER_ID"));
				sllm.setSlKongzhi_Id(rs.getInt("SLKONGZHI_ID"));
				sllm.setSlLine_Id(rs.getInt("SLLINE_ID"));
				sllm.setSlLamp_Num(rs.getString("SLLAMP_NUM"));
				sllm.setSlLamp_Size(rs.getString("SLLAMP_SIZE"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
		return sllm;
	}
	/**
	 * 通过area_ID和sLLine_ID查询路灯信息
	 * @param area_ID 区域ID	
	 * @param sLLine_ID	回路ID
	 * @return 路灯信息
	 * @throws SQLException
	 */
	public List<SlLampModel> queryByAreaID(int area_ID, int sLLine_ID) throws SQLException {
		SlLampModel sllm = null;
		List<SlLampModel> list = new ArrayList<SlLampModel>();
		String sql = "Select * from SLLAMP  where AREA_ID="+area_ID+" and sLLine_ID="+sLLine_ID +"ORDER BY SLLAMP_ID";
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
				sllm = new SlLampModel();
				sllm.setSlLamp_Id(rs.getInt("SLLAMP_ID"));
				sllm.setArea_Id(rs.getInt("AREA_ID"));
				sllm.setGather_Id(rs.getInt("GATHER_ID"));
				sllm.setSlKongzhi_Id(rs.getInt("SLKONGZHI_ID"));
				sllm.setSlLine_Id(rs.getInt("SLLINE_ID"));
				sllm.setSlLamp_Num(rs.getString("SLLAMP_NUM"));
				sllm.setSlLamp_Size(rs.getString("SLLAMP_SIZE"));
				list.add(sllm);
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
	 * 根据ID查询
	 * @param SLLAMP_ID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SlLampModel> queryAll() throws SQLException
	{
		SlLampModel sllm = null;
		ArrayList<SlLampModel> list = new ArrayList<SlLampModel>();
		String sql = "Select * from SLLAMP  ORDER BY SLLAMP_ID";
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
				sllm = new SlLampModel();
				sllm.setSlLamp_Id(rs.getInt("SLLAMP_ID"));
				sllm.setArea_Id(rs.getInt("AREA_ID"));
				sllm.setGather_Id(rs.getInt("GATHER_ID"));
				sllm.setSlKongzhi_Id(rs.getInt("SLKONGZHI_ID"));
				sllm.setSlLine_Id(rs.getInt("SLLINE_ID"));
				sllm.setSlLamp_Num(rs.getString("SLLAMP_NUM"));
				sllm.setSlLamp_Size(rs.getString("SLLAMP_SIZE"));
				list.add(sllm);
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
