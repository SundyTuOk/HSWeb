package com.sf.energy.expert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.expert.dao.FenXiangCountDao;

public class FenXiangCountManage extends HttpServlet
{
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

        String method = request.getParameter("method");

        try
        {
            if ("getElecGroupCount".equalsIgnoreCase(method))
                getElecGroupCount(request, response);

            if ("getElecArchCount".equalsIgnoreCase(method))
                getElecArchCount(request, response);

            if ("getWaterGroupCount".equalsIgnoreCase(method))
                getWaterGroupCount(request, response);

            if ("getWaterArchCount".equalsIgnoreCase(method))
                getWaterArchCount(request, response);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getWaterArchCount(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	 FenXiangCountDao fcd = new FenXiangCountDao();
    	 PrintWriter out = response.getWriter();
     	
         int archID = -1;
         int areaID = -1;
         
         if (request.getParameter("AreaID") != null)
         	areaID = Integer.parseInt(request.getParameter("AreaID"));
         else
             return;
         
         if (request.getParameter("ArchID") != null)
             archID = Integer.parseInt(request.getParameter("ArchID"));
         else
             return;

         int queryYear = 0;
         if (request.getParameter("QueryYear") != null)
             queryYear = Integer.parseInt(request.getParameter("QueryYear"));
         else
             return;

         List<String> titles = fcd.getWaterFenLeiTitle();

         if(areaID == -1)					//选了全校
         {
         	//System.out.println("全校");
         	List<Map<String, Float>> data = fcd.getWaterSchoolFenLeiCountByYear(
         			 queryYear);

             JSONArray json = buildJson(titles, data);
             out.println(json.toString());
         }
         else if(areaID != -1 && archID == -1)	//选了某个区域
         {
         	//System.out.println("区域");
         	List<Map<String, Float>> data = fcd.getWaterAreaFenLeiCountByYear(
         			areaID, queryYear);

             JSONArray json = buildJson(titles, data);
             out.println(json.toString());
         }
         else if(areaID != -1 && archID != -1)	//选了某个建筑的情形
         {
         	//System.out.println("建筑");
         	 List<Map<String, Float>> data = fcd.getWaterArchFenLeiCountByYear(
                      archID, queryYear);

              JSONArray json = buildJson(titles, data);
              out.println(json.toString());
         }
        
         out.flush();
         out.close();

    }

    public void getWaterGroupCount(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	FenXiangCountDao fcd = new FenXiangCountDao();
        int groupID = 0;
        if (request.getParameter("GroupID") != null)
            groupID = Integer.parseInt(request.getParameter("GroupID"));
        else
            return;

        int queryYear = 0;
        if (request.getParameter("QueryYear") != null)
            queryYear = Integer.parseInt(request.getParameter("QueryYear"));
        else
            return;

        List<String> titles = fcd.getWaterFenLeiTitle();

        List<Map<String, Float>> data = fcd.getWaterGroupFenLeiCountByYear(
                groupID, queryYear);

        JSONArray json = buildJson(titles, data);

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    public void getElecGroupCount(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	FenXiangCountDao fcd = new FenXiangCountDao();
        int groupID = 0;
        if (request.getParameter("GroupID") != null)
            groupID = Integer.parseInt(request.getParameter("GroupID"));
        else
            return;

        int queryYear = 0;
        if (request.getParameter("QueryYear") != null)
            queryYear = Integer.parseInt(request.getParameter("QueryYear"));
        else
            return;

        List<String> titles = fcd.getElecFenLeiTitle();

        List<Map<String, Float>> data = fcd.getElecGroupFenLeiCountByYear(
                groupID, queryYear);

        JSONArray json = buildJson(titles, data);

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    public void getElecArchCount(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	FenXiangCountDao fcd = new FenXiangCountDao();
    	PrintWriter out = response.getWriter();
    	
        int archID = -1;
        int areaID = -1;
        
        if (request.getParameter("AreaID") != null)
        	areaID = Integer.parseInt(request.getParameter("AreaID"));
        else
            return;
        
        if (request.getParameter("ArchID") != null)
            archID = Integer.parseInt(request.getParameter("ArchID"));
        else
            return;

        int queryYear = 0;
        if (request.getParameter("QueryYear") != null)
            queryYear = Integer.parseInt(request.getParameter("QueryYear"));
        else
            return;

        List<String> titles = fcd.getElecFenLeiTitle();

        if(areaID == -1)					//选了全校
        {
        	//System.out.println("全校");
        	List<Map<String, Float>> data = fcd.getElecSchoolFenLeiCountByYear(
        			 queryYear);

            JSONArray json = buildJson(titles, data);
            out.println(json.toString());
        }
        else if(areaID != -1 && archID == -1)	//选了某个区域
        {
        	//System.out.println("区域");
        	List<Map<String, Float>> data = fcd.getElecAreaFenLeiCountByYear(
        			areaID, queryYear);

            JSONArray json = buildJson(titles, data);
            out.println(json.toString());
        }
        else if(areaID != -1 && archID != -1)	//选了某个建筑的情形
        {
        	//System.out.println("建筑");
        	 List<Map<String, Float>> data = fcd.getElecArchFenLeiCountByYear(
                     archID, queryYear);

             JSONArray json = buildJson(titles, data);
             out.println(json.toString());
        }
       
        out.flush();
        out.close();
    }

    private String getXmlData(List<String> titles, List<Map<String, Float>> data)
    {
        String queryData = "<?xml version='1.0' encoding='UTF-8'?>"
                + "<chart palette='2' caption='' xAxisName='时间' rotateLabels='0' yAxisName='使用量' showValues='0' bgcolor='FFFFFF' canvasBgColor='FFFFFF' labelDisplay='None' decimals='0' formatNumberScale='0' >"
                + "<categories>" + "<category label='1'/>"
                + "<category label='2'/>" + "<category label='3'/>"
                + "<category label='4'/>" + "<category label='5'/>"
                + "<category label='6'/>" + "<category label='7'/>"
                + "<category label='8'/>" + "<category label='9'/>"
                + "<category label='10'/>" + "<category label='11'/>"
                + "<category label='12'/>" + "</categories>";

        String key = null;
        for (int i = 0; i < titles.size(); i++)
        {
            key = titles.get(i);
            queryData = queryData + "<dataset seriesName='" + key + "'>";
            for (int j = 0; j < data.size(); j++)
            {
                float value = 0;
                if (data.get(j) != null && data.get(j).containsKey(key))
                    value = data.get(j).get(key);

                queryData = queryData + "<set value='" + value + "'/>";
            }

            queryData = queryData + "</dataset>";
        }

        queryData = queryData + "</chart>";

        return queryData;
    }

    private JSONArray buildJson(List<String> titles,
            List<Map<String, Float>> data)
    {
        JSONArray json = new JSONArray();
        String key = null;
        for (int i = 0; i < titles.size(); i++)
        {
            key = titles.get(i);
            JSONObject jo = new JSONObject();
            jo.put("title", key);
            JSONArray fenXiang = new JSONArray();

            for (int j = 0; j < data.size(); j++)
            {
                float value = 0;
                if (data.get(j) != null && data.get(j).containsKey(key))
                    value = data.get(j).get(key);

                fenXiang.add(data.get(j).get(key));
            }
            jo.put("value", fenXiang);
            json.add(jo);
        }

        return json;
    }
}
