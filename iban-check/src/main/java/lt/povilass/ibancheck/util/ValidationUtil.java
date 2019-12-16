package lt.povilass.ibancheck.util;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.config.Configuration;
import lt.povilass.ibancheck.data.IBAN;

@Slf4j
public class ValidationUtil {

	/**
	 * Check if IBAN is valid 
	 * @param iban - IBAN object
	 * @return true if remainder of (numericalIBAN mod 97) equals 1, false if otherwise.
	 */
	public static boolean validateIBAN(IBAN iban) {
		
		log.debug("validateIBAN. IBAN: {}", iban);
		
		String numericalIBAN = CalculationUtil.getNumericalIBAN(iban);
		int rem = CalculationUtil.modulo97(numericalIBAN);
		
		if(rem == 1) {
			log.debug("validateIBAN. Remainder for IBAN {} is 1", iban.getIBAN());
			return true;
		}
		log.debug("validateIBAN. Remainder for IBAN {} is {}", iban.getIBAN(), rem);
		return false;
	}

	/**
	 * Checks if generated check digits are equal with one in IBAN
	 * @param iban - IBAN object
	 * @return true if generated check digits are equal with given ones, false if otherwise
	 */
	public static boolean validateCheckDigits(IBAN iban) {
		log.debug("validateCheckDigits. IBAN: {}", iban);
		
		//replacing check digits with 00 for generation of check digits
		IBAN zerocheck = new IBAN (iban.getIBAN());
		zerocheck.setCheckDigits("00");
		
		String numericalIBAN = CalculationUtil.getNumericalIBAN(zerocheck);
		int rem = CalculationUtil.modulo97(numericalIBAN);
		
		int digits = 98 - rem;

		log.debug("validateCheckDigits");
		if(digits == Integer.valueOf(iban.getCheckDigits())) {
			log.debug("validateCheckDigits. Check digits for IBAN: {} are equal.", iban.getIBAN());
			return true;
		}
		
		log.debug("validateCheckDigits. Check digits for IBAN: {} are NOT equal.", iban.getIBAN());
		return false;
	}

	/**
	 * Method checks if given BBAN format is correct for given country
	 * 
	 * @param bban - IBAN's BBAN part (IBAN without countrycode and check digits)
	 * @param ccode - Country code
	 * @return true if BBAN matched the configured format, returns false if
	 *         otherwise.
	 */
	public static boolean validateBBAN(String bban, String ccode) throws Exception {

		log.debug("validateBBAN. BBAN:{}, Country code: {}", bban, ccode);

		String[] format = Configuration.getBBANFormatByCountry(ccode).split(",");
		if (format.length == 0) {

			log.error("validateBBAN. BBAN configuration for country {} was not found!", ccode);
			throw new Exception("BBAN configuration for country " + ccode + " was not found!");
		}

		log.debug("validateBBAN. format: {}", Configuration.getBBANFormatByCountry(ccode));
		
		int beginIndex = 0;

		// if configuration is found every section of BBAN is checked
		for (String section : format) {

			String secLenghtS = section.substring(0, section.length() - 1);
			String secFormat = section.substring(section.length() - 1);

			if (!secLenghtS.matches(ConstUtil.RGX_NUMERICAL) || !secFormat.matches(ConstUtil.RGX_BBAN_FORMAT)) {

				log.error("validateBBAN. Invalid BBAN configuration {} for {}", section, ccode);
				throw new Exception("Invalid BBAN configuration " + section + " for country: " + ccode);
			}

			int secLenght = Integer.valueOf(secLenghtS);

			switch (secFormat) {
			case ConstUtil.ALPHABETICAL:
				if (!bban.substring(beginIndex, beginIndex+secLenght).matches(ConstUtil.RGX_ALPHABETICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			case ConstUtil.ALPHANUMERICAL:
				if (!bban.substring(beginIndex, beginIndex+secLenght).matches(ConstUtil.RGX_ALPHANUMERICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			case ConstUtil.NUMERICAL:
				if (!bban.substring(beginIndex, beginIndex+secLenght).matches(ConstUtil.RGX_NUMERICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			}
		}
		log.debug("validateBBAN. BBAN {} for ountry {} meets the format!", bban, ccode);
		return true;
	}
	
	
	/**
	 * Checks if given IBAN has correct length
	 * @param iban IBAN to be checked
	 * @param ccode Contry code 
	 * @return true if lenght of iban is equals 
	 * @throws Exception if lenght for given country code is missing or 
	 */
	public static boolean validateLength(String iban, String ccode) throws Exception {

		String ibanLenght = Configuration.getIBANLenghtByCountry(ccode);
		if (ibanLenght.length() == 0 || !ibanLenght.matches(ConstUtil.RGX_NUMERICAL)) {
			log.error("validateLength. Missing or invalid iban lenght parameter for country: {}!", ccode);
			throw new Exception("Missing or invalid iban lenght parameter for country: " + ccode);
		}
		boolean valid = iban.length() == Integer.valueOf(ibanLenght);
		if(valid) {
			log.debug("validateLength. IBAN {} length for country {} is valid.", iban, ccode);
		} else {
			log.debug("validateLength. IBAN {} length for country {} is NOT valid.", iban, ccode);
		}
		
		return valid;
	}

	/**
	 * Checks if IBAN has a valid country code
	 * @param ccode country code to check
	 * @return true if given country code was found in configuration, false if otherwise.
	 * @throws Exception if no countries are configured
	 */
	public static boolean validateCountryCode(String ccode) throws Exception {

		if (Configuration.getCountryCodes().length == 0) {
			log.error("validateCountryCode. No countries were configured!");
			throw new Exception("No countries were configured!");
		}

		boolean valid = Arrays.stream(Configuration.getCountryCodes()).anyMatch(ccode::equals);
		if (valid) {
			log.debug("validateCountryCode. Match is found for country code: {}", ccode);
		} else {
			log.debug("validateCountryCode. No match is found for country code: {}", ccode);
		}
		
		return valid;
	}

}
