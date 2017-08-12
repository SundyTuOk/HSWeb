package com.sf.energy.powernet.servlet;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.powernet.dao.ExtremeValueDao;
import com.sf.energy.powernet.model.ExtremeModel;

public class ExtremeValueManage extends HttpServlet
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        if ("getExtremeValueBetween".equals(method))
            getExtremeValueBetween(request, response);

        if ("getHuiLuPdPartTree".equals(method))
            getHuiLuPdPartTree(request, response);
    }

    private void getHuiLuPdPartTree(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	ExtremeValueDao evd = new ExtremeValueDao();
        List<ExtremeModel> list = evd.getAllHuiLuPdPart();

        if (list != null && list.size() > 0)
        {
            JSONArray json = new JSONArray();

            for (ExtremeModel e : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("Part_ID", e.getPdPartID());
                jo.put("Part_Name", e.getName());
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }
    }

    private String buildNode(JSONObject node)
    {
        String nodeContent = "";
        String id = node.getString("Part_ID");
        String name = node.getString("Part_Name");
        String type = node.getString("Type");

        if ("Son".equals(type))
        {
            nodeContent += "<a href='javascript:void(0);'>";
            nodeContent += "<span class='lbl'> " + name + "</span>";
            nodeContent += "</a>";
        }

        if ("Dad".equals(type))
        {
            nodeContent += "<ul class='nav nav-list'>";
            nodeContent += "<li>";
            nodeContent += "<ul class='submenu' style='display: block;'>";
            nodeContent += "<li>";
            nodeContent += "<a href='javascript:void(0);' class='dropdown-toggle'>";
            nodeContent += "<i class='icon-plus'></i>";
            nodeContent += "<span class='lbl'> " + name + " </span>";
            nodeContent += "</a>";
            nodeContent += "<ul class='submenu' style='display: block;'>";

            for (int i = 0; i < node.getJSONArray("SonList").size(); i++)
            {
                nodeContent += "<li>";
                nodeContent += buildNode(node.getJSONArray("SonList")
                        .getJSONObject(i));
                nodeContent += "</li>";
            }

            nodeContent += "</ul>";
            nodeContent += "</li>";
            nodeContent += "</ul>";
            nodeContent += "</li>";
            nodeContent += "</ul>";
        }

        return nodeContent;
    }

    private JSONArray buildTree(List<ExtremeModel> list) throws SQLException
    {
    	ExtremeValueDao evd = new ExtremeValueDao();
        JSONArray json = new JSONArray();

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getPdpartParentID() == 0)
            {
                JSONObject jo = new JSONObject();

                if (evd.getPdPartByParentID(list.get(i).getPdPartID()) == null)
                {
                    jo.put("Type", "Son");
                }
                else
                {
                    jo.put("Type", "Dad");
                }

                jo.put("Part_ID", list.get(i).getPdPartID());
                jo.put("Part_Name", list.get(i).getName());

                json.add(jo);

                list.remove(i);

                i--;
            }
            else
            {
                if (evd.getPDPartByID(list.get(i).getPdpartParentID()) == null)
                {
                    JSONObject jo = new JSONObject();

                    jo.put("Type", "Son");
                    jo.put("Part_ID", list.get(i).getPdPartID());
                    jo.put("Part_Name", list.get(i).getName());

                    json.add(jo);

                    list.remove(i);

                    i--;
                }
            }
        }

        if (list != null && list.size() > 0)
        {
            for (ExtremeModel em : list)
            {
                JSONObject jo = getJSONObjectByID(json, em.getPdpartParentID());
                jo.put("Type", "Dad");

                if (jo != null)
                {
                    JSONArray ja = null;
                    if (jo.get("SonList") == null)
                        ja = new JSONArray();
                    else
                    {
                        ja = jo.getJSONArray("SonList");
                    }

                    JSONObject jo1 = new JSONObject();

                    jo1.put("Part_ID", em.getPdPartID());
                    jo1.put("Part_Name", em.getName());

                    jo1.put("Type", "Son");

                    ja.add(jo1);
                    jo.put("SonList", ja);
                }

            }
        }
        return json;
    }

    private JSONObject getJSONObjectByID(JSONArray json, Integer id)
    {
        if (json == null)
            return null;

        JSONObject jo = null;

        Integer tempID = 0;

        for (int i = 0; i < json.size(); i++)
        {
            tempID = Integer.parseInt(json.getJSONObject(i).get("Part_ID")
                    .toString());
            if (tempID == id)
            {
                jo = json.getJSONObject(i);
                break;
            }
            else
            {
                if ("Dad".equals(json.getJSONObject(i).get("Type"))
                        && json.getJSONObject(i).getJSONArray("SonList") != null)
                {
                    jo = getJSONObjectByID(
                            json.getJSONObject(i).getJSONArray("SonList"), id);

                    if (jo != null)
                        break;
                }
            }
        }

        return jo;
    }

    private void getExtremeValueBetween(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	ExtremeValueDao evd = new ExtremeValueDao();
        int pdPartID = 0;
        if (request.getParameter("PD_PartID") != null)
            pdPartID = Integer.parseInt(request.getParameter("PD_PartID"));
        else
        {
            return;
        }

        Date startTime = null;
        if (request.getParameter("StartDate") != null)
            startTime = sdf.parse(request.getParameter("StartDate")+" 00:00:00");

        Date endTime = null;
        if (request.getParameter("EndDate") != null)
            endTime = sdf.parse(request.getParameter("EndDate")+ " 23:59:59");

        List<ExtremeModel> list = evd.getExtremeValueBetween(pdPartID,
                startTime, endTime);

        JSONArray json = new JSONArray();

        if (list != null && list.size() > 0)
        {
            for (ExtremeModel em : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("Name", em.getName());
                jo.put("maxPowerZY", em.getMaxPowerZY());
                jo.put("minPowerZY", em.getMinPowerZY());
                jo.put("maxPowerAY", em.getMaxPowerAY());
                jo.put("minPowerAY", em.getMinPowerAY());
                jo.put("maxPowerBY", em.getMaxPowerBY());
                jo.put("minPowerBY", em.getMinPowerBY());
                jo.put("maxPowerCY", em.getMaxPowerCY());
                jo.put("minPowerCY", em.getMinPowerCY());
                jo.put("maxPowerZW", em.getMaxPowerZW());
                jo.put("minPowerZW", em.getMinPowerZW());
                jo.put("maxPowerAW", em.getMaxPowerAW());
                jo.put("minPowerAW", em.getMinPowerAW());
                jo.put("maxPowerBW", em.getMaxPowerBW());
                jo.put("minPowerBW", em.getMinPowerBW());
                jo.put("maxPowerCW", em.getMaxPowerCW());
                jo.put("minPowerCW", em.getMinPowerCW());
                jo.put("maxVoltageA", em.getMaxVoltageA());
                jo.put("minVoltageA", em.getMinVoltageA());
                jo.put("maxVoltageB", em.getMaxVoltageB());
                jo.put("minVoltageB", em.getMinVoltageB());
                jo.put("maxVoltageC", em.getMaxVoltageC());
                jo.put("minVoltageC", em.getMinVoltageC());
                jo.put("maxCurrentA", em.getMaxCurrentA());
                jo.put("minCurrentA", em.getMinCurrentA());
                jo.put("maxCurrentB", em.getMaxCurrentB());
                jo.put("minCurrentB", em.getMinCurrentB());
                jo.put("maxCurrentC", em.getMaxCurrentC());
                jo.put("minCurrentC", em.getMinCurrentC());

                json.add(jo);
            }

        }

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }
}
