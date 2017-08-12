package com.sf.energy.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter extends HttpServlet implements javax.servlet.Filter
{

	private FilterConfig config;
	private String login_page;
	private String home_page;

	/**
	 * Constructor of the object.
	 */
	public LoginFilter()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		config = null;
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException
	{
		// Put your code here

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
//		System.out.println("MyFilter Run!");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rpo = (HttpServletResponse) response;

		// 获取session
		HttpSession session = req.getSession();
		req.setCharacterEncoding("utf-8");
		rpo.setCharacterEncoding("utf-8");

		String userName = (String) session.getAttribute("userName");
//		System.out.println("MyFilter: " + userName);
		String request_uri = req.getRequestURI();// 得到用户请求的URI
		String ctxPath = req.getContextPath();// 得到web应用程序的上下文路径
		String uri = request_uri.substring(ctxPath.length()); // 去除上下文路径，得到剩余部分的路径
//		System.out.println("得到用户请求的URI：" + request_uri);
//		System.out.println("得到web应用程序的上下文路径：" + ctxPath);
//		System.out.println("去除上下文路径，得到剩余部分的路径：" + uri);
		if (!loginCheck(req))
		{
			session.setAttribute("request_uri", request_uri);
			////System.out.println("session里面存的之前请求的页面地址: "+session.getAttribute("request_uri").toString());
			// 避免SVN检出时候项目名字不一样导致跳转不到登陆页面
			rpo.sendRedirect(ctxPath + login_page);
		//	System.out.println("登录页面地址：" + ctxPath + login_page);
		} else
		{
			chain.doFilter(request, response);
		}
	}

	private boolean loginCheck(HttpServletRequest request)
	{
		boolean flag = false;

		HttpSession session = request.getSession();

		if (session.getAttribute("userName") != null)
			flag = true;

		return flag;
	}

	public void init(FilterConfig filterconfig) throws ServletException
	{
		// 从部署描述符中获取登录页面和首页的URI
		config = filterconfig;
		login_page = filterconfig.getInitParameter("LOGIN_URI");
		// home_page = filterconfig.getInitParameter("HOME_URI");
		////System.out.println(home_page + login_page);
		if (null == login_page)
		{
			throw new ServletException("没有找到登录页面哦，亲！！！");
		}
	}

}
