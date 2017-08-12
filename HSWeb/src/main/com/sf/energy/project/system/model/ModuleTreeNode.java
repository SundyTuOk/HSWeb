package com.sf.energy.project.system.model;

import java.util.List;

import com.sf.energy.project.right.model.ModuleModel;

public class ModuleTreeNode
{
    ModuleModel moduel = null;
    List<ModuleTreeNode> sonList = null;

    public ModuleModel getModuel()
    {
        return moduel;
    }

    public void setModuel(ModuleModel moduel)
    {
        this.moduel = moduel;
    }

    public List<ModuleTreeNode> getSonList()
    {
        return sonList;
    }

    public void setSonList(List<ModuleTreeNode> sonList)
    {
        this.sonList = sonList;
    }

}
