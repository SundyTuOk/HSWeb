package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;

public class UseingEnergyWarningDao
{
	String AMBAOJING_ID = "";
	String AMMETER_NAME = "";
	String AMBAOJING_STYLE = "";
	String AMBAOJING_TIME = "";
	String AMBAOJING_REMARK = "";
	int total = 0;

	public List<UseingEnergyWarningDao> paginate(Integer pageCount,
			Integer pageIndex, int flag, int ID, int floorID) 
	{
		String condition="";
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
		
		
		ArrayList<UseingEnergyWarningDao> list = new ArrayList<UseingEnergyWarningDao>();
		String sql = "select AMBAOJING_ID,b.ammeter_name as AMMETER_NAME,(case AMBAOJING_STYLE when 1 then " +
				"'电能匹配模型' else '待机匹配模型' end)AMBAOJING_STYLE,(to_char(AMBAOJING_TIME,'yyyy-mm-dd hh24:mi:ss'))" +
				"AMBAOJING_TIME,AMBAOJING_REMARK from (ambaojing)a,(ammeter)b where b.ammeter_id=a.ammeter_id "+condition+" " +
						"order by AMBAOJING_TIME desc";
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				UseingEnergyWarningDao mi = new UseingEnergyWarningDao();
				mi.setAMBAOJING_ID(rs.getString("AMBAOJING_ID"));
				mi.setAMMETER_NAME(rs.getString("AMMETER_NAME"));
				mi.setAMBAOJING_STYLE(rs.getString("AMBAOJING_STYLE"));
				mi.setAMBAOJING_TIME(rs.getString("AMBAOJING_TIME"));
				mi.setAMBAOJING_REMARK(rs.getString("AMBAOJING_REMARK"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}

	public List<UseingEnergyWarningDao> checkByCondition(String style,
			String startTime, String endTime, Integer pageCount,
			Integer pageIndex, int flag, int ID, int floorID, String tableName, String order) 
	{
		
		String condition=" ";
		if(flag==1)
		{
			condition="and AREA_ID="+ID+" ";
		}
		if(flag==2)
		{
			if(ID!=-1){
				condition="and ARCHITECTURE_ID="+ID+" ";
			}
		}
		if(flag==3)
		{
			condition="and ARCHITECTURE_ID="+ID+" and FLOOR="+floorID+" ";
		}
		if(flag==4)
		{
			condition="and a.AMMETER_ID="+ID+" ";
		}
		
		ArrayList<UseingEnergyWarningDao> list = new ArrayList<UseingEnergyWarningDao>();
		String sql = "select AMBAOJING_ID,b.ammeter_name as AMMETER_NAME,(case AMBAOJING_STYLE when 1 then " +
				"'电能匹配模型' else '待机匹配模型' end)AMBAOJING_STYLE,(to_char(AMBAOJING_TIME,'yyyy-mm-dd hh24:mi:ss'))" +
				"AMBAOJING_TIME,AMBAOJING_REMARK from (ambaojing)a,(ammeter)b where b.ammeter_id=a.ammeter_id "+condition+" ";
		String SqlWhere = " and ";

		String sqlStyle = null;
		if (!"all".equals(style))
		{
			if ("03".equals(style))
				sqlStyle = "AMBAOJING_STYLE=2 ";
			else
				sqlStyle = "AMBAOJING_STYLE=1 ";
		}
		String sqlAMBAOJING_TIME=null;
		if(startTime!= null&&!"".equals(startTime) && endTime!= null&&!"".equals(endTime) )
            sqlAMBAOJING_TIME="AMBAOJING_TIME between to_date('"+startTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd')+1 ";
		
		if(sqlStyle!=null)
			SqlWhere+=sqlStyle;
		
		if(sqlAMBAOJING_TIME!=null)
		{
			if(sqlStyle!=null)
				SqlWhere+="and "+sqlAMBAOJING_TIME;
			else
				SqlWhere+=sqlAMBAOJING_TIME;
		}
		if(!" and ".equals(SqlWhere))
		   sql+=SqlWhere;
		sql+=" order by "+tableName+" "+order;
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=ConnDB.getConnection();
			ps= conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
				UseingEnergyWarningDao mi = new UseingEnergyWarningDao();
				mi.setAMBAOJING_ID(rs.getString("AMBAOJING_ID"));
				mi.setAMMETER_NAME(rs.getString("AMMETER_NAME"));
				mi.setAMBAOJING_STYLE(rs.getString("AMBAOJING_STYLE"));
				mi.setAMBAOJING_TIME(rs.getString("AMBAOJING_TIME"));
				mi.setAMBAOJING_REMARK(rs.getString("AMBAOJING_REMARK"));
				list.add(mi);
				count--;
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}

	public String getAMBAOJING_ID()
	{
		return AMBAOJING_ID;
	}

	public void setAMBAOJING_ID(String aMBAOJING_ID)
	{
		AMBAOJING_ID = aMBAOJING_ID;
	}

	public String getAMMETER_NAME()
	{
		return AMMETER_NAME;
	}

	public void setAMMETER_NAME(String aMMETER_NAME)
	{
		AMMETER_NAME = aMMETER_NAME;
	}

	public String getAMBAOJING_STYLE()
	{
		return AMBAOJING_STYLE;
	}

	public void setAMBAOJING_STYLE(String aMBAOJING_STYLE)
	{
		AMBAOJING_STYLE = aMBAOJING_STYLE;
	}

	public String getAMBAOJING_TIME()
	{
		return AMBAOJING_TIME;
	}

	public void setAMBAOJING_TIME(String aMBAOJING_TIME)
	{
		AMBAOJING_TIME = aMBAOJING_TIME;
	}

	public String getAMBAOJING_REMARK()
	{
		return AMBAOJING_REMARK;
	}

	public void setAMBAOJING_REMARK(String aMBAOJING_REMARK)
	{
		AMBAOJING_REMARK = aMBAOJING_REMARK;
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
