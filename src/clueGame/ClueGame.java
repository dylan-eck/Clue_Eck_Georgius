package clueGame;

import java.awt.BorderLayout;

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
		
		
		//creates the introduction panel and finds out who is the human
		HumanPlayer player=null;
		for(Player p:gameBoard.getPlayers()) {
			if(p.isHuman()) {
				new GameStartPanel(p);
				player = (HumanPlayer) p;
			}
		}
		
		controlPanel = new GameControlPanel(gameBoard);
		infoPanel = new CardInfoPanel(player);
		
		gameFrame.add(gameBoard, BorderLayout.CENTER);
		gameFrame.add(infoPanel, BorderLayout.EAST);
		gameFrame.add(controlPanel, BorderLayout.SOUTH);
		gameFrame.setVisible(true);
		
		runGame();
	}
	
	public void runGame() {
		// initial calcTargets
		Player currentPlayer = gameBoard.getCurrentPlayer();
		gameBoard.calcTargets(gameBoard.getCell(currentPlayer.getLocation()[1],currentPlayer.getLocation()[0]),gameBoard.getDice());
		
		//gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) throws BadConfigFormatException{
		ClueGame clueGame = new ClueGame();
	}
}
