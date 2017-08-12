package com.sf.energy.expert.count;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.ManualMonthDao;
import com.sf.energy.expert.model.ManualEnergyModel;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class SameContrastServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			
			int styleID = -1;		//水电标记
			
			if(request.getParameter("styleID") != null)
			{
				styleID = Integer.parseInt(request.getParameter("styleID"));
			}
			
			try {
				if(styleID == 1)	
				{
					getSameContrastEnergyData(request,response);
				}
				else if(styleID == 0)
				{
					getSameContrastWaterData(request,response);
				}
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	private void getSameContrastEnergyData(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		PrintWriter out = response.getWriter();
		
		String yearlist = "";			//表示已选择年份字符串
		int ArcID = -1;
		String  ArcstyleID = "-1";		//建筑类型
		
		if(request.getParameter("yearlist") != null)
		{
			yearlist=request.getParameter("yearlist").substring(0, request.getParameter("yearlist").length()-1);
		}
		String YearList[]=yearlist.split(",");		//年份字符串转换为数组
		
		if(request.getParameter("arcID") != null)
		{
			ArcID = Integer.parseInt(request.getParameter("arcID"));
		}
		if(request.getParameter("ArcstyleID") != null)
		{
			ArcstyleID = request.getParameter("ArcstyleID");
		}
		
		
		
		ManualMonthDao  mmDao = new ManualMonthDao();
		ElecReportHelperImpl	ehi = new ElecReportHelperImpl();
		Gson 	gson = new Gson();
		
		if("-1".equals(ArcstyleID) && ArcID == -1)
		{
			//System.out.println("全校");
			List<ReportModel>	list = null;
			Map<Integer,List<ReportModel>> map = new HashMap<Integer,List<ReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				list = ehi.getAllAreaCountEveryMonth(Integer.parseInt(YearList[i]));
				map.put(Integer.parseInt(YearList[i]), list);
			}
			
			String data = gson.toJson(map);
			//System.out.println(data);  
			out.println(data);
		}
		else if(!"-1".equals(ArcstyleID) && ArcID == -1)
		{
			//System.out.println("建筑分类");
			List<ReportModel>	list = null;
			Map<Integer,List<ReportModel>> map = new HashMap<Integer,List<ReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				list = ehi.getArcStyleCountEveryMonth(ArcstyleID, Integer.parseInt(YearList[i]));
				map.put(Integer.parseInt(YearList[i]), list);
			}
			
			String data = gson.toJson(map);
			//System.out.println(data);  
			out.println(data);
		}
		else if(!"-1".equals(ArcstyleID) && ArcID != -1)
		{
			//System.out.println("建筑");
			
			List<ReportModel>	list = null;
			Map<Integer,List<ReportModel>> map = new HashMap<Integer,List<ReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				//list = new ArrayList<ReportModel>();
				
				list = ehi.getArcCountEveryMonth(ArcID, Integer.parseInt(YearList[i]));
				
				map.put(Integer.parseInt(YearList[i]), list);
			}
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
		
		/*if(styleID == 1)
		{
			List<ReportModel>	time_list = null;
			List<ReportModel>	time_list1 = null;
			List<ReportModel>	time_list2 = null;
		
			ElecReportHelperImpl  rhi_SameContrast = new ElecReportHelperImpl();
			
			if(AreaID == -1)
			{
				try {
					time_list = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year);
					time_list1 = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year-1);
					time_list2 = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(AreaID != -1&&(ArcID ==-1 || ArcID == 0))
			{
				try {
					time_list = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year);
					time_list1 = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year-1);
					time_list2 = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(AreaID != -1&&ArcID !=-1 &&ArcID!=0)
			{
				try {
					time_list = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year);
					time_list1 = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year-1);
					time_list2 = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Gson gson_SameContrast = new Gson();
			String SameContrastdata = gson_SameContrast.toJson(new Object[]{time_list,time_list1,time_list2});
			////System.out.println(SameContrastdata);
			out.println(SameContrastdata);
		}
		else if(styleID == 0) //选了水的，调用水的方法
		{
			List<WaterReportModel>	time_list = null;
			List<WaterReportModel>	time_list1 = null;
			List<WaterReportModel>	time_list2 = null;
		
			WaterReportHelperImpl  rhi_SameContrast = new WaterReportHelperImpl();
			
			if(AreaID == -1)
			{
				try {
					time_list = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year);
					time_list1 = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year-1);
					time_list2 = rhi_SameContrast.getAllAreaCountEveryMonth(Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(AreaID != -1&&(ArcID ==-1 || ArcID == 0))
			{
				try {
					time_list = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year);
					time_list1 = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year-1);
					time_list2 = rhi_SameContrast.getAreaCountEveryMonth(AreaID, Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(AreaID != -1&&ArcID !=-1 &&ArcID!=0)
			{
				try {
					time_list = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year);
					time_list1 = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year-1);
					time_list2 = rhi_SameContrast.getArcCountEveryMonth(ArcID, Contrast_Year+1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Gson gson_SameContrast = new Gson();
			String SameContrastdata = gson_SameContrast.toJson(new Object[]{time_list,time_list1,time_list2});
			////System.out.println(SameContrastdata);
			out.println(SameContrastdata);
}
		
		*/
		out.flush();
		out.close();
	}
	
	private void getSameContrastWaterData(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		PrintWriter out = response.getWriter();
		
		String yearlist = "";			//表示已选择年份字符串
		int ArcID = -1;
		String  ArcstyleID = "-1";		//建筑类型
		
		if(request.getParameter("yearlist") != null)
		{
			yearlist=request.getParameter("yearlist").substring(0, request.getParameter("yearlist").length()-1);
		}
		String YearList[]=yearlist.split(",");		//年份字符串转换为数组
		
		if(request.getParameter("arcID") != null)
		{
			ArcID = Integer.parseInt(request.getParameter("arcID"));
		}
		if(request.getParameter("ArcstyleID") != null)
		{
			ArcstyleID = request.getParameter("ArcstyleID");
		}
		
		
		
		WaterReportHelperImpl	whi = new WaterReportHelperImpl();
		Gson 	gson = new Gson();
		
		if("-1".equals(ArcstyleID) && ArcID == -1)
		{
			//System.out.println("全校");
			List<WaterReportModel>	list = null;
			Map<Integer,List<WaterReportModel>> map = new HashMap<Integer,List<WaterReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				list = whi.getAllAreaCountEveryMonth(Integer.parseInt(YearList[i]));
				map.put(Integer.parseInt(YearList[i]), list);
			}
			
			String data = gson.toJson(map);
			//System.out.println(data);  
			out.println(data);
		}
		else if(!"-1".equals(ArcstyleID) && ArcID == -1)
		{
			//System.out.println("建筑分类");
			List<WaterReportModel>	list = null;
			Map<Integer,List<WaterReportModel>> map = new HashMap<Integer,List<WaterReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				list = whi.getArcStyleCountEveryMonth(ArcstyleID, Integer.parseInt(YearList[i]));
				map.put(Integer.parseInt(YearList[i]), list);
			}
			
			String data = gson.toJson(map);
			//System.out.println(data);  
			out.println(data);
		}
		else if(!"-1".equals(ArcstyleID) && ArcID != -1)
		{
			//System.out.println("建筑");
			
			List<WaterReportModel>	list = null;
			Map<Integer,List<WaterReportModel>> map = new HashMap<Integer,List<WaterReportModel>>();
			
			for(int i=0;i<YearList.length;i++)
			{
				//list = new ArrayList<WaterReportModel>();
				
				list = whi.getArcCountEveryMonth(ArcID, Integer.parseInt(YearList[i]));
				
				map.put(Integer.parseInt(YearList[i]), list);
			}
			String data = gson.toJson(map);
			//System.out.println(data);
			out.println(data);
		}
	}

}
