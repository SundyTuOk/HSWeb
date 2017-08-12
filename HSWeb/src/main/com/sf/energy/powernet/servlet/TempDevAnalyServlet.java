package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.TempDevDao;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TempDevAnalyServlet extends HttpServlet
{
	DecimalFormat df = new DecimalFormat("#####0.00");
	String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
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
		this.doGet(request, response);
	}

	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, RowsExceededException,
			WriteException
	{
		String method = request.getParameter("method");
		if ("showTempDev".equals(method))
			showTempDev(request, response);
	}

	private void showTempDev(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException
	{
		TempDevDao tdd=new TempDevDao();
		String partStyle_ID="";
		int part_ID=-1;
		String date="";
		// TODO Auto-generated method stub
		if (request.getParameter("styleID") != null)
			partStyle_ID=request.getParameter("styleID");
		if (request.getParameter("partID") != null
				&& request.getParameter("partID").matches(numberPatern)
				&& !"".equals(request.getParameter("partID")))
			part_ID=Integer.parseInt(request.getParameter("partID"));
		if (request.getParameter("date") != null)
			date=request.getParameter("date");
		HashMap<Integer,HashMap<String, String>> lists=new HashMap<Integer,HashMap<String,String>>();
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
       
		lists=tdd.getTempDevDatas(partStyle_ID, part_ID, date);
//		Iterator iterator = lists.keySet().iterator();
		for(int i=0;i<lists.size();i++) {
			HashMap<String, String> line= lists.get(i);
			 jo = new JSONObject();
			 jo.put("TempDev_ID", line.get("TempDev_ID"));
			 jo.put("ValueTime", line.get("ValueTime"));
			 jo.put("TempValue", line.get("TempValue"));
			 jo.put("VoltageTrans_Name", line.get("VoltageTrans_Name"));
			 json.add(jo);
		}
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

}
