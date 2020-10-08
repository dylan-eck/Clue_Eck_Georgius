package clueGame;

public class BadConfigFormatException extends Throwable{

	public BadConfigFormatException() {
		super("Error: Format does not match expected format");
	}
}
