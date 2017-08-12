package com.sf.energy.co2sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.co2sale.model.CO2SaleInfo_SchoolHQ_Model;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.util.ConnDB;

public class CO2SaleInfoDao
{
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

	public List<CO2SaleInfo_SchoolHQ_Model> loadSchoolHQ(int partid) throws SQLException{
		String sql="";
		DepartmentDao dp=new DepartmentDao();
		List<CO2SaleInfo_SchoolHQ_Model> lists=new ArrayList<CO2SaleInfo_SchoolHQ_Model>();
		if(partid==0){
			sql="select * from CO2SaleInfo where orderStatus<>1";
		}else {
			sql="select * from CO2SaleInfo where orderStatus<>1 and (Sale_partmentid="+partid+" or Buy_Partmentid="+partid+")";
		}
		CO2SaleInfo_SchoolHQ_Model co=null;
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
				co=new CO2SaleInfo_SchoolHQ_Model();
				co.setSaleInfoID(rs.getInt("SaleInfoID"));
				co.setBuyORSale(rs.getInt("BuyOrSale"));
				if(rs.getInt("BuyOrSale")==0){//卖
					co.setSale_partmentid(rs.getInt("Sale_partmentid"));
					co.setSale_partmentName(dp.queryNameById(rs.getInt("Sale_partmentid")));
				}else{
					co.setBuy_partmentid(rs.getInt("Buy_partmentid"));
					co.setBuy_partmentName(dp.queryNameById(rs.getInt("Buy_partmentid")));
				}
				co.setAddTime(df.format(rs.getDate("addTime")));
				co.setStatus(rs.getInt("orderStatus"));
				co.setContactPerson(rs.getString("ContactPerson"));
				co.setContactInformation(rs.getString("ContactInformation"));
				co.setPrice(rs.getFloat("price"));
				if(rs.getInt("orderStatus")==1){
					co.setDealTime(df.format(rs.getDate("dealTime")));
				}		
				co.setDealValue(rs.getFloat("dealValue"));
				co.setRemainValue(rs.getFloat("RemainValue"));
				lists.add(co);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//		while(rs.next())
		//		{
		//			co=new CO2SaleInfo_SchoolHQ_Model();
		//			co.setSaleInfoID(rs.getInt("SaleInfoID"));
		//			co.setBuyORSale(rs.getInt("BuyOrSale"));
		//			if(rs.getInt("BuyOrSale")==0){//卖
		//				co.setSale_partmentid(rs.getInt("Sale_partmentid"));
		//				co.setSale_partmentName(dp.queryNameById(rs.getInt("Sale_partmentid")));
		//			}else{
		//				co.setBuy_partmentid(rs.getInt("Buy_partmentid"));
		//				co.setBuy_partmentName(dp.queryNameById(rs.getInt("Buy_partmentid")));
		//			}
		//			co.setAddTime(df.format(rs.getDate("addTime")));
		//			co.setStatus(rs.getInt("orderStatus"));
		//			co.setContactPerson(rs.getString("ContactPerson"));
		//			co.setContactInformation(rs.getString("ContactInformation"));
		//			co.setPrice(rs.getFloat("price"));
		//			if(rs.getInt("orderStatus")==1){
		//				co.setDealTime(df.format(rs.getDate("dealTime")));
		//			}		
		//			co.setDealValue(rs.getFloat("dealValue"));
		//			co.setRemainValue(rs.getFloat("RemainValue"));
		//			lists.add(co);
		//		}

		return lists;
	}

