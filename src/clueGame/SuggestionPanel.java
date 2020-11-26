package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuggestionPanel {
	private static int CONTROL_PANEL_WIDTH = 300;
	private static int CONTROL_PANEL_HEIGHT = 200;
	
	JFrame frame;
	Card person,weapon,room;
	Board board;
	JPanel mainPanel;
	
	private JComboBox<String> people, weapons;
	
	public SuggestionPanel(Board b){
		board = b;
		CreateLayout();
		
		frame = new JFrame();
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setTitle("Make a Suggestion");
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
	
	private void CreateLayout() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,2));
		
		mainPanel.add(new JLabel("Current Room"));
		int[] currentLocation = board.getCurrentPlayer().getLocation();
		String currentRoom = board.getRoom(board.getCell(currentLocation[1], currentLocation[0])).getName();
		JTextField roomName = new JTextField(currentRoom);
		roomName.setEditable(false);
		mainPanel.add(roomName);
		
		mainPanel.add(new JLabel("Person"));
		people = createComboBox(board.getPlayerDeck());
		mainPanel.add(people);
		
		mainPanel.add(new JLabel("Weapon"));
		weapons = createComboBox(board.getWeapons());
		mainPanel.add(weapons);
		
		setUpConfirmationButtons();
	}
	
	private JComboBox<String> createComboBox(Set<Card> cards) {
		JComboBox<String> comboBox = new JComboBox<String>();
		for(Card c : cards) {
			comboBox.addItem(c.getName());
		}
		return comboBox;
	}
	
	private void setUpConfirmationButtons() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelListener());
		mainPanel.add(cancelButton);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new submitListener());
		mainPanel.add(submitButton);
	}
	
	private class submitListener implements ActionListener {
		
		Board board = SuggestionPanel.this.board;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Card person = board.getCard((String) people.getSelectedItem());
			Card weapon = board.getCard((String) weapons.getSelectedItem());
			int[] currentLocation = board.getCurrentPlayer().getLocation();
			char roomChar = board.getCell(currentLocation[1], currentLocation[0]).getChar(); 
			String roomName = board.getRoom(roomChar).getName();
			Card room = board.getCard(roomName);
			Player currentPlayer = board.getCurrentPlayer();
			
			board.handleSuggestion(person, weapon, room, currentPlayer);
			
			(board.getPlayer(person.getName())).move(currentPlayer.getLocation());
			board.repaint();
			
			SuggestionPanel.this.frame.dispose();
		}
	}
	
	private class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SuggestionPanel.this.frame.dispose();
		}	
	}
}
