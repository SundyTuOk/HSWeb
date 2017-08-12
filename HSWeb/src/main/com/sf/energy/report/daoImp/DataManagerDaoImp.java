package com.sf.energy.report.daoImp;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import com.sf.energy.report.model.EnReportDataModel;

@WebService
public interface DataManagerDaoImp {
	/**
	 * 查询数据表管理的数据
	 * @return
	 * @throws SQLException
	 */
	public List<EnReportDataModel> queryDataManager() throws SQLException;
	
	/**
	 * 根据id删除数据表的数据
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public boolean deleteDataManager(int id) throws SQLException;
	
	/**
	 * 增加EnReportData的数据
	 * @param enReportDataModel
	 * @return
	 * @throws SQLException
	 */
	public boolean addDataManager(EnReportDataModel enReportDataModel) throws SQLException;
	
	/**
	 * 获取DataManager的
	 * @return
	 * @throws SQLException
	 */
	public int getDataManagerId() throws SQLException;
	
	/**
	 * 编辑
	 * @param enReportDataModel
	 * @return
	 * @throws SQLException
	 */
	public boolean editDataManager(EnReportDataModel enReportDataModel) throws SQLException;
}
