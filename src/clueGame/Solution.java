package clueGame;

public class Solution {
	private Card weapon;
	private Card room;
	private Card person;
	
	public Solution() {
		
	}
	
	public Card getWeapon() {
		return weapon;
	}
	
	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}
	
	public void setWeapon(Card wep) {
		weapon = wep;
	}
	
	public void setRoom(Card room) {
		this.room = room;
	}
	
	public void setPerson(Card per) {
		person = per;
	}
}
