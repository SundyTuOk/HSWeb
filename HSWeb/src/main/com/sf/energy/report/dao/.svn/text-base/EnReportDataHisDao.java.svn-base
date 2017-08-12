package com.sf.energy.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sf.energy.report.model.EnReportDataHisModel;
import com.sf.energy.report.model.EnReportDataModel;
import com.sf.energy.util.ConnDB;

public class EnReportDataHisDao {
	
	//得到总记录
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
	 * 查询能耗上报历史记录
	 * @return
	 * @throws SQLException
	 */
	public List<EnReportDataHisModel> queryEnReportDataHis(String enHistorysortName,String enHistoryorder,int pageCount,int pageIndex) throws SQLException
	{
		EnReportDataHisModel enReportDataHisModel=null;
		List<EnReportDataHisModel> list=new ArrayList<EnReportDataHisModel>();
		String sql="select a.id,a.EnReportData_ID,b.EnReportData_Num,b.EnReportData_Name,to_char(a.ValueTime,'yyyy-mm-dd') ValueTime,a.Style,a.Result,a.Remark,a.EnReportData_Text,a.EnReportData_Part from EnReportDataHis a,EnReportData b where a.EnReportData_ID=b.EnReportData_ID order by "+enHistorysortName+" "+enHistoryorder; 
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			//得到总记录数
	        rs.last();
			int count = rs.getRow();
			setTotalCount(count);
			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
				rs.absolute(1);
				rs.previous();
			} else
				rs.absolute(pageCount * pageIndex);
	        
	        while(rs.next() && (count > 0))						
			{   
				enReportDataHisModel=new EnReportDataHisModel();
				enReportDataHisModel.setId(rs.getInt("id"));
				enReportDataHisModel.setEnReportData_id(rs.getInt("EnReportData_ID"));
				enReportDataHisModel.setEnReportData_num(rs.getString("EnReportData_Num"));
				enReportDataHisModel.setEnReportData_name(rs.getString("EnReportData_Name"));
				enReportDataHisModel.setValueTime(rs.getString("ValueTime"));
				enReportDataHisModel.setStyle(rs.getString("Style"));
				enReportDataHisModel.setResult(rs.getInt("Result"));
				enReportDataHisModel.setRemark(rs.getString("Remark"));
				enReportDataHisModel.setEnReportData_Text(rs.getString("EnReportData_Text"));
				enReportDataHisModel.setEnReportData_Part(rs.getString("EnReportData_Part"));
				list.add(enReportDataHisModel);
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
		
		//得到总记录数
        rs.last();
		int count = rs.getRow();
		setTotalCount(count);
		if (count <= (pageCount * pageIndex))
			return list;

		count = count - pageCount * pageIndex;

		if (count >= pageCount)
			count = pageCount;

		if ((pageCount * pageIndex) == 0)
		{
			rs.absolute(1);
			rs.previous();
		} else
			rs.absolute(pageCount * pageIndex);
        
        while(rs.next() && (count > 0))						
		{   
			enReportDataHisModel=new EnReportDataHisModel();
			enReportDataHisModel.setId(rs.getInt("id"));
			enReportDataHisModel.setEnReportData_id(rs.getInt("EnReportData_ID"));
			enReportDataHisModel.setEnReportData_num(rs.getString("EnReportData_Num"));
			enReportDataHisModel.setEnReportData_name(rs.getString("EnReportData_Name"));
			enReportDataHisModel.setValueTime(rs.getString("ValueTime"));
			enReportDataHisModel.setStyle(rs.getString("Style"));
			enReportDataHisModel.setResult(rs.getInt("Result"));
			enReportDataHisModel.setRemark(rs.getString("Remark"));
			enReportDataHisModel.setEnReportData_Text(rs.getString("EnReportData_Text"));
			enReportDataHisModel.setEnReportData_Part(rs.getString("EnReportData_Part"));
			list.add(enReportDataHisModel);
			count--;
		}
		if(rs!=null)
			rs.close();
		
		if(ps!=null)
			ps.close();*/
		return list;
	}
	
	/**
	 * 删除历史记录数据
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEnReportDataHis(int id) throws SQLException
	{
		String sql="delete from EnReportDataHis where id="+id;
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
	 * 能耗上报后将记录上传
	 * @param enReportDataHisModel
	 * @return
	 * @throws SQLException
	 */
	public boolean addEnReportDataHis(EnReportDataHisModel enReportDataHisModel) throws SQLException
	{
		String ValueTime=enReportDataHisModel.getValueTime();
		
		
		String sql="insert into EnReportDataHis(id,EnReportData_id,ValueTime,EnReportData_Text,EnReportData_Part,Style,Result,Remark)  values('"+enReportDataHisModel.getId()+"','"+enReportDataHisModel.getEnReportData_id()+
				"',to_date('"+ValueTime+"','yyyy-mm-dd'),'"+enReportDataHisModel.getEnReportData_Text()+
				"','"+enReportDataHisModel.getEnReportData_Part()+"','"+enReportDataHisModel.getStyle()+
				"',"+enReportDataHisModel.getResult()+",'"+enReportDataHisModel.getRemark()+"')";
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
	 * 修改
	 * @param enReportDataModel
	 * @return
	 * @throws SQLException
	 */
	public boolean editDataManager(EnReportDataHisModel enReportDataHisModel) throws SQLException
	{
		String sql="update EnReportDataHis set Result='"+enReportDataHisModel.getResult()
				  +"',ValueTime=to_date('"+enReportDataHisModel.getValueTime()+"','yyyy-mm-dd') where id="+enReportDataHisModel.getId();
		
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
	 * 得到历史记录的ID
	 * @return
	 * @throws SQLException 
	 */
	public int getEnReportDataHisId() throws SQLException
	{
		int id=0;
		String sql="select max(id) Id from EnReportDataHis";
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
