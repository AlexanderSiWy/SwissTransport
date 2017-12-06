package swiss.transport.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import swiss.transport.rest.JsonList;

public class Location {
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

	public static class LocationList implements JsonList<Location> {
		@JsonProperty(value = "stations")
		private List<Location> list;

		@Override
		public List<Location> getList() {
			return list;
		}
	}
}