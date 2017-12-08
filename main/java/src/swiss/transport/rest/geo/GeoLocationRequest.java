package swiss.transport.rest.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import swiss.transport.entity.location.GeoLocation;

public class GeoLocationRequest {
	public GeoLocation getLocation() throws IOException {
		WebTarget target = ClientBuilder.newClient().target("https://www.googleapis.com/geolocation/v1/geolocate")
				.queryParam("key", "AIzaSyCF2InReisOEE-xaGxtCe7Jx4qcb8jLByU");
		GeoLocationRequestEntity geoLocationRequestEntity = new GeoLocationRequestEntity(getWifiAccessPoints());
		return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(geoLocationRequestEntity))
				.readEntity(GeoLocation.class);
	}

	// TODO Language
	private List<WifiAccessPoints> getWifiAccessPoints() throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("netsh wlan show networks mode=bssid");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<String> lines = bufferedReader.lines().collect(Collectors.toList());
		List<WifiAccessPoints> accessPoints = new ArrayList<>();
		WifiAccessPoints currentAccessPoint = null;
		for (String line : lines) {
			if (line.contains("BSSIDD")) {
				if (currentAccessPoint != null) {
					accessPoints.add(currentAccessPoint);
				}
				currentAccessPoint = new WifiAccessPoints(line.substring(getStartIndex(line)).trim());
			} else if (line.contains("Signal")) {
				if (currentAccessPoint != null) {
					currentAccessPoint.setSignalStrength(line.substring(getStartIndex(line)).trim().replace("%", ""));
				}
			} else if (line.contains("Kanal")) {
				if (currentAccessPoint != null) {
					currentAccessPoint.setChannel(line.substring(getStartIndex(line)).trim());
				}
			}
		}
		accessPoints.add(currentAccessPoint);
		return accessPoints;
	}

	private int getStartIndex(String line) {
		return line.indexOf(':') + 1;
	}
}
