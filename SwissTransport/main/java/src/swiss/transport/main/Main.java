package swiss.transport.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SwissTransport.fxml"));
		BorderPane pane = fxmlLoader.load();
	}
}
