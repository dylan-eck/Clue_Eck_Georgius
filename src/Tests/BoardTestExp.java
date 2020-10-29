/*  Dylan Eck
 *  Riley Georgius
 *  Section B
 *  Group 23
 */

package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Experiments.TestBoard;
import Experiments.TestBoardCell;

class BoardTestExp {
	TestBoard testing;
	
	@BeforeEach
	public void setUp() {
		//our Test board can change size depending on what you put in the constructor
		testing = new TestBoard(4,4);
	}
	
	/* Test Locations
	 * 
	 *   X   (1,0)   X   (3,0)
	 * (0,1) (1,1) (2,1)   X
	 *   X   (2,1) (2,2) (3,2)
	 * (0,3)   R     R     X
	 */
	
	// Adjacency tests
	@Test
	void testAdjacencyTopLeft() {
		TestBoardCell testUnit = testing.getCell(0,0);
		Set<TestBoardCell> adjList = testUnit.getAdjList(testing);
		assertTrue(adjList.contains(testing.getCell(0,1)));
		assertTrue(adjList.contains(testing.getCell(1,0)));
		assertEquals(2,adjList.size());
	}
	
	@Test
	void testAdjacencyBottomRight() {
		TestBoardCell testUnit = testing.getCell(3,3);
		Set<TestBoardCell> adjList = testUnit.getAdjList(testing);
		assertTrue(adjList.contains(testing.getCell(2,3)));
		assertTrue(adjList.contains(testing.getCell(3,2)));
		assertEquals(2,adjList.size());
	}
	
	@Test
	void testAdjacencyRightEdge() {
		TestBoardCell testUnit = testing.getCell(3,1);
		Set<TestBoardCell> adjList = testUnit.getAdjList(testing);
		assertTrue(adjList.contains(testing.getCell(3,0)));
		assertTrue(adjList.contains(testing.getCell(2,1)));
		assertTrue(adjList.contains(testing.getCell(3,2)));
		assertEquals(3,adjList.size());
	}
	
	@Test
	void testAdjacencyLeftEdge() {
		TestBoardCell testUnit = testing.getCell(0,2);
		Set<TestBoardCell> adjList = testUnit.getAdjList(testing);
		assertTrue(adjList.contains(testing.getCell(0,1)));
		assertTrue(adjList.contains(testing.getCell(1,2)));
		assertTrue(adjList.contains(testing.getCell(0,3)));
		assertEquals(3,adjList.size());
	}
	
	@Test
	void testAdjacencyTopEdge() {
		TestBoardCell testUnit = testing.getCell(2,0);
		Set<TestBoardCell> adjList = testUnit.getAdjList(testing);
		assertTrue(adjList.contains(testing.getCell(1,0)));
		assertTrue(adjList.contains(testing.getCell(3,0)));
		assertTrue(adjList.contains(testing.getCell(2,1)));
		assertEquals(3,adjList.size());
	}
	
