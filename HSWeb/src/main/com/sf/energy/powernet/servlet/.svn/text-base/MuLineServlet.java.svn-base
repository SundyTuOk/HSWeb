package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.ocsp.Request;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.dao.LoseLineDao;
import com.sf.energy.powernet.dao.MuLineDao;

public class MuLineServlet extends HttpServlet
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doGet(req, resp);
	}
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
//		//System.out.println("method:" + method);

		if ("getDataInfo".equals(method))
		{
			getDataInfo(req, resp);
			
		}
		else if ("BindMuLineLine".equals(method))
		{
			BindMuLineLine(req, resp);
			
		}
	
		
	}
	  
	private void BindMuLineLine(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		JSONArray json=new JSONArray();

		 String thelastTimeList =   req.getParameter(   "thelastTimeList"   ); 
		 String DYMuLineLineList =   req.getParameter(   "DYMuLineLineList"   ); 
		    
		MuLineDao control=new MuLineDao();

		//获取10KV母线（配电房电压等级为10kv 母线）
		//control.BindGYMuLinePDData(json,GYMuLineLineList);
		//获取低压配电房数据
		control.BindDYMuLinePDData(json,DYMuLineLineList,thelastTimeList);

		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	
	private void getDataInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		JSONArray json=new JSONArray();
		
		MuLineDao control=new MuLineDao();
		//获取10KV母线（配电房电压等级为10kv 母线）
		//control.getGYMuLinePDRoomInfo(json);
		//获取低压配电房数据
		control.getDYMuLinePDRoomInfo(json);

		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	
	
}
