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
	
	public void setLabelCell(BoardCell cell) {
		labelCell = cell;
	}
	
	public void setCenterCell(BoardCell cell) {
		centerCell = cell;
	}
	
	public String getName() {
		return roomName;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return null;
	}
	
	public char getLetter() {
		return letter;
	}
	
}
