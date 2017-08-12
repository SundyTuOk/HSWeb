package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sf.energy.light.model.SLContrastModel;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.DateOperation;

public class SLLineDao
{
	/**
	 * 增
	 * @param sllm
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(SLLineModel sllm) throws SQLException
	{
		String sql = "INSERT INTO SLLINE(AREA_ID,GATHER_ID,SLKONGZHI_ID,SLKONGZHI_INDEX,AMMETER_ID,SLLINE_NUM,SLLINE_NAME,SLLINE_STAR,SLLINE_END)"
				+ " Values(?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sllm.getAREA_ID());
			ps.setInt(2, sllm.getGATHER_ID());
			ps.setInt(3, sllm.getSLKONGZHI_ID());
			ps.setInt(4, sllm.getSLKONGZHI_INDEX());
			ps.setInt(5, sllm.getAMMETER_ID());
			ps.setString(6, sllm.getSLLINE_NUM());
			ps.setString(7, sllm.getSLLINE_NAME());
			ps.setString(8, sllm.getSLLINE_STAR());
			ps.setString(9, sllm.getSLLINE_END());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		
		
		return b;
	}
	
	/**
	 * 删
	 * @param SLLINE_ID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int SLLINE_ID) throws SQLException
	{
		String sql = "delete FROM SLLINE where SLLINE_ID =  " + SLLINE_ID;
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
	
	/**
	 * 改
	 * @param sllm
	 * @return
	 * @throws SQLException
	 */
	public boolean modify(SLLineModel sllm) throws SQLException
	{
		String sql = "UPDATE SLLINE SET AREA_ID=?,GATHER_ID=?,SLKONGZHI_ID=?,SLKONGZHI_INDEX=?,AMMETER_ID=?," +
				"SLLINE_NUM=?,SLLINE_NAME=?,SLLINE_STAR=?,SLLINE_END=? where SLLINE_ID=" + sllm.getSLLINE_ID();
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sllm.getAREA_ID());
			ps.setInt(2, sllm.getGATHER_ID());
			ps.setInt(3, sllm.getSLKONGZHI_ID());
			ps.setInt(4, sllm.getSLKONGZHI_INDEX());
			ps.setInt(5, sllm.getAMMETER_ID());
			ps.setString(6, sllm.getSLLINE_NUM());
			ps.setString(7, sllm.getSLLINE_NAME());
			ps.setString(8, sllm.getSLLINE_STAR());
			ps.setString(9, sllm.getSLLINE_END());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	
		
