package com.sf.energy.prepayment.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.project.equipment.dao.GatherDao;
import com.sf.energy.project.equipment.dao.WatermeterDao;
import com.sf.energy.project.equipment.model.Gather;
import com.sf.energy.project.equipment.model.WatermeterModel;
import com.sf.energy.water.manager.current.model.XMLCoder;
import com.sf.energy.water.manager.current.service.SendingXMLCode;
import com.sf.energy.water.manager.current.service.XMLHelper;
import com.sf.energy.water.manager.current.serviceImpl.SendingImmediateCode;

public class ReadWatter
{
	// 本次错误是否有误
	private boolean error;
	// 本次读取产生的错误信息
	private String errorMessage;
	private String ValueTime;
	private String ZY0;

	public ReadWatter()
	{
	}

	public ReadWatter(String waterId, String userID) throws IOException
	{
		GatherDao gd = new GatherDao();
		WatermeterDao ad = new WatermeterDao();
		XMLHelper xmlHelper = new XMLHelper();

		SendingXMLCode immSending = new SendingImmediateCode();

		int queryAmmID = Integer.parseInt(waterId);
		WatermeterModel am = new WatermeterModel();
		
		try
		{
			am = ad.getWatermeterInfoByID(queryAmmID);
			Gather gm = new Gather();
			gm = gd.queryByID(am.getGATHER_ID());
			// 离线
			if (am.getWATERMETER_STAT() == 0)
			{
				error = true;
				errorMessage = "表计离线";
			} else
			// 在线
			{
				int ammeNum = am.getWATERMETER_POINT();// 10
				String ammPointNoDA = xmlHelper.makePointNo(ammeNum);// 0202

				XMLCoder immXmlCoderD = new XMLCoder();// 单相

				immXmlCoderD.setCommandCodeAFN("0C");
				immXmlCoderD.setCommandCodeDA(ammPointNoDA);
				immXmlCoderD.setCommandCodeDT("0110");// 单相
				immXmlCoderD.setTerminalAddress(gd.queryGatherNumByWaterAmmID(queryAmmID));// 002700bb00
				immXmlCoderD.setDatasiteID(String.valueOf(gm.getDatasiteID()));
				immXmlCoderD.setOperCode(userID + "");

				String msg = "";
				try
				{
					msg = immSending.sendXMLToServer(immXmlCoderD);
				} catch (UnknownHostException e)
				{
					error = true;
					errorMessage = "无法找到服务器";
				} catch (SocketTimeoutException e)
				{
					error = true;
					errorMessage = "服务器响应超时";
				} catch (ConnectException e)
				{
					error = true;
					errorMessage = "服务器连接异常";
				}

				if ("".equals(msg))// 服务器没有发送数据回来
				{
					error = true;
					errorMessage = "服务器无响应数据";
				}
				else
				{
					String err = getAttrValue(msg, "commandback", "error");
					if ("0".equals(err))
					{
						error = false;
						ZY0 = getAttrValue(msg, "result", "ZY0");
						ValueTime = getAttrValue(msg, "result", "ValueTime");
					}
					else {
						error = true;
						errorMessage = getAttrValue(msg, "commandback", "errormessage");
					}
				}
			}
		} catch (SQLException e1)
		{
			error = true;
			errorMessage = "获取表计信息";
		}
	}
	
	
	public String getAttrValue(String xml, String tagName, String attrName)
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder;
		NodeList nodeList = null;
		String result = null;
		try
		{
			dombuilder = domfac.newDocumentBuilder();

			read = new StringReader(xml);

			source = new InputSource(read);

			Document document = dombuilder.parse(source);
			nodeList = document.getElementsByTagName(tagName);
			Element elm = (Element) nodeList.item(0);
			result = elm.getAttribute(attrName);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getValueTime()
	{
		return ValueTime;
	}

	public void setValueTime(String valueTime)
	{
		ValueTime = valueTime;
	}

	public String getZY0()
	{
		return ZY0;
	}

	public void setZY0(String zY0)
	{
		ZY0 = zY0;
	}
	

}
