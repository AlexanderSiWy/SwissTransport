package swiss.transport.gui.elements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.controlsfx.control.MasterDetailPane;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import swiss.transport.entity.transport.Connection;
import swiss.transport.entity.transport.Connection.ConnectionList;
import swiss.transport.entity.transport.Location;
import swiss.transport.entity.transport.Section;
import swiss.transport.rest.transport.ConnectionRequest;

public class ResultsView extends MasterDetailPane {

	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	public void setConnectionRequest(Location from, Location to, LocalDate date, LocalTime time,
			boolean isArrivalTime) {
		ConnectionRequest connectionRequest = new ConnectionRequest(from.getId(), to.getId()).date(date).time(time)
				.isArrivalTime(isArrivalTime).limit(ConnectionRequest.MAX_LIMIT);
		ConnectionList connectionList = connectionRequest.get();
		TableView<Connection> connectionTable = getConnectionTable();
		connectionTable.setItems(new ObservableListWrapper<>(connectionList.getList()));
		setMasterNode(connectionTable);
	}

	private TableView<Connection> getConnectionTable() {
		TableView<Connection> tableView = new TableView<Connection>();
		tableView.getColumns().add(getDirectionColumn());
		tableView.getColumns().add(getDepartureColumn());
		tableView.getColumns().add(getArrivalColumn());
		tableView.getColumns().add(getDurationColumn());
		return tableView;
	}

	private TableColumn<Connection, String> getDurationColumn() {
		TableColumn<Connection, String> duration = new TableColumn<>("Dauer");
		duration.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDuration()));
		return duration;
	}

	private TableColumn<Connection, String> getDepartureColumn() {
		TableColumn<Connection, String> departureTime = new TableColumn<>("Abfahrt");
		departureTime.setCellValueFactory(
				param -> new SimpleStringProperty(formatter.format(param.getValue().getFrom().getDeparture())));
		return departureTime;
	}

	private TableColumn<Connection, String> getArrivalColumn() {
		TableColumn<Connection, String> departureTime = new TableColumn<>("Ankunft");
		departureTime.setCellValueFactory(
				param -> new SimpleStringProperty(formatter.format(param.getValue().getTo().getArrival())));
		return departureTime;
	}

	private TableColumn<Connection, String> getDirectionColumn() {
		TableColumn<Connection, String> direction = new TableColumn<Connection, String>("Richtung");
		direction.setCellValueFactory(param -> {
			List<Section> sections = param.getValue().getSections();
			return new SimpleStringProperty(sections.get(sections.size() - 1).getJourney().getTo());
		});
		return direction;
	}
}