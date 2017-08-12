package com.sf.energy.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.report.daoImp.ArcSampleDaoImp;
import com.sf.energy.report.model.ArcSampleModel;
import com.sf.energy.util.ConnDB;

public class ArcSampleDao implements ArcSampleDaoImp{
	ArcSampleModel arcSampleModel=null;
	
	
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
	 * 根据查询的条件获取所需的建筑  -1代表所有
	 * @return
	 * @throws SQLException 
	 */
	public List<ArcSampleModel> queryArcSample(String ArcSamplesortName,String ArcSampleorder,String archstyleid,String archid,int pageCount,int pageIndex) throws SQLException
	{
		List<ArcSampleModel> list=new ArrayList<ArcSampleModel>();
		String strWhere="";
		//archid=-1代表全校,都为0时表示没有选中数，默认也是全校
		if("-1".equals(archid) || ("0".equals(archid)&&"0".equals(archstyleid))){
			strWhere="";
		}
		//
		if(!("-1".equals(archid)) && !("0".equals(archid)) && "0".equals(archstyleid))
		{
			strWhere=" and ARCHITECTURE_ID=" + archid + "";
			
		}
		if(!("0".equals(archstyleid)) && "0".equals(archid))
		{
			strWhere+=" and Architecture_Style='" + archstyleid + "'";
		}
		
		String sql="select Architecture_ID,Area_ID,Architecture_Num,Architecture_Name," +
				"nvl((select DictionaryValue_Value from DictionaryValue where DictionaryValue_Num=" +
				"Architecture_Style and DICTIONARY_ID='103'),' ') ArchitectureStyle,nvl(Architecture_Man,' ') Architecture_Man," +
				"nvl(Architecture_Phone,' ') Architecture_Phone,isEnReport from Architecture where 1=1"+strWhere+" order by "+ArcSamplesortName+" "+ArcSampleorder;
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		try
		{
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
	        	
				arcSampleModel=new ArcSampleModel();
				arcSampleModel.setArchitecture_ID(rs.getInt("Architecture_ID"));
				arcSampleModel.setArea_ID(rs.getInt("Area_ID"));
				arcSampleModel.setArchitecture_Num(rs.getString("Architecture_Num"));
				arcSampleModel.setArchitecture_Name(rs.getString("Architecture_Name"));
				arcSampleModel.setArchitecture_Style(rs.getString("ArchitectureStyle"));
				arcSampleModel.setArchitecture_Man(rs.getString("Architecture_Man"));
				arcSampleModel.setArchitecture_Phone(rs.getString("Architecture_Phone"));
				arcSampleModel.setIsEnReport(rs.getInt("isEnReport"));
				list.add(arcSampleModel);
				count--;
			}
		
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps,rs);
		}
		
		return list;
	
	
	
	}
	/**
	 * 保存被抽样的建筑
	 * @throws SQLException 
	 */
	public boolean checkArcSample(String id,String isEnReport) throws SQLException
	{
		String sql="update Architecture set isEnReport='"+isEnReport+"'where Architecture_ID='"+id+"'";
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
	 * 根据复选框的选中状态保存是否为样本
	 * @return
	 */

	
}
