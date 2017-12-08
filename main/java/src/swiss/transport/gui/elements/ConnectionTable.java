package swiss.transport.gui.elements;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import swiss.transport.entity.transport.Connection;
import swiss.transport.entity.transport.Connection.ConnectionList;

public class ConnectionTable extends TableView<Connection> {

	public ConnectionTable(ConnectionList list) {
		addColumns();
		setConnectionList(list);
	}

	public ConnectionTable() {
		addColumns();
	}

	public void setConnectionList(ConnectionList list) {
		setItems(new ObservableListWrapper<>(list.getList()));
	}

	private void addColumns() {
		TableColumn<Connection, String> direction = new TableColumn<>("Richtung");
		direction.setCellValueFactory(param -> {
			return new SimpleStringProperty(param.getValue().getTo().getLocation().getName());
		});
		getColumns().add(direction);
	}
}
