package com.sf.energy.expert.forecast;

import java.sql.SQLException;

import com.sf.energy.project.system.dao.InformationDao;
import com.sf.energy.project.system.model.InformationModel;

/**
 * 通过去年的建筑数据预测今年的能源消耗
 * 主要通过人口，建筑面积预测
 * @author yanhao
 *
 */
public class ForecastByManArea
{
	/**
	 * 通过建筑人口和面积预测下一年的能耗
	 * @param lastYear 前一年
	 * @param thisYear 今年
	 * @param lastEnergyValue 前一年能耗
	 * @return 今年能耗
	 * @throws SQLException 
	 */
	public float getForecastData(int lastYear,int thisYear,float lastEnergyValue) throws SQLException
	{
		InformationDao infoDao=new InformationDao();
		InformationModel lastInfoModel=null;
		InformationModel thisInfoModel=null;
		float unitLastValue=0;
		float thisYearValue=0;
		
		lastInfoModel=infoDao.queryByTime(lastYear);
		thisInfoModel=infoDao.queryByTime(thisYear);
		if(lastInfoModel.getArchArea()==0||lastInfoModel.getCountMan()==0)
		{
			unitLastValue=0;
		}
		else {
			unitLastValue=lastEnergyValue/(lastInfoModel.getArchArea()*lastInfoModel.getCountMan());
		}
		
		thisYearValue=unitLastValue*thisInfoModel.getArchArea()*thisInfoModel.getCountMan();
		
		return thisYearValue;
	}
}
