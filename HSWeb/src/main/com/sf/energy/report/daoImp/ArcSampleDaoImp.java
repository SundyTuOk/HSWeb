package com.sf.energy.report.daoImp;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.sf.energy.report.model.ArcSampleModel;

@WebService
public interface ArcSampleDaoImp {
	/**
	 * 根据查询的条件获取所需的建筑  -1代表所有
	 * @return
	 * @throws SQLException 
	 */
	
	public List<ArcSampleModel> queryArcSample(String ArcSamplesortName,String ArcSampleorder,String arcSampleArcStyle,String arcSampleID,int pageCount,int pageIndex) throws SQLException;
	
	/**
	 * 保存被抽样的建筑
	 * @throws SQLException 
	 */
	public boolean checkArcSample(String id,String isEnReport) throws SQLException;
	public int getTotalCount();
}
