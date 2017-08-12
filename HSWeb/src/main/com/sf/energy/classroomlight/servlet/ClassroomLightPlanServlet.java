package com.sf.energy.classroomlight.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.classroomlight.dao.LightRoomPlanDao;
import com.sf.energy.classroomlight.model.LightRoomPlanModel;

public class ClassroomLightPlanServlet extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getClassroomLightPlan".equals(method))
			getClassroomLightPlan(request, response);
		
		if ("addCLPlanByPeriod".equals(method))
			addCLPlanByPeriod(request, response);	
		
		if ("addCLPlanByWeek".equals(method))
			addCLPlanByWeek(request, response);
		
		if ("deletCLPlan".equals(method))
			deletCLPlan(request, response);
		
		if ("updateCLPlan".equals(method))
			updateCLPlan(request, response);
	}

	private void updateCLPlan(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{	LightRoomPlanDao lrpd = new LightRoomPlanDao();
		int theID = Integer.parseInt(request.getParameter("SWITCHSCHEDULEID"));
		String openTime = request.getParameter("openTime");
		String clearTime = request.getParameter("clearTime");
		String closeTime = request.getParameter("closeTime");
		String adjustTime = request.getParameter("adjustTime");
		
		String[] str = null;
		LightRoomPlanModel lrpm = new LightRoomPlanModel();
		
		lrpm.setSWITCHSCHEDULEID(theID);
		
		str = openTime.split(":");
		lrpm.setONHH(Integer.parseInt(str[0]));
		lrpm.setONMM(Integer.parseInt(str[1]));
		
		str = clearTime.split(":");
		lrpm.setCLEARHH(Integer.parseInt(str[0]));
		lrpm.setCLEARMM(Integer.parseInt(str[1]));
		
		str = closeTime.split(":");
		lrpm.setOFFHH(Integer.parseInt(str[0]));
		lrpm.setOFFMM(Integer.parseInt(str[1]));
		
		str = adjustTime.split(":");
		lrpm.setADJUSTHH(Integer.parseInt(str[0]));
		lrpm.setADJUSTMM(Integer.parseInt(str[1]));	
		
		String info = "";
		
		if(lrpd.modify(lrpm))
		{
			info = "修改成功";
		}
		else
		{
			info = "修改失败";
		}
		
		PrintWriter pt = response.getWriter();
		pt.println(info);
		pt.flush();
		pt.close();
	}

	private void deletCLPlan(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		
		LightRoomPlanDao lrpd = new LightRoomPlanDao();
		String arrayOfID = request.getParameter("SWITCHSCHEDULEID");

		String[] scheduleID = null;
		scheduleID = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < scheduleID.length; i++)
		{
			if(lrpd.delete(Integer.parseInt(scheduleID[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
	}

	private void addCLPlanByWeek(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		LightRoomPlanDao lrpd = new LightRoomPlanDao();
		LightRoomPlanModel lrpm = new LightRoomPlanModel();
		
		int type = Integer.parseInt(request.getParameter("type"));
		int areaID = Integer.parseInt(request.getParameter("areaID"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		int roomID = Integer.parseInt(request.getParameter("roomID"));
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		String weekdayCLOpenTime = request.getParameter("weekdayCLOpenTime");
		String weekdayCLClearTime = request.getParameter("weekdayCLClearTime");
		String weekdayCLCloseTime = request.getParameter("weekdayCLCloseTime");
		String weekdayCLAdjustTime = request.getParameter("weekdayCLAdjustTime");

		String satCLOpenTime = request.getParameter("satCLOpenTime");
		String satCLClearTime = request.getParameter("satCLClearTime");
		String satCLCloseTime = request.getParameter("satCLCloseTime");
		String satCLAdjustTime = request.getParameter("satCLAdjustTime");

		String sunCLOpenTime = request.getParameter("sunCLOpenTime");
		String sunCLClearTime = request.getParameter("sunCLClearTime");
		String sunCLCloseTime = request.getParameter("sunCLCloseTime");
		String sunCLAdjustTime = request.getParameter("sunCLAdjustTime");
		
		String[] str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		Date time = sdf.parse(startDate);
		// 计算天数
		count = daysBetween(startDate, endDate);
		
		// 统计成功插入条数
		int result = 0;
		
		for (int i = 0; i < count; i++)
		{
			//lrpm = new LightRoomPlanModel();
			
			String weekDay = getWeekDay(time);
			switch (weekDay)
			{
			case "星期一":
			case "星期二":
			case "星期三":
			case "星期四":
			case "星期五":
				lrpm = setModel(type, areaID, archID, floor, roomID, time, weekdayCLOpenTime, weekdayCLClearTime,
						weekdayCLCloseTime, weekdayCLAdjustTime);
				break;
			case "星期六":
				lrpm = setModel(type, areaID, archID, floor, roomID, time, satCLOpenTime, weekdayCLClearTime,
						satCLCloseTime, satCLAdjustTime);
				break;
			case "星期日":
				lrpm = setModel(type, areaID, archID, floor, roomID, time, sunCLOpenTime, sunCLClearTime, sunCLCloseTime,
						sunCLAdjustTime);
				break;
			default:
				break;
			}

			if (lrpd.insert(lrpm))
			{
				result++;
			}

			time = addDate(time, 1);
		}

		PrintWriter out = response.getWriter();
		out.println("成功插入" + result + "条数据。");
		out.flush();
		out.close();
	}

	private LightRoomPlanModel setModel(int type, int areaID, int archID,
			int floor, int roomID, Date time,
			String openTime, String clearTime, String closeTime,
			String adjustTime)
	{
		String[] str = null;
		LightRoomPlanModel tempModel = new LightRoomPlanModel();
		
		tempModel.setTYPE(type);
		tempModel.setAREA_ID(areaID);
		tempModel.setARCHITECTURE_ID(archID);
		tempModel.setFLOOR(floor);
		tempModel.setLIGHTROOMNO(roomID);
		tempModel.setYYYY(getYYYY(time));
		tempModel.setMM(getMM(time));
		tempModel.setDD(getDD(time));
		
		str = openTime.split(":");
		tempModel.setONHH(Integer.parseInt(str[0]));
		tempModel.setONMM(Integer.parseInt(str[1]));
		
		str = clearTime.split(":");
		tempModel.setCLEARHH(Integer.parseInt(str[0]));
		tempModel.setCLEARMM(Integer.parseInt(str[1]));
		
		str = closeTime.split(":");
		tempModel.setOFFHH(Integer.parseInt(str[0]));
		tempModel.setOFFMM(Integer.parseInt(str[1]));
		
		str = adjustTime.split(":");
		tempModel.setADJUSTHH(Integer.parseInt(str[0]));
		tempModel.setADJUSTMM(Integer.parseInt(str[1]));	
		return tempModel;
	}

	private void addCLPlanByPeriod(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		LightRoomPlanDao lrpd = new LightRoomPlanDao();
		LightRoomPlanModel lrpm = null;
		
		int type = Integer.parseInt(request.getParameter("type"));
		int areaID = Integer.parseInt(request.getParameter("areaID"));
		int archID = Integer.parseInt(request.getParameter("archID"));
		int floor = Integer.parseInt(request.getParameter("floor"));
		int roomID = Integer.parseInt(request.getParameter("roomID"));
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String openTime = request.getParameter("openTime");
		String clearTime = request.getParameter("clearTime");
		String closeTime = request.getParameter("closeTime");
		String adjustTime = request.getParameter("adjustTime");
		
		String[] str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		Date time = sdf.parse(startDate);
		// 计算天数
		count = daysBetween(startDate, endDate);
		
		// 统计成功插入条数
		int result = 0;
		
		for (int i = 0; i < count; i++)
		{
			lrpm = new LightRoomPlanModel();
			lrpm.setTYPE(type);
			lrpm.setAREA_ID(areaID);
			lrpm.setARCHITECTURE_ID(archID);
			lrpm.setFLOOR(floor);
			lrpm.setLIGHTROOMNO(roomID);
			lrpm.setYYYY(getYYYY(time));
			lrpm.setMM(getMM(time));
			lrpm.setDD(getDD(time));
			
			str = openTime.split(":");
			lrpm.setONHH(Integer.parseInt(str[0]));
			lrpm.setONMM(Integer.parseInt(str[1]));
			
			str = clearTime.split(":");
			lrpm.setCLEARHH(Integer.parseInt(str[0]));
			lrpm.setCLEARMM(Integer.parseInt(str[1]));
			
			str = closeTime.split(":");
			lrpm.setOFFHH(Integer.parseInt(str[0]));
			lrpm.setOFFMM(Integer.parseInt(str[1]));
			
			str = adjustTime.split(":");
			lrpm.setADJUSTHH(Integer.parseInt(str[0]));
			lrpm.setADJUSTMM(Integer.parseInt(str[1]));	
			
			if(lrpd.insert(lrpm))
			{
				result++;
			}
			
			time=addDate(time, 1);						
		}
		
		PrintWriter out = response.getWriter();
		out.println("成功插入"+result+"条数据。");
		out.flush();
		out.close();	
	}

	private void getClassroomLightPlan(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		LightRoomPlanDao lrpd = new LightRoomPlanDao();
		LightRoomPlanModel lrpm = null;
		
		int thePageCount = 12;
		int thePageIndex = 0;
		int type = 1; //默认全校时间表
		int id = 0;
//		int areaID=0;
//		int archID=0;
		int floor=0;
//		int roomID=0;
		
		if(request.getParameter("ClassroomLightPlanPageCount") != null)
		{
			thePageCount = Integer.parseInt(request.getParameter("ClassroomLightPlanPageCount"));
		}
		if(request.getParameter("ClassroomLightPlanPageIndex") != null)
		{
			thePageIndex = Integer.parseInt(request.getParameter("ClassroomLightPlanPageIndex"));
		}
		if(request.getParameter("type") != null)
		{
			type = Integer.parseInt(request.getParameter("type"));
		}
		if(type == 2)
		{
			if(request.getParameter("areaID") != null)
			{
				id = Integer.parseInt(request.getParameter("areaID"));
			}
		}
		else if(type == 3)
		{
			if(request.getParameter("archID") != null)
			{
				id = Integer.parseInt(request.getParameter("archID"));
			}
		}
		else if(type == 4)
		{
			if(request.getParameter("floor") != null)
			{
				id = Integer.parseInt(request.getParameter("archID"));
				floor = Integer.parseInt(request.getParameter("floor"));
			}
		}
		else if(type == 5)
		{
			if(request.getParameter("roomID") != null)
			{
				id = Integer.parseInt(request.getParameter("roomID"));
			}
		}
		
		int theYear = Integer.parseInt(request.getParameter("theYear"));
		int theMonth = Integer.parseInt(request.getParameter("theMonth"));
		
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ArrayList<LightRoomPlanModel> list = new ArrayList<LightRoomPlanModel>();
		list = lrpd.queryClassroomPlan(thePageCount, thePageIndex, type, id, floor, theYear, theMonth, order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", lrpd.getTotalCount());
		json.add(jo);
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			lrpm=list.get(i);
			jo.put("switchScheduleID",lrpm.getSWITCHSCHEDULEID());
			jo.put("date", lrpm.getYYYY()+"-"+lrpm.getMM()+"-"+lrpm.getDD());
			jo.put("onTime", lrpm.getONHH()+":"+lrpm.getONMM());
			jo.put("clearTime", lrpm.getCLEARHH()+":"+lrpm.getCLEARMM());
			jo.put("adjustTime", lrpm.getADJUSTHH()+":"+lrpm.getADJUSTMM());
			jo.put("offTime", lrpm.getOFFHH()+":"+lrpm.getOFFMM());			
			json.add(jo);
		}
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.size());
		out.println(json.toString());
		out.flush();
		out.close();
		
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
	 * 获取年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private int getYYYY(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		return Integer.parseInt(year);

	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private int getMM(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(date);
		return Integer.parseInt(month);
	}

	/**
	 * 获取日号
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	private int getDD(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = sdf.format(date);
		return Integer.parseInt(day);
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
}
