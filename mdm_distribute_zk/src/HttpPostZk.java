import java.nio.charset.Charset;
import javax.xml.namespace.QName;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import com.esb.distributezk.service.Snippet;
import com.ufida.eip.core.MessageContext;
import com.ufida.eip.exception.EIPException;
import com.ufida.eip.java.IContextProcessor;
import com.ufida.eip.sdo.SDOUtils;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

public class HttpPostZk implements IContextProcessor {

	@Override
	public String handleMessageContext(MessageContext arg0) throws EIPException {
		
		DataObject body = arg0.getBody();
		String data = body.getString("data");
		JSONObject obj =JSONObject.fromObject(data);
//		String url = "http://10.1.3.143:8090/api/receive/user/zk";
//		String result = new String();
//		result=Snippet.doPost(obj, url);
		
		QName qname = this.getRetQName();
	 	Type type = SDOUtils.getType(qname);
		DataObject data_ = SDOUtils.getHelperContext().getDataFactory()
				.create(type);
//		data_.set("returnArg",result);
		arg0.changeBody(qname, data_);
		return null;
	}
	
	
	
	protected QName getRetQName() {
		return new QName("http://service.distributezk.esb.com/Distributezk",
				"distributeResponse");
	}
	
}
