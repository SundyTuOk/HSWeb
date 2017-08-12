package com.sf.energy.project.system.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.PlanTaskDao;
import com.sf.energy.project.system.dao.PlantaskExecuteDao;
import com.sf.energy.project.system.model.PlanTaskModel;
import com.sf.energy.project.system.service.dao.PlantaskExecDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class PlanTaskServerlet extends HttpServlet
{
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			try
			{
				findMethod(request, response);
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ParseException
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);
		if ("getAlltask".equals(method))
		{
			getAlltask(request, response);
		}

		if ("deleteTask".equals(method))
		{
			deleteTask(request, response);
		}

		if ("getTaskNameInDiv".equals(method))
		{
			getTaskNameInDiv(request, response);
		}

		if ("addTask".equals(method))
		{
			addTask(request, response);
		}
		
		if ("editTask".equals(method))
		{
			eidtTask(request, response);
		}
		
		if ("queryByID".equals(method))
		{
			queryByID(request, response);
		}
		if ("excutePlantask".equals(method))
		{
			excutePlantask(request, response);
		}
//		if ("executeDayTask".equals(method))
//		{
//			executeDayTask(request, response);
//		}
//		if ("executeMonthTask".equals(method))
//		{
//			executeMonthTask(request, response);
//		}
//		if ("executeModelTask".equals(method))
//		{
//			executeModelTask(request, response);
//		}
//		
	}
	
	private void excutePlantask(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, ParseException, IOException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int taskID = Integer.parseInt(request.getParameter("taskID").trim());
		PlantaskExecuteDao pte=new PlantaskExecuteDao();
		String userID = "1";
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}
		
		pte.planTask_exec(taskID, userID, df.format(new Date()),response);
		
		makeServerAddTask();
		
	}

	/**
	 * 通过ID查询计划任务控制器
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void queryByID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PlanTaskDao task = new PlanTaskDao();
		int taskID = Integer.parseInt(request.getParameter("taskID").trim());
		PlanTaskModel ptm = task.queryByID(taskID);
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		// / 周期运行类型 D-每天执行，M-每月执行
		jo.put("planTask_02Pinlv", ptm.getPlanTask_02Pinlv() );
		// / 周期运行间隔天数 ，条件：planTask_02Pinlv = "D"
		jo.put("planTask_02PinlvD", ptm.getPlanTask_02PinlvD());
		// / 周期运行执行天数 ，条件：planTask_02Pinlv = "M"
		jo.put("planTask_02PinlvMday", ptm.getPlanTask_02PinlvMday());
		// / 周期运行执行月数 ，条件：planTask_02Pinlv = "M"
		jo.put("planTask_02PinlvMmonth", ptm.getPlanTask_02PinlvMmonth());
		// / 周期运行发生类型 ，1-一次发生于，0-发生周期
		jo.put("planTask_02PinlvStyle", ptm.getPlanTask_02PinlvStyle());
		// / 周期运行发生时间为：一次发生于的发生时间条件（planTask_02PinlvStyle=1）
		jo.put("planTask_02PinlvTime", ptm.getPlanTask_02PinlvTime());
		// / 周期运行发生周期触发时间间隔
		jo.put("planTask_02Zhouqi", ptm.getPlanTask_02Zhouqi());
		// / 周期运行发生周期触发时间类型 ，"Hour"-小时发生，"Minute"-分钟发生
		jo.put("planTask_02ZhouqiStyle", ptm.getPlanTask_02ZhouqiStyle());
		jo.put("PlanTask_SMSWord", ptm.getPlanTask_SMSWord());
		jo.put("PlanTask_Remark", ptm.getPlanTask_Remark());
		// / 定时运行时间
		jo.put("planTask_01Time", ptm.getPlanTask_01Time());
		
		json.add(jo);
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	
	/**
	 * 编辑任务控制器
	 * @param request 请求
	 * @param response 应答
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	private void eidtTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PlanTaskDao task = new PlanTaskDao();
		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
		
		String planTask_ID = request.getParameter("PlanTask_ID");
		String planTask_Style = request.getParameter("PlanTask_Style");
		String planTask_Exec = request.getParameter("PlanTask_Exec");
		String execDate = request.getParameter("execDate");
		if(!"".equals(execDate)&&execDate!=null)
			  execDate=df2.format(df2.parse(execDate));
		String execTime = request.getParameter("execTime");

		String planTask_02Pinlv = request.getParameter("planTask_02Pinlv");
		String planTask_02PinlvD = request.getParameter("planTask_02PinlvD");
		String planTask_02PinlvMday = request
				.getParameter("planTask_02PinlvMday");
		String planTask_02PinlvMmonth = request
				.getParameter("planTask_02PinlvMmonth");

		String planTask_02PinlvStyle = request
				.getParameter("planTask_02PinlvStyle");
		String planTask_02PinlvTime = request
				.getParameter("planTask_02PinlvTime");
		String planTask_02Zhouqi = request.getParameter("planTask_02Zhouqi");
		String planTask_02ZhouqiStyle = request
				.getParameter("planTask_02ZhouqiStyle");

		String PlanTask_SMSWord = request.getParameter("PlanTask_SMSWord");
		String PlanTask_Remark = request.getParameter("PlanTask_Remark");

		String planTask_01Time = execDate + " " + execTime;

		String PlanTask_Tiaojian = "";
		if (planTask_Exec.equals("周期运行"))
		{
			if ("D".equals(planTask_02Pinlv))
			{
				PlanTask_Tiaojian += "每" + planTask_02PinlvD + "天发生,";
			} else
			{
				PlanTask_Tiaojian += "每" + planTask_02PinlvMmonth + "个月,第"
						+ planTask_02PinlvMday + "天发生,";
			}
			if ("1".equals(planTask_02PinlvStyle))
			{
				PlanTask_Tiaojian += "在" + planTask_02PinlvTime + "发生";
			} else
			{
				PlanTask_Tiaojian += "每次间隔" + planTask_02Zhouqi;
				if("Hour".equals(planTask_02ZhouqiStyle))
					PlanTask_Tiaojian+="小时";
				else
					PlanTask_Tiaojian+="分钟";
			}
		}
		if("定时运行".equals(planTask_Exec))
		{
			PlanTask_Tiaojian="在"+planTask_01Time+"发生";
		}
		
		PlanTaskModel ptm = new PlanTaskModel();
		ptm.setPlanTask_ID(Integer.parseInt(planTask_ID));
		ptm.setPlanTask_Style(planTask_Style);
		ptm.setPlanTask_Exec(planTask_Exec);
		ptm.setPlanTask_TiaoJian(PlanTask_Tiaojian);
		
		if("定时运行".equals(planTask_Exec))
		{
			ptm.setPlanTask_01Time(planTask_01Time);
		}
		else if (planTask_Exec.equals("周期运行"))
		{
			ptm.setPlanTask_02Pinlv(planTask_02Pinlv);
			if ("D".equals(planTask_02Pinlv))
			{
				ptm.setPlanTask_02PinlvD(Integer.parseInt(planTask_02PinlvD));
			} else
			{
				ptm.setPlanTask_02PinlvMday(Integer.parseInt(planTask_02PinlvMday));
				ptm.setPlanTask_02PinlvMmonth(Integer.parseInt(planTask_02PinlvMmonth));
			}
			
			ptm.setPlanTask_02PinlvStyle(Integer
					.parseInt(planTask_02PinlvStyle));
			
			if ("1".equals(planTask_02PinlvStyle))
			{
				ptm.setPlanTask_02PinlvTime(planTask_02PinlvTime);
			} else
			{
				ptm.setPlanTask_02Zhouqi(Integer.parseInt(planTask_02Zhouqi));
				ptm.setPlanTask_02ZhouqiStyle(planTask_02ZhouqiStyle);
			}
		}
		else if("短信事件".equals(planTask_Exec))
		{
			ptm.setPlanTask_SMSWord(PlanTask_SMSWord);
		}
		
		ptm.setPlanTask_Remark(PlanTask_Remark);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		ptm.setPlanTask_LastTime(df.format(now));
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		PrintWriter pw = response.getWriter();
		if (task.updateTask(ptm))
		{
			log.writeLog(userLoginName, "计划任务", "编辑计划任务    "+queryTaskNameByNum(ptm.getPlanTask_Style()));
			pw.println("编辑成功！");
			pw.flush();
			pw.close();
		makeServerAddTask();
		} else
		{
			pw.println("编辑失败！");
			pw.flush();
			pw.close();
		}
	}
	/**
	 * 添加计划任务控制器
	 * @param request 请求
	 * @param response 应答
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void addTask(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, SQLException, IOException
	{
		PlanTaskDao task = new PlanTaskDao();
		
		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
		
		String planTask_Style = request.getParameter("PlanTask_Style");
		
		String planTask_Exec = request.getParameter("PlanTask_Exec");
		//定时运行的参数
		String execDate = request.getParameter("execDate");
		String execTime = request.getParameter("execTime");
//		if(!"".equals(execDate)&&execDate!=null)
//		  execDate=df2.format(df1.parse(execDate));
		//周期运行的参数
		//发生频率
		String planTask_02Pinlv = request.getParameter("planTask_02Pinlv");
		//       频率：D  天数  
		String planTask_02PinlvD = request.getParameter("planTask_02PinlvD");
        //       频率：M  每个月的第几天，每几个月
		String planTask_02PinlvMday = request
				.getParameter("planTask_02PinlvMday");
		String planTask_02PinlvMmonth = request
				.getParameter("planTask_02PinlvMmonth");
		//每日发生频率
		String planTask_02PinlvStyle = request
				.getParameter("planTask_02PinlvStyle");
		//        频率：1  一次发生的时间
		String planTask_02PinlvTime = request
				.getParameter("planTask_02PinlvTime");
        //        频率：0  每天发生多次，多次的间隔时间和类型
		String planTask_02Zhouqi = request.getParameter("planTask_02Zhouqi");
		String planTask_02ZhouqiStyle = request
				.getParameter("planTask_02ZhouqiStyle");
		
		//短信事件的参数
		String PlanTask_SMSWord = request.getParameter("PlanTask_SMSWord");
		//备注信息
		String PlanTask_Remark = request.getParameter("PlanTask_Remark");

		String planTask_01Time = execDate + " " + execTime;

		String PlanTask_Tiaojian = "";
		
		if("定时运行".equals(planTask_Exec))
		{
			PlanTask_Tiaojian="在"+planTask_01Time+"发生";
		}
		if (planTask_Exec.equals("周期运行"))
		{
			if ("D".equals(planTask_02Pinlv))
			{
				PlanTask_Tiaojian += "每" + planTask_02PinlvD + "天发生,";
			} else
			{
				PlanTask_Tiaojian += "每" + planTask_02PinlvMmonth + "个月,第"
						+ planTask_02PinlvMday + "天发生,";
			}
			if ("1".equals(planTask_02PinlvStyle))
			{
				PlanTask_Tiaojian += "在" + planTask_02PinlvTime + "发生";
			} else
			{
				PlanTask_Tiaojian += "每次间隔" + planTask_02Zhouqi;
				if("Hour".equals(planTask_02ZhouqiStyle))
					PlanTask_Tiaojian+="小时";
				else
					PlanTask_Tiaojian+="分钟";
			}
		}
		
		
		PlanTaskModel newTask = new PlanTaskModel();

		newTask.setPlanTask_Style(planTask_Style);
		newTask.setPlanTask_Exec(planTask_Exec);
		newTask.setPlanTask_TiaoJian(PlanTask_Tiaojian);
		
		if("定时运行".equals(planTask_Exec))
		{
			newTask.setPlanTask_01Time(planTask_01Time);
		}
		else if (planTask_Exec.equals("周期运行"))
		{
			newTask.setPlanTask_02Pinlv(planTask_02Pinlv);
			if ("D".equals(planTask_02Pinlv))
			{
				newTask.setPlanTask_02PinlvD(Integer.parseInt(planTask_02PinlvD));
			} else
			{
				newTask.setPlanTask_02PinlvMday(Integer.parseInt(planTask_02PinlvMday));
				newTask.setPlanTask_02PinlvMmonth(Integer.parseInt(planTask_02PinlvMmonth));
			}
			
			newTask.setPlanTask_02PinlvStyle(Integer
					.parseInt(planTask_02PinlvStyle));
			
			if ("1".equals(planTask_02PinlvStyle))
			{
				newTask.setPlanTask_02PinlvTime(planTask_02PinlvTime);
			} else
			{
				newTask.setPlanTask_02Zhouqi(Integer.parseInt(planTask_02Zhouqi));
				newTask.setPlanTask_02ZhouqiStyle(planTask_02ZhouqiStyle);
			}
		}
		else if("短信事件".equals(planTask_Exec))
		{
			newTask.setPlanTask_SMSWord(PlanTask_SMSWord);
		}
		
		newTask.setPlanTask_Remark(PlanTask_Remark);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		newTask.setPlanTask_LastTime(df.format(now));
		PrintWriter pw = response.getWriter();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		if (task.insertTask(newTask))
		{
			log.writeLog(userLoginName, "计划任务", "添加计划任务    "+queryTaskNameByNum(newTask.getPlanTask_Style()));
			pw.println("添加成功！");
			pw.flush();
			pw.close();
			makeServerAddTask();
		} else
		{
			pw.println("添加失败！");
			pw.flush();
			pw.close();
		}

	}
	
	/**
	 * 添加任务类型进Div
	 * @param request 请求
	 * @param response 应答
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getTaskNameInDiv(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		String strSql = "select DictionaryValue_Value,DictionaryValue_Num from DictionaryValue where Dictionary_ID=24 ";
		JSONArray js =null;
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			js = new JSONArray();
			while (rs.next())
			{
				JSONObject jo = new JSONObject();
				jo.put("taskName", rs.getString(1));
				jo.put("taskName_num", rs.getString(2));
				js.add(jo);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		PrintWriter pw = response.getWriter();
		pw.println(js.toString());
		pw.flush();
		pw.close();
	}
	private String queryTaskNameByNum(String num){
		String strSql = "select DictionaryValue_Value from DictionaryValue where Dictionary_ID=24 and DictionaryValue_Num='"+num+"'";
		String name="";
		
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				name=rs.getString("DictionaryValue_Value");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return name;
	}
	/**
	 * 删除任务控制器
	 * @param request 请求
	 * @param response 应答
	 * @throws SQLException
	 * @throws IOException
	 */
	private void deleteTask(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		PlanTaskDao task = new PlanTaskDao();
		String taskID = request.getParameter("taskID").trim();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		String resultInfo = null;
		String nameString=queryTaskNameByNum((task.queryByID(Integer.parseInt(taskID)).getPlanTask_Style()));
		if (task.deleteTask(taskID))
		{
			log.writeLog(userLoginName, "计划任务", "删除计划任务    "+nameString);
			resultInfo = "删除成功";
			makeServerAddTask();
		}
		else
			resultInfo = "删除失败";
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}
	
	/**
	 * 获得所有的计划任务
	 * @param request 请求
	 * @param response 应答
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getAlltask(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		String tableName = request.getParameter("tableName");
		String order = request.getParameter("order");
		
		PlanTaskDao task = new PlanTaskDao();
		ArrayList<PlanTaskDao> list = task.getAllTaskInfo(tableName,order);
		JSONArray json = new JSONArray();
		for (int i=list.size()-1;i>=0;i--)
		{
			PlanTaskDao taskInfo =list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("taskID", taskInfo.getTaskID());
			jo.put("PlanTask_Style", taskInfo.getPlanTask_Style());
			jo.put("taskName", taskInfo.getTaskName());
			jo.put("operateStyle", taskInfo.getOperateStyle());
			jo.put("taskCondition", taskInfo.getTaskCondition());
			jo.put("operateState", taskInfo.getOperateState());
			jo.put("lastTime", taskInfo.getLastTime());
			jo.put("nextTime", taskInfo.getNextTime());
			json.add(jo);
		}
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

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
			config=com.sf.energy.util.Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
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
//	private void updateTaskNexttime(int taskID ) throws SQLException, ParseException
//	{
//		ResultSet rs = null;
//		PreparedStatement ps=null;
//		String strsql = "select *  from PlanTask where PlanTask_ID="+taskID;
//		rs= ps.executeQuery();
//		Task task=null;
//		while (rs.next())
//		{
//			ArrayList<String> value = new ArrayList<String>();
//			value.add(rs.getString("PlanTask_ID"));
//			value.add(rs.getString("PlanTask_Style"));
//			value.add(rs.getString("PlanTask_LastTime"));
//			value.add(rs.getString("PlanTask_LastState"));
//			value.add(rs.getString("PlanTask_Exec"));
//			value.add(rs.getString("PlanTask_01Time"));
//			value.add(rs.getString("PlanTask_02Pinlv"));
//			value.add(rs.getString("PlanTask_02PinlvD"));
//			value.add(rs.getString("PlanTask_02PinlvMday"));
//			value.add(rs.getString("PlanTask_02PinlvMmonth"));
//			value.add(rs.getString("PlanTask_02PinlvStyle"));
//			value.add(rs.getString("PlanTask_02PinlvTime"));
//			value.add(rs.getString("PlanTask_02Zhouqi"));
//			value.add(rs.getString("PlanTask_02ZhouqiStyle"));
//
//			task = new Task(value);
//		}
//		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nextTime1 = df.format(task.getPlanTask_NextTime());
//		String sql = "update PlanTask set PlanTask_NextTime='" + nextTime1
//				+ "' where PlanTask_ID=" + task.getPlanTask_ID();
//		PreparedStatement ps2=null;
//		ps2.executeUpdate();
//		if(ps2!=null)
//			ps2.close();
//		if(ps!=null)
//			ps.close();
//		if(rs!=null)
//			rs.close();
//	}
//	/*
//	 *执行用电比配等模型的计划任务
//	 */
//	private void executeModelTask(HttpServletRequest request,
//			HttpServletResponse response) throws SQLException, IOException, ParseException
//	{
//		//获得任务ID
//		String taskID=request.getParameter("taskID").trim();
//		int planTaskID=Integer.valueOf(taskID);
//		//执行任务
//		
////		PlantaskExecDao.planTask_exec(planTaskID, "", "");
//		//更新下一次的执行时间
//		updateTaskNexttime(planTaskID);
//		
//		// 发送服务器让它从新添加
//		makeServerAddTask();
//		
//		PrintWriter out = response.getWriter();
//		out.println("执行成功！");
//		out.flush();
//		out.close();
//	}
	
	
//	/*
//	 * 执行日类型的计划任务
//	 */
//	private void executeDayTask(HttpServletRequest request,
//			HttpServletResponse response) throws ParseException, SQLException, IOException
//	{
//		String taskID=request.getParameter("taskID").trim();
//		String dayStartTime=request.getParameter("dayStartTime").trim();
//		String dayEndTime=request.getParameter("dayEndTime").trim();
//		
//		int planTaskID=Integer.valueOf(taskID);
//		SimpleDateFormat df1=new SimpleDateFormat("MM/dd/yyy");
//		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
//		dayStartTime=df2.format(df1.parse(dayStartTime));
//		dayEndTime=df2.format(df1.parse(dayEndTime));
//		
////		PlantaskExecDao.planTask_exec(planTaskID, dayStartTime, dayEndTime);
//		
//		//更新下一次的执行时间
//		updateTaskNexttime(planTaskID);
//		
//		//发送服务器让它从新添加
//		makeServerAddTask();
//		
//		PrintWriter out = response.getWriter();
//		out.println("执行成功！");
//		out.flush();
//		out.close();
//		
//	}


}
