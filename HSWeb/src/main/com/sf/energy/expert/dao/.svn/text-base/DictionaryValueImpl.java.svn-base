package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.sf.energy.expert.model.BasicInfoArchCountModel;
import com.sf.energy.expert.model.EnergyNameData;
import com.sf.energy.util.ConnDB;


public class DictionaryValueImpl implements DictionaryValueHelper {
	/**
	 * 通过数据字典查询能源名称
	 * @param Dictionary_ID	 数据字典ID
	 * @param DictionaryValue_Num	数据字典value的编号
	 * @return		查询的名称结果集
	 * @throws SQLException
	 */
	public    String  getEnergyName(int Dictionary_ID, int DictionaryValue_Num) throws SQLException
	{
		EnergyNameData end = null;
		String name = null;
		List<EnergyNameData> EnergyNameData_list = new LinkedList<EnergyNameData>();
		String sql = "select DictionaryValue_Value from DictionaryValue where Dictionary_ID = ? and DictionaryValue_Num = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, Dictionary_ID);
			ps.setInt(2, DictionaryValue_Num);
			rs = ps.executeQuery();
			while(rs.next())
			{
				end = new EnergyNameData();
				end.setName(rs.getString("DictionaryValue_Value"));
				EnergyNameData_list.add(end);		
			}
			Gson EnergyName = new Gson();
			name = EnergyName.toJson(EnergyNameData_list);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps = ConnDB.getConnection().prepareStatement(sql);
//		ps.setInt(1, Dictionary_ID);
//		ps.setInt(2, DictionaryValue_Num);
//		rs = ps.executeQuery();
//		while(rs.next())
//		{
//			end = new EnergyNameData();
//			end.setName(rs.getString("DictionaryValue_Value"));
//			EnergyNameData_list.add(end);		
//		}
//		Gson EnergyName = new Gson();
//		name = EnergyName.toJson(EnergyNameData_list);

		
		return name;
	}
	
	
	/**
	 * 		查询所有分类建筑信息及包含的建筑个数
	 * @return		结果集
	 * @throws SQLException
	 */
	public List<BasicInfoArchCountModel> getAllStyelArchInfo() throws SQLException
	{
		BasicInfoArchCountModel  biacmodel = null;
		List<BasicInfoArchCountModel>  list = new ArrayList<BasicInfoArchCountModel>();
		
		String sql = "select StyleNum,StyleName,nvl(ArcCount,0)ArcCount "+
					"	from "+
					"	 ( "+
					"		select DictionaryValue_Num as StyleNum,DictionaryValue_Value as StyleName "+
					"			from DictionaryValue "+
					"		where Dictionary_ID=3)B "+
					"		left join "+
					"		(	"+
					"			select Architecture_Style,count(Architecture_ID)as ArcCount "+
					"				from Architecture group by Architecture_Style "+
					"		)A on StyleNum= Architecture_Style";
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
				biacmodel = new BasicInfoArchCountModel();
				biacmodel.setStyleName(rs.getString("StyleName"));
				biacmodel.setArcCount(rs.getInt("ArcCount"));
				biacmodel.setStyleNum(rs.getString("StyleNum"));
				list.add(biacmodel);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps = ConnDB.getConnection().prepareStatement(sql);
//		rs = ps.executeQuery();
//		while(rs.next())
//		{
//			biacmodel = new BasicInfoArchCountModel();
//			biacmodel.setStyleName(rs.getString("StyleName"));
//			biacmodel.setArcCount(rs.getInt("ArcCount"));
//			biacmodel.setStyleNum(rs.getString("StyleNum"));
//			list.add(biacmodel);
//		}
		
		return list;
	}

	
	/**
	 * 		查询指定分类建筑信息及包含的建筑个数
	 * @return		结果集
	 * @throws SQLException
	 */
	public BasicInfoArchCountModel getStyelArchInfoByStyle(String styleID) throws SQLException
	{
		BasicInfoArchCountModel  biacmodel = null;
		
		String sql = "select StyleNum,StyleName,nvl(ArcCount,0)ArcCount "+
					"	from "+
					"	 ( "+
					"		select DictionaryValue_Num as StyleNum,DictionaryValue_Value as StyleName "+
					"			from DictionaryValue "+
					"		where Dictionary_ID=3 and DictionaryValue_Num = '"+ styleID.charAt(0)+"')B "+
					"		left join "+
					"		(	"+
					"			select Architecture_Style,count(Architecture_ID)as ArcCount "+
					"				from Architecture group by Architecture_Style "+
					"		)A on StyleNum= Architecture_Style";
		
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
				biacmodel = new BasicInfoArchCountModel();
				biacmodel.setStyleName(rs.getString("StyleName"));
				biacmodel.setArcCount(rs.getInt("ArcCount"));
				biacmodel.setStyleNum(rs.getString("StyleNum"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps = ConnDB.getConnection().prepareStatement(sql);
//
//		rs = ps.executeQuery();
//		while(rs.next())
//		{
//			biacmodel = new BasicInfoArchCountModel();
//			biacmodel.setStyleName(rs.getString("StyleName"));
//			biacmodel.setArcCount(rs.getInt("ArcCount"));
//			biacmodel.setStyleNum(rs.getString("StyleNum"));
//		}
//			
		return biacmodel;
	}

	
}
