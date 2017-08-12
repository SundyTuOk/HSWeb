package com.sf.energy.expert.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.BasicInfo_saveHelper;
import com.sf.energy.expert.dao.BasicInfo_saveImpl;
import com.sf.energy.expert.model.BasicInfo_saveData;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class BasicInfo_saveServlet extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int BasicInfo_archID=0;
		int BasicInfo_Year=0;
		int BasicInfo_Month=0;
			
		if(Integer.parseInt(request.getParameter("BasicInfo_archID"))!=0)
		{
			BasicInfo_archID= Integer.parseInt(request.getParameter("BasicInfo_archID"));
		}
		if(Integer.parseInt(request.getParameter("BasicInfo_Year"))!=0)
		{
			BasicInfo_Year= Integer.parseInt(request.getParameter("BasicInfo_Year"));
		}
		if(Integer.parseInt(request.getParameter("BasicInfo_Month"))!=0)
		{
			BasicInfo_Month= Integer.parseInt(request.getParameter("BasicInfo_Month"));
		}
		
		Gson gson_BasicInfo_save = new Gson();
		BasicInfo_saveHelper rhi_BasicInfo_save = new BasicInfo_saveImpl();
		List<BasicInfo_saveData> BasicInfo_save_result = null;
		try
		{
			BasicInfo_save_result = rhi_BasicInfo_save.getBasicInfo_save(BasicInfo_Year);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		String BasicInfo_savedata = gson_BasicInfo_save.toJson(BasicInfo_save_result);
		//System.out.println(BasicInfo_savedata);
		out.println(BasicInfo_savedata);
		out.flush();
		out.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	
}


