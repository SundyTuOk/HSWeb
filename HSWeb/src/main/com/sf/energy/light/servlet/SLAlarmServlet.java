package com.sf.energy.light.servlet;

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

import com.sf.energy.light.dao.SLAlarmDao;
import com.sf.energy.light.dao.SlHistoryDao;
import com.sf.energy.light.model.SLAlarmModel;

public class SLAlarmServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flag=req.getParameter("flag");
		if("loaddate".equals(flag))
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
				deleteSlAlarm(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void deleteSlAlarm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		boolean flag=true;
	    boolean flag1=false;	
		 String[] idarray=req.getParameterValues("idarray[]");
		 SLAlarmDao slAlarmDao=new SLAlarmDao();
				for(int i=0;i<idarray.length;i++)
				{
				
					try {
						flag1=slAlarmDao.deleteSlAlarmall(Integer.parseInt(idarray[i]));
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
	 * 导入数据
	 * @param req
	 * @param resp
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private void loaddate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		int thePageCount = Integer.parseInt(req.getParameter("SLAlarmPageCount"));
		int thePageIndex = Integer.parseInt(req.getParameter("SLAlarmPageIndex"));
		
		int AreaID=Integer.parseInt(req.getParameter("SLAlarmAreaId"));
		int lineID=Integer.parseInt(req.getParameter("SLAlarmLineId"));
		String starttime=req.getParameter("SLAlarmStartTime");
		String endtime=req.getParameter("SLAlarmEndTime");
		String SLAlarmsortName=req.getParameter("SLAlarmsortName");
		String SLAlarmorder=req.getParameter("SLAlarmorder");
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
		
				
	
		List<SLAlarmModel> list=new ArrayList<SLAlarmModel>();
		SLAlarmDao alarmDao=new SLAlarmDao();
		list=alarmDao.querySlAlarm(SLAlarmsortName,SLAlarmorder,starttime, endtime, AreaID, lineID, thePageCount, thePageIndex);
		
		  JSONObject json =new JSONObject();
			 json.put("totalCount", alarmDao.getTotalCount());			
			 
			 JSONArray  jo=JSONArray.fromObject(list);			
			 json.put("jo",jo);
		  
		  
			 resp.setContentType("text/html;charset=utf-8");  

			 PrintWriter pw = resp.getWriter();  
			 pw.print(json.toString());  
			 pw.flush();
			 pw.close();
	}

}
