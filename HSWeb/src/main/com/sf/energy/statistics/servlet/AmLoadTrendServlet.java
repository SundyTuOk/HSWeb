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
import com.sf.energy.statistics.dao.AmLoadTrendHelper;
import com.sf.energy.statistics.dao.AmLoadTrendImpl;
import com.sf.energy.statistics.model.AmLoadTrendData;
import com.sf.energy.statistics.model.ArcAmDetailData;

public class AmLoadTrendServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			AmLoadTrendImp( request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	private void  AmLoadTrendImp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int amID = 0;
		String time = "";
		
		if(request.getParameter("amID") != null)
		{
			amID = Integer.parseInt(request.getParameter("amID"));
			//System.out.println("电表ID"+amID);
		}
		if(request.getParameter("time") != null)
		{
			time = request.getParameter("time");
			//System.out.println("时间"+time);
		}
		
		Gson gson_AmLoadTrend = new Gson();
		AmLoadTrendHelper  rhi_AmLoadTrend = new AmLoadTrendImpl();
		List<AmLoadTrendData>  	AmLoadTrend_result = null;
	
		AmLoadTrend_result = rhi_AmLoadTrend.getAmLoadTrend(amID,  time);
	
		String AmLoadTrenddata =  gson_AmLoadTrend.toJson(AmLoadTrend_result);
		//System.out.println(AmLoadTrenddata);
		PrintWriter out = response.getWriter();
		out.println(AmLoadTrenddata);
		out.flush();
		out.close();
	}


}
