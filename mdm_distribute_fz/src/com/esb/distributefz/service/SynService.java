package com.esb.distributefz.service;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.esb.distributefz.Util.BaseDataUrl;
import com.esb.distributefz.Util.HttpPostMaterial;
import com.esb.distributefz.Util.HttpPostUtil;
import com.ufida.eip.core.MessageContext;
import com.ufida.eip.exception.EIPException;
import com.ufida.eip.java.IContextProcessor;
import com.ufida.eip.sdo.SDOUtils;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

public class SynService implements IContextProcessor {

	@Override
	public String handleMessageContext(MessageContext arg0) throws EIPException {

		// TODO Auto-generated method stub供应商客户同步纺织环思

		DataObject body = arg0.getBody();
		String data = body.getString("data");
		JSONObject obj = JSONObject.fromObject(data);

		JSONArray array = obj.getJSONArray("masterData");
		String url = null;

		switch (body.getString("mdType")) {
		case "customer":
			url = BaseDataUrl.CusUrl;
			break;
		case "supplier":
			url = BaseDataUrl.SupUrl;
			break;
		case "material":
			url = BaseDataUrl.MaterialUrl;
		}

		for (Object json : array) {
			if ((url.equals(BaseDataUrl.CusUrl) || url
					.equals(BaseDataUrl.SupUrl))
					&& !JSONObject.fromObject(json).containsKey("shortname")) {
				((JSONObject) json).put("shortname", JSONObject
						.fromObject(json).getString("name"));
			}

			if (url.equals(BaseDataUrl.SupUrl)) {
				((JSONObject) json).put("supplierType", "03");
			}

			if (url.equals(BaseDataUrl.MaterialUrl)) {
				Map map = filedReplenish(JSONObject.fromObject(json));
				((JSONObject) json).put("isFZ", map.get("isFZ"));
				((JSONObject) json).put("materialClass",
						map.get("materialClass"));
			}
		}

		String result = new String();

		if (url.equals(BaseDataUrl.MaterialUrl)) {
			result = HttpPostMaterial.doPost(obj, url);
		} else {
			result = HttpPostUtil.doPost(obj, url);
		}

		QName qname = this.getRetQName();
		Type type = SDOUtils.getType(qname);
		DataObject data_ = SDOUtils.getHelperContext().getDataFactory()
				.create(type);
		data_.set("returnArg", result);
		arg0.changeBody(qname, data_);
		return null;
	}

	protected QName getRetQName() {
		return new QName(
				"http://service.distributefangzhi.esb.com/DistributeFZ",
				"distributeResponse");
	}

	public Map filedReplenish(JSONObject target) {

		String materialClass_code = JSONObject
				.fromObject(target.getString("materialClass_entity"))
				.getString("code").substring(0, 2);
		Map map = new HashMap();
		if (Integer.parseInt(materialClass_code) >= 36
				&& Integer.parseInt(materialClass_code) <= 46) {
			map.put("materialClass", "布");
			map.put("isFZ", true);
		} else if (Integer.parseInt(materialClass_code) >= 28
				&& Integer.parseInt(materialClass_code) <= 35) {
			map.put("materialClass", "丝");
			map.put("isFZ", true);
		} else {
			map.put("isFZ", false);
		}
		return map;

	}

}