	//calcTarget Tests
	@Test
	void testCalcTargetsNormalTopLeft() {
		TestBoardCell testUnit = testing.getCell(0, 0);
		testing.calcTargets(testUnit, 4);
		Set<TestBoardCell> targets = testing.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(testing.getCell(1, 1)));
		assertTrue(targets.contains(testing.getCell(2, 2)));
		assertTrue(targets.contains(testing.getCell(0, 2)));
		assertTrue(targets.contains(testing.getCell(2, 0)));
		assertTrue(targets.contains(testing.getCell(1, 3)));
		assertTrue(targets.contains(testing.getCell(3, 1)));
		
	}
	
	
	@Test
	void testCalcTargetsNormalTopRight() {
		TestBoardCell testUnit = testing.getCell(3, 0);
		testing.calcTargets(testUnit, 2);
		Set<TestBoardCell> targets = testing.getTargets();
		
		assertEquals(3, targets.size());
		assertTrue(targets.contains(testing.getCell(1, 0)));
		assertTrue(targets.contains(testing.getCell(2, 1)));
		assertTrue(targets.contains(testing.getCell(3, 2)));
	}
	
	
	@Test
	void testCalcTargetsNormalMiddle() {
		TestBoardCell testUnit = testing.getCell(2, 2);
		testing.calcTargets(testUnit, 3);
		Set<TestBoardCell> targets = testing.getTargets();
		
		assertEquals(9, targets.size());
		assertTrue(targets.contains(testing.getCell(1, 0)));
		assertTrue(targets.contains(testing.getCell(0, 1)));
		assertTrue(targets.contains(testing.getCell(3, 0)));
		assertTrue(targets.contains(testing.getCell(2, 1)));
		assertTrue(targets.contains(testing.getCell(1, 2)));
		assertTrue(targets.contains(testing.getCell(0, 3)));
		assertTrue(targets.contains(testing.getCell(3, 2)));
		//test for rooms
		assertTrue(targets.contains(testing.getCell(2, 3)));
		assertTrue(targets.contains(testing.getCell(1, 3)));
	}

	
	@Test
	void testCalcTargetsTop() {
		TestBoardCell testUnit = testing.getCell(1, 0);
		testing.calcTargets(testUnit, 1);
		Set<TestBoardCell> targets = testing.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(testing.getCell(0, 0)));
		assertTrue(targets.contains(testing.getCell(2, 0)));
		assertTrue(targets.contains(testing.getCell(1, 1)));
	}
	
	@Test
	void testCalcTargetsBottomRight() {
		TestBoardCell testUnit = testing.getCell(3, 3);
		testing.calcTargets(testUnit, 6);
		Set<TestBoardCell> targets = testing.getTargets();
		
		assertEquals(8, targets.size());
		assertTrue(targets.contains(testing.getCell(0, 0)));
		assertTrue(targets.contains(testing.getCell(0, 2)));
		assertTrue(targets.contains(testing.getCell(1, 1)));
		assertTrue(targets.contains(testing.getCell(1, 3)));
		assertTrue(targets.contains(testing.getCell(2, 0)));
		assertTrue(targets.contains(testing.getCell(2, 2)));
		assertTrue(targets.contains(testing.getCell(2, 3)));
		assertTrue(targets.contains(testing.getCell(3, 1)));

	}
	
	// Check that the player can move into rooms even if it requires fewer moves than their roll
	@Test
	void testCalcTargetsEnterRoom() {
		TestBoardCell testUnit = testing.getCell(1, 2);
		//has to be an even number of moves
		testing.calcTargets(testUnit, 2);
		Set<TestBoardCell> targets = testing.getTargets();
		assertEquals(7, targets.size());
		//the two rooms
		assertTrue(targets.contains(testing.getCell(1, 3)));
		assertTrue(targets.contains(testing.getCell(2, 3)));
		
		assertTrue(targets.contains(testing.getCell(3, 2)));
		assertTrue(targets.contains(testing.getCell(2, 1)));
		assertTrue(targets.contains(testing.getCell(1, 0)));
		assertTrue(targets.contains(testing.getCell(0, 1)));
		assertTrue(targets.contains(testing.getCell(0, 3)));
	}
	
	// Make sure the player cannot move onto occupied squares
	@Test
	void testCalcTargetsOccupied() {
		TestBoardCell testUnit = testing.getCell(0, 0);
		testing.getCell(2, 1).setOccupied(true);
		testing.getCell(0,3).setOccupied(true);
		
		testing.calcTargets(testUnit, 3);
		Set<TestBoardCell> targets = testing.getTargets();
		
		testing.getCell(2, 1).setOccupied(false);
		testing.getCell(0,3).setOccupied(false);
		
		assertEquals(3, targets.size());
		assertTrue(targets.contains(testing.getCell(1, 0)));
		assertTrue(targets.contains(testing.getCell(0, 1)));
		assertTrue(targets.contains(testing.getCell(1, 2)));
	}
	
}
