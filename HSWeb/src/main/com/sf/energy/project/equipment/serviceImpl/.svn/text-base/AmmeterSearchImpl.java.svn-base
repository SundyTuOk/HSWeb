package com.sf.energy.project.equipment.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sf.energy.project.equipment.model.AmmeterModel;
import com.sf.energy.util.ConnDB;

/**
 * 对电能表【Ammeter】操作, 通过相关信息查询电能表【Ammeter】信息
 * 
 * @author 鄢浩
 * @version 1.0
 * @see com.sf.energy.util.ConnDB#executeQuery(String)
 * @see com.sf.energy.util.ConnDB#executeUpdate(String)
 * @since version 1.0
 */
public class AmmeterSearchImpl implements
        com.sf.energy.project.equipment.service.AmmeterSearch
{
    /**
     * 通过电表名称查询电表
     * 
     * @param name
     *            电表名称
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByName(String name) throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE Ammeter_Name='" + name + "'";
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));
			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 通过区域ID查询电表
     * 
     * @param areaId
     *            区域ID
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByAreaID(int areaId)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE Area_ID=" + areaId;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
 
        return lst;
    }

    /**
     * 通过建筑ID查询电表
     * 
     * @param architectureID
     *            建筑id
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByArchitectureID(int architectureID)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE Architecture_ID="
                + architectureID;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 通过用户号查询电表
     * 
     * @param conNum
     *            用户号
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByComsumerNum(int conNum)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE ConsumerNum=" + conNum;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 通过网关id查询电表
     * 
     * @param gatherID
     *            网关id
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByGatherID(int gatherID)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE Gather_ID=" + gatherID;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 通过电表地址码id查询电表
     * 
     * @param address
     *            电表地址
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByAmmeterAddress485(String address)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE AmMeter_485Address='"
                + address + "'";
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 查询某建筑下某楼层的所有电表信息
     * 
     * @param archID
     *            建筑ID
     * @param floor
     *            楼层
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByArchFloor(int archID, int floor)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * FROM Ammeter WHERE Architecture_ID=" + archID
                + "AND Floor=" + floor;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 查询出某部门的所有电表
     * 
     * @param partmentID
     *            部门ID
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByPartmentID(int partmentID)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * from Ammeter where partment_ID=" + partmentID;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    /**
     * 根据电表用途查询所有电表 1教学、2公共、3生活、4商业
     * 
     * @param useStyleID
     *            用途ID
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByUseStyle(int useStyleID)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * from Ammeter where UseAmStyle=" + useStyleID;
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return lst;
    }

    /**
     * 根据电表性质查询所有电表 A照明,B空调,C动力,D特殊
     * 
     * @param propertyID
     *            用途ID
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> queryByProperty(String propertyID)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        String sql = "SELECT * from Ammeter where Ammeter_Num LIKE '_____________"
                + propertyID + "__'";
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
			    am = new AmmeterModel();

			    am.setAmmeterID(rs.getInt(1));
			    am.setAmmeterPoint(rs.getInt(2));
			    am.setAreaID(rs.getInt(3));
			    am.setArchitectureID(rs.getInt(4));
			    am.setGatherID(rs.getInt(5));
			    am.setAmmeterNum(rs.getString(6));
			    am.setAmmeterName(rs.getString(7));
			    am.setAmmeterPassword(rs.getString(8));
			    am.setAmmeterAddress485(rs.getString(9));
			    am.setMaker(rs.getString(10));
			    am.setMakerCode(rs.getString(11));
			    am.setAssetNo(rs.getString(12));
			    am.setIsSupply(rs.getInt(13));
			    am.setzValue(rs.getDouble(14));
			    am.setjValue(rs.getDouble(15));
			    am.setfValue(rs.getDouble(16));
			    am.setpValue(rs.getDouble(17));
			    am.setgValue(rs.getDouble(18));
			    am.setUseAmStyle(rs.getInt(19));
			    am.setConsumerNum(rs.getString(20));
			    am.setConsumerName(rs.getString(21));
			    am.setConsumerPhone(rs.getString(22));
			    am.setConsumerAddress(rs.getString(23));
			    am.setIsImportantUser(rs.getInt(24));
			    am.setIsCountMeter(rs.getInt(25));
			    am.setIsComputation(rs.getInt(26));
			    am.setAmmeterPlose(rs.getInt(27));
			    am.setPartment(rs.getInt(28));
			    am.setFloor(rs.getInt(29));
			    am.setMeteStyleID(rs.getInt(30));
			    am.setPriceID(rs.getInt(31));
			    am.setAmmeterStat(rs.getInt(32));
			    am.setIsOffAlarm(rs.getInt(33));
			    am.setCurPower(rs.getDouble(34));
			    am.setCostCheck(rs.getInt(35));
			    am.setStandByCheck(rs.getInt(36));
			    am.setXiuzheng(rs.getInt(37));
			    am.setLastTime(rs.getString(38));
			    am.setParentID(rs.getInt(39));

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    public ArrayList<AmmeterModel> queryPaginate(int areaID, int archID,
            int gatherID, String ammAddress, String ammeterName,
            String consumerNum, String sortLabel, boolean isAsc, int pageCount,
            int pageIndex) throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();

        AmmeterModel am = null;

        String sql = "select * from V_Ammeter ";

        if (areaID != 0 || archID != 0 || gatherID != 0
                || (ammAddress != null && !"".equals(ammAddress))
                || (ammeterName != null && !"".equals(ammeterName))
                || (consumerNum != null && !"".equals(consumerNum)))
        {
            sql += "where ";

            if (areaID != 0)
            {
                sql += "Area_ID = " + areaID;
            }

            if (archID != 0)
            {
                if (areaID != 0)
                {
                    sql += " and";
                }

                sql += " Architecture_ID = " + archID;
            }

            if (gatherID != 0)
            {
                if (areaID != 0 || archID != 0)
                {
                    sql += " and";
                }

                sql += " Gather_ID = " + gatherID;
            }

            if (ammAddress != null && !"".equals(ammAddress))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0)
                {
                    sql += " and";
                }

                sql += " AmMeter_485Address = '" + ammAddress + "' ";
            }

            if (ammeterName != null && !"".equals(ammeterName))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0
                        || (ammAddress != null && !"".equals(ammAddress)))
                {
                    sql += " and";
                }

                sql += " Ammeter_Name = '" + ammeterName + "' ";
            }

            if (consumerNum != null && !"".equals(consumerNum))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0
                        || (ammAddress != null && !"".equals(ammAddress))
                        || (ammeterName != null && !"".equals(ammeterName)))
                {
                    sql += " and";
                }

                sql += " ConsumerNum = '" + consumerNum + "' ";
            }

        }

        if (sortLabel != null && !"".equals(sortLabel))
        {
            if (isAsc)
            {
                sql += " order by " + sortLabel + " asc";
            }
            else
            {
                sql += " order by " + sortLabel + " desc";
            }
        }

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(sql,
			        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			if (count <= (pageCount * pageIndex))
			    return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
			    count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
			    rs.absolute(1);
			    rs.previous();
			}
			else
			    rs.absolute(pageCount * pageIndex);

			// rs.previous();
			while (rs.next() && (count > 0))
			{
			    am = buildInstance(rs);

			    // if (rs.getObject(1) != null)
			    // am.setAmmeterID(rs.getInt(1));
			    //
			    // if (rs.getObject(2) != null)
			    // am.setAmmeterPoint(rs.getInt(2));
			    //
			    // if (rs.getObject(3) != null)
			    // am.setAreaID(rs.getInt(3));
			    //
			    // if (rs.getObject(4) != null)
			    // am.setArchitectureID(rs.getInt(4));
			    //
			    // if (rs.getObject(5) != null)
			    // am.setGatherID(rs.getInt(5));
			    //
			    // if (rs.getObject(6) != null)
			    // am.setAmmeterNum(rs.getString(6));
			    //
			    // if (rs.getObject(7) != null)
			    // am.setAmmeterName(rs.getString(7));
			    //
			    // if (rs.getObject(8) != null)
			    // am.setAmmeterPassword(rs.getString(8));
			    //
			    // if (rs.getObject(9) != null)
			    // am.setAmmeterAddress485(rs.getString(9));
			    //
			    // if (rs.getObject(10) != null)
			    // am.setMaker(rs.getString(10));
			    //
			    // if (rs.getObject(11) != null)
			    // am.setMakerCode(rs.getString(11));
			    //
			    // if (rs.getObject(12) != null)
			    // am.setAssetNo(rs.getString(12));
			    //
			    // if (rs.getObject(13) != null)
			    // am.setIsSupply(rs.getInt(13));
			    //
			    // if (rs.getObject(14) != null)
			    // am.setzValue(rs.getDouble(14));
			    //
			    // if (rs.getObject(15) != null)
			    // am.setjValue(rs.getDouble(15));
			    //
			    // if (rs.getObject(16) != null)
			    // am.setfValue(rs.getDouble(16));
			    //
			    // if (rs.getObject(17) != null)
			    // am.setpValue(rs.getDouble(17));
			    //
			    // if (rs.getObject(18) != null)
			    // am.setgValue(rs.getDouble(18));
			    //
			    // if (rs.getObject(19) != null)
			    // am.setUseAmStyle(rs.getInt(19));
			    //
			    // if (rs.getObject(20) != null)
			    // am.setConsumerNum(rs.getString(20));
			    //
			    // if (rs.getObject(21) != null)
			    // am.setConsumerName(rs.getString(21));
			    //
			    // if (rs.getObject(22) != null)
			    // am.setConsumerPhone(rs.getString(22));
			    //
			    // if (rs.getObject(23) != null)
			    // am.setConsumerAddress(rs.getString(23));
			    //
			    // if (rs.getObject(24) != null)
			    // am.setIsImportantUser(rs.getInt(24));
			    //
			    // if (rs.getObject(25) != null)
			    // am.setIsCountMeter(rs.getInt(25));
			    //
			    // if (rs.getObject(26) != null)
			    // am.setIsComputation(rs.getInt(26));
			    //
			    // if (rs.getObject(27) != null)
			    // am.setAmmeterPlose(rs.getInt(27));
			    //
			    // if (rs.getObject(28) != null)
			    // am.setPartment(rs.getInt(28));
			    //
			    // if (rs.getObject(29) != null)
			    // am.setFloor(rs.getInt(29));
			    //
			    // if (rs.getObject(30) != null)
			    // am.setMeteStyleID(rs.getInt(30));
			    //
			    // if (rs.getObject(31) != null)
			    // am.setPriceID(rs.getInt(31));
			    //
			    // if (rs.getObject(32) != null)
			    // am.setAmmeterStat(rs.getInt(32));
			    //
			    // if (rs.getObject(33) != null)
			    // am.setIsOffAlarm(rs.getInt(33));
			    //
			    // if (rs.getObject(34) != null)
			    // am.setCurPower(rs.getDouble(34));
			    //
			    // if (rs.getObject(35) != null)
			    // am.setCostCheck(rs.getInt(35));
			    //
			    // if (rs.getObject(36) != null)
			    // am.setStandByCheck(rs.getInt(36));
			    //
			    // if (rs.getObject(37) != null)
			    // am.setXiuzheng(rs.getInt(37));
			    //
			    // if (rs.getObject(38) != null)
			    // am.setLastTime(rs.getString(38));
			    //
			    // if (rs.getObject(39) != null)
			    // am.setParentID(rs.getInt(39));
			    //
			    // if (rs.getObject("beilv") != null)
			    // am.setBeilv(rs.getInt("beilv"));
			    //
			    // if (rs.getObject("Architecture_Name") != null)
			    // am.setArchName(rs.getString("Architecture_Name"));
			    //
			    // if (rs.getObject("Gather_Name") != null)
			    // am.setGatherName(rs.getString("Gather_Name"));
			    //
			    // if (rs.getObject("Area_Name") != null)
			    // am.setAreaName(rs.getString("Area_Name"));
			    //
			    // if (rs.getObject("USEAMXZ") != null)
			    // am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质
			    //
			    // if (rs.getObject("USEAMFX") != null)
			    // am.setUseStyleName(rs.getString("USEAMFX"));// 分项
			    //
			    // if (rs.getObject("USEAMYJZX") != null)
			    // am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项
			    //
			    // if (rs.getObject("PartmentName") != null)
			    // am.setPartmentName(rs.getString("PartmentName"));
			    //
			    // if (rs.getObject("meteStyle_Name") != null)
			    // am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

			    lst.add(am);
			    count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return lst;
    }

    public ArrayList<AmmeterModel> queryAmmPaginate(int areaID, int archID,
            int gatherID, String ammAddress, String ammeterName,
            String consumerNum, int pageCount, int pageIndex)
            throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();

        AmmeterModel am = null;

        // String
        // complexItems="SELECT * FROM Ammeter WHERE Area_ID=? and Arch_ID=? and Gather_ID=? and AmMeter_485Address=? and Ammeter_Name=? and ConsumerNum=?";
        //
        // PreparedStatement psmt;
        //
        // if(areaID!=0)
        // {
        // psmt.setInt(1, areaID);
        // }else
        // {
        // psmt.setInt(1, 通配符);
        // }

        String complexItems = "SELECT * FROM V_Ammeter ";

        // 判断那个条件是第一个，若是第一个则带上 where 不带and
        // true为第一个,则带上where
        // 区域
        boolean firstItem = true;
        if (areaID != 0 && firstItem == true)
        {
            complexItems += " where Area_ID=" + areaID;
            firstItem = false;
        }

        // 建筑
        if (archID != 0)
        {
            if (firstItem == true)
            {
                complexItems += " where Architecture_ID=" + archID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Architecture_ID=" + archID;
            }
        }

        // 网关
        if (gatherID != 0)
        {
            if (firstItem == true)
            {
                complexItems += "where Gather_ID=" + gatherID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_ID=" + gatherID;
            }
        }

        // 485地址码
        if (ammAddress != null)
        {
            if (firstItem == true)
            {
                complexItems += " where AmMeter_485Address='" + ammAddress
                        + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and AmMeter_485Address='" + ammAddress + "'";
            }
        }

        // 电表名称
        if (ammeterName != null)
        {
            if (firstItem == true)
            {
                complexItems += " where Ammeter_Name='" + ammeterName + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and Ammeter_Name='" + ammeterName + "'";
            }
        }

        // 用户号
        if (consumerNum != null)
        {
            if (firstItem == true)
            {
                complexItems += " where ConsumerNum='" + consumerNum + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and ConsumerNum='" + consumerNum + "'";
            }
        }

        complexItems = complexItems + " ORDER BY Ammeter_ID desc";

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps =conn.prepareStatement(
			        complexItems, ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();

			rs.last();
			int count = rs.getRow();
			if (count <= (pageCount * pageIndex))
			    return null;

			count = count - pageCount * pageIndex;

			if (count >= pageCount)
			    count = pageCount;

			if ((pageCount * pageIndex) == 0)
			{
			    rs.absolute(1);
			    rs.previous();
			}
			else
			    rs.absolute(pageCount * pageIndex);

			// rs.previous();
			while (rs.next() && (count > 0))
			{
			    am = new AmmeterModel();

			    if (rs.getObject(1) != null)
			        am.setAmmeterID(rs.getInt(1));

			    if (rs.getObject(2) != null)
			        am.setAmmeterPoint(rs.getInt(2));

			    if (rs.getObject(3) != null)
			        am.setAreaID(rs.getInt(3));

			    if (rs.getObject(4) != null)
			        am.setArchitectureID(rs.getInt(4));

			    if (rs.getObject(5) != null)
			        am.setGatherID(rs.getInt(5));

			    if (rs.getObject(6) != null)
			        am.setAmmeterNum(rs.getString(6));

			    if (rs.getObject(7) != null)
			        am.setAmmeterName(rs.getString(7));

			    if (rs.getObject(8) != null)
			        am.setAmmeterPassword(rs.getString(8));

			    if (rs.getObject(9) != null)
			        am.setAmmeterAddress485(rs.getString(9));

			    if (rs.getObject(10) != null)
			        am.setMaker(rs.getString(10));

			    if (rs.getObject(11) != null)
			        am.setMakerCode(rs.getString(11));

			    if (rs.getObject(12) != null)
			        am.setAssetNo(rs.getString(12));

			    if (rs.getObject(13) != null)
			        am.setIsSupply(rs.getInt(13));

			    if (rs.getObject(14) != null)
			        am.setzValue(rs.getDouble(14));

			    if (rs.getObject(15) != null)
			        am.setjValue(rs.getDouble(15));

			    if (rs.getObject(16) != null)
			        am.setfValue(rs.getDouble(16));

			    if (rs.getObject(17) != null)
			        am.setpValue(rs.getDouble(17));

			    if (rs.getObject(18) != null)
			        am.setgValue(rs.getDouble(18));

			    if (rs.getObject(19) != null)
			        am.setUseAmStyle(rs.getInt(19));

			    if (rs.getObject(20) != null)
			        am.setConsumerNum(rs.getString(20));

			    if (rs.getObject(21) != null)
			        am.setConsumerName(rs.getString(21));

			    if (rs.getObject(22) != null)
			        am.setConsumerPhone(rs.getString(22));

			    if (rs.getObject(23) != null)
			        am.setConsumerAddress(rs.getString(23));

			    if (rs.getObject(24) != null)
			        am.setIsImportantUser(rs.getInt(24));

			    if (rs.getObject(25) != null)
			        am.setIsCountMeter(rs.getInt(25));

			    if (rs.getObject(26) != null)
			        am.setIsComputation(rs.getInt(26));

			    if (rs.getObject(27) != null)
			        am.setAmmeterPlose(rs.getInt(27));

			    if (rs.getObject(28) != null)
			        am.setPartment(rs.getInt(28));

			    if (rs.getObject(29) != null)
			        am.setFloor(rs.getInt(29));

			    if (rs.getObject(30) != null)
			        am.setMeteStyleID(rs.getInt(30));

			    if (rs.getObject(31) != null)
			        am.setPriceID(rs.getInt(31));

			    if (rs.getObject(32) != null)
			        am.setAmmeterStat(rs.getInt(32));

			    if (rs.getObject(33) != null)
			        am.setIsOffAlarm(rs.getInt(33));

			    if (rs.getObject(34) != null)
			        am.setCurPower(rs.getDouble(34));

			    if (rs.getObject(35) != null)
			        am.setCostCheck(rs.getInt(35));

			    if (rs.getObject(36) != null)
			        am.setStandByCheck(rs.getInt(36));

			    if (rs.getObject(37) != null)
			        am.setXiuzheng(rs.getInt(37));

			    if (rs.getObject(38) != null)
			        am.setLastTime(rs.getString(38));

			    if (rs.getObject(39) != null)
			        am.setParentID(rs.getInt(39));

			    if (rs.getObject("beilv") != null)
			        am.setBeilv(rs.getInt("beilv"));

			    if (rs.getObject("Architecture_Name") != null)
			        am.setArchName(rs.getString("Architecture_Name"));

			    if (rs.getObject("Gather_Name") != null)
			        am.setGatherName(rs.getString("Gather_Name"));

			    if (rs.getObject("Area_Name") != null)
			        am.setAreaName(rs.getString("Area_Name"));

			    if (rs.getObject("USEAMXZ") != null)
			        am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质

			    if (rs.getObject("USEAMFX") != null)
			        am.setUseStyleName(rs.getString("USEAMFX"));// 分项

			    if (rs.getObject("USEAMYJZX") != null)
			        am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项

			    if (rs.getObject("PartmentName") != null)
			        am.setPartmentName(rs.getString("PartmentName"));

			    if (rs.getObject("meteStyle_Name") != null)
			        am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

			    lst.add(am);
			    count--;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return lst;
    }

    public int getSearchCount(int areaID, int archID, int gatherID,
            String ammAddress, String ammeterName, String consumerNum)
            throws SQLException
    {
        int count = 0;
        String complexItems = "SELECT count(*) as recordCount FROM V_Ammeter ";

        // 判断那个条件是第一个，若是第一个则带上 where 不带and
        // true为第一个,则带上where
        // 区域
        boolean firstItem = true;
        if (areaID != 0 && firstItem == true)
        {
            complexItems += " where Area_ID=" + areaID;
            firstItem = false;
        }

        // 建筑
        if (archID != 0)
        {
            if (firstItem == true)
            {
                complexItems += " where Architecture_ID=" + archID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Architecture_ID=" + archID;
            }
        }

        // 网关
        if (gatherID != 0)
        {
            if (firstItem == true)
            {
                complexItems += "where Gather_ID=" + gatherID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_ID=" + gatherID;
            }
        }

        // 485地址码
        if (ammAddress != null)
        {
            if (firstItem == true)
            {
                complexItems += " where AmMeter_485Address='" + ammAddress
                        + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and AmMeter_485Address='" + ammAddress + "'";
            }
        }

        // 电表名称
        if (ammeterName != null)
        {
            if (firstItem == true)
            {
                complexItems += " where Ammeter_Name='" + ammeterName + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and Ammeter_Name='" + ammeterName + "'";
            }
        }

        // 用户号
        if (consumerNum != null)
        {
            if (firstItem == true)
            {
                complexItems += " where ConsumerNum='" + consumerNum + "'";
                firstItem = false;
            }
            else
            {
                complexItems += " and ConsumerNum='" + consumerNum + "'";
            }
        }

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(
			        complexItems);
			rs = ps.executeQuery();

			if (rs.next())
			    count = rs.getInt("recordCount");
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}

        return count;
    }

    /**
     * 根据区域、建筑、网关、地址码、名称和用户号联合查询电表
     * 
     * @param areaID
     *            区域
     * @param archID
     *            建筑
     * @param gatherID
     *            网关
     * @param ammAddress
     *            地址码
     * @param ammeterName
     *            名称
     * @param consumerNum
     *            用户号
     * @return 电表实体类的实例的集合
     * @throws SQLException
     */
    public ArrayList<AmmeterModel> complexQueryAmm(int areaID, int archID,
            int gatherID, String ammAddress, String ammeterName,
            String consumerNum) throws SQLException
    {
        ArrayList<AmmeterModel> lst = new ArrayList<AmmeterModel>();
        AmmeterModel am = null;

        // String
        // complexItems="SELECT * FROM Ammeter WHERE Area_ID=? and Arch_ID=? and Gather_ID=? and AmMeter_485Address=? and Ammeter_Name=? and ConsumerNum=?";
        //
        // PreparedStatement psmt;
        //
        // if(areaID!=0)
        // {
        // psmt.setInt(1, areaID);
        // }else
        // {
        // psmt.setInt(1, 通配符);
        // }

        String complexItems = "SELECT * FROM V_Ammeter ";

        // 判断那个条件是第一个，若是第一个则带上 where 不带and
        // true为第一个,则带上where
        // 区域
        boolean firstItem = true;
        if (areaID != 0 && firstItem == true)
        {
            complexItems += " where Area_ID=" + areaID;
            firstItem = false;
        }

        // 建筑
        if (archID != 0)
        {
            if (firstItem == true)
            {
                complexItems += " where Architecture_ID=" + archID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Architecture_ID=" + archID;
            }
        }

        // 网关
        if (gatherID != 0)
        {
            if (firstItem == true)
            {
                complexItems += "where Gather_ID=" + gatherID;
                firstItem = false;
            }
            else
            {
                complexItems += " and Gather_ID=" + gatherID;
            }
        }

        // 485地址码
        if (ammAddress != null)
        {
            if (firstItem == true)
            {
                complexItems += " where AmMeter_485Address like('%" + ammAddress
                        + "%')";
                firstItem = false;
            }
            else
            {
                complexItems += " and AmMeter_485Address like'%" + ammAddress + "%')";
            }
        }

        // 电表名称
        if (ammeterName != null)
        {
            if (firstItem == true)
            {
                complexItems += " where Ammeter_Name like('%" + ammeterName +  "%')";
                firstItem = false;
            }
            else
            {
                complexItems += " and Ammeter_Name like('%"  + ammeterName + "%')";
            }
        }

        // 用户号
        if (consumerNum != null)
        {
            if (firstItem == true)
            {
                complexItems += " where ConsumerNum like('%" + consumerNum + "%')";
                firstItem = false;
            }
            else
            {
                complexItems += " and ConsumerNum like('%" + consumerNum + "%')";
            }
        }

        Connection conn=null;
		PreparedStatement ps =null;
		ResultSet rs = null;

		try
		{
			conn = ConnDB.getConnection();
			ps = conn.prepareStatement(
			        complexItems);
			rs = ps.executeQuery();

			while (rs.next())
			{
			    am = new AmmeterModel();

			    if (rs.getObject(1) != null)
			        am.setAmmeterID(rs.getInt(1));

			    if (rs.getObject(2) != null)
			        am.setAmmeterPoint(rs.getInt(2));

			    if (rs.getObject(3) != null)
			        am.setAreaID(rs.getInt(3));

			    if (rs.getObject(4) != null)
			        am.setArchitectureID(rs.getInt(4));

			    if (rs.getObject(5) != null)
			        am.setGatherID(rs.getInt(5));

			    if (rs.getObject(6) != null)
			        am.setAmmeterNum(rs.getString(6));

			    if (rs.getObject(7) != null)
			        am.setAmmeterName(rs.getString(7));

			    if (rs.getObject(8) != null)
			        am.setAmmeterPassword(rs.getString(8));

			    if (rs.getObject(9) != null)
			        am.setAmmeterAddress485(rs.getString(9));

			    if (rs.getObject(10) != null)
			        am.setMaker(rs.getString(10));

			    if (rs.getObject(11) != null)
			        am.setMakerCode(rs.getString(11));

			    if (rs.getObject(12) != null)
			        am.setAssetNo(rs.getString(12));

			    if (rs.getObject(13) != null)
			        am.setIsSupply(rs.getInt(13));

			    if (rs.getObject(14) != null)
			        am.setzValue(rs.getDouble(14));

			    if (rs.getObject(15) != null)
			        am.setjValue(rs.getDouble(15));

			    if (rs.getObject(16) != null)
			        am.setfValue(rs.getDouble(16));

			    if (rs.getObject(17) != null)
			        am.setpValue(rs.getDouble(17));

			    if (rs.getObject(18) != null)
			        am.setgValue(rs.getDouble(18));

			    if (rs.getObject(19) != null)
			        am.setUseAmStyle(rs.getInt(19));

			    if (rs.getObject(20) != null)
			        am.setConsumerNum(rs.getString(20));

			    if (rs.getObject(21) != null)
			        am.setConsumerName(rs.getString(21));

			    if (rs.getObject(22) != null)
			        am.setConsumerPhone(rs.getString(22));

			    if (rs.getObject(23) != null)
			        am.setConsumerAddress(rs.getString(23));

			    if (rs.getObject(24) != null)
			        am.setIsImportantUser(rs.getInt(24));

			    if (rs.getObject(25) != null)
			        am.setIsCountMeter(rs.getInt(25));

			    if (rs.getObject(26) != null)
			        am.setIsComputation(rs.getInt(26));

			    if (rs.getObject(27) != null)
			        am.setAmmeterPlose(rs.getInt(27));

			    if (rs.getObject(28) != null)
			        am.setPartment(rs.getInt(28));

			    if (rs.getObject(29) != null)
			        am.setFloor(rs.getInt(29));

			    if (rs.getObject(30) != null)
			        am.setMeteStyleID(rs.getInt(30));

			    if (rs.getObject(31) != null)
			        am.setPriceID(rs.getInt(31));

			    if (rs.getObject(32) != null)
			        am.setAmmeterStat(rs.getInt(32));

			    if (rs.getObject(33) != null)
			        am.setIsOffAlarm(rs.getInt(33));

			    if (rs.getObject(34) != null)
			        am.setCurPower(rs.getDouble(34));

			    if (rs.getObject(35) != null)
			        am.setCostCheck(rs.getInt(35));

			    if (rs.getObject(36) != null)
			        am.setStandByCheck(rs.getInt(36));

			    if (rs.getObject(37) != null)
			        am.setXiuzheng(rs.getInt(37));

			    if (rs.getObject(38) != null)
			        am.setLastTime(rs.getString(38));

			    if (rs.getObject(39) != null)
			        am.setParentID(rs.getInt(39));

			    if (rs.getObject("beilv") != null)
			        am.setBeilv(rs.getInt("beilv"));

			    if (rs.getObject("Architecture_Name") != null)
			        am.setArchName(rs.getString("Architecture_Name"));

			    if (rs.getObject("Gather_Name") != null)
			        am.setGatherName(rs.getString("Gather_Name"));

			    if (rs.getObject("Area_Name") != null)
			        am.setAreaName(rs.getString("Area_Name"));

			    if (rs.getObject("USEAMXZ") != null)
			        am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质

			    if (rs.getObject("USEAMFX") != null)
			        am.setUseStyleName(rs.getString("USEAMFX"));// 分项

			    if (rs.getObject("USEAMYJZX") != null)
			        am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项

			    if (rs.getObject("PartmentName") != null)
			        am.setPartmentName(rs.getString("PartmentName"));

			    if (rs.getObject("meteStyle_Name") != null)
			        am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

			    lst.add(am);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			ConnDB.release(conn, ps, rs);
		}
        return lst;
    }

    public String buildSql(int areaID, int archID, int gatherID,
            String ammAddress, String ammeterName, String consumerNum,
            String sortLabel, boolean isAsc)
    {
        String sql = "select * from V_Ammeter ";

        if (areaID != 0 || archID != 0 || gatherID != 0
                || (ammAddress != null && !"".equals(ammAddress))
                || (ammeterName != null && !"".equals(ammeterName))
                || (consumerNum != null && !"".equals(consumerNum)))
        {
            sql += "where ";

            if (areaID != 0)
            {
                sql += "Area_ID = " + areaID;
            }

            if (archID != 0)
            {
                if (areaID != 0)
                {
                    sql += " and";
                }

                sql += " Architecture_ID = " + archID;
            }

            if (gatherID != 0)
            {
                if (areaID != 0 || archID != 0)
                {
                    sql += " and";
                }

                sql += " Gather_ID = " + gatherID;
            }

            if (ammAddress != null && !"".equals(ammAddress))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0)
                {
                    sql += " and";
                }

                sql += " AmMeter_485Address = '" + ammAddress + "' ";
            }

            if (ammeterName != null && !"".equals(ammeterName))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0
                        || (ammAddress != null && !"".equals(ammAddress)))
                {
                    sql += " and";
                }

                sql += " Ammeter_Name = '" + ammeterName + "' ";
            }

            if (consumerNum != null && !"".equals(consumerNum))
            {
                if (areaID != 0 || archID != 0 || gatherID != 0
                        || (ammAddress != null && !"".equals(ammAddress))
                        || (ammeterName != null && !"".equals(ammeterName)))
                {
                    sql += " and";
                }

                sql += " ConsumerNum = '" + consumerNum + "' ";
            }

        }

        if (sortLabel != null && !"".equals(sortLabel))
        {
            if (isAsc)
            {
                sql += " order by " + sortLabel + " asc";
            }
            else
            {
                sql += " order by " + sortLabel + " desc";
            }
        }

        return sql;
    }

    private AmmeterModel buildInstance(ResultSet rs) throws SQLException
    {
        AmmeterModel am = new AmmeterModel();

        am.setAmmeterID(rs.getInt("AmMeter_ID"));
        am.setAmmeterPoint(rs.getInt("AmMeter_Point"));
        am.setAreaID(rs.getInt("Area_ID"));
        am.setArchitectureID(rs.getInt("Architecture_ID"));
        am.setGatherID(rs.getInt("Gather_ID"));
        am.setAmmeterNum(rs.getString("AmMeter_Num"));
        am.setAmmeterName(rs.getString("AmMeter_Name"));
        am.setAmmeterPassword(rs.getString("AmMeter_Password"));
        am.setAmmeterAddress485(rs.getString("AmMeter_485Address"));
        am.setMaker(rs.getString("Maker"));
        am.setMakerCode(rs.getString("MakerCode"));
        am.setAssetNo(rs.getString("AssetNo"));
        am.setIsSupply(rs.getInt("IsSupply"));
        am.setzValue(rs.getDouble("ZValue"));
        am.setjValue(rs.getDouble("JValue"));
        am.setfValue(rs.getDouble("FValue"));
        am.setpValue(rs.getDouble("PValue"));
        am.setgValue(rs.getDouble("GValue"));
        am.setUseAmStyle(rs.getInt("UseAmStyle"));
        am.setConsumerNum(rs.getString("ConsumerNum"));
        am.setConsumerName(rs.getString("ConsumerName"));
        am.setConsumerPhone(rs.getString("ConsumerPhone"));
        am.setConsumerAddress(rs.getString("ConsumerAddress"));
        am.setIsImportantUser(rs.getInt("IsImportantUser"));
        am.setIsCountMeter(rs.getInt("IsCountMeter"));
        am.setIsComputation(rs.getInt("isComputation"));
        am.setAmmeterPlose(rs.getInt("AmMeter_Plose"));
        am.setPartment(rs.getInt("Partment_ID"));
        am.setFloor(rs.getInt("Floor"));
        am.setMeteStyleID(rs.getInt("MeteStyle_ID"));
        am.setPriceID(rs.getInt("Price_ID"));
        am.setAmmeterStat(rs.getInt("AmMeter_Stat"));
        am.setIsOffAlarm(rs.getInt("IsOffAlarm"));
        am.setCurPower(rs.getDouble("CurPower"));
        am.setCostCheck(rs.getInt("CostCheck"));
        am.setStandByCheck(rs.getInt("StandByCheck"));
        am.setXiuzheng(rs.getInt("Xiuzheng"));
        am.setLastTime(rs.getString("LastTime"));
        am.setBeilv(rs.getInt("beilv"));
        am.setParentID(rs.getInt("ParentID"));
        am.setDataFrom(rs.getInt("DataFrom"));

        // Ammeter新加的字段 但是没有加到V_Ammeter中
        // am.setApstate(rs.getString("APSTATE"));
        // am.setLimitPart(rs.getFloat("LIMITPART"));
        // am.setApybValue(rs.getFloat("APYBVALUE"));
        // am.setISzb(rs.getInt("ISZB"));
        // am.setApManCount(rs.getFloat("APMANCOUNT"));

        am.setArchName(rs.getString("Architecture_Name"));
        am.setGatherName(rs.getString("Gather_Name"));
        am.setAreaName(rs.getString("Area_Name"));
        am.setUseAmStyleName(rs.getString("USEAMXZ"));// 性质
        am.setUseStyleName(rs.getString("USEAMFX"));// 分项
        am.setUseStyleNameYJZX(rs.getString("USEAMYJZX"));// 一级子项
        am.setPartmentName(rs.getString("PartmentName"));
        am.setMeterStyleName(rs.getString("meteStyle_Name"));// 表类型

        return am;
    }
}