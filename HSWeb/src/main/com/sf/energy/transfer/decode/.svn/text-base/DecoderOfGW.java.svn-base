package com.sf.energy.transfer.decode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对收到的报文进行解析 按照不同的字段对报文进行解析，然后逐步组成xml格式
 * 
 * @author 崔正阳
 * @version 1.0
 * @since [盛帆电子/数据中转站1.0]
 */
public class DecoderOfGW
{
	/**
	 * 解析报文内容入口处理函数
	 * 
	 * @param rcvBuffer
	 *            接受到的报文字节数组
	 * @param opercode
	 *            操作员的编号
	 * @param tasknum
	 *            任务的编号
	 * @param terminaladdress
	 *            数据网关的硬件地址
	 * @param lastcommand
	 *            执行的上一条命令
	 * @param lastpara
	 *            上一条执行命令的命令参数
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	public static String receiveParse(byte[] rcvBuffer, String opercode,
			String tasknum, String terminaladdress, String lastcommand,
			String lastpara)
	{
		String xmlrtvalue = "";
		
		try
		{
			// 判断是否是 结束帧
			if ((rcvBuffer[13] & 0x20) == (byte)0x20)
			{
				xmlrtvalue = "<SFROOT gy=\"GW\"><GW terminaladdress=\""
						+ terminaladdress + "\" opercode=\"" + opercode
						+ "\" tasknum=\"" + tasknum + "\" isend=\"1\">";
			} else
			{
				xmlrtvalue = "<SFROOT gy=\"GW\"><GW terminaladdress=\""
						+ terminaladdress + "\" opercode=\"" + opercode
						+ "\" tasknum=\"" + tasknum + "\" isend=\"0\">";
			}
			// 判断是否是确认/否认帧
			if (rcvBuffer[12] == (byte)0x00)
			{
				xmlrtvalue += confirmParse(rcvBuffer, lastcommand);
			} else
			{
				// 有返回数据的解析
				xmlrtvalue += dataParse(rcvBuffer, lastpara);
			}
		} catch (Exception ex)
		{
			// CLog.WriteLog("ParseFrameGW.ReceiveParse:" + ex.ToString());
		}
		xmlrtvalue += "</GW></SFROOT>";
		
		return xmlrtvalue;
	}
	
	/**
	 * AFN=00，对确认/否认解析
	 * 
	 * @param rcvBuffer
	 *            收到的报文字节数组
	 * @param lastcommand
	 *            上一条命令
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String confirmParse(byte[] rcvBuffer, String lastcommand)
	{
		String xmlrtvalue = "";
		switch ((byte)rcvBuffer[16])
		{
		// 全部确认
			case 0x01:
				xmlrtvalue = "<commandback code=\"" + lastcommand
						+ "\" error=\"0\" errormessage=\"\"/>";
				break;
			// 全部否认
			case 0x02:
				xmlrtvalue = "<commandback code=\"" + lastcommand
						+ "\" error=\"1\" errormessage=\"否认\"/>";
				break;
			// 按数据单元标识确认和否认：对收到报文中的全部数据单元标识进行逐个确认/否认
			case 0x04:
				// 要被确认的报文的afn
				String afn = hexToString(rcvBuffer[18]);
				// 数据单元个数
				int num = (rcvBuffer[2] * 256 + rcvBuffer[1] - 21) / 5;
				String backcode = lastcommand;
				for (int i = 0; i < num; i++)
				{
					backcode = afn + hexToString(rcvBuffer[19 + i * 5])
							+ hexToString(rcvBuffer[20 + i * 5])
							+ hexToString(rcvBuffer[21 + i * 5])
							+ hexToString(rcvBuffer[22 + i * 5]);
					String err = hexToString(rcvBuffer[23 + i * 5]);
					if (err.equals("0"))
					{
						xmlrtvalue = "<commandback code=\"" + backcode
								+ "\" error=\"0\" errormessage=\"\"/>";
					} else if (err.equals("1"))
					{
						xmlrtvalue = "<commandback code=\"" + backcode
								+ "\" error=\"1\" errormessage=\"否认\"/>";
					}
				}
				break;
			default:
				xmlrtvalue = "<commandback code=\"" + lastcommand
						+ "\" error=\"1\" errormessage=\"DT错误\"/>";
				break;
		}
		return xmlrtvalue;
	}
	
	/**
	 * 按AFN先对需要返回信息的命令报文解析进行初步判断的函数
	 * 
	 * @param rcvBuffer
	 *            收到的报文字节数组
	 * @param lastpara
	 *            上一条命令的命令参数
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String dataParse(byte[] rcvBuffer, String lastpara)
	{
		String xmlrtvalue = "";
		String afn = hexToString(rcvBuffer[12]);
		
		switch (afn)
		{
			case "09": // 请求终端配置
				xmlrtvalue = Parse09(rcvBuffer, afn);
				break;
			case "0a": // 查询参数
			case "0A":
				xmlrtvalue = Parse0A(rcvBuffer, afn);
				break;
			case "0c": // 请求1类实时数据
			case "0C":
				xmlrtvalue = Parse0C(rcvBuffer, afn, lastpara);
				break;
			case "0d": // 请求2类历史数据
			case "0D":
				xmlrtvalue = Parse0D(rcvBuffer, afn);
				break;
			case "10": // 透明数据转发
				xmlrtvalue = Parse10(rcvBuffer, afn, lastpara);
				break;
			default:
				//System.out.println("没有匹配的AFN:" + afn);
				xmlrtvalue = "<commandback code=\"0000000000\" error=\"1\" errormessage=\"AFN错误\"/>";
				break;
		}
		
		return xmlrtvalue;
	}
	
	/**
	 * 解析AFN=09类的报文内容
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param afn
	 *            报文的AFN字段
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String Parse09(byte[] rcvBuffer, String afn)
	{
		String xmlrtvalue = "";
		
		int dataLen = ((rcvBuffer[2] & 0xff) * 256 + (rcvBuffer[1] & 0xff) - 2) / 4 - 8; // 数据体长度
		int flagLength = 0;
		while (dataLen > 0)
		{
			// DA
			String da = hexToString(rcvBuffer[14 + flagLength])
					+ hexToString(rcvBuffer[15 + flagLength]);
			// DT
			String dt = hexToString(rcvBuffer[16 + flagLength])
					+ hexToString(rcvBuffer[17 + flagLength]);
			// 命令代码
			String commandCode = afn + da + dt;
			String reStrInfo = "";
			switch (dt)
			{
			// 读终端版本信息
				case "0100":
					reStrInfo = parseTerminalVersion(rcvBuffer, flagLength);
					flagLength += 41;
					break;
				default:
					break;
			}
			flagLength += 4;
			dataLen -= flagLength;
			xmlrtvalue = "<commandback code=\""
					+ commandCode
					+ "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\""
					+ reStrInfo + "\"/></commandback>";
		}
		
		return xmlrtvalue;
	}
	
	/**
	 * 根据报文的内容， 解析终端版本信息
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String parseTerminalVersion(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		// 厂商代号
		String factoryCode = "";
		int i = 0;
		for (i = 0; i < 4; i++)
		{
			if (isValidASCII(rcvBuffer[18 + len + i]))
			{
				factoryCode += (char) rcvBuffer[18 + len + i];
			}
		}
		// 设备编号
		String devCode = "";
		for (i = 0; i < 4; i++)
		{
			if (isValidASCII(rcvBuffer[22 + len + i]))
			{
				devCode += (char) rcvBuffer[22 + len + i];
			}
		}
		// 终端软件版本号
		String termSoftVer = "";
		for (i = 0; i < 4; i++)
		{
			if (isValidASCII(rcvBuffer[30 + len + i]))
			{
				termSoftVer += (char) rcvBuffer[30 + len + i];
			}
		}
		// 终端软件发布日期:日月年
		String termPublicDate = "20";
		termPublicDate += hexToString(rcvBuffer[36 + len]) + "年";
		termPublicDate += hexToString(rcvBuffer[35 + len]) + "月";
		termPublicDate += hexToString(rcvBuffer[34 + len]) + "日";
		// 终端配置容量信息码
		String vol = "";
		for (i = 0; i < 11; i++)
		{
			if (isValidASCII(rcvBuffer[37 + len + i]))
			{
				vol += (char) rcvBuffer[37 + len + i];
			}
		}
		// 终端通信协议,版本号
		String protocol = "";
		for (i = 0; i < 4; i++)
		{
			if (isValidASCII(rcvBuffer[48 + len + i]))
			{
				protocol += (char) rcvBuffer[48 + len + i];
			}
		}
		// 终端硬件版本号
		String termHardVer = "";
		for (i = 0; i < 4; i++)
		{
			if (isValidASCII(rcvBuffer[52 + len + i]))
			{
				termHardVer += (char) rcvBuffer[52 + len + i];
			}
		}
		// 终端硬件发布日期:日月年
		String termHardPublicDate = "20";
		termHardPublicDate += hexToString(rcvBuffer[58 + len]) + "年";
		termHardPublicDate += hexToString(rcvBuffer[57 + len]) + "月";
		termHardPublicDate += hexToString(rcvBuffer[56 + len]) + "日";
		reStrInfo = "厂商代号:" + factoryCode + ",设备编号:" + devCode + ",终端软件版本号:"
				+ termSoftVer + ",终端软件发布日期:" + termPublicDate + ",终端配置容量信息码:"
				+ vol + ",终端通信协议,版本号:" + protocol + ",终端硬件版本号:" + termHardVer
				+ ",终端硬件发布日期:" + termHardPublicDate;
		return reStrInfo;
	}
	
	/**
	 * 判断某字节是否是有效的ASCII码
	 * 
	 * @param b
	 *            字节
	 * @return true/false
	 */
	private static boolean isValidASCII(byte b)
	{
		if ((b >= 0x21) && (b <= 0x7E))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 解析AFN=0A 类的报文内容
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param afn
	 *            报文的AFN字段
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String Parse0A(byte[] rcvBuffer, String afn)
	{
		String xmlrtvalue = "";
		
		int dataLen = ((rcvBuffer[2] & 0xff) * 256 + (rcvBuffer[1] & 0xff) - 2) / 4 - 8; // 数据体长度
		int len = 0;
		while (dataLen > 0)
		{
			// DA
			String da = hexToString(rcvBuffer[14 + len])
					+ hexToString(rcvBuffer[15 + len]);
			// DT
			String dt = hexToString(rcvBuffer[16 + len])
					+ hexToString(rcvBuffer[17 + len]);
			String commandCode = afn + da + dt;
			String[] str;
			String reStrInfo = "";
			switch (dt)
			{
			// 终端电能表/交流采样装置配置参数
				case "0201":
					reStrInfo = parseMeterPara(rcvBuffer, len);
					str = reStrInfo.split("\\|");
					int num = Integer.valueOf(str[0]);
					len += (num + 1) * 2;
					xmlrtvalue += "<commandback code=\"" + commandCode
							+ "\" error=\"0\" errormessage=\"\">";
					for (int i = 1; i < str.length; i++)
					{
						xmlrtvalue += "<result name=\"\" value=\"" + str[i]
								+ "\"/>";
					}
					xmlrtvalue += "</commandback>";
					break;
				default:
					break;
			}
			len += 4;
			dataLen -= len;
			if (!dt.equals("0201"))
			{
				xmlrtvalue += "<commandback code=\""
						+ commandCode
						+ "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\""
						+ reStrInfo + "\"/></commandback>";
			}
		}
		
		return xmlrtvalue;
	}
	
	/**
	 * 解析终端电能表、交采装置配置数据参数
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseMeterPara(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		
		int meterNum = rcvBuffer[19 + len] * 256 + rcvBuffer[18 + len]; // 本次电能表/交流采样装置配置数量
		
		for (int i = 0; i < meterNum; i++)
		{
			// 电能表/交流采样装置序号
			int meterindex = (rcvBuffer[21 + len] & 0xff) * 256
					+ (rcvBuffer[20 + len] & 0xff);
			// 所属测量点号
			int pointindex = (rcvBuffer[23 + len] & 0xff) * 256
					+ (rcvBuffer[22 + len] & 0xff);
			// 通信速率
			int baudrate = (rcvBuffer[24 + len] & 0xff) / 32;
			switch (baudrate)
			{
				case 1:
					baudrate = 600;
					break;
				case 2:
					baudrate = 1200;
					break;
				case 3:
					baudrate = 2400;
					break;
				case 4:
					baudrate = 4800;
					break;
				case 5:
					baudrate = 7200;
					break;
				case 6:
					baudrate = 9600;
					break;
				case 7:
					baudrate = 19200;
					break;
				case 0:
					baudrate = 0;
					break;
				default:
					break;
			}
			// 通信端口号
			int port = (rcvBuffer[24 + len] & 0xff) % 32;
			// 通信协议类型
			int protocol = rcvBuffer[25 + len] & 0xff;
			// 通信地址
			String meteraddress = "";
			
			for (int j = 0; j < 6; j++)
			{
				meteraddress += hexToString(rcvBuffer[31 - j + len]);
			}
			// 通信密码
			long password = 0;
			for (int k = 0; k < 6; k++)
			{
				// ??? 不确定pow函数是否对应C#中的Pow函数
				long dr = (long) Math.pow(2, 8 * (5 - k));
				password += rcvBuffer[37 - k + len] * dr;
			}
			// 电能费率个数
			int feilv = (rcvBuffer[38 + len] & 0xff);
			// 整数位个数
			int intnum = ((rcvBuffer[39 + len] & 0xff) / 4 + 4);
			// 小数位个数
			int decimalnum = (rcvBuffer[39 + len] & 0xff) % 4 + 1;
			// 采集器地址
			String colladdress = "";
			for (int m = 0; m < 6; m++)
			{
				colladdress += hexToString(rcvBuffer[45 - m + len]);
			}
			// 用户大类号
			int bigkind = (rcvBuffer[46 + len] & 0xff) / 16;
			// 用户小类号
			int smallkind = (rcvBuffer[46 + len] & 0xff) % 16;
			
			len += 27;
			reStrInfo += "电能表/交流采样装置序号:" + meterindex + ",所属测量点号:" + pointindex
					+ ",通信速率:" + baudrate + ",通信端口号:" + port + ",通信协议类型:"
					+ protocol + ",通信地址:" + meteraddress + ",通信密码:" + password
					+ ",电能费率个数:" + feilv + ",整数位个数:" + intnum + ",小数位个数:"
					+ decimalnum + ",采集器地址:" + colladdress + ",用户大类号:"
					+ bigkind + ",用户小类号:" + smallkind + "|";
		}
		len += 2;
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * 解析AFN=0C 类的报文内容，对实时数据的命令的返回信息的解析
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param afn
	 *            报文的AFN字段
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String Parse0C(byte[] rcvBuffer, String afn, String lastpara)
	{
		String xmlrtvalue = "";
		
		int dataLen = ((rcvBuffer[2] & 0xff) * 256 + (rcvBuffer[1] & 0xff) - 2) / 4 - 8; // 数据体长度
		int len = 0;
		while (dataLen > 4)
		{
			// DA
			String da = hexToString(rcvBuffer[14 + len])
					+ hexToString(rcvBuffer[15 + len]);
			// DT
			String dt = hexToString(rcvBuffer[16 + len])
					+ hexToString(rcvBuffer[17 + len]);
			String commandCode = afn + da + dt;
			String[] str, str1;
			// 扩展规约
			if (da.equals("ffff"))
			{
				// 集中器下所有电表当前正向有功电能示值（总、费率1～M） 湖北国网规约扩展
				switch (dt)
				{
					case "0103": // 集中器下所有电表当前三相电流电压等
						str = ParseZBFF(rcvBuffer, len).split("\\|");
						len += Integer.parseInt(str[0]);
						str1 = str[1].split(",");
						if (str1.length >= 27)
						{
							xmlrtvalue += "<commandback code=\"" + commandCode
									+ "\" error=\"0\" errormessage=\"\">";
							for (int i = 0; i < Integer.parseInt(str1[1]); i++)
							{
								xmlrtvalue += "<result AmMeter_Point=\""
										+ str1[2 + +25 * i]
										+ "\" Meter_Address='"
										+ str1[3 + +25 * i] + "' ValueTime=\""
										+ str1[0] + "\"";
								xmlrtvalue += " PowerZY=\"" + str1[4 + 25 * i]
										+ "\" PowerAY=\"" + str1[5 + 25 * i]
										+ "\" PowerBY=\"" + str1[6 + 25 * i]
										+ "\" PowerCY=\"" + str1[7 + 25 * i]
										+ "\"";
								xmlrtvalue += " PowerZW=\"" + str1[8 + 25 * i]
										+ "\" PowerAW=\"" + str1[9 + 25 * i]
										+ "\" PowerBW=\"" + str1[10 + 25 * i]
										+ "\" PowerCW=\"" + str1[11 + 25 * i]
										+ "\"";
								xmlrtvalue += " PowerFactorZ=\""
										+ str1[12 + 25 * i]
										+ "\" PowerFactorA=\""
										+ str1[13 + 25 * i]
										+ "\" PowerFactorB=\""
										+ str1[14 + 25 * i]
										+ "\" PowerFactorC=\""
										+ str1[15 + 25 * i] + "\"";
								xmlrtvalue += " VoltageA=\""
										+ str1[16 + 25 * i] + "\" VoltageB=\""
										+ str1[17 + 25 * i] + "\" VoltageC=\""
										+ str1[18 + 25 * i] + "\"";
								xmlrtvalue += " CurrentA=\""
										+ str1[19 + 25 * i] + "\" CurrentB=\""
										+ str1[20 + 25 * i] + "\" CurrentC=\""
										+ str1[21 + 25 * i] + "\" Current0=\""
										+ str1[22 + 25 * i] + "\"";
								xmlrtvalue += " PowerSZZ=\""
										+ str1[23 + 25 * i] + "\" PowerSZA=\""
										+ str1[24 + 25 * i] + "\" PowerSZB=\""
										+ str1[25 + 25 * i] + "\" PowerSZC=\""
										+ str1[26 + 25 * i] + "\"/>"; // 视在功率
							}
							xmlrtvalue += "</commandback>";
						}
						break;
					case "0164":
						str = parseYGExtend(rcvBuffer, len).split("\\|");
						len += Integer.parseInt(str[0]);
						str1 = str[1].split(",");
						if (str1.length >= 10)
						{
							xmlrtvalue += "<commandback code=\"" + commandCode
									+ "\" error=\"0\" errormessage=\"\">";
							int lastlen = 0;
							for (int i = 0; i < Integer.parseInt(str1[1]); i++)
							{
								xmlrtvalue += "<result AmMeter_Point=\""
										+ str1[2 + lastlen]
										+ "\" Meter_Address='"
										+ str1[3 + lastlen] + "' ValueTime=\""
										+ str1[0] + "\" ZY0=\""
										+ str1[5 + lastlen] + "\" ";
								// 费率可能不相同
								int thisfl = Integer
										.parseInt(str1[4 + lastlen]);
								for (int j = 0; j < thisfl; j++)
								{
									xmlrtvalue += "ZY" + (j + 1) + "=\""
											+ str1[6 + lastlen + j] + "\" ";
								}
								lastlen += 4 + thisfl;
								xmlrtvalue += "/>";
							}
							xmlrtvalue += "</commandback>";
						}
						break;
					default:
						break;
				}
				len += 4;
				dataLen -= len;
				continue;
			}
			// 测量点号
			double intda = hexToPoint(rcvBuffer[14 + len], rcvBuffer[15 + len]);
			switch (dt)
			{
			// 终端日历时钟
				case "0200":
					xmlrtvalue += "<commandback code=\""
							+ commandCode
							+ "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\""
							+ parseTerminalTimeInfo(rcvBuffer, len)
							+ "\"/></commandback>";
					len += 6;
					break;
				// 当前三相及总有/无功功率、功率因数，三相电压、电流、零序电流
				case "0103":
					str = parseTerminalThreeDateInfo(rcvBuffer, len).split(
							"\\|");
					if (str.length >= 24)
					{
						xmlrtvalue += "<commandback code=\""
								+ commandCode
								+ "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\""
								+ intda + "\" ValueTime=\"" + str[0] + "\"";
						xmlrtvalue += " PowerZY=\"" + str[1] + "\" PowerAY=\""
								+ str[2] + "\" PowerBY=\"" + str[3]
								+ "\" PowerCY=\"" + str[4] + "\"";
						xmlrtvalue += " PowerZW=\"" + str[5] + "\" PowerAW=\""
								+ str[6] + "\" PowerBW=\"" + str[7]
								+ "\" PowerCW=\"" + str[8] + "\"";
						xmlrtvalue += " PowerFactorZ=\"" + str[9]
								+ "\" PowerFactorA=\"" + str[10]
								+ "\" PowerFactorB=\"" + str[11]
								+ "\" PowerFactorC=\"" + str[12] + "\"";
						xmlrtvalue += " VoltageA=\"" + str[13]
								+ "\" VoltageB=\"" + str[14] + "\" VoltageC=\""
								+ str[15] + "\"";
						xmlrtvalue += " CurrentA=\"" + str[16]
								+ "\" CurrentB=\"" + str[17] + "\" CurrentC=\""
								+ str[18] + "\" Current0=\"" + str[19] + "\"";
						xmlrtvalue += " PowerSZZ=\"" + str[20]
								+ "\" PowerSZA=\"" + str[21] + "\" PowerSZB=\""
								+ str[22] + "\" PowerSZC=\"" + str[23]
								+ "\"/></commandback>"; // 视在功率
					}
					len += 67;
					break;
				// 当前正向有功电能示值（总、费率1~M）
				case "0110":
					String metertype="";
					if (lastpara != null)
					{
						String[] paraarray = lastpara.split(",");
						// 表计类型
						metertype = paraarray[0];
					}
					str = parseCurrentYGValue(rcvBuffer, len).split("\\|");
					len += Integer.valueOf(str[0]);
					str1 = str[1].split(",");
					if (str1.length >= 3)
					{
						int thisfl = Integer.parseInt(str1[1]);
						xmlrtvalue += "<commandback code=\""
								+ commandCode
								+ "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\""
								+ intda + "\" ValueTime=\"" + str1[0] + "\"";
						xmlrtvalue += " ZY0=\"" + str1[2] + "\" metertype=\""
								+ metertype + "\" ";
						for (int i = 0; i < thisfl; i++)
						{
							xmlrtvalue += "ZY" + (i + 1) + "=\"" + str1[3 + i]
									+ "\" ";
						}
						xmlrtvalue += "/></commandback>";
					}
					break;
				
				default:
					break;
			}
			len += 4;
			dataLen -= len;
			
		}
		
		return xmlrtvalue;
	}
	
	/**
	 * 解析集中器下所有电表当前三相
	 * 
	 * @param rcvBuffer
	 *            收到的报文
	 * @param len
	 *            长度
	 * @return 解析出的XML内容
	 */
	
	private static String ParseZBFF(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		int thislen = 0;
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 22 + len);
		// 本次上传电表个数N
		int meternum = rcvBuffer[23 + len];
		reStrInfo = readtime + "," + meternum + ",";
		thislen = 6;
		int lastMeterLen = 0;
		for (int i = 0; i < meternum; i++)
		{
			// 测量点号pn
			int pointIndex = hexToPoint(rcvBuffer[24 + len + lastMeterLen],
					rcvBuffer[25 + len + lastMeterLen]);
			// 电表通讯地址
			String meterAddress = "";
			int j = 0;
			for (j = 0; j < 6; j++)
			{
				meterAddress += hexToString(rcvBuffer[31 - j + len
						+ lastMeterLen]);
			}
			reStrInfo += pointIndex + "," + meterAddress + ",";
			
			// 功率
			for (j = 0; j < 8; j++)
			{
				double power = getTermianalW(rcvBuffer, 34 + len + j * 3
						+ lastMeterLen);
				reStrInfo += power + ",";
			}
			// 功率因数
			for (j = 0; j < 4; j++)
			{
				double powerfactor = getTermianalWYinSu(rcvBuffer, 57 + len + j
						* 2 + lastMeterLen);
				reStrInfo += powerfactor + ",";
			}
			// 电压
			for (j = 0; j < 3; j++)
			{
				double voltage = 0;
				boolean isvalid = isValidData(rcvBuffer, 65 + len + j * 2
						+ lastMeterLen, 2);
				if (!isvalid)
				{
					voltage = -1;
				} else
				{
					voltage = ((rcvBuffer[65 + len + j * 2 + lastMeterLen] & 0xff) * 100 + (rcvBuffer[64
							+ len + j * 2 + lastMeterLen] & 0xff)) * 0.1;
				}
				reStrInfo += voltage + ",";
			}
			// 电流
			for (j = 0; j < 4; j++)
			{
				double current = getTerminalA(rcvBuffer, 72 + len + j * 3
						+ lastMeterLen);
				reStrInfo += current + ",";
			}
			// 视在功率
			for (j = 0; j < 4; j++)
			{
				double szpower = getTermianalW(rcvBuffer, 84 + len + j * 3
						+ lastMeterLen);
				reStrInfo += szpower + ",";
			}
			lastMeterLen += 70;
			thislen += lastMeterLen;
		}
		
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		len += thislen;
		
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * AFN=0C,DT=0200 的处理函数,解析终端设备的时间信息
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseTerminalTimeInfo(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		// 年
		String year = "20" + hexToString(rcvBuffer[23 + len]);
		// 月
		String month = String.valueOf((rcvBuffer[22 + len] & 0xff) % 32);
		// 日
		String day = hexToString(rcvBuffer[21 + len]);
		// 时
		String hour = hexToString(rcvBuffer[20 + len]);
		// 分
		String minute = hexToString(rcvBuffer[19 + len]);
		// 秒
		String second = hexToString(rcvBuffer[18 + len]);
		// 星期
		String weekday = chinaWeekDay((rcvBuffer[22 + len] & 0xff) / 32);
		
		reStrInfo = year + "-" + month + "-" + day + " " + hour + ":" + minute
				+ ":" + second + " 星期" + weekday;
		return reStrInfo;
	}
	
	private static String chinaWeekDay(int i)
	{
		String chinaWeekDay = "一";
		switch (i)
		{
			case 1:
				chinaWeekDay = "一";
				break;
			case 2:
				chinaWeekDay = "二";
				break;
			case 3:
				chinaWeekDay = "三";
				break;
			case 4:
				chinaWeekDay = "四";
				break;
			case 5:
				chinaWeekDay = "五";
				break;
			case 6:
				chinaWeekDay = "六";
				break;
			case 7:
				chinaWeekDay = "日";
				break;
			default:
				break;
		}
		return chinaWeekDay;
	}
	
	/**
	 * AFN=0C,DT=0103 的处理函数,解析终端设备的时间信息
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseTerminalThreeDateInfo(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		int i = 0;
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 22 + len);
		reStrInfo = readtime + "|";
		
		// 功率
		for (i = 0; i < 8; i++)
		{
			double power = getTermianalW(rcvBuffer, 25 + len + i * 3);
			reStrInfo += power + "|";
		}
		
		// 功率因数
		for (i = 0; i < 4; i++)
		{
			double powerfactor = getTermianalWYinSu(rcvBuffer, 48 + len + i * 2);
			reStrInfo += powerfactor + "|";
		}
		
		// 当前电压
		for (i = 0; i < 3; i++)
		{
			double voltage = 0;
			Boolean isvalid = isValidData(rcvBuffer, 56 + len + i * 2, 2);
			if (!isvalid)
			{
				voltage = -1;
			} else
			{
				voltage = ((rcvBuffer[56 + len + i * 2] & 0xff) * 100 + (rcvBuffer[55
						+ len + i * 2] & 0xff)) * 0.1;
			}
			reStrInfo += voltage + "|";
		}
		// 当前电流
		for (i = 0; i < 4; i++)
		{
			double current = getTerminalA(rcvBuffer, 63 + len + i * 3);
			reStrInfo += current + "|";
		}
		// 视在功率
		for (i = 0; i < 4; i++)
		{
			double szpower = getTermianalW(rcvBuffer, 75 + len + i * 3);
			reStrInfo += szpower + "|";
		}
		
		return reStrInfo;
	}
	
	private static Boolean isValidData(byte[] rcvBuffer, int startindex, int len)
	{
		boolean isvalid = false;
		for (int i = 0; i < len; i++)
		{
			if (((rcvBuffer[startindex - i] & 0xff) != 0xEE)
					&& ((rcvBuffer[startindex - i] & 0xff) != 0xFF))
			{
				isvalid = true;
				break;
			}
		}
		return isvalid;
	}
	
	/**
	 * 获取终端抄表时间
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param startindex
	 *            标示起始位置，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String getTermianalDataTime(byte[] rcvBuffer, int startindex)
	{
		String time = "20" + hexToString(rcvBuffer[startindex]) + "-"
				+ hexToString(rcvBuffer[startindex - 1]) + "-"
				+ hexToString(rcvBuffer[startindex - 2]) + " ";
		time += hexToString(rcvBuffer[startindex - 3]) + ":"
				+ hexToString(rcvBuffer[startindex - 4]) + ":00";
		return time;
	}
	
	/**
	 * 获取各种功率的参数信息
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param startindex
	 *            标示起始位置，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static double getTermianalW(byte[] rcvBuffer, int startindex)
	{
		double power = 1;
		Boolean isvalid = isValidData(rcvBuffer, startindex, 3);
		if (!isvalid)
		{
			power = -1;
		} else
		{
			if ((rcvBuffer[startindex] >> 7 & 0x01) == 1)
			{
				power = -1;
			}
			rcvBuffer[startindex] = (byte) (rcvBuffer[startindex] % 128);
			power *= (rcvBuffer[startindex] & 0xff)
					+ (rcvBuffer[startindex - 1] & 0xff) * 0.01
					+ (rcvBuffer[startindex - 2] & 0xff) * 0.0001;
		}
		return power;
	}
	
	/**
	 * 获取各种功率因素
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param startindex
	 *            标示起始位置，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static double getTermianalWYinSu(byte[] rcvBuffer, int startindex)
	{
		double powerfactor = 1;
		Boolean isvalid = isValidData(rcvBuffer, startindex, 2);
		if (!isvalid)
		{
			powerfactor = -1;
		} else
		{
			if ((rcvBuffer[startindex] >> 7 & 0x01) == 1)
			{
				powerfactor = -1;
			}
			rcvBuffer[startindex] = (byte) ((rcvBuffer[startindex] & 0xff) % 128);
			powerfactor *= ((rcvBuffer[startindex] & 0xff) * 100 + (rcvBuffer[startindex - 1] & 0xff)) * 0.1;
		}
		return powerfactor;
	}
	
	/**
	 * 获取各项电流数据
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param startindex
	 *            标示起始位置，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static double getTerminalA(byte[] rcvBuffer, int startindex)
	{
		double current = 1;
		Boolean isvalid = isValidData(rcvBuffer, startindex, 3);
		if (!isvalid)
		{
			current = -1;
		} else
		{
			if ((rcvBuffer[startindex] >> 7 & 0x01) == 1)
			{
				current = -1;
			}
			rcvBuffer[startindex] = (byte) (rcvBuffer[startindex] % 128);
			current *= ((rcvBuffer[startindex] & 0xff) * 100
					+ (rcvBuffer[startindex - 1] & 0xff) + (rcvBuffer[startindex - 2] & 0xff) * 0.01) * 0.1;
		}
		return current;
	}
	
	/**
	 * AFN=0C,DT=0110 的处理函数,解析当前正向有功电能视值
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseCurrentYGValue(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 22 + len);
		// 费率数
		int feilvnum = rcvBuffer[23 + len] & 0xff;
		// 有功总电能示值
		double ygzval = getYGSumValue(rcvBuffer, 28);
		reStrInfo = readtime + "," + feilvnum + "," + ygzval + ",";
		
		for (int k = 0; k < feilvnum; k++)
		{
			// 费率m有功总电能示值
			double ygval = getYGSumValue(rcvBuffer, 33 + k * 5);
			reStrInfo += ygval + ",";
		}
		
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		len += 11 + 5 * feilvnum;
		
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * 获得正向有功总电能视值
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param startindex
	 *            标示起始位置，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static double getYGSumValue(byte[] rcvBuffer, int startndex)
	{
		// 有功总电能示值
		double ygzvol = -1;
		if (checkValid(rcvBuffer, startndex, 4))
		{
			ygzvol = ((rcvBuffer[startndex] & 0xf0)>>4) * 100000+(rcvBuffer[startndex] & 0x0f) * 10000
					+((rcvBuffer[startndex-1] & 0xf0)>>4) * 1000+(rcvBuffer[startndex-1] & 0x0f) * 100
					+((rcvBuffer[startndex-2] & 0xf0)>>4) * 10+(rcvBuffer[startndex-2] & 0x0f) * 1
					+((rcvBuffer[startndex-3] & 0xf0)>>4) * 0.1+(rcvBuffer[startndex-3] & 0x0f) * 0.01
					+((rcvBuffer[startndex-4] & 0xf0)>>4) *0.001+(rcvBuffer[startndex-4] & 0x0f) *0.0001;
		}
		return ygzvol;
	}
	
	private static boolean checkValid(byte[] rcvBuffer, int startindex, int len)
	{
		boolean ifvalid = false;
		for (int j = 0; j < len; j++)
		{
			if ((rcvBuffer[startindex - j] != (byte)0xEE) && (rcvBuffer[startindex - j] != (byte)0xff))
			{
				ifvalid = true;
				break;
			}
		}
		return ifvalid;
	}
	
	/**
	 * AFN=0C,DA=FFFF,DT=0164的处理函数,解析有功电能视值
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseYGExtend(byte[] rcvBuffer, int len)
	{
		String reStrInfo = "";
		int thislen = 0;
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 22 + len);
		// 本次上传电表个数N
		int meternum = rcvBuffer[23 + len];
		reStrInfo = readtime + "," + meternum + ",";
		thislen = 6;
		int lastmeterlen = 0;
		for (int i = 0; i < meternum; i++)
		{
			// 测量点号pn
			double pointindex = hexToPoint(rcvBuffer[24 + len + lastmeterlen],
					rcvBuffer[25 + len + lastmeterlen]);
			// 电表通讯地址
			String meteraddress = "";
			for (int j = 0; j < 6; j++)
			{
				meteraddress += hexToString(rcvBuffer[31 - j + len
						+ lastmeterlen]);
			}
			// 费率数M
			int feilvnum = rcvBuffer[32 + len + lastmeterlen] & 0xff;
			// 正向有功总电能示值
			double ygzvol = getYGSumValue(rcvBuffer, 37 + len + lastmeterlen);
			reStrInfo += pointindex + "," + meteraddress + "," + feilvnum + ","
					+ ygzvol + ",";
			
			for (int k = 0; k < feilvnum; k++)
			{
				// 费率m有功总电能示值
				double ygvol = getYGSumValue(rcvBuffer, 42 + len + k * 5
						+ lastmeterlen);
				reStrInfo += ygvol + ",";
			}
			lastmeterlen += 14 + feilvnum * 5;
			thislen += lastmeterlen;
		}
		
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		len += thislen;
		
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * 解析AFN=0D 类的报文内容,读历史数据
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param afn
	 *            报文的AFN字段
	 * @return 将字节数组翻译成的可读的字符串，这个字符串是XML形式的
	 */
	private static String Parse0D(byte[] rcvBuffer, String afn)
	{
		
		String xmlrtvalue = "";
		// 数据体长度
		int dataLen = ((rcvBuffer[2] & 0xff) * 256 + (rcvBuffer[1] & 0xff) - 2) / 4 - 8;
		int len = 0;
		while (dataLen > 0)
		{
			// DA
			String da = hexToString(rcvBuffer[14 + len])
					+ hexToString(rcvBuffer[15 + len]);
			// 测量点号
			double intda = hexToPoint(rcvBuffer[14 + len], rcvBuffer[15 + len]);
			// DT
			String dt = hexToString(rcvBuffer[16 + len])
					+ hexToString(rcvBuffer[17 + len]);
			String commandCode = afn + da + dt;
			String[] str, str1;
			switch (dt)
			{
			// 正向有功电能示值（总、费率1~M）日冻结Td-d
				case "0114":
					str = parseHistoryDayZY(rcvBuffer, len).split("\\|");
					len = Integer.parseInt(str[0]);
					str1 = str[1].split(",");
					if (str1.length >= 4)
					{
						int thisfl = Integer.parseInt(str1[2]);
						String jvalue = "", fvalue = "", pvalue = "", gvalue = "";
						// 假定不超过四费率
						if (thisfl >= 4)
						{
							gvalue = str1[7];
						}
						if (thisfl >= 3)
						{
							pvalue = str1[6];
						}
						if (thisfl >= 2)
						{
							fvalue = str1[5];
						}
						if (thisfl >= 1)
						{
							jvalue = str1[4];
						}
						xmlrtvalue += "<commandback code=\""
								+ commandCode
								+ "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\""
								+ intda + "\" Tdd=\"" + str1[0]
								+ "\" ValueTime=\"" + str1[1] + "\"";
						xmlrtvalue += " ZValueZY=\"" + str1[3]
								+ "\" JValueZY=\"" + jvalue + "\" FValueZY=\""
								+ fvalue + "\" PValueZY=\"" + pvalue
								+ "\" GValueZY=\"" + gvalue + "\"/>";
						xmlrtvalue += "</commandback>";
					}
					break;
				// 数据网关扩展读缺失的历史数据
				case "0264":
					str = parseHistoryDayZYFF(rcvBuffer, len).split("\\|"); // 假设只有4个费率
					len = Integer.parseInt(str[0]);
					str1 = str[1].split(",");
					if (str1.length >= 11)
					{
						xmlrtvalue += "<commandback code=\"" + commandCode
								+ "\" error=\"0\" errormessage=\"\">";
						int flindex = 5;
						for (int i = 0; i < Integer.parseInt(str1[2]); i++)
						{
							int thisfl = Integer.parseInt(str1[flindex]);
							String jvalue = "", fvalue = "", pvalue = "", gvalue = "";
							// 假定不超过四费率
							if (thisfl >= 4)
							{
								gvalue = str1[flindex + 5];
							}
							if (thisfl >= 3)
							{
								pvalue = str1[flindex + 4];
							}
							if (thisfl >= 2)
							{
								fvalue = str1[flindex + 3];
							}
							if (thisfl >= 1)
							{
								jvalue = str1[flindex + 2];
							}
							// 假定都是四费率
							xmlrtvalue += "<result AmMeter_Point=\""
									+ str1[flindex - 2] + "\" Tdd=\"" + str1[0]
									+ "\" ValueTime=\"" + str1[1] + "\"";
							xmlrtvalue += " ZValueZY=\"" + str1[flindex + 1]
									+ "\" JValueZY=\"" + jvalue;
							xmlrtvalue += "\" FValueZY=\"" + fvalue
									+ "\" PValueZY=\"" + pvalue;
							xmlrtvalue += "\" GValueZY=\"" + gvalue + "\"/>";
							flindex += thisfl + 4;
						}
						xmlrtvalue += "</commandback>";
					}
					break;
				default:
					break;
			}
			len += 4;
			dataLen -= len;
		}
		
		return xmlrtvalue;
	}
	
	/**
	 * AFN=0D,DT=0114的处理函数,正向有功电能示值（总、费率1~M）日冻结Td-d
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseHistoryDayZY(byte[] rcvBuffer, int len)
	{
		
		String reStrInfo = "";
		
		int i = 0;
		// 日冻结类数据时标td-d
		String tdd = "20";
		for (i = 0; i < 3; i++)
		{
			tdd += hexToString(rcvBuffer[20 + len - i]);
		}
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 25 + len);
		// 费率数
		int feilvnum = rcvBuffer[26 + len];
		// 正向有功总电能示值
		double ygzvol = getYGSumValue(rcvBuffer, 31 + len);
		reStrInfo = tdd + "," + readtime + "," + feilvnum + "," + ygzvol + ",";
		
		for (i = 0; i < feilvnum; i++)
		{
			double ygvol = getYGSumValue(rcvBuffer, 36 + len + i * 5);
			reStrInfo += ygvol + ",";
		}
		
		len = 14 + 5 * feilvnum;
		
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * AFN=0D,DA=FFFF,DT=0264 数据网关扩展读缺失的历史数据
	 * 
	 * @param rcvBuffer
	 * @param len
	 *            标示长度，对该函数起到标示的作用
	 * @return 解析出来的信息
	 */
	private static String parseHistoryDayZYFF(byte[] rcvBuffer, int len)
	{
		
		String reStrInfo = "";
		int thislen = 0;
		int i = 0;
		// 日冻结类数据时标td-d
		String tdd = "20";
		for (i = 0; i < 3; i++)
		{
			tdd += hexToString(rcvBuffer[20 + len - i]);
		}
		// 终端抄表时间
		String readtime = getTermianalDataTime(rcvBuffer, 25 + len);
		// 电表个数N
		int meternum = rcvBuffer[26 + len];
		reStrInfo = tdd + "," + readtime + "," + meternum + ",";
		thislen = 9;
		int lastmeterlen = 0;
		for (int j = 0; j < meternum; j++)
		{
			// 测量点号pn
			double pointindex = hexToPoint(rcvBuffer[27 + len + lastmeterlen],
					rcvBuffer[28 + len + lastmeterlen]);
			// 电表通讯地址
			String meteraddress = "";
			for (int m = 0; m < 6; m++)
			{
				meteraddress += hexToString(rcvBuffer[34 - m + len
						+ lastmeterlen]);
			}
			// 费率数M
			int feilvnum = rcvBuffer[35 + len + lastmeterlen];
			// 正向有功总电能示值
			double ygzvol = getYGSumValue(rcvBuffer, 40 + len + lastmeterlen);
			reStrInfo += pointindex + "," + meteraddress + "," + feilvnum + ","
					+ ygzvol + ",";
			
			for (int k = 0; k < feilvnum; k++)
			{
				// 费率m有功总电能示值
				double ygvol = getYGSumValue(rcvBuffer, 45 + len + k * 5
						+ lastmeterlen);
				reStrInfo += ygvol + ",";
			}
			lastmeterlen += 14 + feilvnum * 5;
			thislen += lastmeterlen;
		}
		
		len += thislen;
		
		if (reStrInfo.length() > 0)
		{
			reStrInfo = reStrInfo.substring(0, reStrInfo.length() - 1);
		}
		reStrInfo = len + "|" + reStrInfo;
		
		return reStrInfo;
	}
	
	/**
	 * 解析AFN=10类的报文内容
	 * 
	 * @param rcvBuffer
	 *            接收的报文字节数组
	 * @param afn
	 *            报文的AFN字段
	 * @param lastpara
	 *            上一条命令的参数
	 * @return 解析出来的报文内容 ，目前还没有实现
	 */
	private static String Parse10(byte[] rcvBuffer, String afn, String lastpara)
	{
		String xmlrtvalue = "";
		
		String da = hexToString(rcvBuffer[14]) + hexToString(rcvBuffer[15]); // DA
		String dt = hexToString(rcvBuffer[16]) + hexToString(rcvBuffer[17]); // DT
		String commandCode = afn + da + dt;
		int translen = (rcvBuffer[20]&0xff)*256+(rcvBuffer[19]&0xff); // 透明转发内容字节数
		int thisstartindex = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat def = new DecimalFormat("0.00");
		
		while (rcvBuffer[21 + thisstartindex] == (byte)0xFE)
		{
			thisstartindex++;
		}
		if (translen > 0)
		{
			String errmessage = "0";
			String[] sendpara = lastpara.split(",");
			if ("wa9010".equals(sendpara[0]))
			{
				if ( "8".equals(sendpara[12]))// 透传188规约
				{
					if ((rcvBuffer[30 + thisstartindex] & 0x40) == (byte)0x00) // 从站正确应答0x81 0x89  0x83
					{
						xmlrtvalue = "<commandback code=\"" + commandCode
								+ "\" error=\"0\" errormessage=\"\">";
						String waterAddress = "";
						for (int k = 0; k < 7; k++)
						{
							waterAddress += hexToString(rcvBuffer[29 - k
									+ thisstartindex]);
						}
						String waterDataId = hexToString(rcvBuffer[32 + thisstartindex])
								+ hexToString(rcvBuffer[33 + thisstartindex]);
						switch (waterDataId)
						{
							case "901F": // 读计量数据
							case "901f":
								double wavalue = (rcvBuffer[38 + thisstartindex] & 0xff)
										* 10000
										+ (rcvBuffer[37 + thisstartindex] & 0xff)
										* 100
										+ (rcvBuffer[36 + thisstartindex] & 0xff)
										+ (rcvBuffer[35 + thisstartindex] & 0xff)
										* 0.01;
								Date now = new Date();
								xmlrtvalue += "<result AmMeter_Point=\"\" Meter_Address=\""
										+ waterAddress
										+ "\" ValueTime=\""
										+ df.format(now)
										+ "\" ZValueZY=\""
										+ def.format(wavalue)
										+ "\"/></commandback>";
								break;
							default:
								break;
						}
					} else
					// 从站异常应答0xC1 0xC9 0xC3
					{
						xmlrtvalue = "<commandback code=\"" + commandCode
								+ "\" error=\"1\" errormessage=''/>";
					}
				}
			}
			else if ( "lamp".equals(sendpara[0].substring(0, 4)))
            {
				DecoderOfLamp decoderOfLamp=new DecoderOfLamp();
                xmlrtvalue = decoderOfLamp.ReceiveParse(rcvBuffer, 21 + thisstartindex, "lamp," + sendpara[9] + "," + sendpara[10] + "," + sendpara[11] + "," + sendpara[12]);
            }
			else if ("classroom".equals(sendpara[0])) //教室控制器
            {
                xmlrtvalue = DecoderOfClassroom.ReceiveParse(rcvBuffer, 21 + thisstartindex, "classroom," + sendpara[9] + "," + sendpara[10] + "," + sendpara[11] + "," + sendpara[12]);
            }
			else if ( "transmit".equals(sendpara[0])) // 直接透传报文
			{
				String transmitframe = "";
				for (int i = 0; i < translen; i++)
				{
					transmitframe += hexToString(rcvBuffer[21 + i]);
				}
				xmlrtvalue = "<commandback code=\""
						+ commandCode
						+ "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\""
						+ transmitframe + "\"/></commandback>";
			} else
			// 645规约
			{
				 xmlrtvalue = Parse10645(rcvBuffer, thisstartindex, commandCode, lastpara);
			}
		} else
		{
			xmlrtvalue = "<commandback code=\"" + commandCode
					+ "\" error=\"1\" errormessage=\"转发字节数为0\"/>";
		}
		
		return xmlrtvalue;
	}
	/**
	 * 解析645规约
	 * @param rcvBuffer
	 * @param thisstartindex
	 * @param commandCode
	 * @param lastpara
	 * @return
	 */
	private static String Parse10645(byte[] rcvbuffer, int thisstartindex,
			String commandcode, String lastpara)
	{
		String xmlrtvalue = "";

		String address = "";
        for (int j = 0; j < 6; j++)
        {
            address += hexToString(rcvbuffer[27 - j + thisstartindex]);
        }

        if ((rcvbuffer[29 + thisstartindex] & 0x10) == 0x10) //07表
        {
            xmlrtvalue = DecoderOfDgn07.Parse1064507(rcvbuffer, thisstartindex, commandcode, lastpara);
        }
        else //97表
        {
            xmlrtvalue = DecoderOfDGN97.Parse1064597(rcvbuffer, thisstartindex, commandcode, address, lastpara);
        }

        return xmlrtvalue;
	}

	//
	/**
	 * 将byte类型转换成十六进制字符串如:0x69->69
	 * 
	 * @param b
	 *            一个待转换的字节
	 * @return 两位的十六进制字符串
	 */
	static String hexToString(byte b)
	{
		String sl = Integer.toHexString(b & 0x0f);
		String sh = Integer.toHexString((b >> 4) & 0x0f);
		String s = sh + sl;
		return s;
		
	}
	
	/**
	 * 通过两个十六进制的字节，计算出测量点号
	 * 
	 * @param b
	 *            第一个字节
	 * @param c
	 *            第二个字节
	 * @return 测量点号
	 */
	// 将16机制转换成为测量点号
	private static int hexToPoint(byte b, byte c)
	{
		
		int da1 = (int) (b & 0xff);
		int da2 = (int) (c & 0xff);
		// 测量点号
		int intda = (int) (Math.log((double) da1) / Math.log((double) 2) + 1 + (da2 - 1) * 8);
		return intda;
	}
	
}
