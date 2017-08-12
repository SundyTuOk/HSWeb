package com.sf.energy.project.system.servlet;

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

import com.sf.energy.manager.monitor.dao.AmStandByModelDao;
import com.sf.energy.manager.monitor.dao.EnergyMatchModelDao;
import com.sf.energy.manager.monitor.dao.MeterInfoDao;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;

public class EnergyModelMatchServlet extends HttpServlet
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

		if ("paginate".equals(method))
			paginate(req, resp);
		
		if ("getInfoByID".equals(method))
			getInfoByID(req, resp);
		if ("getMOdelByID".equals(method))
			getMOdelByID(req, resp);
		
		if ("insertIntoModel".equals(method))
			insertIntoModel(req, resp);
		
	}

	/*
	 * 将模型插入数据库
	 */
	private void insertIntoModel(HttpServletRequest req,
			HttpServletResponse resp) throws IOException
	{
		String h0=req.getParameter("h0").trim();
		String h1=req.getParameter("h1").trim();
		String h2=req.getParameter("h2").trim();
		String h3=req.getParameter("h3").trim();
		String h4=req.getParameter("h4").trim();	
		String h5=req.getParameter("h5").trim();
		String h6=req.getParameter("h6").trim();
		String h7=req.getParameter("h7").trim();
		String h8=req.getParameter("h8").trim();
		
		String h9=req.getParameter("h9").trim();
		String h10=req.getParameter("h10").trim();
		String h11=req.getParameter("h11").trim();
		String h12=req.getParameter("h12").trim();
		String h13=req.getParameter("h13").trim();	
		String h14=req.getParameter("h14").trim();
		String h15=req.getParameter("h15").trim();
		String h16=req.getParameter("h16").trim();
		String h17=req.getParameter("h17").trim();
		String h18=req.getParameter("h18").trim();
		String h19=req.getParameter("h19").trim();
		String h20=req.getParameter("h20").trim();
		String h21=req.getParameter("h21").trim();
		String h22=req.getParameter("h22").trim();	
		String h23=req.getParameter("h23").trim();
		String meterID=req.getParameter("meterID").trim();
		String isCheck=req.getParameter("isCheck").trim();
		
		EnergyMatchModelDao addModel=new EnergyMatchModelDao();
		addModel.setAMMETER_ID(meterID);
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
		
		int result=0;
		result=addModel.addModel();
		resp.setContentType("application/x-json");
		PrintWriter out=resp.getWriter();
		if(result==0)
		{
			out.println("执行失败！");
		}else{
			out.println("执行成功！");
		}
		out.flush();
		out.close();
		
	}

	/*
	 * 通过ID获得model数据
	 */
	private void getMOdelByID(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		EnergyMatchModelDao model=new EnergyMatchModelDao();
		Date start=new Date();
		Date end=new Date();
//		end.setDate(end.getDate()+1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
		
		if(req.getParameter("dateTime").trim()!=null&&!"".equals(req.getParameter("dateTime").trim()))
		{	
			String date=req.getParameter("dateTime").trim();
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
			try
			{
				start=df.parse(date);
				end=df.parse(date);
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		String meterID=req.getParameter("meterID");
		int AmMeterID = Integer.parseInt(meterID);
		
		
		ElecReportHelperImpl rhi_AmStandByModel = new ElecReportHelperImpl();
		List<AmMeterMataData> dataList = rhi_AmStandByModel.getAmMeterBetween(AmMeterID, start, end);
		
		
		
		EnergyMatchModelDao modelInfo=new EnergyMatchModelDao();
		modelInfo=model.getModelByID(meterID);
			
		JSONArray json=new JSONArray();
		JSONObject jo=new JSONObject();
		
		jo.put("h0",modelInfo.getH0());
		jo.put("h1",modelInfo.getH1());
		jo.put("h2",modelInfo.getH2());
		jo.put("h3",modelInfo.getH3());
		jo.put("h4",modelInfo.getH4());
		jo.put("h5",modelInfo.getH5());
		jo.put("h6",modelInfo.getH6());
		jo.put("h7",modelInfo.getH7());
		jo.put("h8",modelInfo.getH8());
		jo.put("h9",modelInfo.getH9());
		jo.put("h10",modelInfo.getH10());
		jo.put("h11",modelInfo.getH11());
		jo.put("h12",modelInfo.getH12());
		jo.put("h13",modelInfo.getH13());
		jo.put("h14",modelInfo.getH14());
		jo.put("h15",modelInfo.getH15());
		jo.put("h16",modelInfo.getH16());
		jo.put("h17",modelInfo.getH17());
		jo.put("h18",modelInfo.getH18());
		jo.put("h19",modelInfo.getH19());
		jo.put("h20",modelInfo.getH20());
		jo.put("h21",modelInfo.getH21());
		jo.put("h22",modelInfo.getH22());
		jo.put("h23",modelInfo.getH23());
		json.add(jo);
		JSONObject jo2=new JSONObject();
		AmmeterDao ammeterDao=new AmmeterDao();
		String meterName=ammeterDao.queryNameByID(AmMeterID);
		jo2.put("check", modelInfo.getIsCheck());
		jo2.put("MeterName",meterName);
		json.add(jo2);
		
		int hour;
		float value;
		for(AmMeterMataData n:dataList)
		{
			jo=new JSONObject();
			hour=getHour(n.getRecordTimeDate());
			value=n.getValue();
			jo.put("hour", hour);
			jo.put("value", value);
			json.add(jo);
		}
		
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		PrintWriter out=resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	/**
	 * 通过树形选择获得响应的信息
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getInfoByID(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		MeterInfoDao info = new MeterInfoDao();
		
		String AreaID = req.getParameter("AreaID").trim();
		String ArchID = req.getParameter("ArchID").trim();
		String GroupID = req.getParameter("GroupID").trim();
		int PageCount = Integer.parseInt(req
				.getParameter("energyModelMatchPageCount"));
		int PageIndex = Integer.parseInt(req
				.getParameter("energyModelMatchPageIndex"));
		
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		ArrayList<MeterInfoDao> list = new ArrayList<MeterInfoDao>();
		list = info.getMeterInfo(AreaID, GroupID, ArchID,
				PageCount, PageIndex,"COSTCHECK",tableName,order);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (MeterInfoDao n : list)
		{
			jo = new JSONObject();

			jo.put("Ammeter_id", n.getAmmerter_ID());
			jo.put("Ammeter_name", n.getAmmerter_name());
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
	
	
	/**
	 * 获得所有匹配模型的信息
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
				.getParameter("energyModelMatchPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("energyModelMatchPageIndex"));
		String tableName = request.getParameter("tableName");
		String order = request.getParameter("order");
		List<MeterInfoDao> list = info.energyMatchPaginate(thePageCount, thePageIndex,area_id,arch_id,tableName,order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount",info.getTotal() );
		json.add(jo);

		for (MeterInfoDao n : list)
		{
			jo = new JSONObject();
			jo.put("Ammeter_id", n.getAmmerter_ID());
			jo.put("Ammeter_name", n.getAmmerter_name());
			jo.put("Partment", n.getPartment());
			jo.put("isCheck", n.getIsCheck());
			json.add(jo);

			// //System.out.println("AmmeterID:" + n.getAmmeterID());
		}

		response.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
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

}
