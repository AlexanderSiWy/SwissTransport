package swiss.transport.main;

import java.io.IOException;

import swiss.transport.entity.location.GeoLocation;
import swiss.transport.rest.geo.GeoLocationRequest;

public class MainTest {
	public static void main(String[] args) throws IOException {
		GeoLocationRequest request = new GeoLocationRequest();
		GeoLocation location = request.getLocation();
		System.out.println(location.getLocation().getLat());
		System.out.println(location.getLocation().getLng());
	}
}
