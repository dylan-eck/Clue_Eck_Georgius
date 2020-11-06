package clueGame;

import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private Set<Card> seen;
	private BoardCell targets;

	public ComputerPlayer(String name, String color, int x, int y) throws BadConfigFormatException {
		super(name, color, x, y);
	}

	public Solution createSuggestion() {
		return null;
	}
	
	public BoardCell selectTargets() {
		return null;
	}
	
	public void addToSeen(Card card) {
		seen.add(card);
	}
	
	public Set<Card> getSeen() {
		return new HashSet<Card>();
	}
	
	
}
