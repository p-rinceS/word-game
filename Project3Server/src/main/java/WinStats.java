import java.io.Serializable;
import java.util.List;

public class WinStats implements Serializable{

	
	private static final long serialVersionUID = 4L;

	int guessesLeft;
	int livesLeft;
	int lettersGuessed;
	List<String> wordsGuessed;
	
	
	public void setGuessesLeft(int guesses) {
		guessesLeft = guesses;
	}
	
	public void setLivesLeft(int lives) {
		livesLeft = lives;
	}
	
	// probably wont be used...
	public void setLettersLeft(int guessCount) {
		lettersGuessed = guessCount;
	}
	
	public int getGuessesLeft() {
		return guessesLeft;
	}
	
	public int getLivesLeft() {
		return livesLeft;
	}
	
	
}
