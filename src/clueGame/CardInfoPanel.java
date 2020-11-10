package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CardInfoPanel extends JPanel { 
	
	private JPanel peoplePanel, roomsPanel, weaponsPanel;
	
	public CardInfoPanel() {
		CreateLayout();
	}
	
	public void CreateLayout() {
		
		peoplePanel = new JPanel();
		Border peoplePanelBorder = BorderFactory.createTitledBorder("People");
		peoplePanel.setBorder(peoplePanelBorder);
		// number of rows will change throughout the game
		peoplePanel.setLayout(new GridLayout(0,1));
		
		roomsPanel = new JPanel();
		Border roomsPanelBorder = BorderFactory.createTitledBorder("Rooms");
		roomsPanel.setBorder(roomsPanelBorder);
		// number of rows will change throughout the game
		roomsPanel.setLayout(new GridLayout(0,1));
		
		weaponsPanel = new JPanel();
		Border weaponsPanelBorder = BorderFactory.createTitledBorder("Rooms");
		weaponsPanel.setBorder(weaponsPanelBorder);
		// number of rows will change throughout the game
		weaponsPanel.setLayout(new GridLayout(0,1));
		
		Border mainPanelBorder = BorderFactory.createTitledBorder("Known Cards");
		this.setBorder(mainPanelBorder);
		this.setLayout(new GridLayout(3,1));
		this.add(peoplePanel);
		this.add(roomsPanel);
		this.add(weaponsPanel);
	}
	
	public void addPerson(String person) {
		// add a person to the peoplePanel layout (peoplePanel.add(new JTextField("person's name"))
	}
	
	public void addRoom(String room) {
		// same idea as addPerson
	}
	
	public void addWeapon(String weapon) {
		// same idea as adPerson
	}
	
	public static void main(String[] args) {
		CardInfoPanel cardInfoPanel = new CardInfoPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(cardInfoPanel);
		
		frame.setSize(200, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
		
	}
}
