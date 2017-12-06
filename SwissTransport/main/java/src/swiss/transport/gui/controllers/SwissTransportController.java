package swiss.transport.gui.controllers;

import java.util.List;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import swiss.transport.entity.Location;
import swiss.transport.entity.Location.LocationList;
import swiss.transport.rest.LocationRequest;

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
	private GlyphFont font = GlyphFontRegistry.font("FontAwesome");;

	@FXML
	private void initialize() {
		btnSwitch.setGraphic(font.create(Glyph.RANDOM).size(15));
		ValidationSupport validation = new ValidationSupport();
		validation.registerValidator(fieldFrom, new Validator<String>() {

			@Override
			public ValidationResult apply(Control control, String field) {
				return new ValidationResult().addErrorIf(control, "Must not be empty", field.isEmpty());
			}
		});
		setTextFieldPropertys(fieldFrom);
		setTextFieldPropertys(fieldTo);
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
