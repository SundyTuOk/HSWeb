package com.sf.energy.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.report.model.EnReportModel;
import com.sf.energy.util.ConnDB;

public class EnReportDataDao {
	EnReportDataModel enReportDataModel=null;
	List<EnReportDataModel> reportDataList=new ArrayList<EnReportDataModel>();
	/**
	 * 根据id查询EnReportData表里面的数据
	 * @param id
	 * @return 
	 * @throws SQLException
	 */
	public List<EnReportDataModel> queryEnReportData(int id) throws SQLException
	{
		String sql=null;
		if((id==0)||("".equals(id)))
		{
			 sql="select EnReportData_ID,EnReportData_Num,EnReportData_Name,EnReportData_Enable,EnReportData_Style,EnReportData_Text,EnReportData_Part,EnReportData_Remark from EnReportData";
		}
		else {
			sql="select EnReportData_ID,EnReportData_Num,EnReportData_Name,EnReportData_Enable,EnReportData_Style,EnReportData_Text,EnReportData_Part,EnReportData_Remark from EnReportData  where EnReportData_ID=" +id;
		} 
		
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next())
			{
				enReportDataModel=new EnReportDataModel();
				enReportDataModel.setEnReportData_id(rs.getInt(1));
				enReportDataModel.setEnReportData_num(rs.getString(2));
				enReportDataModel.setEnReportData_name(rs.getString(3));
				enReportDataModel.setEnReportData_enable(rs.getInt(4));
				enReportDataModel.setEnReportData_style(rs.getString(5));
				enReportDataModel.setEnReportData_text(rs.getString(6));
				enReportDataModel.setEnReportData_part(rs.getString(7));
				enReportDataModel.setEnReportData_remark(rs.getString(8));	
				reportDataList.add(enReportDataModel);
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			enReportDataModel=new EnReportDataModel();
			enReportDataModel.setEnReportData_id(rs.getInt(1));
			enReportDataModel.setEnReportData_num(rs.getString(2));
			enReportDataModel.setEnReportData_name(rs.getString(3));
			enReportDataModel.setEnReportData_enable(rs.getInt(4));
			enReportDataModel.setEnReportData_style(rs.getString(5));
			enReportDataModel.setEnReportData_text(rs.getString(6));
			enReportDataModel.setEnReportData_part(rs.getString(7));
			enReportDataModel.setEnReportData_remark(rs.getString(8));	
			reportDataList.add(enReportDataModel);
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return reportDataList;
	}
	
	/**
	 * 根据id更改EnReportData表中的数据
	 * @param id 要更改的记录的id
	 * @param enReportDataModel
	 * @return 是否更改成功的标示
	 * @throws SQLException
	 */
	
	public boolean updateEnReportData(int id,EnReportDataModel enReportDataModel) throws SQLException
	{
		String sql="update EnReportData set EnReportData_ID='"+enReportDataModel.getEnReportData_id()+"',EnReportData_Num='"+enReportDataModel.getEnReportData_num()
				  +"',EnReportData_Name='"+enReportDataModel.getEnReportData_name()+"',EnReportData_Enable='"+enReportDataModel.getEnReportData_enable()
				  +"',EnReportData_Style='"+enReportDataModel.getEnReportData_style()+"',EnReportData_Text='"+enReportDataModel.getEnReportData_text()
				  +"',EnReportData_Part='"+enReportDataModel.getEnReportData_part()+"',EnReportData_Remark='"+enReportDataModel.getEnReportData_remark()+"'where EnReportData_ID="+id;
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
		
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		boolean b=(ps.executeUpdate()== 1);
		if(ps!=null){
			ps.close();
		}*/
		return b;
	}
	
	/**
	 * 根据id删除EnReportData表里面的数据
	 * @param id 记录的id
	 * @return 是否删除成功的标示
	 * @throws SQLException
	 */
	public boolean deleteEnReportData(int id) throws SQLException
	{
		String sql="delete from EnReportData where EnReportData_ID="+id;
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
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		boolean b=(ps.executeUpdate()== 1);
		if(ps!=null){
			ps.close();
		}*/
		return b;
	}
	
	/**
	 * 增加EnReportData的数据
	 * @param enReportDataModel
	 * @return
	 * @throws SQLException
	 */
	public boolean addEnReportData(EnReportDataModel enReportDataModel) throws SQLException
	{
		String sql="insert into EnReportData(EnReportData_id,EnReportData_num,EnReportData_name,EnReportData_style,EnReportData_remark) values('"+enReportDataModel.getEnReportData_id()+"','"+enReportDataModel.getEnReportData_num()+"','"+enReportDataModel.getEnReportData_name()+"','"+enReportDataModel.getEnReportData_style()+"','"+enReportDataModel.getEnReportData_remark()+"')";
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		boolean b=(ps.executeUpdate()== 1);
		if(ps!=null){
			ps.close();
		}*/
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
	
	public int getEnReportDataId() throws SQLException
	{
		int id=0;
		String sql="select max(EnReportData_ID) Id from EnReportData";
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
			{
				id=rs.getInt("Id");
			}
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		/*PreparedStatement ps=ConnDB.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			id=rs.getInt("Id");
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return id+1;
	}
}



