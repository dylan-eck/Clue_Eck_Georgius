package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class ComputerAITest {
	
	private static Board board;
	
	private static Card mrsWhite;
	private static Card knife;
	private static Card office;
	
	private static Card missScarlett;
	private static Card handGun;
	private static Card bathroom;
	
	private static Card movieTheater;
	
	@BeforeAll
	static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		board.initialize();	
		
		mrsWhite = board.getCard("Mrs. White");
		knife = board.getCard("Knife");
		office = board.getCard("Office");
		 
	}

	@Test
	void testRoomMatchesCurrentLocation() {
		fail("Not yet implemented");
		
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
