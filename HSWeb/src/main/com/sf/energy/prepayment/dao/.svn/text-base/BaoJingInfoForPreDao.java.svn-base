package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.powernet.model.BaoJingInfoModel;
import com.sf.energy.prepayment.model.ApCardDZModel;
import com.sf.energy.prepayment.model.ApCardSaleInfoModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.util.ConnDB;

public class BaoJingInfoForPreDao
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


	public ArrayList<BaoJingInfoModel> queryInfo(Integer pageCount, Integer pageIndex, String selectInfo, String startTime, String endTime, String order) throws SQLException
	{
		String BaojingInfo_Style = " and Module_Num='4000'";
		String strWhere = "";
		
		if(!"".equals(selectInfo) && selectInfo != null)
		{
			String[] info = selectInfo.split("\\|");
			if("all".equals(info[0]))
			{
				strWhere = "";
			}
			if("area".equals(info[0]))
			{
				strWhere = " and BAOJINGINFO_INDEX in (select ammeter_id from ammeter where Area_ID="+info[1]+")";	        
			}
			if("arch".equals(info[0]))
			{
				strWhere = " and BAOJINGINFO_INDEX in (select ammeter_id from ammeter where Architecture_ID="+info[1]+")";
			}
			if("floor".equals(info[0]))
			{
				strWhere = " and BAOJINGINFO_INDEX in (select ammeter_id from ammeter where Architecture_ID="+info[1]+" and Floor="+info[2]+")";
			}
			if("meter".equals(info[0]))
			{
				strWhere = " and BAOJINGINFO_INDEX="+info[1];
			}
		}
		
		BaoJingInfoModel model=null;
		ArrayList<BaoJingInfoModel> list=new ArrayList<>();
		String sql="select  BAOJINGINFO_ID ,to_char(BAOJINGINFO_TIME,'yyyy-mm-dd hh24:mi:ss')BAOJINGINFO_TIME,BAOJINGINFO_STYLE,BAOJINGINFO_FL,BAOJINGINFO_INDEX,BAOJINGINFO_TITLE,MODULE_NUM " +
				"from BaoJingInfo " +
				"where BAOJINGINFO_TIME >=to_date('"+startTime+"','yyyy-mm-dd') and BAOJINGINFO_TIME<=to_date('"+endTime+"','yyyy-mm-dd')+1";
		sql += strWhere + BaojingInfo_Style + order;
		
		
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

			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while(rs.next() && (count > 0))
			{
				model=new BaoJingInfoModel();
				model.setBAOJINGINFO_ID(rs.getInt("BAOJINGINFO_ID"));
				model.setBAOJINGINFO_TIME(rs.getString("BAOJINGINFO_TIME"));
				model.setBAOJINGINFO_STYLE(rs.getString("BAOJINGINFO_STYLE"));
				model.setBAOJINGINFO_FL(rs.getString("BAOJINGINFO_FL"));
				model.setBAOJINGINFO_INDEX(rs.getString("BAOJINGINFO_INDEX"));
				model.setBAOJINGINFO_TITLE(rs.getString("BAOJINGINFO_TITLE"));
				model.setMODULE_NUM(rs.getString("MODULE_NUM"));
				list.add(model);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}

}
