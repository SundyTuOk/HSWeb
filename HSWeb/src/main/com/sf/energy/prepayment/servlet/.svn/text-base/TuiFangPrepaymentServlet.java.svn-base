package com.sf.energy.prepayment.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.sf.energy.powernet.dao.BaoJingInfoDao;
import com.sf.energy.powernet.model.BaoJingInfoModel;
import com.sf.energy.prepayment.dao.AmMeterTuiFangDao;
import com.sf.energy.prepayment.dao.BaoJingInfoForPreDao;
import com.sf.energy.prepayment.dao.CMMeterDao;
import com.sf.energy.prepayment.model.AmMeterTuiFangModel;
import com.sf.energy.prepayment.model.VolumeSetModel;
import com.sf.energy.project.PDRoom.dao.PD_PartDao;
import com.sf.energy.project.PDRoom.model.PD_PartModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class TuiFangPrepaymentServlet extends HttpServlet
{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String method = req.getParameter("method");
		
		if("getDataForTuiFangInfoPre".equals(method))
			getDataForTuiFangInfoPre(req,resp);
		
		if("deleteTuiFangInfoPre".equals(method))
			deleteTuiFangInfo(req,resp);
		
		if("TuiFang_PutOut".equals(method))
			TuiFang_PutOut(req,resp);
		
	}
	
	private void TuiFang_PutOut(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException{
		
		
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		
		String selectInfo = "";
		
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
		
		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");

		AmMeterTuiFangDao dao = new AmMeterTuiFangDao();
		ArrayList<AmMeterTuiFangModel> list=dao.queryInfo_out(selectInfo,startTime,endTime,order);
		
		String filePath = "/img/TuiFangDetail.xls";
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
		cell.setCellValue("房间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("退房时间");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("退房金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("退房明细");
		cell.setCellStyle(style);

		int i = 0;
		for (AmMeterTuiFangModel sdm : list)
		{
			i++;

			row = sheet.createRow(i);
			row.createCell((short) 0).setCellValue(sdm.getAmMeter_Name());
			row.createCell((short) 1).setCellValue(sdm.getTuiFang_Time());
			row.createCell((short) 2).setCellValue(sdm.getTuiFang_Money());
			row.createCell((short) 3).setCellValue(sdm.getTuiFang_Detail());

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
	
	private void deleteTuiFangInfo(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, SQLException
	{
		String IDs=req.getParameter("delete_EMR_info_ID");
		String[] id=IDs.split("\\|");
		
		
		AmMeterTuiFangDao dao=new AmMeterTuiFangDao();
		// 统计删除插入条数
		int result = 0;

		for (int i = 0; i < id.length; i++)
		{
			if(dao.deleteTuiFangInfoByID(id[i]))
			{
				result++;
			}
		}
		OperationLogInterface log = new OperationLogImplement();
		HttpSession session = req.getSession();
		String userLoginName = (String) session.getAttribute("userName");
		log.writeLog(userLoginName, "预付费退房信息", "删除预付费退房信息");
		PrintWriter out = resp.getWriter();
		out.println("成功删除"+result+"条数据。");
		out.flush();
		out.close();
		

	}



	/**
	 *获得信息 
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getDataForTuiFangInfoPre(HttpServletRequest req,
			HttpServletResponse resp) throws SQLException, IOException
	{
		Integer thePageCount = Integer.parseInt(req
				.getParameter("cPageCount"));
		Integer thePageIndex = Integer.parseInt(req
				.getParameter("cPageIndex"));
		
		String startTime = req.getParameter("startTime").trim();
		String endTime = req.getParameter("endTime").trim();
		
		String SelectTreeFlag = req.getParameter("SelectTreeFlag");
		String SelectTreeID = req.getParameter("SelectTreeID");
		String SelectFloorID = req.getParameter("SelectFloorID");
		
		String selectInfo = "";
		
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
		
		String order = " order by " + req.getParameter("tableName") + " " + req.getParameter("order");

		AmMeterTuiFangDao dao = new AmMeterTuiFangDao();
		ArrayList<AmMeterTuiFangModel> list=dao.queryInfo(thePageCount,thePageIndex,selectInfo,startTime,endTime,order);
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("totalCount", dao.getTotalCount());
		json.add(jo);
		
		for(AmMeterTuiFangModel vsm : list)
		{
			jo = new JSONObject();
			jo.put("TUIFANG_ID", vsm.getTuiFang_ID());
			jo.put("AMMETER_NAME", vsm.getAmMeter_Name());
			jo.put("TUIFANG_TIME", vsm.getTuiFang_Time());
			jo.put("TUIFANG_MONEY", vsm.getTuiFang_Money());
			jo.put("TUIFANG_DETAIL", vsm.getTuiFang_Detail());
			json.add(jo);
		}
		resp.setContentType("application/x-json");

		PrintWriter out = resp.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}

	

	/**
	 * 树形列表信息获取
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	private void selectPD_Part(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		
		int treeStyle=Integer.parseInt(req.getParameter("flag"));
		
		resp.setContentType("text/html;charset=utf-8");  
		JSONObject json = new JSONObject(); 
		JSONArray array = new JSONArray();  
		JSONObject member = null;  
		int id=0;
		int parent_id=0;
		String name="";
	
		String PartStyle_ID="";
		List<PD_PartModel> list=new ArrayList<PD_PartModel>();
		PD_PartModel partModel=new PD_PartModel();
		 PD_PartDao partDao=new PD_PartDao();
		 
		 list=partDao.makeTree(treeStyle);
		 
		 Iterator<PD_PartModel> partIt=list.iterator(); 
		 while(partIt.hasNext())
		  {
			 member=new JSONObject();
		   partModel=partIt.next();
		   id=partModel.getPart_ID();
		   parent_id=partModel.getPart_Parent();
		   name=partModel.getPart_Name();
		  
		   PartStyle_ID=partModel.getPartStyle_ID();
		   
		 //  //System.out.println(id+"  "+parent_id+"  "+name);
		   member.put("id",id);
		   member.put("parent_id",parent_id);
		   member.put("name",name);
		
		   member.put("PartStyle_ID",PartStyle_ID);
		   array.add(member);
		  }
		 String root=partDao.getRoot();
		 json.put("root", root);
		 json.put("jsonArray", array);  
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString()); 
	}

}
