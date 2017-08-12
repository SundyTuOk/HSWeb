package com.sf.energy.transfer.encode;

import java.util.*;

/**
 * <发送组包接口> 
 * <包括普通命令组包、轮抄功能组包、补抄功能组包>
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.transfer.encode.SendGW
 * @since 中转站
 */
public interface Sender
{
	/**
	 * 普通命令组包
	 * @param termminalAddress 数据网关地址
	 * @param commandCode 命令代码
	 * @param para 命令参数
	 * @param pseq 启动帧序号pseq
	 * @return byte[] 发送报文字节数组
	 */
	public byte[] sendCommand(String termminalAddress, String commandCode, String  para, int pseq );
	
	/**
	 * 轮抄功能组命令码
	 * @param meterID 表计测量点号
	 * @return String 命令码
	 */
	public String makeCommandCode(int meterID);
	
	/**
	 * 补抄功能组包
	 * @param terminalAddress 数据网关地址
	 * @param readDate 抄读日期
	 * @param pseq 启动帧序号pseq
	 * @return byte[] 发送报文字节数组
	 */
	public byte[] readLost(String terminalAddress, String readDate, int pseq);
	/**
	 *  发送前特殊命令参数保存
	 *  @param metertype表计类型
	 *  @param autoread 本网关是否自动轮抄
	 *  @param para 命令参数
	 */
    public  String specialCommand(String dataId,String metertype,String autoread,String para);
}