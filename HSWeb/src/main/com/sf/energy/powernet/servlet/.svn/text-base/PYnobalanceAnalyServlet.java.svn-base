package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.RateNoModel;
import com.sf.energy.powernet.service.PWnoBalance;
import com.sf.energy.powernet.service.PYnoBalance;

public class PYnobalanceAnalyServlet extends HttpServlet
{

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		try {
			getResultData(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		this.doGet(request, response);
	}
	private void getResultData(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		
		String  Time = null;//获取前台时间
		Time = request.getParameter("date");
		int month=Integer.parseInt(Time.substring(5,7));
		int day=Integer.parseInt(Time.substring(8,10));
		int year=Integer.parseInt(Time.substring(0,4));
		int styleID = 0;
		int partID = 0;
		System.err.println(request.getParameter("styleID"));
		if(request.getParameter("styleID") != null)
		{
			styleID = Integer.parseInt(request.getParameter("styleID"));
		}
		if(request.getParameter("partID") != null)
		{
			partID = Integer.parseInt(request.getParameter("partID"));
		}
		
		if(styleID == 0)
		{
			//System.out.println("全校");
			
			RateNoModel rnm = null;	 //不同区间不平衡率的数据点个数
			PYnoBalance  vnb = new PYnoBalance();
			ArrayList<RateNoModel>  list = new ArrayList<RateNoModel>();
			
			for (int i = 0; i < 24; i++)
			{
				rnm = vnb.CountAllRateNo(year, month, day, i);	
				list.add(rnm);
			}
			Gson  gson = new Gson();
			String  data = gson.toJson(list);
			//System.out.println(data);
			out.println(data);
		}
		else if(styleID ==1)
		{
			//System.out.println("配电房");
			
			RateNoModel rnm = null;	 //不同区间不平衡率的数据点个数
			PYnoBalance  vnb = new PYnoBalance();
			ArrayList<RateNoModel>  list = new ArrayList<RateNoModel>();
			
			for (int i = 0; i < 24; i++)
			{
				rnm = vnb.CountRateNoByParent(partID, year, month, day, i);	
				list.add(rnm);
			}
			Gson  gson = new Gson();
			String  data = gson.toJson(list);
			//System.out.println(data);
			out.println(data);
		}
		else if(styleID ==2)
		{
			//System.out.println("变压器");
			
			RateNoModel rnm = null;	 //不同区间不平衡率的数据点个数
			PYnoBalance  vnb = new PYnoBalance();
			ArrayList<RateNoModel>  list = new ArrayList<RateNoModel>();
			
			for (int i = 0; i < 24; i++)
			{
				rnm = vnb.CountRateNoByParent(partID, year, month, day, i);	
				list.add(rnm);
			}
			Gson  gson = new Gson();
			String  data = gson.toJson(list);
			//System.out.println(data);
			out.println(data);
		}
		else if(styleID ==3)
		{
			//System.out.println("回路");
		
			RateNoModel rnm = null;	 //不同区间不平衡率的数据点个数
			int Ammeter_ID = 0; //电表ID
			AmMeterPDDataDao  apDao = new AmMeterPDDataDao();
			PYnoBalance  vnb = new PYnoBalance();
			ArrayList<RateNoModel>  list = new ArrayList<RateNoModel>();
			
			for (int i = 0; i <= 23; i++) {
				Ammeter_ID = apDao.getAmmeterIDByPdPartID(partID);
				rnm = vnb.CountRateNo(Ammeter_ID, year, month, day, i);
				list.add(rnm);
			}
			
			Gson  gson = new Gson();
			String  data = gson.toJson(list);
			//System.out.println(data);
			
			out.println(data);
		}
		out.flush();
		out.close();
	}

	

}
