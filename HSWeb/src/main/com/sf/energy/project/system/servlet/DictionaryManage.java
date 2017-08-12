package com.sf.energy.project.system.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.system.dao.DictionaryDao;
import com.sf.energy.project.system.dao.DictionaryValueDao;
import com.sf.energy.project.system.model.DictionaryModel;
import com.sf.energy.project.system.model.DictionaryValueModel;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class DictionaryManage extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        String method = request.getParameter("method");

        if ("getAllDict".equals(method))
            getAllDict(request, response);

        if ("getAllDictValue".equals(method))
            getAllDictValue(request, response);

        if ("updateDictValue".equals(method))
            updateDictValue(request, response);

        if ("updateDict".equals(method))
            updateDict(request, response);

        if ("addDictValue".equals(method))
            addDictValue(request, response);

        if ("addDict".equals(method))
            addDict(request, response);

        if ("deleteDictValue".equals(method))
            deleteDictValue(request, response);

        if ("deleteDict".equals(method))
            deleteDict(request, response);

    }

    private void deleteDict(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		
    	DictionaryDao dd = new DictionaryDao();
        String info = "fail";
        if (request.getParameter("DictID") != null)
        {
            int DictID = Integer.parseInt(request.getParameter("DictID"));
           String nameString=dd.getDictionaryByID(DictID).getDictionaryName();
            if (dd.deleteDictionaryByID(DictID))
            {
            	// 写入日志
    			log.writeLog(userLoginName, "数据字典", "删除数据字典项    "+nameString);
                info = "success";
            }
        }
        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();

    }

    private void deleteDictValue(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        DictionaryValueDao dvd = new DictionaryValueDao();
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		
        if (request.getParameter("DictValueID") != null)
        {
            int DictValueID = Integer.parseInt(request
                    .getParameter("DictValueID"));
            String nameString=dvd.getDictionaryValueByValueID(DictValueID).getDictionaryValueNum();
            if (dvd.deleteDictionaryValueByValueID(DictValueID))
            {
            	// 写入日志
    			log.writeLog(userLoginName, "数据字典", "删除数据字典值    "+nameString);
                List<DictionaryValueModel> list = dvd.getAllDictValue();

                if (list != null)
                {
                    JSONArray json = new JSONArray();

                    for (DictionaryValueModel dm : list)
                    {
                        JSONObject jo = new JSONObject();
                        jo.put("DictionaryValue_ID", dm.getDictionaryValueID());
                        jo.put("Dictionary_ID", dm.getDictionaryID());
                        jo.put("DictionaryValue_Num",
                                dm.getDictionaryValueNum());
                        jo.put("DictionaryValue_Value", dm.getDictionaryValue());
                        json.add(jo);
                    }

                    PrintWriter out = response.getWriter();
                    out.println(json.toString());
                    out.flush();
                    out.close();
                }

            }
        }

    }

    private void addDict(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DictionaryDao dd = new DictionaryDao();
        DictionaryModel dm = new DictionaryModel();
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
        if (request.getParameter("DictName") != null)
            dm.setDictionaryName(request.getParameter("DictName"));

        if (dd.addDictionary(dm))
        {
        	// 写入日志
			log.writeLog(userLoginName, "数据字典", "新增数据字典项    "+dm.getDictionaryName());
            List<DictionaryModel> list = dd.getAllDictionary();

            if (list != null)
            {
                JSONArray json = new JSONArray();

                for (DictionaryModel d : list)
                {
                    JSONObject jo = new JSONObject();
                    jo.put("Dictionary_ID", d.getDictionaryID());
                    jo.put("Dictionary_Name", d.getDictionaryName());
                    jo.put("Dictionary_Remark", d.getDictionaryRemark());
                    json.add(jo);
                }

                PrintWriter out = response.getWriter();
                out.println(json.toString());
                out.flush();
                out.close();
            }

        }

    }

    private void addDictValue(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        DictionaryValueDao dvd = new DictionaryValueDao();
        DictionaryValueModel dvm = new DictionaryValueModel();
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
        if (request.getParameter("DictID") != null)
            dvm.setDictionaryID(Integer.parseInt(request.getParameter("DictID")));

        if (request.getParameter("DictNum") != null)
            dvm.setDictionaryValueNum(request.getParameter("DictNum"));

        if (request.getParameter("DictValueValue") != null)
            dvm.setDictionaryValue(request.getParameter("DictValueValue"));

        if (dvd.addDictionaryValue(dvm))
        {
        	// 写入日志
        	log.writeLog(userLoginName, "数据字典", "新增数据字典值    "+dvm.getDictionaryValueNum());
            List<DictionaryValueModel> list = dvd.getAllDictValue();

            if (list != null)
            {
                JSONArray json = new JSONArray();

                for (DictionaryValueModel dm : list)
                {
                    JSONObject jo = new JSONObject();
                    jo.put("DictionaryValue_ID", dm.getDictionaryValueID());
                    jo.put("Dictionary_ID", dm.getDictionaryID());
                    jo.put("DictionaryValue_Num", dm.getDictionaryValueNum());
                    jo.put("DictionaryValue_Value", dm.getDictionaryValue());
                    json.add(jo);
                }

                PrintWriter out = response.getWriter();
                out.println(json.toString());
                out.flush();
                out.close();
            }

        }

    }

    private void updateDict(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DictionaryDao dd = new DictionaryDao();
        DictionaryModel dvm = new DictionaryModel();
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		
        if (request.getParameter("Dictionary_ID") != null)
            dvm.setDictionaryID(Integer.parseInt(request
                    .getParameter("Dictionary_ID")));

        if (request.getParameter("Dictionary_Name") != null)
            dvm.setDictionaryName(request.getParameter("Dictionary_Name"));

        if (request.getParameter("Dictionary_Remark") != null)
            dvm.setDictionaryRemark(request.getParameter("Dictionary_Remark"));

        if (dd.updateDictionary(dvm))
        {
        	// 写入日志
        	log.writeLog(userLoginName, "数据字典", "编辑数据字典项    "+dvm.getDictionaryName());
            List<DictionaryModel> list = null;

            list = dd.getAllDictionary();

            JSONArray json = new JSONArray();

            if (list != null)
            {
                for (DictionaryModel dm : list)
                {
                    JSONObject jo = new JSONObject();
                    jo.put("Dictionary_ID", dm.getDictionaryID());
                    jo.put("Dictionary_Name", dm.getDictionaryName());
                    jo.put("Dictionary_Remark", dm.getDictionaryRemark());
                    json.add(jo);
                }

                PrintWriter out = response.getWriter();
                out.println(json.toString());
                out.flush();
                out.close();
            }
        }

    }

    private void updateDictValue(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        DictionaryValueDao dvd = new DictionaryValueDao();
        DictionaryValueModel dvm = new DictionaryValueModel();
        OperationLogInterface log = new OperationLogImplement();
		String userLoginName = (String) request.getSession().getAttribute(
				"userName");
		
        if (request.getParameter("DictID") != null)
            dvm.setDictionaryID(Integer.parseInt(request
                    .getParameter("DictID")));
        if (request.getParameter("DictValueID") != null)
            dvm.setDictionaryValueID(Integer.parseInt(request
                    .getParameter("DictValueID")));

        if (request.getParameter("DictNum") != null)
            dvm.setDictionaryValueNum(request.getParameter("DictNum"));

        if (request.getParameter("DicuValueValue") != null)
            dvm.setDictionaryValue(request.getParameter("DicuValueValue"));

        String info = "fail";
        if (dvd.updateDictionaryValue(dvm)){
        	// 写入日志
        	log.writeLog(userLoginName, "数据字典", "编辑数据字典值    "+dvm.getDictionaryValueNum());
            info = "success";
        }
        else
            info = "fail";

        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void getAllDictValue(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        DictionaryValueDao dvd = new DictionaryValueDao();
        List<DictionaryValueModel> list = null;

        list = dvd.getAllDictValue();

        JSONArray json = new JSONArray();

        if (list != null)
        {
            for (DictionaryValueModel dm : list)
            {
                JSONObject jo = new JSONObject();
                jo.put("DictionaryValue_ID", dm.getDictionaryValueID());
                jo.put("Dictionary_ID", dm.getDictionaryID());
                jo.put("DictionaryValue_Num", dm.getDictionaryValueNum());
                jo.put("DictionaryValue_Value", dm.getDictionaryValue());
                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }

    private void getAllDict(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
    	DictionaryDao dd = new DictionaryDao();
        List<DictionaryModel> list = null;

        list = dd.getAllDictionary();

        JSONArray json = new JSONArray();

        if (list != null)
        {
            for (DictionaryModel dm : list)
            {
                JSONObject jo = new JSONObject();
                jo.put("Dictionary_ID", dm.getDictionaryID());
                jo.put("Dictionary_Name", dm.getDictionaryName());
                jo.put("Dictionary_Remark", dm.getDictionaryRemark());
                json.add(jo);
            }

            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();
            out.close();
        }

    }
}
