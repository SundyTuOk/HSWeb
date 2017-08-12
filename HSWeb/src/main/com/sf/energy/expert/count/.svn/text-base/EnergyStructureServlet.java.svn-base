package com.sf.energy.expert.count;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.EnergyDataTabImpl;
import com.sf.energy.expert.dao.ManualMonthDao;
import com.sf.energy.expert.model.EnergyDataTabData;
import com.sf.energy.expert.model.ManualEnergyModel;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.report.dao.EnReportDao;
import com.sf.energy.report.model.EnReportModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class EnergyStructureServlet extends HttpServlet
{

	

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if ("createFusionAllSchool".equalsIgnoreCase(request
				.getParameter("method")))
		{
			try
			{
				createFusionAllSchool(request, response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("createFusionStyle"
				.equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				createFusionStyle(request, response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("createFusion".equalsIgnoreCase(request.getParameter("method")))
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
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void createFusionAllSchool(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException
	{
		Area areaModel = new Area();
		AreaDao areaDao = new AreaDao();
		ReportModel energyModel = new ReportModel();
		ElecReportHelper energyDao = new ElecReportHelperImpl();

		int queryYear = 0;
		int archID = 0;
		String archStyle = "";
		float energyValue = 0;// 电
		float yehuashiyouValue = 0;// 液化石油气
		float meiyouValue = 0;// 煤气
		float qiyouValue = 0;// 汽油
		float meiValue = 0;// 煤
		float chaiyouValue = 0;// 柴油
		float otherValue = 0;// 其他
		float rengongmeiqiValue = 0;// 人工煤气

		EnReportModel reportModel = new EnReportModel();
		//EnergyDataTabData manualModel = new EnergyDataTabData();
		Architecture archModel = new Architecture();
		DecimalFormat df = new DecimalFormat("####0.00");
		DecimalFormat df1 = new DecimalFormat("####0.00%");
		ArchitectureDao archDao = new ArchitectureDao();
		EnReportDao reportDao = new EnReportDao();
		EnergyDataTabImpl manualDao = new EnergyDataTabImpl();
		//ManualMonthDao manualMonthDao = new ManualMonthDao();
		ArrayList<EnReportModel> energyList = new ArrayList<EnReportModel>();
		//Map<String, String> manualMap = new HashMap<String, String>();
		
		//int queryYear = 0;

		//String archStyle = "";
		DictionaryValueDao dvd = new DictionaryValueDao();
		ArrayList<DictionaryValueModel> archStyleList = new ArrayList<DictionaryValueModel>();
		ManualEnergyModel manualModel = new ManualEnergyModel();
		ManualMonthDao manualMonthDao = new ManualMonthDao();
		Map<String, ManualEnergyModel> manualMap = new HashMap<String, ManualEnergyModel>();

		if (Integer.parseInt(request.getParameter("queryYear")) != 0)
		{
			queryYear = Integer.parseInt(request.getParameter("queryYear"));
		}

		try
		{
			archStyleList = dvd.listDictionaryValueByName("建筑分类");
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		for (int i = 0; i < archStyleList.size(); i++)
		{
			archStyle = archStyleList.get(i).getDictionaryValueNum();

			try
			{
				manualModel = manualMonthDao.queryArchStyleEnergyAllYear(
						archStyle, queryYear);
				energyValue += manualModel.getEnergyValue();
				meiValue += manualModel.getMeiValue();
				qiyouValue += manualModel.getQiyouValue();
				chaiyouValue += manualModel.getChaiyouValue();
				meiyouValue += manualModel.getMeiyouValue();
			} catch (SQLException e)
			{

				e.printStackTrace();
			}

		}
		float totalValue = energyValue + meiValue + qiyouValue + chaiyouValue
				+ meiyouValue;

		Map<String, EnergyDataTabData> Map1 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map2 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map3 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map4 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map5 = new HashMap<String, EnergyDataTabData>();
		EnergyDataTabData model = null;

		model = new EnergyDataTabData();
		model.setValue(energyValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("电能"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("电能")
				* energyValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(energyValue / totalValue));
		}
		
		Map1.put("ammValue", model);

		model = new EnergyDataTabData();
		model.setValue(meiValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤") * meiValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(meiValue / totalValue));
		}
		Map2.put("meiValue", model);

		model = new EnergyDataTabData();
		model.setValue(qiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("汽油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("汽油")
				* qiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(qiyouValue / totalValue));
		}
		Map3.put("qiyouValue", model);

		model = new EnergyDataTabData();
		model.setValue(meiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤油")
				* meiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(meiyouValue / totalValue));
		}
		Map4.put("meiyouValue", model);

		model = new EnergyDataTabData();
		model.setValue(chaiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("柴油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("柴油")
				* chaiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(chaiyouValue / totalValue));
		}
		Map5.put("chaiyouValue", model);

		Gson gson = new Gson();
		String gsonString = gson.toJson(new Object[]
		{ Map1, Map2, Map3, Map4, Map5 });

		////System.out.println("全校的能耗json" + gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();
	}

	private void createFusionStyle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException
	{
	
		int queryYear = 0;
		int archID = 0;
		String archStyle = "";
		float energyValue = 0;// 电
		float yehuashiyouValue = 0;// 液化石油气
		float meiyouValue = 0;// 煤气
		float qiyouValue = 0;// 汽油
		float meiValue = 0;// 煤
		float chaiyouValue = 0;// 柴油
		float otherValue = 0;// 其他
		float rengongmeiqiValue = 0;// 人工煤气

		EnReportModel reportModel = new EnReportModel();
		//EnergyDataTabData manualModel = new EnergyDataTabData();
		Architecture archModel = new Architecture();
		DecimalFormat df = new DecimalFormat("####0.00");
		DecimalFormat df1 = new DecimalFormat("####0.00%");
		ArchitectureDao archDao = new ArchitectureDao();
		EnReportDao reportDao = new EnReportDao();
		EnergyDataTabImpl manualDao = new EnergyDataTabImpl();
		//ManualMonthDao manualMonthDao = new ManualMonthDao();
		ArrayList<EnReportModel> energyList = new ArrayList<EnReportModel>();
		Map<String, String> manualMap = new HashMap<String, String>();
		
		ManualEnergyModel manualModel = new ManualEnergyModel();
		ManualMonthDao manualMonthDao = new ManualMonthDao();
		if (Integer.parseInt(request.getParameter("queryYear")) != 0)
		{
			queryYear = Integer.parseInt(request.getParameter("queryYear"));
		}

		if (request.getParameter("archStyle") != null)
		{
			archStyle = request.getParameter("archStyle").trim();
		}

		String archStyleName = archDao.queryArchStyleName(archStyle.charAt(0))
				.trim();
		try
		{
			manualModel = manualMonthDao.queryArchStyleEnergyAllYear(archStyle,
					queryYear);
			energyValue = manualModel.getEnergyValue();
			meiValue = manualModel.getMeiValue();
			qiyouValue = manualModel.getQiyouValue();
			chaiyouValue = manualModel.getChaiyouValue();
			meiyouValue = manualModel.getMeiyouValue();
		} catch (SQLException e)
		{

			e.printStackTrace();
		}

		float totalValue = energyValue + meiValue + qiyouValue + chaiyouValue
				+ meiyouValue;

		Map<String, EnergyDataTabData> Map1 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map2 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map3 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map4 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map5 = new HashMap<String, EnergyDataTabData>();
		EnergyDataTabData model = null;

		model = new EnergyDataTabData();
		model.setValue(energyValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("电能"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("电能")
				* energyValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(energyValue / totalValue));
		}
		Map1.put("ammValue", model);

		model = new EnergyDataTabData();
		model.setValue(meiValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤") * meiValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(meiValue / totalValue));
		}
		Map2.put("meiValue", model);

		model = new EnergyDataTabData();
		model.setValue(qiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("汽油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("汽油")
				* qiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(qiyouValue / totalValue));
		}
		Map3.put("qiyouValue", model);

		model = new EnergyDataTabData();
		model.setValue(meiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤油")
				* meiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(meiyouValue / totalValue));
		}
		Map4.put("meiyouValue", model);

		model = new EnergyDataTabData();
		model.setValue(chaiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("柴油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("柴油")
				* chaiyouValue);
		if(totalValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(chaiyouValue / totalValue));
		}
		Map5.put("chaiyouValue", model);

		Gson gson = new Gson();
		String gsonString = gson.toJson(new Object[]
		{ Map1, Map2, Map3, Map4, Map5, archStyleName });

		////System.out.println("某建筑类别能耗json" + gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();

	}

	private void createFusion(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException
	{
		Area areaModel = new Area();
		AreaDao areaDao = new AreaDao();
		ReportModel energyModel = new ReportModel();
		ElecReportHelper energyDao = new ElecReportHelperImpl();

		int queryYear = 0;
		int archID = 0;
		String archStyle = "";
		float energyValue = 0;// 电
		float yehuashiyouValue = 0;// 液化石油气
		float meiyouValue = 0;// 煤气
		float qiyouValue = 0;// 汽油
		float meiValue = 0;// 煤
		float chaiyouValue = 0;// 柴油
		float otherValue = 0;// 其他
		float rengongmeiqiValue = 0;// 人工煤气

		EnReportModel reportModel = new EnReportModel();
		EnergyDataTabData manualModel = new EnergyDataTabData();
		Architecture archModel = new Architecture();
		DecimalFormat df = new DecimalFormat("####0.00");
		DecimalFormat df1 = new DecimalFormat("####0.00%");
		ArchitectureDao archDao = new ArchitectureDao();
		EnReportDao reportDao = new EnReportDao();
		EnergyDataTabImpl manualDao = new EnergyDataTabImpl();
		ManualMonthDao manualMonthDao = new ManualMonthDao();
		ArrayList<EnReportModel> energyList = new ArrayList<EnReportModel>();
		Map<String, String> manualMap = new HashMap<String, String>();
		
		if (Integer.parseInt(request.getParameter("queryYear")) != 0)
		{
			queryYear = Integer.parseInt(request.getParameter("queryYear"));
		}

		if (Integer.parseInt(request.getParameter("archID")) != 0)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}

		if (request.getParameter("archStyle") != null)
		{
			archStyle = request.getParameter("archStyle");
		}
		try
		{
			// manualStartList=systemDao.queryStartManualEnergy();
			energyList = reportDao.listManualEnReport();
			archModel = archDao.queryByID(archID);
			manualMap = manualMonthDao.queryEnergyAllYearByArchID(queryYear, archID);
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		ReportModel energyYearModel = new ReportModel();
		ElecReportHelper EnergyDao = new ElecReportHelperImpl();
		energyYearModel = energyDao.getArcCountByYear(archID, queryYear);// 年建筑用电

		meiValue = Float.parseFloat(manualMap.get("meiValue"));
		qiyouValue = Float.parseFloat(manualMap.get("qiyouValue"));
		meiyouValue = Float.parseFloat(manualMap.get("meiyouValue"));
		chaiyouValue = Float.parseFloat(manualMap.get("chaiyouValue"));

		// 下面暂时不统计
		rengongmeiqiValue = Float
				.parseFloat(manualMap.get("rengongmeiqiValue"));
		otherValue = Float.parseFloat(manualMap.get("otherValue"));
		yehuashiyouValue = Float
				.parseFloat(manualMap.get("yehuashiyouqiValue"));

		// 求比例用的总能耗
		float totalEnergyValue = energyYearModel.getTotalEnergyCount()
				+ meiyouValue + qiyouValue + meiValue + chaiyouValue;

		Map<String, EnergyDataTabData> Map1 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map2 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map3 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map4 = new HashMap<String, EnergyDataTabData>();
		Map<String, EnergyDataTabData> Map5 = new HashMap<String, EnergyDataTabData>();
		EnergyDataTabData model = null;

		model = new EnergyDataTabData();
		model.setArcName(archDao.queryByID(archID).getName());
		model.setValue(energyYearModel.getTotalEnergyCount());
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("电能"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("电能")
				* energyYearModel.getTotalEnergyCount());
		if(totalEnergyValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else 
		{
			model.setRatio(df1.format(energyYearModel.getTotalEnergyCount() / totalEnergyValue));
		}
		
		Map1.put("ammValue", model);

		model = new EnergyDataTabData();
		model.setArcName(archDao.queryByID(archID).getName());
		model.setValue(meiValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤") * meiValue);
		if(totalEnergyValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else {
			model.setRatio(df1.format(meiValue / totalEnergyValue));
		}
		
		Map2.put("meiValue", model);

		model = new EnergyDataTabData();
		model.setArcName(archDao.queryByID(archID).getName());
		model.setValue(qiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("汽油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("汽油")
				* qiyouValue);
		if(totalEnergyValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else {
			model.setRatio(df1.format(qiyouValue / totalEnergyValue));
		}
		Map3.put("qiyouValue", model);

		model = new EnergyDataTabData();
		model.setArcName(archDao.queryByID(archID).getName());
		model.setValue(meiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("煤油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("煤油")
				* meiyouValue);
		if(totalEnergyValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else {
			model.setRatio(df1.format(meiyouValue / totalEnergyValue));
		}
		Map4.put("meiyouValue", model);

		model = new EnergyDataTabData();
		model.setArcName(archDao.queryByID(archID).getName());
		model.setValue(chaiyouValue);
		model.setZhebiaoxishu(reportDao.queryCoefficientByName("柴油"));
		model.setZhebiaoValue(reportDao.queryCoefficientByName("柴油")
				* chaiyouValue);
		if(totalEnergyValue==0)
		{
			model.setRatio(df1.format(0));
		}
		else {
			model.setRatio(df1.format(chaiyouValue / totalEnergyValue));
		}
		Map5.put("chaiyouValue", model);

		Gson gson = new Gson();
		String gsonString = gson.toJson(new Object[]
		{ Map1, Map2, Map3, Map4, Map5 });

		////System.out.println("某个建筑的能耗json" + gsonString);
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		out.println(gsonString);
		out.flush();
		out.close();
	}
}



