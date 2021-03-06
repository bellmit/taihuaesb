package com.esb.distribute.service;

import java.lang.reflect.Field;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ElementFieldService {
	public String exchangeParam(JSONArray picArray,String mdType){
		JSONArray resultArray = new JSONArray();
		
		Class clazz = null;
		
		switch(mdType){
			case "department":
				clazz = DeptVO.class;
				break;
			case "person":
				clazz = PsndocVO.class;
				break;
		}
		
		System.out.println(picArray);
		for(Object object : picArray){
			JSONObject jsonObject = JSONObject.parseObject( object.toString());
			JSONObject resultJson = exchange(clazz,jsonObject);
			
			//调用下游接口
			if(clazz==DeptVO.class){
				JSONObject result = HttpConnectionUtil.doPost(BaseDataUrl.DEPT_URL, resultJson);
			}else if(clazz==PsndocVO.class){
				JSONObject result = HttpConnectionUtil.doPost(BaseDataUrl.DEPT_URL, resultJson);

			}
			
			resultArray.add(resultJson);
		}
		return resultArray.toString();
	}
	
	
	public JSONObject exchange(Class<?> targetCls, JSONObject jsonObject){
		Field[] targetFields = targetCls.getDeclaredFields();
        JSONObject target = new JSONObject();
        
        for (Field field : targetFields) {
        	ElementField elementField = field.getAnnotation(ElementField.class);
            if (null == elementField) continue;

            // 属性名
            String filedName = field.getName();
            // key
            String key = elementField.key();
            
            String keyName = StringUtils.isEmpty(key) ? filedName : key;
            
            Object value = jsonObject.get(filedName);
            target.put(keyName, value);
            
         // 参照是否取entity中字段, 当不需要时, 此字段有值
            String isEntity = elementField.isEntity();
            // 参照key
            String reference = elementField.reference();

            if (StringUtils.isEmpty(isEntity)) {
                JSONObject entity = jsonObject.getJSONObject(filedName + "_entity");
                if (MapUtils.isEmpty(entity)) 
                	continue;
                value = entity.getString(reference);
            }
            target.put(keyName, value);
        }
		return target;	
	}
	
	
	public void write(JSONArray picArray,String mdType){
		JSONArray resultArray = new JSONArray();
		Class clazz = null;
		System.out.print(mdType);
		switch(mdType){
			case "department":
				clazz = DeptVO.class;
				break;
			case "person":
				clazz = PsndocVO.class;
				break;
			
		}
		
		System.out.println("1234567899");
	}
	
}
