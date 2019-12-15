package lt.povilass.currencyrates.network;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

public interface Connection {
	
	public HttpResponse sendPost(List<NameValuePair> payload) throws Exception;

}
