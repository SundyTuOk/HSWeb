package com.sf.energy.expert.count;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;
import com.sf.energy.water.statistics.dao.WaterReportHelper;
import com.sf.energy.water.statistics.dao.WaterReportHelperImpl;
import com.sf.energy.water.statistics.model.WaterReportModel;

public class SameArcServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int SameArc_Year = 2014;
		String  style = "-1";
		int archID = -1;
		
		ArrayList<Architecture>   arcid = null;
		ReportModel rm = null;
		WaterReportModel wrm = null;
		Architecture archModel=null;
		ArchitectureDao archdao = new ArchitectureDao();
		ElecReportHelper erh=new ElecReportHelperImpl();
		WaterReportHelper wrh=new WaterReportHelperImpl();
		response.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
		PrintWriter out = response.getWriter();
		
		if(request.getParameter("year")!=null)
		{
			SameArc_Year = Integer.parseInt(request.getParameter("year"));
			//System.out.println("年"+SameArc_Year);
		}
		if(request.getParameter("styleID")!=null && request.getParameter("styleID")!="")
		{
			style=request.getParameter("styleID");
			//System.out.println("性质"+style);
		}
		if(request.getParameter("arcID")!=null && request.getParameter("arcID")!="")
		{
			archID=Integer.parseInt(request.getParameter("arcID"));
			//System.out.println("建筑ID"+archID);
		}
		
		
		if(!"-1".equals(style) && archID == -1)
		{
			//System.out.println("建筑分类");
			//System.out.println("分类ID"+style);
			ArrayList<Architecture> archList=new ArrayList<Architecture>();
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			try {
				archList = archdao.queryArchByStyle(style.charAt(0));
				//System.out.println("styleID"+archList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			for(int i = 0;i<archList.size();i++)
			{
				archModel=archList.get(i);
				try {
					//System.out.println(archModel.getId());
					//System.out.println(archModel.getName());
					rm = erh.getArcCountByYear(archModel.getId(), SameArc_Year); //获取电能
					wrm = wrh.getArcCountByYear(archModel.getId(), SameArc_Year); //获取水能
					////System.out.println(rm.getArcName());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				jo.put("arcname", archModel.getName());
				jo.put("totalenergy", rm.getTotalEnergyCount());
				jo.put("totalwaterenergy", wrm.getTotalWaterCount());
				json.add(jo);
			}
			//System.out.println(json.toString());	
			out.println(json.toString());
		}else if(!"-1".equals(style) && archID != -1)
		{
			//System.out.println("建筑");
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			
			
			try {
				archModel = archdao.queryByID(archID);
				
				rm = erh.getArcCountByYear(archID, SameArc_Year);  //获取电能
				wrm = wrh.getArcCountByYear(archID, SameArc_Year); //获取水能
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			jo.put("arcname", archModel.getName());
			jo.put("totalenergy", rm.getTotalEnergyCount());
			jo.put("totalwaterenergy", wrm.getTotalWaterCount());
			json.add(jo);
			
			//System.out.println(json.toString());	
			out.println(json.toString());
			
		}else if("-1".equals(style) && archID == -1)
		{
			//System.out.println("全校");
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			archdao = new ArchitectureDao();
			DictionaryValueDao dvd=new DictionaryValueDao();
			ArrayList<DictionaryValueModel> archStyleList=new ArrayList<DictionaryValueModel>();
			
			try {
				archStyleList=dvd.listDictionaryValueByName("建筑分类");
				for(int i=0;i<archStyleList.size();i++)
				{
					
				    String styleName = archdao.queryArchStyleName(archStyleList.get(i).getDictionaryValueNum().charAt(0));
					rm = erh.getFenleiArcCountByYear(archStyleList.get(i).getDictionaryValueNum().charAt(0), SameArc_Year); //获取电能
					wrm = wrh.getFenleiArcCountByYear(archStyleList.get(i).getDictionaryValueNum().charAt(0), SameArc_Year); //获取水能
					jo.put("styleID", archStyleList.get(i).getDictionaryValueNum().charAt(0));
					jo.put("arcname", styleName);
					jo.put("totalenergy", rm.getTotalEnergyCount());
					jo.put("totalwaterenergy", wrm.getTotalWaterCount());
					json.add(jo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//System.out.println(json.toString());	
			out.println(json.toString());
		}
		
		out.flush();
		out.close();
	}	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	

}
