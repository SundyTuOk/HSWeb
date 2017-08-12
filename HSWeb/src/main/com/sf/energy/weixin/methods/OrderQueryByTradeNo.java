package com.sf.energy.weixin.methods;

import java.util.SortedMap;
import java.util.TreeMap;

import com.sf.energy.weixin.models.orderQueryMap;
import com.sf.energy.weixin.models.weixinParameters;
import com.sf.energy.weixin.utils.MapUtils;
import com.sf.energy.weixin.utils.ParametersUtil;
import com.sf.energy.weixin.utils.RequestHandler;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class OrderQueryByTradeNo {

	private static final weixinParameters wp = weixinParameters.getInstance();
	private static final String url="https://api.mch.weixin.qq.com/pay/orderquery"; 
	public static String orderQueryByTradeNo(String out_trade_no) {
		String appid = wp.getAppid();
		String appsecret = wp.getAppsecret();
		String mch_id = wp.getMch_id();//邮件里给的
		String pkey = wp.getKey();//商户平台里自己设的密钥
		String xmlParam = getxmlParam(appid, mch_id, appsecret, pkey, out_trade_no);
		String rString=(String) WeiXinOperatorUtil.doWeiXinOperation(url, xmlParam);
		System.out.println("out_trade_no:"+out_trade_no+" "+rString);
		return rString;
	}
	private static String getxmlParam(String appid, String mch_id,String appsecret, String pkey,String out_trade_no){
		String nonce_str = ParametersUtil.getNonce_str();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
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
						+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
						+ "</xml>";
		return xmlParam;
	}
	public static void main(String[] args) {
	
		String str =orderQueryByTradeNo("100275");
		System.out.println(str);
//		str = MapUtils.hideStrByMap(new orderQueryHideMap().getMap(), str);
//		System.out.println("final1 str:"+str);
//		str = MapUtils.specialDeal(str);
		//str = MapUtils.translateStrByMap(new orderQueryMap().getMap(), str);
//		System.out.println("final2 str:"+str);	
	}
}
