package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.prepayment.model.APXiaohuModel;
import com.sf.energy.util.ConnDB;

public class APXiaohuDao
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
	 * 查询销户退电信息
	 * @param pageCount
	 * @param pageIndex
	 * @param selectInfo
	 * @param queryMsg
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<APXiaohuModel> queryInfo(int pageCount, int pageIndex, String selectInfo, String queryMsg, String order, boolean isPage) throws SQLException
	{
		ArrayList<APXiaohuModel> list = new ArrayList<APXiaohuModel>();
		APXiaohuModel apxh = null;
		
		String sql = "";
		String where1 = "";
		String where2 = "";
		
		if(!"".equals(queryMsg) && queryMsg != null)
		{
			String[] msg = queryMsg.split("\\|");
			where1 = " where TheTime>=" + "to_date('" + msg[0] + "','yyyy-mm-dd')" + " and TheTime<=" + "(to_date('" + msg[1] + "','yyyy-mm-dd')+1) ";
		}
		
		/*if(!"".equals(selectInfo) && selectInfo != null)
		{
			String[] info = selectInfo.split("\\|");
			if ("area".equals(info[0]))
	        {
				where2 = " and (Area_ID=" + info[1] + " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 )) ";
	        }
	        else if ("arch".equals(info[0]))
	        {
	        	where2 = " and (Architecture_ID=" + info[1] + ")";
	        }
		}*/
		if(!"".equals(selectInfo) && selectInfo != null)
		{
			String[] info = selectInfo.split("\\|");
			if("all".equals(info[0]))
			{
				where2 = " and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1)";
			}
			if("area".equals(info[0]))
			{
				where2 = " and (Area_ID="+info[1]+" and Architecture_ID in (select Architecture_ID from Architecture where Architecture_AdvancePayment=1 ))";	        
			}
			if("arch".equals(info[0]))
			{
				where2 = " and (Architecture_ID="+info[1]+")";
			}
			if("floor".equals(info[0]))
			{
				where2 = " and (Architecture_ID="+info[1]+") and (Floor="+info[2]+")";
			}
			if("meter".equals(info[0]))
			{
				where2 = " and (a.AmMeter_ID="+info[1]+")";
			}
		}
		
		sql = "select "+
				"a.ID,a.Ammeter_ID,Ammeter_Name,"+
				"(select Area_Name from Area c where c.Area_ID=b.Area_ID)Area_Name,"+
				"(select Architecture_Name from Architecture c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,"+
				"Floor,TheTime,QSYValue,QZValue,"+
				"(select Users_name from Users c where a.Users_ID =c.Users_ID)Users_name,"+
				"Status,TheMoney "+
			"from "+
				"(select ID,Ammeter_ID,to_char(TheTime,'yyyy-mm-dd hh24:mi:ss')TheTime,QSYValue,QZValue,Users_ID,Status,TheMoney from  APXiaohuInfo "+where1+") a ,"+
				"Ammeter b "+
			"where "+
				"a.Ammeter_ID=b.Ammeter_ID " + where2 + order;
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
				apxh = new APXiaohuModel();
				apxh.setId(rs.getInt("ID"));
				apxh.setAmMeter_ID(rs.getInt("Ammeter_ID"));
				apxh.setAreaName(rs.getString("Area_Name"));
				apxh.setArchitectureName(rs.getString("Architecture_Name"));
				apxh.setFloorName(rs.getString("Floor"));
				apxh.setAmMeterName(rs.getString("AmMeter_Name"));
				apxh.setTheTime(rs.getString("TheTime"));
				apxh.setTheMoney(rs.getString("TheMoney"));
				apxh.setQSYValue(rs.getString("QSYValue"));
				apxh.setQZValue(rs.getString("QZValue"));
				apxh.setUserName(rs.getString("Users_Name"));
				String status = rs.getString("Status");
	            if ("1".equals(status))
	            {
	            	status = "成功";
	            }
	            else
	            {
	            	status = "失败";
	            }
				apxh.setStatus(status);
				
				list.add(apxh);
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
	
	public String getXiaohuInfoByID(int id){
		String str = "";
		String sql = "SELECT AMMETER_NAME,AMMETER_485ADDRESS from AMMETER where AMMETER_ID=(SELECT AMMETER_ID from APXIAOHUINFO where id="+id+")";
		
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
				str = rs.getString("AMMETER_NAME")+",表地址："+rs.getString("AMMETER_485ADDRESS");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return str;
	}
	
	/**
	 * 删
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int ID) throws SQLException
	{
		String sql = "delete FROM APXiaohuInfo where ID =  " + ID;
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

}
