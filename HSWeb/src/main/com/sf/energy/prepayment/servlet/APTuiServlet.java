package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import com.sf.energy.prepayment.dao.APTuiDao;
import com.sf.energy.prepayment.model.APTuiModel;
import com.sf.energy.prepayment.model.ExceptionModel;

public class APTuiServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
		// System.out.println("method:" + method);
		// 查询分合闸信息
		if ("getAPTuiInfo".equals(method))
			getAPTuiInfo(req, resp);
		
		
	}

	private void getAPTuiInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String AmMeter_ID = req.getParameter("AmMeter_ID");
		String saleInfoNum = req.getParameter("saleInfoNum");
		APTuiDao dao=new APTuiDao();
		
		APTuiModel model = dao.queryInfo(AmMeter_ID,saleInfoNum);
		JSONArray json = new JSONArray();
		if (model!=null)
		{
		  json.add(model);
		}
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
}
