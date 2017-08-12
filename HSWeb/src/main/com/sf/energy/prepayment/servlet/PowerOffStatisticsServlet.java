package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

import com.sf.energy.prepayment.dao.PowerOffStatisticsDao;

public class PowerOffStatisticsServlet extends HttpServlet
{
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String method = request.getParameter("method");
		// System.out.println("method:" + method);
		if("queryEXFZInfo".equals(method))
			queryEXFZInfo(request, response);
	}
	
	private void queryEXFZInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int priceId = 0;
		if(request.getParameter("priceId")!=null){
			priceId = Integer.parseInt(request.getParameter("priceId"));
		}
		String startTime = df.format(new Date());
		if(request.getParameter("startTime")!=null){
			startTime = request.getParameter("startTime");
		}
		String endtTime = df.format(new Date());
		if(request.getParameter("endTime")!=null){
			endtTime = request.getParameter("endTime");
		}
		PowerOffStatisticsDao dao = new PowerOffStatisticsDao();
		JSONArray array = dao.queryEXFZInfoByPrice(priceId,startTime,endtTime);
		PrintWriter out = response.getWriter();
		out.write(array.toString());
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		findMethod(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doGet(request, response);
	}

}
