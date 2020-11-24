package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private Random rand = new Random();
	
	private Card roomCard;
	private Set<Card> allCards, possibleWeapons, possiblePeople;
	
	private boolean accusationReady;
	
	public ComputerPlayer(String name, String color, int x, int y) throws BadConfigFormatException {
		super(name, color, x, y);
		
		possiblePeople = new HashSet<Card>();
		possibleWeapons = new HashSet<Card>();
		accusationReady = false;
	}

	/**
	 * This function generates a suggestion for the computer player based what it knows from what 
	 * has already happened in the current game.
	 * 
	 * @param board		-> 	the game board
	 * @return			-> 	a suggestion as to what the solution might be
	 */
	public Solution createSuggestion(Board board) {
		
		// use the room that the computer is currently in
		BoardCell currentLocation = board.getCell(this.getLocation()[1], this.getLocation()[0]);
		Room currentRoom = board.getRoom(currentLocation);
		roomCard = board.getCard(currentRoom.getName());
		
		// find possible people and weapons
		allCards = board.getCards();
		for(Card card : allCards) {
			if(!this.getHand().contains(card) && !getSeen().contains(card)) {
				if(card.getType() == CardType.PERSON) {
					possiblePeople.add(card);
					
				} else if(card.getType() == CardType.WEAPON) {
					possibleWeapons.add(card);
				}
			}
		}
		
		// pick a person that isn't the computer's hand and hasn't been seen by the computer
		//TODO change this (iterating through a HashSet seams like a bad idea)
		// this code is awful, I know
		// thats what I get for procrastinating
		Card personGuess = null;
		int pos = rand.nextInt(possiblePeople.size());
		int iterator = 0;
		for(Card card : possiblePeople) {
			if(iterator == pos) {
				personGuess = card;
				break;
			}
			iterator++;
		}
		// pick a weapon that isn't the computer's hand and hasn't been seen by the computer
		Card weaponGuess = null;
		pos = rand.nextInt(possibleWeapons.size());
		iterator = 0;
		for(Card card : possibleWeapons) {
			if(iterator == pos) {
				weaponGuess = card;
				break;
			}
			iterator++;
		}
		
		return new Solution(roomCard, personGuess, weaponGuess);
	}
	
	/**
	 * This function picks the cell that the computer will move to next.
	 * 
	 * @param board				-> 	the game board
	 * @param pathLength		->	the distance that the computer player can move
	 * @return					-> 	a single BoardCell that the computer player would like to move to
	 */
	public BoardCell selectTargets(Board board, int pathLength) {
		
		BoardCell currentLocation = board.getCell(this.getLocation()[1], this.getLocation()[0]);
		board.calcTargets(currentLocation, pathLength);
		Set<BoardCell> targets = board.getTargets();
		
		Set<BoardCell> unseenRooms = new HashSet<BoardCell>();
		for(BoardCell cell : targets) {
			//TODO this should use isRoom(), not getChar();
			if(cell.getChar() != 'H' && !getSeen().contains(board.getCard(board.getRoom(cell).getName()))) {
				unseenRooms.add(cell);
			}
		}
		
		BoardCell targetCell = null;
		
		int selection = 0;
		if(!unseenRooms.isEmpty()) {
			//TODO change this (iterating through a HashSet seams like a bad idea)
			selection = rand.nextInt(unseenRooms.size());
			int iterator = 0;
			for(BoardCell cell : unseenRooms) {
				if(iterator == selection) {
					targetCell = cell;
					break;
				}
				iterator++;
			}
		} else {
			selection = rand.nextInt(targets.size());
			int iterator = 0;
			for(BoardCell cell : targets) {
				if(iterator == selection) {
					targetCell = cell;
					break;
				}
				iterator++;
			}
		}
		
		return targetCell;
	}
	
	public boolean isHuman() {
		return false;
	}
	
	public boolean accusationReady() {
		return accusationReady;
	}

}
