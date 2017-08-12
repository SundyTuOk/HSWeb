package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sf.energy.util.Configuration;

public class ConnectionInfoServlet extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		
		if ("checkConnectState".equals(method))
			checkConnectState(request, response);
	}

	public void checkConnectState(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		int connectState = 0;

		Socket s = null;
		XMLConfiguration config=null;
		try
		{
			config = Configuration.getConfiguration("configuration/ProjectConfiguration.xml");
		} catch (ConfigurationException e)
		{
			connectState = 0;
			System.out.println( new RuntimeException("读取配置文件出错"));			
		}
		String serverIP=config.getString("server.IP");
		String serverPort=config.getString("server.port");
		
		try
		{	
			s = new Socket(serverIP,Integer.parseInt(serverPort));
			boolean b = s.isConnected();
			if(b)
			{
				connectState = 1;
			}
		} catch (ConnectException e)
		{
			connectState = 0;
			System.out.println(new RuntimeException("链接服务器失败：服务器的IP--"+serverIP+" 端口号："+serverPort));
			
			
		}finally
		{
			if(s!=null)
				s.close();
		}
	 	
		
		PrintWriter out = response.getWriter();
		out.println(connectState);
		out.flush();
		out.close();
		
	}
}
