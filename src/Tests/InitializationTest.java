package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class InitializationTest {
	
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

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

	//Most of these tests are going to be the same as in FileInitTest306 just changed 
	//to expect the correct values for our board
	
	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Entry Way", board.getRoom('E').getName() );
		assertEquals("Garden", board.getRoom('G').getName() );
		assertEquals("Court Yard", board.getRoom('C').getName() );
		assertEquals("Bathroom", board.getRoom('B').getName() );
		assertEquals("Office", board.getRoom('O').getName() );
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(4, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(7, 12);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(4, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(17, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		//Tests the two doors in the one tile wide walkway
		cell = board.getCell(17, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		cell = board.getCell(17, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(0, 7);
		assertFalse(cell.isDoorway());
		// Test that rooms are not doors
		cell = board.getCell(12, 12);
		assertFalse(cell.isDoorway());
	}
	
	// Test that we have the correct number of doors. 
	//Exactly the same as 306 except we have 3 more doors
		@Test
		public void testNumberOfDoorways() {
			int numDoors = 0;
			for (int row = 0; row < board.getNumRows(); row++)
				for (int col = 0; col < board.getNumColumns(); col++) {
					BoardCell cell = board.getCell(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(22, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRooms() {
			// tests movie theater for center cell
			BoardCell cell = board.getCell( 12, 3);
			Room room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Movie Theater" ) ;
			assertFalse( cell.isLabel() );
			assertTrue( cell.isRoomCenter() ) ;
			assertFalse( cell.isDoorway()) ;
			assertEquals(room.getCenterCell(),cell);

			// tests entry way for label cell
			cell = board.getCell(1, 12);
			room = board.getRoom( cell );
			assertTrue( room != null );
			assertEquals( room.getName(), "Entry Way" ) ;
			assertTrue( cell.isLabel() );
			assertTrue( room.getLabelCell() == cell );
			
			// tests bottom corner of kitchen
			cell = board.getCell(15, 18);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Kitchen" ) ;
			assertFalse(cell.isRoomCenter());
			assertFalse(cell.isLabel());
			assertFalse(cell.isDoorway());
			
			//tests bottom door into kitchen
			cell = board.getCell(16, 21);
			room = board.getRoom( cell ) ;
			assertEquals( room.getName(), "Hallway" ) ;
			assertTrue(cell.isDoorway());
			assertTrue( room != null );
			assertFalse(cell.isRoomCenter());
			
			//tests bottom door out of kitchen
			cell = board.getCell(15, 21);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Kitchen" ) ;
			assertFalse(cell.isDoorway());
			assertFalse(cell.isRoomCenter());
			
			// tests secret passage from office to garden
			cell = board.getCell(24, 24);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Office" ) ;
			assertTrue( cell.getSecretPassage() == 'G' );
			
			// test a walkway
			cell = board.getCell(7, 7);
			room = board.getRoom( cell );
			assertTrue( room != null );
			assertEquals( room.getName(), "Hallway" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );
			
			//tests a starting position
			cell = board.getCell(17, 0);
			room = board.getRoom( cell );
			assertTrue( room != null );
			assertEquals( room.getName(), "Unused" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );
			
		}
}
