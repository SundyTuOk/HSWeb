package com.sf.energy.light.service;

import java.io.IOException;
import java.net.UnknownHostException;

import com.sf.energy.light.model.XMLCoder;


public interface SendingXMLCode
{
	/**
	 * 组装抄表XML命令发送到服务器，服务器返回获得的XML数据
	 * @param xmlCode 组装命令发送到服务器
	 * @return 服务器返回命令
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String sendXMLToServer(XMLCoder xmlCode) throws UnknownHostException, IOException;
	public boolean creatCon() throws UnknownHostException, IOException;

}
