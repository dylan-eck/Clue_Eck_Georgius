package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

public class AccusationFrame {

	private static int CONTROL_PANEL_WIDTH = 750;
	private static int CONTROL_PANEL_HEIGHT = 400;
	
	JFrame frame;
	Card person,weapon,room;
	Board board;
	JPanel mainPanel;
	
	public AccusationFrame(Board b){
		board = b;
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,3));
		
		this.setUpPeople();
		this.setUpWeapons();
		this.setUpRooms();
		
		frame = new JFrame();
		frame.setContentPane(mainPanel);
		frame.setSize(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
	
	private void setUpPeople() {
		ButtonGroup peopleButtons = new ButtonGroup();
		
		JPanel peoplePanel = createPanel("People");
		for(Card p:board.getPlayerDeck()) {
			JToggleButton tempPersonButton = new JToggleButton(p.getName());
			tempPersonButton.addActionListener(new personButtons(p.getName()));
			peopleButtons.add(tempPersonButton);
			peoplePanel.add(tempPersonButton);
		}
		
		mainPanel.add(peoplePanel);
	}
	
	private void setUpWeapons() {
		ButtonGroup weaponButtons = new ButtonGroup();
		
		JPanel weaponPanel = createPanel("Weapons");
		for(Card p:board.getWeapons()) {
			JToggleButton tempWeaponButton = new JToggleButton(p.getName());
			tempWeaponButton.addActionListener(new weaponButtons(p.getName()));
			weaponButtons.add(tempWeaponButton);
			weaponPanel.add(tempWeaponButton);
		}
		
		mainPanel.add(weaponPanel);
	}
	
	private void setUpRooms() {
		ButtonGroup roomButtons = new ButtonGroup();
		
		JPanel roomPanel = createPanel("Rooms");
		for(Card p:board.getRoomDeck()) {
			JToggleButton tempRoomButton = new JToggleButton(p.getName());
			tempRoomButton.addActionListener(new roomButtons(p.getName()));
			roomButtons.add(tempRoomButton);
			roomPanel.add(tempRoomButton);
		}
		
		mainPanel.add(roomPanel);
	}
	
	//Same as from the card Type Panel
	private JPanel createPanel(String name) {
		JPanel panel = new JPanel();
		Border border = BorderFactory.createTitledBorder(name);
		panel.setBorder(border);
		panel.setLayout(new GridLayout(0,1));
		return panel;
	}
	
	private class personButtons implements ActionListener{

		String text;
		public personButtons(String buttonText) {
			super();
			text = buttonText;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			person = new Card(text,"Person");
		}
	}
	
	private class weaponButtons implements ActionListener{

		String text;
		public weaponButtons(String buttonText) {
			super();
			text = buttonText;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			person = new Card(text,"Weapon");
		}
	}
	
	private class roomButtons implements ActionListener{

		String text;
		public roomButtons(String buttonText) {
			super();
			text = buttonText;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			person = new Card(text,"Room");
		}
	}
	
	public static void main(String[] args) {
		//This will be removed when we integrate this.
		//probably by just calling get instance though if this is in the board class we won't have to waste the memory.	
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		 
		board.initialize();
		
		new AccusationFrame(board);
	}
}
