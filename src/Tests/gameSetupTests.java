package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

class gameSetupTests {

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
	public void testPlayers() {
		Set<Player> players = board.getPlayers();
		
		Player green = board.getPlayer(Color.GREEN);
		Player yellow = board.getPlayer(Color.YELLOW);
		Player red = board.getPlayer(Color.RED);
		
		assertEquals(players.size(),6);
		assertTrue(players.contains(green));
		assertTrue(players.contains(yellow));
		assertTrue(players.contains(red));
	}
	
	

}
