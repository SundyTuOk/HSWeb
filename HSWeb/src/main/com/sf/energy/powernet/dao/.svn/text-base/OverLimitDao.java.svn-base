package com.sf.energy.powernet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.powernet.model.OverLimitModel;
import com.sf.energy.util.ConnDB;

public class OverLimitDao
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
	
	public ArrayList<OverLimitModel> queryOverLimitInfo(int pageCount, int pageIndex, int partID, String queryMsg, String orderInfo) throws SQLException
	{
		ArrayList<OverLimitModel> list = new ArrayList<OverLimitModel>();
		OverLimitModel olm = null;
		
		String sql = "";
		String strWhere = "";
		
		String OverStyle = "";
		String strStart = "";
		String strEnd = "";
		
		if(partID != 0)
		{
			strWhere = " where Part_ID in (SELECT part_id FROM pd_part START WITH part_id = " + partID + " CONNECT BY PRIOR part_id = part_parent)";
		}
		
		if(queryMsg != null && !"".equals(queryMsg))
		{
			String[] msg = queryMsg.split("\\|");
			if(!"0".equals(msg[0]))
			{
				OverStyle = " and OverStyle=" + msg[0];
			}
			strStart = msg[1];
			strEnd = msg[2];
		}
		
		sql = "select "+
				"a.*,"+
				"(select Part_Name from (PD_Part)b where a.Part_ID=b.Part_ID)Part_Name,"+
				"(CValue*100/XValue)PerValue,"+
				"(select DictionaryValue_Value from DictionaryValue where OverStyle=DictionaryValue_Num and Dictionary_ID=29 )as  OverStyleName "+
			"from "+
			"("+
				"select "+
					"PD_OverID,Part_ID,"+
					"to_char(ValueTime,'yyyy-mm-dd hh24:mi:ss')ValueTime,"+
					"OverStyle,ZValue,XValue,abs(ZValue-XValue)CValue,Remark "+
				"from "+
					"PD_Over "+
				"where  "+
					"XValue<>0 "+
					"and ValueTime>=to_date('"+strStart+"','yyyy-mm-dd')"+
					"and ValueTime<=(to_date('"+strEnd+"','yyyy-mm-dd')+1) "+OverStyle+
			")A ";
		sql+=strWhere+orderInfo;
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
					
			while(rs.next()&&(count > 0))
			{
				olm = new OverLimitModel();
				olm.setPD_OverID(rs.getString("PD_OverID"));
				olm.setValueTime(rs.getString("ValueTime"));
				olm.setPart_Name(rs.getString("Part_Name"));
				olm.setOverStyleName(rs.getString("OverStyleName"));
				olm.setZValue(rs.getString("ZValue"));
				olm.setXValue(rs.getString("XValue"));
				olm.setCValue(rs.getString("CValue"));
				olm.setPerValue(rs.getString("PerValue"));
				olm.setRemark(rs.getString("Remark"));
				
				list.add(olm);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
//				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs = null;
//		rs = ps.executeQuery();
//
//		rs.last();
//		int count = rs.getRow();
//		setTotalCount(count);
//		if (count <= (pageCount * pageIndex))
//			return list;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//				
//		while(rs.next()&&(count > 0))
//		{
//			olm = new OverLimitModel();
//			olm.setPD_OverID(rs.getString("PD_OverID"));
//			olm.setValueTime(rs.getString("ValueTime"));
//			olm.setPart_Name(rs.getString("Part_Name"));
//			olm.setOverStyleName(rs.getString("OverStyleName"));
//			olm.setZValue(rs.getString("ZValue"));
//			olm.setXValue(rs.getString("XValue"));
//			olm.setCValue(rs.getString("CValue"));
//			olm.setPerValue(rs.getString("PerValue"));
//			olm.setRemark(rs.getString("Remark"));
//			
//			list.add(olm);
//			count--;
//		}
		
		return list;
		
	}
	
	/**
	 * 删
	 * @param PD_OverID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int PD_OverID) throws SQLException
	{
		String sql = "delete FROM PD_OVER where PD_OverID =  " + PD_OverID;
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return b;
	}

}
