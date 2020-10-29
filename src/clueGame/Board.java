package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
//	private Map roomMap<Character, Room> roomMap;
	
	private BoardCell[][] boardCellArray;
	private int numRows;
	private int numCols;
	
	private Scanner layoutFile;
	private String layoutFileName;
	private Scanner setupFile;
	private String setupFileName;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<Room> rooms;
	private char hallwayLetter;
	public final char UNREACHABLE = 'X';
	
	private static Board theInstance = new Board();
	
	public static Board getInstance() {
		return theInstance;
	}
	
	private Board() {
		super();
	}
	
	// set up the game board
	public void initialize() {
		
		rooms = new HashSet<Room>();
		
		try {
			loadSetupConfig();
			setupFile.close();
			loadLayoutConfig();
			layoutFile.close();
			
			//setAdjList of all board cells
			for(BoardCell b[]:boardCellArray) {
				for(BoardCell a:b) {
					//Only place we set Adjacency
					a.setAdj(theInstance);
				}
			}
			
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// setup to read data from setup and 
	public void setConfigFiles(String layOutFile, String setUpFile) {

		layoutFileName = layOutFile;
		setupFileName = setUpFile;
		
		try {
			layoutFile = new Scanner(new File(layOutFile));
			setupFile = new Scanner(new File(setUpFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to locate setup and/or layout file");
		}
	}
	
	public void loadSetupConfig() throws BadConfigFormatException {
		
		String temp = "";
		
		while(setupFile.hasNextLine()) {
			temp = setupFile.nextLine();

			String tempArr[] = temp.split(",");
			if(temp.charAt(0)!='/') {
				if(!tempArr[0].equals("Room") && !tempArr[0].equals("Space")) {
					throw new BadConfigFormatException(setupFileName);
				}else if(tempArr[0].equals("Space") && !tempArr[1].equals("Unused")){
					hallwayLetter = tempArr[2].charAt(1);
					rooms.add(new Room(tempArr[1].substring(1),tempArr[2].charAt(1)));
				}else {
					//we start at index one because the format files have a space before each word or letter
					rooms.add(new Room(tempArr[1].substring(1),tempArr[2].charAt(1)));
				}
			}
		}
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException {
		
		String layoutFileData = "";
		
		String layoutFileLineIn = "";
		int collumnCount = 0;
		//set to one initialy because we read in one line to give us the row length and test for bad formating
		int rowCount = 1;

		//first loop to determine the size of the board
		layoutFileLineIn = layoutFile.nextLine();
		layoutFileData += layoutFileLineIn;
		
		String lineInSplit[] = layoutFileLineIn.split(",");
		collumnCount = lineInSplit.length;
	
		while(layoutFile.hasNextLine()){
			layoutFileLineIn=layoutFile.nextLine();
			layoutFileData += ("\n"+ layoutFileLineIn);			
			lineInSplit = layoutFileLineIn.split(",");
			if(lineInSplit.length != collumnCount) {
				//TODO: add a way to retrieve the files name so that an error message can be printed to a file
				throw new BadConfigFormatException(layoutFileName);
			}
			rowCount++;
		}
		
		
		numRows = rowCount;
		numCols = collumnCount;
		
		boardCellArray = new BoardCell[numCols][numRows];
		
		String layoutFileDataSplit[] = layoutFileData.split("\n"); 
		String currentLine;
		String currentLineSplit[];
		
		//second loop to actualy get the data
			//runs once for each row
			for(int j = 0;j<numRows;j++) {
				int i = 0;
				currentLine = layoutFileDataSplit[j];
				currentLineSplit = currentLine.split(",");
				
				for(String tileString:currentLineSplit) {
					//if the space is just a generic cell with nothing special

					if(tileString.length() == 1) {
						
						boolean testInitial = false;
						for(Room r:rooms) {
							if(r.getLetter()==tileString.charAt(0)) {
								testInitial = true;
							}
						}
						if(!testInitial) {
							throw new BadConfigFormatException(layoutFileName);
						}
						
						boolean isRoom=false;
						for(Room r:rooms) {
							if(tileString.charAt(0)==r.getLetter()) {
								isRoom = true;
								boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),true);
							}
						}
						
						if(!isRoom) {
							boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),false);
						}
						
					//All the doorways, secret passages, room labels, room centers
					}else if(tileString.length()==2){
						
						boolean testInitial = false;
						for(Room r:rooms) {
							if(r.getLetter()==tileString.charAt(0)) {
								testInitial = true;
							}
						}
						if(!testInitial) {
							throw new BadConfigFormatException(layoutFileName);
						}
						
						boolean isRoom=false;
						for(Room r:rooms) {
							if(tileString.charAt(0)==r.getLetter()) {
								isRoom = true;
								boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),true);
							}
						}
						
						if(!isRoom) {
							boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),false);
						}
						
						//a bit messy but better than creating a set and using contains()
						if(tileString.charAt(1)=='^') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.UP);
						}else if(tileString.charAt(1)=='>') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.RIGHT);
						}else if(tileString.charAt(1)=='v') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.DOWN);
						}else if(tileString.charAt(1)=='<') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.LEFT);
						}else if(tileString.charAt(1)=='*') {
							boardCellArray[i][j].setRoomCenter();
							for(Room r:rooms) {
								if(r.getLetter()==tileString.charAt(0)) 
									r.setCenterCell(boardCellArray[i][j]);
							}
						}else if(tileString.charAt(1)=='#') {
							boardCellArray[i][j].setRoomLabel();
							for(Room r:rooms) {
								if(r.getLetter()==tileString.charAt(0)) 
									r.setLabelCell(boardCellArray[i][j]);
							}
						}else {
							boardCellArray[i][j].setSecretPassage(tileString.charAt(1));
						}
							
					}else {
						throw new BadConfigFormatException(layoutFileName);
					}
					i++;
				}
			}
	
	}
	
	public void calcTargets(BoardCell startCell, int pathlength) {
		//visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		//visited.add(startCell);
		findAllTargets(startCell,new HashSet<BoardCell>(), pathlength);
		targets.remove(startCell);
		
		//removes all occupies spaces from targets
		Set<BoardCell> toBeDel = new HashSet<BoardCell>();
		for(BoardCell target : targets) {
			if(target.isOccupied() && !target.isRoomCenter()) {
				toBeDel.add(target);
			}
		}
		
		for(BoardCell target:toBeDel) {
			targets.remove(target);
		}
	}
	
	public void findAllTargets(BoardCell startCell, Set<BoardCell> previousCells, int pathlength) {		
		if(pathlength == 1) {
			for(BoardCell b:startCell.getAdjList()) {
				if(!previousCells.contains(b)) {
					targets.add(b);
				}
			}
		}else {
			for(BoardCell b:startCell.getAdjList()) {
				if(b.isRoomCenter()) {
					targets.add(b);
				}else if(!(previousCells.contains(b))) {
					if(!(b.isOccupied())) {
						previousCells.add(startCell);
						Set<BoardCell> temp = new HashSet<>(previousCells);
						findAllTargets(b,temp,pathlength-1);
					}
				}
			}
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
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

	public BoardCell getCell(int row, int col) {
		return boardCellArray[col][row];
	}
	
	public Set<BoardCell> getAdjList(int x, int y){
		return getCell(x,y).getAdjList();
	}
	
	public char getHallways() {
		return hallwayLetter;
	}
	
	public Set<Room> getAllRooms(){
		return rooms;
	}
}
