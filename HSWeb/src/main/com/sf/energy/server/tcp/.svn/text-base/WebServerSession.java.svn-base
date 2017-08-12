package com.sf.energy.server.tcp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

public class WebServerSession {
	private static WebServerSession webServerSession;
	private WebSession webSession;
	private HashMap<String,WebSession> hashMap=new HashMap<String,WebSession>();
	public HashMap<String, WebSession> getHashMap() {
		return hashMap;
	}
	public void setHashMap(HashMap<String, WebSession> hashMap) {
		this.hashMap = hashMap;
	}
	/**
	 * 类的静态构造方法
	 * @return ServerWebSession
	 */
	public static WebServerSession getWebServerSession(){
		if(webServerSession==null){
			webServerSession=new WebServerSession();
		}
		return webServerSession;
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
		String address=ioSession.getRemoteAddress().toString();
		//System.out.println("Web"+address);
		WebSession webSession=new WebSession(ioSession);
		//webSession.getIoSession().write("senddd");
		webSession.setId(address);
		hashMap.put(address, webSession);
	}
}
