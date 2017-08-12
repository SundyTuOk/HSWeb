package com.sf.energy.transfer.decode;
/**
 * 对收到的报文进行解析处理国网协议的实现类
 * 
 * @author czy
 * @version 1.0
 * @see [ReceiveGW]
 * @since [盛帆电子/数据中转站1.0]
 */
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sf.energy.transfer.encode.EncoderOfGW;
import com.sf.energy.transfer.decode.*;
import com.sf.energy.util.CreateLog;

public class ReceiveGW extends Receiver
{

	@Override
	public String terminalAddressAssign()
	{
		try
		{
			terminalAddress = hexToString(receiveBuffer[8 + startIndex])
					+ hexToString(receiveBuffer[7 + startIndex]);
			terminalAddress += hexToString(receiveBuffer[10 + startIndex])
					+ hexToString(receiveBuffer[9 + startIndex]);
			terminalAddress += hexToString(receiveBuffer[11 + startIndex]);
		} catch (Exception ex)
		{
			CreateLog.writeLog("ReceiveGW.terminalAddressAssign:"
					+ ex.toString());
		}

		return terminalAddress;
	}

	/**
	 * 讲一个字节转换成对应的十六进制字符串
	 * @param b 一个字节
	 * @return 字节对应的十六进制字符串
	 */
	private String hexToString(byte b)
	{
		String sl = Integer.toHexString(b & 0x0f);
		String sh = Integer.toHexString((b >> 4) & 0x0f);
		String s = sh + sl;
		return s;

	}

	@Override
	public String hexBufferToStr(byte[] databuffer, int index)
	{
		String info = "";
		int thisstartindex = 0;
		// 如果是发送数组,则起始下标为0
		if (index == 1) 
		{
			thisstartindex = 0;
		}
		int framelen = (databuffer[2 + thisstartindex] * 256
				+ databuffer[1 + thisstartindex] - 2) / 4 + 8;
		try
		{
			for (int i = 0; i < framelen + thisstartindex; i++)
			{
				info += hexToString(databuffer[i]) + " ";
			}
		} catch (Exception ex)
		{
			CreateLog.writeLog("ReceiveGW.HexBufferToStr:" + ex.toString());
		}

		return info;
	}

	@Override
	public void receiveBufferAssign(byte[] bufferA)
	{
		try
		{
			int framelen = ((bufferA[2 + startIndex] & 0xff) * 256
					+ (bufferA[1 + startIndex] & 0xff) - 2) / 4 + 8;
			receiveBuffer = new byte[framelen];
			int j = startIndex;
			for (int i = 0; i < framelen; i++)
			{
				receiveBuffer[i] += bufferA[j];
				j++;
			}
		} catch (Exception ex)
		{
			CreateLog
					.writeLog("ReceiveGW.ReceiveBufferAssign:" + ex.toString());
		}
	}

	@Override
	public String parseAutoUp(int commandKind) throws SQLException
	{
		String info = "";
		String afn = hexToString(receiveBuffer[12 + startIndex]);
		String dt = hexToString(receiveBuffer[16 + startIndex])
				+ hexToString(receiveBuffer[17 + startIndex]);
		String da = hexToString(receiveBuffer[14 + startIndex])
				+ hexToString(receiveBuffer[15 + startIndex]);
		String command = afn + da + dt;

		if ((receiveBuffer[6 + startIndex] & 0xC0) == 0xC0)
		{
			switch (afn)
			{
			case "02":
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date time=new Date();
				String date =dateFormat.format(time); 

				String ifCheckin = rd.checkTerminalInfo(terminalAddress);
				switch (dt)
				{
				case "0100":
					info = date + " 数据网关" + terminalAddress + "" + ifCheckin
							+ "登陆!\r\n\r\n";
					break;
				case "0200":
					info = date + " 数据网关" + terminalAddress + "" + ifCheckin
							+ "退出登陆!\r\n\r\n";
					break;
				case "0400":
					info = date + " 数据网关" + terminalAddress + "" + ifCheckin
							+ "的心跳!\r\n\r\n";
					break;
				default:
					break;
				}
				info = afn + "|" + dt + "|" + info;
				break;
			case "0c": // 扩展主动上传
				if ((command.equals("0cffff0164"))
						|| (command.equals("0cffff0103")))
				{
					info = DecoderOfGW.receiveParse(receiveBuffer, "-1",
							"0C0000000000000", terminalAddress, "", "");
					info = afn + "|" + dt + "|" + info;
				}
				break;
			case "0d": 
				if (command.equals("0dffff0264"))
				{
					// 数据中转站自动补抄
					if (commandKind == 1)
					{
						info = DecoderOfGW.receiveParse(receiveBuffer, "-1",
								"0C0000000000000", terminalAddress, "", "");
						info = afn + "|" + dt + "|" + info;
					}
				}
				break;
			case "0e": // 网关现在不会报警所以没有报警报文
				info = DecoderOfGW.receiveParse(receiveBuffer, "-1",
						"0E0000000000000", terminalAddress, "0000000000", "");
				info = afn + "|" + dt + "|" + info;
				break;
			default:
				break;

			}
		}

		return info;
	}

