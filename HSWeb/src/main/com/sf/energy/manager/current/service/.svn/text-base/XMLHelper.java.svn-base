package com.sf.energy.manager.current.service;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import org.w3c.dom.Document;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sf.energy.project.equipment.model.Gather;

/*
 * 组装和解析XML的工具类
 * 
 */
public class XMLHelper
{
	/**
	 * 通过测量点号AmmeterPoint计算出command code 的中间4位（DA）
	 * @param meterID
	 * @return
	 */
	public String makePointNo(int meterID)
	{
		
		String da1 = "01";
		String da2 = "01";
        if (meterID % 8 == 0)
        {
            da1 = "80";
            da2 = Integer.toHexString(meterID / 8);
            if(da2.length() == 1)
            {
            	da2 = "0" + da2;
            }
        }
        else
        {
            da1 = Integer.toHexString((int)(Math.pow(2, meterID % 8 - 1)));
            if(da1.length() == 1)
            {
            	da1 = "0" + da1;
            }
        	da2 = Integer.toHexString(meterID / 8 + 1);
        	if(da2.length() == 1)
            {
            	da2 = "0" + da2;
            }
        }
        //信息点DA1DA2
        String pointno = da1 + da2; 
        

		return pointno;
	}
	
	/**
	 * 获得某个节点的
	 * @param xml XML字符串
	 * @param tagName 节点的名称
	 * @return 对应节点的element对象
	 * @throws Exception
	 */
	public NodeList getNodeList(String xml, String tagName) throws Exception
	{
		InputSource source = null;
		StringReader read = null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dombuilder = domfac.newDocumentBuilder();
		
		read = new StringReader(xml);

		source = new InputSource(read);

		Document document = dombuilder.parse(source);
		NodeList nodeList = document.getElementsByTagName(tagName);
		
		return nodeList;
	}
	
	/**
	 * 日期增加n天。
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            增加的天数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	public Date addDate(Date date, int days) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.DATE, days);
		return cld.getTime();
	}
	
}
