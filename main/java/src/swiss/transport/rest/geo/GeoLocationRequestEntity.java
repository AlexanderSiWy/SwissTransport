package swiss.transport.rest.geo;

import java.util.List;

public class GeoLocationRequestEntity {
	private List<WifiAccessPoints> wifiAccessPoints;

	public GeoLocationRequestEntity(List<WifiAccessPoints> wifiAccessPoints) {
		this.wifiAccessPoints = wifiAccessPoints;
	}

	public List<WifiAccessPoints> getWifiAccessPoints() {
		return wifiAccessPoints;
	}
}
