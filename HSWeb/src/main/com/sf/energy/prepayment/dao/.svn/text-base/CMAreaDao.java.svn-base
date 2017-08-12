package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sf.energy.prepayment.model.CMAreaModel;
import com.sf.energy.util.ConnDB;

public class CMAreaDao
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

	public ArrayList<CMAreaModel> queryAllInfo(int pageCount, int pageIndex, String tableName, String order) throws SQLException
	{
		ArrayList<CMAreaModel> list = new ArrayList<CMAreaModel>();
		CMAreaModel cmm = null;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
		
		Date date = new Date();
		String queryDay = sdf1.format(date);
		String queryMonth = sdf2.format(date);
		
//		String[] TitleList = { "Area_Name", "DayAmGross", "DayAmMonsey", "MonthAmGross", "MonthAmMoney", "MeterCount", "UsingMeterCount", "offMeterCount" };
//		String order = "order by Area_ID";
//		String[] orderInfo = null; 
//		
//		//检查是否需要排序
//		if(!"".equals(selectPart) && selectPart != null)
//		{
//			orderInfo = selectPart.split("\\|");
//			for(String title : TitleList)
//			{
//				//检查排序字段是否存在
//				if(title.equals(orderInfo[0]))
//				{
//					order = "order by " + orderInfo[0] + " " +orderInfo[1];
//				}
//			}
//		}
		
		String sql = "select "+
						"* "+
					"from "+
					"("+
						"select "+
							"a.Area_ID,Area_Name,nvl(DayAmMoney,0)DayAmMoney,nvl(DayAmGross,0)DayAmGross,nvl(MonthAmMoney,0)MonthAmMoney,nvl(MonthAmGross,0)MonthAmGross,"+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Area_ID=a.Area_ID  and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) as MeterCount, "+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Area_ID=a.Area_ID and x.AmMeter_Stat>0 and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) as UsingMeterCount,"+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Area_ID=a.Area_ID and (x.IsSupply='1' or x.IsSupply='11' or x.IsSupply='01' or x.IsSupply='10') and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) as offMeterCount "+
						"from "+
						"("+
							"select "+
								"a.Area_ID,Area_Name,DayAmMoney,DayAmGross "+
							"from "+
								"(Area)a "+
							"left join "+
							"("+
								"select "+
									"Area_ID,sum(TheGross)DayAmGross,sum(TheMoney)DayAmMoney "+
								"from "+
								"("+
									"select "+
										"x.Ammeter_ID,Area_ID,TheGross,TheMoney "+
									"from "+
										"(APSaleInfo)x,"+
										"(AmMeter)y "+
									"where "+
										"x.Ammeter_ID=y.Ammeter_ID "+
										"and to_char(BuyTime,'yyyy-mm-dd') = '" + queryDay + "'"+
								")T group by Area_ID"+
							")b on a.Area_ID=b.Area_ID "+
						")a "+
						"left join "+
						"("+
							"select "+
								"Area_ID,sum(TheGross)MonthAmGross,sum(TheMoney)MonthAmMoney "+
							"from "+
							"("+
								"select "+
									"x.Ammeter_ID,Area_ID,TheGross,TheMoney "+
								"from "+
									"(APSaleInfo)x,"+
									"(AmMeter)y "+
								"where "+
									"x.Ammeter_ID=y.Ammeter_ID "+
									"and  to_char(BuyTime,'yyyy-mm') = '" + queryMonth + "'"+
							")T group by Area_ID"+
						")b on a.Area_ID=b.Area_ID "+
					")o ";
		
		sql += "order by "+tableName+" "+order;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
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
			
			while(rs.next()&&(count > 0))
			{
				cmm = new CMAreaModel();
				cmm.setAreaID(rs.getInt("Area_ID"));
				cmm.setAreaOrArchName(rs.getString("Area_Name"));
				cmm.setDayAmGross(rs.getFloat("DayAmGross"));
				cmm.setDayAmMoney(rs.getFloat("DayAmMoney"));
				cmm.setMonthAmGross(rs.getFloat("MonthAmGross"));
				cmm.setMonthAmMoney(rs.getFloat("MonthAmMoney"));
				cmm.setMeterCount(rs.getInt("MeterCount"));
				cmm.setUsingMeterCount(rs.getInt("UsingMeterCount"));
				cmm.setOffMeterCount(rs.getInt("offMeterCount"));
				
				list.add(cmm);
				count--;
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
	
	public ArrayList<CMAreaModel> queryInfoByAreaID(int pageCount, int pageIndex, int areaID, String tableName, String order) throws SQLException
	{
		ArrayList<CMAreaModel> list = new ArrayList<CMAreaModel>();
		CMAreaModel cmm = null;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
		
		Date date = new Date();
		String queryDay = sdf1.format(date);
		String queryMonth = sdf2.format(date);
		
		String sql = "select "+
						"* "+
					"from "+
					"("+
						"select "+
							"a.Architecture_ID,Architecture_Name,nvl(DayAmMoney,0)DayAmMoney,nvl(DayAmGross,0)DayAmGross,nvl(MonthAmMoney,0)MonthAmMoney,nvl(MonthAmGross,0)MonthAmGross,"+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Architecture_ID=a.Architecture_ID  ) as MeterCount, "+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Architecture_ID=a.Architecture_ID and x.AmMeter_Stat>0 ) as UsingMeterCount,"+
							"(select count(x.AmMeter_ID) from (AmMeter)x where x.Architecture_ID=a.Architecture_ID and (x.IsSupply='1' or x.IsSupply='11' or x.IsSupply='01' or x.IsSupply='10') ) as offMeterCount "+
						"from "+
						"("+
							"select "+
								"a.Architecture_ID,a.Architecture_AdvancePayment,Architecture_Name,DayAmMoney,DayAmGross "+
							"from "+
								"(Architecture)a "+
							"left join "+
							"("+
								"select "+
									"Architecture_ID,sum(TheGross)DayAmGross,sum(TheMoney)DayAmMoney "+
								"from "+
								"("+
									"select "+
										"x.Ammeter_ID,Architecture_ID,TheGross,TheMoney "+
									"from "+
										"(APSaleInfo)x,"+
										"(AmMeter)y "+
									"where "+
										"x.Ammeter_ID=y.Ammeter_ID "+
									"and to_char(BuyTime,'yyyy-mm-dd') = '"+queryDay+"'"+
								")T group by Architecture_ID"+
							")b on a.Architecture_ID=b.Architecture_ID "+
							"where "+
								"Area_ID=" + areaID +
							")a "+
						"left join "+
						"("+
							"select "+
								"Architecture_ID,sum(TheGross)MonthAmGross,sum(TheMoney)MonthAmMoney "+
							"from "+
							"("+
								"select "+
									"x.Ammeter_ID,Architecture_ID,TheGross,TheMoney "+
								"from "+
									"(APSaleInfo)x,"+
									"(AmMeter)y "+
								"where "+
									"x.Ammeter_ID=y.Ammeter_ID "+
									"and to_char(BuyTime,'yyyy-mm') = '"+queryMonth+"'"+
							")T group by Architecture_ID"+
						")b on a.Architecture_ID=b.Architecture_ID "+
					"where "+
						"a.Architecture_AdvancePayment = 1 "+
					")o ";
		sql += "order by "+tableName+" "+order;
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
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
			
			while(rs.next()&&(count > 0))
			{
				cmm = new CMAreaModel();
				cmm.setArchitecture_ID(rs.getInt("Architecture_ID"));
				cmm.setAreaOrArchName(rs.getString("Architecture_Name"));
				cmm.setDayAmGross(rs.getFloat("DayAmGross"));
				cmm.setDayAmMoney(rs.getFloat("DayAmMoney"));
				cmm.setMonthAmGross(rs.getFloat("MonthAmGross"));
				cmm.setMonthAmMoney(rs.getFloat("MonthAmMoney"));
				cmm.setMeterCount(rs.getInt("MeterCount"));
				cmm.setUsingMeterCount(rs.getInt("UsingMeterCount"));
				cmm.setOffMeterCount(rs.getInt("offMeterCount"));
				
				list.add(cmm);
				count--;
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

}
