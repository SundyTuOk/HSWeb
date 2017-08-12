package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.system.model.PressureDevModel;
import com.sf.energy.util.ConnDB;

public class PressureDevDao
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
	public PressureDevModel query(int id) throws SQLException
	{
		
		PressureDevModel preModel = null;
		AreaDao areaDao = new AreaDao();
		PumpDao  pDao = new PumpDao();
		GatherDao gDao = new GatherDao();
		String AreaName = "";		//区域名称
		String PumpName = "";		//泵房名称
		String ISout = "";			//进出口
		ArrayList<Gather> GatherName_list = new ArrayList<Gather>(); //网关名
		
		String sql="SELECT PRESSUREDEV_ID,AREA_ID,GATHER_ID,PUMP_ID,PRESSUREDEV_485ADDRESS,PRESSUREDEV_NUM,to_char(LASTTIME,'yyyy-mm-dd hh24:mi:ss')LASTTIME,PRESSUREDEV_NAME,ISOUTPRESSUREDEV,PRESSUREDEV_PORT FROM PRESSUREDEV where PRESSUREDEV_ID="+id;
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
				preModel = new PressureDevModel();
				if(rs.getInt("ISOUTPRESSUREDEV")==1)
				{
					ISout = "进口";
				}
				else if(rs.getInt("ISOUTPRESSUREDEV")==0)
				{
					ISout = "出口";
				}
				else
				{
					ISout = "--";
				}
				
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				AreaName = areaDao.queryNameById(rs.getInt("Area_ID"));
				PumpName = pDao.getPumpNameByID(rs.getInt("PUMP_ID"));
				GatherName_list = gDao.queryGatherByGatherID(rs.getInt("GATHER_ID"));
				if(GatherName_list.size()!=0){
					preModel.setGather_Name(GatherName_list.get(0).getGatherName());
				}
				else{
					preModel.setGather_Name(" ");

				}
				preModel.setPumpName(PumpName);
				preModel.setArea_Name(AreaName);
				preModel.setIsoutName(ISout);
				preModel.setArea_ID(rs.getInt("AREA_ID"));
				preModel.setGather_ID(rs.getInt("GATHER_ID"));
				preModel.setPump_ID(rs.getInt("PUMP_ID"));
				preModel.setIsoutpressureDev(rs.getInt("ISOUTPRESSUREDEV"));
				preModel.setPressureDev_ID(rs.getInt("PressureDev_ID"));
				preModel.setLasttime(rs.getString("lasttime"));
				preModel.setPre_485Address(rs.getString("PressureDev_485Address"));
				preModel.setPressureDev_Name(rs.getString("PressureDev_Name"));
				preModel.setPressureDev_Num(rs.getString("PressureDev_Num"));
				preModel.setPressureDev_Port(rs.getInt("PRESSUREDEV_PORT"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

				
		return preModel;
	}


	public List<PressureDevModel> getPressureDevManageContent(int thePageCount,int thePageIndex) throws SQLException
	{
		
		PressureDevModel preModel = null;
		AreaDao areaDao = new AreaDao();
		PumpDao  pDao = new PumpDao();
		GatherDao gDao = new GatherDao();
		String AreaName = "";		//区域名称
		String PumpName = "";		//泵房名称
		String ISout = "";			//进出口
		ArrayList<Gather> GatherName_list = new ArrayList<Gather>(); //网关名
		
		List<PressureDevModel>  list = new ArrayList<PressureDevModel>();
		
		String sql="SELECT PRESSUREDEV_ID,AREA_ID,GATHER_ID,PUMP_ID,PRESSUREDEV_485ADDRESS,PRESSUREDEV_NUM,to_char(LASTTIME,'yyyy-mm-dd hh24:mi:ss')LASTTIME,PRESSUREDEV_NAME,ISOUTPRESSUREDEV,PRESSUREDEV_PORT FROM PRESSUREDEV";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			
			if(num <= (thePageIndex*thePageCount))
				return list;
			num = num - thePageIndex*thePageCount;
			if(num >= thePageCount)
				num = thePageCount;
			if((thePageIndex*thePageCount)==0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);
			
			
			while(rs.next() && (num>0))
			{
				preModel = new PressureDevModel();
				if(rs.getInt("ISOUTPRESSUREDEV")==1)
				{
					ISout = "进口";
				}
				else if(rs.getInt("ISOUTPRESSUREDEV")==0)
				{
					ISout = "出口";
				}
				else
				{
					ISout = "--";
				}
				
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date time = new Date();
//				try
//				{
//					time = (Date) sdf.parse(rs.getString("lasttime"));
//				} catch (ParseException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				//System.out.println("时间"+time);
				AreaName = areaDao.queryNameById(rs.getInt("Area_ID"));
				PumpName = pDao.getPumpNameByID(rs.getInt("PUMP_ID"));
				GatherName_list = gDao.queryGatherByGatherID(rs.getInt("GATHER_ID"));
				if(GatherName_list.size()!=0){
					preModel.setGather_Name(GatherName_list.get(0).getGatherName());
				}
				else{
					preModel.setGather_Name(" ");

				}
				preModel.setPumpName(PumpName);
				preModel.setArea_Name(AreaName);
				preModel.setIsoutName(ISout);
				preModel.setArea_ID(rs.getInt("AREA_ID"));
				preModel.setGather_ID(rs.getInt("GATHER_ID"));
				preModel.setPump_ID(rs.getInt("PUMP_ID"));
				preModel.setIsoutpressureDev(rs.getInt("ISOUTPRESSUREDEV"));
				preModel.setPressureDev_ID(rs.getInt("PressureDev_ID"));
				preModel.setLasttime(rs.getString("lasttime"));
				preModel.setPre_485Address(rs.getString("PressureDev_485Address"));
				preModel.setPressureDev_Name(rs.getString("PressureDev_Name"));
				preModel.setPressureDev_Num(rs.getString("PressureDev_Num"));
				preModel.setPressureDev_Port(rs.getInt("PRESSUREDEV_PORT"));
				list.add(preModel);
				num--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

				
		return list;
	}
	public List<PressureDevModel> getPressureDevManageContentOrder(int thePageCount,int thePageIndex,String tableName,String order) throws SQLException
	{
		
		PressureDevModel preModel = null;
		AreaDao areaDao = new AreaDao();
		PumpDao  pDao = new PumpDao();
		GatherDao gDao = new GatherDao();
		String AreaName = "";		//区域名称
		String PumpName = "";		//泵房名称
		String ISout = "";			//进出口
		ArrayList<Gather> GatherName_list = new ArrayList<Gather>(); //网关名
		
		List<PressureDevModel>  list = new ArrayList<PressureDevModel>();
		
		String sql="SELECT PRESSUREDEV_ID,AREA_ID,GATHER_ID,PUMP_ID,PRESSUREDEV_485ADDRESS,PRESSUREDEV_NUM,to_char(LASTTIME,'yyyy-mm-dd hh24:mi:ss')LASTTIME,PRESSUREDEV_NAME,ISOUTPRESSUREDEV,PRESSUREDEV_PORT FROM PRESSUREDEV order by "+tableName+" "+ order;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			
			rs.last();
			int num = rs.getRow();
			setTotalCount(num);
			
			if(num <= (thePageIndex*thePageCount))
				return list;
			num = num - thePageIndex*thePageCount;
			if(num >= thePageCount)
				num = thePageCount;
			if((thePageIndex*thePageCount)==0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);
			
			
			while(rs.next() && (num>0))
			{
				preModel = new PressureDevModel();
				if(rs.getInt("ISOUTPRESSUREDEV")==1)
				{
					ISout = "进口";
				}
				else if(rs.getInt("ISOUTPRESSUREDEV")==0)
				{
					ISout = "出口";
				}
				else
				{
					ISout = "--";
				}
				
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date time = new Date();
//				try
//				{
//					time = (Date) sdf.parse(rs.getString("lasttime"));
//				} catch (ParseException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				//System.out.println("时间"+time);
				AreaName = areaDao.queryNameById(rs.getInt("Area_ID"));
				PumpName = pDao.getPumpNameByID(rs.getInt("PUMP_ID"));
				GatherName_list = gDao.queryGatherByGatherID(rs.getInt("GATHER_ID"));
				if(GatherName_list.size()!=0){
					preModel.setGather_Name(GatherName_list.get(0).getGatherName());
				}
				else{
					preModel.setGather_Name(" ");

				}
				preModel.setPumpName(PumpName);
				preModel.setArea_Name(AreaName);
				preModel.setIsoutName(ISout);
				preModel.setArea_ID(rs.getInt("AREA_ID"));
				preModel.setGather_ID(rs.getInt("GATHER_ID"));
				preModel.setPump_ID(rs.getInt("PUMP_ID"));
				preModel.setIsoutpressureDev(rs.getInt("ISOUTPRESSUREDEV"));
				preModel.setPressureDev_ID(rs.getInt("PressureDev_ID"));
				preModel.setLasttime(rs.getString("lasttime"));
				preModel.setPre_485Address(rs.getString("PressureDev_485Address"));
				preModel.setPressureDev_Name(rs.getString("PressureDev_Name"));
				preModel.setPressureDev_Num(rs.getString("PressureDev_Num"));
				preModel.setPressureDev_Port(rs.getInt("PRESSUREDEV_PORT"));
				list.add(preModel);
				num--;
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
	 * 李戬
	 * 2015/07/08 通过areaID查询该地区的pump_ID 和pump_Name
	 * @param areaID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PressureDevModel> query_Pump_Gather_ByAreaID(int areaID)
			throws SQLException
	{
		ArrayList<PressureDevModel> list = new ArrayList<PressureDevModel>();
		PressureDevModel preModel =null;
		String sql = "SELECT AREA.AREA_NAME,PUMP.PUMP_NAME,PUMP.PUMP_ID from AREA,PUMP where AREA.AREA_ID=PUMP.AREA_ID and AREA.AREA_ID=" + areaID;
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
				preModel =new PressureDevModel();
				preModel.setPumpName(rs.getString("PUMP_NAME"));
				preModel.setArea_Name(rs.getString("AREA_NAME"));
				preModel.setPump_ID(rs.getInt("PUMP_ID"));
			//	System.out.println("sql  "+rs.getString("PUMP_NAME")+"   "+rs.getString("AREA_NAME")+"   "+rs.getInt("PUMP_ID"));
				list.add(preModel);
			//	System.out.println("preModel  "+preModel.getPumpName()+"  "+preModel.getArea_Name()+"  "+preModel.getPump_ID());
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
	 * 		删除一条PressureDev记录
	 * @param PressureDev_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean  deletePressureDev(int PressureDev_ID) throws SQLException
	{
		String sql = "DELETE FROM PressureDev WHERE PressureDev_ID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, PressureDev_ID);
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
	 * 		往PressureDev里插入一条记录
	 * @param pred
	 * @return
	 * @throws SQLException
	 */
	public boolean  insertPressureDev(PressureDevModel pred) throws SQLException
	{
		boolean  b = false;
		
		String sql = "INSERT INTO PRESSUREDEV(AREA_ID,GATHER_ID,PUMP_ID,PRESSUREDEV_485ADDRESS,PRESSUREDEV_NUM,LASTTIME,PRESSUREDEV_NAME,ISOUTPRESSUREDEV,PRESSUREDEV_PORT) VALUES(?,?,?,?,?,to_date('"+pred.getLasttime()+"' , 'yyyy-mm-dd hh24:mi:ss'),?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pred.getArea_ID());
			ps.setInt(2, pred.getGather_ID());
			ps.setInt(3, pred.getPump_ID());
			ps.setString(4, pred.getPre_485Address());
			ps.setString(5, pred.getPressureDev_Num());
//			ps.setString(6, pred.getLasttime());
			ps.setString(6, pred.getPressureDev_Name());
			ps.setInt(7, pred.getIsoutpressureDev());
			ps.setInt(8, pred.getPressureDev_Port());
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
	 * 		往PressureDev里更新一条记录
	 * @param pred
	 * @return
	 * @throws SQLException
	 */
	public boolean updatePressureDev(PressureDevModel pred) throws SQLException
	{
		boolean b = false;
		
		String sql = "UPDATE PRESSUREDEV SET AREA_ID=?,GATHER_ID=?,PUMP_ID=?,PressureDev_485Address=?,PressureDev_NUM=?,LASTTIME=to_date('"+pred.getLasttime()+"' , 'yyyy-mm-dd hh24:mi:ss'),PressureDev_NAME=?,ISOUTPressureDev=?,PressureDev_PORT=? WHERE PressureDev_ID=? ";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pred.getArea_ID());
			ps.setInt(2, pred.getGather_ID());
			ps.setInt(3, pred.getPump_ID());
			ps.setString(4, pred.getPre_485Address());
			ps.setString(5, pred.getPressureDev_Num());
			//ps.setString(6, pred.getLasttime());
			ps.setString(6, pred.getPressureDev_Name());
			ps.setInt(7, pred.getIsoutpressureDev());
			ps.setInt(9, pred.getPressureDev_ID());
			ps.setInt(8, pred.getPressureDev_Port());
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
	 * 查询返回数据库条数
	 * @return
	 * @throws SQLException
	 */
	public int queryAllPressureNum() throws SQLException{
		int num=0;
		String sql = "Select * from PRESSUREDEV ORDER BY Pump_ID ";
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
		return num;
	}
	
	
}
