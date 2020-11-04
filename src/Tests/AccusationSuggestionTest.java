package Tests;

import static org.junit.jupiter.api.Assertions.*;

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
	void correctAcusationTest() {
		 Card personAns = board.getSolution().getPerson();
		 Card weaponAns = board.getSolution().getWeapon();
		 Card roomAns = board.getSolution().getRoom();
		 
		 Card personGus = board.getCard(personAns.getName());
		 Card weaponGus = board.getCard(weaponAns.getName());
		 Card roomGus = board.getCard(roomAns.getName());
		 
		 assertTrue(board.checkAccusation(personGus,weaponGus,roomGus));
	}

}
