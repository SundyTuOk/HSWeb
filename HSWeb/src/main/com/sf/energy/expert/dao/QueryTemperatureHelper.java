package com.sf.energy.expert.dao;

import java.sql.SQLException;

public interface QueryTemperatureHelper {

	public  int  getTemperature(int year, int month) throws SQLException;
}
