package main;

import rest.StationRequest;
import transport.Transportations;

public class Main {
	public static void main(String[] args) {
		StationRequest restRequest = new StationRequest();
		restRequest.xy(47, 7);
		// restRequest.query("Luzern");
		restRequest.transportations(Transportations.BUS, Transportations.S_SN_R);
		restRequest.get().forEach(station -> System.out.println(station.getName()));
	}
}
