package main;

import rest.ConnectionRequest;

public class Main {
	public static void main(String[] args) {
		// StationRequest restRequest = new StationRequest();
		// restRequest.xy(47, 7);
		// restRequest.query("Luzern");
		// restRequest.transportations(Transportations.BUS, Transportations.S_SN_R);
		// restRequest.get().forEach(station ->
		// System.out.println(station.getCoordinate().getType()));

		ConnectionRequest connectionRequest = new ConnectionRequest("luzern", "sursee");
		connectionRequest.get().getList().forEach(connection -> System.out.println(connection.getDuration()));
	}
}
