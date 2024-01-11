import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProjectThreeClientTests {

	

	@Test
	void testCategorySelection() {
		Categories category = new Categories();
		assertEquals(category.getAvailableCategories().size(), 3);
		category.setCategory("Animals");
		
		assertEquals(category.getCategory(), "Animals");
		
		category.removeCategory(category.getCategory());
		
		assertEquals(category.getAvailableCategories().size(), 2);
		
		
	}
	
	@Test
	void testLevels() {
		Levels levelSystem= new Levels();
		assertEquals(levelSystem.getLevel(), 1);
		levelSystem.incrementLevel();
		
		assertEquals(levelSystem.getLevel(), 2);
		levelSystem.incrementLevel();
		
		assertEquals(levelSystem.getLevel(), 3);
	
		
		
		levelSystem.resetLevel();
		assertEquals(levelSystem.getLevel(),1);
		
		
	}
	
	@Test
	void testLifeLogic() {
		lifeLogic lives = new lifeLogic();
		
		lives.startGuesses();
		lives.startLives();
		
		assertEquals(lives.getGuessesLeft(), 6);
		assertEquals(lives.getLivesLeft(), 3);
		
		lives.decrementGuessesLeft();
		lives.decrementGuessesLeft();
		
		assertEquals(lives.getGuessesLeft(), 5);
		assertEquals(lives.getLivesLeft(), 2);

		lives.startGuesses();
		lives.startLives();
		
		assertEquals(lives.getGuessesLeft(), 6);
		assertEquals(lives.getLivesLeft(), 3);
		
	}
}
