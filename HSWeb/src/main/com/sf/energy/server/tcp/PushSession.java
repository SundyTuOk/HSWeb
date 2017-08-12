package com.sf.energy.server.tcp;

import java.util.HashMap;

import org.apache.mina.core.session.IoSession;

public class PushSession {
	private static PushSession pushSession;
	private HashMap<String,IoSession> hashMap=new HashMap<String,IoSession>();
	public HashMap<String, IoSession> getHashMap() {
		return hashMap;
	}
	public void setHashMap(HashMap<String, IoSession> hashMap) {
		this.hashMap = hashMap;
	}
	
	public PushSession(){
		
	}
	/**
	 * 类的静态构造方法
	 * @return ServerWebSession
	 */
	public static PushSession getPushSession(){
		if(pushSession==null){
			pushSession=new PushSession();
		}
		return pushSession;
	}
	
	/** 
	 * 中转站连接到服务器，将中转站的相关信息保存到serverSession会话类中
	 * @param ioSession：中转站socket  
	 * @return void
	 * @exception
	 */
	public void firstParse(IoSession ioSession){
		//此session是否存在	
		String address=ioSession.getRemoteAddress().toString();			
		hashMap.put(address, ioSession);
		//System.out.println("socket添加成功");		
	}
}
