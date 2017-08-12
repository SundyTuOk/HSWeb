package com.sf.energy.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sf.energy.sms.service.SendMessage;

public class SendMessageServlet extends HttpServlet
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String phoneList="";
		String content="";
		boolean isSeccuss=false;
		if(request.getParameter("phoneList")!=null)
		{
			phoneList=request.getParameter("phoneList").trim();
		}
		if(request.getParameter("content")!=null)
		{
			content=request.getParameter("content").trim();
		}
		//System.out.println("号码:"+phoneList);
		//System.out.println("内容:"+content);
		
		SendMessage app=new SendMessage();
		try
		{
			isSeccuss=app.sendMegAndToTable(phoneList,content,request,response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println(isSeccuss);
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
