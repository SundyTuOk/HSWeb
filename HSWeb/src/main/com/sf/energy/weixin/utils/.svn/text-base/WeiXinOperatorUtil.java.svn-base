package com.sf.energy.weixin.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.sf.energy.prepayment.dao.SaleDetailsDao;
import com.sf.energy.prepayment.model.SaleDetailsModel;
import com.sf.energy.weixin.interfaseImpl.parseStrToJsonArray;
import com.sf.energy.weixin.interfaseImpl.parseXMLToJsonObj;
import com.sf.energy.weixin.utils.http.HttpClientConnectionManager;

public class WeiXinOperatorUtil {
	public static Object doWeiXinOperation(String url, String xmlParam) {
		Object rObject = new Object();
		String jsonStr = callWeixinAPI(url,xmlParam);
		rObject = resultDataOperator(jsonStr);
		return rObject;
	}
	public static String callWeixinAPI(String url, String xmlParam){
		String jsonStr=null;
		DefaultHttpClient httpclient;
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			//rObject = resultDataOperator(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonStr;
	}
	public static String resultDataOperator(String str){
		String rstr = null;
		//判断返回结果是xml格式还是文本格式
		if(str.equals("")||str==null){
			JSONObject object1 = new JSONObject();
			object1.put("type", "object");
			JSONObject object = new JSONObject();
			object.put("result_code", "FAIL");
			object.put("err_code_des", "查询异常");
			return new Gson().toJson(new Object[]{object1,object});
		}else if(str.indexOf("<xml>")!=-1){
			/**
			 * 微信订单查询和查询结果异常是返回xml格式数据
			 */
			rstr = (String) new parseXMLToJsonObj().parseDataToObject(str);
			//rstr = checkObjectState(rstr);
		}else{
			/**
			 * 日对账单查询正常返回类似excel表格数据
			 * 表头——每一项属性内容
			 */
			rstr = (String) new parseStrToJsonArray().parseDataToObject(str);
			//rstr = checkArrayState(rstr);
		}
		return rstr;
	}
	/**
	 *  判断字符串是数组还是对象
	 * @param str
	 * @return
	 */
	public static String ckeckResultState(String str){
		if(str.indexOf("list")!=-1){
			str = checkArrayState(str);
		}else if(str.indexOf("object")!=-1){
			str = checkObjectState(str);
		}
		return str;
	}
	
	/**
	 * 检验每条订单记录在数据库中是否存在
	 * 屏蔽部分字段属性
	 * @param str
	 * @return
	 */
	public static String checkArrayState(String str){
		str = str.replace("设备号", "数据库状态");
		JSONArray arr = new JSONArray().fromObject(str);
		String type = arr.getJSONObject(0).getString("type");
		if(type.equals("list")){
			JSONArray datas = arr.getJSONArray(1);
			for(int i=0;i<datas.size();i++){
				JSONObject object = datas.getJSONObject(i);
				object.remove("公众账号ID");
				object.remove("商户号");
				object.remove("子商户号");
				if(checkOrderExitInDB(object.getString("微信订单号"))){
					object.put("数据库状态", "订单存在");

				}else{
					object.put("数据库状态", "订单不存在");
				}
			}
			return arr.toString();
		}else {
			return str;
		}
	}
	public static String checkObjectState(String str){
		JSONArray arr = new JSONArray().fromObject(str);	
		JSONObject object = arr.getJSONObject(1);
		if(object.containsKey("transaction_id")){
			String orderNo = object.getString("transaction_id");
			SaleDetailsModel sModel = new SaleDetailsDao().queryDetailByOrderNo(orderNo);
			if(sModel!=null){
				object.put("数据库状态", "订单存在");
				object.put("实际送电房间号",sModel.getAmMeter_Name());

			}else{
				object.put("数据库状态", "订单不存在");
			}
			return arr.toString();
		}else {
			return str;
		}
	}
	/**
	 * 检验微信订单在数据库中是否存在
	 * 存在则正常
	 * 不存在为异常
	 * @param orderNo
	 * @return
	 */
	public static boolean checkOrderExitInDB(String orderNo){
		SaleDetailsDao sDao = new SaleDetailsDao();
		SaleDetailsModel sModel = sDao.queryDetailByOrderNo(orderNo);
		if(sModel!=null){
			return true;
		}
		return false;
	}
	public static void main(String[] args)
	{
		JSONArray array = JSONArray.fromObject("[{'type':'object'},{'业务结果':'FAIL','错误代码':'SYSTEMERROR','错误代码描述':'当前使用此业务的用户较多，请稍后再试。','返回信息':'OK','返回状态码':'SUCCESS'}]");
		JSONObject object = array.getJSONObject(1);
		object.accumulate("错误代码", "订单不存在");
		System.out.println(array.toString());
	}
}
