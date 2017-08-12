package com.sf.energy.light.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.light.dao.SLPlanDao;
import com.sf.energy.light.model.SLPlanModel;


import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class SLPlanServlet extends HttpServlet
{


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
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

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{

		doGet(req, resp);
	}

	/**
	 * 寻找控制器
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void findMethod(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ParseException
	{
		String method = req.getParameter("method");
		//		//System.out.println("method:" + method);

		if ("paginate".equals(method))
			paginate(req, resp);

		if ("deletSLPlan".equals(method))
			deletSLPlan(req, resp);

		if ("addSLPlanStyle1".equals(method))
			addSLPlanStyle1(req, resp);

		if ("addSLPlanStyle2".equals(method))
			addSLPlanStyle2(req, resp);
		if ("updateSLPlan".equals(method))
			updateSLPlan(req, resp);
		if("execSLPlan".equals(method))
			execSLPlan(req, resp);



	}
	/**
	 * 发送命令到服务器
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void makeServerAddTask() throws UnknownHostException, IOException
	{
		// 发送服务器让它从新添加
		XMLConfiguration config=null;

		try
		{
			config=com.sf.energy.util.Configuration.getConfiguration

					("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String IP=config.getString("server.IP");
		int port=Integer.parseInt(config.getString("server.port"));

		String command = "<addPlantask>\r\n";
		Socket s = new Socket(IP, port);
		OutputStream os = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeBytes(command);
	}
	void execSLPlan(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		makeServerAddTask();
		log.writeLog(userLoginName, "路灯计划任务", "执行路灯计划任务");
	}
	/**
	 * 更新控制器
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void updateSLPlan(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		DateFormat df = new SimpleDateFormat("HH:mm");
		SLPlanDao info = new SLPlanDao();
		String theID = req.getParameter("SWITCHSCHEDULEID");
		SLPlanModel beforeModel = info.querySLPlanModelInfo(Integer.parseInt(theID));
		String openAtNightfall = req.getParameter("OpenAtNightfall");
		String offAtNightfall = req.getParameter("OffAtNightfall");
		//		String openAtMorning = req.getParameter("OpenAtMorning");
		//		String offAtMorning = req.getParameter("OffAtMorning");
		int result=0;
		//计算起止时间差
		//		Date d11=null;
		//		Date d12=null;
		//		Date d21=null;
		//		Date d22=null;
		String openAtMorning =""; 
		String offAtMorning = "";
		//		DateFormat df = new SimpleDateFormat("HH:mm");
		Date d11=null;
		Date d12=null;
		Date d21=null;
		Date d22=null;

		long diff2 =0;
		int times=Integer.parseInt(req.getParameter("times"));
		if(times==2){
			try
			{
				openAtMorning=req.getParameter("OpenAtMorning");
				offAtMorning =req.getParameter("OffAtMorning");
				d21 = df.parse(openAtMorning);
				d22 = df.parse(offAtMorning);
				diff2 = d21.getTime() - d22.getTime();
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			d11 = df.parse(openAtNightfall);
			d12 = df.parse(offAtNightfall);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		//		try
		//		{
		//			d21 = df.parse(openAtMorning);
		//			d22 = df.parse(offAtMorning);
		//		} catch (ParseException e)
		//		{
		//			e.printStackTrace();
		//		}

		long diff1 = d11.getTime() - d12.getTime();
		//		long diff2 = d21.getTime() - d22.getTime();
		//		if((diff1<=0)&&(diff2<=0)){	
		result=info.updateSLPlan(theID,openAtNightfall,offAtNightfall,openAtMorning,offAtMorning);
		//		}
		//	System.out.println(diff1+" | "+diff2);
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		PrintWriter pt = resp.getWriter();

		if (result == 1){
			log.writeLog(userLoginName, "路灯计划任务", "修改路灯计划任务  任务Id:"+theID+
					" 第一次开灯时间"+beforeModel.getONHH1()+"->"+openAtNightfall
					+" 第一次熄灯时间"+beforeModel.getOFFHH1()+"->"+offAtNightfall
					+" 第二次开灯时间"+beforeModel.getONHH2()+"->"+openAtMorning
					+" 第二次熄灯时间"+beforeModel.getOFFHH2()+"->"+offAtMorning);
			pt.println("更改成功！");
		}
		else{
			log.writeLog(userLoginName, "路灯计划任务", "修改路灯计划任务  任务Id:"+theID+" 操作未完全成功");
			pt.println("操作未完全成功！");
		}
		pt.flush();
		pt.close();
	}

	/**
	 * 添加控制器2
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void addSLPlanStyle2(HttpServletRequest req,
			HttpServletResponse resp) throws ParseException, SQLException, IOException
	{
		int result = 0;
		SLPlanDao info = new SLPlanDao();
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");

		String openAtNightfall = req.getParameter("OpenAtNightfall");
		String offAtNightfall = req.getParameter("OffAtNightfall");
		//		String openAtMorning = req.getParameter("OpenAtMorning");
		//		String offAtMorning = req.getParameter("OffAtMorning");
		//		DateFormat df = new SimpleDateFormat("HH:mm");
		//		Date d11=null;
		//		Date d12=null;
		//		Date d21=null;
		//		Date d22=null;
		String openAtMorning =""; 
		String offAtMorning = "";
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date d11=null;
		Date d12=null;
		Date d21=null;
		Date d22=null;

		long diff2 =0;
		int times=Integer.parseInt(req.getParameter("times"));
		if(times==2){
			try
			{
				openAtMorning=req.getParameter("OpenAtMorning");
				offAtMorning =req.getParameter("OffAtMorning");
				d21 = df.parse(openAtMorning);
				d22 = df.parse(offAtMorning);
				diff2 = d21.getTime() - d22.getTime();
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			d11 = df.parse(openAtNightfall);
			d12 = df.parse(offAtNightfall);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		//		try
		//		{
		//			d21 = df.parse(openAtMorning);
		//			d22 = df.parse(offAtMorning);
		//		} catch (ParseException e)
		//		{
		//			e.printStackTrace();
		//		}
		long diff1 = d11.getTime() - d12.getTime();
		//		long diff2 = d21.getTime() - d22.getTime();
		String satOpenAtNightfall = req.getParameter("SatOpenAtNightfall");
		String satOffAtNightfall = req.getParameter("SatOffAtNightfall");

		String satOpenAtMorning = req.getParameter("SatOpenAtMorning");
		String satOffAtMorning = req.getParameter("SatOffAtMorning");

		String sunOpenAtNightfall = req.getParameter("SunOpenAtNightfall");
		String sunOffAtNightfall = req.getParameter("SunOffAtNightfall");
		String sunOpenAtMorning = req.getParameter("SunOpenAtMorning");
		String sunOffAtMorning = req.getParameter("SunOffAtMorning");

		if(times==1){
			satOpenAtMorning="";
			satOffAtMorning="";
			sunOpenAtMorning="";
			sunOffAtMorning="";
		}

		String type = "";
		String areaID = req.getParameter("areaID");
		String lineID = req.getParameter("lineID");
		String lampID = req.getParameter("lampID");

		if ("-1".equals(areaID) && "-1".equals(lineID) && "-1".equals(lampID))
			type = "1";
		if (!"-1".equals(areaID) && "-1".equals(lineID) && "-1".equals(lampID))
			type = "2";
		if (!"-1".equals(areaID) && !"-1".equals(lineID) && "-1".equals(lampID))
			type = "3";
		if (!"-1".equals(areaID) && !"-1".equals(lineID)
				&& !"-1".equals(lampID))
			type = "4";
		//		?if(diff1<=0&&diff2<=0){
		result = info.addSLPlanStyle2(startTime, endTime, openAtNightfall,
				offAtNightfall, openAtMorning, offAtMorning,
				satOpenAtNightfall, satOffAtNightfall, satOpenAtMorning,
				satOffAtMorning, sunOpenAtNightfall, sunOffAtNightfall,
				sunOpenAtMorning, sunOffAtMorning, type, areaID, lineID, lampID);
		//		}
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		PrintWriter pt = resp.getWriter();

		if (result == 1){
			log.writeLog(userLoginName, "路灯计划任务", "添加计划任务 添加成功 ");
			pt.println("添加成功！");
		}
		else{
			log.writeLog(userLoginName, "路灯计划任务", "添加计划任务  操作未完全成功");
			pt.println("操作未完全成功！");
		}
		pt.flush();
		pt.close();
	}

	/**
	 * 添加控制器1
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void addSLPlanStyle1(HttpServletRequest req,
			HttpServletResponse resp) throws ParseException, SQLException,
			IOException
	{
		int result=0;
		SLPlanDao info = new SLPlanDao();
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String openAtNightfall = req.getParameter("OpenAtNightfall");
		String offAtNightfall = req.getParameter("OffAtNightfall");
		String openAtMorning =""; 
		String offAtMorning = "";
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date d11=null;
		Date d12=null;
		Date d21=null;
		Date d22=null;

		long diff2 =0;
		int times=Integer.parseInt(req.getParameter("times"));
		if(times==2){
			try
			{
				openAtMorning=req.getParameter("OpenAtMorning");
				offAtMorning =req.getParameter("OffAtMorning");
				d21 = df.parse(openAtMorning);
				d22 = df.parse(offAtMorning);
				diff2 = d21.getTime() - d22.getTime();
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			d11 = df.parse(openAtNightfall);
			d12 = df.parse(offAtNightfall);

		} catch (ParseException e)
		{
			e.printStackTrace();
		}



		long diff1 = d11.getTime() - d12.getTime();


		String areaID = req.getParameter("areaID");
		String lineID = req.getParameter("lineID");
		String lampID = req.getParameter("lampID");

		String type = "";
		if ("-1".equals(areaID) && "-1".equals(lineID) && "-1".equals(lampID))
			type = "1";
		if (!"-1".equals(areaID) && "-1".equals(lineID) && "-1".equals(lampID))
			type = "2";
		if (!"-1".equals(areaID) && !"-1".equals(lineID) && "-1".equals(lampID))
			type = "3";
		if (!"-1".equals(areaID) && !"-1".equals(lineID)
				&& !"-1".equals(lampID))
			type = "4";

		//		if(diff1<=0&&diff2<=0){
		result = info.addSLPlanStyle1(startTime, endTime, openAtNightfall,
				offAtNightfall, openAtMorning, offAtMorning, type, areaID,
				lineID, lampID);
		//		}
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
			
		PrintWriter pt = resp.getWriter();

		if (result == 1){
			log.writeLog(userLoginName, "路灯计划任务", "添加计划任务 添加成功 ");
			pt.println("添加成功！");
		}
		else
		{
			log.writeLog(userLoginName, "路灯计划任务", "添加计划任务  操作未完全成功");
			pt.println("操作未完全成功！");
		}
		pt.flush();
		pt.close();
	}

	/**
	 * 删除控制器
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws SQLException
	 * @throws IOException
	 */
	private void deletSLPlan(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		SLPlanDao info = new SLPlanDao();
		String arrayOfID = req.getParameter("SWITCHSCHEDULEID");

		String[] scheduleID;
		scheduleID = arrayOfID.split("\\|");

		for (int i = 0; i < scheduleID.length; i++)
		{
			info.deleteSLPlanByID(scheduleID[i]);
		}
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		log.writeLog(userLoginName, "路灯计划任务", "删除路灯计划任务");
		PrintWriter out = resp.getWriter();
		out.println("执行结束!");
		out.flush();
		out.close();

	}

	/**
	 * 查询控制器
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws SQLException
	 * @throws IOException
	 */
	private void paginate(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		SLPlanDao info = new SLPlanDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("SLPlanPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("SLPlanPageIndex"));

		Integer allID = Integer.parseInt(req.getParameter("allID"));
		Integer areaID = Integer.parseInt(req.getParameter("areaID"));
		Integer lineID = Integer.parseInt(req.getParameter("lineID"));
		Integer lampID = Integer.parseInt(req.getParameter("lampID"));
		Integer selyear = Integer.parseInt(req.getParameter("year"));
		Integer selmonth = Integer.parseInt(req.getParameter("month"));

		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

		//		//System.out.println(allID + " " + areaID + " " + lineID + " " + lampID+ " " + tableName+ " " + order);

		List<SLPlanModel> list = info.paginate(thePageCount, thePageIndex,
				allID, areaID, lineID, lampID,selyear,selmonth,tableName,order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (SLPlanModel n : list)
		{
			jo = new JSONObject();
			jo.put("SWITCHSCHEDULEID", n.getSWITCHSCHEDULEID());
			jo.put("TYPE", n.getTYPE());

			String year = n.getYYYY();
			String month = n.getMM();
			String day = n.getDD();
			String dayTime = getDayTime(year, month, day);
			jo.put("dayTime", dayTime);

			String hour = "";
			String min = "";
			String time = "";
			hour = n.getONHH1();
			min = n.getONMM1();
			time = getTime(hour, min);
			jo.put("OnAtNightTime", time);

			hour = n.getOFFHH1();
			min = n.getOFFMM1();
			time = getTime(hour, min);
			jo.put("OffAtNightTime", time);

			hour = n.getONHH2();
			min = n.getONMM2();
			time = getTime(hour, min);
			jo.put("OnAtMorningTime", time);

			hour = n.getOFFHH2();
			min = n.getOFFMM2();
			time = getTime(hour, min);
			jo.put("OffAtMorningTime", time);

			json.add(jo);
		}

		//		//System.out.println(json.toString());

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	/**
	 * 拼接时刻
	 * 
	 * @param hour
	 *            小时
	 * @param min
	 *            分钟
	 * @return
	 */
	private String getTime(String hour, String min)
	{
		String time = "";
		if (hour != null && min != null)
		{
			hour=(Integer.parseInt(hour)<10 ? "0" + hour : hour);
			min=(Integer.parseInt(min)<10 ? "0" + min : min);
			time = hour + ":" + min;
		}
		return time;
	}

	/**
	 * 拼接日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return
	 */
	private String getDayTime(String year, String month, String day)
	{
		month=(Integer.parseInt(month)<10 ? "0" + month : month);
		day=(Integer.parseInt(day)<10 ? "0" + day : day);
		String time = year + "-" + month + "-" + day;
		return time;
	}

}
