package com.sf.energy.transfer.encode;

import java.text.DecimalFormat;
import java.util.Arrays;

public class EncoderOfDgn97
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
	
	public static int CorrectDate(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x0A; //数据域长度
        sendbuffer[14 + startindex] = 0x10; //数据标识
        sendbuffer[15 + startindex] = (byte) 0xC0;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 4; i++)
        {
            sendbuffer[23 - i + startindex] = StrToByte(commandpara.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 24; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 24 + startindex, startindex); //校验码
        sendbuffer[25 + startindex] = 0x16; //结束符
        return 26;
    }

    /// <summary>
    /// 97表校时
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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x09; //数据域长度
        sendbuffer[14 + startindex] = 0x11; //数据标识
        sendbuffer[15 + startindex] = (byte) 0xC0;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        for (i = 0; i < 3; i++)
        {
            sendbuffer[22 - i + startindex] = StrToByte(commandpara.substring(i * 2, i * 2+2));
        }
        for (i = 14; i < 23; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 23 + startindex, startindex); //校验码
        sendbuffer[24 + startindex] = 0x16; //结束符
        return 25;
    }

    /// <summary>
    /// 电表拉合闸97规约
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address">表地址</param>
    /// <param name="controlcode">C027 C028</param>
    /// <param name="commandpara">数据标识</param>
    /// <param name="password">电表密码</param>
    /// <param name="startindex">数组起始下标</param>
    /// <returns></returns>
    public static int MeterOnOff(byte[] sendbuffer, String address, String controlcode,String commandpara, String password, int startindex)
    {
        int i = 0;

        //if (commandpara == "0") //拉闸
        //{
        //    commandpara = "3355";
        //}
        //else
        //{
        //    commandpara = "9966"; //合闸
        //}

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x08; //数据域长度
        sendbuffer[14 + startindex] = StrToByte(controlcode.substring(2, 4)); //控制码
        sendbuffer[15 + startindex] = StrToByte(controlcode.substring(0, 2));
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        sendbuffer[20 + startindex] = StrToByte(commandpara.substring(2, 4)); //数据标识
        sendbuffer[21 + startindex] = StrToByte(commandpara.substring(0, 2));
        for (i = 14; i < 22; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 22 + startindex, startindex); //校验码
        sendbuffer[23 + startindex] = 0x16; //结束符
        return 24;
    }

    /// <summary>
    /// 生成校验码
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="index">校验码的下标</param>
    private static void CheckByte(byte[] sendbuffer, int index, int startindex)
    {
        byte temp = 0x00;

        for (int i = 4 + startindex; i < index; i++)
        {
            temp += sendbuffer[i];
        }
        sendbuffer[index] = temp;
    }

    /// <summary>
    /// 将字符串原样转化为字节,如"11"转化为0x11 
    /// </summary>
    /// <param name="str">"11"</param>
    /// <returns>0x11</returns>
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
    /// 读命令
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int ReadData(byte[] sendbuffer, String address, int startindex, String dtid)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x01; //控制码,读数据
        sendbuffer[13 + startindex] = 0x02; //数据域长度
        sendbuffer[14 + startindex] = StrToByte(dtid.substring(2, 4)); //数据标识
        sendbuffer[15 + startindex] = StrToByte(dtid.substring(0, 2));
        for (i = 14 + startindex; i < 16 + startindex; i++)
        {
            sendbuffer[i] += 0x33;
        }
        CheckByte(sendbuffer, 16 + startindex, startindex); //校验码
        sendbuffer[17 + startindex] = 0x16; //结束符
        return 18;
    }

    /// <summary>
    /// 加密：对数据的每字节：求反+0BCH  对数据域的其它信息（密码、数据标识）不做该处理。
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="index"></param>
    /// <param name="startindex"></param>
    private static void Encrypt(byte[] sendbuffer, int index, int startindex)
    {
        for (int i = 20 + startindex; i <= index; i++)
        {
            sendbuffer[i] = (byte)(~sendbuffer[i] + 0xBC);
        }
    }

    /// <summary>
    /// 购电
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int Purchase(byte[] sendbuffer, String address, String paratimes,String paravol, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x0C; //数据域长度
        sendbuffer[14 + startindex] = 0x01; //控制码
        sendbuffer[15 + startindex] = (byte) 0xE5;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        paratimes = padLeft(paratimes, 4);
        sendbuffer[20 + startindex] = StrToByte(paratimes.substring(2, 4)); //购电量(购电次数+购电量)
        sendbuffer[21 + startindex] = StrToByte(paratimes.substring(0, 2));
        DecimalFormat myFormatter = new DecimalFormat("####.00"); 
        paravol = myFormatter.format(Double.parseDouble(paravol));
        String intvol = padLeft(paravol.substring(0, paravol.indexOf(".")), 6);
        String digitvol = padLeft(paravol.substring(paravol.indexOf(".") + 1), 2);
        sendbuffer[22 + startindex] = StrToByte(digitvol);
        sendbuffer[23 + startindex] = StrToByte(intvol.substring(4, 6));
        sendbuffer[24 + startindex] = StrToByte(intvol.substring(2, 4));
        sendbuffer[25 + startindex] = StrToByte(intvol.substring(0, 2));
        Encrypt(sendbuffer, 25 + startindex, startindex);//加密
        for (i = 14; i < 26; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 26 + startindex, startindex); //校验码
        sendbuffer[27 + startindex] = 0x16; //结束符
        return 28;
    }

    /// <summary>
    /// 退购
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int ReturnPurchase(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x08; //数据域长度
        sendbuffer[14 + startindex] = 0x0E; //控制码
        sendbuffer[15 + startindex] = (byte) 0xE5;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        commandpara = padLeft(commandpara, 4);
        sendbuffer[20 + startindex] = StrToByte(commandpara.substring(2, 4)); //购电次数
        sendbuffer[21 + startindex] = StrToByte(commandpara.substring(0, 2));
        Encrypt(sendbuffer, 21 + startindex, startindex);//加密
        for (i = 14; i < 22; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 22 + startindex, startindex); //校验码
        sendbuffer[23 + startindex] = 0x16; //结束符
        return 24;
    }

    /// <summary>
    /// 写恶性负载
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int MalignantLoad(byte[] sendbuffer, String address, String c0c0flag, String c0c0vol, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x09; //数据域长度
        sendbuffer[14 + startindex] = (byte) 0xC0; //控制码
        sendbuffer[15 + startindex] = (byte) 0xC0;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        sendbuffer[20 + startindex] = StrToByte(c0c0flag); //恶性负载标志位
        String changvol = Div20(c0c0vol);
        sendbuffer[21 + startindex] = StrToByte(changvol.substring(2, 4));//恶性负载设置值，实际功率为该值乘以20W
        sendbuffer[22 + startindex] = StrToByte(changvol.substring(0, 2));
        for (i = 14; i < 23; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 23 + startindex, startindex); //校验码
        sendbuffer[24 + startindex] = 0x16; //结束符
        return 25;
    }

    private static String Div20(String originalvol)
    {
        String rt = originalvol;
        double dvol = Double.parseDouble(originalvol);
        dvol /= 20;
        int ii = (int)dvol;
        rt = padLeft(String.valueOf(ii), 4);
        return rt;
    }

    /// <summary>
    /// 写恶性负载
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int MalignantAllow(byte[] sendbuffer, String address, String lowerlimit, String upperlimit, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x0A; //数据域长度
        sendbuffer[14 + startindex] = (byte) 0xC0; //控制码
        sendbuffer[15 + startindex] = (byte) 0xC1;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        String changvol = Div20(lowerlimit);
        sendbuffer[20 + startindex] = StrToByte(changvol.substring(2, 4)); //允许恶性负载设置下限值
        sendbuffer[21 + startindex] = StrToByte(changvol.substring(0, 2));
        changvol = Div20(upperlimit);
        sendbuffer[22 + startindex] = StrToByte(changvol.substring(2, 4));
        sendbuffer[23 + startindex] = StrToByte(changvol.substring(0, 2)); //恶性负载设置值，实际功率为该值乘以20W
        for (i = 14; i < 24; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 24 + startindex, startindex); //校验码
        sendbuffer[25 + startindex] = 0x16; //结束符
        return 26;
    }

    /// <summary>
    /// 设置状态字(预付费状态)
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="password"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int SetFlagWord(byte[] sendbuffer, String address, String commandpara, String password, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x07; //数据域长度
        sendbuffer[14 + startindex] = 0x0B; //控制码
        sendbuffer[15 + startindex] = (byte) 0xE5;
        for (i = 0; i < 4; i++) //电表密码
        {
            sendbuffer[16 + i + startindex] = StrToByte(password.substring(i * 2, i * 2+2));
        }
        if (commandpara == "1")
        {
            sendbuffer[20 + startindex] = 0x04; //预付费
        }
        else
        {
            sendbuffer[20 + startindex] = 0x00; //非预付费
        }
        //Encrypt(sendbuffer, 20 + startindex, startindex);//加密
        for (i = 14; i < 21; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 21 + startindex, startindex); //校验码
        sendbuffer[22 + startindex] = 0x16; //结束符
        return 23;
    }

    /// <summary>
    /// 软件数据总清
    /// </summary>
    /// <param name="sendbuffer"></param>
    /// <param name="address"></param>
    /// <param name="commandpara"></param>
    /// <param name="startindex"></param>
    /// <returns></returns>
    public static int AllClear(byte[] sendbuffer, String address, int startindex)
    {
        int i = 0;

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
        sendbuffer[12 + startindex] = 0x04; //控制码,拉合闸
        sendbuffer[13 + startindex] = 0x0A; //数据域长度
        sendbuffer[14 + startindex] = 0x20; //控制码
        sendbuffer[15 + startindex] = (byte) 0xC1;
        sendbuffer[16 + startindex] = 0x00;//电表密码
        sendbuffer[17 + startindex] = 0x11;
        sendbuffer[18 + startindex] = 0x11;
        sendbuffer[19 + startindex] = 0x11;
        for (i = 0; i < 4; i++)
        {
            sendbuffer[20 + i + startindex] = 0x00; //预付费
        }
        for (i = 14; i < 24; i++)
        {
            sendbuffer[i + startindex] += 0x33;
        }
        CheckByte(sendbuffer, 24 + startindex, startindex); //校验码
        sendbuffer[25 + startindex] = 0x16; //结束符
        return 26;
    }

}
