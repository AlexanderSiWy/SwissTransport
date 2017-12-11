package swiss.transport.gui.elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.StationBoard;
import swiss.transport.entity.transport.StationBoard.StationBoardList;
import swiss.transport.entity.transport.Stop;
import swiss.transport.rest.transport.LocationRequest;
import swiss.transport.rest.transport.StationBoardRequest;

public class StationBoardResultsView extends MasterDetailPane {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private TableView<StationBoard> stationBoardTable = getTable();
	private TableView<Stop> detailTable = getDetailTable();
	private Location station;

	public StationBoardResultsView() {
		setMasterNode(stationBoardTable);
		setDetailNode(detailTable);
		setDetailOnSelectedPropertyChanged();
	}

	private void setDetailOnSelectedPropertyChanged() {
		stationBoardTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				List<Stop> passList = newValue.getPassList();
				if (!passList.isEmpty()) {
					passList.remove(0);
				}
				Stop stop = newValue.getStop();
				stop.setLocation(station);
				passList.add(0, stop);
				detailTable.setItems(new ObservableListWrapper<>(passList));
			}
		});
	}

	private TableView<Stop> getDetailTable() {
		TableView<Stop> tableView = new TableView<>();
		ObservableList<TableColumn<Stop, ?>> columns = tableView.getColumns();
		columns.add(getNameDetailColumn());
		columns.add(getArrivalDetailColumn());
		return tableView;
	}

	private TableColumn<Stop, String> getArrivalDetailColumn() {
		TableColumn<Stop, String> column = new TableColumn<>("Abfahrt");
		column.setCellValueFactory(param -> {
			return new SimpleStringProperty(formatter.format(param.getValue().getDeparture() != null
					? param.getValue().getDeparture() : param.getValue().getArrival()));
		});
		column.setPrefWidth(100);
		return column;
	}

	private TableColumn<Stop, String> getNameDetailColumn() {
		TableColumn<Stop, String> column = new TableColumn<>("Station");
		column.setCellValueFactory(param -> {
			return new SimpleStringProperty(new LocationRequest().query(param.getValue().getLocation().getId()).get()
					.getList().get(0).getName());
		});
		column.setPrefWidth(200);
		return column;
	}

	public void setRequest(Location station, LocalDate date, LocalTime time) {
		this.station = station;
		StationBoardRequest stationBoardRequest = new StationBoardRequest(station.getId())
				.datetime(LocalDateTime.of(date, time));
		StationBoardList stationBoardList = stationBoardRequest.get();
		stationBoardTable.setItems(new ObservableListWrapper<>(stationBoardList.getList()));
	}

	private TableView<StationBoard> getTable() {
		TableView<StationBoard> tableView = new TableView<>();
		tableView.getColumns().add(getDirectionColumn());
		tableView.getColumns().add(getDepartureColumn());
		tableView.getColumns().add(getArrivalColumn());
		tableView.getColumns().add(getPlatformColumn());
		return tableView;
	}

	private TableColumn<StationBoard, String> getPlatformColumn() {
		TableColumn<StationBoard, String> platform = new TableColumn<>("Gleis");
		platform.setCellValueFactory(param -> {
			return new SimpleStringProperty(getPlatformText(param.getValue()));
		});
		platform.setPrefWidth(50);
		return platform;
	}

	public static String getPlatformText(StationBoard value) {
		return value.getStop().getPlatform();
	}

	private TableColumn<StationBoard, String> getDepartureColumn() {
		TableColumn<StationBoard, String> departureTime = new TableColumn<>("Abfahrt");
		departureTime.setCellValueFactory(param -> {
			return new SimpleStringProperty(getDepartureText(param.getValue()));
		});
		departureTime.setPrefWidth(100);
		return departureTime;
	}

	public static String getDepartureText(StationBoard value) {
		return formatter.format(value.getStop().getDeparture());
	}

	private TableColumn<StationBoard, String> getArrivalColumn() {
		TableColumn<StationBoard, String> arrivalTime = new TableColumn<>("Ankunft");
		arrivalTime.setCellValueFactory(param -> {
			return new SimpleStringProperty(getArrivalText(param.getValue()));
		});
		arrivalTime.setPrefWidth(100);
		return arrivalTime;
	}

	public static String getArrivalText(StationBoard value) {
		List<Stop> passList = value.getPassList();
		String text = formatter.format(passList.get(passList.size() - 1).getArrival());
		return text;
	}

	public TableColumn<StationBoard, String> getDirectionColumn() {
		TableColumn<StationBoard, String> direction = new TableColumn<>("Richtung");
		direction.setCellValueFactory(param -> {
			return new SimpleStringProperty(getDirectionText(param.getValue()));
		});
		direction.setPrefWidth(200);
		return direction;
	}

	public static String getDirectionText(StationBoard value) {
		return value.getTo();
	}

	public TableView<StationBoard> getTableView() {
		return stationBoardTable;
	}
}
