package com.sf.energy.login;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.util.MD5Encryption;

public class LoginCheck extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String userName =""; 
		String passWord ="";
		String checkCode ="";
		String checkCodeExpected=(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String savePassword="";
		if(request.getParameter("userName")!=null)
		{
			userName=request.getParameter("userName").trim();
		}
		if(request.getParameter("passWord")!=null)
		{
			passWord= MD5Encryption.MD5(request.getParameter("passWord").trim());
		}
		if(request.getParameter("checkCode")!=null)
		{
			checkCode=request.getParameter("checkCode").trim();
		}
		
		if(request.getParameter("savePassword")!=null)
		{
			savePassword=request.getParameter("savePassword");// checked，没勾上为null
		}
		
		////System.out.println("time" + new Date().toLocaleString());
		////System.out.println("userName:" + userName);
		////System.out.println("passWord:" + passWord);
		////System.out.println("checkCodeInput:" + checkCode);
		////System.out.println("checkCodeExpected:" + checkCodeExpected);
		////System.out.println("isSavePassword:" + savePassword);
		
		HttpSession session = request.getSession();
		int result=this.checkUser(userName, passWord, checkCode, checkCodeExpected);
		
		////System.out.println("1代表用户名为空，2代表密码为空，3代表验证码为空,4代表用户名不存在，5代表密码不正确，6代表验证码不正确，7匹配成功: "+result);
		if (result==1)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "登录名不能为空！");
			response.sendRedirect("Login.jsp");
		}
		else if(result==2)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "密码不能为空！");
			response.sendRedirect("Login.jsp");
		}
		else if(result==3)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "验证码不能为空！");
			response.sendRedirect("Login.jsp");
		}
		else if(result==4)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "用户名不存在！");
			response.sendRedirect("Login.jsp");
		}
		else if(result==5)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "密码不正确！");
			response.sendRedirect("Login.jsp");
		}
		else if(result==6)
		{
			////System.out.println("login failed!");
			session.setAttribute("loginErrorInfo", "验证码不正确！");
			response.sendRedirect("Login.jsp");
		}
		else//匹配成功 
		{
			////System.out.println(userName + " login success at "+ new Date().toLocaleString());
			// 用户信息存入session
			session.setAttribute("userName", userName);
			// 通过用户名获取用户ID
			UsersDao ud = new UsersDao();
			UsersModel um = new UsersModel();
			try
			{
				um = ud.queryByName(userName);
			} catch (SQLException e)
			{
				e.printStackTrace();
				////System.out.println("login failed!");
				session.setAttribute("loginErrorInfo", "登录失败！！！");
				response.sendRedirect("Login.jsp");
			}
			String realName = "";
			try
			{
				realName=ud.queryByName(userName).getUsersName();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
				e1.printStackTrace();
				////System.out.println("login failed!");
				session.setAttribute("loginErrorInfo", "登录失败！！！");
				response.sendRedirect("Login.jsp");
			}
			session.setAttribute("userId", um.getUsersID());
			session.setAttribute("realName", realName);
			session.setMaxInactiveInterval(30*60);// 30minutes

			// 保存cookie
			if ("checked".equalsIgnoreCase(savePassword))
			{
				// Cookie cookie=new Cookie(userName, value);
			}

			////System.out.println("execute after show index.html successfully!");

			String loginIP = this.getRemoteAddress(request);
			String loginTime = new Date().toLocaleString();

			////System.out.println("loginIP:" + loginIP);
			////System.out.println("loginTime:" + loginTime);

			try
			{
				ud.modifyLoginInfo(loginIP, loginTime, 1, userName);
				////System.out.println("插入登录时间和登录IP成功");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

			// 登陆成功跳转到首页
			if (session.getAttribute("request_uri") == null)
			{
				response.sendRedirect("welcome.html");
			}	
			else
			{
				response.sendRedirect(session.getAttribute("request_uri").toString());
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		this.doGet(request, response);
	}

	/**
	 * 登录验证
	 * 1代表用户名为空，2代表密码为空，3代表验证码为空,4代表用户名不存在，5代表密码不正确，6代表验证码不正确，7匹配成功
	 * @param userName
	 * @param passWord
	 * @param checkCode
	 * @param checkCodeExpected
	 * @return 
	 */
	private int checkUser(String userName, String passWord,String checkCode, String checkCodeExpected)
	{
		int b = 0;
		UsersDao ud = new UsersDao();
		UsersModel um = null;
	//	System.out.println(userName+" | "+passWord+" | "+checkCode+" | "+checkCodeExpected);
		if (userName == null || "".equals(userName) )
		{
			b=1;//用户名为空
		}
		else if(passWord==null || "".equals(passWord) )
		{
			b=2;//密码为空
		}
		else if( checkCode==null || "".equals(checkCode))
		{
			b=3;//验证码为空
		}
		else //都不为空，继续匹配
		{
			try
			{
				um = ud.queryByName(userName);
			} catch (SQLException e)
			{
				b=4;// 用户名不存在
				return 4;
			}
			if (um == null)
			{
				b=4;// 用户名不存在
			}
			else if(!um.getUsersPassword().equals(passWord))
			{
				b=5;//密码不正确
			}
			else if(!checkCodeExpected.equalsIgnoreCase(checkCode))
			{
				b=6;//验证码不正确
			}
			else 
			{
				b=7;//匹配成功
			}
		}			
		return b;
	}

	/**
	 * 获取登录用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getRemoteAddress(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
		{
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.equalsIgnoreCase("0:0:0:0:0:0:0:1"))
		{
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**
	 * 获取登录用户的MAC地址
	 * 
	 * @param ip
	 * @return
	 */
	public String getMACAddress(String ip)
	{
		String str = "";
		String macAddress = "";
		try
		{
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++)
			{
				str = input.readLine();
				if (str != null)
				{
					if (str.indexOf("MAC Address") > 1)
					{
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

}
