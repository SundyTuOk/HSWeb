package com.sf.energy.prepayment.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.NW7Provider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.model.YuGouDetailsModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.dao.CurrentMeasureDao;

public class APWaterAddMoneyDao
{

	/**
	 * 初始化售水信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */

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
	 * 
	 * @param pageCount
	 * @param pageIndex
	 * @param selectInfo
	 * @param meterType
	 * @param isPage
	 * @param tableName
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<YuGouDetailsModel> queryWaterAndAmmeterSaleInfo(int pageCount, int pageIndex, String selectInfo, String meterType, boolean isPage,
			String tableName, String order) throws SQLException
	{
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();
		
		if ("0".equals(meterType))
		{
			YuGouDetailsModel ygdm = null;
			String[] info = selectInfo.split("\\|");
			String strSelect2 = "";

			if ("all".equals(info[0]))
			{
				strSelect2 = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
			}
			if ("area".equals(info[0]))
			{
				strSelect2 = " where (Area_ID=" + info[1]
						+ " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";
			}
			if ("arch".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ")";
			}
			if ("floor".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ") and (Floor=" + info[2] + ")";
			}
			if ("meter".equals(info[0]))
			{
				strSelect2 = " where (AmMeter_ID=" + info[1] + ")";
			}

			String sql = "select ('电表欠费')owe,"
					+ "a.AmMeter_ID,to_char(a.JieSuanTime,'yyyy-mm-dd hh24:mi:ss')JieSuanTime,a.ThisRemainMoney,"
					+ "(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
					+ "b.Floor,b.AmMeter_Name "
					//+ "(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name "
					+ "from "
					+ "(select AmMeter_ID,JieSuanTime,ThisRemainMoney from (select AmMeter_ID,JieSuanTime,ThisRemainMoney,row_number() OVER (PARTITION BY AmMeter_ID ORDER BY ThisTime desc) rn from AmMeterJieSuan where ThisRemainMoney<0) where rn=1)a,"
					+ "(select * from AmMeter " + strSelect2 + ")b " + "where a.Ammeter_ID=b.Ammeter_ID";
			sql += " union all ";
			sql += "select  ('水表欠费')owe,"
					+ "a.AmMeter_ID,to_char(a.JieSuanTime,'yyyy-mm-dd hh24:mi:ss')JieSuanTime,a.ThisRemainMoney,"
					+ "(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
					+ "b.Floor,b.WaterMeter_Name as AmMeter_Name "
					//+ "(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name "
					+ "from "
					+ "(select WaterMeter_ID as AmMeter_ID,JieSuanTime,ThisRemainMoney from (select WaterMeter_ID,JieSuanTime,ThisRemainMoney,row_number() OVER (PARTITION BY WaterMeter_ID ORDER BY ThisTime desc) rn from WaterJieSuan where ThisRemainMoney<0) where rn=1)a,"
					+ "(select * from WaterMeter " + strSelect2 + ")b " + "where a.Ammeter_ID=b.WaterMeter_ID";
			sql += " order by " + tableName + " " + order;
			
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
				if(isPage==true)
				{
					setTotalCount(count);
					if (count <= (pageCount * pageIndex))
						return list;

					count = count - pageCount * pageIndex;

					if (count >= pageCount)
						count = pageCount;

					if ((pageCount * pageIndex) == 0)
						rs.beforeFirst();
					else
						rs.absolute(pageCount * pageIndex);
				}
				else
				{
					rs.beforeFirst();
				}
				
				
				while(rs.next()&&(count > 0))
				{
					ygdm = new YuGouDetailsModel();
					ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
					ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
					ygdm.setFloorName(rs.getString("Floor"));
					ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
					ygdm.setReadTime(rs.getString("JieSuanTime"));            
					ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
					ygdm.setOwe(rs.getString("owe"));
		            
		            list.add(ygdm);
					count--;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

//			rs.last();
//			int count = rs.getRow();
//			if(isPage==true)
//			{
//				setTotalCount(count);
//				if (count <= (pageCount * pageIndex))
//					return list;
//
//				count = count - pageCount * pageIndex;
//
//				if (count >= pageCount)
//					count = pageCount;
//
//				if ((pageCount * pageIndex) == 0)
//					rs.beforeFirst();
//				else
//					rs.absolute(pageCount * pageIndex);
//			}
//			else
//			{
//				rs.beforeFirst();
//			}
//			
//			
//			while(rs.next()&&(count > 0))
//			{
//				ygdm = new YuGouDetailsModel();
//				ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
//				ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
//				ygdm.setFloorName(rs.getString("Floor"));
//				ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
//				ygdm.setReadTime(rs.getString("JieSuanTime"));            
//				ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
//				ygdm.setOwe(rs.getString("owe"));
//	            
//	            list.add(ygdm);
//				count--;
//			}

		}
		
		return list;
	}

	/**
	 * 查询后付费欠费的水表
	 * 
	 * @param pageCount
	 * @param pageIndex
	 * @param selectInfo
	 * @param meterType
	 * @param isPage
	 * @param tableName
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<YuGouDetailsModel> queryWaterSaleInfo(int pageCount, int pageIndex, String selectInfo, String meterType, boolean isPage,
			String tableName, String order) throws SQLException
	{
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();

		if ("2".equals(meterType))
		{
			YuGouDetailsModel ygdm = null;
			String[] info = selectInfo.split("\\|");
			String strSelect2 = "";

			if ("all".equals(info[0]))
			{
				strSelect2 = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
			}
			if ("area".equals(info[0]))
			{
				strSelect2 = " where (Area_ID=" + info[1]
						+ " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";
			}
			if ("arch".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ")";
			}
			if ("floor".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ") and (Floor=" + info[2] + ")";
			}
			if ("meter".equals(info[0]))
			{
				strSelect2 = " where (WaterMeter_ID in (" + info[1] + "))";
			}

			String sql = "select "
					+ "a.AmMeter_ID,to_char(a.JieSuanTime,'yyyy-mm-dd hh24:mi:ss')JieSuanTime,a.ThisRemainMoney,"
					+ "(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
					+ "b.Floor,b.WaterMeter_Name as AmMeter_Name "
					//+ "(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name "
					+ "from "
					+ "(select WaterMeter_ID as AmMeter_ID,JieSuanTime,ThisRemainMoney from (select WaterMeter_ID,JieSuanTime,ThisRemainMoney,row_number() OVER (PARTITION BY WaterMeter_ID ORDER BY ThisTime desc) rn from WaterJieSuan where ThisRemainMoney<0) where rn=1)a,"
					+ "(select * from WaterMeter " + strSelect2 + ")b " + "where a.Ammeter_ID=b.WaterMeter_ID";
			sql += " order by " + tableName + " " + order;
			
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
				if(isPage==true)
				{
					setTotalCount(count);
					if (count <= (pageCount * pageIndex))
						return list;

					count = count - pageCount * pageIndex;

					if (count >= pageCount)
						count = pageCount;

					if ((pageCount * pageIndex) == 0)
						rs.beforeFirst();
					else
						rs.absolute(pageCount * pageIndex);
				}
				else
				{
					rs.beforeFirst();
				}
				
				
				while(rs.next()&&(count > 0))
				{
					ygdm = new YuGouDetailsModel();
					ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
					ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
					ygdm.setFloorName(rs.getString("Floor"));
					ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
					ygdm.setReadTime(rs.getString("JieSuanTime"));            
					ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
					
		           /* if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
					{
		            	ygdm.setUsers_Name(rs.getString("Users_Name"));
					}
					else
					{
						ygdm.setUsers_Name("");
					}*/       
		            
		            list.add(ygdm);
					count--;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

//			rs.last();
//			int count = rs.getRow();
//			if(isPage==true)
//			{
//				setTotalCount(count);
//				if (count <= (pageCount * pageIndex))
//					return list;
//
//				count = count - pageCount * pageIndex;
//
//				if (count >= pageCount)
//					count = pageCount;
//
//				if ((pageCount * pageIndex) == 0)
//					rs.beforeFirst();
//				else
//					rs.absolute(pageCount * pageIndex);
//			}
//			else
//			{
//				rs.beforeFirst();
//			}
//			
//			
//			while(rs.next()&&(count > 0))
//			{
//				ygdm = new YuGouDetailsModel();
//				ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
//				ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
//				ygdm.setFloorName(rs.getString("Floor"));
//				ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
//				ygdm.setReadTime(rs.getString("JieSuanTime"));            
//				ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
//				
//	           /* if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
//				{
//	            	ygdm.setUsers_Name(rs.getString("Users_Name"));
//				}
//				else
//				{
//					ygdm.setUsers_Name("");
//				}*/       
//	            
//	            list.add(ygdm);
//				count--;
//			}
			

		}