	public String saleOut(int saleInfo_id, int sale_partmentid) throws SQLException
	{
		String returnMsg="";
		String sql="update Co2SaleInfo set sale_partmentid="+sale_partmentid+" ,orderStatus=2 where saleinfoid="+saleInfo_id;
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);


			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		return returnMsg;
	}

	public String confirmPayment(int saleInfo_id) throws SQLException
	{
		String returnMsg="";
		String sql="update Co2SaleInfo set orderStatus=1, dealtime=sysdate where saleinfoid="+saleInfo_id;
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);


			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		return returnMsg;
	}

	public String deleteSaleInfo(int saleInfo_id) throws SQLException
	{
		String returnMsg="";
		String sql="delete Co2SaleInfo where saleinfoid="+saleInfo_id;
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);


			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		return returnMsg;
	}

	/**添加行情
	 * @param partmentid
	 * @param buyORSale
	 * @param price
	 * @param dealvalue
	 * @param contactPerson
	 * @param contactInformation
	 * @return
	 * @throws SQLException
	 */
	public String addHQ(int partmentid, int buyORSale, float price, float dealvalue, String contactPerson, String contactInformation) throws SQLException
	{
		String returnMsg="";
		String sql="";
		if(buyORSale==0){
			sql="insert into Co2SaleInfo(sale_partmentid,buyORSale,price,dealvalue,contactPerson,contactInformation,addTime,orderStatus) values(?,?,?,?,?,?,sysdate,0)";
		}else{
			sql="insert into Co2SaleInfo(buy_partmentid,buyORSale,price,dealvalue,contactPerson,contactInformation,addTime,orderStatus) values(?,?,?,?,?,?,sysdate,0)";
		}
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			ps.setInt(1, partmentid);
			ps.setInt(2, buyORSale);
			ps.setFloat(3, price);
			ps.setFloat(4, dealvalue);
			ps.setString(5, contactPerson);
			ps.setString(6, contactInformation);

			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}

		//		ps.setInt(1, partmentid);
		//		ps.setInt(2, buyORSale);
		//		ps.setFloat(3, price);
		//		ps.setFloat(4, dealvalue);
		//		ps.setString(5, contactPerson);
		//		ps.setString(6, contactInformation);


		return returnMsg;
	}

	public String buyIn(int saleInfo_id, int buy_partmentid) throws SQLException
	{
		String returnMsg="";
		String sql="update Co2SaleInfo set buy_partmentid="+buy_partmentid+" ,orderStatus=2 where saleinfoid="+saleInfo_id;
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);


			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}
		return returnMsg;
	}

	public String editHQ(int saleInfo_id, int partmentid, int buyORSale, float price, float dealvalue, String contactPerson, String contactInformation) throws SQLException
	{
		String returnMsg="";
		String sql="";
		if(buyORSale==0){
			sql="update Co2SaleInfo set sale_partmentid=?,buyOrSale=?,price=?,dealvalue=?,contactPerson=?,contactInformation=? where saleinfoid=?";
		}else{
			sql="update Co2SaleInfo set buy_partmentid=?,buyOrSale=?,price=?,dealvalue=?,contactPerson=?,contactInformation=? where saleinfoid=?";
		}
		Connection conn0=null;
		PreparedStatement ps =null;

		try
		{
			conn0 = ConnDB.getConnection();
			ps = conn0.prepareStatement(sql);
			ps.setInt(1, partmentid);
			ps.setInt(2, buyORSale);
			ps.setFloat(3, price);
			ps.setFloat(4, dealvalue);
			ps.setString(5, contactPerson);
			ps.setString(6, contactInformation);
			ps.setInt(7, saleInfo_id);

			if(ps.executeUpdate() == 1)
			{
				returnMsg = "success";
			}else
			{
				returnMsg = "fail";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, ps);
		}

		//		ps.setInt(1, partmentid);
		//		ps.setInt(2, buyORSale);
		//		ps.setFloat(3, price);
		//		ps.setFloat(4, dealvalue);
		//		ps.setString(5, contactPerson);
		//		ps.setString(6, contactInformation);
		//		ps.setInt(7, saleInfo_id);
		return returnMsg;
	}

	public List<CO2SaleInfo_SchoolHQ_Model> loadSchoolCJ(int partid) throws SQLException
	{
		String sql="";
		DepartmentDao dp=new DepartmentDao();
		List<CO2SaleInfo_SchoolHQ_Model> lists=new ArrayList<CO2SaleInfo_SchoolHQ_Model>();
		if(partid==0){
			sql="select * from CO2SaleInfo where orderStatus=1";
		}else {
			sql="select * from CO2SaleInfo where orderStatus=1 and (Sale_partmentid="+partid+" or Buy_Partmentid="+partid+")";
		}
		CO2SaleInfo_SchoolHQ_Model co=null;
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
				co=new CO2SaleInfo_SchoolHQ_Model();
				co.setSaleInfoID(rs.getInt("SaleInfoID"));
				co.setBuyORSale(rs.getInt("BuyOrSale"));
				co.setSale_partmentid(rs.getInt("Sale_partmentid"));
				co.setSale_partmentName(dp.queryNameById(rs.getInt("Sale_partmentid")));
				co.setBuy_partmentid(rs.getInt("Buy_partmentid"));
				co.setBuy_partmentName(dp.queryNameById(rs.getInt("Buy_partmentid")));
				co.setAddTime(df.format(rs.getDate("addTime")));
				co.setStatus(rs.getInt("orderStatus"));
				co.setContactPerson(rs.getString("ContactPerson"));
				co.setContactInformation(rs.getString("ContactInformation"));
				co.setPrice(rs.getFloat("price"));
				co.setDealTime(df.format(rs.getDate("dealTime")));
				co.setDealValue(rs.getFloat("dealValue"));
				co.setRemainValue(rs.getFloat("RemainValue"));
				lists.add(co);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//		while(rs.next())
		//		{
		//			co=new CO2SaleInfo_SchoolHQ_Model();
		//			co.setSaleInfoID(rs.getInt("SaleInfoID"));
		//			co.setBuyORSale(rs.getInt("BuyOrSale"));
		//			co.setSale_partmentid(rs.getInt("Sale_partmentid"));
		//			co.setSale_partmentName(dp.queryNameById(rs.getInt("Sale_partmentid")));
		//			co.setBuy_partmentid(rs.getInt("Buy_partmentid"));
		//			co.setBuy_partmentName(dp.queryNameById(rs.getInt("Buy_partmentid")));
		//			co.setAddTime(df.format(rs.getDate("addTime")));
		//			co.setStatus(rs.getInt("orderStatus"));
		//			co.setContactPerson(rs.getString("ContactPerson"));
		//			co.setContactInformation(rs.getString("ContactInformation"));
		//			co.setPrice(rs.getFloat("price"));
		//			co.setDealTime(df.format(rs.getDate("dealTime")));
		//			co.setDealValue(rs.getFloat("dealValue"));
		//			co.setRemainValue(rs.getFloat("RemainValue"));
		//			lists.add(co);
		//		}

		return lists;
	}
}
