package com.sf.energy.expert.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.expert.dao.EnergyMonitorDao;
import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.GetSystemInfo;

public class TotalAmServlet extends HttpServlet {

	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		
		try {
			findTotalAmmethod(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}
	
	
	private void findTotalAmmethod(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		String method = request.getParameter("method");
		
		if ("getTotalAmFusionchart".equalsIgnoreCase(method))
		{
			getTotalAmFusionchart(request, response);
		}
		
		if ("IsTotalAmFusionchart".equalsIgnoreCase(method))
		{
			IsTotalAmFusionchart(request, response);
		}
	}
	
	private	void  getTotalAmFusionchart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException
	{
		 	PrintWriter out = response.getWriter();
		 	Date start = new Date();
	        Date end = new Date();
	    
	        String InfoStyle = "";
	        String InfoID = "";
	        String AmmeterList = "";
	        
	        String TotalAmTime1 = request.getParameter("time");
	        if (TotalAmTime1 != null)
	        {
	            int year1 = Integer.parseInt(TotalAmTime1.substring(0, 4));
	            int month1 = Integer.parseInt(TotalAmTime1.substring(5, 7));
	            int day1 = Integer.parseInt(TotalAmTime1.substring(8, 10));

	            start.setYear(year1 - 1900);
	            start.setMonth(month1 - 1);
	            start.setDate(day1 - 2);
	            start.setHours(-1);
	            start.setMinutes(0);
	            start.setSeconds(0);

	            end.setYear(year1 - 1900);
	            end.setMonth(month1 - 1);
	            end.setDate(day1);
	        }
	      
	        //System.out.println(start);
	        //System.out.println(end);
	        
	        InfoStyle = request.getParameter("InfoStyle");
	        InfoID = request.getParameter("InfoID");
	        
	        EnergyMonitorDao edDao = new EnergyMonitorDao();
	        
	        AmmeterList = edDao.getAmmeterList(InfoStyle, InfoID);
	        
	        if((""+0).equals(AmmeterList))		//说明没有总表
	        {
	        	//System.out.println("没有总表");
	        }
	        else				//	有总表情形
	        {
	        	String AmmeterListArray[]=AmmeterList.split(",");	//得到电表ID数组
	        	List<AmMeterMataData>	list_temp = null;
	   	        
	   	        list_temp = edDao.getTemperatureHourValue(start, end);
	   			
	   	   
	   	        ElecReportHelperImpl rhi_TotalAm = new ElecReportHelperImpl();
	   	        List<AmMeterMataData> TotalAm_list = null;
	   	        Map<Integer, Float> map = new HashMap<Integer, Float>();
	   	        JSONObject jo = new JSONObject();
	   	        JSONArray json = new JSONArray();
	   	        String hour;
	   	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	   	        //System.out.println("长度"+AmmeterListArray.length);
	   	        if(AmmeterListArray.length != 0)		//电表不为空
	   	        {
	   	        	//System.out.println("数据"+AmmeterListArray[0]);
	   	            TotalAm_list = rhi_TotalAm.getAmMeterCountBetween(AmmeterListArray, start,end);
//	   	            for (AmMeterMataData n : TotalAm_list)
//	   	            {
//	   	                map.put(n.getHour(), n.getValue());
//	   	            }
	   	            long t = start.getTime();
	   	            String key;
	   	            int startTime=GetSystemInfo.getWorkTimeForXML().get("startTime");
	   	            int endTime=GetSystemInfo.getWorkTimeForXML().get("endTime");
	   	            Date d = new Date();
	   	            for (int i = 0; i < 72; i++)
	   	            {
	   	                t += 3600000;
	   	                d.setTime(t);
	   	                key = format.format(d);
	   	                jo = new JSONObject();
	   	                hour = getHourString(d);
	   	                jo.put("recordTimeDate", hour);
	   	                jo.put("startWorkTime", startTime);
	   	                jo.put("endWorkTime", endTime);
	   	                jo.put("startQueryTime", format.format(start).substring(0,10));
	   	                jo.put("endQueryTime", format.format(end).substring(0,10));
	   	                jo.put("value",TotalAm_list.get(i).getValue());
	   	                jo.put("temperature", list_temp.get(i).getTemperature());
	   	                jo.put("Flag", 1);
	   	                json.add(jo);
	   	            }
 
	   	            //System.out.println(json.toString());

	   	            out.println(json.toString());
	   	        }
	   	        else
	   	        {
	   	        	int startTime=GetSystemInfo.getWorkTimeForXML().get("startTime");
	   	            int endTime=GetSystemInfo.getWorkTimeForXML().get("endTime");
	   	            for(int i = 0; i < 72; i++)
	   	            {
	   	            	jo.put("startWorkTime", startTime);
		                jo.put("endWorkTime", endTime);
		                jo.put("Flag", 0);
		                json.add(jo);
	   	            }
	   	             
	   	        	out.println(json.toString());
	   	        }
	        }
	                
	     
	        out.flush();
	        out.close();
	}
	
	//判断是否显示总用电
	private  void  IsTotalAmFusionchart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		PrintWriter out = response.getWriter();
		 String InfoStyle = "";
	     String InfoID = "";
	     String AmmeterList = "";
		
		InfoStyle = request.getParameter("InfoStyle");
        InfoID = request.getParameter("InfoID");
        
        EnergyMonitorDao edDao = new EnergyMonitorDao();
        
        AmmeterList = edDao.getAmmeterList(InfoStyle, InfoID);
        
        if((""+0).equals(AmmeterList))		//说明没有总表
        {
        	//System.out.println("没有总表");
        	out.println(0);
        }
        else				//	有总表情形
        {       			
			out.println(1);
        }
        
        out.flush();
        out.close();
	}

	private String getHourString(Date recordTimeDate){
		SimpleDateFormat df=new SimpleDateFormat("HH:00");
		String hour=df.format(recordTimeDate);
		return hour;
	}
	
}
