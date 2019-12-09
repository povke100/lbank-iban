package lt.povilass.currencyrates.util;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.currencyrates.data.FxRates;

@Slf4j
public class XmlUtil {
	
	private static JAXBContext context = null;
	private static Unmarshaller unmarshaller;
	
	static {
		try {
			context = JAXBContext.newInstance(FxRates.class);
			unmarshaller = context.createUnmarshaller();
		} catch (Exception e) {
			log.error("");
		}
	}
	
	public FxRates getRates(InputStream ratesXml) throws JAXBException {
		FxRates fxRates = (FxRates) unmarshaller.unmarshal(ratesXml);
		return fxRates;
	}
}
