package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {

	private String name;
	private Color color;
	private int[] location;
	private Set<Card> hand;
	
	public Player(String name,String color,int x,int y) throws BadConfigFormatException {
		this.name = name;
		this.location = new int[] {x,y};
		hand = new HashSet<Card>();
		
		if(color.equals("White")) {
			this.color=Color.WHITE; 
		}else if(color.equals("Red")) {
			this.color=Color.RED; 
		}else if(color.equals("Yellow")) {
			this.color=Color.YELLOW; 
		}else if(color.equals("Blue")) {
			this.color=Color.BLUE; 
		}else if(color.equals("Green")) {
			this.color=Color.GREEN; 
		}else if(color.equals("Purple")) {
			this.color=Color.MAGENTA; 
		}else {
			throw new BadConfigFormatException();
		}
	}
	
	public Color getColor() {
		return this.color;
	}

	public void addCard(Card c) {
		hand.add(c);
	}
	
	public Set<Card> getHand(){
		return hand;
	}
	
	//This is a function that probably won't be used latter but it helps a lot for testing suggestions
	public void clearHand() {
		hand = new HashSet<Card>();
	}
	
	public String toString() {
		return name + ": " + color + ", @ " + "("+location[0]+","+location[1]+")";
	}
}
