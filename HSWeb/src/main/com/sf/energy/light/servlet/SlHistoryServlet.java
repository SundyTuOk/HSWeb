package com.sf.energy.light.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




import com.sf.energy.light.dao.SlHistoryDao;
import com.sf.energy.light.model.SlHistoryModel;


public class SlHistoryServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");
		
		if("import".equals(flag))
		{
			importSlHistory(req, resp);
		}
		else if("loaddate".equals(flag))
		{
			try {
				loaddate(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if("deletearray".equals(flag))
		{
			try {
				deleteSlHistory(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void deleteSlHistory(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag=true;
	    boolean flag1=false;	
		 String[] idarray=req.getParameterValues("idarray[]");
		 SlHistoryDao slHistoryDao=new SlHistoryDao();
				for(int i=0;i<idarray.length;i++)
				{
					
					try {
						flag1=slHistoryDao.deleteSlHistoryall(Integer.parseInt(idarray[i]));
						flag=flag&flag1;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 String isSuccess="";
		    	 if(flag==true)
		    	 {
		    		 isSuccess="success";
		    	 }
		    	 else {
					isSuccess="failure";
				}
		    	 resp.setContentType("text/html;charset=utf-8");  
				 JSONObject json = new JSONObject(); 
				 json.put("isSuccess",isSuccess);	 
				 PrintWriter pw = resp.getWriter();   
				 pw.print(json.toString());  
				 pw.flush();
				 pw.close();	
	}
	
	/**
	 * 加载数据
	 * @param req
	 * @param resp
	 * @throws SQLException 
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private void loaddate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("SlHistoryPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("SlHistoryPageIndex"));
		
		int AreaID=Integer.parseInt(req.getParameter("SlHistoryAreaId"));
		int lineID=Integer.parseInt(req.getParameter("SlHistoryLineId"));
		String starttime=req.getParameter("SlHistoryStartTime");
		String endtime=req.getParameter("SlHistoryEndTime");
		String state=req.getParameter("SlHistorystate");
		String SlHistorysortName=req.getParameter("SlHistorysortName");
		String SlHistoryorder=req.getParameter("SlHistoryorder");
	
		//起始时间
		if("-1".equals(starttime))
		{
			Date date=new Date();
			int year=date.getYear()+1900;
			int month=date.getMonth()+1;
			starttime=year+"-"+month+"-01";
		}
		//结束时间
		if("-1".equals(endtime))
		{
			Date date=new Date();
			int year=date.getYear()+1900;
			int month=date.getMonth()+1;
			int day=date.getDate();
			endtime=year+"-"+month+"-"+day;
		}
		
		
		 List<SlHistoryModel> slHistoryList=new ArrayList<SlHistoryModel>();
		 SlHistoryDao slHistoryDao=new SlHistoryDao();
		 slHistoryList=slHistoryDao.querySlHistory(SlHistorysortName,SlHistoryorder,starttime,endtime,state,AreaID,lineID,thePageCount,thePageIndex);
		 
			JSONArray json = new JSONArray();
			JSONObject jototal =new JSONObject();
			jototal.put("totalCount", slHistoryDao.getTotalCount());
			json.add(jototal);
			
			for (SlHistoryModel slHistoryModel : slHistoryList)
			{
				JSONObject	jo = new JSONObject();
											
				jo.put("SLLine_Name", slHistoryModel.getSlLine_Name());	
				jo.put("slHistory_id",slHistoryModel.getSlHistory_Id());
				jo.put("SlHistory_Time", slHistoryModel.getSlHistory_Time());
				jo.put("SlHistory_State", slHistoryModel.getSlHistory_State());
				jo.put("Users_Id", slHistoryModel.getUsers_Name());
				jo.put("SlHistory_Style", slHistoryModel.getSlHistory_Style());
				jo.put("SlHistory_Opr", slHistoryModel.getSlHistory_Opr());
							
				json.add(jo);
			}
			 resp.setContentType("text/html;charset=utf-8");  

			 PrintWriter pw = resp.getWriter();  
			 pw.print(json.toString());  
			 pw.flush();
			 pw.close();
	}
	/**
	 * 导出
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private void importSlHistory(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		SlHistoryDao slHistoryDao=new SlHistoryDao();
		List<SlHistoryModel> list=new ArrayList<SlHistoryModel>();
		
		SlHistoryModel slHistoryModel=new SlHistoryModel();
		try {
			list=slHistoryDao.querySlHistoryall();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filePath="/img/lightHistory.xls";
		String XlsPath=this.getServletContext().getRealPath(filePath);
		
		
		// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("学生表一");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow((int) 0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue("路灯回路");
				cell.setCellStyle(style);
				
			    cell = row.createCell((short) 1);
				cell.setCellValue("执行时间");
				cell.setCellStyle(style);
				
				cell = row.createCell((short) 2);				
				cell.setCellValue("执行状态");
				cell.setCellStyle(style);	
				
				cell = row.createCell((short) 3);				
				cell.setCellValue("执行者");
				cell.setCellStyle(style);
				
				cell = row.createCell((short) 4);
				cell.setCellValue("执行类型");
				cell.setCellStyle(style);
				
				cell = row.createCell((short) 5);
				cell.setCellValue("操作");
				cell.setCellStyle(style);
				
				// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
				for (int i = 0; i < list.size(); i++)
				{
					
					row = sheet.createRow((int) i + 1);
					SlHistoryModel stu = (SlHistoryModel) list.get(i);
				
					////System.out.println(stu.getSlHistory_Time());
					// 第四步，创建单元格，并设置值
					row.createCell((short) 0).setCellValue(stu.getSlLine_Name());
				    row.createCell((short) 1).setCellValue(stu.getSlHistory_Time());
					row.createCell((short) 2).setCellValue(stu.getSlHistory_State());
					row.createCell((short) 3).setCellValue(stu.getUsers_Name());				
					row.createCell((short) 4).setCellValue((String) stu.getSlHistory_Style());	
					row.createCell((short) 5).setCellValue((String) stu.getSlHistory_Opr());
				}
				// 第六步，将文件存到指定位置
				try
				{    
					//输出路径
					//String xlspath=req.getParameter("xlspath")+"/img/lightHistory.xls";
				//	//System.out.println(xlspath);
					FileOutputStream fout = new FileOutputStream(XlsPath);
					wb.write(fout);					
					fout.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				//发送给前台
				 resp.setContentType("text/html;charset=utf-8");  
				 JSONObject json = new JSONObject(); 
				 json.put("filePath",filePath);
					
				 
				 PrintWriter pw = resp.getWriter();   
			     pw.print(json.toString());  
	} 
}
