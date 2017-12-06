package rest;

import transport.Connection;
import transport.Connection.ConnectionList;

public class ConnectionRequest extends RestRequest<Connection, ConnectionList> {

	public ConnectionRequest(String from, String to) {
		super("connections", ConnectionList.class);
		addParam(new Parameter("from", from));
		addParam(new Parameter("to", to));
	}
}
