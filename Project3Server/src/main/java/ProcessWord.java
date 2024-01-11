import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessWord implements Serializable{
	
    private static final long serialVersionUID = 2L;

	public String word;
	public String guess;
	
    ProcessWord(String word, String guess) {
        this.word = word;
        this.guess = guess;
    }
    
    public List<Integer> getLetterPositions() {
        List<Integer> positions = new ArrayList<>();

        // Ensure the word and guess are not null or empty
        if (word == null || guess == null || word.isEmpty() || guess.isEmpty()) {
            return positions;
        }

        // Check if the guessed letter is in the word and find positions
        for (int i = 0; i < word.length(); i++) {
            if (word.substring(i, i + 1).equals(guess)) {
                positions.add(i);
            }
        }

        return positions;
    }
    
	
    public boolean isLetterInWord() {

    	if (word == null || guess == null || word.isEmpty() || guess.isEmpty()) {
            return false;
        }

        // Check if the guessed letter is in the word
        return word.contains(guess);
    }
    
    public void setGuess (String guess) {
    	
    	this.guess = guess;
    }
    
    public void setWord(String word) {
    	this.word = word;
    }
    
    public String getGuess() {
    	return this.guess;
    }
    
    public String getWord() {
    	return this.word;
    }
    
    
    

}
