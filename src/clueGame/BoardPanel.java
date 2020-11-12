package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	private static int CONTROL_PANEL_WIDTH = 750;
	private static int CONTROL_PANEL_HEIGHT = 750;
	
	private JPanel visualBoard;
	private int numCols,numRows;
	private JPanel[][] boardCells; 
	private static Board board;
	
	public BoardPanel(Board b) {
		board = b;
		numCols = board.getNumColumns();
		numRows = board.getNumRows();
		
		boardCells = new JPanel[numRows][numCols];
		
		visualBoard = new JPanel();
		visualBoard.setLayout(new GridLayout(numCols,numRows));
		fillBoard();
		
		this.add(visualBoard,BorderLayout.CENTER);
	}
	
	public void fillBoard() {
		for(int row = 0;row<numRows;row++) {
			for(int col = 0;col<numCols;col++) {
				BoardCell tempCell = board.getCell(row, col);
				JPanel tempPanel = new JPanel();

				//Add more options and colors
				if(tempCell.getChar() == board.getHallways()) {
					tempPanel.setBackground(Color.YELLOW);
				}else {
					tempPanel.setBackground(Color.CYAN);
				}
				boardCells[row][col] = tempPanel;
				visualBoard.add(tempPanel);
			}
		}
	}
	
	public static void main(String[] args) {
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		 
		board.initialize();
		
		BoardPanel boardPanel = new BoardPanel(board);
		JFrame frame = new JFrame();
		frame.setContentPane(boardPanel);
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
}
