package com.sf.energy.mail.util;



import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class MailSenderFactory
{
	 /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;
 
    /**
     * 获取邮箱
     * 
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public SimpleMailSender getSender(String userName,String pwd) {
	    
	        if (serviceSms == null)
	        {
	        	synchronized (SimpleMailSender.class)
				{
					if (serviceSms == null)
					{
					   serviceSms = new SimpleMailSender(userName,pwd,"true",true);
					}
				}
	            
	        }
	        return serviceSms;
    }
    
    public static XMLConfiguration getConfiguration()
	{
		String filePath=System.getProperty("user.dir")+ "/config/mail.xml";
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
		// 设置编码
		config.setEncoding("utf-8");

		return config;

	}
}
