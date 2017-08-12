package com.sf.energy.mail.util;

public class SimpleMail
{
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private Object content;
	
	
	public Object getContent()
	{
		return content;
	}
	
	public void setContent(Object content)
	{
		this.content = content;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
}
