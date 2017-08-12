package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.sts.token.delegation.HOKDelegationHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.project.system.dao.PumpDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.water.statistics.dao.WaterLoadTrendImpl;
import com.sf.energy.water.statistics.model.WaterLoadTrendData;

public class PumpEfficiencyAnalysisServlet extends HttpServlet {

	

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			PumpEfficiencyAnalysis( request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
	private void  PumpEfficiencyAnalysis(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int pump_id = 0;
		String time = "";
		
		String PressureList=new String();
		if(request.getParameter("pump_id") != null)
		{
			pump_id = Integer.parseInt(request.getParameter("pump_id"));
		}
		if(request.getParameter("time") != null)
		{
			time = request.getParameter("time");
		}
		PumpDao pd=new PumpDao();
		PressureList =pd.getPressureDevByPumpID(pump_id);
		float PumpXishu=pd.getPumpXishuByPumpID(pump_id);
		List<String> valuetimeList=new ArrayList<String>();
		
		valuetimeList=pd.queryDataValueTime(PressureList,time);
		
		JSONArray json = new JSONArray();
		JSONObject jo=new JSONObject();
		for(int i=0;i<valuetimeList.size();i++){
			String sql="select a.*,b.IsOutPressureDev from PressureDevDatas a,PressureDev b where a.pressureDev_id=b.PressureDev_id and a.PressureDev_ID in("
				+ PressureList + ") and a.valuetime= to_date('"+valuetimeList.get(i)+"','YYYY-MM-DD HH24:MI:SS')";
			Connection conn1 = null;
			PreparedStatement ps1 =null;
			ResultSet rs1 =null;
			try{
				conn1 = ConnDB.getConnection();
				ps1 = conn1.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				float outValue=0;
				float inValue=0;
				while (rs1.next())
				{
					if(rs1.getInt("IsOutPressureDev")==1){
						inValue+=rs1.getFloat("PressureValue");
					}else {
						outValue+=rs1.getFloat("PressureValue");
					}
				}
				if(outValue!=0&&inValue!=0){
					
					float hoverText=(outValue/inValue)/PumpXishu;
					jo=new JSONObject();
					jo.put("datatime", valuetimeList.get(i));
					jo.put("hoverText", hoverText);
					json.add(jo);
				}
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				ConnDB.release(conn1, ps1,rs1);
			}
			
		}

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	

}
