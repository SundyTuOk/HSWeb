package com.sf.energy.expert.analysis.unitArea;

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

import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class AverageWaterServlet extends HttpServlet
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

//	public void createFusionChart(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException
//	{
//		archDao=new ArchitectureDao();
//		areaModel=new Area();
//		areaDao=new AreaDao();
//		wrh= new WaterReportHelperImpl();
//		df= new DecimalFormat("###,##0.00");
//			
//		if(Character.isLetter(request.getParameter("archID").charAt(0)))//查询建筑类别的所有建筑
//		{
//			createFusionStyle(request, response);
//		}
//		else if (Integer.parseInt(request.getParameter("archID"))==0)//查询整个学校的所有建筑
//		{
//			createFusionAllSchool(request, response);
//		}
//		else {//某个建筑
//			createFusion(request, response);
//		}
//		
//	}
	/**
	 * 如果archID是0，则表示选择的是跟部门，该类学校所有建筑都要查询
	 */
	private void createFusionAllSchool(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Architecture archModel=null;
		ArchitectureDao archDao=null;
		Area areaModel=null;
		AreaDao areaDao=null;
		WaterReportModel energyModel=null;
		WaterReportHelper wrh= null;
		DecimalFormat df =null;
		int archID=-1;
		int queryYear=0;
		int queryMonth=0;
		char archStyle='a';
		
		archDao=new ArchitectureDao();
		areaModel=new Area();
		areaDao=new AreaDao();
		wrh= new WaterReportHelperImpl();
		df= new DecimalFormat("####0.00");
		
		float totalByStyle=0;
		int totalMan=0;
		String archStyleName=null;
		if(request.getParameter("queryYear")!=null)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		
		DictionaryValueDao dvd=new DictionaryValueDao();
		ArrayList<DictionaryValueModel> archStyleList=new ArrayList<DictionaryValueModel>();
		
		try
		{
			archStyleList=dvd.listDictionaryValueByName("建筑分类");
			////System.out.println("style"+archStyleList.size());
			
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		for (int i = 0; i < archStyleList.size(); i++)
		{
			
			archStyle=archStyleList.get(i).getDictionaryValueNum().charAt(0);
			try
			{
				archStyleName=archDao.queryArchStyleName(archStyle);
			} catch (SQLException e1)
			{
				
				e1.printStackTrace();
			}
			ArrayList<Architecture> archList=new ArrayList<Architecture>();
			try
			{
				archList=archDao.queryArchByStyle(archStyle);
				////System.out.println("arch"+archList.size());
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
	
			totalMan=0;
			totalByStyle=0;
			for(int j=0;j<archList.size();j++)
			{
				energyModel=new WaterReportModel();
				archModel=archList.get(j);
				totalMan+=archModel.getCountMan();
				if(queryMonth==0)//查整年
				{
					try
					{
						energyModel=wrh.getArcCountByYear(archModel.getId(),queryYear);
					} catch (SQLException e)
					{	
						e.printStackTrace();
					}
					
					totalByStyle += energyModel.getTotalWaterCount();
				}
				else //查某月
				{
					try
					{
						energyModel=wrh.getArcCountByMonth(archModel.getId(),queryYear,queryMonth);
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
					totalByStyle += energyModel.getTotalWaterCount();
				}
				
			}//end for every arch	
				
			jo.put("archID", 0);//js里面用这个标记去组xml
			jo.put("archStyleName", archStyleName);
			jo.put("archCountMan", totalMan);
			jo.put("queryYear", queryYear);
			jo.put("queryMonth", queryMonth);
			jo.put("totalEnergy",df.format(energyModel.getTotalWaterCount()));
			String value="";
			if(totalMan==0)
				value="0.00";
			else
				value=df.format( totalByStyle / totalMan);
				
			jo.put("everyManEnergy", value);
			////System.out.println("value"+value);
			json.add(jo);	

		}//end for every style
		
		////System.out.println("查询学校的所有建筑json"+json.toString());
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	
	/**
	 * 如果archID是字母，则表示选择的是建筑类别，该类别的所有建筑都要查询
	 */
	private void createFusionStyle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Architecture archModel=null;
		ArchitectureDao archDao=null;
		Area areaModel=null;
		AreaDao areaDao=null;
		WaterReportModel energyModel=null;
		WaterReportHelper wrh= null;
		DecimalFormat df =null;
		int archID=-1;
		int queryYear=0;
		int queryMonth=0;
		char archStyle='a';
		
		archDao=new ArchitectureDao();
		areaModel=new Area();
		areaDao=new AreaDao();
		wrh= new WaterReportHelperImpl();
		df= new DecimalFormat("####0.00");
		
		if(request.getParameter("queryYear")!=null)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		if(request.getParameter("archStyle")!=null)
		{
			archStyle=request.getParameter("archStyle").charAt(0);
		}
		
		ArrayList<Architecture> archList=new ArrayList<Architecture>();
		try
		{
			archList=archDao.queryArchByStyle(archStyle);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		for(int i=0;i<archList.size();i++)
		{
			energyModel=new WaterReportModel();
			archModel=archList.get(i);
			//totalMan+=archModel.getCountMan();
			if(queryMonth==0)//查整年
			{
				try
				{
					energyModel=wrh.getArcCountByYear(archModel.getId(),queryYear);
				} catch (SQLException e)
				{	
					e.printStackTrace();
				}
				
				//totalByStyle += energyModel.getTotalWaterCount();
			}
			else //查某月
			{
				try
				{
					energyModel=wrh.getArcCountByMonth(archModel.getId(),queryYear,queryMonth);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				//totalByStyle += energyModel.getTotalWaterCount();
			}
			
			
			jo.put("archName", archModel.getName());
			jo.put("archCountMan", archModel.getCountMan());
			jo.put("archID", archStyle);//某一个类别
			jo.put("archStyleName", archModel.getArchStyleName());
			jo.put("queryYear", queryYear);
			jo.put("queryMonth", queryMonth);
			jo.put("totalEnergy", df.format(energyModel.getTotalWaterCount()));
			jo.put("everyManEnergy", df.format(energyModel.getTotalWaterCount()/archModel.getCountMan()));
			json.add(jo);
			
		}//end for evergy arch	
		
		////System.out.println("某个类别的建筑的json"+json.toString());
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
			
			
		
		
	}
	
	
	
	/**
	 * 如果archID是非0整数，则查询某栋建筑
	 */
	private void createFusion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Architecture archModel=null;
		ArchitectureDao archDao=null;
		Area areaModel=null;
		AreaDao areaDao=null;
		WaterReportModel energyModel=null;
		WaterReportHelper wrh= null;
		DecimalFormat df =null;
		int archID=-1;
		int queryYear=0;
		int queryMonth=0;
		char archStyle='a';
		
		archDao=new ArchitectureDao();
		areaModel=new Area();
		areaDao=new AreaDao();
		wrh= new WaterReportHelperImpl();
		df= new DecimalFormat("####0.00");
		
		if(request.getParameter("queryYear")!=null)
		{
			queryYear= Integer.parseInt(request.getParameter("queryYear"));
		}
		if(Integer.parseInt(request.getParameter("queryMonth"))!=-1)
		{
			queryMonth= Integer.parseInt(request.getParameter("queryMonth"));
		}
		if(Integer.parseInt(request.getParameter("archID"))!=0)
		{
			archID=Integer.parseInt(request.getParameter("archID"));
		}
		
		energyModel=new WaterReportModel();
		try
		{
			archModel=archDao.queryByID(archID);
		} catch (NumberFormatException e1)
		{
			
			e1.printStackTrace();
		} catch (SQLException e1)
		{
			
			e1.printStackTrace();
		}	
		if(queryMonth==0)//查整年
		{
			try
			{
				energyModel=wrh.getArcCountByYear(archModel.getId(),queryYear);
			} catch (SQLException e)
			{	
				e.printStackTrace();
			}
		}
		else //查某月
		{
			try
			{
				energyModel=wrh.getArcCountByMonth(archModel.getId(),queryYear,queryMonth);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("archID", archID);//某一个建筑
		jo.put("archName", archModel.getName());
		jo.put("archCountMan", archModel.getCountMan());
		jo.put("queryYear", queryYear);
		jo.put("queryMonth", queryMonth);
		jo.put("totalEnergy", energyModel.getTotalWaterCount());
		jo.put("everyManEnergy", df.format( energyModel.getTotalWaterCount() / archModel.getCountMan()) );
		json.add(jo);
		
		////System.out.println("单独建筑的json"+json.toString());
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
			
	}
}
