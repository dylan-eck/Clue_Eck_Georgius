package clueGame;

public class Card implements Comparable{

	private String name;
	private CardType type;
	
	public Card(String name,String type) {
		this.name = name;
		
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
	
	@Override
	public boolean equals(Object target) {
		if (target == null) return false;
	    if (target == this) return true;
		if (!(target instanceof Card)) return false;
		Card cardTarget = (Card) target;
		if(cardTarget.getName().equals(this.name)) return true;
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Card [" + name + "]";
	}

	@Override
	public int compareTo(Object target) {
		if(this.equals(target)) {
			return 0;
		}
		return 1;
	}
}
