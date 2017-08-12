package com.sf.energy.project.equipment.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.util.ConnDB;

/**
 * 对电能表【Ammeter】中父子电表关系的操作, 通过相关信息查询电能表【Ammeter】的父、子电表信息
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class AmmeterFiliationImpl implements
		com.sf.energy.project.equipment.service.AmmeterFiliation
{
	/**
	 * 获取某电表的父电表
	 * 
	 * @param amChild
	 *            子电表实例
	 * @return amParent 父电表实例
	 * @throws SQLException
	 */
	public AmmeterModel getParentAmmeter(AmmeterModel amChild)
			throws SQLException
	{
		AmmeterModel amParent = new AmmeterModel();
		String sql = "SELECT * FROM AMMETER WHERE Ammeter_ID ="
				+ amChild.getParentID();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				amParent.setAmmeterID(rs.getInt(1));
				amParent.setAmmeterPoint(rs.getInt(2));
				amParent.setAreaID(rs.getInt(3));
				amParent.setArchitectureID(rs.getInt(4));
				amParent.setGatherID(rs.getInt(5));
				amParent.setAmmeterNum(rs.getString(6));
				amParent.setAmmeterName(rs.getString(7));
				amParent.setAmmeterPassword(rs.getString(8));
				amParent.setAmmeterAddress485(rs.getString(9));
				amParent.setMaker(rs.getString(10));
				amParent.setMakerCode(rs.getString(11));
				amParent.setAssetNo(rs.getString(12));
				amParent.setIsSupply(rs.getInt(13));
				amParent.setzValue(rs.getDouble(14));
				amParent.setjValue(rs.getDouble(15));
				amParent.setfValue(rs.getDouble(16));
				amParent.setpValue(rs.getDouble(17));
				amParent.setgValue(rs.getDouble(18));
				amParent.setUseAmStyle(rs.getInt(19));
				amParent.setConsumerNum(rs.getString(20));
				amParent.setConsumerName(rs.getString(21));
				amParent.setConsumerPhone(rs.getString(22));
				amParent.setConsumerAddress(rs.getString(23));
				amParent.setIsImportantUser(rs.getInt(24));
				amParent.setIsCountMeter(rs.getInt(25));
				amParent.setIsComputation(rs.getInt(26));
				amParent.setAmmeterPlose(rs.getInt(27));
				amParent.setPartment(rs.getInt(28));
				amParent.setFloor(rs.getInt(29));
				amParent.setMeteStyleID(rs.getInt(30));
				amParent.setPriceID(rs.getInt(31));
				amParent.setAmmeterStat(rs.getInt(32));
				amParent.setIsOffAlarm(rs.getInt(33));
				amParent.setCurPower(rs.getDouble(34));
				amParent.setCostCheck(rs.getInt(35));
				amParent.setStandByCheck(rs.getInt(36));
				amParent.setXiuzheng(rs.getInt(37));
				amParent.setLastTime(rs.getString(38));
				amParent.setParentID(rs.getInt(39));

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
		return amParent;
	}

	/**
	 * 获取某电表的所有子表
	 * 
	 * @param amParent
	 *            父电表实例
	 * @return 子电表实体类的实例的集合
	 * @throws SQLException
	 */
	public ArrayList<AmmeterModel> getChildAmmeter(AmmeterModel amParent)
			throws SQLException
	{
		ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
		AmmeterModel amChild = null;
		String sql = "SELECT * FROM AMMETER WHERE ParentID ="
				+ amParent.getAmmeterID();
		Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				amChild = new AmmeterModel();

				amChild.setAmmeterID(rs.getInt(1));
				amChild.setAmmeterPoint(rs.getInt(2));
				amChild.setAreaID(rs.getInt(3));
				amChild.setArchitectureID(rs.getInt(4));
				amChild.setGatherID(rs.getInt(5));
				amChild.setAmmeterNum(rs.getString(6));
				amChild.setAmmeterName(rs.getString(7));
				amChild.setAmmeterPassword(rs.getString(8));
				amChild.setAmmeterAddress485(rs.getString(9));
				amChild.setMaker(rs.getString(10));
				amChild.setMakerCode(rs.getString(11));
				amChild.setAssetNo(rs.getString(12));
				amChild.setIsSupply(rs.getInt(13));
				amChild.setzValue(rs.getDouble(14));
				amChild.setjValue(rs.getDouble(15));
				amChild.setfValue(rs.getDouble(16));
				amChild.setpValue(rs.getDouble(17));
				amChild.setgValue(rs.getDouble(18));
				amChild.setUseAmStyle(rs.getInt(19));
				amChild.setConsumerNum(rs.getString(20));
				amChild.setConsumerName(rs.getString(21));
				amChild.setConsumerPhone(rs.getString(22));
				amChild.setConsumerAddress(rs.getString(23));
				amChild.setIsImportantUser(rs.getInt(24));
				amChild.setIsCountMeter(rs.getInt(25));
				amChild.setIsComputation(rs.getInt(26));
				amChild.setAmmeterPlose(rs.getInt(27));
				amChild.setPartment(rs.getInt(28));
				amChild.setFloor(rs.getInt(29));
				amChild.setMeteStyleID(rs.getInt(30));
				amChild.setPriceID(rs.getInt(31));
				amChild.setAmmeterStat(rs.getInt(32));
				amChild.setIsOffAlarm(rs.getInt(33));
				amChild.setCurPower(rs.getDouble(34));
				amChild.setCostCheck(rs.getInt(35));
				amChild.setStandByCheck(rs.getInt(36));
				amChild.setXiuzheng(rs.getInt(37));
				amChild.setLastTime(rs.getString(38));
				amChild.setParentID(rs.getInt(39));

				lst.add(amChild);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
	
		return lst;
	}

}
