package com.sf.energy.expert.manual;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.dao.ManualMonthDao;
import com.sf.energy.expert.model.ManualMonthModel;
import com.sf.energy.project.right.dao.UsersDao;

public class ManualManagerServlet extends HttpServlet
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
		if("getAllManualEnergy".equalsIgnoreCase(request.getParameter("method")))
		{
			getAllManualEnergy(request,response);
		}
		if("addManualEnergy".equalsIgnoreCase(request.getParameter("method")))
		{
			addManualEnergy(request,response);
		}
		if("editManualEnergy".equalsIgnoreCase(request.getParameter("method")))
		{
			editManualEnergy(request,response);
		}
		
		if("deleteSomeManualEnergy".equalsIgnoreCase(request.getParameter("method")))
		{
			deleteSomeManualEnergy(request,response);
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

	public void getAllManualEnergy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		//SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
		ManualMonthDao manualDao=new ManualMonthDao();
		ArrayList<ManualMonthModel> manualList=null;
		ManualMonthModel manualModel=null;
		
		Integer thePageCount = Integer.parseInt(request
				.getParameter("ManualEnergyPageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("ManualEnergyPageIndex"));
		int flag=Integer.parseInt(request.getParameter("flag"));
		String targetID=request.getParameter("targetID");
		
		String sortName="Manual_ID";
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
		
		manualList=new ArrayList<ManualMonthModel>();
		try
		{
			manualList=manualDao.listAllData(thePageCount, thePageIndex,sortName,order,flag,targetID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", manualDao.getTotal());
		json.add(jo);
		
		for (int i = 0; i < manualList.size(); i++)
		{
			jo = new JSONObject(); 
			manualModel=manualList.get(i);
			jo.put("manualID", manualModel.getManualID());
			jo.put("selectYear", manualModel.getSelectYear());
			jo.put("selectMonth", manualModel.getSelectMonth());
			jo.put("insertTime", manualModel.getInsertTime());
			jo.put("manualManName", manualModel.getManualManName());
			jo.put("archID", manualModel.getArchID());
			jo.put("archName", manualModel.getArchName());
			jo.put("meiValue", manualModel.getMeiValue());
			jo.put("qiyouValue", manualModel.getQiyouValue());
			jo.put("meiyouValue", manualModel.getMeiyouValue());
			jo.put("chaiyouValue", manualModel.getCaiyouValue());
			jo.put("otherValue", manualModel.getOtherValue());
			jo.put("yehuashiyouqiValue", manualModel.getYehuashiyouqiValue());
			jo.put("rengongmeiqiValue", manualModel.getRengongmeiqiValue());
			json.add(jo);
		}
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		////System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
	
	@SuppressWarnings("deprecation")
	public void addManualEnergy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		ManualMonthDao manualDao=new ManualMonthDao();
		
		ManualMonthModel manualModel=null;
		UsersDao ud=new UsersDao();
		int queryYear=0;
		int queryMonth=0;
		int archID=0;
		float meiValue=0;
		float qiyouValue=0;
		float meiyouValue=0;
		float chaiyouValue=0;
		String manualManName=" ";
		boolean b=false;
		String insertTime=new Date().toLocaleString();
		HttpSession session=request.getSession();
		String loginName=(String) session.getAttribute("userName");
		try
		{
			manualManName=ud.queryByName(loginName).getUsersName();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(request.getParameter("queryYear")!=null)
		{
			queryYear=Integer.parseInt(request.getParameter("queryYear")); 
		}
		if(request.getParameter("queryMonth")!=null)
		{
			queryMonth=Integer.parseInt(request.getParameter("queryMonth")); 
		}
		if(request.getParameter("archID")!=null)
		{
			archID=Integer.parseInt(request.getParameter("archID")); 
		}
		if(request.getParameter("7")!=null)
		{
			meiValue=Float.parseFloat(request.getParameter("7"));
		}
		if(request.getParameter("10")!=null)
		{
			qiyouValue=Float.parseFloat(request.getParameter("10"));
		}
		if(request.getParameter("11")!=null)
		{
			meiyouValue=Float.parseFloat(request.getParameter("11"));
		}
		if(request.getParameter("12")!=null)
		{
			chaiyouValue=Float.parseFloat(request.getParameter("12"));
		}
		manualModel=new ManualMonthModel();
		manualModel.setArchID(archID);
		manualModel.setSelectYear(queryYear);
		manualModel.setSelectMonth(queryMonth);
		manualModel.setInsertTime(insertTime);
		manualModel.setManualManName(manualManName);
		manualModel.setMeiValue(meiValue);
		manualModel.setQiyouValue(qiyouValue);
		manualModel.setMeiyouValue(meiyouValue);
		manualModel.setCaiyouValue(chaiyouValue);
		
		try
		{
			b=manualDao.insert(manualModel);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		////System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void editManualEnergy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		ManualMonthDao manualDao=new ManualMonthDao();
		
		ManualMonthModel manualModel=null;
		UsersDao ud=new UsersDao();
		int queryYear=0;
		int queryMonth=0;
		int archID=0;
		int manualID=0;
		float meiValue=0;
		float qiyouValue=0;
		float meiyouValue=0;
		float chaiyouValue=0;
		String manualManName=" ";
		boolean b=false;
		String insertTime=new Date().toLocaleString();
		HttpSession session=request.getSession();
		String loginName=(String) session.getAttribute("userName");
		try
		{
			manualManName=ud.queryByName(loginName).getUsersName();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(request.getParameter("manualID")!=null)
		{
			manualID=Integer.parseInt(request.getParameter("manualID")); 
		}
		
		if(request.getParameter("queryYear")!=null)
		{
			queryYear=Integer.parseInt(request.getParameter("queryYear")); 
		}
		if(request.getParameter("queryMonth")!=null)
		{
			queryMonth=Integer.parseInt(request.getParameter("queryMonth")); 	
		}
		if(request.getParameter("archID")!=null)
		{
			archID=Integer.parseInt(request.getParameter("archID")); 
		}
		if(request.getParameter("7")!=null)
		{
			meiValue=Float.parseFloat(request.getParameter("7"));
		}
		if(request.getParameter("10")!=null)
		{
			qiyouValue=Float.parseFloat(request.getParameter("10"));
		}
		if(request.getParameter("11")!=null)
		{
			meiyouValue=Float.parseFloat(request.getParameter("11"));
		}
		if(request.getParameter("12")!=null)
		{
			chaiyouValue=Float.parseFloat(request.getParameter("12"));
		}
		manualModel=new ManualMonthModel();
		manualModel.setManualID(manualID);
		manualModel.setArchID(archID);
		manualModel.setSelectYear(queryYear);
		manualModel.setSelectMonth(queryMonth);
		manualModel.setInsertTime(insertTime);
		manualModel.setManualManName(manualManName);
		manualModel.setMeiValue(meiValue);
		manualModel.setQiyouValue(qiyouValue);
		manualModel.setMeiyouValue(meiyouValue);
		manualModel.setCaiyouValue(chaiyouValue);
		
		try
		{
			b=manualDao.update(manualModel);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		////System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}
	
	public void deleteSomeManualEnergy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		ManualMonthDao manualDao=new ManualMonthDao();
		
		boolean b=false;
		String idList="";
		if(request.getParameter("theIDList")!=null)
		{
			idList=request.getParameter("theIDList");
		}
		String[] list = idList.split(" ");
		for (String id : list)
		{
			try
			{
				b=manualDao.delete(Integer.parseInt(id));
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
		PrintWriter out = response.getWriter();
		////System.out.println(b);
		out.println(b);
		out.flush();
		out.close();
	}
}
