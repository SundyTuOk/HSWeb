package com.sf.energy.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sf.energy.project.right.dao.UsersDao;

public class Logout extends HttpServlet
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
		
		
		
		String isLogOut= request.getParameter("isLogOut");
		////System.out.println("IsLogOut:"+isLogOut);
		
		if("true".equalsIgnoreCase(isLogOut))
		{
			HttpSession session=request.getSession();
			
			UsersDao ud=new UsersDao();
			try
			{
				//修改表中的用户是否在现统计,改为不在线
				ud.modifyLogOutInfo(0, Integer.parseInt(session.getAttribute("userId").toString()));
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			//使session失效
			session.invalidate();
			
			PrintWriter out = response.getWriter();
		
			out.println("true");
			out.flush();
			out.close();
			//试了下面3种都不行，直接js回调函数里面强制跳到登陆页面
			//response.setHeader("refresh","1;URL=Login.jsp");	
			//request.getRequestDispatcher("Login.jsp").forward(request, response);
			//response.sendRedirect("Login.jsp");
			
			
			////System.out.println("已经安全退出！！");
			return;
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

}
