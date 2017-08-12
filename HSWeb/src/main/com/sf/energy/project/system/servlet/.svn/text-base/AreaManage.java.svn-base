/**
 * 2014-4-17
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

import com.sf.energy.project.right.dao.UsersAreaDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Area;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-17
 * 
 * 
 */
public class AreaManage extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		String method = request.getParameter("method");

		if ("getAllArea".equals(method))
			getAllArea(request, response);

		if ("getAllAreaWithoutUserId".equals(method))
			getAllAreaWithoutUserId(request, response);

		if ("getAllImportantArea".equals(method))
			getAllImportantArea(request, response);

		if ("getAllWaterImportantArea".equals(method))
			getAllWaterImportantArea(request, response);

		if ("deleteArea".equals(method))
			deleteArea(request, response);

		if ("addArea".equals(method))
			addArea(request, response);

		if ("updateArea".equals(method))
			updateArea(request, response);

	}

	private void updateArea(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao ad = new AreaDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		Area area = new Area();
		area.setId(Integer.parseInt(request.getParameter("areaID")));
		area.setNum(request.getParameter("areaNum"));
		area.setName(request.getParameter("areaName"));
		area.setAddress(request.getParameter("areaAddress"));
		area.setPhone(request.getParameter("areaPhone"));
		area.setRemark(request.getParameter("areaRemark"));

		String resultInfo = null;

		if (ad.hasUpdateRepeat(area))
		{
			resultInfo = "不允许添加重复编号或名称";
		} else if (ad.update(area)){
			resultInfo = "success";
			// 写入日志
			log.writeLog(userLoginName, "区域管理", "区域编辑    "+area.getName());
		}
		else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void addArea(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		AreaDao ad = new AreaDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");



		Area area = new Area();

		if ((request.getParameter("areaNum") != null)
				|| (!"".equals(request.getParameter("areaNum"))))
			area.setNum(request.getParameter("areaNum"));

		if (request.getParameter("areaName") != null
				|| (!"".equals(request.getParameter("areaName"))))
			area.setName(request.getParameter("areaName"));

		if (request.getParameter("areaAddress") != null
				|| (!"".equals(request.getParameter("areaAddress"))))
			area.setAddress(request.getParameter("areaAddress"));

		if (request.getParameter("areaPhone") != null
				|| (!"".equals(request.getParameter("areaPhone"))))
			area.setPhone(request.getParameter("areaPhone"));

		if (request.getParameter("areaRemark") != null
				|| (!"".equals(request.getParameter("areaRemark"))))
			area.setRemark(request.getParameter("areaRemark"));

		String resultInfo = null;
		if (ad.hasAddRepeat(area))
		{
			resultInfo = "不允许添加重复编号或名称";
		} else if (ad.add(area))
		{
			// 写入日志
			log.writeLog(userLoginName, "区域管理", "区域新增    "+area.getName());
			resultInfo = "success";
			// 新增时新曾相应权限
			HttpSession session = request.getSession();
			int userID = 0;
			if (session.getAttribute("userId") != null)
			{
				userID = Integer.parseInt(session.getAttribute("userId")
						.toString());
			}
			UsersAreaDao uad = new UsersAreaDao();
			uad.rightAfterInsert(area.getName(), userID);
		} else
			resultInfo = "fail";

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void deleteArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao ad = new AreaDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		Integer areaID = Integer.parseInt(request.getParameter("areaID"));
		String name=ad.queryNameById(areaID);
		String resultInfo = null;

		if (ad.hasAmmeter(areaID) || ad.hasWaterMeter(areaID)
				|| ad.hasGather(areaID))
		{
			resultInfo = "该区域下挂有设备，删除区域前，请先删除所挂的所有设备";
		} else
		{
			if (ad.delete(areaID))
			{
				// 写入日志
				log.writeLog(userLoginName, "区域管理", "区域删除    "+name);
				resultInfo = "success";
				UsersAreaDao uad = new UsersAreaDao();
				uad.rightAfterDelete(areaID);
			}

			else
				resultInfo = "fail";
		}

		PrintWriter out = response.getWriter();
		out.println(resultInfo);
		out.flush();
		out.close();
	}

	private void getAllAreaWithoutUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao ad = new AreaDao();
		OperationLogInterface log = new OperationLogImplement();
		//HttpSession session = request.getSession();
		int userID = 1;
		//		if (session.getAttribute("userId") != null)
		//		{
		//			userID = Integer
		//					.parseInt(session.getAttribute("userId").toString());
		//		}
		List<Area> list = ad.display(userID);
		// List<Area> list = ad.display();

		JSONArray json = new JSONArray();
		for (Area n : list)
		{
			JSONObject jo = new JSONObject();
			jo.put("Area_ID", n.getId());
			jo.put("Area_Num", n.getNum());
			jo.put("Area_Name", n.getName());
			jo.put("Area_Address", n.getAddress());
			jo.put("Area_Phone", n.getPhone());
			jo.put("Area_Remark", n.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getAllArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao ad = new AreaDao();
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = request.getSession();
		int userID = 0;
		if (session.getAttribute("userId") != null)
		{
			userID = Integer
					.parseInt(session.getAttribute("userId").toString());
		}
		List<Area> list = ad.display(userID);
		// List<Area> list = ad.display();

		JSONArray json = new JSONArray();
		for (Area n : list)
		{
			JSONObject jo = new JSONObject();
			jo.put("Area_ID", n.getId());
			jo.put("Area_Num", n.getNum());
			jo.put("Area_Name", n.getName());
			jo.put("Area_Address", n.getAddress());
			jo.put("Area_Phone", n.getPhone());
			jo.put("Area_Remark", n.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");
		//System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	// 重点用户区域
	private void getAllImportantArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao ad = new AreaDao();
		List<Area> list = ad.getImportantArea();

		JSONArray json = new JSONArray();
		for (Area n : list)
		{
			JSONObject jo = new JSONObject();
			jo.put("Area_ID", n.getId());
			// jo.put("Area_Num", n.getNum());
			jo.put("Area_Name", n.getName());
			// jo.put("Area_Address", n.getAddress());
			// jo.put("Area_Phone", n.getPhone());
			// jo.put("Area_Remark", n.getRemark());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	// 有水表的区域
	private void getAllWaterImportantArea(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		AreaDao ad = new AreaDao();
		List<Area> list = ad.getWaterImportantArea();

		JSONArray json = new JSONArray();
		for (Area n : list)
		{
			JSONObject jo = new JSONObject();
			jo.put("Area_ID", n.getId());
			// jo.put("Area_Num", n.getNum());
			jo.put("Area_Name", n.getName());
			// jo.put("Area_Address", n.getAddress());
			// jo.put("Area_Phone", n.getPhone());
			// jo.put("Area_Remark", n.getRemark());
			json.add(jo);
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
