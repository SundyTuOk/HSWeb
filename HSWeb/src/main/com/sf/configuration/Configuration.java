package com.sf.configuration;
import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
/**
 * 
 * @author 
 * @version 1.0
 * @since version 1.0
 */
public class Configuration {
	public static String getString(String key)
	{
		return	getConfiguration().getString(key);
	}
	public static XMLConfiguration getConfiguration()
	{
		String filePath=System.getProperty("user.dir")+ "\\configuration\\ServerConfig.xml";
		File file=new File(filePath);
		if (file == null || !file.exists())
			return null;
		XMLConfiguration config = null;
		try
		{
			config = new XMLConfiguration(file);
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		config.setEncoding("utf-8");

		return config;

	}
	/**
	 * ��ȡһ��Configuration�����ļ�
	 * @param filePath Configuration�����ļ���·��
	 * @return  XMLConfiguration���ӵ�һ��ʵ��	 
	 *  
	 */
	public static XMLConfiguration getConfigurationPool()
	{
		String filePath=System.getProperty("user.dir")+ "/config/proxool.xml";
		File file=new File(filePath);
		if (file == null || !file.exists())
			return null;
		XMLConfiguration config = null;
		try
		{
			config = new XMLConfiguration(file);
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		// ���ñ���
		config.setEncoding("utf-8");

		return config;

	}
}
