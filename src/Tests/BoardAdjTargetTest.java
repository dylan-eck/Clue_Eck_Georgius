package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
	
	@Test
	public void testGetTargetsGarden() {
		
		board.calcTargets(board.getCell(3, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(3, 7)));	
		//secret passage
		assertTrue(targets.contains(board.getCell(21, 21)));
		
		
		board.calcTargets(board.getCell(3, 3), 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(8, 4)));	
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(2, 7)));	
		
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(17, 21)));	
		
		board.calcTargets(board.getCell(3, 3), 5);
		targets= board.getTargets();
		assertEquals(25, targets.size());
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(0, 8)));	
		assertTrue(targets.contains(board.getCell(7, 0)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		//The two rooms
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(3, 12)));
		//through passage
		assertTrue(targets.contains(board.getCell(21, 17)));
		assertTrue(targets.contains(board.getCell(17, 23)));
		
	}
	
	@Test
	public void testgetTargetCourtYard() {

		board.calcTargets(board.getCell(12, 12), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(8, 12)));	
		
		board.calcTargets(board.getCell(12, 12), 2);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 7)));
		assertTrue(targets.contains(board.getCell(8, 11)));	
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(13, 16)));	
		assertTrue(targets.contains(board.getCell(16, 11)));

		board.calcTargets(board.getCell(12, 12), 4);
		targets= board.getTargets();
		assertEquals(23, targets.size());
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(7, 12)));	
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(16, 15)));	
		//two rooms it can get to
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(12, 21)));	
	}
	
	@Test
	public void testGetTargetsHallways() {
		
		board.calcTargets(board.getCell(15, 9), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(15, 8)));
		assertTrue(targets.contains(board.getCell(16, 9)));	
	
		board.calcTargets(board.getCell(15, 9), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(15, 7)));
		assertTrue(targets.contains(board.getCell(16, 8)));	
		assertTrue(targets.contains(board.getCell(16, 10)));	
		
		board.calcTargets(board.getCell(15, 9), 5);
		targets= board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(16, 5)));
		assertTrue(targets.contains(board.getCell(18, 7)));
		assertTrue(targets.contains(board.getCell(16, 13)));	
		assertTrue(targets.contains(board.getCell(15, 18)));	
		assertTrue(targets.contains(board.getCell(11, 8)));	
		//test courtyard
		assertTrue(targets.contains(board.getCell(12, 12)));	
		
	}
	
	@Test
	public void testGetTargetsWithOccupied() {

		//test someone compleatly blocked in
		board.getCell(8, 23).setOccupied(true);
		board.calcTargets(board.getCell(8, 24), 1);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(0, targets.size());
		
		board.getCell(8, 23).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 2);
		board.getCell(8, 23).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(7, 23)));	

		board.getCell(17, 21).setOccupied(true);
		board.getCell(19, 19).setOccupied(true);
		board.calcTargets(board.getCell(21, 21), 2);
		board.getCell(19, 19).setOccupied(false);
		board.getCell(17, 21).setOccupied(false);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(17, 22)));
		assertTrue(targets.contains(board.getCell(16, 21)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(19, 16)));
	}

}
