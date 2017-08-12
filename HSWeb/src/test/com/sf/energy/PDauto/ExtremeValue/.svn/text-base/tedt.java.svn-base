package com.sf.energy.PDauto.ExtremeValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tedt {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String s="2014-12-01";
		String d="2014-12-03";
		Date sDate=df.parse(s);
		Date eDate=df.parse(d);
		
		int a=getListIndex(sDate, eDate, "2014-12-02", 0);
		System.out.println(a);
		
	}
	static int getListIndex(Date begindate,Date endDate,String date,int hour) throws ParseException
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date temp=df.parse(date);
		int result=-1;
		if ((temp.getTime()-begindate.getTime())==0) {
			result=hour;
		} else if((temp.getTime()-begindate.getTime())>0&&(endDate.getTime()-temp.getTime())>0){
			result=hour+24;
		}
		else
		{
			result=hour+48;
		}
		return result;
	}

}
