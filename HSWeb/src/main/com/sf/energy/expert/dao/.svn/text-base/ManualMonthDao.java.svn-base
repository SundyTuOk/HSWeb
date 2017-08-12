package com.sf.energy.expert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sf.energy.expert.model.ManualEnergyModel;
import com.sf.energy.expert.model.ManualMonthModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.ConnDB;

/**
 * 表ManualMonth的数据库操作
 * @author yanhao
 *
 */
public class ManualMonthDao
{
	int total;
	
	ArchitectureDao archDao=new ArchitectureDao();
	DecimalFormat df =new DecimalFormat("####0.00");
	DecimalFormat df1 =new DecimalFormat("####0.00%"); 
	/**
	 * 列出所有的手工录入数据
	 * @return ManualMonth数据的实体类的集合
	 * @throws SQLException 
	 */
	public ArrayList<ManualMonthModel> listAllData(int pageCount, int pageIndex,String sortName,String order,int flag,String targetID) throws SQLException
	{
		ArrayList<ManualMonthModel> energyList=new ArrayList<ManualMonthModel>();
		ManualMonthModel energyModel=null;
		String sql="";
		if(flag==0){
		
			sql="Select Manual_ID,SelectYear,SelectMonth,(selectYear||'年'||selectMonth||'月') manual_Time ,to_char(Manual_InsertTime,'yyyy-mm-dd hh24:mi:ss') Manual_InsertTime,Manual_Man,Architecture_ID,EN07,EN10,EN11,EN12,EN06,EN08,EN09 from ManualMonth ";
			
			if(order.equals("asc"))//升序
			{
				sql+=" order by "+sortName+ " asc";
			}
			else if(order.equals("desc"))
			{
				sql+=" order by "+sortName+ " desc";
			}
			else {
				sql+=" order by "+sortName;
			}
		}
		else if(flag==2){
			sql="Select Manual_ID,SelectYear,SelectMonth,(selectYear||'年'||selectMonth||'月') manual_Time ,to_char(Manual_InsertTime,'yyyy-mm-dd hh24:mi:ss') Manual_InsertTime,Manual_Man,Architecture_ID,EN07,EN10,EN11,EN12,EN06,EN08,EN09 from ManualMonth where architecture_id="+targetID;
			
			if(order.equals("asc"))//升序
			{
				sql+=" order by "+sortName+ " asc";
			}
			else if(order.equals("desc"))
			{
				sql+=" order by "+sortName+ " desc";
			}
			else {
				sql+=" order by "+sortName;
			}
		}
		else if(flag==1){
			sql="Select Manual_ID,SelectYear,SelectMonth,(selectYear||'年'||selectMonth||'月') manual_Time ,to_char(Manual_InsertTime,'yyyy-mm-dd hh24:mi:ss') Manual_InsertTime,Manual_Man,Architecture_ID,EN07,EN10,EN11,EN12,EN06,EN08,EN09 from ManualMonth where architecture_id in (select architecture_id from architecture where architecture_style='"+targetID+"')";
			
			if(order.equals("asc"))//升序
			{
				sql+=" order by "+sortName+ " asc";
			}
			else if(order.equals("desc"))
			{
				sql+=" order by "+sortName+ " desc";
			}
			else {
				sql+=" order by "+sortName;
			}
		}
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
			setTotal(count);
			if (count <= (pageCount * pageIndex))
				return energyList;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);
			
			while (rs.next()&&(count > 0))
			{
				energyModel=new ManualMonthModel();
				
				energyModel.setManualID(rs.getInt("manual_ID"));
				energyModel.setSelectYear(rs.getInt("SELECTYEAR"));
				energyModel.setSelectMonth(rs.getInt("SELECTMonth"));
				energyModel.setInsertTime(rs.getString("MANUAL_INSERTTIME"));
				energyModel.setManualManName(rs.getString("MANUAL_MAN"));
				energyModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				energyModel.setArchName(archDao.queryByID(rs.getInt("ARCHITECTURE_ID")).getName());
				energyModel.setMeiValue(rs.getFloat("EN07"));
				energyModel.setQiyouValue(rs.getFloat("EN10"));
				energyModel.setMeiyouValue(rs.getFloat("EN11"));
				energyModel.setCaiyouValue(rs.getFloat("EN12"));
				energyModel.setOtherValue(rs.getFloat("EN06"));
				energyModel.setYehuashiyouqiValue(rs.getFloat("EN08"));
				energyModel.setRengongmeiqiValue(rs.getFloat("EN09"));
				energyList.add(energyModel);
				
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
//		rs.last();
//		int count = rs.getRow();
//		setTotal(count);
//		if (count <= (pageCount * pageIndex))
//			return energyList;
//
//		count = count - pageCount * pageIndex;
//
//		if (count >= pageCount)
//			count = pageCount;
//
//		if ((pageCount * pageIndex) == 0)
//			rs.beforeFirst();
//		else
//			rs.absolute(pageCount * pageIndex);
//		
//		while (rs.next()&&(count > 0))
//		{
//			energyModel=new ManualMonthModel();
//			
//			energyModel.setManualID(rs.getInt("manual_ID"));
//			energyModel.setSelectYear(rs.getInt("SELECTYEAR"));
//			energyModel.setSelectMonth(rs.getInt("SELECTMonth"));
//			energyModel.setInsertTime(rs.getString("MANUAL_INSERTTIME"));
//			energyModel.setManualManName(rs.getString("MANUAL_MAN"));
//			energyModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			energyModel.setArchName(archDao.queryByID(rs.getInt("ARCHITECTURE_ID")).getName());
//			energyModel.setMeiValue(rs.getFloat("EN07"));
//			energyModel.setQiyouValue(rs.getFloat("EN10"));
//			energyModel.setMeiyouValue(rs.getFloat("EN11"));
//			energyModel.setCaiyouValue(rs.getFloat("EN12"));
//			energyModel.setOtherValue(rs.getFloat("EN06"));
//			energyModel.setYehuashiyouqiValue(rs.getFloat("EN08"));
//			energyModel.setRengongmeiqiValue(rs.getFloat("EN09"));
//			energyList.add(energyModel);
//			
//			count--;
//		}
		
		return energyList;
	}
	
