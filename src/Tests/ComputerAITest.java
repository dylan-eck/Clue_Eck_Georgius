package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
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
		ComputerPlayer testPlayer;
		try {
			
			testPlayer = new ComputerPlayer("Miss Scarlet", "Red", testLocations[0][0], testLocations[0][1]);
			
			Solution computerSuggestion = testPlayer.createSuggestion(board);
			BoardCell currentLocation = board.getCell(testPlayer.getLocation()[0], testPlayer.getLocation()[1]);
			Room currentRoom = board.getRoom(currentLocation);
			
			assertTrue(computerSuggestion.getRoom().getName().equals(currentRoom.getName()));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
	@Test
	// test when there is only one card of each type that hasn't been seen
	void testSelectionOneOption() {
		ComputerPlayer testPlayer;
		try {
			testPlayer = new ComputerPlayer("Colonel Mustard", "Yellow", testLocations[1][0], testLocations[1][1]);
			
			// add all cards to seen except mrsWhite and office
			for(Card card : board.getCards() ) {
				if(!card.equals(mrsWhite) && !card.equals(knife)) {
					testPlayer.addToSeen(card);
				}
			}
			
			Solution computerSuggestion = testPlayer.createSuggestion(board);
			
			// make sure suggestion contains mrsWhite and office
			assertEquals(computerSuggestion.getPerson(),mrsWhite);
			assertEquals(computerSuggestion.getWeapon(),knife);
			
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
			testPlayer = new ComputerPlayer("Mrs. White", "White", testLocations[2][0], testLocations[2][1]);
			
			testPlayer.addToSeen(mrsWhite);
			testPlayer.addToSeen(knife);
			testPlayer.addToSeen(office);
			
			Solution computerSuggestion = testPlayer.createSuggestion(board);
			
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
	
	@Test
	// test when no targets are unseen rooms
	void testTargetNoUnseenRooms() {
		ComputerPlayer testPlayer;
		try {
			testPlayer = new ComputerPlayer("Mrs. White", "White", 16, 18);
			Set<BoardCell> targets = new HashSet<BoardCell>();
			
			for(int i = 0; i < 500; i++) {
				targets.add(testPlayer.selectTargets(board, 3));
			}
			
			assertTrue(targets.contains(board.getCell(16, 17)));
			assertTrue(targets.contains(board.getCell(17, 14)));
			assertTrue(targets.contains(board.getCell(15, 16)));
			assertTrue(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(17, 18)));
			
			testPlayer = new ComputerPlayer("Mrs. White", "White", 7, 12);
			targets = new HashSet<BoardCell>();
			testPlayer.addToSeen(board.getCard("Movie Theater"));
			testPlayer.addToSeen(board.getCard("Court Yard"));
			
			for(int i = 0; i < 200; i++) {
				targets.add(testPlayer.selectTargets(board, 3));
			}
			
			assertTrue(targets.contains(board.getCell(12, 12)));
			assertTrue(targets.contains(board.getCell(12, 3)));
			assertTrue(targets.contains(board.getCell(10, 8)));
			assertTrue(targets.contains(board.getCell(11, 7)));
			assertTrue(targets.contains(board.getCell(13, 7)));
			assertTrue(targets.contains(board.getCell(14, 8)));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
	@Test
	// test when only one possible target is an unseen room
	void testTargetSingleUnseenRoom() {
		ComputerPlayer testPlayer;
		try {
			
			testPlayer = new ComputerPlayer("Miss Scarlet", "Red", 17, 16);
			BoardCell target = testPlayer.selectTargets(board, 4);
			
			assertTrue(target.equals(board.getCell(21, 21)));
			
			testPlayer = new ComputerPlayer("Colonel Mustard", "Yellow", 7, 12);
			testPlayer.addToSeen(board.getCard("Movie Theater"));
			target = testPlayer.selectTargets(board, 2);
			
			assertTrue(target.equals(board.getCell(12, 12)));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
	@Test 
	// test when multiple targets are unseen rooms
	void testTargetMultipleUnseenRooms() {
		ComputerPlayer testPlayer;
		try {
			testPlayer = new ComputerPlayer("Mrs. White", "White", 7, 12);
			Set<BoardCell> targets = new HashSet<BoardCell>();
			
			for(int i = 0; i < 200; i++) {
				targets.add(testPlayer.selectTargets(board, 2));
			}
			
			// make sure a room was selected
			assertTrue(targets.contains(board.getCell(12, 12)) || targets.contains(board.getCell(12, 3)));
			
			assertTrue(!targets.contains(board.getCell(10, 7)));
			assertTrue(!targets.contains(board.getCell(11, 8)));
			assertTrue(!targets.contains(board.getCell(13, 8)));
			assertTrue(!targets.contains(board.getCell(14, 7)));
			
		} catch (BadConfigFormatException e) {
			fail("test failed due to BadConfigFormatException");
		}
	}
	
}
