package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sf.energy.light.model.SLControlModel;
import com.sf.energy.manager.current.model.XMLCoder;
import com.sf.energy.util.ConnDB;

public class SLControlDao
{
	/**
	 * 增
	 * @param slcm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(SLControlModel slcm) throws SQLException
	{
		String sql = "INSERT INTO SLKONGZHI(AREA_ID,SLGATHER_ID,SLKONGZHI_NUM,SLKONGZHI_NAME,SLKONGZHI_485ADDRESS,SLKONGZHI_SIZE,SLKONGZHI_PORT,LAMP_STATE1,LAMP_STATE2,LASTTIME,SLKONGZHI_STATE,LAMP_STATE11,LAMP_STATE21)"
				+ " Values(?,?,?,?,?,?,?,'off','off',to_date('2001-01-01 00:00:00','YYYY-MM-DD HH24:MI:SS'),'0','auto','auto')";
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, slcm.getAREA_ID());
			ps.setInt(2, slcm.getSLGATHER_ID());
			ps.setString(3, slcm.getSLKONGZHI_NUM());
			ps.setString(4, slcm.getSLKONGZHI_NAME());
			ps.setString(5, slcm.getSLKONGZHI_485ADDRESS());
			ps.setString(6, slcm.getSLKONGZHI_SIZE());
			ps.setInt(7, slcm.getSLKONGZHI_PORT());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		PreparedStatement ps;
//		
//		ps.setInt(1, slcm.getAREA_ID());
//		ps.setInt(2, slcm.getSLGATHER_ID());
//		ps.setString(3, slcm.getSLKONGZHI_NUM());
//		ps.setString(4, slcm.getSLKONGZHI_NAME());
//		ps.setString(5, slcm.getSLKONGZHI_485ADDRESS());
//		ps.setString(6, slcm.getSLKONGZHI_SIZE());
//		ps.setInt(7, slcm.getSLKONGZHI_PORT());
//		
//		boolean b = (ps.executeUpdate() == 1);
//
//		if (ps != null)
//			ps.close();
		
		return b;
	}
	
	/**
	 * 删
	 * @param SLKONGZHI_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int SLKONGZHI_ID) throws SQLException
	{
		String sql = "delete FROM SLKONGZHI where SLKONGZHI_ID =  " + SLKONGZHI_ID;
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
	 * @param slcm
	 * @return
	 * @throws SQLException
	 */
	public boolean modify(SLControlModel slcm) throws SQLException
	{
		String sql = "UPDATE SLKONGZHI SET AREA_ID=?,SLGATHER_ID=?,SLKONGZHI_NUM=?,SLKONGZHI_NAME=?," +
				"SLKONGZHI_485ADDRESS=?,SLKONGZHI_SIZE=?,SLKONGZHI_PORT=? where SLKONGZHI_ID=" + slcm.getSLKONGZHI_ID();
		
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, slcm.getAREA_ID());
			ps.setInt(2, slcm.getSLGATHER_ID());
			ps.setString(3, slcm.getSLKONGZHI_NUM());
			ps.setString(4, slcm.getSLKONGZHI_NAME());
			ps.setString(5, slcm.getSLKONGZHI_485ADDRESS());
			ps.setString(6, slcm.getSLKONGZHI_SIZE());
			ps.setInt(7, slcm.getSLKONGZHI_PORT());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
//		PreparedStatement ps;
//		
//		ps.setInt(1, slcm.getAREA_ID());
//		ps.setInt(2, slcm.getSLGATHER_ID());
//		ps.setString(3, slcm.getSLKONGZHI_NUM());
//		ps.setString(4, slcm.getSLKONGZHI_NAME());
//		ps.setString(5, slcm.getSLKONGZHI_485ADDRESS());
//		ps.setString(6, slcm.getSLKONGZHI_SIZE());
//		ps.setInt(7, slcm.getSLKONGZHI_PORT());
//		
//		boolean b = (ps.executeUpdate() == 1);
//
//		if (ps != null)
//			ps.close();
		
