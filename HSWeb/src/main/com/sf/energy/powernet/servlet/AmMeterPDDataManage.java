package com.sf.energy.powernet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.monitor.dao.AmmeterDataMaintenanceDao;
import com.sf.energy.powernet.dao.AmMeterPDDataDao;
import com.sf.energy.powernet.model.AmMeterPDDatasModel;
import com.sf.energy.statistics.model.AmMeterMataData;

public class AmMeterPDDataManage extends HttpServlet
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        if ("getAmMeterPDDataBetween".equals(method))
            getAmMeterPDDataBetween(request, response);

        if ("getAmMeterDataBetween".equals(method))
            getAmMeterDataBetween(request, response);

        if ("updateAmMeterPDData".equals(method))
            updateAmMeterPDData(request, response);

        if ("updateAmMeterData".equals(method))
            updateAmMeterData(request, response);

    }

    private void updateAmMeterData(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException,
            ParseException
    {
        AmmeterDataMaintenanceDao admd = new AmmeterDataMaintenanceDao();
        AmMeterMataData amd = new AmMeterMataData();

        if (request.getParameter("Ammeter_ID") != null)
            amd.setAmMeterID(Integer.parseInt(request
                    .getParameter("Ammeter_ID")));

        if (request.getParameter("Record_Time") != null)
            amd.setRecordTimeDate(ldf.parse(request.getParameter("Record_Time")));

        if (request.getParameter("Value") != null)
            amd.setValue(Float.parseFloat(request.getParameter("Value")));

        String info = "fail";

        if (admd.updateAmmeterData(amd))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info.trim());
        out.flush();
        out.close();
    }

    private void updateAmMeterPDData(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException,
            ParseException
    {
    	AmMeterPDDataDao evd = new AmMeterPDDataDao();
        int ammeterID = 0;
        if (request.getParameter("AmmeterID") != null)
        {
            ammeterID = Integer.parseInt(request.getParameter("AmmeterID"));
        }
        else
        {
            return;
        }

        Date valueTime = null;
        if (request.getParameter("ValueTime") != null)
        {
            valueTime = sdf.parse(request.getParameter("ValueTime"));
        }
        else
        {
            return;
        }

        String cloum = null;
        if (request.getParameter("Cloum") != null)
        {
            cloum = request.getParameter("Cloum");
        }
        else
        {
            return;
        }

        float value = 0;
        if (request.getParameter("Value") != null)
        {
            value = Float.parseFloat(request.getParameter("Value"));
        }
        else
        {
            return;
        }

        String info = null;
        if (evd.updateAmmeterPDDataByCloum(cloum, value, ammeterID, valueTime))
            info = "success";
        else
        {
            info = "fail";
        }

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();

    }

    private void getAmMeterDataBetween(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException,
            ParseException
    {
    	AmMeterPDDataDao evd = new AmMeterPDDataDao();
        List<AmMeterMataData> list = null;

        int pdPartID = 0;
        if (request.getParameter("PdPartID") != null)
        {
            pdPartID = Integer.parseInt(request.getParameter("PdPartID"));
        }
        else
        {
            return;
        }

        Date start = null;
        if (request.getParameter("StartTime") != null)
        {
            start = sdf.parse(request.getParameter("StartTime"));
        }
        else
        {
            return;
        }

        Date end = null;
        if (request.getParameter("EndTime") != null)
        {
            end = sdf.parse(request.getParameter("EndTime"));
        }
        else
        {
            return;
        }

        int pageIndex = 0;
        if (request.getParameter("PageIndex") != null)
        {
            pageIndex = Integer.parseInt(request.getParameter("PageIndex"));
        }
        else
        {
            return;
        }

        int pageCount = 0;
        if (request.getParameter("PageCount") != null)
        {
            pageCount = Integer.parseInt(request.getParameter("PageCount"));
        }
        else
        {
            return;
        }

        Map<String, Object> result = evd.getAmDataBetweenByPDPartID(pdPartID,
                start, end, pageIndex, pageCount);
        int totalCount = 0;

        if (result != null)
        {
            if (result.get("Total") != null)
                totalCount = (int) result.get("Total");

            if (result.get("List") != null)
                list = (List<AmMeterMataData>) result.get("List");
        }

        JSONArray json = new JSONArray();

        JSONObject jo1 = new JSONObject();

        jo1.put("TotalCount", totalCount);

        json.add(jo1);

        if (list != null && list.size() > 0)
        {
            for (AmMeterMataData e : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("AmmeterID", e.getAmMeterID());

                jo.put("ValueTime", ldf.format(e.getRecordTimeDate()));

                jo.put("ZValueZY", e.getValue());

                json.add(jo);
            }

        }

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    private void getAmMeterPDDataBetween(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException,
            ParseException
    {
    	AmMeterPDDataDao evd = new AmMeterPDDataDao();
        List<AmMeterPDDatasModel> list = null;
        int pdPartID = 0;
        if (request.getParameter("PdPartID") != null)
        {
            pdPartID = Integer.parseInt(request.getParameter("PdPartID"));
        }
        else
        {
            return;
        }

        Date start = null;
        if (request.getParameter("StartTime") != null)
        {
            start = sdf.parse(request.getParameter("StartTime"));
        }
        else
        {
            return;
        }

        Date end = null;
        if (request.getParameter("EndTime") != null)
        {
            end = sdf.parse(request.getParameter("EndTime"));
        }
        else
        {
            return;
        }

        int pageIndex = 0;
        if (request.getParameter("PageIndex") != null)
        {
            pageIndex = Integer.parseInt(request.getParameter("PageIndex"));
        }
        else
        {
            return;
        }

        int pageCount = 0;
        if (request.getParameter("PageCount") != null)
        {
            pageCount = Integer.parseInt(request.getParameter("PageCount"));
        }
        else
        {
            return;
        }

        Map<String, Object> result = evd.getAmPDDataBetweenByPDPartID(pdPartID,
                start, end, pageIndex, pageCount);
        int totalCount = 0;

        if (result != null)
        {
            if (result.get("Total") != null)
                totalCount = (int) result.get("Total");

            if (result.get("List") != null)
                list = (List<AmMeterPDDatasModel>) result.get("List");
        }

        JSONArray json = new JSONArray();

        JSONObject jo1 = new JSONObject();

        jo1.put("TotalCount", totalCount);

        json.add(jo1);

        if (list != null && list.size() > 0)
        {
            for (AmMeterPDDatasModel e : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("AmmeterID", e.getAmMeter_ID());

                jo.put("ValueTime", ldf.format(e.getRecordTime()));

                jo.put("PowerZY", e.getPowerZY());
                jo.put("PowerAY", e.getPowerAY());
                jo.put("PowerBY", e.getPowerBY());
                jo.put("PowerCY", e.getPowerCY());

                jo.put("PowerZW", e.getPowerZW());
                jo.put("PowerAW", e.getPowerAW());
                jo.put("PowerBW", e.getPowerBW());
                jo.put("PowerCW", e.getPowerCW());

                jo.put("PowerFactorA", e.getPowerFactorA());
                jo.put("PowerFactorB", e.getPowerFactorB());
                jo.put("PowerFactorC", e.getPowerFactorC());
                jo.put("PowerFactorZ", e.getPowerFactorZ());

                jo.put("VoltageA", e.getVoltageA());
                jo.put("VoltageB", e.getVoltageB());
                jo.put("VoltageC", e.getVoltageC());

                jo.put("Current0", e.getCurrent0());
                jo.put("CurrentA", e.getCurrentA());
                jo.put("CurrentB", e.getCurrentB());
                jo.put("CurrentC", e.getCurrentC());

                jo.put("PowerSZZ", e.getPowerSZZ());
                jo.put("PowerSZA", e.getPowerSZA());
                jo.put("PowerSZB", e.getPowerSZB());
                jo.put("PowerSZC", e.getPowerSZC());

                json.add(jo);
            }

        }

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }
}
