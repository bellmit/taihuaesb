package com.esb.distributefz.Util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
				
				if(isEmpty(i.getJSONArray("sub_material_childtable"))&&(i.containsKey("materialClass")&&i.getString("materialClass").equals("˿"))){
					source.put(i.getString("mdm_code"), "������û���ӱ��޷��·�����˼��֯");
					if(resultJson.getJSONArray("masterData").size()>0){
						iterator.remove();
					}	
				}
				
				if(!i.containsKey("originalMaterialCode")){
					source.put(i.getString("mdm_code"), "������û��ԭ���ϱ����޷��·�����˼��֯");
					if(resultJson.getJSONArray("masterData").size()>0){
						iterator.remove();
					}	
				}
				
				if (!i.getBoolean("isFZ")) {
					source.put(i.getString("mdm_code"), "�����ϲ���Ҫ�·�����˼��֯");
					if(resultJson.getJSONArray("masterData").size()>0){
						iterator.remove();
					}	
				}
			}
		}

		HttpPost post = new HttpPost(url);

		if (resultJson.getJSONArray("masterData") != null&& resultJson.getJSONArray("masterData").size() > 0) {
			// ѭ���������ݣ��ӱ����������Եļӵ����������档
			JSONArray array = resultJson.getJSONArray("masterData");
			JSONArray newArray = new JSONArray();
			Boolean isRepeat = false;
			
			for (Object i : array) {
				
				if (JSONObject.fromObject(i).containsKey("sub_material_childtable")&&!isEmpty(JSONObject.fromObject(i).getJSONArray("sub_material_childtable"))) {

					JSONObject json = JSONObject.fromObject(i);

					JSONArray subArray = json.getJSONArray("sub_material_childtable");
					String enablestate = json.getString("enableState");

					List batchArray = new ArrayList();
					List remarksArray = new ArrayList();

					for (Object j : subArray) {
						String batchNumber = "";
						String childRemarks = "";
						String materialNo= "";
						String yarnCount= "";
						String twistStrong= "";
						String factYarnCount= "";
						String sub_mdmcode = JSONObject.fromObject(j).getString("mdm_code");
						String sub_oricode = JSONObject.fromObject(j).getString("originalMaterialCode");
						StringBuffer mingBuffer = new StringBuffer(sub_oricode);

						if (JSONObject.fromObject(j).containsKey("batchNumber")) {
							batchNumber = JSONObject.fromObject(j).getString("batchNumber");
						}

						if (JSONObject.fromObject(j).containsKey("childRemarks")) {
							childRemarks = JSONObject.fromObject(j).getString("childRemarks");
							mingBuffer.append("/");
							mingBuffer.append(childRemarks);
						}
						//���ϱ���	
						if (JSONObject.fromObject(j).containsKey("materialNo")) {
							materialNo = JSONObject.fromObject(j).getString("materialNo");
						}
						
						//ɴ֧	
						if (JSONObject.fromObject(j).containsKey("yarnCount")) {
							yarnCount = JSONObject.fromObject(j).getString("yarnCount");
						}
						
						//���	
						if (JSONObject.fromObject(j).containsKey("twistStrong")) {
							twistStrong = JSONObject.fromObject(j).getString("twistStrong");
						}
						
						//�ۺ�֧��	
						if (JSONObject.fromObject(j).containsKey("factYarnCount")) {
							factYarnCount = JSONObject.fromObject(j).getString("factYarnCount");
						}

						json.put("batchNumber", batchNumber);
						json.put("childRemarks", childRemarks);
						json.put("childCode", JSONObject.fromObject(j).getString("mdm_code"));
						json.put("ming", mingBuffer.toString());
						json.put("materialNo", materialNo);
						json.put("yarnCount", yarnCount);						
						json.put("twistStrong", twistStrong);
						json.put("factYarnCount", factYarnCount);

						if (enablestate.equals("2")) {
							json.put("enableState", JSONObject.fromObject(j).getString("enableState"));
						} else {
							json.put("enableState", enablestate);
						}

						if (JSONObject.fromObject(j).containsKey("originalMaterialCode")) {
							json.put("originalMaterialCode",JSONObject.fromObject(j).getString("originalMaterialCode"));
						}
						// ͬһ�����£��ӱ������е��������Ժ�˵�����Բ������ظ���
						batchArray.add(json.getString("batchNumber"));
						remarksArray.add(json.getString("childRemarks"));

						if (hasDuplicate(batchArray)|| hasDuplicate(remarksArray)) {
							isRepeat = true;
							source.put(json.getString("mdm_code"),"�������ӱ����Ż�˵���ֶ��ظ�!");
						}

						newArray.add(json);
					}
				} else {
					newArray.add(JSONObject.fromObject(i));
				}

			}

			resultJson.put("masterData", newArray);

			try {
				if (!isRepeat) {
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
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

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

	private static boolean hasDuplicate(List objArray) {
		List<String> recordList = new ArrayList<>();
		for (int i = 0; i < objArray.size(); i++) {
			// ��������ڣ�����ӵ��б���
			if (!recordList.contains(objArray.get(i))) {
				recordList.add(objArray.get(i).toString());
			} else {
				// ��������ظ�ֵ����ֱ������������true
				return true;
			}
		}
		return false;
	}
	
	
	private static boolean isEmpty(JSONArray array){
		Boolean isempty = false;
		
		if(array.isEmpty()||array.size()<1){
			isempty = true;
		}
		
		return isempty;
	}

}
