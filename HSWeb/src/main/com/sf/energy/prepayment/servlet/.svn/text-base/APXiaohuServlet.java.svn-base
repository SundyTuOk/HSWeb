package com.sf.energy.prepayment.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.APXiaohuDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.model.APXiaohuModel;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class APXiaohuServlet extends HttpServlet
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
		} catch (RowsExceededException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e)
		{
			// TODO Auto-generated catch block
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
			HttpServletResponse response) throws SQLException, IOException,
			ParseException, RowsExceededException, WriteException
	{
		String method = request.getParameter("method");
		//System.out.println("method:" + method);

		if ("getAPXiaohuInfo".equals(method))
			getAPXiaohuInfo(request, response);
		
		if ("deletAPXiaohuInfo".equals(method))
			deletAPXiaohuInfo(request, response);
		
		if ("outAPXiaohuInfo".equals(method))
			outAPXiaohuInfo(request, response);
	}

	private void outAPXiaohuInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		APXiaohuDao apxd = new APXiaohuDao();
		
		String beginDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		//String selectStyle = request.getParameter("selectStyle");
		//int selectID = Integer.parseInt(request.getParameter("selectID"));
		String SelectTreeFlag = request.getParameter("SelectTreeFlag");
		String SelectTreeID = request.getParameter("SelectTreeID");
		String SelectFloorID = request.getParameter("SelectFloorID");
		
		String selectInfo = "";
		String queryMsg = beginDate + "|" + endDate;
		
		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}
		
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ArrayList<APXiaohuModel> list = new ArrayList<APXiaohuModel>();
		list = apxd.queryInfo(0, 0, selectInfo, queryMsg, order,false);

		
		String filePath="/img/APXiaohuInfo.xls";
		String XlsPath=this.getServletContext().getRealPath(filePath);
		//System.out.println("XlsPath:"+XlsPath);
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("区域");
		cell.setCellStyle(style);
		
	    cell = row.createCell((short) 1);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 2);				
		cell.setCellValue("楼层");
		cell.setCellStyle(style);	
		
		cell = row.createCell((short) 3);				
		cell.setCellValue("房间");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 4);
		cell.setCellValue("销户时间");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 5);
		cell.setCellValue("退电金额");
		cell.setCellStyle(style);
		
	    cell = row.createCell((short) 6);
		cell.setCellValue("销户前剩余电量");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 7);
		cell.setCellValue("销户前剩余金额");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 8);				
		cell.setCellValue("销户前电表数据");
		cell.setCellStyle(style);	
		
		cell = row.createCell((short) 9);				
		cell.setCellValue("操作员");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 10);				
		cell.setCellValue("销户状态");
		cell.setCellStyle(style);
		
		int i=0;
		for(APXiaohuModel apxm : list)
		{
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(apxm.getAreaName());
			row.createCell((short) 1).setCellValue(apxm.getArchitectureName());
			row.createCell((short) 2).setCellValue(apxm.getFloorName());
			row.createCell((short) 3).setCellValue(apxm.getAmMeterName());
			row.createCell((short) 4).setCellValue(apxm.getTheTime());
			row.createCell((short) 5).setCellValue(apxm.getTheMoney());
			
			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(apxm.getAmMeter_ID());
			if("1".equals(meterPotocol)){
				row.createCell((short) 6).setCellValue(apxm.getQSYValue());
				row.createCell((short) 7).setCellValue("-");
			}else {
				row.createCell((short) 6).setCellValue("-");
				row.createCell((short) 7).setCellValue(apxm.getQSYValue());				
			}
			
			row.createCell((short) 8).setCellValue(apxm.getQZValue());
			row.createCell((short) 9).setCellValue(apxm.getUserName());
			row.createCell((short) 10).setCellValue(apxm.getStatus());
			
		}
		
		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);					
		fout.close();
		
		response.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		json.put("filePath",filePath);
			
		PrintWriter pw = response.getWriter();   
	    pw.print(json.toString());  
		
	}

	private void deletAPXiaohuInfo(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, SQLException, IOException
	{
		APXiaohuDao apxd = new APXiaohuDao();
		String arrayOfID = request.getParameter("APXiaohu_ID");

		String[] IDs = null;
		IDs = arrayOfID.split("\\|");
		
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < IDs.length; i++)
		{
			String info = apxd.getXiaohuInfoByID(Integer.parseInt(IDs[i]));
			String userLoginName = (String) request.getSession().getAttribute(
					"userName");
			OperationLogInterface log = new OperationLogImplement();
			// 写入日志
			log.writeLog(userLoginName, "销户退电报表", "删除销户信息,"+info);
			
			if(apxd.delete(Integer.parseInt(IDs[i])))
			{
				result++;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		
	}

	private void getAPXiaohuInfo(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException
	{
		APXiaohuDao apxd = new APXiaohuDao();
		APXiaohuModel apxm = null;
		
		int thePageCount = Integer.parseInt(request.getParameter("thePageCount"));
		int thePageIndex = Integer.parseInt(request.getParameter("thePageIndex"));
		String beginDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		//String selectStyle = request.getParameter("selectStyle");
		//int selectID = Integer.parseInt(request.getParameter("selectID"));
		String SelectTreeFlag = request.getParameter("SelectTreeFlag");
		String SelectTreeID = request.getParameter("SelectTreeID");
		String SelectFloorID = request.getParameter("SelectFloorID");
		
		String selectInfo = "";
		String queryMsg = beginDate + "|" + endDate;
		
		if ("0".equals(SelectTreeFlag))
		{// 全校
			selectInfo += "all|";
		} else if ("1".equals(SelectTreeFlag))
		{// 区域
			selectInfo += "area|" + SelectTreeID;
		} else if ("2".equals(SelectTreeFlag))
		{// 建筑
			selectInfo += "arch|" + SelectTreeID;
		} else if ("3".equals(SelectTreeFlag))
		{// 楼层
			selectInfo += "floor|" + SelectTreeID + "|" + SelectFloorID;
		} else if ("4".equals(SelectTreeFlag))
		{// 电表
			selectInfo += "meter|" + SelectTreeID;
		}
		
		String order = " order by " + request.getParameter("tableName") + " " + request.getParameter("order");
		
		ArrayList<APXiaohuModel> list = new ArrayList<APXiaohuModel>();
		list = apxd.queryInfo(thePageCount, thePageIndex, selectInfo, queryMsg, order,true);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject(); 
		
		jo.put("totalCount", apxd.getTotalCount());
		json.add(jo);
		for(int i=0;i<list.size();i++)
		{
			jo = new JSONObject(); 
			apxm=list.get(i);
			
			CMMeterDao dao = new CMMeterDao();
			String meterPotocol = dao.getMeterPotocol(apxm.getAmMeter_ID());
			
			jo.put("meterPotocol", meterPotocol);
			jo.put("ID",apxm.getId());
			jo.put("Area_Name",apxm.getAreaName());
			jo.put("Architecture_Name",apxm.getArchitectureName());
			jo.put("Floor",apxm.getFloorName());
			jo.put("AmMeter_Name",apxm.getAmMeterName());
			jo.put("TheTime",apxm.getTheTime());
			jo.put("TheMoney",apxm.getTheMoney());
			jo.put("QSYValue",apxm.getQSYValue());
			jo.put("QZValue",apxm.getQZValue());
			jo.put("Users_Name",apxm.getUserName());
			jo.put("Status",apxm.getStatus());
			json.add(jo);
		}
		
		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		//System.out.println(json.size());
		out.println(json.toString());
		out.flush();
		out.close();
		
	}

}
