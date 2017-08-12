package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;


public class OnlineUsersServlet extends HttpServlet
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
		
		//response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//response.setHeader("content-type", "text/html;charset=UTF-8");
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("OnlineUsersPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("OnlineUsersPageIndex"));
		
		UsersDao ud=new UsersDao();
		
		ArrayList<UsersModel> usersList=new ArrayList<UsersModel>();
		try
		{
			usersList=ud.listUsers(thePageCount, thePageIndex);
			
		} catch (SQLException e)
		{
		
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", ud.getTotal());
		json.add(jo);
		
		for (UsersModel um : usersList)
		{
			jo = new JSONObject();
			jo.put("usersNum", um.getUsersID());
			jo.put("usersName",um.getUsersName());
			jo.put("usersLoginName",um.getUsersLoginName());
			jo.put("usersPartment",um.getUsersPartmentName());
			jo.put("usersIP",um.getIP());
			jo.put("usersLastTime",um.getUsersLastTime());
			jo.put("usersOnline",um.getOnlineName());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/x-json");
		out.println(json.toString());
		out.flush();
		out.close();
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

}
