package com.sf.energy.project.equipment.service;

import java.sql.SQLException;
import java.util.ArrayList;
import com.sf.energy.project.equipment.model.AmmeterModel;


/**
 * 对电能表【Ammeter】中父子电表关系的操作, 通过相关信息查询电能表【Ammeter】的父、子电表信息
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public interface AmmeterFiliation
{
	/**
	 * 获取某电表的父电表
	 * 
	 * @param amChild
	 *            子电表实例
	 * @return 父电表实例
	 * @throws SQLException
	 */
	public AmmeterModel getParentAmmeter(AmmeterModel amChild)
			throws SQLException;
	
	/**
	 * 获取某电表的所有子表
	 * 
	 * @param amParent
	 *            父电表实例
	 * @return  子电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> getChildAmmeter(AmmeterModel amParent)
			throws SQLException;
	
}