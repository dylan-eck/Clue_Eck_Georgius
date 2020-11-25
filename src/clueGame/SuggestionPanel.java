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
		mainPanel.add(createComboBox(board.getPlayerDeck()));
		
		mainPanel.add(new JLabel("Weapon"));
		mainPanel.add(createComboBox(board.getWeapons()));
		
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
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//handle suggestion
		}
	}
	
	private class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SuggestionPanel.this.frame.dispose();
		}	
	}
}
