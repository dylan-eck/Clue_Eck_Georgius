package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;

class AccusationSuggestionTest {

	private static Board board;
	
	private static Card personGus;
	private static Card weaponGus;
	private static Card roomGus;
	
	private static Card personWrong;
	private static Card weaponWrong;
	private static Card roomWrong;
	
	@BeforeAll
	static void makeBoard() {
		//The same as the setup for the previous tests but with our setUp files
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		 
		 personGus = new Card("Mrs. White","Person");
		 weaponGus = new Card("Knife","Weapon");
		 roomGus = new Card("Office","Room");
		 
		 personWrong = new Card("Miss Scarlett","Person");
		 weaponWrong = new Card("Hand Gun","Weapon");
		 roomWrong = new Card("Bathroom","Room");
	}
	
	@Test
	void AcusationTest() {		
		 board.getSolution().setPerson(board.getCard("Mrs. White"));
		 board.getSolution().setWeapon(board.getCard("Knife"));
		 board.getSolution().setRoom(board.getCard("Office"));
		
		 assertTrue(board.checkAccusation(personGus,weaponGus,roomGus));
		 assertFalse(board.checkAccusation(personWrong,weaponWrong,roomWrong));
		 assertFalse(board.checkAccusation(personWrong,weaponGus,roomGus));
		 assertFalse(board.checkAccusation(personGus,weaponWrong,roomGus));
		 assertFalse(board.checkAccusation(personWrong,weaponGus,roomWrong));
	}
	
	//@Test
	void SuggestionTest() {
		
	}

}
