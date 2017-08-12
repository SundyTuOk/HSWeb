package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sf.energy.prepayment.dao.ArchAnalysis_MonthDao;

public class ArchAnalysis_MonthServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);
		//futionchats
		if ("getFusionChartsStr".equals(method))
			getFusionChartsStr(request, response);
		//同比环比table
		if ("getTableStr".equals(method))
			getTableStr(request, response);
	}

	private void getTableStr(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchAnalysis_MonthDao aamd = new ArchAnalysis_MonthDao();
		String selectStyle = request.getParameter("selectStyle");
		String selectID = request.getParameter("selectID");
		String queryStyle = request.getParameter("queryStyle");
		String queryYear = request.getParameter("queryYear");
		
		String selectInfo = selectStyle + "|" + selectID;
		String queryInfo = queryStyle + "|" + queryYear;
		
		String resultInfo = "";
		resultInfo = aamd.getTableStr(selectInfo, queryInfo);
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
	}

	private void getFusionChartsStr(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		ArchAnalysis_MonthDao aamd = new ArchAnalysis_MonthDao();
		String selectStyle = request.getParameter("selectStyle");
		String selectID = request.getParameter("selectID");
		String queryStyle = request.getParameter("queryStyle");
		String queryYear = request.getParameter("queryYear");
		
		String selectInfo = selectStyle + "|" + selectID;
		String queryInfo = queryStyle + "|" + queryYear;
		
		String resultInfo = "";
		resultInfo = aamd.getFusionChartsStr(selectInfo, queryInfo);
		
		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
		
	}
}
