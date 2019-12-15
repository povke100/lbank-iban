package lt.povilass.ibancheck.util;

public class ConstUtil {
	
	public static final String CFG_COUNTRY_CODES = "iban.country.codes";
	public static final String CFG_IBAN_LENGHT = "iban.lenght.";
	public static final String CFG_IBAN_BBAN = "iban.bban.";
	
	public static final String NUMERICAL = "n";
	public static final String ALPHABETICAL = "a";
	public static final String ALPHANUMERICAL = "c";
	
	public static final String RGX_NUMERICAL = "^[0-9]+$";
	public static final String RGX_ALPHABETICAL = "^[A-Z]+$";
	public static final String RGX_ALPHANUMERICAL = "^[A-Z0-9]+$";
	public static final String RGX_BBAN_FORMAT = "(" + ALPHABETICAL + "|" + ALPHANUMERICAL + "|" + NUMERICAL + ")";
	

}