		return b;
	}
	
	/**
	 * 根据ID查询
	 * @param SLKONGZHI_ID
	 * @return
	 * @throws SQLException
	 */
	public SLControlModel queryByID(int SLKONGZHI_ID) throws SQLException
	{
		SLControlModel slcm = new SLControlModel();
		String sql = "Select * from SLKONGZHI where SLKONGZHI_ID=" + SLKONGZHI_ID;
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
				slcm.setSLKONGZHI_ID(SLKONGZHI_ID);
				slcm.setAREA_ID(rs.getInt("AREA_ID"));
				slcm.setSLGATHER_ID(rs.getInt("SLGATHER_ID"));
				slcm.setSLKONGZHI_NUM(rs.getString("SLKONGZHI_NUM"));
				slcm.setSLKONGZHI_NAME(rs.getString("SLKONGZHI_NAME"));
				slcm.setSLKONGZHI_485ADDRESS(rs.getString("SLKONGZHI_485ADDRESS"));
				slcm.setSLKONGZHI_SIZE(rs.getString("SLKONGZHI_SIZE"));
				slcm.setSLKONGZHI_PORT(rs.getInt("SLKONGZHI_PORT"));	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return slcm;
	}
	
	public SLControlModel queryByControlID(String conId) throws SQLException
	{
		SLControlModel slControlModel=null;
		if(conId==null)
		{			
			return null;
		}
		int id=Integer.parseInt(conId);
		String sql="select a.SLKongzhi_Name,b.AREA_ADDRESS from SLKONGZHI a inner join area b on a.area_Id=b.area_Id where a.SLKongzhi_ID="+id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				slControlModel=new SLControlModel();
				slControlModel.setAREA_ADDRESS(rs.getString("AREA_ADDRESS"));
				slControlModel.setSLKONGZHI_NAME(rs.getString("SLKongzhi_Name"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return slControlModel;
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SLControlModel> queryAll() throws SQLException
	{
		SLControlModel slcm = null;
		ArrayList<SLControlModel> list = new ArrayList<SLControlModel>();
		
		String sql = "Select * from SLKONGZHI ORDER BY SLKONGZHI_ID";
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
				slcm = new SLControlModel();
				slcm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				slcm.setAREA_ID(rs.getInt("AREA_ID"));
				slcm.setSLGATHER_ID(rs.getInt("SLGATHER_ID"));
				slcm.setSLKONGZHI_NUM(rs.getString("SLKONGZHI_NUM"));
				slcm.setSLKONGZHI_NAME(rs.getString("SLKONGZHI_NAME"));
				slcm.setSLKONGZHI_485ADDRESS(rs.getString("SLKONGZHI_485ADDRESS"));
				slcm.setSLKONGZHI_SIZE(rs.getString("SLKONGZHI_SIZE"));
				slcm.setSLKONGZHI_PORT(rs.getInt("SLKONGZHI_PORT"));	
				list.add(slcm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}
	
	public XMLCoder lampDown(int gatherID, String address, int port, String userID) throws SQLException
	{
		XMLCoder xc = new XMLCoder();
		//规约
		//xc.setProtocol("GW");
		//操作员码
		//xc.setOperCode("1");
		//通讯方式
		//xc.setWay("2");
		//命令码
		xc.setCode("lampdown");
		//通信密码
		xc.setPw("");
		xc.setOperCode(userID);
		//设置任务编号
    	Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		xc.setTaskNum(sdf.format(time));
		//设置命令参数
    	String parameters = address + "," + port + "' metertype='3";
    	xc.setPara(parameters);
    	
    	String sql = "select Gather_Address,SendWay,Protocol,datasite_id from Gather where Gather_ID=" + gatherID;
    	Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 rs = ps.executeQuery();
			if(rs.next())
			{
				//网关地址
				 xc.setTerminalAddress(rs.getString("Gather_Address"));
				 //通讯地址
				 xc.setWay(rs.getString("SendWay"));
				 //规约
	             xc.setProtocol(rs.getString("Protocol"));
	             xc.setDatasiteID(rs.getString("datasite_id"));
			}else 
			{
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = ps.executeQuery();
//		if(rs.next())
//		{
//			//网关地址
//			 xc.setTerminalAddress(rs.getString("Gather_Address"));
//			 //通讯地址
//			 xc.setWay(rs.getString("SendWay"));
//			 //规约
//             xc.setProtocol(rs.getString("Protocol"));
//		}else 
//		{
//			return null;
//		}
//		if(rs != null)
//		{rs.close();}
//		if(ps !=null)
//		{ps.close();}
    	
		
		return xc;
		
	}
	
	/**
	 * 根据区域ID查询控制器
	 * @param gatherID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SLControlModel> queryControllerByGatherID(int gatherID) throws SQLException
	{
		SLControlModel slcm = null;
		ArrayList<SLControlModel> list = new ArrayList<SLControlModel>();
		
		String sql = "Select * from SLKONGZHI where SLGATHER_ID = "+gatherID+" ORDER BY SLKONGZHI_ID";
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
				slcm = new SLControlModel();
				slcm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				slcm.setAREA_ID(rs.getInt("AREA_ID"));
				slcm.setSLGATHER_ID(rs.getInt("SLGATHER_ID"));
				slcm.setSLKONGZHI_NUM(rs.getString("SLKONGZHI_NUM"));
				slcm.setSLKONGZHI_NAME(rs.getString("SLKONGZHI_NAME"));
				slcm.setSLKONGZHI_485ADDRESS(rs.getString("SLKONGZHI_485ADDRESS"));
				slcm.setSLKONGZHI_SIZE(rs.getString("SLKONGZHI_SIZE"));
				slcm.setSLKONGZHI_PORT(rs.getInt("SLKONGZHI_PORT"));	
				list.add(slcm);
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
