package com.sf.energy.light.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sf.energy.light.dao.SLAmContrastDao;
import com.sf.energy.light.dao.SLLineDao;
import com.sf.energy.light.model.SLAmContrastModel;

import com.sf.energy.light.model.SLLineModel;
import com.sf.energy.manager.current.dao.CurrentMeasureDao;
import com.sf.energy.manager.current.model.CurrentManagerModel;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.project.system.dao.AmmPriceDao;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.GetSystemInfo;

public class AmContrastServlet extends HttpServlet {

	DecimalFormat df = new DecimalFormat("#####0.00");
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		
		int areaID = -1;
		int huiluID = -1;
		
		if(request.getParameter("areaID") != null && request.getParameter("areaID") != "")
		{
			areaID = Integer.parseInt(request.getParameter("areaID"));
		}
		if(request.getParameter("huiluID") != null && request.getParameter("huiluID") != "")
		{
			huiluID = Integer.parseInt(request.getParameter("huiluID"));
			//System.out.println("回路ID"+huiluID);
		}		
		
		String method = request.getParameter("method");
		
		
		 if ("queryDayValue".equals(method))
	        {
	            try
	            {
	                queryDayValue(request, response);
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	        }

	        if ("queryMonthValue".equals(method))
	        {
	            try
	            {
	                queryMonthValue(request, response);
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
		
		if ("queryValueFromTable".equals(method))
		{
			try
			{
				queryValueFromTable(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if ("getAmMeterFusionChart".equals(method))
		{
            try
            {
                getAmMeterFusionChart(request, response);
            }
            catch (ParseException e)
            {

                e.printStackTrace();
            }
            catch (SQLException e)
            {

                e.printStackTrace();
            }
		}
		
		
		 if ("whenSelectedAllAreaFunction".equals(method))
	        {
			 	whenSelectedAllAreaFunction(request, response);
	        }
		 //路灯里面用电概况里回路的
        if ("whenSelectedAreainHuiluFunction".equals(method))
        {
        	whenSelectedAreainHuiluFunction(request, response);
        }
	
		
	}
	
	//72小时用电
	@SuppressWarnings("deprecation")
	private void getAmMeterFusionChart(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        Date start = new Date();
        Date end = new Date();
        int huiluID = 0;	//回路ID
        
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
        if(request.getParameter("huiluID") != null)
        {
        	 huiluID = Integer.parseInt(request.getParameter("huiluID"));
        }
        
        SLLineDao	sld = new SLLineDao();
		SLLineModel	 sm = new SLLineModel();
		sm = sld.queryByID(huiluID);
		
   
        ElecReportHelperImpl rhi_TotalAm = new ElecReportHelperImpl();
        List<AmMeterMataData> TotalAm_list = null;
        Map<String, Float> map = new HashMap<String, Float>();
        JSONObject jo = new JSONObject();
        JSONArray json = new JSONArray();
        String hour;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        
            TotalAm_list = rhi_TotalAm.getAmMeterBetween(sm.getAMMETER_ID(), start,end);
            for (AmMeterMataData n : TotalAm_list)
            {
                map.put(format.format(n.getRecordTimeDate()), n.getValue());
            }
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
                if (map.get(key) == null)
                {
                    jo.put("value", 0);
                }
                else
                {
                    jo.put("value", map.get(key));
                }
                json.add(jo);
            }

           
            //System.out.println(json.toString());

       
        out.println(json.toString());
        out.flush();
        out.close();
    }
	
	//上面表格的数据
	private void queryValueFromTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		int huiluID=0;
		
		if(request.getParameter("huiluID")!=null)
		{
			huiluID = Integer.parseInt(request.getParameter("huiluID"));
		}
		
		 CurrentMeasureDao cd = new CurrentMeasureDao();
	     Map<String, String> ammMap = new HashMap<String, String>();
		
		SLLineDao	sld = new SLLineDao();
		SLLineModel	 sm = new SLLineModel();
		sm = sld.queryByID(huiluID);
		
		AmmeterDao ad = new AmmeterDao();
		AmmPriceDao apd = new AmmPriceDao();
		AmmeterModel am = new AmmeterModel();
		
		am = ad.queryByID(sm.getAMMETER_ID());		//指定电表下的详细信息
		
		Calendar cal = Calendar.getInstance();// 使用日历类
		int selectYear = cal.get(Calendar.YEAR);// 得到年
		int selectMonth = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int selectDay = cal.get(Calendar.DAY_OF_MONTH);// 得到天

		ammMap = cd.getAmmValue(sm.getAMMETER_ID(), selectYear, selectMonth, selectDay);
	
		
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		
		 // 电表示数
        jo.put("ammValue", ammMap.get("ammValue"));
        jo.put("ammPower", ammMap.get("ammPower"));
        jo.put("ammLastTime", ammMap.get("ammLastTime"));

		jo.put("HuiluNum", sm.getSLLINE_NUM());//回路编号
		jo.put("HuiluName", sm.getSLLINE_NAME());//回路名称
		jo.put("ammNum", am.getAmmeterNum());		//电表编号
		jo.put("partmentName", am.getPartmentName());	//隶属机构
		jo.put("isComputation", am.getIsComputation());// 计量属性
		jo.put("useAmStyle", am.getUseAmStyleName());// 性质
		jo.put("styleName", am.getUseStyleName());// 分项
		jo.put("beilv", am.getBeilv());			//倍率
		jo.put("ammPrice", apd.queryByID(am.getPriceID()).getPriceValue());
		jo.put("costCheck", am.getCostCheck());
		jo.put("standByCheck", am.getStandByCheck());
		jo.put("ammAddress", am.getConsumerAddress());
		jo.put("archName", am.getArchName());
		jo.put("ammFloor", am.getFloor());
		jo.put("ammName", am.getAmmeterName());
		jo.put("ammArea", am.getAreaName());
		
		jo.put("ammState", am.getAmmeterStat());
		jo.put("ammTotalValue", (Double.parseDouble(ammMap.get("ammValue"))+am.getXiuzheng()));

		// 为了编辑输出的信息
		jo.put("ammID", am.getAmmeterID());
		jo.put("meteStyle", am.getMeterStyleName());// 表计类型
		jo.put("isOfflineWaring", am.getIsOffAlarm());// 报警
		jo.put("isTotalAmm", am.getIsCountMeter());
		jo.put("styleNameYJZX", am.getUseStyleNameYJZX());// 一级子项

		json.add(jo);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(json.toString());
		out.flush();
		out.close();

	}
	
	
	
	/**
	 * 		查询全校显示所有区域
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	   public void whenSelectedAllAreaFunction(HttpServletRequest request,
	            HttpServletResponse response) throws IOException
	    {
		   CurrentMeasureDao cd = new CurrentMeasureDao();
	        
	        ArrayList<CurrentManagerModel> list=new ArrayList<CurrentManagerModel>();
	        String sortName="Area_Name";
	        String order="asc";
	        String time = null;
	        
	        if(request.getParameter("sortName")!=null)
	        {
	        	sortName=request.getParameter("sortName");
	        }
	        if(request.getParameter("order")!=null)
	        {
	        	if(request.getParameter("order").equals("desc"))
	        	{
	        		order="desc";
	        	}
	        	else if(request.getParameter("order").equals("asc"))
	        	{
	        		order="asc";
				}
	        	
	        }
	        if(request.getParameter("time")!=null)
	        {
	        	time = request.getParameter("time");
	        }
	        int year = Integer.parseInt(time.substring(0, 4));// 得到年
	        int month = Integer.parseInt(time.substring(5, 7));// 得到月，因为从0开始的，所以要加1
	        int day = Integer.parseInt(time.substring(8, 10));// 得到天
	        
	        
	        
	        try
			{
				list=cd.listAllAreaData(sortName, order, year, month, day);
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
	        
	        Gson json =new Gson();
	        String result=json.toJson(list);
	        
	        response.setContentType("application/x-json");
	        PrintWriter out = response.getWriter();
	        //System.out.println("全校"+result);
	        out.println(result);
	        out.flush();
	        out.close();

	    }
	
	/**
	 * 查询区域下所有回路的用电情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void whenSelectedAreainHuiluFunction(HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        Integer thePageCount = Integer.parseInt(request.getParameter("AmContrastPageCount"));
        Integer thePageIndex = Integer.parseInt(request.getParameter("AmContrastPageIndex"));

        int areaID = Integer.parseInt(request.getParameter("areaID"));
        //System.out.println("区域ID"+areaID);
        String sortName="Area_Name";
        String order="asc";
        String time = null;
        
        if(request.getParameter("sortName")!=null)
        {
        	sortName=request.getParameter("sortName");
        }
        if(request.getParameter("order")!=null)
        {
        	if(request.getParameter("order").equals("desc"))
        	{
        		order="desc";
        	}
        	else if(request.getParameter("order").equals("asc"))
        	{
        		order="asc";
			}
        	
        }	
        if(request.getParameter("time")!=null)
        {
        	time = request.getParameter("time");
        }
       
        SLAmContrastDao sad = new SLAmContrastDao();
        ArrayList<SLAmContrastModel> list=new ArrayList<SLAmContrastModel>();
        int year = Integer.parseInt(time.substring(0, 4));// 得到年
        int month = Integer.parseInt(time.substring(5, 7));// 得到月，因为从0开始的，所以要加1
        int day = Integer.parseInt(time.substring(8, 10));// 得到天
        ////System.out.println("年2014"+year);
        ////System.out.println("月4"+month);
        ////System.out.println("日27"+day);
        try
		{
			list=sad.listAllHuiluData(sortName, order, areaID, year, month, day, thePageCount, thePageIndex);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
        
        int totalCount=sad.getTotal();
        
        Gson json =new Gson();
        String result=json.toJson(new Object[]{totalCount,list});
        
        response.setContentType("application/x-json");
        PrintWriter out = response.getWriter();
        //System.out.println("回路数据"+result);
        out.println(result);
        out.flush();
        out.close();
    }
	  /**
     * 从表中查询当天用电
     * @param request
     * @param response
     * @throws Exception
     */
    public void queryDayValue(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        CurrentMeasureDao cmd = new CurrentMeasureDao();
        AmmeterModel am = new AmmeterModel();
        AmmeterDao ad = new AmmeterDao();
        Map<String, String> map = new HashMap<String, String>();
        
        int huiluID = 0;
        if(request.getParameter("huiluID") != null)
        {
        	huiluID = Integer.parseInt(request.getParameter("huiluID"));
        }
        
        SLLineDao	sld = new SLLineDao();
		SLLineModel	 sm = new SLLineModel();
		sm = sld.queryByID(huiluID);
        
        
        String time=request.getParameter("time");
        int selectYear = Integer.parseInt(time.substring(0, 4));
        int selectMonth = Integer.parseInt(time.substring(5, 7));
        int selectDay = Integer.parseInt(time.substring(8, 10));

        am = ad.queryByID(sm.getAMMETER_ID());
        map = cmd.getAmmDayValue(sm.getAMMETER_ID(), selectYear, selectMonth, selectDay);

        if (am != null)
        {
            JSONArray json = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("dayValue", map.get("ZGross"));
            jo.put("dayMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
            json.add(jo);
            response.setContentType("application/x-json");
            PrintWriter out = response.getWriter();
            //System.out.println("日数据"+json.toString());
            out.println(json.toString());
            out.flush();
            out.close();
            
        }

    }

    /**
     * 从表中查询当月用电
     * @param request
     * @param response
     * @throws Exception
     */
    public void queryMonthValue(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        CurrentMeasureDao cmd = new CurrentMeasureDao();
        AmmeterModel am = new AmmeterModel();
        AmmeterDao ad = new AmmeterDao();
        Map<String, String> map = new HashMap<String, String>();
        
        int huiluID = 0;
        if(request.getParameter("huiluID") != null)
        {
        	huiluID = Integer.parseInt(request.getParameter("huiluID"));
        }
        
        SLLineDao	sld = new SLLineDao();
		SLLineModel	 sm = new SLLineModel();
		sm = sld.queryByID(huiluID);
        
        
        String time=request.getParameter("time");
        int selectYear = Integer.parseInt(time.substring(0, 4));
        int selectMonth = Integer.parseInt(time.substring(5, 7));
        int selectDay = Integer.parseInt(time.substring(8, 10));
        
        am = ad.queryByID(sm.getAMMETER_ID());
        map = cmd.getAmmMonthValue(sm.getAMMETER_ID(), selectYear, selectMonth);

        if (am != null)
        { 
            JSONArray json = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("monthValue", map.get("ZGross"));
            jo.put("monthMoney", df.format(Float.parseFloat(map.get("ZMoney"))));
            json.add(jo);
            response.setContentType("application/x-json");
            PrintWriter out = response.getWriter();
            //System.out.println("月数据"+json.toString());
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }
    
	private String getHourString(Date recordTimeDate){
		SimpleDateFormat df=new SimpleDateFormat("HH:00");
		String hour=df.format(recordTimeDate);
		return hour;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private String getTime(Date recordTimeDate) {
		Date d = new Date();
		d.setYear(recordTimeDate.getYear());
		d.setMonth(recordTimeDate.getMonth());
		d.setDate(recordTimeDate.getDate());
		d.setHours(recordTimeDate.getHours());
		d.setMinutes(0);
		d.setSeconds(0);
		return d.getTime() + "";
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doGet(request, response);
	}

}
