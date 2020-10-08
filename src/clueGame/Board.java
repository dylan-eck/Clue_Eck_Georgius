package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Board {
	
//	private Map roomMap<Character, Room> roomMap;
	
	private BoardCell[][] boardCellArray;
	private int numRows;
	private int numCols;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private static Board theInstance = new Board();
	
	private Board() {
		super();
	}
	
	// set up game board
	public void initialize() {
		//Needed some inital value so that we didn't have an outOFBounds exception
		numRows = 24;
		numCols = 24;
		boardCellArray = new BoardCell[numRows][numCols];
		
		for(int i=0;i<numRows;i++) {
			for(int j = 0; j<numCols;j++) {
				boardCellArray[i][j] = new BoardCell(i,j);
			}
		}
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void setConfigFiles(String layoutFile, String setUpFile) {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public Room getRoom(char letter) {
		return new Room();
	}
	
	public Room getRoom(BoardCell cell) {
		return null;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
		targets.remove(startCell);
		
		//removes all occupies spaces from targets
		Set<BoardCell> toBeDel = new HashSet<BoardCell>();
		for(BoardCell target : targets) {
			if(target.isOccupied()) {
				toBeDel.add(target);
			}
		}
		
		for(BoardCell target:toBeDel) {
			targets.remove(target);
		}
	}
	
	public void findAllTargets(BoardCell startCell, int pathlength) {
			
			for(BoardCell adjCell : startCell.getAdjList(this)) {
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
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell(int col, int row) {
		
		return boardCellArray[col][row];
	}
}
