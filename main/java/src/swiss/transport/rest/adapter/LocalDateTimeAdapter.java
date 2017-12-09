package swiss.transport.rest.adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
	@Override
	public LocalDateTime unmarshal(String s) throws Exception {
		return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxx"));
	}

	@Override
	public String marshal(LocalDateTime dateTime) throws Exception {
		return dateTime.toString();
	}

}
