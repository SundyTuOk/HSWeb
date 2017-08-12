package com.sf.energy.weixin.interfaseImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.sf.energy.weixin.interfase.IreturnedDataParse;

public class parseStrToJsonArray implements IreturnedDataParse{

	public Object parseDataToObject(String data) {
		if(data==null||data.equals(""))
		{
			return null;
		}
		//data = data.replace("\'", "");
		/**
		 * 对账单查询结果若有账单
		 * 根据返回字符串格式需要做字符串切割处理
		 */		
		//1.对原始字符串根据换行切分成数组，但是第一次切分完的数组只有一个元素 需要再做处理
		String [] array = data.split("/n");
		//2.对第一步得来的数组的第一个元素再做切分即得到最终数组
		String [] arr = array[0].split("\r\n");
		JSONObject object = new JSONObject();
		object.put("type", "list");
		JSONArray jarr = new JSONArray();
		int n = arr.length;
		String head = arr[0];
		for(int i =1;i<n-2;i++){
			jarr.add(makeStrToJsonObject(head,arr[i]));
		}
		String totalhead = arr[n-2];
		String totaldata = arr[n-1];
		//jarr.add(makeStrToJsonObject(totalhead,totaldata));
		return new Gson().toJson(new Object[]{object,jarr,makeStrToJsonObject(totalhead,totaldata)});
	}
	
	private JSONObject makeStrToJsonObject(String head,String data){
		JSONObject obj = new JSONObject();
		String[] heads = head.split(",");
		String[] datas = data.split(",");
		if(heads.length>0&&datas.length>0){
			for(int i =0;i<heads.length;i++){
				obj.put(heads[i], datas[i].replace("`", ""));			
			}
		}
		return obj;
	}
}
