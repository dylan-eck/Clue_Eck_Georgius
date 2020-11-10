package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private BoardCell[][] boardCellArray;
	private int numRows, numCols;
	private Random r;
	private Iterator nextPlayer;
	
	private Scanner layoutFile, setupFile;
	private String layoutFileName, setupFileName;
	
	private Set<BoardCell> targets, visited;
	private Set<Room> rooms;
	private char hallwayLetter;
	public final char UNREACHABLE = 'X';
	
	private Set<Player> players;
	private Set<Card> deck, removableDeck,weapons,players2,rooms2;
	private Solution solution;
	
	private static Board theInstance = new Board();
	
	public static Board getInstance() {
		return theInstance;
	}
	
	private Board() {
		super();
	}
	
	/**
	 * This function reads in the setup files and initializes the board, players, and cards
	 */
	public void initialize() {
		
		this.rooms = new HashSet<Room>();
		this.players = new HashSet<Player>();
		this.weapons = new HashSet<Card>();
		this.deck = new HashSet<Card>();
		
		r = new Random();
		
		try {
			loadSetupConfig();
			setupFile.close();
			loadLayoutConfig();
			layoutFile.close();
			
			//setAdjList of all board cells
			for(BoardCell b[]:boardCellArray) {
				for(BoardCell a:b) {
					a.setAdj(theInstance);
				}
			}
			
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		removableDeck = new HashSet<Card>(deck);
		solution = new Solution();
		setSolution();
		deal();
		nextPlayer = players.iterator();
	}
	
	/**
	 * This function sets and attempts to open the files that contain 
	 * the board setup and layout information.
	 * 
	 * @param layoutFileName
	 * @param setupFileName
	 */
	public void setConfigFiles(String layoutFileName, String setupFileName) {

		this.layoutFileName = layoutFileName;
		this.setupFileName = setupFileName;
		
		try {
			layoutFile = new Scanner(new File(this.layoutFileName));
			setupFile = new Scanner(new File(this.setupFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to locate setup and/or layout file");
		}
	}
	
	/**
	 * This function loads the information stored in the setup configuration file.
	 * 
	 * @throws BadConfigFormatException -> exception thrown if the setup configuration file is incorrectly formatted.
	 */
	public void loadSetupConfig() throws BadConfigFormatException {
		
		String lineIn = new String();
		
		while(setupFile.hasNextLine()) {
			lineIn = setupFile.nextLine();

			String lineInSplit[] = lineIn.split(",");
			
			if(lineIn.charAt(0)!='/') {
				if(!lineInSplit[0].equals("Room") && !lineInSplit[0].equals("Space")&& !lineInSplit[0].equals("Player")&& !lineInSplit[0].equals("Weapon")) {
					throw new BadConfigFormatException(setupFileName);
				}else if(lineInSplit[0].equals("Space") && !lineInSplit[1].equals("Unused")){
					hallwayLetter = lineInSplit[2].charAt(1);
					rooms.add(new Room(lineInSplit[1].substring(1),lineInSplit[2].charAt(1)));
				}else if(lineInSplit[0].equals("Player")){
					makeCard(lineInSplit[1],"Person");
					if(lineInSplit[5].equals("H")) {
						players.add(new HumanPlayer(lineInSplit[1],lineInSplit[2],Integer.parseInt(lineInSplit[3]),Integer.parseInt(lineInSplit[4])));
					}else {	
						players.add(new ComputerPlayer(lineInSplit[1],lineInSplit[2],Integer.parseInt(lineInSplit[3]),Integer.parseInt(lineInSplit[4])));
					}
				}else if(lineInSplit[0].equals("Weapon")){
					makeCard(lineInSplit[1],"Weapon");
					weapons.add(new Card(lineInSplit[1],"Weapon"));
				}else {
					//we start at index one because the format files have a space before each word or letter
					makeCard(lineInSplit[1].substring(1),"Room");
					rooms.add(new Room(lineInSplit[1].substring(1),lineInSplit[2].charAt(1)));
				}
			}
		}
	}
	
	//Helper function that's called whenever a weapon, room or player is loaded
	private void makeCard(String name,String type) {
		deck.add(new Card(name,type));
	}
	
	/**
	 * This function loads the information stored in the layout configuration file.
	 * 
	 * @throws BadConfigFormatException -> exception thrown if the layout configuration file is incorrectly formatted.
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		
		String layoutFileData = new String(); 
		String layoutFileLineIn = new String();
		
		//set to one initialy because we read in one line to give us the row length and test for bad formating
		int rowCount = 1;
		int collumnCount = 0;
		
		//first loop to determine the size of the board
		layoutFileLineIn = layoutFile.nextLine();
		layoutFileData += layoutFileLineIn;
		
		String layoutFileLineInSplit[] = layoutFileLineIn.split(",");
		collumnCount = layoutFileLineInSplit.length;
	
		while(layoutFile.hasNextLine()){
			layoutFileLineIn=layoutFile.nextLine();
			layoutFileData += ("\n"+ layoutFileLineIn);			
			layoutFileLineInSplit = layoutFileLineIn.split(",");
			if(layoutFileLineInSplit.length != collumnCount) {
				throw new BadConfigFormatException(layoutFileName);
			}
			rowCount++;
		}
		
		numRows = rowCount;
		numCols = collumnCount;
		
		boardCellArray = new BoardCell[numCols][numRows];
		
		loadCells(layoutFileData,numRows,numCols);
	}
	
	/**
	 * This function loads the cell information from the layout configuration file.
	 * 
	 * @param layoutFileData				-> string of data read in from layout configuration file
	 * @param numRows						-> dimensions of the game board
	 * @param numCols
	 * @throws BadConfigFormatException		-> exception thrown if the format of the layout file is incorrect
	 */
	private void loadCells(String layoutFileData, int numRows, int numCols) throws BadConfigFormatException{
	
		String layoutFileDataSplit[] = layoutFileData.split("\n"); 
		String currentLineSplit[];
	
		//runs once for each row
		for(int j = 0;j<numRows;j++) {
			int i = 0;
			currentLineSplit = layoutFileDataSplit[j].split(",");
			
			for(String tileString:currentLineSplit) {
				//if the space is just a generic cell with nothing special
				if(tileString.length() == 1) {
					handleHallwaysAndUnreachable(tileString,i,j);
					
				//All the doorways, secret passages, room labels, room centers
				}else if(tileString.length()==2){
					boolean testLetter = false;
					for(Room r:rooms) {
						if(r.getLetter()==tileString.charAt(0)) {
							testLetter = true;
						}
					}
					if(!testLetter) {
						throw new BadConfigFormatException(layoutFileName);
					}
					boolean isRoom=false;
					for(Room r:rooms) {
						if(tileString.charAt(0)==r.getLetter()) {
							isRoom = true;
							boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),true);
						}
					}
					specialTiles(tileString, i, j);	
				}else{
					throw new BadConfigFormatException(layoutFileName);
				}
				i++;
			}
		}
	}
	
	/**
	 * This helper function takes care of setting up cells from the layout configuration file 
	 * that are either hallways or unreachable.
	 * 
	 * @param tileString 				-> 	cell information read in from file
	 * @param i 						-> 	row number of cell
	 * @param j 						-> 	column number of cell
	 * @throws BadConfigFormatException -> 	exception thrown if the input file format is incorrect
	 */
	private void handleHallwaysAndUnreachable(String tileString, int i, int j) throws BadConfigFormatException{
		boolean testLetter = false;
		for(Room r:rooms) {
			if(r.getLetter()==tileString.charAt(0)) {
				testLetter = true;
			}
		}
		if(!testLetter) {
			throw new BadConfigFormatException(layoutFileName);
		}
		boolean isRoom=false;
		for(Room r:rooms) {
			if(tileString.charAt(0)==r.getLetter()) {
				isRoom = true;
				boardCellArray[i][j] = new BoardCell(i,j,tileString.charAt(0),true);
			}
		}
	}
	
	/**
	 * 
	 * This helper function sets up cells that are doors, room centers, or secret passages.
	 * 
	 * @param tileString 				-> 	cell information read in from file
	 * @param i 						-> 	row number of cell
	 * @param j 						-> 	column number of cell
	 * @throws BadConfigFormatException -> 	exception thrown if the input file format is incorrect
	 */
	private void specialTiles(String tileString, int i, int j) throws BadConfigFormatException{
		//first 4 check for doorways
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
	}
	
	
	/**
	 * This function acts as an entry point for findAllTargets, which find all reachable cells
	 * based on a given start cell and path length
	 * 
	 * @param startCell
	 * @param pathlength
	 */
	public void calcTargets(BoardCell startCell, int pathlength) {
		//visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		//visited.add(startCell);
		findAllTargets(startCell,new HashSet<BoardCell>(), pathlength);
		targets.remove(startCell);
		
		//removes all occupied spaces from targets
		Set<BoardCell> toBeDeleted = new HashSet<BoardCell>();
		for(BoardCell target : targets) {
			if(target.isOccupied() && !target.isRoomCenter()) {
				toBeDeleted.add(target);
			}
		}
		
		for(BoardCell target:toBeDeleted) {
			targets.remove(target);
		}
	}
	
	
	/**
	 * This function recursively finds all cells reachable from a given cell based
	 * on a given path length. 
	 * 
	 * @param startCell
	 * @param previousCells
	 * @param pathLength
	 */
	public void findAllTargets(BoardCell startCell, Set<BoardCell> previousCells, int pathLength) {		
		
		for(BoardCell b : startCell.getAdjList()) {
			if(pathLength == 1) {
				if(!previousCells.contains(b)) {
					targets.add(b);
				}	
			} else if(b.isRoomCenter()) {
				targets.add(b);
			} else if(!previousCells.contains(b) && !b.isOccupied()) {
				previousCells.add(startCell);
				Set<BoardCell> temp = new HashSet<>(previousCells);
				findAllTargets(b,temp,pathLength-1);
			}
		}		
	}
	
	/**
	 * This function deals the cards to the players
	 */
	public void deal() {
		for(Player p:players) {
			//stops when all the cards have been delt out
			if(removableDeck.size()==0)
				break;
			Card temp = getRandomCard();
			removableDeck.remove(temp);
			p.addToHand(temp);
		}
		//I'm using a recursive call in case we have to add a mode with less that 6 players 
		//this method should still deal one card to each player till the deck is empty.
		if(removableDeck.size()!=0) {
			deal();
		}
	}
	
	//This function is mostly used for resetting without having to call initialize 
	public void redeal() {
		removableDeck = new HashSet<Card>(deck);
		removableDeck.remove(solution.getPerson());
		removableDeck.remove(solution.getRoom());
		removableDeck.remove(solution.getWeapon());
		
		deal();
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	public Set<BoardCell> getAdjList(int x, int y){
		return getCell(x,y).getAdjList();
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
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

	public Set<Room> getAllRooms(){
		return rooms;
	}
	
	public BoardCell getCell(int row, int col) {
		return boardCellArray[col][row];
	}
	
	public char getHallways() {
		return hallwayLetter;
	}
	
	public Set<Player> getPlayers(){
		return players;
	}
	
	public Player getPlayer(Color color) {
		for(Player p:players) {
			if(p.getColor()==color) {
				return p;
			}
		}
		return null;
	}
	
	public Set<Card> getWeapons(){
		return weapons;
	}
	
	public Set<Card> getCards(){
		return deck;
	}
	
	public Card getCard(String name) {
		for(Card c:deck) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public Card getRandomCard() {
		int iter = r.nextInt(removableDeck.size());
		int i = 0;
		for(Card temp:removableDeck) {
			if(i == iter) {
				return temp;
			}
			i++;
		}
		return null;
	}
	
	public void setSolution(){
		Card temp = getRandomCard();
		//Not the most efficient way since it's chosing randomly until it finds the type it wants 
		//but it should work on such a small set with reativly even numbers of each type
		while(temp.getType()!=CardType.PERSON) {
			temp = getRandomCard();
		}
		solution.setPerson(temp);
		removableDeck.remove(temp);
		while(temp.getType()!=CardType.WEAPON) {
			temp = getRandomCard();
		}
		solution.setWeapon(temp);
		removableDeck.remove(temp);
		while(temp.getType()!=CardType.ROOM) {
			temp = getRandomCard();
		}
		solution.setRoom(temp);
		removableDeck.remove(temp);
	}
	
	public Solution getSolution() {
		return solution;
	}
	
	public boolean checkAccusation(Card person, Card weapon, Card room) {
		if(person.equals(solution.getPerson()) && weapon.equals(solution.getWeapon()) && room.equals(solution.getRoom()))
			return true;
		else
			return false;
	}

	public Object handleSuggestion(Card person, Card weapon, Card room, Player guesser) {
		//We don't return imediatly in case we have to randomly return a card from someones hand
		
		for(Player p:players) {
			if(!(p.equals(guesser))) {
				Card disproved = p.disproveSuggestion(person, weapon, room);
				if(disproved!=null) {
					return disproved;
				}
			}
		}
		return null;
	}
	
	/**
	 * This function rolls the die when it's needed for player movement
	 */
	//This could be a private function since I think only board will call it but it still needs to be tested
	public int rollDie() {
		return (r.nextInt(6)+1);
	}
	
	public Player getNextPlayer() {
		if(nextPlayer.hasNext()) {
			return (Player) nextPlayer.next();
		}else {
			nextPlayer = players.iterator();
			return (Player) nextPlayer.next();
		}
	}
}
