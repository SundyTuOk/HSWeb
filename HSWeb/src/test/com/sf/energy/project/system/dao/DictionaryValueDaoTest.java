package com.sf.energy.project.system.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.sf.energy.project.system.model.DictionaryValueModel;

public class DictionaryValueDaoTest
{

	DictionaryValueDao dvd = new DictionaryValueDao();

	@Test
	public void listDictionaryValueByName()
	{
		try
		{
			List<DictionaryValueModel> list = dvd
					.listDictionaryValueByName("部门信息");

			print(list);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getDictionaryValueByValueID()
	{
		try
		{
			DictionaryValueModel dvm = dvd.getDictionaryValueByValueID(1);

			print(dvm);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getDictionaryValueByDictionaryID()
	{
		try
		{
			List<DictionaryValueModel> list = dvd
					.getDictionaryValueByDictionaryID(103);

			print(list);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addDictionaryValue()
	{
		DictionaryValueModel dvm = new DictionaryValueModel();
		dvm.setDictionaryID(1);
		dvm.setDictionaryValue("复仇者联盟");
		dvm.setDictionaryValueNum("Shield");
		try
		{
			dvd.addDictionaryValue(dvm);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void print(List<DictionaryValueModel> list)
	{
		for (DictionaryValueModel d : list)
		{
			print(d);
		}
	}

	private void print(DictionaryValueModel d)
	{
		System.out.println(d.getDictionaryValueID());
		System.out.println(d.getDictionaryValueNum());
		System.out.println(d.getDictionaryValue());
		System.out.println();
	}

}
