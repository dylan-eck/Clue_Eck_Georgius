package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

class ComputerAITest {
	
	private static Board board;
	
	private static Card mrsWhite;
	private static Card knife;
	private static Card office;
	
	private static int[][] testLocations = {{3,1},{3,12},{12,12},{21,22}};
	
	@BeforeAll
	static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		board.initialize();	
		
		// test cards
		mrsWhite = board.getCard("Mrs. White");
		knife = board.getCard("Knife");
		office = board.getCard("Office");
		
		 
	}

	@Test
	// make sure that the room given in the suggestion matches the room that the computer is currently in
	void testRoomMatchesCurrentLocation() {
		ComputerPlayer[] testPlayers = new ComputerPlayer[testLocations.length];
		try {
			for(int iterator = 0; iterator < testLocations.length; iterator++) {
				testPlayers[iterator] = new ComputerPlayer("Miss Scarlet", "Red", testLocations[iterator][0], testLocations[iterator][1]);
				
				Solution computerSuggestion = testPlayers[iterator].createSuggestion();
				BoardCell currentLocation = board.getCell(testPlayers[iterator].getLocation()[0], testPlayers[iterator].getLocation()[1]);
				Room currentRoom = board.getRoom(currentLocation);
				assertTrue(computerSuggestion.getRoom().getName() == currentRoom.getName());
			}
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
	@Test
	// test when there is only one card of each type that hasn't been seen
	void testSelectionOneOption() {
		ComputerPlayer testPlayer;
		try {
			testPlayer = new ComputerPlayer("Colonel Mustard", "Yellow", 2, 4);
			
			// add all cards to seen except mrsWhite and office
			for(Card card : board.getCards() ) {
				if(!card.equals(mrsWhite) && !card.equals(knife)) {
					testPlayer.addToSeen(card);
				}
			}
			
			Solution computerSuggestion = testPlayer.createSuggestion();
			
			// make sure suggestion contains mrsWhite and office
			assertTrue(computerSuggestion.getPerson() == mrsWhite);
			assertTrue(computerSuggestion.getWeapon() == knife);
			
			// make sure the suggestion does not contain invalid cards
			assertTrue(board.getCards().contains(computerSuggestion.getPerson()));
			assertTrue(board.getCards().contains(computerSuggestion.getWeapon()));
			assertTrue(board.getCards().contains(computerSuggestion.getRoom()));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
	@Test
	// test when there are multiple cards of each type that have not been seen
	void testSelectionMultipleOptions() {
		ComputerPlayer testPlayer;
		try {
			testPlayer = new ComputerPlayer("Colonel Mustard", "Yellow", 2, 4);
			
			testPlayer.addToSeen(mrsWhite);
			testPlayer.addToSeen(knife);
			testPlayer.addToSeen(office);
			
			Solution computerSuggestion = testPlayer.createSuggestion();
			
			// make sure seen cards are not in the suggestion
			assertTrue(computerSuggestion.getPerson() != mrsWhite);
			assertTrue(computerSuggestion.getWeapon() != knife);
			assertTrue(computerSuggestion.getRoom() != office);
			
			// make sure the suggestion does not contain invalid cards
			assertTrue(board.getCards().contains(computerSuggestion.getPerson()));
			assertTrue(board.getCards().contains(computerSuggestion.getWeapon()));
			assertTrue(board.getCards().contains(computerSuggestion.getRoom()));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
}
