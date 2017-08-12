package com.sf.energy.report.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.sf.energy.report.model.EnReportModel;

@WebService
public interface EnReportDaoImp {

	/**
	 * 根据id查询能耗信息
	 * @param id 要查询能耗信息的编号
	 * @return  能耗的Model
	 * @throws SQLException
	 */
	public List<EnReportModel> queryEnReport(int id) throws SQLException;
	

	/**
	 * 对EnReport表进行修改
	 * @param id
	 * @param enReportModel
	 * @return
	 * @throws SQLException
	 */
	public boolean updateEnReport(int id,EnReportModel enReportModel) throws SQLException;
	
	/**
	 * 获取插入表的ID
	 * @return
	 * @throws SQLException 
	 */
	public int getEnReportId() throws SQLException;
	
	/**
	 * 通过能耗名称查询折标系数
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public float queryCoefficientByName(String name) throws SQLException;
	

	/**
	 * 列出Enreport表中所有的行
	 * @return  能耗的Model组成的集合
	 * @throws SQLException
	 */
	public ArrayList<EnReportModel> listEnReport() throws SQLException;
	
	/**
	 * 列出启用的能源
	 * @return  能耗的Model组成的集合
	 * @throws SQLException
	 */
	public ArrayList<EnReportModel> listManualEnReport() throws SQLException;
}
