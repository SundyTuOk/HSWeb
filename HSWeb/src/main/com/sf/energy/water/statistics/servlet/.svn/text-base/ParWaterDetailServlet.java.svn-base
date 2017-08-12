package com.sf.energy.water.statistics.servlet;

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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.statistics.servlet.ArcArray;
import com.sf.energy.water.statistics.dao.WaterDetailHelper;
import com.sf.energy.water.statistics.dao.WaterDetailHelperImpl;
import com.sf.energy.water.statistics.model.WaterDetailData;

public class ParWaterDetailServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int Partment_ID = -1;//表示选择所有部门
		String st = "";
		String et = "";
		String count = "";
		String index = "";
		
		List<WaterDetailData>  	ParAmDetail_result = null;
		List<WaterDetailData>	maplist = null; //存放map里面的value集合 
		
		Gson gson_ParAmDetail = new Gson();
		WaterDetailHelperImpl  rhi_ParAmDetail = new WaterDetailHelperImpl();
		
		if(request.getParameter("pid") != null)
		{
			Partment_ID = Integer.parseInt(request.getParameter("pid"));
		}
		if(request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if(request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}
		if(request.getParameter("thePageCount") != null)
		{
			count = request.getParameter("thePageCount");
		}

		if(request.getParameter("thePageIndex") != null)
		{
			index = request.getParameter("thePageIndex");
		}
		////System.out.println("Partment_ID"+Partment_ID);
		
		
		int totalCount=0;
		int thePageCount=Integer.parseInt(count);
		int thePageIndex=Integer.parseInt(index);
		
		
		
		
		if(Partment_ID == -1||Partment_ID == 0)//选了所有部门的情况
		{
			////System.out.println("全校");
			int Flag = 1;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			
			Map<String,List<WaterDetailData>> ParAmDetail_map = new HashMap<String,List<WaterDetailData>>();//存放对象数据的map
				try{
					ParAmDetail_result = rhi_ParAmDetail.getWaterDtailAllPartment(thePageCount, thePageIndex, st, et,order);
					totalCount = rhi_ParAmDetail.getTotalCount();
					WaterDetailData amDetailData = null;
					
					for(int i=0;i<ParAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ParAmDetail_result.get(i);
						maplist = new ArrayList<WaterDetailData>();
						for(int j=0;j<ParAmDetail_result.size();j++)
						{
							if(ParAmDetail_result.get(j).getPartmentName().equals(amDetailData.getPartmentName()))
							{
								maplist.add(ParAmDetail_result.get(j));
							}
							
						}
						
						for(int k = 0;k<maplist.size();k++)
						{
							
							istotalAm +=maplist.get(k).getIstotalvalue();
							
							nototalAm +=maplist.get(k).getNototalvalue();
							
						}
						
						for(int m = 0;m<maplist.size();m++)
						{
							maplist.get(m).setCountistotal(istotalAm);
							maplist.get(m).setCountnototal(nototalAm);
						}
						
						
						ParAmDetail_map.put(amDetailData.getPartmentName(), maplist);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String ParAmDetaildata = gson_ParAmDetail.toJson(new Object[]{Flag,ParAmDetail_map,totalCount});
				////System.out.println("卡拉"+ParAmDetaildata);
				out.println(ParAmDetaildata);
		}
		else if(Partment_ID !=-1&&Partment_ID !=0)//只选了一个部门的情况
		{	
			
			////System.out.println("部门");
			int Flag = 2;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			
			Map<String,List<WaterDetailData>> ParAmDetail_map = new HashMap<String,List<WaterDetailData>>();//存放对象数据的map
				try{
					ParAmDetail_result = rhi_ParAmDetail.getWaterDtailByPartment(thePageCount, thePageIndex, Partment_ID, st, et, order);
					totalCount = rhi_ParAmDetail.getTotalCount();
					WaterDetailData amDetailData = null;
					
					for(int i=0;i<ParAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ParAmDetail_result.get(i);
						maplist = new ArrayList<WaterDetailData>();
						for(int j=0;j<ParAmDetail_result.size();j++)
						{
							if(ParAmDetail_result.get(j).getPartmentName().equals(amDetailData.getPartmentName()))
							{
								maplist.add(ParAmDetail_result.get(j));
							}
							
						}
						
						for(int k = 0;k<maplist.size();k++)
						{
							
							istotalAm +=maplist.get(k).getIstotalvalue();
							
							nototalAm +=maplist.get(k).getNototalvalue();
							
						}
						
						for(int m = 0;m<maplist.size();m++)
						{
							maplist.get(m).setCountistotal(istotalAm);
							maplist.get(m).setCountnototal(nototalAm);
						}
						
						
						ParAmDetail_map.put(amDetailData.getPartmentName(), maplist);
						////System.out.println("名字"+amDetailData.getPartmentName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String ParAmDetaildata = gson_ParAmDetail.toJson(new Object[]{Flag,ParAmDetail_map,totalCount});
			out.println(ParAmDetaildata);
		}
		
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
}

