package com.sf.energy.publicity.service;

import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.sf.energy.publicity.dao.publicityDao;

public class publicityService
{
	public String  getArchStyleDataService() throws JSONException{
		//建筑分类表头内容
		StringBuffer sBuffer = new StringBuffer();
		//具体建筑类型用能数据
		StringBuffer dBuffer = new StringBuffer();
		publicityDao dao = new publicityDao();
		List<JSONObject> array = dao.getArchStyleInfo();
		if(array!=null&&array.size()>0){
			JSONObject o = (JSONObject) array.get(0);
			sBuffer.append("<div class='tabList'><ul>");
			sBuffer.append("<li class='cur'>"+o.getString("value")+"</li>");
			
			dBuffer.append("<div class='tabCon'>");	
			
			dBuffer.append("<div class='cur'>");
			dBuffer.append("<table cellpadding='0' cellspacing='0' border='0'>"
					+ "<tr>"
					+ "<th style=' width:200px'>建筑名称</th>"
					+ "<th style=' width:100px'>本月用电量</th>"
					+ "<th style=' width:100px'>本年用电量</th>"
					+ "<th style=' width:100px'>本月用水量</th>"
					+ "<th style=' width:100px'>本年用水量</th>"
					+ "<th style=' width:140px'>本年能耗指标</th>"
					+ "<th style=' width:140px'>本年剩余指标量</th>"
					+ "</tr>");
			dBuffer.append(dao.getArchEnergyByStyle(o.getString("num")));
			dBuffer.append("</table></div>");
			
			for(int i=1;i<array.size();i++){
				if(i<=10){//界面最多能展示11种建筑类型
					o = array.get(i);
					sBuffer.append(o.getString("title"));
					dBuffer.append("<div>");
					dBuffer.append("<table cellpadding='0' cellspacing='0' border='0'>"
							+ "<tr>"
							+ "<th style=' width:200px'>建筑名称</th>"
							+ "<th style=' width:100px'>本月用电量</th>"
							+ "<th style=' width:100px'>本年用电量</th>"
							+ "<th style=' width:100px'>本月用水量</th>"
							+ "<th style=' width:100px'>本年用水量</th>"
							+ "<th style=' width:140px'>本年能耗指标</th>"
							+ "<th style=' width:140px'>本年剩余指标量</th>"
							+ "</tr>");
					dBuffer.append(dao.getArchEnergyByStyle(o.getString("num")));
					dBuffer.append("</table></div>");
				}
			}
			sBuffer.append("</ul></div>");
			dBuffer.append("</div>");
		}	
		sBuffer.append(dBuffer);
		return sBuffer.toString();
	}
	public String getArchFenxiangEnergy(){
		StringBuffer sBuffer = new StringBuffer();
		publicityDao dao = new publicityDao();
		sBuffer.append("<table cellpadding='0' cellspacing='0' border='0' style=' font-size:12px'>");
		sBuffer.append("<tr>"
				+ "<th style=' width:200px;'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "</tr>");
		sBuffer.append(dao.getArchFenxiangEnergy());
		sBuffer.append("</table>");
		return sBuffer.toString();
	}
	public String[] getArchAverageData(){
		StringBuffer aBuffer = new StringBuffer();
		StringBuffer wBuffer = new StringBuffer();
		publicityDao dao = new publicityDao();
		String datas[] = dao.getArchAverage();
		aBuffer.append("<table cellpadding='0' cellspacing='0' border='0' style=' font-size:12px'>");
		wBuffer.append("<table cellpadding='0' cellspacing='0' border='0' style=' font-size:12px'>");
		aBuffer.append("<tr>"
				+ "<th style=' width:200px;'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "</tr>");
		wBuffer.append("<tr>"
				+ "<th style=' width:200px;'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "<th style=' width:140px'></th>"
				+ "</tr>");
		aBuffer.append(datas[0]);
		wBuffer.append(datas[1]);
		aBuffer.append("</table>");
		wBuffer.append("</table>");
		String r[] = new String[]{aBuffer.toString(),wBuffer.toString()};
		return r;
	}
	public String getPartmentFenxiangEnergy(){
		StringBuffer sBuffer = new StringBuffer();
		publicityDao dao = new publicityDao();
		sBuffer.append("<table cellpadding='0' cellspacing='0' border='0' style=' font-size:12px'>");
		sBuffer.append("<tr>"
				+ "<th style=' width:200px;'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "<th style=' width:100px'></th>"
				+ "</tr>");
		sBuffer.append(dao.getPartmentFenxiangEnergy());
		sBuffer.append("</table>");
		return sBuffer.toString();
	}
	public static void main(String[] args) throws JSONException {
		publicityService service = new publicityService();
		//String r[] = service.getArchAverageData();
		System.out.println(service.getPartmentFenxiangEnergy());

	}
}
