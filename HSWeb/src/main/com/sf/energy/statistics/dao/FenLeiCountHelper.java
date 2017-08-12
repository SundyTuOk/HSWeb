package com.sf.energy.statistics.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sf.energy.statistics.model.EnergyRecycleModel;
import com.sf.energy.statistics.model.FenLeiCountModel;

public interface FenLeiCountHelper {
	public List<FenLeiCountModel> getArcFenLeiDataEveryMonthInArea(int areaID, int queryYear) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataEveryMonthInArch(int iD, int queryYear) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataEveryDayInArea(int areaID, int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataByMonthInArea(int areaID, int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataByYearInArea(int areaID, int queryYear) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataEveryDayInArch(int archID, int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataByMonthInArch(int archID, int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getArcFenLeiDataByYearInArch(int archID, int queryYear) throws SQLException;

	public List<FenLeiCountModel> getAllFenLeiDataByMonth(int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getAllFenLeiDataByYear(int queryYear) throws SQLException;

	public List<FenLeiCountModel> getAllFenLeiDataEveryDay(int queryYear, int queryMonth) throws SQLException;

	public List<FenLeiCountModel> getAllFenLeiDataEveryMonth(int queryYear) throws SQLException;
	
	public List<EnergyRecycleModel> getEnergyRecycle(Date startDate,Date endDate) throws SQLException;

}
