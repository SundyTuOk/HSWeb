package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.formula.Area2DPtgBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.current.dao.ArcLoseLineDao;
import com.sf.energy.powernet.dao.LoseLineDao;
import com.sf.energy.project.system.dao.AreaDao;
import com.sf.energy.project.system.model.Area;

public class ArcloseLineServlet extends HttpServlet
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
		// //System.out.println("method:" + method);

		if ("getDataInfo".equals(method))
			getDataInfo(req, resp);

	}

	private void getDataInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		Integer thePageCount = Integer.parseInt(req.getParameter("ArcloseLinePageCount"));
		Integer thePageIndex = Integer.parseInt(req.getParameter("ArcloseLinePageIndex"));

//		String parentID = req.getParameter("parentID").trim();
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		int ammeter_ID=0;
		int archID=0;
		int areaID=0; 
		if(req.getParameter("archid")!=null){
			archID=Integer.parseInt(req.getParameter("archid"));
		}
		if(req.getParameter("areaid")!=null){
			areaID=Integer.parseInt(req.getParameter("areaid"));
		}
		if(req.getParameter("ammeterid")!=null){
			ammeter_ID=Integer.parseInt(req.getParameter("ammeterid"));
		}
		JSONArray json = new JSONArray();
		if(archID==-1||(archID==0&&areaID==0&&ammeter_ID==0)){
			json = getSchoolInfo( startTime, endTime, thePageCount, thePageIndex);
		}else if(areaID!=0){
			json=getAreaInfo(areaID,startTime,endTime);
		}else if(archID!=0&&archID!=-1){
			json=getArchInfo(archID,startTime,endTime);
		}else if(ammeter_ID!=0){
			json=getAmmeterInfo(ammeter_ID,startTime,endTime);
		}
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
//		System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private JSONArray getAmmeterInfo(int ammeter_ID, String startTime, String endTime) throws SQLException
	{
		ArcLoseLineDao control=new ArcLoseLineDao();
		List<Integer> child=control.getChildren(ammeter_ID);
		JSONArray json = new JSONArray();
		JSONObject countjo = new JSONObject();
//		// 计算总数
		countjo.put("totalCount", "1");
		int meterCount = 0;
		countjo.put("meterCount", "0");

		// 查询父亲的信息，生成一个jo 加入json
		JSONObject parentjo = new JSONObject();

		parentjo = control.getInfo(ammeter_ID, startTime, endTime);
		meterCount++;
		countjo.put("meterCount", meterCount);

		json.add(parentjo);
		float totalValue = 0;
		if (!"--".equals(parentjo.get("useValue").toString()))
			totalValue = Float.parseFloat(parentjo.get("useValue").toString());
		// 获得该父亲下面的所有的孩子的信息
		float childrenValue = 0;
		if(child!=null){
			for (int i = 0; i < child.size(); i++)
			{
				JSONObject joInfo = null;
				if (child.get(i) == null)
					continue;
				else
				{
	//				pID = Integer.parseInt(child[i]);
					joInfo = control.getInfo(child.get(i), startTime, endTime);
					if (!"--".equals(joInfo.get("useValue").toString()))
						childrenValue += Float.parseFloat(joInfo.get("useValue").toString());
	
					json.add(joInfo);
					meterCount++;
					countjo.put("meterCount", meterCount);
	
				}
			}
		}
		// 处理表下端的数据
		JSONObject jo = new JSONObject();
		jo.put("childrenValue", childrenValue);
		float loseValue = totalValue - childrenValue;
		jo.put("loseValue", loseValue);
		if (totalValue == 0)
		{
			jo.put("loselV", "--");
		} else
		{
			float loselV = loseValue / totalValue * 100;
			jo.put("loselV", loselV);
		}
		json.add(jo);

		json.add(countjo);

		return json;
	}
	private JSONArray getArchInfo(int archID, String startTime, String endTime) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		AreaDao areaDao = new AreaDao();
		List<Integer> countMeterList = areaDao.CountMeter(archID);
		jo = new JSONObject();
		jo.put("totalCount", countMeterList.size());
		json.add(jo);
		for (int i = 0; i < countMeterList.size(); i++)
		{
			JSONArray jsonModel = new JSONArray();
			jsonModel = getAmmeterInfo(countMeterList.get(i), startTime, endTime);
			json.add(jsonModel);
		}
		return json;
	}
	private JSONArray getAreaInfo(int areaID, String startTime, String endTime) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		AreaDao areaDao = new AreaDao();
		List<Integer> archList = areaDao.CountMeterArch(areaID);
		jo = new JSONObject();
		jo.put("totalCount", archList.size());
		json.add(jo);
		for (int i = 0; i < archList.size(); i++)
		{
			JSONArray jsonModel = new JSONArray();
			jsonModel = getArchInfo(archList.get(i), startTime, endTime);
			json.add(jsonModel);
		}
		return json;
	}
	private JSONArray getSchoolInfo(String startTime, String endTime, int PageCount, int PageIndex) throws SQLException
	{
		JSONArray json = new JSONArray();
		JSONObject jo = null;
		AreaDao areaDao = new AreaDao();
		List<Integer> areaList = areaDao.CountMeterArea();
		jo = new JSONObject();
		jo.put("totalCount", areaList.size());
		json.add(jo);

		int j = PageCount * PageIndex;
		int count = 0;
		for (int i = j; i < areaList.size(); i++)
		{
			JSONArray jsonModel = new JSONArray();
			jsonModel = getAreaInfo(areaList.get(i), startTime, endTime);
			json.add(jsonModel);
			count++;
			if (count >= PageCount)
			{
				break;
			}
		}
		return json;

	}

}
