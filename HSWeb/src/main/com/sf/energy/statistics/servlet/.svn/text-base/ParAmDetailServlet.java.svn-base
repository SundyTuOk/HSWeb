package com.sf.energy.statistics.servlet;

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
import com.sf.energy.statistics.dao.AmHelper;
import com.sf.energy.statistics.dao.AmHelperImpl;
import com.sf.energy.statistics.model.ArcAmDetailData;

public class ParAmDetailServlet extends HttpServlet {
	
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
		
		List<Department> par_list = null;//所有部门的list
		String par_name = null; //部门名称
		List<ArcAmDetailData>  	ParAmDetail_result = null;
		List<ArcAmDetailData>	maplist = null; //存放map里面的value集合 
		
		Gson gson_ParAmDetail = new Gson();
		DepartmentDao dpDao = new DepartmentDao();
		AmHelperImpl  rhi_ParAmDetail = new AmHelperImpl();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer.parseInt(session.getAttribute("userId")
					.toString());
		}
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
		
		//查出所有的部门ID
		try {
			par_list = dpDao.display();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		if(Partment_ID == -1||Partment_ID == 0)//选了所有部门的情况
		{
			////System.out.println("全校");
			int Flag = 1;
			
			String order = " order by " + request.getParameter("sortName") + " " + request.getParameter("order");
			order+= ",ammeter_name";
			Map<String,List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String,List<ArcAmDetailData>>();//存放对象数据的map
				try{
					ParAmDetail_result = rhi_ParAmDetail.getAmDtailAllPartment(thePageCount, thePageIndex, st, et,order,userID);
					totalCount = rhi_ParAmDetail.getTotalCount();
					ArcAmDetailData amDetailData = null;
					
					for(int i=0;i<ParAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ParAmDetail_result.get(i);
						maplist = new ArrayList<ArcAmDetailData>();
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
					/*	Object obj[] = ParAmDetail_map.get(amDetailData.getPartmentName());
						if( obj == null)
						{
							maplist = new ArrayList<ArcAmDetailData>();
							obj = new Object[]{maplist,0.0f,0.0f};
							ParAmDetail_map.put(amDetailData.getPartmentName(),obj);	
						}
						maplist.add(amDetailData);*/
						
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
			order+= ",ammeter_name";
			Map<String,List<ArcAmDetailData>> ParAmDetail_map = new HashMap<String,List<ArcAmDetailData>>();//存放对象数据的map
				try{
					ParAmDetail_result = rhi_ParAmDetail.getAmDetailBetweenByPartment(thePageCount, thePageIndex, Partment_ID, st, et, order);
					totalCount = rhi_ParAmDetail.getTotalCount();
					ArcAmDetailData amDetailData = null;
					
					for(int i=0;i<ParAmDetail_result.size();i++)
					{
						float istotalAm = 0;		//纳入计量
						float nototalAm = 0;		//非纳入计量
						
						amDetailData = ParAmDetail_result.get(i);
						maplist = new ArrayList<ArcAmDetailData>();
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

