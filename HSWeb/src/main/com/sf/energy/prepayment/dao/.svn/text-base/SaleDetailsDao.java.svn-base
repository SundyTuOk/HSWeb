package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.util.ConnDB;

public class SaleDetailsDao
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

	/**
	 * 查询售电明细
	 * @param pageCount 分页大小
	 * @param pageIndex 当前页码
	 * @param selectInfo 选择信息
	 * @param queryMsg 查询条件
	 * @param isPage
	 * @param order 
	 * @param tableName 
	 * @return
	 * @throws SQLException
	 */
	private DateFormat dfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dfh = new SimpleDateFormat("HH:mm:ss");
	private DateFormat dfd = new SimpleDateFormat("yyyyMMdd");
	private DateFormat dfd1 = new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat doubledf = new DecimalFormat("0.00");
	public ArrayList<SaleDetailsModel> queryInfo(int pageCount, int pageIndex, String selectInfo, String queryMsg,boolean isPage, String tableName, String order) throws SQLException
	{
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		SaleDetailsModel sdm = null;
		//System.out.println("info:"+selectInfo+" "+"queryMsg:"+queryMsg);
		String[] info = selectInfo.split("\\|");		
		String strSelect1 = "";
		String strSelect2 = "";

		if(!"".equals(queryMsg) && queryMsg != null)
		{
			String[] msg = queryMsg.split("\\|");

			strSelect1 = " where BuyTime>=to_date('" + msg[0] + "','yyyy-mm-dd hh24:mi:ss') and BuyTime<=to_date('" + msg[1] + "','yyyy-mm-dd hh24:mi:ss')";
			if(!"-1".equals(msg[2]))
			{
				strSelect1 += " and Status=" + msg[2];
			}
			if(!"-1".equals(msg[3]))
			{
				strSelect1 += " and kind='" + msg[3]+"'";
				if(msg[3].equals("6")){
					//System.out.println(msg.length+"  "+msg[4]);
					if(msg.length>=5&&!msg[4].equals("")){
						strSelect1 +=" and orderNo = '"+msg[4]+"'";
					}
				}
			}
		}

		if("all".equals(info[0]))
		{
			strSelect2 = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		}
		if("area".equals(info[0]))
		{
			strSelect2 = " where (Area_ID="+info[1]+" and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";	        
		}
		if("arch".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+")";
		}
		if("floor".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+") and (Floor="+info[2]+")";
		}
		if("meter".equals(info[0]))
		{
			strSelect2 = " where (AmMeter_ID="+info[1]+")";
		}

		String sql = "select "+
				"a.AmMeter_ID,b.Price_ID,p.price_name,SaleInfoNum,orderNo,studentID,sign,Times,Kind,QSYValue,HSYValue,"+
				"(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,a.AmMeter_Name,"+ //ammeter.Price_ID,
				"(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name,"+
				"to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,to_char(SendTime,'yyyy-mm-dd hh24:mi:ss')SendTime,Status,Price,TheGross,TheMoney "+
				"from "+
				"(select * from APSaleInfo " + strSelect1 + ")a,"+
				"(select Price_ID,Price_Name from Price " + ")p,"+
				"(select * from AmMeter " + strSelect2 + ")b "+
				"where a.Ammeter_ID=b.Ammeter_ID and p.price_id=b.price_id";
		String countSql = "select count(*) from ("+sql+")";
		sql +=" order by "+tableName+" "+order;
		String sqlpage = "SELECT * FROM(SELECT ROWNUM ro,n.* FROM(select "+
				"a.AmMeter_ID,b.Price_ID,p.price_name,SaleInfoNum,orderNo,studentID,sign,Times,Kind,QSYValue,HSYValue,"+
				"(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,a.AmMeter_Name,"+ //ammeter.Price_ID,
				"(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name,"+
				"to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,to_char(SendTime,'yyyy-mm-dd hh24:mi:ss')SendTime,Status,Price,TheGross,TheMoney "+
				"from "+
				"(select * from APSaleInfo " + strSelect1 + ")a,"+
				"(select Price_ID,Price_Name from Price " + ")p,"+
				"(select * from AmMeter " + strSelect2 + ")b "+
				"where a.Ammeter_ID=b.Ammeter_ID and p.price_id=b.price_id";
		sqlpage += " order by "+tableName+" "+order+") n WHERE ROWNUM <="+pageCount*(pageIndex+1)+") WHERE ro>"+pageCount*pageIndex;
		//System.out.println(sql);
		//System.out.println(sqlpage);
		int count =getCount(countSql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			if(isPage==true)
			{
				ps = conn.prepareStatement(sqlpage);
				rs = ps.executeQuery();
				setTotalCount(count);			
			}
			else
			{
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
			}
			while(rs.next())
			{
				sdm = new SaleDetailsModel();
				sdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
				sdm.setSALEINFONUM(rs.getString("SaleInfoNum"));
	
				if(rs.getString("sign") != null)
				{
					sdm.setSign(rs.getString("sign"));
				}
				else
				{
					sdm.setSign("");
				}
				
				if(rs.getString("orderNo") != null)
				{
					sdm.setORDERNO(rs.getString("orderNo"));
				}
				else
				{
					sdm.setORDERNO("");
				}
				//System.out.println(rs.getString("orderNo"));

				if(rs.getString("studentID") != null && !"".equals(rs.getString("studentID")))
				{
					sdm.setSTUDENTID(rs.getString("studentID"));
				}
				else
				{
					sdm.setSTUDENTID("");
				}

				sdm.setArchitecture_Name(rs.getString("Architecture_Name"));
				sdm.setFloorName(rs.getString("Floor"));
				sdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				//sdm.setPrice_ID(rs.getInt("Price_ID"));
				sdm.setPrice_Name(rs.getString("price_name"));

				String kindName = "系统售电";
				if ("0".equals(rs.getString("Kind")))
				{
					kindName = "系统售电";
				}
				else if ("1".equals(rs.getString("Kind")))
				{
					kindName = "一卡通售电";
				}
				else if ("2".equals(rs.getString("Kind")))
				{
					kindName = "统一月补";
				}
				else if ("3".equals(rs.getString("Kind")))
				{
					kindName = "临时调剂";
				}
				else if ("4".equals(rs.getString("Kind"))) {
					kindName = "ATM售电";
				}
				else if ("5".equals(rs.getString("Kind"))) {
					//System.out.println(kindName);
					kindName = "一卡通机器售电";
				}
				else if ("6".equals(rs.getString("Kind"))) {
					kindName = "微信支付";
				}
				sdm.setKindName(kindName);

				String status = "未下发";
				if ("1".equals(rs.getString("Status")))
				{
					status = "下发成功";
				}
				else if ("2".equals(rs.getString("Status")))
				{
					status = "下发异常";
				}
				else
				{
					status = "未下发";
				}
				sdm.setStatus(status);

				sdm.setBuyTime(rs.getString("BuyTime"));
				sdm.setPrice(rs.getString("Price"));
				sdm.setTheGross(rs.getString("TheGross"));
				sdm.setTheMoney(rs.getString("TheMoney"));

				if(!"".equals(rs.getString("SendTime")) && rs.getString("SendTime") != null && !"null".equals(rs.getString("SendTime")))
				{
					sdm.setSendTime(rs.getString("SendTime"));
				}else
				{
					sdm.setSendTime("");
				}

				if(!"".equals(rs.getString("QSYValue")) && rs.getString("QSYValue") != null && !"null".equals(rs.getString("QSYValue")))
				{
					sdm.setQSYValue(rs.getString("QSYValue"));
				}else
				{
					sdm.setQSYValue("");
				}

				if(!"".equals(rs.getString("HSYValue")) && rs.getString("HSYValue") != null && !"null".equals(rs.getString("HSYValue")))
				{
					sdm.setHSYValue(rs.getString("HSYValue"));
				}else
				{
					sdm.setHSYValue("");
				}

				if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
				{
					sdm.setUsers_Name(rs.getString("Users_Name"));
				}
				else
				{
					sdm.setUsers_Name("");
				}       

				list.add(sdm);
				
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}


		return list;

	}
	
	public String getAllWaterMeterJieSuan() throws SQLException, ParseException
	{
		String returnInfo = "";
		int errorNum = 0;
		int totalNum = 0;
		String sql = "select WaterMeter_ID,WaterMeter_Name,WATERMETER_485ADDRESS from WaterMeter where isSale=1";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		APWaterAddMoneyDao apDao = new APWaterAddMoneyDao();
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				totalNum++;
				boolean b = apDao.getWaterJieSuan(rs.getInt("WaterMeter_ID"), sdf.format(new Date()));
				if(!b){
					errorNum++;
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		returnInfo = "总共结算"+totalNum+"块表，失败"+errorNum+"个";
		
		return returnInfo;
	}
	
	public String waMeterReJieSuan(int waMeterId) throws ParseException{
		String info = "";
		String sql = "SELECT * FROM WATERJIESUAN WHERE WATERMETER_ID="+waMeterId+" AND ISPAY=1 and ThisUseMoney>0";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				info = "该表存在已经缴费的记录，不能重新结算，若要重新结算，请联系系统管理员！";
			}else{
				sql = "delete from WATERJIESUAN where WATERMETER_ID="+waMeterId;
				PreparedStatement ps1 = conn.prepareStatement(sql);
				ps1.executeUpdate();
				info = "成功删除未缴费的结算记录！";
				ps1.close();
				
				SaleDetailsDao sdd = new SaleDetailsDao();
				String resultInfo = sdd.getWaterMeterJieSuan(0, 0, 0, waMeterId);
				info += resultInfo;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return info;
	}
	
	public String getWaterMeterJieSuan(int areaId,int archId,int floor,int meterId) throws SQLException, ParseException
	{
		String returnInfo = "";
		int errorNum = 0;
		int totalNum = 0;
		String  condition = "";
		if(meterId!=0){
			condition +=" and WaterMeter_ID="+meterId;
		}else if(floor!=0){
			condition +=" and architecture_id="+archId+" and floor="+floor;
		}else if(archId!=0){
			condition +=" and architecture_id="+archId;
		}else if(areaId!=0){
			condition +=" and area_id="+areaId;
		}
		String sql = "select WaterMeter_ID,WaterMeter_Name,WATERMETER_485ADDRESS from WaterMeter where isSale=1 "+condition;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		APWaterAddMoneyDao apDao = new APWaterAddMoneyDao();
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				totalNum++;
				boolean b = apDao.getWaterJieSuan(rs.getInt("WaterMeter_ID"), sdf.format(new Date()));
				if(!b){
					errorNum++;
				}
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		returnInfo = "总共结算"+totalNum+"块表，失败"+errorNum+"个";
		
		return returnInfo;
	}
	
	public ArrayList<SaleDetailsModel> queryInfoWaterInput(String selectInfo) throws SQLException
	{
		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		SaleDetailsModel sdm = null;
		String[] info = selectInfo.split("\\|");		
		String strSelect2 = "";
		
		if("all".equals(info[0]))
		{
			strSelect2 = " where 1=1";
		}
		if("area".equals(info[0]))
		{
			strSelect2 = " where (Area_ID="+info[1]+")";	        
		}
		if("arch".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+")";
		}
		if("floor".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+") and (Floor="+info[2]+")";
		}
		if("meter".equals(info[0]))
		{
			strSelect2 = " where (WaterMeter_ID="+info[1]+")";
		}
		String sql = "select WaterMeter_ID,WaterMeter_Name,WATERMETER_485ADDRESS from WaterMeter "+strSelect2 + " and isSale=1 order by WaterMeter_ID";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				sdm = new SaleDetailsModel();
				sdm.setAmMeter_ID(rs.getInt("WaterMeter_ID"));
				sdm.setAmMeter_485(rs.getString("WATERMETER_485ADDRESS"));
				sdm.setAmMeter_Name(rs.getString("WaterMeter_Name"));
				//获取起始示数
				String starValue = "";
				sql = "select * from (select ThisZValue from WATERJIESUAN a where WATERMETER_ID="
						+ rs.getInt("WaterMeter_ID") + " order by ThisTime desc) where rownum=1";
				Connection conn1 = null;
				PreparedStatement pst1=null;
				ResultSet rs1 =null;
				try
				{
					conn1 = ConnDB.getConnection();
					pst1 = conn1.prepareStatement(sql);
					rs1 = pst1.executeQuery();
					if (rs1.next())
					{
						starValue = rs1.getString("ThisZValue");
					} else
					{
						//第一次没数据，起码从表里面取
						sql = "select ZValueZY from (select ZValueZY from ZWATERDATAS"+rs.getString("WaterMeter_ID") + " order by valuetime asc) where rownum=1";
						Connection conn0 = null;
						PreparedStatement ps0 =null;
						ResultSet rs0 =null;
						try{
							conn0 = ConnDB.getConnection();
							ps0 = conn0.prepareStatement(sql);
							rs0 = ps0.executeQuery();
							if(rs0.next()){
								starValue = rs0.getString("ZValueZY");
							}
						}catch (SQLException e)
						{
							e.printStackTrace();
						} finally
						{
							ConnDB.release(conn0, ps0,rs0);
						}

					}
				} catch (NumberFormatException e)
				{
					e.printStackTrace();
				}finally{
					ConnDB.release(conn1, pst1, rs1);
				}
				sdm.setTheGross(starValue);
				list.add(sdm);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return list;
		
	}
	
	public String getJieSuanWaValue(String saleNum){
		String sql = "select sum(lastzvalue)lastzvalue,sum(thiszvalue)thiszvalue from waterjiesuan where saleinfonum='"+saleNum+"'";
		String starValue = "";
		String endValue = "";
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				starValue = rs.getString("lastzvalue");
				endValue = rs.getString("thiszvalue");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return starValue + "|" + endValue;
	}

	public ArrayList<SaleDetailsModel> queryInfoWater(int pageCount, int pageIndex, String selectInfo, String queryMsg,boolean isPage, String tableName, String order) throws SQLException
	{

		ArrayList<SaleDetailsModel> list = new ArrayList<SaleDetailsModel>();
		SaleDetailsModel sdm = null;
		//System.out.println("info:"+selectInfo+" "+"queryMsg:"+queryMsg);
		String[] info = selectInfo.split("\\|");		
		String strSelect1 = "";
		String strSelect2 = "";

		if(!"".equals(queryMsg) && queryMsg != null)
		{
			String[] msg = queryMsg.split("\\|");

			strSelect1 = " where BuyTime>=to_date('" + msg[0] + "','yyyy-mm-dd hh24:mi:ss') and BuyTime<=to_date('" + msg[1] + "','yyyy-mm-dd hh24:mi:ss')";
			if(!"-1".equals(msg[2]))
			{
				strSelect1 += " and kind='" + msg[2]+"'";
				if(msg[2].equals("6")){
					//System.out.println(msg.length+"  "+msg[4]);
					if(msg.length>=4 && !msg[3].equals("")){
						strSelect1 +=" and orderNo = '"+msg[3]+"'";
					}
				}
			}
		}

		if("all".equals(info[0]))
		{
			//strSelect2 = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		}
		if("area".equals(info[0]))
		{
			strSelect2 = " where (Area_ID="+info[1]+")";	        
		}
		if("arch".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+")";
		}
		if("floor".equals(info[0]))
		{
			strSelect2 = " where (Architecture_ID="+info[1]+") and (Floor="+info[2]+")";
		}
		if("meter".equals(info[0]))
		{
			strSelect2 = " where (WaterMeter_ID="+info[1]+")";
		}

		String sql = "select "+
				"a.WaterMeter_ID,b.Price_ID,p.price_name,SaleInfoNum,orderNo,studentID,sign,Times,Kind,QSYValue,HSYValue,"+
				"(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,a.WaterMeter_Name,"+ //ammeter.Price_ID,
				"(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name,"+
				"to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,to_char(SendTime,'yyyy-mm-dd hh24:mi:ss')SendTime,Status,Price,TheGross,TheMoney "+
				"from "+
				"(select * from APSaleInfoWater " + strSelect1 + ")a,"+
				"(select Price_ID,Price_Name from Price " + ")p,"+
				"(select * from WaterMeter " + strSelect2 + ")b "+
				"where a.Watermeter_ID=b.Watermeter_ID and p.price_id=b.price_id";
		String countSql = "select count(*) from ("+sql+")";
		sql +=" order by "+tableName+" "+order;
		String sqlpage = "SELECT * FROM(SELECT ROWNUM ro,n.* FROM(select "+
				"a.WaterMeter_ID,b.Price_ID,p.price_name,SaleInfoNum,orderNo,studentID,sign,Times,Kind,QSYValue,HSYValue,"+
				"(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
				"Floor,a.WaterMeter_Name,"+ //ammeter.Price_ID,
				"(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name,"+
				"to_char(BuyTime,'yyyy-mm-dd hh24:mi:ss')BuyTime,to_char(SendTime,'yyyy-mm-dd hh24:mi:ss')SendTime,Status,Price,TheGross,TheMoney "+
				"from "+
				"(select * from APSaleInfoWater " + strSelect1 + ")a,"+
				"(select Price_ID,Price_Name from Price " + ")p,"+
				"(select * from WaterMeter " + strSelect2 + ")b "+
				"where a.Watermeter_ID=b.Watermeter_ID and p.price_id=b.price_id";
		sqlpage += " order by "+tableName+" "+order+") n WHERE ROWNUM <="+pageCount*(pageIndex+1)+") WHERE ro>"+pageCount*pageIndex;
		//System.out.println(sql);
		//System.out.println(sqlpage);
		int count =getCount(countSql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			if(isPage==true)
			{
				ps = conn.prepareStatement(sqlpage);
				rs = ps.executeQuery();
				setTotalCount(count);			
			}
			else
			{
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
			}
			while(rs.next())
			{
				sdm = new SaleDetailsModel();
				sdm.setAmMeter_ID(rs.getInt("WaterMeter_ID"));
				sdm.setSALEINFONUM(rs.getString("SaleInfoNum"));
				
				String[] strValues = getJieSuanWaValue(rs.getString("SaleInfoNum")).split("\\|");
				sdm.setQSYValue(strValues[0]);
				sdm.setHSYValue(strValues[1]);
				
				if(rs.getString("sign") != null)
				{
					sdm.setSign(rs.getString("sign"));
				}
				else
				{
					sdm.setSign("");
				}
				
				if(rs.getString("orderNo") != null)
				{
					sdm.setORDERNO(rs.getString("orderNo"));
				}
				else
				{
					sdm.setORDERNO("");
				}
				//System.out.println(rs.getString("orderNo"));

				if(rs.getString("studentID") != null && !"".equals(rs.getString("studentID")))
				{
					sdm.setSTUDENTID(rs.getString("studentID"));
				}
				else
				{
					sdm.setSTUDENTID("");
				}

				sdm.setArchitecture_Name(rs.getString("Architecture_Name"));
				sdm.setFloorName(rs.getString("Floor"));
				sdm.setAmMeter_Name(rs.getString("WaterMeter_Name"));
				//sdm.setPrice_ID(rs.getInt("Price_ID"));
				sdm.setPrice_Name(rs.getString("price_name"));

				String kindName = "系统售水";
				if ("0".equals(rs.getString("Kind")))
				{
					kindName = "系统售水";
				}
				else if ("1".equals(rs.getString("Kind")))
				{
					kindName = "一卡通售水";
				}
				else if ("2".equals(rs.getString("Kind")))
				{
					kindName = "统一月补";
				}
				else if ("3".equals(rs.getString("Kind")))
				{
					kindName = "临时调剂";
				}
				else if ("4".equals(rs.getString("Kind"))) {
					kindName = "ATM售水";
				}
				else if ("5".equals(rs.getString("Kind"))) {
					//System.out.println(kindName);
					kindName = "一卡通机器售水";
				}
				else if ("6".equals(rs.getString("Kind"))) {
					kindName = "微信支付";
				}
				sdm.setKindName(kindName);

				String status = "未下发";
				if ("1".equals(rs.getString("Status")))
				{
					status = "下发成功";
				}
				else if ("2".equals(rs.getString("Status")))
				{
					status = "下发异常";
				}
				else
				{
					status = "未下发";
				}
				sdm.setStatus(status);

				sdm.setBuyTime(rs.getString("BuyTime"));
				sdm.setPrice(rs.getString("Price"));
				sdm.setTheGross(rs.getString("TheGross"));
				sdm.setTheMoney(rs.getString("TheMoney"));

				if(!"".equals(rs.getString("SendTime")) && rs.getString("SendTime") != null && !"null".equals(rs.getString("SendTime")))
				{
					sdm.setSendTime(rs.getString("SendTime"));
				}else
				{
					sdm.setSendTime("");
				}

				/*if(!"".equals(rs.getString("QSYValue")) && rs.getString("QSYValue") != null && !"null".equals(rs.getString("QSYValue")))
				{
					sdm.setQSYValue(rs.getString("QSYValue"));
				}else
				{
					sdm.setQSYValue("");
				}*/

				/*if(!"".equals(rs.getString("HSYValue")) && rs.getString("HSYValue") != null && !"null".equals(rs.getString("HSYValue")))
				{
					sdm.setHSYValue(rs.getString("HSYValue"));
				}else
				{
					sdm.setHSYValue("");
				}*/

				if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
				{
					sdm.setUsers_Name(rs.getString("Users_Name"));
				}
				else
				{
					sdm.setUsers_Name("");
				}       

				list.add(sdm);
				
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}


		return list;

	
	}
	
	
	/**
	 * 删
	 * @param SaleInfoNum
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(String SaleInfoNum) throws SQLException
	{
		String sql = "delete FROM APSaleInfo where SaleInfoNum = '" + SaleInfoNum + "'";
		//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql);
		//		boolean b = (ps.executeUpdate() == 1);
		//		if (ps != null)
		//			ps.close();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b = false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			b = (ps.executeUpdate() == 1);

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return b;
	}

	public int getCount(String sql){
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return count;
	}
	/**
	 * 根据订单号查询订单详细信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public SaleDetailsModel queryDetailByOrderNo(String orderNo){
		SaleDetailsModel model = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from APSALEINFO where orderno =?";

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, orderNo);
			rs = ps.executeQuery();
			if(rs.next()){
				if (model==null)
				{
					model = new SaleDetailsModel();
				}
				model.setSALEINFONUM(rs.getString("SALEINFONUM"));
				model.setAmMeter_ID(rs.getInt("AMMETER_ID"));
				model.setAmMeter_Name(rs.getString("AMMETER_NAME"));
				model.setORDERNO(rs.getString("orderno"));
				model.setBuyTime(rs.getString("BUYTIME"));
				model.setStatus(rs.getString("STATUS"));
				model.setTheMoney(rs.getString("THEMONEY"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return model;
	}

	/**
	 * 根据日期查询当天最后一条记录
	 * @param queryDate
	 * @return
	 */
	public SaleDetailsModel queryDayLastInfo(String queryDate){
		SaleDetailsModel model = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from (select * from APSALEINFO "
				+ "	WHERE BUYTIME BETWEEN "
				+ "TO_DATE ('"+queryDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS')"
				+ "AND TO_DATE ('"+queryDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') "
				+ "ORDER BY saleinfonum desc) where ROWNUM =1";		
		try
		{
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			//ps.setString(1, queryDate);
			rs = ps.executeQuery();
			if(rs.next()){
				if (model==null)
				{
					model = new SaleDetailsModel();
				}
				model.setSALEINFONUM(rs.getString("SALEINFONUM"));
				model.setAmMeter_ID(rs.getInt("AMMETER_ID"));
				model.setAmMeter_Name(rs.getString("AMMETER_NAME"));
				//model.set(rs.getString("AMMETER_485ADDRESS"));
				model.setBuyTime(dfAll.format(dfAll.parse(rs.getString("BUYTIME"))));
				model.setStatus(rs.getString("STATUS"));
				model.setTheMoney(rs.getString("THEMONEY"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return model;
	}

	/**
	 * 将出现故障的微信订单重新插入数据库，下发
	 * @return
	 */
	public boolean insertWeixinOrder(SaleDetailsModel model,String studendName){

		if(model==null){//若model为空直接返回插入失败
			return false;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		boolean b =false;
		String sql = "INSERT INTO APSALEINFO (SALEINFONUM,AMMETER_ID,AMMETER_NAME,AMMETER_485ADDRESS,"
				+ "KIND,USERS_ID,BUYTIME,STATUS,PRICE,THEGROSS,THEMONEY,"
				+ "STUDENTID,ORDERNO,USERS_NAME,SIGN)VALUES("
				+"'"+model.getSALEINFONUM()+"','"+model.getAmMeter_ID()+"','"+model.getAmMeter_Name()+"',"
				+ "'"+model.getAmMeter_485()+"','6','"+model.getUsers_Id()+"',"
				+ "TO_DATE ('"+model.getBuyTime()+"','SYYYY-MM-DD HH24:MI:SS'),'0',"
				+ "'"+model.getPrice()+"','"+model.getTheGross()+"','"+model.getTheMoney()+"','"+studendName+"',"
				+ "'"+model.getORDERNO()+"','weixin','"+dfAll.format(new Date())+"')";
		System.out.println("insertWeixinOrder:"+sql);
		try
		{
			conn = ConnDB.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			b=(ps.executeUpdate()==1);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				conn.setAutoCommit(true);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			ConnDB.release(conn, ps);
		}
		return b;
	}

	/**
	 * 获取插入微信订单的model
	 * 这个方法当且仅当微信支付订单号确实有效才会被调用
	 * 
	 * arrstr 为微信支付订单查询返回结果字符串  jasonarray 字符串类型
	 *final2 str:[{"type":"object"},{"交易状态":"SUCCESS","返回信息":"OK","是否关注公众账号":"Y",
	 *"货币种类":"CNY","商户订单号":"AMFEE_oA6X_t1VvLwibeg51468679145",
	 *"微信支付订单号":"4000102001201607169131393395","交易类型":"JSAPI",
	 *"业务结果":"SUCCESS","订单金额":"100.00","支付完成时间":"2016-07-16 22:25:55",
	 *"返回状态码":"SUCCESS","付款银行":"BOC_DEBIT","现金支付金额":"100.00"}]
	 *	dfAll yyyy-MM-dd HH:mm:ss;
		dfh HH:mm:ss
		dfd = yyyyMMdd
		dfd1 = yyyy-MM-dd;
		doubledf = new DecimalFormat("0.00");
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public SaleDetailsModel getInsertModel(int ammeterId,String arrstr) throws ParseException, SQLException
	{
		AmmeterDao adao = new AmmeterDao();
		UsersDao udao = new UsersDao();
		SaleDetailsModel model= new SaleDetailsModel();
		JSONArray array = JSONArray.fromObject(arrstr);
		JSONObject obj = array.getJSONObject(1);
		//System.out.println(obj.getString("支付完成时间"));
		String date = dfd1.format(dfAll.parse(obj.getString("支付完成时间")));
		SaleDetailsModel lastModel = queryDayLastInfo(date);
		if(lastModel!=null){
			long saleNo = Long.parseLong(lastModel.getSALEINFONUM());
			model.setSALEINFONUM(String.valueOf(saleNo+1));
			Date d = dfAll.parse(lastModel.getBuyTime());
			if(dfh.format(d).equals("23:59:59")){
				model.setBuyTime(lastModel.getBuyTime());
			}else{
				d = new Date(d.getTime()+1000);
				model.setBuyTime(dfAll.format(d));
			}
		}else{
			String saleNo = dfd.format(dfd1.parse(date))+"0001";
			model.setSALEINFONUM(saleNo);
			model.setBuyTime(dfAll.format(dfAll.parse(date+" 00:01:00")));
		}
		model.setSTUDENTID(obj.getString("商户订单号"));
		model.setORDERNO(obj.getString("微信支付订单号"));
		model.setTheMoney(obj.getString("现金支付金额"));
		model.setAmMeter_ID(ammeterId);
		String am_addr = adao.queryAddrById(ammeterId);
		if(am_addr!=null&&!"".equals(am_addr)){
			model.setAmMeter_485(adao.queryAddrById(ammeterId));
		}else{//若485地址不存在 则说明电表信息错误 直接返回null
			return null;
		}
		model.setAmMeter_Name(adao.queryNameByID(ammeterId));
		model.setPrice(String.valueOf(Double.parseDouble(adao.queryPriceValueById(ammeterId))));
		model.setTheGross(
				doubledf.format(Double.parseDouble(model.getTheMoney())/Double.parseDouble(model.getPrice())));
		model.setStatus("0");
		model.setUsers_Name("weixin");
		model.setUsers_Id(udao.queryIdByLoginName(model.getUsers_Name()));
		return model;
	}
	public static void main(String[] args)
	{		
		long l = Long.parseLong("201607160197");
		System.out.println();
		System.out.println(l+1);
	}

}
