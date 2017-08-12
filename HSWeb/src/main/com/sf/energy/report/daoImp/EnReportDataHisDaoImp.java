package com.sf.energy.report.daoImp;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import com.sf.energy.report.model.EnReportDataHisModel;

@WebService
public interface EnReportDataHisDaoImp {

	/**
	 * 查询能耗上报历史记录
	 * @return
	 * @throws SQLException
	 */
	public List<EnReportDataHisModel> queryEnReportDataHis() throws SQLException;
}
