package com.sf.energy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOperation {
	
	/**
	 * 		通过输入年月，查询该月份里最大的天数
	 * @param year		年份
	 * @param month		月份
	 * @return			结果集
	 * @throws ParseException
	 */
	public int queryMaxDayinMonth(int year,int month) throws ParseException
	{
		int MaxDay = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		
		if(month < 10)
		{
			Date dd = sdf.parse(year+"0"+month);
			cal.setTime(dd);
		}
		else
		{
			Date dd = sdf.parse(year+""+month);
			cal.setTime(dd);
		}
		 
		MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return MaxDay;
	}

}
