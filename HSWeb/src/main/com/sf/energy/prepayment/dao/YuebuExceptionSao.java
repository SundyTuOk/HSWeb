package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.ExceptionModel;
import com.sf.energy.prepayment.model.ExceptionYueBuModel;
import com.sf.energy.util.ConnDB;

public class YuebuExceptionSao
{
	private int totalCount = 0;
	public ArrayList<ExceptionYueBuModel> queryInfo(int thePageCount, int thePageIndex, String order, String treeSelect, String startDate, String endDate) throws SQLException
	{
		ArrayList<ExceptionYueBuModel> list = new ArrayList<ExceptionYueBuModel>();
		int start = thePageCount * thePageIndex;
		int end = thePageCount * (thePageIndex + 1);
		
		String sql ="select  * from (select ROWNUM rt,dataTable.* FROM ( "+
					"SELECT ID,Ammeter_ID,Ammeter_Name,LSMancount,LSValue,to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,State,ManCount,LSZValue,ZValue,CValue,QSYValue,HSYValue,UserID, D .Users_Name FROM (	SELECT	ID,A .Ammeter_ID,A .Ammeter_Name,LSMancount,LSValue,TheTime BuyTime,State,A .ManCount,LSZValue,A .ZValue,CValue,QSYValue,HSYValue,UserID "+ 
					"FROM  (APYBYichang) A,(SELECT * FROM AmMeter) b	WHERE		A .Ammeter_ID = b.Ammeter_ID	AND TheTime >= TO_DATE ('"+startDate+"', 'yyyy-mm-dd')		AND TheTime <= TO_DATE ('"+endDate+"', 'yyyy-mm-dd') + 1 "+
					"	) c LEFT JOIN (	SELECT		Users_ID,		Users_Name	FROM		USERS)d  ON c.UserID = D .Users_ID "+order+" )dataTable"
				  + ") where rt>" + start + " AND rt<=" + end;
		
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn=ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			rs.beforeFirst();

			while (rs.next())
			{
				ExceptionYueBuModel model = new ExceptionYueBuModel();
				model.setID(rs.getInt("ID"));
				model.setAmmeter_ID(rs.getInt("Ammeter_ID"));
				model.setAmmeter_Name(rs.getString("Ammeter_Name"));
				model.setLSMancount(rs.getString("LSMancount"));
				model.setLSValue(rs.getString("LSValue"));
				model.setTheTime(rs.getString("BuyTime"));
				model.setState(rs.getString("State"));
				model.setManCount(rs.getString("ManCount"));
				model.setLSZValue(rs.getString("LSZValue"));
				model.setZValue(rs.getString("ZValue"));
				model.setCValue(rs.getString("CValue"));
				model.setQSYValue(rs.getString("QSYValue"));
				model.setHSYValue(rs.getString("HSYValue"));
				model.setUsers_Name(rs.getString("Users_Name"));
				String sql1="select ammeter_id,nvl(Beilv,1)Beilv from ammeter where ammeter_id="+rs.getInt("Ammeter_ID");
//				Connection conn1 = null;
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sql1);
					rs1 = ps1.executeQuery();
					if(rs1.next())
						model.setBeilv(rs1.getString("Beilv"));
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}
				
//				PreparedStatement ps1 = ConnDB.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//				ResultSet rs1 = ps1.executeQuery();
//				
//				if(rs1.next())
//					model.setBeilv(rs1.getString("Beilv"));
//				
//				if(rs1!=null)
//				{
//					rs1.close();
//				}
//				if (ps1!=null)
//				{
//					ps1.close();
//				}
				
				list.add(model);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
			
		}
		return list;
	}
	public int getTotalCount()
	{
		return totalCount;
	}
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int deleteInfo(String ids) throws SQLException
	{
		String sql = "delete from APYBYichang where id in(" + ids + ")";
		
//		PreparedStatement ps = null;
		int a=0;
//		try{
//		ps = ConnDB.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//	
//		a = ps.executeUpdate(sql);
//		}
//		finally{
//			if(ps!=null)
//				ps.close();
//		}
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			a = ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return a;
	}
	
	
	public float getPriceValue(int ammeter_id) throws SQLException
	{
		float price = 0;
		String sql = "select price_value from Price where price_id=(select price_id from ammeter where ammeter_id=" + ammeter_id + ")";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				price = rs.getFloat("price_value");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}

//		PreparedStatement pst = ConnDB.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = pst.executeQuery();
//		if(rs.next()){
//			price = rs.getFloat("price_value");
//		}
//		
//		if(rs != null)
//			rs.close();
//		if(pst != null)
//			pst.close();
		
		return price;
	}
	
	
	
	
	

}
