package clueGame;

import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CardInfoPanel extends JPanel { 
	
	private HumanPlayer humanPlayer;
	private JPanel peoplePanel, roomsPanel, weaponsPanel;
	
	public CardInfoPanel(HumanPlayer humanPlayer) {
		this.humanPlayer = humanPlayer;
		CreateLayout();
	}
	
	public void CreateLayout() {
		
		peoplePanel = createPanel("People");
		roomsPanel = createPanel("Rooms");
		weaponsPanel = createPanel("Weapons");
		
		JTextField tempText = new JTextField("");
		tempText.setColumns(10);
		tempText.setEditable(false);
		peoplePanel.add(tempText);
		
		Border mainPanelBorder = BorderFactory.createTitledBorder("Known Cards");
		this.setBorder(mainPanelBorder);
		this.setLayout(new GridLayout(3,1));
		this.add(peoplePanel);
		this.add(roomsPanel);
		this.add(weaponsPanel);
	}

	private JPanel createPanel(String name) {
		JPanel panel = new JPanel();
		Border border = BorderFactory.createTitledBorder(name);
		panel.setBorder(border);
		panel.setLayout(new GridLayout(0,1));
		return panel;
	}
	
	public void updateInHandAndSeen() {
		//clears the panels before re making them
		peoplePanel.removeAll();
		roomsPanel.removeAll();
		weaponsPanel.removeAll();
		
		peoplePanel.add(new JLabel("In Hand:"));
		roomsPanel.add(new JLabel("In Hand:"));
		weaponsPanel.add(new JLabel("In Hand:"));
		
		addCards(humanPlayer.getHand());
		
		peoplePanel.add(new JLabel("seen:"));
		roomsPanel.add(new JLabel("seen:"));
		weaponsPanel.add(new JLabel("seen:"));
		
		addCards(humanPlayer.getSeen());
	}
	
	private void addCards(Set<Card> cards) {
		for(Card c : cards) {
			JTextField text = new JTextField(c.getName());
			text.setColumns(20);
			text.setEditable(false);
			switch(c.getType()) {
			case PERSON:
				peoplePanel.add(text);
				break;
			case ROOM:
				roomsPanel.add(text);
				break;
			case WEAPON:
				weaponsPanel.add(text);
				break;
			default:
				break;
				
			}
		}
	}
	
//	public static void main(String[] args) throws BadConfigFormatException {
//		
//		HumanPlayer thePlayer = new HumanPlayer("Mrs. White", "White", 12, 12);
//		
//		CardInfoPanel cardInfoPanel = new CardInfoPanel(thePlayer);
//		
//		thePlayer.addToHand(new Card("testPersonInHand", "Person"));
//		thePlayer.addToHand(new Card("testRoomInHand", "Room"));
//		thePlayer.addToHand(new Card("testWeaponInHand", "Weapon"));
//
//		thePlayer.addToSeen(new Card("testPersonSeen", "Person"));
//		thePlayer.addToSeen(new Card("testRoomSeen", "Room"));
//		thePlayer.addToSeen(new Card("testWeaponSeen", "Weapon"));
//		
//		cardInfoPanel.updateInHandAndSeen();
//		
//		//Uncomment for a more full panel
//		//Later this will be done by the next button from the GameControlPanel
//		//Since we use a HashSet to store seen and hand the cards don't show up in the order they were added but that doesn't realy matter.
//		/*thePlayer.addToSeen(new Card("testPersonSeen#2", "Person"));
//		thePlayer.addToSeen(new Card("testPersonSeen#3", "Person"));
//		thePlayer.addToSeen(new Card("testRoomSeen#2", "Room"));
//		thePlayer.addToSeen(new Card("testRoomSeen#3", "Room"));
//		thePlayer.addToSeen(new Card("testRoomSeen#4", "Room"));
//		thePlayer.addToSeen(new Card("testRoomSeen#5", "Room"));
//		thePlayer.addToSeen(new Card("testWeaponSeen#2", "Weapon"));
//		
//		cardInfoPanel.updateInHandAndSeen();
//		*/
//		
//		JFrame frame = new JFrame();
//		frame.setContentPane(cardInfoPanel);
//		frame.setSize(200, 750);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
//		frame.setVisible(true);
//	}
}
