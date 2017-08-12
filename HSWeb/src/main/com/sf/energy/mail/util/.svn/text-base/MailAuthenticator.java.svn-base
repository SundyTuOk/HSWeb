package com.sf.energy.mail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 登陆邮箱服务器验证
 * @author Administrator
 *
 */
public class MailAuthenticator extends Authenticator
{
	/**
     * 用户名（登录邮箱）
     */
    private String username;
    /**
     * 密码
     */
    private String password;
	
	public MailAuthenticator()
	{
		super();
	}
	/**
	 * 初始化登陆信息
	 * @param username 用户名（登录邮箱）
	 * @param password 密码
	 */
	public MailAuthenticator(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
    
	 protected PasswordAuthentication getPasswordAuthentication(){   
	        return new PasswordAuthentication(username, password);   
	  }   
    
    
}
