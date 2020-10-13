package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testAdjancencyRooms() {

		// test center room
		Set<BoardCell> testList = board.getAdjList(12, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(8, 12)));
		assertTrue(testList.contains(board.getCell(12, 8)));
		assertTrue(testList.contains(board.getCell(12, 16)));
		assertTrue(testList.contains(board.getCell(16, 12)));
		
		// test secret passage
		testList = board.getAdjList(21, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(21, 17)));
		
		// test room not center
		testList = board.getAdjList(19, 4);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testAdjancencyAtDoors() {
		
		Set<BoardCell> testList = board.getAdjList(8, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(11, 8)));
		assertTrue(testList.contains(board.getCell(12, 12)));
		assertTrue(testList.contains(board.getCell(12, 7)));
		assertTrue(testList.contains(board.getCell(13, 8)));
		
		testList = board.getAdjList(3, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 2)));
		assertTrue(testList.contains(board.getCell(17, 4)));
		assertTrue(testList.contains(board.getCell(21, 3)));
		
		
		
	}
	
	
	@Test
	public void testAdjancencyWalkway() {
		
		// Test walkway with only walkways as adjacent locations
		Set<BoardCell> testList = board.getAdjList(8, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 8)));
		assertTrue(testList.contains(board.getCell(8, 7)));
		assertTrue(testList.contains(board.getCell(8, 9)));
		assertTrue(testList.contains(board.getCell(9, 8)));
		
		// test next to room
		testList = board.getAdjList(1, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 2)));
		
		// test edges of board
		testList = board.getAdjList(0, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(8, 1)));
		
		testList = board.getAdjList(7, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 7)));
		
		testList = board.getAdjList(24, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(16, 23)));
		
		testList = board.getAdjList(8, 24);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 8)));
		
	}

}
