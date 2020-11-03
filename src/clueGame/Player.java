package clueGame;

import java.awt.Color;
public abstract class Player {

	private String name;
	private Color color;
	private int[] location;
	
	public Player(String name,String color,int x,int y) throws BadConfigFormatException {
		location = new int[2];
		this.name = name;
		location[0] = x;
		location[1] = y;
		
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
	
	public String toString() {
		return name + ": " + color + ", @ " + "("+location[0]+","+location[1]+")";
	}
}
