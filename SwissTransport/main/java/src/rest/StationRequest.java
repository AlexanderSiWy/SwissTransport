package rest;

import transport.Station;
import transport.Station.StationList;
import transport.Transportations;

public class StationRequest extends RestRequest<Station> {

	public StationRequest() {
		super("locations", StationList.class);
	}

	public void query(String query) {
		addParam(new Parameter("query", query));
	}

	public void xy(double x, double y) {
		addParam(new Parameter("x", String.valueOf(x)));
		addParam(new Parameter("y", String.valueOf(y)));
	}

	public void type(String type) {
		addParam(new Parameter("type", type));
	}

	public void transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
	}
}
