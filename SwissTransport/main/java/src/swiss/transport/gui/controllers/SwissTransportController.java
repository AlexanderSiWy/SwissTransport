package swiss.transport.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import swiss.transport.entity.Location;
import swiss.transport.rest.LocationRequest;

public class SwissTransportController {

	@FXML
	private GridPane selectionPane;
	@FXML
	private GridPane resultsPane;
	@FXML
	private ComboBox<Location> fieldFrom;
	@FXML
	private ComboBox<Location> fieldTo;

	@FXML
	private void initialize() {

	}

	@FXML
	private void switchValues(ActionEvent event) {
		Location valueFrom = fieldFrom.getValue();
		fieldFrom.setValue(fieldTo.getValue());
		fieldTo.setValue(valueFrom);
		System.out.println(Stop.class);
		LocationRequest locationRequest = new LocationRequest();
	}

	@FXML
	private void setAutoCompleteValues(KeyEvent event) {

	}
}
