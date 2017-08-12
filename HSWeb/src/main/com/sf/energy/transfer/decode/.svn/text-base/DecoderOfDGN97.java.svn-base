package com.sf.energy.transfer.decode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DecoderOfDGN97
{

	public static String Parse1064597(byte[] rcvbuffer, int thisstartindex,
			String commandcode, String address, String lastpara)
	{
		DecimalFormat dec=new DecimalFormat("#.00");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        String xmlrtvalue = "";

        if ((rcvbuffer[29 + thisstartindex] & 0x40) == 0x00) //从站正确应答(D6=0)
        {
            int datalen = rcvbuffer[30 + thisstartindex];
            for (int i = 0; i < datalen; i++)
            {
                rcvbuffer[31 + i + thisstartindex] = (byte)(rcvbuffer[31 + i + thisstartindex] - 0x33);
            }
            //97表读数据00001,读后续数据00010,重读数据00011.
            if ((rcvbuffer[29 + thisstartindex] & 0x0C) == 0x00)
            {
                String dataid = hexToString(rcvbuffer[32 + thisstartindex]) + hexToString(rcvbuffer[31 + thisstartindex]);
                switch (dataid)
                {
                    case "9010":
                        double wavalue = i16toi10(rcvbuffer[36 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[35 + thisstartindex]) * 100 + i16toi10(rcvbuffer[34 + thisstartindex]) + i16toi10(rcvbuffer[33 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\"\" Meter_Address=\"" + address + "\" ValueTime=\"" + df.format(new Date()) + "\" ZValueZY=\"" + dec.format(wavalue)+ "\"/></commandback>";
                        break;
                    case "C020"://电表运行状态字
                        String meterstate = binaryToString(rcvbuffer[33 + thisstartindex]).substring(4, 6);//照明回路插座回路
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\"\" Meter_Address=\"" + address + "\" ValueTime=\"" + df.format(new Date()) + "\" Flag=\"" + meterstate + "\"/></commandback>";
                        break;
                    case "C0C0"://读恶性负载
                        //Decrypt(rcvbuffer, thisstartindex + 19, thisstartindex);
                        String c0c0flag = hexToString(rcvbuffer[33 + thisstartindex]);//恶性负载标志位
                        int vol = i16toi10(rcvbuffer[35 + thisstartindex]) * 100 + i16toi10(rcvbuffer[34 + thisstartindex]);//恶性负载设置值
                        vol *= 20;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point='' Meter_Address='" + address + "' ValueTime='" + df.format(new Date()) + "'";
                        xmlrtvalue += " Flag='" + c0c0flag + "' Vol='" + vol + "'/></commandback>";
                        break;
                    case "C1C0"://读允许恶性负载范围
                        //Decrypt(rcvbuffer, thisstartindex + 20, thisstartindex);
                        int lowlimit = i16toi10(rcvbuffer[34 + thisstartindex]) * 100 + i16toi10(rcvbuffer[33 + thisstartindex]);//允许恶性负载设置下限值
                        lowlimit *= 20;
                        int uplimit = i16toi10(rcvbuffer[36 + thisstartindex]) * 100 + i16toi10(rcvbuffer[35 + thisstartindex]);//允许恶性负载设置上限值
                        uplimit *= 20;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point='' Meter_Address='" + address + "' ValueTime='" + df.format(new Date()) + "'";
                        xmlrtvalue += " LowLimit='" + lowlimit+ "' UpLimit='" + uplimit + "'/></commandback>";
                        break;
                    case "E501"://读上次购电量
                        Decrypt(rcvbuffer, thisstartindex + 38, thisstartindex + 33);
                        int buytimes = i16toi10(rcvbuffer[34 + thisstartindex]) * 100 + i16toi10(rcvbuffer[33 + thisstartindex]);//购电次数
                        double lastbuyvol = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;//上次购电量
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point='' Meter_Address='" + address + "' ValueTime='" + df.format(new Date()) + "'";
                        xmlrtvalue += " LastBuy='" + dec.format(lastbuyvol) + "' BuyTimes='" + buytimes + "'/></commandback>";
                        break;
                    case "E502"://读当前剩余电量
                        //Decrypt(rcvbuffer, thisstartindex + 33, thisstartindex + 33);
                        double thisvol = i16toi10(rcvbuffer[36 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[35 + thisstartindex]) * 100 + i16toi10(rcvbuffer[34 + thisstartindex]) + i16toi10(rcvbuffer[33 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point=\"\" Meter_Address=\"" + address + "\" ValueTime=\"" + df.format(new Date()) + "\" ZValueZY=\"" + dec.format(thisvol) + "\"/></commandback>";
                        break;
                    case "E50F":
                        double total_usede50e = i16toi10(rcvbuffer[36 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[35 + thisstartindex]) * 100 + i16toi10(rcvbuffer[34 + thisstartindex]) + i16toi10(rcvbuffer[33 + thisstartindex]) * 0.01; //总用电量
                        double total_buye50e = i16toi10(rcvbuffer[40 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[39 + thisstartindex]) * 100 + i16toi10(rcvbuffer[38 + thisstartindex]) + i16toi10(rcvbuffer[37 + thisstartindex]) * 0.01; //总购电量
                        double remaine50e = i16toi10(rcvbuffer[44 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[43 + thisstartindex]) * 100 + i16toi10(rcvbuffer[42 + thisstartindex]) + i16toi10(rcvbuffer[41 + thisstartindex]) * 0.01; //剩余电量
                        double lacke50e = i16toi10(rcvbuffer[48 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[47 + thisstartindex]) * 100 + i16toi10(rcvbuffer[46 + thisstartindex]) + i16toi10(rcvbuffer[45 + thisstartindex]) * 0.01; //负电量
                        double buy_timese50e = i16toi10(rcvbuffer[50 + thisstartindex]) * 100 + i16toi10(rcvbuffer[49 + thisstartindex]); //购电次数
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point='' Meter_Address='" + address + "' ValueTime='" + df.format(new Date()) + "' TotleUsed='" + dec.format(total_usede50e) + "'";
                        xmlrtvalue += " TotleBuy='" + dec.format(total_buye50e) + "' SYValue='" + dec.format(remaine50e) + "' TZValue='" + dec.format(lacke50e) + "' BuyTimes='" + dec.format(buy_timese50e) + "'/></commandback>";
                        break;
                    default: break;
                }
            }
            else
            {
                String writedataid = lastpara.split(",")[0];
                if (writedataid == "E50E")//退购
                {
                    Decrypt(rcvbuffer, thisstartindex + 44, thisstartindex + 31);
                    double total_used = i16toi10(rcvbuffer[34 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[33 + thisstartindex]) * 100 + i16toi10(rcvbuffer[32 + thisstartindex]) + i16toi10(rcvbuffer[31 + thisstartindex]) * 0.01; //累计用量
                    double remain = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01; //剩余电量
                    double lack = i16toi10(rcvbuffer[42 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[41 + thisstartindex]) * 100 + i16toi10(rcvbuffer[40 + thisstartindex]) + i16toi10(rcvbuffer[39 + thisstartindex]) * 0.01; //过零量
                    int buy_times = i16toi10(rcvbuffer[44 + thisstartindex]) * 100 + i16toi10(rcvbuffer[43 + thisstartindex]); //购电次数
                    xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result AmMeter_Point='' Meter_Address='" + address + "' ValueTime='" + df.format(new Date()) + "' TotleUsed='" + dec.format(total_used) + "'";
                    xmlrtvalue += " SYValue='" +dec.format(remain) + "' TZValue='" + dec.format(lack) + "' BuyTimes='" + buy_times + "'/></commandback>";
                }
                else
                {
                    xmlrtvalue = "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"></commandback>";
                }
            }
        }
        else //从站异常应答(D6=1)
        {
            rcvbuffer[31 + thisstartindex] = (byte)(rcvbuffer[31 + thisstartindex] - 0x33);
            String errmessage = DecoderOfDGN97.ErrParse(rcvbuffer[31 + thisstartindex]);
            xmlrtvalue = "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"1\" errormessage=\"" + errmessage + "\"/>";
        }

        return xmlrtvalue;
    }

	private static String ErrParse(byte err)
	{
		String errinfo = "";
        if ((err & 0x40) == 0x40)
        {
            errinfo += "费率数超,";
        }
        if ((err & 0x20) == 0x20)
        {
            errinfo += "日时段数超,";
        }
        if ((err & 0x10) == 0x10)
        {
            errinfo += "年时区数超,";
        }
        if ((err & 0x04) == 0x04)
        {
            errinfo += "密码错,";
        }
        if ((err & 0x02) == 0x02)
        {
            errinfo += "数据标识错,";
        }
        if ((err & 0x01) == 0x01)
        {
            errinfo += "非法数据,";
        }
        if (errinfo.length() > 0)
        {
            errinfo = errinfo.substring(0, errinfo.length() - 1);
        }

        return errinfo;
	}

	private static void Decrypt(byte[] sendbuffer, int endindex, int startindex)
	{
		for (int i = startindex; i <= endindex; i++)
        {
            sendbuffer[i] = (byte)~(sendbuffer[i] - 0xBC);
        }
		
	}

	/**
	 * 字节转十进制
	 * @param b
	 * @return
	 */
	private static int i16toi10(byte b)
	{
		int a=b&0xff;
		return a;
	}

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
		s.toUpperCase();
		return s;
		
	}
	/**
	 * 将byte类型转换成二进制字符串
	 * 
	 * @param b
	 *            一个待转换的字节
	 * @return 8位的二进制字符串
	 */
	static String binaryToString(byte b)
	{
		String aa=Integer.toBinaryString(b&0xff);
		if(aa.length()<8)
		{
			aa=String.format("%08d", Integer.parseInt(aa));
		}
		return aa;
		
	}
}
