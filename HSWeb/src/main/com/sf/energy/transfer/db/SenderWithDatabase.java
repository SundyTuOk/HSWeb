package com.sf.energy.transfer.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JTable;

import com.sf.energy.transfer.form.DataQueryTableModel;

/**
 * <与发送相关的数据库操作> 
 * <包括主动向服务器上传小时数据、取出数据网关下表计、查电表、网关、数据、命令等信息、查缺失数据、更新抄读时间、插限值信息。>
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.transfer.db.SenderWithOracle
 * @since 中转站
 */
interface SenderWithDatabase
{
	/**
	 * 主动向服务器上传小时数据。
	 * 
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @return 某表类型数据
	 * @throws SQLException 
	 */
	public String autoUpToServer(String meterType) throws SQLException;

	/**
	 * 主动向服务器上传三相数据。
	 * 
	 * @return 三相数据
	 * @throws SQLException 
	 */
	public String autoUpThreePhase() throws SQLException;

	/**
	 * 取出某数据网关下所有表计。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @return 数据网关通信地址+其测量点号
	 * @throws SQLException 
	 */
	public String allMeter(String terminalAddress, String meterType) throws SQLException;

	/**
	 * 取出某数据网关下所有没有本次用量的表计。
	 * 轮抄时需判断该数据网关下所有表计是否都已抄读用量。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @return 没有用量的表计所在数据网关通信地址+其测量点号
	 * @throws SQLException 
	 */
	public String metersToRead(String terminalAddress, String meterType) throws SQLException;

	/**
	 * 取出某数据网关下所有在线表计。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @return 数据网关通信地址+其测量点号
	 * @throws SQLException 
	 */
	public ArrayList<String> metersOnline(String terminalAddress,
			String meterType) throws SQLException;

	/**
	 * 把每块表计的的抄读标志置为0。
	 * 通讯初始化以及开启新一轮轮抄时需清空已读标志。
	 * 
	 * @param meterType
	 *            表类型
	 */
	public void readFlgClear(String meterType) throws SQLException;

	/**
	 * 查电表信息
	 * 
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @param meterStyle
	 *            表计类型:单相、三相、预付费
	 * @param terminalAddress
	 *            数据网关地址
	 * @return ResultSet
	 */
	public JTable queryMeter(String meterType, String meterStyle,
			String terminalAddress,JLabel jLabel3) throws SQLException;

	/**
	 * 查网关信息
	 * 
	 * @return ResultSet
	 */
	public String[] queryDG() throws SQLException;

	/**
	 * 查数据
	 * 
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @param thisCommand
	 *            命令
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterAddress
	 *            表计通讯地址
	 * @param readDate
	 *            抄读日期
	 * @return ResultSet
	 */
	public DataQueryTableModel queryData(String meterType, String thisCommand,
			String terminalAddress, String meterAddress, String readDate,JLabel jLabel5) throws SQLException;

	/**
	 * 查数据项
	 * 
	 * @param tableName
	 *            表名
	 * @param thisCommand
	 *            命令
	 * @return ResultSet
	 */
	public ResultSet queryDataItem(String tableName, String thisCommand) throws SQLException;

	/**
	 * 查命令信息
	 * 
	 * @param tableName
	 *            表名
	 * @return ResultSet
	 */
	public ResultSet queryCommand(String tableName) throws SQLException;

	/**
	 * 查缺失数据
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @return ResultSet
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public String queryMissedData(String terminalAddress) throws SQLException, ParseException;

	/**
	 * 更新抄读时间
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param readDate
	 *            抄读日期
	 */
	public void updateLastDate(String terminalAddress, String readDate) throws SQLException;

	public boolean insertLamp(String terminaladdress, String lampaddress,String lampport) throws SQLException;
	/**
	 * 插限值信息
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param alarmValue
	 *            报警限值参数
	 * @return 是否插值成功标志
	 */
	public boolean insertAlarm(String terminalAddress, String alarmValue) throws SQLException;



	
}
