package com.sf.energy.transfer.encode;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.sf.energy.util.CreateLog;

public class EncoderOfDgn07
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
	
	private static byte StrToByte(String str)
    {
        byte rt = 0x00;

        str = padLeft(str, 2);
        int h = AToInt(str.substring(0, 1));
        int l = AToInt(str.substring(1, 2));
        rt = (byte)(h * 16 + l);

        return rt;
    }

    /// <summary>
    /// 主要功能是将字母转化为数字,如E转为14
    /// </summary>
    /// <param name="a"></param>
    /// <returns></returns>
    private static int AToInt(String a)
    {
        int i = 0;

        switch (a)
        {
            case "0": i = 0; break;
            case "1": i = 1; break;
            case "2": i = 2; break;
            case "3": i = 3; break;
            case "4": i = 4; break;
            case "5": i = 5; break;
            case "6": i = 6; break;
            case "7": i = 7; break;
            case "8": i = 8; break;
            case "9": i = 9; break;
            case "A":
            case "a": i = 10; break;
            case "B":
            case "b": i = 11; break;
            case "C":
            case "c": i = 12; break;
            case "D":
            case "d": i = 13; break;
            case "E":
            case "e": i = 14; break;
            case "F":
            case "f": i = 15; break;
            default: break;
        }
        return i;
    }

    /// <summary>
    /// 生成校验码
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="index">校验码的下标</param>
    private static void CheckByte(byte[] sendbuffer, int index,int startindex)
    {
        byte temp = 0x00;

        for (int i = 4 + startindex; i < index; i++)
        {
            temp += sendbuffer[i];
        }
        sendbuffer[index] = temp;
    }

    /// <summary>
    /// 通用命令帧
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="para">参数</param>
    /// <returns></returns>
    public static int GeneralFrame(byte[] sendbuffer, String address, String dataid, String para)
    {
        int len = 0;
        int startindex = 0;
        try
        {
            if (("0000001A".equals(dataid)) || ("0000001C".equals(dataid)))//"0000001B"-->0000001C
            {
                String password = padLeft(para, 8);
                len = MeterOnOff(sendbuffer, address, dataid, password, startindex);
            }
            else
            {
                int seq = Integer.parseInt(para);
                len = ReadData(sendbuffer, address, dataid, seq, startindex);
            }
        }
        catch (Exception ex)
        {
            CreateLog.writeLog("MakeFrameDgn07.GeneralFrame:" + ex.getMessage());
        }
        return len;
    }

    /// <summary>
    /// 读数据
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="seq">帧序列</param>
    /// <returns></returns>
    public static int ReadData(byte[] sendbuffer, String address, String dataid, int seq, int startindex)
    {
        int seqflag = 0;
        if (seq > 0) //读后续数据
        {
            seqflag = 1;
        }

        for (int i = 0; i < startindex; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4+startindex] = 0x68; //帧起始符
        for (int j = 0; j < 6; j++) //地址域
        {
            sendbuffer[10 - j + startindex] = StrToByte(address.substring(j * 2, j * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = (byte)(0x11 + seqflag); //控制码,读数据
        sendbuffer[13 + startindex] = (byte)(0x04 + seqflag); //数据域长度
        for (int k = 0; k < 4; k++) //数据域
        {
            sendbuffer[17 - k + startindex] = (byte)(StrToByte(dataid.substring(k * 2, k * 2+2)) + 0x33);
        }
        CheckByte(sendbuffer, 18 + startindex,startindex); //校验码
        sendbuffer[19 + startindex] = 0x16; //结束符
        return 20;
    }

    /// <summary>
    /// 电表拉合闸
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="password">电表密码</param>
    /// <returns></returns>
    public static int MeterOnOff(byte[] sendbuffer, String address, String dataid, String password, int startindex)
    {
        int i = 0;
        String operno = "01000000"; //人员号
        String time = "990805123000"; //有效截止时间
        //String time = "120101000000"; //有效截止时间

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i+startindex ] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x1C; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x10; //数据域长度
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[14 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[18 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        sendbuffer[22 + startindex] = StrToByte(dataid.substring(6, 8)); //数据标识
        sendbuffer[23 + startindex] = StrToByte(dataid.substring(4, 6));
        for (i = 0; i < 6; i++) //有效截止时间
        {
            sendbuffer[29 - i + startindex] = StrToByte(time.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 30; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 30 + startindex,startindex ); //校验码
        sendbuffer[31+ startindex] = 0x16; //结束符
        return 32;
    }

    /// <summary>
    /// 07表校日期
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="dataid"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int CorrectDate(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;
        String dataid = "04000001";
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码
        sendbuffer[13 + startindex] = 0x10; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //日期
        {
            sendbuffer[29 - i + startindex] = StrToByte(commandpara.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 30; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 30 + startindex,startindex ); //校验码
        sendbuffer[31 + startindex] = 0x16; //结束符
        return 32;
    }

    /// <summary>
    /// 07表校时
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="dataid"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int CorrectTime(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;
        String dataid = "04000102";
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码
        sendbuffer[13 + startindex] = 0x0F; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 3; i++) //时间
        {
            sendbuffer[28 - i + startindex] = StrToByte(commandpara.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 29; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 29 + startindex,startindex ); //校验码
        sendbuffer[30 + startindex] = 0x16; //结束符
        return 31;
    }

    /// <summary>
    /// 07表设置阶梯电价切换时间
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int LadderPriceSet(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;
        String dataid = "04000109";
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码
        sendbuffer[13 + startindex] = 0x11; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 5; i++) //日期
        {
            sendbuffer[30 - i + startindex] = StrToByte(commandpara.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 31; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 31 + startindex, startindex); //校验码
        sendbuffer[32 + startindex] = 0x16; //结束符
        return 33;
    }

    /// <summary>
    /// 07表读取阶梯电价切换时间
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int LadderPriceGet(byte[] sendbuffer, String address, int startindex)
    {
        int i = 0;
        String dataid = "04000109";

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x11; //控制码
        sendbuffer[13 + startindex] = 0x04; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 18; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 18 + startindex, startindex); //校验码
        sendbuffer[19 + startindex] = 0x16; //结束符
        return 20;
    }


    /// <summary>
    /// 电表身份认证
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="meteraddress"></param>
    /// <param name="commandpara"></param>
    /// <param name="meterpass"></param>
    /// <param name="startindex"></param>
    /// <param name="meterprotocol"></param>
    /// <returns></returns>
    public static int MeterIdentify(byte[] sendbuffer, String address, String mw1, String sjs1, int startindex, String pin)
    {
        int i = 0;
        String operno = "02000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x03; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x20; //数据域长度
        sendbuffer[14 + startindex] = (byte) 0xFF;
        sendbuffer[15 + startindex] = 0x00;
        sendbuffer[16 + startindex] = 0x00;
        sendbuffer[17 + startindex] = 0x07;
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[18 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 8; i++) //密文1
        {
            sendbuffer[29 - i + startindex] = StrToByte(mw1.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 8; i++) //随机数1
        {
            sendbuffer[37 - i + startindex] = StrToByte(sjs1.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 8; i++) //分散因子
        {
            sendbuffer[45 - i + startindex] = StrToByte(pin.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 46; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 46 + startindex, startindex); //校验码
        sendbuffer[47 + startindex] = 0x16; //结束符
        return 48;
    }


    /// <summary>
    /// 电表分合闸加密方式
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="meteraddress"></param>
    /// <param name="commandpara"></param>
    /// <param name="meterpass"></param>
    /// <param name="startindex"></param>
    /// <param name="meterprotocol"></param>
    /// <returns></returns>
    public static int MeterControl(byte[] sendbuffer, String address, String miw, String password, int startindex)
    {
        int i = 0;
        String operno = "02000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x1C; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x1C; //数据域长度
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[14 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[18 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 20; i++) //密文
        {
            sendbuffer[41 - i + startindex] = StrToByte(miw.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 42; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 42 + startindex, startindex); //校验码
        sendbuffer[43 + startindex] = 0x16; //结束符
        return 44;
    }

    /// <summary>
    /// 电表预购
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="password">电表密码</param>
    /// <param name="money">充值金额</param>
    /// <param name="times">购电次数</param>
    /// <returns></returns>
    public static int MeterPurchase(byte[] sendbuffer, String address, String dataid, String password, String money, String times, int startindex)
    {
        int i = 0;
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x14; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        double dmoney = Double.parseDouble(money);
        DecimalFormat myFormatter = new DecimalFormat("####.00"); 
        String thismoney = padLeft(myFormatter.format(dmoney), 9);
        thismoney = thismoney.substring(0, 6) + thismoney.substring(7, 9);
        thismoney = longToHexString(Long.parseLong(thismoney));
        for (i = 0; i < 4; i++)
        {
            sendbuffer[26 + i + startindex] = StrToByte(thismoney.substring(i * 2, i * 2+2)); //充值金额
        }
        String thistimes = intToHexString(Integer.parseInt(times));
        for (i = 0; i < 4; i++) //购电次数
        {
            sendbuffer[30 + i + startindex] = StrToByte(thistimes.substring(i * 2, i * 2+2));
        }

        for (i = 14; i < 34; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 34 + startindex, startindex); //校验码
        sendbuffer[35 + startindex] = 0x16; //结束符
        return 36;
    }

    /// <summary>
    /// 电表购电加密方式
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="miw">电量+次数+MAC1+户号+MAC2</param>
    /// <param name="times"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int MeterPurse(byte[] sendbuffer, String address, String miw, String times, int startindex)
    {
        int i = 0;
        String operno = "02000000"; //人员号
        String fileno = "070102FF";
        if (times == "1")
        {
            fileno = "070101FF";
        }

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x03; //控制码,下购电量
        sendbuffer[13 + startindex] = 0x1E; //数据域长度
        for (i = 0; i < 4; i++)
        {
            sendbuffer[17 - i + startindex] = StrToByte(fileno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[18 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        //密文
        String buymoney = miw.substring(0, 8);
        String buytimes = miw.substring(8, 16);
        String mac1 = miw.substring(16, 24);
        String userno = miw.substring(24, 36);
        String mac2 = miw.substring(36);
        for (i = 0; i < 4; i++)
        {
            sendbuffer[25 - i + startindex] = StrToByte(buymoney.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++)
        {
            sendbuffer[29 - i + startindex] = StrToByte(buytimes.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++)
        {
            sendbuffer[33 - i + startindex] = StrToByte(mac1.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 6; i++)
        {
            sendbuffer[39 - i + startindex] = StrToByte(userno.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++)
        {
            sendbuffer[43 - i + startindex] = StrToByte(mac2.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 44; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 44 + startindex, startindex); //校验码
        sendbuffer[45 + startindex] = 0x16; //结束符
        return 46;
    }

    /// <summary>
    /// 开户与销户
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="password">电表密码</param>
    /// <returns></returns>
    public static int AccountOnOff(byte[] sendbuffer, String address, String dataid, String password, String onoff, int startindex)
    {
        int i = 0;
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码
        sendbuffer[13 + startindex] = 0x0D; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        if (onoff == "0") //销户
        {
            sendbuffer[26 + startindex] = 0x00;
        }
        else if (onoff == "1") //开户
        {
            sendbuffer[26 + startindex] = (byte) 0xAA;
        }
        else if (onoff == "2") //非预付费
        {
            sendbuffer[26 + startindex] = (byte) 0xDD;
        }

        for (i = 14; i < 27; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 27 + startindex, startindex); //校验码
        sendbuffer[28 + startindex] = 0x16; //结束符
        return 29;
    }

    /// <summary>
    /// 电表退购上一次
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="dataid">数据标识</param>
    /// <param name="password">电表密码</param>
    /// <returns></returns>
    public static int MeterReturn(byte[] sendbuffer, String address, String dataid, String password, int startindex)
    {
        int i = 0;
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x0D; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        sendbuffer[26 + startindex] = 0x00;
        for (i = 14; i < 27; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 27 + startindex, startindex); //校验码
        sendbuffer[28 + startindex] = 0x16; //结束符
        return 29;
    }

    /// <summary>
    /// 07表设置结算日
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int BalanceSet(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;
        String dataid = "04000B01";//每月第1结算日(也有第二,第三结算日)
        String operno = "01000000"; //人员号

        for (i = 0; i < 4; i++)
        {
            sendbuffer[i + startindex] = (byte) 0xFE;
        }

        sendbuffer[4 + startindex] = 0x68; //帧起始符
        for (i = 0; i < 6; i++) //地址域
        {
            sendbuffer[10 - i + startindex] = StrToByte(address.substring(i * 2, i * 2+2));
        }
        sendbuffer[11 + startindex] = 0x68; //帧起始符
        sendbuffer[12 + startindex] = 0x14; //控制码
        sendbuffer[13 + startindex] = 0x0E; //数据域长度
        for (i = 0; i < 4; i++) //数据标识
        {
            sendbuffer[17 - i + startindex] = StrToByte(dataid.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //密码
        {
            sendbuffer[18 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++) //人员号
        {
            sendbuffer[22 + i + startindex] = StrToByte(operno.substring(i * 2, i * 2+2));
        }
        sendbuffer[26 + startindex] = StrToByte(commandpara.substring(2, 4));
        sendbuffer[27 + startindex] = StrToByte(commandpara.substring(0, 2));
        for (i = 14; i < 28; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 28 + startindex, startindex); //校验码
        sendbuffer[29 + startindex] = 0x16; //结束符
        return 30;
    }
    
    private static String longToHexString(long a)
	{
		String result = Long.toHexString(a);
		for(int i = result.length();i<8;i++)
		{
			result = "0" + result;
		}
		return result;	
	}
	
	private static String intToHexString(int a)
	{
		String result = Integer.toHexString(a);
		for(int i = result.length();i<8;i++)
		{
			result = "0" + result;
		}
		return result;	
	}
}
