package com.sf.energy.project.system.dao;

import org.junit.Test;

import com.sf.energy.project.system.model.DictionaryModel;

public class DictionaryDaoTest
{
    DictionaryDao dd = new DictionaryDao();

    @Test
    public void addDictionary()
    {
        DictionaryModel dm = new DictionaryModel();

        dm.setDictionaryName("神盾特工局");
        dm.setDictionaryRemark("这是一个和NB的地方");
    }
}
