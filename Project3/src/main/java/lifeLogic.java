
public class lifeLogic {
	int guessesLeft;
	int livesLeft;
	
	
	
	
	
	public int getGuessesLeft() {
		
		return guessesLeft;
	}
	
	public int getLivesLeft() {
		return livesLeft;
	}
	
	public void decrementGuessesLeft() {
		guessesLeft--;
		
	}
	
	public void decrementLivesLeft() {
		livesLeft--;
	}
	
	public void startGuesses() {
		guessesLeft = 6;
	}
	
	public void startLives() {
		livesLeft = 3;
	}
}
