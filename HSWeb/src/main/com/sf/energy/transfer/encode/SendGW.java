package com.sf.energy.transfer.encode;

/**
 * <发送组包实现类> <包括普通命令组包、轮抄功能组包、补抄功能组包>
 * 
 * @author lujinquan
 * @version v1.0
 * @see com.sf.energy.transfer.encode.Sender
 * @since 中转站
 */
public class SendGW implements Sender
{
	/// 读命令或写命令,"R"为读,"W"为写
    private String rW;
    
    /// 数据标识
    private String dataId;
    
    /// 数据网关地址   
    private String terminalAddress;

    /// 数据网关密码   
    private String terminalPassword;
    
    /// 表地址   
    private String meterAddress;
    
    /// 表ID  
    private int meterId;
    
    /// 任务编号   
    private String taskNum;
   
    /// 命令参数
    private String para;
    
    /// 发送缓冲区   
    private byte[] dataBuffer;
    
    /// 发送报文的pseq,便于核对
    private byte pseq = 0;
    
    /// 寄存器起始地址(16进制,教室节能用,Lonworks为10进制,存在MeterId中)    
    private String jcqAddress = "";
    		
	
	@Override
	public byte[] sendCommand(String termminalAddress, String commandCode,
			String para, int pseq)
	{
		String afn = commandCode.substring(0, 2);
		commandCode += para;
		int fir = 1;
		int fin = 1;
//		byte[] sendBuffer = EncoderOfGW.generalFrame(termminalAddress, afn,
//				commandCode, fir, fin, pseq);

		return null;
	}

	@Override
	public String makeCommandCode(int meterID)
	{
		String commandCode = "";
		String da1 = "01";
		String da2 = "01";
        if (meterID % 8 == 0)
        {
            da1 = "80";
            da2 = Integer.toHexString(meterID / 8);
            if(da2.length() == 1)
            {
            	da2 = "0" + da2;
            }
        }
        else
        {
            da1 = Integer.toHexString((int)(Math.pow(2, meterID % 8 - 1)));
            if(da1.length() == 1)
            {
            	da1 = "0" + da1;
            }
        	da2 = Integer.toHexString(meterID / 8 + 1);
        	if(da2.length() == 1)
            {
            	da2 = "0" + da2;
            }
        }
        //信息点DA1DA2
        String pointno = da1 + da2; 
        commandCode = "0C" + pointno + "0110";

		return commandCode;
	}

	@Override
	public byte[] readLost(String terminalAddress, String readDate, int pseq)
	{
		String afn = "0D";
		String commandCode = "0DFFFF0264" + readDate;
		int fir = 1;
		int fin = 1;
		byte[] sendBuffer=null; 
//		= EncoderOfGW.generalFrame(terminalAddress, afn,
//				commandCode, fir, fin, pseq);
		
		return sendBuffer;
	}

	public String getrW()
	{
		return rW;
	}

	public void setrW(String rW)
	{
		this.rW = rW;
	}

	public String getDataId()
	{
		return dataId;
	}

	public void setDataId(String dataId)
	{
		this.dataId = dataId;
	}

	public String getTerminalAddress()
	{
		return terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress)
	{
		this.terminalAddress = terminalAddress;
	}

	public String getTerminalPassword()
	{
		return terminalPassword;
	}

	public void setTerminalPassword(String terminalPassword)
	{
		this.terminalPassword = terminalPassword;
	}

	public String getMeterAddress()
	{
		return meterAddress;
	}

	public void setMeterAddress(String meterAddress)
	{
		this.meterAddress = meterAddress;
	}

	public int getMeterId()
	{
		return meterId;
	}

	public void setMeterId(int meterId)
	{
		this.meterId = meterId;
	}

	public String getTaskNum()
	{
		return taskNum;
	}

	public void setTaskNum(String taskNum)
	{
		this.taskNum = taskNum;
	}

	public String getPara()
	{
		return para;
	}

	public void setPara(String para)
	{
		this.para = para;
	}

	public byte[] getDataBuffer()
	{
		return dataBuffer;
	}

	public void setDataBuffer(byte[] dataBuffer)
	{
		this.dataBuffer = dataBuffer;
	}

	public byte getPseq()
	{
		return pseq;
	}

	public void setPseq(byte pseq)
	{
		this.pseq = pseq;
	}

	public String getJcqAddress()
	{
		return jcqAddress;
	}

	public void setJcqAddress(String jcqAddress)
	{
		this.jcqAddress = jcqAddress;
	}

	@Override
	public String specialCommand(String dataId,String metertype, String autoread,String para)
	{
		// TODO Auto-generated method stub
		 String rt = "";

         if (dataId == "0500001006") //删除指定通信端口下的全部电表
         {
             rt = metertype + "," + para;
         }
         if (dataId == "0400000201") //终端电能表/交流采样装置配置参数
         {
             rt = autoread + "," + metertype + "," + para;
         }
         //如果是阶梯电价切换任务,也要保存其标志,返给服务器结果时作为判断依据
         if ((dataId.length()== 10) && (dataId.substring(0, 2) == "0C"))
         {
             if (dataId.substring(6, 4) == "0803")
             {
                 rt = para;
             }
             else if (dataId.substring(6, 4) == "0110")//抄读F129要保存表计类型用于服务器从数据库中查表计ID
             {
                 rt = metertype + "," + para;
             }
         }
         if (dataId == "1000000100")
         {
             //string[] paraarray = Para.Split(',');
             //{
             //    rt = paraarray[0];
             //    if (rt == "lamp10")
             //    {
                     rt = para;
             //    }
             //}
         }

         return rt;
	}

}
