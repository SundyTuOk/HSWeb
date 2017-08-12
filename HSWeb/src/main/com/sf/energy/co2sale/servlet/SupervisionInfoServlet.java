package com.sf.energy.co2sale.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sf.energy.co2sale.impl.CO2JieSuan;
import com.sf.energy.co2sale.impl.CO2SaleInfoHelperImpl;
import com.sf.energy.co2sale.impl.WarningCO2HelperImpl;
import com.sf.energy.co2sale.model.CO2SaleInfoModel;
import com.sf.energy.co2sale.model.CO2WarningModel;

public class SupervisionInfoServlet extends HttpServlet
{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException, MessagingException
	{
		String method = req.getParameter("method");

		if ("getWarningAndSaleInfo".equals(method))
			getWarningAndSaleInfo(req, resp);
		
	}

	public void getWarningAndSaleInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		// int partid = -1;
		Calendar year_cal = Calendar.getInstance();
		int year = year_cal.get(Calendar.YEAR);
//		int month = year_cal.get(Calendar.MONTH);
		// if(request.getParameter("partid")!=null &&
		// request.getParameter("partid")!="")
		// {
		// partid = Integer.parseInt(request.getParameter("partid"));
		// }
		if (request.getParameter("year") != null && request.getParameter("year") != "")
		{
			year = Integer.parseInt(request.getParameter("year"));
		}
		/*if (request.getParameter("month") != null && request.getParameter("month") != "")
		{
			month = Integer.parseInt(request.getParameter("month"));
		}*/
		/** 报警单位数据 **/
		WarningCO2HelperImpl warningPartCo2Impl = new WarningCO2HelperImpl();
		List<CO2WarningModel> warningPartCo2_list = new ArrayList<CO2WarningModel>();

		try
		{
			warningPartCo2_list = warningPartCo2Impl.getPartCO2WarningInfo(year);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** 报警单位数据 **/

		/** 交易记录 **/
		CO2SaleInfoHelperImpl CO2SaleInfoImpl = new CO2SaleInfoHelperImpl();
		List<CO2SaleInfoModel> CO2SaleInfo_list = new ArrayList<CO2SaleInfoModel>();

		try
		{
			CO2SaleInfo_list = CO2SaleInfoImpl.getCO2SaleInfo(year);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** 交易记录 **/
		Gson gson_Supervision = new Gson();
		String SupervisionInfo = gson_Supervision.toJson(new Object[]
		{ warningPartCo2_list, CO2SaleInfo_list });
		// System.out.println(SupervisionInfo);
		out.println(SupervisionInfo);
		out.flush();
		out.close();
	}

	
}
