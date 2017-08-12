package com.sf.energy.server.tftcp;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.sf.energy.server.serverReciveMessage.SSRecvData;
import com.sf.energy.server.tcp.TranServerSession;
import com.sf.energy.server.tcp.TranSession;




public class ServerHandler extends IoHandlerAdapter{
	
	public static Logger logger = Logger.getLogger(ServerHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("服务端与客户端创建连接...");
		//System.out.println("服务端与客户端创建连接...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("服务端与客户端连接打开...");
		logger.info("将中转站的socket信息存入哈希表");
		TranServerSession serverWebSession=TranServerSession.getServerWebSession();
		serverWebSession.firstParse(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = message.toString();
		logger.info("服务端接收到的数据为：" + msg);
	    session.write("receive的的方法地方");
		if(msg.length()<20)
		{
			return;
		}
		else{
			SSRecvData ssRecvData=new SSRecvData(msg,session);
			ssRecvData.receiveFromFront();
			
		}
		
		
		
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("服务端发送信息成功...");
		//System.out.println("服务端发送信息成功...");
		
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
           logger.info("close success");
           TranServerSession serverWebSession=TranServerSession.getServerWebSession();
   		  HashMap<String,TranSession> hashMap=serverWebSession.getHashMap();  		 
   		  hashMap.remove(session.getRemoteAddress().toString());
   		  session.close();
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("服务端进入空闲状态...");
		//System.out.println("服务端进入空闲状态...");
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("服务端发送异常...", cause);
		//System.out.println("服务端发送异常..."+ cause);
	}
}
