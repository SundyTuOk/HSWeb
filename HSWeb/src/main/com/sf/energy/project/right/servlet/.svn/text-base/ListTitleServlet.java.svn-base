package com.sf.energy.project.right.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.right.service.ListUsersModule;
import com.sf.energy.project.right.serviceImpl.ListUsersModuleImpl;
import com.sf.energy.statistics.model.ReportModel;

public class ListTitleServlet extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		if("showIndexRight".equalsIgnoreCase(request.getParameter("method")))
		{
			showIndexRight(request, response);
		}
		
		if("showNameOnIndex".equalsIgnoreCase(request.getParameter("method")))
		{
			showNameOnIndex(request, response);
		}
		
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doGet(request, response);
		
	}
	
	public void showIndexRight(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String EnergyManagerItem =request.getParameter("EnergyManagerItem");
		String ProjectManager=request.getParameter("ProjectManager");
		String WaterManager=request.getParameter("WaterManager");
		String ClassLightManager=request.getParameter("ClassLightManager");
		String EnergySavingManager=request.getParameter("EnergySavingManager");
		String EnergyReportManager=request.getParameter("EnergyReportManager");
		String ElectricAutoManager=request.getParameter("ElectricAutoManager");
		String LampManager=request.getParameter("LampManager");
		
		/*//System.out.println(EnergyManagerItem);
		//System.out.println(ProjectManager);
		//System.out.println(WaterManager);
		//System.out.println(ClassLightManager);
		//System.out.println(EnergySavingManager);
		//System.out.println(EnergyReportManager);
		//System.out.println(ElectricAutoManager);
		//System.out.println(LampManager);*/
		
		ListUsersModule lum=new ListUsersModuleImpl();
		HttpSession session=request.getSession();
		String userName=null;
		String userId=null;
		if(session.getAttribute("userId")!=null && session.getAttribute("userName")!=null)
		{
				userId=session.getAttribute("userId").toString();
				userName=session.getAttribute("userName").toString();
		}
		else {		
			return;
		}
		
		////System.out.println("userId:"+userId);
		////System.out.println("userName:"+userName);
		
		ArrayList<String> arrayList=null;
		try
		{
			//新加了一个方法只返回名字
			arrayList=lum.getUsersRootModuleNameByID(Integer.parseInt(userId));
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		//System.out.println("arrayList Size:"+arrayList.size());
		
//		HashMap<Integer, String> moduleNameMap=new HashMap<Integer, String>();
//		for(int i=0;i<arrayList.size();i++)
//		{
//			moduleNameMap.put(i, arrayList.get(i).getModuleName());
//		}
//		
//		//System.out.println("moduleNameMap Size:"+moduleNameMap.size());
		
		
		
		/*//System.out.println("hasModule "+arrayList.contains(EnergyManagerItem)); 	
		//System.out.println("hasModule "+arrayList.contains(ProjectManager));
		//System.out.println("hasModule "+arrayList.contains(WaterManager));
		//System.out.println("hasModule "+arrayList.contains(ClassLightManager));
		//System.out.println("hasModule "+arrayList.contains(EnergySavingManager));
		//System.out.println("hasModule "+arrayList.contains(EnergyReportManager));
		//System.out.println("hasModule "+arrayList.contains(ElectricAutoManager));
		//System.out.println("hasModule "+arrayList.contains(LampManager));*/
		
		//将时候有模块权限添加到另一个列表准备组装json,可以不要这些代码
//		ArrayList<Boolean> hasModuleList =new ArrayList<Boolean>();
//		hasModuleList.add(arrayList.contains(EnergyManagerItem));
//		hasModuleList.add(arrayList.contains(ProjectManager));
//		hasModuleList.add(arrayList.contains(WaterManager));
//		hasModuleList.add(arrayList.contains(ClassLightManager));
//		hasModuleList.add(arrayList.contains(EnergySavingManager));
//		hasModuleList.add(arrayList.contains(EnergyReportManager));
//		hasModuleList.add(arrayList.contains(ElectricAutoManager));
//		hasModuleList.add(arrayList.contains(LampManager));
//		
//		//System.out.println(hasModuleList.size());//8
		//可以不要这些代码
		
		//组装json
		JSONArray json = new JSONArray();
		
		JSONObject jo = new JSONObject();
		jo.put("EnergyManagerItem",arrayList.contains(EnergyManagerItem));
		jo.put("ProjectManager",arrayList.contains(ProjectManager));
		jo.put("WaterManager",arrayList.contains(WaterManager));	
		jo.put("ClassLightManager",arrayList.contains(ClassLightManager));	
		jo.put("EnergySavingManager",arrayList.contains(EnergySavingManager));	
		jo.put("EnergyReportManager",arrayList.contains(EnergyReportManager));	
		jo.put("ElectricAutoManager",arrayList.contains(ElectricAutoManager));	
		jo.put("LampManager",arrayList.contains(LampManager));	
		json.add(jo);
		
		
		////System.out.println(json);
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	
	public void showNameOnIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		JSONArray json = new JSONArray();	
		JSONObject jo = new JSONObject();
		HttpSession session =request.getSession();
		//登陆成功
		if(session.getAttribute("userName")!=null)
		{
			jo.put("result", 1);
			jo.put("userName", session.getAttribute("userName"));
			jo.put("userId", session.getAttribute("userId"));
		}
		else {
			jo.put("result", 0);
		}
		
		json.add(jo);
		response.setContentType("application/x-json"); 

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
}
