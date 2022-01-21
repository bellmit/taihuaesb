package com.esb.distributerz.Util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class HttpPostMaterial {
	public static String doPost(JSONObject resultJson, String url) {
		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> source = new HashMap<String, Object>();
		Map<String, Object> target = new HashMap<String, Object>();
		String result = null;

		if (url.equals(BaseDataUrl.MaterialUrl)) {
			Iterator iterator = resultJson.getJSONArray("masterData")
					.iterator();
			while (iterator.hasNext()) {
				JSONObject i = (JSONObject) iterator.next();
				
				if(!i.containsKey("originalMaterialCode")){
					i.put("originalMaterialCode", i.getString("code"));
				}
				
				if (!i.getBoolean("isRZ")) {
					source.put(i.getString("mdm_code"), "该物料不需要下发至环思染整");
					iterator.remove();
				}
			}
		}

		HttpPost post = new HttpPost(url);

		// if (resultJson.getJSONArray("masterData") != null
		// && resultJson.getJSONArray("masterData").size() > 0) {
		// // 循环处理数据，子表有批号属性的加到主数据里面。
		// JSONArray array = resultJson.getJSONArray("masterData");
		// JSONArray newArray = new JSONArray();

		// for (Object i : array) {
		// if (JSONObject.fromObject(i).containsKey(
		// "sub_material_childtable")
		// && !ValueUtils.isEmpty(JSONObject.fromObject(i)
		// .getString("sub_material_childtable"))) {
		// JSONArray subArray = JSONObject.fromObject(i).getJSONArray(
		// "sub_material_childtable");
		// for (Object j : subArray) {
		// String batchNumber = "";
		// String childRemarks = "";
		//
		// if (JSONObject.fromObject(j).containsKey("batchNumber")) {
		// batchNumber = JSONObject.fromObject(j).getString(
		// "batchNumber");
		// }
		//
		// if (JSONObject.fromObject(j)
		// .containsKey("childRemarks")) {
		// childRemarks = JSONObject.fromObject(j).getString(
		// "childRemarks");
		// }
		//
		// JSONObject.fromObject(i)
		// .put("batchNumber", batchNumber);
		// JSONObject.fromObject(i).put("childremarks",
		// childRemarks);
		// newArray.add(JSONObject.fromObject(i));
		// }
		// } else {
		// newArray.add(JSONObject.fromObject(i));
		// }
		//
		// }
		//
		// resultJson.put("masterData", newArray);

		try {
			// 取子表数据，循环调用方法

			StringEntity s = new StringEntity(resultJson.toString(),
					Charset.forName("UTF-8"));
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		map = gson.fromJson(result, map.getClass());

		if (map != null) {
			target.putAll(map);
		}

		if (source != null) {
			target.putAll(source);
		}

		return target.toString();
	}
}
