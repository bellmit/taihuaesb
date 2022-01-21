package com.esb.distribute.service;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public  class Snippet {
	public static String doPost(JSONObject resultJson,String url) {
	    HttpClient client = HttpClientBuilder.create().build();
//	    String url = null;

	
	    HttpPost post = new HttpPost(url);
	    String result = null;
	
	    try {
	        StringEntity s = new StringEntity(resultJson.toString(), Charset.forName("UTF-8"));
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
	    return result;
	}
}

