package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.monitor.dao.EnergyMatchModelDao;
import com.sf.energy.manager.monitor.dao.MeterInfoDao;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.water.manager.current.dao.waterMatchModelDao;
import com.sf.energy.water.manager.current.dao.waterMatchModelInfoDao;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class waterMatchModelServlet extends HttpServlet
{
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ParseException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);

		if ("waterMatchmodelInfopaginate".equals(method))
			waterMatchmodelInfopaginate(req, resp);
		if ("getMOdelByID".equals(method))
			getMOdelByID(req, resp);

		if ("insertIntoModel".equals(method))
			insertIntoModel(req, resp);
		if ("getInfoByID".equals(method))
			getInfoByID(req, resp);
	}

	/*
	 * 通过区域，建筑，部门ID 获得信息
	 */
	private void getInfoByID(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		waterMatchModelInfoDao info = new waterMatchModelInfoDao();
		String AreaID = req.getParameter("AreaID").trim();
		String ArchID = req.getParameter("ArchID").trim();
		String GroupID = req.getParameter("GroupID").trim();

		int waterMatchModelPageCount = Integer.parseInt(req
				.getParameter("waterMatchModelPageCount"));
		int waterMatchModelPageIndex = Integer.parseInt(req
				.getParameter("waterMatchModelPageIndex"));
		
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		ArrayList<waterMatchModelInfoDao> list = new ArrayList<waterMatchModelInfoDao>();
		list = info.getMeterInfo(AreaID, GroupID, ArchID,
				waterMatchModelPageCount, waterMatchModelPageIndex,tableName,order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (waterMatchModelInfoDao n : list)
		{
			jo = new JSONObject();
			jo.put("WaterMeter_ID", n.getWaterMeterID());
			jo.put("WaterMeterName", n.getWaterMeterName());
			jo.put("Partment", n.getWaterMeterPartment());
			jo.put("isCheck", n.getIsCheck());
			json.add(jo);

		}

		//System.out.println("参数：" + AreaID + " " + ArchID + " " + GroupID + " ");
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/*
	 * 将模型插入数据库
	 */
	private void insertIntoModel(HttpServletRequest req,
			HttpServletResponse resp) throws IOException
	{
		String h0 = req.getParameter("h0").trim();
		String h1 = req.getParameter("h1").trim();
		String h2 = req.getParameter("h2").trim();
		String h3 = req.getParameter("h3").trim();
		String h4 = req.getParameter("h4").trim();
		String h5 = req.getParameter("h5").trim();
		String h6 = req.getParameter("h6").trim();
		String h7 = req.getParameter("h7").trim();
		String h8 = req.getParameter("h8").trim();

		String h9 = req.getParameter("h9").trim();
		String h10 = req.getParameter("h10").trim();
		String h11 = req.getParameter("h11").trim();
		String h12 = req.getParameter("h12").trim();
		String h13 = req.getParameter("h13").trim();
		String h14 = req.getParameter("h14").trim();
		String h15 = req.getParameter("h15").trim();
		String h16 = req.getParameter("h16").trim();
		String h17 = req.getParameter("h17").trim();
		String h18 = req.getParameter("h18").trim();
		String h19 = req.getParameter("h19").trim();
		String h20 = req.getParameter("h20").trim();
		String h21 = req.getParameter("h21").trim();
		String h22 = req.getParameter("h22").trim();
		String h23 = req.getParameter("h23").trim();
		String meterID = req.getParameter("meterID").trim();
		String isCheck = req.getParameter("isCheck").trim();

		waterMatchModelDao addModel = new waterMatchModelDao();
		addModel.setWATERMETER_ID(meterID);
		addModel.setH0(h0);
		addModel.setH1(h1);
		addModel.setH2(h2);
		addModel.setH3(h3);
		addModel.setH4(h4);
		addModel.setH5(h5);
		addModel.setH6(h6);
		addModel.setH7(h7);
		addModel.setH8(h8);
		addModel.setH9(h9);
		addModel.setH10(h10);
		addModel.setH11(h11);
		addModel.setH12(h12);
		addModel.setH13(h13);
		addModel.setH14(h14);
		addModel.setH15(h15);
		addModel.setH16(h16);
		addModel.setH17(h17);
		addModel.setH18(h18);
		addModel.setH19(h19);
		addModel.setH20(h20);
		addModel.setH21(h21);
		addModel.setH22(h22);
		addModel.setH23(h23);
		addModel.setIsCheck(isCheck);

		int result = 0;
		try
		{
			result = addModel.addModel();
		} catch (SQLException e)
		{
			//System.out.println("添加用水匹配出错！！");
			e.printStackTrace();
		}
		resp.setContentType("application/x-json");
		PrintWriter out = resp.getWriter();
		if (result == 0)
		{
			out.println("执行失败！");
		} else
		{
			out.println("执行成功！");
		}
		out.flush();
		out.close();

	}

	/*
	 * 通过ID获得model数据
	 */
	private void getMOdelByID(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ParseException
	{
		waterMatchModelDao model = new waterMatchModelDao();
		Date start = new Date();
		Date end = new Date();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (req.getParameter("dateTime").trim() != null
				&& !"".equals(req.getParameter("dateTime").trim()))
		{
			String date = req.getParameter("dateTime").trim();

			start = df.parse(date);
			end = df.parse(date);
		}

		String meterID = req.getParameter("waterMaterID");
		int AmMeterID = Integer.parseInt(meterID);

		WaterReportHelperImpl aa = new WaterReportHelperImpl();
		List<WaterMeterMataData> dataList = aa.getWaterMeterBetween(AmMeterID,
				start, end);

		waterMatchModelDao modelInfo = new waterMatchModelDao();
		modelInfo = model.getModelByID(meterID);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("h0", modelInfo.getH0());
		jo.put("h1", modelInfo.getH1());
		jo.put("h2", modelInfo.getH2());
		jo.put("h3", modelInfo.getH3());
		jo.put("h4", modelInfo.getH4());
		jo.put("h5", modelInfo.getH5());
		jo.put("h6", modelInfo.getH6());
		jo.put("h7", modelInfo.getH7());
		jo.put("h8", modelInfo.getH8());
		jo.put("h9", modelInfo.getH9());
		jo.put("h10", modelInfo.getH10());
		jo.put("h11", modelInfo.getH11());
		jo.put("h12", modelInfo.getH12());
		jo.put("h13", modelInfo.getH13());
		jo.put("h14", modelInfo.getH14());
		jo.put("h15", modelInfo.getH15());
		jo.put("h16", modelInfo.getH16());
		jo.put("h17", modelInfo.getH17());
		jo.put("h18", modelInfo.getH18());
		jo.put("h19", modelInfo.getH19());
		jo.put("h20", modelInfo.getH20());
		jo.put("h21", modelInfo.getH21());
		jo.put("h22", modelInfo.getH22());
		jo.put("h23", modelInfo.getH23());

		json.add(jo);

		int hour;
		float value;
		for (WaterMeterMataData n : dataList)
		{
			jo = new JSONObject();
			hour = getHour(n.getRecordTimeDate());
			value = n.getValue();
			jo.put("hour", hour);
			jo.put("value", value);
			json.add(jo);
		}

		resp.setContentType("application/x-json");
		PrintWriter out = resp.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/*
	 * 获取所有的电表信息，并实现分页功能
	 */
	private void waterMatchmodelInfopaginate(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		waterMatchModelInfoDao info = new waterMatchModelInfoDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("waterMatchModelPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("waterMatchModelPageIndex"));
		int area_id=Integer.parseInt(req.getParameter("areaid"));
		int arch_id=Integer.parseInt(req.getParameter("archid"));
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");
		List<waterMatchModelInfoDao> list = info.paginate(thePageCount, thePageIndex,area_id,arch_id,tableName,order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", info.getRecordCount());
		json.add(jo);

		for (waterMatchModelInfoDao n : list)
		{
			jo = new JSONObject();
			jo.put("WaterMeter_ID", n.getWaterMeterID());
			jo.put("WaterMeterName", n.getWaterMeterName());
			jo.put("Partment", n.getWaterMeterPartment());
			jo.put("isCheck", n.getIsCheck());
			json.add(jo);

		}

		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private int getHour(Date time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int theHour = cal.get(Calendar.HOUR_OF_DAY);
		return theHour;
	}

}
