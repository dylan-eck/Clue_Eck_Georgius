package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

class AccusationSuggestionTest {

	private static Board board;
	
	private static Card mrsWhite;
	private static Card knife;
	private static Card office;
	
	private static Card missScarlett;
	private static Card handGun;
	private static Card bathroom;
	
	private static Card movieTheater;
	
	private static Player mustard;
	private static Player scarlett;
	private static Player peacock;
	
	@BeforeAll
	static void makeBoard() {
		//The same as the setup for the previous tests but with our setUp files
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		 
		for(Player p:board.getPlayers()) {
			p.clearHand();
		}
		
		for(Player p:board.getPlayers()) {
			if(p.getColor().equals(Color.YELLOW)) mustard = p; 
			if(p.getColor().equals(Color.RED)) scarlett = p; 
			if(p.getColor().equals(Color.BLUE)) peacock = p; 
		}
		
		 board.getSolution().setPerson(board.getCard("Mrs. White"));
		 board.getSolution().setWeapon(board.getCard("Knife"));
		 board.getSolution().setRoom(board.getCard("Office"));
		
		 mrsWhite = new Card("Mrs. White","Person");
		 knife = new Card("Knife","Weapon");
		 office = new Card("Office","Room");
		 
		 missScarlett = new Card("Miss Scarlett","Person");
		 handGun = new Card("Hand Gun","Weapon");
		 bathroom = new Card("Bathroom","Room");
		 
		 movieTheater = new Card("Movie Theater","Room");
		 
			
		//Order checked is White, Plum, Peacock, Mustard, Green, Scarlet
		mustard.addCard(handGun);
		mustard.addCard(movieTheater);
		scarlett.addCard(bathroom);
		peacock.addCard(missScarlett);
		 
		 
	}
	
	@Test
	void AcusationTest() {		
		
		 assertTrue(board.checkAccusation(mrsWhite,knife,office));
		 assertFalse(board.checkAccusation(missScarlett,handGun,bathroom));
		 assertFalse(board.checkAccusation(missScarlett,knife,office));
		 assertFalse(board.checkAccusation(mrsWhite,handGun,office));
		 assertFalse(board.checkAccusation(missScarlett,knife,bathroom));
	}
	
	@Test
	void SuggestionTestPlayers() {
		
		//I want to return both the card and the person returning but I'm not sure how to do it yet
		//+ the thing that I wanted to use it for is hella complicated so probably won't do it
		assertEquals(peacock.disproveSuggestion(missScarlett,knife,office),missScarlett);
		assertEquals(mustard.disproveSuggestion(mrsWhite,handGun,bathroom),handGun);
		assertEquals(scarlett.disproveSuggestion(mrsWhite,knife,bathroom),bathroom);
		assertTrue(mustard.disproveSuggestion(mrsWhite,handGun,movieTheater).equals(handGun)||mustard.disproveSuggestion(mrsWhite,handGun,movieTheater).equals(movieTheater));
		assertEquals(mustard.disproveSuggestion(mrsWhite,knife,office),null);
		assertEquals(scarlett.disproveSuggestion(mrsWhite,knife,office),null);
	}
	
	@Test
	void SuggestionTestBoard() {
		
		assertEquals(board.handleSuggestion(mrsWhite, handGun, bathroom ,mustard),bathroom);
		assertEquals(board.handleSuggestion(mrsWhite, handGun, office ,mustard),null);
		assertEquals(board.handleSuggestion(missScarlett, handGun, office ,mustard),missScarlett);
		assertEquals(board.handleSuggestion(missScarlett, knife, bathroom,peacock),bathroom);
		assertEquals(board.handleSuggestion(missScarlett, handGun, bathroom,scarlett),handGun);
	}
}
