package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	private Card roomCard;
	private Set<Card> allCards, seen, possibleWeapons, possiblePeople;
	
	public ComputerPlayer(String name, String color, int x, int y) throws BadConfigFormatException {
		super(name, color, x, y);
		
		seen = new HashSet<Card>();
		possiblePeople = new HashSet<Card>();
		possibleWeapons = new HashSet<Card>();
	}

	public Solution createSuggestion(Board board) {
		
		// use the room that the computer is currently in
		BoardCell currentLocation = board.getCell(this.getLocation()[1], this.getLocation()[0]);
		Room currentRoom = board.getRoom(currentLocation);
		roomCard = board.getCard(currentRoom.getName());
		
		// find possible people and weapons
		allCards = board.getCards();
		for(Card card : allCards) {
			if(!this.getHand().contains(card) && !this.seen.contains(card)) {
				if(card.getType() == CardType.PERSON) {
					possiblePeople.add(card);
					
				} else if(card.getType() == CardType.WEAPON) {
					possibleWeapons.add(card);
				}
			}
		}
		
		
		
		// pick a person that isn't the computer's hand and hasn't been seen by the computer
		// this code is awful, I know
		// thats what I get for procrastinating
		Card personGuess = null;
		Random rand = new Random();
		int pos = rand.nextInt(allCards.size());
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
	
	public BoardCell selectTargets() {
		return null;
	}
	
	public void addToSeen(Card card) {
		seen.add(card);
	}
	
	public Set<Card> getSeen() {
		return new HashSet<Card>();
	}
	
	
}
