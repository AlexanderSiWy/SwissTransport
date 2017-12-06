package transport;

public class Stop {
	private Location station;
	private String arrival;
	private String arrivalTimestamp;
	private String departure;
	private String departureTimestamp;
	private String delay;
	private String platform;
	private Prognosis prognosis;
	private String realtimeAvailability;
	private Location location;

	public Location getStation() {
		return station;
	}

	public String getArrival() {
		return arrival;
	}

	public String getArrivalTimestamp() {
		return arrivalTimestamp;
	}

	public String getDeparture() {
		return departure;
	}

	public String getDepartureTimestamp() {
		return departureTimestamp;
	}

	public String getDelay() {
		return delay;
	}

	public String getPlatform() {
		return platform;
	}

	public Prognosis getPrognosis() {
		return prognosis;
	}

	public String getRealtimeAvailability() {
		return realtimeAvailability;
	}

	public Location getLocation() {
		return location;
	}
}
