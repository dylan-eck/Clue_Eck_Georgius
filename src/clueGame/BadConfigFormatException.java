package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Throwable{

	public BadConfigFormatException() {
		super("Error: Format of the input file does not match expected format.");
	}
	
	public BadConfigFormatException(String fileName) {
		super("Error: Format of "+fileName+" does not match expected format.");
		
		PrintWriter temp = null;
		//auto generated try/catch
		try {
			temp = new PrintWriter("logfile.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp.println("Error: Format of "+fileName+" does not match expected format.");
		temp.close();
	}
}
