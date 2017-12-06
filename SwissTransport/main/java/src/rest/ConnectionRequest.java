package rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import transport.Accessibility;
import transport.Connection;
import transport.Connection.ConnectionList;
import transport.Transportations;

public class ConnectionRequest extends RestRequest<Connection, ConnectionList> {
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
	private void to(String to) {
		addParam(new Parameter("to", to));
	}

	/**
	 * 
	 * @param from
	 *            Specifies the departure location of the connection
	 */
	private void from(String from) {
		addParam(new Parameter("from", from));
	}

	/**
	 * 
	 * @param via
	 *            Specifies up to five via locations.
	 */
	public void via(String via1, String via2, String via3, String via4, String via5) {
		addParam(new Parameter("via[]", via1, via2, via3, via4, via5)); // TODO
	}

	/**
	 * 
	 * @param date
	 *            Date of the connection
	 */
	public void date(LocalDate date) {
		addParam(new Parameter("date", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date)));
	}

	/**
	 * 
	 * @param time
	 *            Time of the connection
	 */
	public void date(LocalTime time) {
		addParam(new Parameter("time", DateTimeFormatter.ofPattern("HH:mm").format(time)));
	}

	/**
	 * 
	 * @param isArrivalTime
	 *            defaults to 0, if set to 1 the passed date and time is the arrival
	 *            time
	 */
	public void isArrivalTime(boolean isArrivalTime) {
		addParam(new Parameter("isArrivalTime", isArrivalTime ? "1" : "0"));
	}

	/**
	 * 
	 * @param transportations
	 *            Transportation means;
	 */
	public void transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
	}

	/**
	 * 
	 * @param limit
	 *            1 - 6. Specifies the number of connections to return. If several
	 *            connections depart at the same time they are counted as 1.
	 */
	public void limit(byte limit) {
		addParam(new Parameter("limit", String.valueOf(limit)));
	}

	/**
	 * 
	 * @param page
	 *            0 - 3. Allows pagination of connections. Zero-based, so first page
	 *            is 0, second is 1, third is 2 and so on.
	 */
	public void page(byte page) {
		addParam(new Parameter("page", String.valueOf(page)));
	}

	/**
	 * 
	 * @param direct
	 *            defaults to 0, if set to 1 only direct connections are allowed
	 */
	public void direct(boolean direct) {
		addParam(new Parameter("direct", direct ? "1" : "0"));
	}

	/**
	 * 
	 * @param sleeper
	 *            defaults to 0, if set to 1 only night trains containing beds are
	 *            allowed, implies direct=1
	 */
	public void sleeper(boolean sleeper) {
		addParam(new Parameter("sleeper", sleeper ? "1" : "0"));
	}

	/**
	 * 
	 * @param couchette
	 *            defaults to 0, if set to 1 only night trains containing couchettes
	 *            are allowed, implies direct=1
	 */
	public void couchette(boolean couchette) {
		addParam(new Parameter("couchette", couchette ? "1" : "0"));
	}

	/**
	 * 
	 * @param bike
	 *            defaults to 0, if set to 1 only trains allowing the transport of
	 *            bicycles are allowed
	 */
	public void bike(boolean bike) {
		addParam(new Parameter("bike", bike ? "1" : "0"));
	}

	/**
	 * 
	 * @param accessibility
	 *            Possible values are independent_boarding, assisted_boarding, and
	 *            advanced_notice
	 */
	public void accessibility(Accessibility accessibility) {
		addParam(new Parameter("accessibility", accessibility.value()));
	}
}
