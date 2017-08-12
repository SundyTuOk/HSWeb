package com.sf.energy.weixin.methods;

import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.weixin.interfaseImpl.parseXMLToJsonObj;
import com.sf.energy.weixin.models.orderQueryHideMap;
import com.sf.energy.weixin.models.orderQueryMap;
import com.sf.energy.weixin.models.weixinParameters;
import com.sf.energy.weixin.utils.MapUtils;
import com.sf.energy.weixin.utils.ParametersUtil;
import com.sf.energy.weixin.utils.RequestHandler;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class OrderQuery {

	private static final weixinParameters wp = weixinParameters.getInstance();
	private static final String url="https://api.mch.weixin.qq.com/pay/orderquery"; 
	public static String orderQueryV(String transaction_id) {
		String appid = wp.getAppid();
		String appsecret = wp.getAppsecret();
		String mch_id = wp.getMch_id();//邮件里给的
		String pkey = wp.getKey();//商户平台里自己设的密钥
		String xmlParam = getxmlParam(appid, mch_id, appsecret, pkey, transaction_id);
		String rString=(String) WeiXinOperatorUtil.doWeiXinOperation(url, xmlParam);
		//System.out.println(rString);
		rString = doubleCheckOrderNoReturn(transaction_id,rString);
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
	
	/**
	 * 根据订单查询返回结果中的订单号与查询输入订单号做比较
	 * 
	 * 微信订单号查询可能是模糊查询
	 * @param transaction_id
	 * @param str
	 * @return
	 */
	public static String doubleCheckOrderNoReturn(String transaction_id,String str){
		if(doubleCheckOrderNo(transaction_id,str)){
			return str;
		}else{
			if(str.indexOf("transaction_id")!=-1){
				str = "[{'type':'object'},{'result_code':'FAIL','sign':'','mch_id':'','err_code':'ORDERNOTCONSISTENT','err_code_des':'订单号有误','return_msg':'OK','appid':'','nonce_str':'','return_code':'SUCCESS'}]";
			}
			return str;
		}
	}
	private static boolean doubleCheckOrderNo(String transaction_id,String str){		
		if(str.indexOf("transaction_id")!=-1){
			JSONArray arr = JSONArray.fromObject(str);
			JSONObject object = (JSONObject) arr.get(1);
			String query_transaction_id = object.getString("transaction_id");
			if(query_transaction_id.equals(transaction_id)){
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
	
		String str =orderQueryV("40029220012001705161054231686");
		//System.out.println(str);
//		str = MapUtils.hideStrByMap(new orderQueryHideMap().getMap(), str);
//		System.out.println("final1 str:"+str);
		//str = MapUtils.specialDeal(str);
		//str = MapUtils.translateStrByMap(new orderQueryMap().getMap(), str);
//		System.out.println("final2 str:"+str);
		System.out.println(str);
		
	}
}
