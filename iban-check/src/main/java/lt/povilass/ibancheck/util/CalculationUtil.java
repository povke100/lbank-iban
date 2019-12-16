package lt.povilass.ibancheck.util;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.data.IBAN;

@Slf4j
public class CalculationUtil {
	
	/**
	 * Method returns numerical expression of given IBAN
	 * @param iban - IBAN object
	 * @return rearranged numerical IBAN of null if iban contains any illegal characters
	 */
	public static String getNumericalIBAN(IBAN iban) {
		
		log.debug("getNumericalIBAN. IBAN:{}", iban);
		
		// rearranged iban (first 4 characters are moved to end)
		String rearr = iban.getBban() + iban.getCountryCode() + iban.getCheckDigits();
		log.debug("getNumericalIBAN. rearranged IBAN:{}", rearr);
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < rearr.length(); i++) {
			char c = rearr.charAt(i);
			
			//difference between Unicode value and character's mapped value for 
			if(Character.isUpperCase(c)) {
				int intValue = ((int)c) - 55;
				builder.append(intValue);
			} else if (Character.isDigit(c)) {
				builder.append(c);
			} else {
				log.error("getNumericalIBAN. IBAN contains illegal characters!");
				return null;
			}
		}
		return builder.toString();
	}
	
	/**
	 * Calculates remainder of mod 97 (base 10) of given IBAN 
	 * @param numericalIBAN numerical representation of IBAN (at least 10 digits long)
	 * @return remainder of numericalIBAN or -1 if numericalIBAN is shorter than 10 digits.
	 */
	public static int modulo97(String numericalIBAN) {
		
		log.debug("modulo97. numericalIBAN {}", numericalIBAN);
		int remainder = 0;
				
		String iban = numericalIBAN;
		String part = "";
		
		//taking first 9 digits form iban and calculating 
		if (iban.length() > 9) {
			part = iban.substring(0, 9);
			iban = iban.substring(9);
			
			remainder = Integer.valueOf(part) % 97;
		} else {
			log.error("modulo97. numericalIBAN {} is too short!", numericalIBAN);
			return -1;
		}
		
		//repeatedly 7 (or less) digits are selected until all digits are processed
		while (iban.length() > 0) {
			
			if (iban.length() >= 7) {	
				part = new StringBuilder().append(remainder).append(iban.substring(0, 7)).toString();
				iban = iban.substring(7);
			} else {
				part = new StringBuilder().append(remainder).append(iban.substring(0, iban.length())).toString();
				iban = iban.substring(iban.length());
			}
			
			remainder = Integer.valueOf(part) % 97;	
		}
		return remainder;
	}

}
