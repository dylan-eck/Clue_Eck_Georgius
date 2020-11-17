package clueGame;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, String color, int x, int y) throws BadConfigFormatException{
		super(name, color, x, y);
	}
	
	public boolean isHuman() {
		return true;
	}
	
	public Solution createSuggestion(Board b) {
		return null;
	}
}
