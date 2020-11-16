package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GameControlPanel extends JPanel{
	
	private static int CONTROL_PANEL_WIDTH = 750;
	private static int CONTROL_PANEL_HEIGHT = 180;
	
	private static final String WHOSE_TURN_TEXT = "Whose turn?";
	private static final String ROLL_PANEL_TEXT = "Roll:";
	private static final String ACCUSE_BUTTON_TEXT = "Make Accusation";
	private static final String NEXT_TURN_BUTTON_TEXT = "NEXT!";
	
	private static Board board;
	
	private JTextField rollText, guessText, resultText;
	private static JLabel turnText;
	private static JPanel turnPanel,backgroundColor;
	
	private String diceRoll;
	private Player nextPlayer;
	private boolean playerHasGone;

	public GameControlPanel(Board b) {	
		board = b;
		playerHasGone = false;
		
		diceRoll = Integer.toString(board.rollDie());
		nextPlayer = board.getNextPlayer();
		
		guessText = new JTextField();
		resultText = new JTextField();
		setGuessText("not implemented yet");
		setResultText("not implemented yet");
		CreateLayout();
	}
	
	private void CreateLayout() {
		this.setLayout(new GridLayout(2,1));
		
		// turn information section
		turnPanel = new JPanel();
		JLabel turnLabel = new JLabel(WHOSE_TURN_TEXT);
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		turnText = new JLabel(nextPlayer.getName());
		backgroundColor = new JPanel();
		backgroundColor.setBackground(nextPlayer.getColor());
		backgroundColor.add(turnText);
		turnPanel.setLayout(new GridLayout(2,1));
		turnPanel.add(turnLabel);
		turnPanel.add(backgroundColor);
		
		// roll information section
		JLabel rollLabel = new JLabel(ROLL_PANEL_TEXT);
		rollText = new JTextField(diceRoll, 6);
		JPanel rollPanel = new JPanel();
		rollPanel.add(rollLabel);
		rollPanel.add(rollText);
		
		// buttons to make accusation and move to the next turn
		JButton accuseButton = new JButton(ACCUSE_BUTTON_TEXT);
		JButton nextTurnButton = new JButton(NEXT_TURN_BUTTON_TEXT);
		nextTurnButton.addActionListener(new nextTurnListener());
		accuseButton.addActionListener(new accusationListener());
		
		// upper half of the control panel
		JPanel upperSubPanel = new JPanel();
		upperSubPanel.setLayout(new GridLayout(1,4));
		upperSubPanel.add(turnPanel);
		upperSubPanel.add(rollPanel);
		upperSubPanel.add(accuseButton);
		upperSubPanel.add(nextTurnButton);
		this.add(upperSubPanel, BorderLayout.NORTH);
		
		//Guess panel. It's going to be blank for now
		JPanel guessPanel = createPanel("guess");
		
		//Guess Result panel. Nothing much is happening here either.
		JPanel resultPanel = createPanel("result");

		// lower half of the control panel
		JPanel lowerSubPanel = new JPanel();
		GridLayout lowerLayout = new GridLayout(1,2);
		lowerSubPanel.setLayout(lowerLayout);
		lowerSubPanel.add(guessPanel);
		lowerSubPanel.add(resultPanel);
		add(lowerSubPanel,BorderLayout.CENTER);
	}
	
	//TODO consider renaming this function
	private JPanel createPanel(String name) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(name));
		panel.setLayout(new GridLayout(1,0));
		panel.add(resultText);
		return panel;
	}
	
	private class nextTurnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Doesn't actualy do anything but the diceRoll is stored for now incase we need it.
			if(playerHasGone) {
				diceRoll = Integer.toString(board.rollDie());
				nextPlayer = board.getNextPlayer();
				rollText.setText(diceRoll);
				turnText.setText(nextPlayer.getName());
				backgroundColor.setBackground(nextPlayer.getColor());
			}else {
				new PlayerHasNotGoneErrorPanel();
			}
		}
		
	}
	
	private class accusationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(playerHasGone) {
				new PlayerHasGoneErrorPanel();
			}else {
				new AccusationFrame(board);
			}
		}
		
	}
	
	public void setTurnText(String text) {
		turnText.setText(text);
	}

	public void setRollText(String text) {
		rollText.setText(text);
	}

	public void setGuessText(String text) {
		guessText.setText(text);
	}

	public void setResultText(String text) {
		resultText.setText(text);
	}
	
	public void updatePlayerTurn() {
		playerHasGone = !playerHasGone;
	}
	
	/*public static void main(String[] args) {
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		 
		board.initialize();
		
		GameControlPanel controlPanel = new GameControlPanel(board);
		JFrame frame = new JFrame();
		frame.setContentPane(controlPanel);
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
		
//		controlPanel.setTurnText("setter test");
//		controlPanel.setRollText("setter test");
		controlPanel.setGuessText("setter test");
		controlPanel.setResultText("setter test");
	}*/
}
