package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sf.energy.expert.forecast.ForecastByManArea;
import com.sf.energy.expert.model.ForecastModel;
import com.sf.energy.util.ConnDB;

/**
 * 能源预测相关模块的数据库操作方法
 * @author yanhao
 *
 */
public class ForecastDao
{
	//所有建筑类别的总人口，总面积
	public Map<String,ForecastModel> getAllArchStyleInfo() throws SQLException
	{
		ForecastModel model=null;
		Map<String,ForecastModel> map=new HashMap<String,ForecastModel>();
		String sql="select "
				+"		StyleNum as ID, "
				+"		StyleName as Name, "
				+"		nvl(Architecture_Area,0) as CArea , "
				+"		nvl(count_man,0) as CMan "
				+"	from ( "
				+"			select  "
				+"			DictionaryValue_Num as StyleNum, "
				+"			DictionaryValue_Value as StyleName  "
				+"			from DictionaryValue  "
				+"			where Dictionary_ID=103 "
				+"			)a  "
				+"			left join  "
				+"			( "
				+"					select  "
				+"					Architecture_Style, "
				+"					sum(Architecture_Area) Architecture_Area , "
				+"					sum(Count_Man) Count_Man "
				+"					from Architecture group by Architecture_Style "
				+"			)b  "
				+"			on a.StyleNum=b.Architecture_Style";
		
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
				model=new ForecastModel();
				model.setStyleID(rs.getString("ID"));
				model.setStyleName(rs.getString("Name"));
				model.setcArea(rs.getDouble("CArea"));
				model.setcMan(rs.getDouble("CMan"));
				
				map.put(model.getStyleID(), model);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		
		return map;
	}
	
	
		//获取指定建筑类别的所有建筑总人口，总面积
		public Map<String,ForecastModel> getAllArchInfoByStyle(String style) throws SQLException
		{
			ForecastModel model=null;
			Map<String,ForecastModel> map=new HashMap<String,ForecastModel>();
			String sql="select "
					+"		Architecture_ID as ID, "
					+"		Architecture_name as Name, "
					+"		nvl(Architecture_Area,0) as CArea, "
					+"		nvl(Count_Man,0) as CMan "
					+"	from  "
					+"		Architecture  "
					+"	where  "
					+"		Architecture_Style='"+style+"' ";
			
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
					model=new ForecastModel();
					model.setStyleID(rs.getString("ID"));
					model.setStyleName(rs.getString("Name"));
					model.setcArea(rs.getDouble("CArea"));
					model.setcMan(rs.getDouble("CMan"));
					
					map.put(model.getStyleID(), model);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
			
			return map;
		}
	
		
	/**
	 * 1全校、 2建筑类别、3建筑、4区域、5部门的12个月的能源预测
	 * @param styleName 分项名
	 * @param selectYear 查询的年，默认当前年的前一年
	 * @param energyFlag 判断是1用电、2用水,3代表没有分项的总用电、4没有分项的总用水
	 * @param targetFlag 判断是1全校、 2建筑类别、3建筑、4区域、5部门
	 * @param targetID 1全校、 2建筑类别、3建筑、4区域、5部门 的标识，有时候是int,有时候是String
	 * @return list.size()=12
	 * @throws SQLException
	 */
	public ArrayList<ForecastModel> getAllSchool12MonthInfo(String fenxiangName,int selectYear,int energyFlag,int targetFlag,String targetID) throws SQLException
	{
		ArrayList<ForecastModel> list=new ArrayList<ForecastModel>();
		InformationDao info=new InformationDao();
		ForecastByManArea forecast=new ForecastByManArea();
		ForecastModel model=null;
		DecimalFormat df = new DecimalFormat("#####0.00");
		
		
		for(int i=1;i<=12;i++)
		{
			//通过flag判断是电表还是水表
			String sql="";
			String tableName="";
			if(energyFlag==1)//用电
			{
				if(targetFlag==5)//部门用电
				{
					tableName="T_PartmentFenleiDayAm";
				}
				else //建筑用电
				{
					tableName="T_ArcFenleiDayAm";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear
						+" 		and  "
						+" 	Fenlei =( select DictionaryValue_Num from DictionaryValue where DictionaryValue_Value='"+fenxiangName+"') ";
				
			}
			else if(energyFlag==2) //用水
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayWater";
				}
				else 
				{
					tableName="T_ArcFenleiDayWater";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear
						+" 		and  "
						+" 	Fenlei =( select DictionaryValue_Num from DictionaryValue where DictionaryValue_Value='"+fenxiangName+"') ";
			}
			else if(energyFlag==3)//用电(总)
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayAm";
				}
				else 
				{
					tableName="T_ArcFenleiDayAm";
				}
				
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear;
			}	
			else if (energyFlag==4)//用水总
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayWater";
				}
				else 
				{
					tableName="T_ArcFenleiDayWater";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear;
			}
			
			
			
			if(targetFlag==2)//建筑类别
			{
				sql+=" and Architecture_ID in ( select Architecture_ID  from Architecture where Architecture_Style='"+targetID+"') ";
			}
			else if(targetFlag==3)//建筑
			{
				sql+=" and Architecture_ID = "+targetID;
			}
			else if(targetFlag==4)//区域
			{
				sql+=" and Area_ID = "+targetID;
			}
			else if(targetFlag==5)//部门
			{
				sql+=" and Partment_ID = "+targetID;
			}
			
