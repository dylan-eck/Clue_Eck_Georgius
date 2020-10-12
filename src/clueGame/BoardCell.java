package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int[] location;
	private char letter;
	private DoorDirection doorDirection;
	
	private boolean isOccupied;
	private boolean isRoom;
	private boolean isRoomLabel;
	private boolean isRoomCenter;
	
	private char secretPassage;
	
	private Set<BoardCell> adjList;
	
	public BoardCell(int xpos, int ypos) {
		location = new int[2];
		location[0] = xpos;
		location[1] = ypos;
		
		adjList = new HashSet<BoardCell>();
	}

	public boolean isRoom() {
		return isRoom;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	private void setAdj(Board board) {
		adjList.clear();
		
		for(int i = -1;i<2;i+=2) {
			int tempX = location[0]+i;
			if(tempX>=0 && tempX<=3) {
				adjList.add(board.getCell(tempX,location[1]));
			}
		}
		for(int j = -1;j<2;j+=2) {
			int tempY = location[1]+j;
			if(tempY>=0 && tempY<=3) {
				adjList.add(board.getCell(location[0],tempY));
			}
		}	
	}
	
	public Set<BoardCell> getAdjList(Board board){
		setAdj(board);
		return adjList;
	}
	
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) 
			return true;
		return false;
	}
	
	public boolean isLabel() {
		return isRoomLabel;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getChar() {
		return letter;
	}
	
}
