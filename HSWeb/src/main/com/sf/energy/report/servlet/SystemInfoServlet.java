package com.sf.energy.report.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.report.dao.SystemInfoDao;
import com.sf.energy.report.model.SystemInfoModel;

public class SystemInfoServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{

		String method = req.getParameter("method");
		
		if ("getSystemInfo".equals(method))
		{
			try
			{
				getSystemInfo(req, resp);
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if ("updateSystemInfo2".equals(method))
		{
			try
			{
				updateSystemInfo2(req, resp);
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else
		{
			try
			{
				updateSystemInfo(req, resp);
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void getSystemInfo(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		SystemInfoDao systemInfoDao = new SystemInfoDao();
		SystemInfoModel model = systemInfoDao.querySystemInfo2(1);
		JSONArray json=new JSONArray();
		resp.setContentType("text/html;charset=utf-8");
		if (model != null)
		{
			JSONObject jo = new JSONObject();
			
			jo.put("xznum", model.getSystemInfo_xznum());
			jo.put("complayname", model.getSystemInfo_complayname());
			jo.put("complaynum", model.getSystemInfo_complaynum());
			jo.put("complayshot", model.getSystemInfo_complayshot());
			jo.put("log", model.getSystemInfo_lo());
			jo.put("introduction", model.getComplayintroduction());
			json.add(jo);
			JSONObject jo2 = new JSONObject();
			jo2.put("result", "1");
			json.add(jo2);
			PrintWriter pw = resp.getWriter();
			pw.print(json.toString());
		} else
		{
			
			JSONObject jo = new JSONObject();
			jo.put("result", "0");
			PrintWriter pw = resp.getWriter();
			pw.print(json.toString());
		}
	}

	private void updateSystemInfo2(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		SystemInfoModel systemInfoModel = null;
		systemInfoModel = new SystemInfoModel();
		String SystemInfo_Logo = req.getParameter("SystemInfo_Logo");
		String xznum = req.getParameter("xznum");
		String complayname = req.getParameter("complayname");
		String complaynum = req.getParameter("complaynum");
		String complayshot = req.getParameter("complayshot");
		String COMPLAYINTRODUCTION = req.getParameter("COMPLAYINTRODUCTION");

		// //System.out.println(SystemInfo_Logo + " " + xznum + " " + complayname
		// + " " + complaynum
		// + " " + complayshot + " " + COMPLAYINTRODUCTION);

		systemInfoModel.setSystemInfo_lo(Integer.parseInt(SystemInfo_Logo));
		systemInfoModel.setSystemInfo_xznum(xznum);
		systemInfoModel.setSystemInfo_complayname(complayname);
		systemInfoModel.setSystemInfo_complaynum(complaynum);
		systemInfoModel.setSystemInfo_complayshot(complayshot);
		systemInfoModel.setComplayintroduction(COMPLAYINTRODUCTION);

		SystemInfoDao systemInfoDao = new SystemInfoDao();
		boolean ifUpdate = systemInfoDao.updateSystemInfo2(1, systemInfoModel);
		resp.setContentType("text/html;charset=utf-8");
		if (ifUpdate)
		{
			JSONObject json = new JSONObject();
			json.put("result", "更新成功");
			PrintWriter pw = resp.getWriter();
			pw.print(json.toString());
		} else
		{
			JSONObject json = new JSONObject();
			json.put("result", "更新失败");
			PrintWriter pw = resp.getWriter();
			pw.print(json.toString());
		}

	}

	private void updateSystemInfo(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		SystemInfoModel systemInfoModel = null;
		systemInfoModel = new SystemInfoModel();
		String xznum = req.getParameter("xznum");
		String complayname = req.getParameter("complayname");
		String complaynum = req.getParameter("complaynum");
		String complayshot = req.getParameter("complayshot");
		String enreportserver = req.getParameter("enreportserver");
		int enreportport = Integer.parseInt(req.getParameter("enreportport"));
		systemInfoModel.setSystemInfo_xznum(xznum);
		systemInfoModel.setSystemInfo_complayname(complayname);
		systemInfoModel.setSystemInfo_complaynum(complaynum);
		systemInfoModel.setSystemInfo_complayshot(complayshot);
		systemInfoModel.setEnreportserver(enreportserver);
		systemInfoModel.setEnreportport(enreportport);
		SystemInfoDao systemInfoDao = new SystemInfoDao();
		systemInfoDao.updateSystemInfo(1, systemInfoModel);

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("xznum", xznum);
		json.put("complayname", complayname);
		json.put("complaynum", complaynum);
		json.put("complayshot", complayshot);
		json.put("enreportserver", enreportserver);
		json.put("enreportport", enreportport);
		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}
}
