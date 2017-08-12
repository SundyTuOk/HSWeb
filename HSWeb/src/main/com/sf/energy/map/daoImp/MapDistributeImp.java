package com.sf.energy.map.daoImp;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import com.sf.energy.map.model.MapDistributeModel;

@WebService
public interface MapDistributeImp {
	public List<MapDistributeModel> getMapMark(String LableList_ID) throws SQLException;
	
	public boolean addMapMark(MapDistributeModel mapDistributeModel) throws SQLException;
	
	public boolean deleteMapMar(String LABLELIST_ID,String DATE_ID) throws SQLException;

	public boolean deleteMapMarkAll(String lableList) throws SQLException;

}
