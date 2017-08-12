package com.sf.energy.co2sale.servlet;

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
import com.sf.energy.co2sale.impl.CO2SaleInfoDao;
import com.sf.energy.co2sale.model.CO2SaleInfo_SchoolHQ_Model;

public class CO2SaleInfoServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);
		if ("loadSchoolHQ".equals(method))
			loadSchoolHQ(request, response);
		if ("loadSchoolCJ".equals(method))
			loadSchoolCJ(request, response);
		if ("SaleOut".equals(method))
			saleOut(request, response);
		if ("BuyIn".equals(method))
			BuyIn(request, response);
		if ("ConfirmPayment".equals(method))
			ConfirmPayment(request, response);
		if ("deleteSaleInfo".equals(method))
			deleteSaleInfo(request, response);
		if ("AddHQ".equals(method))
			addHQ(request, response);
		if ("EditHQ".equals(method))
			EditHQ(request, response);
		
		
	}

	/**行情信息
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void loadSchoolHQ(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		List<CO2SaleInfo_SchoolHQ_Model> lists=new ArrayList<CO2SaleInfo_SchoolHQ_Model>();
		
		int partid = 0;
		if (request.getParameter("partid") != null
				&& request.getParameter("partid") != "") {
			partid = Integer.parseInt(request.getParameter("partid"));
		}
		lists=csid.loadSchoolHQ(partid);
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(lists);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	/**成交信息
	 * @param request
	 * @param response
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void loadSchoolCJ(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		List<CO2SaleInfo_SchoolHQ_Model> lists=new ArrayList<CO2SaleInfo_SchoolHQ_Model>();
		
		int partid = 0;
		if (request.getParameter("partid") != null
				&& request.getParameter("partid") != "") {
			partid = Integer.parseInt(request.getParameter("partid"));
		}
		lists=csid.loadSchoolCJ(partid);
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(lists);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	private void saleOut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int SaleInfo_id = 0;
		if (request.getParameter("SaleInfo_id") != null
				&& request.getParameter("SaleInfo_id") != "") {
			SaleInfo_id = Integer.parseInt(request.getParameter("SaleInfo_id"));
		}
		int Sale_partmentid = 0;
		if (request.getParameter("Sale_partmentid") != null
				&& request.getParameter("Sale_partmentid") != "") {
			Sale_partmentid = Integer.parseInt(request.getParameter("Sale_partmentid"));
		}
		String resultInfo="";
		
		try
		{
			String msg = csid.saleOut(SaleInfo_id,Sale_partmentid);
			if ("success".equals(msg))
			{
				resultInfo = "卖出成功，等待对方付款！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "交易失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	private void BuyIn(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int SaleInfo_id = 0;
		if (request.getParameter("SaleInfo_id") != null
				&& request.getParameter("SaleInfo_id") != "") {
			SaleInfo_id = Integer.parseInt(request.getParameter("SaleInfo_id"));
		}
		int Buy_partmentid = 0;
		if (request.getParameter("Buy_partmentid") != null
				&& request.getParameter("Buy_partmentid") != "") {
			Buy_partmentid = Integer.parseInt(request.getParameter("Buy_partmentid"));
		}
		String resultInfo="";
		
		try
		{
			String msg = csid.buyIn(SaleInfo_id,Buy_partmentid);
			if ("success".equals(msg))
			{
				resultInfo = "买入成功，等待对方确认收款！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "交易失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	
	private void ConfirmPayment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int SaleInfo_id = 0;
		if (request.getParameter("SaleInfo_id") != null
				&& request.getParameter("SaleInfo_id") != "") {
			SaleInfo_id = Integer.parseInt(request.getParameter("SaleInfo_id"));
		}
		String resultInfo="";
		
		try
		{
			String msg = csid.confirmPayment(SaleInfo_id);
			if ("success".equals(msg))
			{
				resultInfo = "收款成功！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "交易失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	private void deleteSaleInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int SaleInfo_id = 0;
		if (request.getParameter("SaleInfo_id") != null
				&& request.getParameter("SaleInfo_id") != "") {
			SaleInfo_id = Integer.parseInt(request.getParameter("SaleInfo_id"));
		}
		String resultInfo="";
		
		try
		{
			String msg = csid.deleteSaleInfo(SaleInfo_id);
			if ("success".equals(msg))
			{
				resultInfo = "删除成功！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "删除失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	
	/**添加行情
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void addHQ(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int partmentid = 0;
		if (request.getParameter("partmentid") != null
				&& request.getParameter("partmentid") != "") {
			partmentid = Integer.parseInt(request.getParameter("partmentid"));
		}
		int buyORSale = 0;
		if (request.getParameter("buyORSale") != null
				&& request.getParameter("buyORSale") != "") {
			buyORSale = Integer.parseInt(request.getParameter("buyORSale"));
		}
		float price=0;
		if (request.getParameter("price") != null
				&& request.getParameter("price") != "") {
			price = Float.parseFloat(request.getParameter("price"));
		}
		float dealvalue=0;
		if (request.getParameter("dealvalue") != null
				&& request.getParameter("dealvalue") != "") {
			dealvalue = Float.parseFloat(request.getParameter("dealvalue"));
		}
		String contactPerson=request.getParameter("contactPerson");
		String contactInformation=request.getParameter("contactInformation");
		
		String resultInfo="";
		
		try
		{
			String msg = csid.addHQ(partmentid,buyORSale,price,dealvalue,contactPerson,contactInformation);
			if ("success".equals(msg))
			{
				resultInfo = "添加行情成功！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "添加失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
	
	
	/**编辑行情
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void EditHQ(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		CO2SaleInfoDao csid=new CO2SaleInfoDao();
		int SaleInfo_id = 0;
		if (request.getParameter("SaleInfo_id") != null
				&& request.getParameter("SaleInfo_id") != "") {
			SaleInfo_id = Integer.parseInt(request.getParameter("SaleInfo_id"));
		}
		int partmentid = 0;
		if (request.getParameter("partmentid") != null
				&& request.getParameter("partmentid") != "") {
			partmentid = Integer.parseInt(request.getParameter("partmentid"));
		}
		int buyORSale = 0;
		if (request.getParameter("buyORSale") != null
				&& request.getParameter("buyORSale") != "") {
			buyORSale = Integer.parseInt(request.getParameter("buyORSale"));
		}
		float price=0;
		if (request.getParameter("price") != null
				&& request.getParameter("price") != "") {
			price = Float.parseFloat(request.getParameter("price"));
		}
		float dealvalue=0;
		if (request.getParameter("dealvalue") != null
				&& request.getParameter("dealvalue") != "") {
			dealvalue = Float.parseFloat(request.getParameter("dealvalue"));
		}
		String contactPerson=request.getParameter("contactPerson");
		String contactInformation=request.getParameter("contactInformation");
		
		String resultInfo="";
		
		try
		{
			String msg = csid.editHQ(SaleInfo_id,partmentid,buyORSale,price,dealvalue,contactPerson,contactInformation);
			if ("success".equals(msg))
			{
				resultInfo = "编辑行情成功！";
				// 写入日志
			} else if ("fail".equals(msg))
			{
				resultInfo = "编辑失败！";
			} else
			{
				resultInfo = msg;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson_SavingReport = new Gson();
		String SavingReportInfo = gson_SavingReport.toJson(resultInfo);
		PrintWriter out = response.getWriter();
		out.println(SavingReportInfo);
		out.flush();
		out.close();
	}
}
