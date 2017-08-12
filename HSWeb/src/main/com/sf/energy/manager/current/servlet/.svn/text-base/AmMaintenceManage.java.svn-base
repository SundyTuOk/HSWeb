package com.sf.energy.manager.current.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.manager.current.dao.AmMaintenceDao;
import com.sf.energy.manager.current.model.AmMaintence;
import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.util.DataValidation;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

public class AmMaintenceManage extends HttpServlet
{
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

        if ("addAmMaintence".equals(method))
            addAmMaintence(request, response);

        if ("updateAmMaintence".equals(method))
            updateAmMaintence(request, response);

        if ("deleteAmMaintence".equals(method))
            deleteAmMaintence(request, response);

        if ("getAllAmMaintence".equals(method))
            getAllAmMaintence(request, response);
    }

    private void addAmMaintence(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmmeterDao ammeterDao = new AmmeterDao();
    	AmMaintenceDao amd = new AmMaintenceDao();
        AmMaintence am = new AmMaintence();

        if (request.getParameter("AmmeterID") != null
                && DataValidation.isNumber(request.getParameter("AmmeterID")))
            am.setAmmeterID(Integer.parseInt(request.getParameter("AmmeterID")));

        if (ammeterDao.queryByID(am.getAmmeterID()) == null)
            return;

        if (request.getParameter("MaintTime") != null)
            am.setMainTime(df.parse(request.getParameter("MaintTime")));

        if (request.getParameter("MaintRemark") != null)
            am.setMaintRemark(request.getParameter("MaintRemark"));

        if (request.getParameter("MaintMan") != null)
            am.setMaintMan(request.getParameter("MaintMan"));

        String info = "fail";

        if (amd.insertAmMaintence(am)){
            info = "success";
            OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				log.writeLog(userLoginName, "电表维护", "新增电表维护记录！"+am.getAmmeterName()+"--"+am.getMaintRemark());
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        PrintWriter out = response.getWriter();
        out.println(info);
        out.flush();
        out.close();
    }

    private void updateAmMaintence(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmmeterDao ammeterDao = new AmmeterDao();
    	AmMaintenceDao amd = new AmMaintenceDao();
        AmMaintence am = new AmMaintence();

        if (request.getParameter("AmmeterID") != null
                && DataValidation.isNumber(request.getParameter("AmmeterID")))
            am.setAmmeterID(Integer.parseInt(request.getParameter("AmmeterID")));

        if (ammeterDao.queryByID(am.getAmmeterID()) == null)
            return;

        if (request.getParameter("MaintID") != null
                && DataValidation.isNumber(request.getParameter("MaintID")))
            am.setMaintID(Integer.parseInt(request.getParameter("MaintID")));

        if (request.getParameter("MaintTime") != null)
            am.setMainTime(df.parse(request.getParameter("MaintTime")));

        if (request.getParameter("MaintRemark") != null)
            am.setMaintRemark(request.getParameter("MaintRemark"));

        if (request.getParameter("MaintMan") != null)
            am.setMaintMan(request.getParameter("MaintMan"));

        String info = "fail";

        if (amd.updateAmMaintence(am)){
            info = "success";
            OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				log.writeLog(userLoginName, "电表维护", "编辑电表维护记录！"+am.getAmmeterName()+"--"+am.getMaintRemark());
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

    private void deleteAmMaintence(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmMaintenceDao amd = new AmMaintenceDao();
        int mainID = 0;
        if (request.getParameter("MaintID") != null
                && DataValidation.isNumber(request.getParameter("MaintID")))
            mainID = Integer.parseInt(request.getParameter("MaintID"));

        String info = "fail";

        if (amd.deleteAmMaintence(mainID)){
            info = "success";
            OperationLogInterface log = new OperationLogImplement();
			HttpSession session = request.getSession();
			String userLoginName = (String) session.getAttribute("userName");
			try
			{
				log.writeLog(userLoginName, "电表维护", "删除电表维护记录！"+amd.selectAllAmMaintenceByID(mainID).getAmmeterName());
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

    private void getAllAmMaintence(HttpServletRequest request,
            HttpServletResponse response) throws ParseException, SQLException,
            IOException
    {
    	AmMaintenceDao amd = new AmMaintenceDao();
        String sortLabel = "Maint_ID";
        if (request.getParameter("SortLabel") != null)
            sortLabel = request.getParameter("SortLabel");
        int schoolID=-1;
		int areaID=-1;
		int archID=-1;
		int floor=-1;
		int ammID=-1;
        boolean isAsc = false;
        List<AmMaintence> list =new ArrayList<AmMaintence>();
        if (request.getParameter("SortType") != null)
        {
            if ("Asc".equalsIgnoreCase(request.getParameter("SortType")))
                isAsc = true;

            if ("Desc".equalsIgnoreCase(request.getParameter("SortType")))
                isAsc = false;
        }
        Integer thePageCount = Integer.parseInt(request.getParameter("AmMaintencePageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("AmMaintencePageIndex"));
		
		if(Integer.parseInt(request.getParameter("schoolID"))!=-1)
		{
			schoolID=Integer.parseInt(request.getParameter("schoolID"));
		}
		if(Integer.parseInt(request.getParameter("areaID"))!=-1)
		{
			areaID=Integer.parseInt(request.getParameter("areaID"));
		}
		if(Integer.parseInt(request.getParameter("archID"))!=-1)
		{
			archID=Integer.parseInt(request.getParameter("archID"));
		}
		
		if(Integer.parseInt(request.getParameter("floor"))!=-1)
		{
			floor=Integer.parseInt(request.getParameter("floor"));
		}
		if(Integer.parseInt(request.getParameter("ammID"))!=-1)
		{
			ammID=Integer.parseInt(request.getParameter("ammID"));
		}
		////System.out.println("选择的信息：areaID:"+areaID+" archID:"+archID+" floor:"+floor+" ammID:"+ammID);
		
		//如果是全校业务
		if(areaID==-1 && archID==-1 && floor==-1 && ammID==-1)
		{
			schoolID=0;//将school置为非-1的任何数，则代表是全校的业务
		}
		if(schoolID!=-1)//查整个学校
		{
			list = amd.selectAllAmMaintenceByLableOrder(
			sortLabel, isAsc,thePageCount,thePageIndex);
		}else{
			list = amd.selectAmMaintenceByTree(
					sortLabel,isAsc,areaID,archID,floor,ammID,thePageCount,thePageIndex);
		}
		JSONArray main = new JSONArray();
		JSONObject jo11 = new JSONObject(); 
        jo11.put("totalCount", amd.getTotal());
        main.add(jo11);
        if (list != null && list.size() > 0)
        {
            for (AmMaintence am : list)
            {
                JSONObject jo = new JSONObject();

                jo.put("MaintID", am.getMaintID());
                jo.put("AmmeterID", am.getAmmeterID());
                jo.put("AmmeterName", am.getAmmeterName());
                if (am.getMainTime() != null)
                    jo.put("MaintTime", df.format(am.getMainTime()));
                jo.put("MaintRemark", am.getMaintRemark());
                jo.put("MaintMan", am.getMaintMan());

                main.add(jo);
            }
        }
        PrintWriter out = response.getWriter();
        out.println(main.toString());
        out.flush();
        out.close();
    }
}
