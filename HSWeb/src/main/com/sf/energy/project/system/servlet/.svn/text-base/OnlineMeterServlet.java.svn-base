package com.sf.energy.project.system.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.equipment.serviceImpl.AmmeterSearchImpl;
import com.sf.energy.project.system.dao.DepartmentDao;
import com.sf.energy.project.system.maintenance.OnlineMeter;
import com.sf.energy.project.system.model.DataSite;
import com.sf.energy.project.system.model.Department;
import com.sf.energy.project.system.model.OnlineMeterModel;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class OnlineMeterServlet extends HttpServlet
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

		if ("getMeterState".equals(method))
			getMeterState(request, response);
//		if ("getOnlineMeter".equals(method))
//			getOnlineMeter(request, response);
		if ("onlineMeterPaginate".equals(method))
			onlineMeterPaginate(request, response);
		
		if ("onlineMeterSort".equals(method))
			onlineMeterSort(request, response);
		if ("export".equals(method))
			try
			{
				export(request, response);
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
	
	private void onlineMeterSort(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		OnlineMeter olm = new OnlineMeter();
		int areaID = -1;
		if(request.getParameter("AreaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("AreaID"));
		}
		int archID = -1;
		if(request.getParameter("ArchID") != null)
		{
			archID = Integer.parseInt(request.getParameter("ArchID"));
		}
		int isOnline = -1;
		if(request.getParameter("IsOnline") != null)
		{
			isOnline = Integer.parseInt(request.getParameter("IsOnline"));
		}
		Integer thePageCount = Integer.parseInt(request
				.getParameter("pageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("pageIndex"));
		
		String order = "order by " + request.getParameter("TableName") + " " + request.getParameter("Order");
		
		ArrayList<OnlineMeterModel> lst = olm.sortQuery(areaID, archID, isOnline, thePageCount, thePageIndex, order);
		
		//OnlineMeterModel omm = new OnlineMeterModel();
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", olm.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		
		for (OnlineMeterModel omm : lst)
		{
			//omm = lst.get(i);
			jo = new JSONObject();
			jo.put("meterName", omm.getMeterName());
			jo.put("meteStyleName", omm.getMeteStyleName());
			jo.put("partmentText", omm.getPartmentText());
			jo.put("gatherName", omm.getGatherName());
			jo.put("meter485Address", omm.getMeter485Address());
			jo.put("lastTime", omm.getLastTime());
			jo.put("meterState", omm.getMeterState());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

	private void getMeterState(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException
	{
		OnlineMeter olm = new OnlineMeter();
		String queryType = "";
		if(request.getParameter("QueryType") != null)
		{
			queryType = request.getParameter("QueryType");
		}
		int id = -1;
		if(request.getParameter("ID") != null)
		{
			id = Integer.parseInt(request.getParameter("ID"));
		}
		int isOnline = -1;
		if(request.getParameter("IsOnline") != null)
		{
			isOnline = Integer.parseInt(request.getParameter("IsOnline"));
		}
		Integer thePageCount = Integer.parseInt(request
				.getParameter("pageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("pageIndex"));
		
		ArrayList<OnlineMeterModel> lst = olm.queryMeterState(queryType, id, isOnline,thePageCount,thePageIndex);

		//OnlineMeterModel omm = new OnlineMeterModel();

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", olm.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		for (OnlineMeterModel omm : lst)
		{
			//omm = lst.get(i);
			jo = new JSONObject();
			jo.put("meterName", omm.getMeterName());
			jo.put("meteStyleName", omm.getMeteStyleName());
			jo.put("partmentText", omm.getPartmentText());
			jo.put("gatherName", omm.getGatherName());
			jo.put("meter485Address", omm.getMeter485Address());
			jo.put("lastTime", omm.getLastTime());
			jo.put("meterState", omm.getMeterState());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}
	
//	private void getOnlineMeter(HttpServletRequest request,
//			HttpServletResponse response) throws IOException, SQLException
//	{
//		int areaID = -1;
//		if(request.getParameter("AreaID") != null)
//		{
//			areaID = Integer.parseInt(request.getParameter("AreaID"));
//		}
//		int archID = -1;
//		if(request.getParameter("ArchID") != null)
//		{
//			archID = Integer.parseInt(request.getParameter("ArchID"));
//		}
//		int isOnline = -1;
//		if(request.getParameter("IsOnline") != null)
//		{
//			isOnline = Integer.parseInt(request.getParameter("IsOnline"));
//		}
//		
//		ArrayList<OnlineMeterModel> lst = olm.queryMeterIsOnline(areaID, archID, isOnline);
//
//		OnlineMeterModel omm = new OnlineMeterModel();
//		JSONArray json = new JSONArray();
//		for (int i = lst.size() - 1; i >= 0; i--)
//		{
//			omm = lst.get(i);
//			JSONObject jo = new JSONObject();
//			jo.put("meterName", omm.getMeterName());
//			jo.put("meteStyleName", omm.getMeteStyleName());
//			jo.put("partmentText", omm.getPartmentText());
//			jo.put("gatherName", omm.getGatherName());
//			jo.put("meter485Address", omm.getMeter485Address());
//			jo.put("lastTime", omm.getLastTime());
//			jo.put("meterState", omm.getMeterState());
//			json.add(jo);
//		}
//
//		response.setContentType("application/x-json");
//
//		PrintWriter out = response.getWriter();
//		out.println(json.toString());
//		out.flush();
//		out.close();
//	}
	
	private void onlineMeterPaginate(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		OnlineMeter olm = new OnlineMeter();
		int areaID = -1;
		if(request.getParameter("AreaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("AreaID"));
		}
		int archID = -1;
		if(request.getParameter("ArchID") != null)
		{
			archID = Integer.parseInt(request.getParameter("ArchID"));
		}
		int isOnline = -1;
		if(request.getParameter("IsOnline") != null)
		{
			isOnline = Integer.parseInt(request.getParameter("IsOnline"));
		}
		Integer thePageCount = Integer.parseInt(request
				.getParameter("pageCount"));
		Integer thePageIndex = Integer.parseInt(request
				.getParameter("pageIndex"));
		
		ArrayList<OnlineMeterModel> lst = olm.queryMeterIsOnline(areaID, archID, isOnline,thePageCount,thePageIndex);
		
		//OnlineMeterModel omm = new OnlineMeterModel();
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", olm.getTotalCount());
		// jo.put("pageCount", ad.getRecordCount());
		// jo.put("pageIndex", ad.getRecordCount());
		// jo.put("currentCount", ad.getRecordCount());
		json.add(jo);
		
		for (OnlineMeterModel omm : lst)
		{
			//omm = lst.get(i);
			jo = new JSONObject();
			jo.put("meterName", omm.getMeterName());
			jo.put("meteStyleName", omm.getMeteStyleName());
			jo.put("partmentText", omm.getPartmentText());
			jo.put("gatherName", omm.getGatherName());
			jo.put("meter485Address", omm.getMeter485Address());
			jo.put("lastTime", omm.getLastTime());
			jo.put("meterState", omm.getMeterState());
			json.add(jo);
		}

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
		
	}
	private void export(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, RowsExceededException,
			WriteException
	{
		

		OnlineMeter olm = new OnlineMeter();
		int areaID = -1;
		if(request.getParameter("areaID") != null)
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		int archID = -1;
		if(request.getParameter("archID") != null)
		{
			archID = Integer.parseInt(request.getParameter("archID"));
		}
		int isOnline = -1;
		if(request.getParameter("IsOnline") != null)
		{
			isOnline = Integer.parseInt(request.getParameter("IsOnline"));
		}
		
		String order = "order by " + request.getParameter("SortLabel") + " " + request.getParameter("SortType");
		
		ArrayList<OnlineMeterModel> lst = olm.exportQuery(areaID, archID, isOnline, order);


		File file = getExportOnlineMeterFile(lst);

		FileInputStream fis = new FileInputStream(file);
		byte[] fb = new byte[fis.available()];
		fis.read(fb);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String("在线设备导出文件.xls".getBytes("gb2312"), "iso8859-1"));

		ByteArrayInputStream bais = new ByteArrayInputStream(fb);
		ServletOutputStream sos = response.getOutputStream();
		int b;
		while ((b = bais.read()) != -1)
		{
			sos.write(b);
		}
		bais.close();

		sos.flush();
		sos.close();
		fis.close();

		// 要关闭所用的流才能成功删除文件，否则删除失败，导致临时文件堆积
		file.delete();
	}
	private File getExportOnlineMeterFile(List<OnlineMeterModel> amList)
			throws RowsExceededException, WriteException, IOException
	{
		
		ExportHelper dh = ExportHelper.getInstance();
		File file = null;
		String[] theTitles =
		{ "设备名称", "表计类型", "隶属机构", "表计地址", "网关名称",
				"网关地址", "网关最后通信时间", "网关登录IP",
				"网关在线状态", "表计通信时间",
				"表计通信状态"};
		List<String> firstLine = new LinkedList<String>();

		List<List<String>> data = new LinkedList<List<String>>();

		// 将标题加进去
		for (String title : theTitles)
		{
			firstLine.add(title);
		}

		data.add(firstLine);

		// 将查询结果数据加进去
		for (int i = 0; i < amList.size(); i++)
		{
			OnlineMeterModel am = amList.get(i);
			List<String> item = new LinkedList<String>();

			// 设备名称
			item.add(am.getMeterName());

			// 表计类型
			item.add(am.getMeteStyleName());

			// 隶属机构
			item.add(am.getPartmentText());

			// 表计地址
			item.add(am.getMeter485Address());

			// 网关名称
			item.add(am.getGatherName());

			// 网关地址
			item.add(am.getGatherAddress());

			// 网关最后通信时间
			item.add(am.getGatherLASTTIME());

			// 网关登录IP
			item.add(am.getGatherIP());


			// 网关在线状态
			item.add(am.getGatherState());

			// 表计通信时间
			item.add(am.getLastTime());

			// 表计通信状态
			item.add(am.getMeterState());

			
			data.add(item);
		}
		file = dh.getExportFile(data);

		return file;
	}
}
