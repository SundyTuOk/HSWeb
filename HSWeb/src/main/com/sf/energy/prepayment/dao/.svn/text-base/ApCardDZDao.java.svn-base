package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.ApCardDZModel;
import com.sf.energy.prepayment.model.ApCardSaleInfoModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.util.ConnDB;

public class ApCardDZDao
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

	public ArrayList<ApCardDZModel> queryInfo(int thePageCount, int thePageIndex, String queryMsg, String order) throws SQLException
	{
		ArrayList<ApCardDZModel> list = new ArrayList<ApCardDZModel>();
		ApCardDZModel am = null;

		String where = "";

		if (!"".equals(queryMsg) && queryMsg != null)
		{
			String[] msg = queryMsg.split("\\|");
			where = " where INSERTTIME>=" + "to_date('" + msg[0] + "','yyyy-mm-dd')" + " and INSERTTIME<=" + "(to_date('" + msg[1]
					+ "','yyyy-mm-dd')+1) ";
		}

		String sql = "select APCARDDZ_ID,APCARDDZTIME,CARDMONEY,APMONEY,CMONEY,CARDCOUNTTIMES,CARDTIMES,APTIMES,EROORTIMES,EXECTIMES,(select Users_Name from Users where Users_ID=a.USERS_ID)Users_Name,(to_char(INSERTTIME,'yyyy-mm-dd hh24:mi'))insertTime from (APCardDZ)a "
				+ where + order;

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (thePageCount * thePageIndex))
				return list;

			count = count - thePageCount * thePageIndex;

			if (count >= thePageCount)
				count = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (count > 0))
			{
				am = new ApCardDZModel();
				am.setAPCardDZ_ID(rs.getInt("APCARDDZ_ID"));
				am.setAPCardDZTime(rs.getString("APCARDDZTIME"));
				am.setCardMoney(rs.getFloat("CARDMONEY"));
				am.setApMoney(rs.getFloat("APMONEY"));
				am.setCMoney(rs.getFloat("CMONEY"));
				am.setCardTimes(rs.getInt("CARDTIMES"));
				am.setAPTimes(rs.getInt("APTIMES"));
				am.setEroorTimes(rs.getInt("EROORTIMES"));
				am.setExecTimes(rs.getInt("EXECTIMES"));
				if (rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
					am.setUserName(rs.getString("Users_Name"));
				am.setInsertTime(rs.getString("INSERTTIME"));
				list.add(am);
				count--;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		rs = ps.executeQuery();
//
//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (thePageCount * thePageIndex))
//			return list;
//
//		count = count - thePageCount * thePageIndex;
//
//		if (count >= thePageCount)
//			count = thePageCount;
//
//		if ((thePageCount * thePageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(thePageCount * thePageIndex);
//
//		while (rs.next() && (count > 0))
//		{
//			am = new ApCardDZModel();
//			am.setAPCardDZ_ID(rs.getInt("APCARDDZ_ID"));
//			am.setAPCardDZTime(rs.getString("APCARDDZTIME"));
//			am.setCardMoney(rs.getFloat("CARDMONEY"));
//			am.setApMoney(rs.getFloat("APMONEY"));
//			am.setCMoney(rs.getFloat("CMONEY"));
//			am.setCardTimes(rs.getInt("CARDTIMES"));
//			am.setAPTimes(rs.getInt("APTIMES"));
//			am.setEroorTimes(rs.getInt("EROORTIMES"));
//			am.setExecTimes(rs.getInt("EXECTIMES"));
//			if (rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
//				am.setUserName(rs.getString("Users_Name"));
//			am.setInsertTime(rs.getString("INSERTTIME"));
//			list.add(am);
//			count--;
//		}


		return list;
	}

	public int delete(String theID) throws SQLException
	{
		String sql = "delete from APCardDZ where APCARDDZ_ID in(" + theID + ")";
		String sql1 = "delete from APCardErrorInfo where APCardDZ_ID in(" + theID + ")";
		String sql2 = "delete from APCardSaleInfo where APCardDZ_ID in(" + theID + ")";
		PreparedStatement pst=null;
		int result=0;
		Connection conn =null;
		try
		{
			conn =ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			result = pst.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pst);
		}
		
		
		PreparedStatement pst1=null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pst1 = conn1.prepareStatement(sql1);
			pst1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, pst1);
		}
		
		
		PreparedStatement pst2=null;
		Connection conn2 = null;
		try
		{
			conn2 =ConnDB.getConnection();
			pst2 = conn2.prepareStatement(sql2);

			pst2.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, pst2);
		}


		return result;
	}

	public int insertDZInfo(String dzTime, int userID) throws SQLException
	{
		String sql = " insert into APCardDZ (APCardDZTime, CardMoney, ApMoney, CMoney,CardCountTimes, CardTimes, APTimes, EroorTimes, ExecTimes,Users_ID,insertTime) values('"
				+ dzTime + "', 0, 0, 0,0, 0, 0, 0, 0," + userID + ",SYSDATE)";

		PreparedStatement pst=null;
		Connection conn = null;
		try
		{
			conn =ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pst);
		}
		/*
		 * int id = 0; ResultSet rs = pst.getGeneratedKeys(); if(rs.next()){ id
		 * = rs.getInt(1); }
		 */

		String sqlString = "select nvl(MAX(APCARDDZ_ID),0)ID from APCardDZ";
		PreparedStatement pStatement=null;
		ResultSet rs=null;
		int id=0;
		Connection conn0 =null;
		try
		{
			conn0 = ConnDB.getConnection();
			pStatement = conn0.prepareStatement(sqlString);
			rs = pStatement.executeQuery();
			id = 0;
			if (rs.next())
			{
				id = rs.getInt("ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, pStatement, rs);
		}

		return id;
	}

	public String getSaleInfoNum(String dzTime, String studentNum, String theMoney, int aPCardDZ_ID) throws SQLException
	{
		String salInfoNum = "";
		String sqlString = "select SaleInfoNum from ApSaleInfo where Kind=1 and to_char(BuyTime,'yyyy-mm-dd')='" + dzTime + "' and studentID='"
				+ studentNum + "' and TheMoney=" + theMoney + " and SaleInfoNum not in(select SaleInfoNum from APCardSaleInfo where APCardDZ_ID="
				+ aPCardDZ_ID + ")";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				salInfoNum = rs.getString(1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			salInfoNum = rs.getString(1);
//		}



		return salInfoNum;
	}

	public int insertAPCardInfo(int aPCardDZ_ID, String buyTime, String posNum, String cardManNum, String cardManName, String studentNum,
			String theMoney, String yMoney, String userTimes, String state, String saleInfoNum) throws SQLException
	{
		String sqlString = "insert into APCardSaleInfo(APCardDZ_ID,BuyTime,PosNum,CardManNum,CardManName,StudentNum,TheMoney,YMoney,UserTimes,State,Remark,SaleInfoNum) values"
				+ "("
				+ aPCardDZ_ID
				+ ",to_date('"
				+ buyTime
				+ "','yyyy-mm-dd hh24:mi:ss'),'"
				+ posNum
				+ "','"
				+ cardManNum
				+ "',trim('"
				+ cardManName
				+ "'),trim('" + studentNum + "'),'" + theMoney + "','" + yMoney + "','" + userTimes + "','" + state + "','','" + saleInfoNum + "')";
		PreparedStatement pst=null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			pst = conn0.prepareStatement(sqlString);
			pst.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn0, pst);
		}

		String sql = "select nvl(MAX(APCARDSALEINFO_ID),0)ID from APCardSaleInfo";
		PreparedStatement pst2=null;
		int id=0;
		ResultSet rs=null;
		Connection conn2 = null;
		try
		{
			conn2 =ConnDB.getConnection();
			pst2 = conn2.prepareStatement(sql);

			id = 0;
			rs = pst2.executeQuery();
			if (rs.next())
			{
				id = rs.getInt("ID");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, pst2, rs);
		}

		return id;
	}

	public void insertAPCardErrorInfo(int aPCardSaleInfo_ID, int aPCardDZ_ID, String saleInfoNum, String errorType, int execState, String buyTime,
			String ammeter_name) throws SQLException
	{
		String sql = "insert into APCardErrorInfo(APCardSaleInfo_ID,APCardDZ_ID,SaleInfoNum,ErrorType,ExecState,BuyTime,AmMeter_Name) values("
				+ aPCardSaleInfo_ID + ",'" + aPCardDZ_ID + "','" + saleInfoNum + "','" + errorType + "'," + execState + ",to_date('" + buyTime
				+ "','yyyy/mm/dd hh24:mi:ss'),'" + ammeter_name + "') ";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	public String getAmName(String saleInfoNum) throws SQLException
	{
		String ammeter_name = "";
		String sql = "select AmMeter_Name from ApSaleInfo where SaleInfoNum='" + saleInfoNum + "'";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				ammeter_name = rs.getString(1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			ammeter_name = rs.getString(1);
//		}

		return ammeter_name;
	}

	public ArrayList<SaleDetailsModel> getSaleInfo(String dzTime, int aPCardDZ_ID) throws SQLException
	{
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		SaleDetailsModel sdm = null;

		String sql = "select SaleInfoNum,BuyTime,AmMeter_Name from ApSaleInfo where Kind=1 and to_char(BuyTime,'yyyy-mm-dd')='" + dzTime
				+ "'  and SaleInfoNum not in(select SaleInfoNum from APCardSaleInfo where APCardDZ_ID=" + aPCardDZ_ID + ")";
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
				sdm = new SaleDetailsModel();
				sdm.setSALEINFONUM(rs.getString("SaleInfoNum"));
				sdm.setBuyTime(rs.getString("BuyTime"));
				sdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				list.add(sdm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		while (rs.next())
//		{
//			sdm = new SaleDetailsModel();
//			sdm.setSALEINFONUM(rs.getString("SaleInfoNum"));
//			sdm.setBuyTime(rs.getString("BuyTime"));
//			sdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
//			list.add(sdm);
//		}

		return list;
	}

	public ArrayList<String> getTotalInfo(String dzTime) throws SQLException
	{
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select nvl(sum(TheMoney),0)ApMoney,count(SaleInfoNum)APTimes from ApSaleInfo where Kind=1 and to_char(BuyTime,'yyyy-mm-dd')='"
				+ dzTime + "'  ";

		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			

			if (rs.next())
			{
				list.add(rs.getString("ApMoney"));
				list.add(rs.getString("APTimes"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		if (rs.next())
//		{
//			list.add(rs.getString("ApMoney"));
//			list.add(rs.getString("APTimes"));
//		}


		return list;
	}

	public void updateCardDZInfo(float cardMoney, float apMoney, float cMoney, int cardCountTimes, int cardTimes, int aPTimes, int eroorTimes,
			int aPCardDZ_ID) throws SQLException
	{
		String sql = "update APCardDZ set CardMoney=" + cardMoney + ", ApMoney=" + apMoney + ", CMoney=" + cMoney + ",CardCountTimes="
				+ cardCountTimes + ", CardTimes=" + cardTimes + ", APTimes=" + aPTimes + ", EroorTimes=" + eroorTimes + " where APCardDZ_ID="
				+ aPCardDZ_ID;

		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

	}

	public ArrayList<ApCardSaleInfoModel> getCardSelectInfo(int id, int thePageCount, int thePageIndex, String order) throws SQLException
	{
		ArrayList<ApCardSaleInfoModel> list = new ArrayList<ApCardSaleInfoModel>();
		ApCardSaleInfoModel saleInfo = null;

		String sql = "SELECT     APCardSaleInfo_ID,to_char(BuyTime,'yyyy-mm-dd hh24:mi') BuyTime, PosNum, CardManNum, CardManName, StudentNum, TheMoney, YMoney, UserTimes, State FROM  APCardSaleInfo a where APCardDZ_ID="
				+ id + order;
		//System.out.println(sql);
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (thePageCount * thePageIndex))
				return list;

			count = count - thePageCount * thePageIndex;

			if (count >= thePageCount)
				count = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (count > 0))
			{
				saleInfo = new ApCardSaleInfoModel();
				saleInfo.setAPCardDZ_ID(rs.getInt("APCardSaleInfo_ID"));
				saleInfo.setBuyTime(rs.getString("BuyTime"));
				saleInfo.setPosNum(rs.getString("PosNum"));
				saleInfo.setCardManNum(rs.getString("CardManNum"));
				saleInfo.setCardManName(rs.getString("CardManName"));
				saleInfo.setStudentNum(rs.getString("StudentNum"));
				saleInfo.setTheMoney(rs.getFloat("TheMoney"));
				saleInfo.setYMoney(rs.getFloat("YMoney"));
				saleInfo.setUserTimes(rs.getInt("UserTimes"));
				if(!"".equals(rs.getString("State")) && rs.getString("State")!=null && !"null".equalsIgnoreCase(rs.getString("State"))){
					saleInfo.setState(rs.getString("State"));
				}else{
					saleInfo.setState("");
				}
				
				list.add(saleInfo);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (thePageCount * thePageIndex))
//			return list;
//
//		count = count - thePageCount * thePageIndex;
//
//		if (count >= thePageCount)
//			count = thePageCount;
//
//		if ((thePageCount * thePageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(thePageCount * thePageIndex);
//
//		while (rs.next() && (count > 0))
//		{
//			saleInfo = new ApCardSaleInfoModel();
//			saleInfo.setAPCardDZ_ID(rs.getInt("APCardSaleInfo_ID"));
//			saleInfo.setBuyTime(rs.getString("BuyTime"));
//			saleInfo.setPosNum(rs.getString("PosNum"));
//			saleInfo.setCardManNum(rs.getString("CardManNum"));
//			saleInfo.setCardManName(rs.getString("CardManName"));
//			saleInfo.setStudentNum(rs.getString("StudentNum"));
//			saleInfo.setTheMoney(rs.getFloat("TheMoney"));
//			saleInfo.setYMoney(rs.getFloat("YMoney"));
//			saleInfo.setUserTimes(rs.getInt("UserTimes"));
//			if(!"".equals(rs.getString("State")) && rs.getString("State")!=null && !"null".equalsIgnoreCase(rs.getString("State"))){
//				saleInfo.setState(rs.getString("State"));
//			}else{
//				saleInfo.setState("");
//			}
//			
//			list.add(saleInfo);
//			count--;
//		}

		
		return list;
	}

	public ArrayList<ApCardSaleInfoModel> getSaleSelectInfo(int id, int thePageCount, int thePageIndex, String order) throws SQLException
	{
		ArrayList<ApCardSaleInfoModel> list = new ArrayList<ApCardSaleInfoModel>();
		ApCardSaleInfoModel saleInfo = null;
		String sql = "select a.AmMeter_ID,SaleInfoNum,studentID,Times,Kind,QSYValue,HSYValue,"
				+ "(select Architecture_Name from Architecture c where   b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
				+ "(Floor)FloorName,a.AmMeter_Name,"
				+ "(select Users_Name from Users b where a.Users_ID=b.Users_ID)Users_Name,BuyTime,SendTime,Status,Price,TheGross,TheMoney from (select * from APSaleInfo where to_char(BuyTime,'yyyy-mm-dd')=(select APCardDZTime from APCardDZ where APCardDZ_ID="
				+ id + ") and kind=1 )  a,(select * from AmMeter ) b where a.Ammeter_ID=b.Ammeter_ID" + order;
		//System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (thePageCount * thePageIndex))//thePageIndex:0,1,2,3.....比页面上的页码数小1，当前页码:thePageIndex+1
				return list;

			count = count - thePageCount * thePageIndex;//thePageCount * thePageIndex:前几页的数   count:剩余数

			if (count >= thePageCount)//如果count大于当前页的容量,count为当前页数量
				count = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (count > 0))
			{
				saleInfo = new ApCardSaleInfoModel();
				saleInfo.setSaleInfoNum(rs.getString("SaleInfoNum"));
				saleInfo.setStudentID(rs.getString("studentID"));
				saleInfo.setArchitecture_Name(rs.getString("Architecture_Name"));
				saleInfo.setFloorName(rs.getString("FloorName"));
				saleInfo.setAmMeter_Name(rs.getString("AmMeter_Name"));
				saleInfo.setKind(rs.getString("Kind"));
				saleInfo.setStatus(rs.getString("Status"));
				saleInfo.setBuyTime(rs.getString("BuyTime"));
				saleInfo.setPrice(rs.getFloat("Price"));
				saleInfo.setTheGross(rs.getFloat("TheGross"));
				saleInfo.setTheMoney(rs.getFloat("TheMoney"));
				saleInfo.setSendTime(rs.getString("SendTime"));
				saleInfo.setQSYValue(rs.getFloat("QSYValue"));
				saleInfo.setHSYValue(rs.getFloat("HSYValue"));
				list.add(saleInfo);
				count--;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (thePageCount * thePageIndex))//thePageIndex:0,1,2,3.....比页面上的页码数小1，当前页码:thePageIndex+1
//			return list;
//
//		count = count - thePageCount * thePageIndex;//thePageCount * thePageIndex:前几页的数   count:剩余数
//
//		if (count >= thePageCount)//如果count大于当前页的容量,count为当前页数量
//			count = thePageCount;
//
//		if ((thePageCount * thePageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(thePageCount * thePageIndex);
//
//		while (rs.next() && (count > 0))
//		{
//			saleInfo = new ApCardSaleInfoModel();
//			saleInfo.setSaleInfoNum(rs.getString("SaleInfoNum"));
//			saleInfo.setStudentID(rs.getString("studentID"));
//			saleInfo.setArchitecture_Name(rs.getString("Architecture_Name"));
//			saleInfo.setFloorName(rs.getString("FloorName"));
//			saleInfo.setAmMeter_Name(rs.getString("AmMeter_Name"));
//			saleInfo.setKind(rs.getString("Kind"));
//			saleInfo.setStatus(rs.getString("Status"));
//			saleInfo.setBuyTime(rs.getString("BuyTime"));
//			saleInfo.setPrice(rs.getFloat("Price"));
//			saleInfo.setTheGross(rs.getFloat("TheGross"));
//			saleInfo.setTheMoney(rs.getFloat("TheMoney"));
//			saleInfo.setSendTime(rs.getString("SendTime"));
//			saleInfo.setQSYValue(rs.getFloat("QSYValue"));
//			saleInfo.setHSYValue(rs.getFloat("HSYValue"));
//			list.add(saleInfo);
//			count--;
//		}


		return list;
	}

	public ArrayList<ApCardSaleInfoModel> getDZSelectInfo(int id, int thePageCount, int thePageIndex, String order) throws SQLException
	{
		ArrayList<ApCardSaleInfoModel> list = new ArrayList<ApCardSaleInfoModel>();
		ApCardSaleInfoModel saleInfo = null;
		String sql = "SELECT     APCardSaleInfo_ID,to_char(a.BuyTime,'yyyy-mm-dd hh24:mi') BuyTime, PosNum, CardManNum, CardManName, StudentNum, a.TheMoney, YMoney, UserTimes, State,a.SaleInfoNum,AmMeter_Name FROM  APCardSaleInfo a left join APSaleInfo c on a.SaleInfoNum=c.SaleInfoNum where APCardDZ_ID=" + id
				+ "union all" 
				+ "(select APCardSaleInfo_ID,to_char(a.BuyTime,'yyyy-mm-dd hh24:mi') BuyTime,'','','','',TheMoney,0,0,'',a.SaleInfoNum,a.AmMeter_Name from APSaleInfo a,APCardErrorInfo b where a.SaleInfoNum=b.SaleInfoNum  and APCardDZ_ID=" + id + ")" + order;
		//System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (thePageCount * thePageIndex))//thePageIndex:0,1,2,3.....比页面上的页码数小1，当前页码:thePageIndex+1
				return list;

			count = count - thePageCount * thePageIndex;//thePageCount * thePageIndex:前几页的数   count:剩余数

			if (count >= thePageCount)//如果count大于当前页的容量,count为当前页数量
				count = thePageCount;

			if ((thePageCount * thePageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(thePageCount * thePageIndex);

			while (rs.next() && (count > 0))
			{
				saleInfo = new ApCardSaleInfoModel();
				saleInfo.setBuyTime(rs.getString("BuyTime"));
				if(!"".equals(rs.getString("PosNum")) && rs.getString("PosNum")!=null && !"null".equalsIgnoreCase(rs.getString("PosNum"))){
					saleInfo.setPosNum(rs.getString("PosNum"));
				}else{
					saleInfo.setPosNum("");
				}
				if(!"".equals(rs.getString("CardManNum")) && rs.getString("CardManNum")!=null && !"null".equalsIgnoreCase(rs.getString("CardManNum"))){
					saleInfo.setCardManNum(rs.getString("CardManNum"));
				}else{
					saleInfo.setCardManNum("");
				}
				if(!"".equals(rs.getString("CardManName")) && rs.getString("CardManName")!=null && !"null".equalsIgnoreCase(rs.getString("CardManName"))){
					saleInfo.setCardManName(rs.getString("CardManName"));
				}else{
					saleInfo.setCardManName("");
				}
				if(!"".equals(rs.getString("StudentNum")) && rs.getString("StudentNum")!=null && !"null".equalsIgnoreCase(rs.getString("StudentNum"))){
					saleInfo.setStudentNum(rs.getString("StudentNum"));
				}else{
					saleInfo.setStudentNum("");
				}
				
				saleInfo.setTheMoney(rs.getFloat("TheMoney"));
				saleInfo.setYMoney(rs.getFloat("YMoney"));
				saleInfo.setUserTimes(rs.getInt("UserTimes"));
				
				if(!"".equals(rs.getString("State")) && rs.getString("State")!=null && !"null".equalsIgnoreCase(rs.getString("State"))){
					saleInfo.setState(rs.getString("State"));
				}else{
					saleInfo.setState("");
				}
				if(!"".equals(rs.getString("SaleInfoNum")) && rs.getString("SaleInfoNum")!=null && !"null".equalsIgnoreCase(rs.getString("SaleInfoNum"))){
					saleInfo.setSaleInfoNum(rs.getString("SaleInfoNum"));
				}else{
					saleInfo.setSaleInfoNum("");
				}
				if(!"".equals(rs.getString("AmMeter_Name")) && rs.getString("AmMeter_Name")!=null && !"null".equalsIgnoreCase(rs.getString("AmMeter_Name"))){
					saleInfo.setAmMeter_Name(rs.getString("AmMeter_Name"));
				}else{
					saleInfo.setAmMeter_Name("");;
				}
				
				list.add(saleInfo);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (thePageCount * thePageIndex))//thePageIndex:0,1,2,3.....比页面上的页码数小1，当前页码:thePageIndex+1
//			return list;
//
//		count = count - thePageCount * thePageIndex;//thePageCount * thePageIndex:前几页的数   count:剩余数
//
//		if (count >= thePageCount)//如果count大于当前页的容量,count为当前页数量
//			count = thePageCount;
//
//		if ((thePageCount * thePageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(thePageCount * thePageIndex);
//
//		while (rs.next() && (count > 0))
//		{
//			saleInfo = new ApCardSaleInfoModel();
//			saleInfo.setBuyTime(rs.getString("BuyTime"));
//			if(!"".equals(rs.getString("PosNum")) && rs.getString("PosNum")!=null && !"null".equalsIgnoreCase(rs.getString("PosNum"))){
//				saleInfo.setPosNum(rs.getString("PosNum"));
//			}else{
//				saleInfo.setPosNum("");
//			}
//			if(!"".equals(rs.getString("CardManNum")) && rs.getString("CardManNum")!=null && !"null".equalsIgnoreCase(rs.getString("CardManNum"))){
//				saleInfo.setCardManNum(rs.getString("CardManNum"));
//			}else{
//				saleInfo.setCardManNum("");
//			}
//			if(!"".equals(rs.getString("CardManName")) && rs.getString("CardManName")!=null && !"null".equalsIgnoreCase(rs.getString("CardManName"))){
//				saleInfo.setCardManName(rs.getString("CardManName"));
//			}else{
//				saleInfo.setCardManName("");
//			}
//			if(!"".equals(rs.getString("StudentNum")) && rs.getString("StudentNum")!=null && !"null".equalsIgnoreCase(rs.getString("StudentNum"))){
//				saleInfo.setStudentNum(rs.getString("StudentNum"));
//			}else{
//				saleInfo.setStudentNum("");
//			}
//			
//			saleInfo.setTheMoney(rs.getFloat("TheMoney"));
//			saleInfo.setYMoney(rs.getFloat("YMoney"));
//			saleInfo.setUserTimes(rs.getInt("UserTimes"));
//			
//			if(!"".equals(rs.getString("State")) && rs.getString("State")!=null && !"null".equalsIgnoreCase(rs.getString("State"))){
//				saleInfo.setState(rs.getString("State"));
//			}else{
//				saleInfo.setState("");
//			}
//			if(!"".equals(rs.getString("SaleInfoNum")) && rs.getString("SaleInfoNum")!=null && !"null".equalsIgnoreCase(rs.getString("SaleInfoNum"))){
//				saleInfo.setSaleInfoNum(rs.getString("SaleInfoNum"));
//			}else{
//				saleInfo.setSaleInfoNum("");
//			}
//			if(!"".equals(rs.getString("AmMeter_Name")) && rs.getString("AmMeter_Name")!=null && !"null".equalsIgnoreCase(rs.getString("AmMeter_Name"))){
//				saleInfo.setAmMeter_Name(rs.getString("AmMeter_Name"));
//			}else{
//				saleInfo.setAmMeter_Name("");;
//			}
//			
//			list.add(saleInfo);
//			count--;
//		}

		
		return list;
	}

}
