package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import com.eltima.components.ui.m;
import com.sf.energy.prepayment.model.VolumeSetModel;
import com.sf.energy.util.ConnDB;

public class VolumeSetDao
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

	public ArrayList<VolumeSetModel> queryInfo_out(String selectInfo, String tableName, String order, int price, int AmMeterStatus) throws SQLException
	{
		ArrayList<VolumeSetModel> list = new ArrayList<VolumeSetModel>();
		VolumeSetModel vsm = null;
		String[] info = selectInfo.split("\\|");

		String sql = "select AmMeter_ID, AmMeter_Name, Floor,(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Price_Value from (Price)b where a.Price_ID=b.Price_ID)Price,Price_ID,Beilv,nvl(APState,'未开户')APState,(select METESTYLE_NAME from METESTYLE b where b.METESTYLE_ID=a.METESTYLE_ID)METESTYLE_NAME,AmMeter_Stat,IsSupply from (AmMeter)a where ";
		if ("all".equals(info[0]))
		{
			sql += " Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		}
		if ("area".equals(info[0]))
		{
			sql += " (Area_ID=" + info[1] + " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1))";
		}
		if ("arch".equals(info[0]))
		{
			sql += " (Architecture_ID=" + info[1] + ")";
		}
		if ("floor".equals(info[0]))
		{
			sql += " (Architecture_ID=" + info[1] + ") and (Floor=" + info[2] + ")";
		}
		if ("meter".equals(info[0]))
		{
			sql += " (AmMeter_ID=" + info[1] + ") ";
		}
		sql = "select T1.*,(select  max(SYValue) from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime group by T3.AmMeter_ID)SYValue,(select  max(SYMoney) from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime group by T3.AmMeter_ID)SYMoney from  ("
				+ sql
				+ ")T1 left join (select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID";
		
		if(price != 0 && AmMeterStatus != -1){
			sql += " where T1.price_id=" + price + " and T1.AmMeter_Stat=" + AmMeterStatus + " order by " + tableName + " " + order;
		}
		else if(price != 0 && AmMeterStatus == -1){
			sql += " where T1.price_id=" + price + " order by " + tableName + " " + order;
		}
		else if(price == 0 && AmMeterStatus != -1){
			sql += " where T1.AmMeter_Stat=" + AmMeterStatus + " order by " + tableName + " " + order;
		}
		else{
			sql += " order by " + tableName + " " + order;
		}

		/*
		 * ArrayList<VolumeSetModel> list = new ArrayList<VolumeSetModel>();
		 * VolumeSetModel vsm = null;
		 * 
		 * String sql =
		 * "select AmMeter_ID, AmMeter_Name, Floor,(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Price_Value from (Price)b where a.Price_ID=b.Price_ID)Price,Beilv,nvl(APState,'未开户')APState,AmMeter_Stat,IsSupply from (AmMeter)a where "
		 * ; sql +=
		 * " Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)"
		 * ; sql =
		 * "select T1.*,(select  max(SYValue) from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime group by T3.AmMeter_ID)SYValue from  ("
		 * + sql +
		 * ")T1 left join (select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID"
		 * ; //System.out.println("sql:"+sql);
		 */
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				vsm = new VolumeSetModel();
				vsm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
				vsm.setArchitecture_Name(rs.getString("Architecture_Name"));
				vsm.setFloorName(rs.getString("Floor") + "楼");
				vsm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				vsm.setPrice(rs.getString("Price"));
				vsm.setMeteStyle_Name(rs.getString("METESTYLE_NAME"));
				
				String meterPotocol = (new CMMeterDao()).getMeterPotocol(rs.getInt("AmMeter_ID"));
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(rs.getInt("AmMeter_ID"));
				if ("0".equals(AmMeter_CostType))
				{// 后付费
					CMMeterDao dao = new CMMeterDao();
					String remainMoney = dao.getRemainMoney(rs.getInt("AmMeter_ID"));
					vsm.setSYValue(remainMoney);
				} else
				{// 预付费
					if ("1".equals(meterPotocol))
					{
						if ("".equals(rs.getString("SYValue")) || rs.getString("SYValue") == null)
						{
							vsm.setSYValue("0");
						} else
						{
							vsm.setSYValue(rs.getString("SYValue"));
						}
					} else
					{
						if ("".equals(rs.getString("SYMoney")) || rs.getString("SYMoney") == null)
						{
							vsm.setSYValue("0");
						} else
						{
							vsm.setSYValue(rs.getString("SYMoney"));
						}
					}
				}
				
				vsm.setAPState(rs.getString("APState"));

				String AmMeter_Stat = "在线";
				if ("0".equals(rs.getString("AmMeter_Stat")))
				{
					AmMeter_Stat = "离线";
				}
				vsm.setAmMeter_Stat(AmMeter_Stat);

				String IsSupply = "合闸";
				String state = rs.getString("IsSupply");
				if ("".equals(state))
					state = "00";

				if("0".equals(state.substring(0, 1))){
					IsSupply = "合闸";
				}else{
					IsSupply = "分闸";
				}
				/*if ("00".equals(state))
				{
					IsSupply = "空调：分闸,照明：分闸";
				} else if ("01".equals(state))
				{
					IsSupply = "空调：分闸,照明：合闸";
				} else if ("10".equals(state))
				{
					IsSupply = "空调：合闸,照明：分闸";
				} else if ("11".equals(state))
				{
					IsSupply = "空调：合闸,照明：合闸";
				}*/
				vsm.setIsSupply(IsSupply);

				list.add(vsm);

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;
	}

	public ArrayList<VolumeSetModel> queryInfo(int pageCount, int pageIndex, String selectInfo, boolean isPage, String tableName, String order, int price, int AmMeterStatus)
			throws SQLException
	{
		ArrayList<VolumeSetModel> list = new ArrayList<VolumeSetModel>();
		VolumeSetModel vsm = null;
		String[] info = selectInfo.split("\\|");

		String sql = "select AmMeter_ID, AmMeter_Name, Floor,(select Architecture_Name from (Architecture)b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,(select isunit from (Architecture)b where a.Architecture_ID=b.Architecture_ID)isunit,(select Price_Value from (Price)b where a.Price_ID=b.Price_ID)Price,Price_ID,Beilv,nvl(APState,'未开户')APState,(select METESTYLE_NAME from METESTYLE b where b.METESTYLE_ID=a.METESTYLE_ID)METESTYLE_NAME,AmMeter_Stat,IsSupply from (AmMeter)a where ";
		if ("all".equals(info[0]))
		{
			sql += " Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
		}
		if ("area".equals(info[0]))
		{
			sql += " (Area_ID=" + info[1] + " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1))";
		}
		if ("arch".equals(info[0]))
		{
			sql += " (Architecture_ID=" + info[1] + ")";
		}
		if ("floor".equals(info[0]))
		{
			sql += " (Architecture_ID=" + info[1] + ") and (Floor=" + info[2] + ")";
		}
		if ("meter".equals(info[0]))
		{
			sql += " (AmMeter_ID=" + info[1] + ") ";
		}
		sql = "select T1.*,(select  max(SYValue) from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime group by T3.AmMeter_ID)SYValue,(select  max(SYMoney) from (AmMeterAPDatas)T3 where T3.AmMeter_ID=T2. AmMeter_ID and T3.ValueTime=T2.MValueTime group by T3.AmMeter_ID)SYMoney from  ("
				+ sql
				+ ")T1 left join (select MAX(ValueTime )MValueTime,AmMeter_ID from AmMeterAPDatas group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID ";
		if(price != 0 && AmMeterStatus != -1){
			sql += " where T1.price_id=" + price + " and T1.AmMeter_Stat=" + AmMeterStatus + " order by " + tableName + " " + order;
		}
		else if(price != 0 && AmMeterStatus == -1){
			sql += " where T1.price_id=" + price + " order by " + tableName + " " + order;
		}
		else if(price == 0 && AmMeterStatus != -1){
			sql += " where T1.AmMeter_Stat=" + AmMeterStatus + " order by " + tableName + " " + order;
		}
		else{
			sql += " order by " + tableName + " " + order;
		}
		//sql += " where T1.price=" + price + " order by " + tableName + " " + order;
		//sql += " order by " + tableName + " " + order;

		// System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			if (isPage == true)
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
			} else
			{
				rs.beforeFirst();
			}

			while (rs.next() && (count > 0))
			{
				vsm = new VolumeSetModel();
				String unit = "楼";
				if("1".equals(rs.getString("isunit"))){
					unit = "门";
				}else{
					unit = "楼";
				}
				vsm.setAmMeter_ID(rs.getInt("AmMeter_ID"));
				vsm.setArchitecture_Name(rs.getString("Architecture_Name"));
				vsm.setFloorName(rs.getString("Floor") + unit);
				vsm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				vsm.setPrice(rs.getString("Price"));
				vsm.setMeteStyle_Name(rs.getString("METESTYLE_NAME"));

				String meterPotocol = (new CMMeterDao()).getMeterPotocol(rs.getInt("AmMeter_ID"));
				String AmMeter_CostType = (new CMMeterDao()).getMeterCostType(rs.getInt("AmMeter_ID"));
				if ("0".equals(AmMeter_CostType))
				{// 后付费
					CMMeterDao dao = new CMMeterDao();
					String remainMoney = dao.getRemainMoney(rs.getInt("AmMeter_ID"));
					vsm.setSYValue(remainMoney);
				} else
				{// 预付费
					if ("1".equals(meterPotocol))
					{
						if ("".equals(rs.getString("SYValue")) || rs.getString("SYValue") == null)
						{
							vsm.setSYValue("0");
						} else
						{
							vsm.setSYValue(rs.getString("SYValue"));
						}
					} else
					{
						if ("".equals(rs.getString("SYMoney")) || rs.getString("SYMoney") == null)
						{
							vsm.setSYValue("0");
						} else
						{
							vsm.setSYValue(rs.getString("SYMoney"));
						}
					}

				}

				vsm.setAPState(rs.getString("APState"));

				String AmMeter_Stat = "<span style=\"color:Green\">在线</span>";
				if ("0".equals(rs.getString("AmMeter_Stat")))
				{
					AmMeter_Stat = "<span style=\"color:Red\">离线</span>";
				}
				vsm.setAmMeter_Stat(AmMeter_Stat);

				String IsSupply = "<span style=\"color:Green\">合闸</span>";
				String state = rs.getString("IsSupply");
				if ("".equals(state))
					state = "0";
				
				if("0".equals(state.substring(0, 1))){
					IsSupply = "<span style=\"color:Green\">合闸</span>";
				}else{
					IsSupply = "<span style=\"color:red\">分闸</span>";
				}

				/*if ("00".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">分闸</span>,照明：<span style=\"color:red\">分闸</span>";
				} else if ("01".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">分闸</span>,照明：<span style=\"color:red\">合闸</span>";
				} else if ("10".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">合闸</span>,照明：<span style=\"color:red\">分闸</span>";
				} else if ("11".equals(state))
				{
					IsSupply = "空调：<span style=\"color:red\">合闸</span>,照明：<span style=\"color:red\">合闸</span>";
				}*/
				vsm.setIsSupply(IsSupply);

				list.add(vsm);
				count--;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}

		return list;

	}

	public void serUpdateZQInfo(int amMeterId, String userID, String sYValue, String totoleUsed) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		String sql1 = "delete from AmMeterAPDatas where AmMeter_ID=" + amMeterId;
		String sql2 = "Delete from ZAMDATAS" + String.valueOf(amMeterId);
		String sql3 = "update T_DayAm set EndReadingDate=to_date(sysdate,'yyyy-mm-dd hh24:mi:ss'),EndZValueZY=0 " + "where AmMeter_ID=" + amMeterId
				+ " and SelectYear=" + cal.get(Calendar.YEAR) + " and SelectMonth=" + cal.get(Calendar.MONTH) + 1 + " and SelectDay="
				+ cal.get(Calendar.DAY_OF_MONTH);
		Statement st = null;
		Connection conn0 = null;
		try
		{
			conn0 = ConnDB.getConnection();
			st = conn0.createStatement();
			st.addBatch(sql1);
			st.addBatch(sql2);
			st.addBatch(sql3);
			int[] k = st.executeBatch();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn0, st);
		}

		String GValue = "";
		float Price = 1;
		float beilv = 1;
		String sql = "select nvl(beilv,0)beilv,nvl(GValue,0)GValue,(select Price_Value from Price b where a.Price_ID=b.Price_ID)Price_Value from AmMeter a where AmMeter_ID="
				+ amMeterId;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next())
			{
				GValue = rs.getString("GValue");
				beilv = rs.getFloat("beilv");
				if (!"".equals(rs.getString("Price_Value")) && !"null".equals(rs.getString("Price_Value")))
				{
					Price = rs.getFloat("Price_Value");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, pst, rs);
		}

		if ("".equals(GValue))
			GValue = "0";
		if ("".equals(totoleUsed))
			totoleUsed = "0";
		if ("".equals(sYValue))
			sYValue = "0";
		String TZValue = "0";
		String TZMoney = "0";
		String meterPotocol = (new CMMeterDao()).getMeterPotocol(amMeterId);
		if(Float.parseFloat(GValue) > 0){
			if("1".equals(meterPotocol)){
				float sy = Float.parseFloat(GValue)*beilv - Float.parseFloat(totoleUsed)*beilv;
				if(sy > 0){
					sYValue = sy + "";
				}else {
					TZValue = Math.abs(sy) + "";
					TZMoney = Math.abs(sy * Price) + ""; 
				}
			}else {
				float sy = Float.parseFloat(GValue)*beilv - Float.parseFloat(totoleUsed) * beilv * Price;
				if(sy > 0){
					sYValue = sy + "";
				}else {
					TZMoney = Math.abs(sy) + "";
					TZValue = Math.abs(sy/Price) + ""; 
				}
			}
			
		}
		sql = "update AmMeter set APState='销户',GValue=0 where Ammeter_ID=" + amMeterId;
		Connection conn1 = null;
		PreparedStatement pst1 = null;
		try
		{
			conn1 = ConnDB.getConnection();
			pst1 = conn1.prepareStatement(sql);
			pst1.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn1, pst1);
		}
		sql = "insert into APZQInfo(Ammeter_ID,TheTime,OldSY,NewZValue,SYValue,TZValue,TheMoney,Users_ID) values(" + amMeterId + ",sysdate," + GValue + "," + totoleUsed + "," + sYValue  + "," + TZValue + "," + TZMoney + "," + userID + ")";
		Connection conn2 = null;
		PreparedStatement pst2 = null;
		try
		{
			conn2 = ConnDB.getConnection();
			pst2 = conn2.prepareStatement(sql);
			pst2.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn2, pst2);
		}
	}

}
