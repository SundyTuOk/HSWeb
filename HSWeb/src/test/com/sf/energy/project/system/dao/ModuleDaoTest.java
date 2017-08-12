package com.sf.energy.project.system.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.sf.energy.project.right.dao.ModuleDao;
import com.sf.energy.project.system.model.ModuleTreeNode;

public class ModuleDaoTest
{
    ModuleDao md = new ModuleDao();

    @Test
    public void getModuleTree() throws SQLException
    {
        List<ModuleTreeNode> treeList = md.getModuleTree();

        System.out.println(treeList.size());
    }

    @Test
    public void buildParentSelect() throws SQLException
    {
        List<ModuleTreeNode> treeList = md.getModuleTree();

        System.out.println(buildParentSelect(treeList, ""));
    }

    private String buildParentSelect(List<ModuleTreeNode> list, String spliter)
    {
        String select = "";

        for (ModuleTreeNode node : list)
        {
            select += "<option value='" + node.getModuel().getModuleID() + "'>"
                    + spliter + node.getModuel().getModuleName()
                    + "</option>\n";
            if (node.getSonList() != null)
                select += buildParentSelect(node.getSonList(), spliter
                        + "&nbsp;∟");
        }

        return select;
    }
}