package com.sf.energy.server.tcp;

import org.apache.mina.core.session.IoSession;
/**
 * 服务器和Web端的会话类
 * @author Administrator
 *
 */
public class WebSession {
	private String id;
	private IoSession ioSession;
	public WebSession(){
		
	}
	public WebSession(IoSession ioSession){
		this.ioSession=ioSession;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public IoSession getIoSession() {
		return ioSession;
	}
	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}
	
}
