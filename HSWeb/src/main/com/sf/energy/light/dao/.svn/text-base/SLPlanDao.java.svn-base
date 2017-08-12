package com.sf.energy.light.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sf.energy.light.model.SLPlanModel;
import com.sf.energy.util.ConnDB;

public class SLPlanDao
{
	int total = 0;

	/**
	 * 查
	 * 
	 * @param pageCount
	 *            分页码
	 * @param pageIndex
	 *            页号
	 * @param allID
	 *            全校ID
	 * @param areaID
	 *            区域ID
	 * @param lineID
	 *            回路ID
	 * @param lampID
	 *            单灯ID
	 * @param month 
	 * @param year 
	 * @param order 
	 * @param tableName 
	 * @return 信息数组
	 * @throws SQLException
	 */
	public List<SLPlanModel> paginate(Integer pageCount, Integer pageIndex,
			int allID, int areaID, int lineID, int lampID, int year, int month, String tableName, String order) throws SQLException
			{
		int type = 0;
		ArrayList<SLPlanModel> list = new ArrayList<SLPlanModel>();
		String sql = "select * from slschedule";
		if (allID == 1)
		{
			type = 1;
			sql += " where type=" + type+" and YYYY="+year+" and MM="+month;
		}

		if (allID == -1 && areaID != -1 && lineID == -1 && lampID == -1)
		{
			type = 2;
			sql += " where type=" + type + " and AREA_ID=" + areaID+" and YYYY="+year+" and MM="+month;
		}

		if (allID == -1 && areaID != -1 && lineID != -1 && lampID == -1)
		{
			type = 3;
			//			sql += " where type=" + type + " and AREA_ID=" + areaID
			//					+ " and SLLINE_ID=" + lineID+" and YYYY="+year+" and MM="+month;
			sql += " where type=" + type 
					+ " and SLLINE_ID=" + lineID+" and YYYY="+year+" and MM="+month;
		}

		if (allID == -1 && areaID != -1 && lineID != -1 && lampID != -1)
		{
			type = 4;
			//			sql += " where type=" + type + " and AREA_ID=" + areaID
			//					+ " and SLLINE_ID=" + lineID + " and SLLAMP_ID=" + lampID+" and YYYY="+year+" and MM="+month;
			sql += " where type=" + type 
					+ " and SLLINE_ID=" + lineID + " and SLLAMP_ID=" + lampID+" and YYYY="+year+" and MM="+month;
		}
		sql+=" order by "+tableName+" "+order;
		// System.out.println(sql);
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			setTotal(count);

			if (count <= (pageCount * pageIndex))
				return list;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
				count = pageCount;

			if ((pageCount * pageIndex) == 0)
				rs.beforeFirst();
			else
				rs.absolute(pageCount * pageIndex);

			while (rs.next() && (count > 0))
			{
				SLPlanModel mi = new SLPlanModel();

				mi.setSWITCHSCHEDULEID(rs.getString("SWITCHSCHEDULEID"));
				mi.setTYPE(rs.getString("TYPE"));
				mi.setAREA_ID(rs.getString("AREA_ID"));
				mi.setSLLINE_ID(rs.getString("SLLINE_ID"));
				mi.setSLLAMP_ID(rs.getString("SLLAMP_ID"));

				mi.setYYYY(rs.getString("YYYY"));
				mi.setMM(rs.getString("MM"));
				mi.setDD(rs.getString("DD"));

				mi.setONHH1(rs.getString("ONHH1"));
				mi.setONMM1(rs.getString("ONMM1"));
				mi.setOFFHH1(rs.getString("OFFHH1"));
				mi.setOFFMM1(rs.getString("OFFMM1"));

				mi.setONHH2(rs.getString("ONHH2"));
				mi.setONMM2(rs.getString("ONMM2"));
				mi.setOFFHH2(rs.getString("OFFHH2"));
				mi.setOFFMM2(rs.getString("OFFMM2"));

				list.add(mi);
				count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

		return list;
			}
	/**
	 * 改
	 * @param model 数据集
	 * @return
	 * @throws SQLException 
	 */
	private int updateSLPlanByID(SLPlanModel model) throws SQLException
	{
		String theID=model.getSWITCHSCHEDULEID();
		String onHH1 = model.getONHH1();
		String onMM1 = model.getONMM1();
		String offHH1 = model.getOFFHH1();
		String offMM1 = model.getOFFMM1();
		String onHH2 = model.getONHH2();
		String onMM2 = model.getONMM2();
		String offHH2 = model.getOFFHH2();
		String offMM2 = model.getOFFMM2();

		String sql="update slschedule set ONHH1="+onHH1+",ONMM1="+onMM1+",OFFHH1="+offHH1+",OFFMM1="+offMM1;

		if (!"".equals(onHH2) && !"".equals(onMM2) && !"".equals(offHH2)
				&& !"".equals(offMM2))
			sql+=",ONHH2="+onHH2+",ONMM2="+onMM2+",OFFHH2="+offHH2+",OFFMM2="+offMM2;
		else{
			sql+=",ONHH2=null,ONMM2=null,OFFHH2=null,OFFMM2=null";
		}
		sql+=" where SWITCHSCHEDULEID="+theID;

		Connection conn=null;
		PreparedStatement ps =null;
		int k=0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			k = ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
		return k;
	}
	/**
	 * 删
	 * 
	 * @param scheduleID
	 *            计划ID
	 * @throws SQLException
	 */
	public void deleteSLPlanByID(String scheduleID) throws SQLException
	{
		String sql = "delete from slschedule where SWITCHSCHEDULEID="
				+ scheduleID;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}
	}

