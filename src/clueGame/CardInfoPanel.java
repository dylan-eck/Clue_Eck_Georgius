package clueGame;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
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
		
		updateInHandAndSeen();
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
}
