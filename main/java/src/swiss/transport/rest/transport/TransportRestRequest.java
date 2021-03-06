package swiss.transport.rest.transport;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class TransportRestRequest<T, L extends JsonList<T>> {

	private static final String HTTP_TRANSPORT_OPENDATA_CH_V1 = "http://transport.opendata.ch/v1/";
	private String path;
	private Class<L> clazz;
	private Set<Parameter> params = new HashSet<>();

	protected TransportRestRequest(String path, Class<L> clazz) {
		this.path = path;
		this.clazz = clazz;
	}

	protected L get(Set<Parameter> params) {
		WebTarget query = ClientBuilder.newClient().target(HTTP_TRANSPORT_OPENDATA_CH_V1).path(path);
		for (Parameter param : params) {
			for (String parameterValue : param.getValues()) {
				if (parameterValue != null && !parameterValue.isEmpty()) {
					query = query.queryParam(param.getKey(), parameterValue);
				}
			}
		}
		Response response = query.request(MediaType.APPLICATION_JSON_TYPE).get();
		return response.readEntity(clazz);
	}

	public L get() {
		return get(params);
	}

	public void addParam(Parameter param) {
		params.remove(param);
		params.add(param);
	}

	public Set<Parameter> getParams() {
		return params;
	}
}
