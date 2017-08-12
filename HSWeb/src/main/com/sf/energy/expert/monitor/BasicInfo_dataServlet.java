package com.sf.energy.expert.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.dao.InformationDao;
import com.sf.energy.expert.model.InformationModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class BasicInfo_dataServlet extends HttpServlet {


	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int BasicInfo_archID=0;
		int BasicInfo_Year=0;
		int BasicInfo_Month=0;		
		BasicInfo_archID= Integer.parseInt(request.getParameter("BasicInfo_archID"));
		if(Integer.parseInt(request.getParameter("BasicInfo_Year"))!=0)
		{
			BasicInfo_Year= Integer.parseInt(request.getParameter("BasicInfo_Year"));
		}
		if(Integer.parseInt(request.getParameter("BasicInfo_Month"))!=0)
		{
			BasicInfo_Month= Integer.parseInt(request.getParameter("BasicInfo_Month"));
		}
			
		List<ReportModel>	ToAm = null;
		List<ReportModel>	YeAm = null;
		List<WaterReportModel>	ToWater = null;
		List<WaterReportModel>	YeWater	= null;
		InformationModel  Infom = null;
		Architecture archModel=null;
		ArchitectureDao archDao=null;
		InformationDao  infoDao=null;
		DecimalFormat df = new DecimalFormat("0.00");
		archDao = new ArchitectureDao();
		infoDao = new InformationDao();
		ElecReportHelper   erhi = new ElecReportHelperImpl();
		WaterReportHelper	wrhi = new WaterReportHelperImpl();	
		Date start = new Date();
		start.setYear(114);
		start.setMonth(6);
		start.setDate(22);
		Date YeDay = new Date();
		YeDay.setYear(114);
		YeDay.setMonth(6);
		YeDay.setDate(27);
		try {
			ToAm = erhi.getArcCountBetween(BasicInfo_archID, start, start);
			YeAm = erhi.getArcCountBetween(BasicInfo_archID, YeDay, YeDay);
			ToWater = wrhi.getArcCountBetween(BasicInfo_archID, start, start);
			YeWater = wrhi.getArcCountBetween(BasicInfo_archID, YeDay, YeDay);
			archModel=archDao.queryByID(BasicInfo_archID);	//通过countman得到人口数
			Infom = infoDao.getAreaData(BasicInfo_Year);
			////System.out.println(ToAm.get(0).getTotalEnergyCount());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		if(BasicInfo_archID == 0)//查询全校的情况
		{
			if(ToAm.size() != 0)
			{
				jo.put("ToAm", ToAm.get(0).getTotalEnergyCount());
			}else
			{
				jo.put("ToAm", 0);
			}
			if(YeAm.size() != 0)
			{
				jo.put("YeAm", YeAm.get(0).getTotalEnergyCount());
			}else
			{
				jo.put("YeAm", 0);
			}
			if(ToWater.size() != 0)
			{
				jo.put("ToWater", ToWater.get(0).getTotalWaterCount());
			}else
			{
				jo.put("ToWater", 0);
			}
			if(YeWater.size() != 0)
			{
				jo.put("YeWater", YeWater.get(0).getTotalWaterCount());
			}else
			{
				jo.put("YeWater", 0);
			}
			jo.put("archMan", archModel.getCountMan());
			jo.put("archArea", Infom.getJzarea());
			jo.put("LandArea",Infom.getTdarea() );
			jo.put("GreenArea",Infom.getLharea() );
			if(ToAm.size() != 0)
			{
				jo.put("AverAm", df.format(ToAm.get(0).getTotalEnergyCount()/archModel.getCountMan()));
				jo.put("AreaAm", df.format(ToAm.get(0).getTotalEnergyCount()/Infom.getJzarea()));
			}else
			{
				jo.put("AverAm", 0);
				jo.put("AreaAm", 0);
			}
			if(ToWater.size() != 0)
			{
				jo.put("AverWater", df.format(ToWater.get(0).getTotalWaterCount()/archModel.getCountMan()));
				jo.put("AreaWater", df.format(ToWater.get(0).getTotalWaterCount()/Infom.getJzarea()));
			}else
			{
				jo.put("AverWater", 0);
				jo.put("AreaWater", 0);
			}
		}else//查询各个建筑的情况
		{
			if(ToAm.size() != 0)
			{
				jo.put("ToAm", ToAm.get(0).getTotalEnergyCount());
			}else
			{
				jo.put("ToAm", 0);
			}
			if(YeAm.size() != 0)
			{
				jo.put("YeAm", YeAm.get(0).getTotalEnergyCount());
			}else
			{
				jo.put("YeAm", 0);
			}
			if(ToWater.size() != 0)
			{
				jo.put("ToWater", ToWater.get(0).getTotalWaterCount());
			}else
			{
				jo.put("ToWater", 0);
			}
			if(YeWater.size() != 0)
			{
				jo.put("YeWater", YeWater.get(0).getTotalWaterCount());
			}else
			{
				jo.put("YeWater", 0);
			}
			jo.put("archMan", archModel.getCountMan());
			jo.put("archArea", archModel.getArea());
			jo.put("AirConditionarea",512);
			jo.put("AmairCondi", 11);
			if(ToAm.size() != 0)
			{
				jo.put("AverAm", df.format(ToAm.get(0).getTotalEnergyCount()/archModel.getCountMan()));
				jo.put("AreaAm", df.format(ToAm.get(0).getTotalEnergyCount()/archModel.getArea()));
			}else
			{
				jo.put("AverAm", 0);
				jo.put("AreaAm", 0);
			}
			if(ToWater.size() != 0)
			{
				jo.put("AverWater", df.format(ToWater.get(0).getTotalWaterCount()/archModel.getCountMan()));
				jo.put("AreaWater", df.format(ToWater.get(0).getTotalWaterCount()/archModel.getArea()));
			}else
			{
				jo.put("AverWater", 0);
				jo.put("AreaWater", 0);
			}
		}

		json.add(jo);
		
		
		
		
	
//		Area areaModel=null;
//		AreaDao areaDao=null;
//		
//		ElecReportHelper energyDao=null;
//		List<ReportModel> energyList=null;
//		
//		
//		
//	    areaModel = new Area();
//		areaDao = new AreaDao();
//	    energyDao = new ElecReportHelperImpl();
//		energyList = new ArrayList<ReportModel>();
//		DecimalFormat df = new DecimalFormat("####.00");
//		
//		
//		try
//		{
//			
//		} catch (NumberFormatException e)
//		{
//			e.printStackTrace();
//		} catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		
//		
//		if(BasicInfo_Month==0)//查整年的数据
//		{
//			try
//			{
//				energyList=energyDao.getArcCountEveryMonth(BasicInfo_archID, BasicInfo_Year);
//				energyModel=energyDao.getArcCountByYear(BasicInfo_archID, BasicInfo_Year);
//			} catch (SQLException e)
//			{
//				
//				e.printStackTrace();
//			}
//			
//			JSONArray json = new JSONArray();
//			JSONObject jo = new JSONObject();
//			jo.put("areaName", areaModel.getName());
//			jo.put("archName", archModel.getName());
//			jo.put("archCountMan", archModel.getCountMan());
//			jo.put("archMan", archModel.getCountMan());
//			jo.put("BasicInfo_Year", BasicInfo_Year);
//			jo.put("BasicInfo_Month", BasicInfo_Month);
//			jo.put("totalEnergyCount", df.format( energyModel.getTotalEnergyCount() / archModel.getCountMan()) );
//			
////			for(int i=0;i<energyList.size();i++)
////			{
////				double everyMonthEnergyCount= energyModel.getTotalEnergyCount() / archModel.getCountMan();
////				jo.put("everyMonthEnergyCount",df.format(everyMonthEnergyCount));
////			}
//			json.add(jo);
//			response.setContentType("application/x-json");
//			PrintWriter out = response.getWriter();
//			out.println(json.toString());
//			out.flush();
//			out.close();
//		}
//		else//查某月
//		{
//			//建筑总能耗
//			try
//			{
//				energyModel=energyDao.getArcCountDetailByMonth(BasicInfo_archID, BasicInfo_Year, BasicInfo_Month);
//			} catch (SQLException e1)
//			{	
//				e1.printStackTrace();
//			}
//			//单位面积能耗
//			double unitEnergy=0.00;
//			//System.out.println("TotalEnery :"+energyModel.getTotalEnergyCount());
//			//System.out.println("Man of arch :"+archModel.getCountMan());
//			unitEnergy= energyModel.getTotalEnergyCount() / archModel.getCountMan();
//			//System.out.println("unitEnergy :"+df.format(unitEnergy));
//			JSONArray json = new JSONArray();
//			JSONObject jo = new JSONObject();
//			jo.put("unitEnergyOfMonth",df.format( unitEnergy));
//			jo.put("areaName", areaModel.getName());
//			jo.put("archName", archModel.getName());
//			jo.put("archCountMan", archModel.getCountMan());
//			jo.put("archMan", archModel.getCountMan());
//			jo.put("BasicInfo_Year", BasicInfo_Year);
//			jo.put("BasicInfo_Month", BasicInfo_Month);
//			json.add(jo);
			//System.out.println(json);
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();		
	}

	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

	

}
