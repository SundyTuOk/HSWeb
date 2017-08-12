package com.sf.energy.statistics.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.model.ArcAmDetailData;

public interface AmHelper {
	public List<ArcAmDetailData> getAmDetailBetween(int Architecture_ID, String start,String end) throws SQLException;
	public String getArchitectureName(int Architecture_id) throws SQLException;
	public List<ArcAmDetailData> getAmDetailBetweenByPartment(int p_id,String start,String end) throws SQLException;
	public Map<Integer, String> getAllParInfo() throws SQLException;
	public String getPartmentName(int p_id) throws SQLException;
}
