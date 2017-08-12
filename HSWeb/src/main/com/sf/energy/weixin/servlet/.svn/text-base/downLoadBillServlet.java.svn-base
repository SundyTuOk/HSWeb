package com.sf.energy.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.weixin.service.downLoadBillService;

public class downLoadBillServlet extends HttpServlet
{
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String method = req.getParameter("method");		
		if("downLoadDayBill".equals(method)){
			downLoadDayBill(req, resp);
		}
		if("refreshDayBill".equals(method)){
			refreshDayBill(req, resp);
		}
		if("resendOrderByBill".equals(method)){
			resendOrderByBill(req, resp);
		}
	}
	private void resendOrderByBill(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String queryStartDate = df.format(new Date());
		if(request.getParameter("queryStartDate")!=null&&!request.getParameter("queryStartDate").equals("")){
			queryStartDate = df.format(df1.parse(request.getParameter("queryStartDate")));
		}
		String queryEndDate = df.format(new Date());
		if(request.getParameter("queryEndDate")!=null&&!request.getParameter("queryEndDate").equals("")){
			queryEndDate = df.format(df1.parse(request.getParameter("queryEndDate")));
		}
//		String queryType = "ALL";
//		if(request.getParameter("queryType")!=null&&!request.getParameter("queryType").equals("")){
//			queryType = request.getParameter("queryType");
//		}
		downLoadBillService service = new downLoadBillService();
		Map<String, Integer> map = service.resend_Order_By_Bill_Service(queryStartDate,queryEndDate);	
		PrintWriter out = response.getWriter();
		//System.out.println(jsonstr);
		out.println(new Gson().toJson(map));
		out.flush();
		out.close();
	}
	private void refreshDayBill(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	{
		String queryStartDate = df.format(new Date());
		if(request.getParameter("queryStartDate")!=null&&!request.getParameter("queryStartDate").equals("")){
			queryStartDate = df.format(df1.parse(request.getParameter("queryStartDate")));
		}
		String queryEndDate = df.format(new Date());
		if(request.getParameter("queryEndDate")!=null&&!request.getParameter("queryEndDate").equals("")){
			queryEndDate = df.format(df1.parse(request.getParameter("queryEndDate")));
		}
		String queryType = "ALL";
		if(request.getParameter("queryType")!=null&&!request.getParameter("queryType").equals("")){
			queryType = request.getParameter("queryType");
		}
		downLoadBillService service = new downLoadBillService();
		String jsonstr = service.refresh_Days_Bill_Service(queryStartDate,queryEndDate, queryType);	
		PrintWriter out = response.getWriter();
		//System.out.println(jsonstr);
		out.println(jsonstr);
		out.flush();
		out.close();
	}
	private void downLoadDayBill(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
	{
		String queryStartDate = df.format(new Date());
		if(request.getParameter("queryStartDate")!=null&&!request.getParameter("queryStartDate").equals("")){
			queryStartDate = df.format(df1.parse(request.getParameter("queryStartDate")));
		}
		String queryEndDate = df.format(new Date());
		if(request.getParameter("queryEndDate")!=null&&!request.getParameter("queryEndDate").equals("")){
			queryEndDate = df.format(df1.parse(request.getParameter("queryEndDate")));
		}
		String queryType = "ALL";
		if(request.getParameter("queryType")!=null&&!request.getParameter("queryType").equals("")){
			queryType = request.getParameter("queryType");
		}
		downLoadBillService service = new downLoadBillService();
		String jsonstr = service.downLoad_Days_Bill_Service(queryStartDate,queryEndDate, queryType);	
		PrintWriter out = response.getWriter();
		//System.out.println(jsonstr);
		out.println(jsonstr);
		out.flush();
		out.close();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
