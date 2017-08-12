package com.sf.energy.water.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.util.ConnDB;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;
import com.sf.energy.water.manager.current.dao.WaterDataMaintenanceDao;
import com.sf.energy.water.statistics.model.WaterMeterMataData;

public class WaterDataMaintenanceManage extends HttpServlet
{
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            findMethod(request, response);

        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException,
            ParseException
    {
        String method = request.getParameter("method");

        if ("getWaterDataBetween".equals(method))
            getWaterDataBetween(request, response);

        if ("updateWaterData".equals(method))
            updateWaterData(request, response);
    }

    private void getWaterDataBetween(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	WaterDataMaintenanceDao wdmd = new WaterDataMaintenanceDao();
        int watermeterID = 0;
        if (request.getParameter("Watermeter_ID") != null)
            watermeterID = Integer.parseInt(request
                    .getParameter("Watermeter_ID"));

        Date start = null;
        if (request.getParameter("Start_Time") != null)
            start = df.parse(request.getParameter("Start_Time"));

        Date end = null;
        if (request.getParameter("End_Time") != null)
            end = df.parse(request.getParameter("End_Time"));

        List<WaterMeterMataData> list = wdmd.getWatermeterDataBetween(
                watermeterID, start, end);

        int tempDate = -1;
        JSONArray json = new JSONArray();
        if (list != null && list.size() != 0)
        {
        	int i=0;
            for (WaterMeterMataData a : list)
            {
            	i++;
                if (a.getRecordTimeDate().getDate() != tempDate)
                {
                    tempDate = a.getRecordTimeDate().getDate();
                    JSONObject jo = new JSONObject();
                    jo.put("Date", a.getRecordTimeDate().getDate());

                    JSONObject jo1 = new JSONObject();
                    jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
                    jo1.put("Time", dft.format(a.getRecordTimeDate()));
                    jo1.put("Value", a.getValue());
                    
                  //判断一个和最后一个数据是不是数据库里面的第一个和最后一个数据
                    if(list.size()==1){
                    	jo1.put("recordFirst", "1");
                    	jo1.put("recordLast", "1");
                    	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                    	if(b){
                    		jo1.put("isFirst", "1");
                    	}else{
                    		jo1.put("isFirst", "0");
                    	}
                    	b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                    	if(b){
                    		jo1.put("isLast", "1");
                    	}else{
                    		jo1.put("isLast", "0");
                    	}
                    }else{
                    	if(i==1 && i<list.size()){
                        	jo1.put("recordFirst", "1");
                        	jo1.put("recordLast", "0");
                        	
                        	//判断是不是第一个
                        	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isFirst", "1");
                        	}else{
                        		jo1.put("isFirst", "0");
                        	}
                        	jo1.put("isLast", "0");
                        }else if(i==1 && i==list.size()){
                        	jo1.put("recordFirst", "1");
                        	jo1.put("recordLast", "1");
                        	//判断是不是第一个
                        	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isFirst", "1");
                        	}else{
                        		jo1.put("isFirst", "0");
                        	}
                        	//判断是不是最后一个
                        	b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isLast", "1");
                        	}else{
                        		jo1.put("isLast", "0");
                        	}
                        }else if(i>1 && i==list.size()){
                        	jo1.put("recordFirst", "0");
                        	jo1.put("recordLast", "1");
                        	jo1.put("isFirst", "0");
                        	//判断是不是最后一个
                        	boolean b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isLast", "1");
                        	}else{
                        		jo1.put("isLast", "0");
                        	}
                        }
                        else{
                        	jo1.put("recordFirst", "0");
                        	jo1.put("recordLast", "0");
                        	jo1.put("isFirst", "0");
                        	jo1.put("isLast", "0");
                        }
                    }

                    JSONArray itemList = new JSONArray();
                    itemList.add(jo1);

                    jo.put("item", itemList);

