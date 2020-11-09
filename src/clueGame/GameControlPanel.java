package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel{
	
	private static int CONTROL_PANEL_WIDTH = 750;
	private static int CONTROL_PANEL_HEIGHT = 180;
	
	private static final String ACCUSE_BUTTON_TEXT = "Make Accusation";
	private static final String NEXT_TURN_BUTTON_TEXT = "NEXT!";
	
	private static Board board;
	
	private static String diceRoll;
	private static String nextPlayerName;

	public GameControlPanel() {	
		diceRoll = Integer.toString(board.rollDie());
		nextPlayerName = (board.getNextPlayer()).getName();
		diceRoll = "0";
		nextPlayerName = "No one yet";
		CreateLayout();
	}
	
	private void CreateLayout() {
		
		// turn information section
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField turnText = new JTextField(nextPlayerName);
		JPanel turnPanel = new JPanel();
		turnPanel.add(turnLabel);
		turnPanel.add(turnText);
		
		// roll information section
		JLabel rollLabel = new JLabel("Roll: ");
		JTextField rollText = new JTextField(diceRoll);
		JPanel rollPanel = new JPanel();
		rollPanel.add(rollLabel);
		rollPanel.add(rollText);
		
		// buttons to make accusation and move to the next turn
		JButton accuseButton = new JButton(ACCUSE_BUTTON_TEXT);
		JButton nextTurnButton = new JButton(NEXT_TURN_BUTTON_TEXT);
		nextTurnButton.addActionListener(new nextTurnListener());
		
		// upper half of the control panel
		JPanel upperSubPanel = new JPanel();
		upperSubPanel.add(turnPanel);
		upperSubPanel.add(rollPanel);
		upperSubPanel.add(accuseButton);
		upperSubPanel.add(nextTurnButton);
		add(upperSubPanel, BorderLayout.CENTER);
		
	}
	
	private class nextTurnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			diceRoll = Integer.toString(board.rollDie());
			nextPlayerName = (board.getNextPlayer()).getName();
		}
		
	}
	
	public static void main(String[] args) {
		
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		 
		board.initialize();
		
		
		GameControlPanel controlPanel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(controlPanel);
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
}
