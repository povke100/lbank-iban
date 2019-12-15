package lt.povilass.ibancheck.main;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.config.Configuration;
import lt.povilass.ibancheck.data.IBAN;
import lt.povilass.ibancheck.util.ConstUtil;
import lt.povilass.ibancheck.util.IOUtil;
import lt.povilass.ibancheck.util.ValidationUtil;

@Slf4j
public class Checker {
	
	private static final Logger console = org.slf4j.LoggerFactory.getLogger("lt.povilass.ibancheck.console");
	
	public static void main(String[] args) {
		Checker checker = new Checker();
		System.exit(checker.check());

	}

	public int check() {

		try {
			String mode = Configuration.getMode();
			if (mode.length() == 0) {
				log.error("check. No check mode is defined!");
				return ConstUtil.EXIT_CODE_ERROR;

			} else {
				log.debug("check. Check mode: {}", mode);

				switch (mode) {
				case ConstUtil.MODE_SINGLE:
					this.singleCheckMode();
					break;
				case ConstUtil.MODE_MULTI:
					this.multiCheckMode();
					break;
				default:
					log.error("check. Invalid check mode is set in configuration!");
					return ConstUtil.EXIT_CODE_ERROR;
				}
			}
		} catch (Exception e) {
			log.error("check. Exception occured.", e);
			return ConstUtil.EXIT_CODE_ERROR;
		}

		return ConstUtil.EXIT_CODE_SUCCESS;
	}

	public void singleCheckMode() throws Exception {
		
		try (Scanner sc = new Scanner(System.in)){
			console.info("Enter the iban:");
			String ibanS = sc.nextLine();

			IBAN iban = new IBAN(ibanS);
			iban.setValid(processIBAN(iban));

			console.info(iban.getValidityString());
		}
	}

	public void multiCheckMode() throws Exception {
		try (Scanner sc = new Scanner(System.in)){
			console.info("Enter file path and name:");
			String fileName = sc.nextLine();
			
			List<IBAN> ibans = IOUtil.readIBANS(fileName);
			
			for(IBAN iban : ibans) {
				iban.setValid(processIBAN(iban));
				log.debug("multiCheckMode. IBAN after validation:{}", iban);
			}
			
			IOUtil.writeIBANS(fileName, ibans);
			console.info("Validation is done!");
		}
	}

	public boolean processIBAN(IBAN iban) throws Exception {

		if (!ValidationUtil.validateCountryCode(iban.getCountryCode())) {
			return false;
		}
		
		if (!ValidationUtil.validateLength(iban.getIBAN(), iban.getCountryCode())) {
			return false;
		}
		
		if (!ValidationUtil.validateBBAN(iban.getBban(), iban.getCountryCode())) {
			return false;
		}
		
		if (!ValidationUtil.validateIBAN(iban)) {
			return false;
		}
		
		if (!ValidationUtil.validateCheckDigits(iban)) {
			return false;
		}

		return true;
	}

}
