package com.sf.energy.mail.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class SimpleMailSender
{
	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();
	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	/**
	 * 邮箱session
	 */
	private transient Session session;

	/**
	 * 用于保存发送附件的文件名的集合
	 */
	private Vector file = new Vector();

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 *            SMTP邮件服务器地址
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String smtpHostName, final String username,
			final String password, String valedate, boolean writeLog)
	{
		init(username, password, smtpHostName, valedate, writeLog);
	}

	/**
	 * 初始化邮件发送器
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)，并以此解析SMTP服务器地址
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String username, final String password,
			String valedate, boolean writeLog)
	{
		// 通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
		final String smtpHostName = "smtp." + username.split("@")[1];
		init(username, password, smtpHostName, valedate, writeLog);

	}

	/**
	 * 初始化
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            密码
	 * @param smtpHostName
	 *            SMTP主机地址
	 */
	private void init(String username, String password, String smtpHostName,
			String valedate, boolean writeLog)
	{
		// 初始化props
		props.put("mail.smtp.auth", valedate);
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.port", "25");
		props.put("mail.transport.protocol", "smtp");
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
		session.setDebug(writeLog);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, SimpleMail mail)
			throws AddressException, MessagingException
	{
		send(recipient, mail.getSubject(), mail.getContent());
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, String subject, Object content)
			throws AddressException, MessagingException
	{
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置主题
		message.setSubject(subject);

		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();

		// 设置HTML内容
		html.setContent(content.toString(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		// 判定是否有附加
		if (!file.isEmpty())
		{
			Enumeration efile = file.elements();
			while (efile.hasMoreElements())
			{
				html = new MimeBodyPart();
				String filename = efile.nextElement().toString(); // 选择出每一个附件名
				FileDataSource fds = new FileDataSource(filename); // 得到数据源
				html.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
				html.setFileName(fds.getName()); // 得到文件名同样至入BodyPart
				mainPart.addBodyPart(html);
			}
			file.removeAllElements();
		}
		// 设置邮件内容
		message.setContent(mainPart);

		// 设置发送时间
		message.setSentDate(new Date());
		// 发送
		Transport.send(message);

	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException
	{
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++)
		{
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(content.toString(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		// 判定是否有附加
		if (!file.isEmpty())
		{
			Enumeration efile = file.elements();
			while (efile.hasMoreElements())
			{
				html = new MimeBodyPart();
				String filename = efile.nextElement().toString(); // 选择出每一个附件名
				FileDataSource fds = new FileDataSource(filename); // 得到数据源
				html.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
				html.setFileName(fds.getName()); // 得到文件名同样至入BodyPart
				mainPart.addBodyPart(html);
			}
			file.removeAllElements();
		}
		// 设置邮件内容
		message.setContent(mainPart);
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, SimpleMail mail)
			throws AddressException, MessagingException
	{
		send(recipients, mail.getSubject(), mail.getContent());
	}

	/**
	 * 用于收集附件名称
	 * 
	 * @param fname
	 *            文件的路径
	 */
	public void addAttachfile(String fname)
	{
		file.addElement(fname);
	}

}
