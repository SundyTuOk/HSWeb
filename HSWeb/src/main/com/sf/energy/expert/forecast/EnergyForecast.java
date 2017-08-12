package com.sf.energy.expert.forecast;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.ForecastDao;
import com.sf.energy.expert.model.ForecastModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.dao.InformationDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.project.system.model.InformationModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.util.GetSystemInfo;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class EnergyForecast extends HttpServlet
{
	
	
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		if("createFusion".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				createFusion(request, response);
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}	
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		this.doGet(request, response);
	}

	
	private void createFusion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		int energyFlag=Integer.parseInt(request.getParameter("energyFlag"));
		String fenxiangName=request.getParameter("fenxiangName");
		String targetID=request.getParameter("targetID");
		int targetFlag=Integer.parseInt(request.getParameter("targetFlag"));
		int queryYear=Integer.parseInt(request.getParameter("queryYear"));
		
		ForecastDao fd=new ForecastDao();
		ArrayList<ForecastModel> listLastYear=new ArrayList<ForecastModel>();
		
		listLastYear=fd.getAllSchool12MonthInfo(fenxiangName, queryYear, energyFlag, targetFlag, targetID);
		
		boolean b=false;
		for(int i=0;i<listLastYear.size();i++)
		{
			if(listLastYear.get(i).getzGross()!=0)//有前一年的数据
			{
				b=true;
				break;
			}	
		}
		if(b==false)
		{
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("nodata", "nodata");
			json.add(jo);
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			////System.out.println("forecast nodata!");
			out.println(json.toString());
			out.flush();
			out.close();
		}
		else 
		{
			Gson json =new Gson();
	        String result=json.toJson(listLastYear);
			////System.out.println("能源预测的json"+result);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		}
		
		
	}
	
	
	
	
}
