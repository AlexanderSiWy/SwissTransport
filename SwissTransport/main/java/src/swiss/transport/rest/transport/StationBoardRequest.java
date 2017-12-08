package swiss.transport.rest.transport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import swiss.transport.entity.StationBoard;
import swiss.transport.entity.StationBoard.StationBoardList;
import swiss.transport.entity.Transportations;

public class StationBoardRequest extends TransportRestRequest<StationBoard, StationBoardList> {
	/**
	 * 
	 * @param station
	 *            Specifies the location of which a stationboard should be returned
	 */
	public StationBoardRequest(String station, String id) {
		super("stationboard", StationBoardList.class);
		station(station);
		id(id);
	}

	public StationBoardRequest(String id) {
		this(null, id);
	}

	/**
	 * 
	 * @param station
	 *            Specifies the location of which a stationboard should be returned
	 */
	private StationBoardRequest station(String station) {
		addParam(new Parameter("station", station));
		return this;
	}

	/**
	 * 
	 * @param id
	 *            The id of the station whose stationboard should be returned.
	 *            Alternative to the station parameter; one of these two is
	 *            required. If both an id and a station are specified the id has
	 *            precedence.
	 */
	public StationBoardRequest id(String id) {
		addParam(new Parameter("id", id));
		return this;
	}

	/**
	 * 
	 * @param limit
	 *            Number of departing connections to return. This is not a hard
	 *            limit - if multiple connections leave at the same time it'll
	 *            return any connections that leave at the same time as the last
	 *            connection within the limit. For example: limit=4 will return
	 *            connections leaving at : 19:30 19:32 19:32 19:35 19:35 Because one
	 *            of the connections leaving at 19:35 is within the limit, all
	 *            connections leaving at 19:35 are shown.
	 */
	public StationBoardRequest limit(int limit) {
		addParam(new Parameter("limit", String.valueOf(limit)));
		return this;
	}

	/**
	 * 
	 * @param transportations
	 *            Transportation means;
	 */
	public StationBoardRequest transportations(Transportations... transportations) {
		addParam(new Parameter("transportations[]", Transportations.toStringArray(transportations)));
		return this;
	}

	/**
	 * 
	 * @param datetime
	 *            Date and time of departing connections, in the format YYYY-MM-DD
	 *            hh:mm.
	 */
	public StationBoardRequest datetime(LocalDateTime datetime) {
		addParam(new Parameter("datetime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(datetime)));
		return this;
	}

	/**
	 * 
	 * @param arrival
	 *            departure (default) or arrival (default = false)
	 */
	public StationBoardRequest arrival(boolean arrival) {
		addParam(new Parameter("type", arrival ? "arrival" : "departure "));
		return this;
	}
}
