package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.ExceptionModel;
import com.sf.energy.util.ConnDB;

public class ExceptionDao
{
	private int totalCount = 0;

	public ArrayList<ExceptionModel> queryInfo(int thePageCount, int thePageIndex, String order, String errorType, String strExecState,
			String startDate, String endDate) throws SQLException
	{
		ArrayList<ExceptionModel> list = new ArrayList<ExceptionModel>();

		String where = "";
		if (!"全部".equalsIgnoreCase(errorType))
		{
			where = " and ErrorType='" + errorType + "' ";
		}
		if (!"全部".equalsIgnoreCase(strExecState))
		{
			where += " and ExecState=" + strExecState + " ";
		}
		int start = thePageCount * thePageIndex;
		int end = thePageCount * (thePageIndex + 1);
		String sql = "select  * from (SELECT ROWNUM rt,d.* FROM(SELECT * FROM( "
				+ "SELECT APCardErrorInfo_ID, to_char(a.BuyTime,'yyyy-mm-dd hh24:mi:ss') BuyTime,"
				+ "PosNum,CardManNum,CardManName,StudentNum,TheMoney,a.SaleInfoNum ,AmMeter_Name,ErrorType ,ExecState FROM (APCardErrorInfo) a left join (APCardSaleInfo) b on a.APCardSaleInfo_ID=b.APCardSaleInfo_ID  where a.APCardSaleInfo_ID>0  and a.BuyTime>=to_date('"
				+ startDate
				+ "','yyyy-mm-dd') and a.BuyTime<=to_date('"
				+ endDate
				+ "','yyyy-mm-dd')+1 "
				+ where
				+ "UNION ALL "
				+ "(SELECT APCardErrorInfo_ID, to_char(a.BuyTime,'yyyy-mm-dd hh24:mi:ss') BuyTime,"
				+ "'','','',studentID as StudentNum,TheMoney,a.SaleInfoNum,a.AmMeter_Name ,ErrorType ,ExecState FROM (APCardErrorInfo)a left join (APSaleInfo)b on a.SaleInfoNum=b.SaleInfoNum  where a.APCardSaleInfo_ID=0 and a.BuyTime>=to_date('"
				+ startDate + "','yyyy-mm-dd')  and a.BuyTime<=to_date('" + endDate + "','yyyy-mm-dd')+1)" + ")c " + order + " )d)f where rt>"
				+ start + " AND rt<=" + end;

		
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
				ExceptionModel model = new ExceptionModel();
				model.setAPCardErrorInfo_ID(rs.getInt("APCardErrorInfo_ID"));
				model.setBuyTime(rs.getString("BuyTime"));
				model.setPosNum(rs.getString("PosNum"));
				model.setCardManNum(rs.getString("CardManNum"));
				model.setCardManName(rs.getString("CardManName"));
				model.setStudentNum(rs.getString("StudentNum"));
				model.setTheMoney(rs.getString("TheMoney"));
				model.setSaleInfoNum(rs.getString("SaleInfoNum"));
				model.setStudentNum(rs.getString("AmMeter_Name"));
				model.setErrorType(rs.getString("ErrorType"));
				model.setExecState(rs.getString("ExecState"));
				String sqlAmm = "select AmMeter_ID from APSaleInfo where SaleInfoNum='" + rs.getString("SaleInfoNum") + "'";
//				Connection conn1 = null;
				PreparedStatement ps1 =null;
				ResultSet rs1 =null;
				try{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sqlAmm);
					rs1 = ps1.executeQuery();
					if (rs1.next())
						model.setAmmeter_ID(rs1.getString("AmMeter_ID"));
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1,rs1);
				}

				list.add(model);
			}
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
		String sql = "delete from APCardErrorInfo where APCardErrorInfo_ID in(" + ids + ")";
		Connection conn=null;
		PreparedStatement ps = null;
		int a;
		try
		{
			conn=ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			a = ps.executeUpdate(sql);
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return a;
	}

	/**
	 * 处理一卡通异常
	 * 
	 * @param id
	 * @return 
	 * @throws SQLException
	 */
	public boolean soluteCardException(int id) throws SQLException
	{
		boolean result=false;
		String sql = "select APCardDZ_ID ,ExecState from APCardErrorInfo where APCardErrorInfo_ID=" + id;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int a =0,b=0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			if (rs.next())
			{
				String ExecState = rs.getString("ExecState");
				String APCardDZ_ID = rs.getString("APCardDZ_ID");

				String sql1 = "update APCardErrorInfo set ExecState=1  where APCardErrorInfo_ID=" + id;
//				Connection conn1 = null;
				PreparedStatement ps1 = null;
				try
				{
//					conn1 = ConnDB.getConnection();
					ps1 = conn.prepareStatement(sql1);
					 a =ps1.executeUpdate();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps1);
				}
				

				String sql2 = "update APCardDZ set ExecTimes=ExecTimes+1  where APCardDZ_ID=" + APCardDZ_ID;
				
//				Connection conn2 = null;
				PreparedStatement ps2 = null;
				try
				{
//					conn2 = ConnDB.getConnection();
					ps2 = conn.prepareStatement(sql2);
					 b = ps2.executeUpdate();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release( ps2);
				}
			}

		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
		if(a!=0 && b!=0)
			result=true;
		
		return result;	

	}

}
