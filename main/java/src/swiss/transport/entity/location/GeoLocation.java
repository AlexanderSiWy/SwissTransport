package swiss.transport.entity.location;

public class GeoLocation {
	private GeoCoordinates location;
	private double accuracy;

	public GeoCoordinates getLocation() {
		return location;
	}

	public double getAccuracy() {
		return accuracy;
	}
}
