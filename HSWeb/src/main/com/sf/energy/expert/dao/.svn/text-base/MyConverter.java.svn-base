package com.sf.energy.expert.dao;

import java.io.File;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;

/**
* <p>cn.hcharts.exporting
* <p>File: ExportServlet.java ����ʱ��:2014-7-14����12:44:58</p>
* <p>Title: Highcharts Jsp/Servlet�浼�������� Servlet�ļ�</p>
* <p>Description: publish on http://www.hcharts.cn</p>
* <p>Copyright: Copyright (c) 2014 Highcharts������</p>
* @author zhy
* @version 0.0.1
*/
public class MyConverter extends SVGConverter{
	
	/**
	 * ת������
	 * @param sources SVG�ļ�·��
	 * @param destination Ŀ���ļ�·��
	 * @param type ת�����ͣ��� image/png | image/jpeg | application/pdf |��image/svg+xml����ѡ
	 * @param width ����ͼƬ���
	 * @return Ŀ���ļ���
	 * @throws SVGConverterException
	 */
	public  String conver(String sources,String destination,String type,float width) throws SVGConverterException {
		
		SVGConverter converter = new MyConverter();
		
		// ���ø߶ȣ�Ĭ����400
		converter.setHeight(400);
		
		// ���ÿ�ȣ������ֵ
		converter.setWidth(width);
		
		// ����svgԴ�ļ�·������һ�����飬֧�ֶ���ļ�ͬʱת��
		String[] src = {sources};
		converter.setSources(src);
		

		// ����ͼƬ����
		converter.setQuality(MAXIMUM_QUALITY);
		
		// ��¼�ļ���׺
		String ext = "";
		
		// ��ߴ�����������õ������ͺ��ļ���׺
		if(type.equals("image/png")) {
			converter.setDestinationType(DestinationType.PNG);
			ext = "png";
		} else if(type.equals("image/jpeg")) {
			converter.setDestinationType(DestinationType.JPEG);
			ext = "jpg";
		} else if(type.equals("application/pdf")) {
			converter.setDestinationType(DestinationType.PDF);
			ext = "pdf";
		}  else {
			return null;
		}

		
		
		// ����Ŀ���ļ�·��
		converter.setDst(new File(destination+"\\chart."+ext));
		
		// ִ�е���
		converter.execute();
		return "chart."+ext;
	}
}
