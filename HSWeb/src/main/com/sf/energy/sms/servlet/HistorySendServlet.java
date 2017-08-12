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

import com.google.gson.JsonArray;
import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.sms.dao.SMSHistoryDao;
import com.sf.energy.sms.dao.SMSWillDao;
import com.sf.energy.sms.model.SMSHistoryModel;
import com.sf.energy.sms.model.SMSWillModel;
import com.sf.energy.sms.service.SendMessage;

public class HistorySendServlet extends HttpServlet
{
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
		
		if("getAllHistorySend".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				getAllHistorySend(request,response);
			} catch (ParseException e)
			{
				
				e.printStackTrace();
			}
		}
		
		if("deleteSomeHistorySend".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSomeHistorySend(request,response);
		}
		
		if("sendAgainHistorySend".equalsIgnoreCase(request.getParameter("method")))
		{
			try
			{
				sendAgainHistorySend(request,response);
			} catch (NumberFormatException | SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	
	public void sendAgainHistorySend(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		SMSWillDao	 smswDao = new SMSWillDao();
		SMSHistoryModel  smsModel = null;
		
		String idList="";
		boolean isSuccuss=false;
		SendMessage app=new SendMessage();
		if(request.getParameter("theWillIDList")!= null)
		{
			idList=request.getParameter("theWillIDList");
		}
		
		String[] list = idList.split(" ");
		for(int i=0;i<list.length;i++)
		{
			//System.out.println("id"+list[i]);
			smsModel = smswDao.getHistoryContentByID(Integer.parseInt(list[i]));
			isSuccuss = app.sendMegAndToTable(smsModel.getPhoneList(), smsModel.getContent(), request, response);
//			if(isSuccuss==true)
//			{
//				System.out.println("第"+(i+1)+"条信息发送成功");
//			}
//			else
//			{
//				System.out.println("第"+(i+1)+"条信息发送失败");
//			}
		}
		
		PrintWriter out = response.getWriter();
		out.println(isSuccuss);
		out.flush();
		out.close();
	}
	
	public void getAllHistorySend(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException
	{
		SMSHistoryModel sms=new SMSHistoryModel();
		SMSHistoryDao smsDao=new SMSHistoryDao();
	
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("SMSHistoryPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("SMSHistoryPageIndex"));
		String sortName="sendTime";
	    String order="desc";
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
		ArrayList<SMSHistoryModel> list=new ArrayList<SMSHistoryModel>();
		try
		{
			list=smsDao.listAllSendSMS(sortName,order,thePageCount, thePageIndex);
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

			jo.put("sendTime", sms.getSmsHistoryTime());
			jo.put("historyID",sms.getSmsHistoryID() );
			jo.put("phoneList",sms.getPhoneList() );
			jo.put("content",sms.getContent() );
			jo.put("sendMan",sms.getUserName());
			jo.put("state",sms.getStyleID() );
			jo.put("count",sms.getCount());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");
		//System.out.println(json.size());
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	public void deleteSomeHistorySend(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		boolean b=false;
		String idList="";
		SMSHistoryDao smsDao=new SMSHistoryDao();
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
	
}
