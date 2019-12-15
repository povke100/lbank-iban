package lt.povilass.ibancheck.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Data class of   
 * @author Povilas
 *
 */
@Data 
@Slf4j
public class IBAN {
	
	private String countryCode;
	private String checkDigits;
	private String bban;
	
	private boolean isValid = true;
	
	public IBAN(String iban) {
		
		if(iban.length() > 4) {
			this.countryCode = iban.substring(0, 2);
			this.countryCode = iban.substring(2, 4);
			this.bban = iban.substring(4);
		} else {
			log.debug(" IBAN {} is too short, setting empty values.");
			this.countryCode = this.checkDigits = this.bban = "";
		}
		
	}
	
	public String getIBAN() {
		return new StringBuilder().append(countryCode).append(checkDigits).append(bban).toString();
	}

}
