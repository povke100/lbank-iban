package lt.povilass.ibancheck.util;

public class ConstUtil {
	
	public static final String MODE_SINGLE = "1";
	public static final String MODE_MULTI = "2";
	
	public static final int EXIT_CODE_SUCCESS = 0;
	public static final int EXIT_CODE_ERROR = 1;
	
	public static final String CFG_IBAN_MODE = "iban.check.mode";
	public static final String CFG_COUNTRY_CODES = "iban.country.codes";
	public static final String CFG_IBAN_LENGHT = "iban.length.";
	public static final String CFG_IBAN_BBAN = "iban.bban.";
	
	public static final String NUMERICAL = "n";
	public static final String ALPHABETICAL = "a";
	public static final String ALPHANUMERICAL = "c";
	
	public static final String RGX_NUMERICAL = "^[0-9]+$";
	public static final String RGX_ALPHABETICAL = "^[A-Z]+$";
	public static final String RGX_ALPHANUMERICAL = "^[A-Z0-9]+$";
	public static final String RGX_BBAN_FORMAT = "(" + ALPHABETICAL + "|" + ALPHANUMERICAL + "|" + NUMERICAL + ")";
	
	
	

}
