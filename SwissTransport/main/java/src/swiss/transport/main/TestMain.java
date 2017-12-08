package swiss.transport.main;

import swiss.transport.entity.location.GeoLocation;
import swiss.transport.rest.geo.GeoLocationRequest;

public class TestMain {
	public static void main(String[] args) {
		GeoLocationRequest request = new GeoLocationRequest();
		GeoLocation location = request.getLocation();
		System.out.println(location.getLocation().getLat());
		System.out.println(location.getLocation().getLng());
	}
}