	/**
	 * 增
	 * 
	 * @param model
	 *            数据集
	 * @return
	 * @throws SQLException
	 */
	private int addSLPanl(SLPlanModel model) throws SQLException
	{

		String type = model.getTYPE();
		String areaID = model.getAREA_ID();
		String lineID = model.getSLLINE_ID();
		String lampID = model.getSLLAMP_ID();

		String year = model.getYYYY();
		String month = model.getMM();
		String day = model.getDD();
		String onHH1 = model.getONHH1();
		String onMM1 = model.getONMM1();
		String offHH1 = model.getOFFHH1();
		String offMM1 = model.getOFFMM1();
		String onHH2 = model.getONHH2();
		String onMM2 = model.getONMM2();
		String offHH2 = model.getOFFHH2();
		String offMM2 = model.getOFFMM2();

		String sql = "insert into slschedule (TYPE,AREA_ID,SLLINE_ID,SLLAMP_ID,YYYY,MM,DD,ONHH1,ONMM1,OFFHH1,OFFMM1,ONHH2,ONMM2,OFFHH2,OFFMM2) values("
				+ type + ",";

		// 拼接3个ID
		if ("".equals(type))
		{
			//System.out.println("没有Type");
			return 0;
		}

		if ("1".equals(type))
			sql += "null,null,null,";

		if ("2".equals(type))
			sql += areaID + ",null,null,";

		if ("3".equals(type))
			sql += areaID + "," + lineID + ",null,";

		if ("4".equals(type))
			sql += areaID + "," + lineID + "," + lampID + ",";

		// 拼接日期
		sql += year + "," + month + "," + day + ",";

		// 拼接前两个时间
		sql += onHH1 + "," + onMM1 + "," + offHH1 + "," + offMM1 + ",";

		// 拼接后两个时间
		if (!"".equals(onHH2) && !"".equals(onMM2) && !"".equals(offHH2)
				&& !"".equals(offMM2))
			sql += onHH2 + "," + onMM2 + "," + offHH2 + "," + offMM2;
		else
			sql += "null,null,null,null";
		sql += ")";

		Connection conn=null;
		PreparedStatement ps =null;
		int k=0;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			k = ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

		return k;

	}

	/**
	 * 修改改计划任务
	 * @param theID 			要修改的ID
	 * @param openAtNightfall	傍晚开灯时间
	 * @param offAtNightfall	傍晚熄灯时间
	 * @param openAtMorning		早上开灯时间
	 * @param offAtMorning		早上熄灯时间
	 * @return
	 * @throws SQLException 
	 */
	public int updateSLPlan(String theID, String openAtNightfall,
			String offAtNightfall, String openAtMorning, String offAtMorning) throws SQLException
	{
		String[] str = null;
		SLPlanModel model = new SLPlanModel();

		model.setSWITCHSCHEDULEID(theID);
		str = openAtNightfall.split(":");
		model.setONHH1(str[0]);
		model.setONMM1(str[1]);

		str = offAtNightfall.split(":");
		model.setOFFHH1(str[0]);
		model.setOFFMM1(str[1]);

		if (!"".equals(openAtMorning) && openAtMorning != null)
		{
			str = openAtMorning.split(":");
			model.setONHH2(str[0]);
			model.setONMM2(str[1]);
		}

		if (!"".equals(offAtMorning) && offAtMorning != null)
		{
			str = offAtMorning.split(":");
			model.setOFFHH2(str[0]);
			model.setOFFMM2(str[1]);
		}
		int k=updateSLPlanByID(model);
		int result=1;
		if(k!=1)
			result=0;
		return result;
	}


