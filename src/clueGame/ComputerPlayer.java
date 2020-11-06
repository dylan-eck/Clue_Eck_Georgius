package clueGame;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int x, int y) throws BadConfigFormatException {
		super(name, color, x, y);
	}

	public Solution createSuggestion() {
		return null;
	}
	
	public BoardCell selectTargets() {
		return null;
	}
	
}
