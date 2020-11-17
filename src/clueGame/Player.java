package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {

	private String name;
	private Color color;
	private int[] location;
	private Set<Card> hand,seen;
	
	public Player(String name,String color,int x,int y) throws BadConfigFormatException {
		this.name = name;
		this.location = new int[] {x,y};
		hand = new HashSet<Card>();
		seen = new HashSet<Card>();
		
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
	
	public int[] getLocation() {
		return location;
	}
	
	public Color getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}
	
	public void addToHand(Card c) {
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
	
	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		Set<Card> potentialReturns = new HashSet<Card>();

		if(hand.contains(person)) {
			potentialReturns.add(person);
		}else if(hand.contains(weapon)) {
			potentialReturns.add(weapon);
		}else if(hand.contains(room)) {
			potentialReturns.add(room);
		}

		if(potentialReturns.size()>0) {
			Random random = new Random();
			int iter = random.nextInt(potentialReturns.size());
			int i = 0;
			for(Card c:potentialReturns) {
				if(i==iter) {
					return c;
				}
			i++;
			}
		}
		
		return null;
	}
	
	public void addToSeen(Card card) {
		seen.add(card);
	}
	
	public Set<Card> getSeen() {
		return seen;
	}
	
	public void draw(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(this.getColor());	
		g.fillOval(this.getLocation()[0]*cellWidth, this.getLocation()[1]*cellHeight, cellWidth, cellHeight);
	}
	
	public void move(int[] loc) {
		location = loc;
	}
	
	abstract public boolean isHuman();
	abstract public Solution createSuggestion(Board b);

}
