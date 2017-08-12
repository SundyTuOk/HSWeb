package com.sf.energy.project.equipment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.TempDevDao;
import com.sf.energy.project.equipment.model.TempDevModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TempDevManage extends HttpServlet
{
	String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (RowsExceededException e)
		{
			
			e.printStackTrace();
		} catch (WriteException e)
		{
		
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, RowsExceededException,
			WriteException
	{
		String method = request.getParameter("method");
		if ("getTempDev".equals(method))
			getTempDev(request, response);
		if ("deleteSomeTempDev".equals(method))
			deleteSomeTempDev(request, response);
		if ("getOneTempDev".equals(method))
			getOneTempDev(request, response);
		if ("getAllVoltageTrans".equals(method))
			getAllVoltageTrans(request, response);
		if ("addTempDev".equals(method))
			addTempDev(request, response);
		
		if ("updateTempDev".equals(method))
			updateTempDev(request, response);
		
	}
	private void updateTempDev(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		TempDevDao tdd=new TempDevDao();
		TempDevModel tpModel=new TempDevModel();
		if (request.getParameter("TempDev_Port") != null
				&& request.getParameter("TempDev_Port").matches(numberPatern)
				&& !"".equals(request.getParameter("TempDev_Port")))
			tpModel.setTempDev_Port(Integer.parseInt(request.getParameter("TempDev_Port")));
		if (request.getParameter("TempDev_ID") != null
				&& request.getParameter("TempDev_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("TempDev_ID")))
			tpModel.setTempDev_ID(Integer.parseInt(request.getParameter("TempDev_ID")));
		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			tpModel.setArea_ID(Integer.parseInt(request.getParameter("Area_ID")));
		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			tpModel.setGather_ID(Integer.parseInt(request
					.getParameter("Gather_ID")));
		if (request.getParameter("VoltageTrans_ID") != null
				&& request.getParameter("VoltageTrans_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("VoltageTrans_ID")))
			tpModel.setVoltageTrans_ID(Integer.parseInt(request
					.getParameter("VoltageTrans_ID")));
		
		if (request.getParameter("TempDev_485Address") != null)
			tpModel.setTempDev_485Address(request
					.getParameter("TempDev_485Address"));
		if (request.getParameter("TempDev_Num") != null)
			tpModel.setTempDev_Num(request
					.getParameter("TempDev_Num"));
		if (request.getParameter("TempDev_Name") != null)
			tpModel.setTempDev_Name(request
					.getParameter("TempDev_Name"));
		
//		System.out.println(request.getParameter("TempDev_ID"));
		String resultInfo = null;

		if (tdd.update(tpModel)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "变压器温度设备管理", "新增变压器温度设备"+tpModel.getTempDev_Name());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	private void addTempDev(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		TempDevDao tdd=new TempDevDao();
		TempDevModel tpModel=new TempDevModel();
		if (request.getParameter("TempDev_Port") != null
				&& request.getParameter("TempDev_Port").matches(numberPatern)
				&& !"".equals(request.getParameter("TempDev_Port")))
			tpModel.setTempDev_Port(Integer.parseInt(request.getParameter("TempDev_Port")));
		if (request.getParameter("TempDev_ID") != null
				&& request.getParameter("TempDev_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("TempDev_ID")))
			tpModel.setTempDev_ID(Integer.parseInt(request.getParameter("TempDev_ID")));
		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			tpModel.setArea_ID(Integer.parseInt(request.getParameter("Area_ID")));
		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			tpModel.setGather_ID(Integer.parseInt(request
					.getParameter("Gather_ID")));
		if (request.getParameter("VoltageTrans_ID") != null
				&& request.getParameter("VoltageTrans_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("VoltageTrans_ID")))
			tpModel.setVoltageTrans_ID(Integer.parseInt(request
					.getParameter("VoltageTrans_ID")));
		
		if (request.getParameter("TempDev_485Address") != null)
			tpModel.setTempDev_485Address(request
					.getParameter("TempDev_485Address"));
		if (request.getParameter("TempDev_Num") != null)
			tpModel.setTempDev_Num(request
					.getParameter("TempDev_Num"));
		if (request.getParameter("TempDev_Name") != null)
			tpModel.setTempDev_Name(request
					.getParameter("TempDev_Name"));
		
		
		String resultInfo = null;

		if (tdd.insert(tpModel)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "变压器温度设备管理", "新增变压器温度设备"+tpModel.getTempDev_Name());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllVoltageTrans(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		TempDevDao tdd=new TempDevDao();
		List<TempDevModel> list= tdd.queryAllVoltageTrans(); 
		JSONArray json = new JSONArray();
		JSONObject jo = null;


		for (int i = 0; i<list.size(); i++)
		{
			TempDevModel n = list.get(i);
			jo = new JSONObject();		
			jo.put("VoltageTrans_ID", n.getVoltageTrans_ID());
			jo.put("VoltageTrans_Name", n.getVoltageTrans_Name());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getOneTempDev(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		TempDevDao tdd=new TempDevDao();
		TempDevModel tpModel=new TempDevModel();
		int TempDev_ID=-1;
		if (request.getParameter("TempDev_ID") != null)
			TempDev_ID = Integer.parseInt(request.getParameter("TempDev_ID"));
		else
			return;
		tpModel=tdd.queryTempDevByID(TempDev_ID);
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		jo = new JSONObject();
		jo.put("TempDev_ID", tpModel.getTempDev_ID());
		jo.put("TempDev_Num", tpModel.getTempDev_Num());
		jo.put("Area_ID", tpModel.getArea_ID());
		jo.put("Area_Name", tpModel.getArea_Name());
		jo.put("Gather_ID", tpModel.getGather_ID());
		jo.put("Gather_Name", tpModel.getGather_Name());
		jo.put("TempDev_485Address", tpModel.getTempDev_485Address());
		jo.put("TempDev_Name", tpModel.getTempDev_Name());
		jo.put("VoltageTrans_ID", tpModel.getVoltageTrans_ID());
		jo.put("VoltageTrans_Name", tpModel.getVoltageTrans_Name());
		jo.put("LastTime", tpModel.getLastTime());
		jo.put("TempDev_Port", tpModel.getTempDev_Port());
		json.add(jo);
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void deleteSomeTempDev(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		TempDevDao tdd=new TempDevDao();
		String IDList = null;
		if (request.getParameter("IDList") != null)
			IDList = request.getParameter("IDList");
		else
			return;
		String[] strList = IDList.split(",");
		List<Integer> list = null;
		if (strList != null && strList.length > 0)
		{
			list = new LinkedList<Integer>();
			for (String num : strList)
			{
				list.add(Integer.parseInt(num));
			}
		}
		String info = "fail";
		if (tdd.deleteSomeTempDev(list)){
			info = "success";
			// 写入日志
			log.writeLog(userLoginName, "变压器温度设备管理", "批量删除变压器温度设备");
		}
		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void getTempDev(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String tableName="";
		String order="";
		if (request.getParameter("tableName") != null)
			tableName = request.getParameter("tableName");
		if (request.getParameter("order") != null)
			order = request.getParameter("order");
		
		TempDevDao tdd=new TempDevDao();
		
		
		List<TempDevModel> list= tdd.queryAllTempDevOrder(tableName,order); 
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		for (int i = 0; i<list.size(); i++)
		{
			TempDevModel n = list.get(i);
			jo = new JSONObject();
			jo.put("TempDev_ID", n.getTempDev_ID());
			jo.put("TempDev_Num", n.getTempDev_Num());
			jo.put("Area_ID", n.getArea_ID());
			jo.put("Area_Name", n.getArea_Name());
			jo.put("Gather_ID", n.getGather_ID());
			jo.put("Gather_Name", n.getGather_Name());
			jo.put("TempDev_485Address", n.getTempDev_485Address());
			jo.put("TempDev_Name", n.getTempDev_Name());
			jo.put("VoltageTrans_ID", n.getVoltageTrans_ID());
			jo.put("VoltageTrans_Name", n.getVoltageTrans_Name());
			jo.put("LastTime", n.getLastTime());
			jo.put("TempDev_Port", n.getTempDev_Port());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

}
