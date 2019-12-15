package lt.povilass.ibancheck.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {
	
private static Properties properties = new Properties();
	
	static {
		try {	
			if(Files.exists(Paths.get("./currencyrates.properties"))) {
				log.debug("Loading properties from external file");
				properties.load(new FileInputStream("./currencyrates.properties"));
			} else {
				log.debug("Loading properties from classpath config file...");
				properties.load(Config.class.getClassLoader().getResourceAsStream("currencyrates.properties"));
			}
		} catch (IOException e) {
			log.error("Loading of properties has failed.", e);
		}
	}

}
