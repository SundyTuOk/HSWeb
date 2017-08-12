package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.util.ConnDB;

public class OnlineSqlManage extends HttpServlet
{
    String numberPatern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        findMethod(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response)
    {
        String method = request.getParameter("method");

        try
        {
            if ("executeSql".equals(method))
                executeSql(request, response);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void executeSql(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        String sql = request.getParameter("sql");
        String[] unable = { "update", "delete", "insert", "modify", "alter",
                "drop" };

        boolean flag = true;

        for (int i = 0; i < unable.length - 1; i++)
        {
            if (sql.contains(unable[i]))
            {
                flag = false;
                break;
            }
        }

        int pageCount = 10;
        if (request.getParameter("PageCount") != null
                && request.getParameter("PageCount").matches(numberPatern))
        {
            pageCount = Integer.parseInt(request.getParameter("PageCount"));
        }
        else
        {
            return;
        }

        int queryIndex = 0;
        if (request.getParameter("QueryIndex") != null
                && request.getParameter("QueryIndex").matches(numberPatern))
        {
            queryIndex = Integer.parseInt(request.getParameter("QueryIndex"));
        }
        else
        {
            return;
        }

        if (flag)
        {
            JSONArray json = new JSONArray();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ResultSetMetaData meta = null;
            Connection conn = null;

            try
			{
            	conn = ConnDB.getConnection();
				ps = conn.prepareStatement(sql,
				        ResultSet.TYPE_SCROLL_INSENSITIVE,
				        ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				if (rs.next())
				{
				    JSONObject info = new JSONObject();

				    rs.last();
				    int count = rs.getRow();
				    if (count <= (pageCount * queryIndex))
				        return;

				    info.put("TotalCount", count);

				    count = count - pageCount * queryIndex;

				    if (count >= pageCount)
				        count = pageCount;

				    if ((pageCount * queryIndex) == 0)
				    {
				        rs.absolute(1);
				        rs.previous();
				    }
				    else
				        rs.absolute(pageCount * queryIndex);

				    meta = rs.getMetaData();
				    int columnCount = meta.getColumnCount();
				    int i = 0;

				    info.put("ColumnCount", meta.getColumnCount());
				    json.add(info);

				    JSONObject jo = new JSONObject();
				    for (i = 1; i <= columnCount; i++)
				    {
				        jo.put("" + i, meta.getColumnLabel(i));
				    }
				    json.add(jo);

				    while (rs.next() && (count > 0))
				    {
				        JSONObject job = new JSONObject();
				        for (i = 1; i <= columnCount; i++)
				        {
				            job.put("" + i, rs.getObject(i));
				        }
				        json.add(job);
				        count--;
				    }

				    // int countNum = 20;
				    // do
				    // {
				    // JSONObject job = new JSONObject();
				    // for (i = 1; i <= count; i++)
				    // {
				    // job.put("" + i, rs.getObject(i));
				    // }
				    // json.add(job);
				    // countNum--;
				    // } while (rs.next() && countNum > 0);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
				ConnDB.release(conn, ps, rs);
			}

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }
}