//			System.out.println("能源预测综合sql:"+sql);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				if(rs.next())
				{
					model=new ForecastModel();
					model.setzGross(rs.getDouble("ZGross"));
					model.setzGrossForecast(df.format(forecast.getForecastData(selectYear, selectYear+1, (float)rs.getDouble("ZGross"))));
					model.setcArea(info.getAreaData(selectYear).getJzarea());
					model.setcMan(info.getAreaData(selectYear).getManCount());
					model.setcAreaThis(info.getAreaData(selectYear+1).getJzarea());
					model.setcManThis(info.getAreaData(selectYear+1).getManCount());
					
					list.add(model);
				}
				else //没有数据
				{
					model=new ForecastModel();
					model.setzGross(0);
					model.setzGrossForecast("0.00");
					model.setcArea(info.getAreaData(selectYear).getJzarea());
					model.setcMan(info.getAreaData(selectYear).getManCount());
					model.setcAreaThis(info.getAreaData(selectYear+1).getJzarea());
					model.setcManThis(info.getAreaData(selectYear+1).getManCount());
					
					list.add(model);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

			
		}
		
		return list;
		
	}
	
	
	
	/**
	 * 1全校、 2建筑类别、3建筑、4区域、5部门的12个月的能源预测和能源指标
	 * @param styleName 分项名
	 * @param selectYear 查询的年，默认当前年的前一年
	 * @param energyFlag 判断是1用电、2用水,3代表没有分项的总用电、4没有分项的总用水
	 * @param targetFlag 判断是1全校、 2建筑类别、3建筑、4区域、5部门
	 * @param targetID 1全校、 2建筑类别、3建筑、4区域、5部门 的标识，有时候是int,有时候是String
	 * @param targetValue 计划节约量，用于能源指标
	 * @return list.size()=12
	 * @throws SQLException
	 */
	public ArrayList<ForecastModel> getAllSchool12MonthInfoTarget(String fenxiangName,int selectYear,int energyFlag,int targetFlag,String targetID,float targetValue) throws SQLException
	{
		ArrayList<ForecastModel> list=new ArrayList<ForecastModel>();
		InformationDao info=new InformationDao();
		ForecastByManArea forecast=new ForecastByManArea();
		ForecastModel model=null;
		DecimalFormat df = new DecimalFormat("#####0.00");
		
		
		for(int i=1;i<=12;i++)
		{
			//通过flag判断是电表还是水表
			String sql="";
			String tableName="";
			if(energyFlag==1)//用电
			{
				if(targetFlag==5)//部门用电
				{
					tableName="T_PartmentFenleiDayAm";
				}
				else //建筑用电
				{
					tableName="T_ArcFenleiDayAm";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear
						+" 		and  "
						+" 	Fenlei =( select DictionaryValue_Num from DictionaryValue where DictionaryValue_Value='"+fenxiangName+"') ";
				
			}
			else if(energyFlag==2) //用水
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayWater";
				}
				else 
				{
					tableName="T_ArcFenleiDayWater";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear
						+" 		and  "
						+" 	Fenlei =( select DictionaryValue_Num from DictionaryValue where DictionaryValue_Value='"+fenxiangName+"') ";
			}
			else if(energyFlag==3)//用电(总)
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayAm";
				}
				else 
				{
					tableName="T_ArcFenleiDayAm";
				}
				
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear;
			}	
			else if (energyFlag==4)//用水总
			{
				if(targetFlag==5)
				{
					tableName="T_PartmentFenleiDayWater";
				}
				else 
				{
					tableName="T_ArcFenleiDayWater";
				}
				sql="select "
						+"		sum(ZGross) ZGross "
						+"  from	 "
						+tableName
						+"  where "
						+" 	SelectMonth="+i  
						+" 		and  "
						+" 	SelectYear="+selectYear;
			}
			
			
			
			if(targetFlag==2)//建筑类别
			{
				sql+=" and Architecture_ID in ( select Architecture_ID  from Architecture where Architecture_Style='"+targetID+"') ";
			}
			else if(targetFlag==3)//建筑
			{
				sql+=" and Architecture_ID = "+targetID;
			}
			else if(targetFlag==4)//区域
			{
				sql+=" and Area_ID = "+targetID;
			}
			
			////System.out.println("能源预测综合sql:"+sql);
			Connection conn=null;
			PreparedStatement ps =null;
			ResultSet rs = null;

			try
			{
				conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();			
				if(rs.next())
				{
					model=new ForecastModel();
					model.setzGross(rs.getDouble("ZGross"));
					model.setzGrossForecast(df.format(forecast.getForecastData(selectYear, selectYear+1, (float)rs.getDouble("ZGross"))));
					model.setzGrossTarget(df.format((1-targetValue*1.00/100)*forecast.getForecastData(selectYear, selectYear+1, (float)rs.getDouble("ZGross"))));
					model.setcArea(info.getAreaData(selectYear).getJzarea());
					model.setcMan(info.getAreaData(selectYear).getManCount());
					model.setcAreaThis(info.getAreaData(selectYear+1).getJzarea());
					model.setcManThis(info.getAreaData(selectYear+1).getManCount());
					
					list.add(model);
				}
				else //没有数据
				{
					model=new ForecastModel();
					model.setzGross(0);
					model.setzGrossForecast("0.00");
					model.setzGrossTarget("0.00");
					model.setcArea(info.getAreaData(selectYear).getJzarea());
					model.setcMan(info.getAreaData(selectYear).getManCount());
					model.setcAreaThis(info.getAreaData(selectYear+1).getJzarea());
					model.setcManThis(info.getAreaData(selectYear+1).getManCount());
					
					list.add(model);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}
			
		}
		
		return list;
		
	}
	
}
