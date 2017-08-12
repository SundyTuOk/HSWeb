package com.sf.energy.transfer.encode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sf.energy.util.CreateLog;

/**
 * <发送组包> 
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.transfer.encode.SendGW
 * @since 中转站
 */
public class EncoderOfGW
{
	// /发送报文字节数组缓冲区大小
	private final static int sendBufferSize = 500;

	/**
	 * 字符串补齐，不足位用"0"补齐
	 * 
	 * @param s
	 *            需要补齐的字符串
	 * @param length
	 *            需要补齐成的长度
	 * @return 补齐后的字符串
	 */
	private static String padLeft(String s, int length)
	{
		if(s.length() >= length)
		{
			return s;
		}
		byte[] bs = new byte[length];
		byte[] ss = s.getBytes();
		Arrays.fill(bs, (byte) (48 & 0xff));
		System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
		return new String(bs);
	}

	/**
	 * 将字符串原样转化为字节,如"11"转化为0x11
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return 转换后的byte值
	 */
	private static byte strToByte(String str)
	{
		byte rt = 0x00;

		str = padLeft(str, 2);
		int h = letterToInt(str.substring(0, 1));
		int l = letterToInt(str.substring(1, 2));
		rt = (byte) (h * 16 + l);
		return rt;
	}

	/**
	 * 主要功能是将字母转化为数字,如E转为14
	 * 
	 * @param a
	 *            需要转换的值
	 * @return 转换后的值
	 */
	private static int letterToInt(String a)
	{
		int i = 0;
		switch (a)
		{
		case "0":
			i = 0;
			break;
		case "1":
			i = 1;
			break;
		case "2":
			i = 2;
			break;
		case "3":
			i = 3;
			break;
		case "4":
			i = 4;
			break;
		case "5":
			i = 5;
			break;
		case "6":
			i = 6;
			break;
		case "7":
			i = 7;
			break;
		case "8":
			i = 8;
			break;
		case "9":
			i = 9;
			break;
		case "A":
		case "a":
			i = 10;
			break;
		case "B":
		case "b":
			i = 11;
			break;
		case "C":
		case "c":
			i = 12;
			break;
		case "D":
		case "d":
			i = 13;
			break;
		case "E":
		case "e":
			i = 14;
			break;
		case "F":
		case "f":
			i = 15;
			break;
		default:
			break;
		}
		return i;
	}

	/**
	 * 将10进制数原样转化为16进制,如15转化为0x15
	 * 
	 * @param i10
	 *            需要转换的10进制值
	 * @return 转换后的16进制值
	 */
	private static byte I10ToI16(int i10)
	{
		int i16h = i10 / 10 * 16;
		int i16l = i10 % 10;
		byte rt = (byte) (i16h + i16l);
		return rt;
	}

	/**
	 * 全部确认 00H F1p0 0000000100 无数据体 全部否认 00H F2p0 0000000200 无数据体 按数据单元标识确认和否认
	 * 00H F3p0 0000000400 有数据体
	 * 
	 * @param conAddress
	 *            集中器地址
	 * @param pseq
	 *            启动帧序号pseq
	 * @param sendDt
	 *            发送信息类da
	 * @param receiveAfn
	 *            接收功能码afn
	 * @param receiveDataID
	 *            接收信息点da信息类dt
	 * @return 确认报文字节数组
	 */
	public static byte[] confirm(String conAddress, int pseq, String sendDt,
			byte receiveAfn, String receiveDataID)
	{
		int dataLen = 0;
		byte[] sendBuffer = new byte[sendBufferSize];

		sendBuffer[0] = 0x68; // 起始字符
		sendBuffer[5] = 0x68; // 起始字符
		sendBuffer[6] = 0x00; // 控制域
								// 00000000:下行;报文来自从动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为0,表示确认
		sendBuffer[7] = strToByte(conAddress.substring(2, 4));// 地址域 集中器地址 5字节
		sendBuffer[8] = strToByte(conAddress.substring(0, 2));
		sendBuffer[9] = strToByte(conAddress.substring(6, 8));
		sendBuffer[10] = strToByte(conAddress.substring(4, 6));
		sendBuffer[11] = strToByte(conAddress.substring(8, 10));

		sendBuffer[12] = 0x00; // 应用层功能码AFN
		sendBuffer[13] = (byte) ((96 + pseq) % 256); // 帧序列域SEQ,暂不考虑重发时RSEQ的变化
		sendBuffer[14] = 0x00; // 数据单元标识
		sendBuffer[15] = 0x00;
		sendBuffer[16] = strToByte(sendDt.substring(0, 2));
		sendBuffer[17] = strToByte(sendDt.substring(2, 4));

		if ("0400".equals(sendDt)) // F3有数据体
		{
			dataLen = 6;
			sendBuffer[18] = receiveAfn;
			if (receiveAfn == 0x02)
			{
				sendBuffer[6] = 0x0B; // 控制域
										// 00001011:下行;报文来自从动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为11,表示链路状态响应帧
			}
			sendBuffer[19] = strToByte(receiveDataID.substring(0, 2));
			sendBuffer[20] = strToByte(receiveDataID.substring(2, 4));
			sendBuffer[21] = strToByte(receiveDataID.substring(4, 6));
			sendBuffer[22] = strToByte(receiveDataID.substring(6, 8));
			sendBuffer[23] = 0x00; // 无错误
		}

		sendBuffer[1] = (byte) (((12 + dataLen) * 4 + 2) % 256); // 长度L为控制域,地址域,链路用户数据的和,后缀协议标识10
		sendBuffer[2] = (byte) (((12 + dataLen) * 4 + 2) / 256);
		sendBuffer[3] = sendBuffer[1];
		sendBuffer[4] = sendBuffer[2];

		checkByte(sendBuffer, 18 + dataLen); // 校验和CS
		sendBuffer[19 + dataLen] = 0x16; // 结束字符

		byte[] sendCode = Arrays.copyOf(sendBuffer, 20 + dataLen);
		return sendCode;
	}

	/**
	 * 组包入口函数
	 * 
	 * @param conAddress
	 *            集中器地址
	 * @param afn
	 *            应用功能码AFN
	 * @param command
	 *            命令（命令代码+命令参数）
	 * @param fir
	 *            首帧标识
	 * @param fin
	 *            末帧标识
	 * @param pseq
	 *            启动帧序号pseq
	 * @return 发送报文字节数组
	 */
	public static byte[] generalFrame(String conAddress, String afn,
			String command, int fir, int fin, int pseq, StringBuffer transresult)
	{
		int len = 0;
		byte[] sendBuffer = new byte[sendBufferSize];
		int tpv = 0; // 假定没有时间标签
		int delayTime = 5; // 假定有时间标签的情况下，允许的时间延迟是5秒
		String password = ""; // 假定password全为0
		for (int i = 0; i < 16; i++)
		{
			password += "00";
		}
		sendBuffer[0] = 0x68; // 起始字符
		sendBuffer[5] = 0x68; // 起始字符
		sendBuffer[7] = strToByte(conAddress.substring(2, 4)); // 地址域
		sendBuffer[8] = strToByte(conAddress.substring(0, 2));
		sendBuffer[9] = strToByte(conAddress.substring(6, 8));
		sendBuffer[10] = strToByte(conAddress.substring(4, 6));
		sendBuffer[11] = strToByte(conAddress.substring(8, 10));

		sendBuffer[12] = strToByte(afn); // 应用层功能码AFN
		try
		{
			switch (afn)
			{
			case "01":
				len = reset(sendBuffer, command, tpv, delayTime, pseq, password);
				break;
			case "04":
			case "05":
				len = write0405(sendBuffer, command, tpv, delayTime, fir, fin,
						pseq, password);
				break;
			case "09":
			case "0C":
				len = read090C(sendBuffer, command, tpv, delayTime, fir, fin,
						pseq);
				break;
			case "0A":
			case "0D":
				len = read0A0D(sendBuffer, afn, command, tpv, delayTime, fir,
						fin, pseq);
				break;
			case "0E":
				len = readImportantEvent(sendBuffer, command, tpv, delayTime,
						fir, fin, pseq);
				break;
			case "10":
				len = transmit(sendBuffer, command, tpv, delayTime, fir, fin,
						pseq, password, transresult);
				break;
			default:
				break;
			}
		} catch (Exception ex)
		{
			//System.out.println("cb");
			CreateLog.writeLog("MakeFrameGW.GeneralFrame:" + command + " "
					+ ex.getMessage());
		}
		byte[] sendCode = Arrays.copyOf(sendBuffer, len);
		return sendCode;
	}

