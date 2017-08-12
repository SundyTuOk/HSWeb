package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.sf.energy.manager.monitor.dao.AmmeterDataMaintenanceDao;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.system.dao.PlantaskExecuteDao;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class AmDataMaintenanceManage extends HttpServlet
{
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dft = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");

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

        if ("getAmDataBetween".equals(method))
            getAmDataBetween(request, response);
        if ("ShapingAmDataBetween".equals(method))
			ShapingAmDataBetween(request, response);
        
        if ("updateAmData".equals(method))
            updateAmData(request, response);
    }

    private void ShapingAmDataBetween(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException
	{
    	AmmeterDataMaintenanceDao admd = new AmmeterDataMaintenanceDao();
		int ammeterID = 0;
		if (request.getParameter("Ammeter_ID") != null
				&& request.getParameter("Ammeter_ID") != "")
			ammeterID = Integer.parseInt(request.getParameter("Ammeter_ID"));
		else {
			return;
		}
		double CBZY = 0.8;
		if(request.getParameter("CBYZ")!=null
				&& request.getParameter("CBYZ") != ""){
			CBZY = Double.parseDouble(request.getParameter("CBYZ"))/100;
		}
		Date start = null;
		if (request.getParameter("Start_Time") != null)
			start = df.parse(request.getParameter("Start_Time"));

		Date end = null;
		if (request.getParameter("End_Time") != null)
			end = df.parse(request.getParameter("End_Time"));
		//System.out.println(request.getParameter("Start_Time")+" "+request.getParameter("End_Time"));
		
		/**
		 * 测试时用到
		 * 删除数据
		 */
				
		start.setMinutes(((int) start.getMinutes() / 5) * 5);
		end.setMinutes(((int) end.getMinutes() / 5) * 5);

		long times = getDistanceTimes(start, end);
		times = times / 5;

		PlantaskExecuteDao pedao = new PlantaskExecuteDao();
		//先将插补的数据写入数据库
		if(CBZY<0){
			//System.out.println("删除数据");
			admd.deleteAmData(ammeterID, start, end);
		}else{
			pedao.addAmDatas(ammeterID, start, end);
		}
		List<AmMeterMataData> list1 = admd.getAmDataBetween(ammeterID, start, end);
		List<AmMeterMataData> list = admd.getRealDisplay(list1, ammeterID);
		int tempDate = -1;
		JSONArray json = new JSONArray();
		if (list != null && list.size() != 0) {
			for (AmMeterMataData a : list) {
				if (a.getRecordTimeDate().getDate() != tempDate) {
					tempDate = a.getRecordTimeDate().getDate();
					JSONObject jo = new JSONObject();
					//jo.put("Date", a.getRecordTimeDate().getDate());
					jo.put("Date", dfd.format(a.getRecordTimeDate()));

					JSONObject jo1 = new JSONObject();
					jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
					jo1.put("Time", dft.format(a.getRecordTimeDate()));
					jo1.put("Value", a.getValue());
					jo1.put("Isinsert", a.getIsInsert());
					JSONArray itemList = new JSONArray();
					itemList.add(jo1);

					jo.put("item", itemList);

					json.add(jo);
				} else {
					JSONObject jo1 = new JSONObject();
					jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
					jo1.put("Time", dft.format(a.getRecordTimeDate()));
					jo1.put("Value", a.getValue());
					jo1.put("Isinsert", a.getIsInsert());
					json.getJSONObject(json.size() - 1).getJSONArray("item")
					.add(jo1);
				}

			}
		}
		PrintWriter out = response.getWriter();
		//System.out.println("数据:"+json.toString());
		out.println(json.toString());
		out.flush();
		out.close();		
	}

	private void getAmDataBetween(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmmeterDataMaintenanceDao admd = new AmmeterDataMaintenanceDao();
        int ammeterID = 0;
        if (request.getParameter("Ammeter_ID") != null
                && request.getParameter("Ammeter_ID") != "")
            ammeterID = Integer.parseInt(request.getParameter("Ammeter_ID"));
        else
        {
            return;
        }

        Date start = null;
        if (request.getParameter("Start_Time") != null)
            start = df.parse(request.getParameter("Start_Time"));

        Date end = null;
        if (request.getParameter("End_Time") != null)
            end = df.parse(request.getParameter("End_Time"));

        List<AmMeterMataData> list = admd.getAmDataBetween(ammeterID, start,
                end);

        int tempDate = -1;
        JSONArray json = new JSONArray();
        if (list != null && list.size() != 0)
        {
            for (AmMeterMataData a : list)
            {
                if (a.getRecordTimeDate().getDate() != tempDate)
                {
                    tempDate = a.getRecordTimeDate().getDate();
                    JSONObject jo = new JSONObject();
                    jo.put("Date", a.getRecordTimeDate().getDate());

                    JSONObject jo1 = new JSONObject();
                    jo1.put("RecordTime", df.format(a.getRecordTimeDate()));
                    jo1.put("Time", dft.format(a.getRecordTimeDate()));
                    jo1.put("Value", a.getValue());

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

    private void updateAmData(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmmeterDataMaintenanceDao admd = new AmmeterDataMaintenanceDao();
        AmMeterMataData amd = new AmMeterMataData();
        AmmeterDao ammeterDao=new AmmeterDao();
        if (request.getParameter("Ammeter_ID") != null)
            amd.setAmMeterID(Integer.parseInt(request
                    .getParameter("Ammeter_ID")));

        if (request.getParameter("Record_Time") != null)
            amd.setRecordTimeDate(df.parse(request.getParameter("Record_Time")));

        if (request.getParameter("Value") != null)
            amd.setValue(Float.parseFloat(request.getParameter("Value")));

        String info = "fail";

        if (admd.updateAmmeterData(amd)){
            info = "success";
            OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				log.writeLog(userLoginName, "电能表数据维护", "修改电能表数据！"+ammeterDao.queryNameByID(amd.getAmMeterID())+"--"+amd.getRecordTimeDate()+"/"+amd.getValue());
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
    
    public static long getDistanceTimes(Date one, Date two) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long times = min;
		return times;
	}
}
