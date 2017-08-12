package com.sf.energy.statistics.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.ArcMulSelectModel;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.AmHelper;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.statistics.model.ReportModel;

public class ArchAnalysis_ArchServlert extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String ArchIDList = ""; //表示已选择建筑字符串
		String AreaIDList = "";			//表示已选择区域字符串
		int ArcContrast_Year=2014,ArcContrast_Month=4;
		Map<Integer, Area> resultMap = null;    //查询所有的区域ID
		List<Integer>		ArcIDlist = null;	//存放每个区域下的所有建筑
		List<Integer>		allArcIDlist = null;	//存放所有区域下的所有建筑
		List<Architecture> arealist = null; //单个区域的建筑ID
		
		ArcIDlist = new ArrayList<Integer>();
		allArcIDlist = new ArrayList<Integer>();
		ArchitectureDao  archDao = new ArchitectureDao();
		AreaDao  areaDao = new AreaDao();
		
		if(request.getParameter("year") != null){
			ArcContrast_Year=Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month") != null){
			ArcContrast_Month=Integer.parseInt(request.getParameter("month"));
		}

		//System.out.println("area"+request.getParameter("AreaIDList"));
		//System.out.println("arch"+request.getParameter("ArchIDList"));
		
		
		
		
		
		ElecReportHelperImpl  rhi_ArcContrast = new ElecReportHelperImpl();
		List<ReportModel>  ArcContrastlist = new ArrayList<ReportModel>();
		ReportModel ArcContrastreportmodel = null;
		Map<String,List<ReportModel>> ArcContrastmap_Arc = new HashMap<String,List<ReportModel>>();//建筑的map
		Map<String,List<ReportModel>> ArcContrastmap_Area = new HashMap<String,List<ReportModel>>();//区域的map
		Date ArcContrastdate1 = new Date();
		ArcContrastdate1.setYear(ArcContrast_Year-1);
		ArcContrastdate1.setMonth(ArcContrast_Month);
		Date ArcContrastdate2 = new Date();
		ArcContrastdate2.setYear(ArcContrast_Year);
		ArcContrastdate2.setMonth(ArcContrast_Month);
		Date ArcContrastdate3 = new Date();
		ArcContrastdate3.setYear(ArcContrast_Year);
		ArcContrastdate3.setMonth(ArcContrast_Month-1);
		Date ArcContrasttime[]={ArcContrastdate1,ArcContrastdate2,ArcContrastdate3};

		if(request.getParameter("ArchIDList") !=null && !"".equals(request.getParameter("ArchIDList")))
		{
			//System.out.println("ArchIDList"+request.getParameter("ArchIDList"));
			ArchIDList=request.getParameter("ArchIDList").substring(0, request.getParameter("ArchIDList").length()-1);
			//System.out.println("ArchIDList2"+ArchIDList);
			String ArchIDLists[]=ArchIDList.split(",");
			for(int i = 0;i<ArchIDLists.length;i++){
				ArcContrastlist = new ArrayList<ReportModel>();
				for(int j = 0;j<ArcContrasttime.length;j++){
					try {
						//System.out.println("ArchIDLists[i]"+Integer.parseInt(ArchIDLists[i]));					
						ArcContrastreportmodel = rhi_ArcContrast.getArcCountDetailByMonth(Integer.parseInt(ArchIDLists[i]), ArcContrasttime[j].getYear(),ArcContrasttime[j].getMonth());
						ArcContrastlist.add(ArcContrastreportmodel);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				ArcContrastmap_Arc.put(ArcContrastreportmodel.getArcName(), ArcContrastlist);
			}
		}
		if(request.getParameter("AreaIDList")!=null&& !"".equals(request.getParameter("AreaIDList")))
		{
			//System.out.println("AreaIDList333"+request.getParameter("AreaIDList"));
			AreaIDList = request.getParameter("AreaIDList").substring(0, request.getParameter("AreaIDList").length()-1);
			//System.out.println("AreaIDList"+AreaIDList);
			String AreaIDLists[]=AreaIDList.split(",");
			for(int i = 0;i<AreaIDLists.length;i++){
				ArcContrastlist = new ArrayList<ReportModel>();
				for(int j = 0;j<ArcContrasttime.length;j++){
					try {
						//System.out.println("AreaIDLists[i]"+Integer.parseInt(AreaIDLists[i]));
						ArcContrastreportmodel = rhi_ArcContrast.getAreaCountByMonth(Integer.parseInt(AreaIDLists[i]),  ArcContrasttime[j].getYear(),ArcContrasttime[j].getMonth());
						ArcContrastlist.add(ArcContrastreportmodel);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				ArcContrastmap_Area.put(ArcContrastreportmodel.getAreaName(), ArcContrastlist);
			}
		}
		
		
		
		Gson gson_ArcContrast = new Gson();
		String ArcContrastdata = gson_ArcContrast.toJson(new Object[]{ArcContrastmap_Arc,ArcContrastmap_Area});
		//System.out.println(ArcContrastdata);
		PrintWriter out = response.getWriter();
		out.println(ArcContrastdata);
		out.flush();
		out.close();
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	
}
