package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

class gameSetupTests {

	private static Board board;
	private static Set<Card> weapons;
	private static Card handGun;
	private static Card knife;
	private static Card leadPipe;
	
	@BeforeAll
	static void makeBoard() {
		//The same as the setup for the previous tests but with our setUp files
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv","ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		weapons = board.getWeapons();
		
		for(Card c:weapons){
			if(c.getName().equals("Hand Gun")) handGun = c;
			if(c.getName().equals("Knife")) knife = c;
			if(c.getName().equals("Lead Pipe")) leadPipe = c;
		}
	}
	
	//Tests that the players are properly loaded
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
	
	//Tests that the weapons are properly loaded
	@Test
	public void testWeapons() {

		assertEquals(weapons.size(),6);
		//contains doesn't work. tried rewritting the overiding equals.
		//nothing worked. Above is profe that that handgun is in the set but contains refuses t acknowledge that.
		
		assertTrue(weapons.contains(handGun));
		assertTrue(weapons.contains(knife));
		assertTrue(weapons.contains(leadPipe));
	}
	
	//Tests that the deck is created
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
	
	//Tests that the cards are properly loaded
	@Test
	public void testCardTypes() {
		assertEquals(board.getCard("Knife").getType(),CardType.WEAPON);
		assertEquals(board.getCard("Rope").getType(),CardType.WEAPON);
		assertEquals(board.getCard("Wrench").getType(),CardType.WEAPON);
		assertEquals(board.getCard("Entry Way").getType(),CardType.ROOM);
		assertEquals(board.getCard("Bathroom").getType(),CardType.ROOM);
		assertEquals(board.getCard("Court Yard").getType(),CardType.ROOM);
		assertEquals(board.getCard("Mrs. Peacock").getType(),CardType.PERSON);
		assertEquals(board.getCard("Reverend Green").getType(),CardType.PERSON);
		assertEquals(board.getCard("Colonel Mustard").getType(),CardType.PERSON);
	}

	//Test is the players were assigned a hand of the right size
	@Test
	public void testPlayerHandSizeAndSolution() {
		Set<Player> players = board.getPlayers();
		
		Card weapon = board.getSolution().getWeapon();
		Card room = board.getSolution().getRoom();
		Card person = board.getSolution().getPerson();
		
		assertEquals(weapon.getType(),CardType.WEAPON);
		assertEquals(room.getType(),CardType.ROOM);
		assertEquals(person.getType(),CardType.PERSON);
		
		//checks every hand to see if it has 2-3 cards (one will have 2 all others will have 3)
		for(Player p:players) {
			assertTrue(p.getHand().size()==3 || p.getHand().size()==2);
		}
	}
	
	//Makes sure that the players all get random hands with no repeats
	@Test
	public void testPlayerHands() {
		Set<Player> players = board.getPlayers();
		
		//test 1000 random cards against another 1000 random cards in other players hands
		for(int i = 0;i<=1000;i++) {
			Player player1 = getRandomPlayer(players);
			Player player2 = getRandomPlayer(players);
			//ensures that player1 and player2 are not the same player
			while(player2 == player1) {
				player2 = getRandomPlayer(players);
			}
			
			Set<Card> hand1 = player1.getHand();
			Set<Card> hand2 = player2.getHand();
			
			Card card1 = getRandomCard(hand1);
			Card card2 = getRandomCard(hand2);
			
			assertFalse(card1.equals(card2));
		}
	}
	
	//helper function for the above test
	private Player getRandomPlayer(Set<Player> p) {
		Random r = new Random();
		int iter = r.nextInt(p.size());
		int i = 0;
		for(Player temp:p) {
			if(i == iter) 
				return temp;
			i++;
		}
		return null;
	}
	
	//helper function for the above test
	private Card getRandomCard(Set<Card> h) {
		Random r = new Random();
		int iter2 = r.nextInt(h.size());
		int i = 0;
		for(Card temp:h) {
			if(i == iter2) 
				return temp;
			i++;
		}
		return null;
	}
}
