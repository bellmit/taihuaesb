package com.esb.distributefz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.esb.distributefz.Vo.DistributeRetVO;
import com.esb.distributefz.Vo.MdMapingVO;
import com.google.gson.Gson;
import com.ufida.eip.core.MessageContext;
import com.ufida.eip.exception.EIPException;
import com.ufida.eip.java.IContextProcessor;
import com.ufida.eip.sdo.SDOUtils;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

public class Result implements IContextProcessor {

	@Override
	public String handleMessageContext(MessageContext arg0) throws EIPException {
		// TODO Auto-generated method stub
		String returnArg = arg0.getBody().getString("returnArg");
		List<MdMapingVO> mdMapings = new ArrayList<MdMapingVO>();
		Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(returnArg, map.getClass());
        
        for(String key:map.keySet()){
        	String value = map.get(key).toString();
        	MdMapingVO mapping = new MdMapingVO();
        	mapping.setMdmCode(key);
			if(value.equals("success")){
                mapping.setSuccess(true);
                mapping.setMessage("分发成功");
			}else if(value.equals("该物料不需要下发至环思纺织")){
				mapping.setSuccess(true);
                mapping.setMessage(value);
			}else if(value.equals("该物料没有原物料编码无法下发至环思纺织")){
				mapping.setSuccess(true);
                mapping.setMessage(value);
			}else{
                mapping.setSuccess(false);
                mapping.setMessage(value);
			}
			
			mdMapings.add(mapping);
        }
        
        JSONObject retVO = returnsFormat(mdMapings);
        
        arg0.getBody().set("returnArg",retVO.toString());

		return null;
	}
	
	
	public JSONObject returnsFormat(List resultList){
		
		DistributeRetVO retVO = new DistributeRetVO();
		JSONObject jsonObject = new JSONObject();
		JSONObject result = new JSONObject();
		retVO.setMdMappings(resultList);
		JSONArray jsonArray = JSONArray.fromObject(JSON.toJSONString(retVO.getMdMappings()));
		
		for(int i=0;i<jsonArray.size();i++){
			jsonObject=jsonArray.getJSONObject(i);
			if(jsonObject.getString("success").equals("false")){
				result.put("false",jsonObject.getString("message"));
			}
		}
		
		if(result.containsKey("false")){
			retVO.setSuccess(false);
			retVO.setMessage(result.getString("false"));
		}else{
			retVO.setSuccess(true);
			retVO.setMessage("分发成功");
		}
		
		return JSONObject.fromObject(retVO);
	}

}
