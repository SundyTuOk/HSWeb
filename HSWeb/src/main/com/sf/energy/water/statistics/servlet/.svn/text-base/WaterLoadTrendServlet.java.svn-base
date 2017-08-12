package com.sf.energy.water.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.statistics.dao.AmLoadTrendHelper;
import com.sf.energy.statistics.dao.AmLoadTrendImpl;
import com.sf.energy.statistics.model.AmLoadTrendData;
import com.sf.energy.water.statistics.dao.WaterLoadTrendHelper;
import com.sf.energy.water.statistics.dao.WaterLoadTrendImpl;
import com.sf.energy.water.statistics.model.WaterLoadTrendData;

public class WaterLoadTrendServlet extends HttpServlet {

	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			WaterLoadTrendImp( request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	private void  WaterLoadTrendImp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int waterID = 0;
		String time = "";
		
		if(request.getParameter("waterID") != null)
		{
			waterID = Integer.parseInt(request.getParameter("waterID"));
			//System.out.println("水表ID"+waterID);
		}
		if(request.getParameter("time") != null)
		{
			time = request.getParameter("time");
			//System.out.println("时间"+time);
		}
		
		Gson gson = new Gson();
		WaterLoadTrendImpl  WaterLoadTrend = new WaterLoadTrendImpl();
		List<WaterLoadTrendData>  	waterLoadTrend_result = null;
	
		waterLoadTrend_result = WaterLoadTrend.getcountWaterData(waterID, time);
	
		String data =  gson.toJson(waterLoadTrend_result);
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}


}
