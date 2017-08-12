package com.sf.energy.transfer.tftcp;

import java.awt.TextArea;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JTextArea;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
import com.sf.energy.transfer.tranReciveMessage.TranGataData;
/**
 * 中转站对于事件的处理类
 * @author 蒯越
 * @version 1.0
 * 
 */
public class TranServerHandler extends IoHandlerAdapter{
	public static Logger logger = Logger.getLogger(TranServerHandler.class);
	
	/**
	 * 当数据网关连接到中转站后的处理函数
	 * @param 网关的Socket
	 * @return
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		URL	url=ClassLoader.getSystemResource("log4j.properties");
		PropertyConfigurator.configure(url);
		logger.info("服务端与客户端创建连接...");
	}
	/**
	 * 当连接打开后的处理函数
	 * @param 网关的Socket
	 * @return
	 */

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("服务端与客户端连接打开,将网关信息存入hashmap中");
	     TranGateSession tranGateSession=TranGateSession.getTranGateSession();
	     tranGateSession.firstParse(session);
	}
	/**
	 * 服务器接收到信息的处理函数
	 * @param 网关的Socket，中转站接收到数据网关的信息
	 * @return
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		IoBuffer ioBuffer=(IoBuffer)message;
		byte[] bytes=ioBuffer.array();
		int len = ((bytes[2] & 0xff) * 256+ (bytes[1] & 0xff) - 2) / 4 + 8;
		byte[] recvData=Arrays.copyOf(bytes, len);
		if(len<20)
		{
			//System.out.println("报文长度过小");
			return;
		}
		logger.info("中转站作为服务器收到的报文为");
		for(int i=0;i<recvData.length;i++){
			System.out.print(Integer.toHexString(recvData[i]&0xff) + " ");
		}
		TranGataData s=new TranGataData(recvData,session);
		s.sRecvData();		
	}

	/**
	 * 服务器发送完信息的处理函数
	 * @param 网关的Socket，中转站接收到数据网关的信息
	 * @return
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		//session.close();
		logger.info("服务端发送信息成功...");
	}

	/**
	 * 服务器关闭某个网关后的处理函数
	 * @param 网关的Socket
	 * @return
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("服务端关闭成功...");
		TranGateSession tranGateSession=TranGateSession.getTranGateSession();
		HashMap<String,Session> hashMap=tranGateSession.getHashMap();
		hashMap.remove(session.getRemoteAddress().toString());
		session.close();
		//session.close(true);
	}

	/**
	 * 服务器空闲时的处理函数
	 * @param 网关的Socket，空闲状态
	 * @return
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("服务端进入空闲状态...");
		session.close(true);
	}
	/**
	 * 服务器发生异常的处理函数
	 * @param 网关的Socket，异常原因
	 * @return
	 */

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("服务端发送异常...", cause);
	}
}
