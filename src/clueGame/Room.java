package clueGame;

import java.awt.Graphics;

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
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public void setCenterCell(BoardCell cell) {
		centerCell = cell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public String getName() {
		return roomName;
	}
	
	public char getLetter() {
		return letter;
	}

	public String toString() {
		return roomName + ", " +letter;
	}
}
