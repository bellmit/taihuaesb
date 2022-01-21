package com.test;

import com.alibaba.fastjson.JSONObject;
import com.ufida.eip.core.MessageContext;
import com.ufida.eip.exception.EIPException;
import com.ufida.eip.java.IContextProcessor;
import commonj.sdo.DataObject;

public class DistributeTestService implements IContextProcessor {

	@Override
	public String handleMessageContext(MessageContext arg0) throws EIPException {
		// TODO Auto-generated method stub
		
		DataObject body = arg0.getBody();
		//获取主数据参数
		String data = body.getString("data");
		JSONObject obj = JSONObject.parseObject(data);
		JSONObject result = new JSONObject();
		result.put("masterData", obj.get("masterData"));
		result.put("mdType",obj.getString("mdType"));
		body.set("data",result);
		return null;
	}

}
