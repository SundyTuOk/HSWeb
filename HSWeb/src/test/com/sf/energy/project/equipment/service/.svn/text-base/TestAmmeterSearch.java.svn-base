package com.sf.energy.project.equipment.service;

import org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


import com.sf.energy.project.equipment.model.AmmeterModel;


/**
 * 数据表[Ammeter]操作的测试用例
 * 
 * @author 鄢浩
 * @version 1.0
 * @see
 * @since version 1.0
 */
public class TestAmmeterSearch
{
	@Test
	public void test1() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterModel am=new AmmeterModel();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByAmmeterAddress485("001301344281");
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test2() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByArchFloor(32, 5);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test3() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByAreaID(2);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	
	@Test
	public void test4() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByComsumerNum(11);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test5() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByGatherID(12);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test6() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByName("中508");
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}

	@Test
	public void test7() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByPartmentID(2);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test8() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByProperty("B");
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}

	@Test
	public void test9() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByArchitectureID(32);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
	
	@Test
	public void test10() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterSearch as=new AmmeterSearch();
		lst=as.queryByUseStyle(2);
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
		System.out.println(" ");
	}
}