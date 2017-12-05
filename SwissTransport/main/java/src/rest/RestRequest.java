package rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public abstract class RestRequest<T> {

	private static final String HTTP_TRANSPORT_OPENDATA_CH_V1 = "http://transport.opendata.ch/v1/";
	private String path;
	private Class<? extends JsonList<T>> clazz;
	private Set<Parameter> params = new HashSet<>();

	protected RestRequest(String path, Class<? extends JsonList<T>> clazz) {
		this.path = path;
		this.clazz = clazz;
	}

	protected List<T> get(Set<Parameter> params) {
		WebTarget query = ClientBuilder.newClient().target(HTTP_TRANSPORT_OPENDATA_CH_V1).path(path);
		for (Parameter param : params) {
			for (String parameterValue : param.getValues()) {
				query = query.queryParam(param.getKey(), parameterValue);
				System.out.println(parameterValue);
			}
		}
		return query.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(clazz).getList();
	}

	public List<T> get() {
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