		return list;
		
	}
	
	
	
	/**
	 * 查询后付费欠费的电表
	 * 
	 * @param pageCount
	 * @param pageIndex
	 * @param selectInfo
	 * @param meterType
	 * @param isPage
	 * @param tableName
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<YuGouDetailsModel> queryAmmeterGDInfo(int pageCount, int pageIndex, String selectInfo, String meterType, boolean isPage,
			String tableName, String order) throws SQLException
	{
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();

		if ("1".equals(meterType))
		{
			YuGouDetailsModel ygdm = null;
			String[] info = selectInfo.split("\\|");
			String strSelect2 = "";

			if ("all".equals(info[0]))
			{
				strSelect2 = " where Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
			}
			if ("area".equals(info[0]))
			{
				strSelect2 = " where (Area_ID=" + info[1]
						+ " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";
			}
			if ("arch".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ")";
			}
			if ("floor".equals(info[0]))
			{
				strSelect2 = " where (Architecture_ID=" + info[1] + ") and (Floor=" + info[2] + ")";
			}
			if ("meter".equals(info[0]))
			{
				strSelect2 = " where (AmMeter_ID in (" + info[1] + "))";
			}

			String sql = "select "
					+ "a.AmMeter_ID,to_char(a.JieSuanTime,'yyyy-mm-dd hh24:mi:ss')JieSuanTime,a.ThisRemainMoney,"
					+ "(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"
					+ "b.Floor,b.AmMeter_Name "
					//+ "(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name "
					+ "from "
					+ "(select AmMeter_ID,JieSuanTime,ThisRemainMoney from (select AmMeter_ID,JieSuanTime,ThisRemainMoney,row_number() OVER (PARTITION BY AmMeter_ID ORDER BY ThisTime desc) rn from AmMeterJieSuan where ThisRemainMoney<0) where rn=1)a,"
					+ "(select * from AmMeter " + strSelect2 + ")b " + "where a.Ammeter_ID=b.Ammeter_ID";
			sql += " order by " + tableName + " " + order;
			
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
				if(isPage==true)
				{
					setTotalCount(count);
					if (count <= (pageCount * pageIndex))
						return list;

					count = count - pageCount * pageIndex;

					if (count >= pageCount)
						count = pageCount;

					if ((pageCount * pageIndex) == 0)
						rs.beforeFirst();
					else
						rs.absolute(pageCount * pageIndex);
				}
				else
				{
					rs.beforeFirst();
				}
				
				
				while(rs.next()&&(count > 0))
				{
					ygdm = new YuGouDetailsModel();
					ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
					ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
					ygdm.setFloorName(rs.getString("Floor"));
					ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
					ygdm.setReadTime(rs.getString("JieSuanTime"));            
					ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
					
		            /*if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
					{
		            	ygdm.setUsers_Name(rs.getString("Users_Name"));
					}
					else
					{
						ygdm.setUsers_Name("");
					} */      
		            
		            list.add(ygdm);
					count--;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

