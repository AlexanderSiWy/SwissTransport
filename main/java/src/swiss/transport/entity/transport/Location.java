package swiss.transport.entity.transport;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import swiss.transport.entity.location.GeoLocation;
import swiss.transport.rest.geo.GeoLocationRequest;
import swiss.transport.rest.transport.JsonList;
import swiss.transport.rest.transport.LocationRequest;

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

	@Override
	public String toString() {
		return name;
	}

	public static class LocationList implements JsonList<Location> {
		@JsonProperty(value = "stations")
		private List<Location> list;

		@Override
		public List<Location> getList() {
			return list;
		}

		public Location getClosestLocation(GeoLocation geoLocation) {
			Iterator<Location> iterator = list.iterator();
			double geoY = geoLocation.getLocation().getLng();
			double geoX = geoLocation.getLocation().getLat();
			Location closestLocation = null;
			double closestDistance = Double.MAX_VALUE;
			while (iterator.hasNext()) {
				Location location = iterator.next();
				Coordinate coordinates = location.getCoordinate();
				if (coordinates.getX() != null && coordinates.getY() != null) {
					double distance = Math.abs(geoY - coordinates.getY()) + Math.abs(geoX - coordinates.getX());
					if (distance < closestDistance) {
						closestLocation = location;
						closestDistance = distance;
					}
				}
			}
			return closestLocation;
		}

		public static Location getClosestLocation() throws IOException {
			GeoLocation geoLocation = new GeoLocationRequest().getLocation();
			return new LocationRequest().xy(geoLocation).get().getClosestLocation(geoLocation);
		}
	}
}
