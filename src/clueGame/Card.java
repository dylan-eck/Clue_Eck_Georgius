package clueGame;

public class Card {

	private String name;
	private CardType type;
	
	public Card(String Name,String type) {
		this.name = Name;
		
		if(type.equals("Weapon")) {
			this.type = CardType.WEAPON;
		}else if(type.equals("Room")) {
			this.type = CardType.ROOM;
		}else if(type.equals("Person")) {
			this.type = CardType.PERSON;
		}else {
			System.out.println("I don't know how you got here. Undo whatever you just did or your computer is liable to self destruct in 5 seconds.");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}
}
