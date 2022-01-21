package com.esb.distributerz.Util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class HttpPostUtil {
	public static String doPost(JSONObject resultJson, String url) {
		HttpClient client = HttpClientBuilder.create().build();
		String result = null;
		HttpPost post = new HttpPost(url);

		if (resultJson.getJSONArray("masterData") != null
				&& resultJson.getJSONArray("masterData").size() > 0) {
			try {
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
		}

		return result.toString();
	}
}
