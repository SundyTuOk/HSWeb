package com.sf.energy.waternet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.model.WatermeterModel;

import com.sf.energy.water.statistics.model.WaterMeterMataData;
import com.sf.energy.waternet.dao.WaterBalanceDao;

public class WaterBalanceServlet extends HttpServlet
{

	

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
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
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}

	private void findMethod(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException, ParseException
	{
		String method = req.getParameter("method");
		//System.out.println("method:" + method);

		if ("getTreeData".equals(method))
			getTreeData(req, resp);
		if ("getFusionChartData".equals(method))
			getFusionChartData(req, resp);
		if ("getBigWaterMeterTreeData".equals(method))
			getBigWaterMeterTreeData(req, resp);
	}

	/**
	 * 获得fusionChart的数据
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getFusionChartData(HttpServletRequest req,
			HttpServletResponse resp) throws ParseException, SQLException,
			IOException
	{
		WaterBalanceDao info = new WaterBalanceDao();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String WaterMeterID = req.getParameter("WaterMaterID");
		String checkTime = req.getParameter("CheckDate");
		if (checkTime == null || "".equals(checkTime))
		{
			Date now = new Date();
			checkTime = dateFormat.format(now);
		}
		HashMap<String, String> enterWaterMap = info
				.get24EnterDataOfwaterBalance(WaterMeterID, checkTime);
		HashMap<String, String> exportWaterMap = info
				.get24ExportDataOfwaterBalance(WaterMeterID, checkTime);
		HashMap<String, String> missWaterAnalysisMap = info
				.analysisBalanceForMissWater(WaterMeterID, enterWaterMap,
						exportWaterMap);
		HashMap<String, String> pHLAnalysisMap = info
				.analysisBalanceForPingHengLv(WaterMeterID, enterWaterMap,
						exportWaterMap);
		JSONArray json = new JSONArray();

		JSONObject jo = new JSONObject();
		// 添加子孙的个数
		jo.put("childCount", exportWaterMap.get("count"));
		json.add(jo);
		// 组建进水量的json
		JSONArray jsonEnterWater = new JSONArray();
		for (int i = 0; i < 24; i++)
		{
			jo = new JSONObject();
			String key = String.valueOf(i);
			String value = enterWaterMap.get(key);
			jo.put(i, value);
			jsonEnterWater.add(jo);
		}
		json.add(jsonEnterWater);
		// 组建出水量的json
		JSONArray jsonExportWater = new JSONArray();
		for (int i = 0; i < 24; i++)
		{
			jo = new JSONObject();
			String key = String.valueOf(i);
			String value = exportWaterMap.get(key);
			jo.put(i, value);
			jsonExportWater.add(jo);
		}
		json.add(jsonExportWater);
		// 组建水量差的json
		JSONArray JsonMissWater = new JSONArray();
		for (int i = 0; i < 24; i++)
		{
			jo = new JSONObject();
			String key = String.valueOf(i);
			String value = missWaterAnalysisMap.get(key);
			jo.put(i, value);
			JsonMissWater.add(jo);
		}
		json.add(JsonMissWater);
		// 组建水量差的json
		JSONArray JsonPHLAnalysis = new JSONArray();
		for (int i = 0; i < 24; i++)
		{
			jo = new JSONObject();
			String key = String.valueOf(i);
			String value = pHLAnalysisMap.get(key);
			jo.put(i, value);
			JsonPHLAnalysis.add(jo);
		}
		json.add(JsonPHLAnalysis);

		//System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		System.out.println(json.toString());
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 生成tree控制器
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getTreeData(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		WaterBalanceDao info = new WaterBalanceDao();
		
		String root = info.getTreeRootNodeInfoData();

		List<WatermeterModel> list = info.getTreeInfoData();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		jo = new JSONObject();
		jo.put("root", root);
		json.add(jo);

		for (WatermeterModel n : list)
		{
			jo = new JSONObject();

			// //System.out.println(id+"  "+parent_id+"  "+name);
			jo.put("id", n.getWATERMETER_ID());
			jo.put("parent_id", n.getPARENT_ID());
			jo.put("name", n.getWATERMETER_NAME());

			json.add(jo);
		}
		// //System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
//		System.out.println(json.toString());
		out.flush();
		out.close();
	}
	/**
	 * 生成tree控制器
	 * 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getBigWaterMeterTreeData(HttpServletRequest req, HttpServletResponse resp)
			throws SQLException, IOException
	{
		WaterBalanceDao info = new WaterBalanceDao();
		
		String root = info.getTreeRootNodeInfoData();

		List<WatermeterModel> list = info.getBigWaterMeterTreeInfoData();

		JSONArray json = new JSONArray();
		JSONObject jo = null;

		jo = new JSONObject();
		jo.put("root", root);
		json.add(jo);

		for (WatermeterModel n : list)
		{
			jo = new JSONObject();
			// //System.out.println(id+"  "+parent_id+"  "+name);
			jo.put("id", n.getWATERMETER_ID());
			jo.put("parent_id", n.getPARENT_ID());
			jo.put("name", n.getWATERMETER_NAME());
			json.add(jo);
		}
	//	System.out.println(json.toString());
		resp.setContentType("application/x-json");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
	//	System.out.println(json.toString());
		out.flush();
		out.close();
	}
}