	@Override
	public byte[] ack()
	{
		byte[] sendBuffer = null;
		try
		{
			if (((receiveBuffer[13 + startIndex] & 0x10) == 0x10)
					|| ((receiveBuffer[6 + startIndex] & 0xC0) == 0xC0))// 判断是否需要对该报文进行确认(主动上传得虽然没有在报文中要求确认也给确认把，免得让集中器改程序
			{
				int pseq = receiveBuffer[13 + startIndex] % 16; // 启动帧序号pseq
				String senddt = "0100"; // 默认为全部确认
				byte receiveafn = receiveBuffer[12 + startIndex];
				String receivedadt = "";
				for (int i = 0; i < 4; i++)
				{
					receivedadt += hexToString(receiveBuffer[14 + i
							+ startIndex]);
				}
				if ((receiveafn & 0xff) == 0x02)// 链路接口检测
				{
					senddt = "0400";
				}
				sendBuffer = EncoderOfGW.confirm(terminalAddress + "00", pseq,
						senddt, receiveafn, receivedadt);
			}
		} catch (Exception ex)
		{
			CreateLog.writeLog("ReceiveGW.Ack:" + ex.toString());
		}
		return sendBuffer;
	}

	@Override
	public String commandBack(String opercode, String tasknum,
			String lastcommand, String lastpara)
	{
		String info = "";
		try
		{
			rseq = receiveBuffer[13] & 0x0F; // 取低4位
			info = DecoderOfGW.receiveParse(receiveBuffer, opercode, tasknum,
					terminalAddress, lastcommand, lastpara);
		} catch (Exception ex)
		{
			CreateLog.writeLog("ReceiveGW.CommandBack:" + ex.toString());
		}
		return info;
	}

	@Override
	public Boolean parseSpacialCommand(String lastcommand, String lastpara,
			int pseq, int commandkind, String parsevalue) throws Exception
	{
		boolean ifclear = false;
		String para = lastpara;
		String commandtemp = lastcommand.substring(0, 2)
				+ lastcommand.substring(6, 4);
		if (commandkind == 1)// 自动抄读
		{
			String rt = CVSave(parsevalue, lastcommand, pseq);
		} else
		{
			switch (commandtemp)
			{
			case "010200":// 数据区初始化
			case "010400":// 参数及全体数据区初始化（即恢复至出厂配置）
			case "010800":// 参数（除与系统主站通信有关的）及全体数据区初始化
				ClearTerminalMeters(parsevalue);// 清空数据库中该网关下的表计
				break;
			case "040201":// 终端电能表/交流采样装置配置参数
				String ifautoread = para.substring(0, 1);// 是否主站轮抄,1轮抄,0网关上传
				String metertype = para.substring(2, 1);
				para = para.substring(4);
				// ModifyTerminalAddress(parsevalue, para, metertype,
				// ifautoread);//添加修改数据库中该网关下的表计
				break;
			case "051006":// 删除指定通信端口下的全部电表
				ClearTerminalMeters(parsevalue);// 选择性删除该网关下的表计,因为只有节能系统需要操作自带数据库,而节能系统的端口号都为2,所以可视为全部删除
				break;
			default:
				break;
			}
			ifclear = true;
		}

		return ifclear;
	}

	@Override
	public Boolean clearLastCommand(String lastcommand)
	{
		try
		{
			String thisCommand = hexToString(receiveBuffer[12 + startIndex]);
			for (int i = 0; i < 4; i++)
			{
				thisCommand += hexToString(receiveBuffer[14 + startIndex + i]);
			}
			if ((thisCommand.substring(0, 2).equals("00"))
					|| (thisCommand == lastcommand.substring(0, 10)))
			{
				if (((receiveBuffer[13] & 0x60) == 0x20)
						|| ((receiveBuffer[13] & 0x60) == 0x60))// 结束帧
				{
					return true;
				}
			}
		} catch (Exception ex)
		{
			CreateLog.writeLog("ReceiveGW.ClearLastCommand:" + ex.toString());
		}
		return false;
	}

}
