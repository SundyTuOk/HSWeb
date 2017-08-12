package com.sf.energy.server.tftcp;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.sf.energy.server.db.TimerToTask;
import com.sf.energy.server.serverReciveMessage.SSRecvData;
import com.sf.energy.server.tcp.TranServerSession;
import com.sf.energy.server.tcp.TranSession;
import com.sf.energy.server.tcp.WebServerSession;
import com.sf.energy.server.tcp.WebSession;

public class WebHandler extends IoHandlerAdapter{
	
	public static Logger logger = Logger.getLogger(WebHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("服务端与客户端创建连接...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("服务端与客户端连接打开...");
		logger.info("将中转站的socket信息存入哈希表");
		WebServerSession webServerSession=WebServerSession.getWebServerSession();
		webServerSession.firstParse(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = message.toString();
		//session.write("receive");
		logger.info("服务端接收到的数据为：" + msg);
		if(msg.length()<20)
		{
			return;
		}
		else{
			String flag=msg.substring(0,1);
			if(!"<".equals(flag))
			{
				//System.out.println("转发信息到Node.js");
				Tool tool=new Tool();
				tool.broadcast(msg);
			}
			else if("<addPlantask>".endsWith(msg))
			{
				//System.out.println("调用ADDPlanTask");				
				TimerToTask.addPlanTask();
			}
			else{
			SSRecvData ssRecvData=new SSRecvData(msg,session);
			ssRecvData.receiveFromFront();
			}
			
		}
		
		
		
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("web服务端发送信息成功...");
		//System.out.println("服务端发送信息成功...");
		
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
           logger.info("web close success");
           WebServerSession webServerSession=WebServerSession.getWebServerSession();
           HashMap<String,WebSession> hashMap=webServerSession.getHashMap();
           hashMap.remove(session.getRemoteAddress().toString());
           //session.close();
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