	/**
	 * 复位命令 硬件初始化 01H F1p0 数据区初始化 01H F20p0 参数及全体数据区初始化（即恢复至出厂配置） 01H F3p0
	 * 参数（除与系统主站通信有关的）及全体数据区初始化 01H F4p0
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param command
	 *            命令码，数据标识+数据单元 0100000100 一次只能执行一个命令且无数据体
	 * @param tpv
	 *            启动站是否有时间标签tpv
	 * @param delayTime
	 *            有时间标签的情况下，允许发送传输延时时间
	 * @param pseq
	 *            启动帧序号PSEQ
	 * @param password
	 *            主站生成的密码
	 * @return 字节数组sendBuffer长度
	 */
	private static int reset(byte[] sendBuffer, String command, int tpv,
			int delayTime, int pseq, String password)
	{

		sendBuffer[1] = (byte) (((28 + tpv * 6) * 4 + 2) % 256); // 填写长度域，控制域、地址域、链路用户数据的和，1+5+1+1+4+16+tpv*6,后缀协议标识10
		sendBuffer[2] = (byte) (((28 + tpv * 6) * 4 + 2) / 256);
		sendBuffer[3] = sendBuffer[1];
		sendBuffer[4] = sendBuffer[2];

		sendBuffer[6] = 0x41; // 控制域
								// 01000001，下行;报文来自启动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为01,表示复位命令

		sendBuffer[13] = (byte) ((tpv * 8 + 7) * 16 + pseq); // 帧序列域SEQ,,暂不考虑重发时RSEQ的变化
		sendBuffer[14] = 0x00; // 数据单元标识 4字节 DA=0
		sendBuffer[15] = 0x00;
		sendBuffer[16] = strToByte(command.substring(6, 8)); // DT
		sendBuffer[17] = 0x00;

		for (int j = 0; j < 16; j++)
		{
			sendBuffer[18 + j] = strToByte(password.substring(j * 2, j * 2 + 2));
		}
		if (tpv == 1)
		{
			// 时间标签Tp
			sendBuffer[34] = 0x00; // 启动帧序号计数器PFC 暂时不计数
			Calendar c = Calendar.getInstance();
			sendBuffer[35] = I10ToI16(c.get(Calendar.SECOND)); // 秒
			sendBuffer[36] = I10ToI16(c.get(Calendar.MINUTE)); // 分
			sendBuffer[37] = I10ToI16(c.get(Calendar.HOUR_OF_DAY)); // 时
			sendBuffer[38] = I10ToI16(c.get(Calendar.DATE)); // 日
			sendBuffer[39] = (byte) delayTime; // 允许发送传输延时时间
		}
		checkByte(sendBuffer, 34 + tpv * 6); // 校验和CS
		sendBuffer[35 + tpv * 6] = 0x16; // 结束字符
		return (36 + tpv * 6);
	}

	/**
	 * 生成校验码
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param index
	 *            校验码的下标
	 */
	private static void checkByte(byte[] sendBuffer, int index)
	{
		byte temp = 0x00;

		for (int i = 6; i < index; i++)
		{
			temp += sendBuffer[i];
		}
		sendBuffer[index] = temp;
	}

	/**
	 * 04设置参数，05控制命令 主站IP地址和端口 04H F3p0 终端电能表/交流采样装置配置参数（集中抄表扩充部分） 04H F10p0
	 * 终端抄表运行参数 04H F33p0 台区集中抄表重点户设置 04H F35p0 终端与主站通信流量门限设置 04H F36p0 终端级联通信参数
	 * 04H F37p0 对时命令 05H F31p0
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param command
	 *            命令码，数据标识+数据单元
	 *            0400000400数据单元|0400000201数据单元|0400000104数据单元|0400000404
	 *            数据单元|0400000804数据单元|0400001004数据单元 一次可执行同AFN的一批命令
	 * @param tpv
	 *            启动站是否有时间标签tpv
	 * @param delayTime
	 *            有时间标签的情况下，允许发送传输延时时间
	 * @param fir
	 *            首帧标志
	 * @param fin
	 *            末帧标志
	 * @param pseq
	 *            启动帧序号PSEQ
	 * @param password
	 *            主站生成的密码
	 * @return 字节数组sendBuffer长度
	 */
	private static int write0405(byte[] sendBuffer, String command, int tpv,
			int delayTime, int fir, int fin, int pseq, String password)
	{

		int dataLen = 0; // 数据单元标识及数据单元的长度
		String[] commandArray = command.split("\\|");
		for (int j = 0; j < commandArray.length; j++)
		{
			if (commandArray[j].length() < 10)
			{
				continue;
			}
			String commandName = commandArray[j].substring(0, 10);
			String commandData = commandArray[j].substring(10,
					commandArray[j].length());
			for (int k = 0; k < 4; k++)
			{
				sendBuffer[14 + k + dataLen] = strToByte(commandName.substring(
						k * 2 + 2, k * 2 + 4));
			}
			dataLen += 4;
			switch (commandName)
			{
			case "0400000100": // 终端上行通信口通信参数设置(主要是心跳周期)
				dataLen = setUpParameter(sendBuffer, commandData, dataLen);
				break;
			case "0400000400": // 主站IP地址和端口
				dataLen = setChannelParameter(sendBuffer, commandData, dataLen);
				break;
			case "0400000201": // 终端电能表/交流采样装置配置参数
				dataLen = modiMeterPara(sendBuffer, commandData, dataLen);
				break;
			case "0400000104": // 终端抄表运行参数设置
				dataLen = setReadPara(sendBuffer, commandData, dataLen);
				break;
			case "0400000404": // 台区集中抄表重点户设置
				dataLen = setImpUser(sendBuffer, commandData, dataLen);
				break;
			case "0400000804":
				break;
			case "0400001004":
				break;
			case "0400004004": // 2 类数据配置设置 （在终端支持的2 类数据配置内）
				dataLen = set0DParameters(sendBuffer, commandData, dataLen);
				break;
			case "0500004003": // 对时命令
				dataLen = adjustTime(sendBuffer, commandData, dataLen);
				break;
			case "0500001006": // 删除指定通信端口下的全部电表
				dataLen = deleteMeter(sendBuffer, commandData, dataLen);
				break;
			default:
				break;
			}
		}
		sendBuffer[1] = (byte) (((24 + dataLen + tpv * 6) * 4 + 2) % 256); // 长度L,控制域、地址域、链路用户数据的和,1+5+1+1+dataLen+16+tpv*6,后缀协议标识10
		sendBuffer[2] = (byte) (((24 + dataLen + tpv * 6) * 4 + 2) / 256);
		sendBuffer[3] = sendBuffer[1];
		sendBuffer[4] = sendBuffer[2];

		sendBuffer[6] = 0x4A; // 控制域
								// 01001010:下行;报文来自启动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为10,表示请求1级数据,用于应用层请求确认的链路传输

		sendBuffer[13] = (byte) ((tpv * 8 + fir * 4 + fin * 2 + 1) * 16 + pseq); // 帧序列域SEQ,暂不考虑重发时RSEQ的变化

		for (int k = 0; k < 16; k++)
		{
			sendBuffer[14 + dataLen + k] = strToByte(password.substring(k * 2,
					k * 2 + 2));
		}
		if (tpv == 1)
		{
			// 时间标签Tp
			sendBuffer[30 + dataLen] = 0x00; // 启动帧序号计数器PFC 暂时不计数
			Calendar c = Calendar.getInstance();
			sendBuffer[31 + dataLen] = I10ToI16(c.get(Calendar.SECOND)); // 秒
			sendBuffer[32 + dataLen] = I10ToI16(c.get(Calendar.MINUTE)); // 分
			sendBuffer[33 + dataLen] = I10ToI16(c.get(Calendar.HOUR_OF_DAY)); // 时
			sendBuffer[34 + dataLen] = I10ToI16(c.get(Calendar.DATE)); // 日
			sendBuffer[35 + dataLen] = (byte) delayTime; // 允许发送传输延时时间
		}

		checkByte(sendBuffer, 30 + dataLen + tpv * 6); // 校验和CS
		sendBuffer[31 + dataLen + tpv * 6] = 0x16; // 结束字符

		return (32 + dataLen + tpv * 6);
	}

