package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Throwable{


	public BadConfigFormatException() {
		super("error: input file does not match expected format.");
	}
	
	
	/**
	 * Constructor that writes an error message to a log file.
	 * 
	 * @param badFileName -> name of the file that caused the exception
	 */
	public BadConfigFormatException(String badFileName) {
		super("Error: " + badFileName + " does not match expected format.");
		
		try {
			PrintWriter temp = new PrintWriter("errorlog.txt");
			temp.println("error: " + badFileName + " does not match expected format.");
			temp.close();
		} catch (FileNotFoundException e) {
			System.out.println("log file not found");
		}
	}
}
