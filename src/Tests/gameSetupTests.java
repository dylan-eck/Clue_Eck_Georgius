package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
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
	
	@Test
	public void testWeapons() {
		Set<String> weapons = board.getWeapons();
		
		assertEquals(weapons.size(),6);
		assertTrue(weapons.contains("Hand Gun"));
		assertTrue(weapons.contains("Knife"));
		assertTrue(weapons.contains("Lead Pipe"));
	}
	
	@Test
	public void testCards() {
		Set<Card> cards = board.getCards(); 
		
		assertEquals(cards.size(),21);
		assertTrue(cards.contains(board.getCard("Hand Gun")));
		assertTrue(cards.contains(board.getCard("Rope")));
		assertTrue(cards.contains(board.getCard("Candlestick")));
		assertTrue(cards.contains(board.getCard("Kitchen")));
		assertTrue(cards.contains(board.getCard("Bathroom")));
		assertTrue(cards.contains(board.getCard("Garden")));
		assertTrue(cards.contains(board.getCard("Office")));
		assertTrue(cards.contains(board.getCard("Mrs. White")));
		assertTrue(cards.contains(board.getCard("Miss Scarlett")));
		assertTrue(cards.contains(board.getCard("Professor Plum")));
	}

}
