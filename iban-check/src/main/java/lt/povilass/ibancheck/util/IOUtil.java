package lt.povilass.ibancheck.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.povilass.ibancheck.data.IBAN;

public class IOUtil {

	public static List<IBAN> readIBANS(String fileName) throws FileNotFoundException, IOException{
		
		List<IBAN> ibans = new ArrayList<>();
		String str = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			while ((str = br.readLine()) != null) {
				IBAN iban = new IBAN(str);
				ibans.add(iban);
			}
		}
		
		return ibans;
	}
	
	public static void writeIBANS(String fileName, List<IBAN> ibans) throws IOException {
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName+".out"))){
			for (IBAN iban : ibans) {
				bw.write(iban.getValidityString()+ "\n");
			}
		}
	}
}