	/**
	 * 期间添加方式添加路灯计划任务
	 * 
	 * @param startTime
	 * @param endTime
	 * @param openAtNightfall
	 * @param offAtNightfall
	 * @param openAtMorning
	 * @param offAtMorning
	 * @param type
	 * @param lampID
	 * @param lineID
	 * @param areaID
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public int addSLPlanStyle1(String startTime, String endTime,
			String openAtNightfall, String offAtNightfall,
			String openAtMorning, String offAtMorning, String type,
			String areaID, String lineID, String lampID) throws ParseException,
			SQLException
	{

		deleteBetweenTwoDays(startTime,endTime,type,areaID,lineID,lampID);
		int result = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		Date time = sdf.parse(startTime);
		// 计算天数
		count = daysBetween(startTime, endTime);
		if(count==0)
		{
			result = 0;
		}
		SLPlanModel model = null;
		String[] str = null;

		for (int i = 0; i < count; i++)
		{

			model = new SLPlanModel();
			model.setTYPE(type);
			model.setAREA_ID(areaID);
			model.setSLLINE_ID(lineID);
			model.setSLLAMP_ID(lampID);
			model.setYYYY(getYYYY(time));
			model.setMM(getMM(time));
			model.setDD(getDD(time));

			str = openAtNightfall.split(":");
			model.setONHH1(str[0]);
			model.setONMM1(str[1]);

			str = offAtNightfall.split(":");
			model.setOFFHH1(str[0]);
			model.setOFFMM1(str[1]);

			if (!"".equals(openAtMorning) && openAtMorning != null)
			{
				str = openAtMorning.split(":");
				model.setONHH2(str[0]);
				model.setONMM2(str[1]);
			}

			if (!"".equals(offAtMorning) && offAtMorning != null)
			{
				str = offAtMorning.split(":");
				model.setOFFHH2(str[0]);
				model.setOFFMM2(str[1]);
			}
			int k = addSLPanl(model);
			if (k != 1)
			{
				result = 0;
				deleteBetweenTwoDays(startTime,endTime,type,areaID,lineID,lampID);
				break;
			}


			time=addDate(time, 1);

		}
		return result;

	}
	private void deleteBetweenTwoDays(String startTime, String endTime,String type,
			String areaID, String lineID, String lampID) throws SQLException
	{

		String sql="delete from slschedule where to_date(yyyy||'-'||MM||'-'||dd,'yyyy-mm-dd')>=to_date('"+startTime+"','yyyy-mm-dd') and to_date(yyyy||'-'||MM||'-'||dd,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		if ("1".equals(type))
			sql += " and type=1";

		if ("2".equals(type))
			sql +=" and type=2 and area_ID="+areaID;

		if ("3".equals(type))
			sql += " and type=3 and area_ID="+areaID + " and SLLine_ID=" + lineID;

		if ("4".equals(type))
			sql += " and type=4 and area_ID="+areaID + " and SLLine_ID=" + lineID+" and SLLAMP_ID="+lampID;
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}


	}

	private void deleteBetweenTwoDays(String startTime, String endTime) throws SQLException
	{
		String sql="delete from slschedule where to_date(yyyy||'-'||MM||'-'||dd,'yyyy-mm-dd')>=to_date('"+startTime+"','yyyy-mm-dd') and to_date(yyyy||'-'||MM||'-'||dd,'yyyy-mm-dd')<=to_date('"+endTime+"','yyyy-mm-dd') ";
		Connection conn=null;
		PreparedStatement ps =null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps);
		}

	}
	/**
	 * 按星期循环添加方式添加路灯计划任务
	 * 
	 * @param startTime
	 * @param endTime
	 * @param openAtNightfall
	 * @param offAtNightfall
	 * @param openAtMorning
	 * @param offAtMorning
	 * @param satOpenAtNightfall
	 * @param satOffAtNightfall
	 * @param satOpenAtMorning
	 * @param satOffAtMorning
	 * @param sunOpenAtNightfall
	 * @param sunOffAtNightfall
	 * @param sunOpenAtMorning
	 * @param sunOffAtMorning
	 * @param type
	 * @param areaID
	 * @param lineID
	 * @param lampID
	 * @return 
	 * @throws ParseException
	 * @throws SQLException
	 */
	public int addSLPlanStyle2(String startTime, String endTime,
			String openAtNightfall, String offAtNightfall,
			String openAtMorning, String offAtMorning,
			String satOpenAtNightfall, String satOffAtNightfall,
			String satOpenAtMorning, String satOffAtMorning,
			String sunOpenAtNightfall, String sunOffAtNightfall,
			String sunOpenAtMorning, String sunOffAtMorning, String type,
			String areaID, String lineID, String lampID) throws ParseException,
			SQLException
	{
		deleteBetweenTwoDays(startTime,endTime,type,areaID,lineID,lampID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		Date time = sdf.parse(startTime);
		// 计算天数
		count = daysBetween(startTime, endTime);
		SLPlanModel model = null;

		int result = 1;
		for (int i = 0; i < count; i++)
		{

			String weekDay = getWeekDay(time);

			switch (weekDay)
			{
			case "星期一":
			case "星期二":
			case "星期三":
			case "星期四":
			case "星期五":
				model = makeModel(time, openAtNightfall, offAtNightfall,
						openAtMorning, offAtMorning, type, areaID, lineID,
						lampID);
				break;
			case "星期六":
				model = makeModel(time, satOpenAtNightfall, satOffAtNightfall,
						satOpenAtMorning, satOffAtMorning, type, areaID,
						lineID, lampID);
				break;
			case "星期日":
				model = makeModel(time, sunOpenAtNightfall, sunOffAtNightfall,
						sunOpenAtMorning, sunOffAtMorning, type, areaID,
						lineID, lampID);
				break;
			}
			int k = addSLPanl(model);
			if (k != 1)
			{
				result = 0;
				deleteBetweenTwoDays(startTime,endTime,type,areaID,lineID,lampID);
				break;
			}
			time=addDate(time, 1);

		}
		return result;

	}
	public SLPlanModel querySLPlanModelInfo(int SLId){
		SLPlanModel model = null;
		String sql="SELECT SWITCHSCHEDULEID,YYYY||'-'||MM||'-'||DD DAY, ONHH1||':'||ONMM1 ON1, OFFHH1||':'||OFFMM1 OFF1, ONHH2||':'||ONMM2 ON2, OFFHH2||':'||OFFMM2 OFF2 "
				+ "FROM SLSCHEDULE WHERE SWITCHSCHEDULEID = ?";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SLId);
			rs = ps.executeQuery();
			if(rs.next()){
				model = new SLPlanModel();
				model.setSWITCHSCHEDULEID(rs.getString("SWITCHSCHEDULEID"));
				model.setDD(rs.getString("DAY"));
				model.setONHH1(rs.getString("ON1"));
				model.setOFFHH1(rs.getString("OFF1"));
				model.setONHH2(rs.getString("ON2"));
				model.setOFFHH2(rs.getString("OFF2"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return model;
	}
	private SLPlanModel makeModel(Date time, String openAtNightfall,
			String offAtNightfall, String openAtMorning, String offAtMorning,
			String type, String areaID, String lineID, String lampID)
	{
		String[] str = null;
		SLPlanModel model = new SLPlanModel();

		model.setTYPE(type);
		model.setAREA_ID(areaID);
		model.setSLLINE_ID(lineID);
		model.setSLLAMP_ID(lampID);
		model.setYYYY(getYYYY(time));
		model.setMM(getMM(time));
		model.setDD(getDD(time));

		str = openAtNightfall.split(":");
		model.setONHH1(str[0]);
		model.setONMM1(str[1]);

		str = offAtNightfall.split(":");
		model.setOFFHH1(str[0]);
		model.setOFFMM1(str[1]);

		if (!"".equals(openAtMorning) && openAtMorning != null)
		{
			str = openAtMorning.split(":");
			model.setONHH2(str[0]);
			model.setONMM2(str[1]);
		}

		if (!"".equals(offAtMorning) && offAtMorning != null)
		{
			str = offAtMorning.split(":");
			model.setOFFHH2(str[0]);
			model.setOFFMM2(str[1]);
		}

		return model;
	}

	/**
	 * 输入日期计算星期几
	 * 
	 * @param DateStr
	 *            日期字符串
	 * @return
	 */
	private String getWeekDay(Date DateStr)
	{

		SimpleDateFormat formatD = new SimpleDateFormat("E");// "E"表示"day in week"

		String weekDay = formatD.format(DateStr);
		return weekDay;
	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 天数
	 * @throws ParseException
	 */
	private int daysBetween(String smdate, String bdate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 日期增加n天。
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            增加的天数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addDate(Date date, int days) throws ParseException
	{

		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.DATE, days);
		return cld.getTime();
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private String getYYYY(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		return year;

	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private String getMM(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String year = sdf.format(date);
		return year;
	}

	/**
	 * 获取日号
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private String getDD(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String year = sdf.format(date);
		return year;
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
