package com.sf.energy.project.equipment.service;
import org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.sf.energy.project.equipment.service.AmmeterFiliation;
import com.sf.energy.project.equipment.model.AmmeterModel;
/**
 * 获取某电表的父、子电表功能的测试用例
 * 
 * @author 鄢浩
 * @version 1.0
 * @see
 * @since version 1.0
 */
public class TestAmmeterFiliation
{
	/**
	 * 测试获取父表
	 * @throws SQLException 
	 */
	@Test
	public void testGetParent() throws SQLException
	{
		AmmeterModel amChild=new AmmeterModel();
		AmmeterModel amParent=new AmmeterModel();
		AmmeterFiliation af=new AmmeterFiliation();
		
		//获得ID为1500的电表的父电表，预期为1473
		amChild.setParentID(1473);
		
		amParent=af.getParentAmmeter(amChild); 
		
		Assert.assertEquals(1473, amParent.getAmmeterID());
	}
	
	/**
	 * 测试获取子表
	 * @throws SQLException 
	 */
	@Test
	public void testGetChild() throws SQLException
	{
		ArrayList<AmmeterModel> lst=new ArrayList<AmmeterModel>();
		AmmeterModel amChild=new AmmeterModel();
		AmmeterModel amParent=new AmmeterModel();
		AmmeterFiliation af=new AmmeterFiliation();
		
		//获取电表ID为1473的所有子表，结果为1498,1499,1500,1501
		amParent.setAmmeterID(1473);
		
		lst=af.getChildAmmeter(amParent);
		
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i).getAmmeterID());
		}
	}
}
