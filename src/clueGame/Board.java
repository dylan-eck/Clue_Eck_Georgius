package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
//	private Map roomMap<Character, Room> roomMap;
	
	private BoardCell[][] boardCellArray;
	private int numRows;
	private int numCols;
	
	private Scanner layoutFile;
	private Scanner setupFile;
	
	private String layoutFileName;
	private String setupFileName;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<Room> rooms;
	
	private static Board theInstance = new Board();
	
	private Board() {
		super();
	}
	
	// set up game board
	public void initialize() {
		
		rooms = new HashSet<Room>();
		try {
			loadSetupConfig();
			setupFile.close();
			loadLayoutConfig();
			layoutFile.close();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void setConfigFiles(String layOutFile, String setUpFile) {
		
		//useful for teling where an error occured and when we have to close and re-open a file
		layoutFileName = layOutFile;
		setupFileName = setUpFile;
		
		try {
			layoutFile = new Scanner(new File(layOutFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file "+layOutFile+" isn't working. Please check the spelling.");
		}
			
		try {
			setupFile = new Scanner(new File(setUpFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file "+setUpFile+" isn't working. Please check the spelling.");
		}
		
	}
	
	public void loadSetupConfig() throws BadConfigFormatException {
		
		rooms = new HashSet<Room>();
		
		System.out.println("You have reached the setup part.");
		
		String temp = "";
		
		while(setupFile.hasNextLine()) {
			temp = setupFile.nextLine();
			
			String tempArr[] = temp.split(",");
			if(temp.charAt(0)!='/') {
				if(!tempArr[0].equals("Room") && !tempArr[0].equals("Space")) {
					throw new BadConfigFormatException(setupFileName);
				}else{
					//we start at index one because the format files have a space in the at the beginning
					rooms.add(new Room(tempArr[1].substring(1),tempArr[2].charAt(1)));
				}
			}
		}
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException {

		System.out.println("You have reached the layout part.");
		
		String theWholeThing = "";
		
		String temp = "";
		int tempColCount = 0;
		//set to one initialy because we read in one line to give us the row length and test for bad formating
		int tempRowCount = 1;

		//first loop to determine the size of the board
		temp = layoutFile.nextLine();
		theWholeThing += temp;
		String tempArr[] = temp.split(",");
		tempColCount = tempArr.length;
	
		while(layoutFile.hasNextLine()){
			temp=layoutFile.nextLine();
			theWholeThing += ("\n"+ temp);
			tempArr = temp.split(",");
			if(tempArr.length != tempColCount) {
				//TODO: add a way to retrieve the files name so that an error message can be printed to a file
				throw new BadConfigFormatException(layoutFileName);
			}
			tempRowCount++;
		}
		
		
		numRows = tempRowCount;
		numCols = tempColCount;
		
		boardCellArray = new BoardCell[numCols][numRows];
		
		String theWholeThingSplit[] = theWholeThing.split("\n"); 
		String temp2;
		String tempArr2[];
		
		//second loop to actualy get the data
			//runs once for each row
			for(int j = 0;j<numRows;j++) {
				int i = 0;
				temp2 = theWholeThingSplit[j];
				tempArr2 = temp2.split(",");
				for(String str:tempArr2) {
					//if the space is just a generic cell with nothing special
					if(str.length() == 1) {
						
						boolean testInitial = false;
						for(Room r:rooms) {
							if(r.getLetter()==str.charAt(0)) {
								testInitial = true;
							}
						}
						if(!testInitial) {
							throw new BadConfigFormatException(layoutFileName);
						}
						
						boolean isRoom=false;
						for(Room r:rooms) {
							if(str.charAt(0)==r.getLetter()) {
								isRoom = true;
								boardCellArray[i][j] = new BoardCell(i,j,str.charAt(0),true);
							}
						}
						
						if(!isRoom) {
							boardCellArray[i][j] = new BoardCell(i,j,str.charAt(0),false);
						}
						
					//All the doorways, secret passages, room labels, room centers
					}else if(str.length()==2){
						
						boolean testInitial = false;
						for(Room r:rooms) {
							if(r.getLetter()==str.charAt(0)) {
								testInitial = true;
							}
						}
						if(!testInitial) {
							throw new BadConfigFormatException(layoutFileName);
						}
						
						boolean isRoom=false;
						for(Room r:rooms) {
							if(str.charAt(0)==r.getLetter()) {
								isRoom = true;
								boardCellArray[i][j] = new BoardCell(i,j,str.charAt(0),true);
							}
						}
						
						if(!isRoom) {
							boardCellArray[i][j] = new BoardCell(i,j,str.charAt(0),false);
						}
						
						//a bit messy but better than creating a set and using contains()
						if(str.charAt(1)=='^') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.UP);
						}else if(str.charAt(1)=='>') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.RIGHT);
						}else if(str.charAt(1)=='v') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.DOWN);
						}else if(str.charAt(1)=='<') {
							boardCellArray[i][j].setDoorDirection(DoorDirection.LEFT);
						}else if(str.charAt(1)=='*') {
							boardCellArray[i][j].setRoomCenter();
							for(Room r:rooms) {
								if(r.getLetter()==str.charAt(0)) 
									r.setCenterCell(boardCellArray[i][j]);
							}
						}else if(str.charAt(1)=='#') {
							boardCellArray[i][j].setRoomLabel();
							for(Room r:rooms) {
								if(r.getLetter()==str.charAt(0)) 
									r.setLabelCell(boardCellArray[i][j]);
							}
						}else {
							boardCellArray[i][j].setSecretPassage(str.charAt(1));
						}
							
					}else {
						System.out.println("Ended in square " + i+" "+j);
						System.out.println(temp2);
						throw new BadConfigFormatException(layoutFileName);
					}
					i++;
				}
			}
	
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
	
	public BoardCell getCell(int row, int col) {
		return boardCellArray[col][row];
	}
}
