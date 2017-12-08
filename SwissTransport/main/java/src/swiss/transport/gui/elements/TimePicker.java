package swiss.transport.gui.elements;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.controlsfx.control.textfield.CustomTextField;

public class TimePicker extends CustomTextField {
	public static final int MAX_HOURS = 23;
	public static final int MIN_HOURS = 0;
	public static final int MAX_MINUTES = 59;
	public static final int MIN_MINUTES = 0;
	private LocalTime time;

	public TimePicker() {
		textProperty().addListener((observable, oldValue, newValue) -> getTextFilter(newValue));
		focusedProperty().addListener((observable, oldValue, newValue) -> {
			getTimeFormatter(newValue);
		});
	}

	private void getTimeFormatter(Boolean newValue) {
		if (!newValue) {
			String hour = "";
			String minute = "";
			String text = getText();
			if (text.contains(":")) {
				int indexOfColon = text.indexOf(':');
				hour = text.substring(0, indexOfColon);
				minute = text.substring(indexOfColon + 1);
				hour = ifEmptySetZero(hour);
				minute = ifEmptySetZero(minute);
			} else {
				while (text.length() < 4) {
					text = "0".concat(text);
				}
				hour = text.substring(0, 2);
				minute = text.substring(2);
			}
			setTime(LocalTime.of(getHour(hour), getMinute(minute)));
		}
	}

	private String ifEmptySetZero(String text) {
		if (text.isEmpty()) {
			text = "0";
		}
		return text;
	}

	private int getMinute(String text) {
		int minute = Integer.parseInt(text);
		if (minute > 59) {
			minute = 59;
		} else if (minute < 0) {
			minute = 0;
		}
		return minute;
	}

	private int getHour(String text) {
		int hour = Integer.parseInt(text);
		if (hour == 24) {
			hour = 0;
		} else if (hour > 23) {
			hour = 23;
		} else if (hour < 0) {
			hour = 0;
		}
		return hour;
	}

	private void getTextFilter(String text) {
		int maxLength = text.contains(":") ? 5 : 4;
		if (text.length() > maxLength) {
			setText(text.substring(0, maxLength));
		}
		if (text.matches("[^\\d:]")) {
			setText(text.replaceAll("[^\\d:]", ""));
		}
	}

	public void setTime(LocalTime time) {
		this.time = time;
		if (time != null) {
			setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
		}
	}

	public LocalTime getTime() {
		return time;
	}
}