	private static int setUpParameter(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int setChannelParameter(byte[] sendBuffer,
			String commandData, int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 终端电能表/交流采样装置配置参数
	 * 
	 * @param sendBuffer
	 *            发送数据缓冲区
	 * @param commandData
	 *            命令参数
	 * @param dataLen
	 *            用户数据长度
	 * @return 用户数据长度
	 */
	private static int modiMeterPara(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		String[] str = commandData.split(",");

		int meterNum = Integer.parseInt(str[0]); // 本次电能表/交流采样装置配置数量
		if (str.length < meterNum * 13 + 1)
		{
			return 0;
		}
		sendBuffer[14 + dataLen] = (byte) (meterNum % 256);
		sendBuffer[15 + dataLen] = (byte) (meterNum / 256);
		for (int i = 0; i < meterNum; i++)
		{
			int meterIndex = Integer.parseInt(str[1 + i * 13]); // 电能表/交流采样装置序号
			sendBuffer[16 + dataLen] = (byte) (meterIndex % 256);
			sendBuffer[17 + dataLen] = (byte) (meterIndex / 256);

			int pointIndex = Integer.parseInt(str[2 + i * 13]); // 所属测量点号
			sendBuffer[18 + dataLen] = (byte) (pointIndex % 256);
			sendBuffer[19 + dataLen] = (byte) (pointIndex / 256);

			int baudRate = 1200;
			if (!str[3 + i * 13].equals("") && str[3 + i * 13] != null)
			{
				baudRate = Integer.parseInt(str[3 + i * 13]); // 通信速率
			}
			switch (baudRate)
			{
			case 600:
				baudRate = 1;
				break;
			case 1200:
				baudRate = 2;
				break;
			case 2400:
				baudRate = 3;
				break;
			case 4800:
				baudRate = 4;
				break;
			case 7200:
				baudRate = 5;
				break;
			case 9600:
				baudRate = 6;
				break;
			case 19200:
				baudRate = 7;
				break;
			case 0:
				baudRate = 0;
				break;
			default:
				break;
			}
			int port = Integer.parseInt(str[4 + i * 13]); // 通信端口号
			sendBuffer[20 + dataLen] = (byte) (baudRate * 32 + port);

			int protocol = 1; // 默认97规约；
			if (!str[5 + i * 13].equals("") && str[5 + i * 13] != null)
			{
				protocol = Integer.parseInt(str[5 + i * 13]); // 通信协议类型
			}
			sendBuffer[21 + dataLen] = (byte) protocol;

			String meterAddress = "";
			meterAddress = padLeft(str[6 + i * 13], 12); // 通信地址
			for (int j = 0; j < 6; j++)
			{
				sendBuffer[27 + dataLen - j] = strToByte(meterAddress
						.substring(j * 2, j * 2 + 2));
			}

			if (str[7 + i * 13].equals("") || str[7 + i * 13] == null)
			{
				str[7 + i * 13] = "02000000";
			}
			long password = Long.parseLong(str[7 + i * 13]); // 通信密码
			for (int k = 0; k < 6; k++)
			{
				long dr = (long) Math.pow(2, 8 * (5 - k));
				sendBuffer[33 + dataLen - k] = (byte) (password / dr);
				password = (long) (password % dr);
			}

			int feilv = Integer.parseInt(str[8 + i * 13]); // 电能费率个数
			sendBuffer[34 + dataLen] = (byte) feilv;

			int intNum = Integer.parseInt(str[9 + i * 13]) - 4; // 整数位个数
			int decimalNum = Integer.parseInt(str[10 + i * 13]) - 1; // 小数位个数
			sendBuffer[35 + dataLen] = (byte) (intNum * 4 + decimalNum);

			String collAddress = ""; // 采集器地址
			collAddress = padLeft(str[11 + i * 13], 12);
			for (int m = 0; m < 6; m++)
			{
				sendBuffer[41 + dataLen - m] = strToByte(collAddress.substring(
						m * 2, m * 2 + 2));
			}

			int bigKind = Integer.parseInt(str[12 + i * 13]); // 用户大类号
			int smallKind = Integer.parseInt(str[13 + i * 13]); // 用户小类号
			sendBuffer[42 + dataLen] = (byte) (bigKind * 16 + smallKind);

			dataLen += 27;
		}
		dataLen += 2;

		return dataLen;
	}

	private static int setReadPara(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int setImpUser(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int set0DParameters(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 对时命令
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param commandData
	 *            数据单元内容
	 * @param dataLen
	 *            数据单元长度
	 * @return dataLen 用户数据长度
	 */
	private static int adjustTime(byte[] sendBuffer, String commandData,
			int dataLen)
	{

		String para = padLeft(commandData, 14);

		for (int i = 0; i < 6; i++)
		{
			sendBuffer[19 + dataLen - i] = strToByte(para.substring(i * 2,
					i * 2 + 2));
		}
		int weekday = Integer.parseInt(para.substring(12, 14)); // 星期
		sendBuffer[18 + dataLen] = (byte) (weekday * 32 + sendBuffer[18 + dataLen]);

		dataLen += 6;

		return dataLen;
	}

	/**
	 * 删除指定通信端口下的全部电表
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param commandData
	 *            数据单元内容
	 * @param dataLen
	 *            数据单元长度
	 * @return dataLen 用户数据域长度
	 */
	private static int deleteMeter(byte[] sendBuffer, String commandData,
			int dataLen)
	{

		sendBuffer[14 + dataLen] = Byte.parseByte(commandData);

		dataLen += 1;
		return dataLen;
	}

	/**
	 * 请求终端配置及信息，请求1类数据（实时数据）
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param command
	 *            命令
	 * @param tpv
	 *            时间标签
	 * @param delayTime
	 *            允许延迟时间
	 * @param fir
	 *            首帧标识
	 * @param fin
	 *            末帧标识
	 * @param pseq
	 *            启动帧序号pseq
	 * @return 用户数据长度
	 */
	private static int read090C(byte[] sendBuffer, String command, int tpv,
			int delayTime, int fir, int fin, int pseq)
	{

		int dataLen = 0; // 数据单元标识及数据单元的长度
		String[] commandArray = command.split("\\|");
		for (int j = 0; j < commandArray.length; j++)
		{
			// commandArray[j] = padLeft(commandArray[j], 10);
			for (int k = 0; k < 4; k++)
			{
				sendBuffer[14 + k + dataLen] = strToByte(commandArray[j]
						.substring(k * 2 + 2, k * 2 + 4));
			}
			dataLen += 4;
		}
		sendBuffer[1] = (byte) (((8 + dataLen + tpv * 6) * 4 + 2) % 256); // 长度L,控制域、地址域、链路用户数据的和,1+5+1+1+dataLen+tpv*6,,后缀协议标识10
		sendBuffer[2] = (byte) (((8 + dataLen + tpv * 6) * 4 + 2) / 256);
		sendBuffer[3] = sendBuffer[1];
		sendBuffer[4] = sendBuffer[2];

		sendBuffer[6] = 0x4B; // 控制域
								// 01001011:下行;报文来自启动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为11,表示请求2级数据,用于应用层请求数据的链路传输

		sendBuffer[13] = (byte) ((tpv * 8 + fir * 4 + fin * 2 + 0) * 16 + pseq); // 帧序列域SEQ

		if (tpv == 1)
		{
			// 时间标签Tp
			sendBuffer[14 + dataLen] = 0x00; // 启动帧序号计数器PFC 暂时不计数
			Calendar c = Calendar.getInstance();
			sendBuffer[15 + dataLen] = I10ToI16(c.get(Calendar.SECOND)); // 秒
			sendBuffer[16 + dataLen] = I10ToI16(c.get(Calendar.MINUTE)); // 分
			sendBuffer[17 + dataLen] = I10ToI16(c.get(Calendar.HOUR_OF_DAY)); // 时
			sendBuffer[18 + dataLen] = I10ToI16(c.get(Calendar.DATE)); // 日
			sendBuffer[19 + dataLen] = (byte) delayTime; // 允许发送传输延时时间
		}

		checkByte(sendBuffer, 14 + dataLen + tpv * 6); // 校验和CS
		sendBuffer[15 + dataLen + tpv * 6] = 0x16; // 结束字符

		return (16 + dataLen + tpv * 6);
	}

	/**
	 * 查询参数，请求2类数据（历史数据）
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param afn
	 *            应用功能码AFN
	 * @param command
	 *            命令
	 * @param tpv
	 *            时间标签
	 * @param delayTime
	 *            允许延迟时间
	 * @param fir
	 *            首帧标识
	 * @param fin末帧标识
	 * @param pseq
	 *            启动帧序号pseq
	 * @return 用户数据长度
	 */
	private static int read0A0D(byte[] sendBuffer, String afn, String command,
			int tpv, int delayTime, int fir, int fin, int pseq)
	{

		int dataLen = 0; // 数据单元标识及数据单元的长度
		String[] commandArray = command.split("\\|");
		for (int j = 0; j < commandArray.length; j++)
		{
			if (commandArray[j].length() < 10)
			{
				continue;
			}
			String commandName = commandArray[j].substring(0, 10);
			String commandData = commandArray[j].substring(10,
					commandArray[j].length());
			for (int k = 0; k < 4; k++)
			{
				sendBuffer[14 + k + dataLen] = strToByte(commandName.substring(
						k * 2 + 2, k * 2 + 4));
			}
			dataLen += 4;
			if (afn.equals("0A"))
			{
				switch (commandName)
				{
				case "0A00000201": // 终端电能表/交流采样装置配置参数
					dataLen = readMeterPara(sendBuffer, commandData, dataLen);
					break;
				case "0A00000104": // 终端抄表运行参数
					dataLen = readPara(sendBuffer, commandData, dataLen);
					break;
				case "0A00000404": // 台区集中抄表重点户设置
					// 没有数据体
					break;
				case "0A00004004": // 2 类数据配置设置 （在终端支持的2 类数据配置内）
					dataLen = read0DPara(sendBuffer, commandData, dataLen);
					break;
				default:
					break;
				}
			} else
			{
				switch (commandName.substring(6, 10))
				{
				case "0100": // 正向有／无功电能示值、一／四象限无功电能示值（总、费率1~M　1<=M<=12）日冻结Td-d
				case "0106": // 终端日供电时间、日复位累计次数（日冻结）Td-d
				case "0206": // 终端日控制统计数据（日冻结）Td-d
				case "1006": // 终端与主站日通信流量（日冻结）Td-d
				case "0114": // 正向有功电能示值（总、费率1~M）日冻结Td-d
				case "0164": // 集中器下所有电表当前正向有功电能示值（总、费率1～M） 湖北国网规约扩展
				case "0264": // 数据网关扩充读缺失的历史数据(7天内)
					dataLen = readTdd(sendBuffer, commandData, dataLen);
					break;
				case "0102": // 正向有／无功电能示值、一／四象限无功电能示值（总、费率1~M　1<=M<=12）月冻结Td-m
				case "1002": // 正向有功电能量（总、费率1~M）月冻结Td-m
				case "0406": // 终端月供电时间、月复位累计次数（月冻结）Td-m
				case "0806": // 终端月控制统计数据（月冻结）Td-m
				case "2006": // 终端与主站月通信流量（月冻结）Td-m
				case "0116": // 正向有功电能示值（总、费率1~M）月冻结Td-m
					dataLen = readTdm(sendBuffer, commandData, dataLen);
					break;
				case "100C": // 正向有功总电能示值（曲线）Td-c
					dataLen = readTdc(sendBuffer, commandData, dataLen);
					break;
				default:
					break;
				}
			}
		}
		sendBuffer[1] = (byte) (((8 + dataLen + tpv * 6) * 4 + 2) % 256); // 长度L为控制域、地址域、链路用户数据的和,1+5+1+1+dataLen+tpv*6,后缀协议标识10
		sendBuffer[2] = (byte) (((8 + dataLen + tpv * 6) * 4 + 2) / 256);
		sendBuffer[3] = sendBuffer[1];
		sendBuffer[4] = sendBuffer[2];

		sendBuffer[6] = 0x4B; // 控制域
								// 01001011:下行;报文来自启动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为11,表示请求2级数据,用于应用层请求数据的链路传输

		sendBuffer[13] = (byte) ((tpv * 8 + fir * 4 + fin * 2) * 16 + pseq); // 帧序列域SEQ,暂不考虑重发时RSEQ的变化

		if (tpv == 1)
		{
			// 时间标签Tp
			sendBuffer[14 + dataLen] = 0x00; // 启动帧序号计数器PFC 暂时不计数
			Calendar c = Calendar.getInstance();
			sendBuffer[15 + dataLen] = I10ToI16(c.get(Calendar.SECOND)); // 秒
			sendBuffer[16 + dataLen] = I10ToI16(c.get(Calendar.MINUTE)); // 分
			sendBuffer[17 + dataLen] = I10ToI16(c.get(Calendar.HOUR_OF_DAY)); // 时
			sendBuffer[18 + dataLen] = I10ToI16(c.get(Calendar.DATE)); // 日
			sendBuffer[19 + dataLen] = (byte) delayTime; // 允许发送传输延时时间
		}

		checkByte(sendBuffer, 14 + dataLen + tpv * 6); // 校验和CS
		sendBuffer[15 + dataLen + tpv * 6] = 0x16; // 结束字符

		return (16 + dataLen + tpv * 6);
	}

	/**
	 * 终端电能表/交流采样装置配置参数
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param commandData
	 *            命令参数
	 * @param dataLen
	 *            用户数据长度
	 * @return 用户数据长度
	 */
	private static int readMeterPara(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		String[] str = commandData.split(",");

		int meterNum = Integer.parseInt(str[0]); // 本次查询数量
		sendBuffer[14 + dataLen] = (byte) (meterNum % 256);
		sendBuffer[15 + dataLen] = (byte) (meterNum / 256);

		for (int i = 0; i < meterNum; i++)
		{
			int meterIndex = Integer.parseInt(str[1 + i]); // 本次查询的第一个对象序号
			sendBuffer[16 + dataLen] = (byte) (meterIndex % 256);
			sendBuffer[17 + dataLen] = (byte) (meterIndex / 256);
			dataLen += 2;
		}
		dataLen += 2;
		return dataLen;
	}

	private static int readPara(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int read0DPara(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 抄读日冻结历史数据
	 * 
	 * @param sendBuffer
	 *            发送缓冲区
	 * @param commandData
	 *            命令参数
	 * @param dataLen
	 *            用户数据长度
	 * @return 用户数据长度
	 */
	private static int readTdd(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		String para = padLeft(commandData, 6);

		sendBuffer[16 + dataLen] = strToByte(para.substring(0, 2)); // 年
		sendBuffer[15 + dataLen] = strToByte(para.substring(2, 4)); // 月
		sendBuffer[14 + dataLen] = strToByte(para.substring(4, 6)); // 日

		dataLen += 3;

		return dataLen;
	}

	private static int readTdm(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int readTdc(byte[] sendBuffer, String commandData,
			int dataLen)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int readImportantEvent(byte[] sendBuffer, String command,
			int tpv, int delayTime, int fir, int fin, int pseq)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private static int transmit(byte[] sendBuffer, String command, int tpv,
			int delayTime, int fir, int fin, int pseq, String password, StringBuffer transresult) throws ParseException
	{
		if (command.length() < 10)
        {
            return 0;
        }
		String commandname = command.substring(0, 10);
		String commanddata = command.substring(10, command.length());
		String[] para = commanddata.split(",");
        if (para.length < 10)
        {
            return 0;
        }
        
        int startindex = 24; //数组起始下标
        int translen = 0;
        String transmitcommand = para[0];
        String meteraddress = "", meterpass = "", meterprotocol = "";
        switch(transmitcommand)
        {
        case "onoff": //电表断送电
            String onoff = para[9]; //自定义参数,电表断送电命令0表示断电,1表示通电
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            meterprotocol = para[12]; //自定义参数,电表规约,07规约为30,97规约为1
            String ma = padLeft(para[13], 1);//明文为0,暗文为1
            if ("0".equals(ma)) //明文
            {
                translen = MeterOnOff(sendBuffer, meteraddress, onoff, meterpass, startindex, meterprotocol);
            }
//            else //密文
//            {
//                String commandindex = para[14];
//                String pin = para[15];
//                if ("1".equals(commandindex)) //身份认证
//                {
//                    StringBuilder randnumber = new StringBuilder(50);
//                    int re = IdentityAuthentication(pin, randnumber);//向加密机发身份认证
//                    switch (re)
//                    {
//                        case 0: transresult = "成功";
//                            String sjs1 = randnumber.ToString().Substring(0, 16);
//                            String mw1 = randnumber.ToString().Substring(16);
//                            translen = MakeFrameDgn07.MeterIdentify(sendBuffer, meteraddress, mw1, sjs1, startindex, pin);
//                            break;
//                        case 200: transresult = "连接加密机失败";
//                            return 0;
//                        case 201: transresult = "取随机数1 失败";
//                            return 0;
//                        case 202: transresult = "取随机数2 失败";
//                            return 0;
//                        case 203: transresult = "密钥分散失败";
//                            return 0;
//                        case 204: transresult = "数据加密失败";
//                            return 0;
//                        case 205: transresult = "取密文失败";
//                            return 0;
//                        default: break;
//                    }
//                }
//                else if (commandindex == "2") //操作
//                {
//                    String sjs2 = para[16]; //随机数2
//                    String esam = para[17]; //ESAM
//                    String mingw = "";
//                    switch (onoff)
//                    {
//                        case "0"://拉闸
//                            mingw = "1A00";
//                            break;
//                        case "1"://合闸
//                            mingw = "1B00";
//                            break;
//                        case "2"://保电
//                            mingw = "3A00";
//                            break;
//                        case "3"://保电解除
//                            mingw = "3B00";
//                            break;
//                        default: break;
//                    }
//                    Date now = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
//                    String nexthour = sdf.format(addHour(now,1));
//                    //nexthour = nexthour.Substring(10, 2) + nexthour.Substring(8, 2) + nexthour.Substring(6, 2) + nexthour.Substring(4, 2) + nexthour.Substring(2, 2) + nexthour.Substring(0, 2);
//                    mingw += nexthour;
//                    String sendstr = sjs2 + pin + esam + mingw;
//                    StringBuilder miw = new StringBuilder(50);
//                    int re = UserControl(sendstr, miw);
//                    switch (re)
//                    {
//                        case 0: transresult = "成功";
//                            translen = MakeFrameDgn07.MeterControl(sendBuffer, meteraddress, miw.ToString(), meterpass, startindex);
//                            break;
//                        case 200: transresult = "连接加密机失败";
//                            return 0;
//                        case 201: transresult = "写卡失败";
//                            return 0;
//                        case 202: transresult = "读卡失败";
//                            return 0;
//                        case 203: transresult = "计算密文失败";
//                            return 0;
//                        default: break;
//                    }
//                }
//            }
            break;
        case "xjonoff"://许继modbus拉合闸
            String onoffxj = para[9]; //自定义参数,电表断送电命令0表示断电,1表示通电
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            String controlindex = padLeft(para[11], 8); //自定义参数,继电器序号：00 01 02 03
            translen = EncoderOfModbusXJ.OnOff(sendBuffer, meteraddress, onoffxj, controlindex, startindex);
                break;
        case "correctdate": //电表校日期
            String corrdate = padLeft(para[9], 1); //自定义参数,电表校时命令为时间
            String meteraddress1 = padLeft(para[10], 12); //自定义参数,电表地址
            String meterpass1 = padLeft(para[11], 8); //自定义参数,电表密码
            String meterprotocol1 = para[12]; //自定义参数,电表规约,07规约为30,97规约为1
            translen = MeterCorrectDate(sendBuffer, meteraddress1, corrdate, meterpass1, startindex, meterprotocol1);
            break;
        case "correcttime": //电表校时
            String corrtime = padLeft(para[9], 1); //自定义参数,电表校时命令为时间
            String meteraddress2 = padLeft(para[10], 12); //自定义参数,电表地址
            String meterpass2= padLeft(para[11], 8); //自定义参数,电表密码
            String meterprotocol2 = para[12]; //自定义参数,电表规约,07规约为30,97规约为1
            translen = MeterCorrectTime(sendBuffer, meteraddress2, corrtime, meterpass2, startindex, meterprotocol2);
            break;
//        case "wa9010": //读水表当前止码
//            String meteraddress3 = para[10].PadLeft(12, '0'); //自定义参数,电表地址
//            String meterprotocol3 = para[12]; //自定义参数,电表规约,07规约为30,97规约为1
//            if (meterprotocol3 == "8") //188规约
//            {
//                translen = Wa188901F(sendBuffer, meteraddress3, startindex); //188规约
//            }
//            else
//            {
//                translen = Wa979010(sendBuffer, meteraddress3, startindex); //97规约
//            }
//            break;
//        case "ladderprice": //设置阶梯电价切换时间
//            String commandpara4 = para[9].PadLeft(1, '0'); //自定义参数,set/get
//            String meteraddress4 = para[10].PadLeft(12, '0'); //自定义参数,电表地址
//            String meterpass4 = para[11].PadLeft(8, '0'); //自定义参数,电表密码
//            String changetime = para[12]; //自定义参数,阶梯电价切换时为切换时间
//            translen = LadderPrice07(sendBuffer, meteraddress4, commandpara4, meterpass4, startindex, changetime);
//            break;
        case "lamp41": //读路灯状态
        case "lamp5D": //路灯校时
        	String commandcode = padLeft(para[9], 1); //自定义参数,路灯命令代码
        	String lampaddress = para[10]; //自定义参数,路灯地址
        	String lamppara = para[11]; //自定义参数,路灯参数
        	translen = EncoderOfLamp.GeneralFrame(sendBuffer, lampaddress, commandcode, lamppara, startindex);
            break;
        case "lamp10": //路灯开关
        case "lamp5E"://设置回路1 1-6月开关灯时间
        case "lamp60"://设置回路2 1-6月开关灯时间
        case "lamp5F"://设置回路1 7-12月开关灯时间
        case "lamp61"://设置回路1 7-12月开关灯时间
        	String commandcode5 = padLeft(para[9], 1); //自定义参数,路灯命令代码
        	String meteraddress5 = para[10]; //自定义参数,路灯地址
        	String lamppara5 = para[11] + "," + para[12]; //自定义参数,路灯参数
            translen = EncoderOfLamp.GeneralFrame(sendBuffer, meteraddress5, commandcode5, lamppara5, startindex);
            break;
        case "lamp53"://设置回路1参数
        case "lamp54"://设置回路2参数
        	String commandcode6 = padLeft(para[9], 1); //自定义参数,路灯命令代码
        	String meteraddress6 = para[10]; //自定义参数,路灯地址
        	String lamppara6 = para[11] + "," + para[12] + "," + para[13] + "," + para[14]; //自定义参数,路灯参数
            translen = EncoderOfLamp.GeneralFrame(sendBuffer, meteraddress6, commandcode6, lamppara6, startindex);
            break;
        case "classroom": //教室照明
        	String commandcode7 = padLeft(para[9], 1); //自定义参数,03/10
        	String classroomaddress = para[10]; //自定义参数,教室照明控制器地址
        	String jcqstartaddress = para[11] ; //自定义参数,寄存器起始地址
        	String classroompara = para[12];//命令参数
            //translen = Classroom(sendBuffer, commandcode7, jcqstartaddress, classroomaddress, classroompara, startindex);
            break;
//        case "transmit"://直接透传报文
//            String transmitpara = para[9];
//            translen = FrameTransmit(sendBuffer, transmitpara, startindex);
//            break;
        case "purchase": //购电及开户销户
            String operflag = para[9];
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String para1 = para[12]; //自定义参数,对于购电为购电金额,对于开户销户为1或0
            String purchasepara = "";
            if ("recharge".equals(operflag))
            {
                ma = padLeft(para[13], 1);//明文为0,暗文为1
                String times = para[14]; //自定义参数,对于购电为购电次数
                String userno = para[15]; //户号
                purchasepara = meterpass + "," + para1 + "," + times + "," + userno + "," + ma;
                if ("1".equals(ma))
                {
                    String commandindex = para[16];
                    String pin = para[17];
                    purchasepara += "," + commandindex + "," + pin;
                    if ("2".equals(commandindex))
                    {
                        String sjs2 = para[18]; //随机数2
                        //String esam = para[18]; //ESAM
                        purchasepara += "," + sjs2;
                    }
                }
                translen = MeterRecharge(sendBuffer, meteraddress, operflag, purchasepara, startindex, transresult);
            }
            else
            {
                purchasepara = meterpass + "," + para1;
                translen = MeterPurchase(sendBuffer, meteraddress, operflag, purchasepara, startindex);
            }
            break;
        case "C0C0"://恶性负载读写
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String c0c0flag = para[9]; //自定义参数,如果是写命令则是：YY：恶性负载标志位；XX XX（BCD）恶性负载设置值，实际功率为该值乘以20W
            if ("".equals(c0c0flag) || c0c0flag == null)//读恶性负载
            {
                translen = EncoderOfDgn97.ReadData(sendBuffer, meteraddress, startindex, transmitcommand);
            }
            else//写恶性负载
            {
                String c0c0vol = para[12];
                translen = EncoderOfDgn97.MalignantLoad(sendBuffer, meteraddress, c0c0flag, c0c0vol, meterpass, startindex);
            }
            break;
        case "C120"://软件数据总清
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn97.AllClear(sendBuffer, meteraddress, startindex);
            break;
        case "C1C0"://允许恶性负载范围读写
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String lowerlimit = para[9]; //自定义参数,如果是写命令则是：MIN1 MIN0 允许恶性负载设置下限值  MAX1 MAX0 允许恶性负载设置上限值，实际功率为该值乘以20
            if ("".equals(lowerlimit) || lowerlimit == null)//读恶性负载
            {
                translen = EncoderOfDgn97.ReadData(sendBuffer, meteraddress, startindex, transmitcommand);
            }
            else//写允许恶性负载范围
            {
                String upperlimit = para[12];
                translen = EncoderOfDgn97.MalignantAllow(sendBuffer, meteraddress, lowerlimit, upperlimit, meterpass, startindex);
            }
            break;
        case "C020"://电表运行状态字
        case "E502"://97表读当前剩余电量
        case "E50F"://97表集抄卡表数据集合
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn97.ReadData(sendBuffer, meteraddress, startindex, transmitcommand);
            break;
        case "E501"://97表读上次购电量或购电
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String paratimes = para[9]; //自定义参数,如果是下发购电量则为购电次数
            if ("".equals(paratimes) || paratimes == null)//读上次购电量
            {
                translen = EncoderOfDgn97.ReadData(sendBuffer, meteraddress, startindex, transmitcommand);
            }
            else//购电
            {
                String paravol = para[12];
                translen = EncoderOfDgn97.Purchase(sendBuffer, meteraddress, paratimes,paravol, meterpass, startindex);
            }
            break;
        case "E50B"://设置状态字(预付费状态)
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String parae50b = para[9]; //自定义参数
            translen = EncoderOfDgn97.SetFlagWord(sendBuffer, meteraddress, parae50b, meterpass, startindex);
            break;
        case "E50E"://97表退购
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String parae50e = para[9]; //自定义参数
            translen = EncoderOfDgn97.ReturnPurchase(sendBuffer, meteraddress, parae50e, meterpass, startindex);
            break;
        case "SYValue"://（当前）剩余电量
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "00900100");
            break;
        case "TZValue"://（当前）透支电量
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "00900101");
            break;
        case "SYMoney"://（当前）剩余金额
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "00900200");
            break;
        case "TZMoney"://（当前）透支金额
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "00900201");
            break;
        case "ThisCircleVol": //当前结算周期总累计用电量
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "000B0000");
            break;
        case "LastCircleVol": //上一结算周期总累计用电量
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = Buy07(sendBuffer, meteraddress, startindex, "000B0001");
            break;
        case "setbalance": //设置电表结算日
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            meterpass = padLeft(para[11], 8); //自定义参数,电表密码
            String parabalance = para[12]; //自定义参数
            translen = SetBalance07(sendBuffer, meteraddress, parabalance, meterpass, startindex);
            break;
        case "getbalance": //读取电表结算日
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04000B01", 0, startindex);
            break;
        case "getladdernum": //读取电表阶梯数
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04000207", 0, startindex);
            break;
        case "getflnum": //读取电表费率数
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04000204", 0, startindex);
            break;
        case "getladderchangetime": //读两套阶梯切换时间
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04000109", 0, startindex);
            break;
        case "getladderprice11": //读第一套阶梯价格1
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060101", 0, startindex);
            break;
        case "getladderprice12": //读第一套阶梯价格2
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060102", 0, startindex);
            break;
        case "getladderprice13": //读第一套阶梯价格3
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060103", 0, startindex);
            break;
        case "getladderprice14": //读第一套阶梯价格4
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060104", 0, startindex);
            break;
        case "getladderprice21": //读第二套阶梯价格1
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060301", 0, startindex);
            break;
        case "getladderprice22": //读第二套阶梯价格2
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060302", 0, startindex);
            break;
        case "getladderprice23": //读第二套阶梯价格3
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060303", 0, startindex);
            break;
        case "getladderprice24": //读第二套阶梯价格4
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04060304", 0, startindex);
            break;
        case "getflchangetime": //读两套费率切换时间
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04000108", 0, startindex);
            break;
        case "getflprice11": //读取第一套费率价格1
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050101", 0, startindex);
            break;
        case "getflprice12": //读第一套费率价格2
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050102", 0, startindex);
            break;
        case "getflprice13": //读第一套费率价格3
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050103", 0, startindex);
            break;
        case "getflprice14": //读第一套费率价格4
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050104", 0, startindex);
            break;
        case "getflprice163":
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "0405013F", 0, startindex);
            break;
        case "getflprice21": //读取第二套费率价格1
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050201", 0, startindex);
            break;
        case "getflprice22": //读第二套费率价格2
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050202", 0, startindex);
            break;
        case "getflprice23": //读第二套费率价格3
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050203", 0, startindex);
            break;
        case "getflprice24": //读第二套费率价格4
            meteraddress = padLeft(para[10], 12); //自定义参数,电表地址
            translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "04050204", 0, startindex);
            break;
        default: break;
        }
        sendBuffer[1] = (byte)(((34 + translen + tpv * 6) * 4 + 2) % 256); //长度L为控制域,地址域,链路用户数据的和,1+5+1+1+4+6+translen+16+tpv*6,后缀协议标识10
        sendBuffer[2] = (byte)(((34 + translen + tpv * 6) * 4 + 2) / 256);
        sendBuffer[3] = sendBuffer[1];
        sendBuffer[4] = sendBuffer[2];
        sendBuffer[6] = 0x48; //控制域 01001011:下行;报文来自启动站;暂时不考虑帧计数有效位与帧计数位,都设默认值0;功能码为8,因为七叶的国网主站发送的为8
        //***************链路用户数据开始*****************************************************************************************************************
        sendBuffer[13] = (byte)((tpv * 8 + 6) * 16 + pseq); //帧序列域SEQ,,暂不考虑重发时RSEQ的变化
        for (int j = 0; j < 4; j++) //数据单元标识 4字节
        {
        	sendBuffer[14 + j] = strToByte(commandname.substring(j * 2 + 2, j * 2 + 4));
        }

        sendBuffer[18] = Byte.parseByte(para[1]); //终端通信端口号
        //sendBuffer[18] = 0x02; 

        int baudrate = Integer.parseInt(para[2]); //通信速率
        switch (baudrate)
        {
            case 600: baudrate = 1; break;
            case 1200: baudrate = 2; break;
            case 2400: baudrate = 3; break;
            case 4800: baudrate = 4; break;
            case 7200: baudrate = 5; break;
            case 9600: baudrate = 6; break;
            case 19200: baudrate = 7; break;
            default: baudrate = 6; break; //默认波特率为9600
        }
        int stopbit = Integer.parseInt(para[5]); //停止位
        if (stopbit == 1)
        {
            stopbit = 0;
        }
        else
        {
            stopbit = 1;
        }
        int verifybit = 1; //是否有校验位,0表示无校验,1表示有校验
        int evenorodd = 0; //0表示偶校验,1表示奇校验
        switch (para[3]) //校验位
        {
            case "n": verifybit = 0;
                break;
            case "e": verifybit = 1;
                evenorodd = 0;
                break;
            case "o": verifybit = 1;
                evenorodd = 1;
                break;
            default: break;
        }
        
        int databit = Integer.parseInt(para[4]) - 5; //数据位,0-3表示5-8位数
        sendBuffer[19] = (byte)(baudrate * 32 + stopbit * 16 + verifybit * 8 + evenorodd * 4 + databit); //透明转发通信控制字

        int frameunit = Integer.parseInt(para[6]); //透明转发接收等待报文超时时间单位,10表示10ms,1表示1s
        if (frameunit == 10)
        {
            frameunit = 0; //0表示10ms
        }
        else
        {
            frameunit = 1; //1表示1s
        }
        int framevalue = Integer.parseInt(para[7]); //透明转发接收等待报文超时时间数值
        sendBuffer[20] = (byte)(frameunit * 128 + framevalue); //透明转发接收等待报文超时时间

        sendBuffer[21] = Byte.parseByte(para[8]); //透明转发接收等待字节超时时间,单位10ms

        sendBuffer[22] = (byte)(translen % 256); //透明转发内容字节数
        sendBuffer[23] = (byte)(translen / 256);

        for (int k = 0; k < 16; k++)
        {
        	sendBuffer[24 + translen + k] = strToByte(password.substring(k * 2, k * 2 + 2));
        }
        if (tpv == 1)
        {
        	// 时间标签Tp
        	sendBuffer[40 + translen] = 0x00; // 启动帧序号计数器PFC 暂时不计数
        	Calendar c = Calendar.getInstance();
        	sendBuffer[41 + translen] = I10ToI16(c.get(Calendar.SECOND)); // 秒
        	sendBuffer[42 + translen] = I10ToI16(c.get(Calendar.MINUTE)); // 分
        	sendBuffer[43 + translen] = I10ToI16(c.get(Calendar.HOUR_OF_DAY)); // 时
        	sendBuffer[44 + translen] = I10ToI16(c.get(Calendar.DATE)); // 日
        	sendBuffer[45 + translen] = (byte) delayTime; // 允许发送传输延时时间
        }
        //*************链路用户数据结束*********************************************************************************************************************
        checkByte(sendBuffer, 40 + translen + tpv * 6); //校验和CS
        sendBuffer[41 + translen + tpv * 6] = 0x16; //结束字符

        return (42 + translen + tpv * 6);
	}

	/**
	 * 设置电表结算日
	 * @param sendbuffer
	 * @param meteraddress
	 * @param commandpara
	 * @param meterpass
	 * @param startindex
	 * @return
	 */
	private static int SetBalance07(byte[] sendbuffer, String meteraddress, String commandpara, String meterpass, int startindex)
	{
		int translen = EncoderOfDgn07.BalanceSet(sendbuffer, meteraddress, commandpara, meterpass, startindex); //透明转发内容字节数及赋值
        return translen;
	}

	/**
	 * 07表读剩余金额
	 * @param sendbuffer
	 * @param address
	 * @param startindex
	 * @param dataid
	 * @return
	 */
	private static int Buy07(byte[] sendbuffer, String address, int startindex, String dataid)
	{
		int translen = EncoderOfDgn07.ReadData(sendbuffer, address, dataid, 0, startindex);

        return translen;
	}

	/**
	 * 电表校时间
	 * @param sendBuffer
	 * @param meteraddress2
	 * @param corrtime
	 * @param meterpass2
	 * @param startindex
	 * @param meterprotocol2
	 * @return
	 */
	private static int MeterCorrectTime(byte[] sendbuffer, String meteraddress, String commandpara, String meterpass, int startindex, String meterprotocol)
	{
		int translen = 0;
        if ("30".equals(meterprotocol))
        {
            translen = EncoderOfDgn07.CorrectTime(sendbuffer, meteraddress, commandpara, meterpass, startindex); //透明转发内容字节数及赋值
        }
        else
        {
            translen = EncoderOfDgn07.CorrectTime(sendbuffer, meteraddress, commandpara, meterpass, startindex); //透明转发内容字节数及赋值
        }
        return translen;
	}

	/**
	 * 电表校日期
	 * @param sendBuffer
	 * @param meteraddress
	 * @param commandpara
	 * @param meterpass
	 * @param startindex
	 * @param meterprotocol
	 * @return
	 */
	private static int MeterCorrectDate(byte[] sendBuffer, String meteraddress, String commandpara, String meterpass, int startindex, String meterprotocol)
	{
		int translen = 0;
        if ("30".equals(meterprotocol))
        {
            translen = EncoderOfDgn07.CorrectDate(sendBuffer, meteraddress, commandpara, meterpass, startindex); //透明转发内容字节数及赋值
        }
        else
        {
            translen = EncoderOfDgn07.CorrectDate(sendBuffer, meteraddress, commandpara, meterpass, startindex); //透明转发内容字节数及赋值
        }
        return translen;
	}

	/**
	 * 预购
	 * @param sendBuffer
	 * @param meteraddress
	 * @param operflag
	 * @param purchasepara
	 * @param startindex
	 * @param transresult
	 * @return
	 */
	private static int MeterRecharge(byte[] sendBuffer, String meteraddress,
			String operflag, String purchasepara, int startindex,
			StringBuffer transresult)
	{
		int translen = 0;
        String[] paraarray = purchasepara.split(",");

        if (paraarray.length >= 4)
        {
            String meterpass = padLeft(paraarray[0], 8);
            String money = paraarray[1];
            String times = paraarray[2];
            String userno = paraarray[3];
            if ("0".equals(paraarray[4])) //明文
            {
                translen = EncoderOfDgn07.MeterPurchase(sendBuffer, meteraddress, "07010201", meterpass, money, times, startindex); //透明转发内容字节数及赋值
            }
//            else //密文
//            {
//                String commandindex = paraarray[5];
//                String pin = paraarray[6];
//                if ("1".equals(commandindex)) //身份认证
//                {
//                    StringBuilder randnumber = new StringBuilder(50);
//                    int re = IdentityAuthentication(pin, randnumber);//向加密机发身份认证
//                    switch (re)
//                    {
//                        case 0: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("成功");
//                            String sjs1 = randnumber.toString().substring(0,16);
//                            String mw1 = randnumber.toString().substring(16);
//                            translen = EncoderOfDgn07.MeterIdentify(sendBuffer, meteraddress, mw1, sjs1, startindex, pin);
//                            break;
//                        case 200: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("连接加密机失败");
//                            return 0;
//                        case 201: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("取随机数1 失败");
//                            return 0;
//                        case 202: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("取随机数2 失败");
//                            return 0;
//                        case 203: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("密钥分散失败");
//                            return 0;
//                        case 204: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("数据加密失败");
//                            return 0;
//                        case 205: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("取密文失败");
//                            return 0;
//                        default: break;
//                    }
//                }
//                else if ("2".equals(commandindex)) //操作
//                {
//                    String sjs2 = paraarray[7]; //随机数2
//                    int point = money.indexOf(".");
//                    money = money.substring(0, point) + money.substring(point + 1);
//                    //String mingw = money.PadLeft(8, '0') + times.PadLeft(8, '0') + userno;
//                    String mingw = padLeft(money, 8) + padLeft(times, 8) + "000000000000";
//                    String sendstr = sjs2 + pin + mingw;
//                    StringBuilder miw = new StringBuilder(50);
//                    int re = InCreasePurse(sendstr, miw);
//                    switch (re)
//                    {
//                        case 0: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("成功");
//                            translen = EncoderOfDgn07.MeterPurse(sendBuffer, meteraddress, miw.toString(), times, startindex);
//                            break;
//                        case 200: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("连接加密机失败");
//                            return 0;
//                        case 201: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("写卡失败");
//                            return 0;
//                        case 202: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("读卡失败");
//                            return 0;
//                        case 203: 
//                        	transresult.delete(0, transresult.length());
//                        	transresult.append("计算MAC失败");
//                            return 0;
//                        default: break;
//                    }
//                }
//            }
        }
        return translen;
	}

	/**
	 * 电表预付费
	 * @param sendBuffer
	 * @param meteraddress
	 * @param operflag
	 * @param purchasepara
	 * @param startindex
	 * @return
	 */
	private static int MeterPurchase(byte[] sendBuffer, String meteraddress,
			String operflag, String purchasepara, int startindex)
	{
		int translen = 0;
        String[] paraarray = purchasepara.split(",");

        switch (operflag)
        {
            case "openclose": //开户与销户
                if (paraarray.length >= 2)
                {
                    String password = padLeft(paraarray[0], 8);
                    translen = EncoderOfDgn07.AccountOnOff(sendBuffer, meteraddress, "07010101", password, paraarray[1], startindex); //透明转发内容字节数及赋值
                }
                break;
            case "lasttimes": //上次购电后总购电次数
                translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "03330201", 0, startindex);
                break;
            case "lastremainmoney": //上次购电后剩余金额
                translen = EncoderOfDgn07.ReadData(sendBuffer, meteraddress, "03330501", 0, startindex);
                break;
            case "returnmoney": //退购上一次购电记录
                String password1 = padLeft(paraarray[0], 8);
                translen = EncoderOfDgn07.MeterReturn(sendBuffer, meteraddress, "070102FE", password1, startindex);
                break;
            default: break;
        }
        return translen;
	}

