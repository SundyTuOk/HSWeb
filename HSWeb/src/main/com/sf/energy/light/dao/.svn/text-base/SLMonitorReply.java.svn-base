package com.sf.energy.light.dao;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


public class SLMonitorReply extends IoHandlerAdapter{
	private static Logger logger = Logger.getLogger(SLMonitorReply.class);
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = message.toString();
		logger.info("接收到的信息为：" + msg);	
		//服务器返回信息的解析和插入
		 ParseLightXml parseLightXml=new ParseLightXml();
		 boolean flag=parseLightXml.updateLightMes(msg);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("客户端发生异常...", cause);
	}
}
