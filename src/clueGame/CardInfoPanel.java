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
	
	private JPanel peoplePanel, roomsPanel, weaponsPanel;
	private Set<Card> inHand;
	private Set<Card> seen;
	
	public CardInfoPanel() {
		inHand = new HashSet<Card>();
		seen = new HashSet<Card>();
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
		Border weaponsPanelBorder = BorderFactory.createTitledBorder("Weapons");
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
	
	public void setInHand(Set<Card> inHand) {
		this.inHand = inHand;
	}
	
	public void setSeen(Set<Card> seen) {
		this.seen = seen;
	}
	
	public void updateInHandAndSeen() {
		
		peoplePanel.add(new JLabel("In Hand:"));
		roomsPanel.add(new JLabel("In Hand:"));
		weaponsPanel.add(new JLabel("In Hand:"));
		
		addCards(inHand);
		
		peoplePanel.add(new JLabel("seen:"));
		roomsPanel.add(new JLabel("seen:"));
		weaponsPanel.add(new JLabel("seen:"));
		
		addCards(seen);
	}
	
	private void addCards(Set<Card> cards) {
		
		for(Card c : cards) {
			JTextField text = new JTextField(c.getName());
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
	
	public static void main(String[] args) {
		CardInfoPanel cardInfoPanel = new CardInfoPanel();
		
		Set<Card> testInHand = new HashSet<Card>();
		testInHand.add(new Card("testPersonInHand", "Person"));
		testInHand.add(new Card("testRoomInHand", "Room"));
		testInHand.add(new Card("testWeaponInHand", "Weapon"));
		cardInfoPanel.setInHand(testInHand);
		
		Set<Card> testSeen = new HashSet<Card>();
		testSeen.add(new Card("testPersonSeen", "Person"));
		testSeen.add(new Card("testRoomSeen", "Room"));
		testSeen.add(new Card("testWeaponSeen", "Weapon"));
		cardInfoPanel.setSeen(testSeen);
		
		cardInfoPanel.updateInHandAndSeen();
		
		JFrame frame = new JFrame();
		frame.setContentPane(cardInfoPanel);
		frame.setSize(200, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
	}
}
