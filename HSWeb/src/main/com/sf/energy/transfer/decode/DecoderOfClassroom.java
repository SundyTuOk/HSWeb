package com.sf.energy.transfer.decode;

public class DecoderOfClassroom
{
	public static String ReceiveParse(byte[] rcvbuffer, int startindex, String para)
    {
        String xmlrtvalue = "";
        if ((rcvbuffer[startindex + 1] & 0x80) == 0x80) //错误
        {
            xmlrtvalue = ErrParse(rcvbuffer[startindex + 2], para);
        }
        else
        {
            xmlrtvalue = DataParse(rcvbuffer, startindex, para); //响应
        }

        return xmlrtvalue;
    }

	/**
	 * 异常应答解析
	 * @param err
	 * @param para
	 * @return
	 */
    public static String ErrParse(byte err, String para)
    {
        String errinfo = "";
        switch (err)
        {
            case 0x01: errinfo = "非法的功能码"; break;
            case 0x02: errinfo = "非法的数据地址"; break;
            case 0x03: errinfo = "非法的数据值"; break;
            case 0x04: errinfo = "从站设备故障"; break;
            case 0x05: errinfo = "确认"; break;
            case 0x06: errinfo = "从属设备忙"; break;
            case 0x08: errinfo = "存储奇偶性差错"; break;
            case 0x0A: errinfo = "不可用网关路径"; break;
            case 0x0B: errinfo = "网关目标设备响应失败"; break;
            default: break;
        }
        String xmlrtvalue = "<commandback code=\"1000000100," + para + "\" error=\"1\" errormessage=\"" + errinfo + "\"/>";
        return xmlrtvalue;
    }

    /**
     * 有返回数据的解析
     * @param rcvbuffer
     * @param startindex
     * @param para
     * @return
     */
    private static String DataParse(byte[] rcvbuffer, int startindex, String para)
    {
        String deviceaddress = String.valueOf(rcvbuffer[startindex]&0xff);
        String commandno = byteToHexString(rcvbuffer[1 + startindex] & 0xFF);
        
        String xmlrtvalue = "";
        switch (commandno)
        {
            case "03": //读保持寄存器
                int len = rcvbuffer[startindex + 2];
                String[] str = para.split(",");
                String jcqstartaddress = str[3]; //寄存器起始地址
                String jcqnum = str[4];
                String readvalue = deviceaddress + "," + commandno + "," + jcqstartaddress + "," + jcqnum;
                if (("0400".equals(jcqstartaddress)) && ("3".equals(jcqnum)))
                {
                    int rt1 = rcvbuffer[startindex + 3] * 256 + rcvbuffer[startindex + 4];
                    int rt2 = rcvbuffer[startindex + 5] * 256 + rcvbuffer[startindex + 6];
                    int rt3 = rcvbuffer[startindex + 7] * 256 + rcvbuffer[startindex + 8];
                    readvalue += "," + rt1 + "," + rt2 + "," + rt3;
                }
                else
                {
                    int rt = rcvbuffer[startindex + 3] * 256 + rcvbuffer[startindex + 4];
                    readvalue += "," + String.valueOf(rt);
                }
                xmlrtvalue = "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\">";
                xmlrtvalue += "<result name=\"\" value=\"" + readvalue + "\"/></commandback>";
                break;
            case "10": //写多个寄存器
                xmlrtvalue = "<commandback code=\"1000000100," + para + "\" error=\"0\" errormessage=\"\"/>";
                break;
            default:
                xmlrtvalue = "<commandback code=\"1000000100," + para + "\" error=\"1\" errormessage=\"功能码错误\"/>";
                break;
        }

        return xmlrtvalue;
    }
    
    private static String byteToHexString(int i)
	{
		String result = Integer.toHexString(i);
		if(result.length() == 1)
		{
			result = "0" + result;
		}
		return result;	
	}

}
