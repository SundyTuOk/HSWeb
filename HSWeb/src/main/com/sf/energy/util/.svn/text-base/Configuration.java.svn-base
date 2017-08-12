package com.sf.energy.util;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
/**
 * 对Configuration配置是文件的操作
 * 
 * @author 鄢浩
 * @version 1.0
 * @since version 1.0
 */
public class Configuration
{
	/**
	 * 获取一个Configuration配置文件
	 * @param filePath 文件在工程中的路径
	 * @return XMLConfiguration连接的一个实例
	 * @throws ConfigurationException
	 */
	public static XMLConfiguration getConfiguration(String filePath)
			throws ConfigurationException
	{
		String path= System.getProperty("user.dir")
				+"/"+ filePath;
		File file = new File(path);
		if (file == null || !file.exists())
			return null;
		XMLConfiguration config = null;
		config = new XMLConfiguration(file);

		// 设置编码
		config.setEncoding("utf-8");

		return config;
	}
	/**
	 * 获取一个Configuration配置文件
	 * @return XMLConfiguration连接的一个实例
	 * @throws ConfigurationException
	 */
	public static XMLConfiguration getConfiguration()
			throws ConfigurationException
	{
		String path= System.getProperty("user.dir")
				+"/config/transferconfig.xml";
		File file = new File(path);
		if (file == null || !file.exists())
			return null;
		XMLConfiguration config = null;
		config = new XMLConfiguration(file);

		// 设置编码
		config.setEncoding("utf-8");

		return config;
	}
	
	public static XMLConfiguration getwebserviceConfiguration(String filepath)
			throws ConfigurationException
	{
		
		String filePath=filepath;
		File file = new File(filePath);
		if (file == null || !file.exists())
			return null;
		XMLConfiguration config = null;
		config = new XMLConfiguration(file);

		// 设置编码
		config.setEncoding("utf-8");

		return config;
	}
	public static boolean isInnerIP(String ipAddress){   
        boolean isInnerIp = false;   
        long ipNum = getIpNum(ipAddress);   
	        /**  
	        私有IP：A类  10.0.0.0-10.255.255.255  
	               B类  172.16.0.0-172.31.255.255  
	               C类  192.168.0.0-192.168.255.255  
	        当然，还有127这个网段是环回地址  
	        **/  
	        long aBegin = getIpNum("10.0.0.0");   
	        long aEnd = getIpNum("10.255.255.255");   
	        long bBegin = getIpNum("172.16.0.0");   
	        long bEnd = getIpNum("172.31.255.255");   
	        long cBegin = getIpNum("192.168.0.0");   
	        long cEnd = getIpNum("192.168.255.255");   
	        isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd) || ipAddress.equals("127.0.0.1");   
	        return isInnerIp;              
	} 
	private static long getIpNum(String ipAddress) {   
	    String [] ip = ipAddress.split("\\.");   
	    long a = Integer.parseInt(ip[0]);   
	    long b = Integer.parseInt(ip[1]);   
	    long c = Integer.parseInt(ip[2]);   
	    long d = Integer.parseInt(ip[3]);   
	  
	    long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;   
	    return ipNum;   
	}  
	private static boolean isInner(long userIp,long begin,long end){   
	     return (userIp>=begin) && (userIp<=end);   
	} 
}
