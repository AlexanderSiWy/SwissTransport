package transport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import rest.JsonList;

public class Connection {
	private Stop from;
	private Stop to;
	private String duration;
	private String transfers;
	private String service; // TODO
	private List<String> products;
	private String capacity1st;
	private String capacity2nd;
	@JsonIgnore
	private List<Object> sections; // TODO

	public Stop getFrom() {
		return from;
	}

	public Stop getTo() {
		return to;
	}

	public String getDuration() {
		return duration;
	}

	public String getTransfers() {
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

	public List<Object> getSections() {
		return sections;
	}

	public static class ConnectionList implements JsonList<Location> {
		@JsonProperty(value = "connections")
		private List<Location> list;

		@Override
		public List<Location> getList() {
			return list;
		}
	}
}
