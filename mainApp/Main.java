package mainApp;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
			public void handle(WindowEvent t) {
				Platform.setImplicitExit(true);; //makes sure the program terminates after closing the UI
				System.exit(0);
		    }
		});
		try {
			Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
