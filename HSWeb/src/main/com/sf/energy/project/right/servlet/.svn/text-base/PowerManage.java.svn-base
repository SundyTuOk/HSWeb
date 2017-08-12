/**
 * 2014-4-25
 */
package com.sf.energy.project.right.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.ModuleDao;
import com.sf.energy.project.right.dao.PowerDao;
import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-25
 * 
 * 
 */
public class PowerManage extends HttpServlet
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
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");
		// System.out.println("method:" + method);

		if ("getAllPower".equals(method))
			getAllPower(request, response);

		if ("deletePower".equals(method))
			deletePower(request, response);

		if ("addPower".equals(method))
			addPower(request, response);

		if ("updatePower".equals(method))
			updatePower(request, response);

		if ("getAllModuel".equals(method))
			getAllModel(request, response);

		// if ("getAllPowerTree".equals(method))
		// getAllPowerTree(request, response);

	}

	private void getAllModel(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		ModuleDao md = new ModuleDao();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<ModuleModel> list = md.listModule();
		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			ModuleModel n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Module_ID", n.getModuleID());
			jo.put("Module_Name", n.getModuleName());
			jo.put("Module_WindowName", n.getModuleAddress());
			jo.put("Module_ParentModuleID", n.getModuleParent());
			jo.put("Module_Num", n.getModuleNum());
			jo.put("Module_Mark", n.getModuleMark());
			jo.put("Module_Part1", n.getModulePart1());
			jo.put("Module_Order", n.getModuleOrder());
			jo.put("Module_Remark", n.getModuleRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");
		// System.out.println("getAllModel : "+json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void updatePower(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		PowerDao pd = new PowerDao();
		PowerModel power = new PowerModel();

		power.setPowerID(Integer.parseInt(request.getParameter("Power_ID")));
		power.setPowerName(request.getParameter("Power_Name"));
		power.setModuleID(Integer.parseInt(request.getParameter("Power_ModuleID")));
		power.setPowerLevel(Integer.parseInt(request.getParameter("Power_Level")));
		power.setPowerNum(request.getParameter("Power_Num"));
		power.setPowerRemark(request.getParameter("Power_Remark"));

		String resultInfo = null;
		if (pd.modify(power)){
			resultInfo = "修改权限成功！";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "权限管理", "编辑权限" + power.getPowerName());
		}
		else
			resultInfo = "修改权限失败！";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addPower(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{

		PowerDao pd = new PowerDao();
		PowerModel power = new PowerModel();

		power.setPowerName(request.getParameter("Power_Name"));
		power.setModuleID(Integer.parseInt(request.getParameter("Power_ModuleID")));
		power.setPowerLevel(Integer.parseInt(request.getParameter("Power_Level")));
		power.setPowerNum(request.getParameter("Power_Num"));
		power.setPowerRemark(request.getParameter("Power_Remark"));

		String resultInfo = null;
		if (pd.insert(power))
		{
			resultInfo = "新增权限成功！";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "权限管理", "新增权限" + power.getPowerName() );
		} else
			resultInfo = "新增权限失败！";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deletePower(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PowerDao pd = new PowerDao();
		int PowerID = Integer.parseInt(request.getParameter("Power_ID"));

		String resultInfo = null;

		if (pd.delete(PowerID)){
			resultInfo = "删除权限成功！";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "权限管理", "删除权限" + pd.query(PowerID).getPowerName());
		}
		else
			resultInfo = "删除权限失败！";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllPower(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PowerDao pd = new PowerDao();
		List<PowerModel> list = pd.listModulePower();

		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			PowerModel n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Power_ID", n.getPowerID());
			jo.put("Power_Name", n.getPowerName());
			jo.put("Power_ModuleID", n.getModuleID());
			jo.put("Power_Level", n.getPowerLevel());
			jo.put("Power_Num", n.getPowerNum());
			jo.put("Power_Remark", n.getPowerRemark());

			// 用于建树的模块信息
			jo.put("Module_ID", n.getModuleID());
			jo.put("Module_Name", n.getModuleName());
			jo.put("Module_Parent", n.getModuleParent());
			jo.put("Module_Num", n.getModuleNum());
			json.add(jo);
		}

		response.setContentType("application/x-json");
		// System.out.println("getAllPower : "+json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	// private void getAllPowerTree(HttpServletRequest request,
	// HttpServletResponse response) throws IOException, SQLException
	// {
	// List<PowerModel> list = pd.listModulePower();
	//
	// JSONArray json = new JSONArray();
	// for (int i = list.size() - 1; i >= 0; i--)
	// {
	// PowerModel n = list.get(i);
	// JSONObject jo = new JSONObject();
	// jo.put("Power_ID", n.getPowerID());
	// jo.put("Power_Name", n.getPowerName());
	// jo.put("Module_ID", n.getModuleID());
	// jo.put("Module_Name", n.getModuleName());
	// jo.put("Module_Parent", n.getModuleParent());
	// jo.put("Power_Num", n.getPowerNum());
	// jo.put("Module_Num", n.getModuleNum());
	//
	// json.add(jo);
	// }
	//
	// response.setContentType("application/x-json");
	//
	// PrintWriter out = response.getWriter();
	// out.println(json.toString());
	// out.flush();
	// out.close();
	// }

}
