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
	
	@BeforeAll
	static void makeBoard() {
		//The same as the setup for the previous tests but with our setUp files
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		 
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
	}
	
	//We mess with peoples hands in a lot of the tests so it's worth just resetting hands every time
	///Note this does not affect the solution
	@BeforeEach
	void redeal() {
		board.redeal();
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
	void SuggestionTest() {
		Set<Player> players = board.getPlayers();
		for(Player p:players) {
			p.clearHand();
		}
		
		//Order checked is Peacock, Mustard, Scarlet
		board.getPlayer(Color.YELLOW).addCard(handGun);
		board.getPlayer(Color.YELLOW).addCard(movieTheater);
		board.getPlayer(Color.RED).addCard(bathroom);
		board.getPlayer(Color.BLUE).addCard(missScarlett);
		
		//I want to return both the card and the person returning but I'm not sure how to do it yet
		//+ the thing that I wanted to use it for is hella complicated so probably won't do it
		assertEquals(board.handleSuggestion(missScarlett,knife,office),missScarlett);
		assertEquals(board.handleSuggestion(mrsWhite,handGun,bathroom),handGun);
		assertEquals(board.handleSuggestion(mrsWhite,knife,bathroom),bathroom);
		assertTrue(board.handleSuggestion(mrsWhite,handGun,movieTheater).equals(handGun)||board.handleSuggestion(mrsWhite,handGun,movieTheater).equals(movieTheater));
		assertEquals(board.handleSuggestion(mrsWhite,knife,office),null);
	}
}
