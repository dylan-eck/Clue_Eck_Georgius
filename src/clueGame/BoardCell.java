package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int[] location;
	private boolean isRoom, isRoomCenter, isOccupied, isRoomLabel;
	private char letter, secretPassage;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList;
	
	private static final int DOORWAY_HIGHT = 5;
	private static final int DOORWAY_WIDTH = 5;
	
	public BoardCell(int xpos, int ypos, char letter, boolean isRoom) {
		location = new int[] {xpos, ypos};
		this.letter = letter;
		this.isRoom = isRoom;
		
		//set to false by default can be changed later in layout
		isRoomCenter = false;
		isRoomLabel = false;
		isOccupied = false;
		doorDirection = DoorDirection.NONE;
		//reserved char so it will never appear as a room symbol
		secretPassage = '*';
		
		adjList = new HashSet<BoardCell>();
	}
	
	/**
	 * This function is called to set the Adjacency list of the cell
	 * 
	 * @param board				-> a reference to the board that the cell is in
	 */
	public void setAdj(Board board) {	
		//check to see if it is a hallway
		if(letter == board.getHallways()) {
			
			handleHallways(board);
			
			if(isDoorway()) {
				handleDoors(board);
			}
			
		}else if(secretPassage!='*') {
			//This finds the room that maches the char of secretPassage and then adds it's center cell to the adjList of this cells room it's in
			(board.getRoom(this).getCenterCell()).addAdjCell(board.getRoom(secretPassage).getCenterCell());

		}else if(letter == board.UNREACHABLE) {
			handleHallways(board);
		}
	}
	
	/**
	 * Helper function for setAdj. Handles all of the hallways that aren't doors as well as unreachable tiles.
	 * 
	 * @param board			-> a reference to the board that the cell is in
	 */
	private void handleHallways(Board board) {
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
	}
	
	/**
	 * Helper function for setAdj. Handles all of the doors.
	 * 
	 * @param board			-> a reference to the board that the cell is in
	 */
	private void handleDoors(Board board) {
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
			default:
				break;
		}
	}
	
	public void draw(Graphics g, int width, int height,Board board) {
		if(letter == 'X') {
			g.setColor(Color.BLACK);	
			g.fillRect(location[0]*width, location[1]*height, width, height);
		} else if (letter != 'H') {
			g.setColor(Color.lightGray);	
			g.fillRect(location[0]*width, location[1]*height, width, height);
		} else {
			g.setColor(Color.ORANGE);
			g.fillRect(location[0]*width, location[1]*height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(location[0]*width, location[1]*height, width, height);
		}	
		
		if(this.isDoorway()) {
			switch(doorDirection) {
				case UP:
					g.setColor(Color.BLACK);
					g.fillRect(location[0]*width, location[1]*height, width, DOORWAY_HIGHT);
					break;
				case DOWN:
					g.setColor(Color.BLACK);
					g.fillRect(location[0]*width, (location[1]+1)*height-DOORWAY_HIGHT, width, DOORWAY_HIGHT);
					break;
				case RIGHT:
					g.setColor(Color.BLACK);
					g.fillRect((location[0]+1)*width-DOORWAY_WIDTH, location[1]*height, DOORWAY_WIDTH, height);
					break;
				case LEFT:
					g.setColor(Color.BLACK);
					g.fillRect(location[0]*width, location[1]*height, DOORWAY_WIDTH, height);
					break;
				default:
					break;
			}
		}
		
		if(isRoomLabel) {
			int textWidth = 5*width;
			int textHeight = height;
			
			g.setColor(Color.BLACK);
			g.drawString((board.getRoom(this)).getName(), (location[0]*width)-(textWidth/3), location[1]*height);
		}
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
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
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
	
	public int[] getLocation() {
		return location;
	}
	
	public String toString() {
		return "["+letter+" y: "+location[1]+" x: "+location[0]+"]";
	}
}
