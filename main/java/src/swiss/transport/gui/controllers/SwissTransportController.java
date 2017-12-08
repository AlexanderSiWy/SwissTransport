package swiss.transport.gui.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.Location.LocationList;
import swiss.transport.gui.elements.TimePicker;
import swiss.transport.rest.transport.LocationRequest;

public class SwissTransportController {

	private static final Logger LOGGER = LogManager.getLogger(SwissTransportController.class);

	@FXML
	private GridPane selectionPane;
	@FXML
	private GridPane resultsPane;
	@FXML
	private CustomTextField fieldFrom;
	@FXML
	private CustomTextField fieldTo;
	@FXML
	private Button btnSwitch;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TimePicker timePicker;
	private GlyphFont font = GlyphFontRegistry.font("FontAwesome");;
	private ValidationSupport validation = new ValidationSupport();

	private ObjectProperty<Location> from = new SimpleObjectProperty<>();
	private ObjectProperty<Location> to = new SimpleObjectProperty<>();

	@FXML
	private void initialize() {
		btnSwitch.setGraphic(font.create(Glyph.RANDOM).size(15));
		setTextFieldPropertys(fieldFrom, from);
		setClosestLocation(from);
		setTextFieldPropertys(fieldTo, to);
		setCurrentDate();
		setCurrentTime();
	}

	private void setClosestLocation(ObjectProperty<Location> property) {
		try {
			Location closestLocation = LocationList.getClosestLocation();
			if (closestLocation != null) {
				property.setValue(closestLocation);
			}
		} catch (IOException e) {
			LOGGER.warn("Standort wurde nicht gefunden.");
			LOGGER.debug("Stacktrace: ", e);
		}
	}

	private void setCurrentTime() {
		timePicker.setTime(LocalTime.now());
	}

	private void setCurrentDate() {
		datePicker.setValue(LocalDate.now());
	}

	private void setTextFieldPropertys(CustomTextField textField, ObjectProperty<Location> property) {
		TextFields.bindAutoCompletion(textField, param -> getLocationList(param.getUserText()).getList());
		property.addListener(
				(observable, oldValue, newValue) -> textField.setText(newValue != null ? newValue.getName() : ""));
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				List<Location> list = getLocationList(textField.getText()).getList();
				property.setValue(!list.isEmpty() ? list.get(0) : null);
			}
		});
	}

	private void setValidation() {
		validation.registerValidator(fieldFrom, (control, field) -> new ValidationResult().addErrorIf(control,
				"Must not be empty", ((String) field).isEmpty()));
	}

	private LocationList getLocationList(String text) {
		return new LocationRequest().query(text).get();
	}

	@FXML
	private void switchValues(ActionEvent event) {
		Location valueFrom = from.getValue();
		from.setValue(to.getValue());
		to.setValue(valueFrom);
	}
}
