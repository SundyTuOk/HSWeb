package com.sf.energy.server.tcp;

import org.apache.mina.core.session.IoSession;

public class TranSession
{
	private String id;
	///中转站名称
	private String tranName;
	///中转站和web之间传输的xml报文
	private String xml;
	///中转站的socket
	private IoSession ioSession;
	///数据中转站的地址
	private String tranAddress;
	public TranSession()
	{
		 
		// TODO Auto-generated constructor stub
	}
    public TranSession(IoSession ioSession){
    	if(ioSession!=null){
    		this.ioSession=ioSession;
    	}
    }
    public void close(){
    	this.ioSession.close();
    }

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTranName()
	{
		return tranName;
	}
	public void setTranName(String tranName)
	{
		this.tranName = tranName;
	}
	public String getXml()
	{
		return xml;
	}
	public void setXml(String xml)
	{
		this.xml = xml;
	}
	public IoSession getIoSession()
	{
		return ioSession;
	}
	public void setIoSession(IoSession ioSession)
	{
		this.ioSession = ioSession;
	}
	public String getTranAddress()
	{
		return tranAddress;
	}
	public void setTranAddress(String tranAddress)
	{
		this.tranAddress = tranAddress;
	}
    
}
