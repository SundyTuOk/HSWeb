package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.EstimateDao;
import com.sf.energy.expert.model.EstimateModel;

public class RadarAnalysisServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		try {
			getRadarAnalysisData(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	private void getRadarAnalysisData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int year = 0; //查询的年份
		int style = 0;//建筑类型
		String Index_id = "";	  //建筑ID
		
		if(request.getParameter("year")!=null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("style")!=null)
		{
			style = Integer.parseInt(request.getParameter("style"));
		}
		if(request.getParameter("Index_id")!=null)
		{
			Index_id = request.getParameter("Index_id");
		}
		/*System.out.println(year);
		System.out.println(style);
		System.out.println(Index_id);*/
		EstimateDao   estd = null;
		EstimateModel estm = null;
		
		estd = new EstimateDao();
		estm = new EstimateModel();
		

		estm = estd.getTargetInfo(year, style, Index_id);


		
		String radardata = gson.toJson(estm);
		//System.out.println("xxx:"+radardata);
		out.println(radardata);
		out.flush();
		out.close();
	}

}
