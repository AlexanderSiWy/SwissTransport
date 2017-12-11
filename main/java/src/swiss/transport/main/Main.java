package swiss.transport.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import swiss.transport.mail.Email;

public class Main extends Application {

	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			new Email("hi", "test e").open();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SwissTransport.fxml"));
			BorderPane pane = fxmlLoader.load();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.error("Es ist ein fehler aufgetreten. Überprüfen Sie ihre Internet verbindung.");
			LOGGER.debug("Stacktrace: ", e);
		}
	}
}
