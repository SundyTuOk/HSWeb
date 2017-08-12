package com.sf.energy.statistics.dao;

import java.sql.SQLException;
import java.util.List;

import com.sf.energy.statistics.model.AmLoadTrendData;

public interface AmLoadTrendHelper {
	public List<AmLoadTrendData> getAmLoadTrend(int Ammeter_ID, String date)  throws SQLException ;
	
}
