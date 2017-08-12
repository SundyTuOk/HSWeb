package com.sf.energy.util;

import java.util.Date;

public interface ResultDataSet
{
    // 按列名获取
    public Object getObject(String lable);

    public Integer getInt(String lable);

    public Float getFloat(String lable);

    public Double getDouble(String lable);

    public String getString(String lable);

    public Date getDate(String lable);

    // 按索引获取
    public Object getObject(int index);

    public Integer getInt(int index);

    public Float getFloat(int index);

    public Double getDouble(int index);

    public String getString(int index);

    public Date getDate(int index);

}
