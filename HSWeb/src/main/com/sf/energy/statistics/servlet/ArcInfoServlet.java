package com.sf.energy.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.statistics.dao.AmHelper;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;

public class ArcInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AmHelper  rhi_ArcAmDetail = (AmHelper) new AmHelperImpl();
		Map<Integer, String> arcInfo = null;
		try
		{
			arcInfo = rhi_ArcAmDetail.getAllParInfo();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_ArcAmDetail = new Gson();
		String arcGson =  gson_ArcAmDetail.toJson(arcInfo);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(arcGson);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
