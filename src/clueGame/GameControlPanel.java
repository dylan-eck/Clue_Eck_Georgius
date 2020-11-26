package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel{
	
	private static final String WHOSE_TURN_TEXT = "Whose turn?";
	private static final String ROLL_PANEL_TEXT = "Roll:";
	private static final String ACCUSE_BUTTON_TEXT = "Make Accusation";
	private static final String NEXT_TURN_BUTTON_TEXT = "NEXT!";
	
	private Board board;
	
	private JTextField rollText;
	private static JLabel turnText;
	private static JPanel turnPanel,backgroundColor;
	
	private String diceRoll;
	private Player nextPlayer;
	
	private JButton nextTurnButton;
	
	private JPanel guessPanel, resultPanel;

	public GameControlPanel(Board b) {	
		board = b;
		
		board.rollDie();
		diceRoll = Integer.toString(board.getDice());
		nextPlayer = board.getCurrentPlayer();

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
		nextTurnButton = new JButton(NEXT_TURN_BUTTON_TEXT);
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
		guessPanel = createPanel("guess");
		guessPanel.setLayout(new GridLayout(1,0));
		
		//Guess Result panel. Nothing much is happening here either.
		resultPanel = createPanel("result");
		resultPanel.setLayout(new GridLayout(1,0));

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
		return panel;
	}
	
	private class nextTurnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int diceRoll = board.getDice();
			String diceRollText = Integer.toString(diceRoll);
			rollText.setText(diceRollText);
			turnText.setText(nextPlayer.getName());
			
			if(board.playerHasGone()) {
	
				board.setNextPlayer();
				nextPlayer = board.getCurrentPlayer();
				backgroundColor.setBackground(nextPlayer.getColor());
				board.calcTargets(board.getCell(nextPlayer.getLocation()[1],nextPlayer.getLocation()[0]),diceRoll);
				
			} else if(nextPlayer.isHuman()) {
				new ErrorPanel("You have not finished your turn yet.");
			} else {
				ComputerPlayer computerPlayer = (ComputerPlayer) nextPlayer;
				
				if(computerPlayer.accusationReady()) {
					// TODO add accusation stuff
					
				} else {
					BoardCell target = computerPlayer.selectTargets(board, diceRoll);
					computerPlayer.move(target.getLocation());
					board.setPlayerHasGone();
					
//					if(target.getChar() != 'H') {
//						Solution computerSuggestion = computerPlayer.createSuggestion(board);
//						Card person = computerSuggestion.getPerson();
//						Card room = computerSuggestion.getRoom();
//						Card weapon = computerSuggestion.getWeapon();
//						Player guesser = GameControlPanel.this.board.getCurrentPlayer();
//						GameControlPanel.this.board.handleSuggestion(person, weapon, room, guesser);
//					}
				}
				nextTurnButton.doClick();
			}
			board.repaint();
		}
	}
	
	private class accusationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!board.getCurrentPlayer().isHuman()) {
				new ErrorPanel("It is not your turn");
			}else {
				new AccusationPanel(board);
			}
		}
		
	}
	
	public void updateSuggestionAndResult(Solution suggestion, Card disprovingCard) {
		guessPanel.removeAll();
		resultPanel.removeAll();
		
		String person = suggestion.getPerson().getName();
		String weapon = suggestion.getWeapon().getName();
		String room = suggestion.getRoom().getName();
		
		guessPanel.add(new JLabel(person));
		guessPanel.add(new JLabel(weapon));
		guessPanel.add(new JLabel(room));
		
		if(disprovingCard != null) {
			if(board.getCurrentPlayer().isHuman()) {
				resultPanel.add(new JLabel("Suggestion disproved with the folowing card: " + disprovingCard.getName()));
				
			} else {
				resultPanel.add(new JLabel("Suggestion disproved"));
			}
						
		} else {
			resultPanel.add(new JLabel("Suggestion could not be disproved."));
		}
		
		this.revalidate();
	}
	
	public void setTurnText(String text) {
		turnText.setText(text);
	}

	public void setRollText(String text) {
		rollText.setText(text);
	}
}
