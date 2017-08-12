package com.sf.energy.project.system.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class YCSJXF1Dao
{

	DateFormat df1 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 * @param nodeInText
	 * @param PartList
	 * @param limit 分页参数  为每页条数限制
	 * @param offset 查询起始位置
	 * @return
	 */
	public JSONObject SerGetInfoZ5052(String AreaID,String ArchID, String MeterStyle,
			String txtStart,String txtEnd,
			String txtJE,String txtGross, int offset,int limit){
		int count = getCount(AreaID, ArchID, MeterStyle, txtStart, txtEnd, txtJE, txtGross);
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("total", count);
		if(count==0){
			obj.put("rows", arr);
			return obj;
		}
		String strSelect2 = "";
		if (AreaID.equals("-1")&& ArchID.equals("-1")) strSelect2 = " ";
		if (!AreaID.equals("-1") && ArchID.equals("-1") ) strSelect2 = String.format(" and (Area_ID=%s )", AreaID);
		if (!ArchID.equals("-1") ) strSelect2 = String.format(" and (Architecture_ID=%s)", ArchID.equals("-1") );
		/**
		 * 分页查询语句还可以优化
		 */
		String sql = "select * FROM (SELECT T.*,ROWNUM rowno from (select a." + MeterStyle + "Meter_ID  Meter_ID,(select Area_Name from Area  c where A.Area_ID=c.Area_ID)Area_Name,(select Architecture_Name from Architecture  c where A.Architecture_ID=c.Architecture_ID)Architecture_Name," + MeterStyle + "Meter_Name  Meter_Name," + MeterStyle + "Meter_485Address  Meter_485Address,T.theDATEDIFF,(to_char(SelectYear,'9999')||'-'||to_char(SelectMonth,'99')||'-'||to_char(SelectDay,'99'))TheData, StarReadingDate ,EndReadingDate,StarZValueZY,EndZValueZY,ZGross  from " +
				"(select " + MeterStyle + "Meter_ID,trunc( EndReadingDate-StarReadingDate)theDATEDIFF,SelectYear ,SelectMonth,SelectDay , to_char(StarReadingDate,'YYYY-MM-DD HH24:MI:SS')StarReadingDate ,to_char(EndReadingDate,'YYYY-MM-DD HH24:MI:SS')EndReadingDate,StarZValueZY,EndZValueZY,ZGross   from T_Day" + MeterStyle + " where  "+
				" to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd') >= to_Date('"+txtStart+"','yyyy-mm-dd') "
				+"and to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd') <= to_Date('"+txtEnd+"','yyyy-mm-dd'))T," + MeterStyle + "meter  a " +
				"where theDATEDIFF>"+txtJE +" and ZGross >"+txtGross +" and T." + MeterStyle + "Meter_ID=a." + MeterStyle + "Meter_ID " + strSelect2
				+") T ) where rowno>"+offset+" and rowno<="+(limit+offset);
		//System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();			
			while (rs.next())
			{
				JSONObject jo = new JSONObject();
				jo.put("Meter_ID",rs.getString("Meter_ID") );
				jo.put("Area_Name",rs.getString("Area_Name") );
				jo.put("Architecture_Name",rs.getString("Architecture_Name") );
				jo.put("Meter_Name", rs.getString("Meter_Name"));
				jo.put("Meter_485Address", rs.getString("Meter_485Address"));
				jo.put("theDATEDIFF", rs.getString("theDATEDIFF"));
				jo.put("TheData", rs.getString("TheData"));
				jo.put("StarReadingDate", rs.getString("StarReadingDate"));
				jo.put("StarZValueZY", rs.getString("StarZValueZY"));
				jo.put("EndReadingDate",rs.getString("EndReadingDate") );
				jo.put("EndZValueZY",rs.getString("EndZValueZY") );
				jo.put("ZGross",rs.getString("ZGross") );
				arr.add(jo);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		obj.put("rows", arr);
		return obj;
	}
	public int getCount(String AreaID,String ArchID, String MeterStyle,
			String txtStart,String txtEnd,
			String txtJE,String txtGross){
		int count = 0;
		String strSelect2 = "";
		if (AreaID.equals("-1")&& ArchID.equals("-1")) strSelect2 = " ";
		if (!AreaID.equals("-1") && ArchID.equals("-1") ) strSelect2 = String.format(" and (Area_ID=%s )", AreaID);
		if (!ArchID.equals("-1") ) strSelect2 = String.format(" and (Architecture_ID=%s)", ArchID.equals("-1") );

		String sql = "select count(*) from (select a." + MeterStyle + "Meter_ID  Meter_ID,(select Area_Name from Area  c where A.Area_ID=c.Area_ID)Area_Name,(select Architecture_Name from Architecture  c where A.Architecture_ID=c.Architecture_ID)Architecture_Name," + MeterStyle + "Meter_Name  Meter_Name," + MeterStyle + "Meter_485Address  Meter_485Address,T.theDATEDIFF,(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'))TheData, StarReadingDate ,EndReadingDate,StarZValueZY,EndZValueZY,ZGross  from " +
				"(select " + MeterStyle + "Meter_ID,trunc( EndReadingDate-StarReadingDate)theDATEDIFF,SelectYear ,SelectMonth,SelectDay , to_char(StarReadingDate,'YYYY-MM-DD HH24:MI:SS')StarReadingDate ,to_char(EndReadingDate,'YYYY-MM-DD HH24:MI:SS')EndReadingDate,StarZValueZY,EndZValueZY,ZGross   from T_Day" + MeterStyle + " where  "+
				" to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd') >= to_Date('"+txtStart+"','yyyy-mm-dd') "
				+"and to_date(to_char(SelectYear,'9999')||to_char(SelectMonth,'99')||to_char(SelectDay,'99'),'yyyy-mm-dd') <= to_Date('"+txtEnd+"','yyyy-mm-dd'))T," + MeterStyle + "meter  a " +
				"where theDATEDIFF>"+txtJE +" and ZGross >"+txtGross +" and T." + MeterStyle + "Meter_ID=a." + MeterStyle + "Meter_ID " + strSelect2+" )";		
		//System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return count;

	}
	public String  SerXZData(String Meter_ID, String MeterStyle, String txtStartXZ, String txtEndXZ, String txtStarValue, String txtEndValue) throws SQLException, ParseException
	{


		String sql = "delete from Z" + MeterStyle + "Datas" + Meter_ID + "  where   ValueTime> to_Date('"+txtStartXZ+"','YYYY-MM-DD HH24:MI:SS')   and ValueTime< to_Date('"+txtEndXZ+"','YYYY-MM-DD HH24:MI:SS')  ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeQuery();

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strTable = "";
		Date StartTime = df.parse(txtStartXZ);
		//DateTime StartTime = DateTime.Parse(txtStartXZ);
		// DateTime EndTime = DateTime.Parse(txtEndXZ);
		Date EndTime = df.parse(txtEndXZ);

		Double Gross = Double.parseDouble(txtEndValue) - Double.parseDouble(txtStarValue);
		Double TheValue = Double.parseDouble(txtStarValue);
		Date theTime = StartTime;
		while (theTime.before(EndTime))
		{
			long diff = EndTime.getTime() - theTime.getTime();//这样得到的差值是微秒级别
			// TimeSpan ND = EndTime - theTime;
			long TotalD = diff / (1000 * 60 * 60 * 24);

			// int TotalD = (int)ND.TotalDays;
			if (TotalD < 1)
				TotalD = 1;
			Double PGross = Gross / TotalD;//每天平均电量
			//计算每天的电量
			Random ran = new Random();
			int RandKey = ran.nextInt(40)+80;
			//  int RandKey = ran.Next(80, 120);
			Double TheGross = (PGross * RandKey / 100);
			Calendar cal = Calendar.getInstance();
			cal.setTime(theTime);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

			if (w == 0 || w== 6)//周末用电量小
			{
				//  RandKey = ran.Next(60, 100);
				RandKey = ran.nextInt(40)+60;
				TheGross = TheGross * RandKey / 100;
			}
			Date now=new Date();
			long diffNDT = now.getTime() - theTime.getTime();//这样得到的差值是微秒级别
			//TimeSpan NDT = DateTime.Now - theTime;
			long NDT = diff / (1000 * 60 * 60 * 24);
			if (TotalD <=3 && NDT <= 3)
			{
				//最后3天并且接近当前时间 加载小时电量
				TheGross = TheGross / 24;
				//如果是非工作用量减少
				cal.setTime(theTime);//给Calendar对象设置时间
				int hour = cal.get(Calendar.HOUR_OF_DAY);//取得当前时间的时数
				if (hour< 8 || hour > 18)
				{ 
					RandKey = ran.nextInt(40)+60;
					//RandKey = ran.Next(60, 100);
					TheGross = TheGross * RandKey / 100;
				}

				//theTime = theTime.AddHours(1);
				cal.setTime(theTime);
				cal.add(Calendar.HOUR_OF_DAY, 1);
				theTime=cal.getTime();
			}
			else
			{
				cal.setTime(theTime);//给Calendar对象设置时间
				int hour = cal.get(Calendar.HOUR_OF_DAY);//取得当前时间的时数
				if (hour < 23)//第一天的数据
				{
					TheGross = TheGross * (24 -hour) / 24;     

					//theTime = theTime.AddHours(23 - hour);
					cal.add(Calendar.HOUR_OF_DAY, 23 - hour);
					theTime=cal.getTime();
				}
				else
				{
					//  theTime = theTime.AddDays(1);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					theTime=cal.getTime();
				}
			}

			java.text.DecimalFormat   dff   =new   java.text.DecimalFormat("#.00");  
			String    strTheGross   =  dff.format(TheGross);
			// string strTheGross = TheGross.ToString("f2");
			Gross = Gross - Double.parseDouble(strTheGross);
			TheValue += Double.parseDouble(strTheGross);


			if (TheValue <= Double.parseDouble(txtEndValue) && (theTime.before(EndTime)))
			{

				try
				{
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql="insert into  Z" + MeterStyle + "Datas" + Meter_ID + "(ValueTime,ZValueZY) values( to_Date('"+df.format(theTime)+"','YYYY-MM-DD HH24:MI:SS') ,'" + dff.format(TheValue) + "')";
					conn = ConnDB.getConnection();
					ps = conn.prepareStatement(sql);
					ps.executeQuery();

				} catch (Exception e)
				{
					//e.printStackTrace();

				}finally{
					ConnDB.release(conn, ps, rs);
				}
			}
			// CommonCs.ExecSqlText("insert into  Z" + MeterStyle + "Datas" + Meter_ID + "(ValueTime,ZValueZY) values('" + theTime.ToString("yyyy-MM-dd HH:mm") + "','" + TheValue.ToString("f2") + "')");
		}

		CallableStatement c=null;
		//整理这个时间段内的数据
		try
		{
			String sStartXZ=txtStartXZ.split(" ")[0];
			String sEndXZ=txtEndXZ.split(" ")[0];
			//sql="exec VES_ZLDay" + MeterStyle + "Meter '" + txtStartXZ  + "','" + txtEndXZ + "'," + Meter_ID;
			conn = ConnDB.getConnection();
			//ps = conn.prepareStatement(sql);
			//ps.executeQuery();
			c = conn.prepareCall("{ call VES_ZLDay" + MeterStyle + "Meter(?,?,?) }");
			c.setString(1,sStartXZ);
			c.setString(2,sEndXZ );
			c.setString(3, Meter_ID);
			c.execute();
			conn.commit();

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//CommonCs.ExecSqlText("exec VES_ZLDay" + MeterStyle + "Meter '" + txtStartXZ  + "','" + txtEndXZ + "'," + Meter_ID);
		strTable = "处理成功！";
		return strTable;
	}

}
