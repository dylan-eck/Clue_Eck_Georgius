package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel{
	
	private static int CONTROL_PANEL_WIDTH = 750;
	private static int CONTROL_PANEL_HEIGHT = 180;
	
	private static final String ACCUSE_BUTTON_TEXT = "Make Accusation";
	private static final String NEXT_TURN_BUTTON_TEXT = "NEXT!";
	
	private static Board board;
	
	private static JLabel turnText;
	private static JLabel rollText;
	private static JPanel turnPanel;
	
	private static String diceRoll;
	private static Player nextPlayer;

	public GameControlPanel() {	
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		 
		board.initialize();
		
		diceRoll = Integer.toString(board.rollDie());
		nextPlayer = board.getNextPlayer();
		CreateLayout();
	}
	
	private void CreateLayout() {
		
		// turn information section
		turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		turnText = new JLabel(nextPlayer.getName());
		turnPanel.setBackground(nextPlayer.getColor());
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
		GridLayout upperLayout = new GridLayout(1,4);
		upperSubPanel.setLayout(upperLayout);
		upperSubPanel.add(turnPanel);
		upperSubPanel.add(rollPanel);
		upperSubPanel.add(accuseButton);
		upperSubPanel.add(nextTurnButton);
		this.add(upperSubPanel, BorderLayout.NORTH);
		
		//Guess panel. It's going to be blank for now
		JPanel guessPanel = new JPanel();
		guessPanel.setSize(CONTROL_PANEL_WIDTH/2,CONTROL_PANEL_HEIGHT/2);
		guessPanel.add(new JLabel("Guess                "));
		guessPanel.add(new JLabel("When we actualy do something something might go here"));
		
		//Guess Result panel. Nothing much is happening here either.
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setSize(CONTROL_PANEL_WIDTH/2,CONTROL_PANEL_HEIGHT/2);
		guessResultPanel.add(new JLabel("Guess Result           \n"));
		guessResultPanel.add(new JLabel("When we actualy do something something might go here"));
		
		// lower half of the control panel
		JPanel lowerSubPanel = new JPanel();
		lowerSubPanel.add(guessPanel);
		lowerSubPanel.add(guessResultPanel);
		add(lowerSubPanel,BorderLayout.CENTER);
	}
	
	private class nextTurnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Doesn't actualy do anything but the diceRoll is stored for now incase we need it.
			diceRoll = Integer.toString(board.rollDie());
			nextPlayer = board.getNextPlayer();
			rollText.setText(diceRoll);
			turnText.setText(nextPlayer.getName());
			turnPanel.setBackground(nextPlayer.getColor());
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
