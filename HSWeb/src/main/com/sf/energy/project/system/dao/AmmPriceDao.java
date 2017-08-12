package com.sf.energy.project.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.system.model.AmmPriceModel;
import com.sf.energy.util.ConnDB;

public class AmmPriceDao
{
	/**
	 * 查询电表盒水表费率
	 * @param priceID 价格ID
	 * @return 价格表实体类
	 * @throws SQLException
	 */
	public AmmPriceModel queryByID(int priceID) throws SQLException
	{
		AmmPriceModel price=new AmmPriceModel();
		String sql="select * from price where price_ID="+priceID;
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
				price.setPriceID(rs.getInt("price_ID"));
				price.setPriceNum(rs.getString("price_Num"));
				price.setPriceName(rs.getString("price_Name"));
				price.setPriceStyle(rs.getString("price_Style"));
				price.setPriceValue(rs.getDouble("price_Value"));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return price;
	}
	
	/**
	 * 列出所有的价格条目
	 * @param priceNum 价格编码，E:电表，W:水表
	 * @return 价格条目列表
	 * @throws SQLException
	 */

	public ArrayList<AmmPriceModel> listAllPriceValue( String priceNum) throws SQLException
	{
		ArrayList<AmmPriceModel> list=new ArrayList<AmmPriceModel>();
		AmmPriceModel price;
		String sql="select * from price where Price_Style='"+priceNum+"'";
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
				price=new AmmPriceModel();
				price.setPriceID(rs.getInt("price_ID"));
				price.setPriceNum(rs.getString("price_Num"));
				price.setPriceName(rs.getString("price_Name"));
				price.setPriceStyle(rs.getString("price_Style"));
				price.setPriceValue(rs.getDouble("price_Value"));
				list.add(price);
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
	 * 根据id查名称
	 * @param priceID
	 * @return
	 * @throws SQLException
	 */
	public String queryNameByID(int priceID) throws SQLException
	{
		String name = "";
		String sql="select price_name from price where price_ID="+priceID;
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
				name = rs.getString("price_name");

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return name;
	}
}
