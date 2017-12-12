package swiss.transport.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
				LOGGER.error(e);
				LOGGER.debug("Stacktrace: ", e);
			});
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SwissTransport.fxml"));
			BorderPane pane = fxmlLoader.load();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("images/logo.png"));
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.error("Es ist ein fehler aufgetreten. Überprüfen Sie ihre Internet verbindung.");
			LOGGER.debug("Stacktrace: ", e);
		}
	}
}
