package com.sf.energy.sms.servlet;

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

import com.sf.energy.sms.dao.SMSWillDao;
import com.sf.energy.sms.model.SMSWillModel;

public class AbandonedMessageServlet extends HttpServlet
{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String method = "";
		
		method = request.getParameter("method");
		//System.out.println(method);
		if("getAbandonedMessageContent".equalsIgnoreCase(method))
		{
		
			try
			{
				getAbandonedMessageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("deleteAbandonedMessage".equalsIgnoreCase(method))
		{
			try
			{
				deleteAbandonedMessage(request,response);
			} catch (NumberFormatException | SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	public void getAbandonedMessageContent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		
		PrintWriter out = response.getWriter();
		int thePageCount = 0;
		int thePageIndex = 0;
		String order = "";
		String sortName = "";
		
		SMSWillModel smsw = new SMSWillModel();
		SMSWillDao	 smswDao = new SMSWillDao();
		
		ArrayList<SMSWillModel>  arrlist = new ArrayList<SMSWillModel>();
		
		if(request.getParameter("SMSAbandonedPageCount") != null)
		{
			thePageCount = Integer.parseInt(request.getParameter("SMSAbandonedPageCount"));
		}
		if(request.getParameter("SMSAbandonedPageIndex") != null)
		{
			thePageIndex = Integer.parseInt(request.getParameter("SMSAbandonedPageIndex"));
		}
		
		order = request.getParameter("order");
		sortName = request.getParameter("sortName");
		
		arrlist = smswDao.listAllAbandonedSMS(sortName, order, thePageCount, thePageIndex);
		
		JSONArray  json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		jo.put("totalCount", smswDao.getTotal());
		json.add(jo);
		for(int i=0;i<arrlist.size();i++)
		{
			jo = new JSONObject();
			jo.put("phoneList", arrlist.get(i).getPhoneList());
			jo.put("content", arrlist.get(i).getContent());
			jo.put("userName", arrlist.get(i).getUserName());
			jo.put("insertTime", arrlist.get(i).getInsertTime());
			jo.put("willID", arrlist.get(i).getWillID());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");
		//System.out.println("Data"+json);			
		out.println(json);
		out.flush();
		out.close();
	}
	
	public void deleteAbandonedMessage(HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		boolean b=false;
		String idList="";
		
		SMSWillDao	 smswDao = new SMSWillDao();
		
		/*System.out.println("卡卡"+request.getParameter("theWillIDList"));*/
		if(request.getParameter("theWillIDList")!= null)
		{
			//System.out.println("123");
			idList=request.getParameter("theWillIDList");
		}
		
		String[] list = idList.split(" ");
		
		for(String id:list)
		{
				b = smswDao.deleteAbandonedMessage(Integer.parseInt(id));			
		}
		
		PrintWriter out = response.getWriter();
		out.print(b);
		out.flush();
		out.close();	
	}

	
}
