package swiss.transport.entity.transport;

public class Section {
	private Journey journey;
	private Walk walk;
	private Stop departure;
	private Stop arrival;

	public Journey getJourney() {
		return journey;
	}

	public Walk getWalk() {
		return walk;
	}

	public Stop getDeparture() {
		return departure;
	}

	public Stop getArrival() {
		return arrival;
	}
}