//			rs.last();
//			int count = rs.getRow();
//			if(isPage==true)
//			{
//				setTotalCount(count);
//				if (count <= (pageCount * pageIndex))
//					return list;
//
//				count = count - pageCount * pageIndex;
//
//				if (count >= pageCount)
//					count = pageCount;
//
//				if ((pageCount * pageIndex) == 0)
//					rs.beforeFirst();
//				else
//					rs.absolute(pageCount * pageIndex);
//			}
//			else
//			{
//				rs.beforeFirst();
//			}
//			
//			
//			while(rs.next()&&(count > 0))
//			{
//				ygdm = new YuGouDetailsModel();
//				ygdm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
//				ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
//				ygdm.setFloorName(rs.getString("Floor"));
//				ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
//				ygdm.setReadTime(rs.getString("JieSuanTime"));            
//				ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
//				
//	            /*if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
//				{
//	            	ygdm.setUsers_Name(rs.getString("Users_Name"));
//				}
//				else
//				{
//					ygdm.setUsers_Name("");
//				} */      
//	            
//	            list.add(ygdm);
//				count--;
//			}
			

		}

		return list;
	}
	
	public String queryInfoByWaterID(String id){
		String str = "";
		String sql = "SELECT WATERMETER_ID,WATERMETER_NAME,WATERMETER_485ADDRESS,(SELECT PRICE_VALUE from PRICE where PRICE_ID=a.PRICE_ID)PRICE from WATERMETER a WHERE WATERMETER_ID="
				+ id;
		
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
				//str = rs.getString("WATERMETER_NAME")+",表地址:"+rs.getString("WATERMETER_485ADDRESS");
				str = rs.getString("WATERMETER_NAME") + "|" + rs.getString("WATERMETER_485ADDRESS")
						+ "|" + rs.getString("PRICE");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return str;
	}

	public JSONArray queryInfo(String id) throws SQLException
	{
		String sql = "SELECT WATERMETER_ID,CONSUMERNUM,CONSUMERNAME,WATERMETER_NAME,WATERMETER_485ADDRESS,PRICE_NAME,PRICE_VALUE from (WATERMETER)w left join (PRICE)p ON w.price_id=p.price_id WHERE WATERMETER_ID="
				+ id;
		JSONArray js = new JSONArray();
		JSONObject jo = null;
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
				jo = new JSONObject();
				jo.put("WATERMETER_ID", rs.getString("WATERMETER_ID"));
				jo.put("CONSUMERNUM", rs.getString("CONSUMERNUM"));
				jo.put("CONSUMERNAME", rs.getString("CONSUMERNAME"));
				jo.put("WATERMETER_NAME", rs.getString("WATERMETER_NAME"));
				jo.put("WATERMETER_485ADDRESS", rs.getString("WATERMETER_485ADDRESS"));
				jo.put("PRICE_NAME", rs.getString("PRICE_NAME"));
				jo.put("PRICE_VALUE", rs.getString("PRICE_VALUE"));
				js.add(jo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return js;
	}

	/**
	 * 
	 * @param waterId
	 * @param saleWaterMoney
	 * @param saleWaterGross
	 * @param userID
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public JSONArray saveSaleMoney(String waterId, String waMeter_Name, String waMeter_485Address, String saleWaterMoney, String saleWaterGross, String userID,String userName, String price) throws SQLException, IOException,
			NumberFormatException, ParseException
	{
		String saleNum = GetNextChareNum();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String bubTime = df.format(new Date());

		String sql = "insert INTO APSALEINFOWATER (SALEINFONUM,WATERMETER_ID,WATERMETER_NAME,WATERMETER_485ADDRESS,BUYTIME,Kind,TheMoney,TheGross,Price,USERS_ID,USERS_NAME)" 
				+ " VALUES(" + saleNum + ","+ waterId + ",'" + waMeter_Name + "','" 
				+ waMeter_485Address + "',to_date('" + bubTime + "','yyyy-mm-dd hh24:mi:ss'),0," + saleWaterMoney + ","
				 + saleWaterGross + "," + price + "," + userID + ",'" + userName + "')";
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
//		int rs = ps.executeUpdate();
//
//		if (ps != null)
//			ps.close();

		JSONArray js = new JSONArray();
		JSONObject jo = new JSONObject();
		ReadWatter read = new ReadWatter(waterId, userID);
		int flag = 0;//用于标识是否执行成功
		// read.setError(false);
		// read.setZY0("20.45");
		// read.setValueTime(df.format(new Date()));
		String valueTime = "";
		if (!read.isError())
		{
			String zy0 = read.getZY0();
			valueTime = read.getValueTime();

			CurrentMeasureDao cd = new CurrentMeasureDao();
			cd.insertAfterCommDan(Double.parseDouble(zy0), valueTime, Integer.parseInt(waterId));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			boolean isJiesuan = getWaterJieSuan(Integer.parseInt(waterId), date);
			if (isJiesuan)
			{
				// 更新信息
				boolean updateSale = updateYuGou(saleNum, waterId, valueTime);

				if (updateSale)
				{
					flag = 1;
					jo.put("isError", "0");
					js.add(jo);
				} else
				{
					flag = 3;
					jo.put("isError", "1");
					jo.put("errMsg", "警告：售水单已经生成！<br>结算成功。<br>更新售水单失败！！");
					js.add(jo);
				}
			} else
			{
				flag = 2;
				jo.put("isError", "1");
				jo.put("errMsg", "警告：售水单已经生成！<br>结算失败。<br>更新售水单失败！！");
				js.add(jo);
			}
		} else
		{
			flag = 2;
			jo.put("isError", "1");
			jo.put("errMsg", "警告：售水单已经生成！<br>" + read.getErrorMessage());
			js.add(jo);
		}
		
		if(flag==0 || flag==2){//结算  更新
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			boolean isJiesuan = getWaterJieSuan(Integer.parseInt(waterId), date);
			if (isJiesuan)
			{
				// 更新信息
				boolean updateSale = updateYuGou(saleNum, waterId, valueTime);
			}
		}else if(flag==3){
			updateYuGou(saleNum, waterId, valueTime);
		}
		
		return js;
	}

	private boolean updateYuGou(String saleNum, String waterId, String valueTime) throws SQLException
	{

		float LastRemainMoney = 0;
		float ThisRemainMoney = 0;

		// 查出上次剩余，求出本次剩余
		// 最后一次结算的记录
		String strsql = "select * from (select a.* from  WATERJIESUAN a where waterMeter_ID=" + waterId
				+ " order by JieSuanTime desc) where rownum=1";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strsql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				// 充值采取先生成单子，后结算，在更新，结算的时候已经把单子的金额结算掉了，这里只需要取结算后的数据即可
				// LastRemainMoney = rs.getFloat("ThisRemainMoney");
				// ThisRemainMoney = Float.parseFloat(money) + LastRemainMoney -
				// rs.getFloat("Used_Money");
				LastRemainMoney = rs.getFloat("LastRemainMoney");
				ThisRemainMoney = rs.getFloat("ThisRemainMoney");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			// 充值采取先生成单子，后结算，在更新，结算的时候已经把单子的金额结算掉了，这里只需要取结算后的数据即可
//			// LastRemainMoney = rs.getFloat("ThisRemainMoney");
//			// ThisRemainMoney = Float.parseFloat(money) + LastRemainMoney -
//			// rs.getFloat("Used_Money");
//			LastRemainMoney = rs.getFloat("LastRemainMoney");
//			ThisRemainMoney = rs.getFloat("ThisRemainMoney");
//		}
		
		String valueTimeString = "";
		if(!"".equals(valueTime) && valueTime != null){
			valueTimeString = valueTime;
		}else {
			Connection conn3 = null;
			PreparedStatement pst3 = null;
			ResultSet rs3 = null;
			try
			{
				String sql3 = "SELECT valuetime from (select * from ZWATERDATAS"+waterId+"  ORDER BY VALUETIME DESC) WHERE ROWNUM=1";
				conn3 = ConnDB.getConnection();
				pst3 = conn3.prepareStatement(sql3);
				rs3 = pst3.executeQuery();
				if(rs3.next()){
					valueTimeString = rs3.getString("valuetime");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn3, pst3, rs3);
			}
		}


		/*strsql = "update WATERSALEINFO set BUYTIME=to_date('" + valueTime + "','yyyy-mm-dd hh24:mi:ss'),LastRemainMoney=" + LastRemainMoney
				+ ",THISTOTALMONEY=" + ThisRemainMoney + " where watersaleinfonum='" + saleNum + "'";*/
		strsql = "update WATERSALEINFO set LastRemainMoney=" + LastRemainMoney
				+ ",THISTOTALMONEY=" + ThisRemainMoney + " where watersaleinfonum='" + saleNum + "'";
		Connection conn1=null;
		PreparedStatement ps1 =null;
		boolean b=false;
		try
		{
			conn1 = ConnDB.getConnection();
			ps1 = conn1.prepareStatement(strsql);

			b = (ps1.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, ps1);
		}

		return b;

	}

	public String GetNextChareNum() throws SQLException
	{
		String maxNum = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH;mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd0001");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");

		DecimalFormat nf = new DecimalFormat("0000");

		String sql = "select max(to_number( substr(SaleInfoNum,9,6)))maxNum from APSaleInfoWater where BuyTime>=to_date('"
				+ df3.format(new Date()) + "','yyyy-mm-dd')";
		
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
				maxNum = rs.getString("maxNum");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		while (rs.next())
