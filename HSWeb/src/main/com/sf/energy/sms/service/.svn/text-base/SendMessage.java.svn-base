// SendMessage.java - Sample application.
//
// This application shows you the basic procedure for sending messages.
// You will find how to send synchronous and asynchronous messages.
//
// For asynchronous dispatch, the example application sets a callback
// notification, to see what's happened with messages.

package com.sf.energy.sms.service;



import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sf.energy.sms.dao.SMSHistoryDao;
import com.sf.energy.sms.model.SMSHistoryModel;

/**
 * 调用发送接口，如果发送成功则存入数据库
 * @author yanhao
 *
 */
public class SendMessage
{
	public boolean sendMegAndToTable(String phones,String content,HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		boolean a=false;
		boolean b=false;
		SMSManager app=new SMSManager();
		SMSHistoryDao smsDao=new SMSHistoryDao();
		SMSHistoryModel sms=new SMSHistoryModel();
		sms.setSmsHistoryTime(new Date().toLocaleString());//发送时间
		
		//发送
		a= app.sendSMS(phones, content);
		
		//并存入数据库
		sms.setPhoneList(phones);
		String[] phonesArray = phones.split(" ");
		sms.setCount(phonesArray.length);
		sms.setSmsHistoryTime(new Date().toLocaleString());
		sms.setContent(content);
		sms.setInsertTime(new Date().toLocaleString());
		sms.setUserID((int)request.getSession().getAttribute("userId"));
		
		if(a==true)
		{
			sms.setStyleID(1);//成功！
		}
		else {
			sms.setStyleID(0);//成功！
		}
		b=smsDao.insert(sms);
		
		return a&&b;
	}
}
