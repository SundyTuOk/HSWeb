package com.sf.energy.project.system.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jettison.json.JSONArray;

import com.sf.energy.expert.model.ExpertDetailExportModel;
import com.sf.energy.project.system.model.BaojingModel;
import com.sf.energy.project.system.service.dao.projectBaojingDao;

public class projectBaojingServlet extends HttpServlet
{
	private void findMethod(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		if("displayAllBaojingInfo".equals(method)){
			displayAllBaojingInfo(request,response);
		}
		
		if("exportAllBaojingInfo".equals(method)){
			exportAllBaojingInfo(request,response);
		}
		if("displayAllEQBaojingInfo".equals(method)){
			displayAllEQBaojingInfo(request,response);
		}
	
	}
	
	private void exportAllBaojingInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		String startTime = "";
		if(request.getParameter("startTime") != null)
		{
			startTime = request.getParameter("startTime");
		}
		String endTime = null;
		if(request.getParameter("endTime") != null)
		{
			endTime = request.getParameter("endTime");
		}
		String style = null;
		if(request.getParameter("style") != null)
		{
			style = request.getParameter("style");
		}
		projectBaojingDao dao = new projectBaojingDao();
		List<BaojingModel> list = null;
		list = dao.queryAllBaojingInfo(startTime, endTime, style);
		
		String filePath = "/img/BaojingInfo.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);
		// System.out.println("XlsPath:"+XlsPath);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("报警时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("报警类型");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("报警分类");
		cell.setCellStyle(style1);
		
		cell = row.createCell((short) 3);
		cell.setCellValue("表计名称");
		cell.setCellStyle(style1);
		
		cell = row.createCell((short) 4);
		cell.setCellValue("485地址");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("报警信息");
		cell.setCellStyle(style1);
		
		int i = 0;
		float n = 0;
		for (BaojingModel sdm : list)
		{
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getBaojingInfo_Time());
			row.createCell((short) 1).setCellValue(sdm.getBaojingInfo_Style());
			row.createCell((short) 2).setCellValue(sdm.getBaojingInfo_FL());
			row.createCell((short) 3).setCellValue(sdm.getMeter_Name());
			row.createCell((short) 4).setCellValue(sdm.getMeter_485Address());
			row.createCell((short) 5).setCellValue(sdm.getBaojingInfo_Title());		
		}
		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);
		PrintWriter pw = response.getWriter();
		pw.print(json.toString());	
	}

	private void displayAllEQBaojingInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		int offset = 0;
		if(request.getParameter("offset") != null)
		{
			offset = Integer.parseInt(request.getParameter("offset"));
		}

		int limit = 0;
		if(request.getParameter("limit") != null)
		{
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		String startTime = "";
		if(request.getParameter("startTime") != null)
		{
			startTime = request.getParameter("startTime");
		}
		String endTime = null;
		if(request.getParameter("endTime") != null)
		{
			endTime = request.getParameter("endTime");
		}
		int style = 0;
		if(request.getParameter("style") != null)
		{
			style = Integer.parseInt(request.getParameter("style"));
		}
		int code = 1;
		if(request.getParameter("code") != null)
		{
			code = Integer.parseInt(request.getParameter("code"));
		}
		int errorCode = style+code;
		projectBaojingDao dao = new projectBaojingDao();
		List<JSONObject> list = null;
		list = dao.queryAllEQBaojingInfo(offset, limit, startTime, endTime, errorCode);
		JSONObject object = new JSONObject();
		if(list==null){
			object.put("rows",  new JSONArray());
		}else{
			object.put("rows",  list);
		}
		object.put("total", dao.getEQCount(startTime, endTime, errorCode));	
		//System.out.println(object.toString());
		PrintWriter out = response.getWriter();
		out.println(object.toString());
		out.flush();
		out.close();
	
		
	}

	private void displayAllBaojingInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int offset = 0;
		if(request.getParameter("offset") != null)
		{
			offset = Integer.parseInt(request.getParameter("offset"));
		}

		int limit = 0;
		if(request.getParameter("limit") != null)
		{
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		String startTime = "";
		if(request.getParameter("startTime") != null)
		{
			startTime = request.getParameter("startTime");
		}
		String endTime = null;
		if(request.getParameter("endTime") != null)
		{
			endTime = request.getParameter("endTime");
		}
		String style = null;
		if(request.getParameter("style") != null)
		{
			style = request.getParameter("style");
		}
		projectBaojingDao dao = new projectBaojingDao();
		List<BaojingModel> list = null;
		list = dao.queryAllBaojingInfo(offset, limit, startTime, endTime, style);
		JSONObject object = new JSONObject();
		if(list==null){
			object.put("rows",  new JSONArray());
		}else{
			object.put("rows",  list);
		}
		object.put("total", dao.getCount(startTime, endTime, style));	
		//System.out.println(object.toString());
		PrintWriter out = response.getWriter();
		out.println(object.toString());
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		try
		{
			findMethod(request,response);
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doGet(request, response);
	}

}
