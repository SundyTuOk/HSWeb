package com.sf.energy.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.map.dao.AmMeterDao;
import com.sf.energy.map.dao.WaterMeterDao;
import com.sf.energy.map.dao.WriteXML;
import com.sf.energy.map.model.AmMeterModel;
import com.sf.energy.map.model.WaterMeterModel;

public class MapWaterServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String flag=req.getParameter("flag");
		 if("queryArcWaterName".equals(flag))
			{
				try {
					queryArcWaterName(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if("queryArcWaterrMes".equals(flag))
			{
				try {
					queryArcWaterrMes(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if("getWaterChat".equals(flag))
			{
				try {
					getWaterChat(req, resp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	private void getWaterChat(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String arcId=req.getParameter("ArcId");
		Date d=new Date();   
		String theYear=(d.getYear()+1900)+"";
		String theMonth=(d.getMonth()+1)+"";
		
		WaterMeterDao waterMeterDao=new WaterMeterDao();
		String waterChatXml=waterMeterDao.getMapArcWaterFenStyle(" ", arcId, theYear,theMonth);
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("waterChatXml",waterChatXml);	
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	
	
	//查看水表名称
	private void queryArcWaterName(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String arcId=req.getParameter("ArcId");
		String floor=req.getParameter("floor");
	
		WaterMeterDao waterMeterDao=new WaterMeterDao();
		List<WaterMeterModel> waterList=new ArrayList<WaterMeterModel>();		
		 
		waterList=waterMeterDao.queryWaterMeterByFloor(arcId,floor);
		
		 resp.setContentType("text/html;charset=utf-8");  
		 String jsonString=JSONArray.fromObject(waterList).toString();
		 
		 PrintWriter pw = resp.getWriter();   
		 pw.print(jsonString); 
	}
	
	//查看水表信息
	private void queryArcWaterrMes(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
	{
		String waterMeterId=req.getParameter("waterMeterId");
		WaterMeterDao waterMeterDao=new WaterMeterDao();
		WaterMeterModel waterMeterModel=new WaterMeterModel();
		waterMeterModel=waterMeterDao.queryWaterMeterById(waterMeterId);
		//获取水表示数
		int waterMeterValue=waterMeterDao.getWaterMeterRead(waterMeterId);
		//将水表信息写入到xml文档中
		AmMeterDao amMeterDao=new AmMeterDao();
		//获取图表时间
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//今天日期
		String nowdate=df.format(d);
		//昨天日期
		String lastdate=df.format(d.getTime()-24*60 * 60 * 1000);
		
		amMeterDao.carryPro("mapWaterMeterHour",Integer.parseInt(waterMeterId));
		String waterMeterStringXml=waterMeterDao.getMapArcWater48Hour(lastdate,nowdate);	
	
		
		
		//发送给前台
		 resp.setContentType("text/html;charset=utf-8");  
		 JSONObject json = new JSONObject(); 
		 json.put("waterMeter_Name",waterMeterModel.getWATERMETER_NAME());	
		 json.put("waterMeter_485Address",waterMeterModel.getWATERMETER_485ADDRESS());	
		 json.put("waterMeterValue",waterMeterValue);	
		 json.put("chatXml",waterMeterStringXml);
		 PrintWriter pw = resp.getWriter();   
	     pw.print(json.toString());  
	}
	
	

}
