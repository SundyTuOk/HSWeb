package com.sf.energy.project.equipment.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.energy.project.equipment.model.Gather;

/**
 * 对数据网关信息查找操作 此接口定义了5种查找方法，1.通过区域名查找 2.通过数据网关状态查找 3.通过地址码查找 4.通过名称查找 5.通过编号查找
 * 
 * @author 李涵淼
 * @version 1.0
 * @since version 1.0 
 * 
 */
public interface GatherServiceDao
{
	/**
	 * 通过区域名查找数据网关
	 * 
	 * @param name
	 *            传入的区域名
	 * @return 返回的对象为Gather的List
	 * @throws SQLException 
	 */

	public List<Gather> findGatherByArea(String name) throws SQLException;

	/**
	 * 通过数据网管采集状态查找数据网关
	 * 
	 * @param state
	 *            传入采集信息的状态 （是否采集）
	 * @return 返回对象为Gather的List
	 * @throws Throwable 
	 */
	public List<Gather> findGatherByState(int state) throws Throwable;

	/**
	 * 通过数据网管的地址查找数据网关
	 * 
	 * @param address
	 *            传入数据网关的地址
	 * @return 返回对象为Gather的List
	 * @throws SQLException 
	 */
	public List<Gather> findGatherByAddress(String address) throws SQLException;

	/**
	 * 通过数据网管的名称查找数据网关
	 * 
	 * @param name
	 *            传入数据网关的名称
	 * @return 返回对象为Gahter的List
	 * @throws SQLException 
	 */
	public List<Gather> findGatherByName(String name) throws SQLException;

	/**
	 * 通过数据网管的编号查找数据网关
	 * 
	 * @param num
	 *            传入数据网关的编号
	 * @return 返回对象为Gahter的List
	 * @throws SQLException 
	 */
	public List<Gather> findGatherNum(String num) throws SQLException;
	
	/**
	 * 通过区域、状态、地址码、名称和编号查询出网关
	 * @param areaID 区域
	 * @param state 状态
	 * @param gatherAddress 地址码
	 * @param gatherName 名称
	 * @param gatherNum 编号
	 * @return 返回对象为Gahter的集合
	 * @throws SQLException
	 */
	public ArrayList<Gather> complexQueryGather(int areaID, int state, String gatherAddress, String gatherName, String gatherNum )
	throws SQLException;

}
