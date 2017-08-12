
package com.sf.energy.transfer.db;
/**
 * 接收到报文后和数据库操作相关的接口
 * @author czy
 * @version 1.0
 * @see [ReceiveGW、Receiver]
 * @since [盛帆电子/数据中转站]
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

interface ReceiverWithDatabase
{
	/**
	 * 保存单相或水表用量数据。 数据中转站定时抄读的用量数据（单相数据及水表数据）写入数据库并修改此表计的用量抄收标志为1
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterAddress
	 *            表地址
	 * @param commandCode
	 *            命令码：afn+dt
	 * @param readTime
	 *            抄读时间
	 * @param dataValue
	 *            抄读结果
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @throws SQLException 
	 */
	
	public void dataSave(String terminalAddress, String meterAddress,
			String commandCode, String readTime, String dataValue,
			String meterType) throws SQLException;

	/**
	 * 保存三相数据。数据中转站定时抄读的用量数据（三相数据）写入数据库并修改此表计的用量抄收标志为1
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterAddress
	 *            表地址
	 * @param readTime
	 *            抄读时间
	 * @param dataArray
	 *            三相数据集合
	 * @return 报警信息
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public String dataSaveThreePhase(String terminalAddress, String meterAddress,
			String readTime, ArrayList<String> dataArray) throws NumberFormatException, SQLException;

	/**
	 * 添加修改某数据网关。主站向数据网关下发表号时,如果数据网关回复执行成功,则写入数据库
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param protocol
	 *            规约
	 * @param ifAutoRead
	 *            是否主站轮抄,1轮抄,0网关上传
	 * @throws SQLException 
	 */
	public void insertTerminal(String terminalAddress, String protocol,
			String ifAutoRead) throws SQLException;

	/**
	 * 添加修改某数据网关下表计。主站向数据网关下发表号时,如果数据网关回复执行成功,则写入数据库
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param meterId
	 *            表ID,国网为测量点号
	 * @param meterAddress
	 *            表地址
	 * @param meterType
	 *            表类型:1为电表,2为水表
	 * @param meterStyle
	 *            表计类型
	 * @throws SQLException 
	 */
	public void insertTerminalMeters(String terminalAddress, int meterId,
			String meterAddress, String meterType, String meterStyle) throws SQLException;

	/**
	 * 清空某数据网关下所有表计。主站清空数据网关时,如果数据网关回复执行成功,则删除数据库中该数据网关及其表计。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @throws SQLException 
	 */
	public void clearOneTerminal(String terminalAddress) throws SQLException;

	/**
	 * 根据测量点号取表地址。由于国网集中器返回报文中不带表地址，所以需要根据测量点号取表地址。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @param point
	 *            测量点号
	 * @return 表地址
	 * @throws SQLException 
	 */
	public ArrayList<String> pointToAddress(String terminalAddress, String point) throws SQLException;

	/**
	 * 备份数据库。
	 * 
	 * @return 是否成功标志
	 * @throws IOException 
	 */
	public boolean backUp() throws IOException;

	/**
	 * 检查网关是否已登记。
	 * 
	 * @param terminalAddress
	 *            数据网关地址
	 * @return 是否登记字符串
	 * @throws SQLException 
	 */
	public String checkTerminalInfo(String terminalAddress) throws SQLException;

}
