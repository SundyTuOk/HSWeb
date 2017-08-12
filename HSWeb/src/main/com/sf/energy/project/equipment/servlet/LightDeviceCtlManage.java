package com.sf.energy.project.equipment.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.classroomlight.dao.LightRoomDao;
import com.sf.energy.classroomlight.model.LightRoomModel;
import com.sf.energy.project.equipment.dao.LightDeviceCtlDao;
import com.sf.energy.project.equipment.model.LightDeviceCtlModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class LightDeviceCtlManage extends HttpServlet
{
    String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try
        {
            findMethod(request, response);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        String method = request.getParameter("method");

        if ("getAllLightDevice".equals(method))
            getAllLightDevice(request, response);

        if ("updateLightDevice".equals(method))
            updateLightDevice(request, response);

        if ("addLightDevice".equals(method))
            addLightDevice(request, response);

        if ("deleteLightDevice".equals(method))
            deleteLightDevice(request, response);

        if ("getAllLightRoom".equals(method))
            getAllLightRoom(request, response);
    }

    private void getAllLightRoom(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	LightRoomDao lrd = new LightRoomDao();
        List<LightRoomModel> list = lrd.queryAll();

        if (list != null && list.size() > 0)
        {
            JSONArray json = new JSONArray();

            for (LightRoomModel r : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("RoomID", r.getClassroomLightID());
                jo.put("RoomName", r.getRoomName());

                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getAllLightDevice(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	LightDeviceCtlDao ldc = new LightDeviceCtlDao();
        List<LightDeviceCtlModel> list = ldc.getAllLightDevice();

        JSONArray json = new JSONArray();

        if (list != null)
        {
            for (LightDeviceCtlModel n : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("LightRoomNo", n.getLightRoomNo());
                jo.put("DeviceNo", n.getDeviceNo());
                jo.put("Gather_ID", n.getGather_ID());
                jo.put("Gather_PortNo", n.getGather_PortNo());
                jo.put("BaudRate", n.getBaudRate());
                jo.put("Parity", n.getParity());
                jo.put("DataBit", n.getDataBit());
                jo.put("StopBit", n.getStopBit());
                jo.put("TimeOutUnit", n.getTimeOutUnit());
                jo.put("TimeOutTime", n.getTimeOutTime());
                jo.put("ByteTimeOutTime", n.getByteTimeOutTime());
                jo.put("SwitchMode", n.getSwitchMode());
                jo.put("LineSum", n.getLineSum());
                jo.put("CurrentLineOnSum", n.getCurrentLineOnSum());
                jo.put("MaxPeoplePerLine", n.getMaxPeoplePerLine());
                jo.put("CurrentPeopleSum", n.getCurrentPeopleSum());
                jo.put("SwitchOnLightValue", n.getSwitchOnLightValue());
                jo.put("CurrentLightValue", n.getCurrentLightValue());
                jo.put("Stat", n.getStat());
                jo.put("IsOffAlarm", n.getIsOffAlarm());
                jo.put("LightRoomName", n.getLightRoomName());

                json.add(jo);

            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }
    }

    private void updateLightDevice(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	LightDeviceCtlDao ldc = new LightDeviceCtlDao();
        String info = "fail";
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
        LightDeviceCtlModel l = null;

        if (request.getParameter("DeviceNo") != null
                && request.getParameter("DeviceNo").matches(numberPatern))
        {
            l = ldc.getLightDeviceCtlByDeviceNo(Integer.parseInt(request
                    .getParameter("DeviceNo")));
        }
        else
        {
            return;
        }

        if (request.getParameter("LightRoomNo") != null
                && request.getParameter("LightRoomNo").matches(numberPatern))
        {
            l.setLightRoomNo(Integer.parseInt(request
                    .getParameter("LightRoomNo")));
        }

        if (request.getParameter("Gather_ID") != null
                && request.getParameter("Gather_ID").matches(numberPatern))
        {
            l.setGather_ID(Integer.parseInt(request.getParameter("Gather_ID")));
        }

        if (request.getParameter("Gather_PortNo") != null
                && request.getParameter("Gather_PortNo").matches(numberPatern))
        {
            l.setGather_PortNo(Integer.parseInt(request
                    .getParameter("Gather_PortNo")));
        }

        if (request.getParameter("BaudRate") != null
                && request.getParameter("BaudRate").matches(numberPatern))
        {
            l.setBaudRate(Integer.parseInt(request.getParameter("BaudRate")));
        }

        if (request.getParameter("Parity") != null)
        {
            l.setParity(request.getParameter("Parity").charAt(0));
        }

        if (request.getParameter("DataBit") != null
                && request.getParameter("DataBit").matches(numberPatern))
        {
            l.setDataBit(Integer.parseInt(request.getParameter("DataBit")));
        }

        if (request.getParameter("StopBit") != null
                && request.getParameter("StopBit").matches(numberPatern))
        {
            l.setStopBit(Integer.parseInt(request.getParameter("StopBit")));
        }

        if (request.getParameter("TimeOutUnit") != null
                && request.getParameter("TimeOutUnit").matches(numberPatern))
        {
            l.setTimeOutUnit(Integer.parseInt(request
                    .getParameter("TimeOutUnit")));
        }

        if (request.getParameter("TimeOutTime") != null
                && request.getParameter("TimeOutTime").matches(numberPatern))
        {
            l.setTimeOutTime(Integer.parseInt(request
                    .getParameter("TimeOutTime")));
        }

        if (request.getParameter("ByteTimeOutTime") != null
                && request.getParameter("ByteTimeOutTime")
                        .matches(numberPatern))
        {
            l.setByteTimeOutTime(Integer.parseInt(request
                    .getParameter("ByteTimeOutTime")));
        }

        if (request.getParameter("SwitchMode") != null
                && request.getParameter("SwitchMode").matches(numberPatern))
        {
            l.setSwitchMode(Integer.parseInt(request.getParameter("SwitchMode")));
        }

        if (request.getParameter("LineSum") != null
                && request.getParameter("LineSum").matches(numberPatern))
        {
            l.setLineSum(Integer.parseInt(request.getParameter("LineSum")));
        }

        if (request.getParameter("CurrentLineOnSum") != null
                && request.getParameter("CurrentLineOnSum").matches(
                        numberPatern))
        {
            l.setCurrentLineOnSum(Integer.parseInt(request
                    .getParameter("CurrentLineOnSum")));
        }

        if (request.getParameter("MaxPeoplePerLine") != null
                && request.getParameter("MaxPeoplePerLine").matches(
                        numberPatern))
        {
            l.setMaxPeoplePerLine(Integer.parseInt(request
                    .getParameter("MaxPeoplePerLine")));
        }

        if (request.getParameter("CurrentPeopleSum") != null
                && request.getParameter("CurrentPeopleSum").matches(
                        numberPatern))
        {
            l.setCurrentPeopleSum(Integer.parseInt(request
                    .getParameter("CurrentPeopleSum")));
        }

        if (request.getParameter("SwitchOnLightValue") != null
                && request.getParameter("SwitchOnLightValue").matches(
                        numberPatern))
        {
            l.setSwitchOnLightValue(Integer.parseInt(request
                    .getParameter("SwitchOnLightValue")));
        }

        if (request.getParameter("CurrentLightValue") != null
                && request.getParameter("CurrentLightValue").matches(
                        numberPatern))
        {
            l.setCurrentLightValue(Integer.parseInt(request
                    .getParameter("CurrentLightValue")));
        }

        if (request.getParameter("Stat") != null
                && request.getParameter("Stat").matches(numberPatern))
        {
            l.setStat(Integer.parseInt(request.getParameter("Stat")));
        }

        if (request.getParameter("IsOffAlarm") != null
                && request.getParameter("IsOffAlarm").matches(numberPatern))
        {
            l.setIsOffAlarm(Integer.parseInt(request.getParameter("IsOffAlarm")));
        }

        if (ldc.updateLightDeviceCtl(l)){
            info = "success";
         // 写入日志
            log.writeLog(userLoginName, "教室照明设备管理", "新增教室照明设备："+l.getLightRoomName());
        }
        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void addLightDevice(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	LightDeviceCtlDao ldc = new LightDeviceCtlDao();
    	LightRoomDao lrd = new LightRoomDao();
        String info = "fail";
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
        LightDeviceCtlModel l = new LightDeviceCtlModel();

        if (request.getParameter("LightRoomNo") != null
                && request.getParameter("LightRoomNo").matches(numberPatern))
        {
            l.setLightRoomNo(Integer.parseInt(request
                    .getParameter("LightRoomNo")));
        }
        else
        {
            return;
        }

        if (request.getParameter("Gather_ID") != null
                && request.getParameter("Gather_ID").matches(numberPatern))
        {
            l.setGather_ID(Integer.parseInt(request.getParameter("Gather_ID")));
        }

        if (request.getParameter("Gather_PortNo") != null
                && request.getParameter("Gather_PortNo").matches(numberPatern))
        {
            l.setGather_PortNo(Integer.parseInt(request
                    .getParameter("Gather_PortNo")));
        }

        if (request.getParameter("BaudRate") != null
                && request.getParameter("BaudRate").matches(numberPatern))
        {
            l.setBaudRate(Integer.parseInt(request.getParameter("BaudRate")));
        }

        if (request.getParameter("Parity") != null)
        {
            l.setParity(request.getParameter("Parity").charAt(0));
        }

        if (request.getParameter("DataBit") != null
                && request.getParameter("DataBit").matches(numberPatern))
        {
            l.setDataBit(Integer.parseInt(request.getParameter("DataBit")));
        }

        if (request.getParameter("StopBit") != null
                && request.getParameter("StopBit").matches(numberPatern))
        {
            l.setStopBit(Integer.parseInt(request.getParameter("StopBit")));
        }

        if (request.getParameter("TimeOutUnit") != null
                && request.getParameter("TimeOutUnit").matches(numberPatern))
        {
            l.setTimeOutUnit(Integer.parseInt(request
                    .getParameter("TimeOutUnit")));
        }

        if (request.getParameter("TimeOutTime") != null
                && request.getParameter("TimeOutTime").matches(numberPatern))
        {
            l.setTimeOutTime(Integer.parseInt(request
                    .getParameter("TimeOutTime")));
        }

        if (request.getParameter("ByteTimeOutTime") != null
                && request.getParameter("ByteTimeOutTime")
                        .matches(numberPatern))
        {
            l.setByteTimeOutTime(Integer.parseInt(request
                    .getParameter("ByteTimeOutTime")));
        }

        if (request.getParameter("SwitchMode") != null
                && request.getParameter("SwitchMode").matches(numberPatern))
        {
            l.setSwitchMode(Integer.parseInt(request.getParameter("SwitchMode")));
        }

        if (request.getParameter("LineSum") != null
                && request.getParameter("LineSum").matches(numberPatern))
        {
            l.setLineSum(Integer.parseInt(request.getParameter("LineSum")));
        }

        if (request.getParameter("CurrentLineOnSum") != null
                && request.getParameter("CurrentLineOnSum").matches(
                        numberPatern))
        {
            l.setCurrentLineOnSum(Integer.parseInt(request
                    .getParameter("CurrentLineOnSum")));
        }

        if (request.getParameter("MaxPeoplePerLine") != null
                && request.getParameter("MaxPeoplePerLine").matches(
                        numberPatern))
        {
            l.setMaxPeoplePerLine(Integer.parseInt(request
                    .getParameter("MaxPeoplePerLine")));
        }

        if (request.getParameter("CurrentPeopleSum") != null
                && request.getParameter("CurrentPeopleSum").matches(
                        numberPatern))
        {
            l.setCurrentPeopleSum(Integer.parseInt(request
                    .getParameter("CurrentPeopleSum")));
        }

        if (request.getParameter("SwitchOnLightValue") != null
                && request.getParameter("SwitchOnLightValue").matches(
                        numberPatern))
        {
            l.setSwitchOnLightValue(Integer.parseInt(request
                    .getParameter("SwitchOnLightValue")));
        }

        if (request.getParameter("CurrentLightValue") != null
                && request.getParameter("CurrentLightValue").matches(
                        numberPatern))
        {
            l.setCurrentLightValue(Integer.parseInt(request
                    .getParameter("CurrentLightValue")));
        }

        if (request.getParameter("Stat") != null
                && request.getParameter("Stat").matches(numberPatern))
        {
            l.setStat(Integer.parseInt(request.getParameter("Stat")));
        }

        if (request.getParameter("IsOffAlarm") != null
                && request.getParameter("IsOffAlarm").matches(numberPatern))
        {
            l.setIsOffAlarm(Integer.parseInt(request.getParameter("IsOffAlarm")));
        }

        if (ldc.addLightDeviceCtl(l)){
            info = "success";
            // 写入日志
            log.writeLog(userLoginName, "教室照明设备管理", "新增教室照明设备："+l.getLightRoomName());
        }
        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void deleteLightDevice(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	LightDeviceCtlDao ldc = new LightDeviceCtlDao();
        String info = "fail";

        int deviceNo = -1;

        if (request.getParameter("DeviceNo") != null
                && request.getParameter("DeviceNo").matches(numberPatern))
        {
            deviceNo = Integer.parseInt(request.getParameter("DeviceNo"));
        }
        else
        {
            return;
        }

        if (ldc.deleteLightDeviceCtl(deviceNo))
            info = "success";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }
}
