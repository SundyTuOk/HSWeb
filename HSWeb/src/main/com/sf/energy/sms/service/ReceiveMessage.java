package com.sf.energy.sms.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smslib.InboundMessage;

import com.sf.energy.project.right.dao.UsersDao;
import com.sf.energy.sms.dao.SMSReceiveDao;
import com.sf.energy.sms.model.SMSReceiveModel;

/**
 * 接收短信，判断是否是新短信，存入数据库
 * @author yanhao
 *
 */
public class ReceiveMessage
{
	public boolean receiveMegAndToTable(HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		boolean b=false;
		SMSManager app=new SMSManager();
		SMSReceiveModel sms=null;
		SMSReceiveDao smsDao=new SMSReceiveDao();
		UsersDao ud=new UsersDao();
		String name="";
		name = ud.queryById((int)request.getSession().getAttribute("userId")).getUsersName();
		//接收短信
		List<InboundMessage> readList=app.readSMS();
		//app.destroy();
		//存入数据库,判断是否已经接收，再存入
		for (InboundMessage in : readList)
		{
			//System.out.println(in.getDate().toLocaleString()+" 发信人：" + in.getOriginator() + " 短信内容:"
			//		+ in.getText());
			sms=new SMSReceiveModel();
			sms.setSendPhone(in.getOriginator());
			sms.setContent(in.getText());
			sms.setSendTime(in.getDate().toLocaleString());
			sms.setReceiveManName(name);
			if(!smsDao.queryBYUnique(sms))//数据库里面没有
			{
				b=smsDao.insert(sms);
			}
			
		}
		
		return b;
	}
}
