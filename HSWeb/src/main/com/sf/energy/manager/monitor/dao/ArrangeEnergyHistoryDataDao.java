package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.util.ConnDB;

public class ArrangeEnergyHistoryDataDao
{
	String LogHistoryData_ID="";
	String Opr_Name="";
	String Style_Name="";
	String Exec_Log="";
	String Exec_Time="";
	String Star_Time="";
	String End_Time="";
	int  total=0;
	
	public List<ArrangeEnergyHistoryDataDao> paginate(Integer pageCount,
			Integer pageIndex,Integer type, String tableName, String order) 
	{
		String limit=null;
		if(type==1)
		{
			limit="where PlanTask_Style in('01','02','03','04') ";
		}
		else if(type==2)
		{
			limit="where PlanTask_Style in('11','12','13') ";
		}
		ArrayList<ArrangeEnergyHistoryDataDao> list=new ArrayList<ArrangeEnergyHistoryDataDao>();
		String sql = "select LogHistoryData_ID,PlanTask_Style," +
				"(select  DictionaryValue_Value from DictionaryValue where Dictionary_ID=24 and DictionaryValue_Num =PlanTask_Style)Style_Name," +
				"to_char(Exec_Time,'yyyy-mm-dd hh24:mi:ss')Exec_Time,Exec_Log,(to_char(Star_Time,'yyyy-mm-dd'))Star_Time," +
				"(to_char(End_Time,'yyyy-mm-dd'))End_Time,Exec_Opr," +
				"(case Exec_Opr when 0 then '计划任务' else (select Users_Name from Users where Users_ID=Exec_Opr) end)Opr_Name " +
				"from LogHistoryData "+limit+" ";
		sql+=" order by "+tableName+" "+order;
		PreparedStatement ps=null;
		Connection conn=null;
		ResultSet rs=null;
		
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			
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
			
			while(rs.next() && (count > 0))
			{
				ArrangeEnergyHistoryDataDao mi=new ArrangeEnergyHistoryDataDao();
				mi.setLogHistoryData_ID(rs.getString("LogHistoryData_ID"));
				mi.setOpr_Name(rs.getString("Opr_Name"));
				mi.setStyle_Name(rs.getString("Style_Name"));			
				mi.setExec_Log(rs.getString("Exec_Log"));
				mi.setExec_Time(rs.getString("Exec_Time"));
				mi.setStar_Time(rs.getString("Star_Time"));
				mi.setEnd_Time(rs.getString("End_Time"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return list;
	}
	
	//通过条件查询信息
	public List<ArrangeEnergyHistoryDataDao> checkByCondition(String operator,
			String style, String startTime, String endTime, Integer pageCount, Integer pageIndex,Integer type,String tableName,String order)
	{
		String limit=null;
		if(type==1)
		{
			limit="  PlanTask_Style in('01','02','03','04') ";
		}
		else if(type==2)
		{
			limit="  PlanTask_Style in('11','12','13') ";
		}
		ArrayList<ArrangeEnergyHistoryDataDao> list=new ArrayList<ArrangeEnergyHistoryDataDao>();
		String sql = "select LogHistoryData_ID,PlanTask_Style," +
				"(select  DictionaryValue_Value from DictionaryValue where Dictionary_ID=24 and DictionaryValue_Num =PlanTask_Style)Style_Name," +
				"to_char(Exec_Time,'yyyy-mm-dd hh24:mi:ss')Exec_Time,Exec_Log,(to_char(Star_Time,'yyyy-mm-dd'))Star_Time," +
				"(to_char(End_Time,'yyyy-mm-dd'))End_Time,Exec_Opr," +
				"(case Exec_Opr when 0 then '计划任务' else (select Users_Name from Users where Users_ID=Exec_Opr) end)Opr_Name " +
				"from LogHistoryData";
		String sqlWhere=" where ";
		
		String sqlEXEC_OPR=null;
		if("0".equals(operator))
		
		{
			sqlEXEC_OPR="EXEC_OPR=0 ";
		}
		else if("user".equals(operator))
		{
			sqlEXEC_OPR="EXEC_OPR<>0 ";
		}
		
		String sqlStyle=null;
		if(!"all".equals(style))
		{
			sqlStyle="PlanTask_Style='"+style+"' ";
		}
		
		String sqlExec_Time=null;
		if(!"".equals(startTime)&&startTime!=null&&!"".equals(endTime)&&endTime!=null)
           sqlExec_Time="Exec_Time between to_date('"+startTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd')+1 ";
        
        if(sqlEXEC_OPR!=null)
        	sqlWhere+=sqlEXEC_OPR;
        
		if(sqlStyle!=null)
		{
			if(sqlEXEC_OPR!=null)
				sqlWhere+="and "+sqlStyle;
			else
				sqlWhere+=sqlStyle;
				
		}
		if(sqlEXEC_OPR!=null || sqlStyle!=null)
		{
			if(sqlExec_Time!=null)
				sqlWhere+="and "+sqlExec_Time;
			
		}
		else
		{	
			if(sqlExec_Time!=null)
				sqlWhere+=sqlExec_Time;
		}
		if(!" where ".equals(sqlWhere))
			sql+=sqlWhere+" and "+limit;
		else
			sql+=sqlWhere+limit;
		sql+=" order by "+tableName+" "+order;
		PreparedStatement ps=null;
		Connection conn=null;
		ResultSet rs=null;
		
		try
		{
			conn=ConnDB.getConnection();
			ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			
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
			
			while(rs.next() && (count > 0))
			{
				ArrangeEnergyHistoryDataDao mi=new ArrangeEnergyHistoryDataDao();
				mi.setLogHistoryData_ID(rs.getString("LogHistoryData_ID"));
				mi.setOpr_Name(rs.getString("Opr_Name"));
				mi.setStyle_Name(rs.getString("Style_Name"));			
				mi.setExec_Log(rs.getString("Exec_Log"));
				mi.setExec_Time(rs.getString("Exec_Time"));
				mi.setStar_Time(rs.getString("Star_Time"));
				mi.setEnd_Time(rs.getString("End_Time"));
				list.add(mi);
				count--;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			ConnDB.release(conn, ps, rs);
		}
		return list;
	}
	
	
	public int getRecordCount() 
	{
		int count = -1;
		String sql = "SELECT count(*) as recordCount from LogHistoryData";
		PreparedStatement ps=null;
		Connection conn=null;
		ResultSet rs=null;
		
		try
		{
			conn= ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
				count = rs.getInt("recordCount");
				
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;

	}
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public String getLogHistoryData_ID()
	{
		return LogHistoryData_ID;
	}

	public void setLogHistoryData_ID(String logHistoryData_ID)
	{
		LogHistoryData_ID = logHistoryData_ID;
	}

	public String getOpr_Name()
	{
		return Opr_Name;
	}

	public void setOpr_Name(String opr_Name)
	{
		Opr_Name = opr_Name;
	}

	public String getStyle_Name()
	{
		return Style_Name;
	}

	public void setStyle_Name(String style_Name)
	{
		Style_Name = style_Name;
	}

	public String getExec_Log()
	{
		return Exec_Log;
	}

	public void setExec_Log(String exec_Log)
	{
		Exec_Log = exec_Log;
	}

	public String getExec_Time()
	{
		return Exec_Time;
	}

	public void setExec_Time(String exec_Time)
	{
		Exec_Time = exec_Time;
	}

	public String getStar_Time()
	{
		return Star_Time;
	}

	public void setStar_Time(String star_Time)
	{
		Star_Time = star_Time;
	}

	public String getEnd_Time()
	{
		return End_Time;
	}

	public void setEnd_Time(String end_Time)
	{
		End_Time = end_Time;
	}


	
	
	

}
