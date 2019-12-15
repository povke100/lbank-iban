package lt.povilass.currencyrates.network;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import lt.povilass.currencyrates.config.Config;

public class ConnectionImpl implements Connection{

	@Override
	public HttpResponse sendPost(List<NameValuePair> payload) throws Exception {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost request = new HttpPost(Config.getLBEndpoint());
		
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(Integer.valueOf(Config.getConnectionTimeout()))
				.setSocketTimeout(Integer.valueOf(Config.getSocketTimeout()))
				.build();
				
		 
		request.setConfig(config);
		
		request.addHeader("Content-type", "application/x-www-form-urlencoded");
		request.setEntity(new UrlEncodedFormEntity(payload));
		
		CloseableHttpResponse response = client.execute(request);
		
		client.close();
		
		return response;
	}


}
