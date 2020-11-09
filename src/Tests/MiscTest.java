package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class MiscTest {

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
	void diceRoll() {
		int numberRolled[] = {0,0,0,0,0,0};
		int roll;
		for(int i = 0;i<300;i++) {
			roll = board.rollDie();
			
			switch(roll) {
				case 1:
					numberRolled[0]++;
					break;
				case 2:
					numberRolled[1]++;
					break;
				case 3:
					numberRolled[2]++;
					break;
				case 4:
					numberRolled[3]++;
					break;
				case 5:
					numberRolled[4]++;
					break;
				case 6:
					numberRolled[5]++;
					break;
				default:
					fail("Not a valid number");
					break;
			}
		}
		for(int j:numberRolled) {
			assertTrue(j>=10);
		}
	}

}
