package com.sf.energy.expert.forecast;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sf.energy.expert.dao.TargetAmDao;
import com.sf.energy.expert.dao.TargetWaterDao;
import com.sf.energy.expert.model.ForecastModel;
import com.sf.energy.expert.model.TargetAmModel;
import com.sf.energy.expert.model.TargetWaterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;

import com.sf.energy.project.system.dao.InformationDao;
import com.sf.energy.project.system.model.Architecture;

import com.sf.energy.project.system.model.InformationModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class EnergyTargetServlet extends HttpServlet
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
		
		if("saveEnergyTargetValue".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				saveEnergyTargetValue(request, response);
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}	
		}
		
		if("showTargetFirst".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				showTargetFirst(request, response);
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

	
	/**
	 * 如果archID是非0整数，则查询某栋建筑
	 * @throws SQLException 
	 */
	private void createFusion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		int energyFlag=Integer.parseInt(request.getParameter("energyFlag"));
		String fenxiangName=request.getParameter("fenxiangName");
		String targetID=request.getParameter("targetID");
		int targetFlag=Integer.parseInt(request.getParameter("targetFlag"));
		int queryYear=Integer.parseInt(request.getParameter("queryYear"));
		float targetValue=Float.parseFloat(request.getParameter("targetValue"));
		
		ForecastDao fd=new ForecastDao();
		ArrayList<ForecastModel> listLastYear=new ArrayList<ForecastModel>();
		
		listLastYear=fd.getAllSchool12MonthInfoTarget(fenxiangName, queryYear, energyFlag, targetFlag, targetID,targetValue);
		
		Gson json =new Gson();
        String result=json.toJson(listLastYear);
		////System.out.println("能源预测的json"+result);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}
	
	@SuppressWarnings("deprecation")
	private void saveEnergyTargetValue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		
		int targetFlag=-1;//1全校、 2建筑类别、3建筑、4区域、5部门
		
		String targetID="";
		int energyFlag=-1;
		String targetName="";
		int queryYear=0;
		TargetAmModel taModel=new TargetAmModel();
		TargetWaterModel waterTargetModel=new TargetWaterModel();
		TargetWaterDao waterTargetDao=new TargetWaterDao();
		TargetAmDao taDao=new TargetAmDao();
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		queryYear=new Date().getYear()+1900;//当前年
		if(request.getParameter("targetFlag")!=null)
		{
			targetFlag=Integer.parseInt(request.getParameter("targetFlag"));
		}
		if(request.getParameter("energyFlag")!=null)
		{
			energyFlag=Integer.parseInt(request.getParameter("energyFlag"));
		}
		if(request.getParameter("targetID")!=null)
		{
			targetID=request.getParameter("targetID");
		}
	
		if(request.getParameter("targetName")!=null)
		{
			targetName=request.getParameter("targetName");
		}
		boolean b=false;
		if(energyFlag==1||energyFlag==3)//用电
		{
			
			taModel.setTargetIndex(targetID);
			taModel.setTargetStyle(targetFlag);//建筑
			taModel.setTargetName(targetName);
			taModel.setTargetYear(queryYear);
			taModel.setM1(Float.parseFloat(request.getParameter("m1")));
			taModel.setM2(Float.parseFloat(request.getParameter("m2")));
			taModel.setM3(Float.parseFloat(request.getParameter("m3")));
			taModel.setM4(Float.parseFloat(request.getParameter("m4")));
			taModel.setM5(Float.parseFloat(request.getParameter("m5")));
			taModel.setM6(Float.parseFloat(request.getParameter("m6")));
			taModel.setM7(Float.parseFloat(request.getParameter("m7")));
			taModel.setM8(Float.parseFloat(request.getParameter("m8")));
			taModel.setM9(Float.parseFloat(request.getParameter("m9")));
			taModel.setM10(Float.parseFloat(request.getParameter("m10")));
			taModel.setM11(Float.parseFloat(request.getParameter("m11")));
			taModel.setM12(Float.parseFloat(request.getParameter("m12")));
			taModel.setTargetMan(Integer.parseInt(request.getParameter("queryThisMan")));
			taModel.setTargetTime(new Date().toLocaleString());
		}	
		
		if(energyFlag==2||energyFlag==4)//用水
		{
			waterTargetModel.setTargetIndex(targetID);
			waterTargetModel.setTargetStyle(targetFlag);//建筑
			waterTargetModel.setTargetName(targetName);
			waterTargetModel.setTargetYear(queryYear);
			waterTargetModel.setM1(Float.parseFloat(request.getParameter("m1")));
			waterTargetModel.setM2(Float.parseFloat(request.getParameter("m2")));
			waterTargetModel.setM3(Float.parseFloat(request.getParameter("m3")));
			waterTargetModel.setM4(Float.parseFloat(request.getParameter("m4")));
			waterTargetModel.setM5(Float.parseFloat(request.getParameter("m5")));
			waterTargetModel.setM6(Float.parseFloat(request.getParameter("m6")));
			waterTargetModel.setM7(Float.parseFloat(request.getParameter("m7")));
			waterTargetModel.setM8(Float.parseFloat(request.getParameter("m8")));
			waterTargetModel.setM9(Float.parseFloat(request.getParameter("m9")));
			waterTargetModel.setM10(Float.parseFloat(request.getParameter("m10")));
			waterTargetModel.setM11(Float.parseFloat(request.getParameter("m11")));
			waterTargetModel.setM12(Float.parseFloat(request.getParameter("m12")));
			waterTargetModel.setTargetMan(Integer.parseInt(request.getParameter("queryThisMan")));
			waterTargetModel.setTargetTime(new Date().toLocaleString());
		}
		
		
		
		if(request.getParameter("fenxiangName").equalsIgnoreCase("energy"))
		{
			
			taModel.setTargetFenlei("用电预测(总)");
			if(taDao.hasSame(targetID, targetFlag, queryYear, "用电预测(总)"))//有相同的指标数据就先删除
			{
				taDao.delete(targetID, targetFlag, queryYear, "用电预测(总)");
				b=taDao.insert(taModel);
			}	
			else 
			{
				b=taDao.insert(taModel);
			}
			
		}
		else if (request.getParameter("fenxiangName").equalsIgnoreCase("water"))
		{
			waterTargetModel.setTargetFenlei("用水预测(总)");
			if(waterTargetDao.hasSame(targetID, targetFlag, queryYear, "用水预测(总)"))
			{
				waterTargetDao.delete(targetID, targetFlag, queryYear, "用水预测(总)");
				b=waterTargetDao.insert(waterTargetModel);
			}
			else 
			{
				b=waterTargetDao.insert(waterTargetModel);
			}
			
		}
		else if(request.getParameter("fenxiangName").equalsIgnoreCase("照明插座")||request.getParameter("fenxiangName").equalsIgnoreCase("空调用电")||request.getParameter("fenxiangName").equalsIgnoreCase("动力用电")||request.getParameter("fenxiangName").equalsIgnoreCase("特殊用电"))
		{
			taModel.setTargetFenlei(request.getParameter("fenxiangName"));
			if(taDao.hasSame(targetID, targetFlag, queryYear, request.getParameter("fenxiangName")))//有相同的指标数据就先删除
			{
				taDao.delete(targetID, targetFlag, queryYear, request.getParameter("fenxiangName"));
				b=taDao.insert(taModel);
			}	
			else 
			{
				b=taDao.insert(taModel);
			}
			
		}
		else if(request.getParameter("fenxiangName").equalsIgnoreCase("公共洗手间")||request.getParameter("fenxiangName").equalsIgnoreCase("食堂餐厅")||request.getParameter("fenxiangName").equalsIgnoreCase("澡堂淋浴")||request.getParameter("fenxiangName").equalsIgnoreCase("消防浇灌"))
		{
			waterTargetModel.setTargetFenlei(request.getParameter("fenxiangName"));
			if(waterTargetDao.hasSame(targetID, targetFlag, queryYear, request.getParameter("fenxiangName")))
			{
				waterTargetDao.delete(targetID, targetFlag, queryYear, request.getParameter("fenxiangName"));
				b=waterTargetDao.insert(waterTargetModel);
			}
			else 
			{
				b=waterTargetDao.insert(waterTargetModel);
			}
		}
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(b);
		out.flush();
		out.close();
		
	}
	
	//查询时候先从库里面计算出以前的指标
	private void showTargetFirst(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		ForecastDao fd=new ForecastDao(); 
		ArrayList<ForecastModel> listLastYear=new ArrayList<ForecastModel>();
		int targetFlag=1;//1全校、 2建筑类别、3建筑、4区域、5部门	
		String targetID="";
		int energyFlag=1;
		
		String fenxiangName="";
		int queryYear=0;
		TargetAmModel taModel=new TargetAmModel();
		TargetWaterModel waterTargetModel=new TargetWaterModel();
		TargetWaterDao waterTargetDao=new TargetWaterDao();
		TargetAmDao taDao=new TargetAmDao();
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		
		if(request.getParameter("queryYear")!=null)
		{
			queryYear=Integer.parseInt(request.getParameter("queryYear"));
		}
		if(request.getParameter("targetFlag")!=null)
		{
			targetFlag=Integer.parseInt(request.getParameter("targetFlag"));
		}
		if(request.getParameter("energyFlag")!=null)
		{
			energyFlag=Integer.parseInt(request.getParameter("energyFlag"));
		}
		if(request.getParameter("targetID")!=null)
		{
			targetID=request.getParameter("targetID");
		}

		if(request.getParameter("fenxiangName").equalsIgnoreCase("energy"))
		{		
			fenxiangName="用电预测(总)";
		}
		else if (request.getParameter("fenxiangName").equalsIgnoreCase("water"))
		{
			fenxiangName="用水预测(总)";
		}
		else if(request.getParameter("fenxiangName").equalsIgnoreCase("照明插座")||request.getParameter("fenxiangName").equalsIgnoreCase("空调用电")||request.getParameter("fenxiangName").equalsIgnoreCase("动力用电")||request.getParameter("fenxiangName").equalsIgnoreCase("特殊用电"))
		{
			fenxiangName=request.getParameter("fenxiangName");	
		}
		else if(request.getParameter("fenxiangName").equalsIgnoreCase("公共洗手间")||request.getParameter("fenxiangName").equalsIgnoreCase("食堂餐厅")||request.getParameter("fenxiangName").equalsIgnoreCase("澡堂淋浴")||request.getParameter("fenxiangName").equalsIgnoreCase("消防浇灌"))
		{
			fenxiangName=request.getParameter("fenxiangName");
		}
		
		//先查出能源预测的数据
		listLastYear=fd.getAllSchool12MonthInfo(fenxiangName, queryYear+1, energyFlag, targetFlag, targetID+"");
		//下面查出指标
		if(energyFlag==1||energyFlag==3)//用电
		{
			//查询出数据库中原有的，一定要记得，能源预测的是前一年，查询的是当前年
			taModel=taDao.queryByIndex(targetID, targetFlag, queryYear+1, fenxiangName);
			//判断这个指标是否已经审计过
			boolean isAudit=false;
			if(taDao.isAudit(taModel.getTargetID()))//已经审计过
			{
				isAudit=true;
			}
			Gson json =new Gson();
			String result=json.toJson(new Object[]{listLastYear,taModel,isAudit});//拼成json一起传过去
			////System.out.println("能源指标用电的json："+result);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		}	
		
		if(energyFlag==2||energyFlag==4)//用水
		{
			//查询出数据库中原有的，一定要记得，能源预测的是前一年，查询的是当前年
			waterTargetModel=waterTargetDao.queryByIndex(targetID, targetFlag, queryYear+1, fenxiangName);
			boolean isAudit=false;
			if(waterTargetDao.isAudit(waterTargetModel.getTargetID()))//已经审计过
			{
				isAudit=true;
			}
			Gson json =new Gson();
	        String result=json.toJson(new Object[]{listLastYear,waterTargetModel,isAudit});
			////System.out.println("能源指标用水的json："+result);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		}
			
	}
	
	
}
