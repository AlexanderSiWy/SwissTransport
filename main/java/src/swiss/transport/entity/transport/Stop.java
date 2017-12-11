package swiss.transport.entity.transport;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import swiss.transport.rest.adapter.LocalDateTimeAdapter;

public class Stop {
	private Location station;
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime arrival;
	private String arrivalTimestamp;
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime departure;
	private String departureTimestamp;
	private String delay;
	private String platform;
	private Prognosis prognosis;
	private String realtimeAvailability;
	private Location location;

	public Location getStation() {
		return station;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public String getArrivalTimestamp() {
		return arrivalTimestamp;
	}

	public LocalDateTime getDeparture() {
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

	public void setLocation(Location location) {
		this.location = location;
	}
}
