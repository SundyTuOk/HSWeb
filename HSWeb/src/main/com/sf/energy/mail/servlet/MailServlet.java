package com.sf.energy.mail.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sf.energy.mail.util.MailDao;
import com.sf.energy.mail.util.MailSenderFactory;
import com.sf.energy.mail.util.ReciveOneMail;
import com.sf.energy.mail.util.SimpleMailSender;


public class MailServlet extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try
		{
			findMethod(req, resp);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doGet(req, resp);
	}

	/**
	 * 寻找控制器
	 * 
	 * @param req
	 *            request类
	 * @param resp
	 *            response类
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws MessagingException 
	 */
	private void findMethod(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ParseException, MessagingException
	{
		String method = req.getParameter("method");
		// //System.out.println("method:" + method);

		if ("saveFile".equals(method))
			saveFile(req, resp);

		if ("sendMail".equals(method))
			sendMail(req, resp);

		if ("getAllHistorySend".equals(method))
			getAllHistorySend(req, resp);

		if ("deleteSomeHistorySend".equals(method))
			deleteSomeHistorySend(req, resp);

		if ("saveAssSketch".equals(method))
			saveAssSketch(req, resp);

		if ("getAssSketch".equals(method))
			getAssSketch(req, resp);

		if ("deleteAssSketch".equals(method))
			deleteAssSketch(req, resp);

		if ("getMailFromRemote".equals(method))
			getMailFromRemote(req, resp);
	}

	/**
	 * 从远端获得邮件
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void getMailFromRemote(HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("SMSHistoryPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("SMSHistoryPageIndex"));
		String userID = "1";
		
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}
		
		PrintWriter out = response.getWriter();
		JSONArray js=new JSONArray();
		JSONObject jo = new JSONObject();
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.sina.com");
			props.put("mail.smtp.auth", "true");
			Session session1 = Session.getDefaultInstance(props, null);
			URLName urln = new URLName("pop3", "pop3.sina.com", 110, null,
					"a2963348", "czy_2963348");
			Store store = session1.getStore(urln);
			store.connect();
			Folder folder = store.getFolder("INBOX");


			folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);

			session.setAttribute("folder", folder);

			String from = null;
			String subject = null;
			Message[] message;

			message = folder.getMessages();

			int messageCounts = message.length;
			jo.put("totalCount", messageCounts);
			js.add(jo);
			ReciveOneMail pmm = null;   
			for (int i = 0; i < messageCounts; i++) {
				
				pmm = new ReciveOneMail((MimeMessage) message[i]);  
				try {
					pmm.setDateFormat("yyyy-MM-dd HH:mm:ss");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}   
				try {
					from = pmm.getFrom();
					subject = pmm.getSubject();
				//	System.out.println(from+"  "+subject);
					jo.put("form", from);
					jo.put("subject", subject);
					pmm.setDateFormat("yyyy-MM-dd HH:mm:ss");   
		            jo.put("sentdate", pmm.getSentDate());
		      
		            if (pmm.isContainAttach((Part) message[i]))
					{
		            	jo.put("containAttachment", "是");
					}
		            else
		            {
		            	jo.put("containAttachment", "否");
		            }
		            
		            String checkMail="<button class='btn btn-mini aqua glare btn-danger'  onclick='getContent("+(i+1)+")'>查看邮件<i class='icon-trash bigger-120'></i></button>";
		            jo.put("checkMail", checkMail);
		            
		            js.add(jo);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		out.println(js.toString());
		out.flush();
		out.close();


	}

	private void deleteAssSketch(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MailDao dao = new MailDao();
		String idList = null;
		if (request.getParameter("theIDList") != null)
		{
			idList = request.getParameter("theIDList");
		}

		boolean b = false;

		try
		{
			b = dao.deleteAssSketch(idList.substring(1));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		// System.out.println(b);
		out.println(b);
		out.flush();
		out.close();

	}

	/**
	 * 保存草稿
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void saveAssSketch(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String userID = "1";
		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		MailDao dao = new MailDao();
		String phoneList = "";
		String content = "";
		boolean isSeccuss = false;
		if (request.getParameter("phoneList") != null)
		{
			phoneList = request.getParameter("phoneList").trim();
		}
		if (request.getParameter("content") != null)
		{
			content = request.getParameter("content").trim();
		}
		String mailSubject = request.getParameter("mailSubject");

		String mailFileUrl = request.getParameter("mailFileUrl");

		String[] phonesArray = phoneList.split(" ");

		String[] urlArray = mailFileUrl.split("\\\\");

		String fileName = null;
		boolean updateset = false;
		if (urlArray.length > 1)
		{
			fileName = urlArray[urlArray.length - 1];
		}

		try
		{
			for (String receiver : phonesArray)
			{
				dao.saveAssSketch(userID, receiver, fileName, mailSubject, content);
			}
			isSeccuss = true;
		} catch (SQLException e)
		{
			isSeccuss = false;
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(isSeccuss);
		out.flush();
		out.close();

	}

	/**
	 * 删除发送邮件
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void deleteSomeHistorySend(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		MailDao dao = new MailDao();
		String idList = null;
		if (request.getParameter("theIDList") != null)
		{
			idList = request.getParameter("theIDList");
		}

		boolean b = false;

		try
		{
			b = dao.deleteSomeHistorySend(idList.substring(1));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		// System.out.println(b);
		out.println(b);
		out.flush();
		out.close();

	}

	/**
	 * 获得草稿箱的数据
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAssSketch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		String userID = "1";
		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}
		Integer thePageCount = Integer.parseInt(request.getParameter("SMSHistoryPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("SMSHistoryPageIndex"));
		String sortName = "sendTime";
		String order = "desc";

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}

		MailDao dao = new MailDao();
		JSONArray js = MailDao.listAllAssSketchMail(userID, sortName, order, thePageCount, thePageIndex);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(js.toString());
		out.flush();
		out.close();

	}

	/**
	 * 获得历史数据
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void getAllHistorySend(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
		Integer thePageCount = Integer.parseInt(request.getParameter("SMSHistoryPageCount"));
		Integer thePageIndex = Integer.parseInt(request.getParameter("SMSHistoryPageIndex"));
		String sortName = "sendTime";
		String order = "desc";

		if (request.getParameter("sortName") != null)
		{
			sortName = request.getParameter("sortName");
		}
		if (request.getParameter("order") != null)
		{
			if (request.getParameter("order").equals("desc"))
			{
				order = "desc";
			} else if (request.getParameter("order").equals("asc"))
			{
				order = "asc";
			}

		}

		MailDao dao = new MailDao();
		JSONArray js = MailDao.listAllSendMail(sortName, order, thePageCount, thePageIndex);

		response.setContentType("application/x-json");

		PrintWriter out = response.getWriter();
		out.println(js.toString());
		out.flush();
		out.close();

	}

	/**
	 * 发送邮件
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SQLException
	 */
	private void sendMail(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{

		String userID = "1";
		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null)
		{
			userID = session.getAttribute("userId").toString();
		}

		MailDao dao = new MailDao();
		String phoneList = "";
		String content = "";
		boolean isSeccuss = false;
		if (request.getParameter("phoneList") != null)
		{
			phoneList = request.getParameter("phoneList").trim();
		}
		if (request.getParameter("content") != null)
		{
			content = request.getParameter("content").trim();
		}
		String mailSubject = request.getParameter("mailSubject");

		String mailFileUrl = request.getParameter("mailFileUrl");

		MailSenderFactory factory = new MailSenderFactory();

		String Sender = "a2963348@sina.com";
		// 查询数据库获得发件人的
		SimpleMailSender sender = factory.getSender(Sender, "czy_2963348");

		String[] phonesArray = phoneList.split(" ");

		String[] urlArray = mailFileUrl.split("\\\\");

		String fileName = null;
		boolean updateset = false;
		if (urlArray.length > 1)
		{
			fileName = urlArray[urlArray.length - 1];
		}

		try
		{
			ArrayList<String> list = new ArrayList<String>();

			for (String receiver : phonesArray)
			{
				list.add(receiver);
				dao.saveSendMail(userID, receiver, new Date(), fileName, mailSubject, content, "未发送");
				updateset = true;
			}
			if (fileName != null)
			{
				String url = this.getServletContext().getRealPath("/") + "upLoadFile\\" + fileName;
				sender.addAttachfile(url);
			}
			sender.send(list, mailSubject, content);

			isSeccuss = true;

		} catch (AddressException e)
		{
			isSeccuss = false;
			e.printStackTrace();
		} catch (MessagingException e)
		{
			isSeccuss = false;
			e.printStackTrace();
		}
		if (updateset)
		{
			if (isSeccuss)
			{
				dao.updateState(userID, "成功");
			} else
			{
				dao.updateState(userID, "失败");
			}
		}

		PrintWriter out = response.getWriter();
		out.println(isSeccuss);
		out.flush();
		out.close();
	}

	/**
	 * 添加附件
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String a = "";
		String color = "";
		// 保存上传的文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try
		{
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext())
			{
				FileItem item = (FileItem) itr.next();
				if (item.isFormField())
				{
					System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
				} else
				{
					if (item.getName() != null && !item.getName().equals(""))
					{
						System.out.println("上传文件的大小:" + item.getSize());
						System.out.println("上传文件的类型:" + item.getContentType());
						// item.getName()返回上传文件在客户端的完整路径名称
						System.out.println("上传文件的名称:" + item.getName());

						File tempFile = new File(item.getName());

						// 保存文件
						ServletContext sc = this.getServletContext();
						File file = new File(sc.getRealPath("/") + "upLoadFile", tempFile.getName());
						item.write(file);
						a = "添加附件成功！";
						color = "green";
					} else
					{
						a = "没有选择文件！";
						color = "red";
					}
				}
			}
		} catch (FileUploadException e)
		{
			a = "添加附件发生异常！";
			color = "red";
			e.printStackTrace();
		} catch (Exception e)
		{
			a = "添加附件发生异常！";
			color = "red";
			e.printStackTrace();

		}

		JSONObject jo = new JSONObject();
		jo.put("msg", a);
		jo.put("color", color);
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
}
