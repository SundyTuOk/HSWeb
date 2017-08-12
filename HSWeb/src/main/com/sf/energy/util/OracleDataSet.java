package com.sf.energy.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleDataSet implements ResultDataSet
{
    private Map<String, Object> data = new HashMap<String, Object>();
    private List<ItemInfo> infoList = new ArrayList<ItemInfo>();
    private List<String> numberType = Arrays.asList("NUMBER", "INTEGER",
            "FLOAT", "REAL");
    private List<String> stringType = Arrays.asList("CHAR", "VARCHAR2",
            "NCHAR", "NVARCHAR2");
    private List<String> dateType = Arrays.asList("DATE");

    public OracleDataSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        for (int i = 1; i <= columnCount; i++)
        {
            ItemInfo ii = new ItemInfo();
            ii.setName(meta.getColumnLabel(i));
            ii.setType(meta.getColumnTypeName(i));
            infoList.add(ii);
            data.put(ii.getName(), rs.getObject(i));
        }
    }

    @Override
    public Object getObject(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equalsIgnoreCase(lable))
            {
                return (Integer) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public Integer getInt(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equalsIgnoreCase(lable)
                    && numberType.contains(ii.getType().toUpperCase()))
            {
                return (Integer) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public Float getFloat(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equalsIgnoreCase(lable)
                    && numberType.contains(ii.getType().toUpperCase()))
            {
                return (Float) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public Double getDouble(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equalsIgnoreCase(lable)
                    && numberType.contains(ii.getType().toUpperCase()))
            {
                return (Double) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public String getString(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equalsIgnoreCase(lable)
                    && stringType.contains(ii.getType().toUpperCase()))
            {
                return (String) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public Date getDate(String lable)
    {
        for (ItemInfo ii : infoList)
        {
            if (ii.getName().equals(lable)
                    && dateType.contains(ii.getType().toUpperCase()))
            {
                return (Date) data.get(lable);
            }
        }

        return null;
    }

    @Override
    public Object getObject(int index)
    {
        if (index >= 0 && index < infoList.size())
            return data.get(infoList.get(index).getName());
        else
            return null;
    }

    @Override
    public Integer getInt(int index)
    {
        if (index >= 0
                && index < infoList.size()
                && numberType.contains(infoList.get(index).getType()
                        .toUpperCase()))
            return (Integer) data.get(infoList.get(index).getName());
        else
            return null;
    }

    @Override
    public Float getFloat(int index)
    {
        if (index >= 0
                && index < infoList.size()
                && numberType.contains(infoList.get(index).getType()
                        .toUpperCase()))
            return (Float) data.get(infoList.get(index).getName());
        else
            return null;
    }

    @Override
    public Double getDouble(int index)
    {
        if (index >= 0
                && index < infoList.size()
                && numberType.contains(infoList.get(index).getType()
                        .toUpperCase()))
            return (Double) data.get(infoList.get(index).getName());
        else
            return null;
    }

    @Override
    public String getString(int index)
    {
        if (index >= 0
                && index < infoList.size()
                && numberType.contains(infoList.get(index).getType()
                        .toUpperCase()))
            return (String) data.get(infoList.get(index).getName());
        else
            return null;
    }

    @Override
    public Date getDate(int index)
    {
        if (index >= 0
                && index < infoList.size()
                && numberType.contains(infoList.get(index).getType()
                        .toUpperCase()))
            return (Date) data.get(infoList.get(index).getName());
        else
            return null;
    }

}
