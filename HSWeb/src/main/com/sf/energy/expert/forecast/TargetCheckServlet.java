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
import javax.servlet.http.HttpSession;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.TargetAmAuditDao;
import com.sf.energy.expert.dao.TargetAmDao;
import com.sf.energy.expert.dao.TargetWaterAuditDao;
import com.sf.energy.expert.dao.TargetWaterDao;

import com.sf.energy.expert.model.TargetAmAuditModel;
import com.sf.energy.expert.model.TargetAmModel;
import com.sf.energy.expert.model.TargetWaterAuditModel;
import com.sf.energy.expert.model.TargetWaterModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;


public class TargetCheckServlet extends HttpServlet
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
		if("getAllEnergyCheck".equalsIgnoreCase(request.getParameter("method")))
		{
			getAllEnergyCheck(request,response);
		}
		
		if("getAllEnergyCheckWater".equalsIgnoreCase(request.getParameter("method")))
		{
			getAllEnergyCheckWater(request,response);
		}
		
		if("addEnergyCheck".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				addEnergyCheck(request,response);
			} catch (NumberFormatException e)
			{
				
				e.printStackTrace();
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		
		if("deleteSomeEnergyCheck".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSomeEnergyCheck(request,response);
		}
		
		if("searchEnergyCheck".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				searchEnergyCheck(request,response);
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		/*if("createFusionChart".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				createFusionChart(request,response);
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}*/
		
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
	
	public void getAllEnergyCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		TargetAmAuditModel auditModel=null;
		TargetWaterAuditModel auditWaterModel=null;
		TargetAmModel taModel=null;
		TargetWaterModel waterModel=null;
		TargetAmAuditDao auditDao=new TargetAmAuditDao();
		TargetWaterAuditDao auditWaterDao=new TargetWaterAuditDao();
		TargetAmDao taDao=new TargetAmDao();
		TargetWaterDao waterDao=new TargetWaterDao();
		
		DecimalFormat df= new DecimalFormat("##0.00");
		
		int thePageCount = Integer.parseInt(request
				.getParameter("TargetCheckPageCount"));
		int thePageIndex = Integer.parseInt(request
				.getParameter("TargetCheckPageIndex"));
		
		ArrayList<TargetAmAuditModel> listAmAndWater=new ArrayList<TargetAmAuditModel>();
		float totalTargetValue=0;//年指标量
		float totalTargetMinusValue=0;//年指标差量
		try
		{
			
			
			listAmAndWater=auditDao.listAmAndWaterAudit(thePageCount, thePageIndex);
			
			////System.out.println(listWater.size());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("totalCount", auditDao.getTotal());
		json.add(jo);
		
		for (int i = 0; i < listAmAndWater.size(); i++)
		{
			jo = new JSONObject(); 
			auditModel=listAmAndWater.get(i);
			
			try
			{
				if(auditModel.getMeterType().equals("1"))//电表
				{
					taModel=taDao.queryByID(auditModel.getTargetID());
					totalTargetValue=taModel.getM1()+taModel.getM2()+taModel.getM3()+taModel.getM4()+taModel.getM5()+taModel.getM6()+taModel.getM7()+taModel.getM8()+taModel.getM9()+taModel.getM10()+taModel.getM11()+taModel.getM12();
					jo.put("type", "energy");
				}
				else {
					waterModel=waterDao.queryByID(auditModel.getTargetID());
					totalTargetValue=waterModel.getM1()+waterModel.getM2()+waterModel.getM3()+waterModel.getM4()+waterModel.getM5()+waterModel.getM6()+waterModel.getM7()+waterModel.getM8()+waterModel.getM9()+waterModel.getM10()+waterModel.getM11()+waterModel.getM12();
					jo.put("type", "water");
				}
				
				
				
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			totalTargetMinusValue=auditModel.getM1()+auditModel.getM2()+auditModel.getM3()+auditModel.getM4()+auditModel.getM5()+auditModel.getM6()+auditModel.getM7()+auditModel.getM8()+auditModel.getM9()+auditModel.getM10()+auditModel.getM11()+auditModel.getM12();
			
			jo.put("auditID", auditModel.getAuditID());
			jo.put("targetID", auditModel.getTargetID());
			jo.put("totalTargetValue", df.format(totalTargetValue));//年指标量
			jo.put("totalTargetMinusValue", df.format(totalTargetMinusValue));//年指标差量
			jo.put("targetLastTime", auditModel.getTargetLastTime());
			jo.put("targetTime", auditModel.getTargetTime());
			jo.put("targetManName", auditModel.getTargetManName());
			jo.put("targetState", auditModel.getTargetState());
			jo.put("archID", auditModel.getArchID());
			jo.put("archName", auditModel.getArchName());
			jo.put("targetFenlei", auditModel.getTargetFenlei());
			jo.put("targetYear", auditModel.getTargetYear());
			json.add(jo);
		}
		
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		////System.out.println("电水合并之后的json："+json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	public void getAllEnergyCheckWater(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
//		listWater=new ArrayList<TargetWaterAuditModel>();
//		float totalTargetValue=0;//年指标量
//		float totalTargetMinusValue=0;//年指标差量
//		try
//		{
//			listWater=auditWaterDao.listAllAudit();
//		} catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		
//		JSONArray json = new JSONArray();
//		JSONObject jo = new JSONObject(); 
//		for (int i = 0; i < listWater.size(); i++)
//		{
//			auditWaterModel=listWater.get(i);
//			
//			try
//			{
//				waterModel=waterDao.queryByID(auditWaterModel.getTargetID());
//				totalTargetValue=waterModel.getM1()+waterModel.getM2()+waterModel.getM3()+waterModel.getM4()+waterModel.getM5()+waterModel.getM6()+waterModel.getM7()+waterModel.getM8()+waterModel.getM9()+waterModel.getM10()+waterModel.getM11()+waterModel.getM12();
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			
//			totalTargetMinusValue=auditWaterModel.getM1()+auditWaterModel.getM2()+auditWaterModel.getM3()+auditWaterModel.getM4()+auditWaterModel.getM5()+auditWaterModel.getM6()+auditWaterModel.getM7()+auditWaterModel.getM8()+auditWaterModel.getM9()+auditWaterModel.getM10()+auditWaterModel.getM11()+auditWaterModel.getM12();
//			jo.put("auditID", auditWaterModel.getAuditID());
//			jo.put("targetID", auditWaterModel.getTargetID());
//			jo.put("totalTargetValue", df.format(totalTargetValue));//年指标量
//			jo.put("totalTargetMinusValue", df.format(totalTargetMinusValue));//年指标差量
//			jo.put("targetLastTime", auditWaterModel.getTargetLastTime());
//			jo.put("targetTime", auditWaterModel.getTargetTime());
//			jo.put("targetManName", auditWaterModel.getTargetManName());
//			jo.put("targetState", auditWaterModel.getTargetState());
//			jo.put("archID", auditWaterModel.getArchID());
//			jo.put("archName", auditWaterModel.getArchName());
//			jo.put("targetFenlei", auditWaterModel.getTargetFenlei());
//			jo.put("targetYear", auditWaterModel.getTargetYear());
//			json.add(jo);
//		}
//		response.setContentType("application/x-json");
//
//		PrintWriter out = response.getWriter();
//		//System.out.println(json.toString());
//		out.println(json.toString());
//		out.flush();
//		out.close();
	}
	
	@SuppressWarnings("deprecation")
	public void addEnergyCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException, SQLException
	{
		
		TargetAmAuditModel auditModel=null;
		TargetWaterAuditModel waterAuditModel=null;
		TargetAmAuditDao auditDao=new TargetAmAuditDao();
		TargetWaterAuditDao waterAuditDao=new TargetWaterAuditDao();
		
		TargetAmModel taModel=new TargetAmModel();
		TargetWaterModel waterTargetModel=new TargetWaterModel();
		TargetWaterDao waterTargetDao=new TargetWaterDao();
		TargetAmDao taDao=new TargetAmDao();
		
		 HttpSession session = request.getSession();
		 String userName = "";
         if (session.getAttribute("userName") != null)
         {
        	 userName = session.getAttribute("userName").toString();
         }
		
		int targetFlag=1;//1全校、 2建筑类别、3建筑、4区域、5部门	
		String targetID="";
		int energyFlag=1;
		
		String fenxiangName="";
		int queryYear=0;
		
		
		
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
		
		//首先判断是否操作了【能源指标】
		if(energyFlag==1||energyFlag==3)//用电
		{
			//查询出数据库中原有的，一定要记得，能源预测的是前一年，查询的是当前年
			taModel=taDao.queryByIndex(targetID, targetFlag, queryYear, fenxiangName);
			if(taModel.getTargetID()==0)//说明还未操作【能源指标】
			{
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println("hasNoTarget");
				out.flush();
				out.close();
			}	
			else
			{
				auditModel=new TargetAmAuditModel();
		    	auditModel.setTargetID(taModel.getTargetID());
		    	auditModel.setM1(Float.parseFloat(request.getParameter("changliang1")));//1月
		    	auditModel.setM2(Float.parseFloat(request.getParameter("changliang2")));
		    	auditModel.setM3(Float.parseFloat(request.getParameter("changliang3")));
		    	auditModel.setM4(Float.parseFloat(request.getParameter("changliang4")));
		    	auditModel.setM5(Float.parseFloat(request.getParameter("changliang5")));
		    	auditModel.setM6(Float.parseFloat(request.getParameter("changliang6")));
		    	auditModel.setM7(Float.parseFloat(request.getParameter("changliang7")));
		    	auditModel.setM8(Float.parseFloat(request.getParameter("changliang8")));
		    	auditModel.setM9(Float.parseFloat(request.getParameter("changliang9")));
		    	auditModel.setM10(Float.parseFloat(request.getParameter("changliang10")));
		    	auditModel.setM11(Float.parseFloat(request.getParameter("changliang11")));
		    	auditModel.setM12(Float.parseFloat(request.getParameter("changliang12")));
		    	auditModel.setTargetLastTime(new Date().toLocaleString());
		    	auditModel.setTargetManName(userName);
		    	if(Float.parseFloat(request.getParameter("changliang1"))<=0)
		    	{
		    		auditModel.setTargetState("良好");
		    	}	
		    	else
		    	{
		    		auditModel.setTargetState("超标");
		    	}
		    	
		    	boolean b=false;
		    	if(auditDao.hasSame(taModel.getTargetID()))
		    	{
		    		auditDao.deleteByTargetID(taModel.getTargetID());
		    		b=auditDao.insert(auditModel);
		    	}
		    	else 
		    	{
		    		b=auditDao.insert(auditModel);
				}
				
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(b);
				out.flush();
				out.close();
			}
			
			
		}	
		
		if(energyFlag==2||energyFlag==4)//用水
		{
			//查询出数据库中原有的，一定要记得，能源预测的是前一年，查询的是当前年
			waterTargetModel=waterTargetDao.queryByIndex(targetID, targetFlag, queryYear, fenxiangName);
			if(waterTargetModel.getTargetID()==0)//说明还未操作【能源指标】
			{
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println("hasNoTarget");
				out.flush();
				out.close();
			}	
			else
			{
				waterAuditModel=new TargetWaterAuditModel();
				waterAuditModel.setTargetID(waterTargetModel.getTargetID());
				waterAuditModel.setM1(Float.parseFloat(request.getParameter("changliang1")));//1月
				waterAuditModel.setM2(Float.parseFloat(request.getParameter("changliang2")));
				waterAuditModel.setM3(Float.parseFloat(request.getParameter("changliang3")));
				waterAuditModel.setM4(Float.parseFloat(request.getParameter("changliang4")));
				waterAuditModel.setM5(Float.parseFloat(request.getParameter("changliang5")));
				waterAuditModel.setM6(Float.parseFloat(request.getParameter("changliang6")));
				waterAuditModel.setM7(Float.parseFloat(request.getParameter("changliang7")));
				waterAuditModel.setM8(Float.parseFloat(request.getParameter("changliang8")));
				waterAuditModel.setM9(Float.parseFloat(request.getParameter("changliang9")));
				waterAuditModel.setM10(Float.parseFloat(request.getParameter("changliang10")));
				waterAuditModel.setM11(Float.parseFloat(request.getParameter("changliang11")));
				waterAuditModel.setM12(Float.parseFloat(request.getParameter("changliang12")));
				waterAuditModel.setTargetLastTime(new Date().toLocaleString());
				waterAuditModel.setTargetManName(userName);
		    	if(Float.parseFloat(request.getParameter("changliang1"))<=0)
		    	{
		    		waterAuditModel.setTargetState("良好");
		    	}	
		    	else
		    	{
		    		waterAuditModel.setTargetState("超标");
		    	}
		    		
		    	
		    	boolean b=false;
		    	if(waterAuditDao.hasSame(waterTargetModel.getTargetID()))
		    	{
		    		waterAuditDao.deleteByTargetID(waterTargetModel.getTargetID());
		    		b=waterAuditDao.insert(waterAuditModel);
		    	}
		    	else 
		    	{
		    		b=waterAuditDao.insert(waterAuditModel);
				}
				
				response.setContentType("application/x-json");
				PrintWriter out = response.getWriter();
				out.println(b);
				out.flush();
				out.close();
			}
		}
		
		
		
		
	}

	public void searchEnergyCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException
	{
		int targetFlag=1;//1全校、 2建筑类别、3建筑、4区域、5部门	
		String targetID="";
		int energyFlag=1;
		
		String fenxiangName="";
		int queryYear=0;
		
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
		
		ArrayList<TargetAmAuditModel> listAm=null;
		ArrayList<TargetWaterAuditModel> listWater=null;
		TargetAmAuditModel auditModel=null;
		TargetWaterAuditModel auditWaterModel=null;
		TargetAmModel taModel=null;
		TargetWaterModel waterModel=null;
		TargetAmAuditDao auditDao=new TargetAmAuditDao();
		TargetWaterAuditDao auditWaterDao=new TargetWaterAuditDao();
		TargetAmDao taDao=new TargetAmDao();
		TargetWaterDao waterDao=new TargetWaterDao();
		DecimalFormat df= new DecimalFormat("##0.00");
		
		float totalTargetValue=0;
		float totalTargetMinusValue=0;
		//首先判断是否操作了【能源指标】
		if(energyFlag==1||energyFlag==3)//用电
		{
			listAm=new ArrayList<TargetAmAuditModel>();
			listAm=auditDao.searchAudit(targetID, targetFlag, queryYear, fenxiangName);
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject(); 
			jo.put("totalCount", 1);//这样的强查询永远只有一条记录
			json.add(jo);
			for (int i = 0; i < listAm.size(); i++)
			{
				auditModel=listAm.get(i);
				taModel=taDao.queryByID(auditModel.getTargetID());
				totalTargetValue=taModel.getM1()+taModel.getM2()+taModel.getM3()+taModel.getM4()+taModel.getM5()+taModel.getM6()+taModel.getM7()+taModel.getM8()+taModel.getM9()+taModel.getM10()+taModel.getM11()+taModel.getM12();			
				totalTargetMinusValue=auditModel.getM1()+auditModel.getM2()+auditModel.getM3()+auditModel.getM4()+auditModel.getM5()+auditModel.getM6()+auditModel.getM7()+auditModel.getM8()+auditModel.getM9()+auditModel.getM10()+auditModel.getM11()+auditModel.getM12();
				jo.put("type", "energy");
				jo.put("auditID", auditModel.getAuditID());
				jo.put("targetID", auditModel.getTargetID());
				jo.put("totalTargetValue", df.format(totalTargetValue));//年指标量
				jo.put("totalTargetMinusValue", df.format(totalTargetMinusValue));//年指标差量
				jo.put("targetLastTime", auditModel.getTargetLastTime());
				jo.put("targetTime", auditModel.getTargetTime());
				jo.put("targetManName", auditModel.getTargetManName());
				jo.put("targetState", auditModel.getTargetState());
				jo.put("archID", auditModel.getArchID());
				jo.put("archName", auditModel.getArchName());
				jo.put("targetFenlei", auditModel.getTargetFenlei());
				jo.put("targetYear", auditModel.getTargetYear());
				json.add(jo);
			}
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			////System.out.println("用电审计一览："+json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}
		else if(energyFlag==2||energyFlag==4)//用水
		{
			listWater=new ArrayList<TargetWaterAuditModel>();
			listWater=auditWaterDao.searchAudit(targetID, targetFlag, queryYear, fenxiangName);
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("totalCount", 1);//这样的强查询永远只有一条记录
			json.add(jo);
			for (int i = 0; i < listWater.size(); i++)
			{
				auditWaterModel=listWater.get(i);
				waterModel=waterDao.queryByID(auditWaterModel.getTargetID());
				totalTargetValue=waterModel.getM1()+waterModel.getM2()+waterModel.getM3()+waterModel.getM4()+waterModel.getM5()+waterModel.getM6()+waterModel.getM7()+waterModel.getM8()+waterModel.getM9()+waterModel.getM10()+waterModel.getM11()+waterModel.getM12();
				totalTargetMinusValue=auditWaterModel.getM1()+auditWaterModel.getM2()+auditWaterModel.getM3()+auditWaterModel.getM4()+auditWaterModel.getM5()+auditWaterModel.getM6()+auditWaterModel.getM7()+auditWaterModel.getM8()+auditWaterModel.getM9()+auditWaterModel.getM10()+auditWaterModel.getM11()+auditWaterModel.getM12();
				jo.put("type", "water");
				jo.put("auditID", auditWaterModel.getAuditID());
				jo.put("targetID", auditWaterModel.getTargetID());
				jo.put("totalTargetValue", df.format(totalTargetValue));//年指标量
				jo.put("totalTargetMinusValue", df.format(totalTargetMinusValue));//年指标差量
				jo.put("targetLastTime", auditWaterModel.getTargetLastTime());
				jo.put("targetTime", auditWaterModel.getTargetTime());
				jo.put("targetManName", auditWaterModel.getTargetManName());
				jo.put("targetState", auditWaterModel.getTargetState());
				jo.put("archID", auditWaterModel.getArchID());
				jo.put("archName", auditWaterModel.getArchName());
				jo.put("targetFenlei", auditWaterModel.getTargetFenlei());
				jo.put("targetYear", auditWaterModel.getTargetYear());
				json.add(jo);
			}
			response.setContentType("application/x-json");

			PrintWriter out = response.getWriter();
			////System.out.println("用水审计一览："+json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}
		
		
		
		
	}
	
	public void deleteSomeEnergyCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		TargetAmAuditDao auditDao=new TargetAmAuditDao();
		TargetWaterAuditDao auditWaterDao=new TargetWaterAuditDao();
		
		boolean b=false;
		String idList="";
		String idListWater="";
		if(request.getParameter("theIDList")!=null)
		{
			idList=request.getParameter("theIDList");
		}
		if(request.getParameter("theIDListWater")!=null)
		{
			idListWater=request.getParameter("theIDListWater");
		}
		String[] list = idList.split(" ");
		String[] listWater=idListWater.split(" ");
		if(list.length!=0 && !list[0].equals(""))
		{
			for (String id : list)
			{
				try
				{
					b=auditDao.delete(Integer.parseInt(id));
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if(listWater.length!=0 && !listWater[0].equals(""))
		{
			for (String id : listWater)
			{
				try
				{
					b=auditWaterDao.delete(Integer.parseInt(id));
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
		PrintWriter out = response.getWriter();
		////System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}
	
	
	
	public boolean isNumber(String number) 
	{  
        if(number==null) return false;  
        return number.matches("[+-]?[1-9]+[0-9]*(\\.[0-9]+)?");      
    }  
      
    public boolean isAlpha(String alpha) 
    {  
        if(alpha==null) return false;  
        return alpha.matches("[a-zA-Z]+");      
    }  
      
    public boolean isChinese(String chineseContent) 
    {  
        if(chineseContent==null) return false;  
        return chineseContent.matches("[\u4e00-\u9fa5]");  
    }  
    
   
}
