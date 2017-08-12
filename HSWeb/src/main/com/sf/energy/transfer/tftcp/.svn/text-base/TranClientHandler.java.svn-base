package com.sf.energy.transfer.tftcp;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.sf.energy.transfer.tranReciveMessage.TranSerClientHandl;



public class TranClientHandler extends IoHandlerAdapter {
	private static Logger logger = Logger.getLogger(TranClientHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = message.toString();
		logger.info("中转站作为客户端接收到的信息为：" + msg);
		TranSerClientHandl t=new TranSerClientHandl(msg);
		t.ReveiveFromServer();
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("客户端发生异常...", cause);
	}
}
