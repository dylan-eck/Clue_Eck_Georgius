package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Board {
	
//	private Map roomMap<Character, Room> roomMap;
	
	private BoardCell[][] boardCellArray;
	private int numRows;
	private int numCols;
	
	private BufferedReader layoutFile;
	private BufferedReader setupFile;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<Room> rooms;
	
	private static Board theInstance = new Board();
	
	private Board() {
		super();
	}
	
	// set up game board
	public void initialize() {

		
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void setConfigFiles(String layOutFile, String setUpFile) {
		
		try {
			layoutFile = new BufferedReader(new FileReader(layOutFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file "+layOutFile+" isn't working. Please check the spelling.");
		}
			
		try {
			setupFile = new BufferedReader(new FileReader(setUpFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file "+setUpFile+" isn't working. Please check the spelling.");
		}
		
	}
	
	public void loadSetupConfig() {
		String temp = "";

		try {
			temp = setupFile.readLine();
			String tempArr[] = temp.split(",");
			
			while(tempArr[0]!="Space") {
				if(temp.charAt(0)!='/') {
					if(tempArr[0]!="Room") {
						throw new BadConfigFormatException();
					}else {
						rooms.add(new Room(tempArr[1],tempArr[2].charAt(1)));
					}
				}
				temp = setupFile.readLine();
				tempArr = temp.split(",");
			}
		}catch(IOException e) {
			System.out.println("There was a problem while reading in the line.");
		}catch(BadConfigFormatException e){
			System.out.println("The file is formated incorectly. please reformat and try again.");
		}
	}
	
	public void loadLayoutConfig() {

		String temp = "";
		int tempRowCount = 0;
		//set to one initialy because we read in one line to give us the row length and test for bad formating
		int tempColCount = 1;

		try {
			temp = layoutFile.readLine();
			String tempArr[] = temp.split(",");
			tempRowCount = tempArr.length;
		
			while((temp=layoutFile.readLine())!=null){
				tempArr = temp.split(",");
				if(tempArr.length != tempRowCount) {
					//TODO: add a way to retrieve the files name so that an error message can be printed to a file
					throw new BadConfigFormatException();
				}
				tempColCount++;
			}
		}catch(IOException e) {
			System.out.println("There was a problem while reading in the line.");
		}catch(BadConfigFormatException e){
			System.out.println("The file is formated incorectly. please reformat and try again.");
		}
		
		numRows = tempRowCount;
		numRows = tempColCount;
	}
	
	public Room getRoom(char letter) {
		for(Room i:rooms) {
			if(i.getLetter()==letter) {
				return i;
			}
		}
		return null;
	}
	
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getChar());
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
