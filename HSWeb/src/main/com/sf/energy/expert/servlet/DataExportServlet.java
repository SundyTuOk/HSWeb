package com.sf.energy.expert.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.DataExportDao;
import com.sf.energy.expert.model.AmmeterExportModel;
import com.sf.energy.expert.model.ExpertDetailExportModel;
import com.sf.energy.statistics.dao.AmHelperImpl;

public class DataExportServlet extends HttpServlet
{
	private void findMethod(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		if ("showContent".equals(method))
			showContent(request, response);
		if ("exportContent".equals(method))
			exportContent(request, response);	
		if ("loadPriceInfo".equals(method))
			loadPriceInfo(request, response);	
	}

	private void loadPriceInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DataExportDao exportDao = new DataExportDao();
		String data = exportDao.queryPriceInfo().toString();
		PrintWriter out = response.getWriter();
		out.write(data);
		out.flush();
		out.close();
	}

	private void exportContent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DecimalFormat df = new DecimalFormat("#0.00");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		DataExportDao exportDao = new DataExportDao();
		int ArcID = -1; // 表示选择选择所有建筑
		int AreaID = -1; // 表示选择所有区域
		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";
		int type = 0;
		if (request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if (request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if (request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if (request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}

		if (arcId != null)
		{
			ArcID = Integer.parseInt(arcId);
		}

		if (areaId != null)
		{
			AreaID = Integer.parseInt(areaId);
		}
		if(request.getParameter("type")!=null){	
			type = Integer.parseInt(request.getParameter("type"));
		}
		
		List<ExpertDetailExportModel> listExport=new LinkedList<ExpertDetailExportModel>();
		listExport=	exportDao.queryExportDataInfo(AreaID, ArcID, st, et, type);
		String filePath = "/img/expertArchAmDetail.xls";
		String XlsPath = this.getServletContext().getRealPath(filePath);
		// System.out.println("XlsPath:"+XlsPath);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		String title1 = "所属部门";
		if(type==2){
			title1 = "电费类型";
		}
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(title1);
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("起始日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("终止日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("用电量（千瓦时）");
		cell.setCellStyle(style);
		
		int i = 0;
		float n = 0;
		for (ExpertDetailExportModel sdm : listExport)
		{
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getType_Name());
			row.createCell((short) 1).setCellValue(st);
			row.createCell((short) 2).setCellValue(et);
			if(sdm.getZgross()!=-1){
				row.createCell((short) 3).setCellValue(df.format(sdm.getZgross()));
			}else{
				row.createCell((short) 3).setCellValue("--");
			}
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

	private void showContent(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		int ArchID = -1; // 表示选择选择所有建筑
		int AreaID = -1; // 表示选择所有区域
		String arcId = "";
		String areaId = "";
		String st = "";
		String et = "";
		String count = "";
		String index = "";
		int type = 0;
		if (request.getParameter("aid") != null)
		{
			arcId = request.getParameter("aid");
		}
		if (request.getParameter("areaid") != null)
		{
			areaId = request.getParameter("areaid");
		}
		if (request.getParameter("st") != null)
		{
			st = request.getParameter("st");
		}
		if (request.getParameter("et") != null)
		{
			et = request.getParameter("et");
		}
		if (arcId != null)
		{
			ArchID = Integer.parseInt(arcId);
		}
		if (areaId != null)
		{
			AreaID = Integer.parseInt(areaId);
		}
		if (request.getParameter("thePageCount") != null)
		{
			count = request.getParameter("thePageCount");
		}
		if (request.getParameter("thePageIndex") != null)
		{
			index = request.getParameter("thePageIndex");
		}
		if(request.getParameter("type")!=null){	
			type = Integer.parseInt(request.getParameter("type"));
		}
		DataExportDao exportDao = new DataExportDao();
		List<ExpertDetailExportModel> detail_result = null;
		int totalCount = 0;
		int thePageCount = Integer.parseInt(count);
		int thePageIndex = Integer.parseInt(index);
		detail_result = exportDao.queryShowDataInfo(AreaID, ArchID, thePageCount, thePageIndex, st, et, type);
		totalCount = exportDao.getTotalCount();
		String result = new Gson().toJson(new Object[]{totalCount,detail_result});
		//System.out.println(result);
		out.write(result);
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(request, response);
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
