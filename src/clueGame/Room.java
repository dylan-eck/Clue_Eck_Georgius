package clueGame;

public class Room {

	private String roomName;
	private BoardCell labelCell;
	private BoardCell centerCell;
	private char letter;
	
	public Room(String name,char let) {
		roomName = name;
		letter = let;
		
	}
	
	public String getName() {
		return roomName;
	}
	
	public BoardCell getLabelCell() {
		return null;
	}

	public BoardCell getCenterCell() {
		return null;
	}
	
	public char getLetter() {
		return letter;
	}
	
}
