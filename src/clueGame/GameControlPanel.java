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
	
	private JLabel turnText;
	private JLabel rollText;
	
	private static String diceRoll;
	private static String nextPlayerName;

	public GameControlPanel() {	
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		 
		board.initialize();
		
		diceRoll = Integer.toString(board.rollDie());
		nextPlayerName = (board.getNextPlayer()).getName();
		CreateLayout();
	}
	
	private void CreateLayout() {
		
		// turn information section
		JLabel turnLabel = new JLabel("Whose turn?");
		turnText = new JLabel(nextPlayerName);
		JPanel turnPanel = new JPanel();
		turnPanel.add(turnLabel);
		turnPanel.add(turnText);
		
		// roll information section
		JLabel rollLabel = new JLabel("Roll: ");
		rollText = new JLabel(diceRoll);
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
			//Doesn't actualy do anything but the diceRoll is stored for now incase we need it.
			diceRoll = Integer.toString(board.rollDie());
			nextPlayerName = (board.getNextPlayer()).getName();
			rollText.setText(diceRoll);
			turnText.setText(nextPlayerName);
		}
		
	}
	
	public static void main(String[] args) {
		
		GameControlPanel controlPanel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(controlPanel);
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
}
