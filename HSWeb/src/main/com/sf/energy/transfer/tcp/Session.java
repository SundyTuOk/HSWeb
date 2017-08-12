package com.sf.energy.transfer.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

import org.apache.mina.core.session.IoSession;





/// <summary> 
/// 客户端与服务器之间的会话类 
/// 此类为以克隆类——浅克隆
/// 说明: 
/// 会话类包含远程通讯端的状态,这些状态包括Socket,报文内容, 
/// 客户端退出的类型(正常关闭,强制退出两种类型) 
/// </summary> 
public final class Session implements Cloneable{
	 /// <summary> 
  /// 会话ID 
  /// </summary> 
  private String  id;
  /// <summary> 
  /// 客户端发送到服务器的报文 
  /// 注意:在有些情况下报文可能只是报文的片断而不完整 
  /// </summary> 
  private String datagram;
  /// <summary> 
  /// 16进制字节类型的报文,对于数据网关传过来的数据,因为后面处理都是16进制,所以此处就以16进制存储
  /// </summary> 
  private byte[] datagramhex;

  /// 接收缓冲区起始存储下标,如果有半截报文,则下次存储从其后开始
  /// </summary>
  private int startIndex;

	/// <summary> 
  /// 客户端的Socket 
  /// </summary> 
  private IoSession ioSession;
  
/// <summary>
  /// 数据网关地址
  /// </summary>
  private String dtgateaddress;

  
	  /// <summary>
    /// 数据网关最近通讯时间
    /// </summary>
    private Date dtgateconnecttime=new Date(100,0,0);
    
	  /// <summary>
    /// 是否无缺失的数据
    /// </summary>
    private boolean isNoMissData = true;
    
/// <summary>
  /// 最近一次任务编号
  /// </summary>
  private String tasknum;

	/// <summary>
  /// 最近一次任务的表号
  /// </summary>
  private String meterAddress;

	/// <summary>
  /// 最近一次操作员代码
  /// </summary>
  private String operCode;

	private String lastCommand;

/// <summary>
  /// 上一轮自动抄读线程是否结束
  /// </summary>
  private boolean isAutoReadOver = true;

    /// <summary>
    /// 本次抄读电表时间,用于自动抄读,统一的起始抄读时间方便查询
    /// </summary>
    private Date ThisAmReadTime=new Date(100,0,0);

	/// <summary>
    /// 本次抄读水表时间,用于自动抄读,统一的起始抄读时间方便查询
    /// </summary>
    private Date thisWaReadTime = new Date(100,0,0);

	private Date thisLampReadTime =new Date(100,0,0);


	private Date thisClassroomReadTime =new Date(100,0,0);

	private Date thisLonReadTime =new Date(100,0,0);
	
   


/// <summary>
  /// 规约,通用湖北规约为0,国网规约为1,多功能表规约为2
  /// </summary>
  private String gy;
  
	
	

	 /// <summary>
  /// 上一次命令参数
  /// </summary>
  private String lastPara;
  
	

	  /// <summary>
  /// 保存数据中转站自动轮抄时的帧序列的pseq,以备收到回复报文时核对,因为有些命令回复否认时无法解析出命令代码进行核对
  /// </summary>
  private int pseq = 0;
  
	

	  /// <summary>
  /// 命令类型：1：轮抄；2：客户端命令;3:主动上传
  /// 中转站接到命令后把轮抄标志关掉，但最后一个轮抄回复需此命令类型标志解析
  /// </summary>
  private int commandKind = 1;
  
	

    /// <summary>
    /// 读缺失数据的上一次时标，用来在发了命令后网关没有回复时超时计数
    /// </summary>
    private Date lastReadMissDate = new Date(100,0,0);	
	
  
	
    
	

	/// <summary> 
   /// 构造函数 
   /// </summary> 
   /// <param name="cliSock">会话使用的Socket连接</param> 
	public Session()
	{
		// TODO Auto-generated constructor stub
	}
   public Session(IoSession session)
   {
       //Debug.Assert(cliSock != null);
       if (session != null)
       {
           this.ioSession=session;
       }
       
   }
   
   public void close() throws IOException{
	   
	   ioSession.close();
   }
   

	
	public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getDatagram() {
	return datagram;
}
public void setDatagram(String datagram) {
	this.datagram = datagram;
}
public byte[] getDatagramhex() {
	return datagramhex;
}
public void setDatagramhex(byte[] datagramhex) {
	this.datagramhex = datagramhex;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public IoSession getIoSession() {
	return ioSession;
}
public void setIoSession(IoSession ioSession) {
	this.ioSession = ioSession;
}
public String getDtgateaddress() {
	return dtgateaddress;
}
public void setDtgateaddress(String dtgateaddress) {
	this.dtgateaddress = dtgateaddress;
}
public Date getDtgateconnecttime() {
	return dtgateconnecttime;
}
public void setDtgateconnecttime(Date dtgateconnecttime) {
	this.dtgateconnecttime = dtgateconnecttime;
}
public boolean isNoMissData() {
	return isNoMissData;
}
public void setNoMissData(boolean isNoMissData) {
	this.isNoMissData = isNoMissData;
}
public String getTasknum() {
	return tasknum;
}
public void setTasknum(String tasknum) {
	this.tasknum = tasknum;
}
public String getMeterAddress() {
	return meterAddress;
}
public void setMeterAddress(String meterAddress) {
	this.meterAddress = meterAddress;
}
public String getOperCode() {
	return operCode;
}
public void setOperCode(String operCode) {
	this.operCode = operCode;
}
public String getLastCommand() {
	return lastCommand;
}
public void setLastCommand(String lastCommand) {
	this.lastCommand = lastCommand;
}
public boolean isAutoReadOver() {
	return isAutoReadOver;
}
public void setAutoReadOver(boolean isAutoReadOver) {
	this.isAutoReadOver = isAutoReadOver;
}
public Date getThisAmReadTime() {
	return ThisAmReadTime;
}
public void setThisAmReadTime(Date thisAmReadTime) {
	ThisAmReadTime = thisAmReadTime;
}
public Date getThisWaReadTime() {
	return thisWaReadTime;
}
public void setThisWaReadTime(Date thisWaReadTime) {
	this.thisWaReadTime = thisWaReadTime;
}
public Date getThisLampReadTime() {
	return thisLampReadTime;
}
public void setThisLampReadTime(Date thisLampReadTime) {
	this.thisLampReadTime = thisLampReadTime;
}
public Date getThisClassroomReadTime() {
	return thisClassroomReadTime;
}
public void setThisClassroomReadTime(Date thisClassroomReadTime) {
	this.thisClassroomReadTime = thisClassroomReadTime;
}
public Date getThisLonReadTime() {
	return thisLonReadTime;
}
public void setThisLonReadTime(Date thisLonReadTime) {
	this.thisLonReadTime = thisLonReadTime;
}
public String getGy() {
	return gy;
}
public void setGy(String gy) {
	this.gy = gy;
}
public String getLastPara() {
	return lastPara;
}
public void setLastPara(String lastPara) {
	this.lastPara = lastPara;
}
public int getPseq() {
	return pseq;
}
public void setPseq(int pseq) {
	this.pseq = pseq;
}
public int getCommandKind() {
	return commandKind;
}
public void setCommandKind(int commandKind) {
	this.commandKind = commandKind;
}
public Date getLastReadMissDate() {
	return lastReadMissDate;
}
public void setLastReadMissDate(Date lastReadMissDate) {
	this.lastReadMissDate = lastReadMissDate;
}
	protected Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
