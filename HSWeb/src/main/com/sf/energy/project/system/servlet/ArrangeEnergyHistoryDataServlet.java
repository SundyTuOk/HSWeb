package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.monitor.dao.ArrangeEnergyHistoryDataDao;
import com.sf.energy.project.system.service.dao.PlantaskExecDao;
import com.sf.energy.util.ConnDB;




public class ArrangeEnergyHistoryDataServlet extends HttpServlet
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ParseException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);
		
		if ("paginate".equals(method))
			paginate(req, resp);
		
		if ("getArrangeStyleNamInDiv".equals(method))
			getArrangeStyleNamInDiv(req, resp);
		
		if ("execArrangeStyle".equals(method))
			execArrangeStyle(req, resp);
		
		if ("checkInfo".equals(method))
			checkInfo(req, resp);
		

	}
	/**
	 * 查询过滤控制器
	 * @param req 请求
	 * @param resp 应答
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void checkInfo(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, IOException
	{
		ArrangeEnergyHistoryDataDao info=new ArrangeEnergyHistoryDataDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("arrangeEnergyHistoryDataPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("arrangeEnergyHistoryDataPageIndex"));
		Integer type = Integer.parseInt(req
				.getParameter("type"));
		String operator=req.getParameter("operator").trim();
		String tableName=req.getParameter("tableName").trim();
		String order=req.getParameter("order").trim();
		String style=req.getParameter("style").trim();
		String startTime=null;
		startTime=req.getParameter("startTime").trim();
		String endTime=null;
		endTime=req.getParameter("endTime").trim();
//		SimpleDateFormat df=new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-mm-dd");
		if(startTime!=null && !"".equals(startTime)&&endTime!=null && !"".equals(endTime))
		{
			Date temp=null;
			Date startTimed=df1.parse(startTime);
			Date endTimed=df1.parse(endTime);
			if(startTimed.after(endTimed))
			{
				temp=startTimed;
				startTimed=endTimed;
				endTimed=temp;
			}
			startTime=df1.format(startTimed);
			endTime=df1.format(endTimed);
		}
		
		/*if(startTime!=null && !"".equals(startTime))
		    startTime=df1.format(df.parse(startTime));
		if(endTime!=null && !"".equals(endTime))
			endTime=df1.format(df.parse(endTime));*/
		
		List<ArrangeEnergyHistoryDataDao> list=info.checkByCondition(operator,style,startTime,endTime,thePageCount,thePageIndex,type,tableName,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (ArrangeEnergyHistoryDataDao n : list)
		{
			jo = new JSONObject();
			jo.put("LogHistoryData_ID", n.getLogHistoryData_ID());
			jo.put("Opr_Name", n.getOpr_Name());
			jo.put("Style_Name", n.getStyle_Name());
			jo.put("Exec_Log", n.getExec_Log());
			jo.put("Exec_Time", n.getExec_Time());
			jo.put("Star_Time", n.getStar_Time());
			jo.put("End_Time", n.getEnd_Time());
			json.add(jo);
		}
		
		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	/**
	 * 执行整理数据的控制器
	 * @param req  请求
	 * @param resp 应答
	 * @throws ParseException
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void execArrangeStyle(HttpServletRequest req,
			HttpServletResponse resp) throws ParseException, SQLException, UnknownHostException, IOException
	{
		PlantaskExecDao execDao=new PlantaskExecDao();
		String style=req.getParameter("style").trim();
		String startTime=req.getParameter("startTime").trim();
		String endTime=req.getParameter("endTime").trim();
		
//		SimpleDateFormat df=new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-mm-dd");
		
		if(startTime!=null && !"".equals(startTime)&&endTime!=null && !"".equals(endTime))
		{
			Date temp=null;
			Date startTimed=df1.parse(startTime);
			Date endTimed=df1.parse(endTime);
			if(startTimed.after(endTimed))
			{
				temp=startTimed;
				startTimed=endTimed;
				endTimed=temp;
			}
			startTime=df1.format(startTimed);
			endTime=df1.format(endTimed);
		}
		
//		if(startTime!=null && !"".equals(startTime))
//		    startTime=df1.format(df.parse(startTime));
//		if(endTime!=null && !"".equals(endTime))
//			endTime=df1.format(df.parse(endTime));
		String userId=req.getSession().getAttribute("userId").toString();
		String alert=null;
		if(userId!=null)
		{
		    boolean a=execDao.planTask_exec(style,startTime,endTime,userId);
		    if(a)
		    {
		    	alert="执行成功！";
		    }
		    else
		    {
		    	alert="执行失败！";
		    }
		}
		else
		{
			alert="连接超时，请重新登录！";
		}
		  resp.setContentType("application/x-json");
		   req.setCharacterEncoding("UTF-8");
		   resp.setCharacterEncoding("UTF-8");
		
		   PrintWriter pw = resp.getWriter();
		   pw.println(alert);
		   //System.out.println("发送成功！！！");
		   pw.flush();
		    pw.close();
		
	}

	//将Style名称写入Div
	private void getArrangeStyleNamInDiv(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		String strSql = "select DictionaryValue_Value,DictionaryValue_Num from DictionaryValue where Dictionary_ID=24 ";
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		JSONArray js =null;
		try
		{

			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			js = new JSONArray();
			String arrangeStyleName_num=null;
			while (rs.next())
			{
				arrangeStyleName_num=rs.getString(2);
				if("01".equals(arrangeStyleName_num)||"02".equals(arrangeStyleName_num)||"03".equals(arrangeStyleName_num)||"04".equals(arrangeStyleName_num))
				{
					JSONObject jo = new JSONObject();
					jo.put("arrangeStyleName", rs.getString(1));
					jo.put("arrangeStyleName_num", arrangeStyleName_num);
					jo.put("arrangeStyleName_Class", "energyArrangeStyle");
					js.add(jo);
				}
				else if("11".equals(arrangeStyleName_num)||"12".equals(arrangeStyleName_num)||"13".equals(arrangeStyleName_num))
				{
					JSONObject jo = new JSONObject();
					jo.put("arrangeStyleName", rs.getString(1));
					jo.put("arrangeStyleName_num", arrangeStyleName_num);
					jo.put("arrangeStyleName_Class", "waterArrangeStyle");
					js.add(jo);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		//System.out.println(js.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = resp.getWriter();
		pw.println(js.toString());
		pw.flush();
		pw.close();
		
	}

	//获取界面的信息
	/**
	 * 初始化控制器
	 * @param req	请求
	 * @param resp	应答
	 * @throws IOException
	 * @throws SQLException
	 */
	private void paginate(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		ArrangeEnergyHistoryDataDao info=new ArrangeEnergyHistoryDataDao();
		Integer thePageCount = Integer.parseInt(req
				.getParameter("arrangeEnergyHistoryDataPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("arrangeEnergyHistoryDataPageIndex"));
		
		Integer type = Integer.parseInt(req
				.getParameter("type"));
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");
		
		List<ArrangeEnergyHistoryDataDao> list=info.paginate(thePageCount, thePageIndex,type,tableName,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", info.getTotal());
		json.add(jo);

		for (ArrangeEnergyHistoryDataDao n : list)
		{
			jo = new JSONObject();
			jo.put("LogHistoryData_ID", n.getLogHistoryData_ID());
			jo.put("Opr_Name", n.getOpr_Name());
			jo.put("Style_Name", n.getStyle_Name());
			jo.put("Exec_Log", n.getExec_Log());
			jo.put("Exec_Time", n.getExec_Time());
			jo.put("Star_Time", n.getStar_Time());
			jo.put("End_Time", n.getEnd_Time());
			json.add(jo);
		}
		
//		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}



	
}
