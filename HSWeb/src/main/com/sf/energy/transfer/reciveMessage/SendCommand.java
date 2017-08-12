package com.sf.energy.transfer.reciveMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;
/**
 * 将服务器的命令发送给数据网关
 * @author 蒯越
 * @version 1.0
 */
public class SendCommand
{
	TranGateSession tranGateSession=TranGateSession.getTranGateSession();
	Session session=new Session();
	
	/**
	 * 将服务器的命令发送给数据网关
	 * @param  sendData:服务器的命令
	 * @return null
	 */
	public void sendData(byte[] sendData){
		
		HashMap<String,Session> hashMap=tranGateSession.getHashMap();
		Set<Map.Entry<String,Session>> set=hashMap.entrySet();
		Iterator<Map.Entry<String,Session>> iter=set.iterator();
		while(iter.hasNext()){
			session=iter.next().getValue();
			session.getIoSession().write(IoBuffer.wrap(sendData));
			
		}
	}
	
	
	
}
