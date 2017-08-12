package com.sf.energy.server.tcp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

public class TranServerSession
{
	private static TranServerSession serverWebSession;
	///中转站与服务器的会话类
	private TranSession serverSession;
	///存放中转站与服务器的会话类
	private HashMap<String,TranSession> hashMap=new HashMap<String,TranSession>();
	
	public HashMap<String, TranSession> getHashMap()
	{
		return hashMap;
	}
	public void setHashMap(HashMap<String, TranSession> hashMap)
	{
		this.hashMap = hashMap;
	}
	public TranServerSession(){
		
	}
	/**
	 * 类的静态构造方法
	 * @return ServerWebSession
	 */
	public static TranServerSession getServerWebSession(){
		if(serverWebSession==null){
			serverWebSession=new TranServerSession();
		}
		return serverWebSession;
	}
	/**
	 * @throws IOException  
	 * 关闭一个与客户端之间的会话
	 * @param csession
	 * @return 客户端会话类
	 * @exception
	 */
	public  void closeSession(TranSession serverSession) throws IOException{
		if(serverSession!=null){			
			try
			{
				if(hashMap.containsKey(serverSession.getId())){
					hashMap.remove(serverSession.getId());
					//clientCount--;					
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				// TODO: handle exception
			}
			serverSession.close();
		}
	}
	/** 
	 * 中转站连接到服务器，将中转站的相关信息保存到serverSession会话类中
	 * @param ioSession：中转站socket  
	 * @return void
	 * @exception
	 */
	public void firstParse(IoSession ioSession){
		//此session是否存在
		boolean isSessionExit=false;		
		String address=ioSession.getRemoteAddress().toString().substring(1).split(":")[0];	
		//System.out.println("中转站"+address);
		serverSession=new TranSession(ioSession);
		serverSession.setId(serverSession.getIoSession().getRemoteAddress().toString());
		serverSession.setTranAddress(address);
		//System.out.println(serverSession.getTranAddress());
		hashMap.put(address, serverSession);
		//System.out.println("socket添加成功");		
	}
}
