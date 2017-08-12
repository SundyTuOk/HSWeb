package com.sf.energy.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.ModuleDao;
import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.service.ListUsersModule;
import com.sf.energy.project.right.serviceImpl.ListUsersModuleImpl;

public class ListIndexModuleServlet extends HttpServlet
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
		
		String method = request.getParameter("method");
		
		if ("listAllParentModule".equals(method))
		{
			listAllParentModule(request, response);
		}
		if ("showParentModuleOrNot".equals(method))
		{
			showParentModuleOrNot(request, response);
		}
		if ("hideParentModule".equals(method))
		{
			hideParentModule(request, response);
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

	public void showParentModuleOrNot(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String chickItemName="";
		if(request.getParameter("chickItem")!=null)
		{
			chickItemName=request.getParameter("chickItem");
		}
	//	System.out.println(chickItemName);
		ListUsersModule lum=new ListUsersModuleImpl();
		HttpSession session=request.getSession();
		String userName=null;
		String userId=null;
		String url="";
		
		if(session.getAttribute("userId")==null && session.getAttribute("userName")==null)//没有登录
		{
			try
			{
				url=lum.getUsersModuleURLByName(chickItemName);
				//存入之前请求的路径
				session.setAttribute("request_uri",url);
				session.setAttribute("hasPower","NO");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("NoUsers","true");
			json.add(jo);
			
			////System.out.println(json.toString());
			
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();	
		}
		else //已经登陆
		{		
			userId=session.getAttribute("userId").toString();
			userName=session.getAttribute("userName").toString();
			ArrayList<String> nameList=null;
			
			try
			{
				//新加了一个方法只返回名字
				nameList=lum.getUsersRootModuleNameByID(Integer.parseInt(userId));
				
				url=lum.getUsersModuleURLByName(chickItemName);
				
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			//组装json
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("NoUsers","false");
			jo.put("containsOrNot",nameList.contains(chickItemName));
			//jo.put("containsOrNot",true);
			jo.put("chickItemName", chickItemName);
			jo.put("URL",url);
			json.add(jo);
			
		//	System.out.println(json.toString());
			
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}
		
	}
	public void hideParentModule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String currentItem="";
		if(request.getParameter("currentItem")!=null)
		{
			currentItem=request.getParameter("currentItem");
		}
		//	System.out.println(chickItemName);
		ListUsersModule lum=new ListUsersModuleImpl();
		HttpSession session=request.getSession();
		String userName=null;
		String userId=null;
		String url="";
//		
//		if(session.getAttribute("userId")==null && session.getAttribute("userName")==null)//没有登录
//		{
//			try
//			{
//				url=lum.getUsersModuleURLByName(currentItem);
//				//存入之前请求的路径
//				session.setAttribute("request_uri",url);
//				session.setAttribute("hasPower","NO");
//			} catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//			
//			JSONArray json = new JSONArray();
//			JSONObject jo = new JSONObject();
//			jo.put("NoUsers","true");
//			json.add(jo);
//			
//			////System.out.println(json.toString());
//			
//			response.setContentType("application/x-json");
//			PrintWriter out = response.getWriter();
//			out.println(json.toString());
//			out.flush();
//			out.close();	
//		}
//		else //已经登陆
//		{		
			userId=session.getAttribute("userId").toString();
			userName=session.getAttribute("userName").toString();
			ArrayList<String> nameList=null;
			
			try
			{
				//新加了一个方法只返回名字
				nameList=lum.getUsersRootModuleNameByID(Integer.parseInt(userId));
				
				url=lum.getUsersModuleURLByName(currentItem);
				
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			//组装json
			JSONArray json = new JSONArray();
			JSONObject jo = new JSONObject();
			jo.put("NoUsers","false");
			jo.put("containsOrNot",nameList.contains(currentItem));
			//jo.put("containsOrNot",true);
			jo.put("chickItemName", currentItem);
			jo.put("URL",url);
			json.add(jo);
			
			//	System.out.println(json.toString());
			
			response.setContentType("application/x-json");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
//		}
		
	}
	
	public void listAllParentModule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		ModuleDao md = new ModuleDao();
		List<ModuleModel> list=new ArrayList<ModuleModel>();
		try
		{
			list = md.listAllParentModule();
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		JSONArray json = new JSONArray();
		for (int i = 0; i <list.size(); i++)
		{
			ModuleModel n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Module_ID", n.getModuleID());
			jo.put("Module_Name", n.getModuleName());
			jo.put("Module_WindowName", n.getModuleAddress());
			jo.put("Module_ParentModuleID", n.getModuleParent());
			jo.put("Module_Num", n.getModuleNum());
			jo.put("Module_Mark", n.getModuleMark());
			jo.put("Module_Part1", n.getModulePart1());
			jo.put("Module_Order", n.getModuleOrder());
			jo.put("Module_Remark", n.getModuleRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
}
