package lt.povilass.ibancheck.util;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.config.Configuration;
import lt.povilass.ibancheck.data.IBAN;

@Slf4j
public class ValidationUtil {

	public static boolean validateIBAN(IBAN iban) {

		return false;
	}

	public static boolean validateCheckDigits(IBAN iban) {

		return false;
	}

	/**
	 * Method checks if given BBAN format is correct for given country
	 * 
	 * @param iban
	 * @return true if BBAN matched the configured format, returns false if
	 *         otherwise.
	 */
	public static boolean validateBBAN(IBAN iban) throws Exception {

		log.debug("validateBBAN. IBAN:{}", iban);

		String[] format = Configuration.getBBANFormatByCountry(iban.getCountryCode()).split(",");
		if (format.length == 0) {

			log.error("validateBBAN. BBAN configuration for country {} was not found!", iban.getCountryCode());
			throw new Exception("BBAN configuration for country " + iban.getCountryCode() + " was not found!");
		}

		int beginIndex = 0;

		// if configuration is found every section of BBAN is checked
		for (String section : format) {

			String secLenghtS = section.substring(0, section.length() - 1);
			String secFormat = section.substring(section.length() - 1);

			if (!secLenghtS.matches(ConstUtil.RGX_NUMERICAL) || !secFormat.matches(ConstUtil.RGX_BBAN_FORMAT)) {

				log.error("validateBBAN. Invalid BBAN configuration {} for {}", section, iban.getCountryCode());
				throw new Exception("Invalid BBAN configuration " + section + " for country: " + iban.getCountryCode());
			}

			int secLenght = Integer.valueOf(secLenghtS);

			switch (secFormat) {
			case ConstUtil.ALPHABETICAL:
				if (!iban.getBban().substring(beginIndex, secLenght).matches(ConstUtil.RGX_ALPHABETICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			case ConstUtil.ALPHANUMERICAL:
				if (!iban.getBban().substring(beginIndex, secLenght).matches(ConstUtil.RGX_ALPHANUMERICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			case ConstUtil.NUMERICAL:
				if (!iban.getBban().substring(beginIndex, secLenght).matches(ConstUtil.RGX_NUMERICAL)) {
					return false;
				}
				beginIndex += secLenght;
				break;
			}
		}

		return true;
	}

	public static boolean validateLength(String iban) {

		return false;
	}

	public static boolean validateCountryCode(String ccode) throws Exception {

		if (Configuration.getCountryCodes().length == 0) {
			log.error("validateCountryCode. No countries were configured!");
			throw new Exception("No countries were configured!");
		}

		return Arrays.stream(Configuration.getCountryCodes()).anyMatch(ccode::equals);
	}

}
