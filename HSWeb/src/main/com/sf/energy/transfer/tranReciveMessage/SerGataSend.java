package com.sf.energy.transfer.tranReciveMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.sf.energy.transfer.tcp.Session;
import com.sf.energy.transfer.tcp.TranGateSession;

public class SerGataSend {
	public static byte[] send;
	Session session=new Session();

	public static byte[] getSend()
	{
		return send;
	}
	
	public void sendGata(String dtgateaddress,byte[] send,int len) throws IOException{
		IoSession ioSession=null;
		ioSession=findSocket(dtgateaddress);		
		ioSession.write(send);
		
	}
	
	public IoSession findSocket(String dtgateaddress){
		IoSession ioSession=null;
		TranGateSession tcp=TranGateSession.getTranGateSession();
		Set<Map.Entry<String, Session>> set=tcp.getHashMap().entrySet();
		Iterator<Map.Entry<String, Session>> iter=set.iterator();
		while(iter.hasNext()){
			session=iter.next().getValue();
		    if(session.getDtgateaddress()==dtgateaddress){
		    	
		    	ioSession=session.getIoSession();
		    	
		    	break;
		    }
		}
		return ioSession;
	}
	public void send(byte[] sendMessage,int len){
		  len = ((sendMessage[2] & 0xff) * 256+ (sendMessage[1] & 0xff) - 2) / 4 + 8;
			send=Arrays.copyOf(sendMessage, len);
			
	}
}
