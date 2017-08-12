/**
 * 2014-4-26
 */
package com.sf.energy.project.right.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.right.dao.ModuleDao;
import com.sf.energy.project.right.dao.PowerDao;
import com.sf.energy.project.right.model.ModuleModel;
import com.sf.energy.project.right.model.PowerModel;
import com.sf.energy.project.system.model.ModuleTreeNode;
import com.sf.energy.util.ExportHelper;
import com.sf.energy.util.OperationLogImplement;
import com.sf.energy.util.OperationLogInterface;

/**
 * @author WangZhao 2014-4-26
 * 
 * 
 */
public class ModuleManage extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

    private void findMethod(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        //System.out.println("method:" + method);

        if ("getAllModule".equals(method))
            getAllModule(request, response);

        if ("deleteModule".equals(method))
            deleteModule(request, response);

        if ("addModule".equals(method))
            addModule(request, response);

        if ("updateModule".equals(method))
            updateModule(request, response);

        if ("getAllModuel".equals(method))
            getAllModel(request, response);

        if ("getModuleTree".equals(method))
            getModuleTree(request, response);
        
        if ("getAllModule".equals(method))
        	getAllModule(request, response);
        if ("upLoadImg".equalsIgnoreCase(method))
			upLoadImg(request, response);

    }
    private void upLoadImg(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
    	ExportHelper eh = ExportHelper.getInstance();
    	Map<String, String> imgMap = new HashMap<String, String>();
		String saveFolder = "./upfile/modle";
		File imgFile = eh
				.getImportFile(request, response, saveFolder, "theImg");

		JSONArray main = new JSONArray();

		JSONObject jo = new JSONObject();

		if (imgFile != null)
		{
			String path = imgFile.getAbsolutePath();
			jo.put("type", "success");
			jo.put("path",
					path.substring(path.lastIndexOf("upfile"), path.length()));

			HttpSession session = request.getSession();
			String sessionID = session.getId();
			if (imgMap.get(sessionID) != null)
			{
				File img = new File(imgMap.get(sessionID));
//				img.delete();
			}
			imgMap.put(sessionID, path);
		} else
		{
			jo.put("type", "fail");
		}

		main.add(jo);

		PrintWriter out = response.getWriter();
		out.println(main.toString());
		out.flush();
		out.close();
	}
    private void getModuleTree(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        ModuleDao md = new ModuleDao();
        List<ModuleTreeNode> list = md.getModuleTree();
        JSONArray listArray = new JSONArray();
        buildModuleList(list, listArray);
        JSONObject main = new JSONObject();
        main.put("treeData", buildTreeData(list, true));
        main.put("listData", listArray);
        main.put("parentData", buildParentSelect(list, ""));
        PrintWriter out = response.getWriter();
        out.println(main.toString());
        out.flush();
        out.close();
    }

    private String buildParentSelect(List<ModuleTreeNode> list, String spliter)
    {
        String select = "";

        for (ModuleTreeNode node : list)
        {
            select += "<option value='" + node.getModuel().getModuleID() + "'>"
                    + spliter + node.getModuel().getModuleName() + "</option>";
            if (node.getSonList() != null)
                select += buildParentSelect(node.getSonList(), spliter
                        + "&nbsp;∟");
        }

        return select;
    }

    private void buildModuleList(List<ModuleTreeNode> list, JSONArray listArray)
            throws IOException, SQLException
    {
        for (int i = 0; i < list.size(); i++)
        {
            ModuleModel n = list.get(i).getModuel();
            JSONObject jo = new JSONObject();
            jo.put("Module_ID", n.getModuleID());
            jo.put("Module_Name", n.getModuleName());
            jo.put("Module_WindowName", n.getModuleAddress());
            jo.put("Module_ParentModuleID", n.getModuleParent());
            jo.put("Module_Num", n.getModuleNum());
            jo.put("Module_Mark", n.getModuleMark());
            jo.put("Module_Part1", n.getModulePart1());
            jo.put("Module_Part2", n.getModulePart2().trim());// 暂时用在是否显示
            jo.put("Module_Order", n.getModuleOrder());
            jo.put("Module_Remark", n.getModuleRemark());
            jo.put("Module_Img", n.getModuleImage());
            listArray.add(jo);
            if (list.get(i).getSonList() != null)
                buildModuleList(list.get(i).getSonList(), listArray);
        }
    }

    private JSONObject buildTreeData(List<ModuleTreeNode> theTreeList,
            boolean isRoot)
    {
        JSONObject moduleTreeData = null;
        if (theTreeList != null && theTreeList.size() > 0)
        {
            moduleTreeData = new JSONObject();

            for (ModuleTreeNode t : theTreeList)
            {
                int moduleID = t.getModuel().getModuleID();
                String moduleName = t.getModuel().getModuleName() + " ("
                        + t.getModuel().getModuleNum() + ")";

                JSONObject jo = new JSONObject();

                jo.put("name", moduleName);
                jo.put("ModuleID", moduleID);

                if (t.getSonList() != null)
                {
                    jo.put("type", "folder");

                    jo.put("additionalParameters",
                            buildTreeData(t.getSonList(), false));
                }
                else
                {
                    jo.put("type", "item");
                }

                moduleTreeData.put(moduleID, jo);
            }

            if (!isRoot)
            {
                JSONObject childrenNode = new JSONObject();
                childrenNode.put("children", moduleTreeData);

                return childrenNode;
            }
        }

        return moduleTreeData;
    }

    private void getAllModel(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        ModuleDao md = new ModuleDao();
        // TODO Auto-generated method stub
        List<ModuleModel> list = md.listModule();

        JSONArray json = new JSONArray();
        for (ModuleModel n : list)
        {
            JSONObject jo = new JSONObject();
            jo.put("Module_ID", n.getModuleID());
            jo.put("Module_Name", n.getModuleName());
            json.add(jo);
        }

        response.setContentType("application/x-json");

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

    private void updateModule(HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException
    {
        ModuleDao md = new ModuleDao();
        boolean a;
        boolean b;
        String resultInfo = " ";
        ModuleModel module = new ModuleModel();

        module.setModuleID(Integer.parseInt(request.getParameter("Module_ID")));
        module.setModuleName(request.getParameter("Module_Name"));
        module.setModuleAddress(request.getParameter("Module_WindowName"));
        module.setModuleParent(Integer.parseInt(request
                .getParameter("Module_ParentModuleID")));
        module.setModuleNum(request.getParameter("Module_Num"));
        module.setModuleMark(request.getParameter("Module_Mark"));
        module.setModulePart1(request.getParameter("Module_Part1"));
        module.setModulePart2(request.getParameter("Module_Part2"));// 显示到菜单
        module.setModuleOrder(Integer.parseInt(request
                .getParameter("Module_Order")));
        module.setModuleRemark(request.getParameter("Module_Remark"));
        module.setModuleImage(request.getParameter("Module_Img"));
        a = md.modify(module);

        // 修改对应的一级权限信息
        PowerDao pd = new PowerDao();
        int powerID = pd.queryPowerIDByModuleID(Integer.parseInt(request
                .getParameter("Module_ID")));
        PowerModel pm = new PowerModel();
        pm.setPowerID(powerID);
        pm.setPowerName(request.getParameter("Module_Name"));
        pm.setPowerNum(request.getParameter("Module_Num") + "0");
        pm.setPowerLevel(0);
        pm.setModuleID(Integer.parseInt(request.getParameter("Module_ID")));
        pm.setPowerRemark("根据模块自动新增的一级权限");
        b = pd.modify(pm);

        if (a & b == true)
        {
            resultInfo = "success";
        }
        else
        {
            resultInfo = "failed";
        }
        OperationLogInterface log = new OperationLogImplement();
        HttpSession session = request.getSession();
        String userLoginName = (String) session.getAttribute("userName");
        log.writeLog(userLoginName, "模块管理", "编辑模块 "+module.getModuleName());

        PrintWriter out = response.getWriter();
        out.println(resultInfo);
        out.flush();
        out.close();
    }

    private void addModule(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        ModuleDao md = new ModuleDao();
        String moduleNum = request.getParameter("Module_Num");
        // 新增之前判断模块编码是否存在
        String hasSame = "NoSame";
        if (md.hasSameModuleNum(moduleNum) == true)// 有相同模块编码
        {
            hasSame = "HasSame";
            PrintWriter out = response.getWriter();
            out.println(hasSame);
            out.flush();
            out.close();
        }
        else
        {
            ModuleModel module = new ModuleModel();

            module.setModuleName(request.getParameter("Module_Name"));
            module.setModuleAddress(request.getParameter("Module_WindowName"));
            module.setModuleParent(Integer.parseInt(request
                    .getParameter("Module_ParentModuleID")));
            module.setModuleNum(request.getParameter("Module_Num"));
            module.setModuleMark(request.getParameter("Module_Mark"));
            module.setModulePart1(request.getParameter("Module_Part1"));
            module.setModulePart2(request.getParameter("Module_Part2"));// 显示到菜单
            module.setModuleOrder(Integer.parseInt(request
                    .getParameter("Module_Order")));
            module.setModuleRemark(request.getParameter("Module_Remark"));
            module.setModuleImage(request.getParameter("Module_Img"));
            String resultInfo = null;
            md.insert(module);

            // 自动生成对应的一级权限
            int newModuleID = md.queryModuleIDByNum(request
                    .getParameter("Module_Num"));
            PowerModel pm = new PowerModel();
            PowerDao pd = new PowerDao();
            pm.setPowerName(request.getParameter("Module_Name"));
            pm.setPowerNum(request.getParameter("Module_Num") + "0");
            pm.setPowerLevel(0);
            pm.setModuleID(newModuleID);
            pm.setPowerRemark("根据模块自动新增的一级权限");

            if (pd.insert(pm))
                resultInfo = "success";
            else
                resultInfo = "failed";

            OperationLogInterface log = new OperationLogImplement();
            HttpSession session = request.getSession();
            String userLoginName = (String) session.getAttribute("userName");
            log.writeLog(userLoginName, "模块管理", "新增模块  "+module.getModuleName());

            PrintWriter out = response.getWriter();
            out.println(resultInfo);
            out.flush();
            out.close();
        }

    }

    private void deleteModule(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        ModuleDao md = new ModuleDao();
        int ModuleID = Integer.parseInt(request.getParameter("Module_ID"));
       
        String resultInfo = null;
        // 先删除子模块
        ArrayList<Integer> sonList = new ArrayList<Integer>();
        sonList = md.querySonModule(ModuleID);
        for (int i = 0; i < sonList.size(); i++)
        {
            md.delete(sonList.get(i).intValue());
        }
        // 再删除父模块
        boolean b = md.delete(ModuleID);
        // 最后删除模块下的权限
        ArrayList<Integer> powerIDsList = new ArrayList<Integer>();
        PowerDao pd = new PowerDao();
        powerIDsList = pd.queryPowerIDsByModuleID(ModuleID);
        for (int i = 0; i < powerIDsList.size(); i++)
        {
            pd.delete(powerIDsList.get(i).intValue());
        }

        if (b == true)
        {
            resultInfo = "success";
        }
        else
        {
            resultInfo = "failed";
        }

        OperationLogInterface log = new OperationLogImplement();
        HttpSession session = request.getSession();
        String userLoginName = (String) session.getAttribute("userName");
        log.writeLog(userLoginName, "模块管理", "删除模块"+md.query(ModuleID).getModuleName()+ "及其子模块");

        PrintWriter out = response.getWriter();
        out.println(resultInfo);
        out.flush();
        out.close();
    }

    private void getAllModule(HttpServletRequest request,
            HttpServletResponse response) throws IOException, SQLException
    {
        ModuleDao md = new ModuleDao();
        // List<ModuleModel> data = md.listModule();
        // List<ModuleModel> list = organize(data);
        List<ModuleModel> list = md.listModule();
        JSONArray json = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            ModuleModel n = list.get(i);
            JSONObject jo = new JSONObject();
            jo.put("Module_ID", n.getModuleID());
            jo.put("Module_Name", n.getModuleName());
            jo.put("Module_WindowName", n.getModuleAddress());
            jo.put("Module_ParentModuleID", n.getModuleParent());
            jo.put("Module_Num", n.getModuleNum());
            jo.put("Module_Mark", n.getModuleMark());
            jo.put("Module_Part1", n.getModulePart1());
            jo.put("Module_Part2", n.getModulePart2().trim());// 暂时用在是否显示
            jo.put("Module_Order", n.getModuleOrder());
            jo.put("Module_Remark", n.getModuleRemark());
            json.add(jo);
        }

        response.setContentType("application/x-json");

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.flush();
        out.close();
    }

}