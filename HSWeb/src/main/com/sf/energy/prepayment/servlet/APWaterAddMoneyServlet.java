package com.sf.energy.prepayment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.sf.energy.prepayment.dao.APTuiDao;
import com.sf.energy.prepayment.dao.APWaterAddMoneyDao;
import com.sf.energy.prepayment.model.APTuiModel;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class APWaterAddMoneyServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{

			e.printStackTrace();
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, NumberFormatException, ParseException
	{
		String method = req.getParameter("method");
		// System.out.println("method:" + method);
		// 查询分合闸信息
		if ("getAPWaterAddMoneyInfo".equals(method))
			getAPWaterAddMoneyInfo(req, resp);
		
		if ("saveSaleMoney".equals(method))
			saveSaleMoney(req, resp);
	}
	/**
	 * 保存售水记录
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	private void saveSaleMoney(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, NumberFormatException, ParseException
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}
		
		String waterId = req.getParameter("waterId");
		String saleWaterMoney = req.getParameter("saleWaterMoney");
		String saleWaterGross = req.getParameter("saleWaterGross");
		APWaterAddMoneyDao dao=new APWaterAddMoneyDao();
		
		String[] infos = dao.queryInfoByWaterID(waterId).split("\\|");
		String info = infos[0]+",表地址:"+infos[1];
		String waMeter_name = infos[0];
		String waMeter_485Address = infos[1];
		String price = infos[2];
		String userLoginName = (String) req.getSession().getAttribute(
				"userName");
		String realName = (String) req.getSession().getAttribute(
				"realName");
		OperationLogInterface log = new OperationLogImplement();
		// 写入日志
		log.writeLog(userLoginName, "水表后付费管理", "充值水费,"+info+",金额："+saleWaterMoney);
		
		JSONArray json= dao.saveSaleMoney(waterId,waMeter_name,waMeter_485Address,saleWaterMoney,saleWaterGross,userID,realName,price);
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAPWaterAddMoneyInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String id = req.getParameter("id");
		APWaterAddMoneyDao dao=new APWaterAddMoneyDao();
		
		JSONArray json= dao.queryInfo(id);
	
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	

}
