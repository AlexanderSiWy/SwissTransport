package swiss.transport.gui.elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
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
				Iterator<Stop> iterator = passList.iterator();
				if (iterator.hasNext()) {
					iterator.next().setLocation(station);
				}
				// reload station because station name is not filled
				iterator.forEachRemaining(station -> station.setLocation(
						new LocationRequest().query(station.getLocation().getId()).get().getList().get(0)));
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
		column.setCellValueFactory(param -> new SimpleStringProperty(
				TIME_FORMATTER.format(param.getValue().getDeparture() != null ? param.getValue().getDeparture()
						: param.getValue().getArrival())));
		column.setPrefWidth(100);
		return column;
	}

	private TableColumn<Stop, String> getNameDetailColumn() {
		TableColumn<Stop, String> column = new TableColumn<>("Station");
		column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLocation().getName()));
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
		tableView.getColumns().add(getDateColumn());
		tableView.getColumns().add(getDepartureColumn());
		tableView.getColumns().add(getArrivalColumn());
		tableView.getColumns().add(getPlatformColumn());
		return tableView;
	}

	private TableColumn<StationBoard, String> getPlatformColumn() {
		TableColumn<StationBoard, String> platform = new TableColumn<>("Gleis");
		platform.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStop().getPlatform()));
		platform.setPrefWidth(50);
		return platform;
	}

	private TableColumn<StationBoard, String> getDateColumn() {
		TableColumn<StationBoard, String> departureTime = new TableColumn<>("Datum");
		departureTime.setCellValueFactory(
				param -> new SimpleStringProperty(DATE_FORMATTER.format(param.getValue().getStop().getDeparture())));
		departureTime.setPrefWidth(100);
		return departureTime;
	}

	private TableColumn<StationBoard, String> getDepartureColumn() {
		TableColumn<StationBoard, String> departureTime = new TableColumn<>("Abfahrt");
		departureTime.setCellValueFactory(param -> {
			return new SimpleStringProperty(TIME_FORMATTER.format(param.getValue().getStop().getDeparture()));
		});
		departureTime.setPrefWidth(100);
		return departureTime;
	}

	private TableColumn<StationBoard, String> getArrivalColumn() {
		TableColumn<StationBoard, String> arrivalTime = new TableColumn<>("Ankunft");
		arrivalTime.setCellValueFactory(param -> {
			List<Stop> passList = param.getValue().getPassList();
			return new SimpleStringProperty(TIME_FORMATTER.format(passList.get(passList.size() - 1).getArrival()));
		});
		arrivalTime.setPrefWidth(100);
		return arrivalTime;
	}

	public TableColumn<StationBoard, String> getDirectionColumn() {
		TableColumn<StationBoard, String> direction = new TableColumn<>("Richtung");
		direction.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTo()));
		direction.setPrefWidth(200);
		return direction;
	}

	public TableView<StationBoard> getTableView() {
		return stationBoardTable;
	}
}
