package com.sf.energy.weixin.utils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.sf.energy.project.equipment.dao.AmmeterDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 根据map中的内容翻译或屏蔽原字符串中内容
 * @author Administrator
 *str : [{"type":"object"},{"return_msg":"invalid bill_type","return_code":"FAIL"}]
 */
public class MapUtils
{
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static DecimalFormat dfd = new DecimalFormat("0.00");
	public static String translateStrByMap(Object o,String str){
		Map<String, String> map = (Map<String, String>) o;
		//String rstr=null;
		for(Entry<String, String> entry : map.entrySet()){
			if(str.indexOf(entry.getKey())!=-1){
				str=str.replace(entry.getKey(), entry.getValue());
			}
		}
		return str;
	}
	
	public static String hideStrByMap(Object o,String str){
		Map<String, String> map = (Map<String, String>) o;
		JSONArray array = JSONArray.fromObject(str);
		if(array.size()==2){
			JSONObject object = array.getJSONObject(1);
			for(Entry<String, String> entry : map.entrySet()){
				if(object.containsKey(entry.getKey())){
					object.remove(entry.getKey());
				}
			}
		}
		return array.toString();
	}
	
	public static String specialDeal(String str){
		JSONArray array = JSONArray.fromObject(str);
		if(array.size()==2){
			JSONObject object = array.getJSONObject(1);
			if(object.containsKey("time_end")){
				try
				{
					object.put("time_end", df.format(df1.parse((String) object.get("time_end"))));
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
			if(object.containsKey("total_fee")){
				object.put("total_fee", dfd.format(
						Double.parseDouble(
								(String) object.get("total_fee")
								)/100));
			}
			if(object.containsKey("cash_fee")){
				object.put("cash_fee", dfd.format(
						Double.parseDouble(
								(String) object.get("cash_fee")
								)/100));
			}
			if(object.containsKey("refund_fee")){
				object.put("refund_fee", dfd.format(
						Double.parseDouble(
								(String) object.get("refund_fee")
								)/100));
			}
			if(object.containsKey("attach")){
				try
				{
					object.put("attach",  new AmmeterDao().queryNameById(Integer.parseInt(object.getString("attach"))));
				} catch (NumberFormatException | SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return array.toString();
	}
	public static void main(String[] args) throws ParseException
	{
		System.out.println(df.format(df1.parse("20160716222555")));
	}
}
