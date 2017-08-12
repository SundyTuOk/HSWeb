package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.prepayment.model.YuGouDetailsModel;
import com.sf.energy.util.ConnDB;

public class YuGouDetailsDao
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
	
	public ArrayList<YuGouDetailsModel> queryInfo(int pageCount, int pageIndex, String selectInfo, String queryMsg,boolean isPage, String tableName, String order) throws SQLException
	{
		ArrayList<YuGouDetailsModel> list = new ArrayList<YuGouDetailsModel>();
		YuGouDetailsModel ygdm = null;
		String[] info = selectInfo.split("\\|");		
		String strSelect1 = "";
		String strSelect2 = "";
		
		//System.out.println("selectInfo:"+selectInfo + "      queryMsg:"+queryMsg + "     tableName:"+tableName + "  "+ order);
		
		if(!"".equals(queryMsg) && queryMsg != null)
		{
			String[] msg = queryMsg.split("\\|");
			
			strSelect1 = " where ReadTime>=to_date('" + msg[0] + "','yyyy-mm-dd hh24:mi:ss') and ReadTime<=to_date('" + msg[1] + "','yyyy-mm-dd hh24:mi:ss')";
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
						"a.AmMeter_ID,a.AMMGDINFONUM,to_char(a.ReadTime,'yyyy-mm-dd hh24:mi:ss')ReadTime,a.BuyTimes,a.ThisPurchaseMoney,a.LastRemainMoney,a.ThisRemainMoney,"+
						"(select Architecture_Name from (Architecture)c where b.Architecture_ID=c.Architecture_ID)Architecture_Name,"+
						"b.Floor,b.AmMeter_Name,"+
						"(select Users_Name from (Users)b where a.Users_ID=b.Users_ID)Users_Name "+
					"from "+
						"(select * from AmMeterGDInfo " + strSelect1 + ")a,"+
						"(select * from AmMeter " + strSelect2 + ")b "+
					"where a.Ammeter_ID=b.Ammeter_ID";
		sql += " order by "+tableName+" "+order;
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				ygdm.setAMMGDINFONUM(rs.getString("AMMGDINFONUM"));
				ygdm.setArchitecture_Name(rs.getString("Architecture_Name"));
				ygdm.setFloorName(rs.getString("Floor"));
				ygdm.setAmMeter_Name(rs.getString("AmMeter_Name"));
				ygdm.setReadTime(rs.getString("ReadTime"));
	            
				ygdm.setBuyTimes(rs.getString("BuyTimes"));
				ygdm.setThisPurchaseMoney(rs.getString("ThisPurchaseMoney"));
				ygdm.setLastRemainMoney(rs.getString("LastRemainMoney"));
				ygdm.setThisRemainMoney(rs.getString("ThisRemainMoney"));
				
	            if(rs.getString("Users_Name") != null && !"".equals(rs.getString("Users_Name")))
				{
	            	ygdm.setUsers_Name(rs.getString("Users_Name"));
				}
				else
				{
					ygdm.setUsers_Name("");
				}       
	            
	            list.add(ygdm);
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

	public int deleteInfo(String theID) throws SQLException
	{
		int res = 0;
		String sql = "delete from AmMeterGDInfo where AMMGDINFONUM in("+theID+")";
//		PreparedStatement pst = ConnDB.getConnection().prepareStatement(sql);
//		res = pst.executeUpdate();
//		
//		if(pst!=null)
//			pst.close();
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			res =ps.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps);
		}
		return res;
	}
	
	
}
