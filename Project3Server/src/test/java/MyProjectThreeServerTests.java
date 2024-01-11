import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyProjectThreeServerTests {

	@Test
	void testWordChoiceLogic() {
		
	}
	
	@Test
	void testProcessWordClass() {
		
		ProcessWord pw = new ProcessWord("banana", "n");
		
		List letterList = new ArrayList<>();
		
		letterList.add(2);
		letterList.add(4);
		
		assertEquals(pw.getLetterPositions(), letterList);
		
		assertEquals(pw.isLetterInWord(), true);
		
		pw.setGuess("z");
		
		assertFalse(pw.isLetterInWord());
		
		
		
		
	}
	

}
