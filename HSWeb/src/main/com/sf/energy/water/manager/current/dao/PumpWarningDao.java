package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;
import com.sf.energy.water.manager.current.model.PumpWarningModel;

public class PumpWarningDao
{
	int total = 0;
	public List<PumpWarningModel> checkByCondition(String style,
			String startTime, String endTime, Integer pageCount,
			Integer pageIndex, int flag, int ID, String tableName, String order) throws SQLException
	{
		String condition=" ";
		if(flag==1)
		{
			condition="and b.AREA_ID="+ID+" ";
		}
		if(flag==2)
		{
			condition="and b.Pump_ID="+ID+" ";
		}
		
		ArrayList<PumpWarningModel> list = new ArrayList<PumpWarningModel>();
		String sql = "select PumpWarning_ID,b.Pump_name as PumpWarning_NAME,(case PumpWarning_STYLE when 1 then '低压报警' when 0 then '过压报警' end)PumpWarning_STYLE,"
				+ "(to_char(PumpWarning_TIME,'yyyy-mm-dd hh24:mi:ss'))PumpWarning_TIME,PumpWarning_REMARK from (PumpWarning)a,(Pump)b where b.pump_id=a.pump_id "+condition+" ";
		String SqlWhere = " and ";

		String sqlStyle = null;
		if (!"all".equals(style))
		{
			sqlStyle = "PumpWarning_STYLE="+style;
		}
		String sqlPumpWarning_TIME=null;
		if(startTime!= null&&!"".equals(startTime) && endTime!= null&&!"".equals(endTime) )
			sqlPumpWarning_TIME="PumpWarning_TIME between to_date('"+startTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd')+1 ";
		
		if(sqlStyle!=null)
			SqlWhere+=sqlStyle;
		
		if(sqlPumpWarning_TIME!=null)
		{
			if(sqlStyle!=null)
				SqlWhere+="and "+sqlPumpWarning_TIME;
			else
				SqlWhere+=sqlPumpWarning_TIME;
		}
		if(!" and ".equals(SqlWhere))
		   sql+=SqlWhere;
		sql+=" order by "+tableName+" "+order;
//		System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);

			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				PumpWarningModel mi = new PumpWarningModel();
				mi.setPumpWarning_ID(rs.getString("PumpWarning_ID"));
				mi.setPumpWarning_NAME(rs.getString("PumpWarning_NAME"));
				mi.setPumpWarning_STYLE(rs.getString("PumpWarning_STYLE"));
				mi.setPumpWarning_TIME(rs.getString("PumpWarning_TIME"));
				mi.setPumpWarning_REMARK(rs.getString("PumpWarning_REMARK"));
				list.add(mi);
				count--;
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		/*PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();
		rs.last();
		int count = rs.getRow();
		setTotal(count);

		if (count <= (pageCount * pageIndex))
			return list;

		count = count - pageCount * pageIndex;

		if (count >= pageCount)
			count = pageCount;

		if ((pageCount * pageIndex) == 0)
			rs.beforeFirst();
		else
			rs.absolute(pageCount * pageIndex);

		while (rs.next() && (count > 0))
		{
			PumpWarningModel mi = new PumpWarningModel();
			mi.setPumpWarning_ID(rs.getString("PumpWarning_ID"));
			mi.setPumpWarning_NAME(rs.getString("PumpWarning_NAME"));
			mi.setPumpWarning_STYLE(rs.getString("PumpWarning_STYLE"));
			mi.setPumpWarning_TIME(rs.getString("PumpWarning_TIME"));
			mi.setPumpWarning_REMARK(rs.getString("PumpWarning_REMARK"));
			list.add(mi);
			count--;
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();*/
		return list;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
