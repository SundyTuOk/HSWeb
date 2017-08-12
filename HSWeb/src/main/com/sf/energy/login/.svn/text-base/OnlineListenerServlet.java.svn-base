package com.sf.energy.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;

public class OnlineListenerServlet extends HttpServlet implements
		ServletContextListener, ServletContextAttributeListener,
		HttpSessionListener, HttpSessionAttributeListener
{

	private ServletContext application = null;
	private HttpSession session = null;

	public void contextInitialized(ServletContextEvent sce)
	{

		// 初始化一个application对象
		this.application = sce.getServletContext();
		// 设置一个列表属性，用于保存在线用户名
		this.application.setAttribute("online", new ArrayList());
	}

	public void contextDestroyed(ServletContextEvent sce)
	{

	}

	public void attributeAdded(ServletContextAttributeEvent scab)
	{

	}

	public void attributeRemoved(ServletContextAttributeEvent scab)
	{

	}

	public void attributeReplaced(ServletContextAttributeEvent scab)
	{

	}

	public void sessionCreated(HttpSessionEvent se)
	{

	}

	public void sessionDestroyed(HttpSessionEvent se)
	{

		// 取得用户名列表
		List online = (List) this.application.getAttribute("online");
		// 取得当前用户名
		String userName = null; 
		int userID=1;
		if(se.getSession().getAttribute("userName")!=null)
		{
			userName=(String) se.getSession().getAttribute("userName");
		}	
		
		if(se.getSession().getAttribute("userId")!=null)
		{
			userID=(Integer) se.getSession().getAttribute("userId");
		}
		//System.out.println("OnlineListenerServlet监听器-userName:"+userName);
		// 将此用户名从列表中删除
		online.remove(userName);
		// 将删除后的列表重新设置到application属性中
		this.application.setAttribute("online", online);
		
		//数据库中也要修改状态
		UsersDao ud = new UsersDao();
		//System.out.println("OnlineListenerServlet监听器-userID:"+userID);
		try
		{
			//修改表中的用户是否在现统计,改为不在线
			ud.modifyLogOutInfo(0, userID);
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}

	public void attributeAdded(HttpSessionBindingEvent se)
	{

		// 取得用户名列表
		List online = (List) this.application.getAttribute("online");
		// 将当前用户名添加到列表中
		online.add(se.getValue());
		// 将添加后的列表重新设置到application属性中
		this.application.setAttribute("online", online);
		
	}

	public void attributeRemoved(HttpSessionBindingEvent se)
	{

	}

	public void attributeReplaced(HttpSessionBindingEvent se)
	{

	}
}
