package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.EstimateDao;
import com.sf.energy.expert.model.EstimateModel;

public class ScoreReportServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

			try {
			if("getScoreReport".equalsIgnoreCase(request.getParameter("method")))	//删除操作
			{
				getScoreReport(request,response);
			}
			
			//考评向导里面的报表
			if("getGuideScoreReport".equalsIgnoreCase(request.getParameter("method")))	//删除操作
			{
				getGuideScoreReport(request,response);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if("deleteSomeScoreReport".equalsIgnoreCase(request.getParameter("method")))	//删除操作
			{
				deleteSomeScoreReport(request,response);
			}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	private	 void getScoreReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		int Flag = -1;	//标记是否全校（1是，0不是）
		int year = 0; //查询的年份
		int style = 0;//对象类型
		String Index_id = "";	  //对象ID
		
		int thePageCount = Integer.parseInt(request.getParameter("thePageCount"));
		int thePageIndex = Integer.parseInt(request.getParameter("thePageIndex"));
		
		if(request.getParameter("year")!=null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("style")!=null)
		{
			style = Integer.parseInt(request.getParameter("style"));
		}
		if(request.getParameter("Index_id")!=null)
		{
			Index_id = request.getParameter("Index_id");
		}
		//System.out.println(year);
		//System.out.println(style);
		//System.out.println(Index_id);
		
		EstimateDao   estd = new EstimateDao();
		EstimateModel estm = null;
		List<EstimateModel>	list = null;
		
		if(style == 0)		//选了全校情形
		{
			Flag = 1;
			int totalCount = 0;
			list = estd.getSchoolTargetInfo(year,thePageCount,thePageIndex);
			totalCount = estd.getTotal();
			
			String scoredata = gson.toJson(new Object[]{Flag,list,totalCount});
			//System.out.println("数据:"+scoredata);
			out.println(scoredata);
		}
		else
		{
			Flag = 0;
			int totalCount = 1;
			estm = estd.getTargetInfo(year, style, Index_id);
			
			
			String scoredata = gson.toJson(new Object[]{Flag,estm,totalCount});
			//System.out.println("aaa:"+scoredata);
			out.println(scoredata);
		}
	
		out.flush();
		out.close();
	}
	
	
	private	 void getGuideScoreReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		
		int year = 0; //查询的年份
		int style = 0;//对象类型
		String Index_id = "";	  //对象ID
		
		
		if(request.getParameter("year")!=null)
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("style")!=null)
		{
			style = Integer.parseInt(request.getParameter("style"));
		}
		if(request.getParameter("Index_id")!=null)
		{
			Index_id = request.getParameter("Index_id");
		}
		//System.out.println(year);
		//System.out.println(style);
		//System.out.println(Index_id);
		
		EstimateDao   estd = new EstimateDao();
		EstimateModel estm = null;
		
		estm = estd.getTargetInfo(year, style, Index_id);
			
		String scoredata = gson.toJson(estm);
		//System.out.println("aaa:"+scoredata);
		out.println(scoredata);
		
	
		out.flush();
		out.close();
	}
	
	
	
	
	private  void deleteSomeScoreReport(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		
		EstimateDao   estd = new EstimateDao();
		
		boolean b=false;
		String idList="";
		
		if(request.getParameter("theIDList")!=null)
		{
			idList=request.getParameter("theIDList");
		}
		
		String[] list = idList.split(" ");
		
		if(list.length!=0 && !list[0].equals(""))
		{
			for (String id : list)
			{
				try
				{
					b=estd.delete(Integer.parseInt(id));
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		//System.out.println("看看"+b);
		out.println(b);
		out.flush();
		out.close();
		
	}

}
