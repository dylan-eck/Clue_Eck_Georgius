package clueGame;

public class Solution {
	private Card weapon;
	private Card room;
	private Card person;
	
	public Solution() {
		
	}
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
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
	
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public void setRoom(Card room) {
		this.room = room;
	}
	
	public void setPerson(Card person) {
		this.person = person;
	}
}
