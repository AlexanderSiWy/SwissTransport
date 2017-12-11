package swiss.transport.gui.elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.StationBoard;
import swiss.transport.entity.transport.StationBoard.StationBoardList;
import swiss.transport.entity.transport.Stop;
import swiss.transport.rest.transport.StationBoardRequest;

public class StationBoardResultsView extends MasterDetailPane {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private TableView<StationBoard> stationBoardTable;

	public StationBoardResultsView() {
		stationBoardTable = getConnectionTable();
		setMasterNode(stationBoardTable);
	}

	public void setRequest(Location station, LocalDate date, LocalTime time) {
		StationBoardRequest stationBoardRequest = new StationBoardRequest(station.getId())
				.datetime(LocalDateTime.of(date, time));
		StationBoardList stationBoardList = stationBoardRequest.get();
		stationBoardTable.setItems(new ObservableListWrapper<>(stationBoardList.getList()));
	}

	private TableView<StationBoard> getConnectionTable() {
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
