package com.sf.energy.weixin.interfaseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;
import com.sf.energy.weixin.interfase.IreturnedDataParse;
import com.sf.energy.weixin.utils.XMLUtil;

public class parseXMLToJsonObj implements IreturnedDataParse{

	public Object parseDataToObject(String data) {
		if (null == data || "".equals(data)) {
			return null;
		}

		Map m = new HashMap();
		try {
			InputStream in = XMLUtil.String2Inputstream(data);
			
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			doc = builder.build(in);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if (children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = XMLUtil.getChildrenText(children);
				}
				m.put(k, v);
				in.close();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject object = new JSONObject();
		object.put("type", "object");
		return new Gson().toJson(new Object[]{object,m});

	}

}
