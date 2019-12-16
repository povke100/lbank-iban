package lt.povilass.ibancheck.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lt.povilass.ibancheck.data.IBAN;
import lt.povilass.ibancheck.main.Checker;
import lt.povilass.ibancheck.util.CalculationUtil;
import lt.povilass.ibancheck.util.ValidationUtil;

public class Tests {

	@Test
	public void testModulo97() {
		String numIBAN = "1000011101001000212912";
		int rem = CalculationUtil.modulo97(numIBAN);
		Assertions.assertEquals(1, rem);
		
		numIBAN = "1000011101001000212915";
		rem = CalculationUtil.modulo97(numIBAN);
		Assertions.assertEquals(4, rem);
		
		numIBAN = "100212915";
		rem = CalculationUtil.modulo97(numIBAN);
		Assertions.assertEquals(-1, rem);
	}
	
	@Test
	public void testGetNumericalIBAN() {
		IBAN iban = new IBAN("LT647044001231465456");
		String numIBAN = CalculationUtil.getNumericalIBAN(iban);
		Assertions.assertEquals("7044001231465456212964", numIBAN);
		
		iban = new IBAN("64704400123165456");
		numIBAN = CalculationUtil.getNumericalIBAN(iban);
		Assertions.assertEquals("44001231654566470", numIBAN);
		
		iban = new IBAN("LT647044001231c65456");
		numIBAN = CalculationUtil.getNumericalIBAN(iban);
		Assertions.assertNull(numIBAN);
		
	}
	
	@Test
	public void testValidateBBAN() {
		IBAN iban = new IBAN("LT647044001231465456");
		try {
			boolean valid = ValidationUtil.validateBBAN(iban.getBban(), iban.getCountryCode());
			Assertions.assertTrue(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		iban = new IBAN("LT6470440012314654");
		try {
			ValidationUtil.validateBBAN(iban.getBban(), iban.getCountryCode());
			Assertions.fail();
		} catch (Exception e) {
			//ignore
		}
		
		iban = new IBAN("LT64704400123146AB56");
		try {
			boolean valid = ValidationUtil.validateBBAN(iban.getBban(), iban.getCountryCode());
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testValidateCountryCode() {
		String ccode = "GB";
		try {
			boolean valid = ValidationUtil.validateCountryCode(ccode);
			Assertions.assertTrue(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		ccode = "AA";
		try {
			boolean valid = ValidationUtil.validateCountryCode(ccode);
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		ccode = "BBB";
		try {
			boolean valid = ValidationUtil.validateCountryCode(ccode);
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testValidateIBANLength() {
		String iban = "FR1420041010050500013M02607";
		String ccode = "FR";
		try {
			boolean valid = ValidationUtil.validateLength(iban, ccode);
			Assertions.assertTrue(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		iban = "FR1420041010050500013M02";
		ccode = "FR";
		try {
			boolean valid = ValidationUtil.validateLength(iban, ccode);
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
	}
	
	@Test
	public void testProcessIBAN() {
		Checker ch = new Checker();
		
		IBAN iban = new IBAN("MK07250120000058984");
		try {
			boolean valid = ch.processIBAN(iban);
			Assertions.assertTrue(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		iban = new IBAN("GB82WEST12345698765432");
		try {
			boolean valid = ch.processIBAN(iban);
			Assertions.assertTrue(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		iban = new IBAN("LV121000011101001000");
		try {
			boolean valid = ch.processIBAN(iban);
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
		
		iban = new IBAN("AA051245445454552117989");
		try {
			boolean valid = ch.processIBAN(iban);
			Assertions.assertFalse(valid);
		} catch (Exception e) {
			Assertions.fail();
		}
	}
}
