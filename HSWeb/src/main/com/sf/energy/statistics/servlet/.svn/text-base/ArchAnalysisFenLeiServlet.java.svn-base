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

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Architecture;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.ReportModel;

public class ArchAnalysisFenLeiServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		int ArcTimeFenlei_start_year1=2012,ArcTimeFenlei_start_month1=2,
		ArcTimeFenlei_end_year1=2014,ArcTimeFenlei_end_month1=4,
		ArcTimeFenlei_start_year2=2013,ArcTimeFenlei_start_month2=2,
		ArcTimeFenlei_end_year2=2014,ArcTimeFenlei_end_month2=4;
		int ArcID = -1; 		//表示选择选择所有建筑
		int AreaID = -1;		//表示选择所有区域
		
		Map<Integer, Area> resultMap = null;    //查询所有的区域ID
		List<Integer>		allArcIDlist = null;	//存放所有区域下的所有建筑
		List<Architecture> arealist = null; //单个区域的建筑ID
		List<Architecture> list = null; //通过区域id获取下面的所有建筑id
		
		List<Integer>		ArcIDlist = null;	//存放每个区域下的所有建筑
		allArcIDlist = new ArrayList<Integer>();
		ArchitectureDao  archDao = new ArchitectureDao();
		AreaDao  areaDao = new AreaDao();
		ArcIDlist = new ArrayList<Integer>();
		
		if(request.getParameter("start_year1")!=null){
			//System.out.println(ArcTimeFenlei_start_year1);
			ArcTimeFenlei_start_year1=Integer.parseInt(request.getParameter("start_year1"));
		}
		if(request.getParameter("start_month1")!=null){
			ArcTimeFenlei_start_month1=Integer.parseInt(request.getParameter("start_month1"));
		}
		if(request.getParameter("end_year1")!=null){
			ArcTimeFenlei_end_year1=Integer.parseInt(request.getParameter("end_year1"));
		}
		if(request.getParameter("end_month1")!=null){
			ArcTimeFenlei_end_month1=Integer.parseInt(request.getParameter("end_month1"));
		}
		if(request.getParameter("start_year2")!=null){
			ArcTimeFenlei_start_year2=Integer.parseInt(request.getParameter("start_year2"));
		}
		if(request.getParameter("start_month2")!=null){
			ArcTimeFenlei_start_month2=Integer.parseInt(request.getParameter("start_month2"));
		}
		if(request.getParameter("end_year2")!=null){
			ArcTimeFenlei_end_year2=Integer.parseInt(request.getParameter("end_year2"));
		}
		if(request.getParameter("end_month2")!=null){
			ArcTimeFenlei_end_month2=Integer.parseInt(request.getParameter("end_month2"));
		}
		
	
		if(request.getParameter("aid") !=null ){
			ArcID=Integer.parseInt(request.getParameter("aid"));
		}
		if(request.getParameter("areaid") !=null ){
			AreaID=Integer.parseInt(request.getParameter("areaid"));
		}
		
		
		//查出所有的区域ID
		try {
			resultMap = areaDao.getAllArea();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
				
		//查出所有区域下的建筑ID
		for (int i : resultMap.keySet()) {
			try {
				arealist = archDao.queryArchByAreaID(i);
				for (int j = 0; j < arealist.size(); j++) {
					allArcIDlist.add(arealist.get(j).getId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//通过区域ID查出对应区域下面建筑ID
		try {
			list = archDao.queryArchByAreaID(AreaID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ArcIDlist.add(list.get(i).getId());
		}
				
		Date ArcTimeFenlei_fenxiangstart=new Date(),ArcTimeFenlei_fenxiangend=new Date();
		ArcTimeFenlei_fenxiangstart.setYear(ArcTimeFenlei_start_year1-1900);
		ArcTimeFenlei_fenxiangstart.setMonth(ArcTimeFenlei_start_month1);
		ArcTimeFenlei_fenxiangstart.setDate(1);
		ArcTimeFenlei_fenxiangend.setYear(ArcTimeFenlei_end_year1-1900);
		ArcTimeFenlei_fenxiangend.setMonth(ArcTimeFenlei_end_month1);
		ArcTimeFenlei_fenxiangend.setDate(30);
		Date ArcTimeFenlei_fenxiangstart1=new Date(),ArcTimeFenlei_fenxiangend1=new Date();
		ArcTimeFenlei_fenxiangstart1.setYear(ArcTimeFenlei_start_year2-1900);
		ArcTimeFenlei_fenxiangstart1.setMonth(ArcTimeFenlei_start_month2);
		ArcTimeFenlei_fenxiangstart1.setDate(1);
		ArcTimeFenlei_fenxiangend1.setYear(ArcTimeFenlei_end_year2-1900);
		ArcTimeFenlei_fenxiangend1.setMonth(ArcTimeFenlei_end_month2);
		ArcTimeFenlei_fenxiangend1.setDate(30);
		Map<String, Float> ArcTimeFenlei_fenxiangmap = null;
		Map<String, Float> ArcTimeFenlei_fenxiangmap1 = null;
		Map<String, Float>  ArcTimeFenlei_stylemap = null;
		Map<String, Float>  ArcTimeFenlei_stylemap1 = null;
		float Value_fenxiang1 = 0;
		float Value_fenxiang2 = 0;
		float Value_fenxiang3 = 0;
		float Value_fenxiang4 = 0;
		float Value_fenxiang11 = 0;
		float Value_fenxiang22 = 0;
		float Value_fenxiang33 = 0;
		float Value_fenxiang44 = 0;
		float Value_style1 = 0;
		float Value_style2 = 0;
		float Value_style3 = 0;
		float Value_style4 = 0;
		float Value_style11 = 0;
		float Value_style22 = 0;
		float Value_style33 = 0;
		float Value_style44 = 0;
		
		PrintWriter out = response.getWriter();
		
		if( AreaID == -1) //选了所有区域的情形，列出区域下所有建筑
		{
//				//System.out.println("ttt:"+allArcIDlist);
				ElecReportHelperImpl  rhi_ArcTimeFenlei_fenxiang = new ElecReportHelperImpl();
				ElecReportHelperImpl  rhi_ArcTimeFenlei_style = new ElecReportHelperImpl();
				try {
					for(int i=0;i<allArcIDlist.size();i++)
					{
//						//System.out.println("zzz"+allArcIDlist.get(i).intValue());
						ArcTimeFenlei_fenxiangmap = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(allArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
						ArcTimeFenlei_fenxiangmap1 = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(allArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
						Value_fenxiang1+=ArcTimeFenlei_fenxiangmap.get("特殊用电");
						Value_fenxiang2+=ArcTimeFenlei_fenxiangmap.get("空调用电");
						Value_fenxiang3+=ArcTimeFenlei_fenxiangmap.get("照明插座");
						Value_fenxiang4+=ArcTimeFenlei_fenxiangmap.get("动力用电");
						Value_fenxiang11+=ArcTimeFenlei_fenxiangmap1.get("特殊用电");
						Value_fenxiang22+=ArcTimeFenlei_fenxiangmap1.get("空调用电");
						Value_fenxiang33+=ArcTimeFenlei_fenxiangmap1.get("照明插座");
						Value_fenxiang44+=ArcTimeFenlei_fenxiangmap1.get("动力用电");
					    ArcTimeFenlei_stylemap = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(allArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
						ArcTimeFenlei_stylemap1 = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(allArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
						Value_style1+=ArcTimeFenlei_stylemap.get("生活");
						Value_style2+=ArcTimeFenlei_stylemap.get("教学");
						Value_style3+=ArcTimeFenlei_stylemap.get("公共");
						Value_style4+=ArcTimeFenlei_stylemap.get("商业");
						Value_style11+=ArcTimeFenlei_stylemap1.get("生活");
						Value_style22+=ArcTimeFenlei_stylemap1.get("教学");
						Value_style33+=ArcTimeFenlei_stylemap1.get("公共");
						Value_style44+=ArcTimeFenlei_stylemap1.get("商业");
						
					}
					ArcTimeFenlei_fenxiangmap = new HashMap<String, Float>();
					ArcTimeFenlei_fenxiangmap.put("特殊用电", Value_fenxiang1);
					ArcTimeFenlei_fenxiangmap.put("空调用电", Value_fenxiang2);
					ArcTimeFenlei_fenxiangmap.put("照明插座", Value_fenxiang3);
					ArcTimeFenlei_fenxiangmap.put("动力用电", Value_fenxiang4);
					ArcTimeFenlei_fenxiangmap1 = new HashMap<String, Float>();
					ArcTimeFenlei_fenxiangmap1.put("特殊用电", Value_fenxiang11);
					ArcTimeFenlei_fenxiangmap1.put("空调用电", Value_fenxiang22);
					ArcTimeFenlei_fenxiangmap1.put("照明插座", Value_fenxiang33);
					ArcTimeFenlei_fenxiangmap1.put("动力用电", Value_fenxiang44);
					ArcTimeFenlei_stylemap = new HashMap<String, Float>();
					ArcTimeFenlei_stylemap.put("生活", Value_style1);
					ArcTimeFenlei_stylemap.put("教学", Value_style2);
					ArcTimeFenlei_stylemap.put("公共", Value_style3);
					ArcTimeFenlei_stylemap.put("商业", Value_style4);
					ArcTimeFenlei_stylemap1 = new HashMap<String, Float>();
					ArcTimeFenlei_stylemap1.put("生活", Value_style11);
					ArcTimeFenlei_stylemap1.put("教学", Value_style22);
					ArcTimeFenlei_stylemap1.put("公共", Value_style33);
					ArcTimeFenlei_stylemap1.put("商业", Value_style44);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}else if(AreaID != -1 && ArcID==-1)
		{
			ElecReportHelperImpl  rhi_ArcTimeFenlei_fenxiang = new ElecReportHelperImpl();
			ElecReportHelperImpl  rhi_ArcTimeFenlei_style = new ElecReportHelperImpl();
			try {
				for(int i=0;i<ArcIDlist.size();i++)
				{
//					//System.out.println("zzz"+ArcIDlist.get(i).intValue());
					ArcTimeFenlei_fenxiangmap = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(ArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
					ArcTimeFenlei_fenxiangmap1 = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(ArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
					Value_fenxiang1+=ArcTimeFenlei_fenxiangmap.get("特殊用电");
					Value_fenxiang2+=ArcTimeFenlei_fenxiangmap.get("空调用电");
					Value_fenxiang3+=ArcTimeFenlei_fenxiangmap.get("照明插座");
					Value_fenxiang4+=ArcTimeFenlei_fenxiangmap.get("动力用电");
					Value_fenxiang11+=ArcTimeFenlei_fenxiangmap1.get("特殊用电");
					Value_fenxiang22+=ArcTimeFenlei_fenxiangmap1.get("空调用电");
					Value_fenxiang33+=ArcTimeFenlei_fenxiangmap1.get("照明插座");
					Value_fenxiang44+=ArcTimeFenlei_fenxiangmap1.get("动力用电");
				    ArcTimeFenlei_stylemap = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(ArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
					ArcTimeFenlei_stylemap1 = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(ArcIDlist.get(i).intValue(),ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
					Value_style1+=ArcTimeFenlei_stylemap.get("生活");
					Value_style2+=ArcTimeFenlei_stylemap.get("教学");
					Value_style3+=ArcTimeFenlei_stylemap.get("公共");
					Value_style4+=ArcTimeFenlei_stylemap.get("商业");
					Value_style11+=ArcTimeFenlei_stylemap1.get("生活");
					Value_style22+=ArcTimeFenlei_stylemap1.get("教学");
					Value_style33+=ArcTimeFenlei_stylemap1.get("公共");
					Value_style44+=ArcTimeFenlei_stylemap1.get("商业");
					
				}
				ArcTimeFenlei_fenxiangmap = new HashMap<String, Float>();
				ArcTimeFenlei_fenxiangmap.put("特殊用电", Value_fenxiang1);
				ArcTimeFenlei_fenxiangmap.put("空调用电", Value_fenxiang2);
				ArcTimeFenlei_fenxiangmap.put("照明插座", Value_fenxiang3);
				ArcTimeFenlei_fenxiangmap.put("动力用电", Value_fenxiang4);
				ArcTimeFenlei_fenxiangmap1 = new HashMap<String, Float>();
				ArcTimeFenlei_fenxiangmap1.put("特殊用电", Value_fenxiang11);
				ArcTimeFenlei_fenxiangmap1.put("空调用电", Value_fenxiang22);
				ArcTimeFenlei_fenxiangmap1.put("照明插座", Value_fenxiang33);
				ArcTimeFenlei_fenxiangmap1.put("动力用电", Value_fenxiang44);
				ArcTimeFenlei_stylemap = new HashMap<String, Float>();
				ArcTimeFenlei_stylemap.put("生活", Value_style1);
				ArcTimeFenlei_stylemap.put("教学", Value_style2);
				ArcTimeFenlei_stylemap.put("公共", Value_style3);
				ArcTimeFenlei_stylemap.put("商业", Value_style4);
				ArcTimeFenlei_stylemap1 = new HashMap<String, Float>();
				ArcTimeFenlei_stylemap1.put("生活", Value_style11);
				ArcTimeFenlei_stylemap1.put("教学", Value_style22);
				ArcTimeFenlei_stylemap1.put("公共", Value_style33);
				ArcTimeFenlei_stylemap1.put("商业", Value_style44);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		else if(AreaID != -1 && ArcID!=-1)
		{
		
			try {
				ElecReportHelperImpl  rhi_ArcTimeFenlei_fenxiang = new ElecReportHelperImpl();
				ArcTimeFenlei_fenxiangmap = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(ArcID,ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
				ArcTimeFenlei_fenxiangmap1 = rhi_ArcTimeFenlei_fenxiang.getArcFenLeiCountBetween(ArcID,ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
				ElecReportHelperImpl  rhi_ArcTimeFenlei_style = new ElecReportHelperImpl();
				ArcTimeFenlei_stylemap = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(ArcID,ArcTimeFenlei_fenxiangstart, ArcTimeFenlei_fenxiangend);
				ArcTimeFenlei_stylemap1 = rhi_ArcTimeFenlei_style.getArcStyleCountBetween(ArcID,ArcTimeFenlei_fenxiangstart1, ArcTimeFenlei_fenxiangend1);
				
				////System.out.println(ArcTimeFenlei_fenxiangdata);
				
			} catch (SQLException e) {
				e.printStackTrace();
				out.print(-1);
			}
		}
		Gson gson_ArcTimeFenlei_style = new Gson();
		String ArcTimeFenlei_fenxiangdata = gson_ArcTimeFenlei_style.toJson(new Object[]
				{ArcTimeFenlei_fenxiangmap,ArcTimeFenlei_fenxiangmap1,
				ArcTimeFenlei_stylemap,ArcTimeFenlei_stylemap1});
		out.println(ArcTimeFenlei_fenxiangdata);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
}
