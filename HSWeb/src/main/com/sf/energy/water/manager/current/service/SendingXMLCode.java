package com.sf.energy.water.manager.current.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import com.sf.energy.water.manager.current.model.XMLCoder;

/**
 * 发送即抄命令的接口
 * @author yanhao
 *
 */
public interface SendingXMLCode
{
	/**
	 * 组装抄表XML命令发送到服务器，服务器返回获得的XML数据
	 * @param xmlCode 组装命令发送到服务器
	 * @return 服务器返回命令
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String sendXMLToServer(XMLCoder xmlCode) throws UnknownHostException, IOException,SQLException,SocketTimeoutException ;
}
