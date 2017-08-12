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

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.PDTempWetDao;
import com.sf.energy.project.equipment.model.PDTempWetModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class PDTempWetManage extends HttpServlet
{

	/**李戬
	 *配电房温湿度设备管理servlet
	 *
	 */
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e)
		{
			// TODO Auto-generated catch block
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
		if ("getPDTempWet".equals(method))
			getPDTempWet(request, response);
		if ("deleteSomePDTempWet".equals(method))
			deleteSomePDTempWet(request, response);
		if ("getOnePDTempWet".equals(method))
			getOnePDTempWet(request, response);
		if ("getAllPDRoom".equals(method))
			getAllPDRoom(request, response);
		if ("addPDTempWet".equals(method))
			addPDTempWet(request, response);
		
		if ("updatePDTempWet".equals(method))
			updatePDTempWet(request, response);
		
	}
	private void updatePDTempWet(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		PDTempWetDao pdtdao=new PDTempWetDao();
		PDTempWetModel pdtwModel=new PDTempWetModel();
		if (request.getParameter("PDTempWet_Port") != null
				&& request.getParameter("PDTempWet_Port").matches(numberPatern)
				&& !"".equals(request.getParameter("PDTempWet_Port")))
			pdtwModel.setPDTempWet_Port(Integer.parseInt(request.getParameter("PDTempWet_Port")));
		if (request.getParameter("PDTempWet_ID") != null
				&& request.getParameter("PDTempWet_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("PDTempWet_ID")))
			pdtwModel.setPDTempWet_ID(Integer.parseInt(request.getParameter("PDTempWet_ID")));
		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			pdtwModel.setArea_ID(Integer.parseInt(request.getParameter("Area_ID")));
		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			pdtwModel.setGather_ID(Integer.parseInt(request
					.getParameter("Gather_ID")));
		if (request.getParameter("PDRoom_ID") != null
				&& request.getParameter("PDRoom_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("PDRoom_ID")))
			pdtwModel.setPDRoom_ID(Integer.parseInt(request
					.getParameter("PDRoom_ID")));
		
		if (request.getParameter("PDTempWet_485Address") != null)
			pdtwModel.setPDTempWet_485Address(request
					.getParameter("PDTempWet_485Address"));
		if (request.getParameter("PDTempWet_Num") != null)
			pdtwModel.setPDTempWet_Num(request
					.getParameter("PDTempWet_Num"));
		if (request.getParameter("PDTempWet_Name") != null)
			pdtwModel.setPDTempWet_Name(request
					.getParameter("PDTempWet_Name"));
		
		
		String resultInfo = null;

		if (pdtdao.update(pdtwModel)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "配电温湿度设备管理", "编辑温湿度设备"+pdtwModel.getPDTempWet_Name());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}
	private void addPDTempWet(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		PDTempWetDao pdtdao=new PDTempWetDao();		
		PDTempWetModel pdtwModel=new PDTempWetModel();
		if (request.getParameter("PDTempWet_Port") != null
				&& request.getParameter("PDTempWet_Port").matches(numberPatern)
				&& !"".equals(request.getParameter("PDTempWet_Port")))
			pdtwModel.setPDTempWet_Port(Integer.parseInt(request.getParameter("PDTempWet_Port")));
		if (request.getParameter("Area_ID") != null
				&& request.getParameter("Area_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Area_ID")))
			pdtwModel.setArea_ID(Integer.parseInt(request.getParameter("Area_ID")));
		if (request.getParameter("Gather_ID") != null
				&& request.getParameter("Gather_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("Gather_ID")))
			pdtwModel.setGather_ID(Integer.parseInt(request
					.getParameter("Gather_ID")));
		if (request.getParameter("PDRoom_ID") != null
				&& request.getParameter("PDRoom_ID").matches(numberPatern)
				&& !"".equals(request.getParameter("PDRoom_ID")))
			pdtwModel.setPDRoom_ID(Integer.parseInt(request
					.getParameter("PDRoom_ID")));
		
		if (request.getParameter("PDTempWet_485Address") != null)
			pdtwModel.setPDTempWet_485Address(request
					.getParameter("PDTempWet_485Address"));
		if (request.getParameter("PDTempWet_Num") != null)
			pdtwModel.setPDTempWet_Num(request
					.getParameter("PDTempWet_Num"));
		if (request.getParameter("PDTempWet_Name") != null)
			pdtwModel.setPDTempWet_Name(request
					.getParameter("PDTempWet_Name"));
		
		if (request.getParameter("lasttime") != null)
			pdtwModel.setLastTime(request
					.getParameter("lasttime"));
		
		
		String resultInfo = null;

		if (pdtdao.insert(pdtwModel)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "配电温湿度设备管理", "新增温湿度设备"+pdtwModel.getPDTempWet_Name());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllPDRoom(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		PDTempWetDao pdtdao=new PDTempWetDao();
		List<PDTempWetModel> list= pdtdao.queryAllPDRoom(); 
		JSONArray json = new JSONArray();
		JSONObject jo = null;


		for (int i = 0; i<list.size(); i++)
		{
			PDTempWetModel n = list.get(i);
			jo = new JSONObject();		
			jo.put("PDRoom_ID", n.getPDRoom_ID());
			jo.put("PDRoom_Name", n.getPDRoom_Name());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getOnePDTempWet(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		PDTempWetDao pdtdao=new PDTempWetDao();
		PDTempWetModel pdtwModel=new PDTempWetModel();
		int PDTempWet_ID=-1;
		if (request.getParameter("PDTempWet_ID") != null)
			PDTempWet_ID = Integer.parseInt(request.getParameter("PDTempWet_ID"));
		else
			return;
		pdtwModel=pdtdao.queryPDTempWetByID(PDTempWet_ID);
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		jo = new JSONObject();
		jo.put("PDTempWet_ID", pdtwModel.getPDTempWet_ID());
		jo.put("PDTempWet_Num", pdtwModel.getPDTempWet_Num());
		jo.put("Area_ID", pdtwModel.getArea_ID());
		jo.put("Area_Name", pdtwModel.getArea_Name());
		jo.put("Gather_ID", pdtwModel.getGather_ID());
		jo.put("Gather_Name", pdtwModel.getGather_Name());
		jo.put("PDTempWet_485Address", pdtwModel.getPDTempWet_485Address());
		jo.put("PDTempWet_Name", pdtwModel.getPDTempWet_Name());
		jo.put("PDRoom_ID", pdtwModel.getPDRoom_ID());
		jo.put("PDRoom_Name", pdtwModel.getPDRoom_Name());
		jo.put("LastTime", pdtwModel.getLastTime());
		jo.put("PDTempWet_Port", pdtwModel.getPDTempWet_Port());
		json.add(jo);
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void deleteSomePDTempWet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		PDTempWetDao pdtdao=new PDTempWetDao();
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
		if (pdtdao.deleteSomePDTempWet(list)){
			info = "success";
			// 写入日志
			log.writeLog(userLoginName, "配电温湿度设备管理", "批量删除温湿度设备");
		}
		
		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void getPDTempWet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String tableName="";
		String order="";
		if (request.getParameter("tableName") != null)
			tableName = request.getParameter("tableName");
		if (request.getParameter("order") != null)
			order = request.getParameter("order");
		PDTempWetDao pdtdao=new PDTempWetDao();
		List<PDTempWetModel> list= pdtdao.queryAllPDTempWetOrder(tableName,order); 
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		for (int i = 0; i<list.size(); i++)
		{
			PDTempWetModel n = list.get(i);
			jo = new JSONObject();
			jo.put("PDTempWet_ID", n.getPDTempWet_ID());
			jo.put("PDTempWet_Num", n.getPDTempWet_Num());
			jo.put("Area_ID", n.getArea_ID());
			jo.put("Area_Name", n.getArea_Name());
			jo.put("Gather_ID", n.getGather_ID());
			jo.put("Gather_Name", n.getGather_Name());
			jo.put("PDTempWet_485Address", n.getPDTempWet_485Address());
			jo.put("PDTempWet_Name", n.getPDTempWet_Name());
			jo.put("PDRoom_ID", n.getPDRoom_ID());
			jo.put("PDRoom_Name", n.getPDRoom_Name());
			jo.put("LastTime", n.getLastTime());
			jo.put("PDTempWet_Port", n.getPDTempWet_Port());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

}