	private static int MeterOnOff(byte[] sendBuffer, String meteraddress,
			String commandpara, String meterpass, int startindex, String meterprotocol)
	{
		int translen = 0;
        String dataid = "";
        switch (meterprotocol)
        { 
            case "1": //DL/T64597
                if (commandpara.length() == 1)
                {
                    commandpara = "C028" + commandpara;
                }
                if (commandpara.length() == 5)
                {
                    String controlcode = commandpara.substring(0, 4);
                    String onoff = commandpara.substring(4);
                    if ("0".equals(onoff)) //拉闸
                    {
                        dataid = "3355";
                    }
                    else
                    {
                        dataid = "9966"; //合闸
                    }
                    translen = EncoderOfDgn97.MeterOnOff(sendBuffer, meteraddress, controlcode,dataid, meterpass, startindex); //透明转发内容字节数及赋值
                }
                break;
            case "30": //DL/T64507
                if ("0".equals(commandpara)) //拉闸
                {
                    dataid = "0000001A";
                }
                else
                {
                    //dataid = "0000001B"; //合闸
                    dataid = "0000001C"; //合闸
                }
                translen = EncoderOfDgn07.MeterOnOff(sendBuffer, meteraddress, dataid, meterpass, startindex); //透明转发内容字节数及赋值
                break;
            case "255": //DL/T64507邵阳
                if ("0".equals(commandpara)) //拉闸
                {
                    dataid = "000000FA";
                }
                else
                {
                    dataid = "000000FB"; //合闸
                }
                translen = EncoderOfDgn07.MeterOnOff(sendBuffer, meteraddress, dataid, meterpass, startindex); //透明转发内容字节数及赋值
                break;
        }
        return translen;
	}
	
	/**
	 * 时间增加N小时
	 * 
	 * @param date
	 *            时间
	 * @param hours
	 *            增加的小时数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private static Date addHour(Date date, int hours) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.HOUR_OF_DAY, hours);
		return cld.getTime();
	}
}
