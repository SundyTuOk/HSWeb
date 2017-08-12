package com.sf.energy.transfer.communication;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.sf.energy.util.ConnDB;
/**
 * 整理月信息
 * @author ky
 *
 */
public class TimeProcedureMonth extends TimerTask
{
	private Connection conn=null;
	/**
	 * 整理月用电信息  时间以字符串的形式传递到存储过程中
	 * @throws SQLException 
	 */
	private void zLMonthAm() throws SQLException{
		 conn=ConnDB.getConnection();
		 Date date=new Date();
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");	
		 String d1=df.format(date);
		 long n=date.getTime()+30*24*60*60*1000;
		 Date date1=new Date(n);
		 String d2=df.format(date1);
		 ///创建存储过程对象
		 CallableStatement  c=conn.prepareCall("{ call VES_VES_ZLMonthAm(?,?) }");
		 c.setString(0,d1);
		 c.setString(1,d2);
		 c.execute();
		 conn.commit();
	}
	@Override
	public void run()
	{
		try
		{
			zLMonthAm();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
	
}
