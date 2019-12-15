package lt.povilass.ibancheck.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.util.ConstUtil;

@Slf4j
public class Config {

	private static Properties properties = new Properties();
	
	private static final String CONGIF_FILE_NAME = "ibancheck.properties";
	
	private static String [] countryCodes = null;

	static {
		try {
			if (Files.exists(Paths.get("./" + CONGIF_FILE_NAME))) {
				log.debug("Loading properties from external file");
				properties.load(new FileInputStream("./" + CONGIF_FILE_NAME));
			} else {
				log.debug("Loading properties from classpath config file...");
				properties.load(Config.class.getClassLoader().getResourceAsStream(CONGIF_FILE_NAME));
			}
			
			String ccodes = properties.getProperty(ConstUtil.CFG_COUNTRY_CODES, ""); 
			if(ccodes.length() != 0) {
				countryCodes = ccodes.split(",");
			}
			
			
		} catch (IOException e) {
			log.error("Loading of properties has failed.", e);
		}
	}
	
	public String [] getCountryCodes() {
		return countryCodes;
	}
	
	public String getIBANLenghtByCountry(String countryCode) {
		return properties.getProperty(ConstUtil.CFG_IBAN_LENGHT + countryCode, "");
	}
	
	public String getBBANFormatByCountry(String countryCode) {
		return properties.getProperty(ConstUtil.CFG_IBAN_BBAN + countryCode, "");
	}

}
