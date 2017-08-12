package com.sf.energy.project.run.servlet;

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

import com.sf.energy.project.right.model.UsersModel;
import com.sf.energy.project.run.dao.LogInfoDao;
import com.sf.energy.project.run.model.LogInfoModel;

public class SystemLogServlet extends HttpServlet
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if("getAllSystemLog".equalsIgnoreCase(request.getParameter("method")))
		{
			getAllSystemLog(request,response);
		}
		
		if("deleteSystemLog".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSystemLog(request,response);
		}
		
		if("deleteSomeLog".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSomeLog(request,response);
		}
		
		if("searchLog".equalsIgnoreCase(request.getParameter("method")))
		{
			searchLog(request,response);
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		this.doGet(request, response);
	}

	public void getAllSystemLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String sortName="SystemLog_ID";
        String order="asc";
        
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
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("pageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("pageIndex"));
		
		ArrayList<LogInfoModel> logList=new ArrayList<LogInfoModel>();
		LogInfoDao logDao=new LogInfoDao();
		try
		{
			logList=logDao.listLogInfo(thePageCount,thePageIndex,sortName,order);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", logDao.getTotalCount());
		json.add(jo);
		for (LogInfoModel logModel : logList)
		{
			jo = new JSONObject();
			jo.put("SystemLogID", logModel.getSystemLogID());
			jo.put("ModuleName", logModel.getModuleName());
			jo.put("OperationKeyWord", logModel.getOperationKeyWord());
			jo.put("OperationResult", logModel.getOperationResultString());
			jo.put("LogMessage", logModel.getLogMessage());
			jo.put("OperationTime", logModel.getOperationTime());
			jo.put("UserName", logModel.getUserName());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/x-json");
		out.println(json.toString());
		out.flush();
		out.close();
	
	}
	
	
	public void deleteSystemLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int SystemLogID=Integer.parseInt(request.getParameter("SystemLogID"));
		////System.out.println("delete SystemLogID: "+SystemLogID);
		LogInfoDao logDao=new LogInfoDao();
		String resultInfo="success";
		try
		{
			if(logDao.deleteSystemLog(SystemLogID))
			{
				resultInfo="success";
			}
			else 
			{
				resultInfo="failed";
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/x-json");
		
		out.println(resultInfo);
		out.flush();
		out.close();
		
		
	}
	
	public void deleteSomeLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String idList = request.getParameter("IDList");
		////System.out.println("idList:"+idList);
		String[] list = idList.split(" ");
		LogInfoDao logDao=new LogInfoDao();
		String resultInfo="success";
		int ID=0;
		for (String id : list)
		{
			ID = Integer.parseInt(id);
			try
			{
				logDao.deleteSystemLog(ID);
			} catch (SQLException e)
			{
				e.printStackTrace();
				resultInfo="failed";
			}
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/x-json");
		out.println(resultInfo);
		out.flush();
		out.close();
		

	}
	
	public void searchLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("pageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("pageIndex"));
		
		String searchKeyWords = request.getParameter("searchKeyWords");
		ArrayList<LogInfoModel> logList=new ArrayList<LogInfoModel>();
		LogInfoDao logDao=new LogInfoDao();
		try
		{
			logList=logDao.searchLogInfo(searchKeyWords,thePageCount,thePageIndex);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", logDao.getTotalCount());
		json.add(jo);
		for (LogInfoModel logModel : logList)
		{
			jo = new JSONObject();
			jo.put("SystemLogID", logModel.getSystemLogID());
			jo.put("ModuleName", logModel.getModuleName());
			jo.put("OperationKeyWord", logModel.getOperationKeyWord());
			jo.put("OperationResult", logModel.getOperationResultString());
			jo.put("LogMessage", logModel.getLogMessage());
			jo.put("OperationTime", logModel.getOperationTime());
			jo.put("UserName", logModel.getUserName());
			json.add(jo);
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/x-json");
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	
	
	
}