		return b;
	}
	
	/**
	 * 根据ID查询
	 * @param SLLINE_ID
	 * @return
	 * @throws SQLException
	 */
	public SLLineModel queryByID(int SLLINE_ID) throws SQLException
	{
		SLLineModel sllm = new SLLineModel();
		String sql = "Select * from SLLINE where SLLINE_ID=" + SLLINE_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				sllm.setSLLINE_ID(SLLINE_ID);
				sllm.setAREA_ID(rs.getInt("AREA_ID"));
				sllm.setGATHER_ID(rs.getInt("GATHER_ID"));
				sllm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				sllm.setSLKONGZHI_INDEX(rs.getInt("SLKONGZHI_INDEX"));
				sllm.setAMMETER_ID(rs.getInt("AMMETER_ID"));
				sllm.setSLLINE_NUM(rs.getString("SLLINE_NUM"));
				sllm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				sllm.setSLLINE_STAR(rs.getString("SLLINE_STAR"));
				sllm.setSLLINE_END(rs.getString("SLLINE_END"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return sllm;
	}
	
	/**
	 * 根据AREA_ID查询
	 * @param AREA_ID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SLLineModel> queryByAreaID(int AREA_ID) throws SQLException
	{
		SLLineModel sllm = null;
		ArrayList<SLLineModel> list = new ArrayList<SLLineModel>();
		String sql = "Select * from SLLINE where AREA_ID=" + AREA_ID;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				sllm = new SLLineModel();
				sllm.setSLLINE_ID(rs.getInt("SLLINE_ID"));
				sllm.setAREA_ID(rs.getInt("AREA_ID"));
				sllm.setGATHER_ID(rs.getInt("GATHER_ID"));
				sllm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				sllm.setSLKONGZHI_INDEX(rs.getInt("SLKONGZHI_INDEX"));
				sllm.setAMMETER_ID(rs.getInt("AMMETER_ID"));
				sllm.setSLLINE_NUM(rs.getString("SLLINE_NUM"));
				sllm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				sllm.setSLLINE_STAR(rs.getString("SLLINE_STAR"));
				sllm.setSLLINE_END(rs.getString("SLLINE_END"));
				list.add(sllm);
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
	 * 查询所有
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SLLineModel> queryAll() throws SQLException
	{
		SLLineModel sllm = null;
		ArrayList<SLLineModel> list = new ArrayList<SLLineModel>();
		
		String sql = "Select * from SLLINE ORDER BY SLLINE_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				sllm = new SLLineModel();
				sllm.setSLLINE_ID(rs.getInt("SLLINE_ID"));
				sllm.setAREA_ID(rs.getInt("AREA_ID"));
				sllm.setGATHER_ID(rs.getInt("GATHER_ID"));
				sllm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				sllm.setSLKONGZHI_INDEX(rs.getInt("SLKONGZHI_INDEX"));
				sllm.setAMMETER_ID(rs.getInt("AMMETER_ID"));
				sllm.setSLLINE_NUM(rs.getString("SLLINE_NUM"));
				sllm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				sllm.setSLLINE_STAR(rs.getString("SLLINE_STAR"));
				sllm.setSLLINE_END(rs.getString("SLLINE_END"));
				list.add(sllm);
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
	 * 通过回路ID和指定年份，查找该年所有月份的电量
	 * @param SLLINE_ID		回路ID
	 * @param year			查询的年份
	 * @return				结果集
	 * @throws Exception
	 */
	public  ArrayList<SLContrastModel>  queryMonContrast(int SLLINE_ID,int year) throws Exception
	{
		SLContrastModel	slcm = null;
		float max = 0;
		float min = 0;
		String month = null;
		Date date = null;
		ArrayList<SLContrastModel> list = new ArrayList<SLContrastModel>();
		HashMap<String, SLContrastModel> map = new HashMap<String, SLContrastModel>();
		SimpleDateFormat df=new SimpleDateFormat("MM");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		String sql = "select ammeterdatas.AMMETER_ID,(max(ZVALUEZY))maxValue,(min(ZVALUEZY))minValue,max(SLLINE_NAME)SLLINE_NAME," +
				"(to_char(VALUETIME,'yyyy-MM'))meter_month  from ammeterdatas,SLLINE " +
				" where  ammeterdatas.AMMETER_ID = SLLINE.AMMETER_ID and SLLINE_ID = ?  " +
				"and extract(year from valuetime) = ? group by to_char(VALUETIME,'yyyy-MM'),ammeterdatas.AMMETER_ID" +
				" order by meter_month";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLLINE_ID);
			ps.setInt(2, year);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				slcm = new SLContrastModel();
				max = rs.getFloat("maxValue");
				min = rs.getFloat("minValue");
				month = rs.getString("meter_month");
				date=sdf.parse(month);
				//System.out.println(date);
				String mon=df.format(date);
				slcm.setTotalam(max-min);
				slcm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				slcm.setMonth(Integer.parseInt(mon));
				map.put(slcm.getMonth()+"", slcm);	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
		//匹配数据库中有无该月份数据
		for(int i = 1;i<=12;i++)
		{
			slcm = (SLContrastModel)map.get(i + "");
			if(map.get(i+"") == null)
			{
				slcm = new SLContrastModel();
				slcm.setTotalam(0);
				slcm.setMonth(i);			
			}
			list.add(slcm);
		}
		return list;	
	}
	
	/**
	 * 		通过区域ID,查询指定年月的该区域内路灯用电量
	 * @param areaID	区域ID
	 * @param year		年份
	 * @param month		月份
	 * @return			结果集
	 * @throws SQLException
	 */
	public SLContrastModel queryMonthAmByArea(int areaID,int year,int month) throws SQLException
	{
		SLContrastModel	slcm = null;
		String name = null;
		
		String sql = " select  sum(ZGROSS) ZGROSS ,sum(ZMONEY) ZMONEY from T_MONTHAM where " +
				" Ammeter_ID in (select Ammeter_ID from SLLINE where area_ID = ?) " +
				"and SELECTYEAR = ? and SELECTMONTH = ?";
		
		AreaDao  areaDao = new AreaDao();
		name = areaDao.queryNameById(areaID);		//通过区域ID查询区域名称
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			if(rs.next())
			{
				slcm = new SLContrastModel();
				slcm.setTotalam(rs.getFloat("ZGROSS"));
				slcm.setMoney(rs.getFloat("ZMONEY"));
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setAREA_NAME(name);
			}
			else
			{
				slcm = new SLContrastModel();
				slcm.setTotalam(0);
				slcm.setMoney(0);
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setAREA_NAME(name);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
		return slcm;
	}
	
	
	
//	/**
//	 * 		通过区域ID,查询指定年月日区域用电
//	 * @param areaID	区域ID
//	 * @param year		年份
//	 * @param month		月份
//	 * @param day		日期
//	 * @return			结果集
//	 * @throws SQLException
//	 */
//	public SLContrastModel queryDayAmByArea(int areaID,int year,int month,int day) throws SQLException
//	{
//		SLContrastModel	slcm = null;
//		String name = null;
//		
//		String sql = " select  sum(ZGROSS) ZGROSS from T_DAYAM " +
//				"where  Ammeter_ID in (select Ammeter_ID from SLLINE where area_ID = ?) " +
//				"and SELECTYEAR = ? and SELECTMONTH = ? and SELECTDAY = ?";
//		
//		ps.setInt(1, areaID);
//		ps.setInt(2, year);
//		ps.setInt(3, month);
//		ps.setInt(4, day);
//		
//		AreaDao  areaDao = new AreaDao();
//		name = areaDao.queryNameById(areaID);		//通过区域ID查询区域名称
//		
//		ResultSet	rs = ps.executeQuery();
//		if(rs.next())
//		{
//			slcm = new SLContrastModel();
//			slcm.setTotalam(rs.getFloat("ZGROSS"));
//			slcm.setYear(year);
//			slcm.setMonth(month);
//			slcm.setDay(day);
//			slcm.setAREA_NAME(name);
//		}
//		else
//		{
//			slcm = new SLContrastModel();
//			slcm.setTotalam(0);
//			slcm.setYear(year);
//			slcm.setMonth(month);
//			slcm.setDay(day);
//			slcm.setAREA_NAME(name);
//		}
//		
//		if(rs != null)
//		{
//			rs.close();
//		}
//		
//		if(ps != null)
//		{
//			ps.close();
//		}
//		
//		return slcm;
//	}
	
	
	/**
	 * 		通过回路ID和年月查询指定回路该年月里每一天的用电情况
	 * @param SLLine_ID			回路ID
	 * @param year				年份
	 * @param month				月份
	 * @return					结果集
	 * @throws SQLException
	 * @throws Exception 
	 */
	public ArrayList<SLContrastModel> queryDayAmByArea(int SLLine_ID,int year,int month) throws SQLException, Exception
	{
		ArrayList<SLContrastModel> list = new ArrayList<SLContrastModel>();
		List<String>	list_day = new ArrayList<String>();
		SLContrastModel	slcm = null;
		SLLineModel model = null;		//获取回路名称
		int MaxDay = 0;
		
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
		
		String sql = "select SLLine_ID as ID,SelectDay,ZGross from  (select SLLine_ID,SLLine_Name,AmMeter_ID from SLLine where AmMeter_ID<>0 and SLLine_ID=?) a ,(select AmMeter_ID,SelectDay,ZGross from T_DayAm where SelectYear=? and  SelectMonth=?) b where a.AmMeter_ID=b.AmMeter_ID";
		SLLineDao  Daysld = new SLLineDao();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, SLLine_ID);
			ps.setInt(2, year);
			ps.setInt(3, month);
							
			rs = ps.executeQuery();		
			while(rs.next())
			{	
				slcm = new SLContrastModel();
				model = Daysld.queryByID(SLLine_ID);
				slcm.setTotalam(rs.getFloat("ZGROSS"));
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setDay(rs.getInt("SelectDay"));
				slcm.setSLLINE_NAME(model.getSLLINE_NAME());
				list.add(slcm);
			}
			
			rs.beforeFirst();		//结果集返回到初始来判断
			if(!rs.next())
			{
				for(int i = 1;i<=MaxDay;i++)
				{
					slcm = new SLContrastModel();
					model = Daysld.queryByID(SLLine_ID);
					slcm.setTotalam(0);
					slcm.setYear(year);
					slcm.setMonth(month);
					slcm.setDay(i);
					slcm.setSLLINE_NAME(model.getSLLINE_NAME());
					list.add(slcm);
				}
			}	
			
			for(int i = 0;i<list.size();i++)
			{
				list_day.add(list.get(i).getDay()+"");
			}
			
		
			
			for(int i = 1;i<=MaxDay;i++)
			{
				
						if(!list_day.contains(i+""))
						{
							slcm = new SLContrastModel();
							model = Daysld.queryByID(SLLine_ID);
							slcm.setTotalam(0);
							slcm.setYear(year);
							slcm.setMonth(month);
							slcm.setDay(i);
							slcm.setSLLINE_NAME(model.getSLLINE_NAME());
							list.add(slcm);
						}		
				
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
	 * 		查询指定年月该月每一天所有区域的用电情况
	 * @param year		年份
	 * @param month		月份
	 * @return			结果集
	 * @throws SQLException
	 * @throws Exception 
	 */
	public List<SLContrastModel> queryDayAmAllArea(int area_id,int year,int month) throws SQLException, Exception
	{
		List<SLContrastModel> list = new ArrayList<SLContrastModel>();
		List<String>	list_day = new ArrayList<String>();
		SLContrastModel	slcm = null;
		String name = null;
		int MaxDay = 0;
		
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
		AreaDao  areaDao = new AreaDao();

		String sql = "select T.Area_ID as ID, T.SelectDay, sum (T.ZGross) as ZGross " +
				"from " +
				"(select a.Area_ID, a.AmMeter_ID, a.SLLine_Name,b.SelectDay, b.ZGross " +
				"from " +
				"( select Area_ID, SLLine_Name, AmMeter_ID from SLLine where AmMeter_ID <> 0 and Area_ID= ? ) a, " +
				"( select  AmMeter_ID, SelectDay, ZGross from T_DayAm  where  SelectYear = ?  and SelectMonth = ? )b " +
				"where a.AmMeter_ID = b.AmMeter_ID) T group by Area_ID, SelectDay ORDER BY selectDay";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			ps.setInt(1, area_id);
			ps.setInt(2, year);
			ps.setInt(3, month);
			
					//通过区域ID查询区域名称
			
			rs = ps.executeQuery();
			
		
				while(rs.next())
				{
		
					slcm = new SLContrastModel();
					name = areaDao.queryNameById(area_id);
					slcm.setTotalam(rs.getFloat("ZGROSS"));
					slcm.setYear(year);
					slcm.setMonth(month);
					slcm.setDay(rs.getInt("SelectDay"));
					slcm.setAREA_NAME(name);
					list.add(slcm);
				}
				rs.beforeFirst();		//结果集返回到初始来判断
				if(!rs.next())
				{
					for(int i = 1;i<=MaxDay;i++)
					{
						slcm = new SLContrastModel();
						name = areaDao.queryNameById(area_id);
						slcm.setTotalam(0);
						slcm.setYear(year);
						slcm.setMonth(month);
						slcm.setDay(i);
						slcm.setAREA_NAME(name);
						list.add(slcm);
					}
				}	
				
				
				for(int i = 0;i<list.size();i++)
				{
					list_day.add(list.get(i).getDay()+"");
				}
				
			
				
				for(int i = 1;i<=MaxDay;i++)
				{				
							if(!list_day.contains(i+""))
							{
								slcm = new SLContrastModel();
								name = areaDao.queryNameById(area_id);
								slcm.setTotalam(0);
								slcm.setYear(year);
								slcm.setMonth(month);
								slcm.setDay(i);
								slcm.setAREA_NAME(name);
								list.add(slcm);
							}	
					
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
	 * 		通过回路ID，查询指定年月里面该回路的详细信息
	 * @param SLLine_ID		回路ID
	 * @param year			年份
	 * @param month			月份
	 * @return				结果集
	 * @throws SQLException
	 */
	public 	SLContrastModel  queryMonthAmByhuilu(int SLLine_ID,int year,int month ) throws SQLException
	{
		SLContrastModel	slcm = null;
		SLLineModel sllm = null;	//获取回路名称
		sllm = queryByID(SLLine_ID);
		
		String sql = "select SLLINE_NAME, SLLINE.Ammeter_ID, ZGROSS ,ZMONEY " +
				"from T_MONTHAM,SLLINE where   SLLINE_ID  = ?  " +
				"and SELECTYEAR = ? and SELECTMONTH = ? " +
				"and SLLINE.Ammeter_ID = T_MONTHAM.Ammeter_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLLine_ID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				slcm = new SLContrastModel();
				slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
				slcm.setTotalam(rs.getFloat("ZGROSS"));
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setMoney(rs.getFloat("ZMONEY"));
			}
			else
			{
				slcm = new SLContrastModel();
				slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
				slcm.setTotalam(0);
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setMoney(0);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
		return slcm;
	}
	
	
	/**
	 * 		通过回路ID,查询指定年月日里面该回路的用电情况
	 * @param SLLine_ID			回路ID
	 * @param year				年份
	 * @param month				月份
	 * @param day				日
	 * @return					结果集
	 * @throws SQLException
	 */
	public 	SLContrastModel  queryDayAmByhuilu(int SLLine_ID,int year,int month,int day ) throws SQLException
	{
		SLContrastModel	slcm = null;
		SLLineModel sllm = null;	//获取回路名称
		sllm = queryByID(SLLine_ID);
		
		String sql = "select  SLLINE.Ammeter_ID, ZGROSS from T_DAYAM,SLLINE " +
				"where   SLLINE_ID  = ?  and SELECTYEAR = ? " +
				"and SELECTMONTH = ? and SELECTDAY = ? " +
				"and SLLINE.Ammeter_ID = T_DAYAM.Ammeter_ID";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLLine_ID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			ps.setInt(4, day);
			
			
			rs = ps.executeQuery();
			if(rs.next())
			{
				slcm = new SLContrastModel();
				slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
				slcm.setTotalam(rs.getFloat("ZGROSS"));
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setDay(day);
			}
			else
			{
				slcm = new SLContrastModel();
				slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
				slcm.setTotalam(0);
				slcm.setYear(year);
				slcm.setMonth(month);
				slcm.setDay(day);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return slcm;
	}
	
	
	public 	Map<String,String>  queryDayAmByhuiluInMonth(int SLLine_ID,int year,int month) throws SQLException, ParseException
	{
		SLContrastModel	slcm = null;
		SLLineModel sllm = null;	//获取回路名称
		sllm = queryByID(SLLine_ID);
		
		Map<String,String> map = new HashMap<String,String>();
		
		int MaxDay = 0;		
		DateOperation dateop = new DateOperation();
		MaxDay = dateop.queryMaxDayinMonth(year, month);	//查询月份有多少天
		
		for(int i=1;i<=MaxDay;i++)
		{
			map.put(i+"", 0+"");
		}
		
		String sql = "select  SLLINE.Ammeter_ID,SELECTDAY, ZGROSS from T_DAYAM,SLLINE " +
				"where   SLLINE_ID  = ?  and SELECTYEAR = ? " +
				"and SELECTMONTH = ?  " +
				"and SLLINE.Ammeter_ID = T_DAYAM.Ammeter_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLLine_ID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			while(rs.next())
			{
				for(String key :map.keySet())
				{
					if((rs.getInt("SELECTDAY")+"").equals(key))
					{
						map.put(key, rs.getFloat("ZGROSS")+"");
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		
//		if(rs.next())
//		{
//			slcm = new SLContrastModel();
//			slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
//			slcm.setTotalam(rs.getFloat("ZGROSS"));
//			slcm.setYear(year);
//			slcm.setMonth(month);
//			slcm.setDay(day);
//		}
//		else
//		{
//			slcm = new SLContrastModel();
//			slcm.setSLLINE_NAME(sllm.getSLLINE_NAME());
//			slcm.setTotalam(0);
//			slcm.setYear(year);
//			slcm.setMonth(month);
//			slcm.setDay(day);
//		}
		
		if(rs != null)
		{
			rs.close();
		}
		
		if(ps != null)
		{
			ps.close();
		}
		return map;
	}
	
	/**
	 * 通过回路ID和指定年月，查询指定年月每一天的电量
	 * @param SLLINE_ID		回路ID
	 * @param year			查询年份
	 * @param month			查询月份
	 * @return				结果集
	 * @throws Exception
	 */
	public  ArrayList<SLContrastModel>  queryDayContrast(int SLLINE_ID,int year,int month) throws Exception
	{
		SLContrastModel	slcm = null;
		float max = 0;
		float min = 0;
		String date = null;
		Date time = null;
		ArrayList<SLContrastModel> list = new ArrayList<SLContrastModel>();
		HashMap<String, SLContrastModel> 	map = new HashMap<String, SLContrastModel>();
		SimpleDateFormat df=new SimpleDateFormat("dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int lday = getLastDayOfMonth(year, month); 
		
		String sql = "select ammeterdatas.AMMETER_ID,(max(ZVALUEZY))maxValue,max(SLLINE_NAME)SLLINE_NAME," +
				"(min(ZVALUEZY))minValue,(to_char(VALUETIME,'yyyy-MM-dd'))meter_month  " +
				"from ammeterdatas,SLLINE  where  ammeterdatas.AMMETER_ID = SLLINE.AMMETER_ID " +
				"and SLLINE_ID = ?  and extract(year from valuetime) = ? and " +
				"extract(month from valuetime) = ? group by to_char(VALUETIME,'yyyy-MM-dd')," +
				"ammeterdatas.AMMETER_ID order by meter_month";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLLINE_ID);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			while(rs.next())
			{
				slcm = new SLContrastModel();
				max = rs.getFloat("maxValue");
				min = rs.getFloat("minValue");
				date = rs.getString("meter_month");
				time = sdf.parse(date);
				String day = df.format(time);
				slcm.setTotalam(max-min);
				slcm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				slcm.setDay(Integer.parseInt(day));
				map.put(slcm.getDay()+"", slcm);	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
//		ps.setInt(1, SLLINE_ID);
//		ps.setInt(2, year);
//		ps.setInt(3, month);
//		ResultSet	rs = ps.executeQuery();
//		while(rs.next())
//		{
//			slcm = new SLContrastModel();
//			max = rs.getFloat("maxValue");
//			min = rs.getFloat("minValue");
//			date = rs.getString("meter_month");
//			time = sdf.parse(date);
//			String day = df.format(time);
//			slcm.setTotalam(max-min);
//			slcm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
//			slcm.setDay(Integer.parseInt(day));
//			map.put(slcm.getDay()+"", slcm);	
//		}

		//匹配数据库中有无该天的数据
		for(int i=1;i<=lday;i++)
		{
			slcm = (SLContrastModel)map.get(i + "");
			if(map.get(i + "") == null){
				slcm = new SLContrastModel();
				slcm.setTotalam(0);
				slcm.setDay(i);
			}
			slcm.setMonth(month);		
			list.add(slcm);	
		}
		return list;	
	}

	
	/**
	 * 查询指定年月的天数
	 * @param year		年份
	 * @param month		月份
	 * @return			结果集
	 */
	public  int getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);      
       return cal.getActualMaximum(Calendar.DATE);
       
    }   
	
	public ArrayList<SLLineModel> queryByControllerID(int controllerID) throws SQLException
	{
		SLLineModel sllm = null;
		ArrayList<SLLineModel> list = new ArrayList<SLLineModel>();
		
		String sql = "Select * from SLLINE where SLKONGZHI_ID = "+controllerID+" ORDER BY SLLINE_ID";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next())
			{
				sllm = new SLLineModel();
				sllm.setSLLINE_ID(rs.getInt("SLLINE_ID"));
				sllm.setAREA_ID(rs.getInt("AREA_ID"));
				sllm.setGATHER_ID(rs.getInt("GATHER_ID"));
				sllm.setSLKONGZHI_ID(rs.getInt("SLKONGZHI_ID"));
				sllm.setSLKONGZHI_INDEX(rs.getInt("SLKONGZHI_INDEX"));
				sllm.setAMMETER_ID(rs.getInt("AMMETER_ID"));
				sllm.setSLLINE_NUM(rs.getString("SLLINE_NUM"));
				sllm.setSLLINE_NAME(rs.getString("SLLINE_NAME"));
				sllm.setSLLINE_STAR(rs.getString("SLLINE_STAR"));
				sllm.setSLLINE_END(rs.getString("SLLINE_END"));
				list.add(sllm);
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
