package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.powernet.model.BaoJingInfoModel;
import com.sf.energy.util.ConnDB;

public class BaoJingInfoDao
{
	int totalCount;
	/**
	 * 查
	 * @param order 
	 * @param tableName 列名称
	 * @param endTime 结束时间
	 * @param startTime 起始时间
	 * @param style   该设备属于那种类型，变压器还是回路还是什么
	 * @param partID  该设备的ID
	 * @param thePageIndex 
	 * @param thePageCount 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BaoJingInfoModel> queryInfo(Integer pageCount, Integer pageIndex, String partID, String style, String startTime, String endTime, String tableName, String order) throws SQLException
	{
		String BaojingInfo_Style = " and Module_Num='D000'";
		String strWhere = "";
		if("01".equals(style))
		{
			strWhere = " and  BaojingInfo_Index in(select Part_ID from (PD_Part)m start with m.Part_ID="+partID+" connect by m.PART_PARENT=prior m.Part_ID) ";
		}
		if("03".equals(style))
		{
			strWhere = " and  BaojingInfo_Index ="+partID;
		}
		
		BaoJingInfoModel model=null;
		ArrayList<BaoJingInfoModel> list=new ArrayList<>();
		String sql="select  BAOJINGINFO_ID,PD_PART.PART_NAME ,to_char(BAOJINGINFO_TIME,'yyyy-mm-dd hh24:mi:ss')BAOJINGINFO_TIME,BAOJINGINFO_STYLE,BAOJINGINFO_FL,BAOJINGINFO_INDEX,BAOJINGINFO_TITLE,MODULE_NUM " +
				"from BaoJingInfo ,PD_PART " +
				"where PD_PART.PART_ID=BAOJINGINFO.BAOJINGINFO_INDEX and BAOJINGINFO_TIME >=to_date('"+startTime+"','yyyy-mm-dd') and BAOJINGINFO_TIME<=to_date('"+endTime+"','yyyy-mm-dd')+1";
		sql+=strWhere+BaojingInfo_Style;
		
		sql+=" order by "+tableName+" "+order;
		
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
			
			while(rs.next())
			{
				model=new BaoJingInfoModel();
				model.setBAOJINGINFO_NAME(rs.getString("PART_NAME"));
				model.setBAOJINGINFO_ID(rs.getInt("BAOJINGINFO_ID"));
				model.setBAOJINGINFO_TIME(rs.getString("BAOJINGINFO_TIME"));
				model.setBAOJINGINFO_STYLE(rs.getString("BAOJINGINFO_STYLE"));
				model.setBAOJINGINFO_FL(rs.getString("BAOJINGINFO_FL"));
				model.setBAOJINGINFO_INDEX(rs.getString("BAOJINGINFO_INDEX"));
				model.setBAOJINGINFO_TITLE(rs.getString("BAOJINGINFO_TITLE"));
				model.setMODULE_NUM(rs.getString("MODULE_NUM"));
				list.add(model);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		

		
		return list;
	}
	
	
	/**
	 * 删
	 * 
	 * @param scheduleID
	 *            计划ID
	 * @return 
	 * @throws SQLException
	 */
	public boolean deleteSLPlanByID(String id) throws SQLException
	{
		String sql = "delete from BaoJingInfo where BAOJINGINFO_ID="+ id;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			int aa=ps.executeUpdate();
			if (ps != null)
				ps.close();
			if(aa==1)
				return true;
			else
				return false;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}finally{
			ConnDB.release(conn, ps);
		}

	}


	public int getTotalCount()
	{
		return totalCount;
	}


	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

}
