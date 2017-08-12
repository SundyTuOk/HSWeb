package com.sf.energy.weixin.methods;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sf.energy.weixin.dao.weixinDao;
import com.sf.energy.weixin.models.billModel;
import com.sf.energy.weixin.models.domwLoadBillMap;
import com.sf.energy.weixin.models.weixinParameters;
import com.sf.energy.weixin.utils.MapUtils;
import com.sf.energy.weixin.utils.ParametersUtil;
import com.sf.energy.weixin.utils.RequestHandler;
import com.sf.energy.weixin.utils.WeiXinOperatorUtil;

public class downloadbill {

	private static final String url="https://api.mch.weixin.qq.com/pay/downloadbill";
	private static final weixinParameters wp = weixinParameters.getInstance();
	public static String checkbill(String queryDate,String queryType) {	
		String rString=billQuery(queryDate,queryType);
		rString = WeiXinOperatorUtil.ckeckResultState(rString);
		return rString;
	}
	public static String billQuery(String queryDate,String queryType) {
		String appid = wp.getAppid();
		String appsecret = wp.getAppsecret();
		String mch_id = wp.getMch_id();//邮件里给的
		String pkey = wp.getKey();//商户平台里自己设的密钥
		String xmlParam = getxmlParam(appid,mch_id,appsecret,pkey,queryDate,queryType);
		String rString=(String) WeiXinOperatorUtil.doWeiXinOperation(url, xmlParam);
		//System.out.println(rString);
		return rString;
	}
	private static String getxmlParam(String appid, String mch_id,String appsecret, String pkey, String queryDate, String queryType){
		String nonce_str = ParametersUtil.getNonce_str();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("bill_date", queryDate);
		packageParams.put("bill_type", queryType);
		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, pkey);
		String sign = reqHandler.createSign(packageParams);
		String xmlParam = 
				"<xml>" 
						+ "<appid>" + appid + "</appid>" 
						+ "<mch_id>"+ mch_id + "</mch_id>" 
						+ "<nonce_str>" + nonce_str + "</nonce_str>" 
						+ "<sign><![CDATA[" + sign + "]]></sign>"
						+ "<bill_date><![CDATA["+queryDate+"]]></bill_date>"
						+ "<bill_type>"+queryType+"</bill_type>"
						+ "</xml>";
		return xmlParam;
	}
	/**
	 * 将查询的两天的数据整合到一起
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static String addDatas(String data1,String data2){
		String rdata = null;
		if(data1!=null&&data2!=null){
			JSONArray arr1 = JSONArray.fromObject(data1);
			JSONArray arr2 = JSONArray.fromObject(data2);
			//System.out.println(data1);
			//System.out.println(data2);
			String type1 = arr1.getJSONObject(0).getString("type");
			String type2 = arr2.getJSONObject(0).getString("type");
			if(type1.equals("object")){
				if(type2.equals("object")){
					return data1;
				}else{
					return data2;
				}
			}else if(type2.equals("object")){
				return data1;	
			}else{
				JSONArray rArray = JSONArray.fromObject(data1);
				rArray.getJSONArray(1).addAll(arr2.getJSONArray(1));
				JSONObject object = addObject(arr1.getJSONObject(2),arr2.getJSONObject(2));
				rArray.remove(2);
				rArray.add(object);
				rdata = rArray.toString();
			}
		}else{
			return (data1!=null)?data1:data2;
		}
		return rdata;
	}
	/**
	 * 将两个jsonobject对象的相同属性值相加组成新的对象
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private static JSONObject addObject(JSONObject obj1,JSONObject obj2){
		JSONObject object = new JSONObject();
		if(obj1==null){
			if(obj2!=null){
				return obj2;
			}else{
				return null;
			}
		}else if(obj2==null){
			return obj1;
		}else{
			Iterator<String> iterator = obj1.keys();
			while (iterator.hasNext())
			{
				String key = (String) iterator.next();
				object.put(key, obj1.getDouble(key)+obj2.getDouble(key));
			}
		}
		return object;
	}
	/**
	 * 先查询数据库中是否存在当天数据
	 * 有则直接返回 没有则先查询保存数据库再返回
	 * @param date
	 * @return
	 */
	public static String getBillData(String date){
		weixinDao wDao = new weixinDao();
		billModel model = wDao.getDayBill(date);
		if(model!=null){
			return model.getDatas();
		}else{//只保存 ALL 全部订单
			String data = billQuery(date, "ALL");
			data = WeiXinOperatorUtil.ckeckResultState(data);
			JSONArray array = new JSONArray().fromObject(data);
			if(array.getJSONObject(0).getString("type").equals("list")){
				wDao.insertOneRecord(date, data);
			}			
			return data;
		}
	}
	public static void main(String[] args) {
		String str = downloadbill.billQuery("20161108","ALL");
//		JSONArray array = JSONArray.fromObject(str);
//		JSONArray array2 = array.getJSONArray(1);
//		StringBuffer s = new StringBuffer();
//		for(int i =0;i<array2.size();i++){
//			//s.append("'"+array2.getJSONObject(i).getString("微信订单号")+"',");
//			System.out.println("insert into weixinOrderTemp(orderNo) values('"+array2.getJSONObject(i).getString("微信订单号")+"');");
//		}
		
		//System.out.println(array2.size());
		//System.out.println(s.toString());	
		str = MapUtils.translateStrByMap(new domwLoadBillMap().getMap(), str);
		System.out.println(str);
	}
}
