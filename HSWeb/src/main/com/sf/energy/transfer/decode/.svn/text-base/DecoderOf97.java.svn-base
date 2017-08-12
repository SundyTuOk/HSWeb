package com.sf.energy.transfer.decode;

public class DecoderOf97
{

	public static String ErrParse(byte err)
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

}
