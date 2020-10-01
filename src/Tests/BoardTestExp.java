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
	 * (0,3) (1,3) (2,3)   X
	 */
	
	@Test
	void testAdjacencyTopLeft() {
		TestBoardCell testUnit = testing.getCell(0,0);
		Set<TestBoardCell> adjList = testUnit.getAdjList();
		assertTrue(adjList.contains(testing.getCell(0,1)));
		assertTrue(adjList.contains(testing.getCell(1,0)));
		assertFalse(adjList.contains(testing.getCell(1,1)));
		assertEquals(2,adjList.size());
	}
	
	@Test
	void testAdjacencyBottomRight() {
		TestBoardCell testUnit = testing.getCell(3,3);
		Set<TestBoardCell> adjList = testUnit.getAdjList();
		assertTrue(adjList.contains(testing.getCell(2,3)));
		assertTrue(adjList.contains(testing.getCell(3,2)));
		assertFalse(adjList.contains(testing.getCell(2,2)));
		assertEquals(2,adjList.size());
	}
	
	@Test
	void testAdjacencyRightEdge() {
		TestBoardCell testUnit = testing.getCell(3,1);
		Set<TestBoardCell> adjList = testUnit.getAdjList();
		assertTrue(adjList.contains(testing.getCell(3,0)));
		assertTrue(adjList.contains(testing.getCell(2,1)));
		assertTrue(adjList.contains(testing.getCell(3,2)));
		assertEquals(3,adjList.size());
	}
	
	@Test
	void testAdjacencyLeftEdge() {
		TestBoardCell testUnit = testing.getCell(0,2);
		Set<TestBoardCell> adjList = testUnit.getAdjList();
		assertTrue(adjList.contains(testing.getCell(0,1)));
		assertTrue(adjList.contains(testing.getCell(1,2)));
		assertTrue(adjList.contains(testing.getCell(0,3)));
		assertEquals(3,adjList.size());
	}
	
	@Test
	void testAdjacencyTopEdge() {
		TestBoardCell testUnit = testing.getCell(2,0);
		Set<TestBoardCell> adjList = testUnit.getAdjList();
		assertTrue(adjList.contains(testing.getCell(1,0)));
		assertTrue(adjList.contains(testing.getCell(3,0)));
		assertTrue(adjList.contains(testing.getCell(2,1)));
		assertEquals(3,adjList.size());
	}
}
