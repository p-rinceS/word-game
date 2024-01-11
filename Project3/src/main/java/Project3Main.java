import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Project3Main extends Application {
	
	String chosenCategory;
	
	Levels levelSystem = new Levels();
	Scene startScene;
	Scene categorySelection;
	Scene gameScene;
	Scene endScene;
	
	
	
	Categories clientCategory = new Categories();
	lifeLogic wordLifeLogic = new lifeLogic();
	Text wordText;
	
	Text currentCategory;
	Text lettersInWord;
	Text endText = new Text();
	List<Object> lettersGuessed = new ArrayList<>();
	int wordSize;
	String showString;
	StringBuilder stringBeginning;
	
	int countUnusedGuesses = 0;
	
	
	WinStats statistics = new WinStats();
	
	
	
	
	private Text hint = new Text();
    private TranslateTransition translateTransition;
    private FadeTransition fadeTransition;
    private String levelImgPath = "L1.png";
    private Text totalWord;
    private List<Integer> indicesFromServer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
    
	private void setLevelImage(int level) {
		
		if (level == 1){
			levelImgPath = "L1.png";
		}
		else if (level == 2){
			levelImgPath = "L2.png";
		}
		else {
			levelImgPath = "L3.png";
		}
		
	}
	
	// TODO:
		//FIX THIS FUNCTION
			// FINAL THOUGHTS: STRING PROGRESS NEEDS TO BE ACCESSIBLE,
			// YOU NEED TO ADD TO THE CORRECT INDEX AS YOU GET THE INDICES.
			// SO CREATE STRINGBUILDER GLOBAL
			// AS YOU GET YOUR LETTERS, CHANGE THE _ TO THE LETTERS.
	
	private StringBuilder makeNewString () {
		
	    StringBuilder stringProgress = new StringBuilder();
	    
		for (int i = 0; i < wordSize; i++) {
			stringProgress.append("_");
		}
		
		return stringProgress;
		
	}
	
	
			
	private StringBuilder addOntoString(List<Integer> indices, String guess, StringBuilder stringProgress) {
	
	    for (int index : indices) {
	        if (index >= 0 && index < wordSize) {
	            stringProgress.setCharAt(index, guess.charAt(0));
	        }
	    }
	    return stringProgress;
	    
	    }
	
    private static String addSpacesBetweenCharacters(StringBuilder stringBuilder) {
    	   StringBuilder result = new StringBuilder();

    	    for (int i = 0; i < stringBuilder.length(); i++) {
    	        result.append(stringBuilder.charAt(i)).append(' '); // Append each character and a space
    	    }
        
        return result.toString();
    }

	
	
    private ImageView createImage(String imageUrl) {
        Image categoryImage = new Image(getClass().getResource(imageUrl).toExternalForm());
        ImageView imageView = new ImageView(categoryImage);
        imageView.setFitWidth(325);
        imageView.setFitHeight(325);
        return imageView;
    }
    
    private Image createJustImage(String imageUrl) {
        Image categoryImage = new Image(getClass().getResource(imageUrl).toExternalForm());

        return categoryImage;
    }
    
    private void setupTransitions() {
        translateTransition = new TranslateTransition(Duration.seconds(4), hint);
        translateTransition.setFromY(100);
        translateTransition.setToY(-90);

        fadeTransition = new FadeTransition(Duration.seconds(3), hint);
        fadeTransition.setToValue(0);
        hint.setVisible(true);
        
        translateTransition.setOnFinished(event -> {
//          hint.setLayoutX(0);
//          hint.setLayoutY(0);
      	
//      	hint.setVisible(false);
          hint.setTranslateY(0); // Reset the translation
        });

    }
    
    private void startTransition() {
    	hint.setOpacity(1);
        translateTransition.stop(); // Stop previous transition, if running
        hint.setTranslateY(0);
        fadeTransition.stop();      // Stop previous transition, if running
        setupTransitions();         // Reset the transitions
        translateTransition.play();
        fadeTransition.play();
        
    }
    
    private void scaleBtn (Button btn) {
    	
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), btn);
        
        scaleTransition.setFromX(1.0); // Initial scale factor in the X direction
        scaleTransition.setFromY(1.0); // Initial scale factor in the Y direction
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        scaleTransition.play();
        
    }
    
    public static void popEffect(Text text) {
        // Create a ScaleTransition with the specified duration
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), text);

        // Set the initial and final scale factors
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        // Set the number of cycles for the bounce effect
        scaleTransition.setCycleCount(2);

        // Auto-reverse the animation
        scaleTransition.setAutoReverse(true);

        // Play the animation
        scaleTransition.play();
    }
    
    
    private void descaleBtn (Button btn) {
    	
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), btn);
        
        
        scaleTransition.setFromX(btn.getScaleX()); // Initial scale factor in the X direction
        scaleTransition.setFromY(btn.getScaleY()); // Initial scale factor in the Y direction
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        scaleTransition.play();
        
    }
    
    
    

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Prince's Word Game");
		
		

        
	    LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#253959")), new Stop(.7, Color.web("#6f86ab")));	    

     BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
     Background background = new Background(backgroundFill);
        
        
     // Create a Client instance
     
     // client gets created off rip, but only started when we hit play 
     						// only then it connects to server.
     Client client = new Client(data -> {
         // Handle received data (you can update UI components here)
         System.out.println("\033[0;31m" + "[SERVER]: " + "\033[0m" + data);
     });
	     
     
     
     
     
	       //					CREATE PLAY SCREEN
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     	BorderPane startScreenRoot = new BorderPane();

	     	startScene = new Scene(startScreenRoot, 1500, 800);
	     	
	     	
	     	hint.setVisible(true);

	     	startScreenRoot.setCenter(hint);
	     	
	     	Text beginText = new Text("Enter port ID and press play to begin!");
	     	
	     	
	        Timeline timeline = new Timeline();

	        timeline.getKeyFrames().addAll(
	                new KeyFrame(Duration.ZERO, new KeyValue(beginText.translateYProperty(), 0, Interpolator.EASE_BOTH)),
	                new KeyFrame(Duration.seconds(2), new KeyValue(beginText.translateYProperty(), 50, Interpolator.EASE_BOTH)),
	                new KeyFrame(Duration.seconds(4), new KeyValue(beginText.translateYProperty(), 0, Interpolator.EASE_BOTH))
	        );
	        
	        beginText.setOnMouseClicked(event -> {
	        	
	        	beginText.setScaleX(beginText.getScaleX() + 0.01);
	        	beginText.setScaleY(beginText.getScaleY() + 0.01);
	        });

	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.play();
	     	
	     	
	     	beginText.setStyle("-fx-font-size: 40px; -fx-fill: white; -fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     	HBox beginHolder =  new HBox();
	     	beginHolder.setAlignment(Pos.CENTER);
	     	beginHolder.getChildren().add(beginText);
	     	startScreenRoot.setCenter(beginHolder);
	     	ImageView title = createImage("WordGameTitle.png");
	     	
	     	title.setFitHeight(200);
	     	title.setFitWidth(800);
	     	
	     	HBox titleHolder = new HBox();
	     	
	     	titleHolder.getChildren().add(title);
	   	    startScreenRoot.setMaxWidth(Region.USE_PREF_SIZE); // Set maximum width to preferred size

	     	titleHolder.setAlignment(Pos.CENTER);
	     	
	     	startScreenRoot.setTop(titleHolder);
	     	
	     	Button playBtn = new Button("PLAY!");
	     	TextField enterPort = new TextField();
	     	
	     	enterPort.setPromptText("Enter Port: ");
	     	
	     	enterPort.setStyle(
		    		 "-fx-font-size: 20px;"
		     		+ "-fx-border-width: 0; "
		     		+ "-fx-background-color: transparent; "
		     		+ "-fx-alignment: center;"
		     		+ "-fx-text-fill: white;"
		     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     	
	     	startScreenRoot.setBackground(background);
	     	startScreenRoot.setPadding(new Insets(50));
	     	
	     	playBtn.setPrefHeight(50);
	     	
	     	playBtn.setPrefWidth(200);
	     	
	        playBtn.setStyle("-fx-font-size: 30px;");

	       
	        HBox portAndButtonHolder = new HBox();
	        
	        portAndButtonHolder.setAlignment(Pos.CENTER);
	     	VBox playBtnHolder = new VBox();
	     	
	     	
	     	
	     	playBtnHolder.setSpacing(30);
	     	
	     	playBtnHolder.getChildren().addAll(enterPort, playBtn);
	     	
	     	playBtnHolder.setAlignment(Pos.CENTER);
	     	
	     	portAndButtonHolder.getChildren().add(playBtnHolder);
	     	
	     	

	        enterPort.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ENTER) {
	                playBtn.fire();
	            }
	        });
	     	
	     	startScreenRoot.setBottom(portAndButtonHolder);
	     	
	     	
	     	

	     	
	     	
	     
	     	
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	     
	     
	    //			CATEGORY SELECTION SCENE CREATION
	   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     
	     BorderPane categoryRoot = new BorderPane();
	     

	     
	     categoryRoot.setBackground(background);
	     
	     
	     Text categoryText = new Text("Select a category for level " + levelSystem.getLevel() + "!");
	     	
	     HBox categoryTextHolder = new HBox();
	     
	     categoryTextHolder.setAlignment(Pos.CENTER);
	     

	     StackPane topHolder = new StackPane();
	     
	     categoryText.setStyle("-fx-font-size: 40px; -fx-fill: white; -fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");

	     categoryTextHolder.getChildren().add(categoryText);
