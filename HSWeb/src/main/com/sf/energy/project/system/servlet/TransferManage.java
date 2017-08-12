/**
 * 2014-4-24
 */
package com.sf.energy.project.system.servlet;

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

import com.sf.energy.project.system.dao.DataSiteDao;
import com.sf.energy.project.system.model.DataSite;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-24
 * 
 * 
 */
public class TransferManage extends HttpServlet
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
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getAllTransfer".equals(method))
			getAllTransfer(request, response);

		if ("deleteTransfer".equals(method))
			deleteTransfer(request, response);

		if ("addTransfer".equals(method))
			addTransfer(request, response);

		if ("updateTransfer".equals(method))
			updateTransfer(request, response);

		if ("deleteSomeTransfer".equals(method))
			deleteSomeTransfer(request, response);

	}

	private void deleteSomeTransfer(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		DataSiteDao dsd = new DataSiteDao();
		String idList = request.getParameter("IDList");
		String[] list = idList.split(" ");
		Integer ID = 0;
		String resultInfo = "success";
		for (String id : list)
		{
			ID = Integer.parseInt(id);
			if (!dsd.delete(ID))
				resultInfo = "fail";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void updateTransfer(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		DataSiteDao dsd = new DataSiteDao();
		DataSite dataSite = new DataSite();

		dataSite.setDatasiteID(Integer.parseInt(request
				.getParameter("Transfer_ID")));
		dataSite.setDatasiteNum(request.getParameter("Transfer_Num"));
		dataSite.setDatasiteName(request.getParameter("Transfer_Name"));
		dataSite.setDatasiteIP(request.getParameter("Transfer_IP"));
		dataSite.setDatasitePort(Integer.parseInt(request
				.getParameter("Transfer_Port")));
		dataSite.setDatasiteHeart(Integer.parseInt(request
				.getParameter("Transfer_Heart")));
		dataSite.setDatasiteLastConTime(request
				.getParameter("Transfer_LastConTime"));
		dataSite.setDatasiteRemark(request.getParameter("Transfer_Remark"));

		String resultInfo = null;
		if (dsd.update(dataSite)){
			resultInfo = "success";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "数据中转站管理", "编辑数据中转站！"+dataSite.getDatasiteName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addTransfer(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		DataSiteDao dsd = new DataSiteDao();
		DataSite dataSite = new DataSite();

		dataSite.setDatasiteNum(request.getParameter("Transfer_Num"));
		dataSite.setDatasiteName(request.getParameter("Transfer_Name"));
		dataSite.setDatasiteIP(request.getParameter("Transfer_IP"));
		dataSite.setDatasitePort(Integer.parseInt(request
				.getParameter("Transfer_Port")));
		dataSite.setDatasiteHeart(Integer.parseInt(request
				.getParameter("Transfer_Heart")));
		dataSite.setDatasiteLastConTime(request
				.getParameter("Transfer_LastConTime"));
		dataSite.setDatasiteRemark(request.getParameter("Transfer_Remark"));

		String resultInfo = null;
		if (dsd.add(dataSite)){
			resultInfo = "success";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "数据中转站管理", "新增数据中转站！"+dataSite.getDatasiteName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteTransfer(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		DataSiteDao dsd = new DataSiteDao();
		Integer TransferID = Integer.parseInt(request
				.getParameter("TransferID"));

		String resultInfo = null;

		if (dsd.delete(TransferID)){
			resultInfo = "success";
			OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			log.writeLog(userLoginName, "数据中转站管理", "删除数据中转站！"+dsd.queryNameById(TransferID));
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllTransfer(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		DataSiteDao dsd = new DataSiteDao();
		List<DataSite> list = dsd.displayAll();

		JSONArray json = new JSONArray();
		for (int i = list.size() - 1; i >= 0; i--)
		{
			DataSite n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("Transfer_ID", n.getDatasiteID());
			jo.put("Transfer_Num", n.getDatasiteNum());
			jo.put("Transfer_Name", n.getDatasiteName());
			jo.put("Transfer_IP", n.getDatasiteIP());
			jo.put("Transfer_Port", n.getDatasitePort());
			jo.put("Transfer_Heart", n.getDatasiteHeart());
			jo.put("Transfer_LastConTime", n.getDatasiteLastConTime());
			jo.put("Transfer_Remark", n.getDatasiteRemark());
			json.add(jo);
			// //System.out.println("TransferName:" + n.getDatasiteName());
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private String readJSONString(HttpServletRequest request)
	{
		StringBuffer json = new StringBuffer();
		String line = null;
		try
		{
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
			{
				json.append(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}
}
