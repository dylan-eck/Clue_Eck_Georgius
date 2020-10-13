/*  Dylan Eck
 *  Riley Georgius
 *  Section B
 *  Group 23
 */

package Experiments;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard(int x, int y) {
		board = new TestBoardCell[4][4];
		for(int i=0;i<x;i++) {
			for(int j = 0; j<y;j++) {
				board[i][j] = new TestBoardCell(i,j,false);
			}
		}
		//creates the two rooms at the bottom
		board[1][3] = new TestBoardCell(1,3,true);
		board[2][3] = new TestBoardCell(2,3,true);
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
		targets.remove(startCell);
		
		//removes all occupies spaces from targets
		Set<TestBoardCell> toBeDel = new HashSet<TestBoardCell>();
		for(TestBoardCell target : targets) {
			if(target.isOccupied()) {
				toBeDel.add(target);
			}
		}
		
		for(TestBoardCell target:toBeDel) {
			targets.remove(target);
		}
	}

	public void findAllTargets(TestBoardCell startCell, int pathlength) {
		
		for(TestBoardCell adjCell : startCell.getAdjList(this)) {
			if(adjCell.isRoom()) {
				targets.add(adjCell);
			}
			if(!visited.contains(adjCell)) {
				visited.add(adjCell);
				if(pathlength == 1) {
					targets.add(adjCell);
				}else {
					findAllTargets(adjCell,pathlength-1);
				}
				
				visited.remove(startCell);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int col, int row) {
		
		return board[col][row];
	}
}