//	     categoryRoot.setCenter(categoryTextHolder);
	        Timeline timeline2 = new Timeline();

	        timeline2.getKeyFrames().addAll(
	                new KeyFrame(Duration.ZERO, new KeyValue(categoryText.translateYProperty(), 0, Interpolator.EASE_BOTH)),
	                new KeyFrame(Duration.seconds(2), new KeyValue(categoryText.translateYProperty(), 50, Interpolator.EASE_BOTH)),
	                new KeyFrame(Duration.seconds(4), new KeyValue(categoryText.translateYProperty(), 0, Interpolator.EASE_BOTH))
	        );
	        
	        timeline2.setCycleCount(Timeline.INDEFINITE);
	        timeline2.play();
	     	
	     
	     categorySelection = new Scene(categoryRoot, 1500, 800);
	     
	     HBox Categories = new HBox();
	     VBox categoryButtons = new VBox();

	     // CATEGORY IMAGES ---

	       topHolder.getChildren().addAll(Categories, categoryTextHolder);
	        ImageView instructions = createImage("ChooseYourCategory.png");
	        instructions.setFitWidth(500);
	        instructions.setFitHeight(500);
	        Categories.getChildren().add(instructions);
	        //	        categoriesHBox.setMargin(category3Box, new Insets(20,20,20,20));
	        categoryButtons.setSpacing(10);
	        
	        categoryButtons.setAlignment(Pos.TOP_CENTER);
	        
	        Button animals = new Button("Animals");
	        Button fruits = new Button("Fruits");	
	        Button countries = new Button("Countries");
	        
	        
	        animals.setPrefHeight(50);
	        animals.setPrefWidth(200);
	        
	        animals.setStyle("-fx-font-size: 20px;");
	        fruits.setStyle("-fx-font-size: 20px;");
	        countries.setStyle("-fx-font-size: 20px;");
	        
	        fruits.setPrefHeight(50);
	        fruits.setPrefWidth(200);
	     
	        
	        countries.setPrefHeight(50);
	        countries.setPrefWidth(200);
	        
	        
	        // --- CONTINUE BUTTON ---
	        Button continueBtn = new Button("Continue");
	        
	        continueBtn.setVisible(false);
	        continueBtn.setStyle("-fx-font-size: 20px;");
	        
	        continueBtn.setPrefHeight(50);
	        continueBtn.setPrefWidth(200);
	        
	        VBox ctnbtnHolder = new VBox();
	        
	        ctnbtnHolder.getChildren().add(continueBtn);
	        
	        ctnbtnHolder.setAlignment(Pos.CENTER);
	        
	        
	        categoryButtons.getChildren().addAll(animals,countries,fruits);
	        
	        
	        categoryRoot.setBottom(ctnbtnHolder);
	        categoryRoot.setCenter(categoryButtons);
	        
	        
	        
	        
	        continueBtn.setOnAction(e -> {
	        	
	            System.out.println("Client is now moving to level: " + levelSystem.getLevel());
	            continueBtn.setVisible(false);
	            
	        	Categories categoryChoice = new Categories();
	        	categoryChoice.setCategory(chosenCategory);
	        	clientCategory.setCategory(chosenCategory);
	            client.sendCategory(categoryChoice);
	            wordLifeLogic.startGuesses();
				
				Platform.runLater(() -> {
					
					primaryStage.setScene(gameScene);
					currentCategory.setText(clientCategory.getCategory());
					lettersInWord.setText("Word Length: " + Integer.toString(client.getWordSize()));
					wordSize = client.getWordSize();
					stringBeginning = makeNewString();
					
					
				 
					setLevelImage(levelSystem.getLevel());
					
					
				if (categoryChoice.getCategory() == "Animals"){
						animals.setVisible(false);
						
				}
				else if(categoryChoice.getCategory() == "Countries") {
					countries.setVisible(false);
				}
				else {
					fruits.setVisible(false);
				}
				});
				
		    	try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

//	            categoryChoice.setCategory("None");
	        });
	        
	        animals.setOnAction(e -> {
//	            System.out.println("Animals button pressed");
	            continueBtn.setVisible(true);
	            chosenCategory = "Animals";
	            scaleBtn(animals);
	            descaleBtn(fruits);
	            animals.setOpacity(1);
	            fruits.setOpacity(.5);
	            countries.setOpacity(.5);
	            descaleBtn(countries);

	            
	            
	            
	        });

	        fruits.setOnAction(e -> {
	        	chosenCategory = "Fruits";
//	        	System.out.println("Fruits button pressed");
	            continueBtn.setVisible(true);

	            animals.setOpacity(.5);
	            fruits.setOpacity(1);
	            countries.setOpacity(.5);
	            scaleBtn(fruits);
	            descaleBtn(animals);
	            descaleBtn(countries);
	            
	            
	        });
	        
	        countries.setOnAction(e -> {
	        	chosenCategory = "Countries";
//	        	System.out.println("Countries button pressed");
	            continueBtn.setVisible(true);
	            scaleBtn(countries);
	            descaleBtn(fruits);
	            descaleBtn(animals);
	            
	            animals.setOpacity(.5);
	            fruits.setOpacity(.5);
	            countries.setOpacity(1);
	            
	            
	        });
            
            Categories.setAlignment(Pos.TOP_CENTER);
	        

	     
	     categoryRoot.setTop(topHolder);
	     
	     
	     
	     
	   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     
	     
	     // 					CREATE GAME SCENE
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     BorderPane gameRoot = new BorderPane();
	     
	     gameRoot.setBackground(background);
	     
	     
	     gameScene = new Scene(gameRoot, 1500, 800);
	     
	     TextField guessLocation = new TextField();
	     
	     
	        TextFormatter<String> textFormatter = new TextFormatter<>(change ->
            (change.getControlNewText().matches("[a-zA-Z]*") && change.getControlNewText().length() <= 1) ? change : null);
   	     
	        guessLocation.textProperty().addListener((observable, oldValue, newValue) -> {
	            guessLocation.setText(newValue.toUpperCase());
	        });
	        
	        
	        wordText = new Text("Guess the Word!");
	        wordText.setTranslateX(5);

		     wordText.setStyle(";-fx-font-size: 50px; "
    		+ "-fx-fill: white; "
    		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");

	     gameRoot.setCenter(wordText);
	     
	     guessLocation.setTextFormatter(textFormatter);
	     guessLocation.setStyle(
	    		 "-fx-font-size: 20px;"
	     		+ "-fx-border-width: 0; "
	     		+ "-fx-background-color: transparent; "
	     		+ "-fx-alignment: center;"
	     		+ "-fx-text-fill: white;"
	     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     guessLocation.setPrefWidth(50);
	     guessLocation.setPromptText("Enter Guess:");
	     
	     guessLocation.setPrefWidth(200);


	     
	     ImageView levelOneText = createImage(levelImgPath);
	     levelOneText.setFitHeight(50);	
	     levelOneText.setFitWidth(125);
	     BorderPane topPanel = new BorderPane();
	     
	     topPanel.setRight(levelOneText);
	     
	     Text guessesLeft = new Text("Guesses Left: 6"); 
	     currentCategory = new Text();
	     lettersInWord = new Text();
	    
	     currentCategory.setStyle(";-fx-font-size: 20px; "
		     		+ "-fx-fill: white; "
		     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     
	     lettersInWord.setStyle(";-fx-font-size: 20px; "
		     		+ "-fx-fill: white; "
		     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     
	     guessesLeft.setStyle("-fx-font-size: 20px; "
	     		+ "-fx-fill: white; "
	     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     VBox guessesLeftHolder = new VBox();
	     
	     
	     guessesLeftHolder.getChildren().addAll(currentCategory,lettersInWord, guessesLeft);
	     guessesLeftHolder.setAlignment(Pos.CENTER_LEFT);
	     topPanel.setLeft(guessesLeftHolder);
	     
	     gameRoot.setTop(topPanel);
	     VBox guessLocationHolder = new VBox();
	     Button confirmGuessBtn = new Button("Confirm Guess");
	     Button tryAgainBtn = new Button("Try again!");
	     
	     tryAgainBtn.setVisible(false);
	     
	     guessLocationHolder.setSpacing(10);
	     
	     
	     gameRoot.setPadding(new Insets(30));
	     
//	     guessLocation.setPrefWidth(50);
	     guessLocationHolder.setMaxWidth(Region.USE_PREF_SIZE); // Set maximum width to preferred size
	     guessLocationHolder.getChildren().addAll(guessLocation, confirmGuessBtn, tryAgainBtn);
	     HBox guessLocationHolderHolder = new HBox();
	     
	     guessLocationHolderHolder.setAlignment(Pos.CENTER);
	     guessLocationHolderHolder.getChildren().add(guessLocationHolder);
	     guessLocationHolder.setAlignment(Pos.CENTER);
	     
	     HBox hintHolder = new HBox();
	     hintHolder.setAlignment(Pos.BOTTOM_CENTER);
	     hint.setStyle("-fx-font-size: 15px; -fx-fill: white;  -fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     
//	     hint.setVisible(false);
//	     hint.setText("Invalid guess option.");
	     hintHolder.getChildren().add(hint);
	     topPanel.setBottom(hintHolder);
	     gameRoot.setBottom(guessLocationHolderHolder);
	     

	        guessLocation.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ENTER) {
	                confirmGuessBtn.fire();
	            }
	        });

	     
	     confirmGuessBtn.setOnAction(event3 -> {
	    	 
		     if (guessLocation.getText().equals("")) {
		    	 
		    	 hint.setText("Invalid guess: Enter a character!");
					setupTransitions();
			    	startTransition(); 
		    	 return;
		     }
		     else {
		    	 
		    	 
		    	 
		     }
		     
		     lettersGuessed.add(guessLocation.getText());
		     

		     
		     
		     
		     

		     
	    	client.sendLetterGuess(guessLocation.getText());
	    	
	    	
	    	
	    	
	    	try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	    	
	    	

	    	indicesFromServer = client.letterPos;
	    	
//		     String progress = getCurrentProgress(indicesFromServer, guessLocation.getText());
		     
		     stringBeginning = addOntoString(indicesFromServer, guessLocation.getText(), stringBeginning);
//		     System.out.println(stringBeginning.toString());
		     
		     showString = addSpacesBetweenCharacters(stringBeginning);
		     
		     wordText.setText(showString.toString());
//	 
//	    	System.out.println(client.letterPos.size());
	    	 if (client.letterPos.size() > 0) {
	    		 hint.setText("\"" + guessLocation.getText() + "\" is in the word!");
	    	 }
	    	 else {
	    		 hint.setText("\"" + guessLocation.getText() + "\" is not in the word!");
	 	    	 wordLifeLogic.decrementGuessesLeft();
	 	    	guessesLeft.setText("Guesses Left: " + wordLifeLogic.getGuessesLeft());


  	 }
	    if (stringBeginning.toString().contains("_") && wordLifeLogic.getGuessesLeft() == 0) {
	    
	    if (wordLifeLogic.getLivesLeft() != 1) {
		 hint.setText("You're out of guesses! Try another word?");
		 guessLocation.setVisible(false);
		 confirmGuessBtn.setVisible(false);
		 tryAgainBtn.setVisible(true);
	    }
	    else {
	    	primaryStage.setScene(endScene);
    		endText.setText("So sad... You Lost.");

	    }
	    }
	    
	    if (!stringBeginning.toString().contains("_")) {
	    	if(levelSystem.getLevel() != 3) {

		 hint.setText("You guessed the word correctly! Moving to next level!");
		 wordText.setText("Guess the Word!");
		 levelSystem.incrementLevel();
		 countUnusedGuesses += wordLifeLogic.getGuessesLeft();
		 statistics.setGuessesLeft(countUnusedGuesses);
		 setLevelImage(levelSystem.getLevel());
		 primaryStage.setScene(categorySelection);
		 fruits.setOpacity(1);
		 countries.setOpacity(1);
		 animals.setOpacity(1);
		 
		 categoryText.setText("Select a category for level " + levelSystem.getLevel() + "!");
		 levelOneText.setImage(createJustImage(levelImgPath));
	    	}
	    	else {
	    		
	    		
	    		client.sendWinStats(statistics);
	    		
	    	   	try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	    	   	
	    	   	primaryStage.setScene(endScene);
	    		endText.setText("Congratulations you win!");
	    		System.out.println("YOU WIN!!!");
	    		
	    	}
		 
		 
	    }
	    		popEffect(wordText);
	    		guessLocation.clear();
	    	 	setupTransitions();
		    	startTransition(); 
	     });
	     
	     tryAgainBtn.setOnAction(e -> {
	    	 	wordLifeLogic.decrementLivesLeft();
	    	 	wordLifeLogic.startGuesses();
	    	 	
	    	 	hint.setText("You have " + wordLifeLogic.getLivesLeft() + " tries left!\n Make it count.");
	    	 	Categories tryAgainCategory = new Categories();
	    	 	
	    	 	tryAgainCategory.setCategory(chosenCategory);
	    	 	client.sendCategory(tryAgainCategory);
	    	 	
		    	try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

	    	 	
	    	 	wordSize = client.getWordSize();
	    	 	
				stringBeginning = makeNewString();
	    	 	
	    	 	lettersInWord.setText("Word Length: " + Integer.toString(client.getWordSize()));
	 	    	guessesLeft.setText("Guesses Left: " + wordLifeLogic.getGuessesLeft());
	    	 	wordText.setText("Guess the Word!");
	    	 	
	    	 	
	    		 guessLocation.setVisible(true);
	    		 confirmGuessBtn.setVisible(true);
	    		 tryAgainBtn.setVisible(false);
	    	 	
	    	 	
	    	 	setupTransitions();
		    	startTransition(); 
	    	 
	     });
	     

	     	
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     	
	     
	     	 // 				CREATE END SCREEN
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     
	     BorderPane endRoot = new BorderPane();
	     
	     endRoot.setBackground(background);
	     
	     HBox endTextHolder = new HBox();
	     endTextHolder.setAlignment(Pos.CENTER);
	     
	     endTextHolder.getChildren().add(endText);
	     endText.setStyle(";-fx-font-size: 60px; "
		     		+ "-fx-fill: white; "
		     		+ "-fx-effect: dropshadow( gaussian , black , 5, 0.2 , 0 , 0 );");
	     
	     popEffect(endText);
	     
	     endRoot.setCenter(endTextHolder);
	     
	     endScene = new Scene(endRoot, 1500, 800);
	     
	     
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		   //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	     
	     

//	     try {
	     	playBtn.setOnAction( event -> {

	     	
	            client.setPortID(Integer.valueOf(enterPort.getText()));
	            

	            client.start();
	           
	            wordLifeLogic.startLives(); // start with 3 lives. never resets.
	            
	    
	            // make it so it will only move on if the client and server connection is successful.

	            	if (enterPort.getText() != "") {
	            		primaryStage.setScene(categorySelection);
	            	}else {
	            		System.out.println("Please enter a port to connect to server.");
	            	}
	            
	     	});
	     	
//	     }
//	     catch(ConnectException e) {
//	    	 System.out.println("PORT NOT FOUND!?!?!");
//	     }
	     
//	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(startScene);
			primaryStage.show();
		
				
		
	}

}
