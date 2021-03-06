package swiss.transport.gui.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import swiss.transport.entity.transport.Connection;
import swiss.transport.entity.transport.Coordinate;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.Location.LocationList;
import swiss.transport.entity.transport.StationBoard;
import swiss.transport.exception.MailNotSupportedException;
import swiss.transport.gui.elements.ConnectionResultsView;
import swiss.transport.gui.elements.StationBoardResultsView;
import swiss.transport.gui.elements.TableViewToStringConverter;
import swiss.transport.gui.elements.TimePicker;
import swiss.transport.mail.Email;
import swiss.transport.rest.transport.LocationRequest;

public class SwissTransportController {

	private static final Logger LOGGER = LogManager.getLogger(SwissTransportController.class);

	@FXML
	private ConnectionResultsView connectionResultsView;
	@FXML
	private StationBoardResultsView stationBoardResultsView;
	@FXML
	private WebView mapView;
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
	@FXML
	private Label lblDeparture;
	@FXML
	private Label lblArrival;
	@FXML
	private ToggleSwitch tglbtnTimeIsArrival;
	@FXML
	private Button btnSearch;
	@FXML
	private GridPane formPane;
	@FXML
	private Tab mapTab;
	@FXML
	private Tab stationBoardTab;
	@FXML
	private Tab connectionsTab;
	@FXML
	private TabPane tabPane;
	@FXML
	private BorderPane mainPane;
	@FXML
	private Button btnMailConnecitonResultsView;
	@FXML
	private Button btnMailStationResultsView;

	private GlyphFont font = GlyphFontRegistry.font("FontAwesome");
	private ValidationSupport validation = new ValidationSupport();

	private ObjectProperty<Location> from = new SimpleObjectProperty<>();
	private ObjectProperty<Location> to = new SimpleObjectProperty<>();

	@FXML
	private void initialize() {
		btnSwitch.setGraphic(font.create(Glyph.RANDOM).size(15));
		btnMailConnecitonResultsView.setGraphic(font.create(Glyph.ENVELOPE).size(15));
		btnMailStationResultsView.setGraphic(font.create(Glyph.ENVELOPE).size(15));
		setTextFieldPropertys(fieldFrom, from);
		setMapLoadOnFromChange();
		setTextFieldPropertys(fieldTo, to);
		setCurrentDate();
		setCurrentTime();
		setArrivalDeparturePropertys();
		setValidation();
		validation.invalidProperty().addListener((observable, oldValue, newValue) -> btnSearch.setDisable(newValue));
		setSearchOnControlEnter();
		setClosestLocation(from, fieldFrom);
	}

