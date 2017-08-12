package com.sf.energy.powernet.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.dao.AmMeterPDDatasDao;
import com.sf.energy.powernet.model.PWnoBalanceModel;
import com.sf.energy.powernet.model.RateNoModel;
import com.sf.energy.powernet.model.VnoBalanceModel;

public class PWnoBalance
{
	/**
	 * 通过电压不平衡率查询电压不平衡率在各个区间的个数
	 * @param am_ID		电表ID
	 * @param year		年份
	 * @param month		月份
	 * @param day		日期
	 * @param hour		小数
	 * @return			结果集
	 * @throws SQLException
	 */
	public RateNoModel CountRateNo(int am_ID,int year,int month,int day,int hour) throws SQLException 
	{
		RateNoModel  rnm = null;
		List<PWnoBalanceModel>  list = null;
		AmMeterPDDatasDao  apdd = null;
		int  count5 = 0;      //平衡率小于5%的个数
		int  count510 = 0;    //平衡率大于5%小于10%的个数
		int  count1020 = 0;   //平衡率大于10%小于20%的个数
		int  count20 = 0;     //平衡率大于20%的个数
		int  count = 0;       //数据记录的条数
		float rate = 0;		  //电压数据合格率
		
		apdd = new AmMeterPDDatasDao();
		rnm = new RateNoModel();
		
		list = apdd.getPWNobalancedata(am_ID, year, month, day, hour);
		if(list.size() == 0)
		{
			rnm.setCount(0);
			rnm.setCount5(0);
			rnm.setCount510(0);
			rnm.setCount1020(0);
			rnm.setCount20(0);
			rnm.setRate(0);
		}
		count = apdd.getcountNo(am_ID, year, month, day, hour);	
		for(int i = 0;i < list.size();i++)
		{ 
			if(list.get(i).getPWrate() < 5)
			{
				count5 = count5 + 1;
			}else if(list.get(i).getPWrate() > 5 && list.get(i).getPWrate() <= 10)
			{
				count510 = count510 + 1;
			}else if(list.get(i).getPWrate() > 10 && list.get(i).getPWrate() <=20)
			{
				count1020 = count1020 + 1;
			}else if(list.get(i).getPWrate() > 20)
			{
				count20 = count20 + 1;
			}
		}
		if(count != 0)
		{
			rate = (count5 + count510)/count;
			BigDecimal   b  =   new BigDecimal(rate);
			rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  //四舍五入保留两位小数
		}else
		{
			rate = 0;
		}
		
		rnm.setCount(count);
		rnm.setCount5(count5);
		rnm.setCount510(count510);
		rnm.setCount1020(count1020);
		rnm.setCount20(count20);
		rnm.setRate(rate);
		
		return rnm;		
	}
	
	
	/**
	 * 		通过电压不平衡率查询电压不平衡率在各个区间的个数(全校的)
	 * @param year		年
	 * @param month		月
	 * @param day		日
	 * @param hour		小时
	 * @return			结果集
	 * @throws SQLException
	 */
	public RateNoModel CountAllRateNo(int year,int month,int day,int hour) throws SQLException 
	{
		RateNoModel  rnm = null;
		List<PWnoBalanceModel>  list = null;
		AmMeterPDDatasDao  apdd = null;
		int  count5 = 0;      //平衡率小于5%的个数
		int  count510 = 0;    //平衡率大于5%小于10%的个数
		int  count1020 = 0;   //平衡率大于10%小于20%的个数
		int  count20 = 0;     //平衡率大于20%的个数
		int  count = 0;       //数据记录的条数
		float rate = 0;		  //电压数据合格率
		
		apdd = new AmMeterPDDatasDao();
		rnm = new RateNoModel();
		
		list = apdd.getAllPWNobalancedata(year, month, day, hour);
		if(list.size() == 0)
		{
			rnm.setCount(0);
			rnm.setCount5(0);
			rnm.setCount510(0);
			rnm.setCount1020(0);
			rnm.setCount20(0);
			rnm.setRate(0);
		}
		count = apdd.getAllcountNo(year, month, day, hour);	
		for(int i = 0;i < list.size();i++)
		{ 
			if(list.get(i).getPWrate() < 5)
			{
				count5 = count5 + 1;
			}else if(list.get(i).getPWrate() > 5 && list.get(i).getPWrate() <= 10)
			{
				count510 = count510 + 1;
			}else if(list.get(i).getPWrate() > 10 && list.get(i).getPWrate() <=20)
			{
				count1020 = count1020 + 1;
			}else if(list.get(i).getPWrate() > 20)
			{
				count20 = count20 + 1;
			}
		}
		if(count != 0)
		{
			rate = (count5 + count510)/count;
			BigDecimal   b  =   new BigDecimal(rate);
			rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  //四舍五入保留两位小数
		}else
		{
			rate = 0;
		}
		
		rnm.setCount(count);
		rnm.setCount5(count5);
		rnm.setCount510(count510);
		rnm.setCount1020(count1020);
		rnm.setCount20(count20);
		rnm.setRate(rate);
		
		return rnm;		
	}
	
	
	
	/**
	 * 			通过根节点查询其下面所有子回路一个小时不平衡率情况
	 * @param parent_ID			根节点
	 * @param year				年
	 * @param month				月
	 * @param day				日
	 * @param hour				小时
	 * @return					结果集
	 * @throws SQLException
	 */
	public	RateNoModel	CountRateNoByParent(int parent_ID,int year,int month,int day,int hour) throws SQLException
	{
		RateNoModel  rnm = null;
		int 	amID = -1;
		List<Integer>		list_huilu = null;	//		存放回路ID
		List<Integer>		list_amID = new ArrayList<Integer>();	//		存放电表ID
		AmMeterPDDatasDao  apdds = null;
		int  count5 = 0;      //平衡率小于5%的个数
		int  count510 = 0;    //平衡率大于5%小于10%的个数
		int  count1020 = 0;   //平衡率大于10%小于20%的个数
		int  count20 = 0;     //平衡率大于20%的个数
		int  count = 0;       //数据记录的条数
		float rate = 0;		  //数据合格率
		
		apdds = new AmMeterPDDatasDao();
		AmMeterPDDataDao	apdd = new AmMeterPDDataDao();
		
		rnm = new RateNoModel();
		
		list_huilu = apdds.getHuiluByParent(parent_ID);
		for(int i=0;i<list_huilu.size();i++)			//通过回路获取所有电表ID
		{	
			amID = apdd.getAmmeterIDByPdPartID(list_huilu.get(i));
			list_amID.add(amID);
		}
		
		for(int i=0;i<list_amID.size();i++)				//查出所有电表的平衡率个数累加
		{
			rnm = CountRateNo(list_amID.get(i),year, month, day, hour);
			count5 += rnm.getCount5();
			count510 +=rnm.getCount510();
			count1020 +=rnm.getCount1020();
			count20 +=rnm.getCount20();
			count +=rnm.getCount();
		}
		
		if(count != 0)		//计算合格率
		{
			rate = (count5 + count510)/count;
			BigDecimal   b  =   new BigDecimal(rate);
			rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  //四舍五入保留两位小数
		}else
		{
			rate = 0;
		}
		
		rnm = new RateNoModel();
		rnm.setCount(count);
		rnm.setCount5(count5);
		rnm.setCount510(count510);
		rnm.setCount1020(count1020);
		rnm.setCount20(count20);
		rnm.setRate(rate);
		
		
		
		return rnm;
	}
}
