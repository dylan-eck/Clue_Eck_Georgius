package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class ClueGame {
	
	private JFrame gameFrame;
	private Board gameBoard;
	private GameControlPanel controlPanel;
	private CardInfoPanel infoPanel;
	// \/ TODO fix this \/
	public ClueGame() throws BadConfigFormatException{

		gameFrame = new JFrame();
		gameFrame.setTitle("Clue");
		gameFrame.setSize(800, 600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		gameBoard.initialize();
		
		controlPanel = new GameControlPanel(gameBoard);
		
		HumanPlayer player=null;
		
		for(Player p:gameBoard.getPlayers()) {
			if(p.isHuman()) {
				player = (HumanPlayer) p;
			}
		}
		infoPanel = new CardInfoPanel(player);
		
		gameFrame.add(gameBoard, BorderLayout.CENTER);
		gameFrame.add(infoPanel, BorderLayout.EAST);
		gameFrame.add(controlPanel, BorderLayout.SOUTH);
		gameFrame.setVisible(true);
	}
	
	public static void main(String[] args) throws BadConfigFormatException{
		ClueGame clueGame = new ClueGame();
	}
}
