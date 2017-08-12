package com.sf.energy.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.sms.dao.SMSReceiveDao;
import com.sf.energy.sms.model.SMSReceiveModel;
import com.sf.energy.sms.service.ReceiveMessage;

public class ReceiveMessageServlet extends HttpServlet
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
		
		if("getAllReceiveMeaasge".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				getAllReceiveMeaasge(request,response);
			} catch (ParseException e)
			{
				
				e.printStackTrace();
			}
		}
		
		if("deleteSomeReceiveMessage".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSomeReceiveMessage(request,response);
		}
		if("doItNowReceiveMessage".equalsIgnoreCase(request.getParameter("method")))
		{
			doItNowReceiveMessage(request,response);
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
	
	public void getAllReceiveMeaasge(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException
	{
		SMSReceiveModel sms=new SMSReceiveModel();
		SMSReceiveDao smsDao=new SMSReceiveDao();

		String sortName="sendTime";
	    String order="desc";
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("SMSPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("SMSPageIndex"));
		
		if(request.getParameter("sortName")!=null)
	    {
	        sortName=request.getParameter("sortName");
	    }
	    if(request.getParameter("order")!=null)
	    {
        	if(request.getParameter("order").equals("desc"))
        	{
        		order="desc";
        	}
        	else if(request.getParameter("order").equals("asc"))
        	{
        		order="asc";
			}
	        	
	    }	
		
		ArrayList<SMSReceiveModel> list=new ArrayList<SMSReceiveModel>();
		try
		{
			list=smsDao.listAllReceiveSMS(sortName,order,thePageCount, thePageIndex);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", smsDao.getTotal());
		json.add(jo);
		
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			sms=list.get(i);
			jo.put("receiveID", sms.getReceiveID());
			jo.put("sendPhone",sms.getSendPhone() );
			jo.put("sendTime",sms.getSendTime());
			jo.put("content",sms.getContent() );
			jo.put("receiveMan",sms.getReceiveManName());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	public void deleteSomeReceiveMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		boolean b=false;
		String idList="";
		SMSReceiveDao smsDao=new SMSReceiveDao();
		if(request.getParameter("theIDList")!=null)
		{
			idList=request.getParameter("theIDList");
		}
		
		String[] list = idList.split(" ");
		for (String id : list)
		{
			try
			{
				b=smsDao.delete(Integer.parseInt(id));
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
		
		PrintWriter out = response.getWriter();
		//System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}

	
	public void doItNowReceiveMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		boolean b=false;
		//执行一次收短信,并存到数据库
		ReceiveMessage app=new ReceiveMessage();
		
		try
		{
			b=app.receiveMegAndToTable(request, response);
		} catch (SQLException e1)
		{
			
			e1.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		//System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}
}
