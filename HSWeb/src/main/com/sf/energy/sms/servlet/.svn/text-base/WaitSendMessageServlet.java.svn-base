package com.sf.energy.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.sms.dao.SMSWillDao;
import com.sf.energy.sms.model.SMSWillModel;
import com.sf.energy.sms.service.SendMessage;

public class WaitSendMessageServlet extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String method = "";
		
		if(request.getParameter("method") != null)
		{
			method = request.getParameter("method");
		}
		
		if("getWaitSendMessageContent".equalsIgnoreCase(method))
		{
			try
			{
				getWaitSendMessageContent(request,response);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("deleteSendedMessage".equalsIgnoreCase(method))
		{
				try
				{
					deleteSendedMessage(request,response);
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		//重发
		if("SendMessageAgain".equalsIgnoreCase(method))
		{
			try
			{
				SendMessageAgain(request,response);
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
	
	//读取初始的内容
	public void getWaitSendMessageContent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		int thePageCount = 0;
		int thePageIndex = 0;
		String order = "";
		String sortName = "";
		
		SMSWillModel smsw = new SMSWillModel();
		SMSWillDao	 smswDao = new SMSWillDao();
		
		ArrayList<SMSWillModel>  arrlist = new ArrayList<SMSWillModel>();
		
		if(request.getParameter("SMSWaitPageCount") != null)
		{
			thePageCount = Integer.parseInt(request.getParameter("SMSWaitPageCount"));
		}
		if(request.getParameter("SMSWaitPageIndex") != null)
		{
			thePageIndex = Integer.parseInt(request.getParameter("SMSWaitPageIndex"));
		}
		
		order = request.getParameter("order");
		sortName = request.getParameter("sortName");
		
		arrlist = smswDao.listAllWaitSendSMS(sortName, order, thePageCount, thePageIndex);
		
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
					
		out.println(json);
		out.flush();
		out.close();
	}
	
	public void deleteSendedMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		boolean b=false;
		String idList="";
		
		SMSWillDao	 smswDao = new SMSWillDao();
		
		/*System.out.println("卡卡"+request.getParameter("theWillIDList"));*/
		if(request.getParameter("theWillIDList")!= null)
		{
			System.out.println("123");
			idList=request.getParameter("theWillIDList");
		}
		System.out.println("HID"+idList.toString());
		String[] list = idList.split(" ");
		System.out.println("ID"+list.toString());
		for(String id:list)
		{
				b = smswDao.deleteSendedMessage(Integer.parseInt(id));			
		}
		
		PrintWriter out = response.getWriter();
		out.print(b);
		out.flush();
		out.close();			
	}
	
	public void SendMessageAgain(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, SQLException
	{
		SMSWillDao	 smswDao = new SMSWillDao();
		SMSWillModel  smsModel = null;
		
		String idList="";
		boolean isSuccuss=false;
		SendMessage app=new SendMessage();
		if(request.getParameter("theWillIDList")!= null)
		{
			idList=request.getParameter("theWillIDList");
		}
		
//		String[] list = idList.split(" ");
//		
//		//用MAP存放对应记录是否发送成功
//		Map<Integer,String>  map = new HashMap<Integer,String>();
//		for(int i=0;i<list.length;i++)
//		{
//			smsModel = smswDao.getContentByID(Integer.parseInt(list[i]));
			smsModel = smswDao.getContentByID(Integer.parseInt(idList));
			isSuccuss = app.sendMegAndToTable(smsModel.getPhoneList(), smsModel.getContent(), request, response);
			
//			if(isSuccuss==true)
//			{
//				System.out.println("第"+(i+1)+"条信息发送成功");
//				map.put(i+1,"成功");
//			}
//			else
//			{
//				System.out.println("第"+(i+1)+"条信息发送失败");
//				map.put(i+1,"失败");
//			}
//		}
		
		PrintWriter out = response.getWriter();
//		Gson gson = new Gson();
//		String data = gson.toJson(map);
		
		//response.setContentType("application/x-json");
		//System.out.println("结果"+data);
		out.print(isSuccuss);
		out.flush();
		out.close();
	}

}
