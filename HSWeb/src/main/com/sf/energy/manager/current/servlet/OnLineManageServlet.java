package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.sf.energy.manager.current.dao.OnLineManageDao;
import com.sf.energy.powernet.dao.MuBarDao;
import com.sf.energy.weixin.service.downLoadBillService;

public class OnLineManageServlet extends HttpServlet
{

	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String method = req.getParameter("method");		
		if("SelectDATASITEInfo".equals(method)){
			SelectDATASITEInfo(req, resp);
		}
		if("SelectGatherInfo".equals(method)){
			SelectGatherInfo(req, resp);
		}
		if("SelectMeterInfo".equals(method)){
			SelectMeterInfo(req, resp);
		}
	}
	private void SelectDATASITEInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, SQLException
	{
		
		OnLineManageDao control=new OnLineManageDao();
		JSONArray json=control.SelectDATASITEInfo();
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	private void SelectGatherInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, SQLException
	{
		String DATASITE_ID = (String) req.getParameter("ID");
		OnLineManageDao control=new OnLineManageDao();
		JSONArray json=control.SelectGatherInfo(DATASITE_ID);
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	private void SelectMeterInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, SQLException
	{
		String Gather_ID = (String) req.getParameter("ID");
		OnLineManageDao control=new OnLineManageDao();
		JSONArray json=control.SelectMeterInfo(Gather_ID);
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException
	{
		// Put your code here
	}

}
