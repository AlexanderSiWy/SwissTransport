package swiss.transport.gui.elements;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewToStringConverter<T> {
	public String convert(TableView<T> table) {
		StringBuilder builder = new StringBuilder();
		String tab = "\t";
		String newLine = "\n";
		Map<TableColumn<T, ?>, Integer> longestText = getLongestTextInColumn(table);
		ObservableList<T> items = table.getItems();
		for (TableColumn<T, ?> column : table.getColumns()) {
			builder.append(toLenght(column.getText(), longestText.get(column))).append(tab);
		}
		builder.append(newLine);
		for (T item : items) {
			for (TableColumn<T, ?> column : table.getColumns()) {
				Object cellData = column.getCellObservableValue(item).getValue();
				builder.append(cellData == null ? toLenght("", longestText.get(column))
						: toLenght(cellData.toString(), longestText.get(column))).append(tab);
			}
			builder.append(newLine);
		}
		return builder.toString();
	}

	private String toLenght(String text, Integer length) {
		// because in email a space is not same size as a normal char
		int localLength = length + 5;
		double difference = (localLength - text.length()) * 1.1;
		while (text.length() < difference + localLength) {
			text = text.concat(" ");
		}
		return text;
	}

	private Map<TableColumn<T, ?>, Integer> getLongestTextInColumn(TableView<T> table) {
		HashMap<TableColumn<T, ?>, Integer> map = new HashMap<>();
		ObservableList<T> items = table.getItems();
		ObservableList<TableColumn<T, ?>> columns = table.getColumns();
		for (TableColumn<T, ?> column : columns) {
			map.put(column, column.getText().length());
		}
		for (T item : items) {
			for (TableColumn<T, ?> column : columns) {
				Object cellData = column.getCellObservableValue(item).getValue();
				int length = cellData == null ? 0 : cellData.toString().length();
				if (length > map.get(column)) {
					map.put(column, length);
				}
			}
		}
		return map;
	}
}
