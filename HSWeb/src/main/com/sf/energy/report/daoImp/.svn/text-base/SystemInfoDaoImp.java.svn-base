package com.sf.energy.report.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebService;

import com.sf.energy.report.model.SystemInfoModel;

@WebService
public interface SystemInfoDaoImp {
	public SystemInfoModel querySystemInfo(int id) throws SQLException;
	
	public SystemInfoModel querySystemInfo2(int id) throws SQLException;
	
	/**
	 * 查询出启用了手工录入的能源
	 * @return 能源编码的id集合
	 * @throws SQLException 
	 */
	public  ArrayList<Integer> queryStartManualEnergy() throws SQLException;
}
