package com.sf.energy.prepayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sf.energy.powernet.model.BaoJingInfoModel;
import com.sf.energy.prepayment.model.AmMeterTuiFangModel;
import com.sf.energy.util.ConnDB;

public class AmMeterTuiFangDao
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
	
	public boolean deleteTuiFangInfoByID(String id) throws SQLException
	{
		String sql = "delete from Ammetertuifangdetail where TUIFANG_ID="+ id;
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
	
	public boolean insertTuiFangInfo(String AmMeter_ID, String detail, String tui_money){
		boolean b = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String ammeter_name = (new CMMeterDao()).getAmMeterNameById(AmMeter_ID);
		String sql = "insert into Ammetertuifangdetail(ammeter_id,ammeter_name,tuifang_time,tuifang_detail,tuifang_money) values("
					+ AmMeter_ID + ",'" + ammeter_name + "',to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),'"
					+ detail + "'," + tui_money
					+ ")";
		Connection conn = null;
		PreparedStatement ps = null;
		
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
	
	public ArrayList<AmMeterTuiFangModel> queryInfo(Integer pageCount, Integer pageIndex, String selectInfo, String startTime, String endTime, String order) throws SQLException
	{
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
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Area_ID="+info[1]+")";	        
			}
			if("arch".equals(info[0]))
			{
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Architecture_ID="+info[1]+")";
			}
			if("floor".equals(info[0]))
			{
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Architecture_ID="+info[1]+" and Floor="+info[2]+")";
			}
			if("meter".equals(info[0]))
			{
				strWhere = " and ammeter_id="+info[1];
			}
		}
		
		AmMeterTuiFangModel model=null;
		ArrayList<AmMeterTuiFangModel> list=new ArrayList<AmMeterTuiFangModel>();
		String sql="select  tuifang_id,ammeter_id,ammeter_name ,to_char(tuifang_time,'yyyy-mm-dd hh24:mi:ss')tuifang_time,tuifang_detail,tuifang_money " +
				"from Ammetertuifangdetail " +
				"where tuifang_time >=to_date('"+startTime+"','yyyy-mm-dd') and tuifang_time<=to_date('"+endTime+"','yyyy-mm-dd')+1";
		sql += strWhere + order;
		
		
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
				model=new AmMeterTuiFangModel();
				model.setTuiFang_ID(rs.getInt("tuifang_id"));
				model.setAmMeter_ID(rs.getInt("ammeter_id"));
				model.setAmMeter_Name(rs.getString("ammeter_name"));
				model.setTuiFang_Time(rs.getString("tuifang_time"));
				model.setTuiFang_Detail(rs.getString("tuifang_detail"));
				model.setTuiFang_Money(rs.getString("tuifang_money"));
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
	
	
	public ArrayList<AmMeterTuiFangModel> queryInfo_out( String selectInfo, String startTime, String endTime, String order) throws SQLException
	{
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
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Area_ID="+info[1]+")";	        
			}
			if("arch".equals(info[0]))
			{
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Architecture_ID="+info[1]+")";
			}
			if("floor".equals(info[0]))
			{
				strWhere = " and ammeter_id in (select ammeter_id from ammeter where Architecture_ID="+info[1]+" and Floor="+info[2]+")";
			}
			if("meter".equals(info[0]))
			{
				strWhere = " and ammeter_id="+info[1];
			}
		}
		
		AmMeterTuiFangModel model=null;
		ArrayList<AmMeterTuiFangModel> list=new ArrayList<AmMeterTuiFangModel>();
		String sql="select  tuifang_id,ammeter_id,ammeter_name ,to_char(tuifang_time,'yyyy-mm-dd hh24:mi:ss')tuifang_time,tuifang_detail,tuifang_money " +
				"from Ammetertuifangdetail " +
				"where tuifang_time >=to_date('"+startTime+"','yyyy-mm-dd') and tuifang_time<=to_date('"+endTime+"','yyyy-mm-dd')+1";
		sql += strWhere + order;
		
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();	

			while(rs.next())
			{
				model=new AmMeterTuiFangModel();
				model.setTuiFang_ID(rs.getInt("tuifang_id"));
				model.setAmMeter_ID(rs.getInt("ammeter_id"));
				model.setAmMeter_Name(rs.getString("ammeter_name"));
				model.setTuiFang_Time(rs.getString("tuifang_time"));
				model.setTuiFang_Detail(rs.getString("tuifang_detail"));
				model.setTuiFang_Money(rs.getString("tuifang_money"));
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
	

}
