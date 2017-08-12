package com.sf.energy.water.statistics.servlet;

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

import com.google.gson.Gson;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class WaterPartmentAnalysisPartmentServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取指定部门指定年月电量数据
				response.setCharacterEncoding("utf-8");
				request.setCharacterEncoding("utf-8");
				int PartmentContrast_Year=2014,PartmentContrast_Month=4;
				
				String partmentIDList="";
				
				if(request.getParameter("year")!=null)
				{
					PartmentContrast_Year=Integer.parseInt(request.getParameter("year"));
				}
				if(request.getParameter("month")!=null)
				{
					PartmentContrast_Month=Integer.parseInt(request.getParameter("month"));
				}
				
				if(request.getParameter("partmentIDList") !=null ){
					//System.out.println("partmentIDList"+request.getParameter("partmentIDList"));
					partmentIDList=request.getParameter("partmentIDList").substring(0, request.getParameter("partmentIDList").length()-1);
					//System.out.println("partmentIDList2"+partmentIDList);
					
				}
				
				String partmentIDLists[] = partmentIDList.split(",");
				
				ElecReportHelperImpl  rhi_PartmentContrast = new ElecReportHelperImpl();
				List<ReportModel>  PartmentContrastlist = new ArrayList<ReportModel>();
				ReportModel PartmentContrastreportmodel = null;
				Map<String,List<ReportModel>> PartmentContrastmap = new HashMap<String,List<ReportModel>>();
				Date PartmentContrastdate1 = new Date();
				PartmentContrastdate1.setYear(PartmentContrast_Year-1);
				PartmentContrastdate1.setMonth(PartmentContrast_Month);
				Date PartmentContrastdate2 = new Date();
				PartmentContrastdate2.setYear(PartmentContrast_Year);
				PartmentContrastdate2.setMonth(PartmentContrast_Month);
				Date PartmentContrastdate3 = new Date();
				PartmentContrastdate3.setYear(PartmentContrast_Year);
				PartmentContrastdate3.setMonth(PartmentContrast_Month-1);
				Date PartmentContrasttime[]={PartmentContrastdate1,PartmentContrastdate2,PartmentContrastdate3};
				
				for(int i = 0;i<partmentIDLists.length;i++)
				{
					PartmentContrastlist = new ArrayList<ReportModel>();
					for(int j = 0;j<PartmentContrasttime.length;j++)
					{
				  		try {
							PartmentContrastreportmodel = rhi_PartmentContrast.getGroupCountDetailByMonth(Integer.parseInt(partmentIDLists[i]), PartmentContrasttime[j].getYear(),PartmentContrasttime[j].getMonth());
							PartmentContrastlist.add(PartmentContrastreportmodel); 
						} catch (SQLException e) {
							e.printStackTrace();
						} 		
				  	}
				  	PartmentContrastmap.put(PartmentContrastreportmodel.getGroupName(), PartmentContrastlist);
				}
				Gson gson_PartmentContrast = new Gson();
				String PartmentContrastdata = gson_PartmentContrast.toJson(PartmentContrastmap);
				////System.out.println(PartmentContrastdata);
				PrintWriter out = response.getWriter();
				out.println(PartmentContrastdata);
				out.flush();
				out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
