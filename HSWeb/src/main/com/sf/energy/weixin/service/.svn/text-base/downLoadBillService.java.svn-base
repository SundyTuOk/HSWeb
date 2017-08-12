package com.sf.energy.weixin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.methods.downloadbill;
import com.sf.energy.weixin.methods.resendOrderByBill;
import com.sf.energy.weixin.models.domwLoadBillMap;
import com.sf.energy.weixin.utils.MapUtils;

public class downLoadBillService
{

	public  Map<String, Integer> resend_Order_By_Bill_Service(String queryStartDate,String queryEndDate){
		Map<String, Integer> map = null;
		Map<String, Integer> rmap = null;
		try
		{
			int count = daysBetween(queryStartDate,queryEndDate);
			if(count>=0){
				for(int i=0;i<=count;i++){
					String currdate = getAfterDay(queryStartDate,i);
					map = resendOrderByBill.resendOrder(currdate, "ALL");
					rmap = resendOrderByBill.addMap(rmap, map);
				}
				System.out.println(rmap.toString());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return rmap;
	}

	public String downLoad_Day_Bill_Service(String queryDate,String queryType){
		String str = null;
		str=downloadbill.billQuery(queryDate, queryType);
		str = MapUtils.translateStrByMap(new domwLoadBillMap().getMap(), str);
		return str;
	}
	public String refresh_Days_Bill_Service(String queryStartDate,String queryEndDate,String queryType){
		String str = null;	
		weixinDao wDao = new weixinDao();
		try
		{
			int count = daysBetween(queryStartDate,queryEndDate);
			if(count>=0){
				for(int i=0;i<=count;i++){
					String currdate = getAfterDay(queryStartDate,i);
					String datas=null;
					if(queryType.equals("ALL")){
						/**
						 * 由于数据库中只保存 ALL 所有订单 
						 * 所以 若查询类型为 ALL  则调用先查询数据库再取数据的方法
						 */
						//if(wDao.deleteDayBill(currdate)){
						wDao.deleteDayBill(currdate);
						datas = downloadbill.getBillData(currdate);
						//}
					}else{
						/**
						 * 否则直接调用查询微信数据的方法
						 */
						datas = downloadbill.checkbill(currdate, queryType);
					}
					if(i==0){
						str = datas;
					}else{
						str = downloadbill.addDatas(str, datas);
					}
				}
			}
			if(str!=null){
				str = MapUtils.translateStrByMap(new domwLoadBillMap().getMap(), str);
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return str;
	}
	public String downLoad_Days_Bill_Service(String queryStartDate,String queryEndDate,String queryType){
		String str = null;	
		try
		{
			int count = daysBetween(queryStartDate,queryEndDate);
			if(count>=0){
				for(int i=0;i<=count;i++){
					String currdate = getAfterDay(queryStartDate,i);
					String datas=null;
					if(queryType.equals("ALL")){
						datas = downloadbill.getBillData(currdate);
						//datas = downloadbill.checkbill(currdate, queryType);
					}else{
						datas = downloadbill.checkbill(currdate, queryType);
					}
					if(i==0){
						str = datas;
					}else{
						str = downloadbill.addDatas(str, datas);
					}
				}
			}
			if(str!=null){
				str = MapUtils.translateStrByMap(new domwLoadBillMap().getMap(), str);
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return str;
	}


	private int daysBetween(String smdate,String bdate) throws ParseException{ 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(sdf.parse(smdate)); 
		long time1 = cal.getTimeInMillis(); 
		cal.setTime(sdf.parse(bdate)); 
		long time2 = cal.getTimeInMillis(); 
		long between_days=(time2-time1)/(1000*3600*24); 
		return Integer.parseInt(String.valueOf(between_days)); 
	} 
	private String getAfterDay(String datestr,int i) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sf.parse(datestr));
		c.add(Calendar.DAY_OF_MONTH, i);
		return sf.format(c.getTime());
	}
	public static void main(String[] args) throws ParseException
	{
		String string=  new downLoadBillService().downLoad_Days_Bill_Service("20160821","20160822","ALL");
		System.out.println(string);
		//System.out.println(daysBetween("20160721","20160721"));
		//System.out.println(getTomorrowDay("20160721"));
	}
}
