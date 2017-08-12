package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.water.manager.current.dao.MeterInfoDao;
import com.sf.energy.water.manager.current.dao.WaterStandByModelDao;
import com.sf.energy.water.manager.current.model.MeterInfoModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class WaterStandByModelServerlet extends HttpServlet
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
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

		if ("addModel".equals(method))
			addModel(req, resp);

		if ("getInfoByID".equals(method))
			getInfoByID(req, resp);

		if ("getWaterMeterModelBetween".equals(method))
			getWaterMeterModelBetween(req, resp);

		if ("paginate".equals(method))
			paginate(req, resp);

		if ("getMOdelByID".equals(method))
		{
			getModelByID(req, resp);
		}
	}
	/**
	 * 获得model信息的控制器
	 * @param req 请求
	 * @param resp 应答
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getModelByID(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SQLException
	{
		WaterStandByModelDao model = new WaterStandByModelDao();
		
		String meterID = req.getParameter("meterID");
		WaterStandByModelDao modelByID=null;
		modelByID = model.getModelInfoByID(meterID);
		JSONArray json = new JSONArray();
		WatermeterDao WatermeterDao=new WatermeterDao();
		String meterName=WatermeterDao.queryNameByID(Integer.parseInt(meterID));
		JSONObject jo = new JSONObject();
		if(modelByID!=null){
			
			jo.put("maxValue", modelByID.getUpperLimit());
			jo.put("minValue", modelByID.getLowLimit());
			jo.put("startTime", modelByID.getStartTime());
			jo.put("endtime", modelByID.getEndTime());
			jo.put("isCheck", modelByID.getIsCheck());
			
		}
		jo.put("meterName",meterName);
		json.add(jo);
		resp.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获得页面展示信息的控制器
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 */
	private void paginate(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		MeterInfoDao info = new MeterInfoDao();
		int area_id=Integer.parseInt(request.getParameter("areaid"));
		int arch_id=Integer.parseInt(request.getParameter("archid"));
		Integer thePageCount = Integer.parseInt(request
				.getParameter("standByModelPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("standByModelPageIndex"));
		String tableName = request.getParameter("tableName");
		String order = request.getParameter("order");
		List<MeterInfoModel> list = info.paginate(thePageCount, thePageIndex,area_id,arch_id,tableName,order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (MeterInfoModel n : list)
		{
			jo = new JSONObject();
			jo.put("Watermeter_id", n.getWatermeter_ID());
			jo.put("Watermeter_name", n.getWatermeter_name());
			jo.put("Partment", n.getPartment());
			jo.put("isCheck", n.getIsCheck());
			json.add(jo);

			
		}

		//System.out.println(json.toString());
		response.setContentType("application/x-json");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 获得每小时的信息的控制器
	 * @param req	请求
	 * @param resp	应答
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getWaterMeterModelBetween(HttpServletRequest req,
			HttpServletResponse resp) throws ParseException, SQLException,
			IOException
	{
		WaterStandByModelDao model = new WaterStandByModelDao();
		Date start=new Date();
//		Date end=new Date();
//		end.setDate(end.getDate()+1);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
		if(req.getParameter("dateTime").trim()!=null&&!"".equals(req.getParameter("dateTime").trim()))
		{	
			
			String date=req.getParameter("dateTime").trim();
			start=df.parse(date);
//			int month=Integer.parseInt(date.substring(0,2));
//			int day=Integer.parseInt(date.substring(3,5));
//			int year=Integer.parseInt(date.substring(6,10));
//			start.setYear(year-1900);
//			start.setMonth(month-1);
//			start.setDate(day);
//			
//			end.setYear(year-1900);
//			end.setMonth(month-1);
//			end.setDate(day+1);
		}
	
		String meterID = req.getParameter("WatermeterID").trim();
		int WatermeterID = Integer.parseInt(meterID);
		
		
		WaterReportHelper waterReportHelper = new WaterReportHelperImpl();
		List<WaterMeterMataData> dataList = waterReportHelper.getWaterMeterBetween(WatermeterID, start, start);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		//获得该电表的模型数据【0】
		WaterStandByModelDao modelByID=null;
		modelByID = model.getModelInfoByID(meterID);
		if(modelByID!=null){
			
		
		jo.put("maxValue", modelByID.getUpperLimit());
		jo.put("minValue", modelByID.getLowLimit());
		
		String startTime=modelByID.getStartTime();
		jo.put("startTime", getHour(startTime));
		
		String endtime=modelByID.getEndTime();
		jo.put("endtime", getHour(endtime));
		
		jo.put("isCheck", modelByID.getIsCheck());
		}
		json.add(jo);
		
		//获得数据【1】
		int hour;
		float value;
		for(WaterMeterMataData n:dataList)
		{
			jo=new JSONObject();
			hour=getHour(n.getRecordTimeDate());
			value=n.getValue();
			jo.put("hour", hour);
			jo.put("value", value);
			json.add(jo);
		}
		
		PrintWriter out = resp.getWriter();
		
		//System.out.println(json.toString());
		
		out.println(json.toString());
		out.flush();
		out.close();

	}
	
	private int getHour(Date time)
	{
		 Calendar cal=Calendar.getInstance();
		cal.setTime(time);
		int theHour=cal.get(Calendar.HOUR_OF_DAY);
		return theHour;
	}
	private String getHour(String time)
	{
		 String theHour=null;
		 if("0".equals(time.substring(0,1)))
			 theHour=time.substring(1, 2);
		 else
			 theHour=time.substring(0,2);
		return theHour;
	}

	private void getInfoByID(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		MeterInfoDao info = new MeterInfoDao();
		
		String AreaID = req.getParameter("AreaID").trim();
		String ArchID = req.getParameter("ArchID").trim();
		String GroupID = req.getParameter("GroupID").trim();
		int standByModelPageCount = Integer.parseInt(req
				.getParameter("standByModelPageCount"));
		int standByModelPageIndex = Integer.parseInt(req
				.getParameter("standByModelPageIndex"));
		
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		List<MeterInfoModel> list = new ArrayList<MeterInfoModel>();
		list = info.getMeterInfo(AreaID, GroupID, ArchID,
				standByModelPageCount, standByModelPageIndex,"STANDBYCHECK",tableName,order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (MeterInfoModel n : list)
		{
			jo = new JSONObject();

			jo.put("Watermeter_id", n.getWatermeter_ID());
			jo.put("Watermeter_name", n.getWatermeter_name());
			jo.put("Partment", n.getPartment());
			jo.put("isCheck", n.getIsCheck());

			json.add(jo);
		}
		resp.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void addModel(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		WaterStandByModelDao model = new WaterStandByModelDao();

		model.setWatermeterID(req.getParameter("WatermeterID"));
		model.setEndTime(req.getParameter("endTime"));
		model.setStartTime(req.getParameter("startTime"));
		model.setLowLimit(req.getParameter("lowLimit"));
		model.setUpperLimit(req.getParameter("upperLimit"));
		model.setIsCheck(req.getParameter("isCheck"));

		String resultInfo = null;
		if (model.addModel())
			resultInfo = "成功";
		else
			resultInfo = "失败";
		PrintWriter out = resp.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	

}
