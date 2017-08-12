package com.sf.energy.map.daoImp;

import java.sql.SQLException;

import javax.jws.WebService;

import com.sf.energy.map.model.ArchitrctureMode;
@WebService
public interface ArchitrctureDaoImp {
	/**
	 * 查询建筑信息
	 * @param id 建筑id
	 * @return
	 * @throws SQLException
	 */
	public ArchitrctureMode queryArchitrcture(int id) throws SQLException;
}
