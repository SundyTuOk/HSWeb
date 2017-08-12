package com.sf.energy.transfer.communication;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.sf.energy.util.ConnDB;
/**
 * 定时调用整理日信息
 * @author ky
 *
 */
public class TimeProcedureDay extends TimerTask
{
	private Connection conn=null;
	/**
	 * 整理每天的日电量，建筑用电和部门用电,时间以字符串的形式传递过去
	 * @throws SQLException
	 * 
	 */
	private void zLDayAm() throws SQLException{
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");		
		long n=date.getTime()+24*60*60*1000;
		Date date1=new Date(n);
		String d1=df.format(date);
		String d2=df.format(date1);
		 conn=ConnDB.getConnection();
		 ///创建存储过程对象
		 CallableStatement  c=conn.prepareCall("{ call VES_ZLDayAm(?,?) }");
		 c.setString(0, d1);
		 c.setString(1, d2);
		 c.execute();
		 conn.commit();
	}
	
	@Override
	public void run()
	{
		try
		{
			zLDayAm();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
	
}
