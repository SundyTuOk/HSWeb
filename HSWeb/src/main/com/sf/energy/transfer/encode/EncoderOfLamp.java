package com.sf.energy.transfer.encode;

import java.util.Arrays;

import com.sf.energy.util.CreateLog;

public class EncoderOfLamp
{
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
		if (s.length() >= length)
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

	public static void CRC16(byte[] sendbuffer, int count, int startindex) /*
																			 * 函数以
																			 * unsigned
																			 * short
																			 * 类型返回
																			 * CRC
																			 */
	{
		byte[] auchCRCHi =
		{ 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81,
				0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
				(byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
				(byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01,
				(byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0,
				(byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01,
				(byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81,
				0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0,
				(byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81,
				0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x01,
				(byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1, (byte) 0x81,
				0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x01,
				(byte) 0xC0, (byte) 0x80, 0x41, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x00, (byte) 0xC1, (byte) 0x81, 0x40, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00,
				(byte) 0xC1, (byte) 0x81, 0x40, 0x01, (byte) 0xC0, (byte) 0x80,
				0x41, 0x01, (byte) 0xC0, (byte) 0x80, 0x41, 0x00, (byte) 0xC1,
				(byte) 0x81, 0x40 };
		byte[] auchCRCLo =
		{ 0x00, (byte) 0xC0, (byte) 0xC1, 0x01, (byte) 0xC3, 0x03, 0x02,
				(byte) 0xC2, (byte) 0xC6, 0x06, 0x07, (byte) 0xC7, 0x05,
				(byte) 0xC5, (byte) 0xC4, 0x04, (byte) 0xCC, 0x0C, 0x0D,
				(byte) 0xCD, 0x0F, (byte) 0xCF, (byte) 0xCE, 0x0E, 0x0A,
				(byte) 0xCA, (byte) 0xCB, 0x0B, (byte) 0xC9, 0x09, 0x08,
				(byte) 0xC8, (byte) 0xD8, 0x18, 0x19, (byte) 0xD9, 0x1B,
				(byte) 0xDB, (byte) 0xDA, 0x1A, 0x1E, (byte) 0xDE, (byte) 0xDF,
				0x1F, (byte) 0xDD, 0x1D, 0x1C, (byte) 0xDC, 0x14, (byte) 0xD4,
				(byte) 0xD5, 0x15, (byte) 0xD7, 0x17, 0x16, (byte) 0xD6,
				(byte) 0xD2, 0x12, 0x13, (byte) 0xD3, 0x11, (byte) 0xD1,
				(byte) 0xD0, 0x10, (byte) 0xF0, 0x30, 0x31, (byte) 0xF1, 0x33,
				(byte) 0xF3, (byte) 0xF2, 0x32, 0x36, (byte) 0xF6, (byte) 0xF7,
				0x37, (byte) 0xF5, 0x35, 0x34, (byte) 0xF4, 0x3C, (byte) 0xFC,
				(byte) 0xFD, 0x3D, (byte) 0xFF, 0x3F, 0x3E, (byte) 0xFE,
				(byte) 0xFA, 0x3A, 0x3B, (byte) 0xFB, 0x39, (byte) 0xF9,
				(byte) 0xF8, 0x38, 0x28, (byte) 0xE8, (byte) 0xE9, 0x29,
				(byte) 0xEB, 0x2B, 0x2A, (byte) 0xEA, (byte) 0xEE, 0x2E, 0x2F,
				(byte) 0xEF, 0x2D, (byte) 0xED, (byte) 0xEC, 0x2C, (byte) 0xE4,
				0x24, 0x25, (byte) 0xE5, 0x27, (byte) 0xE7, (byte) 0xE6, 0x26,
				0x22, (byte) 0xE2, (byte) 0xE3, 0x23, (byte) 0xE1, 0x21, 0x20,
				(byte) 0xE0, (byte) 0xA0, 0x60, 0x61, (byte) 0xA1, 0x63,
				(byte) 0xA3, (byte) 0xA2, 0x62, 0x66, (byte) 0xA6, (byte) 0xA7,
				0x67, (byte) 0xA5, 0x65, 0x64, (byte) 0xA4, 0x6C, (byte) 0xAC,
				(byte) 0xAD, 0x6D, (byte) 0xAF, 0x6F, 0x6E, (byte) 0xAE,
				(byte) 0xAA, 0x6A, 0x6B, (byte) 0xAB, 0x69, (byte) 0xA9,
				(byte) 0xA8, 0x68, 0x78, (byte) 0xB8, (byte) 0xB9, 0x79,
				(byte) 0xBB, 0x7B, 0x7A, (byte) 0xBA, (byte) 0xBE, 0x7E, 0x7F,
				(byte) 0xBF, 0x7D, (byte) 0xBD, (byte) 0xBC, 0x7C, (byte) 0xB4,
				0x74, 0x75, (byte) 0xB5, 0x77, (byte) 0xB7, (byte) 0xB6, 0x76,
				0x72, (byte) 0xB2, (byte) 0xB3, 0x73, (byte) 0xB1, 0x71, 0x70,
				(byte) 0xB0, 0x50, (byte) 0x90, (byte) 0x91, 0x51, (byte) 0x93,
				0x53, 0x52, (byte) 0x92, (byte) 0x96, 0x56, 0x57, (byte) 0x97,
				0x55, (byte) 0x95, (byte) 0x94, 0x54, (byte) 0x9C, 0x5C, 0x5D,
				(byte) 0x9D, 0x5F, (byte) 0x9F, (byte) 0x9E, 0x5E, 0x5A,
				(byte) 0x9A, (byte) 0x9B, 0x5B, (byte) 0x99, 0x59, 0x58,
				(byte) 0x98, (byte) 0x88, 0x48, 0x49, (byte) 0x89, 0x4B,
				(byte) 0x8B, (byte) 0x8A, 0x4A, 0x4E, (byte) 0x8E, (byte) 0x8F,
				0x4F, (byte) 0x8D, 0x4D, 0x4C, (byte) 0x8C, 0x44, (byte) 0x84,
				(byte) 0x85, 0x45, (byte) 0x87, 0x47, 0x46, (byte) 0x86,
				(byte) 0x82, 0x42, 0x43, (byte) 0x83, 0x41, (byte) 0x81,
				(byte) 0x80, 0x40 };
		byte uchCRCHi = (byte) 0xFF; /* CRC 的高字节初始化 */
		byte uchCRCLo = (byte) 0xFF; /* CRC 的低字节初始化 */
		int uIndex = 0; /* CRC 查询表索引 */
		int i = startindex - count;
		while (count > 0) /* 完成整个报文缓冲区 */
		{
			count--;
			uIndex = (uchCRCLo ^ sendbuffer[i]) & 0xff; /* 计算 CRC */
			// //System.out.println(uIndex);
			uchCRCLo = (byte) (uchCRCHi ^ auchCRCHi[uIndex]);
			uchCRCHi = auchCRCLo[uIndex];
			i++;
		}
		int crc = ((((int) uchCRCHi) << 8 | (((int) uchCRCLo) & 0xff))) & 0xffff;
		// //System.out.println(crc);
		sendbuffer[startindex] = (byte) (crc & 255);
		sendbuffer[startindex + 1] = (byte) ((crc >> 8) & 0xff);
	}

	public static int GeneralFrame(byte[] sendbuffer, String address,
			String commandcode, String para, int startindex)
	{
		int len = 0;

		sendbuffer[0 + startindex] = (byte) (Integer.parseInt(address));
		sendbuffer[1 + startindex] = strToByte(commandcode);
		switch (commandcode)
		{
		case "41":// read status
		case "57":// read common parameter
		case "4F":// read light1 parameter
		case "50":// read light2 parameter
			CRC16(sendbuffer, 2, 2 + startindex);
			len = 4;
			break;
		case "53":// change light1 parameter
		case "54":// change light2 parameter
			len = ChangeLightParameter(sendbuffer, para, startindex);
			break;
		case "59":// change rs485 address
			len = ChangeAddress(sendbuffer, para, startindex);
			break;
		case "5A":// change rs485 baudrate
			len = ChangeBaud(sendbuffer, para, startindex);
			break;
		case "5B":// change areacode
			len = ChangeAreaCode(sendbuffer, para, startindex);
			break;
		case "5D":// change time
			len = ChangeTime(sendbuffer, para, startindex);
			break;
		case "5E":// change light1 mode0 1-6 month parameter
		case "5F":// change light1 mode0 7-12 month parameter
		case "60":// change light2 mode0 1-6 month parameter
		case "61":// change light2 mode0 7-12 month parameter
			len = ChangeMode0Parameter(sendbuffer, para, startindex);
			break;
		case "62":// read light1 mode0 1-6 month parameter
		case "63":// read light1 mode0 7-12 month parameter
		case "64":// read light2 mode0 1-6 month parameter
		case "65":// read light2 mode0 7-12 month parameter
			CRC16(sendbuffer, 2, 2 + startindex);
			len = 4;
			break;
		case "10": // 写寄存器
			len = OnOff(sendbuffer, para, startindex);
			break;
		default:
			break;
		}
		return len;
	}

	private static int ChangeLightParameter(byte[] sendbuffer, String para,
			int startindex)
	{
		String[] paraarray = para.split(",");
		String energymode = paraarray[0];
		switch (energymode)
		{
		case "midnight":
			energymode = "0";
			break;
		case "normal":
			energymode = "1";
			break;
		case "double":
			energymode = "2";
			break;
		}
		String operatingmode = paraarray[1];
		sendbuffer[2 + startindex] = strToByte(energymode + operatingmode);
		String midnightclosetime = paraarray[2];
		sendbuffer[3 + startindex] = strToByte(midnightclosetime
				.substring(0, 2));
		sendbuffer[4 + startindex] = strToByte(midnightclosetime.substring(2));
		String doubleopentime = paraarray[3];
		sendbuffer[5 + startindex] = strToByte(doubleopentime.substring(0, 2));
		sendbuffer[6 + startindex] = strToByte(doubleopentime.substring(2));
		CRC16(sendbuffer, 7, 7 + startindex);
		return 9;
	}

	private static int ChangeAddress(byte[] sendbuffer, String para,
			int startindex)
	{
		sendbuffer[2 + startindex] = strToByte(para);
		CRC16(sendbuffer, 3, 3 + startindex);
		return 5;
	}

	private static int ChangeBaud(byte[] sendbuffer, String para, int startindex)
	{
		String baud = "";
		switch (para)
		{
		case "1200":
			baud = "0";
			break;
		case "2400":
			baud = "1";
			break;
		case "4800":
			baud = "2";
			break;
		case "9600":
			baud = "3";
			break;
		default:
			break;
		}
		sendbuffer[2 + startindex] = strToByte(padLeft(baud, 2));
		CRC16(sendbuffer, 3, 3 + startindex);
		return 5;
	}

	private static int ChangeAreaCode(byte[] sendbuffer, String para,
			int startindex)
	{
		String areacode = padLeft(para, 4);
		sendbuffer[2 + startindex] = strToByte(areacode.substring(0, 2));
		sendbuffer[3 + startindex] = strToByte(areacode.substring(2));
		CRC16(sendbuffer, 4, 4 + startindex);
		return 6;
	}

	private static int ChangeTime(byte[] sendbuffer, String para, int startindex)
	{
		String time = padLeft(para, 10);
		for (int i = 0; i < 5; i++)
		{
			sendbuffer[2 + i + startindex] = strToByte(time.substring(i * 2,
					i * 2 + 2));
		}
		CRC16(sendbuffer, 7, 7 + startindex);
		return 9;
	}

	private static int ChangeMode0Parameter(byte[] sendbuffer, String para,
			int startindex)
	{
		String ontime = para.substring(0, 4);
		String offtime = para.substring(5);

		String minite1 = ontime.substring(2);
		// minite1 = (int.Parse(minite1) / 5).ToString().PadLeft(2, '0');
		minite1 = byteToHexString((Integer.parseInt(minite1) / 5) & 0xFF);
		ontime = ontime.substring(0, 2) + minite1;

		String minite2 = offtime.substring(2);
		// minite2 = (int.Parse(minite2) / 5).ToString().PadLeft(2, '0');
		minite2 = byteToHexString((Integer.parseInt(minite2) / 5));
		offtime = offtime.substring(0, 2) + minite2;

		String time = ontime + offtime;
		for (int i = 0; i < 6; i++)
		{
			time += time;
		}
		for (int j = 0; j < 24; j++)
		{
			sendbuffer[2 + j + startindex] = strToByte(time.substring(j * 2,
					j * 2 + 2));
		}
		CRC16(sendbuffer, 26, 26 + startindex);
		return 28;
	}

	private static int OnOff(byte[] sendbuffer, String para, int startindex)
	{
		String[] onoffpara = para.split(",");
		for (int i = 0; i < 2; i++)
		{
			if ("on".equals(onoffpara[i])) // 开
			{
				onoffpara[i] = "1";
			} else if ("off".equals(onoffpara[i])) // 关
			{
				onoffpara[i] = "2";
			} else if ("auto".equals(onoffpara[i])) // 自动
			{
				onoffpara[i] = "0";
			}
		}
		sendbuffer[2 + startindex] = 0x00;
		sendbuffer[3 + startindex] = 0x3D;
		sendbuffer[4 + startindex] = 0x00;
		sendbuffer[5 + startindex] = 0x01;
		sendbuffer[6 + startindex] = 0x02;
		sendbuffer[7 + startindex] = (byte) (Integer.parseInt(onoffpara[0]));
		sendbuffer[8 + startindex] = (byte) (Integer.parseInt(onoffpara[1]));
		CRC16(sendbuffer, 9, 9 + startindex);
		return 11;
	}

	private static String byteToHexString(int i)
	{
		String result = Integer.toHexString(i);
		if (result.length() == 1)
		{
			result = "0" + result;
		}
		return result;
	}
}
