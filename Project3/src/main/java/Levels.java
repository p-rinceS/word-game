

// very simple levels class :)

public class Levels {

	
	// always start at level 1;
	private int level = 1;
	
	
	
	// get level to send to server to know where you are.
	public int getLevel() {
		return level;
	}
	
	// usually levels can only go up not down, unless next function
	public void incrementLevel () {
		
		level++;
	}
	
	// useful if the player wants to forcerestart
	public void resetLevel() {
		
		level = 1;
	}
	
	
	
	
}
