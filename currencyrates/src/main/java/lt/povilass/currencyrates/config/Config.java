package lt.povilass.currencyrates.config;

import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.currencyrates.util.ConstantUtil;

@Slf4j
public class Config {
	
	private Properties properties;
	
	public Config(String configPath) throws IOException {
		log.debug("");
		properties = new Properties();
		properties.load(Config.class.getClassLoader().getResourceAsStream("currencyrates.properties"));
	}
	
	public String getConnectionTimeout() {
		return properties.getProperty(ConstantUtil.CGF_CONNECTION_TIMEOUT, "5000");
	}
	
	public String getSocketTimeout() {
		return properties.getProperty(ConstantUtil.CFG_SOCKET_TIMEOUT, "5000");
	}
	
	public String getLBEndpoint() {
		return properties.getProperty(ConstantUtil.CFG_LB_ENDPOINT, "");
	}
	
	
	public String getCurrencies() {
		return properties.getProperty(ConstantUtil.CFG_CURRENCIES, "");
	}
}