//		{
//			maxNum = rs.getString("maxNum");
//		}


		String nextNum = "";
		if (maxNum == null)
		{
			nextNum = df2.format(new Date());
		} else
		{
			int nextnum = Integer.parseInt(maxNum) + 1;

			nextNum = df3.format(new Date()) + nf.format(nextnum);
		}
		return nextNum;

	}

	public boolean getWaterJieSuan(int amMeter_ID, String endTime) throws SQLException, ParseException
	{
		boolean b = false;
		String errormsg = "";
		String StarTime = "";
		float ThisZValue = 0;
		float LastZValue = 0;
		String ThisTime = "";
		String LastTime = "";
		String BeiLv = "1";
		float price = 0;
		float ThisRemainMoney = 0;
		float LastRemainMoney = 0;

		String strsql = "select Beilv,(select price_value from price where price_id=(select price_id from WATERMETER where WATERMETER_ID="
				+ amMeter_ID + "))price" + " from WATERMETER a where WATERMETER_ID=" + amMeter_ID;
		
		PreparedStatement pst1=null;
		ResultSet rs1 =null;
		Connection conn1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pst1 = conn1.prepareStatement(strsql);
			rs1 = pst1.executeQuery();
			if (rs1.next())
			{
				BeiLv = rs1.getString("Beilv");
				price = rs1.getFloat("price");

			}
		} catch (Exception e1)
		{
			e1.printStackTrace();
			return false;
			
		}finally{
			ConnDB.release(conn1, pst1, rs1);
		}

		// 获取结算的起始时间，如果系统还未结算就取表码为初始值
		strsql = "select * from (select ThisZValue,to_char(ThisTime,'yyyy-mm-dd hh24:mi:ss')ThisTime,jieSuanTime from WATERJIESUAN a where WATERMETER_ID="
				+ amMeter_ID + " order by ThisTime desc) where rownum=1";
		Connection conn = null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			conn = ConnDB.getConnection();
			pst = conn.prepareStatement(strsql);
			rs = pst.executeQuery();
			if (rs.next())
			{
				LastZValue = Float.parseFloat(rs.getString("ThisZValue"));
				LastTime = rs.getString("ThisTime");
				StarTime = rs.getString("jieSuanTime");

			} else
			{
				//第一次没数据，起码从表里面取
				String sqlString = "select ZValueZY,to_char(ValueTime,'yyyy-mm-dd hh24:mi:ss')ValueTime from (select * from ZWATERDATAS"+String.valueOf(amMeter_ID) + " order by valuetime asc) where rownum=1";
				Connection conn0 = null;
				PreparedStatement ps0 =null;
				ResultSet rs0 =null;
				try{
					conn0 = ConnDB.getConnection();
					ps0 = conn0.prepareStatement(sqlString);
					rs0 = ps0.executeQuery();
					if(rs0.next()){
						LastZValue = rs0.getFloat("ZValueZY");
						LastTime = rs0.getString("ValueTime");
						StarTime = LastTime;
					}else{
						/*LastTime = "1900-01-01 00:00:00";
						StarTime = LastTime;*/
						return false;
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					ConnDB.release(conn0, ps0,rs0);
				}
				
				//LastZValue = 0;
				//LastTime = "1900-01-01 00:00:00";
				//LastRemainMoney = 0;
				//StarTime = "1900-01-01 00:00:00";

			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
			return false;
		}finally{
			ConnDB.release(conn, pst, rs);
		}

		// 当起始时间小于终止时间时
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date starDate = new Date();
		Date endDate = new Date();

		starDate = sdf.parse(StarTime);
		endDate = sdf.parse(endTime);

		if (starDate.before(endDate))
		{
			// 获取终止数据
			float usedmoney = 0;
			float usedvol = 0;
			strsql = "select to_char(ValueTime,'yyyy-mm-dd hh24:mi:ss')ValueTime,ZValueZY from (select ValueTime,ZValueZY from ZWaterDatas"+ amMeter_ID 
					+ " where ValueTime>to_date('" + sdf.format(starDate) + "','yyyy-mm-dd hh24:mi:ss') and ValueTime<=to_date('" + endTime
					+ "','yyyy-mm-dd hh24:mi:ss') order by ValueTime desc) where rownum=1";
			PreparedStatement pst3 = null;
			ResultSet rs3 = null;
			Connection conn3 =null;
			try
			{
				conn3 =ConnDB.getConnection();
				pst3 = conn3.prepareStatement(strsql);
				rs3 = pst3.executeQuery();
				if (rs3.next())
				{
					ThisZValue = rs3.getFloat("ZValueZY");
					//ThisZValue = ThisZValue * Float.parseFloat(BeiLv);
					ThisTime = rs3.getString("ValueTime");

					usedvol = ThisZValue - LastZValue;// 用量
					DecimalFormat df = new DecimalFormat("0.##");
					// 使用金额
					String usedmoney1 = df.format(usedvol * price);// 要除倍率
					usedmoney = Float.parseFloat(usedmoney1);
					// 本次剩余金额----上次剩余+已购金额-使用金额
					/*float buyTotalMoney = 0;
					strsql = "select nvl(sum(ThisPurchaseMoney),0)total from WaterSaleInfo where WaterMeter_ID =" + amMeter_ID + " and BuyTime>to_date('"
							+ StarTime + "','yyyy-mm-dd hh24:mi:ss') and BuyTime<=to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss')";
					PreparedStatement pst4=null;
					ResultSet rs4=null;
					try
					{
						pst4 = conn3.prepareStatement(strsql);
						rs4 = pst4.executeQuery();
						if (rs4.next())
						{
							buyTotalMoney = rs4.getFloat("total");
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}finally{
						ConnDB.release( pst4, rs4);
					}

					ThisRemainMoney = LastRemainMoney + buyTotalMoney - usedmoney;*/

				} else
				{
					ThisZValue = LastZValue;
					ThisTime = LastTime;
				}
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
				return false;
			}finally{
				ConnDB.release(conn3, pst3, rs3);
			}

			int isPay=0;
			if(usedmoney==0){
				isPay=1;
			}
			// 插入数据库
			strsql = "insert into WaterJieSuan(WaterMeter_ID,ThisZValue ,LastZValue,ThisTime,LastTime,JieSuanTime,LastRemainMoney,ThisRemainMoney,ThisUseMoney,ThisUseGross,isPay) "
					+ "values("
					+ amMeter_ID
					+ ","
					+ ThisZValue
					+ ","
					+ LastZValue
					+ ",to_date('"
					+ sdf.format(sdf.parse(ThisTime))
					+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
					+ sdf.format(sdf.parse(LastTime))
					+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
					+ endTime
					+ "','yyyy-mm-dd hh24:mi:ss'),"
					+ LastRemainMoney
					+ ","
					+ ThisRemainMoney + "," + usedmoney + "," + usedvol + ","+isPay+")";

			PreparedStatement pst5 =null;
			Connection conn5 = null;
			try
			{
				conn5 = ConnDB.getConnection();
				pst5 = conn5.prepareStatement(strsql);
				b = (pst5.executeUpdate() == 1);
			} catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}finally{
				ConnDB.release(conn5, pst5);
			}

		} else
		{
			errormsg = "本次结算起始时间大于中止时间，不能结算";
		}

		return b;

	}
	
	
	public String getTelNum(int amMeter_ID, String table,String id) throws SQLException
	{
		String tel=null;
		String sql = "select consumername,consumerphone from "+table+" where "+id+"="+amMeter_ID;
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
				tel=rs.getString("consumerphone");
				tel+=","+rs.getString("consumername");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		if (rs.next())
//		{
//			tel=rs.getString("consumerphone");
//			tel+=","+rs.getString("consumername");
//		}


		return tel;
	}

	public boolean insertSMSWill(String phone, String content, String userID)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String nowString = sdf.format(now);
		boolean b = false;
		
		String sql = "insert into SMSWILL(SMSWILL_PHONELIST,SMSWILL_CONTENT,SMSWILL_STATE,SMSWILL_REPEATTIME,USERS_ID,INSERTTIME,MODULE_NUM) values('"
				+ phone + "','" + content + "',0,3," + userID + ",'" + nowString + "','0000')";
		Connection conn = null;
		PreparedStatement pst = null;
		//System.out.println(sql);
		try
		{
			conn = ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			b = (pst.executeUpdate()==1);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, pst);
		}
		
		return b;
	}
	
	

}
