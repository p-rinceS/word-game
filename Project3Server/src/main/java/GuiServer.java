
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	
	TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
//	Client clientConnection;
	
	ListView<String> listItems, listItems2;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
//		primaryStage.setTitle("The Networked Client/Server GUI Example");
		
		


		listItems = new ListView<String>();
		
		
		primaryStage.setTitle("This is the Server");

		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(createServerGui());
		primaryStage.show();
		
	}
	
    private void startServer(int port) {
        serverConnection = new Server(port, data -> {
            // Handle server data as needed
            Platform.runLater(() -> {
                listItems.getItems().add(data.toString());
                // Update UI or log server data
                System.out.println("Server received: " + data);
            });
        });

        // You may want to hide or close the configuration window when starting the server
        // primaryStage.hide();

        // Optionally, show the server GUI or any other UI components related to the server
        // primaryStage.setScene(createServerGui());
    }
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		
		Text portNum = new Text("PORT #:");
		
		portNum.setStyle("-fx-font-size: 30px;  -fx-fill: white;");
		pane.setPadding(new Insets(70));
			pane.setStyle("-fx-background-color: gray");
		
		pane.setCenter(listItems);
		
		TextField enterPort = new TextField();
		
		HBox enterPortHolder = new HBox();
		
		HBox portNumHolder = new HBox();
		portNumHolder.setAlignment(Pos.CENTER);
		
		portNumHolder.getChildren().add(portNum);
		
		
		Button confirmPort = new Button("Confirm Port");
		
		
		enterPort.setPromptText("Enter Port: ");
		enterPortHolder.getChildren().addAll(enterPort, confirmPort);

		confirmPort.setOnAction(event -> {
			portNum.setText(enterPort.getText());
			int portID = Integer.parseInt(enterPort.getText());
			startServer(portID);
			enterPort.setVisible(false);
			confirmPort.setVisible(false);

		});
		
		enterPortHolder.setAlignment(Pos.CENTER);
		pane.setBottom(enterPortHolder);
		pane.setTop(portNumHolder);
	
		return new Scene(pane, 500, 400);
		
		
	}
	
	
	

}
