package com.sf.energy.water.manager.current.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.manager.monitor.dao.UseingEnergyWarningDao;
import com.sf.energy.util.ConnDB;

public class UseingWaterWarningDao
{
	String WATERBAOJING_ID = "";
	String WATerMETER_NAME = "";
	String WATERBAOJING_STYLE = "";
	String WATERBAOJING_TIME = "";
	String WATERBAOJING_REMARK = "";
	int total = 0;

	public List<UseingWaterWarningDao> paginate(Integer pageCount,
			Integer pageIndex, int flag, int ID, int floorID, String tableName, String order) throws SQLException
	{
		String condition=" ";
		if(flag==1)
		{
			condition="and AREA_ID="+ID+" ";
		}
		if(flag==2)
		{
			condition="and ARCHITECTURE_ID="+ID+" ";
		}
		if(flag==3)
		{
			condition="and ARCHITECTURE_ID="+ID+" and FLOOR="+floorID+" ";
		}
		if(flag==4)
		{
			condition="and a.AMMETER_ID="+ID+" ";
		}
		
		ArrayList<UseingWaterWarningDao> list = new ArrayList<UseingWaterWarningDao>();
		String sql = "select WATERBAOJING_ID,b.watermeter_name as WATerMETER_NAME,"
				+ "(case WATERBAOJING_STYLE when 1 then '水能匹配模型' end)WATERBAOJING_STYLE,"
				+ "(to_char(WATERBAOJING_TIME,'yyyy-mm-dd hh24:mi:ss'))WATERBAOJING_TIME,WATERBAOJING_REMARK from (WATERBAOJING)a,(watermeter)b where watermeter_id=a.watermeter_id  ";
		sql+=" order by "+tableName+" "+order;
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
				UseingWaterWarningDao mi = new UseingWaterWarningDao();
				mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_ID"));
				mi.setWATerMETER_NAME(rs.getString("WATerMETER_NAME"));
				mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_STYLE"));
				mi.setWATERBAOJING_TIME(rs.getString("WATERBAOJING_TIME"));
				mi.setWATERBAOJING_REMARK(rs.getString("WATERBAOJING_REMARK"));
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
		/*
		PreparedStatement ps = ConnDB.getConnection().prepareStatement(sql,
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
			UseingWaterWarningDao mi = new UseingWaterWarningDao();
			mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_ID"));
			mi.setWATerMETER_NAME(rs.getString("WATerMETER_NAME"));
			mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_STYLE"));
			mi.setWATERBAOJING_TIME(rs.getString("WATERBAOJING_TIME"));
			mi.setWATERBAOJING_REMARK(rs.getString("WATERBAOJING_REMARK"));
			list.add(mi);
			count--;
		}
		
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();*/
		return list;
	}
	
	public List<UseingWaterWarningDao> checkByCondition(String style,
			String startTime, String endTime, Integer pageCount,
			Integer pageIndex, int flag, int ID, int floorID, String tableName, String order) throws SQLException
	{
		String condition=" ";
		if(flag==1)
		{
			condition="and AREA_ID="+ID+" ";
		}
		if(flag==2)
		{
			condition="and ARCHITECTURE_ID="+ID+" ";
		}
		if(flag==3)
		{
			condition="and ARCHITECTURE_ID="+ID+" and FLOOR="+floorID+" ";
		}
		if(flag==4)
		{
			condition="and a.watermeter_id="+ID+" ";
		}
		
		ArrayList<UseingWaterWarningDao> list = new ArrayList<UseingWaterWarningDao>();
		String sql = "select WATERBAOJING_ID,b.watermeter_name as WATerMETER_NAME,(case WATERBAOJING_STYLE when 1 then '水能匹配模型' end)WATERBAOJING_STYLE,"
				+ "(to_char(WATERBAOJING_TIME,'yyyy-mm-dd hh24:mi:ss'))WATERBAOJING_TIME,WATERBAOJING_REMARK from (WATERBAOJING)a,(watermeter)b where b.watermeter_id=a.watermeter_id "+condition+" ";
		String SqlWhere = " and ";

		String sqlStyle = null;
		if (!"all".equals(style))
		{
			sqlStyle = "WATERBAOJING_STYLE=1 ";
		}
		String sqlWATERBAOJING_TIME=null;
		if(startTime!= null&&!"".equals(startTime) && endTime!= null&&!"".equals(endTime) )
			sqlWATERBAOJING_TIME="WATERBAOJING_TIME between to_date('"+startTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd')+1 ";
		
		if(sqlStyle!=null)
			SqlWhere+=sqlStyle;
		
		if(sqlWATERBAOJING_TIME!=null)
		{
			if(sqlStyle!=null)
				SqlWhere+="and "+sqlWATERBAOJING_TIME;
			else
				SqlWhere+=sqlWATERBAOJING_TIME;
		}
		if(!" and ".equals(SqlWhere))
		   sql+=SqlWhere;
		sql+=" order by "+tableName+" "+order;
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
				UseingWaterWarningDao mi = new UseingWaterWarningDao();
				mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_ID"));
				mi.setWATerMETER_NAME(rs.getString("WATerMETER_NAME"));
				mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_STYLE"));
				mi.setWATERBAOJING_TIME(rs.getString("WATERBAOJING_TIME"));
				mi.setWATERBAOJING_REMARK(rs.getString("WATERBAOJING_REMARK"));
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
			UseingWaterWarningDao mi = new UseingWaterWarningDao();
			mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_ID"));
			mi.setWATerMETER_NAME(rs.getString("WATerMETER_NAME"));
			mi.setWATERBAOJING_STYLE(rs.getString("WATERBAOJING_STYLE"));
			mi.setWATERBAOJING_TIME(rs.getString("WATERBAOJING_TIME"));
			mi.setWATERBAOJING_REMARK(rs.getString("WATERBAOJING_REMARK"));
			list.add(mi);
			count--;
		}
		if(rs!=null)
			rs.close();
		if(ps!=null)
			ps.close();*/
		return list;
	}

	public String getWATERBAOJING_ID()
	{
		return WATERBAOJING_ID;
	}

	public void setWATERBAOJING_ID(String wATERBAOJING_ID)
	{
		WATERBAOJING_ID = wATERBAOJING_ID;
	}

	public String getWATerMETER_NAME()
	{
		return WATerMETER_NAME;
	}

	public void setWATerMETER_NAME(String wATerMETER_NAME)
	{
		WATerMETER_NAME = wATerMETER_NAME;
	}

	public String getWATERBAOJING_STYLE()
	{
		return WATERBAOJING_STYLE;
	}

	public void setWATERBAOJING_STYLE(String wATERBAOJING_STYLE)
	{
		WATERBAOJING_STYLE = wATERBAOJING_STYLE;
	}

	public String getWATERBAOJING_TIME()
	{
		return WATERBAOJING_TIME;
	}

	public void setWATERBAOJING_TIME(String wATERBAOJING_TIME)
	{
		WATERBAOJING_TIME = wATERBAOJING_TIME;
	}

	public String getWATERBAOJING_REMARK()
	{
		return WATERBAOJING_REMARK;
	}

	public void setWATERBAOJING_REMARK(String wATERBAOJING_REMARK)
	{
		WATERBAOJING_REMARK = wATERBAOJING_REMARK;
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
