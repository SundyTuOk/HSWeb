package com.sf.energy.welcome.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.broker.scheduler.Job;

import com.sf.energy.co2sale.impl.CO2QuotaManageDao;
import com.sf.energy.expert.dao.EnergyMonitorDao;
import com.sf.energy.expert.dao.InformationDao;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.dao.PartmentFixedDao;
import com.sf.energy.project.system.model.Users;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;
import com.sf.energy.welcome.Model.BaoJingInfoModel;
import com.sf.energy.welcome.dao.WelcomeInfoDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WelcomeInfoServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (RowsExceededException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, RowsExceededException,
			WriteException
	{
		String method = request.getParameter("method");

		if ("getUsersRight".equals(method))
			getUsersRight(request, response);
		if ("getDetailInputData".equals(method))
			getDetailInputData(request, response);
		if ("getAmMeterTotalDatas".equals(method))
			getAmMeterTotalDatas(request, response);
		if ("getWaterMeterTotalDatas".equals(method))
			getWaterMeterTotalDatas(request, response);
		if ("loadWarningInfo".equals(method))
			loadWarningInfo(request, response);
		if ("loadQuoteRemainInfo".equals(method))
			loadQuoteRemainInfo(request, response);
	}

	private void loadQuoteRemainInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}
		UsersModel user = new UsersModel();
		UsersDao udDao = new UsersDao();
		user = udDao.queryById(userID);
		DepartmentDao dpDao = new DepartmentDao();
		
		int partment_ID = 0;
		if (request.getParameter("pid") != null)
		{
			partment_ID = Integer.parseInt(request.getParameter("pid"));
		}
		int year=2010;
		if (request.getParameter("year") != null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		JSONArray json = new JSONArray();
		PartmentFixedDao paDao=new PartmentFixedDao();
		double remainEnergy=paDao.getRemainQuotaPartment(year+"", partment_ID+"");
		JSONObject jo = new JSONObject();
		jo.put("EnergyQuotaRemain", remainEnergy);
		CO2QuotaManageDao co2dao=new CO2QuotaManageDao();
		double reaminCO2=co2dao.getCO2QuotaRemain(year+"", partment_ID+"");
		jo.put("CO2QuotaRemain", reaminCO2);
		jo.put("partment_Name", dpDao.queryNameById(user.getUsersDepartment()));
		json.add(jo);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}
	private void loadWarningInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		WelcomeInfoDao wid = new WelcomeInfoDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BaoJingInfoModel> list = new ArrayList<BaoJingInfoModel>();
		list = wid.getWarningInfo(100);
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject jo = new JSONObject();
			jo.put("time", list.get(i).getBaoJingInfo_Time());
			jo.put("Model", list.get(i).getBaoJingInfo_MoudleNum());
			jo.put("content", list.get(i).getBaoJingInfo_content());
			json.add(jo);
		}
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void getWaterMeterTotalDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		WelcomeInfoDao wid = new WelcomeInfoDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Float> list = wid.getWaterMeterTotalDatas();
		Map<String, Float> resultMap = sortMapByKey(list);
		JSONArray json = new JSONArray();
		if (resultMap != null)
		{
			for (Map.Entry<String, Float> entry : resultMap.entrySet())
			{
				JSONObject jo = new JSONObject();
				jo.put("Valuetime", entry.getKey());
				jo.put("Zvaluezy", entry.getValue());
				json.add(jo);
			}
		}
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void getAmMeterTotalDatas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		WelcomeInfoDao wid = new WelcomeInfoDao();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Float> list = wid.getAmMeterTotalDatas();
		Map<String, Float> resultMap = sortMapByKey(list);
		JSONArray json = new JSONArray();
		if (resultMap != null)
		{
			for (Map.Entry<String, Float> entry : resultMap.entrySet())
			{
				JSONObject jo = new JSONObject();
				jo.put("Valuetime", entry.getKey());
				jo.put("Zvaluezy", entry.getValue());
				json.add(jo);
			}
		}
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Float> sortMapByKey(Map<String, Float> map)
	{
		if (map == null || map.isEmpty())
		{
			return null;
		}
		class MapKeyComparator implements Comparator<String>
		{

			@Override
			public int compare(String str1, String str2)
			{

				return str1.compareTo(str2);
			}
		}
		Map<String, Float> sortMap = new TreeMap<String, Float>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}

	private void getUsersRight(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}
		UsersModel user = new UsersModel();
		UsersDao udDao = new UsersDao();
		user = udDao.queryById(userID);
		DepartmentDao dpDao = new DepartmentDao();
		if (user != null)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("IsAlarm", user.getIsAlarm());
			jo.put("Partment_ID", user.getUsersDepartment());
			jo.put("partment_Name", dpDao.queryNameById(user.getUsersDepartment()));
			json.add(jo);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		}
	}

	private void getDetailInputData(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId").toString());
		}

		int partment_ID = 0;
		if (request.getParameter("partmentid") != null)
		{
			partment_ID = Integer.parseInt(request.getParameter("partmentid"));
		}
		ElecReportHelperImpl erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl wrhi = new WaterReportHelperImpl();

		// 获取当前时间
		Calendar getData_cal = Calendar.getInstance();
		int Now_year = getData_cal.get(Calendar.YEAR);
		int Now_month = getData_cal.get(Calendar.MONTH);
		int Now_day = getData_cal.get(Calendar.DATE);
		if (partment_ID == 1)
		{
			List<ReportModel> ToAm = null;
			List<ReportModel> YeAm = null;
			List<WaterReportModel> ToWater = null;
			List<WaterReportModel> YeWater = null;
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();

			getData_cal.add(Calendar.DAY_OF_MONTH, -1);
			Date toDay = getData_cal.getTime();
			getData_cal.add(Calendar.DAY_OF_MONTH, -1);
			Date YeDay =getData_cal.getTime();

			ToAm = erhi.getSchoolCountBetween(toDay, toDay);
			YeAm = erhi.getSchoolCountBetween(YeDay, YeDay);

			ToWater = wrhi.getSchoolCountBetween(toDay, toDay);
			YeWater = wrhi.getSchoolCountBetween(YeDay, YeDay);
			if (ToAm.size() != 0) // 今日用电
			{
				float totalAm = 0;
				for (int i = 0; i < ToAm.size(); i++)
				{
					totalAm += ToAm.get(i).getTotalEnergyCount();
				}
				jo.put("ToAm", totalAm);
			} else
			{
				jo.put("ToAm", 0);
			}
			if (YeAm.size() != 0) // 昨日用电
			{
				float totalAm = 0;
				for (int i = 0; i < YeAm.size(); i++)
				{
					totalAm += YeAm.get(i).getTotalEnergyCount();
				}
				jo.put("YeAm", totalAm);
			} else
			{
				jo.put("YeAm", 0);
			}
			if (ToWater.size() != 0)// 今日用水
			{
				float totalAm = 0;
				for (int i = 0; i < ToWater.size(); i++)
				{
					totalAm += ToWater.get(i).getTotalWaterCount();
				}
				jo.put("ToWater", totalAm);
			} else
			{
				jo.put("ToWater", 0);
			}
			if (YeWater.size() != 0)// 昨日用水
			{
				float totalAm = 0;
				for (int i = 0; i < YeWater.size(); i++)
				{
					totalAm += YeWater.get(i).getTotalWaterCount();
				}
				jo.put("YeWater", totalAm);
			} else
			{
				jo.put("YeWater", 0);
			}
			json.add(jo);
			out.println(json.toString());

		} else
		{
			List<ReportModel> ToAm = new ArrayList<ReportModel>();
			List<ReportModel> YeAm = new ArrayList<ReportModel>();
			List<WaterReportModel> ToWater = new ArrayList<WaterReportModel>();
			List<WaterReportModel> YeWater = new ArrayList<WaterReportModel>();
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();

			Date toDay = new Date();
			toDay.setYear(Now_year - 1900);
			toDay.setMonth(Now_month);
			toDay.setDate(Now_day);
			Date YeDay = new Date();
			YeDay.setYear(Now_year - 1900);
			YeDay.setMonth(Now_month);
			YeDay.setDate(Now_day - 1);

			ToAm = erhi.getGroupCountBetween(partment_ID, toDay, toDay);
			YeAm = erhi.getGroupCountBetween(partment_ID, YeDay, YeDay);

			if (ToAm.size() != 0) // 今日用电
			{
				float totalAm = 0;
				for (int i = 0; i < ToAm.size(); i++)
				{
					totalAm += ToAm.get(i).getTotalEnergyCount();
				}
				jo.put("ToAm", totalAm);
			} else
			{
				jo.put("ToAm", 0);
			}
			if (YeAm.size() != 0) // 昨日用电
			{
				float totalAm = 0;
				for (int i = 0; i < YeAm.size(); i++)
				{
					totalAm += YeAm.get(i).getTotalEnergyCount();
				}
				jo.put("YeAm", totalAm);
			} else
			{
				jo.put("YeAm", 0);
			}
			if (ToWater.size() != 0)// 今日用水
			{
				float totalAm = 0;
				for (int i = 0; i < ToWater.size(); i++)
				{
					totalAm += ToWater.get(i).getTotalWaterCount();
				}
				jo.put("ToWater", totalAm);
			} else
			{
				jo.put("ToWater", 0);
			}
			if (YeWater.size() != 0)// 昨日用水
			{
				float totalAm = 0;
				for (int i = 0; i < YeWater.size(); i++)
				{
					totalAm += YeWater.get(i).getTotalWaterCount();
				}
				jo.put("YeWater", totalAm);
			} else
			{
				jo.put("YeWater", 0);
			}
			json.add(jo);
			out.println(json.toString());
		}
		out.flush();
		out.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

}
