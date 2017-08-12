package com.sf.energy.expert.analysis.unitArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.sf.energy.expert.dao.ManualMonthDao;
import com.sf.energy.expert.model.EnergyDataTabData;
import com.sf.energy.expert.model.ManualEnergyModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.report.model.EnReportModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;


public class AverageEnergyServlet extends HttpServlet
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
		if("createFusionAllSchool".equalsIgnoreCase(request.getParameter("method")))
		{
			createFusionAllSchool(request,response);
		}
		if("createFusionStyle".equalsIgnoreCase(request.getParameter("method")))
		{
			createFusionStyle(request,response);
		}
		if("createFusion".equalsIgnoreCase(request.getParameter("method")))
		{
			createFusion(request,response);
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

	
	
	
	private void createFusionAllSchool(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
		int queryYear=0;
		int queryMonth=0;
		String archStyle="";
		DictionaryValueDao dvd=new DictionaryValueDao();
		ArrayList<DictionaryValueModel> archStyleList=new ArrayList<DictionaryValueModel>();
		ManualEnergyModel manualModel=new ManualEnergyModel();
		ManualMonthDao manualDao=new ManualMonthDao();
		Map<String,ManualEnergyModel> manualMap=new HashMap<String,ManualEnergyModel>();
		
		
		if(Integer.parseInt(request.getParameter("queryYear"))!=0)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		
		try
		{
			archStyleList=dvd.listDictionaryValueByName("建筑分类");
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		
		for (int i = 0; i < archStyleList.size(); i++)
		{
			archStyle=archStyleList.get(i).getDictionaryValueNum();
			
			if(queryMonth==0)//查整年
			{
				try
				{
					manualModel=manualDao.queryArchStyleEnergyAllYear(archStyle, queryYear);
				} catch (SQLException e)
				{
					
					e.printStackTrace();
				}
				
				manualMap.put(archStyleList.get(i).getDictionaryValue().trim(), manualModel);
				
			}
			else //某月
			{
				try
				{
					manualModel=manualDao.queryArchStyleEnergyMonth(archStyle, queryYear,queryMonth);
				} catch (SQLException e)
				{
					
					e.printStackTrace();
				}
				
				manualMap.put(archStyleList.get(i).getDictionaryValue().trim(), manualModel);
			}
			
		}
		
		Gson gson=new Gson();
		String gsonString=gson.toJson(manualMap);
		
		
		////System.out.println("全校的能耗json"+gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();
		
	}
	private void createFusionStyle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
		ArchitectureDao archDao=null;
		
		int queryYear=0;
		int queryMonth=0;
		String archStyle="";
		
		archDao=new ArchitectureDao();
		
		ManualEnergyModel manualModel=new ManualEnergyModel();
		ManualMonthDao manualDao=new ManualMonthDao();
		Map<String,ManualEnergyModel> manualMap=new HashMap<String,ManualEnergyModel>();
		ArrayList<Architecture> archList=new ArrayList<Architecture>();
		
		if(Integer.parseInt(request.getParameter("queryYear"))!=0)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		if(request.getParameter("archStyle")!=null)
		{
			archStyle= request.getParameter("archStyle").trim();
		}
		
		
		
		try
		{
			archList=archDao.queryArchByStyle(archStyle.charAt(0));
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		
		for (int i = 0; i < archList.size(); i++)
		{
		
			if(queryMonth==0)//查整年
			{
				try
				{
					manualModel=manualDao.queryArchEnergyAllYear(archList.get(i).getId(), queryYear);
					manualModel.setArchStyleName(archDao.queryArchStyleName(archStyle.charAt(0)).trim());//获取建筑类别
				} catch (SQLException e)
				{
					
					e.printStackTrace();
				}
				
				manualMap.put(archList.get(i).getName().trim(), manualModel);
				
			}
			else //某月
			{
				try
				{
					manualModel=manualDao.queryArchEnergyMonth(archList.get(i).getId(), queryYear,queryMonth);
					manualModel.setArchStyleName(archDao.queryArchStyleName(archStyle.charAt(0)).trim());//获取建筑类别
				} catch (SQLException e)
				{
					
					e.printStackTrace();
				}
				
				manualMap.put(archList.get(i).getName().trim(), manualModel);
			}
			
		}
		
		Gson gson=new Gson();
		String gsonString=gson.toJson(manualMap);
		
		
		////System.out.println("某建筑类别的能耗json"+gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();
	}
	private void createFusion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
		
		
		int queryYear=0;
		int queryMonth=0;
		int archID=0;
		
		
		ManualEnergyModel manualModel=new ManualEnergyModel();
		ManualMonthDao manualDao=new ManualMonthDao();
		
		
		
		if(Integer.parseInt(request.getParameter("queryYear"))!=0)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		if(request.getParameter("archID")!=null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		
		
		
		
		
		if(queryMonth==0)//查整年
		{
			try
			{
				manualModel=manualDao.queryArchEnergyAllYear(archID, queryYear);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			
		}
		else //某月
		{
			try
			{
				manualModel=manualDao.queryArchEnergyMonth(archID, queryYear,queryMonth);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			
		}
		
		Gson gson=new Gson();
		String gsonString=gson.toJson(manualModel);
		
		////System.out.println("某建筑的能耗json"+gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();
	}
}
