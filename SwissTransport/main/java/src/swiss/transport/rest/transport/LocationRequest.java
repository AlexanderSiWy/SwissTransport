package swiss.transport.rest.transport;

import swiss.transport.entity.Location;
import swiss.transport.entity.Location.LocationList;
import swiss.transport.entity.Transportations;

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

	public LocationRequest query(String query) {
		addParam(new Parameter("query", query));
		return this;
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
	public LocationRequest xy(double x, double y) {
		addParam(new Parameter("x", String.valueOf(x)));
		addParam(new Parameter("y", String.valueOf(y)));
		return this;
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
	public LocationRequest type(String type) {
		addParam(new Parameter("type", type));
		return this;
	}

	/**
	 * 
	 * @param transportations
	 *            Only with x and y parameter. Transportation means;
	 */
	public LocationRequest transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
		return this;
	}
}
