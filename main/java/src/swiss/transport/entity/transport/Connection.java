package swiss.transport.entity.transport;

import java.time.Duration;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonProperty;

import swiss.transport.rest.adapter.DurationAdapter;
import swiss.transport.rest.transport.JsonList;

public class Connection {
	private Stop from;
	private Stop to;
	@XmlJavaTypeAdapter(DurationAdapter.class)
	private Duration duration;
	private int transfers;
	private String service;
	private List<String> products;
	private String capacity1st;
	private String capacity2nd;
	private List<Section> sections;

	public Stop getFrom() {
		return from;
	}

	public Stop getTo() {
		return to;
	}

	public Duration getDuration() {
		return duration;
	}

	public int getTransfers() {
		return transfers;
	}

	public String getService() {
		return service;
	}

	public List<String> getProducts() {
		return products;
	}

	public String getCapacity1st() {
		return capacity1st;
	}

	public String getCapacity2nd() {
		return capacity2nd;
	}

	public List<Section> getSections() {
		return sections;
	}

	public static class ConnectionList implements JsonList<Connection> {
		@JsonProperty(value = "connections")
		private List<Connection> list;
		private Location from;
		private Location to;
		private Stations stations;

		@Override
		public List<Connection> getList() {
			return list;
		}

		public Location getFrom() {
			return from;
		}

		public Location getTo() {
			return to;
		}

		public Stations getStations() {
			return stations;
		}
	}
}
