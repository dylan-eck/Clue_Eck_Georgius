package clueGame;

public enum DoorDirection {
	DOWN("v"),UP("^"),LEFT("<"),RIGHT(">"),NONE(" ");
	
	private String direction;
	
	private DoorDirection(String dir) {
		this.direction = dir;
	}
	
	public String toString() {
		return this.direction;
	}
}
