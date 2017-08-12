package com.sf.energy.expert.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.sf.energy.expert.dao.DictionaryValueImpl;
import com.sf.energy.expert.dao.EnergyMonitorDao;
import com.sf.energy.expert.dao.EstimateDao;
import com.sf.energy.expert.dao.InformationDao;
import com.sf.energy.expert.dao.ManualMonthDao;
import com.sf.energy.expert.dao.QueryTemperatureHelper;
import com.sf.energy.expert.dao.QueryTemperatureImpl;
import com.sf.energy.expert.model.BasicInfoArchCountModel;
import com.sf.energy.expert.model.BasicInfo_saveData;
import com.sf.energy.expert.model.EstimateModel;
import com.sf.energy.expert.model.InformationModel;
import com.sf.energy.expert.model.ManualEnergyModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class EnergyMonitorServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			findmethod(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private void findmethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		String method = request.getParameter("method");
		
		if ("getBasicInfo_archFusionchart".equalsIgnoreCase(method))
		{
			getBasicInfo_archFusionchart(request, response);
		}
		
		else if ("getReportAngulaFusionchart".equalsIgnoreCase(method))
		{
			getReportAngulaFusionchart(request, response);
		}
		
		else if ("getDetailInputData".equalsIgnoreCase(method))
		{
			getDetailInputData(request, response);
		}
		
		else if ("getSchoolInfoData".equalsIgnoreCase(method))
		{
			getSchoolInfoData(request, response);
		}
		else{
		
		/*****************************************/
		
		String StyleID = -1+"";
		int ArcID = -1;
		int	energyID = -1;
		String	amID = -1+"";
		String	waterID = -1+"";
		
		
		StyleID = request.getParameter("styleID");
		if (request.getParameter("archID")!=null&&!"".equals(request.getParameter("archID")))
		{
			ArcID = Integer.parseInt(request.getParameter("archID"));
		}
		if (request.getParameter("energyID")!=null&&!"".equals(request.getParameter("energyID")))
		{
			energyID = Integer.parseInt(request.getParameter("energyID"));

		}
//		ArcID = Integer.parseInt(request.getParameter("archID"));
//		energyID = Integer.parseInt(request.getParameter("energyID"));
		amID = request.getParameter("amID");
		waterID = request.getParameter("waterID");
		
		
		if((0+"").equals(StyleID)&& energyID == 0 )		//选了全校的情形
		{
			//全校数据
			if ("getEnergyMonitorDataBySchool".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataBySchool(request, response);
			}
		}
		if((0+"").equals(StyleID)&& energyID == 1 && (0+"").equals(amID) )		// 全校电耗
		{
			//全校电耗数据
			if ("getEnergyMonitorDataBySchool".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataBySchool(request, response);
			}
		}
		if((0+"").equals(StyleID)&& energyID == 2 && (0+"").equals(waterID) )		//全校水耗
		{
			//全校水耗数据
			if ("getEnergyMonitorDataBySchool".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataBySchool(request, response);
			}
		}
		if((0+"").equals(StyleID)  && energyID == 1 && !(0+"").equals(amID))		//选了全校电耗子项
		{
			//System.out.println("电子");
			if ("getEnergyMonitorDataByArcAmZX".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArcAmZX(request, response);
			}
				
		}
		
		if((0+"").equals(StyleID) && energyID == 2 && !(0+"").equals(waterID))		//选了全校水耗子项
		{
			//System.out.println("水子");
			if ("getEnergyMonitorDataByArcWaterZX".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArcAmZX(request, response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID ==0)		//选了分类建筑的情形
		{
			//选了分类建筑数据
			if ("getEnergyMonitorDataByArcStyle".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArcStyle(request, response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 0)		//选了建筑的情形
		{
			//选了建筑数据
			if ("getEnergyMonitorDataByArc".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArc(request, response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 1 && (0+"").equals(amID) )		//选了电耗选项
		{
			if ("getEnergyMonitorDataByArcAm".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArc(request, response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 2 && (0+"").equals(waterID) )		//选了水耗选项
		{
			if ("getEnergyMonitorDataByArcWater".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArc(request, response);
			}
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 1 && !(0+"").equals(amID))		//选了电耗子项
		{
			//System.out.println("电子");
			if ("getEnergyMonitorDataByArcAmZX".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArcAmZX(request, response);
			}
				
		}
		
		if(!(0+"").equals(StyleID) && ArcID != 0 && energyID == 2 && !(0+"").equals(waterID))		//选了水耗子项
		{
			//System.out.println("水子");
			if ("getEnergyMonitorDataByArcWaterZX".equalsIgnoreCase(method))
			{
				getEnergyMonitorDataByArcAmZX(request, response);
			}
		}
		
		}	
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	
	//获取学校简介信息
	private	void getSchoolInfoData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		String Info = "";
		
		EnergyMonitorDao energyMDao=new EnergyMonitorDao();
		
		Info = energyMDao.getSchoolIntroduce();
		
		Gson gson = new Gson();
		
		String data = gson.toJson(Info);
		
		out.println(data);
		out.flush();
		out.close();
		
	}
	
	
	//获取各个要填充的数据
	private void  getDetailInputData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		String styleID = "";
		int archID = 0;
		int year = 0;
		int month = -1;
		
		if(request.getParameter("styleID") != null)
		{
			styleID = request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("year")!= null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month")!= null)
		{
			month = Integer.parseInt(request.getParameter("month"));
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		ElecReportHelperImpl erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl wrhi = new WaterReportHelperImpl();
		InformationDao	infoDao = new InformationDao();	
		
		//获取当前时间
		Calendar getData_cal = Calendar.getInstance(); 
		int Now_year = getData_cal.get(Calendar.YEAR);
		int Now_month = getData_cal.get(Calendar.MONTH)+1;
		int Now_day = getData_cal.get(Calendar.DATE);
		Date toDay = getData_cal.getTime();
	
		//获取昨天时间
		getData_cal.add(Calendar.DAY_OF_MONTH, -1);
		int Last_year = getData_cal.get(Calendar.YEAR);
		int Last_month = getData_cal.get(Calendar.MONTH)+1;
		int Last_day = getData_cal.get(Calendar.DATE);
		Date YeDay = getData_cal.getTime();
		
		
		if((0+"").equals(styleID))	//全校
		{
			//System.out.println("全校");
			List<ReportModel>	ToAm = null;
			List<ReportModel>	YeAm = null;
			List<WaterReportModel>	ToWater = null;
			List<WaterReportModel>	YeWater	= null;
			InformationModel  Infomodel = null;
			ReportModel		rm = null;		//全校用年电信息
			WaterReportModel	wrm = null;		//全校年用水信息
			ReportModel		lastrm = null;		//全校去年电信息
			WaterReportModel	lastwrm = null;		//全校去年用水信息
			ReportModel		month_rm = null;		//全校月用电信息
			WaterReportModel	month_wrm = null;		//全校月用水信息
			float	amMoney = 0;						//昨日电费
			float	waterMoney = 0;						//昨日水费
			float YearAm = 0; //今年用电
			float YearWater = 0;//今年用水
			float LastYearAm = 0; //去年用电
			float LastYearWater = 0;//去年用水
			
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			
			EnergyMonitorDao energyMDao=new EnergyMonitorDao();
			
			ToAm = erhi.getSchoolCountBetween(toDay, toDay);
			YeAm = erhi.getSchoolCountBetween(YeDay, YeDay);
			rm = erhi.getAllSchoolEnergyByYear(year);
			lastrm =  erhi.getAllSchoolEnergyByYear(year-1);
			ToWater = wrhi.getSchoolCountBetween(toDay, toDay);
			YeWater = wrhi.getSchoolCountBetween(YeDay, YeDay);
			wrm = wrhi.getAllSchoolWaterByYear(year);
			lastwrm = wrhi.getAllSchoolWaterByYear(year-1);
			Infomodel = infoDao.getAreaData(year);
			if(rm!=null){
				jo.put("YearAm", rm.getTotalEnergyCount());
			}else{
				jo.put("YearAm", 0);
			}
			if(lastrm!=null){
				jo.put("LastYearAm", lastrm.getTotalEnergyCount());
			}else{
				jo.put("LastYearAm", 0);
			}
			if(wrm!=null){
				jo.put("YearWm", wrm.getTotalWaterCount());
			}else{
				jo.put("YearWm", 0);
			}
			if(lastwrm!=null){
				jo.put("LastYearWm", lastwrm.getTotalWaterCount());
			}else{
				jo.put("LastYearWm", 0);
			}
			if(ToAm.size() != 0)	//今日用电
			{
				float totalAm=0;
				for(int i=0;i<ToAm.size();i++){
					totalAm+=ToAm.get(i).getTotalEnergyCount();
				}
				jo.put("ToAm", totalAm);
			}else
			{
				jo.put("ToAm", 0);
			}
			if(YeAm.size() != 0)	//昨日用电
			{
				float totalAm=0;
				for(int i=0;i<YeAm.size();i++){
					totalAm+=YeAm.get(i).getTotalEnergyCount();
				}
				jo.put("YeAm", totalAm);
			}else
			{
				jo.put("YeAm", 0);
			}
			if(ToWater.size() != 0)//今日用水
			{
				float totalAm=0;
				for(int i=0;i<ToWater.size();i++){
					totalAm+=ToWater.get(i).getTotalWaterCount();
				}
				jo.put("ToWater", totalAm);
			}else
			{
				jo.put("ToWater", 0);
			}
			if(YeWater.size() != 0)//昨日用水
			{
				float totalAm=0;
				for(int i=0;i<YeWater.size();i++){
					totalAm+=YeWater.get(i).getTotalWaterCount();
				}
				jo.put("YeWater", totalAm);
			}else
			{
				jo.put("YeWater", 0);
			}
			
			amMoney = energyMDao.getTotalAmMoneyBySchool(Last_year, Last_month, Last_day);
			waterMoney = energyMDao.getTotalWaterMoneyBySchool(Last_year, Last_month, Last_day);
			
			jo.put("TotalMoney", amMoney+waterMoney);		//能耗总金额
			
			jo.put("ManCount", Infomodel.getManCount());
			jo.put("archArea", Infomodel.getJzarea());
			jo.put("LandArea",Infomodel.getTdarea() );
			jo.put("GreenArea",Infomodel.getLharea() );
			
			if(month == 0)	//全年
			{
				//System.out.println("全年");
				if(rm != null)
				{
					jo.put("AverAm", df.format(rm.getTotalEnergyCount()/Infomodel.getManCount()));
					jo.put("AreaAm", df.format(rm.getTotalEnergyCount()/Infomodel.getJzarea()));
				}
				else
				{
					jo.put("AverAm", 0);
					jo.put("AreaAm", 0);
				}
				if(wrm != null)
				{
					jo.put("AverWater", df.format(wrm.getTotalWaterCount()/Infomodel.getManCount()));
					jo.put("AreaWater", df.format(wrm.getTotalWaterCount()/Infomodel.getJzarea()));
				}
				else
				{
					jo.put("AverWater", 0);
					jo.put("AreaWater", 0);
				}
			}
			else			//指定月份
			{
				//System.out.println("月份");
				month_rm = erhi.getAllSchoolEnergyByMonth(year, month);
				month_wrm = wrhi.getAllSchoolWaterByMonth(year, month);
				if(rm != null)
				{
					jo.put("AverAm", df.format(month_rm.getTotalEnergyCount()/Infomodel.getManCount()));
					jo.put("AreaAm", df.format(month_rm.getTotalEnergyCount()/Infomodel.getJzarea()));
				}
				else
				{
					jo.put("AverAm", 0);
					jo.put("AreaAm", 0);
				}
				if(wrm != null)
				{
					jo.put("AverWater", df.format(wrm.getTotalWaterCount()/Infomodel.getManCount()));
					jo.put("AreaWater", df.format(wrm.getTotalWaterCount()/Infomodel.getJzarea()));
				}
				else
				{
					jo.put("AverWater", 0);
					jo.put("AreaWater", 0);
				}
			}
			json.add(jo);
			//System.out.println(json);
			out.println(json.toString());
		}
		else if(!(0+"").equals(styleID) && archID == 0)	//分类
		{
			//System.out.println("分类");
			
			ManualEnergyModel	manualModel = null;		//电能model
			ManualEnergyModel	lastmanualModel = null;
			WaterReportModel 	energyModel=null;		//水能model
			Architecture archModel=null;
			float totalByStyle=0;
			float	airArea = 0;			//空调面积
			float	ariAmYear = 0;			//年空调用电
			float	ariAmMonth = 0;			//月空调用电
			float   ToAm = 0;				//今日用电	
			float   ToWater = 0;
			float   YeAm = 0;
			float   YeWater = 0;
			float   YeAmMoney = 0;
			float   YeWaterMoney = 0;
			float	TotalMoney = 0;
			
			float YearAm = 0; //今年用电
			float YearWater = 0;//今年用水
			float LastYearAm = 0; //去年用电
			float LastYearWater = 0;//去年用水
			
			
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			ManualMonthDao manualDao=new ManualMonthDao();
			ArchitectureDao archDao=new ArchitectureDao();
			EnergyMonitorDao energyMDao=new EnergyMonitorDao();
			ArrayList<Architecture> archList=new ArrayList<Architecture>();
			WaterReportHelper wrh = new WaterReportHelperImpl();
			
			archList=archDao.queryArchByStyle(styleID.charAt(0));		//获取该类建筑下面所有建筑
			
			for(int i=0;i<archList.size();i++)
			{
				energyModel=new WaterReportModel();
				archModel=archList.get(i);
				if(month == 0)		//全年
				{
					energyModel = wrh.getArcCountByYear(archModel.getId(), year);
					totalByStyle +=energyModel.getTotalWaterCount();
				}
				else				//某月
				{
					energyModel = wrh.getArcCountByMonth(archModel.getId(), year, month);
					totalByStyle +=energyModel.getTotalWaterCount();
				}
				energyModel = wrh.getArcCountByYear(archModel.getId(), year);
				YearWater+=energyModel.getTotalWaterCount();
				energyModel = wrh.getArcCountByYear(archModel.getId(), year-1);
				LastYearWater+=energyModel.getTotalWaterCount();
			}
			jo.put("YearWater", YearWater);
			jo.put("LastYearWater", LastYearWater);
			airArea = energyMDao.getAirConditonAreaByStyle(styleID);
			jo.put("AirConditionarea", airArea);
			
			ToAm = energyMDao.getTodayAmByarchStyleNotatal(styleID, Now_year, Now_month, Now_day);
			ToWater = energyMDao.getTodayWaterByarchStyleNotatal(styleID, Now_year, Now_month, Now_day);
			YeAm = energyMDao.getTodayAmByarchStyleNotatal(styleID, Last_year, Last_month, Last_day);
			YeWater = energyMDao.getTodayWaterByarchStyleNotatal(styleID, Last_year, Last_month, Last_day);
			YeAmMoney = energyMDao.getTotalAmMoneyByarchStyle(styleID, Last_year, Last_month, Last_day);
			YeWaterMoney = energyMDao.getTotalWaterMoneyByarchStyle(styleID, Last_year, Last_month, Last_day);
			TotalMoney = YeAmMoney+YeWaterMoney;
			
			
			jo.put("ToAm", ToAm);
			jo.put("ToWater", ToWater);
			jo.put("YeAm", YeAm);
			jo.put("YeWater", YeWater);
			jo.put("TotalMoney", TotalMoney);
		
			
			if(month == 0)		//全年
			{
				manualModel = manualDao.queryArchStyleEnergyAllYear(styleID, year-1);
				jo.put("LastYearAm", manualModel.getEnergyValue());
				manualModel = manualDao.queryArchStyleEnergyAllYear(styleID, year);
				jo.put("YearAm", manualModel.getEnergyValue());
				ariAmYear = energyMDao.getAirConditonAmByArchStyleForYear(styleID, year);
				
				jo.put("AmairCondi", ariAmYear);
				jo.put("archArea", manualModel.getArchArea());
				jo.put("ManCount", manualModel.getArchCountMan());
				jo.put("AverAm", manualModel.getAverageEnergy());
				jo.put("AreaAm", manualModel.getUnitEnergy());
				jo.put("AverWater", df.format(totalByStyle/manualModel.getArchCountMan()));
				jo.put("AreaWater", df.format(totalByStyle/manualModel.getArchArea()));
			}
			else				//某月
			{
				manualModel = manualDao.queryArchStyleEnergyAllYear(styleID, year-1);
				jo.put("LastYearAm", manualModel.getEnergyValue());
				manualModel = manualDao.queryArchStyleEnergyAllYear(styleID, year);
				jo.put("YearAm", manualModel.getEnergyValue());
				
				manualModel = manualDao.queryArchStyleEnergyMonth(styleID, year, month);
				ariAmMonth = energyMDao.getAirConditonAmByArchStyleForMonth(styleID, year, month);
				
				jo.put("AmairCondi", ariAmMonth);
				jo.put("archArea", manualModel.getArchArea());
				jo.put("ManCount", manualModel.getArchCountMan());
				jo.put("AverAm", manualModel.getAverageEnergy());
				jo.put("AreaAm", manualModel.getUnitEnergy());
				jo.put("AverWater", df.format(totalByStyle/manualModel.getArchCountMan()));
				jo.put("AreaWater", df.format(totalByStyle/manualModel.getArchArea()));
			}
			
			json.add(jo);
			//System.out.println("分类数据"+json);
			out.println(json.toString());
			
		}
		else if(!(0+"").equals(styleID) && archID != 0)	//建筑
		{	
			//System.out.println("建筑");
			
			ManualEnergyModel	manualModel = null;
			WaterReportModel 	energyModel=null;		//水能model
			float	airArea = 0;			//空调面积
			float	ariAmYear = 0;			//年空调用电
			float	ariAmMonth = 0;			//月空调用电
			float   ToAm = 0;				//今日用电	
			float   ToWater = 0;
			float   YeAm = 0;
			float   YeWater = 0;
			float   YeAmMoney = 0;
			float   YeWaterMoney = 0;
			float	TotalMoney = 0;
			
			ManualMonthDao manualDao=new ManualMonthDao();
			WaterReportHelper wrh = new WaterReportHelperImpl();
			EnergyMonitorDao energyMDao=new EnergyMonitorDao();
			
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			
			
			airArea = energyMDao.getAirConditonAreaByArch(archID);
			jo.put("AirConditionarea", airArea);
			
			ToAm = energyMDao.getTodayAmByarchNotatal(archID, Now_year, Now_month, Now_day);
			ToWater = energyMDao.getTodayWaterByarchNotatal(archID, Now_year, Now_month, Now_day);
			YeAm = energyMDao.getTodayAmByarchNotatal(archID, Last_year, Last_month, Last_day);
			YeWater = energyMDao.getTodayWaterByarchNotatal(archID, Last_year, Last_month, Last_day);
			YeAmMoney = energyMDao.getTotalAmMoneyByarch(archID, Last_year, Last_month, Last_day);
			YeWaterMoney = energyMDao.getTotalWaterMoneyByarch(archID, Last_year, Last_month, Last_day);
			TotalMoney = YeAmMoney+YeWaterMoney;
			
			jo.put("ToAm", ToAm);
			jo.put("ToWater", ToWater);
			jo.put("YeAm", YeAm);
			jo.put("YeWater", YeWater);
			jo.put("TotalMoney", TotalMoney);
			manualModel = manualDao.queryArchEnergyAllYear(archID, year-1);
			jo.put("LastYearAm", manualModel.getEnergyValue());
			energyModel = wrh.getArcCountByYear(archID, year-1);
			jo.put("LastYearWm", energyModel.getTotalWaterCount());
			manualModel = manualDao.queryArchEnergyAllYear(archID, year);
			jo.put("YearAm", manualModel.getEnergyValue());
			energyModel = wrh.getArcCountByYear(archID, year);
			jo.put("YearWm", energyModel.getTotalWaterCount());
			
			if(month == 0)		//全年
			{
				//manualModel = manualDao.queryArchEnergyAllYear(archID, year);
				//energyModel = wrh.getArcCountByYear(archID, year);
				ariAmYear = energyMDao.getAirConditonAmByArchForYear(archID, year);
				
				jo.put("AmairCondi", ariAmYear);
				jo.put("archArea", manualModel.getArchArea());
				jo.put("ManCount", manualModel.getArchCountMan());
				jo.put("AverAm", manualModel.getAverageEnergy());
				jo.put("AreaAm", manualModel.getUnitEnergy());
				jo.put("AverWater", df.format(energyModel.getTotalWaterCount()/manualModel.getArchCountMan()));
				jo.put("AreaWater", df.format(energyModel.getTotalWaterCount()/manualModel.getArchArea()));
			}
			else				//某月
			{
				manualModel = manualDao.queryArchEnergyMonth(archID, year, month);
				energyModel = wrh.getArcCountByMonth(archID, year, month);
				ariAmMonth = energyMDao.getAirConditonAmByArchForMonth(archID, year, month);
				
				jo.put("AmairCondi", ariAmMonth);
				jo.put("archArea", manualModel.getArchArea());
				jo.put("ManCount", manualModel.getArchCountMan());
				jo.put("AverAm", manualModel.getAverageEnergy());
				jo.put("AreaAm", manualModel.getUnitEnergy());
				jo.put("AverWater", df.format(energyModel.getTotalWaterCount()/manualModel.getArchCountMan()));
				jo.put("AreaWater", df.format(energyModel.getTotalWaterCount()/manualModel.getArchArea()));
			}
			
			json.add(jo);
			//System.out.println("建筑数据"+json);
			out.println(json.toString());
		}
			
		out.flush();
		out.close();
	}
	
	//获取分类建筑个数fusionchart图
	private void getBasicInfo_archFusionchart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		List<BasicInfoArchCountModel>	list = null;
		String styleID = 0+"";
		int   archID = -1;
		BasicInfoArchCountModel bacmodel = null;
		
		DictionaryValueImpl  dicValue = new DictionaryValueImpl();
		
		if(request.getParameter("styleID") != null)
		{
			styleID = request.getParameter("styleID");
		}
		if(request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		
		if(!(0+"").equals(styleID) && archID == 0)
		{
			bacmodel = dicValue.getStyelArchInfoByStyle(styleID);
		}
		
		list = dicValue.getAllStyelArchInfo();
		
		Gson gson = new Gson();
		
		String data = gson.toJson(new Object[]{list,bacmodel});
		////System.out.println("分类信息"+data);
		out.println(data);
		out.flush();
		out.close();
	}
	
	
	//获取仪表盘评分报告
	private	void getReportAngulaFusionchart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		String styleID = "";
		int archID = 0;
		int year = 0;
		float count = 0;	//评分
		EstimateModel estmodel = null;	
		
		
		if(request.getParameter("styleID") != null)
		{
			styleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("year")!= null)
		{
			year= Integer.parseInt(request.getParameter("year"));
		}
		
		EstimateDao  estDao = new EstimateDao();
		
		
		if((0+"").equals(styleID))
		{
			////System.out.println("全校");
			estmodel = estDao.getTargetInfo(year, 0, styleID);
		}
		else if(!(0+"").equals(styleID) && archID == 0)
		{
			estmodel = estDao.getTargetInfo(year, 3, styleID);
			////System.out.println("分类");
		}
		else if(!(0+"").equals(styleID) && archID != 0)
		{
			estmodel = estDao.getTargetInfo(year, 2, archID+"");
			////System.out.println("建筑");
		}
		
		if(estmodel != null)	//数据库没有值则把分数设为0
		{
			count = estmodel.getEstimate_Count();
		}
		else
		{
			count = 0;
		}
		
		Gson gson = new Gson();
	
		String data = gson.toJson(count);
		//System.out.println("分数"+data);
		out.println(data);
		out.flush();
		out.close();
	}

	
	private void getMonitorInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		
		String  StyleID = "-1";
		int EnergyMonitor_archID=-1;
		int EnergyMonitor_Year=0;
		int EnergyMonitor_Month=0;
		List<ReportModel> EnergyMonitor_Am;
		List<WaterReportModel> EnergyMonitor_Water;
		
		int EnergyMonitor_Temp = 0;
		
		
		if(request.getParameter("styleID") != null && request.getParameter("styleID") != "")
		{
			StyleID = request.getParameter("styleID");
		}
	
		if(request.getParameter("EnergyMonitor_archID")!=null && request.getParameter("EnergyMonitor_archID")!=""){
			EnergyMonitor_archID=Integer.parseInt(request.getParameter("EnergyMonitor_archID"));
		}
		
		//System.out.println("styleID"+StyleID);
		//System.out.println("EnergyMonitor_archID"+EnergyMonitor_archID);
		if(request.getParameter("EnergyMonitor_Year")!=null){
			EnergyMonitor_Year=Integer.parseInt(request.getParameter("EnergyMonitor_Year"));
		}
		if(request.getParameter("EnergyMonitor_Month")!=null){
			EnergyMonitor_Month=Integer.parseInt(request.getParameter("EnergyMonitor_Month"));
		}
		try 
		{
			QueryTemperatureHelper rhi_Temp = new QueryTemperatureImpl();
			EnergyMonitor_Temp  = rhi_Temp.getTemperature(EnergyMonitor_Year, EnergyMonitor_Month);
			Gson gson_EnergyMonitor = new Gson();
			
			if( !"-1".equals(StyleID) && EnergyMonitor_archID != -1)	//选了类型和建筑的情形
			{
				ElecReportHelperImpl  rhi_EnergyMonitor_Am = new ElecReportHelperImpl();
				//EnergyMonitor_Am = rhi_EnergyMonitor_Am.getArcCountEveryDay(EnergyMonitor_archID, EnergyMonitor_Year, EnergyMonitor_Month);
				WaterReportHelperImpl  rhi_EnergyMonitor_Water = new WaterReportHelperImpl();
				//EnergyMonitor_Water = rhi_EnergyMonitor_Water.getArcCountEveryDay(EnergyMonitor_archID, EnergyMonitor_Year, EnergyMonitor_Month);
				
				//String EnergyMonitordata = gson_EnergyMonitor.toJson(new Object[]{EnergyMonitor_Am,EnergyMonitor_Water,EnergyMonitor_Temp});
				////System.out.println(EnergyMonitordata);
				//out.println(EnergyMonitordata);
			}else if(!"-1".equals(StyleID) &&  EnergyMonitor_archID ==-1)			//选了类型和所有建筑的情形
			{
				ElecReportHelperImpl  rhi_EnergyMonitor_Am = new ElecReportHelperImpl();
				EnergyMonitor_Am = rhi_EnergyMonitor_Am.getFenleiArcCountEveryDay(StyleID.charAt(0), EnergyMonitor_Year, EnergyMonitor_Month);
				WaterReportHelperImpl  rhi_EnergyMonitor_Water = new WaterReportHelperImpl();
				EnergyMonitor_Water = rhi_EnergyMonitor_Water.getFenleiArcCountEveryDay(StyleID.charAt(0), EnergyMonitor_Year, EnergyMonitor_Month);
				
				String EnergyMonitordata = gson_EnergyMonitor.toJson(new Object[]
						{EnergyMonitor_Am,EnergyMonitor_Water,EnergyMonitor_Temp});
				//System.out.println(EnergyMonitordata);
				out.println(EnergyMonitordata);
			}else if("-1".equals(StyleID) &&  EnergyMonitor_archID ==-1)
			{
				//System.out.println("全校");
				ElecReportHelperImpl  rhi_EnergyMonitor_Am = new ElecReportHelperImpl();
				EnergyMonitor_Am = rhi_EnergyMonitor_Am.getAllFenleiArcCountEveryDay(EnergyMonitor_Year, EnergyMonitor_Month);
				WaterReportHelperImpl  rhi_EnergyMonitor_Water = new WaterReportHelperImpl();
				EnergyMonitor_Water = rhi_EnergyMonitor_Water.getAllFenleiArcCountEveryDay(EnergyMonitor_Year, EnergyMonitor_Month);
				
				String EnergyMonitordata = gson_EnergyMonitor.toJson(new Object[]
						{EnergyMonitor_Am,EnergyMonitor_Water,EnergyMonitor_Temp});
				//System.out.println(EnergyMonitordata);
				out.println(EnergyMonitordata);
			}
			
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
	
	public void getEnergyMonitorDataBySchool(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		String styleID = "";
		int archID = 0;
		int year = 0;
		int month = 0;
		
		if(request.getParameter("styleID") != null)
		{
			styleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			month= Integer.parseInt(request.getParameter("Month"));
		}
		
		
		List<ReportModel>  list_Am = null;
		List<WaterReportModel>	list_Water = null;
		List<AmMeterMataData>	list_temp = null;
		
		ElecReportHelperImpl  erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl  wrhi = new WaterReportHelperImpl();
		EnergyMonitorDao  emDao = new EnergyMonitorDao();
		
		list_Am = erhi.getAllAreaCountEveryDay(year, month);
		list_Water = wrhi.getAllAreaCountEveryDay(year, month);
		list_temp = emDao.getTemperatureDayValue(year, month);
		
		Gson  gson = new Gson();
		String 	data = gson.toJson(new Object[]{list_Am,list_Water,list_temp});
		//System.out.println("能耗全校"+data);
		
		out.println(data);
		out.flush();
		out.close();
		
	}
	
	public void getEnergyMonitorDataByArcStyle(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		String styleID = "";
		int archID = 0;
		int year = 0;
		int month = 0;
		
		if(request.getParameter("styleID") != null)
		{
			styleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			month= Integer.parseInt(request.getParameter("Month"));
		}
		
		
		List<ReportModel>  list_Am = null;
		List<WaterReportModel>	list_Water = null;
		List<AmMeterMataData>	list_temp = null;
		
		ElecReportHelperImpl  erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl  wrhi = new WaterReportHelperImpl();
		EnergyMonitorDao  emDao = new EnergyMonitorDao();
		
		list_Am = erhi.getArcStyleCountEveryDay(styleID,year, month);
		list_Water = wrhi.getArcStyleCountEveryDay(styleID,year, month);
		list_temp = emDao.getTemperatureDayValue(year, month);
		
		Gson  gson = new Gson();
		String 	data = gson.toJson(new Object[]{list_Am,list_Water,list_temp});
		//System.out.println("能耗建筑分类"+data);
		
		out.println(data);
		out.flush();
		out.close();	
	}
	
	public void getEnergyMonitorDataByArc(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		String styleID = "";
		int archID = 0;
		int year = 0;
		int month = 0;
		
		if(request.getParameter("styleID") != null)
		{
			styleID= request.getParameter("styleID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			month= Integer.parseInt(request.getParameter("Month"));
		}
		
		
		List<ReportModel>  list_Am = null;
		List<WaterReportModel>	list_Water = null;
		List<AmMeterMataData>	list_temp = null;
		
		ElecReportHelperImpl  erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl  wrhi = new WaterReportHelperImpl();
		EnergyMonitorDao  emDao = new EnergyMonitorDao();
		
		list_Am = erhi.getArcCountEveryDay(archID,year, month);
		list_Water = wrhi.getArcCountEveryDay(archID,year, month);
		list_temp = emDao.getTemperatureDayValue(year, month);
		
		Gson  gson = new Gson();
		String 	data = gson.toJson(new Object[]{list_Am,list_Water,list_temp});
		//System.out.println("能耗建筑"+data);
		
		out.println(data);
		out.flush();
		out.close();	
	}
	
	
	public void getEnergyMonitorDataByArcAmZX(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		PrintWriter out = response.getWriter();
		String WaterID = "";
		String amID = "";
		int archID = 0;
		int year = 0;
		int month = 0;
		
		if(request.getParameter("amID") != null)
		{
			amID= request.getParameter("amID");
		}
		if(request.getParameter("waterID") != null)
		{
			WaterID= request.getParameter("waterID");
		}
		if(request.getParameter("archID")!= null)
		{
			archID= Integer.parseInt(request.getParameter("archID"));
		}
		if(request.getParameter("Year")!= null)
		{
			year= Integer.parseInt(request.getParameter("Year"));
		}
		if(request.getParameter("Month")!= null)
		{
			month= Integer.parseInt(request.getParameter("Month"));
		}
		
		
		List<ReportModel>  list_Am = null;
		List<WaterReportModel>	list_Water = null;
		List<AmMeterMataData>	list_temp = null;
		
		ElecReportHelperImpl  erhi = new ElecReportHelperImpl();
		WaterReportHelperImpl  wrhi = new WaterReportHelperImpl();
		EnergyMonitorDao  emDao = new EnergyMonitorDao();
		
		list_Am = erhi.getArcAmByFenxiangCountEveryDay(amID, archID, year, month);
		list_Water = wrhi.getArcWaterByFenxiangCountEveryDay(WaterID, archID, year, month);
		list_temp = emDao.getTemperatureDayValue(year, month);
		
		Gson  gson = new Gson();
		String 	data = gson.toJson(new Object[]{list_Am,list_Water,list_temp});
		//System.out.println("能耗子项"+data);
		
		out.println(data);
		out.flush();
		out.close();	
	}
	public static void main(String[] args)
	{
		Calendar getData_cal = Calendar.getInstance(); 
		getData_cal.add(Calendar.DAY_OF_MONTH, -17);
		System.out.println(getData_cal.getTime());
		Date toDay = getData_cal.getTime();	
		getData_cal.add(Calendar.DAY_OF_MONTH, -1);
		Date YeDay = getData_cal.getTime();	
		System.out.println(toDay);
		System.out.println(YeDay);
	}

}
