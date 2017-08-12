package com.sf.energy.prepayment.model;

/**
 * 向服务器发送XML命令的实体类
 * 
 * @author yanhao
 * 
 */
public class XMLCoder
{
	// / 规约
	String protocol = "GW";
	// / 操作员代码
	String operCode = "1";
//	//操作员名称
//		String operName="";
//		public String getOperName()
//		{
//			return operName;
//		}
//
//		public void setOperName(String operName)
//		{
//			this.operName = operName;
//		}
		
		//中转站ID
	    String datasiteID="";
		public String getDatasiteID()
		{
			return datasiteID;
		}

		public void setDatasiteID(String datasiteID)
		{
			this.datasiteID = datasiteID;
		}
	// / 任务编号
	String taskNum = "";
	// / 网关地址
	String terminalAddress = "";
	// / 通讯方式
	String way = "2";
	// / 命令码
	String code = "";
	// / 密码
	String pw = "00000000000000000000000000000000";
	// / 是否自动轮抄
	String autoRead = "1";
	// / 命令参数
	String para = "";
	// / 统计该网关下表计数量
	int count = 0;
	
	int meterType=0;
	String tGrosString;
	String dataid = "";//命令标示
	String meterAddress = "";

	public String getMeterAddress()
	{
		return meterAddress;
	}

	public void setMeterAddress(String meterAddress)
	{
		this.meterAddress = meterAddress;
	}

	public String getDataid()
	{
		return dataid;
	}

	public void setDataid(String dataid)
	{
		this.dataid = dataid;
	}

	public String gettGrosString() {
		return tGrosString;
	}

	public void settGrosString(String tGrosString) {
		this.tGrosString = tGrosString;
	}

	String commandCodeAFN;
	String commandCodeDA;
	String commandCodeDT;

	
	
	public int getMeterType()
	{
		return meterType;
	}

	public void setMeterType(int meterType)
	{
		this.meterType = meterType;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getAutoRead()
	{
		return autoRead;
	}

	public void setAutoRead(String autoRead)
	{
		this.autoRead = autoRead;
	}

	public String getCommandCodeAFN()
	{
		return commandCodeAFN;
	}

	public void setCommandCodeAFN(String commandCodeAFN)
	{
		this.commandCodeAFN = commandCodeAFN;
	}

	public String getCommandCodeDA()
	{
		return commandCodeDA;
	}

	public void setCommandCodeDA(String commandCodeDA)
	{
		this.commandCodeDA = commandCodeDA;
	}

	public String getCommandCodeDT()
	{
		return commandCodeDT;
	}

	public void setCommandCodeDT(String commandCodeDT)
	{
		this.commandCodeDT = commandCodeDT;
	}

	public String getOperCode()
	{
		return operCode;
	}

	public void setOperCode(String operCode)
	{
		this.operCode = operCode;
	}

	public String getTaskNum()
	{
		return taskNum;
	}

	public void setTaskNum(String taskNum)
	{
		this.taskNum = taskNum;
	}

	public String getTerminalAddress()
	{
		return terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress)
	{
		this.terminalAddress = terminalAddress;
	}

	public String getWay()
	{
		return way;
	}

	public void setWay(String way)
	{
		this.way = way;
	}

	public String getPw()
	{
		return pw;
	}

	public void setPw(String pw)
	{
		this.pw = pw;
	}

	public String getPara()
	{
		return para;
	}

	public void setPara(String para)
	{
		this.para = para;
	}

}
