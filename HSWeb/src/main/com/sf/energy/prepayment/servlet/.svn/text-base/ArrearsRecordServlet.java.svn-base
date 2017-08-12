package com.sf.energy.prepayment.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.prepayment.dao.APWaterAddMoneyDao;
import com.sf.energy.prepayment.model.YuGouDetailsModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class ArrearsRecordServlet extends HttpServlet
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

			e.printStackTrace();
		} catch (Exception e)
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

	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String method = req.getParameter("method");

		// 查询分合闸信息
		if ("getAmArrearsRecordsInfo".equals(method))
			getAmArrearsRecordsInfo(req, resp);

		if ("getWmArrearsRecordsInfo".equals(method))
			getWmArrearsRecordsInfo(req, resp);

		if ("exportArrearsRecords".equals(method))
			exportArrearsRecords(req, resp);

		if ("amArrearsRecordsSMSInfo".equals(method))
			amArrearsRecordsSMSInfo(req, resp);

		if ("wmArrearsRecordsSMSInfo".equals(method))
			wmArrearsRecordsSMSInfo(req, resp);
	}

	private void wmArrearsRecordsSMSInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String selectInfo = "";

		String type = req.getParameter("type");

		if ("notAll".equals(type))
		{
			String ids = req.getParameter("ids").trim();
			ids = ids.substring(1);
			selectInfo += "meter|" + ids;
		} else
		{
			selectInfo += "all|";
		}

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		APWaterAddMoneyDao dao = new APWaterAddMoneyDao();
		ArrayList<YuGouDetailsModel> list = dao.queryWaterSaleInfo(0, 0, selectInfo, "2", false, "Architecture_Name", "asc");
		String errNameList = "";
		int successNum = 0;
		for (YuGouDetailsModel model : list)
		{
			String tel = dao.getTelNum(model.getAmMeter_ID(), "watermeter", "watermeter_id");
			/*
			 * jo=new JSONObject(); jo.put("tel", tel.split(",")[0]);
			 * jo.put("content"
			 * ,tel.split(",")[1]+" 先生/女士：您的水费已欠费："+(-Float.parseFloat
			 * (model.getThisRemainMoney()))+"元"); json.add(jo);
			 */
			String phone = tel.split(",")[0];
			if (phone == null || "".equals(phone) || "null".equals(phone))
			{// 不存在电话号码 不插入，记录失败
				if ("".equals(errNameList))
				{
					errNameList = model.getAmMeter_Name();
				} else
				{
					errNameList = errNameList + "," + model.getAmMeter_Name();
				}
			} else
			{// 插入数据库 成功
				String userLoginName = (String) req.getSession().getAttribute(
						"userName");
				OperationLogInterface log = new OperationLogImplement();
				// 写入日志
				log.writeLog(userLoginName, "欠费记录", "发送水表欠费信息,用户："+model.getAmMeter_Name());
				
				String content = tel.split(",")[1] + " 用户，您好：您的水费已欠费：" + (Math.abs(Float.parseFloat(model.getThisRemainMoney()))) + "元";
				boolean b = dao.insertSMSWill(phone, content, userID);
				if (b)
				{
					successNum++;
				}
			}
		}
		jo.put("totalCount", list.size());
		jo.put("successNum", successNum);
		jo.put("errNameList", errNameList);

		json.add(jo);
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void amArrearsRecordsSMSInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		HttpSession session = req.getSession();
		String userID = "1";
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		String selectInfo = "";

		String type = req.getParameter("type");

		if ("notAll".equals(type))
		{
			String ids = req.getParameter("ids").trim();
			ids = ids.substring(1);
			selectInfo += "meter|" + ids;
		} else
		{
			selectInfo += "all|";
		}

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();

		APWaterAddMoneyDao dao = new APWaterAddMoneyDao();
		ArrayList<YuGouDetailsModel> list = dao.queryAmmeterGDInfo(0, 0, selectInfo, "1", false, "Architecture_Name", "asc");
		int successNum = 0;
		String errNameList = "";
		for (YuGouDetailsModel model : list)
		{
			/*
			 * String
			 * tel=dao.getTelNum(model.getAmMeter_ID(),"ammeter","ammeter_id");
			 * jo=new JSONObject();
			 * 
			 * jo.put("tel", tel.split(",")[0]);
			 * jo.put("content",tel.split(",")[
			 * 1]+" 先生/女士：您的电费已欠费："+(-Float.parseFloat
			 * (model.getThisRemainMoney()))+"元"); json.add(jo);
			 */
			String tel = dao.getTelNum(model.getAmMeter_ID(), "ammeter", "ammeter_id");
			String phone = tel.split(",")[0];
			if (phone == null || "".equals(phone) || "null".equals(phone))
			{// 不存在电话号码 不插入，记录失败
				String aaa = model.getAmMeter_Name();
				if ("".equals(errNameList))
				{
					errNameList = model.getAmMeter_Name();
				} else
				{
					errNameList = errNameList + "," + model.getAmMeter_Name();
				}
			} else
			{// 插入数据库 成功
				String userLoginName = (String) req.getSession().getAttribute(
						"userName");
				OperationLogInterface log = new OperationLogImplement();
				// 写入日志
				log.writeLog(userLoginName, "欠费记录", "发送电表欠费信息,用户："+model.getAmMeter_Name());
				
				String content = tel.split(",")[1] + " 用户，您好：您的电费已欠费：" + (Math.abs(Float.parseFloat(model.getThisRemainMoney()))) + "元";
				boolean b = dao.insertSMSWill(phone, content, userID);
				if (b)
				{
					successNum++;
				}
			}

		}
		jo.put("totalCount", list.size());
		jo.put("successNum", successNum);
		jo.put("errNameList", errNameList);

		json.add(jo);
		
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	private void exportArrearsRecords(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException
	{
		// int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		// int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		String selectInfo = "";
		String queryMsg = "";

		String meterType = req.getParameter("meterType");
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

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

		APWaterAddMoneyDao dao = new APWaterAddMoneyDao();

		ArrayList<YuGouDetailsModel> list = null;
		if ("1".equals(meterType))
		{
			list = dao.queryAmmeterGDInfo(0, 0, selectInfo, meterType, false, tableName, order);
		} else
		{
			list = dao.queryWaterSaleInfo(0, 0, selectInfo, meterType, false, tableName, order);
		}

		// ArrayList<YuGouDetailsModel> list =
		// dao.queryWaterAndAmmeterSaleInfo(0, 0, selectInfo, "0", false,
		// "Architecture_Name", "asc");

		String filePath = "/img/ArrearsRecords.xls";
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

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("建筑");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("楼层");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("结算时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("欠费类型");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("欠费金额");
		cell.setCellStyle(style);

		int i = 0;
		for (YuGouDetailsModel sdm : list)
		{
			i++;
			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getArchitecture_Name());
			row.createCell((short) 1).setCellValue(sdm.getFloorName());
			row.createCell((short) 2).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 3).setCellValue(sdm.getReadTime());
			if ("1".equals(meterType))
			{
				row.createCell((short) 4).setCellValue("电表欠费");
			} else
			{
				row.createCell((short) 4).setCellValue("水表欠费");
			}
			// row.createCell((short) 4).setCellValue(sdm.getOwe());
			row.createCell((short) 5).setCellValue(sdm.getThisRemainMoney());
		}

		FileOutputStream fout = new FileOutputStream(XlsPath);
		wb.write(fout);
		fout.close();

		resp.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		json.put("filePath", filePath);

		PrintWriter pw = resp.getWriter();
		pw.print(json.toString());

	}

	private void getAmArrearsRecordsInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		String selectInfo = "";
		String queryMsg = "";

		String meterType = req.getParameter("meterType");
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

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

		APWaterAddMoneyDao dao = new APWaterAddMoneyDao();
		ArrayList<YuGouDetailsModel> list = dao.queryAmmeterGDInfo(thePageCount, thePageIndex, selectInfo, meterType, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);
		for (YuGouDetailsModel model : list)
		{
			json.add(model);
		}
		resp.setContentType("application/x-json");

		// System.out.println(json.toString());
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();
	}

	private void getWmArrearsRecordsInfo(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("Pagecount"));
		int thePageIndex = Integer.parseInt(req.getParameter("Pageindex"));
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		String selectInfo = "";
		String queryMsg = "";

		String meterType = req.getParameter("meterType");
		String tableName = req.getParameter("tableName");
		String order = req.getParameter("order");

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

		APWaterAddMoneyDao dao = new APWaterAddMoneyDao();
		ArrayList<YuGouDetailsModel> list = dao.queryWaterSaleInfo(thePageCount, thePageIndex, selectInfo, meterType, true, tableName, order);

		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);

		for (YuGouDetailsModel model : list)
		{
			json.add(model);
		}
		resp.setContentType("application/x-json");

		// System.out.println(json.toString());
		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}
}
