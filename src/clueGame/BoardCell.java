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
	
	public BoardCell(int xpos, int ypos, char l,boolean isR) {
		location = new int[2];
		location[0] = xpos;
		location[1] = ypos;
		letter = l;
		isRoom = isR;
		
		//set to false by default can be changed later in layout
		isRoomCenter = false;
		isRoomLabel = false;
		isOccupied = false;
		doorDirection = DoorDirection.NONE;
		//reserved char so it will never appear as a room symbol
		secretPassage = '*';
		
		adjList = new HashSet<BoardCell>();
	}
	
	public void setRoomLabel() {
		isRoomLabel = true;
	}
	
	public boolean isLabel() {
		return isRoomLabel;
	}
	
	public void setRoomCenter() {
		isRoomCenter = true;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	public void setSecretPassage(char exit) {
		secretPassage = exit;
	}
	
	//TODO: Might not need this
	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setDoorDirection(DoorDirection d) {
		doorDirection = d;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) 
			return true;
		return false;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public void setAdj(Board board) {	
		//check to see if it is a hallway
		if(letter == board.getHallways()) {
			for(int i = -1;i<2;i+=2) {
				int tempX = location[0]+i;
				if(tempX>=0 && tempX<=(board.getNumColumns()-1) && board.getCell(location[1],tempX).getChar()==board.getHallways()) {
					adjList.add(board.getCell(location[1],tempX));
				}
			}
			for(int j = -1;j<2;j+=2) {
				int tempY = location[1]+j;
				if(tempY>=0 && tempY<=(board.getNumRows()-1) && board.getCell(tempY,location[0]).getChar()==board.getHallways()) {
					adjList.add(board.getCell(tempY,location[0]));
				}
			}
			
			if(isDoorway()) {
				switch(doorDirection) {
					case UP:
						adjList.add(board.getRoom(board.getCell(location[1]-1,location[0])).getCenterCell());
						//Setting adj from room to doorways here because it's hard doing it from the room center 
						(board.getRoom(board.getCell(location[1]-1,location[0])).getCenterCell()).addAdjCell(this);
						break;
					case DOWN:
						adjList.add(board.getRoom(board.getCell(location[1]+1,location[0])).getCenterCell());
						(board.getRoom(board.getCell(location[1]+1,location[0])).getCenterCell()).addAdjCell(this);
						break;
					case RIGHT:
						adjList.add(board.getRoom(board.getCell(location[1],location[0]+1)).getCenterCell());
						(board.getRoom(board.getCell(location[1],location[0]+1)).getCenterCell()).addAdjCell(this);
						break;
					case LEFT:
						adjList.add(board.getRoom(board.getCell(location[1],location[0]-1)).getCenterCell());
						(board.getRoom(board.getCell(location[1],location[0]-1)).getCenterCell()).addAdjCell(this);
						break;
					case NONE:
						break;
				}
			}
		}
		
		if(secretPassage!='*') {
			//This finds the room that maches the char of secretPassage and then adds it's center cell to the adjList of this cells room it's in
			(board.getRoom(this).getCenterCell()).addAdjCell(board.getRoom(secretPassage).getCenterCell());
		}
	}
	
	public void addAdjCell(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	
	public char getChar() {
		return letter;
	}
	
	public String toString() {
		return letter+" x: "+location[0]+ " y: "+location[1];
	}
}