                    json.add(jo);
                }
                else
                {
                    JSONObject jo1 = new JSONObject();
                    jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
                    jo1.put("Time", dft.format(a.getRecordTimeDate()));
                    jo1.put("Value", a.getValue());
                    
                  //判断一个和最后一个数据是不是数据库里面的第一个和最后一个数据
                    if(list.size()==1){
                    	jo1.put("recordFirst", "1");
                    	jo1.put("recordLast", "1");
                    	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                    	if(b){
                    		jo1.put("isFirst", "1");
                    	}else{
                    		jo1.put("isFirst", "0");
                    	}
                    	b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                    	if(b){
                    		jo1.put("isLast", "1");
                    	}else{
                    		jo1.put("isLast", "0");
                    	}
                    }else{
                    	if(i==1 && i<list.size()){
                        	jo1.put("recordFirst", "1");
                        	jo1.put("recordLast", "0");
                        	
                        	//判断是不是第一个
                        	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isFirst", "1");
                        	}else{
                        		jo1.put("isFirst", "0");
                        	}
                        	jo1.put("isLast", "0");
                        }else if(i==1 && i==list.size()){
                        	jo1.put("recordFirst", "1");
                        	jo1.put("recordLast", "1");
                        	//判断是不是第一个
                        	boolean b = isFirstOrLastValue(watermeterID, "F",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isFirst", "1");
                        	}else{
                        		jo1.put("isFirst", "0");
                        	}
                        	//判断是不是最后一个
                        	b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isLast", "1");
                        	}else{
                        		jo1.put("isLast", "0");
                        	}
                        }else if(i>1 && i==list.size()){
                        	jo1.put("recordFirst", "0");
                        	jo1.put("recordLast", "1");
                        	jo1.put("isFirst", "0");
                        	//判断是不是最后一个
                        	boolean b = isFirstOrLastValue(watermeterID, "L",a.getRecordTimeDate());
                        	if(b){
                        		jo1.put("isLast", "1");
                        	}else{
                        		jo1.put("isLast", "0");
                        	}
                        }
                        else{
                        	jo1.put("recordFirst", "0");
                        	jo1.put("recordLast", "0");
                        	jo1.put("isFirst", "0");
                        	jo1.put("isLast", "0");
                        }
                    }
                    
                    json.getJSONObject(json.size() - 1).getJSONArray("item")
                            .add(jo1);
                }

            }
        }

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    private void updateWaterData(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {  	WaterDataMaintenanceDao wdmd = new WaterDataMaintenanceDao();
        WaterMeterMataData amd = new WaterMeterMataData();
        WatermeterDao ammeterDao=new WatermeterDao();
        if (request.getParameter("Watermeter_ID") != null)
            amd.setWaterMeterID(Integer.parseInt(request
                    .getParameter("Watermeter_ID")));

        if (request.getParameter("Record_Time") != null)
            amd.setRecordTimeDate(df.parse(request.getParameter("Record_Time")));

        if (request.getParameter("Value") != null)
            amd.setValue(Float.parseFloat(request.getParameter("Value")));

        String info = "fail";

        if (wdmd.updateWatermeterData(amd)){
            info = "success";
            OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				log.writeLog(userLoginName, "水表数据维护", "修改水表数据！"+ammeterDao.queryNameByID(amd.getWaterMeterID())+"--"+amd.getRecordTimeDate()+"/"+amd.getValue());
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        PrintWriter out = response.getWriter();
        out.println(info.trim());
        out.flush();
        out.close();
    }
    
    public boolean isFirstOrLastValue(int waMeter_id, String type,Date time){
    	boolean b = false;
    	
    	String sql = "";
    	if("F".equals(type)){
    		sql = "select * from ZWaterDatas"+waMeter_id + " where valuetime<to_date('"+df.format(time)+"','yyyy-mm-dd hh24:mi:ss')";
    	}else if("L".equals(type)){
    		sql = "select * from ZWaterDatas"+waMeter_id + " where valuetime>to_date('"+df.format(time)+"','yyyy-mm-dd hh24:mi:ss')";
    	}
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()){
				b= false;
			}else{
				b=true;
			}
				
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			ConnDB.release(conn, ps, rs);
		}
    	
    	return b;
    }
    
}
