package com.sf.energy.server.tftcp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.mina.core.session.IoSession;

import com.sf.energy.server.tcp.PushSession;
import com.sf.energy.server.tcp.TranServerSession;
import com.sf.energy.server.tcp.TranSession;
import com.sf.energy.server.tcp.WebServerSession;
import com.sf.energy.server.tcp.WebSession;
///这个类提供一些工具方法
public class Tool
{
	/** 
	 * 将字节转为十六进制的字符串
	 * @param 要转的byte src 
	 * @return 16进制的字符串
	 * @exception
	 */
	
	public  static String bytesToHexString(byte src){
		int a=src & 0xFF;
		String b=Integer.toHexString(a);
		return b;
	}
/*	public byte[] bytesToHexBytes(byte[] src){
		byte[] Hex=new byte[src.length];
		for(int i=0;i<src.length;i++){
			src[i]
		}
	}
	/** 
	 * 计算报文长度
	 * @param 要计算的byte bytes 
	 * @return 报文长度
	 * @exception
	 */
	public int getLength(byte[] bytes){
		int len=0;
		 len = ((bytes[2] & 0xff) * 256+ (bytes[1] & 0xff) - 2) / 4 + 8;
		 return len;
	}
/*	public static void main(String[] args){
		int aa = 15;
		byte a = (byte)aa;
		//System.out.println(bytesToHexString(a));
	}
	*/
	/**
	 * 遍历哈希表,查找相应的中转站会话类
	 * @param str 中转站地址
	 * @return
	 */
	public TranSession findSession(String str){
		TranSession session=new TranSession();
		TranServerSession serverWebSession=TranServerSession.getServerWebSession();
		HashMap<String,TranSession> hashMap=serverWebSession.getHashMap();
		//System.out.println("db"+str);
		session=hashMap.get(str);
		if(session!=null){
			return session;
		}
		else {
			return null;
		}
	}
	public WebSession findWebSession(String str){
		WebSession session=new WebSession();
		WebServerSession webServerSession=WebServerSession.getWebServerSession();
		HashMap<String,WebSession> hashMap=webServerSession.getHashMap();
		session=hashMap.get(str);		
		if(session!=null)
		{
			//System.out.println("find");
			//System.out.println(session.getId());
			//session.getIoSession().write("dddss");
			return session;
		}
		else {
			//System.out.println("not find");
			return null;
		}
		
	}
	
	/**
	 * 将报警信息广播到页面
	 * @param msg
	 */
	public void broadcast(String msg){
		IoSession ioSession;
		PushSession pushSession=PushSession.getPushSession();
		HashMap<String,IoSession> hashMap=pushSession.getHashMap();
		Iterator iter=hashMap.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,IoSession> enter=(Map.Entry<String,IoSession>)iter.next();
			ioSession=enter.getValue();
			ioSession.write(msg);
		}
				
	}
}
