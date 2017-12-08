package swiss.transport.gui.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

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
	private ValidationSupport validation;

	@FXML
	private void initialize() {
		btnSwitch.setGraphic(font.create(Glyph.RANDOM).size(15));
		setTextFieldPropertys(fieldFrom);
		setTextFieldPropertys(fieldTo);
		setCurrentDate();
		setCurrentTime();
	}

	private void setCurrentTime() {
		timePicker.setTime(LocalTime.now());
	}

	private void setCurrentDate() {
		datePicker.setValue(LocalDate.now());
	}

	private void setTextFieldPropertys(CustomTextField textField) {
		TextFields.bindAutoCompletion(textField, param -> {
			return getLocationList(param.getUserText()).getList();
		});
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				List<Location> list = getLocationList(textField.getText()).getList();
				textField.setText(!list.isEmpty() ? list.get(0).toString() : "");
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
		String valueFrom = fieldFrom.getText();
		fieldFrom.setText(fieldTo.getText());
		fieldTo.setText(valueFrom);
	}
}
