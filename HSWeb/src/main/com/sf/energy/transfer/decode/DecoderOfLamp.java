package com.sf.energy.transfer.decode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对收到的报文进行解析 按照不同的字段对报文进行解析，然后逐步组成路灯协议的xml格式
 * 
 * @author 崔正阳
 * @version 1.0
 * @since [盛帆电子/数据中转站1.0]
 */
public class DecoderOfLamp
{

	public String ReceiveParse(byte[] rcvbuffer, int startindex, String para)
	{
		String xmlrtvalue = "";
		
		String address = String.valueOf(rcvbuffer[startindex]&0xff);
		String commandcode = hexToString(rcvbuffer[1 + startindex]);
         
		String reStrInfo = address + "," + commandcode;
         switch (commandcode)
         {
             case "41": //read status(数据中转站不停地读)
                 reStrInfo += ParseStatus(rcvbuffer, startindex);
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
             case "57": //read common parameter
                 reStrInfo += ParseCommonParameter(rcvbuffer, startindex);
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
             case "4F": //read light1 parameter
             case "4f": //read light1 parameter
             case "50": //read light2 parameter
                 reStrInfo += ParseLightParameter(rcvbuffer, startindex);
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
             case "53": //change light1 parameter
             case "54": //change light2 parameter
             case "59": //change rs485 address
             case "5A": //change rs485 baudrate
             case "5a": //change rs485 baudrate	 
             case "5B": //change areacode
             case "5b": //change areacode
             case "5D": //change time
             case "5d": //change time
             case "5E"://change light1 mode0 1-6 month parameter
             case "5e"://change light1 mode0 1-6 month parameter
             case "5f"://change light1 mode0 7-12 month parameter
             case "5F"://change light1 mode0 7-12 month parameter
             case "60"://change light2 mode0 1-6 month parameter
             case "61"://change light2 mode0 7-12 month parameter
             case "10": //手动开关
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
             case "62"://read light1 mode0 1-6 month parameter
             case "63"://read light1 mode0 7-12 month parameter
             case "64"://read light2 mode0 1-6 month parameter
             case "65"://read light2 mode0 7-12 month parameter
                 reStrInfo += ParseMode0Parameter(rcvbuffer, startindex);
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
             default: //异常应答
                 xmlrtvalue += "<commandback code=\"1000000100," + para + "\" error=\"1\" errormessage=\"\"><result name=\"\" value=\"" + reStrInfo + "\"/></commandback>";
                 break;
         }
		return null;
	}
	
	
	private String ParseMode0Parameter(byte[] rcvbuffer, int startindex) {
		String reStrInfo = "";
		SimpleDateFormat monthDf=new SimpleDateFormat("MM");
		Date now=new Date();
		String month=monthDf.format(now);
        int thismonth = Integer.parseInt(month) % 6;
        String time = "";
        for (int i = 0; i < 24; i++)
        {
            time += hexToString(rcvbuffer[2 + i + startindex]);
        }
        reStrInfo = "," + time.substring((thismonth - 1) * 8, (thismonth - 1) * 8+8);

        return reStrInfo;
	}


	private String ParseLightParameter(byte[] rcvbuffer, int startindex) {
		String reStrInfo = "";

        String energymode = ""; //work mode
		int operatingmode = 0;
        if ((rcvbuffer[2 + startindex] & 0xF0) == (byte) 0x00)
        {
            energymode = "midnight";
        }
        else if ((rcvbuffer[2 + startindex] & 0x10) == (byte)0x10)
        {
            energymode = "normal";
        }
        else if ((rcvbuffer[2 + startindex] & 0x20) == (byte)0x20)
        {
            energymode = "double";
        }
        operatingmode = rcvbuffer[2 + startindex] & 0x0F;
        String midnightclosetime = hexToString(rcvbuffer[3 + startindex]) + ":" + hexToString(rcvbuffer[4 + startindex]);
        String doubleopentime = hexToString(rcvbuffer[5 + startindex]) + ":" + hexToString(rcvbuffer[6 + startindex]);
        reStrInfo = "," + energymode + "," + operatingmode + "," + midnightclosetime + "," + doubleopentime;

        return reStrInfo;
	}

	private String ParseCommonParameter(byte[] rcvbuffer, int startindex) {
		String reStrInfo = "";

        String areacode = ""; //area code
        for (int i = 0; i < 4; i++)
        {
            areacode += hexToString(rcvbuffer[2 + i + startindex]);
        }
        String thistime = "20" + hexToString(rcvbuffer[6 + startindex])+ "-" + hexToString(rcvbuffer[7 + startindex]) + "-" + hexToString(rcvbuffer[8 + startindex]) + " " + hexToString(rcvbuffer[9 + startindex]) + ":" + hexToString(rcvbuffer[10 + startindex]); //clock
        reStrInfo = "," + areacode + "," + thistime;

        return reStrInfo;
	}

	/**
	 * 解析状态
	 * @param rcvbuffer		字节数组
	 * @param startindex	起始地址
	 * @return
	 */
	private String ParseStatus(byte[] rcvbuffer, int startindex) {
		String reStrInfo = "";

		String ver = ""; //software version
        if (IsValidASCII(rcvbuffer[2 + startindex]))
        {
            ver += (char)rcvbuffer[2 + startindex];
        }
        if (IsValidASCII(rcvbuffer[3 + startindex]))
        {
            ver += (char)rcvbuffer[3 + startindex];
        }
        String state1 = "on", state2 = "on"; //relay state
        if ((rcvbuffer[4 + startindex] & 0x01) == (byte)0x01)
        {
            state1 = "off";
        }
        if ((rcvbuffer[4 + startindex] & 0x02) == (byte)0x02)
        {
            state2 = "off";
        }
        String time1 = "", time2 = ""; //accumutive worked time
        for (int i = 0; i < 4; i++)
        {
            time2 += hexToString(rcvbuffer[8 - i + startindex]);
        }
        for (int j = 0; j < 4; j++)
        {
            time1 += hexToString(rcvbuffer[12 - j + startindex]);
        }
        reStrInfo = "," + ver + "," + state1 + "," + state2 + "," + time1 + "," + time2;
        return reStrInfo;
	}
	/**
	 * 检查是否是合理的ASCII 码
	 * @param thisbyte 单个字节
	 * @return 是返回true 不是返回false
	 */
	private boolean IsValidASCII(byte thisbyte) {
		
		if ((thisbyte >= 0x21) && (thisbyte <= 0x7E))
        {
            return true;
        }
        return false;
	}

	/**
	 * 将byte类型转换成十六进制字符串如:0x69->69
	 * 
	 * @param b
	 *            一个待转换的字节
	 * @return 两位的十六进制字符串
	 */
	 String hexToString(byte b)
	{
		String sl = Integer.toHexString(b & 0x0f);
		String sh = Integer.toHexString((b >> 4) & 0x0f);
		String s = sh + sl;
		return s.toLowerCase();
		
	}

}
