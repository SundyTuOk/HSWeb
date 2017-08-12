package com.sf.energy.transfer.decode;

import java.text.DecimalFormat;

public class DecoderOfDgn07
{
	
	
	public static String Parse1064507(byte[] rcvbuffer, int thisstartindex,
			String commandcode, String lastpara)
	{
		DecimalFormat dec=new DecimalFormat("#.00");
		DecimalFormat dec2=new DecimalFormat("#.0000");
        String xmlrtvalue = "";

        if ((rcvbuffer[29 + thisstartindex] & 0x40) == 0x00) //从站正确应答(D6=0)
        {
            int datalen = rcvbuffer[30 + thisstartindex];
            for (int i = 0; i < datalen; i++)
            {
                rcvbuffer[31 + i + thisstartindex] = (byte)(rcvbuffer[31 + i + thisstartindex] - 0x33);
            }
            if ((rcvbuffer[29 + thisstartindex] & 0x0C) == 0x00) //读数据
            {
                String dataid =hexToString(rcvbuffer[34 + thisstartindex]) +hexToString(rcvbuffer[33 + thisstartindex]) + hexToString(rcvbuffer[32 + thisstartindex])+ hexToString(rcvbuffer[31 + thisstartindex]);
                switch (dataid)
                {
                    case "04000109": //阶梯电价切换时间
                    case "04000108":
                        String changetime = "20" + hexToString(rcvbuffer[39 + thisstartindex]) + "-" + hexToString(rcvbuffer[38 + thisstartindex]) + "-" + hexToString(rcvbuffer[37 + thisstartindex]) + " " + hexToString(rcvbuffer[36 + thisstartindex]) + ":" + hexToString(rcvbuffer[35 + thisstartindex])+ ":00";
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + changetime + "\"/></commandback>";
                        break;
                    case "00900100": //（当前）剩余电量
                        double syvalue = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(syvalue) + "\"/></commandback>";
                        break;
                    case "00900101": //（当前）透支电量
                        double tzvalue = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(tzvalue) + "\"/></commandback>";
                        break;
                    case "00900200": //当前剩余金额
                        double symoney = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(symoney) + "\"/></commandback>";
                        break;
                    case "00900201": //（当前）透支金额
                        double tzmoney = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(tzmoney) + "\"/></commandback>";
                        break;
                    case "000B0000": //当前结算周期总累计用电量
                        double thiscirclevol = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(thiscirclevol) + "\"/></commandback>";
                        break;
                    case "000B0001": //上一结算周期总累计用电量
                        double lastcirclevol = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(lastcirclevol) + "\"/></commandback>";
                        break;
                    case "03330201": //上次购电后总购电次数
                        int lasttimes = i16toi10(rcvbuffer[36 + thisstartindex]) * 100 + i16toi10(rcvbuffer[35 + thisstartindex]);
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + lasttimes + "\"/></commandback>";
                        break;
                    case "03330501": //上次购电后剩余金额
                        double lastmoney = i16toi10(rcvbuffer[38 + thisstartindex]) * 10000 + i16toi10(rcvbuffer[37 + thisstartindex]) * 100 + i16toi10(rcvbuffer[36 + thisstartindex]) + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.01;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "," + lastpara + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec.format(lastmoney) + "\"/></commandback>";
                        break;
                    case "070000FF": //电表身份认证
                        String sjs2 = "", esam = "";
                        int j = 0;
                        for (j = 0; j < 4; j++)
                        {
                            sjs2 += hexToString(rcvbuffer[38 + thisstartindex - j]);
                        }
                        for (j = 0; j < 8; j++)
                        {
                            esam += hexToString(rcvbuffer[46 + thisstartindex - j]);
                        }
                        String rtstr = sjs2 + "," + esam;
                        xmlrtvalue += "<result name=\"\" value=\"" + rtstr + "\"/>";
                        break;
                    case "04000B01": //读电表结算日
                        String jsday = hexToString(rcvbuffer[36 + thisstartindex]);
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + jsday + "\"/></commandback>";
                        break;
                    case "04000204": //读电表费率数
                    case "04000207": //读电表阶梯数
                        int num = i16toi10(rcvbuffer[35 + thisstartindex]);
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + num + "\"/></commandback>";
                        break;
                    case "04060101": //读电价
                    case "04060102":
                    case "04060103":
                    case "04060104":
                    case "04060301":
                    case "04060302":
                    case "04060303":
                    case "04060304":
                    case "04050101": //第一套费率价格一
                    case "04050102":
                    case "04050103":
                    case "04050104":
                    case "0405013F":
                    case "04050201":
                    case "04050202":
                    case "04050203":
                    case "04050204":
                        double flprice11 = i16toi10(rcvbuffer[38 + thisstartindex]) * 100 + i16toi10(rcvbuffer[37 + thisstartindex]) + i16toi10(rcvbuffer[36 + thisstartindex]) * 0.01 + i16toi10(rcvbuffer[35 + thisstartindex]) * 0.0001;
                        xmlrtvalue += "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"><result name=\"\" value=\"" + dec2.format(flprice11) + "\"/></commandback>";
                        break;
                    default:
                        xmlrtvalue = "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"></commandback>";
                        break;
                }
            }
            else  //写命令无法从报文中获得数据标识
            {
                String[] sendpara = lastpara.split(",");
                switch (sendpara[9])
                {
                    case "openclose"://开户与销户
                    case "recharge"://购电
                    case "returnmoney"://退购上一次购电记录
                        xmlrtvalue = "<commandback code=\"" + commandcode + sendpara[9] + "\" error=\"0\" errormessage=\"\"></commandback>";
                        break;
                    default:
                        xmlrtvalue = "<commandback code=\"" + commandcode + "\" error=\"0\" errormessage=\"\"></commandback>";
                        break;
                }
            }
        }
        else //从站异常应答(D6=1)
        {
            rcvbuffer[31 + thisstartindex] = (byte)(rcvbuffer[31 + thisstartindex] - 0x33);
            String errmessage = DecoderOfDgn07.ErrParse(rcvbuffer[31 + thisstartindex]);
            xmlrtvalue = "<commandback code=\"" + commandcode + "\" error=\"1\" errormessage=\"" + errmessage + "\"/>";
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
            errinfo += "时时段数超,";
        }
        if ((err & 0x10) == 0x10)
        {
            errinfo += "年时区数超,";
        }
        if ((err & 0x08) == 0x08)
        {
            errinfo += "通讯速率不能更改,";
        }
        if ((err & 0x04) == 0x04)
        {
            errinfo += "密码错/未授权,";
        }
        if ((err & 0x02) == 0x02)
        {
            errinfo += "无请求数据,";
        }
        if ((err & 0x01) == 0x01)
        {
            errinfo += "其他错误,";
        }
        if (errinfo.length() > 0)
        {
            errinfo = errinfo.substring(0, errinfo.length() - 1);
        }

        return errinfo;
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

}
