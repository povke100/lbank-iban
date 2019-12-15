package lt.povilass.ibancheck.main;

import lombok.extern.slf4j.Slf4j;
import lt.povilass.ibancheck.config.Configuration;

@Slf4j
public class Checker {
	
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	public static void main(String[] args) {
		Checker checker = new Checker();
		System.exit(checker.check());

	}
	
	public int check() {
		int exitcode = SUCCESS;
		
		
		if (Configuration.getMode().length() == 0) {
			log.error("check. ");
		}
		
		
		return exitcode;
	}

}
