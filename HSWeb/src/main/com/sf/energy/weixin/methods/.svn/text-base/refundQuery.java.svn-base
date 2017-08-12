package com.sf.energy.weixin.methods;

import java.util.SortedMap;
import java.util.TreeMap;

import com.sf.energy.weixin.models.weixinParameters;
import com.sf.energy.weixin.utils.ParametersUtil;
import com.sf.energy.weixin.utils.RequestHandler;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class refundQuery
{
	private static final weixinParameters wp = weixinParameters.getInstance();
	private static final String url="https://api.mch.weixin.qq.com/pay/refundquery"; 
	public static String refundQuery(String transaction_id) {
		String appid = wp.getAppid();
		String appsecret = wp.getAppsecret();
		String mch_id = wp.getMch_id();//邮件里给的
		String pkey = wp.getKey();//商户平台里自己设的密钥
		String xmlParam = getxmlParam(appid, mch_id, appsecret, pkey, transaction_id);
		String rString=(String) WeiXinOperatorUtil.doWeiXinOperation(url, xmlParam);
		//System.out.println(rString);
		return rString;
	}
	private static String getxmlParam(String appid, String mch_id,String appsecret, String pkey,String transaction_id){
		String nonce_str = ParametersUtil.getNonce_str();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("transaction_id", transaction_id);
		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, pkey);
		String sign = reqHandler.createSign(packageParams);
		String xmlParam = 
				"<xml>" 
						+ "<appid>" + appid + "</appid>" 
						+ "<mch_id>" + mch_id + "</mch_id>" 
						+ "<nonce_str>" + nonce_str + "</nonce_str>" 
						+ "<sign><![CDATA[" + sign + "]]></sign>"
						+ "<transaction_id>" + transaction_id + "</transaction_id>"
						+ "</xml>";
		return xmlParam;
	}
	public static void main(String[] args)
	{
		String string = refundQuery("4007862001201608070784641772");
		System.out.println(string);
	}
}
