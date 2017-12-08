package swiss.transport.rest.transport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import swiss.transport.entity.transport.Accessibility;
import swiss.transport.entity.transport.Connection;
import swiss.transport.entity.transport.Transportations;
import swiss.transport.entity.transport.Connection.ConnectionList;

public class ConnectionRequest extends TransportRestRequest<Connection, ConnectionList> {
	/**
	 * 
	 * @param from
	 *            Specifies the departure location of the connection
	 * @param to
	 *            Specifies the arrival location of the connection
	 */
	public ConnectionRequest(String from, String to) {
		super("connections", ConnectionList.class);
		from(from);
		to(to);
	}

	/**
	 * 
	 * @param to
	 *            Specifies the arrival location of the connection
	 */
	private ConnectionRequest to(String to) {
		addParam(new Parameter("to", to));
		return this;
	}

	/**
	 * 
	 * @param from
	 *            Specifies the departure location of the connection
	 */
	private ConnectionRequest from(String from) {
		addParam(new Parameter("from", from));
		return this;
	}

	/**
	 * 
	 * @param via
	 *            Specifies up to five via locations.
	 */
	public ConnectionRequest via(String via1, String via2, String via3, String via4, String via5) {
		addParam(new Parameter("via[]", via1, via2, via3, via4, via5)); // TODO
		return this;
	}

	/**
	 * 
	 * @param date
	 *            Date of the connection
	 */
	public ConnectionRequest date(LocalDate date) {
		addParam(new Parameter("date", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date)));
		return this;
	}

	/**
	 * 
	 * @param time
	 *            Time of the connection
	 */
	public ConnectionRequest date(LocalTime time) {
		addParam(new Parameter("time", DateTimeFormatter.ofPattern("HH:mm").format(time)));
		return this;
	}

	/**
	 * 
	 * @param isArrivalTime
	 *            defaults to 0, if set to 1 the passed date and time is the arrival
	 *            time
	 */
	public ConnectionRequest isArrivalTime(boolean isArrivalTime) {
		addParam(new Parameter("isArrivalTime", isArrivalTime ? "1" : "0"));
		return this;
	}

	/**
	 * 
	 * @param transportations
	 *            Transportation means;
	 */
	public ConnectionRequest transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
		return this;
	}

	/**
	 * 
	 * @param limit
	 *            1 - 6. Specifies the number of connections to return. If several
	 *            connections depart at the same time they are counted as 1.
	 */
	public ConnectionRequest limit(int limit) {
		addParam(new Parameter("limit", String.valueOf(limit)));
		return this;
	}

	/**
	 * 
	 * @param page
	 *            0 - 3. Allows pagination of connections. Zero-based, so first page
	 *            is 0, second is 1, third is 2 and so on.
	 */
	public ConnectionRequest page(int page) {
		addParam(new Parameter("page", String.valueOf(page)));
		return this;
	}

	/**
	 * 
	 * @param direct
	 *            defaults to 0, if set to 1 only direct connections are allowed
	 */
	public ConnectionRequest direct(boolean direct) {
		addParam(new Parameter("direct", direct ? "1" : "0"));
		return this;
	}

	/**
	 * 
	 * @param sleeper
	 *            defaults to 0, if set to 1 only night trains containing beds are
	 *            allowed, implies direct=1
	 */
	public ConnectionRequest sleeper(boolean sleeper) {
		addParam(new Parameter("sleeper", sleeper ? "1" : "0"));
		return this;
	}

	/**
	 * 
	 * @param couchette
	 *            defaults to 0, if set to 1 only night trains containing couchettes
	 *            are allowed, implies direct=1
	 */
	public ConnectionRequest couchette(boolean couchette) {
		addParam(new Parameter("couchette", couchette ? "1" : "0"));
		return this;
	}

	/**
	 * 
	 * @param bike
	 *            defaults to 0, if set to 1 only trains allowing the transport of
	 *            bicycles are allowed
	 */
	public ConnectionRequest bike(boolean bike) {
		addParam(new Parameter("bike", bike ? "1" : "0"));
		return this;
	}

	/**
	 * 
	 * @param accessibility
	 *            Possible values are independent_boarding, assisted_boarding, and
	 *            advanced_notice
	 */
	public ConnectionRequest accessibility(Accessibility accessibility) {
		addParam(new Parameter("accessibility", accessibility.value()));
		return this;
	}
}
