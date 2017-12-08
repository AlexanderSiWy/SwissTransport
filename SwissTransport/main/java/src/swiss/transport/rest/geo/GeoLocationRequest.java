package swiss.transport.rest.geo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import swiss.transport.entity.location.GeoLocation;

public class GeoLocationRequest {
	public GeoLocation getLocation() {
		WebTarget target = ClientBuilder.newClient().target("https://www.googleapis.com/geolocation/v1/geolocate")
				.queryParam("key", "AIzaSyCF2InReisOEE-xaGxtCe7Jx4qcb8jLByU");
		List<WifiAccessPoints> maccAdresses = new ArrayList<>();
		maccAdresses.add(new WifiAccessPoints("08:6a:0a:95:33:69", "-43", null, null, null));
		maccAdresses.add(new WifiAccessPoints("8c:04:ff:04:52:74", "-55", null, null, null));
		GeoLocationRequestEntity geoLocationRequestEntity = new GeoLocationRequestEntity(maccAdresses);
		return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(geoLocationRequestEntity))
				.readEntity(GeoLocation.class);
	}
}
