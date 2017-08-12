package com.sf.energy.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.map.daoImp.MapDistributeImp;
import com.sf.energy.map.model.MapDistributeModel;
import com.sf.energy.util.ConnDB;

public class MapDistributeDao implements MapDistributeImp{
	
	//查询地图上面的标志
	public List<MapDistributeModel> getMapMark(String LableList_ID) throws SQLException
	{
		List<MapDistributeModel> list=new ArrayList<MapDistributeModel>();
		String sql="select Date_ID,Remark,PointX,PointY from LableInfo where LableList_ID="+LableList_ID;
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
				MapDistributeModel mapDistributeModel=new MapDistributeModel();		 		 
				mapDistributeModel.setDate_ID(rs.getString("Date_ID"));
				mapDistributeModel.setRemark(rs.getString("Remark"));
				mapDistributeModel.setPointX(rs.getString("PointX"));
				mapDistributeModel.setPointY(rs.getString("PointY"));
				list.add(mapDistributeModel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		while (rs.next())
//		{
//			MapDistributeModel mapDistributeModel=new MapDistributeModel();		 		 
//			mapDistributeModel.setDate_ID(rs.getString("Date_ID"));
//			mapDistributeModel.setRemark(rs.getString("Remark"));
//			mapDistributeModel.setPointX(rs.getString("PointX"));
//			mapDistributeModel.setPointY(rs.getString("PointY"));
//			list.add(mapDistributeModel);
//		}
//		if(rs!=null)
//			rs.close();
//		
//		if(ps!=null)
//			ps.close();
		return list;
	}
	
	//往地图上增加标志
	public boolean addMapMark(MapDistributeModel mapDistributeModel) throws SQLException
	{
		
		String sql="insert into LableInfo(LABLELIST_ID,DATE_ID,REMARK,POINTX,POINTY)  values('"+mapDistributeModel.getLableList_ID()+
				"','"+mapDistributeModel.getDate_ID()+
				"','"+mapDistributeModel.getRemark()+"','"+mapDistributeModel.getPointX()+
				"','"+mapDistributeModel.getPointY()+"')";
	
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
	
	//删除地图上面的标志
	public boolean deleteMapMar(String LABLELIST_ID,String DATE_ID) throws SQLException
	{
		String sql="delete from LableInfo where LABLELIST_ID="+LABLELIST_ID+" and DATE_ID="+DATE_ID;
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
	
	//删除地图某类型下的所有标志
	public boolean deleteMapMarkAll(String lableList) throws SQLException
	{
		String sql="delete from LableInfo where LABLELIST_ID="+lableList;
	
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
}

