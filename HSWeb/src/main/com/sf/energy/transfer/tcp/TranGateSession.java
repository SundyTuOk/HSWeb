package com.sf.energy.transfer.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.metal.OceanTheme;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;



public class TranGateSession
{
	private static TranGateSession tranGateSession;
	private Session session;
	//存放会话类的哈希表
	private static HashMap<String, Session> hashMap=new HashMap<String, Session>();

	public HashMap<String, Session> getHashMap()
	{
		return hashMap;
	}
	
	public void setHashMap(HashMap<String, Session> hashMap)
	{
		this.hashMap = hashMap;
	}

	//当前连接的客户端数
	public static short clientCount=1;
	

	//数据网关的socket
	private IoSession ioSession;
	public TranGateSession(){
		URL	url=ClassLoader.getSystemResource("log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
  public static TranGateSession getTranGateSession(){
	  if(tranGateSession==null){
		  tranGateSession=new TranGateSession();
	  }
	  return tranGateSession;
  }
	
	/**
	 * @throws IOException  
	 * 关闭一个与客户端之间的会话
	 * @param csession
	 * @return 客户端会话类
	 * @exception
	 */
	public  void closeSession(Session session) throws IOException{
		if(session!=null){
			session.setDatagram(null);
			try
			{
				if(hashMap.containsKey(session.getId())){
					hashMap.remove(session.getId());
					//clientCount--;					
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				// TODO: handle exception
			}
			session.close();
		}
	}
	
	/** 
	 * 发送命令给数据网关
	 * @param recvSession：接收命令的客户端    rvcBuffer：发送的内容
	 * @return 发送成功 true 失败false
	 * @exception
	 */
	public boolean sendHex(Session recvSession,byte[] rvcBuffer){
		if((recvSession.getIoSession()!=null)&& (recvSession.getIoSession().isConnected()))
		{
			int len = ((rvcBuffer[2] & 0xff) * 256+ (rvcBuffer[1] & 0xff) - 2) / 4 + 8;
			byte[] sendData=Arrays.copyOf(rvcBuffer, len);
			recvSession.getIoSession().write(IoBuffer.wrap(rvcBuffer));
			return true;
		}		
		return false;
	}
	/** 
	 * 网关连接到中转站，将网关的相关信息保存到session会话类中
	 * @param ioSession：网关socket    rvcBuffer：接收到的内容
	 * @return void
	 * @exception
	 */
	public void firstParse(IoSession ioSession){
		//此session是否存在
		boolean isSessionExit=false;
//		String address=ioSession.getRemoteAddress().toString().substring(1);
//		String ip=address.split(":")[0];
//		Set<Map.Entry<String,Session>> set=hashMap.entrySet();
//		Iterator<Map.Entry<String,Session>> it=set.iterator();
//		while(it.hasNext()){
//			session=it.next().getValue();
//			if(ip.equals(session.getTranAddress())){
//				isSessionExit=true;
//				break;
//			}
//		}
//		if(isSessionExit==false){
//			session=new Session(ioSession);
//			session.setId(session.getIoSession().getRemoteAddress().toString());
//			session.session(ip);
//			//System.out.println(session.getTranAddress());
//			hashMap.put(session.getId(), session);
//			//System.out.println("socket添加成功");
//		}
//		else {
//			return;
//		}	
		 session=new Session(ioSession);
		session.setId(session.getIoSession().getRemoteAddress().toString());
		hashMap.put(session.getId(), session);
		//System.out.println("插入成功"+hashMap.size());
		++clientCount;
		//System.out.println(clientCount);
		
	}
	

}
