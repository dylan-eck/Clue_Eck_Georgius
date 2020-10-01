package Experiments;

import java.util.Set;

public class TestBoardCell {
	private int[] location;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	
	public TestBoardCell(int x, int y,boolean r) {
		location = new int[2];
		location[0] = x;
		location[1] = y;
		isRoom = r;
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		
		isOccupied = occupied;
	}
	
	public boolean isOccupied() {
		
		return isOccupied;
	}
	
	
}
