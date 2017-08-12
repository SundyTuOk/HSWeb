package com.sf.energy.map.daoImp;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import com.sf.energy.map.model.AmMeterModel;

@WebService
public interface AmMeterDaoImp {
	/**
	 * 查询某个电表的相关信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public AmMeterModel queryAmMeter(int id) throws SQLException;
	
	/**
	 * 查询某个建筑下某个楼层下的电表信息
	 * @param arcId
	 * @param floor
	 * @return
	 * @throws SQLException
	 */
	public List<AmMeterModel> queryArcFoolAmMeter(int arcId,int floor) throws SQLException;
	
	/**
	 * 查询该建筑有多少层
	 * @param arcId
	 * @return
	 * @throws SQLException
	 */
	public int queryArcFloor(int arcId) throws SQLException;
}
