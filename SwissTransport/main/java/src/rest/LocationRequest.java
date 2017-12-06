package rest;

import transport.Location;
import transport.Location.LocationList;
import transport.Transportations;

public class LocationRequest extends RestRequest<Location, LocationList> {

	public LocationRequest() {
		super("locations", LocationList.class);
	}

	/**
	 * 
	 * @param query
	 *            Specifies the location name to search for
	 * 
	 *            Basel
	 */

	public void query(String query) {
		addParam(new Parameter("query", query));
	}

	/**
	 * 
	 * @param x
	 *            Latitude
	 * 
	 *            47.476001
	 * @param y
	 *            Longitude
	 * 
	 *            8.306130
	 */
	public void xy(double x, double y) {
		addParam(new Parameter("x", String.valueOf(x)));
		addParam(new Parameter("y", String.valueOf(y)));
	}

	/**
	 * 
	 * @param type
	 *            Only with query parameter. Specifies the location type, possible
	 *            types are: all (default): Looks up for all types of locations
	 *            station: Looks up for stations (train station, bus station) poi:
	 *            Looks up for points of interest (Clock tower, China garden)
	 *            address: Looks up for an address (Zurich Bahnhofstrasse 33)
	 */
	public void type(String type) {
		addParam(new Parameter("type", type));
	}

	/**
	 * 
	 * @param transportations
	 *            Only with x and y parameter. Transportation means;
	 */
	public void transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
	}
}