	private void setMapLoadOnFromChange() {
		from.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				tabPane.getSelectionModel().select(mapTab);
				loadMap();
			}
		});
	}

	private void loadMap() {
		mapView.getEngine().loadContent(getGoogleMapsIframe(from.getValue().getCoordinate()));
	}

	private String getGoogleMapsIframe(Coordinate coordinate) {
		return "<iframe\r\n" + "  width=\"100%\" height=\"95%\" frameborder=\"0\" style=\"border:0\""
				+ "  src=\"https://www.google.com/maps/embed/v1/place?key=AIzaSyCNnL2vDiSRb9urLsTosrTT5MlRohifxo0"
				+ "&q=" + coordinate.getX() + "," + coordinate.getY() + "\"></iframe>";
	}

	private void setSearchOnControlEnter() {
		formPane.setOnKeyReleased(event -> {
			if (event.isControlDown() && event.getCode().equals(KeyCode.ENTER)) {
				search();
			}
		});
	}

	private void setArrivalDeparturePropertys() {
		setFontWeight(lblDeparture, FontWeight.BOLD);
		tglbtnTimeIsArrival.selectedProperty().addListener((observable, oldValue, newValue) -> {
			revertCssAdjustments();
			setFonts(newValue);
		});
	}

	private void revertCssAdjustments() {
		tglbtnTimeIsArrival.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
	}

	private void setFonts(Boolean isArrival) {
		if (isArrival) {
			setFontWeight(lblArrival, FontWeight.BOLD);
			setFontWeight(lblDeparture, FontWeight.NORMAL);
		} else {
			setFontWeight(lblArrival, FontWeight.NORMAL);
			setFontWeight(lblDeparture, FontWeight.BOLD);
		}
	}

	private void setFontWeight(Label label, FontWeight bold) {
		Font font = lblArrival.getFont();
		label.setFont(Font.font(font.getFamily(), bold, font.getSize()));
	}

	@FXML
	private void searchOnButton(ActionEvent event) {
		search();
	}

	private void search() {
		selectFirst(fieldTo, to);
		selectFirst(fieldFrom, from);
		btnMailConnecitonResultsView.setDisable(true);
		btnMailStationResultsView.setDisable(true);
		if (!validation.isInvalid()) {
			tabPane.getSelectionModel().select(stationBoardTab);
			if (to.getValue() != null) {
				connectionResultsView.setRequest(from.getValue(), to.getValue(), datePicker.getValue(),
						timePicker.getTime(), tglbtnTimeIsArrival.isSelected());
				tabPane.getSelectionModel().select(connectionsTab);
				btnMailConnecitonResultsView.setDisable(false);
			} else {
				connectionResultsView.setRequestNull();
			}
			stationBoardResultsView.setRequest(from.getValue(), datePicker.getValue(), timePicker.getTime());
			btnMailStationResultsView.setDisable(false);
		}
	}

	private void setClosestLocation(ObjectProperty<Location> property, CustomTextField field) {
		// so it doesn't crash if getting closest location doesn't work
		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				Location closestLocation = LocationList.getClosestLocation();
				Platform.runLater(() -> {
					if (closestLocation != null) {
						property.setValue(closestLocation);
					}
				});
			} catch (IOException e) {
				LOGGER.warn("Standort wurde nicht gefunden.");
				LOGGER.debug("Stacktrace: ", e);
			}
		});
	}

	private void setCurrentTime() {
		timePicker.setTime(LocalTime.now());
	}

	private void setCurrentDate() {
		datePicker.setValue(LocalDate.now());
	}

	private void setTextFieldPropertys(CustomTextField textField, ObjectProperty<Location> property) {
		bindAutoComplete(textField);
		property.addListener((observable, oldValue, newValue) -> setFieldText(textField, newValue));
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				selectFirst(textField, property);
			}
		});
	}

	private AutoCompletionBinding<Location> bindAutoComplete(CustomTextField textField) {
		return TextFields.bindAutoCompletion(textField, param -> {
			List<Location> list = getLocationList(param.getUserText()).getList();
			if (!list.isEmpty() && list.get(0).getName().equals(textField.getText())) {
				return null;
			}
			return list;
		});
	}

	private void setFieldText(CustomTextField textField, Location newValue) {
		textField.setText(newValue != null ? newValue.getName() : "");
	}

	private void selectFirst(CustomTextField textField, ObjectProperty<Location> property) {
		List<Location> list = getLocationList(textField.getText()).getList();
		if (!list.isEmpty()) {
			if (!list.get(0).equals(property.getValue())) {
				property.setValue(list.get(0));
			}
		} else {
			property.setValue(null);
		}
	}

	private void setValidation() {
		final String text = "Darf nicht leer sein";
		validation.registerValidator(fieldFrom, false,
				(control, field) -> new ValidationResult().addErrorIf(control, text, ((String) field).isEmpty()));
		validation.registerValidator(fieldTo, false,
				(control, field) -> new ValidationResult().addWarningIf(control, text, ((String) field).isEmpty()));
		validation.registerValidator(datePicker, false,
				(control, field) -> new ValidationResult().addErrorIf(control, text, field == null));
		validation.registerValidator(timePicker, false,
				(control, field) -> new ValidationResult().addErrorIf(control, text, field == null));
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

	@FXML
	private void mailStationResultsView(ActionEvent event) {
		sendEmail(String.format("Stationsliste von %s", from.getValue().getName()),
				new TableViewToStringConverter<StationBoard>().convert(stationBoardResultsView.getTableView()));
	}

	@FXML
	private void mailConnectionResultsView(ActionEvent event) {
		sendEmail(String.format("Verbindungen von %s nach %s", from.getValue().getName(), to.getValue().getName()),
				new TableViewToStringConverter<Connection>().convert(connectionResultsView.getTableView()));
	}

	private void sendEmail(String subject, String content) {
		try {
			new Email(subject, content).open();
		} catch (IOException | URISyntaxException | MailNotSupportedException e) {
			LOGGER.error("Mail senden ist fehlgeschlagen.");
			LOGGER.debug("Stacktrace: ", e);
		}
	}
}
