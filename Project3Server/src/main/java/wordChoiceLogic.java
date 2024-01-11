import java.util.ArrayList;
import java.util.Random;

public class wordChoiceLogic {
	

	private ArrayList<String> animalList = new ArrayList<String>();
	private ArrayList<String> countriesList = new ArrayList<String>();
	private ArrayList<String> fruitsList = new ArrayList<String>();
	
	public String chosenWord;
	
    Random random = new Random();

	wordChoiceLogic(){
		initializeLists();
	}
	
	public void initializeLists() {
		// ANIMALS
		animalList.add("cougar");
		animalList.add("parrot");
		animalList.add("lizard");
		animalList.add("panther");
		animalList.add("elephant");
		animalList.add("pigeon");
		animalList.add("jaguar");
		animalList.add("bluejay");
		animalList.add("gorilla");
		animalList.add("panda");
		animalList.add("giraffe");
		animalList.add("zebra");
		animalList.add("rabbit");
		animalList.add("coyote");
		animalList.add("toucan");
		animalList.add("owl");
		animalList.add("hippo");
		animalList.add("squirrel");
		animalList.add("flamingo");
		animalList.add("ferret");
		animalList.add("shark");
		animalList.add("butterfly");
		animalList.add("koala");
		animalList.add("penguin");
		animalList.add("bear");
		animalList.add("rhino");
		animalList.add("whale");
		animalList.add("beaver");
		animalList.add("octopus");
		animalList.add("kangaroo");
		animalList.add("aligator");
		animalList.add("eagle");
		
		// COUNTRIES
		
		countriesList.add("greece");
		countriesList.add("china");
		countriesList.add("chile");
		countriesList.add("italy");
		countriesList.add("india");
		countriesList.add("pakistan");
		countriesList.add("iran");
		countriesList.add("austrailia");
		countriesList.add("netherlands");
		countriesList.add("sweden");
		countriesList.add("germany");
		countriesList.add("russia");
		countriesList.add("belarus");
		countriesList.add("canada");
		countriesList.add("zimbabwe");
		countriesList.add("egypt");
		countriesList.add("france");
		countriesList.add("belgium");
		countriesList.add("madagascar");
		countriesList.add("argentina");
		countriesList.add("portugal");
		countriesList.add("latvia");
		countriesList.add("kazakhstan");
		countriesList.add("libya");
		countriesList.add("lithuania");
		countriesList.add("croatia");
		countriesList.add("jamaica");
		countriesList.add("finland");
		countriesList.add("japan");
		countriesList.add("korea");
		countriesList.add("vietnam");
		countriesList.add("denmark");
		countriesList.add("brazil");
		countriesList.add("armenia");
		countriesList.add("ireland");
		countriesList.add("mexico");

		
		// FRUITS
		
		fruitsList.add("strawberry");
		fruitsList.add("orange");
		fruitsList.add("pineapple");
		fruitsList.add("cherries");
		fruitsList.add("kiwi");
		fruitsList.add("blackberry");
		fruitsList.add("papaya");
		fruitsList.add("apricot");
		fruitsList.add("watermelon");
		fruitsList.add("apple");
		fruitsList.add("mango");
		fruitsList.add("banana");
		fruitsList.add("grape");
		fruitsList.add("guava");
		fruitsList.add("cataloupe");
		fruitsList.add("grapefruit");
		fruitsList.add("avacado");
		fruitsList.add("plum");
		fruitsList.add("blueberry");
		fruitsList.add("lemon");
		fruitsList.add("raspberry");
		fruitsList.add("pear");
		fruitsList.add("pomogranate");
		fruitsList.add("lime");
		fruitsList.add("dragonfruit");
		

	}
	


	
	public String chooseWord(String category) {
		
		
		
		switch(category) {
		
		case "Animals":
			
			
            int animalIndex = random.nextInt(animalList.size()); // get random index
			String animalWord = animalList.get(animalIndex); // word from random index
            animalList.remove(animalIndex); // remove the word at that index

            return animalWord;

		case "Countries":
			
            int countryIndex = random.nextInt(countriesList.size()); // get random index
			String countryWord = countriesList.get(countryIndex); // word from random index
			countriesList.remove(countryIndex); // remove the word at that index

            return countryWord;

		case "Fruits":
			
            int fruitsIndex = random.nextInt(fruitsList.size()); // get random index
			String fruitWord = fruitsList.get(fruitsIndex); // word from random index
			fruitsList.remove(fruitsIndex); // remove the word at that index

            return fruitWord;

		default:
			System.out.println("Category Error: Cannot choose word.");
			return "";
		
		}

	} // end of chooseWord()

	public String getChosenWord() {
		
		return chosenWord;
	}
	
	public void setChosenWord(String word) {
	
		chosenWord = word;
	
	}
}
