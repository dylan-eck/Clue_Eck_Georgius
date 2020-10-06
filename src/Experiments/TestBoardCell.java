/*  Dylan Eck
 *  Riley Georgius
 *  Section B
 *  Group 23
 */

package Experiments;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int[] location;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	
	private void setAdj(TestBoard board) {
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
	
	public TestBoardCell(int x, int y,boolean r) {
		location = new int[2];
		location[0] = x;
		location[1] = y;
		isRoom = r;
		
		adjList = new HashSet<TestBoardCell>();
	}
	
	public Set<TestBoardCell> getAdjList(TestBoard board){
		setAdj(board);
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

	@Override
	public String toString() {
		return "TestBoardCell [location=" + Arrays.toString(location) + "]";
	}

}
