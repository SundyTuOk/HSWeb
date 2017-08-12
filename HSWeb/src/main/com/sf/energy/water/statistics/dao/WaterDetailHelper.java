package com.sf.energy.water.statistics.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sf.energy.statistics.model.ArcAmDetailData;
import com.sf.energy.water.statistics.model.WaterDetailData;

public interface WaterDetailHelper {

	public List<WaterDetailData> getWaterDetailBetween(int Architecture_ID, String start,String end) throws SQLException;
	public String getArchitectureName(int Architecture_id);
	public List<WaterDetailData> getWaterDetailBetweenByPartment(int p_id,String start,String end) throws SQLException;
	public Map<Integer, String> getAllParInfo();
	public String getPartmentName(int p_id);

}
