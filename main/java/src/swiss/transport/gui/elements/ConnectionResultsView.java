package swiss.transport.gui.elements;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.MasterDetailPane;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import swiss.transport.entity.transport.Connection;
import swiss.transport.entity.transport.Connection.ConnectionList;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.Section;
import swiss.transport.rest.transport.ConnectionRequest;

public class ConnectionResultsView extends MasterDetailPane {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	private TableView<Connection> connectionTable = getConnectionTable();
	private TableView<Section> detailTable = getDetailTable();

	public ConnectionResultsView() {
		setDetailOnSelectedPropertyChanged();
		setMasterNode(connectionTable);
		setDetailNode(detailTable);
	}

	private void setDetailOnSelectedPropertyChanged() {
		connectionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				detailTable.setItems(new ObservableListWrapper<>(newValue.getSections().stream()
						.filter(value -> value.getJourney() != null).collect(Collectors.toList())));
			}
		});
	}

	private TableView<Section> getDetailTable() {
		TableView<Section> tableView = new TableView<>();
		ObservableList<TableColumn<Section, ?>> columns = tableView.getColumns();
		columns.add(getDepartureDetailColumn());
		columns.add(getDepartureTimeDetailColumn());
		columns.add(getDeparturePlatformDetailColumn());
		columns.add(getArrivalDetailColumn());
		columns.add(getArrivalTimeDetailColumn());
		return tableView;
	}

	private TableColumn<Section, String> getDepartureTimeDetailColumn() {
		TableColumn<Section, String> column = new TableColumn<>("Abfahrt");
		column.setCellValueFactory(param -> new SimpleStringProperty(
				TIME_FORMATTER.format(param.getValue().getDeparture().getDeparture())));
		column.setPrefWidth(60);
		return column;
	}

	private TableColumn<Section, String> getArrivalTimeDetailColumn() {
		TableColumn<Section, String> column = new TableColumn<>("Ankunft");
		column.setCellValueFactory(
				param -> new SimpleStringProperty(TIME_FORMATTER.format(param.getValue().getArrival().getArrival())));
		column.setPrefWidth(60);
		return column;
	}

	private TableColumn<Section, String> getArrivalDetailColumn() {
		TableColumn<Section, String> column = new TableColumn<>("Nach");
		column.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getArrival().getLocation().getName()));
		column.setPrefWidth(200);
		return column;
	}

	private TableColumn<Section, String> getDeparturePlatformDetailColumn() {
		TableColumn<Section, String> column = new TableColumn<>("Gleis");
		column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDeparture().getPlatform()));
		column.setPrefWidth(50);
		return column;
	}

	private TableColumn<Section, String> getDepartureDetailColumn() {
		TableColumn<Section, String> column = new TableColumn<>("Von");
		column.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getDeparture().getLocation().getName()));
		column.setPrefWidth(200);
		return column;
	}

	public void setRequest(Location from, Location to, LocalDate date, LocalTime time, boolean isArrivalTime) {
		ConnectionRequest connectionRequest = new ConnectionRequest(from.getId(), to.getId()).date(date).time(time)
				.isArrivalTime(isArrivalTime).limit(ConnectionRequest.MAX_LIMIT);
		ConnectionList connectionList = connectionRequest.get();
		detailTable.setItems(null);
		connectionTable.setItems(new ObservableListWrapper<>(connectionList.getList()));
	}

	private TableView<Connection> getConnectionTable() {
		TableView<Connection> tableView = new TableView<>();
		ObservableList<TableColumn<Connection, ?>> columns = tableView.getColumns();
		columns.add(getDirectionColumn());
		columns.add(getDateColumn());
		columns.add(getDepartureColumn());
		columns.add(getArrivalColumn());
		columns.add(getDurationColumn());
		columns.add(getTransfersColumn());
		columns.add(getPlatformColumn());
		return tableView;
	}

	private TableColumn<Connection, String> getPlatformColumn() {
		TableColumn<Connection, String> platform = new TableColumn<>("Gleis");
		platform.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFrom().getPlatform()));
		platform.setPrefWidth(50);
		return platform;
	}

	private TableColumn<Connection, String> getTransfersColumn() {
		TableColumn<Connection, String> transfers = new TableColumn<>("Umsteigen");
		transfers.setCellValueFactory(
				param -> new SimpleStringProperty(String.valueOf(param.getValue().getTransfers())));
		transfers.setPrefWidth(70);
		return transfers;
	}

	private TableColumn<Connection, String> getDurationColumn() {
		TableColumn<Connection, String> durationColumn = new TableColumn<>("Dauer");
		durationColumn.setCellValueFactory(
				param -> new SimpleStringProperty(getDurationText(param.getValue().getDuration())));
		durationColumn.setPrefWidth(100);
		return durationColumn;
	}

	private String getDurationText(Duration duration) {
		int totalMinutes = (int) duration.getSeconds() / 60;
		int totalHours = totalMinutes / 60;
		int minutes = totalMinutes % 60;
		int hours = totalHours % 24;
		int days = totalHours / 24;
		String durationText = (days == 0 ? "" : days + " Tage ") + (hours == 0 ? "" : hours + " h ")
				+ (minutes == 0 ? "" : minutes + " min");
		return durationText;
	}

	private TableColumn<Connection, String> getDepartureColumn() {
		TableColumn<Connection, String> departureTime = new TableColumn<>("Abfahrt");
		departureTime.setCellValueFactory(
				param -> new SimpleStringProperty(TIME_FORMATTER.format(param.getValue().getFrom().getDeparture())));
		departureTime.setPrefWidth(60);
		return departureTime;
	}

	private TableColumn<Connection, String> getDateColumn() {
		TableColumn<Connection, String> departureTime = new TableColumn<>("Datum");
		departureTime.setCellValueFactory(
				param -> new SimpleStringProperty(DATE_FORMATTER.format(param.getValue().getFrom().getDeparture())));
		departureTime.setPrefWidth(75);
		return departureTime;
	}

	private TableColumn<Connection, String> getArrivalColumn() {
		TableColumn<Connection, String> arrivalTime = new TableColumn<>("Ankunft");
		arrivalTime.setCellValueFactory(
				param -> new SimpleStringProperty(TIME_FORMATTER.format(param.getValue().getTo().getArrival())));
		arrivalTime.setPrefWidth(60);
		return arrivalTime;
	}

	private TableColumn<Connection, String> getDirectionColumn() {
		TableColumn<Connection, String> direction = new TableColumn<Connection, String>("Richtung");
		direction.setCellValueFactory(param -> {
			List<Section> sections = param.getValue().getSections();
			for (Section section : sections) {
				if (section.getJourney() != null) {
					return new SimpleStringProperty(section.getJourney().getTo());
				}
			}
			return new SimpleStringProperty(param.getValue().getTo().getLocation().getName());
		});
		direction.setPrefWidth(200);
		return direction;
	}

	public TableView<Connection> getTableView() {
		return connectionTable;
	}

	public void setRequestNull() {
		detailTable.setItems(null);
		connectionTable.setItems(null);
	}
}
