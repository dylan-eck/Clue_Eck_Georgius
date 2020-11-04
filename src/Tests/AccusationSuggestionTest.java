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
	
	@BeforeAll
	static void makeBoard() {
		//The same as the setup for the previous tests but with our setUp files
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	void AcusationTest() {
		 board.getSolution().setPerson(board.getCard("Mrs. White"));
		 board.getSolution().setWeapon(board.getCard("Knife"));
		 board.getSolution().setRoom(board.getCard("Office"));
		 
		 Card personGus = board.getCard("Mrs. White");
		 Card weaponGus = board.getCard("Knife");
		 Card roomGus = board.getCard("Office");
		 
		 Card personWrong = board.getCard("Miss Scarlett");
		 Card weaponWrong = board.getCard("Hand Gun");
		 Card roomWrong = board.getCard("Bathroom");
		 
		 assertTrue(board.checkAccusation(personGus,weaponGus,roomGus));
		 assertFalse(board.checkAccusation(personWrong,weaponWrong,roomWrong));
	}

}
