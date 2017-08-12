package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.dao.WatermeterManualDao;
import com.sf.energy.expert.model.WatermeterManual;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.system.dao.ArchitectureDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class WatermeterManualManage extends HttpServlet
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
			e.printStackTrace();
		} catch (ParseException e)
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
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		String method = request.getParameter("method");

		if ("getWmmeterManualByUser".equals(method))
			getWmmeterManualByUser(request, response);

		if ("deleteWmmeterManual".equals(method))
			deleteWmmeterManual(request, response);

		if ("addWmmetersManual".equals(method))
			addWmmetersManual(request, response);

		if ("updateWmmetersManual".equals(method))
			updateWmmetersManual(request, response);

		if ("deleteSomeWmManual".equals(method))
			deleteSomeWmManual(request, response);

		if ("parginate".equals(method))
			parginate(request, response);

	}

	private void deleteSomeWmManual(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表手工录入", "批量删除水表手工录入记录");

		String DataIDList = null;
		if (request.getParameter("DataIDList") != null)
			DataIDList = request.getParameter("DataIDList");
		else
			return;

		String[] strList = DataIDList.split(",");
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
		if (wmd.deleteSomeWmManualByDataID(list))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void addWmmetersManual(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表手工录入", "新增单条水表手工录入记录");

		WatermeterManual am = new WatermeterManual();

		int meterID = 0;
		if (request.getParameter("MeterID") != null
				&& DataValidation.isNumber(request.getParameter("MeterID")))
			meterID = Integer.parseInt(request.getParameter("MeterID"));
		else
			return;
		am.setMeterID(meterID);

		// WatermeterModel wm = wmmeterDao.getWatermeterByID(meterID);
		//
		// am.setArchID(wm.getARCHITECTURE_ID());
		//
		// am.setAreaID(wm.getAREA_ID());

		Date valueTime = new Date();
		am.setValueTime(valueTime);

		float zValue = 0;
		if (request.getParameter("ZValue") != null
				&& DataValidation.isNumber(request.getParameter("ZValue")))
			zValue = Float.parseFloat(request.getParameter("ZValue"));
		am.setzValue(zValue);

		float powerFactor = 0;
		if (request.getParameter("PowerFactor") != null
				&& DataValidation.isNumber(request.getParameter("PowerFactor")))
			powerFactor = Float.parseFloat(request.getParameter("PowerFactor"));
		am.setPowerFactorz(powerFactor);

		float zGross = 0;
		if (request.getParameter("ZGross") != null
				&& DataValidation.isNumber(request.getParameter("ZGross")))
			zGross = Float.parseFloat(request.getParameter("ZGross"));
		am.setzGross(zGross);

		float zMoney = 0;
		if (request.getParameter("ZMoney") != null
				&& DataValidation.isNumber(request.getParameter("ZMoney")))
			zMoney = Float.parseFloat(request.getParameter("ZMoney"));
		am.setzMoney(zMoney);

		String valyeTimeStr = null;
		if (request.getParameter("ValueTime") != null)
		{
			valyeTimeStr = request.getParameter("ValueTime");
			am.setValueTime(df.parse(valyeTimeStr));
		}

		Date insertTime = new Date();
		am.setInsertTime(insertTime);

		int userID = 0;
		if (request.getSession().getAttribute("userId") != null)
			userID = Integer.parseInt(request.getSession()
					.getAttribute("userId").toString());
		else
			return;
		am.setUserID(userID);

		String info = "fail";
		if (wmd.insertWmManual(am))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void updateWmmetersManual(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表手工录入", "编辑水表手工录入记录");

		WatermeterManual am;

		int dataID = 0;
		if (request.getParameter("DataID") != null
				&& DataValidation.isNumber(request.getParameter("DataID")))
			dataID = Integer.parseInt(request.getParameter("DataID"));
		else
			return;

		am = wmd.selectWmManualByDataID(dataID);

		if (am == null)
			return;

		int meterID = 0;
		if (request.getParameter("MeterID") != null
				&& DataValidation.isNumber(request.getParameter("MeterID")))
			meterID = Integer.parseInt(request.getParameter("MeterID"));
		else
			return;
		am.setMeterID(meterID);

		Date valueTime = new Date();
		am.setValueTime(valueTime);

		float zValue = 0;
		if (request.getParameter("ZValue") != null
				&& DataValidation.isNumber(request.getParameter("ZValue")))
			zValue = Float.parseFloat(request.getParameter("ZValue"));
		am.setzValue(zValue);

		float powerFactor = 0;
		if (request.getParameter("PowerFactor") != null
				&& DataValidation.isNumber(request.getParameter("PowerFactor")))
			powerFactor = Float.parseFloat(request.getParameter("PowerFactor"));
		am.setPowerFactorz(powerFactor);

		float zGross = 0;
		if (request.getParameter("ZGross") != null
				&& DataValidation.isNumber(request.getParameter("ZGross")))
			zGross = Float.parseFloat(request.getParameter("ZGross"));
		am.setzGross(zGross);

		float zMoney = 0;
		if (request.getParameter("ZMoney") != null
				&& DataValidation.isNumber(request.getParameter("ZMoney")))
			zMoney = Float.parseFloat(request.getParameter("ZMoney"));
		am.setzMoney(zMoney);

		String valyeTimeStr = null;
		if (request.getParameter("ValueTime") != null)
		{
			valyeTimeStr = request.getParameter("ValueTime");
			am.setValueTime(df.parse(valyeTimeStr));
		}

		int userID = 0;
		if (request.getSession().getAttribute("userId") != null)
			userID = Integer.parseInt(request.getSession()
					.getAttribute("userId").toString());
		else
			return;
		am.setUserID(userID);

		String info = "fail";
		if (wmd.updateWmManual(am))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void deleteWmmeterManual(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		
		OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");

		// 写入日志
		log.writeLog(userLoginName, "水表手工录入", "删除单条水表手工录入记录");

		int dataID = 0;
		if (request.getParameter("DataID") != null
				&& DataValidation.isNumber(request.getParameter("DataID")))
			dataID = Integer.parseInt(request.getParameter("DataID"));
		else
			return;

		String info = "fail";
		if (wmd.deleteWmManualByDataID(dataID))
			info = "success";

		PrintWriter out = response.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	private void getWmmeterManualByUser(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		int userID = 0;
		if (request.getSession().getAttribute("userId") != null)
			userID = Integer.parseInt(request.getSession()
					.getAttribute("userId").toString());
		else
			return;

		List<WatermeterManual> list = wmd.selectWmManualByUser(userID);

		if (list != null && list.size() > 0)
		{
			JSONArray main = new JSONArray();

			for (WatermeterManual am : list)
			{
				main.add(buildNode(am));
			}

			PrintWriter out = response.getWriter();
			out.println(main.toString());
			out.flush();
			out.close();
		}
	}

	private void parginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		WatermeterManualDao wmd = new WatermeterManualDao();
		int userID = 0;
		if (request.getSession().getAttribute("userId") != null)
			userID = Integer.parseInt(request.getSession()
					.getAttribute("userId").toString());
		else
			return;

		int areaID = -1;
		if (request.getParameter("AreaID") != null
				&& DataValidation.isNumber(request.getParameter("AreaID")))
			areaID = Integer.parseInt(request.getParameter("AreaID"));

		int archID = -1;
		if (request.getParameter("ArchID") != null
				&& DataValidation.isNumber(request.getParameter("ArchID")))
			archID = Integer.parseInt(request.getParameter("ArchID"));

		int meterID = -1;
		if (request.getParameter("MeterID") != null
				&& DataValidation.isNumber(request.getParameter("MeterID")))
			meterID = Integer.parseInt(request.getParameter("MeterID"));

		String sortLable = "DataID";
		if (request.getParameter("SortLable") != null)
			sortLable = request.getParameter("SortLable");

		boolean isAsc = false;
		String type = "Asc";
		if (request.getParameter("SortType") != null)
		{
			type = request.getParameter("SortType");

			if (type.equalsIgnoreCase("Asc"))
				isAsc = true;

			if (type.equalsIgnoreCase("Desc"))
				isAsc = false;
		}

		int pageCount = 10;
		if (request.getParameter("PageCount") != null
				&& DataValidation.isNumber(request.getParameter("PageCount")))
			pageCount = Integer.parseInt(request.getParameter("PageCount"));

		int pageIndex = 0;
		if (request.getParameter("PageIndex") != null
				&& DataValidation.isNumber(request.getParameter("PageIndex")))
			pageIndex = Integer.parseInt(request.getParameter("PageIndex"));

		List<WatermeterManual> list = null;
		int count = 0;

		Map<String, Object> result = wmd.parginate(userID, areaID, archID,
				meterID, sortLable, isAsc, pageCount, pageIndex);

		if (result != null)
		{
			list = (List<WatermeterManual>) result.get("List");
			count = (int) result.get("TotalCount");
		}

		JSONArray main = new JSONArray();
		JSONObject total = new JSONObject();
		total.put("TotalCount", count);
		total.put("TotalPage", Math.ceil(count / pageCount));
		total.put("Index", pageIndex);
		total.put("SortLable", sortLable);
		total.put("SortType", type);
		main.add(total);

		if (list != null && list.size() > 0)
		{
			for (WatermeterManual am : list)
			{
				main.add(buildNode(am));
			}
		}

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}

	private JSONObject buildNode(WatermeterManual am)
	{
		JSONObject jo = new JSONObject();

		jo.put("DataID", am.getDataID());
		jo.put("MeterID", am.getMeterID());
		jo.put("MeterName", am.getMeterName());
		jo.put("AreaName", am.getAreaName());
		jo.put("ArchName", am.getArchName());
		jo.put("ZValue", am.getzValue());
		jo.put("ZGross", am.getzGross());
		jo.put("PowerFactor", am.getPowerFactorz());
		jo.put("ZMoney", am.getzMoney());
		jo.put("InsertTime", df.format(am.getInsertTime()));
		jo.put("ValueTime", df.format(am.getValueTime()));
		jo.put("UserName", am.getUserName());

		return jo;
	}
}
