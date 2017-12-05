package transport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import rest.JsonList;

public class Station {
	private String id;
	private String name;
	private String score;
	private Coordinate coordinate;
	private String distance;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return score;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public String getDistance() {
		return distance;
	}

	public static class StationList implements JsonList<Station> {
		@JsonProperty(value = "stations")
		private List<Station> list;

		@Override
		public List<Station> getList() {
			return list;
		}
	}
}