	/**
	 * 通过ID查询能源
	 * @param manualID
	 * @return
	 * @throws SQLException
	 */
	public ManualMonthModel queryByID(int manualID) throws SQLException
	{
		ManualMonthModel energyModel=new ManualMonthModel();
		String sql="Select * from ManualMonth  where Manual_ID="+manualID;
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{	
				energyModel.setManualID(rs.getInt("manual_ID"));
				energyModel.setSelectYear(rs.getInt("SELECTYEAR"));
				energyModel.setSelectMonth(rs.getInt("SELECTMonth"));
				energyModel.setInsertTime(rs.getString("MANUAL_INSERTTIME"));
				energyModel.setManualManName(rs.getString("MANUAL_MAN"));
				energyModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
				energyModel.setArchName(archDao.queryByID(rs.getInt("ARCHITECTURE_ID")).getName());
				energyModel.setMeiValue(rs.getFloat("EN07"));
				energyModel.setQiyouValue(rs.getFloat("EN10"));
				energyModel.setMeiyouValue(rs.getFloat("EN11"));
				energyModel.setCaiyouValue(rs.getFloat("EN12"));
				energyModel.setOtherValue(rs.getFloat("EN06"));
				energyModel.setYehuashiyouqiValue(rs.getFloat("EN08"));
				energyModel.setRengongmeiqiValue(rs.getFloat("EN09"));
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		if (rs.next())
//		{	
//			energyModel.setManualID(rs.getInt("manual_ID"));
//			energyModel.setSelectYear(rs.getInt("SELECTYEAR"));
//			energyModel.setSelectMonth(rs.getInt("SELECTMonth"));
//			energyModel.setInsertTime(rs.getString("MANUAL_INSERTTIME"));
//			energyModel.setManualManName(rs.getString("MANUAL_MAN"));
//			energyModel.setArchID(rs.getInt("ARCHITECTURE_ID"));
//			energyModel.setArchName(archDao.queryByID(rs.getInt("ARCHITECTURE_ID")).getName());
//			energyModel.setMeiValue(rs.getFloat("EN07"));
//			energyModel.setQiyouValue(rs.getFloat("EN10"));
//			energyModel.setMeiyouValue(rs.getFloat("EN11"));
//			energyModel.setCaiyouValue(rs.getFloat("EN12"));
//			energyModel.setOtherValue(rs.getFloat("EN06"));
//			energyModel.setYehuashiyouqiValue(rs.getFloat("EN08"));
//			energyModel.setRengongmeiqiValue(rs.getFloat("EN09"));
//			
//		}
	
		
		return energyModel;
	}
	
	/**
	 * 查询整年的能耗
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryEnergyAllYear(int year) throws SQLException
	{
	
		Map<String,String> map=new HashMap<String,String>(); 
		String sql="Select sum(EN07),sum(EN10),sum(EN11),sum(EN12),sum(EN06),sum(EN08),sum(EN09) from ManualMonth where selectYear= "+year;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{	
				map.put("meiValue", rs.getString("sum(EN07)"));
				map.put("qiyouValue", rs.getString("sum(EN10)"));
				map.put("meiyouValue", rs.getString("sum(EN11)"));
				map.put("chaiyouValue", rs.getString("sum(EN12)"));
				//这个版本暂时不用数据
				map.put("otherValue", rs.getString("sum(EN06)"));
				map.put("yehuashiyouqiValue", rs.getString("sum(EN08)"));
				map.put("rengongmeiqiValue", rs.getString("sum(EN09)"));
			}
			else {
				map.put("meiValue", "0.0");
				map.put("qiyouValue", "0.0");
				map.put("meiyouValue", "0.0");
				map.put("chaiyouValue", "0.0");
				//这个版本暂时不用数据
				map.put("otherValue", "0.0");
				map.put("yehuashiyouqiValue", "0.0");
				map.put("rengongmeiqiValue", "0.0");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		if (rs.next())
//		{	
//			map.put("meiValue", rs.getString("sum(EN07)"));
//			map.put("qiyouValue", rs.getString("sum(EN10)"));
//			map.put("meiyouValue", rs.getString("sum(EN11)"));
//			map.put("chaiyouValue", rs.getString("sum(EN12)"));
//			//这个版本暂时不用数据
//			map.put("otherValue", rs.getString("sum(EN06)"));
//			map.put("yehuashiyouqiValue", rs.getString("sum(EN08)"));
//			map.put("rengongmeiqiValue", rs.getString("sum(EN09)"));
//		}
//		else {
//			map.put("meiValue", "0.0");
//			map.put("qiyouValue", "0.0");
//			map.put("meiyouValue", "0.0");
//			map.put("chaiyouValue", "0.0");
//			//这个版本暂时不用数据
//			map.put("otherValue", "0.0");
//			map.put("yehuashiyouqiValue", "0.0");
//			map.put("rengongmeiqiValue", "0.0");
//		}
		
		return map;
	}

	/**
	 * 查询整年的能耗
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryEnergyAllYearByArchID(int year,int arch_id) throws SQLException
	{
	
		Map<String,String> map=new HashMap<String,String>(); 
		String sql="Select nvl(sum(EN07),0) EN07 ,nvl(sum(EN10),0) EN10,nvl(sum(EN11),0) EN11,nvl(sum(EN12),0) EN12,nvl(sum(EN06),0) EN06,nvl(sum(EN08),0) EN08,nvl(sum(EN09),0) EN09 from ManualMonth where selectYear= "+year +" and architecture_id="+arch_id;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{	
				map.put("meiValue", rs.getString("EN07"));
				map.put("qiyouValue", rs.getString("EN10"));
				map.put("meiyouValue", rs.getString("EN11"));
				map.put("chaiyouValue", rs.getString("EN12"));
				//这个版本暂时不用数据
				map.put("otherValue", rs.getString("EN06"));
				map.put("yehuashiyouqiValue", rs.getString("EN08"));
				map.put("rengongmeiqiValue", rs.getString("EN09"));
			}
			else {
				map.put("meiValue", "0.0");
				map.put("qiyouValue", "0.0");
				map.put("meiyouValue", "0.0");
				map.put("chaiyouValue", "0.0");
				//这个版本暂时不用数据
				map.put("otherValue", "0.0");
				map.put("yehuashiyouqiValue", "0.0");
				map.put("rengongmeiqiValue", "0.0");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		if (rs.next())
//		{	
//			map.put("meiValue", rs.getString("EN07"));
//			map.put("qiyouValue", rs.getString("EN10"));
//			map.put("meiyouValue", rs.getString("EN11"));
//			map.put("chaiyouValue", rs.getString("EN12"));
//			//这个版本暂时不用数据
//			map.put("otherValue", rs.getString("EN06"));
//			map.put("yehuashiyouqiValue", rs.getString("EN08"));
//			map.put("rengongmeiqiValue", rs.getString("EN09"));
//		}
//		else {
//			map.put("meiValue", "0.0");
//			map.put("qiyouValue", "0.0");
//			map.put("meiyouValue", "0.0");
//			map.put("chaiyouValue", "0.0");
//			//这个版本暂时不用数据
//			map.put("otherValue", "0.0");
//			map.put("yehuashiyouqiValue", "0.0");
//			map.put("rengongmeiqiValue", "0.0");
//		}
		
		return map;
	}
	
	/**
	 * 查询单月的能耗
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	
	public Map<String,String> queryEnergyMonth(int year,int month) throws SQLException
	{
		
		Map<String,String> map=new HashMap<String,String>(); 
		String sql="Select sum(EN07),sum(EN10),sum(EN11),sum(EN12),sum(EN06),sum(EN08),sum(EN09) from ManualMonth where selectYear= "+year+" and selectMonth="+month;
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			if (rs.next())
			{	
				map.put("meiValue", rs.getString("sum(EN07)"));
				map.put("qiyouValue", rs.getString("sum(EN10)"));
				map.put("meiyouValue", rs.getString("sum(EN11)"));
				map.put("chaiyouValue", rs.getString("sum(EN12)"));
				//这个版本暂时不用数据
				map.put("otherValue", rs.getString("sum(EN06)"));
				map.put("yehuashiyouqiValue", rs.getString("sum(EN08)"));
				map.put("rengongmeiqiValue", rs.getString("sum(EN09)"));
			}
			else {
				map.put("meiValue", "0.0");
				map.put("qiyouValue", "0.0");
				map.put("meiyouValue", "0.0");
				map.put("chaiyouValue", "0.0");
				//这个版本暂时不用数据
				map.put("otherValue", "0.0");
				map.put("yehuashiyouqiValue", "0.0");
				map.put("rengongmeiqiValue", "0.0");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		if (rs.next())
//		{	
//			map.put("meiValue", rs.getString("sum(EN07)"));
//			map.put("qiyouValue", rs.getString("sum(EN10)"));
//			map.put("meiyouValue", rs.getString("sum(EN11)"));
//			map.put("chaiyouValue", rs.getString("sum(EN12)"));
//			//这个版本暂时不用数据
//			map.put("otherValue", rs.getString("sum(EN06)"));
//			map.put("yehuashiyouqiValue", rs.getString("sum(EN08)"));
//			map.put("rengongmeiqiValue", rs.getString("sum(EN09)"));
//		}
//		else {
//			map.put("meiValue", "0.0");
//			map.put("qiyouValue", "0.0");
//			map.put("meiyouValue", "0.0");
//			map.put("chaiyouValue", "0.0");
//			//这个版本暂时不用数据
//			map.put("otherValue", "0.0");
//			map.put("yehuashiyouqiValue", "0.0");
//			map.put("rengongmeiqiValue", "0.0");
//		}

		return map;
	}
	
	/**
	 * 查询某建筑整年能耗
	 * @param archID
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public ManualEnergyModel queryArchEnergyAllYear(int archID,int year) throws SQLException
	{
		ManualEnergyModel model=new ManualEnergyModel();
		Architecture archModel =new Architecture();
		ArchitectureDao archDao=new ArchitectureDao();
		ReportModel energyModel=new ReportModel();
		ElecReportHelper energyDao=new ElecReportHelperImpl();
		archModel=archDao.queryByID(archID);
		energyModel=energyDao.getArcCountByYear(archID, year);
		float totalValue=0;
		
		String sql="Select sum(EN07),sum(EN10),sum(EN11),sum(EN12),sum(EN06),sum(EN08),sum(EN09) from ManualMonth where selectYear= "+year+" and Architecture_ID="+archID;
//		System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			model.setQueryMonth(0);
			model.setQueryYear(year);
			model.setArchID(archModel.getId());
			model.setArchName(archModel.getName());
			model.setArchCountMan(archModel.getCountMan());
			model.setArchArea(archModel.getArea());
			model.setEnergyValue(energyModel.getTotalEnergyCount());
			if (rs.next())
			{	
				totalValue=energyModel.getTotalEnergyCount()+rs.getFloat("sum(EN07)")+rs.getFloat("sum(EN10)")+rs.getFloat("sum(EN11)")+rs.getFloat("sum(EN12)");
				
				if(totalValue==0)
				{
					model.setRatioEnergy(0+"");
					model.setRatioMei(0+"");
					model.setRatioMeiyou(0+"");
					model.setRatioQiyou(0+"");
					model.setRatioChaiyou(0+"");
				}
				else 
				{
					if(totalValue!=0){
					model.setRatioEnergy(df1.format(energyModel.getTotalEnergyCount()/totalValue));
					model.setRatioMei(df1.format(rs.getFloat("sum(EN07)")/totalValue));
					model.setRatioMeiyou(df1.format(rs.getFloat("sum(EN11)")/totalValue));
					model.setRatioQiyou(df1.format(rs.getFloat("sum(EN10)")/totalValue));
					model.setRatioChaiyou(df1.format(rs.getFloat("sum(EN12)")/totalValue));
					}
				}
				
				
				model.setMeiValue(rs.getFloat("sum(EN07)"));
				model.setQiyouValue(rs.getFloat("sum(EN10)"));
				model.setMeiyouValue(rs.getFloat("sum(EN11)"));
				model.setChaiyouValue(rs.getFloat("sum(EN12)"));
				if(archModel.getArea()!=0){
					model.setUnitEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getArea())));
					model.setUnitMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getArea())));
					model.setUnitMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getArea())));
					model.setUnitQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getArea())));
					model.setUnitChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getArea())));
				}
				if(archModel.getCountMan()!=0){
					model.setAverageEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getCountMan())));
					model.setAverageMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getCountMan())));
					model.setAverageMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getCountMan())));
					model.setAverageQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getCountMan())));
					model.setAverageChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getCountMan())));
				}
			}
			else 
			{
				model.setRatioEnergy(0+"");
				model.setRatioMei(0+"");
				model.setRatioMeiyou(0+"");
				model.setRatioQiyou(0+"");
				model.setRatioChaiyou(0+"");
				
				model.setMeiValue(0);
				model.setQiyouValue(0);
				model.setMeiyouValue(0);
				model.setChaiyouValue(0);
				
				model.setUnitEnergy(0);
				model.setUnitMei(0);
				model.setUnitMeiyou(0);
				model.setUnitQiyou(0);
				model.setUnitChaiyou(0);
				
				model.setAverageEnergy(0);
				model.setAverageMei(0);
				model.setAverageMeiyou(0);
				model.setAverageQiyou(0);
				model.setAverageChaiyou(0);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		model.setQueryMonth(0);
//		model.setQueryYear(year);
//		model.setArchID(archModel.getId());
//		model.setArchName(archModel.getName());
//		model.setArchCountMan(archModel.getCountMan());
//		model.setArchArea(archModel.getArea());
//		model.setEnergyValue(energyModel.getTotalEnergyCount());
//		if (rs.next())
//		{	
//			totalValue=energyModel.getTotalEnergyCount()+rs.getFloat("sum(EN07)")+rs.getFloat("sum(EN10)")+rs.getFloat("sum(EN11)")+rs.getFloat("sum(EN12)");
//			
//			if(totalValue==0)
//			{
//				model.setRatioEnergy(0+"");
//				model.setRatioMei(0+"");
//				model.setRatioMeiyou(0+"");
//				model.setRatioQiyou(0+"");
//				model.setRatioChaiyou(0+"");
//			}
//			else 
//			{
//				model.setRatioEnergy(df1.format(energyModel.getTotalEnergyCount()/totalValue));
//				model.setRatioMei(df1.format(rs.getFloat("sum(EN07)")/totalValue));
//				model.setRatioMeiyou(df1.format(rs.getFloat("sum(EN11)")/totalValue));
//				model.setRatioQiyou(df1.format(rs.getFloat("sum(EN10)")/totalValue));
//				model.setRatioChaiyou(df1.format(rs.getFloat("sum(EN12)")/totalValue));
//			}
//			
//			
//			model.setMeiValue(rs.getFloat("sum(EN07)"));
//			model.setQiyouValue(rs.getFloat("sum(EN10)"));
//			model.setMeiyouValue(rs.getFloat("sum(EN11)"));
//			model.setChaiyouValue(rs.getFloat("sum(EN12)"));
//			
//			model.setUnitEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getArea())));
//			model.setUnitMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getArea())));
//			model.setUnitMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getArea())));
//			model.setUnitQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getArea())));
//			model.setUnitChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getArea())));
//			
//			model.setAverageEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getCountMan())));
//			model.setAverageMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getCountMan())));
//			model.setAverageMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getCountMan())));
//			model.setAverageQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getCountMan())));
//			model.setAverageChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getCountMan())));
//		}
//		else 
//		{
//			model.setRatioEnergy(0+"");
//			model.setRatioMei(0+"");
//			model.setRatioMeiyou(0+"");
//			model.setRatioQiyou(0+"");
//			model.setRatioChaiyou(0+"");
//			
//			model.setMeiValue(0);
//			model.setQiyouValue(0);
//			model.setMeiyouValue(0);
//			model.setChaiyouValue(0);
//			
//			model.setUnitEnergy(0);
//			model.setUnitMei(0);
//			model.setUnitMeiyou(0);
//			model.setUnitQiyou(0);
//			model.setUnitChaiyou(0);
//			
//			model.setAverageEnergy(0);
//			model.setAverageMei(0);
//			model.setAverageMeiyou(0);
//			model.setAverageQiyou(0);
//			model.setAverageChaiyou(0);
//		}

		
		return model;
	}
	
	/**
	 * 查询某建筑单月能耗
	 * @param archID
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public ManualEnergyModel queryArchEnergyMonth(int archID,int year,int month) throws SQLException
	{
		ManualEnergyModel model=new ManualEnergyModel();
		Architecture archModel =new Architecture();
		ArchitectureDao archDao=new ArchitectureDao();
		ReportModel energyModel=new ReportModel();
		ElecReportHelper energyDao=new ElecReportHelperImpl();
		archModel=archDao.queryByID(archID);
		energyModel=energyDao.getArcCountByMonth(archID, year,month);
		float totalValue=0;
		String sql="Select sum(EN07),sum(EN10),sum(EN11),sum(EN12),sum(EN06),sum(EN08),sum(EN09) from ManualMonth where selectYear= "+year+" and Architecture_ID="+archID +" and selectMonth="+month;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			model.setQueryMonth(month);
			model.setQueryYear(year);
			model.setArchID(archModel.getId());
			model.setArchName(archModel.getName());
			model.setArchCountMan(archModel.getCountMan());
			model.setArchArea(archModel.getArea());
			model.setEnergyValue(energyModel.getTotalEnergyCount());
			if (rs.next())
			{	
				totalValue=energyModel.getTotalEnergyCount()+rs.getFloat("sum(EN07)")+rs.getFloat("sum(EN10)")+rs.getFloat("sum(EN11)")+rs.getFloat("sum(EN12)");
				
				if(totalValue==0)
				{
					model.setRatioEnergy(0+"");
					model.setRatioMei(0+"");
					model.setRatioMeiyou(0+"");
					model.setRatioQiyou(0+"");
					model.setRatioChaiyou(0+"");
				}
				else 
				{
					if(totalValue!=0){
					model.setRatioEnergy(df1.format(energyModel.getTotalEnergyCount()/totalValue));
					model.setRatioMei(df1.format(rs.getFloat("sum(EN07)")/totalValue));
					model.setRatioMeiyou(df1.format(rs.getFloat("sum(EN11)")/totalValue));
					model.setRatioQiyou(df1.format(rs.getFloat("sum(EN10)")/totalValue));
					model.setRatioChaiyou(df1.format(rs.getFloat("sum(EN12)")/totalValue));
					}
				}
				
				model.setMeiValue(rs.getFloat("sum(EN07)"));
				model.setQiyouValue(rs.getFloat("sum(EN10)"));
				model.setMeiyouValue(rs.getFloat("sum(EN11)"));
				model.setChaiyouValue(rs.getFloat("sum(EN12)"));
				if(archModel.getArea()!=0){
				model.setUnitEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getArea())));
				model.setUnitMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getArea())));
				model.setUnitMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getArea())));
				model.setUnitQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getArea())));
				model.setUnitChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getArea())));
				}
				if(archModel.getCountMan()!=0){
				model.setAverageEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getCountMan())));
				model.setAverageMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getCountMan())));
				model.setAverageMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getCountMan())));
				model.setAverageQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getCountMan())));
				model.setAverageChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getCountMan())));
				}
			}
			else 
			{
				model.setRatioEnergy(0+"");
				model.setRatioMei(0+"");
				model.setRatioMeiyou(0+"");
				model.setRatioQiyou(0+"");
				model.setRatioChaiyou(0+"");
				
				model.setMeiValue(0);
				model.setQiyouValue(0);
				model.setMeiyouValue(0);
				model.setChaiyouValue(0);
				
				model.setUnitEnergy(0);
				model.setUnitMei(0);
				model.setUnitMeiyou(0);
				model.setUnitQiyou(0);
				model.setUnitChaiyou(0);
				
				model.setAverageEnergy(0);
				model.setAverageMei(0);
				model.setAverageMeiyou(0);
				model.setAverageQiyou(0);
				model.setAverageChaiyou(0);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

//		model.setQueryMonth(month);
//		model.setQueryYear(year);
//		model.setArchID(archModel.getId());
//		model.setArchName(archModel.getName());
//		model.setArchCountMan(archModel.getCountMan());
//		model.setArchArea(archModel.getArea());
//		model.setEnergyValue(energyModel.getTotalEnergyCount());
//		if (rs.next())
//		{	
//			totalValue=energyModel.getTotalEnergyCount()+rs.getFloat("sum(EN07)")+rs.getFloat("sum(EN10)")+rs.getFloat("sum(EN11)")+rs.getFloat("sum(EN12)");
//			
//			if(totalValue==0)
//			{
//				model.setRatioEnergy(0+"");
//				model.setRatioMei(0+"");
//				model.setRatioMeiyou(0+"");
//				model.setRatioQiyou(0+"");
//				model.setRatioChaiyou(0+"");
//			}
//			else 
//			{
//				model.setRatioEnergy(df1.format(energyModel.getTotalEnergyCount()/totalValue));
//				model.setRatioMei(df1.format(rs.getFloat("sum(EN07)")/totalValue));
//				model.setRatioMeiyou(df1.format(rs.getFloat("sum(EN11)")/totalValue));
//				model.setRatioQiyou(df1.format(rs.getFloat("sum(EN10)")/totalValue));
//				model.setRatioChaiyou(df1.format(rs.getFloat("sum(EN12)")/totalValue));
//			}
//			
//			model.setMeiValue(rs.getFloat("sum(EN07)"));
//			model.setQiyouValue(rs.getFloat("sum(EN10)"));
//			model.setMeiyouValue(rs.getFloat("sum(EN11)"));
//			model.setChaiyouValue(rs.getFloat("sum(EN12)"));
//			
//			model.setUnitEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getArea())));
//			model.setUnitMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getArea())));
//			model.setUnitMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getArea())));
//			model.setUnitQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getArea())));
//			model.setUnitChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getArea())));
//			
//			model.setAverageEnergy(Float.parseFloat(df.format(energyModel.getTotalEnergyCount()/archModel.getCountMan())));
//			model.setAverageMei(Float.parseFloat(df.format(rs.getFloat("sum(EN07)")/archModel.getCountMan())));
//			model.setAverageMeiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN11)")/archModel.getCountMan())));
//			model.setAverageQiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN10)")/archModel.getCountMan())));
//			model.setAverageChaiyou(Float.parseFloat(df.format(rs.getFloat("sum(EN12)")/archModel.getCountMan())));
//			
//		}
//		else 
//		{
//			model.setRatioEnergy(0+"");
//			model.setRatioMei(0+"");
//			model.setRatioMeiyou(0+"");
//			model.setRatioQiyou(0+"");
//			model.setRatioChaiyou(0+"");
//			
//			model.setMeiValue(0);
//			model.setQiyouValue(0);
//			model.setMeiyouValue(0);
//			model.setChaiyouValue(0);
//			
//			model.setUnitEnergy(0);
//			model.setUnitMei(0);
//			model.setUnitMeiyou(0);
//			model.setUnitQiyou(0);
//			model.setUnitChaiyou(0);
//			
//			model.setAverageEnergy(0);
//			model.setAverageMei(0);
//			model.setAverageMeiyou(0);
//			model.setAverageQiyou(0);
//			model.setAverageChaiyou(0);
//		}
		
		return model;
	}
	
	/**
	 * 查询某建筑类别整年能耗
	 * @param archID
	 * @param year
	 * @return
	 * @throws SQLException
	 */
	public ManualEnergyModel queryArchStyleEnergyAllYear(String archStyle,int year) throws SQLException
	{
		ManualEnergyModel model=new ManualEnergyModel();
		ArrayList<Architecture> archList=new ArrayList<Architecture>();
		
		float totalEnergyValue=0;
		float totalMeiValue=0;
		float totalMeiyouValue=0;
		float totalQiyouValue=0;
		float totalChaiyouValue=0;
		int totalManCount=0;
		float totalArea=0;
		float totalValue=0;
		
		try
		{
			archList=archDao.queryArchByStyle(archStyle.charAt(0));
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		model.setArchStyleName(archDao.queryArchStyleName(archStyle.charAt(0)));
		if(archList.size()==0)//防止总人口为0，导致NaN问题
		{
			model.setRatioEnergy(0+"");
			model.setRatioMei(0+"");
			model.setRatioMeiyou(0+"");
			model.setRatioQiyou(0+"");
			model.setRatioChaiyou(0+"");
			
			model.setQueryMonth(0);
			model.setQueryYear(year);
			
			model.setArchArea(0);
			model.setArchCountMan(0);
			
			model.setEnergyValue(0);
			model.setMeiValue(0);
			model.setQiyouValue(0);
			model.setMeiyouValue(0);
			model.setChaiyouValue(0);
			model.setArchArea(0);
			model.setArchCountMan(0);
			
			model.setUnitEnergy(0);
			model.setUnitMei(0);
			model.setUnitMeiyou(0);
			model.setUnitQiyou(0);
			model.setUnitChaiyou(0);
			
			model.setAverageEnergy(0);
			model.setAverageMei(0);
			model.setAverageMeiyou(0);
			model.setAverageQiyou(0);
			model.setAverageChaiyou(0);
			
			return model;
		}
		
		for(int i=0;i<archList.size();i++)
		{
			ManualEnergyModel m=new ManualEnergyModel();
			m=this.queryArchEnergyAllYear(archList.get(i).getId(), year);
			
			totalEnergyValue+=m.getEnergyValue();
			totalMeiValue+=m.getMeiValue();
			totalMeiyouValue+=m.getMeiyouValue();
			totalQiyouValue+=m.getQiyouValue();
			totalChaiyouValue+=m.getChaiyouValue();
			totalManCount+=m.getArchCountMan();
			totalArea+=m.getArchArea();
			
			totalValue=totalEnergyValue+totalMeiValue+totalMeiyouValue+totalQiyouValue+totalChaiyouValue;
		}
		if(totalValue!=0){
		model.setRatioEnergy(df1.format(totalEnergyValue/totalValue));
		model.setRatioMei(df1.format(totalMeiValue/totalValue));
		model.setRatioMeiyou(df1.format(totalMeiyouValue/totalValue));
		model.setRatioQiyou(df1.format(totalQiyouValue/totalValue));
		model.setRatioChaiyou(df1.format(totalChaiyouValue/totalValue));
		}
		model.setQueryMonth(0);
		model.setQueryYear(year);
		
		model.setArchArea(totalArea);
		model.setArchCountMan(totalManCount);
		
		model.setEnergyValue(totalEnergyValue);
		model.setMeiValue(totalMeiValue);
		model.setQiyouValue(totalQiyouValue);
		model.setMeiyouValue(totalMeiyouValue);
		model.setChaiyouValue(totalChaiyouValue);
		model.setArchArea(totalArea);
		model.setArchCountMan(totalManCount);
		if(totalArea!=0){
		model.setUnitEnergy(Float.parseFloat(df.format(totalEnergyValue/totalArea)));
		model.setUnitMei(Float.parseFloat(df.format(totalMeiValue/totalArea)));
		model.setUnitMeiyou(Float.parseFloat(df.format(totalMeiyouValue/totalArea)));
		model.setUnitQiyou(Float.parseFloat(df.format(totalQiyouValue/totalArea)));
		model.setUnitChaiyou(Float.parseFloat(df.format(totalChaiyouValue/totalArea)));
		}
		if(totalManCount!=0){
		model.setAverageEnergy(Float.parseFloat(df.format(totalEnergyValue/totalManCount)));
		model.setAverageMei(Float.parseFloat(df.format(totalMeiValue/totalManCount)));
		model.setAverageMeiyou(Float.parseFloat(df.format(totalMeiyouValue/totalManCount)));
		model.setAverageQiyou(Float.parseFloat(df.format(totalQiyouValue/totalManCount)));
		model.setAverageChaiyou(Float.parseFloat(df.format(totalChaiyouValue/totalManCount)));
		}
		return model;
	}
	
	/**
	 * 查询某建筑类别单月能耗
	 * @param archStyle
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public ManualEnergyModel queryArchStyleEnergyMonth(String archStyle,int year,int month) throws SQLException
	{
		ManualEnergyModel model=new ManualEnergyModel();
		ArrayList<Architecture> archList=new ArrayList<Architecture>();
		
		float totalEnergyValue=0;
		float totalMeiValue=0;
		float totalMeiyouValue=0;
		float totalQiyouValue=0;
		float totalChaiyouValue=0;
		int totalManCount=0;
		float totalArea=0;
		float totalValue=0;
		try
		{
			archList=archDao.queryArchByStyle(archStyle.charAt(0));
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		model.setArchStyleName(archDao.queryArchStyleName(archStyle.charAt(0)));
		if(archList.size()==0)//防止总人口为0，导致NaN问题
		{
			model.setRatioEnergy(0+"");
			model.setRatioMei(0+"");
			model.setRatioMeiyou(0+"");
			model.setRatioQiyou(0+"");
			model.setRatioChaiyou(0+"");
			
			model.setQueryMonth(month);
			model.setQueryYear(year);
			
			model.setArchArea(0);
			model.setArchCountMan(0);
			
			model.setEnergyValue(0);
			model.setMeiValue(0);
			model.setQiyouValue(0);
			model.setMeiyouValue(0);
			model.setChaiyouValue(0);
			model.setArchArea(0);
			model.setArchCountMan(0);
			
			model.setUnitEnergy(0);
			model.setUnitMei(0);
			model.setUnitMeiyou(0);
			model.setUnitQiyou(0);
			model.setUnitChaiyou(0);
			
			model.setAverageEnergy(0);
			model.setAverageMei(0);
			model.setAverageMeiyou(0);
			model.setAverageQiyou(0);
			model.setAverageChaiyou(0);
			
			return model;
		}
		
		for(int i=0;i<archList.size();i++)
		{
			ManualEnergyModel m=new ManualEnergyModel();
			
			m=this.queryArchEnergyMonth(archList.get(i).getId(), year,month);
			totalEnergyValue+=m.getEnergyValue();
			totalMeiValue+=m.getMeiValue();
			totalMeiyouValue+=m.getMeiyouValue();
			totalQiyouValue+=m.getQiyouValue();
			totalChaiyouValue+=m.getChaiyouValue();
			totalManCount+=m.getArchCountMan();
			totalArea+=m.getArchArea();
			
			totalValue=totalEnergyValue+totalMeiValue+totalMeiyouValue+totalQiyouValue+totalChaiyouValue;
		}
		if(totalValue!=0){
		model.setRatioEnergy(df1.format(totalEnergyValue/totalValue));
		model.setRatioMei(df1.format(totalMeiValue/totalValue));
		model.setRatioMeiyou(df1.format(totalMeiyouValue/totalValue));
		model.setRatioQiyou(df1.format(totalQiyouValue/totalValue));
		model.setRatioChaiyou(df1.format(totalChaiyouValue/totalValue));
		}
		model.setQueryMonth(month);
		model.setQueryYear(year);
		
		model.setArchArea(totalArea);
		model.setArchCountMan(totalManCount);
		
		model.setEnergyValue(totalEnergyValue);
		model.setMeiValue(totalMeiValue);
		model.setQiyouValue(totalQiyouValue);
		model.setMeiyouValue(totalMeiyouValue);
		model.setChaiyouValue(totalChaiyouValue);
		model.setArchArea(totalArea);
		model.setArchCountMan(totalManCount);
		if(totalArea!=0){
		model.setUnitEnergy(Float.parseFloat(df.format(totalEnergyValue/totalArea)));
		model.setUnitMei(Float.parseFloat(df.format(totalMeiValue/totalArea)));
		model.setUnitMeiyou(Float.parseFloat(df.format(totalMeiyouValue/totalArea)));
		model.setUnitQiyou(Float.parseFloat(df.format(totalQiyouValue/totalArea)));
		model.setUnitChaiyou(Float.parseFloat(df.format(totalChaiyouValue/totalArea)));
		}
		if(totalManCount!=0){
		model.setAverageEnergy(Float.parseFloat(df.format(totalEnergyValue/totalManCount)));
		model.setAverageMei(Float.parseFloat(df.format(totalMeiValue/totalManCount)));
		model.setAverageMeiyou(Float.parseFloat(df.format(totalMeiyouValue/totalManCount)));
		model.setAverageQiyou(Float.parseFloat(df.format(totalQiyouValue/totalManCount)));
		model.setAverageChaiyou(Float.parseFloat(df.format(totalChaiyouValue/totalManCount)));
		}
		return model;
	}
	
	/**
	 * 新增能源手工录入
	 * @param manualModel
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(ManualMonthModel manualModel) throws SQLException
	{
		String sql="insert into  ManualMonth(SELECTYEAR,SELECTMonth,MANUAL_INSERTTIME,MANUAL_MAN,ARCHITECTURE_ID,EN07,EN10,EN11,EN12,EN06,EN08,EN09) values(?,?,to_date('"+manualModel.getInsertTime()+"','yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1,manualModel.getSelectYear() );
			ps.setInt(2, manualModel.getSelectMonth());
			ps.setString(3, manualModel.getManualManName());
			ps.setInt(4, manualModel.getArchID());
			ps.setFloat(5, manualModel.getMeiValue());
			ps.setFloat(6, manualModel.getQiyouValue());
			ps.setFloat(7, manualModel.getMeiyouValue());
			ps.setFloat(8, manualModel.getCaiyouValue());
			ps.setFloat(9, manualModel.getOtherValue());
			ps.setFloat(10, manualModel.getYehuashiyouqiValue());
			ps.setFloat(11, manualModel.getRengongmeiqiValue());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}		
//		ps.setInt(1,manualModel.getSelectYear() );
//		ps.setInt(2, manualModel.getSelectMonth());
//		ps.setString(3, manualModel.getManualManName());
//		ps.setInt(4, manualModel.getArchID());
//		ps.setFloat(5, manualModel.getMeiValue());
//		ps.setFloat(6, manualModel.getQiyouValue());
//		ps.setFloat(7, manualModel.getMeiyouValue());
//		ps.setFloat(8, manualModel.getCaiyouValue());
//		ps.setFloat(9, manualModel.getOtherValue());
//		ps.setFloat(10, manualModel.getYehuashiyouqiValue());
//		ps.setFloat(11, manualModel.getRengongmeiqiValue());
//		
//		b = (ps.executeUpdate() == 1);
//	 
		return b;
	}
	
	/**
	 * 通过id删除能源手工录入数据
	 * @param manualID
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int manualID) throws SQLException
	{
		String sql="delete from ManualMonth where manual_ID="+manualID;
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
	 * 通过id修改能源手工录入数据
	 * @param manualModel
	 * @return
	 * @throws SQLException
	 */
	public boolean update(ManualMonthModel manualModel) throws SQLException
	{
		
		String sql="update ManualMonth set SELECTYEAR=?, SELECTMonth=?, MANUAL_INSERTTIME=to_date('"+manualModel.getInsertTime()+"','yyyy-mm-dd hh24:mi:ss'), MANUAL_MAN=?, ARCHITECTURE_ID=?, EN07=?, EN10=?, EN11=?, EN12=?, EN06=?, EN08=?, EN09=? where Manual_ID=?";
		Connection conn=null;
		PreparedStatement ps =null;
		boolean b=false;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1,manualModel.getSelectYear() );
			ps.setInt(2, manualModel.getSelectMonth());
			ps.setString(3, manualModel.getManualManName());
			ps.setInt(4, manualModel.getArchID());
			ps.setFloat(5, manualModel.getMeiValue());
			ps.setFloat(6, manualModel.getQiyouValue());
			ps.setFloat(7, manualModel.getMeiyouValue());
			ps.setFloat(8, manualModel.getCaiyouValue());
			ps.setFloat(9, manualModel.getOtherValue());
			ps.setFloat(10, manualModel.getYehuashiyouqiValue());
			ps.setFloat(11, manualModel.getRengongmeiqiValue());
			ps.setInt(12, manualModel.getManualID());
			b = (ps.executeUpdate() == 1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}		
//		ps.setInt(1,manualModel.getSelectYear() );
//		ps.setInt(2, manualModel.getSelectMonth());
//		ps.setString(3, manualModel.getManualManName());
//		ps.setInt(4, manualModel.getArchID());
//		ps.setFloat(5, manualModel.getMeiValue());
//		ps.setFloat(6, manualModel.getQiyouValue());
//		ps.setFloat(7, manualModel.getMeiyouValue());
//		ps.setFloat(8, manualModel.getCaiyouValue());
//		ps.setFloat(9, manualModel.getOtherValue());
//		ps.setFloat(10, manualModel.getYehuashiyouqiValue());
//		ps.setFloat(11, manualModel.getRengongmeiqiValue());
//		ps.setInt(12, manualModel.getManualID());
//		
//		b = (ps.executeUpdate() == 1);

		return b;
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
