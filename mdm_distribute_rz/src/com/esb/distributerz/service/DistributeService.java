package com.esb.distributerz.service;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ufida.eip.core.MessageContext;
import com.ufida.eip.exception.EIPException;
import com.ufida.eip.java.IContextProcessor;
import com.ufida.eip.log.EIPLogger;

import commonj.sdo.DataObject;

public class DistributeService implements IContextProcessor {

	@Override
	public String handleMessageContext(MessageContext arg0) throws EIPException {
		// TODO Auto-generated method stub
		DataObject body = arg0.getBody();
		//��ȡ�����ݲ���
		String data = body.getString("data");
		JSONObject obj =JSONObject.fromObject(data);
		
		EIPLogger.info("1��ģ�飺��WeChat�����ַ�����Ϊ:" + obj);
		
		JSONObject result = new JSONObject();
		String mdType = obj.getString("mdType");
	 	String masterData = obj.getString("masterData");
	 	JSONArray picArray = JSONArray.fromObject(masterData);
		result.put("masterData", picArray);
		result.put("mdType",mdType);
		body.set("data",result.toString());
		body.set("mdType", mdType);
		EIPLogger.info("�����νӿڷ�������");
		return null;
	}

}
