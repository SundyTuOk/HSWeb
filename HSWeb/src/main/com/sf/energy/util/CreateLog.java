package com.sf.energy.util;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class CreateLog
{
	/**
	 * 写错误日志文件
	 * 
	 * @param logString
	 *            日志内容
	 */
	public static void writeLog(String logString)
	{
		try
		{
			String logFilePathName = null;
			Calendar cd = Calendar.getInstance();// 日志文件时间
			int year = cd.get(Calendar.YEAR);
			String month = addZero(cd.get(Calendar.MONTH) + 1);
			String day = addZero(cd.get(Calendar.DAY_OF_MONTH));
			String hour = addZero(cd.get(Calendar.HOUR_OF_DAY));
			String min = addZero(cd.get(Calendar.MINUTE));
			String sec = addZero(cd.get(Calendar.SECOND));

			File fileParentDir = new File("./log");// 判断log目录是否存在
			if (!fileParentDir.exists())
			{
				fileParentDir.mkdir();
			}

			logFilePathName = "./log/" + year + month + day + ".log";// 日志文件名

			PrintWriter printWriter = new PrintWriter(new FileOutputStream(
					logFilePathName, true));// 紧接文件尾写入日志字符串
			String time = "[" + year + month + day + "-" + hour + ":" + min
					+ ":" + sec + "] ";
			printWriter.println(time + logString);
			printWriter.flush();

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}

	/**
	 * 日月时分秒以两位形式输出，不足为用“0”补足。
	 * 
	 * @param i
	 *            日月时分秒
	 * @return 日月时分秒的两位形式字符串
	 */
	public static String addZero(int i)
	{
		if (i < 10)
		{
			String tmpString = "0" + i;
			return tmpString;
		} else
		{
			return String.valueOf(i);
		}
	}

}
