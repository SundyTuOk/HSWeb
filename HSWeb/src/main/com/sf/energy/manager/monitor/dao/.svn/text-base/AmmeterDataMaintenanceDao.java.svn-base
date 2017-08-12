package com.sf.energy.manager.monitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sf.energy.project.equipment.dao.AmmeterDao;
import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.statistics.dao.ElecReportHelper;
import com.sf.energy.statistics.dao.ElecReportHelperImpl;
import com.sf.energy.statistics.model.AmMeterMataData;
import com.sf.energy.util.ConnDB;

public class AmmeterDataMaintenanceDao
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ElecReportHelper rh = new ElecReportHelperImpl();

	public boolean updateAmmeterData(AmMeterMataData amd)
	{
		boolean flag = false;

		String sql = "UPDATE ZAMDATAS"+String.valueOf(amd.getAmMeterID())+" SET ZValueZY = ? WHERE ValueTime = to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps =null;
		Connection conn=null;
		try
		{
			conn=ConnDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setFloat(1, amd.getValue());
			//ps.setInt(2, amd.getAmMeterID());
			ps.setString(2, df.format(amd.getRecordTimeDate()));

			ps.executeUpdate();
			flag = true;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}finally{
			ConnDB.release(conn, ps);
		}

		return flag;
	}

	public List<AmMeterMataData> getAmDataBetween(int ammeterID, Date start,
			Date end) throws SQLException
			{
		List<AmMeterMataData> list = null;

		list = rh.getOriginalAmmeterDataBetween(ammeterID, start, end);

		return list;
			}

	/**
	 * 指定电表数据表删除数据
	 * 删除指定时间段内插补的数据（isinsert=1）
	 *
	 * 只在插入补充数据时有用
	 * @param ammterID
	 */
	public void deleteAmData(int ammeterid,Date start,Date end) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String sql = "delete from ZAMDATAS"+String.valueOf(ammeterid)+" where isinsert = 1 and valuetime between to_date('"
					+df.format(start)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"
					+df.format(end)+"','yyyy-mm-dd hh24:mi:ss')";					
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {

		}finally{
			ConnDB.release(conn, ps);
		}		
	}

	/**
	 * 电能数据表中存放的数据为 电表实时读数*倍率+修正量 
	 * 得到电表实时示数
	 * @param list 电能数据表中的数据集合
	 * @param ammeterID 电表id
	 * @return
	 * @throws SQLException 
	 */
	public List<AmMeterMataData> getRealDisplay(List<AmMeterMataData> list,int ammeterID) throws SQLException{
		List<AmMeterMataData> rList = new ArrayList<AmMeterMataData>();
		AmmeterDao ad = new AmmeterDao();
		AmmeterModel am = new AmmeterModel();
		am = ad.queryByID(ammeterID);
		if(list!=null){
			if(am!=null&&list.size()>0){
				for(AmMeterMataData n:list){
					AmMeterMataData m = new AmMeterMataData();
					m.setAmMeterID(ammeterID);
					m.setHour(n.getHour());
					m.setIsInsert(n.getIsInsert());
					m.setRecordTimeDate(n.getRecordTimeDate());
					m.setTemperature(n.getTemperature());
					m.setValue(((n.getValue()-am.getXiuzheng())/am.getBeilv()));
					rList.add(m);
				}
			}
		}
		return rList;
	}

	public List<AmMeterMataData> getShapingAmDataBetween(int ammeterID, Date start,
			Date end,List<AmMeterMataData> list) throws SQLException
			{
		List<AmMeterMataData> rlist = null;
		ElecReportHelper rh = new ElecReportHelperImpl();
		rlist = rh.getShapingAmDataBetween(ammeterID, start, end, list);

		return rlist;
			}
	
	public AmMeterMataData queryLastData(int ammeterID, Date start) throws SQLException
	{
		ElecReportHelper rh = new ElecReportHelperImpl();
		AmMeterMataData amd = null;

		amd = rh.getOriginalAmmeterLastData(ammeterID, start);

		return amd;
	}
	public static long getDistanceTimes(Date one, Date two) {
	
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		long times = diff/1000/60;
		return times;
	}

	public AmMeterMataData queryBhindData(int ammeterID, Date end) throws SQLException
	{
		ElecReportHelper rh = new ElecReportHelperImpl();
		AmMeterMataData amd = null;

		amd = rh.getOriginalAmmeterBehindData(ammeterID, end);

		return amd;
	}

	/**批处理
	 * 向指定电表数据表中插入数据
	 * 只在插入补充数据时有用
	 * @param ammterID
	 * @throws SQLException 
	 */
	public void insertAmData(List<AmMeterMataData> list) throws SQLException {
		Connection conn = null;
		Statement ps = null;
		try {
			//System.out.println(sql);
			conn = ConnDB.getConnection();
			conn.setAutoCommit(false);
			ps=conn.createStatement();
			for(int i=0;i<list.size();i++){
				AmMeterMataData am = list.get(i);
				int ammterID = am.getAmMeterID();
				String sql = "Insert into ZAMDATAS"+String.valueOf(ammterID)+"(VALUETIME,ZVALUEZY,ISINSERT) VALUES(to_date('"
						+df.format(am.getRecordTimeDate())+"','yyyy-mm-dd hh24:mi:ss'),"
						+am.getValue()+","+am.getIsInsert()+")";
				ps.addBatch(sql);
				//System.out.println("第 "+(i+1)+" 条数据" + sql);
				if(i%1000==0){
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			ps.executeBatch();
			//ps.clearBatch();
			conn.commit();
			//System.out.println("插入数据库成功！");
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.setAutoCommit(true);
			ConnDB.release(conn, ps);
		}		
	}

}
