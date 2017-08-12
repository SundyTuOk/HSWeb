package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.APMangeDao;
import com.sf.energy.project.system.model.APManageModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class APManageServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("insert".equals(method))
			insert(request, response);
		if ("delete".equals(method))
			delete(request, response);
		if ("modify".equals(method))
			modify(request, response);
		if ("queryByID".equals(method))
			queryByID(request, response);
		if ("queryAll".equals(method))
			queryAll(request, response);
	}

	private void queryAll(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException, ParseException
	{
		APMangeDao apmd = new APMangeDao();
		ArrayList<APManageModel> list = apmd.queryAll();

		JSONArray json = new JSONArray();
		JSONObject jo;
		for (APManageModel apmm : list)
		{
			jo = new JSONObject();
			jo.put("priceID", apmm.getPriceID());
			jo.put("priceStyle", apmm.getPriceStyle());
			jo.put("priceNum", apmm.getPriceNum());
			jo.put("priceName", apmm.getPriceName());
			jo.put("priceValue", apmm.getPriceValue());
			jo.put("BJValue", apmm.getBJValue());
			jo.put("LZValue", apmm.getLZValue());
			jo.put("YBValue", apmm.getYBValue());
			jo.put("EXFZ", apmm.getEXFZ());
			jo.put("EXFZSX", apmm.getEXFZSX());
			jo.put("EXFZXX", apmm.getEXFZXX());
			jo.put("autoCut", apmm.getAutoCut());
			jo.put("EXFZHF", apmm.getEXFZHF());
			jo.put("limit", apmm.getLimit());
			jo.put("YBMonthList", apmm.getYBMonthList());
			jo.put("TYDSD", apmm.getTYDSD());
			jo.put("QDYFF", apmm.getQDYFF());
			jo.put("AUTOTZ", apmm.getAUTOTZ());
			jo.put("TZDL", apmm.getTZDL());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void queryByID(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException, ParseException
	{
		APMangeDao apmd = new APMangeDao();
		int id = 0;
		if (request.getParameter("priceID") != null)
		{
			id = Integer.parseInt(request.getParameter("priceID"));
		}

		APManageModel apmm = apmd.queryByID(id);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		jo.put("priceID", apmm.getPriceID());
		jo.put("priceStyle", apmm.getPriceStyle());
		jo.put("priceNum", apmm.getPriceNum());
		jo.put("priceName", apmm.getPriceName());
		jo.put("priceValue", apmm.getPriceValue());
		jo.put("BJValue", apmm.getBJValue());
		jo.put("LZValue", apmm.getLZValue());
		jo.put("YBValue", apmm.getYBValue());
		jo.put("EXFZ", apmm.getEXFZ());
		jo.put("EXFZSX", apmm.getEXFZSX());
		jo.put("EXFZXX", apmm.getEXFZXX());
		jo.put("autoCut", apmm.getAutoCut());
		jo.put("EXFZHF", apmm.getEXFZHF());
		jo.put("limit", apmm.getLimit());
		jo.put("YBMonthList", apmm.getYBMonthList());
		jo.put("TYDSD", apmm.getTYDSD());
		jo.put("QDYFF", apmm.getQDYFF());
		jo.put("AUTOTZ", apmm.getAUTOTZ());
		jo.put("TZDL", apmm.getTZDL());
		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void modify(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		APMangeDao apmd = new APMangeDao();
		OperationLogInterface log = new OperationLogImplement();
		APManageModel apmm = new APManageModel();
		if (!"".equals(request.getParameter("priceID"))
				&& request.getParameter("priceID") != null)
			apmm.setPriceID(Integer.parseInt(request.getParameter("priceID")));

		if (!"".equals(request.getParameter("Price_Style"))
				&& request.getParameter("Price_Style") != null)
			apmm.setPriceStyle(request.getParameter("Price_Style"));

		if (!"".equals(request.getParameter("Price_Num"))
				&& request.getParameter("Price_Num") != null)
			apmm.setPriceNum(request.getParameter("Price_Num"));

		if (!"".equals(request.getParameter("Price_Name"))
				&& request.getParameter("Price_Name") != null)
			apmm.setPriceName(request.getParameter("Price_Name"));

		if (!"".equals(request.getParameter("Price_Value"))
				&& request.getParameter("Price_Value") != null)
			apmm.setPriceValue(request.getParameter("Price_Value"));

		if (!"".equals(request.getParameter("BJValue"))
				&& request.getParameter("BJValue") != null)
			apmm.setBJValue(request.getParameter("BJValue"));
		else {
			apmm.setBJValue("0");
		}

		if (!"".equals(request.getParameter("LZValue"))
				&& request.getParameter("LZValue") != null)
			apmm.setLZValue(request.getParameter("LZValue"));
		else {
			apmm.setLZValue("0");
		}

		if (!"".equals(request.getParameter("YBValue"))
				&& request.getParameter("YBValue") != null)
			apmm.setYBValue(request.getParameter("YBValue"));
		else{
			apmm.setYBValue("0");
		}

		if (!"".equals(request.getParameter("EXFZ"))
				&& request.getParameter("EXFZ") != null)
			apmm.setEXFZ(request.getParameter("EXFZ"));
		else {
			apmm.setEXFZ("0");
		}

		if (!"".equals(request.getParameter("EXFZSX"))
				&& request.getParameter("EXFZSX") != null)
			apmm.setEXFZSX(request.getParameter("EXFZSX"));
		else {
			apmm.setEXFZSX("0");
		}

		if (!"".equals(request.getParameter("EXFZXX"))
				&& request.getParameter("EXFZXX") != null)
			apmm.setEXFZXX(request.getParameter("EXFZXX"));
		else{
			apmm.setEXFZXX("0");
		}

		//		if (!"".equals(request.getParameter("priceID"))
		//				&& request.getParameter("priceID") != null)
		apmm.setAutoCut("");

		if (!"".equals(request.getParameter("EXFZHF"))
				&& request.getParameter("EXFZHF") != null)
			apmm.setEXFZHF(request.getParameter("EXFZHF"));
		else {
			apmm.setEXFZHF("0");
		}

		if (!"".equals(request.getParameter("Limit"))
				&& request.getParameter("Limit") != null)
			apmm.setLimit(request.getParameter("Limit"));
		else {
			apmm.setLimit("0");
		}

		if (!"".equals(request.getParameter("YBMonthList"))
				&& request.getParameter("YBMonthList") != null)
			apmm.setYBMonthList(request.getParameter("YBMonthList"));

		if (!"".equals(request.getParameter("TYDSD"))
				&& request.getParameter("TYDSD") != null)
			apmm.setTYDSD(request.getParameter("TYDSD"));

		if (!"".equals(request.getParameter("QDYFF"))
				&& request.getParameter("QDYFF") != null)
			apmm.setQDYFF(request.getParameter("QDYFF"));

		if (!"".equals(request.getParameter("AUTOTZ"))
				&& request.getParameter("AUTOTZ") != null)
			apmm.setAUTOTZ(request.getParameter("AUTOTZ"));

		if (!"".equals(request.getParameter("TZDL"))
				&& request.getParameter("TZDL") != null)
			apmm.setTZDL(request.getParameter("TZDL"));

		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if(apmd.checkInfo(apmm)){
				resultInfo = "编号、名称、单价不能为空!";
			}
			else if(apmd.hasrepeatEditNum(apmm)){
				resultInfo="编号不能重复!";
			}
			else if (apmd.modify(apmm))
			{
				resultInfo = "编辑成功！";
				// 写入日志
				log.writeLog(userLoginName, "预付费配置", "编辑 "+apmm.getPriceName());
			} else
			{
				resultInfo = "编辑失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "预付费配置", "编辑", "新增失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException
	{
		APMangeDao apmd = new APMangeDao();
		OperationLogInterface log = new OperationLogImplement();
		int id = 0;
		if (request.getParameter("priceID") != null)
		{
			id = Integer.parseInt(request.getParameter("priceID"));
		}

		String resultInfo = null;

		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			String nameString="";
			try
			{
				nameString = apmd.queryByID(id).getPriceName();
			} catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (apmd.delete(id))
			{
				resultInfo = "success";
				log.writeLog(userLoginName, "预付费配置", "删除"+nameString);
			} else
			{
				resultInfo = "fail";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "预付费配置", "删除", "删除失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();

	}

	private void insert(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		APMangeDao apmd = new APMangeDao();
		OperationLogInterface log = new OperationLogImplement();
		APManageModel apmm = new APManageModel();

		if (!"".equals(request.getParameter("Price_Style"))
				&& request.getParameter("Price_Style") != null)
			apmm.setPriceStyle(request.getParameter("Price_Style"));

		if (!"".equals(request.getParameter("Price_Num"))
				&& request.getParameter("Price_Num") != null)
			apmm.setPriceNum(request.getParameter("Price_Num"));

		if (!"".equals(request.getParameter("Price_Name"))
				&& request.getParameter("Price_Name") != null)
			apmm.setPriceName(request.getParameter("Price_Name"));

		if (!"".equals(request.getParameter("Price_Value"))
				&& request.getParameter("Price_Value") != null)
			apmm.setPriceValue(request.getParameter("Price_Value"));

		if (!"".equals(request.getParameter("BJValue"))
				&& request.getParameter("BJValue") != null)
			apmm.setBJValue(request.getParameter("BJValue"));
		else {
			apmm.setBJValue("0");
		}

		if (!"".equals(request.getParameter("LZValue"))
				&& request.getParameter("LZValue") != null)
			apmm.setLZValue(request.getParameter("LZValue"));
		else {
			apmm.setLZValue("0");
		}

		if (!"".equals(request.getParameter("YBValue"))
				&& request.getParameter("YBValue") != null)
			apmm.setYBValue(request.getParameter("YBValue"));
		else{
			apmm.setYBValue("0");
		}

		if (!"".equals(request.getParameter("EXFZ"))
				&& request.getParameter("EXFZ") != null)
			apmm.setEXFZ(request.getParameter("EXFZ"));
		else {
			apmm.setEXFZ("0");
		}

		if (!"".equals(request.getParameter("EXFZSX"))
				&& request.getParameter("EXFZSX") != null)
			apmm.setEXFZSX(request.getParameter("EXFZSX"));
		else {
			apmm.setEXFZSX("0");
		}

		if (!"".equals(request.getParameter("EXFZXX"))
				&& request.getParameter("EXFZXX") != null)
			apmm.setEXFZXX(request.getParameter("EXFZXX"));
		else{
			apmm.setEXFZXX("0");
		}

		//		if (!"".equals(request.getParameter("priceID"))
		//				&& request.getParameter("priceID") != null)
		apmm.setAutoCut("");

		if (!"".equals(request.getParameter("EXFZHF"))
				&& request.getParameter("EXFZHF") != null)
			apmm.setEXFZHF(request.getParameter("EXFZHF"));
		else {
			apmm.setEXFZHF("0");
		}

		if (!"".equals(request.getParameter("Limit"))
				&& request.getParameter("Limit") != null)
			apmm.setLimit(request.getParameter("Limit"));
		else {
			apmm.setLimit("0");
		}

		if (!"".equals(request.getParameter("YBMonthList"))
				&& request.getParameter("YBMonthList") != null)
			apmm.setYBMonthList(request.getParameter("YBMonthList"));

		if (!"".equals(request.getParameter("TYDSD"))
				&& request.getParameter("TYDSD") != null)
			apmm.setTYDSD(request.getParameter("TYDSD"));

		if (!"".equals(request.getParameter("QDYFF"))
				&& request.getParameter("QDYFF") != null)
			apmm.setQDYFF(request.getParameter("QDYFF"));

		if (!"".equals(request.getParameter("AUTOTZ"))
				&& request.getParameter("AUTOTZ") != null)
			apmm.setAUTOTZ(request.getParameter("AUTOTZ"));

		if (!"".equals(request.getParameter("TZDL"))
				&& request.getParameter("TZDL") != null)
			apmm.setTZDL(request.getParameter("TZDL"));
		String resultInfo = null;
		HttpSession session = request.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		try
		{
			if(apmd.checkInfo(apmm)){
				resultInfo = "编号、名称、单价不能为空!";
			}
			else if(apmd.hasrepeatNum(apmm.getPriceNum())){
				resultInfo="编号不能重复!";
			}
			else if (apmd.insert(apmm))
			{
				resultInfo = "添加成功！";
				// 写入日志
				log.writeLog(userLoginName, "预付费配置", "新增  "+apmm.getPriceName());
			} else
			{
				resultInfo = "添加失败！";
				// 写入日志
				log.writeLogErrInfo(userLoginName, "预付费配置", "新增", "新增失败！！！");
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}


}
