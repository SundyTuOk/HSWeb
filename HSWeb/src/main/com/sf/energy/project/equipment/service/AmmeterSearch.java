package com.sf.energy.project.equipment.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.equipment.model.AmmeterModel;

/**
 * 对电能表【Ammeter】操作, 通过相关信息查询电能表【Ammeter】信息
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public interface AmmeterSearch
{
	/**
	 * 通过电表名称查询电表
	 * 
	 * @param name
	 *            电表名称
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByName(String name) throws SQLException;

	/**
	 * 通过区域ID查询电表
	 * 
	 * @param areaId
	 *            区域ID
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByAreaID(int areaId)
			throws SQLException;

	/**
	 * 通过建筑ID查询电表
	 * 
	 * @param architectureID
	 *            建筑id
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByArchitectureID(int architectureID)
			throws SQLException;

	/**
	 * 通过用户号查询电表
	 * 
	 * @param conNum
	 *            用户号
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByComsumerNum(int conNum)
			throws SQLException;

	/**
	 * 通过网关id查询电表
	 * 
	 * @param gatherID
	 *            网关id
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByGatherID(int gatherID)
			throws SQLException;

	/**
	 * 通过电表地址码id查询电表
	 * 
	 * @param address
	 *            电表地址
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByAmmeterAddress485(String address)
			throws SQLException;

	/**
	 * 查询某建筑下某楼层的所有电表信息
	 * 
	 * @param archID
	 *            建筑ID
	 * @param floor
	 *            楼层
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByArchFloor(int archID, int floor)
			throws SQLException;

	/**
	 * 查询出某部门的所有电表
	 * 
	 * @param partmentID
	 *            部门ID
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByPartmentID(int partmentID)
			throws SQLException;

	/**
	 * 根据电表用途查询所有电表 1教学、2公共、3生活、4商业
	 * 
	 * @param useStyleID
	 *            用途ID
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByUseStyle(int useStyleID)
			throws SQLException;

	/**
	 * 根据电表性质查询所有电表 A照明,B空调,C动力,D特殊
	 * 
	 * @param propertyID
	 *            用途ID
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> queryByProperty(String propertyID)
			throws SQLException;

	/**
	 * 根据区域、建筑、网关、地址码、名称和用户号联合查询电表
	 * 
	 * @param areaID
	 *            区域
	 * @param archID
	 *            建筑
	 * @param gatherID
	 *            网关
	 * @param ammAddress
	 *            地址码
	 * @param ammeterName
	 *            名称
	 * @param consumerNum
	 *            用户号
	 * @return 电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> complexQueryAmm(int areaID, int archID,
			int gatherID, String ammAddress, String ammeterName,
			String consumerNum) throws SQLException;

	public ArrayList<AmmeterModel> queryAmmPaginate(int areaID, int archID,
			int gatherID, String ammAddress, String ammeterName,
			String consumerNum, int pageCount, int pageIndex)
			throws SQLException;

	public int getSearchCount(int areaID, int archID, int gatherID,
			String ammAddress, String ammeterName, String consumerNum)
			throws SQLException;
}
